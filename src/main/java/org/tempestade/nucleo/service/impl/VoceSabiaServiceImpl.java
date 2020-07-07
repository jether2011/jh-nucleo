package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.VoceSabiaService;
import org.tempestade.nucleo.domain.VoceSabia;
import org.tempestade.nucleo.repository.VoceSabiaRepository;
import org.tempestade.nucleo.service.dto.VoceSabiaDTO;
import org.tempestade.nucleo.service.mapper.VoceSabiaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link VoceSabia}.
 */
@Service
@Transactional
public class VoceSabiaServiceImpl implements VoceSabiaService {

    private final Logger log = LoggerFactory.getLogger(VoceSabiaServiceImpl.class);

    private final VoceSabiaRepository voceSabiaRepository;

    private final VoceSabiaMapper voceSabiaMapper;

    public VoceSabiaServiceImpl(VoceSabiaRepository voceSabiaRepository, VoceSabiaMapper voceSabiaMapper) {
        this.voceSabiaRepository = voceSabiaRepository;
        this.voceSabiaMapper = voceSabiaMapper;
    }

    @Override
    public VoceSabiaDTO save(VoceSabiaDTO voceSabiaDTO) {
        log.debug("Request to save VoceSabia : {}", voceSabiaDTO);
        VoceSabia voceSabia = voceSabiaMapper.toEntity(voceSabiaDTO);
        voceSabia = voceSabiaRepository.save(voceSabia);
        return voceSabiaMapper.toDto(voceSabia);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VoceSabiaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VoceSabias");
        return voceSabiaRepository.findAll(pageable)
            .map(voceSabiaMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<VoceSabiaDTO> findOne(Long id) {
        log.debug("Request to get VoceSabia : {}", id);
        return voceSabiaRepository.findById(id)
            .map(voceSabiaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete VoceSabia : {}", id);
        voceSabiaRepository.deleteById(id);
    }
}
