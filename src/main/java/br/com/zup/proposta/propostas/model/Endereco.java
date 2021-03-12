package br.com.zup.proposta.propostas.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Embeddable
public class Endereco {

    @NotBlank
    private String logradouro;

    @NotBlank
    private String complemento;

    @NotBlank
    private String bairro;

    @NotBlank
    private String cep;

    @NotBlank
    private String cidade;

    @NotBlank
    private String estado;

    @Deprecated
    public Endereco() {
    }

    public Endereco(@NotBlank String logradouro, @NotBlank String complemento, @NotBlank String bairro, @NotBlank String cep, @NotBlank String cidade, @NotBlank String estado) {
        this.logradouro = logradouro;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cep = cep;
        this.cidade = cidade;
        this.estado = estado;
    }
}
