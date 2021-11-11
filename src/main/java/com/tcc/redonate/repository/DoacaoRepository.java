package com.tcc.redonate.repository;

import com.tcc.redonate.entity.Doacao;
import com.tcc.redonate.entity.Doador;
import com.tcc.redonate.entity.Instituicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface DoacaoRepository extends JpaRepository<Doacao, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Doacao d SET d.ativo = 0 WHERE d.id = :id")
    void deactivateChatById(Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Doacao d SET d.visivel = 0 WHERE d.id = :id")
    void deleteChatById(Long id);

    @Query("SELECT d FROM Doacao d WHERE d.doador = :id AND d.ativo = true")
    List<Doacao> getDoacoesAtivas(Doador id);

    @Query("SELECT d FROM Doacao d WHERE d.instituicao = :id AND d.visivel = true")
    List<Doacao> getDoacoesVisiveis(Instituicao id);
}
