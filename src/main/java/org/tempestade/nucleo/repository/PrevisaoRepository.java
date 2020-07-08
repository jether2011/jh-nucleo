package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.Previsao;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Previsao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrevisaoRepository extends JpaRepository<Previsao, Long> {
}
