package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.Alvo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Alvo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlvoRepository extends JpaRepository<Alvo, Long> {
}
