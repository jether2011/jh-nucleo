package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.PlanoRedeService;
import org.tempestade.nucleo.domain.PlanoRede;
import org.tempestade.nucleo.repository.PlanoRedeRepository;
import org.tempestade.nucleo.service.dto.PlanoRedeDTO;
import org.tempestade.nucleo.service.mapper.PlanoRedeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PlanoRede}.
 */
@Service
@Transactional
public class PlanoRedeServiceImpl implements PlanoRedeService {

    private final Logger log = LoggerFactory.getLogger(PlanoRedeServiceImpl.class);

    private final PlanoRedeRepository planoRedeRepository;

    private final PlanoRedeMapper planoRedeMapper;

    public PlanoRedeServiceImpl(PlanoRedeRepository planoRedeRepository, PlanoRedeMapper planoRedeMapper) {
        this.planoRedeRepository = planoRedeRepository;
        this.planoRedeMapper = planoRedeMapper;
    }

    @Override
    public PlanoRedeDTO save(PlanoRedeDTO planoRedeDTO) {
        log.debug("Request to save PlanoRede : {}", planoRedeDTO);
        PlanoRede planoRede = planoRedeMapper.toEntity(planoRedeDTO);
        planoRede = planoRedeRepository.save(planoRede);
        return planoRedeMapper.toDto(planoRede);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PlanoRedeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PlanoRedes");
        return planoRedeRepository.findAll(pageable)
            .map(planoRedeMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PlanoRedeDTO> findOne(Long id) {
        log.debug("Request to get PlanoRede : {}", id);
        return planoRedeRepository.findById(id)
            .map(planoRedeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PlanoRede : {}", id);
        planoRedeRepository.deleteById(id);
    }
}
