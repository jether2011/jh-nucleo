package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.Informativo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Informativo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InformativoRepository extends JpaRepository<Informativo, Long> {
}
