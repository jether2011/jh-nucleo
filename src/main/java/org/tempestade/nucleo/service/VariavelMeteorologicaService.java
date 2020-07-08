package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.VariavelMeteorologicaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.VariavelMeteorologica}.
 */
public interface VariavelMeteorologicaService {

    /**
     * Save a variavelMeteorologica.
     *
     * @param variavelMeteorologicaDTO the entity to save.
     * @return the persisted entity.
     */
    VariavelMeteorologicaDTO save(VariavelMeteorologicaDTO variavelMeteorologicaDTO);

    /**
     * Get all the variavelMeteorologicas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VariavelMeteorologicaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" variavelMeteorologica.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VariavelMeteorologicaDTO> findOne(Long id);

    /**
     * Delete the "id" variavelMeteorologica.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
