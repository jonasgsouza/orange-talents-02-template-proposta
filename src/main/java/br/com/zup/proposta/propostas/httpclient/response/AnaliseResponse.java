package br.com.zup.proposta.propostas.httpclient.response;

public class AnaliseResponse {
    private ResultadoAnalise resultadoSolicitacao;

    @Deprecated
    public AnaliseResponse() {
    }

    public AnaliseResponse(ResultadoAnalise resultadoSolicitacao) {
        this.resultadoSolicitacao = resultadoSolicitacao;
    }

    public ResultadoAnalise getResultadoSolicitacao() {
        return resultadoSolicitacao;
    }

}
