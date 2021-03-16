package br.com.zup.proposta.cartoes.controller;

import br.com.zup.proposta.cartoes.httpclient.CartaoClient;
import br.com.zup.proposta.cartoes.model.Bloqueio;
import br.com.zup.proposta.cartoes.repository.CartaoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@RestController
public class BloqueioController {
    private final CartaoRepository cartaoRepository;
    private final CartaoClient cartaoClient;

    public BloqueioController(CartaoRepository cartaoRepository, CartaoClient cartaoClient) {
        this.cartaoRepository = cartaoRepository;
        this.cartaoClient = cartaoClient;
    }

    @PostMapping("/api/cartoes/{id}/bloquear")
    @Transactional
    public ResponseEntity<?> bloquearCartao(@PathVariable Long id, HttpServletRequest request) {
        var cartao = cartaoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não encontrado"));
        cartao.bloquear(new Bloqueio(cartao, request.getRemoteAddr(), request.getHeader("User-Agent")), cartaoClient);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
