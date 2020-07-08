package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.UsuarioPerfilService;
import org.tempestade.nucleo.domain.UsuarioPerfil;
import org.tempestade.nucleo.repository.UsuarioPerfilRepository;
import org.tempestade.nucleo.service.dto.UsuarioPerfilDTO;
import org.tempestade.nucleo.service.mapper.UsuarioPerfilMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UsuarioPerfil}.
 */
@Service
@Transactional
public class UsuarioPerfilServiceImpl implements UsuarioPerfilService {

    private final Logger log = LoggerFactory.getLogger(UsuarioPerfilServiceImpl.class);

    private final UsuarioPerfilRepository usuarioPerfilRepository;

    private final UsuarioPerfilMapper usuarioPerfilMapper;

    public UsuarioPerfilServiceImpl(UsuarioPerfilRepository usuarioPerfilRepository, UsuarioPerfilMapper usuarioPerfilMapper) {
        this.usuarioPerfilRepository = usuarioPerfilRepository;
        this.usuarioPerfilMapper = usuarioPerfilMapper;
    }

    @Override
    public UsuarioPerfilDTO save(UsuarioPerfilDTO usuarioPerfilDTO) {
        log.debug("Request to save UsuarioPerfil : {}", usuarioPerfilDTO);
        UsuarioPerfil usuarioPerfil = usuarioPerfilMapper.toEntity(usuarioPerfilDTO);
        usuarioPerfil = usuarioPerfilRepository.save(usuarioPerfil);
        return usuarioPerfilMapper.toDto(usuarioPerfil);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UsuarioPerfilDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UsuarioPerfils");
        return usuarioPerfilRepository.findAll(pageable)
            .map(usuarioPerfilMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UsuarioPerfilDTO> findOne(Long id) {
        log.debug("Request to get UsuarioPerfil : {}", id);
        return usuarioPerfilRepository.findById(id)
            .map(usuarioPerfilMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UsuarioPerfil : {}", id);
        usuarioPerfilRepository.deleteById(id);
    }
}
