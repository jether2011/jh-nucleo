package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.BoletimPrevObsService;
import org.tempestade.nucleo.domain.BoletimPrevObs;
import org.tempestade.nucleo.repository.BoletimPrevObsRepository;
import org.tempestade.nucleo.service.dto.BoletimPrevObsDTO;
import org.tempestade.nucleo.service.mapper.BoletimPrevObsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link BoletimPrevObs}.
 */
@Service
@Transactional
public class BoletimPrevObsServiceImpl implements BoletimPrevObsService {

    private final Logger log = LoggerFactory.getLogger(BoletimPrevObsServiceImpl.class);

    private final BoletimPrevObsRepository boletimPrevObsRepository;

    private final BoletimPrevObsMapper boletimPrevObsMapper;

    public BoletimPrevObsServiceImpl(BoletimPrevObsRepository boletimPrevObsRepository, BoletimPrevObsMapper boletimPrevObsMapper) {
        this.boletimPrevObsRepository = boletimPrevObsRepository;
        this.boletimPrevObsMapper = boletimPrevObsMapper;
    }

    @Override
    public BoletimPrevObsDTO save(BoletimPrevObsDTO boletimPrevObsDTO) {
        log.debug("Request to save BoletimPrevObs : {}", boletimPrevObsDTO);
        BoletimPrevObs boletimPrevObs = boletimPrevObsMapper.toEntity(boletimPrevObsDTO);
        boletimPrevObs = boletimPrevObsRepository.save(boletimPrevObs);
        return boletimPrevObsMapper.toDto(boletimPrevObs);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BoletimPrevObsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BoletimPrevObs");
        return boletimPrevObsRepository.findAll(pageable)
            .map(boletimPrevObsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<BoletimPrevObsDTO> findOne(Long id) {
        log.debug("Request to get BoletimPrevObs : {}", id);
        return boletimPrevObsRepository.findById(id)
            .map(boletimPrevObsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BoletimPrevObs : {}", id);
        boletimPrevObsRepository.deleteById(id);
    }
}
