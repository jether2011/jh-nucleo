package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.TipoEnvioService;
import org.tempestade.nucleo.domain.TipoEnvio;
import org.tempestade.nucleo.repository.TipoEnvioRepository;
import org.tempestade.nucleo.service.dto.TipoEnvioDTO;
import org.tempestade.nucleo.service.mapper.TipoEnvioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TipoEnvio}.
 */
@Service
@Transactional
public class TipoEnvioServiceImpl implements TipoEnvioService {

    private final Logger log = LoggerFactory.getLogger(TipoEnvioServiceImpl.class);

    private final TipoEnvioRepository tipoEnvioRepository;

    private final TipoEnvioMapper tipoEnvioMapper;

    public TipoEnvioServiceImpl(TipoEnvioRepository tipoEnvioRepository, TipoEnvioMapper tipoEnvioMapper) {
        this.tipoEnvioRepository = tipoEnvioRepository;
        this.tipoEnvioMapper = tipoEnvioMapper;
    }

    @Override
    public TipoEnvioDTO save(TipoEnvioDTO tipoEnvioDTO) {
        log.debug("Request to save TipoEnvio : {}", tipoEnvioDTO);
        TipoEnvio tipoEnvio = tipoEnvioMapper.toEntity(tipoEnvioDTO);
        tipoEnvio = tipoEnvioRepository.save(tipoEnvio);
        return tipoEnvioMapper.toDto(tipoEnvio);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TipoEnvioDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TipoEnvios");
        return tipoEnvioRepository.findAll(pageable)
            .map(tipoEnvioMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TipoEnvioDTO> findOne(Long id) {
        log.debug("Request to get TipoEnvio : {}", id);
        return tipoEnvioRepository.findById(id)
            .map(tipoEnvioMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoEnvio : {}", id);
        tipoEnvioRepository.deleteById(id);
    }
}
