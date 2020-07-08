package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.PontosCardeaisService;
import org.tempestade.nucleo.domain.PontosCardeais;
import org.tempestade.nucleo.repository.PontosCardeaisRepository;
import org.tempestade.nucleo.service.dto.PontosCardeaisDTO;
import org.tempestade.nucleo.service.mapper.PontosCardeaisMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PontosCardeais}.
 */
@Service
@Transactional
public class PontosCardeaisServiceImpl implements PontosCardeaisService {

    private final Logger log = LoggerFactory.getLogger(PontosCardeaisServiceImpl.class);

    private final PontosCardeaisRepository pontosCardeaisRepository;

    private final PontosCardeaisMapper pontosCardeaisMapper;

    public PontosCardeaisServiceImpl(PontosCardeaisRepository pontosCardeaisRepository, PontosCardeaisMapper pontosCardeaisMapper) {
        this.pontosCardeaisRepository = pontosCardeaisRepository;
        this.pontosCardeaisMapper = pontosCardeaisMapper;
    }

    @Override
    public PontosCardeaisDTO save(PontosCardeaisDTO pontosCardeaisDTO) {
        log.debug("Request to save PontosCardeais : {}", pontosCardeaisDTO);
        PontosCardeais pontosCardeais = pontosCardeaisMapper.toEntity(pontosCardeaisDTO);
        pontosCardeais = pontosCardeaisRepository.save(pontosCardeais);
        return pontosCardeaisMapper.toDto(pontosCardeais);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PontosCardeaisDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PontosCardeais");
        return pontosCardeaisRepository.findAll(pageable)
            .map(pontosCardeaisMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PontosCardeaisDTO> findOne(Long id) {
        log.debug("Request to get PontosCardeais : {}", id);
        return pontosCardeaisRepository.findById(id)
            .map(pontosCardeaisMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PontosCardeais : {}", id);
        pontosCardeaisRepository.deleteById(id);
    }
}
