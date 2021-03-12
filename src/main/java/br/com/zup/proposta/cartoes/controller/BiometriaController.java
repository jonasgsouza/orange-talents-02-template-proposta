package br.com.zup.proposta.cartoes.controller;

import br.com.zup.proposta.cartoes.controller.request.NovaBiometriaRequest;
import br.com.zup.proposta.cartoes.controller.response.BiometriaResponse;
import br.com.zup.proposta.cartoes.repository.BiometriaRepository;
import br.com.zup.proposta.cartoes.repository.CartaoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class BiometriaController {
    private final CartaoRepository cartaoRepository;
    private final BiometriaRepository biometriaRepository;

    public BiometriaController(CartaoRepository cartaoRepository, BiometriaRepository biometriaRepository) {
        this.cartaoRepository = cartaoRepository;
        this.biometriaRepository = biometriaRepository;
    }

    @PostMapping("/api/cartoes/{id}/biometria")
    @Transactional
    public ResponseEntity<?> cadastrarBiometria(@RequestBody @Valid NovaBiometriaRequest request,
                                                @PathVariable Long id) {
        var cartao = cartaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não encontrado"));
        var biometria = request.toModel(cartao);
        cartao.adicionarBiometria(biometria);
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(biometria.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/api/cartoes/{id}/biometria")
    public ResponseEntity<BiometriaResponse> buscarBiometria(@PathVariable Long id) {
        var biometria = biometriaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Biometria não encontrada"));
        return ResponseEntity.ok(new BiometriaResponse(biometria));
    }
}
