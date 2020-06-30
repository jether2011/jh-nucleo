package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.DescargaUnidade;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DescargaUnidade entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DescargaUnidadeRepository extends JpaRepository<DescargaUnidade, Long>, JpaSpecificationExecutor<DescargaUnidade> {
}
