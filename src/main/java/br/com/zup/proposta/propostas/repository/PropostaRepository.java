package br.com.zup.proposta.propostas.repository;

import br.com.zup.proposta.propostas.model.Proposta;
import br.com.zup.proposta.propostas.model.PropostaStatus;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PropostaRepository extends CrudRepository<Proposta, Long> {

    Optional<Proposta> findByDocumentoHash(String documento);

    Boolean existsByDocumentoHash(String documento);

    @Lock(LockModeType.PESSIMISTIC_READ)
    List<Proposta> findByCartaoIdNullAndStatusEquals(PropostaStatus status);

    Optional<Proposta> findByUuid(UUID propostaUuid);
}
