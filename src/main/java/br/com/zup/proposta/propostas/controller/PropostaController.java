package br.com.zup.proposta.propostas.controller;

import br.com.zup.proposta.propostas.controller.request.NovaPropostaRequest;
import br.com.zup.proposta.propostas.controller.response.PropostaResponse;
import br.com.zup.proposta.propostas.httpclient.AnaliseClient;
import br.com.zup.proposta.propostas.model.Proposta;
import br.com.zup.proposta.propostas.repository.PropostaRepository;
import br.com.zup.proposta.util.EncryptionUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/propostas")
public class PropostaController {

    private final PropostaRepository propostaRepository;
    private final AnaliseClient analiseClient;
    private final EncryptionUtil encryptionUtil;

    public PropostaController(PropostaRepository propostaRepository, AnaliseClient analiseClient, EncryptionUtil encryptionUtil) {
        this.propostaRepository = propostaRepository;
        this.analiseClient = analiseClient;
        this.encryptionUtil = encryptionUtil;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> criarProposta(@RequestBody @Valid NovaPropostaRequest request, UriComponentsBuilder uriBuilder) {
        Proposta proposta = request.toModel(encryptionUtil);
        if (propostaRepository.existsByDocumentoHash(proposta.getDocumentoHash()))
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Já existe uma proposta para o mesmo documento");
        propostaRepository.save(proposta);
        proposta.enviarParaAnalise(analiseClient);
        var uri = uriBuilder.path("/api/propostas/{id}")
                .buildAndExpand(proposta.getUuid())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{propostaUuid}")
    public ResponseEntity<PropostaResponse> buscarProposta(@PathVariable UUID propostaUuid) {
        Proposta proposta = propostaRepository.findByUuid(propostaUuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(new PropostaResponse(proposta, encryptionUtil));
    }
}
