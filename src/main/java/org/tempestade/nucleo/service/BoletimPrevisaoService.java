package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.BoletimPrevisaoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.BoletimPrevisao}.
 */
public interface BoletimPrevisaoService {

    /**
     * Save a boletimPrevisao.
     *
     * @param boletimPrevisaoDTO the entity to save.
     * @return the persisted entity.
     */
    BoletimPrevisaoDTO save(BoletimPrevisaoDTO boletimPrevisaoDTO);

    /**
     * Get all the boletimPrevisaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BoletimPrevisaoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" boletimPrevisao.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BoletimPrevisaoDTO> findOne(Long id);

    /**
     * Delete the "id" boletimPrevisao.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
