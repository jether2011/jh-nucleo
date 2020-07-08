package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.FerramentaService;
import org.tempestade.nucleo.domain.Ferramenta;
import org.tempestade.nucleo.repository.FerramentaRepository;
import org.tempestade.nucleo.service.dto.FerramentaDTO;
import org.tempestade.nucleo.service.mapper.FerramentaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Ferramenta}.
 */
@Service
@Transactional
public class FerramentaServiceImpl implements FerramentaService {

    private final Logger log = LoggerFactory.getLogger(FerramentaServiceImpl.class);

    private final FerramentaRepository ferramentaRepository;

    private final FerramentaMapper ferramentaMapper;

    public FerramentaServiceImpl(FerramentaRepository ferramentaRepository, FerramentaMapper ferramentaMapper) {
        this.ferramentaRepository = ferramentaRepository;
        this.ferramentaMapper = ferramentaMapper;
    }

    @Override
    public FerramentaDTO save(FerramentaDTO ferramentaDTO) {
        log.debug("Request to save Ferramenta : {}", ferramentaDTO);
        Ferramenta ferramenta = ferramentaMapper.toEntity(ferramentaDTO);
        ferramenta = ferramentaRepository.save(ferramenta);
        return ferramentaMapper.toDto(ferramenta);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FerramentaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Ferramentas");
        return ferramentaRepository.findAll(pageable)
            .map(ferramentaMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<FerramentaDTO> findOne(Long id) {
        log.debug("Request to get Ferramenta : {}", id);
        return ferramentaRepository.findById(id)
            .map(ferramentaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Ferramenta : {}", id);
        ferramentaRepository.deleteById(id);
    }
}
