package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.VentoVmFaixaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.VentoVmFaixa}.
 */
public interface VentoVmFaixaService {

    /**
     * Save a ventoVmFaixa.
     *
     * @param ventoVmFaixaDTO the entity to save.
     * @return the persisted entity.
     */
    VentoVmFaixaDTO save(VentoVmFaixaDTO ventoVmFaixaDTO);

    /**
     * Get all the ventoVmFaixas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VentoVmFaixaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" ventoVmFaixa.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VentoVmFaixaDTO> findOne(Long id);

    /**
     * Delete the "id" ventoVmFaixa.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
