package br.com.zup.proposta.cartoes.controller.response;

import br.com.zup.proposta.cartoes.model.Carteira;
import br.com.zup.proposta.cartoes.model.TipoCarteira;

public class CarteiraResponse {
    private String email;
    private TipoCarteira carteira;

    public CarteiraResponse(Carteira carteira) {
        this.email = carteira.getEmail();
        this.carteira = carteira.getTipoCarteira();
    }

    public String getEmail() {
        return email;
    }

    public TipoCarteira getCarteira() {
        return carteira;
    }
}
