package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.RecursoTipoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.RecursoTipo}.
 */
public interface RecursoTipoService {

    /**
     * Save a recursoTipo.
     *
     * @param recursoTipoDTO the entity to save.
     * @return the persisted entity.
     */
    RecursoTipoDTO save(RecursoTipoDTO recursoTipoDTO);

    /**
     * Get all the recursoTipos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RecursoTipoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" recursoTipo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RecursoTipoDTO> findOne(Long id);

    /**
     * Delete the "id" recursoTipo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
