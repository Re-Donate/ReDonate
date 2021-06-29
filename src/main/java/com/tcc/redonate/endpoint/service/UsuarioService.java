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


@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public void atualizarUsuario(Usuario dadosAntigos, Usuario novosDados){
        dadosAntigos.setNomeUsuario(novosDados.getNomeUsuario());
        dadosAntigos.setEnderecoUsuario(novosDados.getEnderecoUsuario());
        dadosAntigos.setCidadeUsuario(novosDados.getCidadeUsuario());
        dadosAntigos.setEstadoUsuario(novosDados.getEstadoUsuario());
        dadosAntigos.setTelefoneUsuario(novosDados.getTelefoneUsuario());
        dadosAntigos.setCelularUsuario(novosDados.getCelularUsuario());
        dadosAntigos.setCpfUsuario(novosDados.getCpfUsuario());
    }

    public Usuario getUsuarioLogado(HttpServletRequest request){
        log.info("Buscando dados do Usuario Logado");
        Long idUsuario = Long.parseLong(""+request.getSession().getAttribute("idLogin"));
        Usuario usuarioLogado = usuarioRepository.getById(idUsuario);
        return usuarioLogado;
    }

    public Usuario login(Usuario usuario){
        log.info("Buscando dados do Usuario");
        Usuario userTestLogin = usuarioRepository.findByEmailUsuario(usuario.getEmailUsuario());
        if(userTestLogin != null && userTestLogin.getSenhaUsuario().equals(usuario.getSenhaUsuario()))
            return userTestLogin;
        return null;
    }

    public boolean saveDoador(Usuario usuario, Doador doador){
        log.info("Salvando dados do Doador");
        usuario.setDoador(doador);
        doador.setUsuarioDoador(usuario);

        Usuario newUser = usuarioRepository.save(usuario);
        return newUser != null;
    }

    public boolean saveInstituicao(Usuario usuario, Instituicao instituicao){
        log.info("Salvando dados da Instituição");
        usuario.setInstituicao(instituicao);
        instituicao.setUsuarioInstituicao(usuario);

        Usuario newUser = usuarioRepository.save(usuario);
        return newUser != null;
    }
}
