package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.AvisoMeteorologicoService;
import org.tempestade.nucleo.domain.AvisoMeteorologico;
import org.tempestade.nucleo.repository.AvisoMeteorologicoRepository;
import org.tempestade.nucleo.service.dto.AvisoMeteorologicoDTO;
import org.tempestade.nucleo.service.mapper.AvisoMeteorologicoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AvisoMeteorologico}.
 */
@Service
@Transactional
public class AvisoMeteorologicoServiceImpl implements AvisoMeteorologicoService {

    private final Logger log = LoggerFactory.getLogger(AvisoMeteorologicoServiceImpl.class);

    private final AvisoMeteorologicoRepository avisoMeteorologicoRepository;

    private final AvisoMeteorologicoMapper avisoMeteorologicoMapper;

    public AvisoMeteorologicoServiceImpl(AvisoMeteorologicoRepository avisoMeteorologicoRepository, AvisoMeteorologicoMapper avisoMeteorologicoMapper) {
        this.avisoMeteorologicoRepository = avisoMeteorologicoRepository;
        this.avisoMeteorologicoMapper = avisoMeteorologicoMapper;
    }

    @Override
    public AvisoMeteorologicoDTO save(AvisoMeteorologicoDTO avisoMeteorologicoDTO) {
        log.debug("Request to save AvisoMeteorologico : {}", avisoMeteorologicoDTO);
        AvisoMeteorologico avisoMeteorologico = avisoMeteorologicoMapper.toEntity(avisoMeteorologicoDTO);
        avisoMeteorologico = avisoMeteorologicoRepository.save(avisoMeteorologico);
        return avisoMeteorologicoMapper.toDto(avisoMeteorologico);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AvisoMeteorologicoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AvisoMeteorologicos");
        return avisoMeteorologicoRepository.findAll(pageable)
            .map(avisoMeteorologicoMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<AvisoMeteorologicoDTO> findOne(Long id) {
        log.debug("Request to get AvisoMeteorologico : {}", id);
        return avisoMeteorologicoRepository.findById(id)
            .map(avisoMeteorologicoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AvisoMeteorologico : {}", id);
        avisoMeteorologicoRepository.deleteById(id);
    }
}
