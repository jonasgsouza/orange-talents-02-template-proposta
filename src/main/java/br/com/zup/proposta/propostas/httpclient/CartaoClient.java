package br.com.zup.proposta.propostas.httpclient;

import br.com.zup.proposta.propostas.httpclient.response.CartaoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cartoes", url = "${cartoes.url}", fallback = CartaoClientFallback.class)
public interface CartaoClient {

    @GetMapping("/api/cartoes")
    CartaoResponse consultar(@RequestParam Long idProposta);
}
