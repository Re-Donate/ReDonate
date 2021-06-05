package com.tcc.redonate.endpoint.controller;

import com.tcc.redonate.endpoint.service.InstituicaoService;
import com.tcc.redonate.model.Instituicao;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/instituicoes")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InstituicaoController {
    private final InstituicaoService instituicaoService;

    @RequestMapping(method = RequestMethod.GET)
    public String listarInstituicoes(Model model){
        List<Instituicao> instList = instituicaoService.list();

        if(instList != null){
            model.addAttribute("instituicoes", instList);
        }
        return "listarInst";
    }
}
