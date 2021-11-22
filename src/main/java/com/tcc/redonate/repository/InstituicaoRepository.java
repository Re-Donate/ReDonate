package com.tcc.redonate.repository;

import com.tcc.redonate.entity.Instituicao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstituicaoRepository extends JpaRepository<Instituicao, Long> {

    /*
     * Buscar instituição por CNPJ
     * Params: String contendo um CNPJ
     * Returns: Instância da instituição encontrada
     * */
    Instituicao findByCnpjInstituicao(String cnpjInstituicao);
}
