package br.com.zup.proposta.propostas.model;

import br.com.zup.proposta.cartoes.httpclient.CartaoClient;
import br.com.zup.proposta.cartoes.httpclient.response.CartaoResponse;
import br.com.zup.proposta.cartoes.model.Cartao;
import br.com.zup.proposta.propostas.httpclient.AnaliseClient;
import br.com.zup.proposta.propostas.httpclient.request.AnaliseRequest;
import br.com.zup.proposta.util.EncryptionUtil;
import br.com.zup.proposta.util.SHA256Digest;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "propostas")
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private UUID uuid = UUID.randomUUID();

    @NotBlank
    @Column(unique = true, nullable = false)
    private String documento;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String documentoHash;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @Email
    @NotBlank
    @Column(nullable = false)
    private String email;

    @Embedded
    private Endereco endereco;

    @NotNull
    @Positive
    @Column(nullable = false)
    private BigDecimal salario;

    @Enumerated(EnumType.STRING)
    private PropostaStatus status;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Cartao cartao;

    @Deprecated
    public Proposta() {
    }

    public Proposta(@NotBlank String documento, @NotBlank String nome, @Email @NotBlank String email, Endereco endereco, @NotNull @Positive BigDecimal salario, EncryptionUtil encryptionUtil) {
        this.documento = encryptionUtil.textEncrypt(documento);
        this.documentoHash = SHA256Digest.digest(documento);
        this.nome = nome;
        this.email = email;
        this.endereco = endereco;
        this.salario = salario;
    }

    public Long getId() {
        return id;
    }

    public void enviarParaAnalise(AnaliseClient client, EncryptionUtil encryptionUtil) {
        Assert.notNull(uuid, "A proposta não possui um UUID");
        var response = client.solicitarAnalise(new AnaliseRequest(this, encryptionUtil));
        status = response.getResultadoSolicitacao().getPropostaStatus();
    }

    public void consultarCartao(CartaoClient client) {
        CartaoResponse response = client.consultar(uuid);
        cartao = response.toModel();
    }

    public PropostaStatus getStatus() {
        return status;
    }

    public String getDocumento() {
        return documento;
    }

    public String getDecryptedDocumento(EncryptionUtil encryptionUtil) {
        return encryptionUtil.textDecrypt(documento);
    }

    public Cartao getCartao() {
        return cartao;
    }

    public String getNome() {
        return nome;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getDocumentoHash() {
        return documentoHash;
    }
}
