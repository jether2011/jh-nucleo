package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.RecursoTipo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the RecursoTipo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecursoTipoRepository extends JpaRepository<RecursoTipo, Long>, JpaSpecificationExecutor<RecursoTipo> {
}
