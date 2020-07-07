package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.RecursoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.Recurso}.
 */
public interface RecursoService {

    /**
     * Save a recurso.
     *
     * @param recursoDTO the entity to save.
     * @return the persisted entity.
     */
    RecursoDTO save(RecursoDTO recursoDTO);

    /**
     * Get all the recursos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RecursoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" recurso.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RecursoDTO> findOne(Long id);

    /**
     * Delete the "id" recurso.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
