package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.AvisoService;
import org.tempestade.nucleo.domain.Aviso;
import org.tempestade.nucleo.repository.AvisoRepository;
import org.tempestade.nucleo.service.dto.AvisoDTO;
import org.tempestade.nucleo.service.mapper.AvisoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Aviso}.
 */
@Service
@Transactional
public class AvisoServiceImpl implements AvisoService {

    private final Logger log = LoggerFactory.getLogger(AvisoServiceImpl.class);

    private final AvisoRepository avisoRepository;

    private final AvisoMapper avisoMapper;

    public AvisoServiceImpl(AvisoRepository avisoRepository, AvisoMapper avisoMapper) {
        this.avisoRepository = avisoRepository;
        this.avisoMapper = avisoMapper;
    }

    @Override
    public AvisoDTO save(AvisoDTO avisoDTO) {
        log.debug("Request to save Aviso : {}", avisoDTO);
        Aviso aviso = avisoMapper.toEntity(avisoDTO);
        aviso = avisoRepository.save(aviso);
        return avisoMapper.toDto(aviso);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AvisoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Avisos");
        return avisoRepository.findAll(pageable)
            .map(avisoMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<AvisoDTO> findOne(Long id) {
        log.debug("Request to get Aviso : {}", id);
        return avisoRepository.findById(id)
            .map(avisoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Aviso : {}", id);
        avisoRepository.deleteById(id);
    }
}
