package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.CondicaoTempoService;
import org.tempestade.nucleo.domain.CondicaoTempo;
import org.tempestade.nucleo.repository.CondicaoTempoRepository;
import org.tempestade.nucleo.service.dto.CondicaoTempoDTO;
import org.tempestade.nucleo.service.mapper.CondicaoTempoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CondicaoTempo}.
 */
@Service
@Transactional
public class CondicaoTempoServiceImpl implements CondicaoTempoService {

    private final Logger log = LoggerFactory.getLogger(CondicaoTempoServiceImpl.class);

    private final CondicaoTempoRepository condicaoTempoRepository;

    private final CondicaoTempoMapper condicaoTempoMapper;

    public CondicaoTempoServiceImpl(CondicaoTempoRepository condicaoTempoRepository, CondicaoTempoMapper condicaoTempoMapper) {
        this.condicaoTempoRepository = condicaoTempoRepository;
        this.condicaoTempoMapper = condicaoTempoMapper;
    }

    @Override
    public CondicaoTempoDTO save(CondicaoTempoDTO condicaoTempoDTO) {
        log.debug("Request to save CondicaoTempo : {}", condicaoTempoDTO);
        CondicaoTempo condicaoTempo = condicaoTempoMapper.toEntity(condicaoTempoDTO);
        condicaoTempo = condicaoTempoRepository.save(condicaoTempo);
        return condicaoTempoMapper.toDto(condicaoTempo);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CondicaoTempoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CondicaoTempos");
        return condicaoTempoRepository.findAll(pageable)
            .map(condicaoTempoMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CondicaoTempoDTO> findOne(Long id) {
        log.debug("Request to get CondicaoTempo : {}", id);
        return condicaoTempoRepository.findById(id)
            .map(condicaoTempoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CondicaoTempo : {}", id);
        condicaoTempoRepository.deleteById(id);
    }
}
