package br.com.zup.proposta.cartoes.controller;

import br.com.zup.proposta.cartoes.controller.request.NovaCarteiraRequest;
import br.com.zup.proposta.cartoes.controller.response.CarteiraResponse;
import br.com.zup.proposta.cartoes.httpclient.CartaoClient;
import br.com.zup.proposta.cartoes.model.Cartao;
import br.com.zup.proposta.cartoes.model.Carteira;
import br.com.zup.proposta.cartoes.repository.CartaoRepository;
import br.com.zup.proposta.cartoes.repository.CarteiraRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RestController
public class CarteiraController {

    private final CartaoRepository cartaoRepository;
    private final CartaoClient cartaoClient;
    private final CarteiraRepository carteiraRepository;

    public CarteiraController(CartaoRepository cartaoRepository, CartaoClient cartaoClient, CarteiraRepository carteiraRepository) {
        this.cartaoRepository = cartaoRepository;
        this.cartaoClient = cartaoClient;
        this.carteiraRepository = carteiraRepository;
    }

    @PostMapping("/api/cartoes/{cartaoUuid}/carteiras")
    @Transactional
    public ResponseEntity<?> adicionarCarteira(@PathVariable UUID cartaoUuid, @RequestBody @Valid NovaCarteiraRequest request, UriComponentsBuilder uriBuilder) {
        Cartao cartao = cartaoRepository.findByUuid(cartaoUuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não encontrado"));
        Carteira carteira = request.toModel(cartao);
        if (carteiraRepository.existsByTipoCarteira(carteira.getTipoCarteira()))
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Cartão já associado com a carteira " + carteira.getTipoCarteira().name());
        cartao.adicionarCarteira(carteira, cartaoClient);
        URI uri = uriBuilder.path("/api/carteiras/{carteiraUuid}").buildAndExpand(carteira.getUuid()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/api/carteiras/{carteiraUuid}")
    public ResponseEntity<CarteiraResponse> buscarCarteira(@PathVariable UUID carteiraUuid) {
        Carteira carteira = carteiraRepository.findByUuid(carteiraUuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Carteira não encontrada"));
        return ResponseEntity.ok(new CarteiraResponse(carteira));
    }
}
