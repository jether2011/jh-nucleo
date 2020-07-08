package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.PlanoStatusService;
import org.tempestade.nucleo.domain.PlanoStatus;
import org.tempestade.nucleo.repository.PlanoStatusRepository;
import org.tempestade.nucleo.service.dto.PlanoStatusDTO;
import org.tempestade.nucleo.service.mapper.PlanoStatusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PlanoStatus}.
 */
@Service
@Transactional
public class PlanoStatusServiceImpl implements PlanoStatusService {

    private final Logger log = LoggerFactory.getLogger(PlanoStatusServiceImpl.class);

    private final PlanoStatusRepository planoStatusRepository;

    private final PlanoStatusMapper planoStatusMapper;

    public PlanoStatusServiceImpl(PlanoStatusRepository planoStatusRepository, PlanoStatusMapper planoStatusMapper) {
        this.planoStatusRepository = planoStatusRepository;
        this.planoStatusMapper = planoStatusMapper;
    }

    @Override
    public PlanoStatusDTO save(PlanoStatusDTO planoStatusDTO) {
        log.debug("Request to save PlanoStatus : {}", planoStatusDTO);
        PlanoStatus planoStatus = planoStatusMapper.toEntity(planoStatusDTO);
        planoStatus = planoStatusRepository.save(planoStatus);
        return planoStatusMapper.toDto(planoStatus);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PlanoStatusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PlanoStatuses");
        return planoStatusRepository.findAll(pageable)
            .map(planoStatusMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PlanoStatusDTO> findOne(Long id) {
        log.debug("Request to get PlanoStatus : {}", id);
        return planoStatusRepository.findById(id)
            .map(planoStatusMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PlanoStatus : {}", id);
        planoStatusRepository.deleteById(id);
    }
}
