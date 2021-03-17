package br.com.zup.proposta.cartoes.httpclient;

import br.com.zup.proposta.cartoes.httpclient.request.AvisoRequest;
import br.com.zup.proposta.cartoes.httpclient.request.BloqueioRequest;
import br.com.zup.proposta.cartoes.httpclient.request.CarteiraRequest;
import br.com.zup.proposta.cartoes.httpclient.response.AvisoResponse;
import br.com.zup.proposta.cartoes.httpclient.response.BloqueioResponse;
import br.com.zup.proposta.cartoes.httpclient.response.CartaoResponse;
import br.com.zup.proposta.cartoes.httpclient.response.CarteiraResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Component
public class CartaoClientFallback implements CartaoClient {

    @Override
    public CartaoResponse consultar(UUID idProposta) {
        throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Não foi possível consultar o cartão");
    }

    @Override
    public BloqueioResponse bloquear(String cartao, BloqueioRequest request) {
        throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Não foi possível bloquear o cartão");
    }

    @Override
    public AvisoResponse registrarAviso(String numeroCartao, AvisoRequest request) {
        throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Não foi possível registrar o aviso");
    }

    @Override
    public CarteiraResponse adicionarCarteira(String numeroCartao, CarteiraRequest carteiraRequest) {
        throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Não foi possível adicionar a carteira");
    }
}
