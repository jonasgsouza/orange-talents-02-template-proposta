package br.com.zup.proposta.cartoes.controller.response;

import br.com.zup.proposta.cartoes.model.Biometria;

public class BiometriaResponse {

    private String fingerprint;

    public BiometriaResponse(Biometria biometria) {
        fingerprint = biometria.getFingerprint();
    }

    public String getFingerprint() {
        return fingerprint;
    }
}
