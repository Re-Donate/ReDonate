package com.tcc.redonate.endpoint.controller;

import com.tcc.redonate.endpoint.service.DoadorService;
import com.tcc.redonate.endpoint.service.UsuarioService;
import com.tcc.redonate.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final DoadorService doadorService;

    @GetMapping
    public String index(HttpServletRequest request, Model model){
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if(inputFlashMap != null) {
            model.addAttribute("success", inputFlashMap.get("success"));
            model.addAttribute("falhaLogin", inputFlashMap.get("falhaLogin"));
            model.addAttribute("accessDenial", inputFlashMap.get("accessDenial"));
            model.addAttribute("emailNotUnique", inputFlashMap.get("emailNotUnique"));
        }

        request.getSession().invalidate();
        return "index";
    }

    @RequestMapping(value = "/cadastrar", method = RequestMethod.GET)
    public String cadastrarForm(){ return "cadastrarUser"; }

    @RequestMapping(value = "/cadastrar", method = RequestMethod.POST)
    public RedirectView cadastrarUser(Usuario usuario, HttpServletRequest request, RedirectAttributes redirectAttributes){
        RedirectView redirectView = new RedirectView("", false);

        if(usuarioService.isEmailUnique(usuario)) {

            request.getSession().setAttribute("lastCreatedUser", usuario);

            Boolean isDoador = request.getParameter("tipoUsuario").equals("Doador");

            if (isDoador)
                redirectView.setUrl("/doadores/cadastrarDoador");
            else
                redirectView.setUrl("/instituicoes/cadastrarInstituicao");

        }else {

            redirectView.setUrl("/");
            redirectAttributes.addFlashAttribute("emailNotUnique", true);
        }

        return  redirectView;
    }

    @RequestMapping(value = "/logar", method = RequestMethod.POST)
    public RedirectView logarUsuario(Usuario usuario, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        RedirectView redirectView = new RedirectView("", false);
        Usuario testLogin = usuarioService.login(usuario);

        if (testLogin != null) {
            request.getSession().setAttribute("idLogin", testLogin.getId());

            if(doadorService.isDoador(testLogin.getId()))
                redirectView.setUrl("/instituicoes");
            else
                redirectView.setUrl("/instituicoes/dados");

        } else {
            redirectAttributes.addFlashAttribute("falhaLogin", true);
            redirectView.setUrl("/");
        }

        return redirectView;
    }
}
