package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.AlvoBloqueioService;
import org.tempestade.nucleo.domain.AlvoBloqueio;
import org.tempestade.nucleo.repository.AlvoBloqueioRepository;
import org.tempestade.nucleo.service.dto.AlvoBloqueioDTO;
import org.tempestade.nucleo.service.mapper.AlvoBloqueioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AlvoBloqueio}.
 */
@Service
@Transactional
public class AlvoBloqueioServiceImpl implements AlvoBloqueioService {

    private final Logger log = LoggerFactory.getLogger(AlvoBloqueioServiceImpl.class);

    private final AlvoBloqueioRepository alvoBloqueioRepository;

    private final AlvoBloqueioMapper alvoBloqueioMapper;

    public AlvoBloqueioServiceImpl(AlvoBloqueioRepository alvoBloqueioRepository, AlvoBloqueioMapper alvoBloqueioMapper) {
        this.alvoBloqueioRepository = alvoBloqueioRepository;
        this.alvoBloqueioMapper = alvoBloqueioMapper;
    }

    @Override
    public AlvoBloqueioDTO save(AlvoBloqueioDTO alvoBloqueioDTO) {
        log.debug("Request to save AlvoBloqueio : {}", alvoBloqueioDTO);
        AlvoBloqueio alvoBloqueio = alvoBloqueioMapper.toEntity(alvoBloqueioDTO);
        alvoBloqueio = alvoBloqueioRepository.save(alvoBloqueio);
        return alvoBloqueioMapper.toDto(alvoBloqueio);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AlvoBloqueioDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AlvoBloqueios");
        return alvoBloqueioRepository.findAll(pageable)
            .map(alvoBloqueioMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<AlvoBloqueioDTO> findOne(Long id) {
        log.debug("Request to get AlvoBloqueio : {}", id);
        return alvoBloqueioRepository.findById(id)
            .map(alvoBloqueioMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AlvoBloqueio : {}", id);
        alvoBloqueioRepository.deleteById(id);
    }
}
