package com.tcc.redonate.endpoint.service;

import com.tcc.redonate.entity.Mensagem;
import com.tcc.redonate.repository.MensagemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MensagemService {
    private final MensagemRepository mensagemRepository;

    public void saveMensagem(Mensagem mensagem){
        mensagemRepository.save(mensagem);
    }

    public List<Mensagem> carregarHistorico(Long id) {
        List<Mensagem> mensagens = mensagemRepository.findByDoacao(id);

        return mensagens;
    }
}
