package com.tcc.redonate.repository;

import com.tcc.redonate.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByEmailUsuario(String emailUsuario);

    @Query("SELECT obj FROM Usuario obj WHERE obj.nomeUsuario LIKE %:nome% AND obj.instituicao.causaInstituicao LIKE %:causa% AND obj.instituicao.necessidadesInstituicao LIKE %:necessidade%")
    List<Usuario> filtrarInstituicoes(String nome, String causa, String necessidade);

}
