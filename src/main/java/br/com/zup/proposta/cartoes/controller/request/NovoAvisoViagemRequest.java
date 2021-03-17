package br.com.zup.proposta.cartoes.controller.request;

import br.com.zup.proposta.cartoes.model.AvisoViagem;
import br.com.zup.proposta.cartoes.model.Cartao;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class NovoAvisoViagemRequest {

    @NotBlank
    private String destino;

    @NotNull
    @Future
    private LocalDate termino;

    public NovoAvisoViagemRequest(String destino, LocalDate termino) {
        this.destino = destino;
        this.termino = termino;
    }

    public String getDestino() {
        return destino;
    }

    public LocalDate getTermino() {
        return termino;
    }

    public AvisoViagem toModel(Cartao cartao, HttpServletRequest request) {
        return new AvisoViagem(cartao, destino, termino, request.getRemoteAddr(), request.getHeader("User-Agent"));
    }
}
