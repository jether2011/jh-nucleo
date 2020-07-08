package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.UmidadeAr;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UmidadeAr entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UmidadeArRepository extends JpaRepository<UmidadeAr, Long> {
}
