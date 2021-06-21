package com.tcc.redonate.endpoint.service;

import com.tcc.redonate.model.Doacao;
import com.tcc.redonate.repository.DoacaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DoacaoService {
    private final DoacaoRepository doacaoRepository;

    public void create(Doacao doacao){
        log.info("Criando nova docao");
        doacaoRepository.save(doacao);
    }

    public List<Doacao> findByIdInstituicao(Long idInstituicao){
        log.info("Buscando doações enviadas à instituição");
        return doacaoRepository.findByIdInstituicao(idInstituicao);
    }
}
