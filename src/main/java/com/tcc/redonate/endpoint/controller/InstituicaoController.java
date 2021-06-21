package com.tcc.redonate.endpoint.controller;

import com.tcc.redonate.endpoint.service.DoadorService;
import com.tcc.redonate.endpoint.service.InstituicaoService;
import com.tcc.redonate.endpoint.service.UsuarioService;
import com.tcc.redonate.model.Doador;
import com.tcc.redonate.model.Instituicao;
import com.tcc.redonate.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.swing.*;
import java.util.List;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InstituicaoController {
    private final InstituicaoService instituicaoService;
    private final DoadorService doadorService;
    private final UsuarioService usuarioService;

    @RequestMapping(value = "/instituicoes", method = RequestMethod.GET)
    public String listarInstituicoes(Model model, HttpServletRequest request){
        List<Instituicao> instList = instituicaoService.list();

        Doador doadorLogado = doadorService.findByUsuarioLogado(request);

        if(instList != null){
            model.addAttribute("instituicoes", instList);
        }
        if(doadorLogado != null){
            model.addAttribute("dadosDoador", doadorLogado);
        }
        return "listarInst";
    }


    @RequestMapping(value = "/instituicoes/dados", method = RequestMethod.GET)
    public String dadosInstituicao(HttpServletRequest request, Model model){

        Instituicao dadosInstituicao = instituicaoService.findByUsuarioLogado(request);

        if(dadosInstituicao != null)
            model.addAttribute("dadosInst", dadosInstituicao);


        return "dadosInstituicao";
    }

    @RequestMapping(value = "/instituicao/{inst}", method = RequestMethod.GET)
    public String detalharInstituicao(@PathVariable("inst") int inst, Model model){
        Instituicao instDetail = instituicaoService.detail(Long.valueOf(inst));
        if(instDetail != null){
            model.addAttribute("detalhes", instDetail);
        }
        return "detalharInst";
    }

    @RequestMapping(value = "/instituicoes/dados", method = RequestMethod.POST)
    public String atualizarDadosInstituicao(Instituicao instituicao, HttpServletRequest request){
        Long idUsuario = Long.valueOf(""+request.getSession().getAttribute("idLogin"));
        instituicao.setIdUsuario(idUsuario);
        instituicaoService.update(instituicao);

        System.setProperty("java.awt.headless", "false");
        //messageType: 0 -> Error
        //messageType: 1 -> Information
        //messageType: 2 -> Warning
        //messageType: 3 -> Question
        //messageType: -1 -> Plain
        JOptionPane.showMessageDialog(null, "Dados alterados com sucesso", "Alteração de dados", 1);

        return "redirect:/instituicoes/dados";
    }

    @RequestMapping(value = "/instituicoes/cadastrarInstituicao", method = RequestMethod.GET)
    public String cadastrarInstituicaoForm(){ return "cadastrarInstituicao"; }

    @RequestMapping(value = "/instituicoes/cadastrarInstituicao", method = RequestMethod.POST)
    public String cadastrarInstituicao(Instituicao instituicao, HttpServletRequest request){
        Usuario User = (Usuario) request.getSession().getAttribute("lastCreatedUser");
        Usuario newUser = usuarioService.create(User);

        instituicao.setIdUsuario(newUser.getId());
        instituicaoService.create(instituicao);
        return "redirect:/";
    }
}
