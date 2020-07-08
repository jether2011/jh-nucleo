package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.AlvoBloqueio;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AlvoBloqueio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlvoBloqueioRepository extends JpaRepository<AlvoBloqueio, Long> {
}
