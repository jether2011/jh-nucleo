package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.AcumuladoChuvaFaixaService;
import org.tempestade.nucleo.domain.AcumuladoChuvaFaixa;
import org.tempestade.nucleo.repository.AcumuladoChuvaFaixaRepository;
import org.tempestade.nucleo.service.dto.AcumuladoChuvaFaixaDTO;
import org.tempestade.nucleo.service.mapper.AcumuladoChuvaFaixaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AcumuladoChuvaFaixa}.
 */
@Service
@Transactional
public class AcumuladoChuvaFaixaServiceImpl implements AcumuladoChuvaFaixaService {

    private final Logger log = LoggerFactory.getLogger(AcumuladoChuvaFaixaServiceImpl.class);

    private final AcumuladoChuvaFaixaRepository acumuladoChuvaFaixaRepository;

    private final AcumuladoChuvaFaixaMapper acumuladoChuvaFaixaMapper;

    public AcumuladoChuvaFaixaServiceImpl(AcumuladoChuvaFaixaRepository acumuladoChuvaFaixaRepository, AcumuladoChuvaFaixaMapper acumuladoChuvaFaixaMapper) {
        this.acumuladoChuvaFaixaRepository = acumuladoChuvaFaixaRepository;
        this.acumuladoChuvaFaixaMapper = acumuladoChuvaFaixaMapper;
    }

    @Override
    public AcumuladoChuvaFaixaDTO save(AcumuladoChuvaFaixaDTO acumuladoChuvaFaixaDTO) {
        log.debug("Request to save AcumuladoChuvaFaixa : {}", acumuladoChuvaFaixaDTO);
        AcumuladoChuvaFaixa acumuladoChuvaFaixa = acumuladoChuvaFaixaMapper.toEntity(acumuladoChuvaFaixaDTO);
        acumuladoChuvaFaixa = acumuladoChuvaFaixaRepository.save(acumuladoChuvaFaixa);
        return acumuladoChuvaFaixaMapper.toDto(acumuladoChuvaFaixa);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AcumuladoChuvaFaixaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AcumuladoChuvaFaixas");
        return acumuladoChuvaFaixaRepository.findAll(pageable)
            .map(acumuladoChuvaFaixaMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<AcumuladoChuvaFaixaDTO> findOne(Long id) {
        log.debug("Request to get AcumuladoChuvaFaixa : {}", id);
        return acumuladoChuvaFaixaRepository.findById(id)
            .map(acumuladoChuvaFaixaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AcumuladoChuvaFaixa : {}", id);
        acumuladoChuvaFaixaRepository.deleteById(id);
    }
}
