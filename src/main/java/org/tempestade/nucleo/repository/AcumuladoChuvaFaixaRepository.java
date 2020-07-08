package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.AcumuladoChuvaFaixa;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AcumuladoChuvaFaixa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AcumuladoChuvaFaixaRepository extends JpaRepository<AcumuladoChuvaFaixa, Long> {
}
