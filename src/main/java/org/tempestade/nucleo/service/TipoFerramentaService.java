package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.TipoFerramentaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.TipoFerramenta}.
 */
public interface TipoFerramentaService {

    /**
     * Save a tipoFerramenta.
     *
     * @param tipoFerramentaDTO the entity to save.
     * @return the persisted entity.
     */
    TipoFerramentaDTO save(TipoFerramentaDTO tipoFerramentaDTO);

    /**
     * Get all the tipoFerramentas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TipoFerramentaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" tipoFerramenta.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TipoFerramentaDTO> findOne(Long id);

    /**
     * Delete the "id" tipoFerramenta.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
