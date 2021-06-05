package com.tcc.redonate.endpoint.controller;

import com.tcc.redonate.endpoint.service.UsuarioService;
import com.tcc.redonate.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UsuarioController {
    private final UsuarioService usuarioService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(HttpServletRequest request){
        request.getSession().invalidate();
        return "index";
    }

    @RequestMapping(value = "/logar", method = RequestMethod.POST)
    public String logarUsuario(Usuario usuario, HttpServletRequest request, Model model) {
        Usuario testLogin = usuarioService.login(usuario);
        if (testLogin != null) {
            request.getSession().setAttribute("idLogin", testLogin.getId());
            return "redirect:/instituicoes";
        } else {
            model.addAttribute("falhaLogin", 1);
            return "index";
        }
    }
}
