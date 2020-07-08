package org.tempestade.nucleo.service.impl;

import org.tempestade.nucleo.service.JornadaTrabalhoService;
import org.tempestade.nucleo.domain.JornadaTrabalho;
import org.tempestade.nucleo.repository.JornadaTrabalhoRepository;
import org.tempestade.nucleo.service.dto.JornadaTrabalhoDTO;
import org.tempestade.nucleo.service.mapper.JornadaTrabalhoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link JornadaTrabalho}.
 */
@Service
@Transactional
public class JornadaTrabalhoServiceImpl implements JornadaTrabalhoService {

    private final Logger log = LoggerFactory.getLogger(JornadaTrabalhoServiceImpl.class);

    private final JornadaTrabalhoRepository jornadaTrabalhoRepository;

    private final JornadaTrabalhoMapper jornadaTrabalhoMapper;

    public JornadaTrabalhoServiceImpl(JornadaTrabalhoRepository jornadaTrabalhoRepository, JornadaTrabalhoMapper jornadaTrabalhoMapper) {
        this.jornadaTrabalhoRepository = jornadaTrabalhoRepository;
        this.jornadaTrabalhoMapper = jornadaTrabalhoMapper;
    }

    @Override
    public JornadaTrabalhoDTO save(JornadaTrabalhoDTO jornadaTrabalhoDTO) {
        log.debug("Request to save JornadaTrabalho : {}", jornadaTrabalhoDTO);
        JornadaTrabalho jornadaTrabalho = jornadaTrabalhoMapper.toEntity(jornadaTrabalhoDTO);
        jornadaTrabalho = jornadaTrabalhoRepository.save(jornadaTrabalho);
        return jornadaTrabalhoMapper.toDto(jornadaTrabalho);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<JornadaTrabalhoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all JornadaTrabalhos");
        return jornadaTrabalhoRepository.findAll(pageable)
            .map(jornadaTrabalhoMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<JornadaTrabalhoDTO> findOne(Long id) {
        log.debug("Request to get JornadaTrabalho : {}", id);
        return jornadaTrabalhoRepository.findById(id)
            .map(jornadaTrabalhoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete JornadaTrabalho : {}", id);
        jornadaTrabalhoRepository.deleteById(id);
    }
}
