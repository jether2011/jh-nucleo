package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.ConsolidacaoService;
import org.tempestade.nucleo.domain.Consolidacao;
import org.tempestade.nucleo.repository.ConsolidacaoRepository;
import org.tempestade.nucleo.service.dto.ConsolidacaoDTO;
import org.tempestade.nucleo.service.mapper.ConsolidacaoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Consolidacao}.
 */
@Service
@Transactional
public class ConsolidacaoServiceImpl implements ConsolidacaoService {

    private final Logger log = LoggerFactory.getLogger(ConsolidacaoServiceImpl.class);

    private final ConsolidacaoRepository consolidacaoRepository;

    private final ConsolidacaoMapper consolidacaoMapper;

    public ConsolidacaoServiceImpl(ConsolidacaoRepository consolidacaoRepository, ConsolidacaoMapper consolidacaoMapper) {
        this.consolidacaoRepository = consolidacaoRepository;
        this.consolidacaoMapper = consolidacaoMapper;
    }

    @Override
    public ConsolidacaoDTO save(ConsolidacaoDTO consolidacaoDTO) {
        log.debug("Request to save Consolidacao : {}", consolidacaoDTO);
        Consolidacao consolidacao = consolidacaoMapper.toEntity(consolidacaoDTO);
        consolidacao = consolidacaoRepository.save(consolidacao);
        return consolidacaoMapper.toDto(consolidacao);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConsolidacaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Consolidacaos");
        return consolidacaoRepository.findAll(pageable)
            .map(consolidacaoMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ConsolidacaoDTO> findOne(Long id) {
        log.debug("Request to get Consolidacao : {}", id);
        return consolidacaoRepository.findById(id)
            .map(consolidacaoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Consolidacao : {}", id);
        consolidacaoRepository.deleteById(id);
    }
}
