package br.com.zup.proposta.cartoes.httpclient;

import br.com.zup.proposta.cartoes.httpclient.request.BloqueioRequest;
import br.com.zup.proposta.cartoes.httpclient.response.BloqueioResponse;
import br.com.zup.proposta.cartoes.httpclient.response.CartaoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class CartaoClientFallback implements CartaoClient {

    @Override
    public CartaoResponse consultar(Long idProposta) {
        throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Não foi possível consultar o cartão");
    }

    @Override
    public BloqueioResponse bloquear(String cartao, BloqueioRequest request) {
        throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Não foi possível bloquear o cartão");
    }
}
