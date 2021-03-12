package br.com.zup.proposta.cartoes.repository;

import br.com.zup.proposta.cartoes.model.Cartao;
import org.springframework.data.repository.CrudRepository;

public interface CartaoRepository extends CrudRepository<Cartao, Long> {
}
