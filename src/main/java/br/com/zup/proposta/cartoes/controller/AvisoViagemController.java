package br.com.zup.proposta.cartoes.controller;

import br.com.zup.proposta.cartoes.controller.request.NovoAvisoViagemRequest;
import br.com.zup.proposta.cartoes.httpclient.CartaoClient;
import br.com.zup.proposta.cartoes.model.Cartao;
import br.com.zup.proposta.cartoes.repository.CartaoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.UUID;

@RestController
public class AvisoViagemController {
    private final CartaoRepository cartaoRepository;
    private final CartaoClient cartaoClient;

    public AvisoViagemController(CartaoRepository cartaoRepository, CartaoClient cartaoClient) {
        this.cartaoRepository = cartaoRepository;
        this.cartaoClient = cartaoClient;
    }

    @PostMapping("/api/cartoes/{cartaoUuid}/avisos")
    @Transactional
    public ResponseEntity<?> registrarAviso(@PathVariable UUID cartaoUuid, @RequestBody @Valid NovoAvisoViagemRequest request, HttpServletRequest servletRequest) {
        Cartao cartao = cartaoRepository.findByUuid(cartaoUuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não encontrado"));
        cartao.registrarAvisoViagem(request.toModel(cartao, servletRequest), cartaoClient);
        return ResponseEntity.ok().build();
    }
}
