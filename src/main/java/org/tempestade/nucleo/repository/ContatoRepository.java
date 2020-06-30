package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.Contato;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Contato entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContatoRepository extends JpaRepository<Contato, Long>, JpaSpecificationExecutor<Contato> {
}
