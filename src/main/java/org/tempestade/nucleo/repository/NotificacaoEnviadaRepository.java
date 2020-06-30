package org.tempestade.nucleo.repository;

import org.tempestade.nucleo.domain.NotificacaoEnviada;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the NotificacaoEnviada entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NotificacaoEnviadaRepository extends JpaRepository<NotificacaoEnviada, Long>, JpaSpecificationExecutor<NotificacaoEnviada> {
}
