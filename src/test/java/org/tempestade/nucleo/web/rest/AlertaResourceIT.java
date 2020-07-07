package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.Alerta;
import org.tempestade.nucleo.domain.PlanoRecurso;
import org.tempestade.nucleo.domain.Alvo;
import org.tempestade.nucleo.domain.Usuario;
import org.tempestade.nucleo.domain.AlertaRisco;
import org.tempestade.nucleo.domain.TempestadeNivel;
import org.tempestade.nucleo.domain.AlertaTipo;
import org.tempestade.nucleo.repository.AlertaRepository;
import org.tempestade.nucleo.service.AlertaService;
import org.tempestade.nucleo.service.dto.AlertaDTO;
import org.tempestade.nucleo.service.mapper.AlertaMapper;
import org.tempestade.nucleo.service.dto.AlertaCriteria;
import org.tempestade.nucleo.service.AlertaQueryService;

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
 * Integration tests for the {@link AlertaResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AlertaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTATO = "AAAAAAAAAA";
    private static final String UPDATED_CONTATO = "BBBBBBBBBB";

    private static final String DEFAULT_DURACAO = "20:41:51";
    private static final String UPDATED_DURACAO = "03:33:19";

    private static final Boolean DEFAULT_AUTOMATICO = false;
    private static final Boolean UPDATED_AUTOMATICO = true;

    private static final Boolean DEFAULT_CRITICO = false;
    private static final Boolean UPDATED_CRITICO = true;

    private static final String DEFAULT_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO = "BBBBBBBBBB";

    private static final Integer DEFAULT_ALERTA_PAI_ID = 1;
    private static final Integer UPDATED_ALERTA_PAI_ID = 2;
    private static final Integer SMALLER_ALERTA_PAI_ID = 1 - 1;

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private AlertaRepository alertaRepository;

    @Autowired
    private AlertaMapper alertaMapper;

    @Autowired
    private AlertaService alertaService;

    @Autowired
    private AlertaQueryService alertaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAlertaMockMvc;

    private Alerta alerta;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Alerta createEntity(EntityManager em) {
        Alerta alerta = new Alerta()
            .nome(DEFAULT_NOME)
            .contato(DEFAULT_CONTATO)
            .duracao(DEFAULT_DURACAO)
            .automatico(DEFAULT_AUTOMATICO)
            .critico(DEFAULT_CRITICO)
            .observacao(DEFAULT_OBSERVACAO)
            .alertaPaiId(DEFAULT_ALERTA_PAI_ID)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return alerta;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Alerta createUpdatedEntity(EntityManager em) {
        Alerta alerta = new Alerta()
            .nome(UPDATED_NOME)
            .contato(UPDATED_CONTATO)
            .duracao(UPDATED_DURACAO)
            .automatico(UPDATED_AUTOMATICO)
            .critico(UPDATED_CRITICO)
            .observacao(UPDATED_OBSERVACAO)
            .alertaPaiId(UPDATED_ALERTA_PAI_ID)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return alerta;
    }

    @BeforeEach
    public void initTest() {
        alerta = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlerta() throws Exception {
        int databaseSizeBeforeCreate = alertaRepository.findAll().size();
        // Create the Alerta
        AlertaDTO alertaDTO = alertaMapper.toDto(alerta);
        restAlertaMockMvc.perform(post("/api/alertas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertaDTO)))
            .andExpect(status().isCreated());

        // Validate the Alerta in the database
        List<Alerta> alertaList = alertaRepository.findAll();
        assertThat(alertaList).hasSize(databaseSizeBeforeCreate + 1);
        Alerta testAlerta = alertaList.get(alertaList.size() - 1);
        assertThat(testAlerta.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testAlerta.getContato()).isEqualTo(DEFAULT_CONTATO);
        assertThat(testAlerta.getDuracao()).isEqualTo(DEFAULT_DURACAO);
        assertThat(testAlerta.isAutomatico()).isEqualTo(DEFAULT_AUTOMATICO);
        assertThat(testAlerta.isCritico()).isEqualTo(DEFAULT_CRITICO);
        assertThat(testAlerta.getObservacao()).isEqualTo(DEFAULT_OBSERVACAO);
        assertThat(testAlerta.getAlertaPaiId()).isEqualTo(DEFAULT_ALERTA_PAI_ID);
        assertThat(testAlerta.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testAlerta.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createAlertaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alertaRepository.findAll().size();

        // Create the Alerta with an existing ID
        alerta.setId(1L);
        AlertaDTO alertaDTO = alertaMapper.toDto(alerta);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlertaMockMvc.perform(post("/api/alertas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alerta in the database
        List<Alerta> alertaList = alertaRepository.findAll();
        assertThat(alertaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = alertaRepository.findAll().size();
        // set the field null
        alerta.setNome(null);

        // Create the Alerta, which fails.
        AlertaDTO alertaDTO = alertaMapper.toDto(alerta);


        restAlertaMockMvc.perform(post("/api/alertas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertaDTO)))
            .andExpect(status().isBadRequest());

        List<Alerta> alertaList = alertaRepository.findAll();
        assertThat(alertaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = alertaRepository.findAll().size();
        // set the field null
        alerta.setCreated(null);

        // Create the Alerta, which fails.
        AlertaDTO alertaDTO = alertaMapper.toDto(alerta);


        restAlertaMockMvc.perform(post("/api/alertas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertaDTO)))
            .andExpect(status().isBadRequest());

        List<Alerta> alertaList = alertaRepository.findAll();
        assertThat(alertaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAlertas() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList
        restAlertaMockMvc.perform(get("/api/alertas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alerta.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].contato").value(hasItem(DEFAULT_CONTATO)))
            .andExpect(jsonPath("$.[*].duracao").value(hasItem(DEFAULT_DURACAO)))
            .andExpect(jsonPath("$.[*].automatico").value(hasItem(DEFAULT_AUTOMATICO.booleanValue())))
            .andExpect(jsonPath("$.[*].critico").value(hasItem(DEFAULT_CRITICO.booleanValue())))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO)))
            .andExpect(jsonPath("$.[*].alertaPaiId").value(hasItem(DEFAULT_ALERTA_PAI_ID)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getAlerta() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get the alerta
        restAlertaMockMvc.perform(get("/api/alertas/{id}", alerta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(alerta.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.contato").value(DEFAULT_CONTATO))
            .andExpect(jsonPath("$.duracao").value(DEFAULT_DURACAO))
            .andExpect(jsonPath("$.automatico").value(DEFAULT_AUTOMATICO.booleanValue()))
            .andExpect(jsonPath("$.critico").value(DEFAULT_CRITICO.booleanValue()))
            .andExpect(jsonPath("$.observacao").value(DEFAULT_OBSERVACAO))
            .andExpect(jsonPath("$.alertaPaiId").value(DEFAULT_ALERTA_PAI_ID))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }


    @Test
    @Transactional
    public void getAlertasByIdFiltering() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        Long id = alerta.getId();

        defaultAlertaShouldBeFound("id.equals=" + id);
        defaultAlertaShouldNotBeFound("id.notEquals=" + id);

        defaultAlertaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAlertaShouldNotBeFound("id.greaterThan=" + id);

        defaultAlertaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAlertaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAlertasByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList where nome equals to DEFAULT_NOME
        defaultAlertaShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the alertaList where nome equals to UPDATED_NOME
        defaultAlertaShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAlertasByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList where nome not equals to DEFAULT_NOME
        defaultAlertaShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the alertaList where nome not equals to UPDATED_NOME
        defaultAlertaShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAlertasByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultAlertaShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the alertaList where nome equals to UPDATED_NOME
        defaultAlertaShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAlertasByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList where nome is not null
        defaultAlertaShouldBeFound("nome.specified=true");

        // Get all the alertaList where nome is null
        defaultAlertaShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlertasByNomeContainsSomething() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList where nome contains DEFAULT_NOME
        defaultAlertaShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the alertaList where nome contains UPDATED_NOME
        defaultAlertaShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAlertasByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList where nome does not contain DEFAULT_NOME
        defaultAlertaShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the alertaList where nome does not contain UPDATED_NOME
        defaultAlertaShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllAlertasByContatoIsEqualToSomething() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList where contato equals to DEFAULT_CONTATO
        defaultAlertaShouldBeFound("contato.equals=" + DEFAULT_CONTATO);

        // Get all the alertaList where contato equals to UPDATED_CONTATO
        defaultAlertaShouldNotBeFound("contato.equals=" + UPDATED_CONTATO);
    }

    @Test
    @Transactional
    public void getAllAlertasByContatoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList where contato not equals to DEFAULT_CONTATO
        defaultAlertaShouldNotBeFound("contato.notEquals=" + DEFAULT_CONTATO);

        // Get all the alertaList where contato not equals to UPDATED_CONTATO
        defaultAlertaShouldBeFound("contato.notEquals=" + UPDATED_CONTATO);
    }

    @Test
    @Transactional
    public void getAllAlertasByContatoIsInShouldWork() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList where contato in DEFAULT_CONTATO or UPDATED_CONTATO
        defaultAlertaShouldBeFound("contato.in=" + DEFAULT_CONTATO + "," + UPDATED_CONTATO);

        // Get all the alertaList where contato equals to UPDATED_CONTATO
        defaultAlertaShouldNotBeFound("contato.in=" + UPDATED_CONTATO);
    }

    @Test
    @Transactional
    public void getAllAlertasByContatoIsNullOrNotNull() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList where contato is not null
        defaultAlertaShouldBeFound("contato.specified=true");

        // Get all the alertaList where contato is null
        defaultAlertaShouldNotBeFound("contato.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlertasByContatoContainsSomething() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList where contato contains DEFAULT_CONTATO
        defaultAlertaShouldBeFound("contato.contains=" + DEFAULT_CONTATO);

        // Get all the alertaList where contato contains UPDATED_CONTATO
        defaultAlertaShouldNotBeFound("contato.contains=" + UPDATED_CONTATO);
    }

    @Test
    @Transactional
    public void getAllAlertasByContatoNotContainsSomething() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList where contato does not contain DEFAULT_CONTATO
        defaultAlertaShouldNotBeFound("contato.doesNotContain=" + DEFAULT_CONTATO);

        // Get all the alertaList where contato does not contain UPDATED_CONTATO
        defaultAlertaShouldBeFound("contato.doesNotContain=" + UPDATED_CONTATO);
    }


    @Test
    @Transactional
    public void getAllAlertasByDuracaoIsEqualToSomething() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList where duracao equals to DEFAULT_DURACAO
        defaultAlertaShouldBeFound("duracao.equals=" + DEFAULT_DURACAO);

        // Get all the alertaList where duracao equals to UPDATED_DURACAO
        defaultAlertaShouldNotBeFound("duracao.equals=" + UPDATED_DURACAO);
    }

    @Test
    @Transactional
    public void getAllAlertasByDuracaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList where duracao not equals to DEFAULT_DURACAO
        defaultAlertaShouldNotBeFound("duracao.notEquals=" + DEFAULT_DURACAO);

        // Get all the alertaList where duracao not equals to UPDATED_DURACAO
        defaultAlertaShouldBeFound("duracao.notEquals=" + UPDATED_DURACAO);
    }

    @Test
    @Transactional
    public void getAllAlertasByDuracaoIsInShouldWork() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList where duracao in DEFAULT_DURACAO or UPDATED_DURACAO
        defaultAlertaShouldBeFound("duracao.in=" + DEFAULT_DURACAO + "," + UPDATED_DURACAO);

        // Get all the alertaList where duracao equals to UPDATED_DURACAO
        defaultAlertaShouldNotBeFound("duracao.in=" + UPDATED_DURACAO);
    }

    @Test
    @Transactional
    public void getAllAlertasByDuracaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList where duracao is not null
        defaultAlertaShouldBeFound("duracao.specified=true");

        // Get all the alertaList where duracao is null
        defaultAlertaShouldNotBeFound("duracao.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlertasByDuracaoContainsSomething() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList where duracao contains DEFAULT_DURACAO
        defaultAlertaShouldBeFound("duracao.contains=" + DEFAULT_DURACAO);

        // Get all the alertaList where duracao contains UPDATED_DURACAO
        defaultAlertaShouldNotBeFound("duracao.contains=" + UPDATED_DURACAO);
    }

    @Test
    @Transactional
    public void getAllAlertasByDuracaoNotContainsSomething() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList where duracao does not contain DEFAULT_DURACAO
        defaultAlertaShouldNotBeFound("duracao.doesNotContain=" + DEFAULT_DURACAO);

        // Get all the alertaList where duracao does not contain UPDATED_DURACAO
        defaultAlertaShouldBeFound("duracao.doesNotContain=" + UPDATED_DURACAO);
    }


    @Test
    @Transactional
    public void getAllAlertasByAutomaticoIsEqualToSomething() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList where automatico equals to DEFAULT_AUTOMATICO
        defaultAlertaShouldBeFound("automatico.equals=" + DEFAULT_AUTOMATICO);

        // Get all the alertaList where automatico equals to UPDATED_AUTOMATICO
        defaultAlertaShouldNotBeFound("automatico.equals=" + UPDATED_AUTOMATICO);
    }

    @Test
    @Transactional
    public void getAllAlertasByAutomaticoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList where automatico not equals to DEFAULT_AUTOMATICO
        defaultAlertaShouldNotBeFound("automatico.notEquals=" + DEFAULT_AUTOMATICO);

        // Get all the alertaList where automatico not equals to UPDATED_AUTOMATICO
        defaultAlertaShouldBeFound("automatico.notEquals=" + UPDATED_AUTOMATICO);
    }

    @Test
    @Transactional
    public void getAllAlertasByAutomaticoIsInShouldWork() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList where automatico in DEFAULT_AUTOMATICO or UPDATED_AUTOMATICO
        defaultAlertaShouldBeFound("automatico.in=" + DEFAULT_AUTOMATICO + "," + UPDATED_AUTOMATICO);

        // Get all the alertaList where automatico equals to UPDATED_AUTOMATICO
        defaultAlertaShouldNotBeFound("automatico.in=" + UPDATED_AUTOMATICO);
    }

    @Test
    @Transactional
    public void getAllAlertasByAutomaticoIsNullOrNotNull() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList where automatico is not null
        defaultAlertaShouldBeFound("automatico.specified=true");

        // Get all the alertaList where automatico is null
        defaultAlertaShouldNotBeFound("automatico.specified=false");
    }

    @Test
    @Transactional
    public void getAllAlertasByCriticoIsEqualToSomething() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList where critico equals to DEFAULT_CRITICO
        defaultAlertaShouldBeFound("critico.equals=" + DEFAULT_CRITICO);

        // Get all the alertaList where critico equals to UPDATED_CRITICO
        defaultAlertaShouldNotBeFound("critico.equals=" + UPDATED_CRITICO);
    }

    @Test
    @Transactional
    public void getAllAlertasByCriticoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList where critico not equals to DEFAULT_CRITICO
        defaultAlertaShouldNotBeFound("critico.notEquals=" + DEFAULT_CRITICO);

        // Get all the alertaList where critico not equals to UPDATED_CRITICO
        defaultAlertaShouldBeFound("critico.notEquals=" + UPDATED_CRITICO);
    }

    @Test
    @Transactional
    public void getAllAlertasByCriticoIsInShouldWork() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList where critico in DEFAULT_CRITICO or UPDATED_CRITICO
        defaultAlertaShouldBeFound("critico.in=" + DEFAULT_CRITICO + "," + UPDATED_CRITICO);

        // Get all the alertaList where critico equals to UPDATED_CRITICO
        defaultAlertaShouldNotBeFound("critico.in=" + UPDATED_CRITICO);
    }

    @Test
    @Transactional
    public void getAllAlertasByCriticoIsNullOrNotNull() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList where critico is not null
        defaultAlertaShouldBeFound("critico.specified=true");

        // Get all the alertaList where critico is null
        defaultAlertaShouldNotBeFound("critico.specified=false");
    }

    @Test
    @Transactional
    public void getAllAlertasByObservacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList where observacao equals to DEFAULT_OBSERVACAO
        defaultAlertaShouldBeFound("observacao.equals=" + DEFAULT_OBSERVACAO);

        // Get all the alertaList where observacao equals to UPDATED_OBSERVACAO
        defaultAlertaShouldNotBeFound("observacao.equals=" + UPDATED_OBSERVACAO);
    }

    @Test
    @Transactional
    public void getAllAlertasByObservacaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList where observacao not equals to DEFAULT_OBSERVACAO
        defaultAlertaShouldNotBeFound("observacao.notEquals=" + DEFAULT_OBSERVACAO);

        // Get all the alertaList where observacao not equals to UPDATED_OBSERVACAO
        defaultAlertaShouldBeFound("observacao.notEquals=" + UPDATED_OBSERVACAO);
    }

    @Test
    @Transactional
    public void getAllAlertasByObservacaoIsInShouldWork() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList where observacao in DEFAULT_OBSERVACAO or UPDATED_OBSERVACAO
        defaultAlertaShouldBeFound("observacao.in=" + DEFAULT_OBSERVACAO + "," + UPDATED_OBSERVACAO);

        // Get all the alertaList where observacao equals to UPDATED_OBSERVACAO
        defaultAlertaShouldNotBeFound("observacao.in=" + UPDATED_OBSERVACAO);
    }

    @Test
    @Transactional
    public void getAllAlertasByObservacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList where observacao is not null
        defaultAlertaShouldBeFound("observacao.specified=true");

        // Get all the alertaList where observacao is null
        defaultAlertaShouldNotBeFound("observacao.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlertasByObservacaoContainsSomething() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList where observacao contains DEFAULT_OBSERVACAO
        defaultAlertaShouldBeFound("observacao.contains=" + DEFAULT_OBSERVACAO);

        // Get all the alertaList where observacao contains UPDATED_OBSERVACAO
        defaultAlertaShouldNotBeFound("observacao.contains=" + UPDATED_OBSERVACAO);
    }

    @Test
    @Transactional
    public void getAllAlertasByObservacaoNotContainsSomething() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList where observacao does not contain DEFAULT_OBSERVACAO
        defaultAlertaShouldNotBeFound("observacao.doesNotContain=" + DEFAULT_OBSERVACAO);

        // Get all the alertaList where observacao does not contain UPDATED_OBSERVACAO
        defaultAlertaShouldBeFound("observacao.doesNotContain=" + UPDATED_OBSERVACAO);
    }


    @Test
    @Transactional
    public void getAllAlertasByAlertaPaiIdIsEqualToSomething() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList where alertaPaiId equals to DEFAULT_ALERTA_PAI_ID
        defaultAlertaShouldBeFound("alertaPaiId.equals=" + DEFAULT_ALERTA_PAI_ID);

        // Get all the alertaList where alertaPaiId equals to UPDATED_ALERTA_PAI_ID
        defaultAlertaShouldNotBeFound("alertaPaiId.equals=" + UPDATED_ALERTA_PAI_ID);
    }

    @Test
    @Transactional
    public void getAllAlertasByAlertaPaiIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList where alertaPaiId not equals to DEFAULT_ALERTA_PAI_ID
        defaultAlertaShouldNotBeFound("alertaPaiId.notEquals=" + DEFAULT_ALERTA_PAI_ID);

        // Get all the alertaList where alertaPaiId not equals to UPDATED_ALERTA_PAI_ID
        defaultAlertaShouldBeFound("alertaPaiId.notEquals=" + UPDATED_ALERTA_PAI_ID);
    }

    @Test
    @Transactional
    public void getAllAlertasByAlertaPaiIdIsInShouldWork() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList where alertaPaiId in DEFAULT_ALERTA_PAI_ID or UPDATED_ALERTA_PAI_ID
        defaultAlertaShouldBeFound("alertaPaiId.in=" + DEFAULT_ALERTA_PAI_ID + "," + UPDATED_ALERTA_PAI_ID);

        // Get all the alertaList where alertaPaiId equals to UPDATED_ALERTA_PAI_ID
        defaultAlertaShouldNotBeFound("alertaPaiId.in=" + UPDATED_ALERTA_PAI_ID);
    }

    @Test
    @Transactional
    public void getAllAlertasByAlertaPaiIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList where alertaPaiId is not null
        defaultAlertaShouldBeFound("alertaPaiId.specified=true");

        // Get all the alertaList where alertaPaiId is null
        defaultAlertaShouldNotBeFound("alertaPaiId.specified=false");
    }

    @Test
    @Transactional
    public void getAllAlertasByAlertaPaiIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList where alertaPaiId is greater than or equal to DEFAULT_ALERTA_PAI_ID
        defaultAlertaShouldBeFound("alertaPaiId.greaterThanOrEqual=" + DEFAULT_ALERTA_PAI_ID);

        // Get all the alertaList where alertaPaiId is greater than or equal to UPDATED_ALERTA_PAI_ID
        defaultAlertaShouldNotBeFound("alertaPaiId.greaterThanOrEqual=" + UPDATED_ALERTA_PAI_ID);
    }

    @Test
    @Transactional
    public void getAllAlertasByAlertaPaiIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList where alertaPaiId is less than or equal to DEFAULT_ALERTA_PAI_ID
        defaultAlertaShouldBeFound("alertaPaiId.lessThanOrEqual=" + DEFAULT_ALERTA_PAI_ID);

        // Get all the alertaList where alertaPaiId is less than or equal to SMALLER_ALERTA_PAI_ID
        defaultAlertaShouldNotBeFound("alertaPaiId.lessThanOrEqual=" + SMALLER_ALERTA_PAI_ID);
    }

    @Test
    @Transactional
    public void getAllAlertasByAlertaPaiIdIsLessThanSomething() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList where alertaPaiId is less than DEFAULT_ALERTA_PAI_ID
        defaultAlertaShouldNotBeFound("alertaPaiId.lessThan=" + DEFAULT_ALERTA_PAI_ID);

        // Get all the alertaList where alertaPaiId is less than UPDATED_ALERTA_PAI_ID
        defaultAlertaShouldBeFound("alertaPaiId.lessThan=" + UPDATED_ALERTA_PAI_ID);
    }

    @Test
    @Transactional
    public void getAllAlertasByAlertaPaiIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList where alertaPaiId is greater than DEFAULT_ALERTA_PAI_ID
        defaultAlertaShouldNotBeFound("alertaPaiId.greaterThan=" + DEFAULT_ALERTA_PAI_ID);

        // Get all the alertaList where alertaPaiId is greater than SMALLER_ALERTA_PAI_ID
        defaultAlertaShouldBeFound("alertaPaiId.greaterThan=" + SMALLER_ALERTA_PAI_ID);
    }


    @Test
    @Transactional
    public void getAllAlertasByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList where created equals to DEFAULT_CREATED
        defaultAlertaShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the alertaList where created equals to UPDATED_CREATED
        defaultAlertaShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllAlertasByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList where created not equals to DEFAULT_CREATED
        defaultAlertaShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the alertaList where created not equals to UPDATED_CREATED
        defaultAlertaShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllAlertasByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultAlertaShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the alertaList where created equals to UPDATED_CREATED
        defaultAlertaShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllAlertasByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList where created is not null
        defaultAlertaShouldBeFound("created.specified=true");

        // Get all the alertaList where created is null
        defaultAlertaShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllAlertasByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList where updated equals to DEFAULT_UPDATED
        defaultAlertaShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the alertaList where updated equals to UPDATED_UPDATED
        defaultAlertaShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllAlertasByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList where updated not equals to DEFAULT_UPDATED
        defaultAlertaShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the alertaList where updated not equals to UPDATED_UPDATED
        defaultAlertaShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllAlertasByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultAlertaShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the alertaList where updated equals to UPDATED_UPDATED
        defaultAlertaShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllAlertasByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList where updated is not null
        defaultAlertaShouldBeFound("updated.specified=true");

        // Get all the alertaList where updated is null
        defaultAlertaShouldNotBeFound("updated.specified=false");
    }

    @Test
    @Transactional
    public void getAllAlertasByPlanoRecursoIsEqualToSomething() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);
        PlanoRecurso planoRecurso = PlanoRecursoResourceIT.createEntity(em);
        em.persist(planoRecurso);
        em.flush();
        alerta.setPlanoRecurso(planoRecurso);
        alertaRepository.saveAndFlush(alerta);
        Long planoRecursoId = planoRecurso.getId();

        // Get all the alertaList where planoRecurso equals to planoRecursoId
        defaultAlertaShouldBeFound("planoRecursoId.equals=" + planoRecursoId);

        // Get all the alertaList where planoRecurso equals to planoRecursoId + 1
        defaultAlertaShouldNotBeFound("planoRecursoId.equals=" + (planoRecursoId + 1));
    }


    @Test
    @Transactional
    public void getAllAlertasByAlvoIsEqualToSomething() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);
        Alvo alvo = AlvoResourceIT.createEntity(em);
        em.persist(alvo);
        em.flush();
        alerta.setAlvo(alvo);
        alertaRepository.saveAndFlush(alerta);
        Long alvoId = alvo.getId();

        // Get all the alertaList where alvo equals to alvoId
        defaultAlertaShouldBeFound("alvoId.equals=" + alvoId);

        // Get all the alertaList where alvo equals to alvoId + 1
        defaultAlertaShouldNotBeFound("alvoId.equals=" + (alvoId + 1));
    }


    @Test
    @Transactional
    public void getAllAlertasByUsuarioIsEqualToSomething() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);
        Usuario usuario = UsuarioResourceIT.createEntity(em);
        em.persist(usuario);
        em.flush();
        alerta.setUsuario(usuario);
        alertaRepository.saveAndFlush(alerta);
        Long usuarioId = usuario.getId();

        // Get all the alertaList where usuario equals to usuarioId
        defaultAlertaShouldBeFound("usuarioId.equals=" + usuarioId);

        // Get all the alertaList where usuario equals to usuarioId + 1
        defaultAlertaShouldNotBeFound("usuarioId.equals=" + (usuarioId + 1));
    }


    @Test
    @Transactional
    public void getAllAlertasByAlertaRiscoIsEqualToSomething() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);
        AlertaRisco alertaRisco = AlertaRiscoResourceIT.createEntity(em);
        em.persist(alertaRisco);
        em.flush();
        alerta.setAlertaRisco(alertaRisco);
        alertaRepository.saveAndFlush(alerta);
        Long alertaRiscoId = alertaRisco.getId();

        // Get all the alertaList where alertaRisco equals to alertaRiscoId
        defaultAlertaShouldBeFound("alertaRiscoId.equals=" + alertaRiscoId);

        // Get all the alertaList where alertaRisco equals to alertaRiscoId + 1
        defaultAlertaShouldNotBeFound("alertaRiscoId.equals=" + (alertaRiscoId + 1));
    }


    @Test
    @Transactional
    public void getAllAlertasByTempestadeNivelIsEqualToSomething() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);
        TempestadeNivel tempestadeNivel = TempestadeNivelResourceIT.createEntity(em);
        em.persist(tempestadeNivel);
        em.flush();
        alerta.setTempestadeNivel(tempestadeNivel);
        alertaRepository.saveAndFlush(alerta);
        Long tempestadeNivelId = tempestadeNivel.getId();

        // Get all the alertaList where tempestadeNivel equals to tempestadeNivelId
        defaultAlertaShouldBeFound("tempestadeNivelId.equals=" + tempestadeNivelId);

        // Get all the alertaList where tempestadeNivel equals to tempestadeNivelId + 1
        defaultAlertaShouldNotBeFound("tempestadeNivelId.equals=" + (tempestadeNivelId + 1));
    }


    @Test
    @Transactional
    public void getAllAlertasByAlertaTipoIsEqualToSomething() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);
        AlertaTipo alertaTipo = AlertaTipoResourceIT.createEntity(em);
        em.persist(alertaTipo);
        em.flush();
        alerta.setAlertaTipo(alertaTipo);
        alertaRepository.saveAndFlush(alerta);
        Long alertaTipoId = alertaTipo.getId();

        // Get all the alertaList where alertaTipo equals to alertaTipoId
        defaultAlertaShouldBeFound("alertaTipoId.equals=" + alertaTipoId);

        // Get all the alertaList where alertaTipo equals to alertaTipoId + 1
        defaultAlertaShouldNotBeFound("alertaTipoId.equals=" + (alertaTipoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAlertaShouldBeFound(String filter) throws Exception {
        restAlertaMockMvc.perform(get("/api/alertas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alerta.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].contato").value(hasItem(DEFAULT_CONTATO)))
            .andExpect(jsonPath("$.[*].duracao").value(hasItem(DEFAULT_DURACAO)))
            .andExpect(jsonPath("$.[*].automatico").value(hasItem(DEFAULT_AUTOMATICO.booleanValue())))
            .andExpect(jsonPath("$.[*].critico").value(hasItem(DEFAULT_CRITICO.booleanValue())))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO)))
            .andExpect(jsonPath("$.[*].alertaPaiId").value(hasItem(DEFAULT_ALERTA_PAI_ID)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restAlertaMockMvc.perform(get("/api/alertas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAlertaShouldNotBeFound(String filter) throws Exception {
        restAlertaMockMvc.perform(get("/api/alertas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAlertaMockMvc.perform(get("/api/alertas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingAlerta() throws Exception {
        // Get the alerta
        restAlertaMockMvc.perform(get("/api/alertas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlerta() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        int databaseSizeBeforeUpdate = alertaRepository.findAll().size();

        // Update the alerta
        Alerta updatedAlerta = alertaRepository.findById(alerta.getId()).get();
        // Disconnect from session so that the updates on updatedAlerta are not directly saved in db
        em.detach(updatedAlerta);
        updatedAlerta
            .nome(UPDATED_NOME)
            .contato(UPDATED_CONTATO)
            .duracao(UPDATED_DURACAO)
            .automatico(UPDATED_AUTOMATICO)
            .critico(UPDATED_CRITICO)
            .observacao(UPDATED_OBSERVACAO)
            .alertaPaiId(UPDATED_ALERTA_PAI_ID)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        AlertaDTO alertaDTO = alertaMapper.toDto(updatedAlerta);

        restAlertaMockMvc.perform(put("/api/alertas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertaDTO)))
            .andExpect(status().isOk());

        // Validate the Alerta in the database
        List<Alerta> alertaList = alertaRepository.findAll();
        assertThat(alertaList).hasSize(databaseSizeBeforeUpdate);
        Alerta testAlerta = alertaList.get(alertaList.size() - 1);
        assertThat(testAlerta.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testAlerta.getContato()).isEqualTo(UPDATED_CONTATO);
        assertThat(testAlerta.getDuracao()).isEqualTo(UPDATED_DURACAO);
        assertThat(testAlerta.isAutomatico()).isEqualTo(UPDATED_AUTOMATICO);
        assertThat(testAlerta.isCritico()).isEqualTo(UPDATED_CRITICO);
        assertThat(testAlerta.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
        assertThat(testAlerta.getAlertaPaiId()).isEqualTo(UPDATED_ALERTA_PAI_ID);
        assertThat(testAlerta.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testAlerta.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingAlerta() throws Exception {
        int databaseSizeBeforeUpdate = alertaRepository.findAll().size();

        // Create the Alerta
        AlertaDTO alertaDTO = alertaMapper.toDto(alerta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlertaMockMvc.perform(put("/api/alertas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alerta in the database
        List<Alerta> alertaList = alertaRepository.findAll();
        assertThat(alertaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAlerta() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        int databaseSizeBeforeDelete = alertaRepository.findAll().size();

        // Delete the alerta
        restAlertaMockMvc.perform(delete("/api/alertas/{id}", alerta.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Alerta> alertaList = alertaRepository.findAll();
        assertThat(alertaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
