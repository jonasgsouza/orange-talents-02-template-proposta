package br.com.zup.proposta.cartoes.httpclient.response;

public class BloqueioResponse {

    private ResultadoBloqueio resultado;

    @Deprecated
    public BloqueioResponse() {
    }

    public BloqueioResponse(ResultadoBloqueio resultado) {
        this.resultado = resultado;
    }

    public ResultadoBloqueio getResultado() {
        return resultado;
    }
}
