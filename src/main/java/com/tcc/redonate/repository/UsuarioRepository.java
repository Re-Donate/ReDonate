package com.tcc.redonate.repository;

import com.tcc.redonate.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByCpfUsuario(String cpfUsuario);

    Usuario findByEmailUsuario(String emailUsuario);

    @Query("SELECT obj FROM Usuario obj WHERE " +
            "obj.instituicao.necessidadesInstituicao LIKE %:necessidade1% OR " +
            "obj.instituicao.necessidadesInstituicao LIKE %:necessidade2% OR " +
            "obj.instituicao.necessidadesInstituicao LIKE %:necessidade3% OR " +
            "obj.instituicao.necessidadesInstituicao LIKE %:necessidade4% OR " +
            "obj.instituicao.necessidadesInstituicao LIKE %:necessidade5% OR " +
            "obj.instituicao.necessidadesInstituicao LIKE %:necessidade6%")
    List<Usuario> filtrarInstituicoes(String necessidade1, String necessidade2, String necessidade3, String necessidade4, String necessidade5, String necessidade6);

}
