package com.tcc.redonate.endpoint.controller;

import com.tcc.redonate.endpoint.service.MensagemService;
import com.tcc.redonate.endpoint.service.UsuarioService;
import com.tcc.redonate.entity.Mensagem;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MensagemController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MensagemService mensagemService;
    private final UsuarioService usuarioService;

    @MessageMapping("/chat/{to}")
    public void sendMessage(@DestinationVariable Long to, Mensagem message) {
        System.out.println("handling send message: " + message + " to: " + to);

        boolean isExists = usuarioService.existe(to);
        if (isExists) {
            simpMessagingTemplate.convertAndSend("/topic/messages/" + to, message);

            mensagemService.saveMensagem(message);
        }
    }

    @GetMapping(value = "/historico/{id}")
    public ResponseEntity<List<Mensagem>> carregarHistorico(@PathVariable Long id) {
        List<Mensagem> historicoDeConversa = mensagemService.carregarHistorico(id);

        return ResponseEntity.ok(historicoDeConversa);
    }
}
