package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.VentoVmFaixaService;
import org.tempestade.nucleo.domain.VentoVmFaixa;
import org.tempestade.nucleo.repository.VentoVmFaixaRepository;
import org.tempestade.nucleo.service.dto.VentoVmFaixaDTO;
import org.tempestade.nucleo.service.mapper.VentoVmFaixaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link VentoVmFaixa}.
 */
@Service
@Transactional
public class VentoVmFaixaServiceImpl implements VentoVmFaixaService {

    private final Logger log = LoggerFactory.getLogger(VentoVmFaixaServiceImpl.class);

    private final VentoVmFaixaRepository ventoVmFaixaRepository;

    private final VentoVmFaixaMapper ventoVmFaixaMapper;

    public VentoVmFaixaServiceImpl(VentoVmFaixaRepository ventoVmFaixaRepository, VentoVmFaixaMapper ventoVmFaixaMapper) {
        this.ventoVmFaixaRepository = ventoVmFaixaRepository;
        this.ventoVmFaixaMapper = ventoVmFaixaMapper;
    }

    @Override
    public VentoVmFaixaDTO save(VentoVmFaixaDTO ventoVmFaixaDTO) {
        log.debug("Request to save VentoVmFaixa : {}", ventoVmFaixaDTO);
        VentoVmFaixa ventoVmFaixa = ventoVmFaixaMapper.toEntity(ventoVmFaixaDTO);
        ventoVmFaixa = ventoVmFaixaRepository.save(ventoVmFaixa);
        return ventoVmFaixaMapper.toDto(ventoVmFaixa);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VentoVmFaixaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VentoVmFaixas");
        return ventoVmFaixaRepository.findAll(pageable)
            .map(ventoVmFaixaMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<VentoVmFaixaDTO> findOne(Long id) {
        log.debug("Request to get VentoVmFaixa : {}", id);
        return ventoVmFaixaRepository.findById(id)
            .map(ventoVmFaixaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete VentoVmFaixa : {}", id);
        ventoVmFaixaRepository.deleteById(id);
    }
}
