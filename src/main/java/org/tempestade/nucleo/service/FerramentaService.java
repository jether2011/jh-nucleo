package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.FerramentaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.Ferramenta}.
 */
public interface FerramentaService {

    /**
     * Save a ferramenta.
     *
     * @param ferramentaDTO the entity to save.
     * @return the persisted entity.
     */
    FerramentaDTO save(FerramentaDTO ferramentaDTO);

    /**
     * Get all the ferramentas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FerramentaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" ferramenta.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FerramentaDTO> findOne(Long id);

    /**
     * Delete the "id" ferramenta.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
