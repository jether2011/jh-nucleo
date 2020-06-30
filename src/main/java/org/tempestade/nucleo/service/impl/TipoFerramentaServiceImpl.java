package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.TipoFerramentaService;
import org.tempestade.nucleo.domain.TipoFerramenta;
import org.tempestade.nucleo.repository.TipoFerramentaRepository;
import org.tempestade.nucleo.service.dto.TipoFerramentaDTO;
import org.tempestade.nucleo.service.mapper.TipoFerramentaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TipoFerramenta}.
 */
@Service
@Transactional
public class TipoFerramentaServiceImpl implements TipoFerramentaService {

    private final Logger log = LoggerFactory.getLogger(TipoFerramentaServiceImpl.class);

    private final TipoFerramentaRepository tipoFerramentaRepository;

    private final TipoFerramentaMapper tipoFerramentaMapper;

    public TipoFerramentaServiceImpl(TipoFerramentaRepository tipoFerramentaRepository, TipoFerramentaMapper tipoFerramentaMapper) {
        this.tipoFerramentaRepository = tipoFerramentaRepository;
        this.tipoFerramentaMapper = tipoFerramentaMapper;
    }

    @Override
    public TipoFerramentaDTO save(TipoFerramentaDTO tipoFerramentaDTO) {
        log.debug("Request to save TipoFerramenta : {}", tipoFerramentaDTO);
        TipoFerramenta tipoFerramenta = tipoFerramentaMapper.toEntity(tipoFerramentaDTO);
        tipoFerramenta = tipoFerramentaRepository.save(tipoFerramenta);
        return tipoFerramentaMapper.toDto(tipoFerramenta);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TipoFerramentaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TipoFerramentas");
        return tipoFerramentaRepository.findAll(pageable)
            .map(tipoFerramentaMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TipoFerramentaDTO> findOne(Long id) {
        log.debug("Request to get TipoFerramenta : {}", id);
        return tipoFerramentaRepository.findById(id)
            .map(tipoFerramentaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoFerramenta : {}", id);
        tipoFerramentaRepository.deleteById(id);
    }
}
