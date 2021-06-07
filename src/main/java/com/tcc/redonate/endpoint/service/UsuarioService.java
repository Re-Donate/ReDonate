package com.tcc.redonate.endpoint.service;

import com.tcc.redonate.model.Usuario;
import com.tcc.redonate.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public Usuario login(Usuario usuario){
        log.info("Buscando dados do Usuario");
        Usuario userTestLogin = usuarioRepository.findByEmailUsuario(usuario.getEmailUsuario());
        if(userTestLogin != null && userTestLogin.getSenhaUsuario().equals(usuario.getSenhaUsuario()))
            return userTestLogin;
        return null;
    }

    public Usuario create(Usuario usuario){
        log.info("Criando novo usuario");
        Usuario newUser = usuarioRepository.save(usuario);
        return newUser;
    }
}
