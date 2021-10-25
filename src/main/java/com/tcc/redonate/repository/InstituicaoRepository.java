package com.tcc.redonate.repository;

import com.tcc.redonate.model.Instituicao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstituicaoRepository extends JpaRepository<Instituicao, Long> {

    Instituicao findByCnpjInstituicao(String cnpjInstituicao);
}
