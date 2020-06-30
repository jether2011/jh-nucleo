package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.PlanoLayerDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.PlanoLayer}.
 */
public interface PlanoLayerService {

    /**
     * Save a planoLayer.
     *
     * @param planoLayerDTO the entity to save.
     * @return the persisted entity.
     */
    PlanoLayerDTO save(PlanoLayerDTO planoLayerDTO);

    /**
     * Get all the planoLayers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PlanoLayerDTO> findAll(Pageable pageable);


    /**
     * Get the "id" planoLayer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PlanoLayerDTO> findOne(Long id);

    /**
     * Delete the "id" planoLayer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
