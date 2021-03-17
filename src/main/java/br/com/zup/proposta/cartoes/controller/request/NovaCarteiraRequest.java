package br.com.zup.proposta.cartoes.controller.request;

import br.com.zup.proposta.cartoes.model.Cartao;
import br.com.zup.proposta.cartoes.model.Carteira;
import br.com.zup.proposta.cartoes.model.TipoCarteira;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class NovaCarteiraRequest {

    @NotBlank
    @Email
    private String email;

    @NotNull
    private TipoCarteira carteira;

    public NovaCarteiraRequest(@NotBlank @Email String email, @NotNull TipoCarteira carteira) {
        this.email = email;
        this.carteira = carteira;
    }

    public String getEmail() {
        return email;
    }

    public TipoCarteira getCarteira() {
        return carteira;
    }

    public Carteira toModel(Cartao cartao) {
        return new Carteira(cartao, email, carteira);
    }
}
