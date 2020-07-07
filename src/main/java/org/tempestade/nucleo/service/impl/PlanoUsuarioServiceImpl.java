package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.PlanoUsuarioService;
import org.tempestade.nucleo.domain.PlanoUsuario;
import org.tempestade.nucleo.repository.PlanoUsuarioRepository;
import org.tempestade.nucleo.service.dto.PlanoUsuarioDTO;
import org.tempestade.nucleo.service.mapper.PlanoUsuarioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PlanoUsuario}.
 */
@Service
@Transactional
public class PlanoUsuarioServiceImpl implements PlanoUsuarioService {

    private final Logger log = LoggerFactory.getLogger(PlanoUsuarioServiceImpl.class);

    private final PlanoUsuarioRepository planoUsuarioRepository;

    private final PlanoUsuarioMapper planoUsuarioMapper;

    public PlanoUsuarioServiceImpl(PlanoUsuarioRepository planoUsuarioRepository, PlanoUsuarioMapper planoUsuarioMapper) {
        this.planoUsuarioRepository = planoUsuarioRepository;
        this.planoUsuarioMapper = planoUsuarioMapper;
    }

    @Override
    public PlanoUsuarioDTO save(PlanoUsuarioDTO planoUsuarioDTO) {
        log.debug("Request to save PlanoUsuario : {}", planoUsuarioDTO);
        PlanoUsuario planoUsuario = planoUsuarioMapper.toEntity(planoUsuarioDTO);
        planoUsuario = planoUsuarioRepository.save(planoUsuario);
        return planoUsuarioMapper.toDto(planoUsuario);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PlanoUsuarioDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PlanoUsuarios");
        return planoUsuarioRepository.findAll(pageable)
            .map(planoUsuarioMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PlanoUsuarioDTO> findOne(Long id) {
        log.debug("Request to get PlanoUsuario : {}", id);
        return planoUsuarioRepository.findById(id)
            .map(planoUsuarioMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PlanoUsuario : {}", id);
        planoUsuarioRepository.deleteById(id);
    }
}
