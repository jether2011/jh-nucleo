package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.IntensidadeChuvaService;
import org.tempestade.nucleo.domain.IntensidadeChuva;
import org.tempestade.nucleo.repository.IntensidadeChuvaRepository;
import org.tempestade.nucleo.service.dto.IntensidadeChuvaDTO;
import org.tempestade.nucleo.service.mapper.IntensidadeChuvaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link IntensidadeChuva}.
 */
@Service
@Transactional
public class IntensidadeChuvaServiceImpl implements IntensidadeChuvaService {

    private final Logger log = LoggerFactory.getLogger(IntensidadeChuvaServiceImpl.class);

    private final IntensidadeChuvaRepository intensidadeChuvaRepository;

    private final IntensidadeChuvaMapper intensidadeChuvaMapper;

    public IntensidadeChuvaServiceImpl(IntensidadeChuvaRepository intensidadeChuvaRepository, IntensidadeChuvaMapper intensidadeChuvaMapper) {
        this.intensidadeChuvaRepository = intensidadeChuvaRepository;
        this.intensidadeChuvaMapper = intensidadeChuvaMapper;
    }

    @Override
    public IntensidadeChuvaDTO save(IntensidadeChuvaDTO intensidadeChuvaDTO) {
        log.debug("Request to save IntensidadeChuva : {}", intensidadeChuvaDTO);
        IntensidadeChuva intensidadeChuva = intensidadeChuvaMapper.toEntity(intensidadeChuvaDTO);
        intensidadeChuva = intensidadeChuvaRepository.save(intensidadeChuva);
        return intensidadeChuvaMapper.toDto(intensidadeChuva);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<IntensidadeChuvaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all IntensidadeChuvas");
        return intensidadeChuvaRepository.findAll(pageable)
            .map(intensidadeChuvaMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<IntensidadeChuvaDTO> findOne(Long id) {
        log.debug("Request to get IntensidadeChuva : {}", id);
        return intensidadeChuvaRepository.findById(id)
            .map(intensidadeChuvaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete IntensidadeChuva : {}", id);
        intensidadeChuvaRepository.deleteById(id);
    }
}
