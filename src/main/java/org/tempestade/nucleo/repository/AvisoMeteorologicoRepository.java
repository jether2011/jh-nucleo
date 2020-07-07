package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.AvisoMeteorologico;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AvisoMeteorologico entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AvisoMeteorologicoRepository extends JpaRepository<AvisoMeteorologico, Long>, JpaSpecificationExecutor<AvisoMeteorologico> {
}
