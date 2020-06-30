package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.RecursoTemplateService;
import org.tempestade.nucleo.domain.RecursoTemplate;
import org.tempestade.nucleo.repository.RecursoTemplateRepository;
import org.tempestade.nucleo.service.dto.RecursoTemplateDTO;
import org.tempestade.nucleo.service.mapper.RecursoTemplateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link RecursoTemplate}.
 */
@Service
@Transactional
public class RecursoTemplateServiceImpl implements RecursoTemplateService {

    private final Logger log = LoggerFactory.getLogger(RecursoTemplateServiceImpl.class);

    private final RecursoTemplateRepository recursoTemplateRepository;

    private final RecursoTemplateMapper recursoTemplateMapper;

    public RecursoTemplateServiceImpl(RecursoTemplateRepository recursoTemplateRepository, RecursoTemplateMapper recursoTemplateMapper) {
        this.recursoTemplateRepository = recursoTemplateRepository;
        this.recursoTemplateMapper = recursoTemplateMapper;
    }

    @Override
    public RecursoTemplateDTO save(RecursoTemplateDTO recursoTemplateDTO) {
        log.debug("Request to save RecursoTemplate : {}", recursoTemplateDTO);
        RecursoTemplate recursoTemplate = recursoTemplateMapper.toEntity(recursoTemplateDTO);
        recursoTemplate = recursoTemplateRepository.save(recursoTemplate);
        return recursoTemplateMapper.toDto(recursoTemplate);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RecursoTemplateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RecursoTemplates");
        return recursoTemplateRepository.findAll(pageable)
            .map(recursoTemplateMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<RecursoTemplateDTO> findOne(Long id) {
        log.debug("Request to get RecursoTemplate : {}", id);
        return recursoTemplateRepository.findById(id)
            .map(recursoTemplateMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RecursoTemplate : {}", id);
        recursoTemplateRepository.deleteById(id);
    }
}
