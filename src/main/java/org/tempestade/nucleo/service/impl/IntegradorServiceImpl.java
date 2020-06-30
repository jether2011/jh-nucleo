package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.IntegradorService;
import org.tempestade.nucleo.domain.Integrador;
import org.tempestade.nucleo.repository.IntegradorRepository;
import org.tempestade.nucleo.service.dto.IntegradorDTO;
import org.tempestade.nucleo.service.mapper.IntegradorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Integrador}.
 */
@Service
@Transactional
public class IntegradorServiceImpl implements IntegradorService {

    private final Logger log = LoggerFactory.getLogger(IntegradorServiceImpl.class);

    private final IntegradorRepository integradorRepository;

    private final IntegradorMapper integradorMapper;

    public IntegradorServiceImpl(IntegradorRepository integradorRepository, IntegradorMapper integradorMapper) {
        this.integradorRepository = integradorRepository;
        this.integradorMapper = integradorMapper;
    }

    @Override
    public IntegradorDTO save(IntegradorDTO integradorDTO) {
        log.debug("Request to save Integrador : {}", integradorDTO);
        Integrador integrador = integradorMapper.toEntity(integradorDTO);
        integrador = integradorRepository.save(integrador);
        return integradorMapper.toDto(integrador);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<IntegradorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Integradors");
        return integradorRepository.findAll(pageable)
            .map(integradorMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<IntegradorDTO> findOne(Long id) {
        log.debug("Request to get Integrador : {}", id);
        return integradorRepository.findById(id)
            .map(integradorMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Integrador : {}", id);
        integradorRepository.deleteById(id);
    }
}
