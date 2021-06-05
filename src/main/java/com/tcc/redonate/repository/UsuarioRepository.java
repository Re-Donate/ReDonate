package com.tcc.redonate.repository;

import com.tcc.redonate.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    public Usuario findByEmailUsuario(String emailUsuario);

}
