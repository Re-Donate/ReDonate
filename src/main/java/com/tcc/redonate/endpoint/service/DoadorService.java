package com.tcc.redonate.endpoint.service;

import com.tcc.redonate.model.Doador;
import com.tcc.redonate.repository.DoadorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DoadorService {
    private final DoadorRepository doadorRepository;

    public Long findByUserId(Long idUsuario){
        log.info("Buscando dados de Doador a partir de usuário logado");
        return doadorRepository.findByIdUsuario(idUsuario).getId();
    }
}
