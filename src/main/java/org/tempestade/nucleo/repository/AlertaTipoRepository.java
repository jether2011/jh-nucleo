package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.AlertaTipo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AlertaTipo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlertaTipoRepository extends JpaRepository<AlertaTipo, Long>, JpaSpecificationExecutor<AlertaTipo> {
}
