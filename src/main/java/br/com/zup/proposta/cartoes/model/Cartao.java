package br.com.zup.proposta.cartoes.model;

import br.com.zup.proposta.cartoes.httpclient.CartaoClient;
import br.com.zup.proposta.cartoes.httpclient.request.AvisoRequest;
import br.com.zup.proposta.cartoes.httpclient.request.BloqueioRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cartoes")
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    private CartaoStatus status = CartaoStatus.LIBERADO;

    @OneToMany(mappedBy = "cartao", cascade = CascadeType.PERSIST)
    private List<Bloqueio> bloqueios = new ArrayList<>();

    @OneToMany(mappedBy = "cartao", cascade = CascadeType.PERSIST)
    private List<AvisoViagem> avisos = new ArrayList<>();

    @Deprecated
    public Cartao() {
    }

    public Cartao(String numeroCartao, LocalDateTime emitidoEm, String titular) {
        this.numeroCartao = numeroCartao;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
    }

    public Long getId() {
        return id;
    }

    public Boolean bloqueado() {
        return status == CartaoStatus.BLOQUEADO;
    }

    public void bloquear(Bloqueio bloqueio, CartaoClient client) {
        if (bloqueado())
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "O cartão já está bloqueado");
        var response = client.bloquear(numeroCartao, new BloqueioRequest("propostas"));
        status = response.getResultado().getCartaoStatus();
        bloqueios.add(bloqueio);
    }

    public void registrarAvisoViagem(AvisoViagem aviso, CartaoClient client) {
        client.registrarAviso(numeroCartao, new AvisoRequest(aviso));
        avisos.add(aviso);
    }


}
