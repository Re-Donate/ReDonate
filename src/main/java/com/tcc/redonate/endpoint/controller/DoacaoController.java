package com.tcc.redonate.endpoint.controller;

import com.tcc.redonate.endpoint.service.DoacaoService;
import com.tcc.redonate.endpoint.service.DoadorService;
import com.tcc.redonate.endpoint.service.InstituicaoService;
import com.tcc.redonate.model.Doacao;
import com.tcc.redonate.model.Instituicao;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/instituicao")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DoacaoController {
    private final DoacaoService doacaoService;
    private final InstituicaoService instituicaoService;
    private final DoadorService doadorService;

    @RequestMapping(value = "/{inst}", method = RequestMethod.GET)
    public String detalharInstituicao(@PathVariable("inst") int inst, Model model){
        Instituicao instDetail = instituicaoService.detail(Long.valueOf(inst));
        if(instDetail != null){
            model.addAttribute("detalhes", instDetail);
        }
        return "detalharInst";
    }

    @RequestMapping(value = "/{inst}", method = RequestMethod.POST)
    public String criarDoacao(@PathVariable("inst") Long inst, Doacao doacao, HttpServletRequest request){
        doacao.setIdInstituicao(inst);

        Long idUsuario = Long.parseLong(""+request.getSession().getAttribute("idLogin"));
        Long idDoador = doadorService.findByUserId(idUsuario).getId();
        doacao.setIdDoador(idDoador);
        doacaoService.create(doacao);
        return "redirect:/instituicoes";
    }
}
