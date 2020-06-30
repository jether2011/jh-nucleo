package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.LayerDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.Layer}.
 */
public interface LayerService {

    /**
     * Save a layer.
     *
     * @param layerDTO the entity to save.
     * @return the persisted entity.
     */
    LayerDTO save(LayerDTO layerDTO);

    /**
     * Get all the layers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LayerDTO> findAll(Pageable pageable);


    /**
     * Get the "id" layer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LayerDTO> findOne(Long id);

    /**
     * Delete the "id" layer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
