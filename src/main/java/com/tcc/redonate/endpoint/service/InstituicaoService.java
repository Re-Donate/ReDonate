package com.tcc.redonate.endpoint.service;

import com.tcc.redonate.model.Instituicao;
import com.tcc.redonate.repository.InstituicaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InstituicaoService {
    private final InstituicaoRepository instituicaoRepository;

    public void update(Instituicao instituicao){
        log.info("Atualizando dados da instituicao");
        instituicaoRepository.save(instituicao);
    }

    public List<Instituicao> list(){
        log.info("Listando todas as Instituicoes");
        return instituicaoRepository.findAll();
    }

    public Instituicao detail(Long id){
        log.info("Buscando dados de uma instituicao");
        return instituicaoRepository.getById(id);
    }

    public Instituicao findByUserId(Long idUsuario){
        log.info("Buscando dados de Instituicao a partir do usuario logado");
        return instituicaoRepository.findByIdUsuario(idUsuario);
    }

    public void create(Instituicao instituicao){
        log.info("Criando nova instituição");
        instituicaoRepository.save(instituicao);
    }
}
