package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.AlertaFerramenta;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AlertaFerramenta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlertaFerramentaRepository extends JpaRepository<AlertaFerramenta, Long> {
}
