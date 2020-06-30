package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.Alerta;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Alerta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Long>, JpaSpecificationExecutor<Alerta> {
}
