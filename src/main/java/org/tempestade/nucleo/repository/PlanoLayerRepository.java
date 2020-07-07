package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.PlanoLayer;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PlanoLayer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlanoLayerRepository extends JpaRepository<PlanoLayer, Long>, JpaSpecificationExecutor<PlanoLayer> {
}
