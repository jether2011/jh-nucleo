package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.TempestadeProbabilidade;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TempestadeProbabilidade entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TempestadeProbabilidadeRepository extends JpaRepository<TempestadeProbabilidade, Long> {
}
