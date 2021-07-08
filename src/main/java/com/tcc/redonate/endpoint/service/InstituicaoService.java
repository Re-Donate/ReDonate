package com.tcc.redonate.endpoint.service;

import com.tcc.redonate.model.Instituicao;
import com.tcc.redonate.model.Usuario;
import com.tcc.redonate.repository.InstituicaoRepository;
import com.tcc.redonate.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InstituicaoService {
    private final InstituicaoRepository instituicaoRepository;
    private final UsuarioRepository usuarioRepository;

    public List<Instituicao> list(){
        log.info("Listando todas as Instituicoes");
        return instituicaoRepository.findAll();
    }

    public Instituicao findOne(Long id){
        log.info("Buscando dados de uma instituicao");
        return instituicaoRepository.getById(id);
    }

    public Instituicao findByUsuarioLogado(HttpServletRequest request){
        log.info("Buscando dados de Instituicao a partir do usuario logado");
        if(request.getSession().getAttribute("idLogin") != null) {
            Long idUsuario = Long.valueOf("" + request.getSession().getAttribute("idLogin"));
            Usuario usuarioLogado = usuarioRepository.getById(idUsuario);
            return usuarioLogado.getInstituicao();
        }else
            return  null;
    }
}
