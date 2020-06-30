package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.UsuarioPerfil;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UsuarioPerfil entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UsuarioPerfilRepository extends JpaRepository<UsuarioPerfil, Long>, JpaSpecificationExecutor<UsuarioPerfil> {
}
