package com.tcc.redonate.repository;

import com.tcc.redonate.entity.Mensagem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MensagemRepository extends JpaRepository<Mensagem, Long> {

    List<Mensagem> findByDoacao(Long doacao);
}
