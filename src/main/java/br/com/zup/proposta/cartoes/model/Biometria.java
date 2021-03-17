package br.com.zup.proposta.cartoes.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "biometrias")
public class Biometria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private UUID uuid = UUID.randomUUID();

    @ManyToOne
    private Cartao cartao;

    @Lob
    private String fingerprint;

    @Deprecated
    public Biometria() {
    }

    public Biometria(Cartao cartao, String fingerprint) {
        this.cartao = cartao;
        this.fingerprint = fingerprint;
    }

    public Long getId() {
        return id;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public UUID getUuid() {
        return uuid;
    }
}
