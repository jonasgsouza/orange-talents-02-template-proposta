package br.com.zup.proposta.propostas.controller.response;

import br.com.zup.proposta.propostas.model.Proposta;
import br.com.zup.proposta.propostas.model.PropostaStatus;

public class PropostaResponse {
    private Long id;
    private String documento;
    private PropostaStatus status;

    public PropostaResponse(Proposta proposta) {
        this.id = proposta.getId();
        this.documento = proposta.getDocumento();
        this.status = proposta.getStatus();
    }

    public Long getId() {
        return id;
    }

    public String getDocumento() {
        return documento;
    }

    public PropostaStatus getStatus() {
        return status;
    }
}
