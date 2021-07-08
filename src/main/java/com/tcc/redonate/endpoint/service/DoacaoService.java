package com.tcc.redonate.endpoint.service;

import com.tcc.redonate.model.Doacao;
import com.tcc.redonate.model.Doador;
import com.tcc.redonate.model.Instituicao;
import com.tcc.redonate.repository.DoacaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DoacaoService {
    private final DoacaoRepository doacaoRepository;

    public boolean create(Doacao doacao, Doador doador, Instituicao instituicao){
        log.info("Criando nova docao");
        instituicao.getDoacoes().add(doacao);
        doador.getDoacoes().add(doacao);

        doacao.setDoador(doador);
        doacao.setInstituicao(instituicao);

        return doacaoRepository.save(doacao) != null;
    }
}
