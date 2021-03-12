package br.com.zup.proposta.propostas.model;

import br.com.zup.proposta.cartoes.httpclient.CartaoClient;
import br.com.zup.proposta.cartoes.model.Cartao;
import br.com.zup.proposta.propostas.httpclient.AnaliseClient;
import br.com.zup.proposta.propostas.httpclient.request.AnaliseRequest;
import br.com.zup.proposta.validation.annotation.CpfOuCnpj;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CpfOuCnpj
    @NotBlank
    @Column(unique = true)
    private String documento;

    @NotBlank
    private String nome;

    @Email
    @NotBlank
    private String email;

    @Embedded
    private Endereco endereco;

    @NotNull
    @Positive
    private BigDecimal salario;

    @Enumerated(EnumType.STRING)
    private PropostaStatus status;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Cartao cartao;

    @Deprecated
    public Proposta() {
    }

    public Proposta(@NotBlank String documento, @NotBlank String nome, @Email @NotBlank String email, Endereco endereco, @NotNull @Positive BigDecimal salario) {
        this.documento = documento;
        this.nome = nome;
        this.email = email;
        this.endereco = endereco;
        this.salario = salario;
    }

    public Long getId() {
        return id;
    }

    public void enviarParaAnalise(AnaliseClient client) {
        Assert.notNull(id, "A proposta não possui um id");
        var response = client.solicitarAnalise(new AnaliseRequest(documento, nome, id.toString()));
        status = response.getResultadoSolicitacao().getPropostaStatus();
    }

    public void consultarCartao(CartaoClient client) {
        var response = client.consultar(id);
        cartao = response.toModel(this);
    }

    public PropostaStatus getStatus() {
        return status;
    }

    public String getDocumento() {
        return documento;
    }

    public Cartao getCartao() {
        return cartao;
    }
}
