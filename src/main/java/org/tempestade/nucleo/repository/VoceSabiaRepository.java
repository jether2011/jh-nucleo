package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.VoceSabia;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the VoceSabia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VoceSabiaRepository extends JpaRepository<VoceSabia, Long>, JpaSpecificationExecutor<VoceSabia> {
}
