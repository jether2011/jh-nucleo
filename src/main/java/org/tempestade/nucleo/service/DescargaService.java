package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.DescargaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.Descarga}.
 */
public interface DescargaService {

    /**
     * Save a descarga.
     *
     * @param descargaDTO the entity to save.
     * @return the persisted entity.
     */
    DescargaDTO save(DescargaDTO descargaDTO);

    /**
     * Get all the descargas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DescargaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" descarga.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DescargaDTO> findOne(Long id);

    /**
     * Delete the "id" descarga.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
