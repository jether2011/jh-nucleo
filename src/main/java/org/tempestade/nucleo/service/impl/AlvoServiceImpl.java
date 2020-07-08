package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.AlvoService;
import org.tempestade.nucleo.domain.Alvo;
import org.tempestade.nucleo.repository.AlvoRepository;
import org.tempestade.nucleo.service.dto.AlvoDTO;
import org.tempestade.nucleo.service.mapper.AlvoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Alvo}.
 */
@Service
@Transactional
public class AlvoServiceImpl implements AlvoService {

    private final Logger log = LoggerFactory.getLogger(AlvoServiceImpl.class);

    private final AlvoRepository alvoRepository;

    private final AlvoMapper alvoMapper;

    public AlvoServiceImpl(AlvoRepository alvoRepository, AlvoMapper alvoMapper) {
        this.alvoRepository = alvoRepository;
        this.alvoMapper = alvoMapper;
    }

    @Override
    public AlvoDTO save(AlvoDTO alvoDTO) {
        log.debug("Request to save Alvo : {}", alvoDTO);
        Alvo alvo = alvoMapper.toEntity(alvoDTO);
        alvo = alvoRepository.save(alvo);
        return alvoMapper.toDto(alvo);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AlvoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Alvos");
        return alvoRepository.findAll(pageable)
            .map(alvoMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<AlvoDTO> findOne(Long id) {
        log.debug("Request to get Alvo : {}", id);
        return alvoRepository.findById(id)
            .map(alvoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Alvo : {}", id);
        alvoRepository.deleteById(id);
    }
}
