package br.com.zup.proposta.cartoes.httpclient;

import br.com.zup.proposta.cartoes.httpclient.request.AvisoRequest;
import br.com.zup.proposta.cartoes.httpclient.request.BloqueioRequest;
import br.com.zup.proposta.cartoes.httpclient.request.CarteiraRequest;
import br.com.zup.proposta.cartoes.httpclient.response.AvisoResponse;
import br.com.zup.proposta.cartoes.httpclient.response.BloqueioResponse;
import br.com.zup.proposta.cartoes.httpclient.response.CartaoResponse;
import br.com.zup.proposta.cartoes.httpclient.response.CarteiraResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "cartaoClient", url = "${cartaoClient.url}", fallback = CartaoClientFallback.class)
public interface CartaoClient {

    @GetMapping
    CartaoResponse consultar(@RequestParam UUID idProposta);

    @PostMapping("/{numeroCartao}/bloqueios")
    BloqueioResponse bloquear(@PathVariable String numeroCartao, BloqueioRequest request);

    @PostMapping("/{numeroCartao}/avisos")
    AvisoResponse registrarAviso(@PathVariable String numeroCartao, AvisoRequest request);

    @PostMapping("/{numeroCartao}/carteiras")
    CarteiraResponse adicionarCarteira(@PathVariable String numeroCartao, CarteiraRequest carteiraRequest);
}
