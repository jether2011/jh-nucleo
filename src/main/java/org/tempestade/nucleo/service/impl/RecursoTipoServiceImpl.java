package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.RecursoTipoService;
import org.tempestade.nucleo.domain.RecursoTipo;
import org.tempestade.nucleo.repository.RecursoTipoRepository;
import org.tempestade.nucleo.service.dto.RecursoTipoDTO;
import org.tempestade.nucleo.service.mapper.RecursoTipoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link RecursoTipo}.
 */
@Service
@Transactional
public class RecursoTipoServiceImpl implements RecursoTipoService {

    private final Logger log = LoggerFactory.getLogger(RecursoTipoServiceImpl.class);

    private final RecursoTipoRepository recursoTipoRepository;

    private final RecursoTipoMapper recursoTipoMapper;

    public RecursoTipoServiceImpl(RecursoTipoRepository recursoTipoRepository, RecursoTipoMapper recursoTipoMapper) {
        this.recursoTipoRepository = recursoTipoRepository;
        this.recursoTipoMapper = recursoTipoMapper;
    }

    @Override
    public RecursoTipoDTO save(RecursoTipoDTO recursoTipoDTO) {
        log.debug("Request to save RecursoTipo : {}", recursoTipoDTO);
        RecursoTipo recursoTipo = recursoTipoMapper.toEntity(recursoTipoDTO);
        recursoTipo = recursoTipoRepository.save(recursoTipo);
        return recursoTipoMapper.toDto(recursoTipo);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RecursoTipoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RecursoTipos");
        return recursoTipoRepository.findAll(pageable)
            .map(recursoTipoMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<RecursoTipoDTO> findOne(Long id) {
        log.debug("Request to get RecursoTipo : {}", id);
        return recursoTipoRepository.findById(id)
            .map(recursoTipoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RecursoTipo : {}", id);
        recursoTipoRepository.deleteById(id);
    }
}
