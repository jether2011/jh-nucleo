package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.PlanoRecursoTipoEnvio;
import org.tempestade.nucleo.domain.PlanoRecurso;
import org.tempestade.nucleo.domain.TipoEnvio;
import org.tempestade.nucleo.repository.PlanoRecursoTipoEnvioRepository;
import org.tempestade.nucleo.service.PlanoRecursoTipoEnvioService;
import org.tempestade.nucleo.service.dto.PlanoRecursoTipoEnvioDTO;
import org.tempestade.nucleo.service.mapper.PlanoRecursoTipoEnvioMapper;
import org.tempestade.nucleo.service.dto.PlanoRecursoTipoEnvioCriteria;
import org.tempestade.nucleo.service.PlanoRecursoTipoEnvioQueryService;

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
 * Integration tests for the {@link PlanoRecursoTipoEnvioResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PlanoRecursoTipoEnvioResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Integer DEFAULT_QTD = 1;
    private static final Integer UPDATED_QTD = 2;
    private static final Integer SMALLER_QTD = 1 - 1;

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PlanoRecursoTipoEnvioRepository planoRecursoTipoEnvioRepository;

    @Autowired
    private PlanoRecursoTipoEnvioMapper planoRecursoTipoEnvioMapper;

    @Autowired
    private PlanoRecursoTipoEnvioService planoRecursoTipoEnvioService;

    @Autowired
    private PlanoRecursoTipoEnvioQueryService planoRecursoTipoEnvioQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlanoRecursoTipoEnvioMockMvc;

    private PlanoRecursoTipoEnvio planoRecursoTipoEnvio;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanoRecursoTipoEnvio createEntity(EntityManager em) {
        PlanoRecursoTipoEnvio planoRecursoTipoEnvio = new PlanoRecursoTipoEnvio()
            .name(DEFAULT_NAME)
            .descricao(DEFAULT_DESCRICAO)
            .qtd(DEFAULT_QTD)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return planoRecursoTipoEnvio;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanoRecursoTipoEnvio createUpdatedEntity(EntityManager em) {
        PlanoRecursoTipoEnvio planoRecursoTipoEnvio = new PlanoRecursoTipoEnvio()
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .qtd(UPDATED_QTD)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return planoRecursoTipoEnvio;
    }

    @BeforeEach
    public void initTest() {
        planoRecursoTipoEnvio = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlanoRecursoTipoEnvio() throws Exception {
        int databaseSizeBeforeCreate = planoRecursoTipoEnvioRepository.findAll().size();
        // Create the PlanoRecursoTipoEnvio
        PlanoRecursoTipoEnvioDTO planoRecursoTipoEnvioDTO = planoRecursoTipoEnvioMapper.toDto(planoRecursoTipoEnvio);
        restPlanoRecursoTipoEnvioMockMvc.perform(post("/api/plano-recurso-tipo-envios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoRecursoTipoEnvioDTO)))
            .andExpect(status().isCreated());

        // Validate the PlanoRecursoTipoEnvio in the database
        List<PlanoRecursoTipoEnvio> planoRecursoTipoEnvioList = planoRecursoTipoEnvioRepository.findAll();
        assertThat(planoRecursoTipoEnvioList).hasSize(databaseSizeBeforeCreate + 1);
        PlanoRecursoTipoEnvio testPlanoRecursoTipoEnvio = planoRecursoTipoEnvioList.get(planoRecursoTipoEnvioList.size() - 1);
        assertThat(testPlanoRecursoTipoEnvio.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPlanoRecursoTipoEnvio.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testPlanoRecursoTipoEnvio.getQtd()).isEqualTo(DEFAULT_QTD);
        assertThat(testPlanoRecursoTipoEnvio.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testPlanoRecursoTipoEnvio.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createPlanoRecursoTipoEnvioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = planoRecursoTipoEnvioRepository.findAll().size();

        // Create the PlanoRecursoTipoEnvio with an existing ID
        planoRecursoTipoEnvio.setId(1L);
        PlanoRecursoTipoEnvioDTO planoRecursoTipoEnvioDTO = planoRecursoTipoEnvioMapper.toDto(planoRecursoTipoEnvio);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanoRecursoTipoEnvioMockMvc.perform(post("/api/plano-recurso-tipo-envios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoRecursoTipoEnvioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PlanoRecursoTipoEnvio in the database
        List<PlanoRecursoTipoEnvio> planoRecursoTipoEnvioList = planoRecursoTipoEnvioRepository.findAll();
        assertThat(planoRecursoTipoEnvioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoRecursoTipoEnvioRepository.findAll().size();
        // set the field null
        planoRecursoTipoEnvio.setName(null);

        // Create the PlanoRecursoTipoEnvio, which fails.
        PlanoRecursoTipoEnvioDTO planoRecursoTipoEnvioDTO = planoRecursoTipoEnvioMapper.toDto(planoRecursoTipoEnvio);


        restPlanoRecursoTipoEnvioMockMvc.perform(post("/api/plano-recurso-tipo-envios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoRecursoTipoEnvioDTO)))
            .andExpect(status().isBadRequest());

        List<PlanoRecursoTipoEnvio> planoRecursoTipoEnvioList = planoRecursoTipoEnvioRepository.findAll();
        assertThat(planoRecursoTipoEnvioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoRecursoTipoEnvioRepository.findAll().size();
        // set the field null
        planoRecursoTipoEnvio.setDescricao(null);

        // Create the PlanoRecursoTipoEnvio, which fails.
        PlanoRecursoTipoEnvioDTO planoRecursoTipoEnvioDTO = planoRecursoTipoEnvioMapper.toDto(planoRecursoTipoEnvio);


        restPlanoRecursoTipoEnvioMockMvc.perform(post("/api/plano-recurso-tipo-envios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoRecursoTipoEnvioDTO)))
            .andExpect(status().isBadRequest());

        List<PlanoRecursoTipoEnvio> planoRecursoTipoEnvioList = planoRecursoTipoEnvioRepository.findAll();
        assertThat(planoRecursoTipoEnvioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoRecursoTipoEnvioRepository.findAll().size();
        // set the field null
        planoRecursoTipoEnvio.setCreated(null);

        // Create the PlanoRecursoTipoEnvio, which fails.
        PlanoRecursoTipoEnvioDTO planoRecursoTipoEnvioDTO = planoRecursoTipoEnvioMapper.toDto(planoRecursoTipoEnvio);


        restPlanoRecursoTipoEnvioMockMvc.perform(post("/api/plano-recurso-tipo-envios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoRecursoTipoEnvioDTO)))
            .andExpect(status().isBadRequest());

        List<PlanoRecursoTipoEnvio> planoRecursoTipoEnvioList = planoRecursoTipoEnvioRepository.findAll();
        assertThat(planoRecursoTipoEnvioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPlanoRecursoTipoEnvios() throws Exception {
        // Initialize the database
        planoRecursoTipoEnvioRepository.saveAndFlush(planoRecursoTipoEnvio);

        // Get all the planoRecursoTipoEnvioList
        restPlanoRecursoTipoEnvioMockMvc.perform(get("/api/plano-recurso-tipo-envios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planoRecursoTipoEnvio.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].qtd").value(hasItem(DEFAULT_QTD)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getPlanoRecursoTipoEnvio() throws Exception {
        // Initialize the database
        planoRecursoTipoEnvioRepository.saveAndFlush(planoRecursoTipoEnvio);

        // Get the planoRecursoTipoEnvio
        restPlanoRecursoTipoEnvioMockMvc.perform(get("/api/plano-recurso-tipo-envios/{id}", planoRecursoTipoEnvio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(planoRecursoTipoEnvio.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.qtd").value(DEFAULT_QTD))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }


    @Test
    @Transactional
    public void getPlanoRecursoTipoEnviosByIdFiltering() throws Exception {
        // Initialize the database
        planoRecursoTipoEnvioRepository.saveAndFlush(planoRecursoTipoEnvio);

        Long id = planoRecursoTipoEnvio.getId();

        defaultPlanoRecursoTipoEnvioShouldBeFound("id.equals=" + id);
        defaultPlanoRecursoTipoEnvioShouldNotBeFound("id.notEquals=" + id);

        defaultPlanoRecursoTipoEnvioShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPlanoRecursoTipoEnvioShouldNotBeFound("id.greaterThan=" + id);

        defaultPlanoRecursoTipoEnvioShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPlanoRecursoTipoEnvioShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPlanoRecursoTipoEnviosByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        planoRecursoTipoEnvioRepository.saveAndFlush(planoRecursoTipoEnvio);

        // Get all the planoRecursoTipoEnvioList where name equals to DEFAULT_NAME
        defaultPlanoRecursoTipoEnvioShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the planoRecursoTipoEnvioList where name equals to UPDATED_NAME
        defaultPlanoRecursoTipoEnvioShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPlanoRecursoTipoEnviosByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        planoRecursoTipoEnvioRepository.saveAndFlush(planoRecursoTipoEnvio);

        // Get all the planoRecursoTipoEnvioList where name not equals to DEFAULT_NAME
        defaultPlanoRecursoTipoEnvioShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the planoRecursoTipoEnvioList where name not equals to UPDATED_NAME
        defaultPlanoRecursoTipoEnvioShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPlanoRecursoTipoEnviosByNameIsInShouldWork() throws Exception {
        // Initialize the database
        planoRecursoTipoEnvioRepository.saveAndFlush(planoRecursoTipoEnvio);

        // Get all the planoRecursoTipoEnvioList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPlanoRecursoTipoEnvioShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the planoRecursoTipoEnvioList where name equals to UPDATED_NAME
        defaultPlanoRecursoTipoEnvioShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPlanoRecursoTipoEnviosByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoRecursoTipoEnvioRepository.saveAndFlush(planoRecursoTipoEnvio);

        // Get all the planoRecursoTipoEnvioList where name is not null
        defaultPlanoRecursoTipoEnvioShouldBeFound("name.specified=true");

        // Get all the planoRecursoTipoEnvioList where name is null
        defaultPlanoRecursoTipoEnvioShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllPlanoRecursoTipoEnviosByNameContainsSomething() throws Exception {
        // Initialize the database
        planoRecursoTipoEnvioRepository.saveAndFlush(planoRecursoTipoEnvio);

        // Get all the planoRecursoTipoEnvioList where name contains DEFAULT_NAME
        defaultPlanoRecursoTipoEnvioShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the planoRecursoTipoEnvioList where name contains UPDATED_NAME
        defaultPlanoRecursoTipoEnvioShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPlanoRecursoTipoEnviosByNameNotContainsSomething() throws Exception {
        // Initialize the database
        planoRecursoTipoEnvioRepository.saveAndFlush(planoRecursoTipoEnvio);

        // Get all the planoRecursoTipoEnvioList where name does not contain DEFAULT_NAME
        defaultPlanoRecursoTipoEnvioShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the planoRecursoTipoEnvioList where name does not contain UPDATED_NAME
        defaultPlanoRecursoTipoEnvioShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllPlanoRecursoTipoEnviosByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        planoRecursoTipoEnvioRepository.saveAndFlush(planoRecursoTipoEnvio);

        // Get all the planoRecursoTipoEnvioList where descricao equals to DEFAULT_DESCRICAO
        defaultPlanoRecursoTipoEnvioShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the planoRecursoTipoEnvioList where descricao equals to UPDATED_DESCRICAO
        defaultPlanoRecursoTipoEnvioShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllPlanoRecursoTipoEnviosByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        planoRecursoTipoEnvioRepository.saveAndFlush(planoRecursoTipoEnvio);

        // Get all the planoRecursoTipoEnvioList where descricao not equals to DEFAULT_DESCRICAO
        defaultPlanoRecursoTipoEnvioShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the planoRecursoTipoEnvioList where descricao not equals to UPDATED_DESCRICAO
        defaultPlanoRecursoTipoEnvioShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllPlanoRecursoTipoEnviosByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        planoRecursoTipoEnvioRepository.saveAndFlush(planoRecursoTipoEnvio);

        // Get all the planoRecursoTipoEnvioList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultPlanoRecursoTipoEnvioShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the planoRecursoTipoEnvioList where descricao equals to UPDATED_DESCRICAO
        defaultPlanoRecursoTipoEnvioShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllPlanoRecursoTipoEnviosByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoRecursoTipoEnvioRepository.saveAndFlush(planoRecursoTipoEnvio);

        // Get all the planoRecursoTipoEnvioList where descricao is not null
        defaultPlanoRecursoTipoEnvioShouldBeFound("descricao.specified=true");

        // Get all the planoRecursoTipoEnvioList where descricao is null
        defaultPlanoRecursoTipoEnvioShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllPlanoRecursoTipoEnviosByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        planoRecursoTipoEnvioRepository.saveAndFlush(planoRecursoTipoEnvio);

        // Get all the planoRecursoTipoEnvioList where descricao contains DEFAULT_DESCRICAO
        defaultPlanoRecursoTipoEnvioShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the planoRecursoTipoEnvioList where descricao contains UPDATED_DESCRICAO
        defaultPlanoRecursoTipoEnvioShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllPlanoRecursoTipoEnviosByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        planoRecursoTipoEnvioRepository.saveAndFlush(planoRecursoTipoEnvio);

        // Get all the planoRecursoTipoEnvioList where descricao does not contain DEFAULT_DESCRICAO
        defaultPlanoRecursoTipoEnvioShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the planoRecursoTipoEnvioList where descricao does not contain UPDATED_DESCRICAO
        defaultPlanoRecursoTipoEnvioShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllPlanoRecursoTipoEnviosByQtdIsEqualToSomething() throws Exception {
        // Initialize the database
        planoRecursoTipoEnvioRepository.saveAndFlush(planoRecursoTipoEnvio);

        // Get all the planoRecursoTipoEnvioList where qtd equals to DEFAULT_QTD
        defaultPlanoRecursoTipoEnvioShouldBeFound("qtd.equals=" + DEFAULT_QTD);

        // Get all the planoRecursoTipoEnvioList where qtd equals to UPDATED_QTD
        defaultPlanoRecursoTipoEnvioShouldNotBeFound("qtd.equals=" + UPDATED_QTD);
    }

    @Test
    @Transactional
    public void getAllPlanoRecursoTipoEnviosByQtdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        planoRecursoTipoEnvioRepository.saveAndFlush(planoRecursoTipoEnvio);

        // Get all the planoRecursoTipoEnvioList where qtd not equals to DEFAULT_QTD
        defaultPlanoRecursoTipoEnvioShouldNotBeFound("qtd.notEquals=" + DEFAULT_QTD);

        // Get all the planoRecursoTipoEnvioList where qtd not equals to UPDATED_QTD
        defaultPlanoRecursoTipoEnvioShouldBeFound("qtd.notEquals=" + UPDATED_QTD);
    }

    @Test
    @Transactional
    public void getAllPlanoRecursoTipoEnviosByQtdIsInShouldWork() throws Exception {
        // Initialize the database
        planoRecursoTipoEnvioRepository.saveAndFlush(planoRecursoTipoEnvio);

        // Get all the planoRecursoTipoEnvioList where qtd in DEFAULT_QTD or UPDATED_QTD
        defaultPlanoRecursoTipoEnvioShouldBeFound("qtd.in=" + DEFAULT_QTD + "," + UPDATED_QTD);

        // Get all the planoRecursoTipoEnvioList where qtd equals to UPDATED_QTD
        defaultPlanoRecursoTipoEnvioShouldNotBeFound("qtd.in=" + UPDATED_QTD);
    }

    @Test
    @Transactional
    public void getAllPlanoRecursoTipoEnviosByQtdIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoRecursoTipoEnvioRepository.saveAndFlush(planoRecursoTipoEnvio);

        // Get all the planoRecursoTipoEnvioList where qtd is not null
        defaultPlanoRecursoTipoEnvioShouldBeFound("qtd.specified=true");

        // Get all the planoRecursoTipoEnvioList where qtd is null
        defaultPlanoRecursoTipoEnvioShouldNotBeFound("qtd.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlanoRecursoTipoEnviosByQtdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoRecursoTipoEnvioRepository.saveAndFlush(planoRecursoTipoEnvio);

        // Get all the planoRecursoTipoEnvioList where qtd is greater than or equal to DEFAULT_QTD
        defaultPlanoRecursoTipoEnvioShouldBeFound("qtd.greaterThanOrEqual=" + DEFAULT_QTD);

        // Get all the planoRecursoTipoEnvioList where qtd is greater than or equal to UPDATED_QTD
        defaultPlanoRecursoTipoEnvioShouldNotBeFound("qtd.greaterThanOrEqual=" + UPDATED_QTD);
    }

    @Test
    @Transactional
    public void getAllPlanoRecursoTipoEnviosByQtdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoRecursoTipoEnvioRepository.saveAndFlush(planoRecursoTipoEnvio);

        // Get all the planoRecursoTipoEnvioList where qtd is less than or equal to DEFAULT_QTD
        defaultPlanoRecursoTipoEnvioShouldBeFound("qtd.lessThanOrEqual=" + DEFAULT_QTD);

        // Get all the planoRecursoTipoEnvioList where qtd is less than or equal to SMALLER_QTD
        defaultPlanoRecursoTipoEnvioShouldNotBeFound("qtd.lessThanOrEqual=" + SMALLER_QTD);
    }

    @Test
    @Transactional
    public void getAllPlanoRecursoTipoEnviosByQtdIsLessThanSomething() throws Exception {
        // Initialize the database
        planoRecursoTipoEnvioRepository.saveAndFlush(planoRecursoTipoEnvio);

        // Get all the planoRecursoTipoEnvioList where qtd is less than DEFAULT_QTD
        defaultPlanoRecursoTipoEnvioShouldNotBeFound("qtd.lessThan=" + DEFAULT_QTD);

        // Get all the planoRecursoTipoEnvioList where qtd is less than UPDATED_QTD
        defaultPlanoRecursoTipoEnvioShouldBeFound("qtd.lessThan=" + UPDATED_QTD);
    }

    @Test
    @Transactional
    public void getAllPlanoRecursoTipoEnviosByQtdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        planoRecursoTipoEnvioRepository.saveAndFlush(planoRecursoTipoEnvio);

        // Get all the planoRecursoTipoEnvioList where qtd is greater than DEFAULT_QTD
        defaultPlanoRecursoTipoEnvioShouldNotBeFound("qtd.greaterThan=" + DEFAULT_QTD);

        // Get all the planoRecursoTipoEnvioList where qtd is greater than SMALLER_QTD
        defaultPlanoRecursoTipoEnvioShouldBeFound("qtd.greaterThan=" + SMALLER_QTD);
    }


    @Test
    @Transactional
    public void getAllPlanoRecursoTipoEnviosByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        planoRecursoTipoEnvioRepository.saveAndFlush(planoRecursoTipoEnvio);

        // Get all the planoRecursoTipoEnvioList where created equals to DEFAULT_CREATED
        defaultPlanoRecursoTipoEnvioShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the planoRecursoTipoEnvioList where created equals to UPDATED_CREATED
        defaultPlanoRecursoTipoEnvioShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllPlanoRecursoTipoEnviosByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        planoRecursoTipoEnvioRepository.saveAndFlush(planoRecursoTipoEnvio);

        // Get all the planoRecursoTipoEnvioList where created not equals to DEFAULT_CREATED
        defaultPlanoRecursoTipoEnvioShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the planoRecursoTipoEnvioList where created not equals to UPDATED_CREATED
        defaultPlanoRecursoTipoEnvioShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllPlanoRecursoTipoEnviosByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        planoRecursoTipoEnvioRepository.saveAndFlush(planoRecursoTipoEnvio);

        // Get all the planoRecursoTipoEnvioList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultPlanoRecursoTipoEnvioShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the planoRecursoTipoEnvioList where created equals to UPDATED_CREATED
        defaultPlanoRecursoTipoEnvioShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllPlanoRecursoTipoEnviosByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoRecursoTipoEnvioRepository.saveAndFlush(planoRecursoTipoEnvio);

        // Get all the planoRecursoTipoEnvioList where created is not null
        defaultPlanoRecursoTipoEnvioShouldBeFound("created.specified=true");

        // Get all the planoRecursoTipoEnvioList where created is null
        defaultPlanoRecursoTipoEnvioShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlanoRecursoTipoEnviosByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        planoRecursoTipoEnvioRepository.saveAndFlush(planoRecursoTipoEnvio);

        // Get all the planoRecursoTipoEnvioList where updated equals to DEFAULT_UPDATED
        defaultPlanoRecursoTipoEnvioShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the planoRecursoTipoEnvioList where updated equals to UPDATED_UPDATED
        defaultPlanoRecursoTipoEnvioShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllPlanoRecursoTipoEnviosByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        planoRecursoTipoEnvioRepository.saveAndFlush(planoRecursoTipoEnvio);

        // Get all the planoRecursoTipoEnvioList where updated not equals to DEFAULT_UPDATED
        defaultPlanoRecursoTipoEnvioShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the planoRecursoTipoEnvioList where updated not equals to UPDATED_UPDATED
        defaultPlanoRecursoTipoEnvioShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllPlanoRecursoTipoEnviosByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        planoRecursoTipoEnvioRepository.saveAndFlush(planoRecursoTipoEnvio);

        // Get all the planoRecursoTipoEnvioList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultPlanoRecursoTipoEnvioShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the planoRecursoTipoEnvioList where updated equals to UPDATED_UPDATED
        defaultPlanoRecursoTipoEnvioShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllPlanoRecursoTipoEnviosByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoRecursoTipoEnvioRepository.saveAndFlush(planoRecursoTipoEnvio);

        // Get all the planoRecursoTipoEnvioList where updated is not null
        defaultPlanoRecursoTipoEnvioShouldBeFound("updated.specified=true");

        // Get all the planoRecursoTipoEnvioList where updated is null
        defaultPlanoRecursoTipoEnvioShouldNotBeFound("updated.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlanoRecursoTipoEnviosByPlanoRecursoIsEqualToSomething() throws Exception {
        // Initialize the database
        planoRecursoTipoEnvioRepository.saveAndFlush(planoRecursoTipoEnvio);
        PlanoRecurso planoRecurso = PlanoRecursoResourceIT.createEntity(em);
        em.persist(planoRecurso);
        em.flush();
        planoRecursoTipoEnvio.setPlanoRecurso(planoRecurso);
        planoRecursoTipoEnvioRepository.saveAndFlush(planoRecursoTipoEnvio);
        Long planoRecursoId = planoRecurso.getId();

        // Get all the planoRecursoTipoEnvioList where planoRecurso equals to planoRecursoId
        defaultPlanoRecursoTipoEnvioShouldBeFound("planoRecursoId.equals=" + planoRecursoId);

        // Get all the planoRecursoTipoEnvioList where planoRecurso equals to planoRecursoId + 1
        defaultPlanoRecursoTipoEnvioShouldNotBeFound("planoRecursoId.equals=" + (planoRecursoId + 1));
    }


    @Test
    @Transactional
    public void getAllPlanoRecursoTipoEnviosByTipoEnvioIsEqualToSomething() throws Exception {
        // Initialize the database
        planoRecursoTipoEnvioRepository.saveAndFlush(planoRecursoTipoEnvio);
        TipoEnvio tipoEnvio = TipoEnvioResourceIT.createEntity(em);
        em.persist(tipoEnvio);
        em.flush();
        planoRecursoTipoEnvio.setTipoEnvio(tipoEnvio);
        planoRecursoTipoEnvioRepository.saveAndFlush(planoRecursoTipoEnvio);
        Long tipoEnvioId = tipoEnvio.getId();

        // Get all the planoRecursoTipoEnvioList where tipoEnvio equals to tipoEnvioId
        defaultPlanoRecursoTipoEnvioShouldBeFound("tipoEnvioId.equals=" + tipoEnvioId);

        // Get all the planoRecursoTipoEnvioList where tipoEnvio equals to tipoEnvioId + 1
        defaultPlanoRecursoTipoEnvioShouldNotBeFound("tipoEnvioId.equals=" + (tipoEnvioId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPlanoRecursoTipoEnvioShouldBeFound(String filter) throws Exception {
        restPlanoRecursoTipoEnvioMockMvc.perform(get("/api/plano-recurso-tipo-envios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planoRecursoTipoEnvio.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].qtd").value(hasItem(DEFAULT_QTD)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restPlanoRecursoTipoEnvioMockMvc.perform(get("/api/plano-recurso-tipo-envios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPlanoRecursoTipoEnvioShouldNotBeFound(String filter) throws Exception {
        restPlanoRecursoTipoEnvioMockMvc.perform(get("/api/plano-recurso-tipo-envios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPlanoRecursoTipoEnvioMockMvc.perform(get("/api/plano-recurso-tipo-envios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPlanoRecursoTipoEnvio() throws Exception {
        // Get the planoRecursoTipoEnvio
        restPlanoRecursoTipoEnvioMockMvc.perform(get("/api/plano-recurso-tipo-envios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlanoRecursoTipoEnvio() throws Exception {
        // Initialize the database
        planoRecursoTipoEnvioRepository.saveAndFlush(planoRecursoTipoEnvio);

        int databaseSizeBeforeUpdate = planoRecursoTipoEnvioRepository.findAll().size();

        // Update the planoRecursoTipoEnvio
        PlanoRecursoTipoEnvio updatedPlanoRecursoTipoEnvio = planoRecursoTipoEnvioRepository.findById(planoRecursoTipoEnvio.getId()).get();
        // Disconnect from session so that the updates on updatedPlanoRecursoTipoEnvio are not directly saved in db
        em.detach(updatedPlanoRecursoTipoEnvio);
        updatedPlanoRecursoTipoEnvio
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .qtd(UPDATED_QTD)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        PlanoRecursoTipoEnvioDTO planoRecursoTipoEnvioDTO = planoRecursoTipoEnvioMapper.toDto(updatedPlanoRecursoTipoEnvio);

        restPlanoRecursoTipoEnvioMockMvc.perform(put("/api/plano-recurso-tipo-envios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoRecursoTipoEnvioDTO)))
            .andExpect(status().isOk());

        // Validate the PlanoRecursoTipoEnvio in the database
        List<PlanoRecursoTipoEnvio> planoRecursoTipoEnvioList = planoRecursoTipoEnvioRepository.findAll();
        assertThat(planoRecursoTipoEnvioList).hasSize(databaseSizeBeforeUpdate);
        PlanoRecursoTipoEnvio testPlanoRecursoTipoEnvio = planoRecursoTipoEnvioList.get(planoRecursoTipoEnvioList.size() - 1);
        assertThat(testPlanoRecursoTipoEnvio.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPlanoRecursoTipoEnvio.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testPlanoRecursoTipoEnvio.getQtd()).isEqualTo(UPDATED_QTD);
        assertThat(testPlanoRecursoTipoEnvio.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testPlanoRecursoTipoEnvio.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingPlanoRecursoTipoEnvio() throws Exception {
        int databaseSizeBeforeUpdate = planoRecursoTipoEnvioRepository.findAll().size();

        // Create the PlanoRecursoTipoEnvio
        PlanoRecursoTipoEnvioDTO planoRecursoTipoEnvioDTO = planoRecursoTipoEnvioMapper.toDto(planoRecursoTipoEnvio);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanoRecursoTipoEnvioMockMvc.perform(put("/api/plano-recurso-tipo-envios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoRecursoTipoEnvioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PlanoRecursoTipoEnvio in the database
        List<PlanoRecursoTipoEnvio> planoRecursoTipoEnvioList = planoRecursoTipoEnvioRepository.findAll();
        assertThat(planoRecursoTipoEnvioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePlanoRecursoTipoEnvio() throws Exception {
        // Initialize the database
        planoRecursoTipoEnvioRepository.saveAndFlush(planoRecursoTipoEnvio);

        int databaseSizeBeforeDelete = planoRecursoTipoEnvioRepository.findAll().size();

        // Delete the planoRecursoTipoEnvio
        restPlanoRecursoTipoEnvioMockMvc.perform(delete("/api/plano-recurso-tipo-envios/{id}", planoRecursoTipoEnvio.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlanoRecursoTipoEnvio> planoRecursoTipoEnvioList = planoRecursoTipoEnvioRepository.findAll();
        assertThat(planoRecursoTipoEnvioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
