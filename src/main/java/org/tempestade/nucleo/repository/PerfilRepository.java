package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.Perfil;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Perfil entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long>, JpaSpecificationExecutor<Perfil> {
}
