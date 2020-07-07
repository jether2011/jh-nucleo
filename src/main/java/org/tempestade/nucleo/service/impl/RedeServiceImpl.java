package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.RedeService;
import org.tempestade.nucleo.domain.Rede;
import org.tempestade.nucleo.repository.RedeRepository;
import org.tempestade.nucleo.service.dto.RedeDTO;
import org.tempestade.nucleo.service.mapper.RedeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Rede}.
 */
@Service
@Transactional
public class RedeServiceImpl implements RedeService {

    private final Logger log = LoggerFactory.getLogger(RedeServiceImpl.class);

    private final RedeRepository redeRepository;

    private final RedeMapper redeMapper;

    public RedeServiceImpl(RedeRepository redeRepository, RedeMapper redeMapper) {
        this.redeRepository = redeRepository;
        this.redeMapper = redeMapper;
    }

    @Override
    public RedeDTO save(RedeDTO redeDTO) {
        log.debug("Request to save Rede : {}", redeDTO);
        Rede rede = redeMapper.toEntity(redeDTO);
        rede = redeRepository.save(rede);
        return redeMapper.toDto(rede);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RedeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Redes");
        return redeRepository.findAll(pageable)
            .map(redeMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<RedeDTO> findOne(Long id) {
        log.debug("Request to get Rede : {}", id);
        return redeRepository.findById(id)
            .map(redeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Rede : {}", id);
        redeRepository.deleteById(id);
    }
}
