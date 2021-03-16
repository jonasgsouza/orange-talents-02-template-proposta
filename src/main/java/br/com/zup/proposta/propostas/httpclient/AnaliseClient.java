package br.com.zup.proposta.propostas.httpclient;

import br.com.zup.proposta.propostas.httpclient.request.AnaliseRequest;
import br.com.zup.proposta.propostas.httpclient.response.AnaliseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "analiseClient", url = "${analiseClient.url}", fallbackFactory = AnaliseClientFallbackFactory.class)
public interface AnaliseClient {

    @PostMapping(value = "/api/solicitacao", consumes = "application/json")
    AnaliseResponse solicitarAnalise(AnaliseRequest request);

}
