package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.PlanoRecursoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.PlanoRecurso}.
 */
public interface PlanoRecursoService {

    /**
     * Save a planoRecurso.
     *
     * @param planoRecursoDTO the entity to save.
     * @return the persisted entity.
     */
    PlanoRecursoDTO save(PlanoRecursoDTO planoRecursoDTO);

    /**
     * Get all the planoRecursos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PlanoRecursoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" planoRecurso.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PlanoRecursoDTO> findOne(Long id);

    /**
     * Delete the "id" planoRecurso.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
