package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.VentoVmFaixa;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the VentoVmFaixa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VentoVmFaixaRepository extends JpaRepository<VentoVmFaixa, Long>, JpaSpecificationExecutor<VentoVmFaixa> {
}
