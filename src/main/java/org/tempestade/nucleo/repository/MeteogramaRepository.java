package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.Meteograma;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Meteograma entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MeteogramaRepository extends JpaRepository<Meteograma, Long> {
}
