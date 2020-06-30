package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.BoletimPrevObsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.BoletimPrevObs}.
 */
public interface BoletimPrevObsService {

    /**
     * Save a boletimPrevObs.
     *
     * @param boletimPrevObsDTO the entity to save.
     * @return the persisted entity.
     */
    BoletimPrevObsDTO save(BoletimPrevObsDTO boletimPrevObsDTO);

    /**
     * Get all the boletimPrevObs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BoletimPrevObsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" boletimPrevObs.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BoletimPrevObsDTO> findOne(Long id);

    /**
     * Delete the "id" boletimPrevObs.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
