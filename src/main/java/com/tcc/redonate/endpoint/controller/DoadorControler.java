package com.tcc.redonate.endpoint.controller;

import com.tcc.redonate.endpoint.service.DoadorService;
import com.tcc.redonate.endpoint.service.UsuarioService;
import com.tcc.redonate.model.Doador;
import com.tcc.redonate.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/doadores")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DoadorControler {
    private final DoadorService doadorService;
    private final UsuarioService usuarioService;

    @RequestMapping(value = "/cadastrarDoador", method = RequestMethod.GET)
    public String cadastrarDoadorForm(){ return "cadastrarDoador"; }

    @RequestMapping(value = "/cadastrarDoador", method = RequestMethod.POST)
    public String cadastrarDoador(Usuario usuario, Doador doador, HttpServletRequest request){
        Usuario User = (Usuario) request.getSession().getAttribute("lastCreatedUser");
        usuario.setEmailUsuario(User.getEmailUsuario());
        usuario.setSenhaUsuario(User.getSenhaUsuario());

        usuario.setDoador(doador);
        doador.setUsuarioDoador(usuario);

        usuarioService.create(usuario);
        return "redirect:/";
    }

}
