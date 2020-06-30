package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.LayerService;
import org.tempestade.nucleo.domain.Layer;
import org.tempestade.nucleo.repository.LayerRepository;
import org.tempestade.nucleo.service.dto.LayerDTO;
import org.tempestade.nucleo.service.mapper.LayerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Layer}.
 */
@Service
@Transactional
public class LayerServiceImpl implements LayerService {

    private final Logger log = LoggerFactory.getLogger(LayerServiceImpl.class);

    private final LayerRepository layerRepository;

    private final LayerMapper layerMapper;

    public LayerServiceImpl(LayerRepository layerRepository, LayerMapper layerMapper) {
        this.layerRepository = layerRepository;
        this.layerMapper = layerMapper;
    }

    @Override
    public LayerDTO save(LayerDTO layerDTO) {
        log.debug("Request to save Layer : {}", layerDTO);
        Layer layer = layerMapper.toEntity(layerDTO);
        layer = layerRepository.save(layer);
        return layerMapper.toDto(layer);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LayerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Layers");
        return layerRepository.findAll(pageable)
            .map(layerMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<LayerDTO> findOne(Long id) {
        log.debug("Request to get Layer : {}", id);
        return layerRepository.findById(id)
            .map(layerMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Layer : {}", id);
        layerRepository.deleteById(id);
    }
}
