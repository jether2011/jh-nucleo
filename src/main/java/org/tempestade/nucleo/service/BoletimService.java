package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.BoletimDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.Boletim}.
 */
public interface BoletimService {

    /**
     * Save a boletim.
     *
     * @param boletimDTO the entity to save.
     * @return the persisted entity.
     */
    BoletimDTO save(BoletimDTO boletimDTO);

    /**
     * Get all the boletims.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BoletimDTO> findAll(Pageable pageable);


    /**
     * Get the "id" boletim.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BoletimDTO> findOne(Long id);

    /**
     * Delete the "id" boletim.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
