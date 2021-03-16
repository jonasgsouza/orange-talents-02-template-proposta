package br.com.zup.proposta.cartoes.controller.request;

import br.com.zup.proposta.cartoes.model.Bloqueio;
import br.com.zup.proposta.cartoes.model.Cartao;

import javax.validation.constraints.NotBlank;

public class NovoBloqueioRequest {


    @NotBlank
    private String ip;

    @NotBlank
    private String userAgent;

    public NovoBloqueioRequest(@NotBlank String ip, @NotBlank String userAgent) {
        this.ip = ip;
        this.userAgent = userAgent;
    }

    public String getIp() {
        return ip;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public Bloqueio toModel(Cartao cartao) {
        return new Bloqueio(cartao, ip, userAgent);
    }
}
