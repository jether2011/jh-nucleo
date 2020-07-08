package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.UmidadeArService;
import org.tempestade.nucleo.domain.UmidadeAr;
import org.tempestade.nucleo.repository.UmidadeArRepository;
import org.tempestade.nucleo.service.dto.UmidadeArDTO;
import org.tempestade.nucleo.service.mapper.UmidadeArMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UmidadeAr}.
 */
@Service
@Transactional
public class UmidadeArServiceImpl implements UmidadeArService {

    private final Logger log = LoggerFactory.getLogger(UmidadeArServiceImpl.class);

    private final UmidadeArRepository umidadeArRepository;

    private final UmidadeArMapper umidadeArMapper;

    public UmidadeArServiceImpl(UmidadeArRepository umidadeArRepository, UmidadeArMapper umidadeArMapper) {
        this.umidadeArRepository = umidadeArRepository;
        this.umidadeArMapper = umidadeArMapper;
    }

    @Override
    public UmidadeArDTO save(UmidadeArDTO umidadeArDTO) {
        log.debug("Request to save UmidadeAr : {}", umidadeArDTO);
        UmidadeAr umidadeAr = umidadeArMapper.toEntity(umidadeArDTO);
        umidadeAr = umidadeArRepository.save(umidadeAr);
        return umidadeArMapper.toDto(umidadeAr);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UmidadeArDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UmidadeArs");
        return umidadeArRepository.findAll(pageable)
            .map(umidadeArMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UmidadeArDTO> findOne(Long id) {
        log.debug("Request to get UmidadeAr : {}", id);
        return umidadeArRepository.findById(id)
            .map(umidadeArMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UmidadeAr : {}", id);
        umidadeArRepository.deleteById(id);
    }
}
