package br.com.zup.proposta.propostas.controller.response;

import br.com.zup.proposta.cartoes.model.Cartao;
import br.com.zup.proposta.propostas.model.Proposta;
import br.com.zup.proposta.propostas.model.PropostaStatus;
import br.com.zup.proposta.util.EncryptionUtil;
import org.springframework.lang.Nullable;

import java.util.Optional;
import java.util.UUID;

public class PropostaResponse {
    @Nullable
    private UUID idCartao;
    private String documento;
    private PropostaStatus status;

    public PropostaResponse(Proposta proposta, EncryptionUtil encryptionUtil) {
        this.idCartao = Optional.ofNullable(proposta.getCartao()).map(Cartao::getUuid).orElse(null);
        this.documento = proposta.getDecryptedDocumento(encryptionUtil);
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
