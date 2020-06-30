package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.PlanoUsuario;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PlanoUsuario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlanoUsuarioRepository extends JpaRepository<PlanoUsuario, Long>, JpaSpecificationExecutor<PlanoUsuario> {
}
