package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.PlanoRecursoTipoEnvioService;
import org.tempestade.nucleo.domain.PlanoRecursoTipoEnvio;
import org.tempestade.nucleo.repository.PlanoRecursoTipoEnvioRepository;
import org.tempestade.nucleo.service.dto.PlanoRecursoTipoEnvioDTO;
import org.tempestade.nucleo.service.mapper.PlanoRecursoTipoEnvioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PlanoRecursoTipoEnvio}.
 */
@Service
@Transactional
public class PlanoRecursoTipoEnvioServiceImpl implements PlanoRecursoTipoEnvioService {

    private final Logger log = LoggerFactory.getLogger(PlanoRecursoTipoEnvioServiceImpl.class);

    private final PlanoRecursoTipoEnvioRepository planoRecursoTipoEnvioRepository;

    private final PlanoRecursoTipoEnvioMapper planoRecursoTipoEnvioMapper;

    public PlanoRecursoTipoEnvioServiceImpl(PlanoRecursoTipoEnvioRepository planoRecursoTipoEnvioRepository, PlanoRecursoTipoEnvioMapper planoRecursoTipoEnvioMapper) {
        this.planoRecursoTipoEnvioRepository = planoRecursoTipoEnvioRepository;
        this.planoRecursoTipoEnvioMapper = planoRecursoTipoEnvioMapper;
    }

    @Override
    public PlanoRecursoTipoEnvioDTO save(PlanoRecursoTipoEnvioDTO planoRecursoTipoEnvioDTO) {
        log.debug("Request to save PlanoRecursoTipoEnvio : {}", planoRecursoTipoEnvioDTO);
        PlanoRecursoTipoEnvio planoRecursoTipoEnvio = planoRecursoTipoEnvioMapper.toEntity(planoRecursoTipoEnvioDTO);
        planoRecursoTipoEnvio = planoRecursoTipoEnvioRepository.save(planoRecursoTipoEnvio);
        return planoRecursoTipoEnvioMapper.toDto(planoRecursoTipoEnvio);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PlanoRecursoTipoEnvioDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PlanoRecursoTipoEnvios");
        return planoRecursoTipoEnvioRepository.findAll(pageable)
            .map(planoRecursoTipoEnvioMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PlanoRecursoTipoEnvioDTO> findOne(Long id) {
        log.debug("Request to get PlanoRecursoTipoEnvio : {}", id);
        return planoRecursoTipoEnvioRepository.findById(id)
            .map(planoRecursoTipoEnvioMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PlanoRecursoTipoEnvio : {}", id);
        planoRecursoTipoEnvioRepository.deleteById(id);
    }
}
