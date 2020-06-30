package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.AvisoTipo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AvisoTipo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AvisoTipoRepository extends JpaRepository<AvisoTipo, Long>, JpaSpecificationExecutor<AvisoTipo> {
}
