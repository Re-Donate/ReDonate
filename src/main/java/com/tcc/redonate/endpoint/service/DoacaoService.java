package com.tcc.redonate.endpoint.service;

import com.tcc.redonate.entity.Doacao;
import com.tcc.redonate.entity.Doador;
import com.tcc.redonate.entity.Instituicao;
import com.tcc.redonate.repository.DoacaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DoacaoService {
    private final DoacaoRepository doacaoRepository;

    /*
     * Salva uma nova doação no banco de dados
     * Params: Instância da nova doação, do doador que a fez e da instituição que a recebeu
     * Returns: True caso a doação seja persistida no banco com sucesso
     * */
    public boolean create(Doacao doacao, Doador doador, Instituicao instituicao){
        log.info("Criando nova docao");
        instituicao.getDoacoes().add(doacao);
        doador.getDoacoes().add(doacao);

        doacao.setDoador(doador);
        doacao.setInstituicao(instituicao);

        return doacaoRepository.save(doacao) != null;
    }

    /*
     * Desativar um chat para que o doador não possam mais vê-lo
     * Params: Long contendo o ID de uma doação
     * Returns: Nada
     * */
    public void desativarChat(Long id) {
        log.info("Desativando doação para que o chat não seja mais visível para o doador");
        doacaoRepository.deactivateChatById(id);
    }

    /*
     * Desativar a visibilidade de um chat para que a instituição não possa mais vê-lo
     * Params: Long contendo o ID de uma doação
     * Returns: Nada
     * */
    public void apagarChat(Long id) {
        log.info("Desabilitando a visibilidade do chat para a instituição");
        doacaoRepository.deleteChatById(id);
    }

    /*
     * Buscar doações ativas para carregar lista de contatos do doador
     * Params: Instância do doador conectado
     * Returns: Lista de doações, com chats ativos, feitas por este doador
     * */
    public List<Doacao> buscarDoacoesAtivas(Doador doador) {
        log.info("Buscando chats ativos do doador logado");
        return doacaoRepository.getDoacoesAtivas(doador);
    }

    /*
     * Buscar doações visíveis para carregar lista de contatos da instituição
     * Params: Instância da instituição conectada
     * Returns: Lista de doações, com chats visíveis, recebidas por esta instituição
     * */
    public List<Doacao> buscarDoacoesVisiveis(Instituicao instituicao) {
        log.info("Buscando chats visíveis para a instituição logada");
        return doacaoRepository.getDoacoesVisiveis(instituicao);
    }

    /*
     * Carrega dados necessários para o histórico de doações de um usuário
     * Params: Lista de doações feitas ou recebidas,
     *         valor para a soma parcial de cada tipo de doação,
     *         Lista de causas das doações feitas ou recebidas,
     *         Lista dos valores totais de cada causa das doações,
     *         Booleano indicando se o usuário logado é uma instituição,
     *         Lista de objetos que representa a outra ponta das doações (Doadores ou Instituições)
     * Returns: Valor da soma total de todas as doações
     * */
    public float carregarDadosDoacao(List<Doacao> doacoes, float soma, List<String> causasDoacao, List<Float> valoresPorCausa, boolean isInstituicao, List<Object> outraPonta) {
        log.info("Carregando dados das doações feitas ou recebidas pelo usuário");

        float valorTotal = 0;
        for(int i = 0; i < doacoes.size(); i++){

            Doacao doacaoAtual = doacoes.get(i);
            if(i != 0) {
                Doacao doacaoAnterior = doacoes.get(i - 1);
                if(doacaoAtual.getCausaDoacao().equals(doacaoAnterior.getCausaDoacao())){
                    soma += doacaoAtual.getValorDoacao();
                }else{
                    causasDoacao.add(doacaoAnterior.getCausaDoacao());
                    valoresPorCausa.add(soma);
                    valorTotal += soma;
                    soma = doacaoAtual.getValorDoacao();
                }
            }

            if(i == (doacoes.size()-1)){
                causasDoacao.add(doacaoAtual.getCausaDoacao());
                valoresPorCausa.add(soma);
                valorTotal += soma;
            }

            if(isInstituicao)
                outraPonta.add(doacaoAtual.getDoador());
            else
                outraPonta.add(doacaoAtual.getInstituicao());
        }

        return valorTotal;
    }
}
