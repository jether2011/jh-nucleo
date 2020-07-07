package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.BoletimPrevisaoService;
import org.tempestade.nucleo.domain.BoletimPrevisao;
import org.tempestade.nucleo.repository.BoletimPrevisaoRepository;
import org.tempestade.nucleo.service.dto.BoletimPrevisaoDTO;
import org.tempestade.nucleo.service.mapper.BoletimPrevisaoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link BoletimPrevisao}.
 */
@Service
@Transactional
public class BoletimPrevisaoServiceImpl implements BoletimPrevisaoService {

    private final Logger log = LoggerFactory.getLogger(BoletimPrevisaoServiceImpl.class);

    private final BoletimPrevisaoRepository boletimPrevisaoRepository;

    private final BoletimPrevisaoMapper boletimPrevisaoMapper;

    public BoletimPrevisaoServiceImpl(BoletimPrevisaoRepository boletimPrevisaoRepository, BoletimPrevisaoMapper boletimPrevisaoMapper) {
        this.boletimPrevisaoRepository = boletimPrevisaoRepository;
        this.boletimPrevisaoMapper = boletimPrevisaoMapper;
    }

    @Override
    public BoletimPrevisaoDTO save(BoletimPrevisaoDTO boletimPrevisaoDTO) {
        log.debug("Request to save BoletimPrevisao : {}", boletimPrevisaoDTO);
        BoletimPrevisao boletimPrevisao = boletimPrevisaoMapper.toEntity(boletimPrevisaoDTO);
        boletimPrevisao = boletimPrevisaoRepository.save(boletimPrevisao);
        return boletimPrevisaoMapper.toDto(boletimPrevisao);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BoletimPrevisaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BoletimPrevisaos");
        return boletimPrevisaoRepository.findAll(pageable)
            .map(boletimPrevisaoMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<BoletimPrevisaoDTO> findOne(Long id) {
        log.debug("Request to get BoletimPrevisao : {}", id);
        return boletimPrevisaoRepository.findById(id)
            .map(boletimPrevisaoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BoletimPrevisao : {}", id);
        boletimPrevisaoRepository.deleteById(id);
    }
}
