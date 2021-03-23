package br.com.zup.proposta.propostas.httpclient.request;

import br.com.zup.proposta.propostas.model.Proposta;
import br.com.zup.proposta.util.EncryptionUtil;

import java.util.UUID;

public class AnaliseRequest {
    private String documento;
    private String nome;
    private UUID idProposta;

    public AnaliseRequest(Proposta proposta, EncryptionUtil encryptionUtil) {
        this.documento = proposta.getDecryptedDocumento(encryptionUtil);
        this.nome = proposta.getNome();
        this.idProposta = proposta.getUuid();
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public UUID getIdProposta() {
        return idProposta;
    }
}
