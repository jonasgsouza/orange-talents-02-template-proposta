package br.com.zup.proposta.cartoes.repository;

import br.com.zup.proposta.cartoes.model.Biometria;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface BiometriaRepository extends CrudRepository<Biometria, Long> {
    Optional<Biometria> findByUuid(UUID biometriaUuid);
}
