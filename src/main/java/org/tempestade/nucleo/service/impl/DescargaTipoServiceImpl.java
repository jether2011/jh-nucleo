package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.DescargaTipoService;
import org.tempestade.nucleo.domain.DescargaTipo;
import org.tempestade.nucleo.repository.DescargaTipoRepository;
import org.tempestade.nucleo.service.dto.DescargaTipoDTO;
import org.tempestade.nucleo.service.mapper.DescargaTipoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DescargaTipo}.
 */
@Service
@Transactional
public class DescargaTipoServiceImpl implements DescargaTipoService {

    private final Logger log = LoggerFactory.getLogger(DescargaTipoServiceImpl.class);

    private final DescargaTipoRepository descargaTipoRepository;

    private final DescargaTipoMapper descargaTipoMapper;

    public DescargaTipoServiceImpl(DescargaTipoRepository descargaTipoRepository, DescargaTipoMapper descargaTipoMapper) {
        this.descargaTipoRepository = descargaTipoRepository;
        this.descargaTipoMapper = descargaTipoMapper;
    }

    @Override
    public DescargaTipoDTO save(DescargaTipoDTO descargaTipoDTO) {
        log.debug("Request to save DescargaTipo : {}", descargaTipoDTO);
        DescargaTipo descargaTipo = descargaTipoMapper.toEntity(descargaTipoDTO);
        descargaTipo = descargaTipoRepository.save(descargaTipo);
        return descargaTipoMapper.toDto(descargaTipo);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DescargaTipoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DescargaTipos");
        return descargaTipoRepository.findAll(pageable)
            .map(descargaTipoMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<DescargaTipoDTO> findOne(Long id) {
        log.debug("Request to get DescargaTipo : {}", id);
        return descargaTipoRepository.findById(id)
            .map(descargaTipoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DescargaTipo : {}", id);
        descargaTipoRepository.deleteById(id);
    }
}
