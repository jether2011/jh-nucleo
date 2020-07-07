package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.PrevisaoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.Previsao}.
 */
public interface PrevisaoService {

    /**
     * Save a previsao.
     *
     * @param previsaoDTO the entity to save.
     * @return the persisted entity.
     */
    PrevisaoDTO save(PrevisaoDTO previsaoDTO);

    /**
     * Get all the previsaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PrevisaoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" previsao.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PrevisaoDTO> findOne(Long id);

    /**
     * Delete the "id" previsao.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
