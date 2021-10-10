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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InstituicaoController {
    private final InstituicaoService instituicaoService;
    private final DoadorService doadorService;
    private final UsuarioService usuarioService;

    @RequestMapping(value = "/instituicoes", method = RequestMethod.GET)
    public String listarInstituicoes(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes){
        Doador doadorLogado = doadorService.findByUsuarioLogado(request);

        if(doadorLogado != null) {
            model.addAttribute("dadosDoador", doadorLogado);

            Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
            if (inputFlashMap != null)
                model.addAttribute("success", inputFlashMap.get("success"));

            List<Instituicao> instList = instituicaoService.list();
            if (instList != null)
                model.addAttribute("instituicoes", instList);

            return "listarInst";
        }else {
            redirectAttributes.addFlashAttribute("accessDenial", true);
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/instituicoes/filtro", method = RequestMethod.GET)
    public ResponseEntity<List<Usuario>> filtrarInstituicoes(HttpServletRequest request){
        String nomeFiltro = request.getParameter("nome");
        String causaFiltro = request.getParameter("causa");
        String necessidadeFiltro = request.getParameter("necessidade");

        List<Usuario> instituicoesFiltradas = usuarioService.filtrarInstituicoes(nomeFiltro, causaFiltro, necessidadeFiltro);

        return ResponseEntity.ok(instituicoesFiltradas);
    }

    @RequestMapping(value = "/instituicoes/dados", method = RequestMethod.GET)
    public String dadosInstituicao(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes){
        Instituicao dadosInstituicao = instituicaoService.findByUsuarioLogado(request);

        if(dadosInstituicao != null) {
            model.addAttribute("dadosInst", dadosInstituicao);

            Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
            if (inputFlashMap != null)
                model.addAttribute("success", inputFlashMap.get("success"));

            return "dadosInstituicao";
        }else {
            redirectAttributes.addFlashAttribute("accessDenial", true);
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/instituicoes/dados", method = RequestMethod.POST)
    public RedirectView atualizarDadosInstituicao(Usuario usuario, Instituicao instituicao, HttpServletRequest request, RedirectAttributes redirectAttributes){
        RedirectView redirectView = new RedirectView("/instituicoes/dados", false);

        Usuario usuarioLogado = usuarioService.getUsuarioLogado(request);
        List<Doacao> doacoes = instituicaoService.findOne(instituicao.getId()).getDoacoes();

        instituicao.setDoacoes(doacoes);

        usuarioService.atualizarUsuario(usuarioLogado, usuario);
        boolean success = usuarioService.saveUsuarioInstituicao(usuario, instituicao);
        redirectAttributes.addFlashAttribute("success", success);

        return redirectView;
    }

    @RequestMapping(value = "/instituicao/{inst}", method = RequestMethod.GET)
    public String detalharInstituicao(@PathVariable("inst") int inst, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes){
        Usuario usuarioLogado = usuarioService.getUsuarioLogado(request);

        if(usuarioLogado != null) {
            model.addAttribute("dadosDoador", usuarioLogado);

            Instituicao instDetail = instituicaoService.findOne((long) inst);
            if (instDetail != null)
                model.addAttribute("detalhes", instDetail);

            return "detalharInst";
        }else {
            redirectAttributes.addFlashAttribute("accessDenial", true);
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/instituicoes/cadastrarInstituicao", method = RequestMethod.GET)
    public String cadastrarInstituicaoForm(){ return "cadastrarInstituicao"; }

    @RequestMapping(value = "/instituicoes/cadastrarInstituicao", method = RequestMethod.POST)
    public RedirectView cadastrarInstituicao(Usuario usuario, Instituicao instituicao, HttpServletRequest request, RedirectAttributes redirectAttributes){
        RedirectView redirectView = new RedirectView("/", false);

        Usuario User = (Usuario) request.getSession().getAttribute("lastCreatedUser");
        usuario.setEmailUsuario(User.getEmailUsuario());
        usuario.setSenhaUsuario(User.getSenhaUsuario());

        boolean success = usuarioService.saveUsuarioInstituicao(usuario, instituicao);
        redirectAttributes.addFlashAttribute("success", success);

        return redirectView;
    }
}
