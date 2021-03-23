package br.com.zup.proposta.cartoes.model;

import br.com.zup.proposta.cartoes.httpclient.CartaoClient;
import br.com.zup.proposta.cartoes.httpclient.request.BloqueioRequest;
import br.com.zup.proposta.cartoes.httpclient.request.CarteiraRequest;
import br.com.zup.proposta.cartoes.httpclient.response.BloqueioResponse;
import br.com.zup.proposta.cartoes.httpclient.response.CarteiraResponse;
import br.com.zup.proposta.cartoes.httpclient.response.ResultadoBloqueio;
import br.com.zup.proposta.cartoes.httpclient.response.ResultadoCarteira;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartaoTest {

    @Mock
    private CartaoClient cartaoClient;

    private Cartao cartao;

    @BeforeEach
    void setUp() {
        cartao = new Cartao("5461-5641-6345-8421", LocalDateTime.now(), "Cláudio Fernando Ricardo Campos");
    }

    @Test
    void deveriaBloquearOCartaoComSucesso() {
        when(cartaoClient.bloquear(eq(cartao.getNumeroCartao()), any(BloqueioRequest.class))).thenReturn(new BloqueioResponse(ResultadoBloqueio.BLOQUEADO));
        cartao.bloquear(new Bloqueio(cartao, "127.0.0.1", "chrome"), cartaoClient);
        assertEquals(CartaoStatus.BLOQUEADO, cartao.getStatus());
        assertEquals(1, cartao.getBloqueios().size());
    }

    @Test
    void deveriaLancarExcecaoQuandoTentarBloquearCartaoJaBloqueado() {
        when(cartaoClient.bloquear(eq(cartao.getNumeroCartao()), any(BloqueioRequest.class))).thenReturn(new BloqueioResponse(ResultadoBloqueio.BLOQUEADO));
        cartao.bloquear(new Bloqueio(cartao, "127.0.0.1", "chrome"), cartaoClient);
        try {
            cartao.bloquear(new Bloqueio(cartao, "127.0.0.2", "opera"), cartaoClient);
        }catch (Exception e) {
            assertTrue(e instanceof ResponseStatusException);
            assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, ((ResponseStatusException) e).getStatus());
        }
    }

    @Test
    void deveriaAdicionarUmaNovaCarteira() {
        when(cartaoClient.adicionarCarteira(eq(cartao.getNumeroCartao()), any(CarteiraRequest.class))).thenReturn(new CarteiraResponse(ResultadoCarteira.ASSOCIADA));
        cartao.adicionarCarteira(new Carteira(cartao, "email@email.com", TipoCarteira.PAYPAL), cartaoClient);
        assertEquals(1, cartao.getCarteiras().size());
    }

    @Test
    void deveriaLancarExcecaoQuandoAdicionarCarteiraRepetida() {
        when(cartaoClient.adicionarCarteira(eq(cartao.getNumeroCartao()), any(CarteiraRequest.class))).thenReturn(new CarteiraResponse(ResultadoCarteira.ASSOCIADA));
        cartao.adicionarCarteira(new Carteira(cartao, "email@email.com", TipoCarteira.PAYPAL), cartaoClient);
        try {
            cartao.adicionarCarteira(new Carteira(cartao, "email@email.com", TipoCarteira.PAYPAL), cartaoClient);
        }catch (Exception e) {
            assertTrue(e instanceof ResponseStatusException);
            assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, ((ResponseStatusException) e).getStatus());
        }
    }

}