package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.BoletimPrevVariavelMet;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BoletimPrevVariavelMet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BoletimPrevVariavelMetRepository extends JpaRepository<BoletimPrevVariavelMet, Long> {
}
