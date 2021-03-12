package br.com.zup.proposta.propostas.job;

import br.com.zup.proposta.propostas.httpclient.CartaoClient;
import br.com.zup.proposta.propostas.model.PropostaStatus;
import br.com.zup.proposta.propostas.repository.PropostaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class ConsultaCartaoJob {

    private final PropostaRepository propostaRepository;
    private final CartaoClient cartaoClient;
    private final Logger logger = LoggerFactory.getLogger(ConsultaCartaoJob.class);

    public ConsultaCartaoJob(PropostaRepository propostaRepository, CartaoClient cartaoClient) {
        this.propostaRepository = propostaRepository;
        this.cartaoClient = cartaoClient;
    }

    @Transactional
    @Scheduled(fixedDelayString = "${periodicidade.consulta-cartao}")
    public void buscarCartoes() {
        logger.info("Consultando cartões...");
        var propostas = propostaRepository
                .findByNumeroCartaoNullAndStatusEquals(PropostaStatus.ELEGIVEL);
        logger.info(propostas.size() + " proposta(s) encontrada(s)");
        propostas.parallelStream().forEach(proposta -> {
            logger.info("Consultando cartão");
            proposta.consultarCartao(cartaoClient);
        });
    }
}
