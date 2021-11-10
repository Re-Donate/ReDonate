package com.tcc.redonate.repository;

import com.tcc.redonate.entity.Doador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoadorRepository extends JpaRepository<Doador, Long> {
}
