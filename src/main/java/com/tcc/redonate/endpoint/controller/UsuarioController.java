package com.tcc.redonate.endpoint.controller;

import com.tcc.redonate.endpoint.service.UsuarioService;
import com.tcc.redonate.model.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping(value = "/Users")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UsuarioController {
    private final UsuarioService usuarioService;

    @RequestMapping(method = RequestMethod.GET)
    public String listarUsuarios(Model model){
        List<Usuario> userList = usuarioService.list();

        if(userList != null){
            model.addAttribute("usuarios", userList);
        }
        return "listarUsuarios";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String criarUsuario(Usuario usuario){
        usuarioService.create(usuario);
        return "redirect:/Users";
    }
}
