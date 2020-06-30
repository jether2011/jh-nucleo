package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.AlertaFerramentaService;
import org.tempestade.nucleo.domain.AlertaFerramenta;
import org.tempestade.nucleo.repository.AlertaFerramentaRepository;
import org.tempestade.nucleo.service.dto.AlertaFerramentaDTO;
import org.tempestade.nucleo.service.mapper.AlertaFerramentaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AlertaFerramenta}.
 */
@Service
@Transactional
public class AlertaFerramentaServiceImpl implements AlertaFerramentaService {

    private final Logger log = LoggerFactory.getLogger(AlertaFerramentaServiceImpl.class);

    private final AlertaFerramentaRepository alertaFerramentaRepository;

    private final AlertaFerramentaMapper alertaFerramentaMapper;

    public AlertaFerramentaServiceImpl(AlertaFerramentaRepository alertaFerramentaRepository, AlertaFerramentaMapper alertaFerramentaMapper) {
        this.alertaFerramentaRepository = alertaFerramentaRepository;
        this.alertaFerramentaMapper = alertaFerramentaMapper;
    }

    @Override
    public AlertaFerramentaDTO save(AlertaFerramentaDTO alertaFerramentaDTO) {
        log.debug("Request to save AlertaFerramenta : {}", alertaFerramentaDTO);
        AlertaFerramenta alertaFerramenta = alertaFerramentaMapper.toEntity(alertaFerramentaDTO);
        alertaFerramenta = alertaFerramentaRepository.save(alertaFerramenta);
        return alertaFerramentaMapper.toDto(alertaFerramenta);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AlertaFerramentaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AlertaFerramentas");
        return alertaFerramentaRepository.findAll(pageable)
            .map(alertaFerramentaMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<AlertaFerramentaDTO> findOne(Long id) {
        log.debug("Request to get AlertaFerramenta : {}", id);
        return alertaFerramentaRepository.findById(id)
            .map(alertaFerramentaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AlertaFerramenta : {}", id);
        alertaFerramentaRepository.deleteById(id);
    }
}
