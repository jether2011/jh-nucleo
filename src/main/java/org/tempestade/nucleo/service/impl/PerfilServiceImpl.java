package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.PerfilService;
import org.tempestade.nucleo.domain.Perfil;
import org.tempestade.nucleo.repository.PerfilRepository;
import org.tempestade.nucleo.service.dto.PerfilDTO;
import org.tempestade.nucleo.service.mapper.PerfilMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Perfil}.
 */
@Service
@Transactional
public class PerfilServiceImpl implements PerfilService {

    private final Logger log = LoggerFactory.getLogger(PerfilServiceImpl.class);

    private final PerfilRepository perfilRepository;

    private final PerfilMapper perfilMapper;

    public PerfilServiceImpl(PerfilRepository perfilRepository, PerfilMapper perfilMapper) {
        this.perfilRepository = perfilRepository;
        this.perfilMapper = perfilMapper;
    }

    @Override
    public PerfilDTO save(PerfilDTO perfilDTO) {
        log.debug("Request to save Perfil : {}", perfilDTO);
        Perfil perfil = perfilMapper.toEntity(perfilDTO);
        perfil = perfilRepository.save(perfil);
        return perfilMapper.toDto(perfil);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PerfilDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Perfils");
        return perfilRepository.findAll(pageable)
            .map(perfilMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PerfilDTO> findOne(Long id) {
        log.debug("Request to get Perfil : {}", id);
        return perfilRepository.findById(id)
            .map(perfilMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Perfil : {}", id);
        perfilRepository.deleteById(id);
    }
}
