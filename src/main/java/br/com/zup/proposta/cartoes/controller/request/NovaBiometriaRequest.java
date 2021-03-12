package br.com.zup.proposta.cartoes.controller.request;

import br.com.zup.proposta.cartoes.model.Biometria;
import br.com.zup.proposta.cartoes.model.Cartao;
import br.com.zup.proposta.cartoes.repository.CartaoRepository;

import javax.validation.constraints.NotBlank;

public class NovaBiometriaRequest {

    @NotBlank
    private String fingerprint;

    @Deprecated
    public NovaBiometriaRequest() {
    }

    public NovaBiometriaRequest(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public Biometria toModel(Cartao cartao) {
        return new Biometria(cartao, fingerprint);
    }
}
