package br.com.zup.proposta.cartoes.repository;

import br.com.zup.proposta.cartoes.model.Carteira;
import br.com.zup.proposta.cartoes.model.TipoCarteira;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface CarteiraRepository extends CrudRepository<Carteira, Long> {

    Optional<Carteira> findByUuid(UUID carteiraUuid);

    Boolean existsByTipoCarteira(TipoCarteira tipoCarteira);
}
