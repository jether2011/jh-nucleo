package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.TipoEnvio;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TipoEnvio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoEnvioRepository extends JpaRepository<TipoEnvio, Long>, JpaSpecificationExecutor<TipoEnvio> {
}
