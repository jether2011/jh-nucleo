package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.DiaSemana;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DiaSemana entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiaSemanaRepository extends JpaRepository<DiaSemana, Long> {
}
