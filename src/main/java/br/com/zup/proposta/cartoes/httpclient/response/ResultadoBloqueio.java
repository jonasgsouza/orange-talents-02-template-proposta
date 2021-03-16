package br.com.zup.proposta.cartoes.httpclient.response;

import br.com.zup.proposta.cartoes.model.CartaoStatus;

public enum ResultadoBloqueio {
    BLOQUEADO(CartaoStatus.BLOQUEADO), LIBERADO(CartaoStatus.LIBERADO);

    private CartaoStatus cartaoStatus;

    ResultadoBloqueio(CartaoStatus cartaoStatus) {
        this.cartaoStatus = cartaoStatus;
    }

    public CartaoStatus getCartaoStatus() {
        return this.cartaoStatus;
    }
}
