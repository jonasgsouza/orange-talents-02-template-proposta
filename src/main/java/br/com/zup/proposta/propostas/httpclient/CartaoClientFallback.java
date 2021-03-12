package br.com.zup.proposta.propostas.httpclient;

import br.com.zup.proposta.propostas.httpclient.response.CartaoResponse;
import org.springframework.stereotype.Component;

@Component
public class CartaoClientFallback implements CartaoClient {

    @Override
    public CartaoResponse consultar(Long idProposta) {
        return new CartaoResponse(null);
    }
}
