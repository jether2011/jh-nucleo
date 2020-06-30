package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.BoletimPrevisao;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BoletimPrevisao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BoletimPrevisaoRepository extends JpaRepository<BoletimPrevisao, Long>, JpaSpecificationExecutor<BoletimPrevisao> {
}
