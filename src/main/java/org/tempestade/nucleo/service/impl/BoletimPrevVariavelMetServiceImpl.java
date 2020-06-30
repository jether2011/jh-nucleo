package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.BoletimPrevVariavelMetService;
import org.tempestade.nucleo.domain.BoletimPrevVariavelMet;
import org.tempestade.nucleo.repository.BoletimPrevVariavelMetRepository;
import org.tempestade.nucleo.service.dto.BoletimPrevVariavelMetDTO;
import org.tempestade.nucleo.service.mapper.BoletimPrevVariavelMetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link BoletimPrevVariavelMet}.
 */
@Service
@Transactional
public class BoletimPrevVariavelMetServiceImpl implements BoletimPrevVariavelMetService {

    private final Logger log = LoggerFactory.getLogger(BoletimPrevVariavelMetServiceImpl.class);

    private final BoletimPrevVariavelMetRepository boletimPrevVariavelMetRepository;

    private final BoletimPrevVariavelMetMapper boletimPrevVariavelMetMapper;

    public BoletimPrevVariavelMetServiceImpl(BoletimPrevVariavelMetRepository boletimPrevVariavelMetRepository, BoletimPrevVariavelMetMapper boletimPrevVariavelMetMapper) {
        this.boletimPrevVariavelMetRepository = boletimPrevVariavelMetRepository;
        this.boletimPrevVariavelMetMapper = boletimPrevVariavelMetMapper;
    }

    @Override
    public BoletimPrevVariavelMetDTO save(BoletimPrevVariavelMetDTO boletimPrevVariavelMetDTO) {
        log.debug("Request to save BoletimPrevVariavelMet : {}", boletimPrevVariavelMetDTO);
        BoletimPrevVariavelMet boletimPrevVariavelMet = boletimPrevVariavelMetMapper.toEntity(boletimPrevVariavelMetDTO);
        boletimPrevVariavelMet = boletimPrevVariavelMetRepository.save(boletimPrevVariavelMet);
        return boletimPrevVariavelMetMapper.toDto(boletimPrevVariavelMet);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BoletimPrevVariavelMetDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BoletimPrevVariavelMets");
        return boletimPrevVariavelMetRepository.findAll(pageable)
            .map(boletimPrevVariavelMetMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<BoletimPrevVariavelMetDTO> findOne(Long id) {
        log.debug("Request to get BoletimPrevVariavelMet : {}", id);
        return boletimPrevVariavelMetRepository.findById(id)
            .map(boletimPrevVariavelMetMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BoletimPrevVariavelMet : {}", id);
        boletimPrevVariavelMetRepository.deleteById(id);
    }
}
