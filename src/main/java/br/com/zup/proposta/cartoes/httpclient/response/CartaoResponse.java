package br.com.zup.proposta.cartoes.httpclient.response;

import br.com.zup.proposta.cartoes.model.Cartao;
import br.com.zup.proposta.propostas.model.Proposta;

import java.time.LocalDateTime;

public class CartaoResponse {
    private String id;
    private LocalDateTime emitidoEm;
    private String titular;

    @Deprecated
    public CartaoResponse() {
    }

    public CartaoResponse(String id, LocalDateTime emitidoEm, String titular) {
        this.id = id;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public String getTitular() {
        return titular;
    }

    public Cartao toModel(Proposta proposta) {
        return new Cartao(id, emitidoEm, titular);
    }
}
