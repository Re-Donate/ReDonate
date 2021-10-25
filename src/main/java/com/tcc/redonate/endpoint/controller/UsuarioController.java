package com.tcc.redonate.endpoint.controller;

import com.tcc.redonate.endpoint.service.DoadorService;
import com.tcc.redonate.endpoint.service.UsuarioService;
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
import javax.websocket.server.PathParam;
import java.util.Map;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final DoadorService doadorService;

    @GetMapping
    public String index(HttpServletRequest request, Model model){
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if(inputFlashMap != null) {
            model.addAttribute("success", inputFlashMap.get("success"));
            model.addAttribute("falhaLogin", inputFlashMap.get("falhaLogin"));
            model.addAttribute("accessDenial", inputFlashMap.get("accessDenial"));
            model.addAttribute("permissionDenial", inputFlashMap.get("permissionDenial"));
            model.addAttribute("notUnique", inputFlashMap.get("notUnique"));
        }

        request.getSession().invalidate();
        return "index";
    }

    @GetMapping(value = "/cadastrar")
    public String cadastrarForm(){ return "cadastrarUser"; }

    @PostMapping(value = "/cadastrar")
    public RedirectView cadastrarUser(Usuario usuario, HttpServletRequest request, RedirectAttributes redirectAttributes){
        RedirectView redirectView = new RedirectView("", false);

        if(usuarioService.isEmailUnique(usuario)) {

            request.getSession().setAttribute("lastCreatedUser", usuario);

            Boolean isDoador = request.getParameter("tipoUsuario").equals("Doador");

            if (isDoador)
                redirectView.setUrl("/doadores/cadastrarDoador");
            else
                redirectView.setUrl("/instituicoes/cadastrarInstituicao");

        }else {

            redirectView.setUrl("/");
            redirectAttributes.addFlashAttribute("notUnique", "Email");
        }

        return  redirectView;
    }

    @PostMapping(value = "/logar")
    public RedirectView logarUsuario(Usuario usuario, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        RedirectView redirectView = new RedirectView("", false);
        Usuario testLogin = usuarioService.login(usuario);

        if (testLogin != null) {
            request.getSession().setAttribute("idLogin", testLogin.getId());

            if(doadorService.isDoador(testLogin.getId()))
                redirectView.setUrl("/instituicoes");
            else
                redirectView.setUrl("/instituicoes/dados");

        } else {
            redirectAttributes.addFlashAttribute("falhaLogin", true);
            redirectView.setUrl("/");
        }

        return redirectView;
    }

    @GetMapping(value = "/getuser/{id}")
    public ResponseEntity<Usuario> getUserById(@PathVariable Long id) {
        Usuario usuario = usuarioService.getById(id);

        return ResponseEntity.ok(usuario);
    }

    @GetMapping(value = "/validar/cpf")
    public ResponseEntity<Boolean> validarCPF(@PathParam("cpf") String cpf) {
        return ResponseEntity.ok(usuarioService.isCPFValid(cpf));
    }
}
