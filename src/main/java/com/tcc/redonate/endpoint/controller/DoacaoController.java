package com.tcc.redonate.endpoint.controller;

import com.tcc.redonate.endpoint.service.DoacaoService;
import com.tcc.redonate.endpoint.service.DoadorService;
import com.tcc.redonate.endpoint.service.InstituicaoService;
import com.tcc.redonate.endpoint.service.UsuarioService;
import com.tcc.redonate.model.*;
import lombok.RequiredArgsConstructor;
import org.dom4j.rule.Mode;
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
            doacoes = usuarioLogado.getDoador().getDoacoes();
            model.addAttribute("isDoador", true);
        }else{
            doacoes = usuarioLogado.getInstituicao().getDoacoes();
            model.addAttribute("isDoador", false);
        }

        model.addAttribute("doacoesExistentes", doacoes);

        return "chat";
    }

    @PostMapping(value = "/instituicao/{inst}")
    public RedirectView criarDoacao(@PathVariable("inst") Long inst, Doacao doacao, HttpServletRequest request, RedirectAttributes redirectAttributes){
        Instituicao instituicao = instituicaoService.findOne(inst);
        Doador doador = doadorService.findByUsuarioLogado(request);

        RedirectView redirectView = new RedirectView("/instituicoes", false);
        boolean success = doacaoService.create(doacao, doador, instituicao);
        redirectAttributes.addFlashAttribute("success", success);

        return redirectView;
    }

    @GetMapping(value = "/doacoes/dados")
    public String carregarDadosDoacoes(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        Usuario dadosUsuario = usuarioService.getUsuarioLogado(request);
        boolean queroDoadores = false;

        if(dadosUsuario != null) {
            List<Doacao> doacoes = new ArrayList<Doacao>();

            if (dadosUsuario.getInstituicao() != null) {
                Instituicao dadosInst = dadosUsuario.getInstituicao();
                doacoes = dadosInst.getDoacoes();
                queroDoadores = true;
            } else {
                Doador dadosDoad = dadosUsuario.getDoador();
                doacoes = dadosDoad.getDoacoes();
            }

            if(!doacoes.isEmpty()){
                Collections.sort(doacoes);

                List<Object> outraPonta = new ArrayList<>();
                List<String> causasDoacao = new ArrayList<>();
                List<Float> valoresPorCausa = new ArrayList<>();
                float valorTotal = 0;
                float soma = doacoes.get(0).getValorDoacao();

                for(int i = 0; i < doacoes.size(); i++){

                    Doacao doacaoAtual = doacoes.get(i);
                    if(i != 0) {
                        Doacao doacaoAnterior = doacoes.get(i - 1);
                        if(doacaoAtual.getCausaDoacao().equals(doacaoAnterior.getCausaDoacao())){
                            soma += doacaoAtual.getValorDoacao();
                        }else{
                            causasDoacao.add(doacaoAnterior.getCausaDoacao());
                            valoresPorCausa.add(soma);
                            valorTotal += soma;
                            soma = doacaoAtual.getValorDoacao();
                        }
                    }

                    if(i == (doacoes.size()-1)){
                        causasDoacao.add(doacaoAtual.getCausaDoacao());
                        valoresPorCausa.add(soma);
                        valorTotal += soma;
                    }

                    if(queroDoadores)
                        outraPonta.add(doacaoAtual.getDoador());
                    else
                        outraPonta.add(doacaoAtual.getInstituicao());
                }

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
