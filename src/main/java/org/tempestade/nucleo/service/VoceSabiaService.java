package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.VoceSabiaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.VoceSabia}.
 */
public interface VoceSabiaService {

    /**
     * Save a voceSabia.
     *
     * @param voceSabiaDTO the entity to save.
     * @return the persisted entity.
     */
    VoceSabiaDTO save(VoceSabiaDTO voceSabiaDTO);

    /**
     * Get all the voceSabias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VoceSabiaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" voceSabia.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VoceSabiaDTO> findOne(Long id);

    /**
     * Delete the "id" voceSabia.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
