package com.tcc.redonate.endpoint.service;

import com.tcc.redonate.entity.Instituicao;
import com.tcc.redonate.entity.Usuario;
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

    public boolean isCNPJUnique(Instituicao instituicao){
        log.info("Verificando se o CNPJ ja existe no banco");
        Instituicao testCNPJ = instituicaoRepository.findByCnpjInstituicao(instituicao.getCnpjInstituicao());

        if(testCNPJ != null)
            return false;

        return true;
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

    public boolean isCNPJValid(String cnpj) {
        String[] digitos = cnpj
                .replace(".", "")
                .replace("/", "")
                .split("-");

        for(int j = 0; j < 2; j++) {
            int index = 0;
            int soma = 0;

            for (int i = digitos[0].length() + 1; i > 1; i--) {
                int mult;
                if (i > 9)
                    mult = i - 8;
                else
                    mult = i;

                soma += Character.getNumericValue(digitos[0].charAt(index)) * mult;
                index++;
            }

            int result = soma % 11;

            if (result < 2) {
                result = 0;
            } else {
                result = 11 - result;
            }

            if(result == Character.getNumericValue(digitos[1].charAt(j))){
                digitos[0] += digitos[1].charAt(j);
            }else{
                return false;
            }
        }
        return true;
    };
}
