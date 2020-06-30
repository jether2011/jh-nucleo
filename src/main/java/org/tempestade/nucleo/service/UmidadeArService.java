package org.tempestade.nucleo.service;

import org.tempestade.nucleo.service.dto.UmidadeArDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link org.tempestade.nucleo.domain.UmidadeAr}.
 */
public interface UmidadeArService {

    /**
     * Save a umidadeAr.
     *
     * @param umidadeArDTO the entity to save.
     * @return the persisted entity.
     */
    UmidadeArDTO save(UmidadeArDTO umidadeArDTO);

    /**
     * Get all the umidadeArs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UmidadeArDTO> findAll(Pageable pageable);


    /**
     * Get the "id" umidadeAr.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UmidadeArDTO> findOne(Long id);

    /**
     * Delete the "id" umidadeAr.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
