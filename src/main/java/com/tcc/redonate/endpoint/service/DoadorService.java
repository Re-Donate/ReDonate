package com.tcc.redonate.endpoint.service;

import com.tcc.redonate.model.Doador;
import com.tcc.redonate.model.Usuario;
import com.tcc.redonate.repository.DoadorRepository;
import com.tcc.redonate.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DoadorService {
    private final DoadorRepository doadorRepository;
    private final UsuarioRepository usuarioRepository;

    public Doador findById(Long id){
        log.info("Buscando doador a partir do ID");
        return doadorRepository.getById(id);
    }

    public Doador findByUsuarioLogado(HttpServletRequest request){
        log.info("Buscando dados de Doador a partir de usuário logado");
        Long idUsuario = Long.parseLong(""+request.getSession().getAttribute("idLogin"));
        Usuario usuarioLogado = usuarioRepository.getById(idUsuario);
        return usuarioLogado.getDoador();
    }

    public boolean isDoador(Long idUsuario){
        log.info("Checando se o usuário que logou é um doador");
        Usuario usuarioLogado = usuarioRepository.getById(idUsuario);

        return usuarioLogado.getDoador() != null;
    }

    public void create(Doador doador){
        log.info("Criando novo doador");
        doadorRepository.save(doador);
    }
}
