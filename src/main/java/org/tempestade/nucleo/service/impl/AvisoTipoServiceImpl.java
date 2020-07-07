package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.AvisoTipoService;
import org.tempestade.nucleo.domain.AvisoTipo;
import org.tempestade.nucleo.repository.AvisoTipoRepository;
import org.tempestade.nucleo.service.dto.AvisoTipoDTO;
import org.tempestade.nucleo.service.mapper.AvisoTipoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AvisoTipo}.
 */
@Service
@Transactional
public class AvisoTipoServiceImpl implements AvisoTipoService {

    private final Logger log = LoggerFactory.getLogger(AvisoTipoServiceImpl.class);

    private final AvisoTipoRepository avisoTipoRepository;

    private final AvisoTipoMapper avisoTipoMapper;

    public AvisoTipoServiceImpl(AvisoTipoRepository avisoTipoRepository, AvisoTipoMapper avisoTipoMapper) {
        this.avisoTipoRepository = avisoTipoRepository;
        this.avisoTipoMapper = avisoTipoMapper;
    }

    @Override
    public AvisoTipoDTO save(AvisoTipoDTO avisoTipoDTO) {
        log.debug("Request to save AvisoTipo : {}", avisoTipoDTO);
        AvisoTipo avisoTipo = avisoTipoMapper.toEntity(avisoTipoDTO);
        avisoTipo = avisoTipoRepository.save(avisoTipo);
        return avisoTipoMapper.toDto(avisoTipo);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AvisoTipoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AvisoTipos");
        return avisoTipoRepository.findAll(pageable)
            .map(avisoTipoMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<AvisoTipoDTO> findOne(Long id) {
        log.debug("Request to get AvisoTipo : {}", id);
        return avisoTipoRepository.findById(id)
            .map(avisoTipoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AvisoTipo : {}", id);
        avisoTipoRepository.deleteById(id);
    }
}
