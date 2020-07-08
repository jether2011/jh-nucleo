package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.ContatoTipoEnvio;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ContatoTipoEnvio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContatoTipoEnvioRepository extends JpaRepository<ContatoTipoEnvio, Long> {
}
