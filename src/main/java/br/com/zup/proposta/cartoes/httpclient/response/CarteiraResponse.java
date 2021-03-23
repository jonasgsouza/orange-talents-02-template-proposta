package br.com.zup.proposta.cartoes.httpclient.response;

public class CarteiraResponse {

    private ResultadoCarteira resultado;

    @Deprecated
    public CarteiraResponse() {}

    public CarteiraResponse(ResultadoCarteira resultado) {
        this.resultado = resultado;
    }

    public ResultadoCarteira getResultado() {
        return resultado;
    }
}
