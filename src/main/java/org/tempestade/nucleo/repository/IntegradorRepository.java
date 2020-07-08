package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.Integrador;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Integrador entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IntegradorRepository extends JpaRepository<Integrador, Long> {
}
