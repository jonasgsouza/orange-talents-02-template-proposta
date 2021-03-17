package br.com.zup.proposta.cartoes.repository;

import br.com.zup.proposta.cartoes.model.Cartao;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartaoRepository extends CrudRepository<Cartao, Long> {

    Optional<Cartao> findByUuid(UUID uuid);
}
