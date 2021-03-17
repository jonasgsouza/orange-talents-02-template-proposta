package br.com.zup.proposta.cartoes.httpclient.request;

import br.com.zup.proposta.cartoes.model.Carteira;
import org.springframework.util.Assert;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class CarteiraRequest {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String carteira;

    public CarteiraRequest(Carteira carteira) {
        Assert.notNull(carteira.getTipoCarteira(), "Carteira não pode ser nula");
        this.email = carteira.getEmail();
        this.carteira = carteira.getTipoCarteira().name();
    }

    public String getEmail() {
        return email;
    }

    public String getCarteira() {
        return carteira;
    }
}
