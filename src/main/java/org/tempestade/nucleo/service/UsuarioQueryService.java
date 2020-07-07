package org.tempestade.nucleo.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import org.tempestade.nucleo.domain.Usuario;
import org.tempestade.nucleo.domain.*; // for static metamodels
import org.tempestade.nucleo.repository.UsuarioRepository;
import org.tempestade.nucleo.service.dto.UsuarioCriteria;
import org.tempestade.nucleo.service.dto.UsuarioDTO;
import org.tempestade.nucleo.service.mapper.UsuarioMapper;

/**
 * Service for executing complex queries for {@link Usuario} entities in the database.
 * The main input is a {@link UsuarioCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UsuarioDTO} or a {@link Page} of {@link UsuarioDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UsuarioQueryService extends QueryService<Usuario> {

    private final Logger log = LoggerFactory.getLogger(UsuarioQueryService.class);

    private final UsuarioRepository usuarioRepository;

    private final UsuarioMapper usuarioMapper;

    public UsuarioQueryService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    /**
     * Return a {@link List} of {@link UsuarioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UsuarioDTO> findByCriteria(UsuarioCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Usuario> specification = createSpecification(criteria);
        return usuarioMapper.toDto(usuarioRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link UsuarioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UsuarioDTO> findByCriteria(UsuarioCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Usuario> specification = createSpecification(criteria);
        return usuarioRepository.findAll(specification, page)
            .map(usuarioMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UsuarioCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Usuario> specification = createSpecification(criteria);
        return usuarioRepository.count(specification);
    }

    /**
     * Function to convert {@link UsuarioCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Usuario> createSpecification(UsuarioCriteria criteria) {
        Specification<Usuario> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Usuario_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Usuario_.name));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), Usuario_.descricao));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Usuario_.email));
            }
            if (criteria.getSenha() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSenha(), Usuario_.senha));
            }
            if (criteria.getCnpj() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCnpj(), Usuario_.cnpj));
            }
            if (criteria.getCpf() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCpf(), Usuario_.cpf));
            }
            if (criteria.getCep() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCep(), Usuario_.cep));
            }
            if (criteria.getEndereco() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEndereco(), Usuario_.endereco));
            }
            if (criteria.getNumero() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumero(), Usuario_.numero));
            }
            if (criteria.getBairro() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBairro(), Usuario_.bairro));
            }
            if (criteria.getCidade() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCidade(), Usuario_.cidade));
            }
            if (criteria.getEstado() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEstado(), Usuario_.estado));
            }
            if (criteria.getTelefone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelefone(), Usuario_.telefone));
            }
            if (criteria.getFax() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFax(), Usuario_.fax));
            }
            if (criteria.getCelular() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCelular(), Usuario_.celular));
            }
            if (criteria.getDetalhe() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDetalhe(), Usuario_.detalhe));
            }
            if (criteria.getBloqueado() != null) {
                specification = specification.and(buildSpecification(criteria.getBloqueado(), Usuario_.bloqueado));
            }
            if (criteria.getComplemento() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComplemento(), Usuario_.complemento));
            }
            if (criteria.getNaoPodeExcluir() != null) {
                specification = specification.and(buildSpecification(criteria.getNaoPodeExcluir(), Usuario_.naoPodeExcluir));
            }
            if (criteria.getUltimoAcesso() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUltimoAcesso(), Usuario_.ultimoAcesso));
            }
            if (criteria.getSenhaFirebase() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSenhaFirebase(), Usuario_.senhaFirebase));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), Usuario_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), Usuario_.updated));
            }
        }
        return specification;
    }
}
