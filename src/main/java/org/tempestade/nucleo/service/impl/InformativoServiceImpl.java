package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.InformativoService;
import org.tempestade.nucleo.domain.Informativo;
import org.tempestade.nucleo.repository.InformativoRepository;
import org.tempestade.nucleo.service.dto.InformativoDTO;
import org.tempestade.nucleo.service.mapper.InformativoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Informativo}.
 */
@Service
@Transactional
public class InformativoServiceImpl implements InformativoService {

    private final Logger log = LoggerFactory.getLogger(InformativoServiceImpl.class);

    private final InformativoRepository informativoRepository;

    private final InformativoMapper informativoMapper;

    public InformativoServiceImpl(InformativoRepository informativoRepository, InformativoMapper informativoMapper) {
        this.informativoRepository = informativoRepository;
        this.informativoMapper = informativoMapper;
    }

    @Override
    public InformativoDTO save(InformativoDTO informativoDTO) {
        log.debug("Request to save Informativo : {}", informativoDTO);
        Informativo informativo = informativoMapper.toEntity(informativoDTO);
        informativo = informativoRepository.save(informativo);
        return informativoMapper.toDto(informativo);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InformativoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Informativos");
        return informativoRepository.findAll(pageable)
            .map(informativoMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<InformativoDTO> findOne(Long id) {
        log.debug("Request to get Informativo : {}", id);
        return informativoRepository.findById(id)
            .map(informativoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Informativo : {}", id);
        informativoRepository.deleteById(id);
    }
}
