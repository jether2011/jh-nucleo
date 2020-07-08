package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.TipoFerramenta;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TipoFerramenta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoFerramentaRepository extends JpaRepository<TipoFerramenta, Long> {
}
