package br.com.zup.proposta.propostas.controller.response;

import br.com.zup.proposta.propostas.model.Proposta;
import br.com.zup.proposta.propostas.model.PropostaStatus;

import java.util.Optional;

public class PropostaResponse {
    private Long idCartao;
    private String documento;
    private PropostaStatus status;

    public PropostaResponse(Proposta proposta) {
        var cartao = Optional.ofNullable(proposta.getCartao());
        if (cartao.isPresent()) {
            this.idCartao = cartao.get().getId();
        }
        this.documento = proposta.getDocumento();
        this.status = proposta.getStatus();
    }

    public Long getIdCartao() {
        return idCartao;
    }

    public String getDocumento() {
        return documento;
    }

    public PropostaStatus getStatus() {
        return status;
    }
}
