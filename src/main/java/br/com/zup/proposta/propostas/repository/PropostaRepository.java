package br.com.zup.proposta.propostas.repository;

import br.com.zup.proposta.propostas.model.Proposta;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.LockModeType;
import java.util.List;

public interface PropostaRepository extends CrudRepository<Proposta, Long> {

    Boolean existsByDocumento(String documento);

    @Lock(LockModeType.PESSIMISTIC_READ)
    List<Proposta> findByNumeroCartaoNull();

}
