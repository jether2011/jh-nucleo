package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.IntensidadeChuva;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the IntensidadeChuva entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IntensidadeChuvaRepository extends JpaRepository<IntensidadeChuva, Long>, JpaSpecificationExecutor<IntensidadeChuva> {
}
