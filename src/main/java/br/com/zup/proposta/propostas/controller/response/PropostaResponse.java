package br.com.zup.proposta.propostas.controller.response;

import br.com.zup.proposta.propostas.model.Proposta;
import br.com.zup.proposta.propostas.model.PropostaStatus;
import org.springframework.lang.Nullable;

import java.util.UUID;

public class PropostaResponse {
    @Nullable
    private UUID idCartao;
    private String documento;
    private PropostaStatus status;

    public PropostaResponse(Proposta proposta) {
        if (proposta.getCartao() != null) {
            this.idCartao = proposta.getCartao().getUuid();
        }
        this.documento = proposta.getDocumento();
        this.status = proposta.getStatus();
    }

    public UUID getIdCartao() {
        return idCartao;
    }

    public String getDocumento() {
        return documento;
    }

    public PropostaStatus getStatus() {
        return status;
    }
}
