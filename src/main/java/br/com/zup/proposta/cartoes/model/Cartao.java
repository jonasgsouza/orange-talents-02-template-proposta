package br.com.zup.proposta.cartoes.model;

import br.com.zup.proposta.cartoes.httpclient.CartaoClient;
import br.com.zup.proposta.cartoes.httpclient.request.AvisoRequest;
import br.com.zup.proposta.cartoes.httpclient.request.BloqueioRequest;
import br.com.zup.proposta.cartoes.httpclient.request.CarteiraRequest;
import br.com.zup.proposta.cartoes.httpclient.response.BloqueioResponse;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "cartoes")
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private UUID uuid = UUID.randomUUID();

    @NotBlank
    @Column(nullable = false)
    private String numeroCartao;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime emitidoEm;

    @NotBlank
    @Column(nullable = false)
    private String titular;

    @Enumerated(EnumType.STRING)
    private CartaoStatus status = CartaoStatus.OK;

    @OneToMany(mappedBy = "cartao", cascade = CascadeType.PERSIST)
    private List<Bloqueio> bloqueios = new ArrayList<>();

    @OneToMany(mappedBy = "cartao", cascade = CascadeType.PERSIST)
    private List<AvisoViagem> avisos = new ArrayList<>();

    @OneToMany(mappedBy = "cartao", cascade = CascadeType.PERSIST)
    private Set<Carteira> carteiras = new HashSet<>();

    @Deprecated
    public Cartao() {
    }

    public Cartao(String numeroCartao, LocalDateTime emitidoEm, String titular) {
        this.numeroCartao = numeroCartao;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
    }

    public Boolean bloqueado() {
        return status == CartaoStatus.BLOQUEADO;
    }

    public void bloquear(Bloqueio bloqueio, CartaoClient client) {
        if (bloqueado())
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "O cartão já está bloqueado");
        BloqueioResponse response = client.bloquear(numeroCartao, new BloqueioRequest("propostas"));
        status = response.getResultado().getCartaoStatus();
        bloqueios.add(bloqueio);
    }

    public void registrarAvisoViagem(AvisoViagem aviso, CartaoClient client) {
        client.registrarAviso(numeroCartao, new AvisoRequest(aviso));
        avisos.add(aviso);
    }

    public void adicionarCarteira(Carteira carteira, CartaoClient client) {
        client.adicionarCarteira(numeroCartao, new CarteiraRequest(carteira));
        Assert.isTrue(carteiras.add(carteira), "Carteira já foi associada ao cartão");
    }

    public UUID getUuid() {
        return uuid;
    }

}
