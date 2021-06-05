package com.tcc.redonate.endpoint.service;

import com.tcc.redonate.model.Instituicao;
import com.tcc.redonate.repository.InstituicaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InstituicaoService {
    private final InstituicaoRepository instituicaoRepository;

    public Iterable<Instituicao> list(Pageable pageable){
        log.info("Listando todas as Instituicoes");
        return instituicaoRepository.findAll(pageable);
    }
}
