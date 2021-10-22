package com.tcc.redonate.endpoint.service;

import com.tcc.redonate.model.Mensagem;
import com.tcc.redonate.repository.MensagemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MensagemService {
    private final MensagemRepository mensagemRepository;

    public void saveMensagem(Mensagem mensagem){
        mensagemRepository.save(mensagem);
    }
}
