package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.RecursoTemplate;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the RecursoTemplate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecursoTemplateRepository extends JpaRepository<RecursoTemplate, Long> {
}
