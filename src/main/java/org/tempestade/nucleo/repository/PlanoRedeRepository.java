package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.PlanoRede;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PlanoRede entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlanoRedeRepository extends JpaRepository<PlanoRede, Long> {
}
