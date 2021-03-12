package br.com.zup.proposta.propostas.httpclient;

import br.com.zup.proposta.propostas.httpclient.response.AnaliseResponse;
import br.com.zup.proposta.propostas.httpclient.response.ResultadoAnalise;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class AnaliseClientFallbackFactory implements FallbackFactory<AnaliseClient> {
    private final Logger logger = LoggerFactory.getLogger(AnaliseClientFallbackFactory.class);

    @Override
    public AnaliseClient create(Throwable cause) {
        if (cause instanceof FeignException.UnprocessableEntity) {
            return request -> new AnaliseResponse(ResultadoAnalise.COM_RESTRICAO);
        }
        throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,
                "Falha ao analisar proposta. Causa: " + cause.getMessage());
    }
}
