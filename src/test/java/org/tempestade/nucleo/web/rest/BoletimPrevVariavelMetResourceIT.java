package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.BoletimPrevVariavelMet;
import org.tempestade.nucleo.domain.BoletimPrevisao;
import org.tempestade.nucleo.domain.VariavelMeteorologica;
import org.tempestade.nucleo.repository.BoletimPrevVariavelMetRepository;
import org.tempestade.nucleo.service.BoletimPrevVariavelMetService;
import org.tempestade.nucleo.service.dto.BoletimPrevVariavelMetDTO;
import org.tempestade.nucleo.service.mapper.BoletimPrevVariavelMetMapper;
import org.tempestade.nucleo.service.dto.BoletimPrevVariavelMetCriteria;
import org.tempestade.nucleo.service.BoletimPrevVariavelMetQueryService;

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
 * Integration tests for the {@link BoletimPrevVariavelMetResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BoletimPrevVariavelMetResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private BoletimPrevVariavelMetRepository boletimPrevVariavelMetRepository;

    @Autowired
    private BoletimPrevVariavelMetMapper boletimPrevVariavelMetMapper;

    @Autowired
    private BoletimPrevVariavelMetService boletimPrevVariavelMetService;

    @Autowired
    private BoletimPrevVariavelMetQueryService boletimPrevVariavelMetQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBoletimPrevVariavelMetMockMvc;

    private BoletimPrevVariavelMet boletimPrevVariavelMet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BoletimPrevVariavelMet createEntity(EntityManager em) {
        BoletimPrevVariavelMet boletimPrevVariavelMet = new BoletimPrevVariavelMet()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        // Add required entity
        BoletimPrevisao boletimPrevisao;
        if (TestUtil.findAll(em, BoletimPrevisao.class).isEmpty()) {
            boletimPrevisao = BoletimPrevisaoResourceIT.createEntity(em);
            em.persist(boletimPrevisao);
            em.flush();
        } else {
            boletimPrevisao = TestUtil.findAll(em, BoletimPrevisao.class).get(0);
        }
        boletimPrevVariavelMet.setBoletimPrevisao(boletimPrevisao);
        // Add required entity
        VariavelMeteorologica variavelMeteorologica;
        if (TestUtil.findAll(em, VariavelMeteorologica.class).isEmpty()) {
            variavelMeteorologica = VariavelMeteorologicaResourceIT.createEntity(em);
            em.persist(variavelMeteorologica);
            em.flush();
        } else {
            variavelMeteorologica = TestUtil.findAll(em, VariavelMeteorologica.class).get(0);
        }
        boletimPrevVariavelMet.setVariavelMeteorologica(variavelMeteorologica);
        return boletimPrevVariavelMet;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BoletimPrevVariavelMet createUpdatedEntity(EntityManager em) {
        BoletimPrevVariavelMet boletimPrevVariavelMet = new BoletimPrevVariavelMet()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        // Add required entity
        BoletimPrevisao boletimPrevisao;
        if (TestUtil.findAll(em, BoletimPrevisao.class).isEmpty()) {
            boletimPrevisao = BoletimPrevisaoResourceIT.createUpdatedEntity(em);
            em.persist(boletimPrevisao);
            em.flush();
        } else {
            boletimPrevisao = TestUtil.findAll(em, BoletimPrevisao.class).get(0);
        }
        boletimPrevVariavelMet.setBoletimPrevisao(boletimPrevisao);
        // Add required entity
        VariavelMeteorologica variavelMeteorologica;
        if (TestUtil.findAll(em, VariavelMeteorologica.class).isEmpty()) {
            variavelMeteorologica = VariavelMeteorologicaResourceIT.createUpdatedEntity(em);
            em.persist(variavelMeteorologica);
            em.flush();
        } else {
            variavelMeteorologica = TestUtil.findAll(em, VariavelMeteorologica.class).get(0);
        }
        boletimPrevVariavelMet.setVariavelMeteorologica(variavelMeteorologica);
        return boletimPrevVariavelMet;
    }

    @BeforeEach
    public void initTest() {
        boletimPrevVariavelMet = createEntity(em);
    }

    @Test
    @Transactional
    public void createBoletimPrevVariavelMet() throws Exception {
        int databaseSizeBeforeCreate = boletimPrevVariavelMetRepository.findAll().size();
        // Create the BoletimPrevVariavelMet
        BoletimPrevVariavelMetDTO boletimPrevVariavelMetDTO = boletimPrevVariavelMetMapper.toDto(boletimPrevVariavelMet);
        restBoletimPrevVariavelMetMockMvc.perform(post("/api/boletim-prev-variavel-mets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boletimPrevVariavelMetDTO)))
            .andExpect(status().isCreated());

        // Validate the BoletimPrevVariavelMet in the database
        List<BoletimPrevVariavelMet> boletimPrevVariavelMetList = boletimPrevVariavelMetRepository.findAll();
        assertThat(boletimPrevVariavelMetList).hasSize(databaseSizeBeforeCreate + 1);
        BoletimPrevVariavelMet testBoletimPrevVariavelMet = boletimPrevVariavelMetList.get(boletimPrevVariavelMetList.size() - 1);
        assertThat(testBoletimPrevVariavelMet.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testBoletimPrevVariavelMet.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testBoletimPrevVariavelMet.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testBoletimPrevVariavelMet.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createBoletimPrevVariavelMetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = boletimPrevVariavelMetRepository.findAll().size();

        // Create the BoletimPrevVariavelMet with an existing ID
        boletimPrevVariavelMet.setId(1L);
        BoletimPrevVariavelMetDTO boletimPrevVariavelMetDTO = boletimPrevVariavelMetMapper.toDto(boletimPrevVariavelMet);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBoletimPrevVariavelMetMockMvc.perform(post("/api/boletim-prev-variavel-mets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boletimPrevVariavelMetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BoletimPrevVariavelMet in the database
        List<BoletimPrevVariavelMet> boletimPrevVariavelMetList = boletimPrevVariavelMetRepository.findAll();
        assertThat(boletimPrevVariavelMetList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = boletimPrevVariavelMetRepository.findAll().size();
        // set the field null
        boletimPrevVariavelMet.setNome(null);

        // Create the BoletimPrevVariavelMet, which fails.
        BoletimPrevVariavelMetDTO boletimPrevVariavelMetDTO = boletimPrevVariavelMetMapper.toDto(boletimPrevVariavelMet);


        restBoletimPrevVariavelMetMockMvc.perform(post("/api/boletim-prev-variavel-mets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boletimPrevVariavelMetDTO)))
            .andExpect(status().isBadRequest());

        List<BoletimPrevVariavelMet> boletimPrevVariavelMetList = boletimPrevVariavelMetRepository.findAll();
        assertThat(boletimPrevVariavelMetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = boletimPrevVariavelMetRepository.findAll().size();
        // set the field null
        boletimPrevVariavelMet.setCreated(null);

        // Create the BoletimPrevVariavelMet, which fails.
        BoletimPrevVariavelMetDTO boletimPrevVariavelMetDTO = boletimPrevVariavelMetMapper.toDto(boletimPrevVariavelMet);


        restBoletimPrevVariavelMetMockMvc.perform(post("/api/boletim-prev-variavel-mets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boletimPrevVariavelMetDTO)))
            .andExpect(status().isBadRequest());

        List<BoletimPrevVariavelMet> boletimPrevVariavelMetList = boletimPrevVariavelMetRepository.findAll();
        assertThat(boletimPrevVariavelMetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevVariavelMets() throws Exception {
        // Initialize the database
        boletimPrevVariavelMetRepository.saveAndFlush(boletimPrevVariavelMet);

        // Get all the boletimPrevVariavelMetList
        restBoletimPrevVariavelMetMockMvc.perform(get("/api/boletim-prev-variavel-mets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(boletimPrevVariavelMet.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getBoletimPrevVariavelMet() throws Exception {
        // Initialize the database
        boletimPrevVariavelMetRepository.saveAndFlush(boletimPrevVariavelMet);

        // Get the boletimPrevVariavelMet
        restBoletimPrevVariavelMetMockMvc.perform(get("/api/boletim-prev-variavel-mets/{id}", boletimPrevVariavelMet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(boletimPrevVariavelMet.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }


    @Test
    @Transactional
    public void getBoletimPrevVariavelMetsByIdFiltering() throws Exception {
        // Initialize the database
        boletimPrevVariavelMetRepository.saveAndFlush(boletimPrevVariavelMet);

        Long id = boletimPrevVariavelMet.getId();

        defaultBoletimPrevVariavelMetShouldBeFound("id.equals=" + id);
        defaultBoletimPrevVariavelMetShouldNotBeFound("id.notEquals=" + id);

        defaultBoletimPrevVariavelMetShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBoletimPrevVariavelMetShouldNotBeFound("id.greaterThan=" + id);

        defaultBoletimPrevVariavelMetShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBoletimPrevVariavelMetShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllBoletimPrevVariavelMetsByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevVariavelMetRepository.saveAndFlush(boletimPrevVariavelMet);

        // Get all the boletimPrevVariavelMetList where nome equals to DEFAULT_NOME
        defaultBoletimPrevVariavelMetShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the boletimPrevVariavelMetList where nome equals to UPDATED_NOME
        defaultBoletimPrevVariavelMetShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevVariavelMetsByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevVariavelMetRepository.saveAndFlush(boletimPrevVariavelMet);

        // Get all the boletimPrevVariavelMetList where nome not equals to DEFAULT_NOME
        defaultBoletimPrevVariavelMetShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the boletimPrevVariavelMetList where nome not equals to UPDATED_NOME
        defaultBoletimPrevVariavelMetShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevVariavelMetsByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        boletimPrevVariavelMetRepository.saveAndFlush(boletimPrevVariavelMet);

        // Get all the boletimPrevVariavelMetList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultBoletimPrevVariavelMetShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the boletimPrevVariavelMetList where nome equals to UPDATED_NOME
        defaultBoletimPrevVariavelMetShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevVariavelMetsByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        boletimPrevVariavelMetRepository.saveAndFlush(boletimPrevVariavelMet);

        // Get all the boletimPrevVariavelMetList where nome is not null
        defaultBoletimPrevVariavelMetShouldBeFound("nome.specified=true");

        // Get all the boletimPrevVariavelMetList where nome is null
        defaultBoletimPrevVariavelMetShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllBoletimPrevVariavelMetsByNomeContainsSomething() throws Exception {
        // Initialize the database
        boletimPrevVariavelMetRepository.saveAndFlush(boletimPrevVariavelMet);

        // Get all the boletimPrevVariavelMetList where nome contains DEFAULT_NOME
        defaultBoletimPrevVariavelMetShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the boletimPrevVariavelMetList where nome contains UPDATED_NOME
        defaultBoletimPrevVariavelMetShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevVariavelMetsByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        boletimPrevVariavelMetRepository.saveAndFlush(boletimPrevVariavelMet);

        // Get all the boletimPrevVariavelMetList where nome does not contain DEFAULT_NOME
        defaultBoletimPrevVariavelMetShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the boletimPrevVariavelMetList where nome does not contain UPDATED_NOME
        defaultBoletimPrevVariavelMetShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllBoletimPrevVariavelMetsByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevVariavelMetRepository.saveAndFlush(boletimPrevVariavelMet);

        // Get all the boletimPrevVariavelMetList where descricao equals to DEFAULT_DESCRICAO
        defaultBoletimPrevVariavelMetShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the boletimPrevVariavelMetList where descricao equals to UPDATED_DESCRICAO
        defaultBoletimPrevVariavelMetShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevVariavelMetsByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevVariavelMetRepository.saveAndFlush(boletimPrevVariavelMet);

        // Get all the boletimPrevVariavelMetList where descricao not equals to DEFAULT_DESCRICAO
        defaultBoletimPrevVariavelMetShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the boletimPrevVariavelMetList where descricao not equals to UPDATED_DESCRICAO
        defaultBoletimPrevVariavelMetShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevVariavelMetsByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        boletimPrevVariavelMetRepository.saveAndFlush(boletimPrevVariavelMet);

        // Get all the boletimPrevVariavelMetList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultBoletimPrevVariavelMetShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the boletimPrevVariavelMetList where descricao equals to UPDATED_DESCRICAO
        defaultBoletimPrevVariavelMetShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevVariavelMetsByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        boletimPrevVariavelMetRepository.saveAndFlush(boletimPrevVariavelMet);

        // Get all the boletimPrevVariavelMetList where descricao is not null
        defaultBoletimPrevVariavelMetShouldBeFound("descricao.specified=true");

        // Get all the boletimPrevVariavelMetList where descricao is null
        defaultBoletimPrevVariavelMetShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllBoletimPrevVariavelMetsByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        boletimPrevVariavelMetRepository.saveAndFlush(boletimPrevVariavelMet);

        // Get all the boletimPrevVariavelMetList where descricao contains DEFAULT_DESCRICAO
        defaultBoletimPrevVariavelMetShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the boletimPrevVariavelMetList where descricao contains UPDATED_DESCRICAO
        defaultBoletimPrevVariavelMetShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevVariavelMetsByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        boletimPrevVariavelMetRepository.saveAndFlush(boletimPrevVariavelMet);

        // Get all the boletimPrevVariavelMetList where descricao does not contain DEFAULT_DESCRICAO
        defaultBoletimPrevVariavelMetShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the boletimPrevVariavelMetList where descricao does not contain UPDATED_DESCRICAO
        defaultBoletimPrevVariavelMetShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllBoletimPrevVariavelMetsByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevVariavelMetRepository.saveAndFlush(boletimPrevVariavelMet);

        // Get all the boletimPrevVariavelMetList where created equals to DEFAULT_CREATED
        defaultBoletimPrevVariavelMetShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the boletimPrevVariavelMetList where created equals to UPDATED_CREATED
        defaultBoletimPrevVariavelMetShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevVariavelMetsByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevVariavelMetRepository.saveAndFlush(boletimPrevVariavelMet);

        // Get all the boletimPrevVariavelMetList where created not equals to DEFAULT_CREATED
        defaultBoletimPrevVariavelMetShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the boletimPrevVariavelMetList where created not equals to UPDATED_CREATED
        defaultBoletimPrevVariavelMetShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevVariavelMetsByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        boletimPrevVariavelMetRepository.saveAndFlush(boletimPrevVariavelMet);

        // Get all the boletimPrevVariavelMetList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultBoletimPrevVariavelMetShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the boletimPrevVariavelMetList where created equals to UPDATED_CREATED
        defaultBoletimPrevVariavelMetShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevVariavelMetsByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        boletimPrevVariavelMetRepository.saveAndFlush(boletimPrevVariavelMet);

        // Get all the boletimPrevVariavelMetList where created is not null
        defaultBoletimPrevVariavelMetShouldBeFound("created.specified=true");

        // Get all the boletimPrevVariavelMetList where created is null
        defaultBoletimPrevVariavelMetShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllBoletimPrevVariavelMetsByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevVariavelMetRepository.saveAndFlush(boletimPrevVariavelMet);

        // Get all the boletimPrevVariavelMetList where updated equals to DEFAULT_UPDATED
        defaultBoletimPrevVariavelMetShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the boletimPrevVariavelMetList where updated equals to UPDATED_UPDATED
        defaultBoletimPrevVariavelMetShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevVariavelMetsByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        boletimPrevVariavelMetRepository.saveAndFlush(boletimPrevVariavelMet);

        // Get all the boletimPrevVariavelMetList where updated not equals to DEFAULT_UPDATED
        defaultBoletimPrevVariavelMetShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the boletimPrevVariavelMetList where updated not equals to UPDATED_UPDATED
        defaultBoletimPrevVariavelMetShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevVariavelMetsByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        boletimPrevVariavelMetRepository.saveAndFlush(boletimPrevVariavelMet);

        // Get all the boletimPrevVariavelMetList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultBoletimPrevVariavelMetShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the boletimPrevVariavelMetList where updated equals to UPDATED_UPDATED
        defaultBoletimPrevVariavelMetShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevVariavelMetsByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        boletimPrevVariavelMetRepository.saveAndFlush(boletimPrevVariavelMet);

        // Get all the boletimPrevVariavelMetList where updated is not null
        defaultBoletimPrevVariavelMetShouldBeFound("updated.specified=true");

        // Get all the boletimPrevVariavelMetList where updated is null
        defaultBoletimPrevVariavelMetShouldNotBeFound("updated.specified=false");
    }

    @Test
    @Transactional
    public void getAllBoletimPrevVariavelMetsByBoletimPrevisaoIsEqualToSomething() throws Exception {
        // Get already existing entity
        BoletimPrevisao boletimPrevisao = boletimPrevVariavelMet.getBoletimPrevisao();
        boletimPrevVariavelMetRepository.saveAndFlush(boletimPrevVariavelMet);
        Long boletimPrevisaoId = boletimPrevisao.getId();

        // Get all the boletimPrevVariavelMetList where boletimPrevisao equals to boletimPrevisaoId
        defaultBoletimPrevVariavelMetShouldBeFound("boletimPrevisaoId.equals=" + boletimPrevisaoId);

        // Get all the boletimPrevVariavelMetList where boletimPrevisao equals to boletimPrevisaoId + 1
        defaultBoletimPrevVariavelMetShouldNotBeFound("boletimPrevisaoId.equals=" + (boletimPrevisaoId + 1));
    }


    @Test
    @Transactional
    public void getAllBoletimPrevVariavelMetsByVariavelMeteorologicaIsEqualToSomething() throws Exception {
        // Get already existing entity
        VariavelMeteorologica variavelMeteorologica = boletimPrevVariavelMet.getVariavelMeteorologica();
        boletimPrevVariavelMetRepository.saveAndFlush(boletimPrevVariavelMet);
        Long variavelMeteorologicaId = variavelMeteorologica.getId();

        // Get all the boletimPrevVariavelMetList where variavelMeteorologica equals to variavelMeteorologicaId
        defaultBoletimPrevVariavelMetShouldBeFound("variavelMeteorologicaId.equals=" + variavelMeteorologicaId);

        // Get all the boletimPrevVariavelMetList where variavelMeteorologica equals to variavelMeteorologicaId + 1
        defaultBoletimPrevVariavelMetShouldNotBeFound("variavelMeteorologicaId.equals=" + (variavelMeteorologicaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBoletimPrevVariavelMetShouldBeFound(String filter) throws Exception {
        restBoletimPrevVariavelMetMockMvc.perform(get("/api/boletim-prev-variavel-mets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(boletimPrevVariavelMet.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restBoletimPrevVariavelMetMockMvc.perform(get("/api/boletim-prev-variavel-mets/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBoletimPrevVariavelMetShouldNotBeFound(String filter) throws Exception {
        restBoletimPrevVariavelMetMockMvc.perform(get("/api/boletim-prev-variavel-mets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBoletimPrevVariavelMetMockMvc.perform(get("/api/boletim-prev-variavel-mets/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingBoletimPrevVariavelMet() throws Exception {
        // Get the boletimPrevVariavelMet
        restBoletimPrevVariavelMetMockMvc.perform(get("/api/boletim-prev-variavel-mets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBoletimPrevVariavelMet() throws Exception {
        // Initialize the database
        boletimPrevVariavelMetRepository.saveAndFlush(boletimPrevVariavelMet);

        int databaseSizeBeforeUpdate = boletimPrevVariavelMetRepository.findAll().size();

        // Update the boletimPrevVariavelMet
        BoletimPrevVariavelMet updatedBoletimPrevVariavelMet = boletimPrevVariavelMetRepository.findById(boletimPrevVariavelMet.getId()).get();
        // Disconnect from session so that the updates on updatedBoletimPrevVariavelMet are not directly saved in db
        em.detach(updatedBoletimPrevVariavelMet);
        updatedBoletimPrevVariavelMet
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        BoletimPrevVariavelMetDTO boletimPrevVariavelMetDTO = boletimPrevVariavelMetMapper.toDto(updatedBoletimPrevVariavelMet);

        restBoletimPrevVariavelMetMockMvc.perform(put("/api/boletim-prev-variavel-mets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boletimPrevVariavelMetDTO)))
            .andExpect(status().isOk());

        // Validate the BoletimPrevVariavelMet in the database
        List<BoletimPrevVariavelMet> boletimPrevVariavelMetList = boletimPrevVariavelMetRepository.findAll();
        assertThat(boletimPrevVariavelMetList).hasSize(databaseSizeBeforeUpdate);
        BoletimPrevVariavelMet testBoletimPrevVariavelMet = boletimPrevVariavelMetList.get(boletimPrevVariavelMetList.size() - 1);
        assertThat(testBoletimPrevVariavelMet.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testBoletimPrevVariavelMet.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testBoletimPrevVariavelMet.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testBoletimPrevVariavelMet.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingBoletimPrevVariavelMet() throws Exception {
        int databaseSizeBeforeUpdate = boletimPrevVariavelMetRepository.findAll().size();

        // Create the BoletimPrevVariavelMet
        BoletimPrevVariavelMetDTO boletimPrevVariavelMetDTO = boletimPrevVariavelMetMapper.toDto(boletimPrevVariavelMet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBoletimPrevVariavelMetMockMvc.perform(put("/api/boletim-prev-variavel-mets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boletimPrevVariavelMetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BoletimPrevVariavelMet in the database
        List<BoletimPrevVariavelMet> boletimPrevVariavelMetList = boletimPrevVariavelMetRepository.findAll();
        assertThat(boletimPrevVariavelMetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBoletimPrevVariavelMet() throws Exception {
        // Initialize the database
        boletimPrevVariavelMetRepository.saveAndFlush(boletimPrevVariavelMet);

        int databaseSizeBeforeDelete = boletimPrevVariavelMetRepository.findAll().size();

        // Delete the boletimPrevVariavelMet
        restBoletimPrevVariavelMetMockMvc.perform(delete("/api/boletim-prev-variavel-mets/{id}", boletimPrevVariavelMet.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BoletimPrevVariavelMet> boletimPrevVariavelMetList = boletimPrevVariavelMetRepository.findAll();
        assertThat(boletimPrevVariavelMetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
