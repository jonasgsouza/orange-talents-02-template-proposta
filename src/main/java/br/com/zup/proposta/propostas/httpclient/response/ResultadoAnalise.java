package br.com.zup.proposta.propostas.httpclient.response;

import br.com.zup.proposta.propostas.model.PropostaStatus;

import static br.com.zup.proposta.propostas.model.PropostaStatus.ELEGIVEL;
import static br.com.zup.proposta.propostas.model.PropostaStatus.NAO_ELEGIVEL;

public enum ResultadoAnalise {
    COM_RESTRICAO(NAO_ELEGIVEL), SEM_RESTRICAO(ELEGIVEL);
    private PropostaStatus propostaStatus;

    ResultadoAnalise(PropostaStatus propostaStatus) {
        this.propostaStatus = propostaStatus;
    }

    public PropostaStatus getPropostaStatus() {
        return propostaStatus;
    }
}
