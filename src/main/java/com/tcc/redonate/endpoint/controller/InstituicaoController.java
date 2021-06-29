package com.tcc.redonate.endpoint.controller;

import com.tcc.redonate.endpoint.service.DoadorService;
import com.tcc.redonate.endpoint.service.InstituicaoService;
import com.tcc.redonate.endpoint.service.UsuarioService;
import com.tcc.redonate.model.Doacao;
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

    @RequestMapping(value = "/instituicoes/dados", method = RequestMethod.POST)
    public String atualizarDadosInstituicao(Usuario usuario, Instituicao instituicao, HttpServletRequest request){
        Usuario usuarioLogado = usuarioService.getUsuarioLogado(request);
        List<Doacao> doacoes = instituicaoService.findOne(instituicao.getId()).getDoacoes();

        instituicao.setDoacoes(doacoes);

        usuarioService.atualizarUsuario(usuarioLogado, usuario);
        usuarioService.saveInstituicao(usuarioLogado, instituicao);

        return "redirect:/instituicoes/dados";
    }

    @RequestMapping(value = "/instituicao/{inst}", method = RequestMethod.GET)
    public String detalharInstituicao(@PathVariable("inst") int inst, Model model, HttpServletRequest request){
        Instituicao instDetail = instituicaoService.findOne(Long.valueOf(inst));
        Usuario usuarioLogado = usuarioService.getUsuarioLogado(request);
        if(instDetail != null)
            model.addAttribute("detalhes", instDetail);
        if(instDetail != null)
            model.addAttribute("dadosDoador", usuarioLogado);
        return "detalharInst";
    }

    @RequestMapping(value = "/instituicoes/cadastrarInstituicao", method = RequestMethod.GET)
    public String cadastrarInstituicaoForm(){ return "cadastrarInstituicao"; }

    @RequestMapping(value = "/instituicoes/cadastrarInstituicao", method = RequestMethod.POST)
    public String cadastrarInstituicao(Usuario usuario, Instituicao instituicao, HttpServletRequest request){
        Usuario User = (Usuario) request.getSession().getAttribute("lastCreatedUser");
        usuario.setEmailUsuario(User.getEmailUsuario());
        usuario.setSenhaUsuario(User.getSenhaUsuario());

        usuarioService.saveInstituicao(usuario, instituicao);
        return "redirect:/";
    }
}
