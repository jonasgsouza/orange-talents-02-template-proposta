package br.com.zup.proposta.cartoes.controller;

import br.com.zup.proposta.cartoes.controller.request.NovaBiometriaRequest;
import br.com.zup.proposta.cartoes.controller.response.BiometriaResponse;
import br.com.zup.proposta.cartoes.model.Biometria;
import br.com.zup.proposta.cartoes.model.Cartao;
import br.com.zup.proposta.cartoes.repository.BiometriaRepository;
import br.com.zup.proposta.cartoes.repository.CartaoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.UUID;

@RestController
public class BiometriaController {
    private final CartaoRepository cartaoRepository;
    private final BiometriaRepository biometriaRepository;

    public BiometriaController(CartaoRepository cartaoRepository, BiometriaRepository biometriaRepository) {
        this.cartaoRepository = cartaoRepository;
        this.biometriaRepository = biometriaRepository;
    }

    @PostMapping("/api/cartoes/{cartaoUuid}/biometria")
    @Transactional
    public ResponseEntity<?> cadastrarBiometria(@RequestBody @Valid NovaBiometriaRequest request,
                                                @PathVariable UUID cartaoUuid,
                                                UriComponentsBuilder uriBuilder
    ) {
        Cartao cartao = cartaoRepository.findByUuid(cartaoUuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não encontrado"));
        Biometria biometria = biometriaRepository.save(request.toModel(cartao));
        var uri = uriBuilder
                .path("/api/biometria/{id}")
                .buildAndExpand(biometria.getUuid())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/api/biometria/{biometriaUuid}")
    public ResponseEntity<BiometriaResponse> buscarBiometria(@PathVariable UUID biometriaUuid) {
        Biometria biometria = biometriaRepository.findByUuid(biometriaUuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Biometria não encontrada"));
        return ResponseEntity.ok(new BiometriaResponse(biometria));
    }
}
