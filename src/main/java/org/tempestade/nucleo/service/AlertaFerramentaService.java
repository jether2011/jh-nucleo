package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.AlertaFerramentaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.AlertaFerramenta}.
 */
public interface AlertaFerramentaService {

    /**
     * Save a alertaFerramenta.
     *
     * @param alertaFerramentaDTO the entity to save.
     * @return the persisted entity.
     */
    AlertaFerramentaDTO save(AlertaFerramentaDTO alertaFerramentaDTO);

    /**
     * Get all the alertaFerramentas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AlertaFerramentaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" alertaFerramenta.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AlertaFerramentaDTO> findOne(Long id);

    /**
     * Delete the "id" alertaFerramenta.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
