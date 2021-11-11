package com.tcc.redonate.endpoint.controller;

import com.tcc.redonate.endpoint.service.InstituicaoService;
import com.tcc.redonate.endpoint.service.UsuarioService;
import com.tcc.redonate.entity.Doacao;
import com.tcc.redonate.entity.Instituicao;
import com.tcc.redonate.entity.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InstituicaoController {
    private final InstituicaoService instituicaoService;
    private final UsuarioService usuarioService;

    @GetMapping(value = "/instituicoes")
    public String listarInstituicoes(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes){
        Usuario usuarioLogado = usuarioService.getUsuarioLogado(request);

        if(usuarioLogado != null && usuarioLogado.getDoador() != null) {
            model.addAttribute("dadosDoador", usuarioLogado);

            Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
            if (inputFlashMap != null)
                model.addAttribute("success", inputFlashMap.get("success"));

            return "listarInst";
        }else {

            if(usuarioLogado != null) {
                redirectAttributes.addFlashAttribute("permissionDenial", "Doadores");
            }else {
                redirectAttributes.addFlashAttribute("accessDenial", true);
            }

            return "redirect:/";

        }
    }

    @GetMapping(value = "/instituicoes/filtro")
    public ResponseEntity<List<Usuario>> filtrarInstituicoes(HttpServletRequest request){
        String necessidade1 = request.getParameter("necessidade1");
        String necessidade2 = request.getParameter("necessidade2");
        String necessidade3 = request.getParameter("necessidade3");
        String necessidade4 = request.getParameter("necessidade4");
        String necessidade5 = request.getParameter("necessidade5");
        String necessidade6 = request.getParameter("necessidade6");

        List<Usuario> instituicoesFiltradas = usuarioService.filtrarInstituicoes(necessidade1, necessidade2, necessidade3, necessidade4, necessidade5, necessidade6);

        return ResponseEntity.ok(instituicoesFiltradas);
    }

    @GetMapping(value = "/instituicoes/dados")
    public String dadosInstituicao(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes){
        Usuario usuarioLogado = usuarioService.getUsuarioLogado(request);

        if(usuarioLogado != null && usuarioLogado.getInstituicao() != null) {
            model.addAttribute("dadosInst", usuarioLogado.getInstituicao());

            Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
            if (inputFlashMap != null)
                model.addAttribute("success", inputFlashMap.get("success"));

            return "dadosInstituicao";
        }else {

            if(usuarioLogado != null){
                redirectAttributes.addFlashAttribute("permissionDenial", "Instituições");
            }else {
                redirectAttributes.addFlashAttribute("accessDenial", true);
            }

            return "redirect:/";
        }
    }

    @PostMapping(value = "/instituicoes/dados")
    public RedirectView atualizarDadosInstituicao(Usuario usuario, Instituicao instituicao, HttpServletRequest request, RedirectAttributes redirectAttributes){
        RedirectView redirectView = new RedirectView("/instituicoes/dados", false);

        Usuario usuarioLogado = usuarioService.getUsuarioLogado(request);
        List<Doacao> doacoes = instituicaoService.findOne(instituicao.getId()).getDoacoes();

        instituicao.setDoacoes(doacoes);

        usuarioService.atualizarUsuario(usuarioLogado, usuario);
        boolean success = usuarioService.saveUsuarioInstituicao(usuario, instituicao);
        redirectAttributes.addFlashAttribute("success", success);

        return redirectView;
    }

    @GetMapping(value = "/instituicao/{inst}")
    public String detalharInstituicao(@PathVariable("inst") int inst, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes){
        Usuario usuarioLogado = usuarioService.getUsuarioLogado(request);

        if(usuarioLogado != null && usuarioLogado.getDoador() != null) {
            model.addAttribute("dadosDoador", usuarioLogado);

            Instituicao instDetail = instituicaoService.findOne((long) inst);
            if (instDetail != null)
                model.addAttribute("detalhes", instDetail);

            return "detalharInst";
        }else {

            if(usuarioLogado != null) {
                redirectAttributes.addFlashAttribute("permissionDenial", "Doadores");
            }else {
                redirectAttributes.addFlashAttribute("accessDenial", true);
            }

            return "redirect:/";
        }
    }

    @GetMapping(value = "/instituicoes/cadastrarInstituicao")
    public String cadastrarInstituicaoForm(){ return "cadastrarInstituicao"; }

    @PostMapping(value = "/instituicoes/cadastrarInstituicao")
    public RedirectView cadastrarInstituicao(Usuario usuario, Instituicao instituicao, HttpServletRequest request, RedirectAttributes redirectAttributes){
        RedirectView redirectView = new RedirectView("/", false);

        Usuario User = (Usuario) request.getSession().getAttribute("lastCreatedUser");
        usuario.setEmailUsuario(User.getEmailUsuario());
        usuario.setSenhaUsuario(User.getSenhaUsuario());

        if(usuario.getCpfUsuario().isBlank()) {
            usuario.setCpfUsuario(null);
            if(instituicaoService.isCNPJUnique(instituicao)){
                boolean success = usuarioService.saveUsuarioInstituicao(usuario, instituicao);
                redirectAttributes.addFlashAttribute("success", success);
            }else{
                redirectAttributes.addFlashAttribute("notUnique", "CNPJ");
            }
        }else {
            if (instituicao.getCnpjInstituicao().isBlank()) {
                instituicao.setCnpjInstituicao(null);
                if(usuarioService.isCPFUnique(usuario)){
                    boolean success = usuarioService.saveUsuarioInstituicao(usuario, instituicao);
                    redirectAttributes.addFlashAttribute("success", success);
                }else{
                    redirectAttributes.addFlashAttribute("notUnique", "CPF");
                }
            }
        }


        return redirectView;
    }

    @GetMapping(value = "/validar/cnpj")
    public ResponseEntity<Boolean> validarCNPJ(@PathParam("cnpj") String cnpj) {
        return ResponseEntity.ok(instituicaoService.isCNPJValid(cnpj));
    }
}
