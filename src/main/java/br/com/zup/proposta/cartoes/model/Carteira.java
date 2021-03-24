package br.com.zup.proposta.cartoes.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "carteiras")
public class Carteira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private UUID uuid = UUID.randomUUID();

    @NotNull
    @ManyToOne(optional = false)
    private Cartao cartao;

    @NotBlank
    @Email
    @Column(nullable = false)
    private String email;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoCarteira tipoCarteira;

    @Deprecated
    public Carteira() {
    }

    public Carteira(@NotNull Cartao cartao, @NotBlank @Email String email, @NotNull TipoCarteira tipoCarteira) {
        this.email = email;
        this.cartao = cartao;
        this.tipoCarteira = tipoCarteira;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carteira carteira1 = (Carteira) o;
        return tipoCarteira.equals(carteira1.tipoCarteira);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipoCarteira);
    }

    public TipoCarteira getTipoCarteira() {
        return tipoCarteira;
    }

    public String getEmail() {
        return email;
    }

    public UUID getUuid() {
        return uuid;
    }

}
