package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.DescargaUnidadeService;
import org.tempestade.nucleo.domain.DescargaUnidade;
import org.tempestade.nucleo.repository.DescargaUnidadeRepository;
import org.tempestade.nucleo.service.dto.DescargaUnidadeDTO;
import org.tempestade.nucleo.service.mapper.DescargaUnidadeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DescargaUnidade}.
 */
@Service
@Transactional
public class DescargaUnidadeServiceImpl implements DescargaUnidadeService {

    private final Logger log = LoggerFactory.getLogger(DescargaUnidadeServiceImpl.class);

    private final DescargaUnidadeRepository descargaUnidadeRepository;

    private final DescargaUnidadeMapper descargaUnidadeMapper;

    public DescargaUnidadeServiceImpl(DescargaUnidadeRepository descargaUnidadeRepository, DescargaUnidadeMapper descargaUnidadeMapper) {
        this.descargaUnidadeRepository = descargaUnidadeRepository;
        this.descargaUnidadeMapper = descargaUnidadeMapper;
    }

    @Override
    public DescargaUnidadeDTO save(DescargaUnidadeDTO descargaUnidadeDTO) {
        log.debug("Request to save DescargaUnidade : {}", descargaUnidadeDTO);
        DescargaUnidade descargaUnidade = descargaUnidadeMapper.toEntity(descargaUnidadeDTO);
        descargaUnidade = descargaUnidadeRepository.save(descargaUnidade);
        return descargaUnidadeMapper.toDto(descargaUnidade);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DescargaUnidadeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DescargaUnidades");
        return descargaUnidadeRepository.findAll(pageable)
            .map(descargaUnidadeMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<DescargaUnidadeDTO> findOne(Long id) {
        log.debug("Request to get DescargaUnidade : {}", id);
        return descargaUnidadeRepository.findById(id)
            .map(descargaUnidadeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DescargaUnidade : {}", id);
        descargaUnidadeRepository.deleteById(id);
    }
}
