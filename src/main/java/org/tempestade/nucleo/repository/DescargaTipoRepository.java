package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.DescargaTipo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DescargaTipo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DescargaTipoRepository extends JpaRepository<DescargaTipo, Long> {
}
