package br.com.zup.proposta.cartoes.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bloqueios")
public class Bloqueio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Cartao cartao;

    @Column(nullable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();

    @Column(nullable = false)
    private String ip;

    @Column(nullable = false)
    private String userAgent;

    @Deprecated
    public Bloqueio() {
    }

    public Bloqueio(Cartao cartao, String ip, String userAgent) {
        this.cartao = cartao;
        this.ip = ip;
        this.userAgent = userAgent;
    }
}
