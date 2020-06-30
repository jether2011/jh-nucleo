package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.VariavelMeteorologica;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the VariavelMeteorologica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VariavelMeteorologicaRepository extends JpaRepository<VariavelMeteorologica, Long>, JpaSpecificationExecutor<VariavelMeteorologica> {
}
