package com.tcc.redonate.repository;

import com.tcc.redonate.model.Doador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoadorRepository extends JpaRepository<Doador, Long> {
    Doador findByIdUsuario(Long idUsuario);
}
