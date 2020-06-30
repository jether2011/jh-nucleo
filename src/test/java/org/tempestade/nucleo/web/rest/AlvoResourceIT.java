package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.Alvo;
import org.tempestade.nucleo.domain.Plano;
import org.tempestade.nucleo.repository.AlvoRepository;
import org.tempestade.nucleo.service.AlvoService;
import org.tempestade.nucleo.service.dto.AlvoDTO;
import org.tempestade.nucleo.service.mapper.AlvoMapper;
import org.tempestade.nucleo.service.dto.AlvoCriteria;
import org.tempestade.nucleo.service.AlvoQueryService;

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
    private AlvoQueryService alvoQueryService;

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
    public void getAlvosByIdFiltering() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        Long id = alvo.getId();

        defaultAlvoShouldBeFound("id.equals=" + id);
        defaultAlvoShouldNotBeFound("id.notEquals=" + id);

        defaultAlvoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAlvoShouldNotBeFound("id.greaterThan=" + id);

        defaultAlvoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAlvoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAlvosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where nome equals to DEFAULT_NOME
        defaultAlvoShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the alvoList where nome equals to UPDATED_NOME
        defaultAlvoShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAlvosByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where nome not equals to DEFAULT_NOME
        defaultAlvoShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the alvoList where nome not equals to UPDATED_NOME
        defaultAlvoShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAlvosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultAlvoShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the alvoList where nome equals to UPDATED_NOME
        defaultAlvoShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAlvosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where nome is not null
        defaultAlvoShouldBeFound("nome.specified=true");

        // Get all the alvoList where nome is null
        defaultAlvoShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlvosByNomeContainsSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where nome contains DEFAULT_NOME
        defaultAlvoShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the alvoList where nome contains UPDATED_NOME
        defaultAlvoShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAlvosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where nome does not contain DEFAULT_NOME
        defaultAlvoShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the alvoList where nome does not contain UPDATED_NOME
        defaultAlvoShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllAlvosByNomeReduzidoIsEqualToSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where nomeReduzido equals to DEFAULT_NOME_REDUZIDO
        defaultAlvoShouldBeFound("nomeReduzido.equals=" + DEFAULT_NOME_REDUZIDO);

        // Get all the alvoList where nomeReduzido equals to UPDATED_NOME_REDUZIDO
        defaultAlvoShouldNotBeFound("nomeReduzido.equals=" + UPDATED_NOME_REDUZIDO);
    }

    @Test
    @Transactional
    public void getAllAlvosByNomeReduzidoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where nomeReduzido not equals to DEFAULT_NOME_REDUZIDO
        defaultAlvoShouldNotBeFound("nomeReduzido.notEquals=" + DEFAULT_NOME_REDUZIDO);

        // Get all the alvoList where nomeReduzido not equals to UPDATED_NOME_REDUZIDO
        defaultAlvoShouldBeFound("nomeReduzido.notEquals=" + UPDATED_NOME_REDUZIDO);
    }

    @Test
    @Transactional
    public void getAllAlvosByNomeReduzidoIsInShouldWork() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where nomeReduzido in DEFAULT_NOME_REDUZIDO or UPDATED_NOME_REDUZIDO
        defaultAlvoShouldBeFound("nomeReduzido.in=" + DEFAULT_NOME_REDUZIDO + "," + UPDATED_NOME_REDUZIDO);

        // Get all the alvoList where nomeReduzido equals to UPDATED_NOME_REDUZIDO
        defaultAlvoShouldNotBeFound("nomeReduzido.in=" + UPDATED_NOME_REDUZIDO);
    }

    @Test
    @Transactional
    public void getAllAlvosByNomeReduzidoIsNullOrNotNull() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where nomeReduzido is not null
        defaultAlvoShouldBeFound("nomeReduzido.specified=true");

        // Get all the alvoList where nomeReduzido is null
        defaultAlvoShouldNotBeFound("nomeReduzido.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlvosByNomeReduzidoContainsSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where nomeReduzido contains DEFAULT_NOME_REDUZIDO
        defaultAlvoShouldBeFound("nomeReduzido.contains=" + DEFAULT_NOME_REDUZIDO);

        // Get all the alvoList where nomeReduzido contains UPDATED_NOME_REDUZIDO
        defaultAlvoShouldNotBeFound("nomeReduzido.contains=" + UPDATED_NOME_REDUZIDO);
    }

    @Test
    @Transactional
    public void getAllAlvosByNomeReduzidoNotContainsSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where nomeReduzido does not contain DEFAULT_NOME_REDUZIDO
        defaultAlvoShouldNotBeFound("nomeReduzido.doesNotContain=" + DEFAULT_NOME_REDUZIDO);

        // Get all the alvoList where nomeReduzido does not contain UPDATED_NOME_REDUZIDO
        defaultAlvoShouldBeFound("nomeReduzido.doesNotContain=" + UPDATED_NOME_REDUZIDO);
    }


    @Test
    @Transactional
    public void getAllAlvosByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where descricao equals to DEFAULT_DESCRICAO
        defaultAlvoShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the alvoList where descricao equals to UPDATED_DESCRICAO
        defaultAlvoShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllAlvosByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where descricao not equals to DEFAULT_DESCRICAO
        defaultAlvoShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the alvoList where descricao not equals to UPDATED_DESCRICAO
        defaultAlvoShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllAlvosByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultAlvoShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the alvoList where descricao equals to UPDATED_DESCRICAO
        defaultAlvoShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllAlvosByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where descricao is not null
        defaultAlvoShouldBeFound("descricao.specified=true");

        // Get all the alvoList where descricao is null
        defaultAlvoShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlvosByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where descricao contains DEFAULT_DESCRICAO
        defaultAlvoShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the alvoList where descricao contains UPDATED_DESCRICAO
        defaultAlvoShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllAlvosByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where descricao does not contain DEFAULT_DESCRICAO
        defaultAlvoShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the alvoList where descricao does not contain UPDATED_DESCRICAO
        defaultAlvoShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllAlvosByPrimeiroPontoIsEqualToSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where primeiroPonto equals to DEFAULT_PRIMEIRO_PONTO
        defaultAlvoShouldBeFound("primeiroPonto.equals=" + DEFAULT_PRIMEIRO_PONTO);

        // Get all the alvoList where primeiroPonto equals to UPDATED_PRIMEIRO_PONTO
        defaultAlvoShouldNotBeFound("primeiroPonto.equals=" + UPDATED_PRIMEIRO_PONTO);
    }

    @Test
    @Transactional
    public void getAllAlvosByPrimeiroPontoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where primeiroPonto not equals to DEFAULT_PRIMEIRO_PONTO
        defaultAlvoShouldNotBeFound("primeiroPonto.notEquals=" + DEFAULT_PRIMEIRO_PONTO);

        // Get all the alvoList where primeiroPonto not equals to UPDATED_PRIMEIRO_PONTO
        defaultAlvoShouldBeFound("primeiroPonto.notEquals=" + UPDATED_PRIMEIRO_PONTO);
    }

    @Test
    @Transactional
    public void getAllAlvosByPrimeiroPontoIsInShouldWork() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where primeiroPonto in DEFAULT_PRIMEIRO_PONTO or UPDATED_PRIMEIRO_PONTO
        defaultAlvoShouldBeFound("primeiroPonto.in=" + DEFAULT_PRIMEIRO_PONTO + "," + UPDATED_PRIMEIRO_PONTO);

        // Get all the alvoList where primeiroPonto equals to UPDATED_PRIMEIRO_PONTO
        defaultAlvoShouldNotBeFound("primeiroPonto.in=" + UPDATED_PRIMEIRO_PONTO);
    }

    @Test
    @Transactional
    public void getAllAlvosByPrimeiroPontoIsNullOrNotNull() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where primeiroPonto is not null
        defaultAlvoShouldBeFound("primeiroPonto.specified=true");

        // Get all the alvoList where primeiroPonto is null
        defaultAlvoShouldNotBeFound("primeiroPonto.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlvosByPrimeiroPontoContainsSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where primeiroPonto contains DEFAULT_PRIMEIRO_PONTO
        defaultAlvoShouldBeFound("primeiroPonto.contains=" + DEFAULT_PRIMEIRO_PONTO);

        // Get all the alvoList where primeiroPonto contains UPDATED_PRIMEIRO_PONTO
        defaultAlvoShouldNotBeFound("primeiroPonto.contains=" + UPDATED_PRIMEIRO_PONTO);
    }

    @Test
    @Transactional
    public void getAllAlvosByPrimeiroPontoNotContainsSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where primeiroPonto does not contain DEFAULT_PRIMEIRO_PONTO
        defaultAlvoShouldNotBeFound("primeiroPonto.doesNotContain=" + DEFAULT_PRIMEIRO_PONTO);

        // Get all the alvoList where primeiroPonto does not contain UPDATED_PRIMEIRO_PONTO
        defaultAlvoShouldBeFound("primeiroPonto.doesNotContain=" + UPDATED_PRIMEIRO_PONTO);
    }


    @Test
    @Transactional
    public void getAllAlvosByUltimoPontoIsEqualToSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where ultimoPonto equals to DEFAULT_ULTIMO_PONTO
        defaultAlvoShouldBeFound("ultimoPonto.equals=" + DEFAULT_ULTIMO_PONTO);

        // Get all the alvoList where ultimoPonto equals to UPDATED_ULTIMO_PONTO
        defaultAlvoShouldNotBeFound("ultimoPonto.equals=" + UPDATED_ULTIMO_PONTO);
    }

    @Test
    @Transactional
    public void getAllAlvosByUltimoPontoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where ultimoPonto not equals to DEFAULT_ULTIMO_PONTO
        defaultAlvoShouldNotBeFound("ultimoPonto.notEquals=" + DEFAULT_ULTIMO_PONTO);

        // Get all the alvoList where ultimoPonto not equals to UPDATED_ULTIMO_PONTO
        defaultAlvoShouldBeFound("ultimoPonto.notEquals=" + UPDATED_ULTIMO_PONTO);
    }

    @Test
    @Transactional
    public void getAllAlvosByUltimoPontoIsInShouldWork() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where ultimoPonto in DEFAULT_ULTIMO_PONTO or UPDATED_ULTIMO_PONTO
        defaultAlvoShouldBeFound("ultimoPonto.in=" + DEFAULT_ULTIMO_PONTO + "," + UPDATED_ULTIMO_PONTO);

        // Get all the alvoList where ultimoPonto equals to UPDATED_ULTIMO_PONTO
        defaultAlvoShouldNotBeFound("ultimoPonto.in=" + UPDATED_ULTIMO_PONTO);
    }

    @Test
    @Transactional
    public void getAllAlvosByUltimoPontoIsNullOrNotNull() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where ultimoPonto is not null
        defaultAlvoShouldBeFound("ultimoPonto.specified=true");

        // Get all the alvoList where ultimoPonto is null
        defaultAlvoShouldNotBeFound("ultimoPonto.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlvosByUltimoPontoContainsSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where ultimoPonto contains DEFAULT_ULTIMO_PONTO
        defaultAlvoShouldBeFound("ultimoPonto.contains=" + DEFAULT_ULTIMO_PONTO);

        // Get all the alvoList where ultimoPonto contains UPDATED_ULTIMO_PONTO
        defaultAlvoShouldNotBeFound("ultimoPonto.contains=" + UPDATED_ULTIMO_PONTO);
    }

    @Test
    @Transactional
    public void getAllAlvosByUltimoPontoNotContainsSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where ultimoPonto does not contain DEFAULT_ULTIMO_PONTO
        defaultAlvoShouldNotBeFound("ultimoPonto.doesNotContain=" + DEFAULT_ULTIMO_PONTO);

        // Get all the alvoList where ultimoPonto does not contain UPDATED_ULTIMO_PONTO
        defaultAlvoShouldBeFound("ultimoPonto.doesNotContain=" + UPDATED_ULTIMO_PONTO);
    }


    @Test
    @Transactional
    public void getAllAlvosByHorarioLiberacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where horarioLiberacao equals to DEFAULT_HORARIO_LIBERACAO
        defaultAlvoShouldBeFound("horarioLiberacao.equals=" + DEFAULT_HORARIO_LIBERACAO);

        // Get all the alvoList where horarioLiberacao equals to UPDATED_HORARIO_LIBERACAO
        defaultAlvoShouldNotBeFound("horarioLiberacao.equals=" + UPDATED_HORARIO_LIBERACAO);
    }

    @Test
    @Transactional
    public void getAllAlvosByHorarioLiberacaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where horarioLiberacao not equals to DEFAULT_HORARIO_LIBERACAO
        defaultAlvoShouldNotBeFound("horarioLiberacao.notEquals=" + DEFAULT_HORARIO_LIBERACAO);

        // Get all the alvoList where horarioLiberacao not equals to UPDATED_HORARIO_LIBERACAO
        defaultAlvoShouldBeFound("horarioLiberacao.notEquals=" + UPDATED_HORARIO_LIBERACAO);
    }

    @Test
    @Transactional
    public void getAllAlvosByHorarioLiberacaoIsInShouldWork() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where horarioLiberacao in DEFAULT_HORARIO_LIBERACAO or UPDATED_HORARIO_LIBERACAO
        defaultAlvoShouldBeFound("horarioLiberacao.in=" + DEFAULT_HORARIO_LIBERACAO + "," + UPDATED_HORARIO_LIBERACAO);

        // Get all the alvoList where horarioLiberacao equals to UPDATED_HORARIO_LIBERACAO
        defaultAlvoShouldNotBeFound("horarioLiberacao.in=" + UPDATED_HORARIO_LIBERACAO);
    }

    @Test
    @Transactional
    public void getAllAlvosByHorarioLiberacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where horarioLiberacao is not null
        defaultAlvoShouldBeFound("horarioLiberacao.specified=true");

        // Get all the alvoList where horarioLiberacao is null
        defaultAlvoShouldNotBeFound("horarioLiberacao.specified=false");
    }

    @Test
    @Transactional
    public void getAllAlvosByHorarioIsEqualToSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where horario equals to DEFAULT_HORARIO
        defaultAlvoShouldBeFound("horario.equals=" + DEFAULT_HORARIO);

        // Get all the alvoList where horario equals to UPDATED_HORARIO
        defaultAlvoShouldNotBeFound("horario.equals=" + UPDATED_HORARIO);
    }

    @Test
    @Transactional
    public void getAllAlvosByHorarioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where horario not equals to DEFAULT_HORARIO
        defaultAlvoShouldNotBeFound("horario.notEquals=" + DEFAULT_HORARIO);

        // Get all the alvoList where horario not equals to UPDATED_HORARIO
        defaultAlvoShouldBeFound("horario.notEquals=" + UPDATED_HORARIO);
    }

    @Test
    @Transactional
    public void getAllAlvosByHorarioIsInShouldWork() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where horario in DEFAULT_HORARIO or UPDATED_HORARIO
        defaultAlvoShouldBeFound("horario.in=" + DEFAULT_HORARIO + "," + UPDATED_HORARIO);

        // Get all the alvoList where horario equals to UPDATED_HORARIO
        defaultAlvoShouldNotBeFound("horario.in=" + UPDATED_HORARIO);
    }

    @Test
    @Transactional
    public void getAllAlvosByHorarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where horario is not null
        defaultAlvoShouldBeFound("horario.specified=true");

        // Get all the alvoList where horario is null
        defaultAlvoShouldNotBeFound("horario.specified=false");
    }

    @Test
    @Transactional
    public void getAllAlvosByDuracaoIsEqualToSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where duracao equals to DEFAULT_DURACAO
        defaultAlvoShouldBeFound("duracao.equals=" + DEFAULT_DURACAO);

        // Get all the alvoList where duracao equals to UPDATED_DURACAO
        defaultAlvoShouldNotBeFound("duracao.equals=" + UPDATED_DURACAO);
    }

    @Test
    @Transactional
    public void getAllAlvosByDuracaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where duracao not equals to DEFAULT_DURACAO
        defaultAlvoShouldNotBeFound("duracao.notEquals=" + DEFAULT_DURACAO);

        // Get all the alvoList where duracao not equals to UPDATED_DURACAO
        defaultAlvoShouldBeFound("duracao.notEquals=" + UPDATED_DURACAO);
    }

    @Test
    @Transactional
    public void getAllAlvosByDuracaoIsInShouldWork() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where duracao in DEFAULT_DURACAO or UPDATED_DURACAO
        defaultAlvoShouldBeFound("duracao.in=" + DEFAULT_DURACAO + "," + UPDATED_DURACAO);

        // Get all the alvoList where duracao equals to UPDATED_DURACAO
        defaultAlvoShouldNotBeFound("duracao.in=" + UPDATED_DURACAO);
    }

    @Test
    @Transactional
    public void getAllAlvosByDuracaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where duracao is not null
        defaultAlvoShouldBeFound("duracao.specified=true");

        // Get all the alvoList where duracao is null
        defaultAlvoShouldNotBeFound("duracao.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlvosByDuracaoContainsSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where duracao contains DEFAULT_DURACAO
        defaultAlvoShouldBeFound("duracao.contains=" + DEFAULT_DURACAO);

        // Get all the alvoList where duracao contains UPDATED_DURACAO
        defaultAlvoShouldNotBeFound("duracao.contains=" + UPDATED_DURACAO);
    }

    @Test
    @Transactional
    public void getAllAlvosByDuracaoNotContainsSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where duracao does not contain DEFAULT_DURACAO
        defaultAlvoShouldNotBeFound("duracao.doesNotContain=" + DEFAULT_DURACAO);

        // Get all the alvoList where duracao does not contain UPDATED_DURACAO
        defaultAlvoShouldBeFound("duracao.doesNotContain=" + UPDATED_DURACAO);
    }


    @Test
    @Transactional
    public void getAllAlvosByDuracaoAtualIsEqualToSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where duracaoAtual equals to DEFAULT_DURACAO_ATUAL
        defaultAlvoShouldBeFound("duracaoAtual.equals=" + DEFAULT_DURACAO_ATUAL);

        // Get all the alvoList where duracaoAtual equals to UPDATED_DURACAO_ATUAL
        defaultAlvoShouldNotBeFound("duracaoAtual.equals=" + UPDATED_DURACAO_ATUAL);
    }

    @Test
    @Transactional
    public void getAllAlvosByDuracaoAtualIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where duracaoAtual not equals to DEFAULT_DURACAO_ATUAL
        defaultAlvoShouldNotBeFound("duracaoAtual.notEquals=" + DEFAULT_DURACAO_ATUAL);

        // Get all the alvoList where duracaoAtual not equals to UPDATED_DURACAO_ATUAL
        defaultAlvoShouldBeFound("duracaoAtual.notEquals=" + UPDATED_DURACAO_ATUAL);
    }

    @Test
    @Transactional
    public void getAllAlvosByDuracaoAtualIsInShouldWork() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where duracaoAtual in DEFAULT_DURACAO_ATUAL or UPDATED_DURACAO_ATUAL
        defaultAlvoShouldBeFound("duracaoAtual.in=" + DEFAULT_DURACAO_ATUAL + "," + UPDATED_DURACAO_ATUAL);

        // Get all the alvoList where duracaoAtual equals to UPDATED_DURACAO_ATUAL
        defaultAlvoShouldNotBeFound("duracaoAtual.in=" + UPDATED_DURACAO_ATUAL);
    }

    @Test
    @Transactional
    public void getAllAlvosByDuracaoAtualIsNullOrNotNull() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where duracaoAtual is not null
        defaultAlvoShouldBeFound("duracaoAtual.specified=true");

        // Get all the alvoList where duracaoAtual is null
        defaultAlvoShouldNotBeFound("duracaoAtual.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlvosByDuracaoAtualContainsSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where duracaoAtual contains DEFAULT_DURACAO_ATUAL
        defaultAlvoShouldBeFound("duracaoAtual.contains=" + DEFAULT_DURACAO_ATUAL);

        // Get all the alvoList where duracaoAtual contains UPDATED_DURACAO_ATUAL
        defaultAlvoShouldNotBeFound("duracaoAtual.contains=" + UPDATED_DURACAO_ATUAL);
    }

    @Test
    @Transactional
    public void getAllAlvosByDuracaoAtualNotContainsSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where duracaoAtual does not contain DEFAULT_DURACAO_ATUAL
        defaultAlvoShouldNotBeFound("duracaoAtual.doesNotContain=" + DEFAULT_DURACAO_ATUAL);

        // Get all the alvoList where duracaoAtual does not contain UPDATED_DURACAO_ATUAL
        defaultAlvoShouldBeFound("duracaoAtual.doesNotContain=" + UPDATED_DURACAO_ATUAL);
    }


    @Test
    @Transactional
    public void getAllAlvosByDataDesativadoIsEqualToSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where dataDesativado equals to DEFAULT_DATA_DESATIVADO
        defaultAlvoShouldBeFound("dataDesativado.equals=" + DEFAULT_DATA_DESATIVADO);

        // Get all the alvoList where dataDesativado equals to UPDATED_DATA_DESATIVADO
        defaultAlvoShouldNotBeFound("dataDesativado.equals=" + UPDATED_DATA_DESATIVADO);
    }

    @Test
    @Transactional
    public void getAllAlvosByDataDesativadoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where dataDesativado not equals to DEFAULT_DATA_DESATIVADO
        defaultAlvoShouldNotBeFound("dataDesativado.notEquals=" + DEFAULT_DATA_DESATIVADO);

        // Get all the alvoList where dataDesativado not equals to UPDATED_DATA_DESATIVADO
        defaultAlvoShouldBeFound("dataDesativado.notEquals=" + UPDATED_DATA_DESATIVADO);
    }

    @Test
    @Transactional
    public void getAllAlvosByDataDesativadoIsInShouldWork() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where dataDesativado in DEFAULT_DATA_DESATIVADO or UPDATED_DATA_DESATIVADO
        defaultAlvoShouldBeFound("dataDesativado.in=" + DEFAULT_DATA_DESATIVADO + "," + UPDATED_DATA_DESATIVADO);

        // Get all the alvoList where dataDesativado equals to UPDATED_DATA_DESATIVADO
        defaultAlvoShouldNotBeFound("dataDesativado.in=" + UPDATED_DATA_DESATIVADO);
    }

    @Test
    @Transactional
    public void getAllAlvosByDataDesativadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where dataDesativado is not null
        defaultAlvoShouldBeFound("dataDesativado.specified=true");

        // Get all the alvoList where dataDesativado is null
        defaultAlvoShouldNotBeFound("dataDesativado.specified=false");
    }

    @Test
    @Transactional
    public void getAllAlvosByCoordenadasAlertaPontosIsEqualToSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where coordenadasAlertaPontos equals to DEFAULT_COORDENADAS_ALERTA_PONTOS
        defaultAlvoShouldBeFound("coordenadasAlertaPontos.equals=" + DEFAULT_COORDENADAS_ALERTA_PONTOS);

        // Get all the alvoList where coordenadasAlertaPontos equals to UPDATED_COORDENADAS_ALERTA_PONTOS
        defaultAlvoShouldNotBeFound("coordenadasAlertaPontos.equals=" + UPDATED_COORDENADAS_ALERTA_PONTOS);
    }

    @Test
    @Transactional
    public void getAllAlvosByCoordenadasAlertaPontosIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where coordenadasAlertaPontos not equals to DEFAULT_COORDENADAS_ALERTA_PONTOS
        defaultAlvoShouldNotBeFound("coordenadasAlertaPontos.notEquals=" + DEFAULT_COORDENADAS_ALERTA_PONTOS);

        // Get all the alvoList where coordenadasAlertaPontos not equals to UPDATED_COORDENADAS_ALERTA_PONTOS
        defaultAlvoShouldBeFound("coordenadasAlertaPontos.notEquals=" + UPDATED_COORDENADAS_ALERTA_PONTOS);
    }

    @Test
    @Transactional
    public void getAllAlvosByCoordenadasAlertaPontosIsInShouldWork() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where coordenadasAlertaPontos in DEFAULT_COORDENADAS_ALERTA_PONTOS or UPDATED_COORDENADAS_ALERTA_PONTOS
        defaultAlvoShouldBeFound("coordenadasAlertaPontos.in=" + DEFAULT_COORDENADAS_ALERTA_PONTOS + "," + UPDATED_COORDENADAS_ALERTA_PONTOS);

        // Get all the alvoList where coordenadasAlertaPontos equals to UPDATED_COORDENADAS_ALERTA_PONTOS
        defaultAlvoShouldNotBeFound("coordenadasAlertaPontos.in=" + UPDATED_COORDENADAS_ALERTA_PONTOS);
    }

    @Test
    @Transactional
    public void getAllAlvosByCoordenadasAlertaPontosIsNullOrNotNull() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where coordenadasAlertaPontos is not null
        defaultAlvoShouldBeFound("coordenadasAlertaPontos.specified=true");

        // Get all the alvoList where coordenadasAlertaPontos is null
        defaultAlvoShouldNotBeFound("coordenadasAlertaPontos.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlvosByCoordenadasAlertaPontosContainsSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where coordenadasAlertaPontos contains DEFAULT_COORDENADAS_ALERTA_PONTOS
        defaultAlvoShouldBeFound("coordenadasAlertaPontos.contains=" + DEFAULT_COORDENADAS_ALERTA_PONTOS);

        // Get all the alvoList where coordenadasAlertaPontos contains UPDATED_COORDENADAS_ALERTA_PONTOS
        defaultAlvoShouldNotBeFound("coordenadasAlertaPontos.contains=" + UPDATED_COORDENADAS_ALERTA_PONTOS);
    }

    @Test
    @Transactional
    public void getAllAlvosByCoordenadasAlertaPontosNotContainsSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where coordenadasAlertaPontos does not contain DEFAULT_COORDENADAS_ALERTA_PONTOS
        defaultAlvoShouldNotBeFound("coordenadasAlertaPontos.doesNotContain=" + DEFAULT_COORDENADAS_ALERTA_PONTOS);

        // Get all the alvoList where coordenadasAlertaPontos does not contain UPDATED_COORDENADAS_ALERTA_PONTOS
        defaultAlvoShouldBeFound("coordenadasAlertaPontos.doesNotContain=" + UPDATED_COORDENADAS_ALERTA_PONTOS);
    }


    @Test
    @Transactional
    public void getAllAlvosByCoordenadasLiberacaoPontosIsEqualToSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where coordenadasLiberacaoPontos equals to DEFAULT_COORDENADAS_LIBERACAO_PONTOS
        defaultAlvoShouldBeFound("coordenadasLiberacaoPontos.equals=" + DEFAULT_COORDENADAS_LIBERACAO_PONTOS);

        // Get all the alvoList where coordenadasLiberacaoPontos equals to UPDATED_COORDENADAS_LIBERACAO_PONTOS
        defaultAlvoShouldNotBeFound("coordenadasLiberacaoPontos.equals=" + UPDATED_COORDENADAS_LIBERACAO_PONTOS);
    }

    @Test
    @Transactional
    public void getAllAlvosByCoordenadasLiberacaoPontosIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where coordenadasLiberacaoPontos not equals to DEFAULT_COORDENADAS_LIBERACAO_PONTOS
        defaultAlvoShouldNotBeFound("coordenadasLiberacaoPontos.notEquals=" + DEFAULT_COORDENADAS_LIBERACAO_PONTOS);

        // Get all the alvoList where coordenadasLiberacaoPontos not equals to UPDATED_COORDENADAS_LIBERACAO_PONTOS
        defaultAlvoShouldBeFound("coordenadasLiberacaoPontos.notEquals=" + UPDATED_COORDENADAS_LIBERACAO_PONTOS);
    }

    @Test
    @Transactional
    public void getAllAlvosByCoordenadasLiberacaoPontosIsInShouldWork() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where coordenadasLiberacaoPontos in DEFAULT_COORDENADAS_LIBERACAO_PONTOS or UPDATED_COORDENADAS_LIBERACAO_PONTOS
        defaultAlvoShouldBeFound("coordenadasLiberacaoPontos.in=" + DEFAULT_COORDENADAS_LIBERACAO_PONTOS + "," + UPDATED_COORDENADAS_LIBERACAO_PONTOS);

        // Get all the alvoList where coordenadasLiberacaoPontos equals to UPDATED_COORDENADAS_LIBERACAO_PONTOS
        defaultAlvoShouldNotBeFound("coordenadasLiberacaoPontos.in=" + UPDATED_COORDENADAS_LIBERACAO_PONTOS);
    }

    @Test
    @Transactional
    public void getAllAlvosByCoordenadasLiberacaoPontosIsNullOrNotNull() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where coordenadasLiberacaoPontos is not null
        defaultAlvoShouldBeFound("coordenadasLiberacaoPontos.specified=true");

        // Get all the alvoList where coordenadasLiberacaoPontos is null
        defaultAlvoShouldNotBeFound("coordenadasLiberacaoPontos.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlvosByCoordenadasLiberacaoPontosContainsSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where coordenadasLiberacaoPontos contains DEFAULT_COORDENADAS_LIBERACAO_PONTOS
        defaultAlvoShouldBeFound("coordenadasLiberacaoPontos.contains=" + DEFAULT_COORDENADAS_LIBERACAO_PONTOS);

        // Get all the alvoList where coordenadasLiberacaoPontos contains UPDATED_COORDENADAS_LIBERACAO_PONTOS
        defaultAlvoShouldNotBeFound("coordenadasLiberacaoPontos.contains=" + UPDATED_COORDENADAS_LIBERACAO_PONTOS);
    }

    @Test
    @Transactional
    public void getAllAlvosByCoordenadasLiberacaoPontosNotContainsSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where coordenadasLiberacaoPontos does not contain DEFAULT_COORDENADAS_LIBERACAO_PONTOS
        defaultAlvoShouldNotBeFound("coordenadasLiberacaoPontos.doesNotContain=" + DEFAULT_COORDENADAS_LIBERACAO_PONTOS);

        // Get all the alvoList where coordenadasLiberacaoPontos does not contain UPDATED_COORDENADAS_LIBERACAO_PONTOS
        defaultAlvoShouldBeFound("coordenadasLiberacaoPontos.doesNotContain=" + UPDATED_COORDENADAS_LIBERACAO_PONTOS);
    }


    @Test
    @Transactional
    public void getAllAlvosByTelegramTokenBotIsEqualToSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where telegramTokenBot equals to DEFAULT_TELEGRAM_TOKEN_BOT
        defaultAlvoShouldBeFound("telegramTokenBot.equals=" + DEFAULT_TELEGRAM_TOKEN_BOT);

        // Get all the alvoList where telegramTokenBot equals to UPDATED_TELEGRAM_TOKEN_BOT
        defaultAlvoShouldNotBeFound("telegramTokenBot.equals=" + UPDATED_TELEGRAM_TOKEN_BOT);
    }

    @Test
    @Transactional
    public void getAllAlvosByTelegramTokenBotIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where telegramTokenBot not equals to DEFAULT_TELEGRAM_TOKEN_BOT
        defaultAlvoShouldNotBeFound("telegramTokenBot.notEquals=" + DEFAULT_TELEGRAM_TOKEN_BOT);

        // Get all the alvoList where telegramTokenBot not equals to UPDATED_TELEGRAM_TOKEN_BOT
        defaultAlvoShouldBeFound("telegramTokenBot.notEquals=" + UPDATED_TELEGRAM_TOKEN_BOT);
    }

    @Test
    @Transactional
    public void getAllAlvosByTelegramTokenBotIsInShouldWork() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where telegramTokenBot in DEFAULT_TELEGRAM_TOKEN_BOT or UPDATED_TELEGRAM_TOKEN_BOT
        defaultAlvoShouldBeFound("telegramTokenBot.in=" + DEFAULT_TELEGRAM_TOKEN_BOT + "," + UPDATED_TELEGRAM_TOKEN_BOT);

        // Get all the alvoList where telegramTokenBot equals to UPDATED_TELEGRAM_TOKEN_BOT
        defaultAlvoShouldNotBeFound("telegramTokenBot.in=" + UPDATED_TELEGRAM_TOKEN_BOT);
    }

    @Test
    @Transactional
    public void getAllAlvosByTelegramTokenBotIsNullOrNotNull() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where telegramTokenBot is not null
        defaultAlvoShouldBeFound("telegramTokenBot.specified=true");

        // Get all the alvoList where telegramTokenBot is null
        defaultAlvoShouldNotBeFound("telegramTokenBot.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlvosByTelegramTokenBotContainsSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where telegramTokenBot contains DEFAULT_TELEGRAM_TOKEN_BOT
        defaultAlvoShouldBeFound("telegramTokenBot.contains=" + DEFAULT_TELEGRAM_TOKEN_BOT);

        // Get all the alvoList where telegramTokenBot contains UPDATED_TELEGRAM_TOKEN_BOT
        defaultAlvoShouldNotBeFound("telegramTokenBot.contains=" + UPDATED_TELEGRAM_TOKEN_BOT);
    }

    @Test
    @Transactional
    public void getAllAlvosByTelegramTokenBotNotContainsSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where telegramTokenBot does not contain DEFAULT_TELEGRAM_TOKEN_BOT
        defaultAlvoShouldNotBeFound("telegramTokenBot.doesNotContain=" + DEFAULT_TELEGRAM_TOKEN_BOT);

        // Get all the alvoList where telegramTokenBot does not contain UPDATED_TELEGRAM_TOKEN_BOT
        defaultAlvoShouldBeFound("telegramTokenBot.doesNotContain=" + UPDATED_TELEGRAM_TOKEN_BOT);
    }


    @Test
    @Transactional
    public void getAllAlvosByTelegramChatIdIsEqualToSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where telegramChatId equals to DEFAULT_TELEGRAM_CHAT_ID
        defaultAlvoShouldBeFound("telegramChatId.equals=" + DEFAULT_TELEGRAM_CHAT_ID);

        // Get all the alvoList where telegramChatId equals to UPDATED_TELEGRAM_CHAT_ID
        defaultAlvoShouldNotBeFound("telegramChatId.equals=" + UPDATED_TELEGRAM_CHAT_ID);
    }

    @Test
    @Transactional
    public void getAllAlvosByTelegramChatIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where telegramChatId not equals to DEFAULT_TELEGRAM_CHAT_ID
        defaultAlvoShouldNotBeFound("telegramChatId.notEquals=" + DEFAULT_TELEGRAM_CHAT_ID);

        // Get all the alvoList where telegramChatId not equals to UPDATED_TELEGRAM_CHAT_ID
        defaultAlvoShouldBeFound("telegramChatId.notEquals=" + UPDATED_TELEGRAM_CHAT_ID);
    }

    @Test
    @Transactional
    public void getAllAlvosByTelegramChatIdIsInShouldWork() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where telegramChatId in DEFAULT_TELEGRAM_CHAT_ID or UPDATED_TELEGRAM_CHAT_ID
        defaultAlvoShouldBeFound("telegramChatId.in=" + DEFAULT_TELEGRAM_CHAT_ID + "," + UPDATED_TELEGRAM_CHAT_ID);

        // Get all the alvoList where telegramChatId equals to UPDATED_TELEGRAM_CHAT_ID
        defaultAlvoShouldNotBeFound("telegramChatId.in=" + UPDATED_TELEGRAM_CHAT_ID);
    }

    @Test
    @Transactional
    public void getAllAlvosByTelegramChatIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where telegramChatId is not null
        defaultAlvoShouldBeFound("telegramChatId.specified=true");

        // Get all the alvoList where telegramChatId is null
        defaultAlvoShouldNotBeFound("telegramChatId.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlvosByTelegramChatIdContainsSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where telegramChatId contains DEFAULT_TELEGRAM_CHAT_ID
        defaultAlvoShouldBeFound("telegramChatId.contains=" + DEFAULT_TELEGRAM_CHAT_ID);

        // Get all the alvoList where telegramChatId contains UPDATED_TELEGRAM_CHAT_ID
        defaultAlvoShouldNotBeFound("telegramChatId.contains=" + UPDATED_TELEGRAM_CHAT_ID);
    }

    @Test
    @Transactional
    public void getAllAlvosByTelegramChatIdNotContainsSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where telegramChatId does not contain DEFAULT_TELEGRAM_CHAT_ID
        defaultAlvoShouldNotBeFound("telegramChatId.doesNotContain=" + DEFAULT_TELEGRAM_CHAT_ID);

        // Get all the alvoList where telegramChatId does not contain UPDATED_TELEGRAM_CHAT_ID
        defaultAlvoShouldBeFound("telegramChatId.doesNotContain=" + UPDATED_TELEGRAM_CHAT_ID);
    }


    @Test
    @Transactional
    public void getAllAlvosByHorarioBloqueioNotificacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where horarioBloqueioNotificacao equals to DEFAULT_HORARIO_BLOQUEIO_NOTIFICACAO
        defaultAlvoShouldBeFound("horarioBloqueioNotificacao.equals=" + DEFAULT_HORARIO_BLOQUEIO_NOTIFICACAO);

        // Get all the alvoList where horarioBloqueioNotificacao equals to UPDATED_HORARIO_BLOQUEIO_NOTIFICACAO
        defaultAlvoShouldNotBeFound("horarioBloqueioNotificacao.equals=" + UPDATED_HORARIO_BLOQUEIO_NOTIFICACAO);
    }

    @Test
    @Transactional
    public void getAllAlvosByHorarioBloqueioNotificacaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where horarioBloqueioNotificacao not equals to DEFAULT_HORARIO_BLOQUEIO_NOTIFICACAO
        defaultAlvoShouldNotBeFound("horarioBloqueioNotificacao.notEquals=" + DEFAULT_HORARIO_BLOQUEIO_NOTIFICACAO);

        // Get all the alvoList where horarioBloqueioNotificacao not equals to UPDATED_HORARIO_BLOQUEIO_NOTIFICACAO
        defaultAlvoShouldBeFound("horarioBloqueioNotificacao.notEquals=" + UPDATED_HORARIO_BLOQUEIO_NOTIFICACAO);
    }

    @Test
    @Transactional
    public void getAllAlvosByHorarioBloqueioNotificacaoIsInShouldWork() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where horarioBloqueioNotificacao in DEFAULT_HORARIO_BLOQUEIO_NOTIFICACAO or UPDATED_HORARIO_BLOQUEIO_NOTIFICACAO
        defaultAlvoShouldBeFound("horarioBloqueioNotificacao.in=" + DEFAULT_HORARIO_BLOQUEIO_NOTIFICACAO + "," + UPDATED_HORARIO_BLOQUEIO_NOTIFICACAO);

        // Get all the alvoList where horarioBloqueioNotificacao equals to UPDATED_HORARIO_BLOQUEIO_NOTIFICACAO
        defaultAlvoShouldNotBeFound("horarioBloqueioNotificacao.in=" + UPDATED_HORARIO_BLOQUEIO_NOTIFICACAO);
    }

    @Test
    @Transactional
    public void getAllAlvosByHorarioBloqueioNotificacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where horarioBloqueioNotificacao is not null
        defaultAlvoShouldBeFound("horarioBloqueioNotificacao.specified=true");

        // Get all the alvoList where horarioBloqueioNotificacao is null
        defaultAlvoShouldNotBeFound("horarioBloqueioNotificacao.specified=false");
    }

    @Test
    @Transactional
    public void getAllAlvosByCoordenadasOriginalPontosIsEqualToSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where coordenadasOriginalPontos equals to DEFAULT_COORDENADAS_ORIGINAL_PONTOS
        defaultAlvoShouldBeFound("coordenadasOriginalPontos.equals=" + DEFAULT_COORDENADAS_ORIGINAL_PONTOS);

        // Get all the alvoList where coordenadasOriginalPontos equals to UPDATED_COORDENADAS_ORIGINAL_PONTOS
        defaultAlvoShouldNotBeFound("coordenadasOriginalPontos.equals=" + UPDATED_COORDENADAS_ORIGINAL_PONTOS);
    }

    @Test
    @Transactional
    public void getAllAlvosByCoordenadasOriginalPontosIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where coordenadasOriginalPontos not equals to DEFAULT_COORDENADAS_ORIGINAL_PONTOS
        defaultAlvoShouldNotBeFound("coordenadasOriginalPontos.notEquals=" + DEFAULT_COORDENADAS_ORIGINAL_PONTOS);

        // Get all the alvoList where coordenadasOriginalPontos not equals to UPDATED_COORDENADAS_ORIGINAL_PONTOS
        defaultAlvoShouldBeFound("coordenadasOriginalPontos.notEquals=" + UPDATED_COORDENADAS_ORIGINAL_PONTOS);
    }

    @Test
    @Transactional
    public void getAllAlvosByCoordenadasOriginalPontosIsInShouldWork() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where coordenadasOriginalPontos in DEFAULT_COORDENADAS_ORIGINAL_PONTOS or UPDATED_COORDENADAS_ORIGINAL_PONTOS
        defaultAlvoShouldBeFound("coordenadasOriginalPontos.in=" + DEFAULT_COORDENADAS_ORIGINAL_PONTOS + "," + UPDATED_COORDENADAS_ORIGINAL_PONTOS);

        // Get all the alvoList where coordenadasOriginalPontos equals to UPDATED_COORDENADAS_ORIGINAL_PONTOS
        defaultAlvoShouldNotBeFound("coordenadasOriginalPontos.in=" + UPDATED_COORDENADAS_ORIGINAL_PONTOS);
    }

    @Test
    @Transactional
    public void getAllAlvosByCoordenadasOriginalPontosIsNullOrNotNull() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where coordenadasOriginalPontos is not null
        defaultAlvoShouldBeFound("coordenadasOriginalPontos.specified=true");

        // Get all the alvoList where coordenadasOriginalPontos is null
        defaultAlvoShouldNotBeFound("coordenadasOriginalPontos.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlvosByCoordenadasOriginalPontosContainsSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where coordenadasOriginalPontos contains DEFAULT_COORDENADAS_ORIGINAL_PONTOS
        defaultAlvoShouldBeFound("coordenadasOriginalPontos.contains=" + DEFAULT_COORDENADAS_ORIGINAL_PONTOS);

        // Get all the alvoList where coordenadasOriginalPontos contains UPDATED_COORDENADAS_ORIGINAL_PONTOS
        defaultAlvoShouldNotBeFound("coordenadasOriginalPontos.contains=" + UPDATED_COORDENADAS_ORIGINAL_PONTOS);
    }

    @Test
    @Transactional
    public void getAllAlvosByCoordenadasOriginalPontosNotContainsSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where coordenadasOriginalPontos does not contain DEFAULT_COORDENADAS_ORIGINAL_PONTOS
        defaultAlvoShouldNotBeFound("coordenadasOriginalPontos.doesNotContain=" + DEFAULT_COORDENADAS_ORIGINAL_PONTOS);

        // Get all the alvoList where coordenadasOriginalPontos does not contain UPDATED_COORDENADAS_ORIGINAL_PONTOS
        defaultAlvoShouldBeFound("coordenadasOriginalPontos.doesNotContain=" + UPDATED_COORDENADAS_ORIGINAL_PONTOS);
    }


    @Test
    @Transactional
    public void getAllAlvosByAtivoIsEqualToSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where ativo equals to DEFAULT_ATIVO
        defaultAlvoShouldBeFound("ativo.equals=" + DEFAULT_ATIVO);

        // Get all the alvoList where ativo equals to UPDATED_ATIVO
        defaultAlvoShouldNotBeFound("ativo.equals=" + UPDATED_ATIVO);
    }

    @Test
    @Transactional
    public void getAllAlvosByAtivoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where ativo not equals to DEFAULT_ATIVO
        defaultAlvoShouldNotBeFound("ativo.notEquals=" + DEFAULT_ATIVO);

        // Get all the alvoList where ativo not equals to UPDATED_ATIVO
        defaultAlvoShouldBeFound("ativo.notEquals=" + UPDATED_ATIVO);
    }

    @Test
    @Transactional
    public void getAllAlvosByAtivoIsInShouldWork() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where ativo in DEFAULT_ATIVO or UPDATED_ATIVO
        defaultAlvoShouldBeFound("ativo.in=" + DEFAULT_ATIVO + "," + UPDATED_ATIVO);

        // Get all the alvoList where ativo equals to UPDATED_ATIVO
        defaultAlvoShouldNotBeFound("ativo.in=" + UPDATED_ATIVO);
    }

    @Test
    @Transactional
    public void getAllAlvosByAtivoIsNullOrNotNull() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where ativo is not null
        defaultAlvoShouldBeFound("ativo.specified=true");

        // Get all the alvoList where ativo is null
        defaultAlvoShouldNotBeFound("ativo.specified=false");
    }

    @Test
    @Transactional
    public void getAllAlvosByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where created equals to DEFAULT_CREATED
        defaultAlvoShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the alvoList where created equals to UPDATED_CREATED
        defaultAlvoShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllAlvosByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where created not equals to DEFAULT_CREATED
        defaultAlvoShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the alvoList where created not equals to UPDATED_CREATED
        defaultAlvoShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllAlvosByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultAlvoShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the alvoList where created equals to UPDATED_CREATED
        defaultAlvoShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllAlvosByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where created is not null
        defaultAlvoShouldBeFound("created.specified=true");

        // Get all the alvoList where created is null
        defaultAlvoShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllAlvosByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where updated equals to DEFAULT_UPDATED
        defaultAlvoShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the alvoList where updated equals to UPDATED_UPDATED
        defaultAlvoShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllAlvosByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where updated not equals to DEFAULT_UPDATED
        defaultAlvoShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the alvoList where updated not equals to UPDATED_UPDATED
        defaultAlvoShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllAlvosByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultAlvoShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the alvoList where updated equals to UPDATED_UPDATED
        defaultAlvoShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllAlvosByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);

        // Get all the alvoList where updated is not null
        defaultAlvoShouldBeFound("updated.specified=true");

        // Get all the alvoList where updated is null
        defaultAlvoShouldNotBeFound("updated.specified=false");
    }

    @Test
    @Transactional
    public void getAllAlvosByPlanoIsEqualToSomething() throws Exception {
        // Initialize the database
        alvoRepository.saveAndFlush(alvo);
        Plano plano = PlanoResourceIT.createEntity(em);
        em.persist(plano);
        em.flush();
        alvo.setPlano(plano);
        alvoRepository.saveAndFlush(alvo);
        Long planoId = plano.getId();

        // Get all the alvoList where plano equals to planoId
        defaultAlvoShouldBeFound("planoId.equals=" + planoId);

        // Get all the alvoList where plano equals to planoId + 1
        defaultAlvoShouldNotBeFound("planoId.equals=" + (planoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAlvoShouldBeFound(String filter) throws Exception {
        restAlvoMockMvc.perform(get("/api/alvos?sort=id,desc&" + filter))
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

        // Check, that the count call also returns 1
        restAlvoMockMvc.perform(get("/api/alvos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAlvoShouldNotBeFound(String filter) throws Exception {
        restAlvoMockMvc.perform(get("/api/alvos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAlvoMockMvc.perform(get("/api/alvos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
