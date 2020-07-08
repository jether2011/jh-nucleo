package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.ConsolidacaoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.Consolidacao}.
 */
public interface ConsolidacaoService {

    /**
     * Save a consolidacao.
     *
     * @param consolidacaoDTO the entity to save.
     * @return the persisted entity.
     */
    ConsolidacaoDTO save(ConsolidacaoDTO consolidacaoDTO);

    /**
     * Get all the consolidacaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ConsolidacaoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" consolidacao.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ConsolidacaoDTO> findOne(Long id);

    /**
     * Delete the "id" consolidacao.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
