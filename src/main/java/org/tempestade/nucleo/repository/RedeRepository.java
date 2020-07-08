package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.Rede;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Rede entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RedeRepository extends JpaRepository<Rede, Long> {
}
