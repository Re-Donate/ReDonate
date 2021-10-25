package com.tcc.redonate.endpoint.controller;

import com.tcc.redonate.endpoint.service.DoadorService;
import com.tcc.redonate.endpoint.service.UsuarioService;
import com.tcc.redonate.model.Doacao;
import com.tcc.redonate.model.Doador;
import com.tcc.redonate.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/doadores")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DoadorControler {
    private final UsuarioService usuarioService;
    private final DoadorService doadorService;

    @GetMapping(value = "/cadastrarDoador")
    public String cadastrarDoadorForm(){ return "cadastrarDoador"; }

    @PostMapping(value = "/cadastrarDoador")
    public RedirectView cadastrarDoador(Usuario usuario, Doador doador, HttpServletRequest request, RedirectAttributes redirectAttributes){
        RedirectView redirectView = new RedirectView("/", false);

        Usuario User = (Usuario) request.getSession().getAttribute("lastCreatedUser");
        usuario.setEmailUsuario(User.getEmailUsuario());
        usuario.setSenhaUsuario(User.getSenhaUsuario());

        if(usuarioService.isCPFUnique(usuario)) {
            boolean success = usuarioService.saveUsuarioDoador(usuario, doador);
            redirectAttributes.addFlashAttribute("success", success);
        }else{
            redirectAttributes.addFlashAttribute("notUnique", "CPF");
        }

        return redirectView;
    }

    @GetMapping(value = "/dados")
    public String dadosDoador(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes){
        Doador dadosDoador = doadorService.findByUsuarioLogado(request);

        if(dadosDoador != null){
            model.addAttribute("dadosDoad", dadosDoador);

            Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
            if (inputFlashMap != null)
                model.addAttribute("success", inputFlashMap.get("success"));

            return "dadosDoador";
        }else{
            redirectAttributes.addFlashAttribute("accessDenial", true);
            return "redirect:/";
        }

    }

    @PostMapping(value = "/dados")
    public RedirectView atualizarDadosDoador(Usuario usuario, Doador doador, HttpServletRequest request, RedirectAttributes redirectAttributes){
        RedirectView redirectView = new RedirectView("/doadores/dados", false);

        Usuario usuarioLogado = usuarioService.getUsuarioLogado(request);
        List<Doacao> doacoes = doadorService.findOne(doador.getId()).getDoacoes();

        doador.setDoacoes(doacoes);

        usuarioService.atualizarUsuario(usuarioLogado, usuario);
        boolean success = usuarioService.saveUsuarioDoador(usuario, doador);
        redirectAttributes.addFlashAttribute("success", success);

        return redirectView;
    }

}
