package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.AlertaTipoService;
import org.tempestade.nucleo.domain.AlertaTipo;
import org.tempestade.nucleo.repository.AlertaTipoRepository;
import org.tempestade.nucleo.service.dto.AlertaTipoDTO;
import org.tempestade.nucleo.service.mapper.AlertaTipoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AlertaTipo}.
 */
@Service
@Transactional
public class AlertaTipoServiceImpl implements AlertaTipoService {

    private final Logger log = LoggerFactory.getLogger(AlertaTipoServiceImpl.class);

    private final AlertaTipoRepository alertaTipoRepository;

    private final AlertaTipoMapper alertaTipoMapper;

    public AlertaTipoServiceImpl(AlertaTipoRepository alertaTipoRepository, AlertaTipoMapper alertaTipoMapper) {
        this.alertaTipoRepository = alertaTipoRepository;
        this.alertaTipoMapper = alertaTipoMapper;
    }

    @Override
    public AlertaTipoDTO save(AlertaTipoDTO alertaTipoDTO) {
        log.debug("Request to save AlertaTipo : {}", alertaTipoDTO);
        AlertaTipo alertaTipo = alertaTipoMapper.toEntity(alertaTipoDTO);
        alertaTipo = alertaTipoRepository.save(alertaTipo);
        return alertaTipoMapper.toDto(alertaTipo);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AlertaTipoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AlertaTipos");
        return alertaTipoRepository.findAll(pageable)
            .map(alertaTipoMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<AlertaTipoDTO> findOne(Long id) {
        log.debug("Request to get AlertaTipo : {}", id);
        return alertaTipoRepository.findById(id)
            .map(alertaTipoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AlertaTipo : {}", id);
        alertaTipoRepository.deleteById(id);
    }
}
