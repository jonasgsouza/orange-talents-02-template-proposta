package br.com.zup.proposta.propostas.controller;

import br.com.zup.proposta.propostas.controller.request.NovaPropostaRequest;
import br.com.zup.proposta.propostas.controller.response.PropostaResponse;
import br.com.zup.proposta.propostas.httpclient.AnaliseClient;
import br.com.zup.proposta.propostas.repository.PropostaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/propostas")
public class PropostaController {

    private final PropostaRepository propostaRepository;
    private final AnaliseClient analiseClient;

    public PropostaController(PropostaRepository propostaRepository, AnaliseClient analiseClient) {
        this.propostaRepository = propostaRepository;
        this.analiseClient = analiseClient;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> criar(@RequestBody @Valid NovaPropostaRequest request, UriComponentsBuilder uriBuilder) {
        if (propostaRepository.existsByDocumento(request.getDocumento()))
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Já existe uma proposta para o mesmo documento");
        var proposta = propostaRepository.save(request.toModel());
        proposta.enviarParaAnalise(analiseClient);
        var uri = uriBuilder.path("/api/propostas/{id}")
                .buildAndExpand(proposta.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropostaResponse> buscar(@PathVariable Long id) {
        var proposta = propostaRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(new PropostaResponse(proposta));
    }
}
