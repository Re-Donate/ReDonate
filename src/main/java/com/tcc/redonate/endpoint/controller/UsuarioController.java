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

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final DoadorService doadorService;

    @GetMapping
    public String index(HttpServletRequest request){
        request.getSession().invalidate();
        return "index";
    }

    @RequestMapping(value = "/cadastrar", method = RequestMethod.GET)
    public String cadastrarForm(){ return "cadastrarUser"; }

    @RequestMapping(value = "/cadastrar", method = RequestMethod.POST)
    public String cadastrarUser(Usuario usuario, HttpServletRequest request){
        request.getSession().setAttribute("lastCreatedUser", usuario);

        Boolean isDoador = request.getParameter("tipoUsuario").equals("Doador");

        if(isDoador)
            return "redirect:/doadores/cadastrarDoador";
        else
            return "redirect:/instituicoes/cadastrarInstituicao";
    }

    @RequestMapping(value = "/logar", method = RequestMethod.POST)
    public String logarUsuario(Usuario usuario, HttpServletRequest request, Model model) {
        Usuario testLogin = usuarioService.login(usuario);
        if (testLogin != null) {
            request.getSession().setAttribute("idLogin", testLogin.getId());
            if(doadorService.isDoador(testLogin.getId()))
                return "redirect:/instituicoes";
            else
                return "redirect:/instituicoes/dados";
        } else {
            model.addAttribute("falhaLogin", 1);
            return "index";
        }
    }
}
