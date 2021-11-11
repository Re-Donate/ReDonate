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

    public boolean create(Doacao doacao, Doador doador, Instituicao instituicao){
        log.info("Criando nova docao");
        instituicao.getDoacoes().add(doacao);
        doador.getDoacoes().add(doacao);

        doacao.setDoador(doador);
        doacao.setInstituicao(instituicao);

        return doacaoRepository.save(doacao) != null;
    }

    public void desativarChat(Long id) {
        log.info("Desativando doação para que o chat não seja mais visível para o doador");
        doacaoRepository.deactivateChatById(id);
    }

    public void apagarChat(Long id) {
        log.info("Desabilitando a visibilidade do chat para a instituição");
        doacaoRepository.deleteChatById(id);
    }

    public List<Doacao> buscarDoacoesAtivas(Doador doador) {
        log.info("Buscando chats ativos do doador logado");
        return doacaoRepository.getDoacoesAtivas(doador);
    }

    public List<Doacao> buscarDoacoesVisiveis(Instituicao instituicao) {
        log.info("Buscando chats visíveis para a instituição logada");
        return doacaoRepository.getDoacoesVisiveis(instituicao);
    }

    public float carregarDadosDoacao(List<Doacao> doacoes, float soma, List<String> causasDoacao, List<Float> valoresPorCausa, boolean queroDoadores, List<Object> outraPonta) {
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

            if(queroDoadores)
                outraPonta.add(doacaoAtual.getDoador());
            else
                outraPonta.add(doacaoAtual.getInstituicao());
        }

        return valorTotal;
    }
}
