package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.AlertaService;
import org.tempestade.nucleo.domain.Alerta;
import org.tempestade.nucleo.repository.AlertaRepository;
import org.tempestade.nucleo.service.dto.AlertaDTO;
import org.tempestade.nucleo.service.mapper.AlertaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Alerta}.
 */
@Service
@Transactional
public class AlertaServiceImpl implements AlertaService {

    private final Logger log = LoggerFactory.getLogger(AlertaServiceImpl.class);

    private final AlertaRepository alertaRepository;

    private final AlertaMapper alertaMapper;

    public AlertaServiceImpl(AlertaRepository alertaRepository, AlertaMapper alertaMapper) {
        this.alertaRepository = alertaRepository;
        this.alertaMapper = alertaMapper;
    }

    @Override
    public AlertaDTO save(AlertaDTO alertaDTO) {
        log.debug("Request to save Alerta : {}", alertaDTO);
        Alerta alerta = alertaMapper.toEntity(alertaDTO);
        alerta = alertaRepository.save(alerta);
        return alertaMapper.toDto(alerta);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AlertaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Alertas");
        return alertaRepository.findAll(pageable)
            .map(alertaMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<AlertaDTO> findOne(Long id) {
        log.debug("Request to get Alerta : {}", id);
        return alertaRepository.findById(id)
            .map(alertaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Alerta : {}", id);
        alertaRepository.deleteById(id);
    }
}
