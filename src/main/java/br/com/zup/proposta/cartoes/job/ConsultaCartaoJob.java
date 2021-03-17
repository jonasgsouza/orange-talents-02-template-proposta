package br.com.zup.proposta.cartoes.job;

import br.com.zup.proposta.cartoes.httpclient.CartaoClient;
import br.com.zup.proposta.propostas.model.Proposta;
import br.com.zup.proposta.propostas.model.PropostaStatus;
import br.com.zup.proposta.propostas.repository.PropostaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

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
        List<Proposta> propostas = propostaRepository
                .findByCartaoIdNullAndStatusEquals(PropostaStatus.ELEGIVEL);
        logger.info(propostas.size() + " proposta(s) encontrada(s)");
        propostas.forEach(proposta -> {
            logger.info("Consultando cartão");
            proposta.consultarCartao(cartaoClient);
        });
    }
}
