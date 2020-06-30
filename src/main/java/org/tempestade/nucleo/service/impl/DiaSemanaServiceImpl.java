package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.DiaSemanaService;
import org.tempestade.nucleo.domain.DiaSemana;
import org.tempestade.nucleo.repository.DiaSemanaRepository;
import org.tempestade.nucleo.service.dto.DiaSemanaDTO;
import org.tempestade.nucleo.service.mapper.DiaSemanaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DiaSemana}.
 */
@Service
@Transactional
public class DiaSemanaServiceImpl implements DiaSemanaService {

    private final Logger log = LoggerFactory.getLogger(DiaSemanaServiceImpl.class);

    private final DiaSemanaRepository diaSemanaRepository;

    private final DiaSemanaMapper diaSemanaMapper;

    public DiaSemanaServiceImpl(DiaSemanaRepository diaSemanaRepository, DiaSemanaMapper diaSemanaMapper) {
        this.diaSemanaRepository = diaSemanaRepository;
        this.diaSemanaMapper = diaSemanaMapper;
    }

    @Override
    public DiaSemanaDTO save(DiaSemanaDTO diaSemanaDTO) {
        log.debug("Request to save DiaSemana : {}", diaSemanaDTO);
        DiaSemana diaSemana = diaSemanaMapper.toEntity(diaSemanaDTO);
        diaSemana = diaSemanaRepository.save(diaSemana);
        return diaSemanaMapper.toDto(diaSemana);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DiaSemanaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DiaSemanas");
        return diaSemanaRepository.findAll(pageable)
            .map(diaSemanaMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<DiaSemanaDTO> findOne(Long id) {
        log.debug("Request to get DiaSemana : {}", id);
        return diaSemanaRepository.findById(id)
            .map(diaSemanaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DiaSemana : {}", id);
        diaSemanaRepository.deleteById(id);
    }
}
