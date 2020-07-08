package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.TempestadeNivelDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.TempestadeNivel}.
 */
public interface TempestadeNivelService {

    /**
     * Save a tempestadeNivel.
     *
     * @param tempestadeNivelDTO the entity to save.
     * @return the persisted entity.
     */
    TempestadeNivelDTO save(TempestadeNivelDTO tempestadeNivelDTO);

    /**
     * Get all the tempestadeNivels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TempestadeNivelDTO> findAll(Pageable pageable);


    /**
     * Get the "id" tempestadeNivel.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TempestadeNivelDTO> findOne(Long id);

    /**
     * Delete the "id" tempestadeNivel.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
