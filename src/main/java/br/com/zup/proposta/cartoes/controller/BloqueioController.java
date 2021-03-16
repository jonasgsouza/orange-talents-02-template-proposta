package br.com.zup.proposta.cartoes.controller;

import br.com.zup.proposta.cartoes.controller.request.NovoBloqueioRequest;
import br.com.zup.proposta.cartoes.httpclient.CartaoClient;
import br.com.zup.proposta.cartoes.repository.BloqueioRepository;
import br.com.zup.proposta.cartoes.repository.CartaoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class BloqueioController {
    private final CartaoRepository cartaoRepository;
    private final BloqueioRepository bloqueioRepository;
    private final CartaoClient cartaoClient;

    public BloqueioController(CartaoRepository cartaoRepository, BloqueioRepository bloqueioRepository, CartaoClient cartaoClient) {
        this.cartaoRepository = cartaoRepository;
        this.bloqueioRepository = bloqueioRepository;
        this.cartaoClient = cartaoClient;
    }

    @PostMapping("/api/cartoes/{id}/bloquear")
    @Transactional
    public ResponseEntity<?> bloquearCartao(@PathVariable Long id, @RequestBody @Valid NovoBloqueioRequest request) {
        var cartao = cartaoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não encontrado"));
        cartao.bloquear(request.toModel(cartao), cartaoClient);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
