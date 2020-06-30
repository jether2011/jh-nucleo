package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.TipoEnvioIntegradorService;
import org.tempestade.nucleo.domain.TipoEnvioIntegrador;
import org.tempestade.nucleo.repository.TipoEnvioIntegradorRepository;
import org.tempestade.nucleo.service.dto.TipoEnvioIntegradorDTO;
import org.tempestade.nucleo.service.mapper.TipoEnvioIntegradorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TipoEnvioIntegrador}.
 */
@Service
@Transactional
public class TipoEnvioIntegradorServiceImpl implements TipoEnvioIntegradorService {

    private final Logger log = LoggerFactory.getLogger(TipoEnvioIntegradorServiceImpl.class);

    private final TipoEnvioIntegradorRepository tipoEnvioIntegradorRepository;

    private final TipoEnvioIntegradorMapper tipoEnvioIntegradorMapper;

    public TipoEnvioIntegradorServiceImpl(TipoEnvioIntegradorRepository tipoEnvioIntegradorRepository, TipoEnvioIntegradorMapper tipoEnvioIntegradorMapper) {
        this.tipoEnvioIntegradorRepository = tipoEnvioIntegradorRepository;
        this.tipoEnvioIntegradorMapper = tipoEnvioIntegradorMapper;
    }

    @Override
    public TipoEnvioIntegradorDTO save(TipoEnvioIntegradorDTO tipoEnvioIntegradorDTO) {
        log.debug("Request to save TipoEnvioIntegrador : {}", tipoEnvioIntegradorDTO);
        TipoEnvioIntegrador tipoEnvioIntegrador = tipoEnvioIntegradorMapper.toEntity(tipoEnvioIntegradorDTO);
        tipoEnvioIntegrador = tipoEnvioIntegradorRepository.save(tipoEnvioIntegrador);
        return tipoEnvioIntegradorMapper.toDto(tipoEnvioIntegrador);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TipoEnvioIntegradorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TipoEnvioIntegradors");
        return tipoEnvioIntegradorRepository.findAll(pageable)
            .map(tipoEnvioIntegradorMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TipoEnvioIntegradorDTO> findOne(Long id) {
        log.debug("Request to get TipoEnvioIntegrador : {}", id);
        return tipoEnvioIntegradorRepository.findById(id)
            .map(tipoEnvioIntegradorMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoEnvioIntegrador : {}", id);
        tipoEnvioIntegradorRepository.deleteById(id);
    }
}
