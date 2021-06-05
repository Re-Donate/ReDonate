package com.tcc.redonate.endpoint.service;

import com.tcc.redonate.model.Doador;
import com.tcc.redonate.repository.DoadorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DoadorService {
    private final DoadorRepository doadorRepository;

    public Iterable<Doador> list(Pageable pageable){
        log.info("Listando todos os doadores");
        return doadorRepository.findAll(pageable);
    }
}
