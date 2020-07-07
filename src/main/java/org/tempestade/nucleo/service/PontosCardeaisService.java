package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.PontosCardeaisDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.PontosCardeais}.
 */
public interface PontosCardeaisService {

    /**
     * Save a pontosCardeais.
     *
     * @param pontosCardeaisDTO the entity to save.
     * @return the persisted entity.
     */
    PontosCardeaisDTO save(PontosCardeaisDTO pontosCardeaisDTO);

    /**
     * Get all the pontosCardeais.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PontosCardeaisDTO> findAll(Pageable pageable);


    /**
     * Get the "id" pontosCardeais.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PontosCardeaisDTO> findOne(Long id);

    /**
     * Delete the "id" pontosCardeais.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
