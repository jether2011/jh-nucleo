package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.TipoEnvioIntegrador;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TipoEnvioIntegrador entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoEnvioIntegradorRepository extends JpaRepository<TipoEnvioIntegrador, Long>, JpaSpecificationExecutor<TipoEnvioIntegrador> {
}
