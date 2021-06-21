package com.tcc.redonate.repository;

import com.tcc.redonate.model.Doacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoacaoRepository extends JpaRepository<Doacao, Long> {
    List<Doacao> findByIdInstituicao(Long idInstituicao);
}
