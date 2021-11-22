package com.tcc.redonate.endpoint.service;

import com.tcc.redonate.entity.Doador;
import com.tcc.redonate.entity.Usuario;
import com.tcc.redonate.repository.DoadorRepository;
import com.tcc.redonate.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DoadorService {
    private final DoadorRepository doadorRepository;
    private final UsuarioRepository usuarioRepository;

    public Doador findOne(Long id){
        log.info("Buscando dados de uma instituicao");
        return doadorRepository.getById(id);
    }

    /*
     * Testa o tipo de um usuário (Doador ou instituição)
     * Params: Long contendo o ID de um usuário
     * Returns: True caso o usuário seja do tipo Doador, False caso seja do tipo Instituição
     * */
    public boolean isDoador(Long idUsuario){
        log.info("Checando se o usuário que logou é um doador");
        Usuario usuarioLogado = usuarioRepository.getById(idUsuario);

        return usuarioLogado.getDoador() != null;
    }
}
