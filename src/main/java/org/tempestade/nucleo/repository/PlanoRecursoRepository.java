package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.PlanoRecurso;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PlanoRecurso entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlanoRecursoRepository extends JpaRepository<PlanoRecurso, Long>, JpaSpecificationExecutor<PlanoRecurso> {
}
