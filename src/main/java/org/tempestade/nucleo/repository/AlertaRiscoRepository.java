package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.AlertaRisco;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AlertaRisco entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlertaRiscoRepository extends JpaRepository<AlertaRisco, Long>, JpaSpecificationExecutor<AlertaRisco> {
}
