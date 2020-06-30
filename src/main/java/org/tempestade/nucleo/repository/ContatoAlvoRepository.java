package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.ContatoAlvo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ContatoAlvo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContatoAlvoRepository extends JpaRepository<ContatoAlvo, Long>, JpaSpecificationExecutor<ContatoAlvo> {
}
