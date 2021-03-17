package br.com.zup.proposta.cartoes.controller;

import br.com.zup.proposta.cartoes.httpclient.CartaoClient;
import br.com.zup.proposta.cartoes.model.Bloqueio;
import br.com.zup.proposta.cartoes.model.Cartao;
import br.com.zup.proposta.cartoes.repository.CartaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.UUID;

@RestController
public class BloqueioController {
    private final Logger logger = LoggerFactory.getLogger(BloqueioController.class);
    private final CartaoRepository cartaoRepository;
    private final CartaoClient cartaoClient;

    public BloqueioController(CartaoRepository cartaoRepository, CartaoClient cartaoClient) {
        this.cartaoRepository = cartaoRepository;
        this.cartaoClient = cartaoClient;
    }

    @PostMapping("/api/cartoes/{cartaoUuid}/bloquear")
    @Transactional
    public ResponseEntity<?> bloquearCartao(@PathVariable UUID cartaoUuid, HttpServletRequest request) {
        Cartao cartao = cartaoRepository.findByUuid(cartaoUuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não encontrado"));
        cartao.bloquear(new Bloqueio(cartao, request.getRemoteAddr(), request.getHeader("User-Agent")), cartaoClient);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
