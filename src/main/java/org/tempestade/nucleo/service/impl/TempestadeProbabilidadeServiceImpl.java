package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.TempestadeProbabilidadeService;
import org.tempestade.nucleo.domain.TempestadeProbabilidade;
import org.tempestade.nucleo.repository.TempestadeProbabilidadeRepository;
import org.tempestade.nucleo.service.dto.TempestadeProbabilidadeDTO;
import org.tempestade.nucleo.service.mapper.TempestadeProbabilidadeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TempestadeProbabilidade}.
 */
@Service
@Transactional
public class TempestadeProbabilidadeServiceImpl implements TempestadeProbabilidadeService {

    private final Logger log = LoggerFactory.getLogger(TempestadeProbabilidadeServiceImpl.class);

    private final TempestadeProbabilidadeRepository tempestadeProbabilidadeRepository;

    private final TempestadeProbabilidadeMapper tempestadeProbabilidadeMapper;

    public TempestadeProbabilidadeServiceImpl(TempestadeProbabilidadeRepository tempestadeProbabilidadeRepository, TempestadeProbabilidadeMapper tempestadeProbabilidadeMapper) {
        this.tempestadeProbabilidadeRepository = tempestadeProbabilidadeRepository;
        this.tempestadeProbabilidadeMapper = tempestadeProbabilidadeMapper;
    }

    @Override
    public TempestadeProbabilidadeDTO save(TempestadeProbabilidadeDTO tempestadeProbabilidadeDTO) {
        log.debug("Request to save TempestadeProbabilidade : {}", tempestadeProbabilidadeDTO);
        TempestadeProbabilidade tempestadeProbabilidade = tempestadeProbabilidadeMapper.toEntity(tempestadeProbabilidadeDTO);
        tempestadeProbabilidade = tempestadeProbabilidadeRepository.save(tempestadeProbabilidade);
        return tempestadeProbabilidadeMapper.toDto(tempestadeProbabilidade);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TempestadeProbabilidadeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TempestadeProbabilidades");
        return tempestadeProbabilidadeRepository.findAll(pageable)
            .map(tempestadeProbabilidadeMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TempestadeProbabilidadeDTO> findOne(Long id) {
        log.debug("Request to get TempestadeProbabilidade : {}", id);
        return tempestadeProbabilidadeRepository.findById(id)
            .map(tempestadeProbabilidadeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TempestadeProbabilidade : {}", id);
        tempestadeProbabilidadeRepository.deleteById(id);
    }
}
