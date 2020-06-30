package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.TipoEnvioIntegrador;
import org.tempestade.nucleo.domain.TipoEnvio;
import org.tempestade.nucleo.domain.Integrador;
import org.tempestade.nucleo.repository.TipoEnvioIntegradorRepository;
import org.tempestade.nucleo.service.TipoEnvioIntegradorService;
import org.tempestade.nucleo.service.dto.TipoEnvioIntegradorDTO;
import org.tempestade.nucleo.service.mapper.TipoEnvioIntegradorMapper;
import org.tempestade.nucleo.service.dto.TipoEnvioIntegradorCriteria;
import org.tempestade.nucleo.service.TipoEnvioIntegradorQueryService;

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
 * Integration tests for the {@link TipoEnvioIntegradorResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TipoEnvioIntegradorResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ATIVO = false;
    private static final Boolean UPDATED_ATIVO = true;

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private TipoEnvioIntegradorRepository tipoEnvioIntegradorRepository;

    @Autowired
    private TipoEnvioIntegradorMapper tipoEnvioIntegradorMapper;

    @Autowired
    private TipoEnvioIntegradorService tipoEnvioIntegradorService;

    @Autowired
    private TipoEnvioIntegradorQueryService tipoEnvioIntegradorQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoEnvioIntegradorMockMvc;

    private TipoEnvioIntegrador tipoEnvioIntegrador;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoEnvioIntegrador createEntity(EntityManager em) {
        TipoEnvioIntegrador tipoEnvioIntegrador = new TipoEnvioIntegrador()
            .name(DEFAULT_NAME)
            .descricao(DEFAULT_DESCRICAO)
            .ativo(DEFAULT_ATIVO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        // Add required entity
        TipoEnvio tipoEnvio;
        if (TestUtil.findAll(em, TipoEnvio.class).isEmpty()) {
            tipoEnvio = TipoEnvioResourceIT.createEntity(em);
            em.persist(tipoEnvio);
            em.flush();
        } else {
            tipoEnvio = TestUtil.findAll(em, TipoEnvio.class).get(0);
        }
        tipoEnvioIntegrador.setTipoEnvio(tipoEnvio);
        // Add required entity
        Integrador integrador;
        if (TestUtil.findAll(em, Integrador.class).isEmpty()) {
            integrador = IntegradorResourceIT.createEntity(em);
            em.persist(integrador);
            em.flush();
        } else {
            integrador = TestUtil.findAll(em, Integrador.class).get(0);
        }
        tipoEnvioIntegrador.setIntegrador(integrador);
        return tipoEnvioIntegrador;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoEnvioIntegrador createUpdatedEntity(EntityManager em) {
        TipoEnvioIntegrador tipoEnvioIntegrador = new TipoEnvioIntegrador()
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .ativo(UPDATED_ATIVO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        // Add required entity
        TipoEnvio tipoEnvio;
        if (TestUtil.findAll(em, TipoEnvio.class).isEmpty()) {
            tipoEnvio = TipoEnvioResourceIT.createUpdatedEntity(em);
            em.persist(tipoEnvio);
            em.flush();
        } else {
            tipoEnvio = TestUtil.findAll(em, TipoEnvio.class).get(0);
        }
        tipoEnvioIntegrador.setTipoEnvio(tipoEnvio);
        // Add required entity
        Integrador integrador;
        if (TestUtil.findAll(em, Integrador.class).isEmpty()) {
            integrador = IntegradorResourceIT.createUpdatedEntity(em);
            em.persist(integrador);
            em.flush();
        } else {
            integrador = TestUtil.findAll(em, Integrador.class).get(0);
        }
        tipoEnvioIntegrador.setIntegrador(integrador);
        return tipoEnvioIntegrador;
    }

    @BeforeEach
    public void initTest() {
        tipoEnvioIntegrador = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoEnvioIntegrador() throws Exception {
        int databaseSizeBeforeCreate = tipoEnvioIntegradorRepository.findAll().size();
        // Create the TipoEnvioIntegrador
        TipoEnvioIntegradorDTO tipoEnvioIntegradorDTO = tipoEnvioIntegradorMapper.toDto(tipoEnvioIntegrador);
        restTipoEnvioIntegradorMockMvc.perform(post("/api/tipo-envio-integradors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoEnvioIntegradorDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoEnvioIntegrador in the database
        List<TipoEnvioIntegrador> tipoEnvioIntegradorList = tipoEnvioIntegradorRepository.findAll();
        assertThat(tipoEnvioIntegradorList).hasSize(databaseSizeBeforeCreate + 1);
        TipoEnvioIntegrador testTipoEnvioIntegrador = tipoEnvioIntegradorList.get(tipoEnvioIntegradorList.size() - 1);
        assertThat(testTipoEnvioIntegrador.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTipoEnvioIntegrador.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testTipoEnvioIntegrador.isAtivo()).isEqualTo(DEFAULT_ATIVO);
        assertThat(testTipoEnvioIntegrador.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testTipoEnvioIntegrador.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createTipoEnvioIntegradorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoEnvioIntegradorRepository.findAll().size();

        // Create the TipoEnvioIntegrador with an existing ID
        tipoEnvioIntegrador.setId(1L);
        TipoEnvioIntegradorDTO tipoEnvioIntegradorDTO = tipoEnvioIntegradorMapper.toDto(tipoEnvioIntegrador);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoEnvioIntegradorMockMvc.perform(post("/api/tipo-envio-integradors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoEnvioIntegradorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoEnvioIntegrador in the database
        List<TipoEnvioIntegrador> tipoEnvioIntegradorList = tipoEnvioIntegradorRepository.findAll();
        assertThat(tipoEnvioIntegradorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoEnvioIntegradorRepository.findAll().size();
        // set the field null
        tipoEnvioIntegrador.setName(null);

        // Create the TipoEnvioIntegrador, which fails.
        TipoEnvioIntegradorDTO tipoEnvioIntegradorDTO = tipoEnvioIntegradorMapper.toDto(tipoEnvioIntegrador);


        restTipoEnvioIntegradorMockMvc.perform(post("/api/tipo-envio-integradors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoEnvioIntegradorDTO)))
            .andExpect(status().isBadRequest());

        List<TipoEnvioIntegrador> tipoEnvioIntegradorList = tipoEnvioIntegradorRepository.findAll();
        assertThat(tipoEnvioIntegradorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoEnvioIntegradorRepository.findAll().size();
        // set the field null
        tipoEnvioIntegrador.setDescricao(null);

        // Create the TipoEnvioIntegrador, which fails.
        TipoEnvioIntegradorDTO tipoEnvioIntegradorDTO = tipoEnvioIntegradorMapper.toDto(tipoEnvioIntegrador);


        restTipoEnvioIntegradorMockMvc.perform(post("/api/tipo-envio-integradors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoEnvioIntegradorDTO)))
            .andExpect(status().isBadRequest());

        List<TipoEnvioIntegrador> tipoEnvioIntegradorList = tipoEnvioIntegradorRepository.findAll();
        assertThat(tipoEnvioIntegradorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoEnvioIntegradorRepository.findAll().size();
        // set the field null
        tipoEnvioIntegrador.setCreated(null);

        // Create the TipoEnvioIntegrador, which fails.
        TipoEnvioIntegradorDTO tipoEnvioIntegradorDTO = tipoEnvioIntegradorMapper.toDto(tipoEnvioIntegrador);


        restTipoEnvioIntegradorMockMvc.perform(post("/api/tipo-envio-integradors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoEnvioIntegradorDTO)))
            .andExpect(status().isBadRequest());

        List<TipoEnvioIntegrador> tipoEnvioIntegradorList = tipoEnvioIntegradorRepository.findAll();
        assertThat(tipoEnvioIntegradorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoEnvioIntegradors() throws Exception {
        // Initialize the database
        tipoEnvioIntegradorRepository.saveAndFlush(tipoEnvioIntegrador);

        // Get all the tipoEnvioIntegradorList
        restTipoEnvioIntegradorMockMvc.perform(get("/api/tipo-envio-integradors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoEnvioIntegrador.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getTipoEnvioIntegrador() throws Exception {
        // Initialize the database
        tipoEnvioIntegradorRepository.saveAndFlush(tipoEnvioIntegrador);

        // Get the tipoEnvioIntegrador
        restTipoEnvioIntegradorMockMvc.perform(get("/api/tipo-envio-integradors/{id}", tipoEnvioIntegrador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoEnvioIntegrador.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }


    @Test
    @Transactional
    public void getTipoEnvioIntegradorsByIdFiltering() throws Exception {
        // Initialize the database
        tipoEnvioIntegradorRepository.saveAndFlush(tipoEnvioIntegrador);

        Long id = tipoEnvioIntegrador.getId();

        defaultTipoEnvioIntegradorShouldBeFound("id.equals=" + id);
        defaultTipoEnvioIntegradorShouldNotBeFound("id.notEquals=" + id);

        defaultTipoEnvioIntegradorShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTipoEnvioIntegradorShouldNotBeFound("id.greaterThan=" + id);

        defaultTipoEnvioIntegradorShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTipoEnvioIntegradorShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTipoEnvioIntegradorsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        tipoEnvioIntegradorRepository.saveAndFlush(tipoEnvioIntegrador);

        // Get all the tipoEnvioIntegradorList where name equals to DEFAULT_NAME
        defaultTipoEnvioIntegradorShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the tipoEnvioIntegradorList where name equals to UPDATED_NAME
        defaultTipoEnvioIntegradorShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTipoEnvioIntegradorsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tipoEnvioIntegradorRepository.saveAndFlush(tipoEnvioIntegrador);

        // Get all the tipoEnvioIntegradorList where name not equals to DEFAULT_NAME
        defaultTipoEnvioIntegradorShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the tipoEnvioIntegradorList where name not equals to UPDATED_NAME
        defaultTipoEnvioIntegradorShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTipoEnvioIntegradorsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        tipoEnvioIntegradorRepository.saveAndFlush(tipoEnvioIntegrador);

        // Get all the tipoEnvioIntegradorList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTipoEnvioIntegradorShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the tipoEnvioIntegradorList where name equals to UPDATED_NAME
        defaultTipoEnvioIntegradorShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTipoEnvioIntegradorsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipoEnvioIntegradorRepository.saveAndFlush(tipoEnvioIntegrador);

        // Get all the tipoEnvioIntegradorList where name is not null
        defaultTipoEnvioIntegradorShouldBeFound("name.specified=true");

        // Get all the tipoEnvioIntegradorList where name is null
        defaultTipoEnvioIntegradorShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllTipoEnvioIntegradorsByNameContainsSomething() throws Exception {
        // Initialize the database
        tipoEnvioIntegradorRepository.saveAndFlush(tipoEnvioIntegrador);

        // Get all the tipoEnvioIntegradorList where name contains DEFAULT_NAME
        defaultTipoEnvioIntegradorShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the tipoEnvioIntegradorList where name contains UPDATED_NAME
        defaultTipoEnvioIntegradorShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTipoEnvioIntegradorsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        tipoEnvioIntegradorRepository.saveAndFlush(tipoEnvioIntegrador);

        // Get all the tipoEnvioIntegradorList where name does not contain DEFAULT_NAME
        defaultTipoEnvioIntegradorShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the tipoEnvioIntegradorList where name does not contain UPDATED_NAME
        defaultTipoEnvioIntegradorShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllTipoEnvioIntegradorsByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        tipoEnvioIntegradorRepository.saveAndFlush(tipoEnvioIntegrador);

        // Get all the tipoEnvioIntegradorList where descricao equals to DEFAULT_DESCRICAO
        defaultTipoEnvioIntegradorShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the tipoEnvioIntegradorList where descricao equals to UPDATED_DESCRICAO
        defaultTipoEnvioIntegradorShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllTipoEnvioIntegradorsByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tipoEnvioIntegradorRepository.saveAndFlush(tipoEnvioIntegrador);

        // Get all the tipoEnvioIntegradorList where descricao not equals to DEFAULT_DESCRICAO
        defaultTipoEnvioIntegradorShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the tipoEnvioIntegradorList where descricao not equals to UPDATED_DESCRICAO
        defaultTipoEnvioIntegradorShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllTipoEnvioIntegradorsByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        tipoEnvioIntegradorRepository.saveAndFlush(tipoEnvioIntegrador);

        // Get all the tipoEnvioIntegradorList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultTipoEnvioIntegradorShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the tipoEnvioIntegradorList where descricao equals to UPDATED_DESCRICAO
        defaultTipoEnvioIntegradorShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllTipoEnvioIntegradorsByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipoEnvioIntegradorRepository.saveAndFlush(tipoEnvioIntegrador);

        // Get all the tipoEnvioIntegradorList where descricao is not null
        defaultTipoEnvioIntegradorShouldBeFound("descricao.specified=true");

        // Get all the tipoEnvioIntegradorList where descricao is null
        defaultTipoEnvioIntegradorShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllTipoEnvioIntegradorsByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        tipoEnvioIntegradorRepository.saveAndFlush(tipoEnvioIntegrador);

        // Get all the tipoEnvioIntegradorList where descricao contains DEFAULT_DESCRICAO
        defaultTipoEnvioIntegradorShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the tipoEnvioIntegradorList where descricao contains UPDATED_DESCRICAO
        defaultTipoEnvioIntegradorShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllTipoEnvioIntegradorsByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        tipoEnvioIntegradorRepository.saveAndFlush(tipoEnvioIntegrador);

        // Get all the tipoEnvioIntegradorList where descricao does not contain DEFAULT_DESCRICAO
        defaultTipoEnvioIntegradorShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the tipoEnvioIntegradorList where descricao does not contain UPDATED_DESCRICAO
        defaultTipoEnvioIntegradorShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllTipoEnvioIntegradorsByAtivoIsEqualToSomething() throws Exception {
        // Initialize the database
        tipoEnvioIntegradorRepository.saveAndFlush(tipoEnvioIntegrador);

        // Get all the tipoEnvioIntegradorList where ativo equals to DEFAULT_ATIVO
        defaultTipoEnvioIntegradorShouldBeFound("ativo.equals=" + DEFAULT_ATIVO);

        // Get all the tipoEnvioIntegradorList where ativo equals to UPDATED_ATIVO
        defaultTipoEnvioIntegradorShouldNotBeFound("ativo.equals=" + UPDATED_ATIVO);
    }

    @Test
    @Transactional
    public void getAllTipoEnvioIntegradorsByAtivoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tipoEnvioIntegradorRepository.saveAndFlush(tipoEnvioIntegrador);

        // Get all the tipoEnvioIntegradorList where ativo not equals to DEFAULT_ATIVO
        defaultTipoEnvioIntegradorShouldNotBeFound("ativo.notEquals=" + DEFAULT_ATIVO);

        // Get all the tipoEnvioIntegradorList where ativo not equals to UPDATED_ATIVO
        defaultTipoEnvioIntegradorShouldBeFound("ativo.notEquals=" + UPDATED_ATIVO);
    }

    @Test
    @Transactional
    public void getAllTipoEnvioIntegradorsByAtivoIsInShouldWork() throws Exception {
        // Initialize the database
        tipoEnvioIntegradorRepository.saveAndFlush(tipoEnvioIntegrador);

        // Get all the tipoEnvioIntegradorList where ativo in DEFAULT_ATIVO or UPDATED_ATIVO
        defaultTipoEnvioIntegradorShouldBeFound("ativo.in=" + DEFAULT_ATIVO + "," + UPDATED_ATIVO);

        // Get all the tipoEnvioIntegradorList where ativo equals to UPDATED_ATIVO
        defaultTipoEnvioIntegradorShouldNotBeFound("ativo.in=" + UPDATED_ATIVO);
    }

    @Test
    @Transactional
    public void getAllTipoEnvioIntegradorsByAtivoIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipoEnvioIntegradorRepository.saveAndFlush(tipoEnvioIntegrador);

        // Get all the tipoEnvioIntegradorList where ativo is not null
        defaultTipoEnvioIntegradorShouldBeFound("ativo.specified=true");

        // Get all the tipoEnvioIntegradorList where ativo is null
        defaultTipoEnvioIntegradorShouldNotBeFound("ativo.specified=false");
    }

    @Test
    @Transactional
    public void getAllTipoEnvioIntegradorsByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        tipoEnvioIntegradorRepository.saveAndFlush(tipoEnvioIntegrador);

        // Get all the tipoEnvioIntegradorList where created equals to DEFAULT_CREATED
        defaultTipoEnvioIntegradorShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the tipoEnvioIntegradorList where created equals to UPDATED_CREATED
        defaultTipoEnvioIntegradorShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllTipoEnvioIntegradorsByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tipoEnvioIntegradorRepository.saveAndFlush(tipoEnvioIntegrador);

        // Get all the tipoEnvioIntegradorList where created not equals to DEFAULT_CREATED
        defaultTipoEnvioIntegradorShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the tipoEnvioIntegradorList where created not equals to UPDATED_CREATED
        defaultTipoEnvioIntegradorShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllTipoEnvioIntegradorsByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        tipoEnvioIntegradorRepository.saveAndFlush(tipoEnvioIntegrador);

        // Get all the tipoEnvioIntegradorList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultTipoEnvioIntegradorShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the tipoEnvioIntegradorList where created equals to UPDATED_CREATED
        defaultTipoEnvioIntegradorShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllTipoEnvioIntegradorsByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipoEnvioIntegradorRepository.saveAndFlush(tipoEnvioIntegrador);

        // Get all the tipoEnvioIntegradorList where created is not null
        defaultTipoEnvioIntegradorShouldBeFound("created.specified=true");

        // Get all the tipoEnvioIntegradorList where created is null
        defaultTipoEnvioIntegradorShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllTipoEnvioIntegradorsByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        tipoEnvioIntegradorRepository.saveAndFlush(tipoEnvioIntegrador);

        // Get all the tipoEnvioIntegradorList where updated equals to DEFAULT_UPDATED
        defaultTipoEnvioIntegradorShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the tipoEnvioIntegradorList where updated equals to UPDATED_UPDATED
        defaultTipoEnvioIntegradorShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllTipoEnvioIntegradorsByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tipoEnvioIntegradorRepository.saveAndFlush(tipoEnvioIntegrador);

        // Get all the tipoEnvioIntegradorList where updated not equals to DEFAULT_UPDATED
        defaultTipoEnvioIntegradorShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the tipoEnvioIntegradorList where updated not equals to UPDATED_UPDATED
        defaultTipoEnvioIntegradorShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllTipoEnvioIntegradorsByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        tipoEnvioIntegradorRepository.saveAndFlush(tipoEnvioIntegrador);

        // Get all the tipoEnvioIntegradorList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultTipoEnvioIntegradorShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the tipoEnvioIntegradorList where updated equals to UPDATED_UPDATED
        defaultTipoEnvioIntegradorShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllTipoEnvioIntegradorsByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipoEnvioIntegradorRepository.saveAndFlush(tipoEnvioIntegrador);

        // Get all the tipoEnvioIntegradorList where updated is not null
        defaultTipoEnvioIntegradorShouldBeFound("updated.specified=true");

        // Get all the tipoEnvioIntegradorList where updated is null
        defaultTipoEnvioIntegradorShouldNotBeFound("updated.specified=false");
    }

    @Test
    @Transactional
    public void getAllTipoEnvioIntegradorsByTipoEnvioIsEqualToSomething() throws Exception {
        // Get already existing entity
        TipoEnvio tipoEnvio = tipoEnvioIntegrador.getTipoEnvio();
        tipoEnvioIntegradorRepository.saveAndFlush(tipoEnvioIntegrador);
        Long tipoEnvioId = tipoEnvio.getId();

        // Get all the tipoEnvioIntegradorList where tipoEnvio equals to tipoEnvioId
        defaultTipoEnvioIntegradorShouldBeFound("tipoEnvioId.equals=" + tipoEnvioId);

        // Get all the tipoEnvioIntegradorList where tipoEnvio equals to tipoEnvioId + 1
        defaultTipoEnvioIntegradorShouldNotBeFound("tipoEnvioId.equals=" + (tipoEnvioId + 1));
    }


    @Test
    @Transactional
    public void getAllTipoEnvioIntegradorsByIntegradorIsEqualToSomething() throws Exception {
        // Get already existing entity
        Integrador integrador = tipoEnvioIntegrador.getIntegrador();
        tipoEnvioIntegradorRepository.saveAndFlush(tipoEnvioIntegrador);
        Long integradorId = integrador.getId();

        // Get all the tipoEnvioIntegradorList where integrador equals to integradorId
        defaultTipoEnvioIntegradorShouldBeFound("integradorId.equals=" + integradorId);

        // Get all the tipoEnvioIntegradorList where integrador equals to integradorId + 1
        defaultTipoEnvioIntegradorShouldNotBeFound("integradorId.equals=" + (integradorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTipoEnvioIntegradorShouldBeFound(String filter) throws Exception {
        restTipoEnvioIntegradorMockMvc.perform(get("/api/tipo-envio-integradors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoEnvioIntegrador.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restTipoEnvioIntegradorMockMvc.perform(get("/api/tipo-envio-integradors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTipoEnvioIntegradorShouldNotBeFound(String filter) throws Exception {
        restTipoEnvioIntegradorMockMvc.perform(get("/api/tipo-envio-integradors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTipoEnvioIntegradorMockMvc.perform(get("/api/tipo-envio-integradors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTipoEnvioIntegrador() throws Exception {
        // Get the tipoEnvioIntegrador
        restTipoEnvioIntegradorMockMvc.perform(get("/api/tipo-envio-integradors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoEnvioIntegrador() throws Exception {
        // Initialize the database
        tipoEnvioIntegradorRepository.saveAndFlush(tipoEnvioIntegrador);

        int databaseSizeBeforeUpdate = tipoEnvioIntegradorRepository.findAll().size();

        // Update the tipoEnvioIntegrador
        TipoEnvioIntegrador updatedTipoEnvioIntegrador = tipoEnvioIntegradorRepository.findById(tipoEnvioIntegrador.getId()).get();
        // Disconnect from session so that the updates on updatedTipoEnvioIntegrador are not directly saved in db
        em.detach(updatedTipoEnvioIntegrador);
        updatedTipoEnvioIntegrador
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .ativo(UPDATED_ATIVO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        TipoEnvioIntegradorDTO tipoEnvioIntegradorDTO = tipoEnvioIntegradorMapper.toDto(updatedTipoEnvioIntegrador);

        restTipoEnvioIntegradorMockMvc.perform(put("/api/tipo-envio-integradors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoEnvioIntegradorDTO)))
            .andExpect(status().isOk());

        // Validate the TipoEnvioIntegrador in the database
        List<TipoEnvioIntegrador> tipoEnvioIntegradorList = tipoEnvioIntegradorRepository.findAll();
        assertThat(tipoEnvioIntegradorList).hasSize(databaseSizeBeforeUpdate);
        TipoEnvioIntegrador testTipoEnvioIntegrador = tipoEnvioIntegradorList.get(tipoEnvioIntegradorList.size() - 1);
        assertThat(testTipoEnvioIntegrador.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTipoEnvioIntegrador.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testTipoEnvioIntegrador.isAtivo()).isEqualTo(UPDATED_ATIVO);
        assertThat(testTipoEnvioIntegrador.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testTipoEnvioIntegrador.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoEnvioIntegrador() throws Exception {
        int databaseSizeBeforeUpdate = tipoEnvioIntegradorRepository.findAll().size();

        // Create the TipoEnvioIntegrador
        TipoEnvioIntegradorDTO tipoEnvioIntegradorDTO = tipoEnvioIntegradorMapper.toDto(tipoEnvioIntegrador);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoEnvioIntegradorMockMvc.perform(put("/api/tipo-envio-integradors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoEnvioIntegradorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoEnvioIntegrador in the database
        List<TipoEnvioIntegrador> tipoEnvioIntegradorList = tipoEnvioIntegradorRepository.findAll();
        assertThat(tipoEnvioIntegradorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoEnvioIntegrador() throws Exception {
        // Initialize the database
        tipoEnvioIntegradorRepository.saveAndFlush(tipoEnvioIntegrador);

        int databaseSizeBeforeDelete = tipoEnvioIntegradorRepository.findAll().size();

        // Delete the tipoEnvioIntegrador
        restTipoEnvioIntegradorMockMvc.perform(delete("/api/tipo-envio-integradors/{id}", tipoEnvioIntegrador.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoEnvioIntegrador> tipoEnvioIntegradorList = tipoEnvioIntegradorRepository.findAll();
        assertThat(tipoEnvioIntegradorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
