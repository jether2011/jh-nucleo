package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.PrevisaoService;
import org.tempestade.nucleo.domain.Previsao;
import org.tempestade.nucleo.repository.PrevisaoRepository;
import org.tempestade.nucleo.service.dto.PrevisaoDTO;
import org.tempestade.nucleo.service.mapper.PrevisaoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Previsao}.
 */
@Service
@Transactional
public class PrevisaoServiceImpl implements PrevisaoService {

    private final Logger log = LoggerFactory.getLogger(PrevisaoServiceImpl.class);

    private final PrevisaoRepository previsaoRepository;

    private final PrevisaoMapper previsaoMapper;

    public PrevisaoServiceImpl(PrevisaoRepository previsaoRepository, PrevisaoMapper previsaoMapper) {
        this.previsaoRepository = previsaoRepository;
        this.previsaoMapper = previsaoMapper;
    }

    @Override
    public PrevisaoDTO save(PrevisaoDTO previsaoDTO) {
        log.debug("Request to save Previsao : {}", previsaoDTO);
        Previsao previsao = previsaoMapper.toEntity(previsaoDTO);
        previsao = previsaoRepository.save(previsao);
        return previsaoMapper.toDto(previsao);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PrevisaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Previsaos");
        return previsaoRepository.findAll(pageable)
            .map(previsaoMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PrevisaoDTO> findOne(Long id) {
        log.debug("Request to get Previsao : {}", id);
        return previsaoRepository.findById(id)
            .map(previsaoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Previsao : {}", id);
        previsaoRepository.deleteById(id);
    }
}
