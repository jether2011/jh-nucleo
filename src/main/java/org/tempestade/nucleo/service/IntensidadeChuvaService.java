package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.IntensidadeChuvaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.IntensidadeChuva}.
 */
public interface IntensidadeChuvaService {

    /**
     * Save a intensidadeChuva.
     *
     * @param intensidadeChuvaDTO the entity to save.
     * @return the persisted entity.
     */
    IntensidadeChuvaDTO save(IntensidadeChuvaDTO intensidadeChuvaDTO);

    /**
     * Get all the intensidadeChuvas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<IntensidadeChuvaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" intensidadeChuva.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IntensidadeChuvaDTO> findOne(Long id);

    /**
     * Delete the "id" intensidadeChuva.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
