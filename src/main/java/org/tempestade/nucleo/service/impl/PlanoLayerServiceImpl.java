package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.PlanoLayerService;
import org.tempestade.nucleo.domain.PlanoLayer;
import org.tempestade.nucleo.repository.PlanoLayerRepository;
import org.tempestade.nucleo.service.dto.PlanoLayerDTO;
import org.tempestade.nucleo.service.mapper.PlanoLayerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PlanoLayer}.
 */
@Service
@Transactional
public class PlanoLayerServiceImpl implements PlanoLayerService {

    private final Logger log = LoggerFactory.getLogger(PlanoLayerServiceImpl.class);

    private final PlanoLayerRepository planoLayerRepository;

    private final PlanoLayerMapper planoLayerMapper;

    public PlanoLayerServiceImpl(PlanoLayerRepository planoLayerRepository, PlanoLayerMapper planoLayerMapper) {
        this.planoLayerRepository = planoLayerRepository;
        this.planoLayerMapper = planoLayerMapper;
    }

    @Override
    public PlanoLayerDTO save(PlanoLayerDTO planoLayerDTO) {
        log.debug("Request to save PlanoLayer : {}", planoLayerDTO);
        PlanoLayer planoLayer = planoLayerMapper.toEntity(planoLayerDTO);
        planoLayer = planoLayerRepository.save(planoLayer);
        return planoLayerMapper.toDto(planoLayer);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PlanoLayerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PlanoLayers");
        return planoLayerRepository.findAll(pageable)
            .map(planoLayerMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PlanoLayerDTO> findOne(Long id) {
        log.debug("Request to get PlanoLayer : {}", id);
        return planoLayerRepository.findById(id)
            .map(planoLayerMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PlanoLayer : {}", id);
        planoLayerRepository.deleteById(id);
    }
}
