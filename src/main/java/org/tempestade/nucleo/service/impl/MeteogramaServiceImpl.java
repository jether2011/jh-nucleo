package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.MeteogramaService;
import org.tempestade.nucleo.domain.Meteograma;
import org.tempestade.nucleo.repository.MeteogramaRepository;
import org.tempestade.nucleo.service.dto.MeteogramaDTO;
import org.tempestade.nucleo.service.mapper.MeteogramaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Meteograma}.
 */
@Service
@Transactional
public class MeteogramaServiceImpl implements MeteogramaService {

    private final Logger log = LoggerFactory.getLogger(MeteogramaServiceImpl.class);

    private final MeteogramaRepository meteogramaRepository;

    private final MeteogramaMapper meteogramaMapper;

    public MeteogramaServiceImpl(MeteogramaRepository meteogramaRepository, MeteogramaMapper meteogramaMapper) {
        this.meteogramaRepository = meteogramaRepository;
        this.meteogramaMapper = meteogramaMapper;
    }

    @Override
    public MeteogramaDTO save(MeteogramaDTO meteogramaDTO) {
        log.debug("Request to save Meteograma : {}", meteogramaDTO);
        Meteograma meteograma = meteogramaMapper.toEntity(meteogramaDTO);
        meteograma = meteogramaRepository.save(meteograma);
        return meteogramaMapper.toDto(meteograma);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MeteogramaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Meteogramas");
        return meteogramaRepository.findAll(pageable)
            .map(meteogramaMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<MeteogramaDTO> findOne(Long id) {
        log.debug("Request to get Meteograma : {}", id);
        return meteogramaRepository.findById(id)
            .map(meteogramaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Meteograma : {}", id);
        meteogramaRepository.deleteById(id);
    }
}
