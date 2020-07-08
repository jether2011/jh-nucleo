package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.TempestadeNivel;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TempestadeNivel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TempestadeNivelRepository extends JpaRepository<TempestadeNivel, Long> {
}
