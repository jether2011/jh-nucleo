package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.NoticiaService;
import org.tempestade.nucleo.domain.Noticia;
import org.tempestade.nucleo.repository.NoticiaRepository;
import org.tempestade.nucleo.service.dto.NoticiaDTO;
import org.tempestade.nucleo.service.mapper.NoticiaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Noticia}.
 */
@Service
@Transactional
public class NoticiaServiceImpl implements NoticiaService {

    private final Logger log = LoggerFactory.getLogger(NoticiaServiceImpl.class);

    private final NoticiaRepository noticiaRepository;

    private final NoticiaMapper noticiaMapper;

    public NoticiaServiceImpl(NoticiaRepository noticiaRepository, NoticiaMapper noticiaMapper) {
        this.noticiaRepository = noticiaRepository;
        this.noticiaMapper = noticiaMapper;
    }

    @Override
    public NoticiaDTO save(NoticiaDTO noticiaDTO) {
        log.debug("Request to save Noticia : {}", noticiaDTO);
        Noticia noticia = noticiaMapper.toEntity(noticiaDTO);
        noticia = noticiaRepository.save(noticia);
        return noticiaMapper.toDto(noticia);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NoticiaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Noticias");
        return noticiaRepository.findAll(pageable)
            .map(noticiaMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<NoticiaDTO> findOne(Long id) {
        log.debug("Request to get Noticia : {}", id);
        return noticiaRepository.findById(id)
            .map(noticiaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Noticia : {}", id);
        noticiaRepository.deleteById(id);
    }
}
