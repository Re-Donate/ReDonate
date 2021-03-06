package com.tcc.redonate.endpoint.service;

import com.tcc.redonate.entity.Doador;
import com.tcc.redonate.entity.Instituicao;
import com.tcc.redonate.entity.Usuario;
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
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    /*
     * Testa a existência de um usuário com base em seu ID
     * Params: Long contendo o ID de um usuário
     * Returns: True para usuário existente e False para usuário inexistente
     * */
    public boolean existe(Long id){
        Usuario usuario = usuarioRepository.getById(id);

        return usuario != null;
    }

    /*
     * Atualiza os dados de uma instância de Usuário incompleta
     * Params: Duas instâncias de usuários, a primeira contendo os dados faltantes na segunda
     * Returns: Nada
     * */
    public void atualizarUsuario(Usuario dadosAntigos, Usuario novosDados){
        novosDados.setId(dadosAntigos.getId());
        novosDados.setEmailUsuario(dadosAntigos.getEmailUsuario());
        novosDados.setSenhaUsuario(dadosAntigos.getSenhaUsuario());
        novosDados.setCpfUsuario(dadosAntigos.getCpfUsuario());
        novosDados.setInstituicao(dadosAntigos.getInstituicao());
        novosDados.setDoador(dadosAntigos.getDoador());
    }

    /*
     * Retorna a instância do usuário logado no momento com base na variável de sessão
     * Params: HttpRequest do sistema
     * Returns: Instância do usuário logado, caso a variável de sessão exista, NULL, caso ela não exista
     * */
    public Usuario getUsuarioLogado(HttpServletRequest request){
        log.info("Buscando dados do Usuario Logado");
        if(request.getSession().getAttribute("idLogin") != null) {
            Long idUsuario = Long.parseLong("" + request.getSession().getAttribute("idLogin"));
            return usuarioRepository.getById(idUsuario);
        }else
            return null;
    }

    /*
     * Compara o email e senha do usuário enviado com o banco de dados
     * Params: Instância do usuário tentando efetuar login
     * Returns: Instância do usuário que efetuou o login ou NULL caso a comparação falhe
     * */
    public Usuario login(Usuario usuario){
        log.info("Buscando dados do Usuario");
        Usuario userTestLogin = usuarioRepository.findByEmailUsuario(usuario.getEmailUsuario());
        if(userTestLogin != null && userTestLogin.getSenhaUsuario().equals(usuario.getSenhaUsuario()))
            return userTestLogin;
        return null;
    }

    /*
     * Compara email do usuário enviado com os emails já existentes
     * Params: Instância de um usuário
     * Returns: True caso não seja encontrado nenhum email igual ao enviado, False caso seja encontrado um usuário com este email
     * */
    public boolean isEmailUnique(Usuario usuario){
        log.info("Verificando se o email ja existe no banco");
        Usuario testEmail = usuarioRepository.findByEmailUsuario(usuario.getEmailUsuario());

        if(testEmail != null)
            return false;

        return true;
    }

    /*
     * Compara CPF do usuário enviado com os CPFs já existentes
     * Params: Instância de um usuário
     * Returns: True caso não seja encontrado nenhum CPF igual ao enviado, False caso seja encontrado um usuário com este CPF
     * */
    public boolean isCPFUnique(Usuario usuario){
        log.info("Verificando se o CPF ja existe no banco");
        Usuario testCPF = usuarioRepository.findByCpfUsuario(usuario.getCpfUsuario());

        if(testCPF != null)
            return false;

        return true;
    }

    /*
     * Salva a instância de um doador no banco de dados
     * Params: Instância de um Usuário e instância de um Doador
     * Returns: True, caso o novo doador seja persistido no banco
     * */
    public boolean saveUsuarioDoador(Usuario usuario, Doador doador){
        log.info("Salvando dados do Doador");
        usuario.setDoador(doador);
        doador.setUsuarioDoador(usuario);

        Usuario newUser = usuarioRepository.save(usuario);
        return newUser != null;
    }

    /*
     * Salva a instância de uma instituição no banco de dados
     * Params: Instância de um Usuário e instância de uma Instituição
     * Returns: True, caso a nova instituição seja persistida no banco
     * */
    public boolean saveUsuarioInstituicao(Usuario usuario, Instituicao instituicao){
        log.info("Salvando dados da Instituição");
        usuario.setInstituicao(instituicao);
        instituicao.setUsuarioInstituicao(usuario);

        Usuario newUser = usuarioRepository.save(usuario);
        return newUser != null;
    }

    /*
     * Filtra instituições com base nas necessidades presentes
     * Params: 6 Strings, cada um com o valor de alguma necessidade (Opcionais)
     * Returns: Lista de usuários encontrados
     * */
    public List<Usuario> filtrarInstituicoes(String necessidade1, String necessidade2, String necessidade3, String necessidade4, String necessidade5, String necessidade6){
        log.info("Procurando instituicoes com base na busca do usuario");
        return usuarioRepository.filtrarInstituicoes(necessidade1, necessidade2, necessidade3, necessidade4, necessidade5, necessidade6);
    }

    /*
     * Valida o CPF inserido pelo usuário
     * Params: String contendo um CPF
     * Returns: True, caso o CPF seja válido, False, caso não seja
     * */
    public boolean isCPFValid(String cpf){
        String[] digitos = cpf
                .replace(".", "")
                .split("-");

        for(int j = 0; j < 2; j++) {

            int index = 0;
            int soma = 0;

            for (int i = digitos[0].length() + 1; i > 1; i--) {
                soma += Character.getNumericValue(digitos[0].charAt(index)) * i;
                index++;
            }
            int result = (soma * 10) % 11;

            if(result == 10)
                result = 0;

            if(result == Character.getNumericValue(digitos[1].charAt(j))){
                digitos[0] += digitos[1].charAt(j);
            }else{
                return false;
            }

        }

        return true;
    }

    public Usuario getById(Long id){
        log.info("Buscando dados do usuario a partir do ID");

        return usuarioRepository.getById(id);
    }
}
