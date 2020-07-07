package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.RecursoService;
import org.tempestade.nucleo.domain.Recurso;
import org.tempestade.nucleo.repository.RecursoRepository;
import org.tempestade.nucleo.service.dto.RecursoDTO;
import org.tempestade.nucleo.service.mapper.RecursoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Recurso}.
 */
@Service
@Transactional
public class RecursoServiceImpl implements RecursoService {

    private final Logger log = LoggerFactory.getLogger(RecursoServiceImpl.class);

    private final RecursoRepository recursoRepository;

    private final RecursoMapper recursoMapper;

    public RecursoServiceImpl(RecursoRepository recursoRepository, RecursoMapper recursoMapper) {
        this.recursoRepository = recursoRepository;
        this.recursoMapper = recursoMapper;
    }

    @Override
    public RecursoDTO save(RecursoDTO recursoDTO) {
        log.debug("Request to save Recurso : {}", recursoDTO);
        Recurso recurso = recursoMapper.toEntity(recursoDTO);
        recurso = recursoRepository.save(recurso);
        return recursoMapper.toDto(recurso);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RecursoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Recursos");
        return recursoRepository.findAll(pageable)
            .map(recursoMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<RecursoDTO> findOne(Long id) {
        log.debug("Request to get Recurso : {}", id);
        return recursoRepository.findById(id)
            .map(recursoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Recurso : {}", id);
        recursoRepository.deleteById(id);
    }
}
