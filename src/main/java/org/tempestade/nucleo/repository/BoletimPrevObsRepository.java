package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.BoletimPrevObs;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BoletimPrevObs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BoletimPrevObsRepository extends JpaRepository<BoletimPrevObs, Long> {
}
