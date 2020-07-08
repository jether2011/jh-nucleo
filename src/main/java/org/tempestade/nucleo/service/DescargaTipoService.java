package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.DescargaTipoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.DescargaTipo}.
 */
public interface DescargaTipoService {

    /**
     * Save a descargaTipo.
     *
     * @param descargaTipoDTO the entity to save.
     * @return the persisted entity.
     */
    DescargaTipoDTO save(DescargaTipoDTO descargaTipoDTO);

    /**
     * Get all the descargaTipos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DescargaTipoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" descargaTipo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DescargaTipoDTO> findOne(Long id);

    /**
     * Delete the "id" descargaTipo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
