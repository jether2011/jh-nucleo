package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.BoletimPrevVariavelMetDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.BoletimPrevVariavelMet}.
 */
public interface BoletimPrevVariavelMetService {

    /**
     * Save a boletimPrevVariavelMet.
     *
     * @param boletimPrevVariavelMetDTO the entity to save.
     * @return the persisted entity.
     */
    BoletimPrevVariavelMetDTO save(BoletimPrevVariavelMetDTO boletimPrevVariavelMetDTO);

    /**
     * Get all the boletimPrevVariavelMets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BoletimPrevVariavelMetDTO> findAll(Pageable pageable);


    /**
     * Get the "id" boletimPrevVariavelMet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BoletimPrevVariavelMetDTO> findOne(Long id);

    /**
     * Delete the "id" boletimPrevVariavelMet.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
