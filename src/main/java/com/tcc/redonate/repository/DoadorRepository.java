package com.tcc.redonate.repository;

import com.tcc.redonate.model.Doador;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DoadorRepository extends PagingAndSortingRepository<Doador, Long> {
}
