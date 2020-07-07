package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.TempestadeNivelService;
import org.tempestade.nucleo.domain.TempestadeNivel;
import org.tempestade.nucleo.repository.TempestadeNivelRepository;
import org.tempestade.nucleo.service.dto.TempestadeNivelDTO;
import org.tempestade.nucleo.service.mapper.TempestadeNivelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TempestadeNivel}.
 */
@Service
@Transactional
public class TempestadeNivelServiceImpl implements TempestadeNivelService {

    private final Logger log = LoggerFactory.getLogger(TempestadeNivelServiceImpl.class);

    private final TempestadeNivelRepository tempestadeNivelRepository;

    private final TempestadeNivelMapper tempestadeNivelMapper;

    public TempestadeNivelServiceImpl(TempestadeNivelRepository tempestadeNivelRepository, TempestadeNivelMapper tempestadeNivelMapper) {
        this.tempestadeNivelRepository = tempestadeNivelRepository;
        this.tempestadeNivelMapper = tempestadeNivelMapper;
    }

    @Override
    public TempestadeNivelDTO save(TempestadeNivelDTO tempestadeNivelDTO) {
        log.debug("Request to save TempestadeNivel : {}", tempestadeNivelDTO);
        TempestadeNivel tempestadeNivel = tempestadeNivelMapper.toEntity(tempestadeNivelDTO);
        tempestadeNivel = tempestadeNivelRepository.save(tempestadeNivel);
        return tempestadeNivelMapper.toDto(tempestadeNivel);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TempestadeNivelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TempestadeNivels");
        return tempestadeNivelRepository.findAll(pageable)
            .map(tempestadeNivelMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TempestadeNivelDTO> findOne(Long id) {
        log.debug("Request to get TempestadeNivel : {}", id);
        return tempestadeNivelRepository.findById(id)
            .map(tempestadeNivelMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TempestadeNivel : {}", id);
        tempestadeNivelRepository.deleteById(id);
    }
}
