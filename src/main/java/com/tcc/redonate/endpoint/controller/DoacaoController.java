package com.tcc.redonate.endpoint.controller;

import com.tcc.redonate.endpoint.service.DoacaoService;
import com.tcc.redonate.endpoint.service.DoadorService;
import com.tcc.redonate.endpoint.service.InstituicaoService;
import com.tcc.redonate.model.Doacao;
import com.tcc.redonate.model.Doador;
import com.tcc.redonate.model.Instituicao;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DoacaoController {
    private final DoacaoService doacaoService;
    private final DoadorService doadorService;
    private final InstituicaoService instituicaoService;

    @RequestMapping(value = "/instituicao/{inst}", method = RequestMethod.POST)
    public String criarDoacao(@PathVariable("inst") Long inst, Doacao doacao, HttpServletRequest request){
        doacao.setIdInstituicao(inst);

        Long idDoador = doadorService.findByUsuarioLogado(request).getId();
        doacao.setIdDoador(idDoador);

        doacaoService.create(doacao);
        return "redirect:/instituicoes";
    }

    @RequestMapping(value = "/doacoes/dados", method = RequestMethod.GET)
    public String carregarDadosDoacoes(HttpServletRequest request, Model model){
        Instituicao dadosInstituicao = instituicaoService.findByUsuarioLogado(request);

        if(dadosInstituicao != null) {
            List<Doacao> doacoes = doacaoService.findByIdInstituicao(dadosInstituicao.getId());
            if(!doacoes.isEmpty()){
                Collections.sort(doacoes);

                List<Doador> doadores = new ArrayList<>();
                List<String> causasDoacao = new ArrayList<>();
                List<Float> valoresPorCausa = new ArrayList<>();
                float valorTotal = 0;
                float soma = doacoes.get(0).getValorDoacao();

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

                    doadores.add(doadorService.findById(doacaoAtual.getIdDoador()));
                }

                model.addAttribute("doadores", doadores);
                model.addAttribute("causas", causasDoacao);
                model.addAttribute("valores", valoresPorCausa);
                model.addAttribute("total", valorTotal);

            }
            model.addAttribute("doacoes", doacoes);
            model.addAttribute("dadosInst", dadosInstituicao);

            return "dadosDoacoes";
        }else{
            return "redirect:/";
        }
    }
}
