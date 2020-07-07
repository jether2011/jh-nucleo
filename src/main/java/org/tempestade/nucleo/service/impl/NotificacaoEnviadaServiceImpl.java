package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.NotificacaoEnviadaService;
import org.tempestade.nucleo.domain.NotificacaoEnviada;
import org.tempestade.nucleo.repository.NotificacaoEnviadaRepository;
import org.tempestade.nucleo.service.dto.NotificacaoEnviadaDTO;
import org.tempestade.nucleo.service.mapper.NotificacaoEnviadaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link NotificacaoEnviada}.
 */
@Service
@Transactional
public class NotificacaoEnviadaServiceImpl implements NotificacaoEnviadaService {

    private final Logger log = LoggerFactory.getLogger(NotificacaoEnviadaServiceImpl.class);

    private final NotificacaoEnviadaRepository notificacaoEnviadaRepository;

    private final NotificacaoEnviadaMapper notificacaoEnviadaMapper;

    public NotificacaoEnviadaServiceImpl(NotificacaoEnviadaRepository notificacaoEnviadaRepository, NotificacaoEnviadaMapper notificacaoEnviadaMapper) {
        this.notificacaoEnviadaRepository = notificacaoEnviadaRepository;
        this.notificacaoEnviadaMapper = notificacaoEnviadaMapper;
    }

    @Override
    public NotificacaoEnviadaDTO save(NotificacaoEnviadaDTO notificacaoEnviadaDTO) {
        log.debug("Request to save NotificacaoEnviada : {}", notificacaoEnviadaDTO);
        NotificacaoEnviada notificacaoEnviada = notificacaoEnviadaMapper.toEntity(notificacaoEnviadaDTO);
        notificacaoEnviada = notificacaoEnviadaRepository.save(notificacaoEnviada);
        return notificacaoEnviadaMapper.toDto(notificacaoEnviada);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NotificacaoEnviadaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all NotificacaoEnviadas");
        return notificacaoEnviadaRepository.findAll(pageable)
            .map(notificacaoEnviadaMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<NotificacaoEnviadaDTO> findOne(Long id) {
        log.debug("Request to get NotificacaoEnviada : {}", id);
        return notificacaoEnviadaRepository.findById(id)
            .map(notificacaoEnviadaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete NotificacaoEnviada : {}", id);
        notificacaoEnviadaRepository.deleteById(id);
    }
}
