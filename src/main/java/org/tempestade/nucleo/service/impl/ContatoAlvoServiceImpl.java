package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.ContatoAlvoService;
import org.tempestade.nucleo.domain.ContatoAlvo;
import org.tempestade.nucleo.repository.ContatoAlvoRepository;
import org.tempestade.nucleo.service.dto.ContatoAlvoDTO;
import org.tempestade.nucleo.service.mapper.ContatoAlvoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ContatoAlvo}.
 */
@Service
@Transactional
public class ContatoAlvoServiceImpl implements ContatoAlvoService {

    private final Logger log = LoggerFactory.getLogger(ContatoAlvoServiceImpl.class);

    private final ContatoAlvoRepository contatoAlvoRepository;

    private final ContatoAlvoMapper contatoAlvoMapper;

    public ContatoAlvoServiceImpl(ContatoAlvoRepository contatoAlvoRepository, ContatoAlvoMapper contatoAlvoMapper) {
        this.contatoAlvoRepository = contatoAlvoRepository;
        this.contatoAlvoMapper = contatoAlvoMapper;
    }

    @Override
    public ContatoAlvoDTO save(ContatoAlvoDTO contatoAlvoDTO) {
        log.debug("Request to save ContatoAlvo : {}", contatoAlvoDTO);
        ContatoAlvo contatoAlvo = contatoAlvoMapper.toEntity(contatoAlvoDTO);
        contatoAlvo = contatoAlvoRepository.save(contatoAlvo);
        return contatoAlvoMapper.toDto(contatoAlvo);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContatoAlvoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ContatoAlvos");
        return contatoAlvoRepository.findAll(pageable)
            .map(contatoAlvoMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ContatoAlvoDTO> findOne(Long id) {
        log.debug("Request to get ContatoAlvo : {}", id);
        return contatoAlvoRepository.findById(id)
            .map(contatoAlvoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ContatoAlvo : {}", id);
        contatoAlvoRepository.deleteById(id);
    }
}
