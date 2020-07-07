package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.Consolidacao;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Consolidacao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConsolidacaoRepository extends JpaRepository<Consolidacao, Long>, JpaSpecificationExecutor<Consolidacao> {
}
