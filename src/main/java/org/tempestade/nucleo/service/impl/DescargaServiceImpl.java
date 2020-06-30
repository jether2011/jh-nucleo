package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.DescargaService;
import org.tempestade.nucleo.domain.Descarga;
import org.tempestade.nucleo.repository.DescargaRepository;
import org.tempestade.nucleo.service.dto.DescargaDTO;
import org.tempestade.nucleo.service.mapper.DescargaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Descarga}.
 */
@Service
@Transactional
public class DescargaServiceImpl implements DescargaService {

    private final Logger log = LoggerFactory.getLogger(DescargaServiceImpl.class);

    private final DescargaRepository descargaRepository;

    private final DescargaMapper descargaMapper;

    public DescargaServiceImpl(DescargaRepository descargaRepository, DescargaMapper descargaMapper) {
        this.descargaRepository = descargaRepository;
        this.descargaMapper = descargaMapper;
    }

    @Override
    public DescargaDTO save(DescargaDTO descargaDTO) {
        log.debug("Request to save Descarga : {}", descargaDTO);
        Descarga descarga = descargaMapper.toEntity(descargaDTO);
        descarga = descargaRepository.save(descarga);
        return descargaMapper.toDto(descarga);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DescargaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Descargas");
        return descargaRepository.findAll(pageable)
            .map(descargaMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<DescargaDTO> findOne(Long id) {
        log.debug("Request to get Descarga : {}", id);
        return descargaRepository.findById(id)
            .map(descargaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Descarga : {}", id);
        descargaRepository.deleteById(id);
    }
}
