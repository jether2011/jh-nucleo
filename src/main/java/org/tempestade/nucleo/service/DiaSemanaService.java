package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.DiaSemanaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.DiaSemana}.
 */
public interface DiaSemanaService {

    /**
     * Save a diaSemana.
     *
     * @param diaSemanaDTO the entity to save.
     * @return the persisted entity.
     */
    DiaSemanaDTO save(DiaSemanaDTO diaSemanaDTO);

    /**
     * Get all the diaSemanas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DiaSemanaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" diaSemana.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DiaSemanaDTO> findOne(Long id);

    /**
     * Delete the "id" diaSemana.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
