package com.tcc.redonate.endpoint.controller;

import com.tcc.redonate.endpoint.service.DoacaoService;
import com.tcc.redonate.endpoint.service.DoadorService;
import com.tcc.redonate.endpoint.service.InstituicaoService;
import com.tcc.redonate.endpoint.service.UsuarioService;
import com.tcc.redonate.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DoacaoController {
    private final DoacaoService doacaoService;
    private final DoadorService doadorService;
    private final InstituicaoService instituicaoService;
    private final UsuarioService usuarioService;

    @GetMapping(value = "/doacoes/chat")
    public String carregarChat(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        Usuario usuarioLogado = usuarioService.getUsuarioLogado(request);

        if(usuarioLogado == null){
            redirectAttributes.addFlashAttribute("accessDenial", true);
            return "redirect:/";
        }

        model.addAttribute("dadosUsuario", usuarioLogado);
        List<Doacao> doacoes;

        if(usuarioLogado.getDoador() != null){
            doacoes = doacaoService.buscarDoacoesAtivas(usuarioLogado.getDoador());
            model.addAttribute("isDoador", true);
        }else{
            doacoes = doacaoService.buscarDoacoesVisiveis(usuarioLogado.getInstituicao());
            model.addAttribute("isDoador", false);
        }

        model.addAttribute("doacoesExistentes", doacoes);

        return "chat";
    }

    @PostMapping(value = "/instituicao/{inst}")
    public RedirectView criarDoacao(@PathVariable("inst") Long inst, Doacao doacao, HttpServletRequest request, RedirectAttributes redirectAttributes){
        doacao.setAtivo(true);
        doacao.setVisivel(true);
        Instituicao instituicao = instituicaoService.findOne(inst);
        Doador doador = doadorService.findByUsuarioLogado(request);

        RedirectView redirectView = new RedirectView("/instituicoes", false);
        boolean success = doacaoService.create(doacao, doador, instituicao);
        redirectAttributes.addFlashAttribute("success", success);

        return redirectView;
    }

    @PatchMapping(value = "/doacao/desativar/{id}")
    public ResponseEntity<String> desativarChat(@PathVariable Long id){
        doacaoService.desativarChat(id);
        return ResponseEntity.ok("Chat desativado com sucesso!");
    }

    @PatchMapping(value = "/doacao/apagar/{id}")
    public ResponseEntity<String> apagarChat(@PathVariable Long id) {
        doacaoService.apagarChat(id);
        return ResponseEntity.ok("Chat apagado com sucesso!");
    }

    @GetMapping(value = "/doacoes/dados")
    public String carregarDadosDoacoes(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        Usuario dadosUsuario = usuarioService.getUsuarioLogado(request);
        boolean queroDoadores = false;

        if(dadosUsuario != null) {
            List<Doacao> doacoes = new ArrayList<Doacao>();

            if (dadosUsuario.getInstituicao() != null) {
                doacoes = dadosUsuario.getInstituicao().getDoacoes();
                queroDoadores = true;
            } else {
                doacoes = dadosUsuario.getDoador().getDoacoes();
            }

            if(!doacoes.isEmpty()){
                Collections.sort(doacoes);

                List<Object> outraPonta = new ArrayList<>();
                List<String> causasDoacao = new ArrayList<>();
                List<Float> valoresPorCausa = new ArrayList<>();
                float soma = doacoes.get(0).getValorDoacao();

                float valorTotal = doacaoService.carregarDadosDoacao(doacoes, soma, causasDoacao, valoresPorCausa, queroDoadores, outraPonta);

                model.addAttribute("outraPonta", outraPonta);
                model.addAttribute("causas", causasDoacao);
                model.addAttribute("valores", valoresPorCausa);
                model.addAttribute("total", valorTotal);

            }
            model.addAttribute("doacoes", doacoes);
            model.addAttribute("dadosUser", dadosUsuario);
            model.addAttribute("queroDoadores", queroDoadores);

            return "dadosDoacoes";

        }else{

            redirectAttributes.addFlashAttribute("accessDenial", true);
            return "redirect:/";

        }
    }

}
