package com.tcc.redonate.repository;

import com.tcc.redonate.entity.Doacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoacaoRepository extends JpaRepository<Doacao, Long> {
}
