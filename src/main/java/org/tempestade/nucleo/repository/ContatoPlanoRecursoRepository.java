package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.ContatoPlanoRecurso;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ContatoPlanoRecurso entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContatoPlanoRecursoRepository extends JpaRepository<ContatoPlanoRecurso, Long> {
}
