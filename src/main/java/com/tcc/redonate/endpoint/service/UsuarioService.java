package com.tcc.redonate.endpoint.service;

import com.tcc.redonate.model.Usuario;
import com.tcc.redonate.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public List<Usuario> list(){
        log.info("Listando todos os usuarios");
        return usuarioRepository.findAll();
    }

    public void create(Usuario usuario){
        log.info("Criando novo usuario");
        usuarioRepository.save(usuario);
    }
}
