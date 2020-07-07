package org.tempestade.nucleo.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import io.github.jhipster.config.cache.PrefixedKeyGenerator;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {
    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, org.tempestade.nucleo.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, org.tempestade.nucleo.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, org.tempestade.nucleo.domain.User.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.Authority.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.User.class.getName() + ".authorities");
            createCache(cm, org.tempestade.nucleo.domain.AcumuladoChuvaFaixa.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.Alerta.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.AlertaFerramenta.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.AlertaRisco.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.AlertaTipo.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.Alvo.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.AlvoBloqueio.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.Aviso.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.AvisoMeteorologico.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.AvisoTipo.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.Boletim.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.BoletimPrevisao.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.BoletimPrevObs.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.BoletimPrevVariavelMet.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.CondicaoTempo.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.Consolidacao.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.Contato.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.ContatoAlvo.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.ContatoPlanoRecurso.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.ContatoTipoEnvio.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.Descarga.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.DescargaTipo.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.DescargaUnidade.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.DiaSemana.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.Empresa.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.Ferramenta.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.Informativo.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.Integrador.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.IntensidadeChuva.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.JornadaTrabalho.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.Layer.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.Log.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.Meteograma.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.Noticia.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.NotificacaoEnviada.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.Perfil.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.Plano.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.PlanoLayer.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.PlanoRecurso.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.PlanoRecursoTipoEnvio.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.PlanoRede.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.PlanoStatus.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.PlanoUsuario.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.PontosCardeais.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.Previsao.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.Recurso.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.RecursoTemplate.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.RecursoTipo.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.Rede.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.TempestadeNivel.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.TempestadeProbabilidade.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.TipoEnvio.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.TipoEnvioIntegrador.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.TipoFerramenta.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.UmidadeAr.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.Usuario.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.UsuarioPerfil.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.VariavelMeteorologica.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.VentoVmFaixa.class.getName());
            createCache(cm, org.tempestade.nucleo.domain.VoceSabia.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
