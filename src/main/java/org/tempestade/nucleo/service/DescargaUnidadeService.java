package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.DescargaUnidadeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.DescargaUnidade}.
 */
public interface DescargaUnidadeService {

    /**
     * Save a descargaUnidade.
     *
     * @param descargaUnidadeDTO the entity to save.
     * @return the persisted entity.
     */
    DescargaUnidadeDTO save(DescargaUnidadeDTO descargaUnidadeDTO);

    /**
     * Get all the descargaUnidades.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DescargaUnidadeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" descargaUnidade.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DescargaUnidadeDTO> findOne(Long id);

    /**
     * Delete the "id" descargaUnidade.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
