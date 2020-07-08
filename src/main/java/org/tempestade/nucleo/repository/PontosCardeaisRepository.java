package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.PontosCardeais;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PontosCardeais entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PontosCardeaisRepository extends JpaRepository<PontosCardeais, Long> {
}
