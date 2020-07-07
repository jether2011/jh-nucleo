package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.PlanoService;
import org.tempestade.nucleo.domain.Plano;
import org.tempestade.nucleo.repository.PlanoRepository;
import org.tempestade.nucleo.service.dto.PlanoDTO;
import org.tempestade.nucleo.service.mapper.PlanoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Plano}.
 */
@Service
@Transactional
public class PlanoServiceImpl implements PlanoService {

    private final Logger log = LoggerFactory.getLogger(PlanoServiceImpl.class);

    private final PlanoRepository planoRepository;

    private final PlanoMapper planoMapper;

    public PlanoServiceImpl(PlanoRepository planoRepository, PlanoMapper planoMapper) {
        this.planoRepository = planoRepository;
        this.planoMapper = planoMapper;
    }

    @Override
    public PlanoDTO save(PlanoDTO planoDTO) {
        log.debug("Request to save Plano : {}", planoDTO);
        Plano plano = planoMapper.toEntity(planoDTO);
        plano = planoRepository.save(plano);
        return planoMapper.toDto(plano);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PlanoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Planos");
        return planoRepository.findAll(pageable)
            .map(planoMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PlanoDTO> findOne(Long id) {
        log.debug("Request to get Plano : {}", id);
        return planoRepository.findById(id)
            .map(planoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Plano : {}", id);
        planoRepository.deleteById(id);
    }
}
