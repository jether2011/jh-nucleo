package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.MeteogramaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.Meteograma}.
 */
public interface MeteogramaService {

    /**
     * Save a meteograma.
     *
     * @param meteogramaDTO the entity to save.
     * @return the persisted entity.
     */
    MeteogramaDTO save(MeteogramaDTO meteogramaDTO);

    /**
     * Get all the meteogramas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MeteogramaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" meteograma.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MeteogramaDTO> findOne(Long id);

    /**
     * Delete the "id" meteograma.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
