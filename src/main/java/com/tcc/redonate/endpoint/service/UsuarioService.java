package com.tcc.redonate.endpoint.service;

import com.tcc.redonate.model.Doador;
import com.tcc.redonate.model.Instituicao;
import com.tcc.redonate.model.Usuario;
import com.tcc.redonate.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public boolean existe(Long id){
        Usuario usuario = usuarioRepository.getById(id);

        return usuario != null;
    }

    public void atualizarUsuario(Usuario dadosAntigos, Usuario novosDados){
        novosDados.setId(dadosAntigos.getId());
        novosDados.setEmailUsuario(dadosAntigos.getEmailUsuario());
        novosDados.setSenhaUsuario(dadosAntigos.getSenhaUsuario());
        novosDados.setInstituicao(dadosAntigos.getInstituicao());
        novosDados.setDoador(dadosAntigos.getDoador());
    }

    public Usuario getUsuarioLogado(HttpServletRequest request){
        log.info("Buscando dados do Usuario Logado");
        if(request.getSession().getAttribute("idLogin") != null) {
            Long idUsuario = Long.parseLong("" + request.getSession().getAttribute("idLogin"));
            return usuarioRepository.getById(idUsuario);
        }else
            return null;
    }

    public Usuario login(Usuario usuario){
        log.info("Buscando dados do Usuario");
        Usuario userTestLogin = usuarioRepository.findByEmailUsuario(usuario.getEmailUsuario());
        if(userTestLogin != null && userTestLogin.getSenhaUsuario().equals(usuario.getSenhaUsuario()))
            return userTestLogin;
        return null;
    }

    public boolean isEmailUnique(Usuario usuario){
        log.info("Verificando se o email ja existe no banco");
        Usuario testEmail = usuarioRepository.findByEmailUsuario(usuario.getEmailUsuario());

        if(testEmail != null)
            return false;

        return true;
    }

    public boolean saveUsuarioDoador(Usuario usuario, Doador doador){
        log.info("Salvando dados do Doador");
        usuario.setDoador(doador);
        doador.setUsuarioDoador(usuario);

        Usuario newUser = usuarioRepository.save(usuario);
        return newUser != null;
    }

    public boolean saveUsuarioInstituicao(Usuario usuario, Instituicao instituicao){
        log.info("Salvando dados da Instituição");
        usuario.setInstituicao(instituicao);
        instituicao.setUsuarioInstituicao(usuario);

        Usuario newUser = usuarioRepository.save(usuario);
        return newUser != null;
    }

    public List<Usuario> filtrarInstituicoes(String nome, String causa, String necessidade){
        log.info("Procurando instituicoes com base na busca do usuario");
        return usuarioRepository.filtrarInstituicoes(nome, causa, necessidade);
    }

    public Usuario getById(Long id){
        log.info("Buscando dados do usuario a partir do ID");

        return usuarioRepository.getById(id);
    }
}
