package br.com.zup.proposta.propostas.model;

import br.com.zup.proposta.cartoes.httpclient.CartaoClient;
import br.com.zup.proposta.cartoes.httpclient.response.CartaoResponse;
import br.com.zup.proposta.propostas.httpclient.AnaliseClient;
import br.com.zup.proposta.propostas.httpclient.request.AnaliseRequest;
import br.com.zup.proposta.propostas.httpclient.response.AnaliseResponse;
import br.com.zup.proposta.propostas.httpclient.response.ResultadoAnalise;
import br.com.zup.proposta.util.EncryptionUtil;
import br.com.zup.proposta.util.SHA256Digest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
class PropostaTest {

    @Mock()
    private EncryptionUtil encryptionUtil;

    @Mock
    private AnaliseClient analiseClient;

    @Mock
    private CartaoClient cartaoClient;

    @BeforeEach
    void setUp() {
        when(analiseClient.solicitarAnalise(any(AnaliseRequest.class))).then(var1 -> {
            AnaliseRequest request = var1.getArgument(0, AnaliseRequest.class);
            return request.getDocumento().startsWith("3") ? new AnaliseResponse(ResultadoAnalise.COM_RESTRICAO) : new AnaliseResponse(ResultadoAnalise.SEM_RESTRICAO);
        });
    }

    @Test
    void deveriaEnviarParaAnaliseEDevolverStatusElegivel() {
        var documento = "12345678909";
        String randomString = SHA256Digest.digest(documento);
        when(encryptionUtil.textEncrypt(documento)).thenReturn(randomString);
        when(encryptionUtil.textDecrypt(randomString)).thenReturn(documento);
        var proposta = new Proposta(
                documento,
                "Fulano",
                "email@email.com",
                new Endereco("Logradouro", "Complemento", "Bairro", "45125520", "Cidade", "Estado"),
                new BigDecimal("2000"),
                encryptionUtil
        );
        proposta.enviarParaAnalise(analiseClient, encryptionUtil);
        assertEquals(PropostaStatus.ELEGIVEL, proposta.getStatus());
    }

    @Test
    void deveriaEnviarParaAnaliseEDevolverStatusNaoElegivel() {
        var documento = "37924849009";
        String randomString = SHA256Digest.digest(documento);
        when(encryptionUtil.textEncrypt(documento)).thenReturn(randomString);
        when(encryptionUtil.textDecrypt(randomString)).thenReturn(documento);
        var proposta = new Proposta(
                documento,
                "Fulano",
                "email@email.com",
                new Endereco("Logradouro", "Complemento", "Bairro", "45125520", "Cidade", "Estado"),
                new BigDecimal("2000"),
                encryptionUtil
        );
        proposta.enviarParaAnalise(analiseClient, encryptionUtil);
        assertEquals(PropostaStatus.NAO_ELEGIVEL, proposta.getStatus());
    }

    @Test
    void deveriaAssociarUmNovoCartaoComSucesso() {
        var numeroCartao = "5168-2684-4284-5731";
        when(cartaoClient.consultar(any(UUID.class))).thenReturn(new CartaoResponse("5168-2684-4284-5731", LocalDateTime.now(), "Marina Isabel Betina Moreira"));
        var proposta = new Proposta(
                "12345678909",
                "Fulano",
                "email@email.com",
                new Endereco("Logradouro", "Complemento", "Bairro", "45125520", "Cidade", "Estado"),
                new BigDecimal("2000"),
                encryptionUtil
        );
        proposta.consultarCartao(cartaoClient);
        assertNotNull(proposta.getCartao());
        assertEquals(proposta.getCartao().getNumeroCartao(), numeroCartao);
    }

}