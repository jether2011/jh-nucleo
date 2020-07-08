package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.AlertaRiscoService;
import org.tempestade.nucleo.domain.AlertaRisco;
import org.tempestade.nucleo.repository.AlertaRiscoRepository;
import org.tempestade.nucleo.service.dto.AlertaRiscoDTO;
import org.tempestade.nucleo.service.mapper.AlertaRiscoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AlertaRisco}.
 */
@Service
@Transactional
public class AlertaRiscoServiceImpl implements AlertaRiscoService {

    private final Logger log = LoggerFactory.getLogger(AlertaRiscoServiceImpl.class);

    private final AlertaRiscoRepository alertaRiscoRepository;

    private final AlertaRiscoMapper alertaRiscoMapper;

    public AlertaRiscoServiceImpl(AlertaRiscoRepository alertaRiscoRepository, AlertaRiscoMapper alertaRiscoMapper) {
        this.alertaRiscoRepository = alertaRiscoRepository;
        this.alertaRiscoMapper = alertaRiscoMapper;
    }

    @Override
    public AlertaRiscoDTO save(AlertaRiscoDTO alertaRiscoDTO) {
        log.debug("Request to save AlertaRisco : {}", alertaRiscoDTO);
        AlertaRisco alertaRisco = alertaRiscoMapper.toEntity(alertaRiscoDTO);
        alertaRisco = alertaRiscoRepository.save(alertaRisco);
        return alertaRiscoMapper.toDto(alertaRisco);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AlertaRiscoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AlertaRiscos");
        return alertaRiscoRepository.findAll(pageable)
            .map(alertaRiscoMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<AlertaRiscoDTO> findOne(Long id) {
        log.debug("Request to get AlertaRisco : {}", id);
        return alertaRiscoRepository.findById(id)
            .map(alertaRiscoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AlertaRisco : {}", id);
        alertaRiscoRepository.deleteById(id);
    }
}
