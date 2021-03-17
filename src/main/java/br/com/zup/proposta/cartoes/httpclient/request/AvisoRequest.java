package br.com.zup.proposta.cartoes.httpclient.request;

import br.com.zup.proposta.cartoes.model.AvisoViagem;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class AvisoRequest {

    @NotBlank
    private String destino;

    @NotNull
    @Future
    private LocalDate validoAte;

    public AvisoRequest(AvisoViagem aviso) {
        this.destino = aviso.getDestino();
        this.validoAte = aviso.getTermino();
    }

    public String getDestino() {
        return destino;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }
}
