package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.BoletimService;
import org.tempestade.nucleo.domain.Boletim;
import org.tempestade.nucleo.repository.BoletimRepository;
import org.tempestade.nucleo.service.dto.BoletimDTO;
import org.tempestade.nucleo.service.mapper.BoletimMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Boletim}.
 */
@Service
@Transactional
public class BoletimServiceImpl implements BoletimService {

    private final Logger log = LoggerFactory.getLogger(BoletimServiceImpl.class);

    private final BoletimRepository boletimRepository;

    private final BoletimMapper boletimMapper;

    public BoletimServiceImpl(BoletimRepository boletimRepository, BoletimMapper boletimMapper) {
        this.boletimRepository = boletimRepository;
        this.boletimMapper = boletimMapper;
    }

    @Override
    public BoletimDTO save(BoletimDTO boletimDTO) {
        log.debug("Request to save Boletim : {}", boletimDTO);
        Boletim boletim = boletimMapper.toEntity(boletimDTO);
        boletim = boletimRepository.save(boletim);
        return boletimMapper.toDto(boletim);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BoletimDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Boletims");
        return boletimRepository.findAll(pageable)
            .map(boletimMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<BoletimDTO> findOne(Long id) {
        log.debug("Request to get Boletim : {}", id);
        return boletimRepository.findById(id)
            .map(boletimMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Boletim : {}", id);
        boletimRepository.deleteById(id);
    }
}
