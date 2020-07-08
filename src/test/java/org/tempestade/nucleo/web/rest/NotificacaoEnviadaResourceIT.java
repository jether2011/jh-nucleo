package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.NotificacaoEnviada;
import org.tempestade.nucleo.repository.NotificacaoEnviadaRepository;
import org.tempestade.nucleo.service.NotificacaoEnviadaService;
import org.tempestade.nucleo.service.dto.NotificacaoEnviadaDTO;
import org.tempestade.nucleo.service.mapper.NotificacaoEnviadaMapper;

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

    private static final Integer DEFAULT_CONTADOR = 1;
    private static final Integer UPDATED_CONTADOR = 2;

    private static final String DEFAULT_AMAZON_MESSAGE_ID = "AAAAAAAAAA";
    private static final String UPDATED_AMAZON_MESSAGE_ID = "BBBBBBBBBB";

    private static final Instant DEFAULT_AMAZON_DATE_LOG = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_AMAZON_DATE_LOG = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_PRICE_IN_USD = 1D;
    private static final Double UPDATED_PRICE_IN_USD = 2D;

    private static final String DEFAULT_AMAZON_RESPOSTA = "AAAAAAAAAA";
    private static final String UPDATED_AMAZON_RESPOSTA = "BBBBBBBBBB";

    private static final Integer DEFAULT_REFERENCE_ID = 1;
    private static final Integer UPDATED_REFERENCE_ID = 2;

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
