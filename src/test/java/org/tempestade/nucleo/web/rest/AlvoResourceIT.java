package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.Alvo;
import org.tempestade.nucleo.repository.AlvoRepository;
import org.tempestade.nucleo.service.AlvoService;
import org.tempestade.nucleo.service.dto.AlvoDTO;
import org.tempestade.nucleo.service.mapper.AlvoMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AlvoResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AlvoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_REDUZIDO = "AAAAAAAAAA";
    private static final String UPDATED_NOME_REDUZIDO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_PRIMEIRO_PONTO = "AAAAAAAAAA";
    private static final String UPDATED_PRIMEIRO_PONTO = "BBBBBBBBBB";

    private static final String DEFAULT_ULTIMO_PONTO = "AAAAAAAAAA";
    private static final String UPDATED_ULTIMO_PONTO = "BBBBBBBBBB";

    private static final Instant DEFAULT_HORARIO_LIBERACAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HORARIO_LIBERACAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_HORARIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HORARIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DURACAO = "22:14:18";
    private static final String UPDATED_DURACAO = "20:48:51";

    private static final String DEFAULT_DURACAO_ATUAL = "12:28:44";
    private static final String UPDATED_DURACAO_ATUAL = "13:36:11";

    private static final Instant DEFAULT_DATA_DESATIVADO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_DESATIVADO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_COORDENADAS_ALERTA_PONTOS = "AAAAAAAAAA";
    private static final String UPDATED_COORDENADAS_ALERTA_PONTOS = "BBBBBBBBBB";

    private static final String DEFAULT_COORDENADAS_LIBERACAO_PONTOS = "AAAAAAAAAA";
    private static final String UPDATED_COORDENADAS_LIBERACAO_PONTOS = "BBBBBBBBBB";

    private static final String DEFAULT_TELEGRAM_TOKEN_BOT = "AAAAAAAAAA";
    private static final String UPDATED_TELEGRAM_TOKEN_BOT = "BBBBBBBBBB";

    private static final String DEFAULT_TELEGRAM_CHAT_ID = "AAAAAAAAAA";
    private static final String UPDATED_TELEGRAM_CHAT_ID = "BBBBBBBBBB";

    private static final Instant DEFAULT_HORARIO_BLOQUEIO_NOTIFICACAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HORARIO_BLOQUEIO_NOTIFICACAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_COORDENADAS_ORIGINAL_PONTOS = "AAAAAAAAAA";
    private static final String UPDATED_COORDENADAS_ORIGINAL_PONTOS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ATIVO = false;
    private static final Boolean UPDATED_ATIVO = true;

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private AlvoRepository alvoRepository;

    @Autowired
    private AlvoMapper alvoMapper;

    @Autowired
    private AlvoService alvoService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAlvoMockMvc;

    private Alvo alvo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Alvo createEntity(EntityManager em) {
        Alvo alvo = new Alvo()
            .nome(DEFAULT_NOME)
            .nomeReduzido(DEFAULT_NOME_REDUZIDO)
            .descricao(DEFAULT_DESCRICAO)
            .primeiroPonto(DEFAULT_PRIMEIRO_PONTO)
            .ultimoPonto(DEFAULT_ULTIMO_PONTO)
            .horarioLiberacao(DEFAULT_HORARIO_LIBERACAO)
            .horario(DEFAULT_HORARIO)
            .duracao(DEFAULT_DURACAO)
            .duracaoAtual(DEFAULT_DURACAO_ATUAL)
            .dataDesativado(DEFAULT_DATA_DESATIVADO)
            .coordenadasAlertaPontos(DEFAULT_COORDENADAS_ALERTA_PONTOS)
            .coordenadasLiberacaoPontos(DEFAULT_COORDENADAS_LIBERACAO_PONTOS)
            .telegramTokenBot(DEFAULT_TELEGRAM_TOKEN_BOT)
            .telegramChatId(DEFAULT_TELEGRAM_CHAT_ID)
            .horarioBloqueioNotificacao(DEFAULT_HORARIO_BLOQUEIO_NOTIFICACAO)
            .coordenadasOriginalPontos(DEFAULT_COORDENADAS_ORIGINAL_PONTOS)
            .ativo(DEFAULT_ATIVO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return alvo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Alvo createUpdatedEntity(EntityManager em) {
        Alvo alvo = new Alvo()
            .nome(UPDATED_NOME)
            .nomeReduzido(UPDATED_NOME_REDUZIDO)
            .descricao(UPDATED_DESCRICAO)
            .primeiroPonto(UPDATED_PRIMEIRO_PONTO)
            .ultimoPonto(UPDATED_ULTIMO_PONTO)
            .horarioLiberacao(UPDATED_HORARIO_LIBERACAO)
            .horario(UPDATED_HORARIO)
            .duracao(UPDATED_DURACAO)
            .duracaoAtual(UPDATED_DURACAO_ATUAL)
            .dataDesativado(UPDATED_DATA_DESATIVADO)
            .coordenadasAlertaPontos(UPDATED_COORDENADAS_ALERTA_PONTOS)
            .coordenadasLiberacaoPontos(UPDATED_COORDENADAS_LIBERACAO_PONTOS)
            .telegramTokenBot(UPDATED_TELEGRAM_TOKEN_BOT)
            .telegramChatId(UPDATED_TELEGRAM_CHAT_ID)
            .horarioBloqueioNotificacao(UPDATED_HORARIO_BLOQUEIO_NOTIFICACAO)
            .coordenadasOriginalPontos(UPDATED_COORDENADAS_ORIGINAL_PONTOS)
            .ativo(UPDATED_ATIVO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return alvo;
    }

    @BeforeEach
    public void initTest() {
        alvo = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlvo() throws Exception {
        int databaseSizeBeforeCreate = alvoRepository.findAll().size();
        // Create the Alvo
        AlvoDTO alvoDTO = alvoMapper.toDto(alvo);
        restAlvoMockMvc.perform(post("/api/alvos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alvoDTO)))
            .andExpect(status().isCreated());

        // Validate the Alvo in the database
        List<Alvo> alvoList = alvoRepository.findAll();
        assertThat(alvoList).hasSize(databaseSizeBeforeCreate + 1);
        Alvo testAlvo = alvoList.get(alvoList.size() - 1);
        assertThat(testAlvo.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testAlvo.getNomeReduzido()).isEqualTo(DEFAULT_NOME_REDUZIDO);
        assertThat(testAlvo.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testAlvo.getPrimeiroPonto()).isEqualTo(DEFAULT_PRIMEIRO_PONTO);
        assertThat(testAlvo.getUltimoPonto()).isEqualTo(DEFAULT_ULTIMO_PONTO);
        assertThat(testAlvo.getHorarioLiberacao()).isEqualTo(DEFAULT_HORARIO_LIBERACAO);
        assertThat(testAlvo.getHorario()).isEqualTo(DEFAULT_HORARIO);
        assertThat(testAlvo.getDuracao()).isEqualTo(DEFAULT_DURACAO);
        assertThat(testAlvo.getDuracaoAtual()).isEqualTo(DEFAULT_DURACAO_ATUAL);
        assertThat(testAlvo.getDataDesativado()).isEqualTo(DEFAULT_DATA_DESATIVADO);
        assertThat(testAlvo.getCoordenadasAlertaPontos()).isEqualTo(DEFAULT_COORDENADAS_ALERTA_PONTOS);
        assertThat(testAlvo.getCoordenadasLiberacaoPontos()).isEqualTo(DEFAULT_COORDENADAS_LIBERACAO_PONTOS);
        assertThat(testAlvo.getTelegramTokenBot()).isEqualTo(DEFAULT_TELEGRAM_TOKEN_BOT);
        assertThat(testAlvo.getTelegramChatId()).isEqualTo(DEFAULT_TELEGRAM_CHAT_ID);
        assertThat(testAlvo.getHorarioBloqueioNotificacao()).isEqualTo(DEFAULT_HORARIO_BLOQUEIO_NOTIFICACAO);
        assertThat(testAlvo.getCoordenadasOriginalPontos()).isEqualTo(DEFAULT_COORDENADAS_ORIGINAL_PONTOS);
        assertThat(testAlvo.isAtivo()).isEqualTo(DEFAULT_ATIVO);
        assertThat(testAlvo.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testAlvo.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createAlvoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alvoRepository.findAll().size();

        // Create the Alvo with an existing ID
        alvo.setId(1L);
        AlvoDTO alvoDTO = alvoMapper.toDto(alvo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlvoMockMvc.perform(post("/api/alvos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alvoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alvo in the database
        List<Alvo> alvoList = alvoRepository.findAll();
        assertThat(alvoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = alvoRepository.findAll().size();
        // set the field null
        alvo.setNome(null);

        // Create the Alvo, which fails.
        AlvoDTO alvoDTO = alvoMapper.toDto(alvo);


        restAlvoMockMvc.perform(post("/api/alvos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alvoDTO)))
            .andExpect(status().isBadRequest());

        List<Alvo> alvoList = alvoRepository.findAll();
        assertThat(alvoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHorarioLiberacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = alvoRepository.findAll().size();
        // set the field null
        alvo.setHorarioLiberacao(null);

        // Create the Alvo, which fails.
        AlvoDTO alvoDTO = alvoMapper.toDto(alvo);


        restAlvoMockMvc.perform(post("/api/alvos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alvoDTO)))
            .andExpect(status().isBadRequest());

        List<Alvo> alvoList = alvoRepository.findAll();
        assertThat(alvoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHorarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = alvoRepository.findAll().size();
        // set the field null
        alvo.setHorario(null);

        // Create the Alvo, which fails.
        AlvoDTO alvoDTO = alvoMapper.toDto(alvo);


        restAlvoMockMvc.perform(post("/api/alvos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alvoDTO)))
            .andExpect(status().isBadRequest());

        List<Alvo> alvoList = alvoRepository.findAll();
        assertThat(alvoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataDesativadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = alvoRepository.findAll().size();
        // set the field null
        alvo.setDataDesativado(null);

        // Create the Alvo, which fails.
        AlvoDTO alvoDTO = alvoMapper.toDto(alvo);


        restAlvoMockMvc.perform(post("/api/alvos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alvoDTO)))
            .andExpect(status().isBadRequest());

        List<Alvo> alvoList = alvoRepository.findAll();
        assertThat(alvoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHorarioBloqueioNotificacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = alvoRepository.findAll().size();
        // set the field null
        alvo.setHorarioBloqueioNotificacao(null);

        // Create the Alvo, which fails.
        AlvoDTO alvoDTO = alvoMapper.toDto(alvo);


        restAlvoMockMvc.perform(post("/api/alvos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alvoDTO)))
            .andExpect(status().isBadRequest());

        List<Alvo> alvoList = alvoRepository.findAll();
        assertThat(alvoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = alvoRepository.findAll().size();
        // set the field null
        alvo.setCreated(null);

        // Create the Alvo, which fails.
        AlvoDTO alvoDTO = alvoMapper.toDto(alvo);


        restAlvoMockMvc.perform(post("/api/alvos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alvoDTO)))
            .andExpect(status().isBadRequest());

        List<Alvo> alvoList = alvoRepository.findAll();
        assertThat(alvoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAlvos() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList
        restAlvoMockMvc.perform(get("/api/alvos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alvo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].nomeReduzido").value(hasItem(DEFAULT_NOME_REDUZIDO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].primeiroPonto").value(hasItem(DEFAULT_PRIMEIRO_PONTO)))
            .andExpect(jsonPath("$.[*].ultimoPonto").value(hasItem(DEFAULT_ULTIMO_PONTO)))
            .andExpect(jsonPath("$.[*].horarioLiberacao").value(hasItem(DEFAULT_HORARIO_LIBERACAO.toString())))
            .andExpect(jsonPath("$.[*].horario").value(hasItem(DEFAULT_HORARIO.toString())))
            .andExpect(jsonPath("$.[*].duracao").value(hasItem(DEFAULT_DURACAO)))
            .andExpect(jsonPath("$.[*].duracaoAtual").value(hasItem(DEFAULT_DURACAO_ATUAL)))
            .andExpect(jsonPath("$.[*].dataDesativado").value(hasItem(DEFAULT_DATA_DESATIVADO.toString())))
            .andExpect(jsonPath("$.[*].coordenadasAlertaPontos").value(hasItem(DEFAULT_COORDENADAS_ALERTA_PONTOS)))
            .andExpect(jsonPath("$.[*].coordenadasLiberacaoPontos").value(hasItem(DEFAULT_COORDENADAS_LIBERACAO_PONTOS)))
            .andExpect(jsonPath("$.[*].telegramTokenBot").value(hasItem(DEFAULT_TELEGRAM_TOKEN_BOT)))
            .andExpect(jsonPath("$.[*].telegramChatId").value(hasItem(DEFAULT_TELEGRAM_CHAT_ID)))
            .andExpect(jsonPath("$.[*].horarioBloqueioNotificacao").value(hasItem(DEFAULT_HORARIO_BLOQUEIO_NOTIFICACAO.toString())))
            .andExpect(jsonPath("$.[*].coordenadasOriginalPontos").value(hasItem(DEFAULT_COORDENADAS_ORIGINAL_PONTOS)))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getAlvo() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get the alvo
        restAlvoMockMvc.perform(get("/api/alvos/{id}", alvo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(alvo.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.nomeReduzido").value(DEFAULT_NOME_REDUZIDO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.primeiroPonto").value(DEFAULT_PRIMEIRO_PONTO))
            .andExpect(jsonPath("$.ultimoPonto").value(DEFAULT_ULTIMO_PONTO))
            .andExpect(jsonPath("$.horarioLiberacao").value(DEFAULT_HORARIO_LIBERACAO.toString()))
            .andExpect(jsonPath("$.horario").value(DEFAULT_HORARIO.toString()))
            .andExpect(jsonPath("$.duracao").value(DEFAULT_DURACAO))
            .andExpect(jsonPath("$.duracaoAtual").value(DEFAULT_DURACAO_ATUAL))
            .andExpect(jsonPath("$.dataDesativado").value(DEFAULT_DATA_DESATIVADO.toString()))
            .andExpect(jsonPath("$.coordenadasAlertaPontos").value(DEFAULT_COORDENADAS_ALERTA_PONTOS))
            .andExpect(jsonPath("$.coordenadasLiberacaoPontos").value(DEFAULT_COORDENADAS_LIBERACAO_PONTOS))
            .andExpect(jsonPath("$.telegramTokenBot").value(DEFAULT_TELEGRAM_TOKEN_BOT))
            .andExpect(jsonPath("$.telegramChatId").value(DEFAULT_TELEGRAM_CHAT_ID))
            .andExpect(jsonPath("$.horarioBloqueioNotificacao").value(DEFAULT_HORARIO_BLOQUEIO_NOTIFICACAO.toString()))
            .andExpect(jsonPath("$.coordenadasOriginalPontos").value(DEFAULT_COORDENADAS_ORIGINAL_PONTOS))
            .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingAlvo() throws Exception {
        // Get the alvo
        restAlvoMockMvc.perform(get("/api/alvos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlvo() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        int databaseSizeBeforeUpdate = alvoRepository.findAll().size();

        // Update the alvo
        Alvo updatedAlvo = alvoRepository.findById(alvo.getId()).get();
        // Disconnect from session so that the updates on updatedAlvo are not directly saved in db
        em.detach(updatedAlvo);
        updatedAlvo
            .nome(UPDATED_NOME)
            .nomeReduzido(UPDATED_NOME_REDUZIDO)
            .descricao(UPDATED_DESCRICAO)
            .primeiroPonto(UPDATED_PRIMEIRO_PONTO)
            .ultimoPonto(UPDATED_ULTIMO_PONTO)
            .horarioLiberacao(UPDATED_HORARIO_LIBERACAO)
            .horario(UPDATED_HORARIO)
            .duracao(UPDATED_DURACAO)
            .duracaoAtual(UPDATED_DURACAO_ATUAL)
            .dataDesativado(UPDATED_DATA_DESATIVADO)
            .coordenadasAlertaPontos(UPDATED_COORDENADAS_ALERTA_PONTOS)
            .coordenadasLiberacaoPontos(UPDATED_COORDENADAS_LIBERACAO_PONTOS)
            .telegramTokenBot(UPDATED_TELEGRAM_TOKEN_BOT)
            .telegramChatId(UPDATED_TELEGRAM_CHAT_ID)
            .horarioBloqueioNotificacao(UPDATED_HORARIO_BLOQUEIO_NOTIFICACAO)
            .coordenadasOriginalPontos(UPDATED_COORDENADAS_ORIGINAL_PONTOS)
            .ativo(UPDATED_ATIVO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        AlvoDTO alvoDTO = alvoMapper.toDto(updatedAlvo);

        restAlvoMockMvc.perform(put("/api/alvos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alvoDTO)))
            .andExpect(status().isOk());

        // Validate the Alvo in the database
        List<Alvo> alvoList = alvoRepository.findAll();
        assertThat(alvoList).hasSize(databaseSizeBeforeUpdate);
        Alvo testAlvo = alvoList.get(alvoList.size() - 1);
        assertThat(testAlvo.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testAlvo.getNomeReduzido()).isEqualTo(UPDATED_NOME_REDUZIDO);
        assertThat(testAlvo.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testAlvo.getPrimeiroPonto()).isEqualTo(UPDATED_PRIMEIRO_PONTO);
        assertThat(testAlvo.getUltimoPonto()).isEqualTo(UPDATED_ULTIMO_PONTO);
        assertThat(testAlvo.getHorarioLiberacao()).isEqualTo(UPDATED_HORARIO_LIBERACAO);
        assertThat(testAlvo.getHorario()).isEqualTo(UPDATED_HORARIO);
        assertThat(testAlvo.getDuracao()).isEqualTo(UPDATED_DURACAO);
        assertThat(testAlvo.getDuracaoAtual()).isEqualTo(UPDATED_DURACAO_ATUAL);
        assertThat(testAlvo.getDataDesativado()).isEqualTo(UPDATED_DATA_DESATIVADO);
        assertThat(testAlvo.getCoordenadasAlertaPontos()).isEqualTo(UPDATED_COORDENADAS_ALERTA_PONTOS);
        assertThat(testAlvo.getCoordenadasLiberacaoPontos()).isEqualTo(UPDATED_COORDENADAS_LIBERACAO_PONTOS);
        assertThat(testAlvo.getTelegramTokenBot()).isEqualTo(UPDATED_TELEGRAM_TOKEN_BOT);
        assertThat(testAlvo.getTelegramChatId()).isEqualTo(UPDATED_TELEGRAM_CHAT_ID);
        assertThat(testAlvo.getHorarioBloqueioNotificacao()).isEqualTo(UPDATED_HORARIO_BLOQUEIO_NOTIFICACAO);
        assertThat(testAlvo.getCoordenadasOriginalPontos()).isEqualTo(UPDATED_COORDENADAS_ORIGINAL_PONTOS);
        assertThat(testAlvo.isAtivo()).isEqualTo(UPDATED_ATIVO);
        assertThat(testAlvo.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testAlvo.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingAlvo() throws Exception {
        int databaseSizeBeforeUpdate = alvoRepository.findAll().size();

        // Create the Alvo
        AlvoDTO alvoDTO = alvoMapper.toDto(alvo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlvoMockMvc.perform(put("/api/alvos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alvoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alvo in the database
        List<Alvo> alvoList = alvoRepository.findAll();
        assertThat(alvoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAlvo() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        int databaseSizeBeforeDelete = alvoRepository.findAll().size();

        // Delete the alvo
        restAlvoMockMvc.perform(delete("/api/alvos/{id}", alvo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Alvo> alvoList = alvoRepository.findAll();
        assertThat(alvoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
