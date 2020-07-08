package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.TempestadeProbabilidadeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.TempestadeProbabilidade}.
 */
public interface TempestadeProbabilidadeService {

    /**
     * Save a tempestadeProbabilidade.
     *
     * @param tempestadeProbabilidadeDTO the entity to save.
     * @return the persisted entity.
     */
    TempestadeProbabilidadeDTO save(TempestadeProbabilidadeDTO tempestadeProbabilidadeDTO);

    /**
     * Get all the tempestadeProbabilidades.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TempestadeProbabilidadeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" tempestadeProbabilidade.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TempestadeProbabilidadeDTO> findOne(Long id);

    /**
     * Delete the "id" tempestadeProbabilidade.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
