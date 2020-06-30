package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.AlertaRiscoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.AlertaRisco}.
 */
public interface AlertaRiscoService {

    /**
     * Save a alertaRisco.
     *
     * @param alertaRiscoDTO the entity to save.
     * @return the persisted entity.
     */
    AlertaRiscoDTO save(AlertaRiscoDTO alertaRiscoDTO);

    /**
     * Get all the alertaRiscos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AlertaRiscoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" alertaRisco.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AlertaRiscoDTO> findOne(Long id);

    /**
     * Delete the "id" alertaRisco.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
