package com.tcc.redonate.endpoint.controller;

import com.tcc.redonate.endpoint.service.DoadorService;
import com.tcc.redonate.model.Doador;
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

    @RequestMapping(value = "/cadastrarDoador", method = RequestMethod.GET)
    public String cadastrarDoadorForm(){ return "cadastrarDoador"; }

    @RequestMapping(value = "/cadastrarDoador", method = RequestMethod.POST)
    public String cadastrarDoador(Doador doador, HttpServletRequest request){
        Long newUserId = Long.valueOf(""+request.getSession().getAttribute("lastCreatedUserId"));
        doador.setIdUsuario(newUserId);
        doadorService.create(doador);
        return "redirect:/";
    }

}
