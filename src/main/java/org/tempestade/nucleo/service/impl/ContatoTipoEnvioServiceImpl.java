package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.ContatoTipoEnvioService;
import org.tempestade.nucleo.domain.ContatoTipoEnvio;
import org.tempestade.nucleo.repository.ContatoTipoEnvioRepository;
import org.tempestade.nucleo.service.dto.ContatoTipoEnvioDTO;
import org.tempestade.nucleo.service.mapper.ContatoTipoEnvioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ContatoTipoEnvio}.
 */
@Service
@Transactional
public class ContatoTipoEnvioServiceImpl implements ContatoTipoEnvioService {

    private final Logger log = LoggerFactory.getLogger(ContatoTipoEnvioServiceImpl.class);

    private final ContatoTipoEnvioRepository contatoTipoEnvioRepository;

    private final ContatoTipoEnvioMapper contatoTipoEnvioMapper;

    public ContatoTipoEnvioServiceImpl(ContatoTipoEnvioRepository contatoTipoEnvioRepository, ContatoTipoEnvioMapper contatoTipoEnvioMapper) {
        this.contatoTipoEnvioRepository = contatoTipoEnvioRepository;
        this.contatoTipoEnvioMapper = contatoTipoEnvioMapper;
    }

    @Override
    public ContatoTipoEnvioDTO save(ContatoTipoEnvioDTO contatoTipoEnvioDTO) {
        log.debug("Request to save ContatoTipoEnvio : {}", contatoTipoEnvioDTO);
        ContatoTipoEnvio contatoTipoEnvio = contatoTipoEnvioMapper.toEntity(contatoTipoEnvioDTO);
        contatoTipoEnvio = contatoTipoEnvioRepository.save(contatoTipoEnvio);
        return contatoTipoEnvioMapper.toDto(contatoTipoEnvio);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContatoTipoEnvioDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ContatoTipoEnvios");
        return contatoTipoEnvioRepository.findAll(pageable)
            .map(contatoTipoEnvioMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ContatoTipoEnvioDTO> findOne(Long id) {
        log.debug("Request to get ContatoTipoEnvio : {}", id);
        return contatoTipoEnvioRepository.findById(id)
            .map(contatoTipoEnvioMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ContatoTipoEnvio : {}", id);
        contatoTipoEnvioRepository.deleteById(id);
    }
}
