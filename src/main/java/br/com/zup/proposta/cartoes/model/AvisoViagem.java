package br.com.zup.proposta.cartoes.model;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "avisos_viagem")
public class AvisoViagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    private Cartao cartao;

    @NotBlank
    private String destino;

    @NotNull
    @Future
    private LocalDate termino;

    private LocalDateTime criadoEm = LocalDateTime.now();

    private String ip;

    private String userAgent;

    @Deprecated
    public AvisoViagem() {
    }

    public AvisoViagem(@NotNull Cartao cartao, @NotBlank String destino, @NotNull @Future LocalDate termino, String ip, String userAgent) {
        this.cartao = cartao;
        this.destino = destino;
        this.termino = termino;
        this.ip = ip;
        this.userAgent = userAgent;
    }

    public String getDestino() {
        return destino;
    }

    public LocalDate getTermino() {
        return termino;
    }
}
