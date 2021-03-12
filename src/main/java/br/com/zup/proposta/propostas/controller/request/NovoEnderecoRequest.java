package br.com.zup.proposta.propostas.controller.request;

import br.com.zup.proposta.propostas.model.Endereco;

import javax.validation.constraints.NotBlank;

public class NovoEnderecoRequest {
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

    public NovoEnderecoRequest(@NotBlank String logradouro, @NotBlank String complemento, @NotBlank String bairro, @NotBlank String cep, @NotBlank String cidade, @NotBlank String estado) {
        this.logradouro = logradouro;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cep = cep;
        this.cidade = cidade;
        this.estado = estado;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCep() {
        return cep;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

    public Endereco toModel() {
        return new Endereco(logradouro, complemento, bairro, cep, cidade, estado);
    }
}
