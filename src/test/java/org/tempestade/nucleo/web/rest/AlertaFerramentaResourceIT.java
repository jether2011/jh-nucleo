package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.AlertaFerramenta;
import org.tempestade.nucleo.domain.Alerta;
import org.tempestade.nucleo.domain.Ferramenta;
import org.tempestade.nucleo.repository.AlertaFerramentaRepository;
import org.tempestade.nucleo.service.AlertaFerramentaService;
import org.tempestade.nucleo.service.dto.AlertaFerramentaDTO;
import org.tempestade.nucleo.service.mapper.AlertaFerramentaMapper;
import org.tempestade.nucleo.service.dto.AlertaFerramentaCriteria;
import org.tempestade.nucleo.service.AlertaFerramentaQueryService;

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
 * Integration tests for the {@link AlertaFerramentaResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AlertaFerramentaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private AlertaFerramentaRepository alertaFerramentaRepository;

    @Autowired
    private AlertaFerramentaMapper alertaFerramentaMapper;

    @Autowired
    private AlertaFerramentaService alertaFerramentaService;

    @Autowired
    private AlertaFerramentaQueryService alertaFerramentaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAlertaFerramentaMockMvc;

    private AlertaFerramenta alertaFerramenta;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlertaFerramenta createEntity(EntityManager em) {
        AlertaFerramenta alertaFerramenta = new AlertaFerramenta()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        // Add required entity
        Alerta alerta;
        if (TestUtil.findAll(em, Alerta.class).isEmpty()) {
            alerta = AlertaResourceIT.createEntity(em);
            em.persist(alerta);
            em.flush();
        } else {
            alerta = TestUtil.findAll(em, Alerta.class).get(0);
        }
        alertaFerramenta.setAlerta(alerta);
        // Add required entity
        Ferramenta ferramenta;
        if (TestUtil.findAll(em, Ferramenta.class).isEmpty()) {
            ferramenta = FerramentaResourceIT.createEntity(em);
            em.persist(ferramenta);
            em.flush();
        } else {
            ferramenta = TestUtil.findAll(em, Ferramenta.class).get(0);
        }
        alertaFerramenta.setFerramenta(ferramenta);
        return alertaFerramenta;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlertaFerramenta createUpdatedEntity(EntityManager em) {
        AlertaFerramenta alertaFerramenta = new AlertaFerramenta()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        // Add required entity
        Alerta alerta;
        if (TestUtil.findAll(em, Alerta.class).isEmpty()) {
            alerta = AlertaResourceIT.createUpdatedEntity(em);
            em.persist(alerta);
            em.flush();
        } else {
            alerta = TestUtil.findAll(em, Alerta.class).get(0);
        }
        alertaFerramenta.setAlerta(alerta);
        // Add required entity
        Ferramenta ferramenta;
        if (TestUtil.findAll(em, Ferramenta.class).isEmpty()) {
            ferramenta = FerramentaResourceIT.createUpdatedEntity(em);
            em.persist(ferramenta);
            em.flush();
        } else {
            ferramenta = TestUtil.findAll(em, Ferramenta.class).get(0);
        }
        alertaFerramenta.setFerramenta(ferramenta);
        return alertaFerramenta;
    }

    @BeforeEach
    public void initTest() {
        alertaFerramenta = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlertaFerramenta() throws Exception {
        int databaseSizeBeforeCreate = alertaFerramentaRepository.findAll().size();
        // Create the AlertaFerramenta
        AlertaFerramentaDTO alertaFerramentaDTO = alertaFerramentaMapper.toDto(alertaFerramenta);
        restAlertaFerramentaMockMvc.perform(post("/api/alerta-ferramentas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertaFerramentaDTO)))
            .andExpect(status().isCreated());

        // Validate the AlertaFerramenta in the database
        List<AlertaFerramenta> alertaFerramentaList = alertaFerramentaRepository.findAll();
        assertThat(alertaFerramentaList).hasSize(databaseSizeBeforeCreate + 1);
        AlertaFerramenta testAlertaFerramenta = alertaFerramentaList.get(alertaFerramentaList.size() - 1);
        assertThat(testAlertaFerramenta.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testAlertaFerramenta.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testAlertaFerramenta.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testAlertaFerramenta.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createAlertaFerramentaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alertaFerramentaRepository.findAll().size();

        // Create the AlertaFerramenta with an existing ID
        alertaFerramenta.setId(1L);
        AlertaFerramentaDTO alertaFerramentaDTO = alertaFerramentaMapper.toDto(alertaFerramenta);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlertaFerramentaMockMvc.perform(post("/api/alerta-ferramentas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertaFerramentaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AlertaFerramenta in the database
        List<AlertaFerramenta> alertaFerramentaList = alertaFerramentaRepository.findAll();
        assertThat(alertaFerramentaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = alertaFerramentaRepository.findAll().size();
        // set the field null
        alertaFerramenta.setNome(null);

        // Create the AlertaFerramenta, which fails.
        AlertaFerramentaDTO alertaFerramentaDTO = alertaFerramentaMapper.toDto(alertaFerramenta);


        restAlertaFerramentaMockMvc.perform(post("/api/alerta-ferramentas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertaFerramentaDTO)))
            .andExpect(status().isBadRequest());

        List<AlertaFerramenta> alertaFerramentaList = alertaFerramentaRepository.findAll();
        assertThat(alertaFerramentaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = alertaFerramentaRepository.findAll().size();
        // set the field null
        alertaFerramenta.setCreated(null);

        // Create the AlertaFerramenta, which fails.
        AlertaFerramentaDTO alertaFerramentaDTO = alertaFerramentaMapper.toDto(alertaFerramenta);


        restAlertaFerramentaMockMvc.perform(post("/api/alerta-ferramentas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertaFerramentaDTO)))
            .andExpect(status().isBadRequest());

        List<AlertaFerramenta> alertaFerramentaList = alertaFerramentaRepository.findAll();
        assertThat(alertaFerramentaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAlertaFerramentas() throws Exception {
        // Initialize the database
        alertaFerramentaRepository.saveAndFlush(alertaFerramenta);

        // Get all the alertaFerramentaList
        restAlertaFerramentaMockMvc.perform(get("/api/alerta-ferramentas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alertaFerramenta.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getAlertaFerramenta() throws Exception {
        // Initialize the database
        alertaFerramentaRepository.saveAndFlush(alertaFerramenta);

        // Get the alertaFerramenta
        restAlertaFerramentaMockMvc.perform(get("/api/alerta-ferramentas/{id}", alertaFerramenta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(alertaFerramenta.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }


    @Test
    @Transactional
    public void getAlertaFerramentasByIdFiltering() throws Exception {
        // Initialize the database
        alertaFerramentaRepository.saveAndFlush(alertaFerramenta);

        Long id = alertaFerramenta.getId();

        defaultAlertaFerramentaShouldBeFound("id.equals=" + id);
        defaultAlertaFerramentaShouldNotBeFound("id.notEquals=" + id);

        defaultAlertaFerramentaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAlertaFerramentaShouldNotBeFound("id.greaterThan=" + id);

        defaultAlertaFerramentaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAlertaFerramentaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAlertaFerramentasByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        alertaFerramentaRepository.saveAndFlush(alertaFerramenta);

        // Get all the alertaFerramentaList where nome equals to DEFAULT_NOME
        defaultAlertaFerramentaShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the alertaFerramentaList where nome equals to UPDATED_NOME
        defaultAlertaFerramentaShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAlertaFerramentasByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alertaFerramentaRepository.saveAndFlush(alertaFerramenta);

        // Get all the alertaFerramentaList where nome not equals to DEFAULT_NOME
        defaultAlertaFerramentaShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the alertaFerramentaList where nome not equals to UPDATED_NOME
        defaultAlertaFerramentaShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAlertaFerramentasByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        alertaFerramentaRepository.saveAndFlush(alertaFerramenta);

        // Get all the alertaFerramentaList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultAlertaFerramentaShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the alertaFerramentaList where nome equals to UPDATED_NOME
        defaultAlertaFerramentaShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAlertaFerramentasByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        alertaFerramentaRepository.saveAndFlush(alertaFerramenta);

        // Get all the alertaFerramentaList where nome is not null
        defaultAlertaFerramentaShouldBeFound("nome.specified=true");

        // Get all the alertaFerramentaList where nome is null
        defaultAlertaFerramentaShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlertaFerramentasByNomeContainsSomething() throws Exception {
        // Initialize the database
        alertaFerramentaRepository.saveAndFlush(alertaFerramenta);

        // Get all the alertaFerramentaList where nome contains DEFAULT_NOME
        defaultAlertaFerramentaShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the alertaFerramentaList where nome contains UPDATED_NOME
        defaultAlertaFerramentaShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAlertaFerramentasByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        alertaFerramentaRepository.saveAndFlush(alertaFerramenta);

        // Get all the alertaFerramentaList where nome does not contain DEFAULT_NOME
        defaultAlertaFerramentaShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the alertaFerramentaList where nome does not contain UPDATED_NOME
        defaultAlertaFerramentaShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllAlertaFerramentasByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        alertaFerramentaRepository.saveAndFlush(alertaFerramenta);

        // Get all the alertaFerramentaList where descricao equals to DEFAULT_DESCRICAO
        defaultAlertaFerramentaShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the alertaFerramentaList where descricao equals to UPDATED_DESCRICAO
        defaultAlertaFerramentaShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllAlertaFerramentasByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alertaFerramentaRepository.saveAndFlush(alertaFerramenta);

        // Get all the alertaFerramentaList where descricao not equals to DEFAULT_DESCRICAO
        defaultAlertaFerramentaShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the alertaFerramentaList where descricao not equals to UPDATED_DESCRICAO
        defaultAlertaFerramentaShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllAlertaFerramentasByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        alertaFerramentaRepository.saveAndFlush(alertaFerramenta);

        // Get all the alertaFerramentaList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultAlertaFerramentaShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the alertaFerramentaList where descricao equals to UPDATED_DESCRICAO
        defaultAlertaFerramentaShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllAlertaFerramentasByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        alertaFerramentaRepository.saveAndFlush(alertaFerramenta);

        // Get all the alertaFerramentaList where descricao is not null
        defaultAlertaFerramentaShouldBeFound("descricao.specified=true");

        // Get all the alertaFerramentaList where descricao is null
        defaultAlertaFerramentaShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlertaFerramentasByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        alertaFerramentaRepository.saveAndFlush(alertaFerramenta);

        // Get all the alertaFerramentaList where descricao contains DEFAULT_DESCRICAO
        defaultAlertaFerramentaShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the alertaFerramentaList where descricao contains UPDATED_DESCRICAO
        defaultAlertaFerramentaShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllAlertaFerramentasByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        alertaFerramentaRepository.saveAndFlush(alertaFerramenta);

        // Get all the alertaFerramentaList where descricao does not contain DEFAULT_DESCRICAO
        defaultAlertaFerramentaShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the alertaFerramentaList where descricao does not contain UPDATED_DESCRICAO
        defaultAlertaFerramentaShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllAlertaFerramentasByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        alertaFerramentaRepository.saveAndFlush(alertaFerramenta);

        // Get all the alertaFerramentaList where created equals to DEFAULT_CREATED
        defaultAlertaFerramentaShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the alertaFerramentaList where created equals to UPDATED_CREATED
        defaultAlertaFerramentaShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllAlertaFerramentasByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alertaFerramentaRepository.saveAndFlush(alertaFerramenta);

        // Get all the alertaFerramentaList where created not equals to DEFAULT_CREATED
        defaultAlertaFerramentaShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the alertaFerramentaList where created not equals to UPDATED_CREATED
        defaultAlertaFerramentaShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllAlertaFerramentasByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        alertaFerramentaRepository.saveAndFlush(alertaFerramenta);

        // Get all the alertaFerramentaList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultAlertaFerramentaShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the alertaFerramentaList where created equals to UPDATED_CREATED
        defaultAlertaFerramentaShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllAlertaFerramentasByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        alertaFerramentaRepository.saveAndFlush(alertaFerramenta);

        // Get all the alertaFerramentaList where created is not null
        defaultAlertaFerramentaShouldBeFound("created.specified=true");

        // Get all the alertaFerramentaList where created is null
        defaultAlertaFerramentaShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllAlertaFerramentasByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        alertaFerramentaRepository.saveAndFlush(alertaFerramenta);

        // Get all the alertaFerramentaList where updated equals to DEFAULT_UPDATED
        defaultAlertaFerramentaShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the alertaFerramentaList where updated equals to UPDATED_UPDATED
        defaultAlertaFerramentaShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllAlertaFerramentasByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alertaFerramentaRepository.saveAndFlush(alertaFerramenta);

        // Get all the alertaFerramentaList where updated not equals to DEFAULT_UPDATED
        defaultAlertaFerramentaShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the alertaFerramentaList where updated not equals to UPDATED_UPDATED
        defaultAlertaFerramentaShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllAlertaFerramentasByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        alertaFerramentaRepository.saveAndFlush(alertaFerramenta);

        // Get all the alertaFerramentaList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultAlertaFerramentaShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the alertaFerramentaList where updated equals to UPDATED_UPDATED
        defaultAlertaFerramentaShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllAlertaFerramentasByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        alertaFerramentaRepository.saveAndFlush(alertaFerramenta);

        // Get all the alertaFerramentaList where updated is not null
        defaultAlertaFerramentaShouldBeFound("updated.specified=true");

        // Get all the alertaFerramentaList where updated is null
        defaultAlertaFerramentaShouldNotBeFound("updated.specified=false");
    }

    @Test
    @Transactional
    public void getAllAlertaFerramentasByAlertaIsEqualToSomething() throws Exception {
        // Get already existing entity
        Alerta alerta = alertaFerramenta.getAlerta();
        alertaFerramentaRepository.saveAndFlush(alertaFerramenta);
        Long alertaId = alerta.getId();

        // Get all the alertaFerramentaList where alerta equals to alertaId
        defaultAlertaFerramentaShouldBeFound("alertaId.equals=" + alertaId);

        // Get all the alertaFerramentaList where alerta equals to alertaId + 1
        defaultAlertaFerramentaShouldNotBeFound("alertaId.equals=" + (alertaId + 1));
    }


    @Test
    @Transactional
    public void getAllAlertaFerramentasByFerramentaIsEqualToSomething() throws Exception {
        // Get already existing entity
        Ferramenta ferramenta = alertaFerramenta.getFerramenta();
        alertaFerramentaRepository.saveAndFlush(alertaFerramenta);
        Long ferramentaId = ferramenta.getId();

        // Get all the alertaFerramentaList where ferramenta equals to ferramentaId
        defaultAlertaFerramentaShouldBeFound("ferramentaId.equals=" + ferramentaId);

        // Get all the alertaFerramentaList where ferramenta equals to ferramentaId + 1
        defaultAlertaFerramentaShouldNotBeFound("ferramentaId.equals=" + (ferramentaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAlertaFerramentaShouldBeFound(String filter) throws Exception {
        restAlertaFerramentaMockMvc.perform(get("/api/alerta-ferramentas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alertaFerramenta.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restAlertaFerramentaMockMvc.perform(get("/api/alerta-ferramentas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAlertaFerramentaShouldNotBeFound(String filter) throws Exception {
        restAlertaFerramentaMockMvc.perform(get("/api/alerta-ferramentas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAlertaFerramentaMockMvc.perform(get("/api/alerta-ferramentas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingAlertaFerramenta() throws Exception {
        // Get the alertaFerramenta
        restAlertaFerramentaMockMvc.perform(get("/api/alerta-ferramentas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlertaFerramenta() throws Exception {
        // Initialize the database
        alertaFerramentaRepository.saveAndFlush(alertaFerramenta);

        int databaseSizeBeforeUpdate = alertaFerramentaRepository.findAll().size();

        // Update the alertaFerramenta
        AlertaFerramenta updatedAlertaFerramenta = alertaFerramentaRepository.findById(alertaFerramenta.getId()).get();
        // Disconnect from session so that the updates on updatedAlertaFerramenta are not directly saved in db
        em.detach(updatedAlertaFerramenta);
        updatedAlertaFerramenta
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        AlertaFerramentaDTO alertaFerramentaDTO = alertaFerramentaMapper.toDto(updatedAlertaFerramenta);

        restAlertaFerramentaMockMvc.perform(put("/api/alerta-ferramentas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertaFerramentaDTO)))
            .andExpect(status().isOk());

        // Validate the AlertaFerramenta in the database
        List<AlertaFerramenta> alertaFerramentaList = alertaFerramentaRepository.findAll();
        assertThat(alertaFerramentaList).hasSize(databaseSizeBeforeUpdate);
        AlertaFerramenta testAlertaFerramenta = alertaFerramentaList.get(alertaFerramentaList.size() - 1);
        assertThat(testAlertaFerramenta.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testAlertaFerramenta.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testAlertaFerramenta.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testAlertaFerramenta.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingAlertaFerramenta() throws Exception {
        int databaseSizeBeforeUpdate = alertaFerramentaRepository.findAll().size();

        // Create the AlertaFerramenta
        AlertaFerramentaDTO alertaFerramentaDTO = alertaFerramentaMapper.toDto(alertaFerramenta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlertaFerramentaMockMvc.perform(put("/api/alerta-ferramentas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertaFerramentaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AlertaFerramenta in the database
        List<AlertaFerramenta> alertaFerramentaList = alertaFerramentaRepository.findAll();
        assertThat(alertaFerramentaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAlertaFerramenta() throws Exception {
        // Initialize the database
        alertaFerramentaRepository.saveAndFlush(alertaFerramenta);

        int databaseSizeBeforeDelete = alertaFerramentaRepository.findAll().size();

        // Delete the alertaFerramenta
        restAlertaFerramentaMockMvc.perform(delete("/api/alerta-ferramentas/{id}", alertaFerramenta.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AlertaFerramenta> alertaFerramentaList = alertaFerramentaRepository.findAll();
        assertThat(alertaFerramentaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
