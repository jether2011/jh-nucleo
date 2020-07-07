package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.LogService;
import org.tempestade.nucleo.domain.Log;
import org.tempestade.nucleo.repository.LogRepository;
import org.tempestade.nucleo.service.dto.LogDTO;
import org.tempestade.nucleo.service.mapper.LogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Log}.
 */
@Service
@Transactional
public class LogServiceImpl implements LogService {

    private final Logger log = LoggerFactory.getLogger(LogServiceImpl.class);

    private final LogRepository logRepository;

    private final LogMapper logMapper;

    public LogServiceImpl(LogRepository logRepository, LogMapper logMapper) {
        this.logRepository = logRepository;
        this.logMapper = logMapper;
    }

    @Override
    public LogDTO save(LogDTO logDTO) {
        log.debug("Request to save Log : {}", logDTO);
        Log log = logMapper.toEntity(logDTO);
        log = logRepository.save(log);
        return logMapper.toDto(log);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LogDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Logs");
        return logRepository.findAll(pageable)
            .map(logMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<LogDTO> findOne(Long id) {
        log.debug("Request to get Log : {}", id);
        return logRepository.findById(id)
            .map(logMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Log : {}", id);
        logRepository.deleteById(id);
    }
}
