package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.JornadaTrabalho;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the JornadaTrabalho entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JornadaTrabalhoRepository extends JpaRepository<JornadaTrabalho, Long>, JpaSpecificationExecutor<JornadaTrabalho> {
}
