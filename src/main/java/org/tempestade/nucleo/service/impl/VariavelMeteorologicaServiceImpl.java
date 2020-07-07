package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.VariavelMeteorologicaService;
import org.tempestade.nucleo.domain.VariavelMeteorologica;
import org.tempestade.nucleo.repository.VariavelMeteorologicaRepository;
import org.tempestade.nucleo.service.dto.VariavelMeteorologicaDTO;
import org.tempestade.nucleo.service.mapper.VariavelMeteorologicaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link VariavelMeteorologica}.
 */
@Service
@Transactional
public class VariavelMeteorologicaServiceImpl implements VariavelMeteorologicaService {

    private final Logger log = LoggerFactory.getLogger(VariavelMeteorologicaServiceImpl.class);

    private final VariavelMeteorologicaRepository variavelMeteorologicaRepository;

    private final VariavelMeteorologicaMapper variavelMeteorologicaMapper;

    public VariavelMeteorologicaServiceImpl(VariavelMeteorologicaRepository variavelMeteorologicaRepository, VariavelMeteorologicaMapper variavelMeteorologicaMapper) {
        this.variavelMeteorologicaRepository = variavelMeteorologicaRepository;
        this.variavelMeteorologicaMapper = variavelMeteorologicaMapper;
    }

    @Override
    public VariavelMeteorologicaDTO save(VariavelMeteorologicaDTO variavelMeteorologicaDTO) {
        log.debug("Request to save VariavelMeteorologica : {}", variavelMeteorologicaDTO);
        VariavelMeteorologica variavelMeteorologica = variavelMeteorologicaMapper.toEntity(variavelMeteorologicaDTO);
        variavelMeteorologica = variavelMeteorologicaRepository.save(variavelMeteorologica);
        return variavelMeteorologicaMapper.toDto(variavelMeteorologica);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VariavelMeteorologicaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VariavelMeteorologicas");
        return variavelMeteorologicaRepository.findAll(pageable)
            .map(variavelMeteorologicaMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<VariavelMeteorologicaDTO> findOne(Long id) {
        log.debug("Request to get VariavelMeteorologica : {}", id);
        return variavelMeteorologicaRepository.findById(id)
            .map(variavelMeteorologicaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete VariavelMeteorologica : {}", id);
        variavelMeteorologicaRepository.deleteById(id);
    }
}
