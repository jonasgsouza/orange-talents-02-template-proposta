package br.com.zup.proposta.config;

import feign.Client;
import feign.opentracing.TracingClient;
import io.opentracing.Tracer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignTracingConfiguration {

    @Bean
    public Client feignClient(Tracer tracer) {
        return new TracingClient(new Client.Default(null, null), tracer);
    }
}
