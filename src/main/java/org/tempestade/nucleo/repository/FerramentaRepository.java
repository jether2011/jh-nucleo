package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.Ferramenta;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Ferramenta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FerramentaRepository extends JpaRepository<Ferramenta, Long> {
}
