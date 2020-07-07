package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.CondicaoTempo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CondicaoTempo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CondicaoTempoRepository extends JpaRepository<CondicaoTempo, Long>, JpaSpecificationExecutor<CondicaoTempo> {
}
