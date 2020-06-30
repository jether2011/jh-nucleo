package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.Descarga;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Descarga entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DescargaRepository extends JpaRepository<Descarga, Long>, JpaSpecificationExecutor<Descarga> {
}
