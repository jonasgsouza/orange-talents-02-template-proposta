package br.com.zup.proposta.propostas.controller.request;

import br.com.zup.proposta.propostas.model.Proposta;
import br.com.zup.proposta.validation.annotation.CpfOuCnpj;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class NovaPropostaRequest {

    @CpfOuCnpj
    @NotBlank
    private String documento;

    @NotBlank
    private String nome;

    @Email
    @NotBlank
    private String email;

    @NotNull
    @Valid
    private NovoEnderecoRequest endereco;

    @NotNull
    @Positive
    private BigDecimal salario;

    public NovaPropostaRequest(@NotBlank String documento, @NotBlank String nome, @Email @NotBlank String email, @NotNull NovoEnderecoRequest endereco, @NotNull @Positive BigDecimal salario) {
        this.documento = documento;
        this.nome = nome;
        this.email = email;
        this.endereco = endereco;
        this.salario = salario;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public NovoEnderecoRequest getEndereco() {
        return endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public Proposta toModel() {
        return new Proposta(documento, nome, email, endereco.toModel(), salario);
    }
}
