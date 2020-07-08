package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.ContatoPlanoRecursoService;
import org.tempestade.nucleo.domain.ContatoPlanoRecurso;
import org.tempestade.nucleo.repository.ContatoPlanoRecursoRepository;
import org.tempestade.nucleo.service.dto.ContatoPlanoRecursoDTO;
import org.tempestade.nucleo.service.mapper.ContatoPlanoRecursoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ContatoPlanoRecurso}.
 */
@Service
@Transactional
public class ContatoPlanoRecursoServiceImpl implements ContatoPlanoRecursoService {

    private final Logger log = LoggerFactory.getLogger(ContatoPlanoRecursoServiceImpl.class);

    private final ContatoPlanoRecursoRepository contatoPlanoRecursoRepository;

    private final ContatoPlanoRecursoMapper contatoPlanoRecursoMapper;

    public ContatoPlanoRecursoServiceImpl(ContatoPlanoRecursoRepository contatoPlanoRecursoRepository, ContatoPlanoRecursoMapper contatoPlanoRecursoMapper) {
        this.contatoPlanoRecursoRepository = contatoPlanoRecursoRepository;
        this.contatoPlanoRecursoMapper = contatoPlanoRecursoMapper;
    }

    @Override
    public ContatoPlanoRecursoDTO save(ContatoPlanoRecursoDTO contatoPlanoRecursoDTO) {
        log.debug("Request to save ContatoPlanoRecurso : {}", contatoPlanoRecursoDTO);
        ContatoPlanoRecurso contatoPlanoRecurso = contatoPlanoRecursoMapper.toEntity(contatoPlanoRecursoDTO);
        contatoPlanoRecurso = contatoPlanoRecursoRepository.save(contatoPlanoRecurso);
        return contatoPlanoRecursoMapper.toDto(contatoPlanoRecurso);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContatoPlanoRecursoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ContatoPlanoRecursos");
        return contatoPlanoRecursoRepository.findAll(pageable)
            .map(contatoPlanoRecursoMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ContatoPlanoRecursoDTO> findOne(Long id) {
        log.debug("Request to get ContatoPlanoRecurso : {}", id);
        return contatoPlanoRecursoRepository.findById(id)
            .map(contatoPlanoRecursoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ContatoPlanoRecurso : {}", id);
        contatoPlanoRecursoRepository.deleteById(id);
    }
}
