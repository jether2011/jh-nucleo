package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.PlanoRecursoService;
import org.tempestade.nucleo.domain.PlanoRecurso;
import org.tempestade.nucleo.repository.PlanoRecursoRepository;
import org.tempestade.nucleo.service.dto.PlanoRecursoDTO;
import org.tempestade.nucleo.service.mapper.PlanoRecursoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PlanoRecurso}.
 */
@Service
@Transactional
public class PlanoRecursoServiceImpl implements PlanoRecursoService {

    private final Logger log = LoggerFactory.getLogger(PlanoRecursoServiceImpl.class);

    private final PlanoRecursoRepository planoRecursoRepository;

    private final PlanoRecursoMapper planoRecursoMapper;

    public PlanoRecursoServiceImpl(PlanoRecursoRepository planoRecursoRepository, PlanoRecursoMapper planoRecursoMapper) {
        this.planoRecursoRepository = planoRecursoRepository;
        this.planoRecursoMapper = planoRecursoMapper;
    }

    @Override
    public PlanoRecursoDTO save(PlanoRecursoDTO planoRecursoDTO) {
        log.debug("Request to save PlanoRecurso : {}", planoRecursoDTO);
        PlanoRecurso planoRecurso = planoRecursoMapper.toEntity(planoRecursoDTO);
        planoRecurso = planoRecursoRepository.save(planoRecurso);
        return planoRecursoMapper.toDto(planoRecurso);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PlanoRecursoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PlanoRecursos");
        return planoRecursoRepository.findAll(pageable)
            .map(planoRecursoMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PlanoRecursoDTO> findOne(Long id) {
        log.debug("Request to get PlanoRecurso : {}", id);
        return planoRecursoRepository.findById(id)
            .map(planoRecursoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PlanoRecurso : {}", id);
        planoRecursoRepository.deleteById(id);
    }
}
