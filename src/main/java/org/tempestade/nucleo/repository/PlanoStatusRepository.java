package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.PlanoStatus;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PlanoStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlanoStatusRepository extends JpaRepository<PlanoStatus, Long>, JpaSpecificationExecutor<PlanoStatus> {
}
