package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.NotificacaoEnviada;
import org.tempestade.nucleo.domain.PlanoRecurso;
import org.tempestade.nucleo.repository.NotificacaoEnviadaRepository;
import org.tempestade.nucleo.service.NotificacaoEnviadaService;
import org.tempestade.nucleo.service.dto.NotificacaoEnviadaDTO;
import org.tempestade.nucleo.service.mapper.NotificacaoEnviadaMapper;
import org.tempestade.nucleo.service.dto.NotificacaoEnviadaCriteria;
import org.tempestade.nucleo.service.NotificacaoEnviadaQueryService;

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
 * Integration tests for the {@link NotificacaoEnviadaResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class NotificacaoEnviadaResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_DESTINATARIOS = "AAAAAAAAAA";
    private static final String UPDATED_DESTINATARIOS = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_ASSUNTO = "AAAAAAAAAA";
    private static final String UPDATED_ASSUNTO = "BBBBBBBBBB";

    private static final Integer DEFAULT_ENVIADO = 1;
    private static final Integer UPDATED_ENVIADO = 2;
    private static final Integer SMALLER_ENVIADO = 1 - 1;

    private static final Integer DEFAULT_CONTADOR = 1;
    private static final Integer UPDATED_CONTADOR = 2;
    private static final Integer SMALLER_CONTADOR = 1 - 1;

    private static final String DEFAULT_AMAZON_MESSAGE_ID = "AAAAAAAAAA";
    private static final String UPDATED_AMAZON_MESSAGE_ID = "BBBBBBBBBB";

    private static final Instant DEFAULT_AMAZON_DATE_LOG = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_AMAZON_DATE_LOG = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_PRICE_IN_USD = 1D;
    private static final Double UPDATED_PRICE_IN_USD = 2D;
    private static final Double SMALLER_PRICE_IN_USD = 1D - 1D;

    private static final String DEFAULT_AMAZON_RESPOSTA = "AAAAAAAAAA";
    private static final String UPDATED_AMAZON_RESPOSTA = "BBBBBBBBBB";

    private static final Integer DEFAULT_REFERENCE_ID = 1;
    private static final Integer UPDATED_REFERENCE_ID = 2;
    private static final Integer SMALLER_REFERENCE_ID = 1 - 1;

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private NotificacaoEnviadaRepository notificacaoEnviadaRepository;

    @Autowired
    private NotificacaoEnviadaMapper notificacaoEnviadaMapper;

    @Autowired
    private NotificacaoEnviadaService notificacaoEnviadaService;

    @Autowired
    private NotificacaoEnviadaQueryService notificacaoEnviadaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNotificacaoEnviadaMockMvc;

    private NotificacaoEnviada notificacaoEnviada;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NotificacaoEnviada createEntity(EntityManager em) {
        NotificacaoEnviada notificacaoEnviada = new NotificacaoEnviada()
            .name(DEFAULT_NAME)
            .descricao(DEFAULT_DESCRICAO)
            .destinatarios(DEFAULT_DESTINATARIOS)
            .tipo(DEFAULT_TIPO)
            .status(DEFAULT_STATUS)
            .assunto(DEFAULT_ASSUNTO)
            .enviado(DEFAULT_ENVIADO)
            .contador(DEFAULT_CONTADOR)
            .amazonMessageId(DEFAULT_AMAZON_MESSAGE_ID)
            .amazonDateLog(DEFAULT_AMAZON_DATE_LOG)
            .priceInUsd(DEFAULT_PRICE_IN_USD)
            .amazonResposta(DEFAULT_AMAZON_RESPOSTA)
            .referenceId(DEFAULT_REFERENCE_ID)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return notificacaoEnviada;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NotificacaoEnviada createUpdatedEntity(EntityManager em) {
        NotificacaoEnviada notificacaoEnviada = new NotificacaoEnviada()
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .destinatarios(UPDATED_DESTINATARIOS)
            .tipo(UPDATED_TIPO)
            .status(UPDATED_STATUS)
            .assunto(UPDATED_ASSUNTO)
            .enviado(UPDATED_ENVIADO)
            .contador(UPDATED_CONTADOR)
            .amazonMessageId(UPDATED_AMAZON_MESSAGE_ID)
            .amazonDateLog(UPDATED_AMAZON_DATE_LOG)
            .priceInUsd(UPDATED_PRICE_IN_USD)
            .amazonResposta(UPDATED_AMAZON_RESPOSTA)
            .referenceId(UPDATED_REFERENCE_ID)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return notificacaoEnviada;
    }

    @BeforeEach
    public void initTest() {
        notificacaoEnviada = createEntity(em);
    }

    @Test
    @Transactional
    public void createNotificacaoEnviada() throws Exception {
        int databaseSizeBeforeCreate = notificacaoEnviadaRepository.findAll().size();
        // Create the NotificacaoEnviada
        NotificacaoEnviadaDTO notificacaoEnviadaDTO = notificacaoEnviadaMapper.toDto(notificacaoEnviada);
        restNotificacaoEnviadaMockMvc.perform(post("/api/notificacao-enviadas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificacaoEnviadaDTO)))
            .andExpect(status().isCreated());

        // Validate the NotificacaoEnviada in the database
        List<NotificacaoEnviada> notificacaoEnviadaList = notificacaoEnviadaRepository.findAll();
        assertThat(notificacaoEnviadaList).hasSize(databaseSizeBeforeCreate + 1);
        NotificacaoEnviada testNotificacaoEnviada = notificacaoEnviadaList.get(notificacaoEnviadaList.size() - 1);
        assertThat(testNotificacaoEnviada.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testNotificacaoEnviada.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testNotificacaoEnviada.getDestinatarios()).isEqualTo(DEFAULT_DESTINATARIOS);
        assertThat(testNotificacaoEnviada.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testNotificacaoEnviada.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testNotificacaoEnviada.getAssunto()).isEqualTo(DEFAULT_ASSUNTO);
        assertThat(testNotificacaoEnviada.getEnviado()).isEqualTo(DEFAULT_ENVIADO);
        assertThat(testNotificacaoEnviada.getContador()).isEqualTo(DEFAULT_CONTADOR);
        assertThat(testNotificacaoEnviada.getAmazonMessageId()).isEqualTo(DEFAULT_AMAZON_MESSAGE_ID);
        assertThat(testNotificacaoEnviada.getAmazonDateLog()).isEqualTo(DEFAULT_AMAZON_DATE_LOG);
        assertThat(testNotificacaoEnviada.getPriceInUsd()).isEqualTo(DEFAULT_PRICE_IN_USD);
        assertThat(testNotificacaoEnviada.getAmazonResposta()).isEqualTo(DEFAULT_AMAZON_RESPOSTA);
        assertThat(testNotificacaoEnviada.getReferenceId()).isEqualTo(DEFAULT_REFERENCE_ID);
        assertThat(testNotificacaoEnviada.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testNotificacaoEnviada.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createNotificacaoEnviadaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = notificacaoEnviadaRepository.findAll().size();

        // Create the NotificacaoEnviada with an existing ID
        notificacaoEnviada.setId(1L);
        NotificacaoEnviadaDTO notificacaoEnviadaDTO = notificacaoEnviadaMapper.toDto(notificacaoEnviada);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotificacaoEnviadaMockMvc.perform(post("/api/notificacao-enviadas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificacaoEnviadaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NotificacaoEnviada in the database
        List<NotificacaoEnviada> notificacaoEnviadaList = notificacaoEnviadaRepository.findAll();
        assertThat(notificacaoEnviadaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificacaoEnviadaRepository.findAll().size();
        // set the field null
        notificacaoEnviada.setName(null);

        // Create the NotificacaoEnviada, which fails.
        NotificacaoEnviadaDTO notificacaoEnviadaDTO = notificacaoEnviadaMapper.toDto(notificacaoEnviada);


        restNotificacaoEnviadaMockMvc.perform(post("/api/notificacao-enviadas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificacaoEnviadaDTO)))
            .andExpect(status().isBadRequest());

        List<NotificacaoEnviada> notificacaoEnviadaList = notificacaoEnviadaRepository.findAll();
        assertThat(notificacaoEnviadaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificacaoEnviadaRepository.findAll().size();
        // set the field null
        notificacaoEnviada.setDescricao(null);

        // Create the NotificacaoEnviada, which fails.
        NotificacaoEnviadaDTO notificacaoEnviadaDTO = notificacaoEnviadaMapper.toDto(notificacaoEnviada);


        restNotificacaoEnviadaMockMvc.perform(post("/api/notificacao-enviadas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificacaoEnviadaDTO)))
            .andExpect(status().isBadRequest());

        List<NotificacaoEnviada> notificacaoEnviadaList = notificacaoEnviadaRepository.findAll();
        assertThat(notificacaoEnviadaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmazonDateLogIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificacaoEnviadaRepository.findAll().size();
        // set the field null
        notificacaoEnviada.setAmazonDateLog(null);

        // Create the NotificacaoEnviada, which fails.
        NotificacaoEnviadaDTO notificacaoEnviadaDTO = notificacaoEnviadaMapper.toDto(notificacaoEnviada);


        restNotificacaoEnviadaMockMvc.perform(post("/api/notificacao-enviadas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificacaoEnviadaDTO)))
            .andExpect(status().isBadRequest());

        List<NotificacaoEnviada> notificacaoEnviadaList = notificacaoEnviadaRepository.findAll();
        assertThat(notificacaoEnviadaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificacaoEnviadaRepository.findAll().size();
        // set the field null
        notificacaoEnviada.setCreated(null);

        // Create the NotificacaoEnviada, which fails.
        NotificacaoEnviadaDTO notificacaoEnviadaDTO = notificacaoEnviadaMapper.toDto(notificacaoEnviada);


        restNotificacaoEnviadaMockMvc.perform(post("/api/notificacao-enviadas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificacaoEnviadaDTO)))
            .andExpect(status().isBadRequest());

        List<NotificacaoEnviada> notificacaoEnviadaList = notificacaoEnviadaRepository.findAll();
        assertThat(notificacaoEnviadaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadas() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList
        restNotificacaoEnviadaMockMvc.perform(get("/api/notificacao-enviadas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notificacaoEnviada.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].destinatarios").value(hasItem(DEFAULT_DESTINATARIOS)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].assunto").value(hasItem(DEFAULT_ASSUNTO)))
            .andExpect(jsonPath("$.[*].enviado").value(hasItem(DEFAULT_ENVIADO)))
            .andExpect(jsonPath("$.[*].contador").value(hasItem(DEFAULT_CONTADOR)))
            .andExpect(jsonPath("$.[*].amazonMessageId").value(hasItem(DEFAULT_AMAZON_MESSAGE_ID)))
            .andExpect(jsonPath("$.[*].amazonDateLog").value(hasItem(DEFAULT_AMAZON_DATE_LOG.toString())))
            .andExpect(jsonPath("$.[*].priceInUsd").value(hasItem(DEFAULT_PRICE_IN_USD.doubleValue())))
            .andExpect(jsonPath("$.[*].amazonResposta").value(hasItem(DEFAULT_AMAZON_RESPOSTA)))
            .andExpect(jsonPath("$.[*].referenceId").value(hasItem(DEFAULT_REFERENCE_ID)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getNotificacaoEnviada() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get the notificacaoEnviada
        restNotificacaoEnviadaMockMvc.perform(get("/api/notificacao-enviadas/{id}", notificacaoEnviada.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(notificacaoEnviada.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.destinatarios").value(DEFAULT_DESTINATARIOS))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.assunto").value(DEFAULT_ASSUNTO))
            .andExpect(jsonPath("$.enviado").value(DEFAULT_ENVIADO))
            .andExpect(jsonPath("$.contador").value(DEFAULT_CONTADOR))
            .andExpect(jsonPath("$.amazonMessageId").value(DEFAULT_AMAZON_MESSAGE_ID))
            .andExpect(jsonPath("$.amazonDateLog").value(DEFAULT_AMAZON_DATE_LOG.toString()))
            .andExpect(jsonPath("$.priceInUsd").value(DEFAULT_PRICE_IN_USD.doubleValue()))
            .andExpect(jsonPath("$.amazonResposta").value(DEFAULT_AMAZON_RESPOSTA))
            .andExpect(jsonPath("$.referenceId").value(DEFAULT_REFERENCE_ID))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }


    @Test
    @Transactional
    public void getNotificacaoEnviadasByIdFiltering() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        Long id = notificacaoEnviada.getId();

        defaultNotificacaoEnviadaShouldBeFound("id.equals=" + id);
        defaultNotificacaoEnviadaShouldNotBeFound("id.notEquals=" + id);

        defaultNotificacaoEnviadaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNotificacaoEnviadaShouldNotBeFound("id.greaterThan=" + id);

        defaultNotificacaoEnviadaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNotificacaoEnviadaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where name equals to DEFAULT_NAME
        defaultNotificacaoEnviadaShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the notificacaoEnviadaList where name equals to UPDATED_NAME
        defaultNotificacaoEnviadaShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where name not equals to DEFAULT_NAME
        defaultNotificacaoEnviadaShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the notificacaoEnviadaList where name not equals to UPDATED_NAME
        defaultNotificacaoEnviadaShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByNameIsInShouldWork() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where name in DEFAULT_NAME or UPDATED_NAME
        defaultNotificacaoEnviadaShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the notificacaoEnviadaList where name equals to UPDATED_NAME
        defaultNotificacaoEnviadaShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where name is not null
        defaultNotificacaoEnviadaShouldBeFound("name.specified=true");

        // Get all the notificacaoEnviadaList where name is null
        defaultNotificacaoEnviadaShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllNotificacaoEnviadasByNameContainsSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where name contains DEFAULT_NAME
        defaultNotificacaoEnviadaShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the notificacaoEnviadaList where name contains UPDATED_NAME
        defaultNotificacaoEnviadaShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByNameNotContainsSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where name does not contain DEFAULT_NAME
        defaultNotificacaoEnviadaShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the notificacaoEnviadaList where name does not contain UPDATED_NAME
        defaultNotificacaoEnviadaShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where descricao equals to DEFAULT_DESCRICAO
        defaultNotificacaoEnviadaShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the notificacaoEnviadaList where descricao equals to UPDATED_DESCRICAO
        defaultNotificacaoEnviadaShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where descricao not equals to DEFAULT_DESCRICAO
        defaultNotificacaoEnviadaShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the notificacaoEnviadaList where descricao not equals to UPDATED_DESCRICAO
        defaultNotificacaoEnviadaShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultNotificacaoEnviadaShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the notificacaoEnviadaList where descricao equals to UPDATED_DESCRICAO
        defaultNotificacaoEnviadaShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where descricao is not null
        defaultNotificacaoEnviadaShouldBeFound("descricao.specified=true");

        // Get all the notificacaoEnviadaList where descricao is null
        defaultNotificacaoEnviadaShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllNotificacaoEnviadasByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where descricao contains DEFAULT_DESCRICAO
        defaultNotificacaoEnviadaShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the notificacaoEnviadaList where descricao contains UPDATED_DESCRICAO
        defaultNotificacaoEnviadaShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where descricao does not contain DEFAULT_DESCRICAO
        defaultNotificacaoEnviadaShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the notificacaoEnviadaList where descricao does not contain UPDATED_DESCRICAO
        defaultNotificacaoEnviadaShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByDestinatariosIsEqualToSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where destinatarios equals to DEFAULT_DESTINATARIOS
        defaultNotificacaoEnviadaShouldBeFound("destinatarios.equals=" + DEFAULT_DESTINATARIOS);

        // Get all the notificacaoEnviadaList where destinatarios equals to UPDATED_DESTINATARIOS
        defaultNotificacaoEnviadaShouldNotBeFound("destinatarios.equals=" + UPDATED_DESTINATARIOS);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByDestinatariosIsNotEqualToSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where destinatarios not equals to DEFAULT_DESTINATARIOS
        defaultNotificacaoEnviadaShouldNotBeFound("destinatarios.notEquals=" + DEFAULT_DESTINATARIOS);

        // Get all the notificacaoEnviadaList where destinatarios not equals to UPDATED_DESTINATARIOS
        defaultNotificacaoEnviadaShouldBeFound("destinatarios.notEquals=" + UPDATED_DESTINATARIOS);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByDestinatariosIsInShouldWork() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where destinatarios in DEFAULT_DESTINATARIOS or UPDATED_DESTINATARIOS
        defaultNotificacaoEnviadaShouldBeFound("destinatarios.in=" + DEFAULT_DESTINATARIOS + "," + UPDATED_DESTINATARIOS);

        // Get all the notificacaoEnviadaList where destinatarios equals to UPDATED_DESTINATARIOS
        defaultNotificacaoEnviadaShouldNotBeFound("destinatarios.in=" + UPDATED_DESTINATARIOS);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByDestinatariosIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where destinatarios is not null
        defaultNotificacaoEnviadaShouldBeFound("destinatarios.specified=true");

        // Get all the notificacaoEnviadaList where destinatarios is null
        defaultNotificacaoEnviadaShouldNotBeFound("destinatarios.specified=false");
    }
                @Test
    @Transactional
    public void getAllNotificacaoEnviadasByDestinatariosContainsSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where destinatarios contains DEFAULT_DESTINATARIOS
        defaultNotificacaoEnviadaShouldBeFound("destinatarios.contains=" + DEFAULT_DESTINATARIOS);

        // Get all the notificacaoEnviadaList where destinatarios contains UPDATED_DESTINATARIOS
        defaultNotificacaoEnviadaShouldNotBeFound("destinatarios.contains=" + UPDATED_DESTINATARIOS);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByDestinatariosNotContainsSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where destinatarios does not contain DEFAULT_DESTINATARIOS
        defaultNotificacaoEnviadaShouldNotBeFound("destinatarios.doesNotContain=" + DEFAULT_DESTINATARIOS);

        // Get all the notificacaoEnviadaList where destinatarios does not contain UPDATED_DESTINATARIOS
        defaultNotificacaoEnviadaShouldBeFound("destinatarios.doesNotContain=" + UPDATED_DESTINATARIOS);
    }


    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByTipoIsEqualToSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where tipo equals to DEFAULT_TIPO
        defaultNotificacaoEnviadaShouldBeFound("tipo.equals=" + DEFAULT_TIPO);

        // Get all the notificacaoEnviadaList where tipo equals to UPDATED_TIPO
        defaultNotificacaoEnviadaShouldNotBeFound("tipo.equals=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByTipoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where tipo not equals to DEFAULT_TIPO
        defaultNotificacaoEnviadaShouldNotBeFound("tipo.notEquals=" + DEFAULT_TIPO);

        // Get all the notificacaoEnviadaList where tipo not equals to UPDATED_TIPO
        defaultNotificacaoEnviadaShouldBeFound("tipo.notEquals=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByTipoIsInShouldWork() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where tipo in DEFAULT_TIPO or UPDATED_TIPO
        defaultNotificacaoEnviadaShouldBeFound("tipo.in=" + DEFAULT_TIPO + "," + UPDATED_TIPO);

        // Get all the notificacaoEnviadaList where tipo equals to UPDATED_TIPO
        defaultNotificacaoEnviadaShouldNotBeFound("tipo.in=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByTipoIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where tipo is not null
        defaultNotificacaoEnviadaShouldBeFound("tipo.specified=true");

        // Get all the notificacaoEnviadaList where tipo is null
        defaultNotificacaoEnviadaShouldNotBeFound("tipo.specified=false");
    }
                @Test
    @Transactional
    public void getAllNotificacaoEnviadasByTipoContainsSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where tipo contains DEFAULT_TIPO
        defaultNotificacaoEnviadaShouldBeFound("tipo.contains=" + DEFAULT_TIPO);

        // Get all the notificacaoEnviadaList where tipo contains UPDATED_TIPO
        defaultNotificacaoEnviadaShouldNotBeFound("tipo.contains=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByTipoNotContainsSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where tipo does not contain DEFAULT_TIPO
        defaultNotificacaoEnviadaShouldNotBeFound("tipo.doesNotContain=" + DEFAULT_TIPO);

        // Get all the notificacaoEnviadaList where tipo does not contain UPDATED_TIPO
        defaultNotificacaoEnviadaShouldBeFound("tipo.doesNotContain=" + UPDATED_TIPO);
    }


    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where status equals to DEFAULT_STATUS
        defaultNotificacaoEnviadaShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the notificacaoEnviadaList where status equals to UPDATED_STATUS
        defaultNotificacaoEnviadaShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where status not equals to DEFAULT_STATUS
        defaultNotificacaoEnviadaShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the notificacaoEnviadaList where status not equals to UPDATED_STATUS
        defaultNotificacaoEnviadaShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultNotificacaoEnviadaShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the notificacaoEnviadaList where status equals to UPDATED_STATUS
        defaultNotificacaoEnviadaShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where status is not null
        defaultNotificacaoEnviadaShouldBeFound("status.specified=true");

        // Get all the notificacaoEnviadaList where status is null
        defaultNotificacaoEnviadaShouldNotBeFound("status.specified=false");
    }
                @Test
    @Transactional
    public void getAllNotificacaoEnviadasByStatusContainsSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where status contains DEFAULT_STATUS
        defaultNotificacaoEnviadaShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the notificacaoEnviadaList where status contains UPDATED_STATUS
        defaultNotificacaoEnviadaShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where status does not contain DEFAULT_STATUS
        defaultNotificacaoEnviadaShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the notificacaoEnviadaList where status does not contain UPDATED_STATUS
        defaultNotificacaoEnviadaShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }


    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByAssuntoIsEqualToSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where assunto equals to DEFAULT_ASSUNTO
        defaultNotificacaoEnviadaShouldBeFound("assunto.equals=" + DEFAULT_ASSUNTO);

        // Get all the notificacaoEnviadaList where assunto equals to UPDATED_ASSUNTO
        defaultNotificacaoEnviadaShouldNotBeFound("assunto.equals=" + UPDATED_ASSUNTO);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByAssuntoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where assunto not equals to DEFAULT_ASSUNTO
        defaultNotificacaoEnviadaShouldNotBeFound("assunto.notEquals=" + DEFAULT_ASSUNTO);

        // Get all the notificacaoEnviadaList where assunto not equals to UPDATED_ASSUNTO
        defaultNotificacaoEnviadaShouldBeFound("assunto.notEquals=" + UPDATED_ASSUNTO);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByAssuntoIsInShouldWork() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where assunto in DEFAULT_ASSUNTO or UPDATED_ASSUNTO
        defaultNotificacaoEnviadaShouldBeFound("assunto.in=" + DEFAULT_ASSUNTO + "," + UPDATED_ASSUNTO);

        // Get all the notificacaoEnviadaList where assunto equals to UPDATED_ASSUNTO
        defaultNotificacaoEnviadaShouldNotBeFound("assunto.in=" + UPDATED_ASSUNTO);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByAssuntoIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where assunto is not null
        defaultNotificacaoEnviadaShouldBeFound("assunto.specified=true");

        // Get all the notificacaoEnviadaList where assunto is null
        defaultNotificacaoEnviadaShouldNotBeFound("assunto.specified=false");
    }
                @Test
    @Transactional
    public void getAllNotificacaoEnviadasByAssuntoContainsSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where assunto contains DEFAULT_ASSUNTO
        defaultNotificacaoEnviadaShouldBeFound("assunto.contains=" + DEFAULT_ASSUNTO);

        // Get all the notificacaoEnviadaList where assunto contains UPDATED_ASSUNTO
        defaultNotificacaoEnviadaShouldNotBeFound("assunto.contains=" + UPDATED_ASSUNTO);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByAssuntoNotContainsSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where assunto does not contain DEFAULT_ASSUNTO
        defaultNotificacaoEnviadaShouldNotBeFound("assunto.doesNotContain=" + DEFAULT_ASSUNTO);

        // Get all the notificacaoEnviadaList where assunto does not contain UPDATED_ASSUNTO
        defaultNotificacaoEnviadaShouldBeFound("assunto.doesNotContain=" + UPDATED_ASSUNTO);
    }


    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByEnviadoIsEqualToSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where enviado equals to DEFAULT_ENVIADO
        defaultNotificacaoEnviadaShouldBeFound("enviado.equals=" + DEFAULT_ENVIADO);

        // Get all the notificacaoEnviadaList where enviado equals to UPDATED_ENVIADO
        defaultNotificacaoEnviadaShouldNotBeFound("enviado.equals=" + UPDATED_ENVIADO);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByEnviadoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where enviado not equals to DEFAULT_ENVIADO
        defaultNotificacaoEnviadaShouldNotBeFound("enviado.notEquals=" + DEFAULT_ENVIADO);

        // Get all the notificacaoEnviadaList where enviado not equals to UPDATED_ENVIADO
        defaultNotificacaoEnviadaShouldBeFound("enviado.notEquals=" + UPDATED_ENVIADO);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByEnviadoIsInShouldWork() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where enviado in DEFAULT_ENVIADO or UPDATED_ENVIADO
        defaultNotificacaoEnviadaShouldBeFound("enviado.in=" + DEFAULT_ENVIADO + "," + UPDATED_ENVIADO);

        // Get all the notificacaoEnviadaList where enviado equals to UPDATED_ENVIADO
        defaultNotificacaoEnviadaShouldNotBeFound("enviado.in=" + UPDATED_ENVIADO);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByEnviadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where enviado is not null
        defaultNotificacaoEnviadaShouldBeFound("enviado.specified=true");

        // Get all the notificacaoEnviadaList where enviado is null
        defaultNotificacaoEnviadaShouldNotBeFound("enviado.specified=false");
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByEnviadoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where enviado is greater than or equal to DEFAULT_ENVIADO
        defaultNotificacaoEnviadaShouldBeFound("enviado.greaterThanOrEqual=" + DEFAULT_ENVIADO);

        // Get all the notificacaoEnviadaList where enviado is greater than or equal to UPDATED_ENVIADO
        defaultNotificacaoEnviadaShouldNotBeFound("enviado.greaterThanOrEqual=" + UPDATED_ENVIADO);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByEnviadoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where enviado is less than or equal to DEFAULT_ENVIADO
        defaultNotificacaoEnviadaShouldBeFound("enviado.lessThanOrEqual=" + DEFAULT_ENVIADO);

        // Get all the notificacaoEnviadaList where enviado is less than or equal to SMALLER_ENVIADO
        defaultNotificacaoEnviadaShouldNotBeFound("enviado.lessThanOrEqual=" + SMALLER_ENVIADO);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByEnviadoIsLessThanSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where enviado is less than DEFAULT_ENVIADO
        defaultNotificacaoEnviadaShouldNotBeFound("enviado.lessThan=" + DEFAULT_ENVIADO);

        // Get all the notificacaoEnviadaList where enviado is less than UPDATED_ENVIADO
        defaultNotificacaoEnviadaShouldBeFound("enviado.lessThan=" + UPDATED_ENVIADO);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByEnviadoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where enviado is greater than DEFAULT_ENVIADO
        defaultNotificacaoEnviadaShouldNotBeFound("enviado.greaterThan=" + DEFAULT_ENVIADO);

        // Get all the notificacaoEnviadaList where enviado is greater than SMALLER_ENVIADO
        defaultNotificacaoEnviadaShouldBeFound("enviado.greaterThan=" + SMALLER_ENVIADO);
    }


    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByContadorIsEqualToSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where contador equals to DEFAULT_CONTADOR
        defaultNotificacaoEnviadaShouldBeFound("contador.equals=" + DEFAULT_CONTADOR);

        // Get all the notificacaoEnviadaList where contador equals to UPDATED_CONTADOR
        defaultNotificacaoEnviadaShouldNotBeFound("contador.equals=" + UPDATED_CONTADOR);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByContadorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where contador not equals to DEFAULT_CONTADOR
        defaultNotificacaoEnviadaShouldNotBeFound("contador.notEquals=" + DEFAULT_CONTADOR);

        // Get all the notificacaoEnviadaList where contador not equals to UPDATED_CONTADOR
        defaultNotificacaoEnviadaShouldBeFound("contador.notEquals=" + UPDATED_CONTADOR);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByContadorIsInShouldWork() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where contador in DEFAULT_CONTADOR or UPDATED_CONTADOR
        defaultNotificacaoEnviadaShouldBeFound("contador.in=" + DEFAULT_CONTADOR + "," + UPDATED_CONTADOR);

        // Get all the notificacaoEnviadaList where contador equals to UPDATED_CONTADOR
        defaultNotificacaoEnviadaShouldNotBeFound("contador.in=" + UPDATED_CONTADOR);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByContadorIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where contador is not null
        defaultNotificacaoEnviadaShouldBeFound("contador.specified=true");

        // Get all the notificacaoEnviadaList where contador is null
        defaultNotificacaoEnviadaShouldNotBeFound("contador.specified=false");
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByContadorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where contador is greater than or equal to DEFAULT_CONTADOR
        defaultNotificacaoEnviadaShouldBeFound("contador.greaterThanOrEqual=" + DEFAULT_CONTADOR);

        // Get all the notificacaoEnviadaList where contador is greater than or equal to UPDATED_CONTADOR
        defaultNotificacaoEnviadaShouldNotBeFound("contador.greaterThanOrEqual=" + UPDATED_CONTADOR);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByContadorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where contador is less than or equal to DEFAULT_CONTADOR
        defaultNotificacaoEnviadaShouldBeFound("contador.lessThanOrEqual=" + DEFAULT_CONTADOR);

        // Get all the notificacaoEnviadaList where contador is less than or equal to SMALLER_CONTADOR
        defaultNotificacaoEnviadaShouldNotBeFound("contador.lessThanOrEqual=" + SMALLER_CONTADOR);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByContadorIsLessThanSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where contador is less than DEFAULT_CONTADOR
        defaultNotificacaoEnviadaShouldNotBeFound("contador.lessThan=" + DEFAULT_CONTADOR);

        // Get all the notificacaoEnviadaList where contador is less than UPDATED_CONTADOR
        defaultNotificacaoEnviadaShouldBeFound("contador.lessThan=" + UPDATED_CONTADOR);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByContadorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where contador is greater than DEFAULT_CONTADOR
        defaultNotificacaoEnviadaShouldNotBeFound("contador.greaterThan=" + DEFAULT_CONTADOR);

        // Get all the notificacaoEnviadaList where contador is greater than SMALLER_CONTADOR
        defaultNotificacaoEnviadaShouldBeFound("contador.greaterThan=" + SMALLER_CONTADOR);
    }


    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByAmazonMessageIdIsEqualToSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where amazonMessageId equals to DEFAULT_AMAZON_MESSAGE_ID
        defaultNotificacaoEnviadaShouldBeFound("amazonMessageId.equals=" + DEFAULT_AMAZON_MESSAGE_ID);

        // Get all the notificacaoEnviadaList where amazonMessageId equals to UPDATED_AMAZON_MESSAGE_ID
        defaultNotificacaoEnviadaShouldNotBeFound("amazonMessageId.equals=" + UPDATED_AMAZON_MESSAGE_ID);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByAmazonMessageIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where amazonMessageId not equals to DEFAULT_AMAZON_MESSAGE_ID
        defaultNotificacaoEnviadaShouldNotBeFound("amazonMessageId.notEquals=" + DEFAULT_AMAZON_MESSAGE_ID);

        // Get all the notificacaoEnviadaList where amazonMessageId not equals to UPDATED_AMAZON_MESSAGE_ID
        defaultNotificacaoEnviadaShouldBeFound("amazonMessageId.notEquals=" + UPDATED_AMAZON_MESSAGE_ID);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByAmazonMessageIdIsInShouldWork() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where amazonMessageId in DEFAULT_AMAZON_MESSAGE_ID or UPDATED_AMAZON_MESSAGE_ID
        defaultNotificacaoEnviadaShouldBeFound("amazonMessageId.in=" + DEFAULT_AMAZON_MESSAGE_ID + "," + UPDATED_AMAZON_MESSAGE_ID);

        // Get all the notificacaoEnviadaList where amazonMessageId equals to UPDATED_AMAZON_MESSAGE_ID
        defaultNotificacaoEnviadaShouldNotBeFound("amazonMessageId.in=" + UPDATED_AMAZON_MESSAGE_ID);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByAmazonMessageIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where amazonMessageId is not null
        defaultNotificacaoEnviadaShouldBeFound("amazonMessageId.specified=true");

        // Get all the notificacaoEnviadaList where amazonMessageId is null
        defaultNotificacaoEnviadaShouldNotBeFound("amazonMessageId.specified=false");
    }
                @Test
    @Transactional
    public void getAllNotificacaoEnviadasByAmazonMessageIdContainsSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where amazonMessageId contains DEFAULT_AMAZON_MESSAGE_ID
        defaultNotificacaoEnviadaShouldBeFound("amazonMessageId.contains=" + DEFAULT_AMAZON_MESSAGE_ID);

        // Get all the notificacaoEnviadaList where amazonMessageId contains UPDATED_AMAZON_MESSAGE_ID
        defaultNotificacaoEnviadaShouldNotBeFound("amazonMessageId.contains=" + UPDATED_AMAZON_MESSAGE_ID);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByAmazonMessageIdNotContainsSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where amazonMessageId does not contain DEFAULT_AMAZON_MESSAGE_ID
        defaultNotificacaoEnviadaShouldNotBeFound("amazonMessageId.doesNotContain=" + DEFAULT_AMAZON_MESSAGE_ID);

        // Get all the notificacaoEnviadaList where amazonMessageId does not contain UPDATED_AMAZON_MESSAGE_ID
        defaultNotificacaoEnviadaShouldBeFound("amazonMessageId.doesNotContain=" + UPDATED_AMAZON_MESSAGE_ID);
    }


    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByAmazonDateLogIsEqualToSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where amazonDateLog equals to DEFAULT_AMAZON_DATE_LOG
        defaultNotificacaoEnviadaShouldBeFound("amazonDateLog.equals=" + DEFAULT_AMAZON_DATE_LOG);

        // Get all the notificacaoEnviadaList where amazonDateLog equals to UPDATED_AMAZON_DATE_LOG
        defaultNotificacaoEnviadaShouldNotBeFound("amazonDateLog.equals=" + UPDATED_AMAZON_DATE_LOG);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByAmazonDateLogIsNotEqualToSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where amazonDateLog not equals to DEFAULT_AMAZON_DATE_LOG
        defaultNotificacaoEnviadaShouldNotBeFound("amazonDateLog.notEquals=" + DEFAULT_AMAZON_DATE_LOG);

        // Get all the notificacaoEnviadaList where amazonDateLog not equals to UPDATED_AMAZON_DATE_LOG
        defaultNotificacaoEnviadaShouldBeFound("amazonDateLog.notEquals=" + UPDATED_AMAZON_DATE_LOG);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByAmazonDateLogIsInShouldWork() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where amazonDateLog in DEFAULT_AMAZON_DATE_LOG or UPDATED_AMAZON_DATE_LOG
        defaultNotificacaoEnviadaShouldBeFound("amazonDateLog.in=" + DEFAULT_AMAZON_DATE_LOG + "," + UPDATED_AMAZON_DATE_LOG);

        // Get all the notificacaoEnviadaList where amazonDateLog equals to UPDATED_AMAZON_DATE_LOG
        defaultNotificacaoEnviadaShouldNotBeFound("amazonDateLog.in=" + UPDATED_AMAZON_DATE_LOG);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByAmazonDateLogIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where amazonDateLog is not null
        defaultNotificacaoEnviadaShouldBeFound("amazonDateLog.specified=true");

        // Get all the notificacaoEnviadaList where amazonDateLog is null
        defaultNotificacaoEnviadaShouldNotBeFound("amazonDateLog.specified=false");
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByPriceInUsdIsEqualToSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where priceInUsd equals to DEFAULT_PRICE_IN_USD
        defaultNotificacaoEnviadaShouldBeFound("priceInUsd.equals=" + DEFAULT_PRICE_IN_USD);

        // Get all the notificacaoEnviadaList where priceInUsd equals to UPDATED_PRICE_IN_USD
        defaultNotificacaoEnviadaShouldNotBeFound("priceInUsd.equals=" + UPDATED_PRICE_IN_USD);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByPriceInUsdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where priceInUsd not equals to DEFAULT_PRICE_IN_USD
        defaultNotificacaoEnviadaShouldNotBeFound("priceInUsd.notEquals=" + DEFAULT_PRICE_IN_USD);

        // Get all the notificacaoEnviadaList where priceInUsd not equals to UPDATED_PRICE_IN_USD
        defaultNotificacaoEnviadaShouldBeFound("priceInUsd.notEquals=" + UPDATED_PRICE_IN_USD);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByPriceInUsdIsInShouldWork() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where priceInUsd in DEFAULT_PRICE_IN_USD or UPDATED_PRICE_IN_USD
        defaultNotificacaoEnviadaShouldBeFound("priceInUsd.in=" + DEFAULT_PRICE_IN_USD + "," + UPDATED_PRICE_IN_USD);

        // Get all the notificacaoEnviadaList where priceInUsd equals to UPDATED_PRICE_IN_USD
        defaultNotificacaoEnviadaShouldNotBeFound("priceInUsd.in=" + UPDATED_PRICE_IN_USD);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByPriceInUsdIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where priceInUsd is not null
        defaultNotificacaoEnviadaShouldBeFound("priceInUsd.specified=true");

        // Get all the notificacaoEnviadaList where priceInUsd is null
        defaultNotificacaoEnviadaShouldNotBeFound("priceInUsd.specified=false");
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByPriceInUsdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where priceInUsd is greater than or equal to DEFAULT_PRICE_IN_USD
        defaultNotificacaoEnviadaShouldBeFound("priceInUsd.greaterThanOrEqual=" + DEFAULT_PRICE_IN_USD);

        // Get all the notificacaoEnviadaList where priceInUsd is greater than or equal to UPDATED_PRICE_IN_USD
        defaultNotificacaoEnviadaShouldNotBeFound("priceInUsd.greaterThanOrEqual=" + UPDATED_PRICE_IN_USD);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByPriceInUsdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where priceInUsd is less than or equal to DEFAULT_PRICE_IN_USD
        defaultNotificacaoEnviadaShouldBeFound("priceInUsd.lessThanOrEqual=" + DEFAULT_PRICE_IN_USD);

        // Get all the notificacaoEnviadaList where priceInUsd is less than or equal to SMALLER_PRICE_IN_USD
        defaultNotificacaoEnviadaShouldNotBeFound("priceInUsd.lessThanOrEqual=" + SMALLER_PRICE_IN_USD);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByPriceInUsdIsLessThanSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where priceInUsd is less than DEFAULT_PRICE_IN_USD
        defaultNotificacaoEnviadaShouldNotBeFound("priceInUsd.lessThan=" + DEFAULT_PRICE_IN_USD);

        // Get all the notificacaoEnviadaList where priceInUsd is less than UPDATED_PRICE_IN_USD
        defaultNotificacaoEnviadaShouldBeFound("priceInUsd.lessThan=" + UPDATED_PRICE_IN_USD);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByPriceInUsdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where priceInUsd is greater than DEFAULT_PRICE_IN_USD
        defaultNotificacaoEnviadaShouldNotBeFound("priceInUsd.greaterThan=" + DEFAULT_PRICE_IN_USD);

        // Get all the notificacaoEnviadaList where priceInUsd is greater than SMALLER_PRICE_IN_USD
        defaultNotificacaoEnviadaShouldBeFound("priceInUsd.greaterThan=" + SMALLER_PRICE_IN_USD);
    }


    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByAmazonRespostaIsEqualToSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where amazonResposta equals to DEFAULT_AMAZON_RESPOSTA
        defaultNotificacaoEnviadaShouldBeFound("amazonResposta.equals=" + DEFAULT_AMAZON_RESPOSTA);

        // Get all the notificacaoEnviadaList where amazonResposta equals to UPDATED_AMAZON_RESPOSTA
        defaultNotificacaoEnviadaShouldNotBeFound("amazonResposta.equals=" + UPDATED_AMAZON_RESPOSTA);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByAmazonRespostaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where amazonResposta not equals to DEFAULT_AMAZON_RESPOSTA
        defaultNotificacaoEnviadaShouldNotBeFound("amazonResposta.notEquals=" + DEFAULT_AMAZON_RESPOSTA);

        // Get all the notificacaoEnviadaList where amazonResposta not equals to UPDATED_AMAZON_RESPOSTA
        defaultNotificacaoEnviadaShouldBeFound("amazonResposta.notEquals=" + UPDATED_AMAZON_RESPOSTA);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByAmazonRespostaIsInShouldWork() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where amazonResposta in DEFAULT_AMAZON_RESPOSTA or UPDATED_AMAZON_RESPOSTA
        defaultNotificacaoEnviadaShouldBeFound("amazonResposta.in=" + DEFAULT_AMAZON_RESPOSTA + "," + UPDATED_AMAZON_RESPOSTA);

        // Get all the notificacaoEnviadaList where amazonResposta equals to UPDATED_AMAZON_RESPOSTA
        defaultNotificacaoEnviadaShouldNotBeFound("amazonResposta.in=" + UPDATED_AMAZON_RESPOSTA);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByAmazonRespostaIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where amazonResposta is not null
        defaultNotificacaoEnviadaShouldBeFound("amazonResposta.specified=true");

        // Get all the notificacaoEnviadaList where amazonResposta is null
        defaultNotificacaoEnviadaShouldNotBeFound("amazonResposta.specified=false");
    }
                @Test
    @Transactional
    public void getAllNotificacaoEnviadasByAmazonRespostaContainsSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where amazonResposta contains DEFAULT_AMAZON_RESPOSTA
        defaultNotificacaoEnviadaShouldBeFound("amazonResposta.contains=" + DEFAULT_AMAZON_RESPOSTA);

        // Get all the notificacaoEnviadaList where amazonResposta contains UPDATED_AMAZON_RESPOSTA
        defaultNotificacaoEnviadaShouldNotBeFound("amazonResposta.contains=" + UPDATED_AMAZON_RESPOSTA);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByAmazonRespostaNotContainsSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where amazonResposta does not contain DEFAULT_AMAZON_RESPOSTA
        defaultNotificacaoEnviadaShouldNotBeFound("amazonResposta.doesNotContain=" + DEFAULT_AMAZON_RESPOSTA);

        // Get all the notificacaoEnviadaList where amazonResposta does not contain UPDATED_AMAZON_RESPOSTA
        defaultNotificacaoEnviadaShouldBeFound("amazonResposta.doesNotContain=" + UPDATED_AMAZON_RESPOSTA);
    }


    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByReferenceIdIsEqualToSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where referenceId equals to DEFAULT_REFERENCE_ID
        defaultNotificacaoEnviadaShouldBeFound("referenceId.equals=" + DEFAULT_REFERENCE_ID);

        // Get all the notificacaoEnviadaList where referenceId equals to UPDATED_REFERENCE_ID
        defaultNotificacaoEnviadaShouldNotBeFound("referenceId.equals=" + UPDATED_REFERENCE_ID);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByReferenceIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where referenceId not equals to DEFAULT_REFERENCE_ID
        defaultNotificacaoEnviadaShouldNotBeFound("referenceId.notEquals=" + DEFAULT_REFERENCE_ID);

        // Get all the notificacaoEnviadaList where referenceId not equals to UPDATED_REFERENCE_ID
        defaultNotificacaoEnviadaShouldBeFound("referenceId.notEquals=" + UPDATED_REFERENCE_ID);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByReferenceIdIsInShouldWork() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where referenceId in DEFAULT_REFERENCE_ID or UPDATED_REFERENCE_ID
        defaultNotificacaoEnviadaShouldBeFound("referenceId.in=" + DEFAULT_REFERENCE_ID + "," + UPDATED_REFERENCE_ID);

        // Get all the notificacaoEnviadaList where referenceId equals to UPDATED_REFERENCE_ID
        defaultNotificacaoEnviadaShouldNotBeFound("referenceId.in=" + UPDATED_REFERENCE_ID);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByReferenceIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where referenceId is not null
        defaultNotificacaoEnviadaShouldBeFound("referenceId.specified=true");

        // Get all the notificacaoEnviadaList where referenceId is null
        defaultNotificacaoEnviadaShouldNotBeFound("referenceId.specified=false");
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByReferenceIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where referenceId is greater than or equal to DEFAULT_REFERENCE_ID
        defaultNotificacaoEnviadaShouldBeFound("referenceId.greaterThanOrEqual=" + DEFAULT_REFERENCE_ID);

        // Get all the notificacaoEnviadaList where referenceId is greater than or equal to UPDATED_REFERENCE_ID
        defaultNotificacaoEnviadaShouldNotBeFound("referenceId.greaterThanOrEqual=" + UPDATED_REFERENCE_ID);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByReferenceIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where referenceId is less than or equal to DEFAULT_REFERENCE_ID
        defaultNotificacaoEnviadaShouldBeFound("referenceId.lessThanOrEqual=" + DEFAULT_REFERENCE_ID);

        // Get all the notificacaoEnviadaList where referenceId is less than or equal to SMALLER_REFERENCE_ID
        defaultNotificacaoEnviadaShouldNotBeFound("referenceId.lessThanOrEqual=" + SMALLER_REFERENCE_ID);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByReferenceIdIsLessThanSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where referenceId is less than DEFAULT_REFERENCE_ID
        defaultNotificacaoEnviadaShouldNotBeFound("referenceId.lessThan=" + DEFAULT_REFERENCE_ID);

        // Get all the notificacaoEnviadaList where referenceId is less than UPDATED_REFERENCE_ID
        defaultNotificacaoEnviadaShouldBeFound("referenceId.lessThan=" + UPDATED_REFERENCE_ID);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByReferenceIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where referenceId is greater than DEFAULT_REFERENCE_ID
        defaultNotificacaoEnviadaShouldNotBeFound("referenceId.greaterThan=" + DEFAULT_REFERENCE_ID);

        // Get all the notificacaoEnviadaList where referenceId is greater than SMALLER_REFERENCE_ID
        defaultNotificacaoEnviadaShouldBeFound("referenceId.greaterThan=" + SMALLER_REFERENCE_ID);
    }


    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where created equals to DEFAULT_CREATED
        defaultNotificacaoEnviadaShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the notificacaoEnviadaList where created equals to UPDATED_CREATED
        defaultNotificacaoEnviadaShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where created not equals to DEFAULT_CREATED
        defaultNotificacaoEnviadaShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the notificacaoEnviadaList where created not equals to UPDATED_CREATED
        defaultNotificacaoEnviadaShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultNotificacaoEnviadaShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the notificacaoEnviadaList where created equals to UPDATED_CREATED
        defaultNotificacaoEnviadaShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where created is not null
        defaultNotificacaoEnviadaShouldBeFound("created.specified=true");

        // Get all the notificacaoEnviadaList where created is null
        defaultNotificacaoEnviadaShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where updated equals to DEFAULT_UPDATED
        defaultNotificacaoEnviadaShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the notificacaoEnviadaList where updated equals to UPDATED_UPDATED
        defaultNotificacaoEnviadaShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where updated not equals to DEFAULT_UPDATED
        defaultNotificacaoEnviadaShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the notificacaoEnviadaList where updated not equals to UPDATED_UPDATED
        defaultNotificacaoEnviadaShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultNotificacaoEnviadaShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the notificacaoEnviadaList where updated equals to UPDATED_UPDATED
        defaultNotificacaoEnviadaShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        // Get all the notificacaoEnviadaList where updated is not null
        defaultNotificacaoEnviadaShouldBeFound("updated.specified=true");

        // Get all the notificacaoEnviadaList where updated is null
        defaultNotificacaoEnviadaShouldNotBeFound("updated.specified=false");
    }

    @Test
    @Transactional
    public void getAllNotificacaoEnviadasByPlanoRecursoIsEqualToSomething() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);
        PlanoRecurso planoRecurso = PlanoRecursoResourceIT.createEntity(em);
        em.persist(planoRecurso);
        em.flush();
        notificacaoEnviada.setPlanoRecurso(planoRecurso);
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);
        Long planoRecursoId = planoRecurso.getId();

        // Get all the notificacaoEnviadaList where planoRecurso equals to planoRecursoId
        defaultNotificacaoEnviadaShouldBeFound("planoRecursoId.equals=" + planoRecursoId);

        // Get all the notificacaoEnviadaList where planoRecurso equals to planoRecursoId + 1
        defaultNotificacaoEnviadaShouldNotBeFound("planoRecursoId.equals=" + (planoRecursoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNotificacaoEnviadaShouldBeFound(String filter) throws Exception {
        restNotificacaoEnviadaMockMvc.perform(get("/api/notificacao-enviadas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notificacaoEnviada.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].destinatarios").value(hasItem(DEFAULT_DESTINATARIOS)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].assunto").value(hasItem(DEFAULT_ASSUNTO)))
            .andExpect(jsonPath("$.[*].enviado").value(hasItem(DEFAULT_ENVIADO)))
            .andExpect(jsonPath("$.[*].contador").value(hasItem(DEFAULT_CONTADOR)))
            .andExpect(jsonPath("$.[*].amazonMessageId").value(hasItem(DEFAULT_AMAZON_MESSAGE_ID)))
            .andExpect(jsonPath("$.[*].amazonDateLog").value(hasItem(DEFAULT_AMAZON_DATE_LOG.toString())))
            .andExpect(jsonPath("$.[*].priceInUsd").value(hasItem(DEFAULT_PRICE_IN_USD.doubleValue())))
            .andExpect(jsonPath("$.[*].amazonResposta").value(hasItem(DEFAULT_AMAZON_RESPOSTA)))
            .andExpect(jsonPath("$.[*].referenceId").value(hasItem(DEFAULT_REFERENCE_ID)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restNotificacaoEnviadaMockMvc.perform(get("/api/notificacao-enviadas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNotificacaoEnviadaShouldNotBeFound(String filter) throws Exception {
        restNotificacaoEnviadaMockMvc.perform(get("/api/notificacao-enviadas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNotificacaoEnviadaMockMvc.perform(get("/api/notificacao-enviadas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingNotificacaoEnviada() throws Exception {
        // Get the notificacaoEnviada
        restNotificacaoEnviadaMockMvc.perform(get("/api/notificacao-enviadas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNotificacaoEnviada() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        int databaseSizeBeforeUpdate = notificacaoEnviadaRepository.findAll().size();

        // Update the notificacaoEnviada
        NotificacaoEnviada updatedNotificacaoEnviada = notificacaoEnviadaRepository.findById(notificacaoEnviada.getId()).get();
        // Disconnect from session so that the updates on updatedNotificacaoEnviada are not directly saved in db
        em.detach(updatedNotificacaoEnviada);
        updatedNotificacaoEnviada
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .destinatarios(UPDATED_DESTINATARIOS)
            .tipo(UPDATED_TIPO)
            .status(UPDATED_STATUS)
            .assunto(UPDATED_ASSUNTO)
            .enviado(UPDATED_ENVIADO)
            .contador(UPDATED_CONTADOR)
            .amazonMessageId(UPDATED_AMAZON_MESSAGE_ID)
            .amazonDateLog(UPDATED_AMAZON_DATE_LOG)
            .priceInUsd(UPDATED_PRICE_IN_USD)
            .amazonResposta(UPDATED_AMAZON_RESPOSTA)
            .referenceId(UPDATED_REFERENCE_ID)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        NotificacaoEnviadaDTO notificacaoEnviadaDTO = notificacaoEnviadaMapper.toDto(updatedNotificacaoEnviada);

        restNotificacaoEnviadaMockMvc.perform(put("/api/notificacao-enviadas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificacaoEnviadaDTO)))
            .andExpect(status().isOk());

        // Validate the NotificacaoEnviada in the database
        List<NotificacaoEnviada> notificacaoEnviadaList = notificacaoEnviadaRepository.findAll();
        assertThat(notificacaoEnviadaList).hasSize(databaseSizeBeforeUpdate);
        NotificacaoEnviada testNotificacaoEnviada = notificacaoEnviadaList.get(notificacaoEnviadaList.size() - 1);
        assertThat(testNotificacaoEnviada.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNotificacaoEnviada.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testNotificacaoEnviada.getDestinatarios()).isEqualTo(UPDATED_DESTINATARIOS);
        assertThat(testNotificacaoEnviada.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testNotificacaoEnviada.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testNotificacaoEnviada.getAssunto()).isEqualTo(UPDATED_ASSUNTO);
        assertThat(testNotificacaoEnviada.getEnviado()).isEqualTo(UPDATED_ENVIADO);
        assertThat(testNotificacaoEnviada.getContador()).isEqualTo(UPDATED_CONTADOR);
        assertThat(testNotificacaoEnviada.getAmazonMessageId()).isEqualTo(UPDATED_AMAZON_MESSAGE_ID);
        assertThat(testNotificacaoEnviada.getAmazonDateLog()).isEqualTo(UPDATED_AMAZON_DATE_LOG);
        assertThat(testNotificacaoEnviada.getPriceInUsd()).isEqualTo(UPDATED_PRICE_IN_USD);
        assertThat(testNotificacaoEnviada.getAmazonResposta()).isEqualTo(UPDATED_AMAZON_RESPOSTA);
        assertThat(testNotificacaoEnviada.getReferenceId()).isEqualTo(UPDATED_REFERENCE_ID);
        assertThat(testNotificacaoEnviada.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testNotificacaoEnviada.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingNotificacaoEnviada() throws Exception {
        int databaseSizeBeforeUpdate = notificacaoEnviadaRepository.findAll().size();

        // Create the NotificacaoEnviada
        NotificacaoEnviadaDTO notificacaoEnviadaDTO = notificacaoEnviadaMapper.toDto(notificacaoEnviada);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotificacaoEnviadaMockMvc.perform(put("/api/notificacao-enviadas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificacaoEnviadaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NotificacaoEnviada in the database
        List<NotificacaoEnviada> notificacaoEnviadaList = notificacaoEnviadaRepository.findAll();
        assertThat(notificacaoEnviadaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNotificacaoEnviada() throws Exception {
        // Initialize the database
        notificacaoEnviadaRepository.saveAndFlush(notificacaoEnviada);

        int databaseSizeBeforeDelete = notificacaoEnviadaRepository.findAll().size();

        // Delete the notificacaoEnviada
        restNotificacaoEnviadaMockMvc.perform(delete("/api/notificacao-enviadas/{id}", notificacaoEnviada.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NotificacaoEnviada> notificacaoEnviadaList = notificacaoEnviadaRepository.findAll();
        assertThat(notificacaoEnviadaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
