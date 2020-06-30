package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.AcumuladoChuvaFaixaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.AcumuladoChuvaFaixa}.
 */
public interface AcumuladoChuvaFaixaService {

    /**
     * Save a acumuladoChuvaFaixa.
     *
     * @param acumuladoChuvaFaixaDTO the entity to save.
     * @return the persisted entity.
     */
    AcumuladoChuvaFaixaDTO save(AcumuladoChuvaFaixaDTO acumuladoChuvaFaixaDTO);

    /**
     * Get all the acumuladoChuvaFaixas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AcumuladoChuvaFaixaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" acumuladoChuvaFaixa.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AcumuladoChuvaFaixaDTO> findOne(Long id);

    /**
     * Delete the "id" acumuladoChuvaFaixa.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
