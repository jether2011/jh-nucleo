package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.PlanoRecursoTipoEnvio;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PlanoRecursoTipoEnvio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlanoRecursoTipoEnvioRepository extends JpaRepository<PlanoRecursoTipoEnvio, Long>, JpaSpecificationExecutor<PlanoRecursoTipoEnvio> {
}
