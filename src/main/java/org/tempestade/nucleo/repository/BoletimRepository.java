package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.Boletim;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Boletim entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BoletimRepository extends JpaRepository<Boletim, Long>, JpaSpecificationExecutor<Boletim> {
}
