package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.TipoFerramenta;
import org.tempestade.nucleo.repository.TipoFerramentaRepository;
import org.tempestade.nucleo.service.TipoFerramentaService;
import org.tempestade.nucleo.service.dto.TipoFerramentaDTO;
import org.tempestade.nucleo.service.mapper.TipoFerramentaMapper;
import org.tempestade.nucleo.service.dto.TipoFerramentaCriteria;
import org.tempestade.nucleo.service.TipoFerramentaQueryService;

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
 * Integration tests for the {@link TipoFerramentaResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TipoFerramentaResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private TipoFerramentaRepository tipoFerramentaRepository;

    @Autowired
    private TipoFerramentaMapper tipoFerramentaMapper;

    @Autowired
    private TipoFerramentaService tipoFerramentaService;

    @Autowired
    private TipoFerramentaQueryService tipoFerramentaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoFerramentaMockMvc;

    private TipoFerramenta tipoFerramenta;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoFerramenta createEntity(EntityManager em) {
        TipoFerramenta tipoFerramenta = new TipoFerramenta()
            .name(DEFAULT_NAME)
            .descricao(DEFAULT_DESCRICAO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return tipoFerramenta;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoFerramenta createUpdatedEntity(EntityManager em) {
        TipoFerramenta tipoFerramenta = new TipoFerramenta()
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return tipoFerramenta;
    }

    @BeforeEach
    public void initTest() {
        tipoFerramenta = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoFerramenta() throws Exception {
        int databaseSizeBeforeCreate = tipoFerramentaRepository.findAll().size();
        // Create the TipoFerramenta
        TipoFerramentaDTO tipoFerramentaDTO = tipoFerramentaMapper.toDto(tipoFerramenta);
        restTipoFerramentaMockMvc.perform(post("/api/tipo-ferramentas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoFerramentaDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoFerramenta in the database
        List<TipoFerramenta> tipoFerramentaList = tipoFerramentaRepository.findAll();
        assertThat(tipoFerramentaList).hasSize(databaseSizeBeforeCreate + 1);
        TipoFerramenta testTipoFerramenta = tipoFerramentaList.get(tipoFerramentaList.size() - 1);
        assertThat(testTipoFerramenta.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTipoFerramenta.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testTipoFerramenta.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testTipoFerramenta.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createTipoFerramentaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoFerramentaRepository.findAll().size();

        // Create the TipoFerramenta with an existing ID
        tipoFerramenta.setId(1L);
        TipoFerramentaDTO tipoFerramentaDTO = tipoFerramentaMapper.toDto(tipoFerramenta);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoFerramentaMockMvc.perform(post("/api/tipo-ferramentas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoFerramentaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoFerramenta in the database
        List<TipoFerramenta> tipoFerramentaList = tipoFerramentaRepository.findAll();
        assertThat(tipoFerramentaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoFerramentaRepository.findAll().size();
        // set the field null
        tipoFerramenta.setName(null);

        // Create the TipoFerramenta, which fails.
        TipoFerramentaDTO tipoFerramentaDTO = tipoFerramentaMapper.toDto(tipoFerramenta);


        restTipoFerramentaMockMvc.perform(post("/api/tipo-ferramentas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoFerramentaDTO)))
            .andExpect(status().isBadRequest());

        List<TipoFerramenta> tipoFerramentaList = tipoFerramentaRepository.findAll();
        assertThat(tipoFerramentaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoFerramentaRepository.findAll().size();
        // set the field null
        tipoFerramenta.setDescricao(null);

        // Create the TipoFerramenta, which fails.
        TipoFerramentaDTO tipoFerramentaDTO = tipoFerramentaMapper.toDto(tipoFerramenta);


        restTipoFerramentaMockMvc.perform(post("/api/tipo-ferramentas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoFerramentaDTO)))
            .andExpect(status().isBadRequest());

        List<TipoFerramenta> tipoFerramentaList = tipoFerramentaRepository.findAll();
        assertThat(tipoFerramentaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoFerramentaRepository.findAll().size();
        // set the field null
        tipoFerramenta.setCreated(null);

        // Create the TipoFerramenta, which fails.
        TipoFerramentaDTO tipoFerramentaDTO = tipoFerramentaMapper.toDto(tipoFerramenta);


        restTipoFerramentaMockMvc.perform(post("/api/tipo-ferramentas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoFerramentaDTO)))
            .andExpect(status().isBadRequest());

        List<TipoFerramenta> tipoFerramentaList = tipoFerramentaRepository.findAll();
        assertThat(tipoFerramentaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoFerramentas() throws Exception {
        // Initialize the database
        tipoFerramentaRepository.saveAndFlush(tipoFerramenta);

        // Get all the tipoFerramentaList
        restTipoFerramentaMockMvc.perform(get("/api/tipo-ferramentas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoFerramenta.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getTipoFerramenta() throws Exception {
        // Initialize the database
        tipoFerramentaRepository.saveAndFlush(tipoFerramenta);

        // Get the tipoFerramenta
        restTipoFerramentaMockMvc.perform(get("/api/tipo-ferramentas/{id}", tipoFerramenta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoFerramenta.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }


    @Test
    @Transactional
    public void getTipoFerramentasByIdFiltering() throws Exception {
        // Initialize the database
        tipoFerramentaRepository.saveAndFlush(tipoFerramenta);

        Long id = tipoFerramenta.getId();

        defaultTipoFerramentaShouldBeFound("id.equals=" + id);
        defaultTipoFerramentaShouldNotBeFound("id.notEquals=" + id);

        defaultTipoFerramentaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTipoFerramentaShouldNotBeFound("id.greaterThan=" + id);

        defaultTipoFerramentaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTipoFerramentaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTipoFerramentasByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        tipoFerramentaRepository.saveAndFlush(tipoFerramenta);

        // Get all the tipoFerramentaList where name equals to DEFAULT_NAME
        defaultTipoFerramentaShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the tipoFerramentaList where name equals to UPDATED_NAME
        defaultTipoFerramentaShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTipoFerramentasByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tipoFerramentaRepository.saveAndFlush(tipoFerramenta);

        // Get all the tipoFerramentaList where name not equals to DEFAULT_NAME
        defaultTipoFerramentaShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the tipoFerramentaList where name not equals to UPDATED_NAME
        defaultTipoFerramentaShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTipoFerramentasByNameIsInShouldWork() throws Exception {
        // Initialize the database
        tipoFerramentaRepository.saveAndFlush(tipoFerramenta);

        // Get all the tipoFerramentaList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTipoFerramentaShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the tipoFerramentaList where name equals to UPDATED_NAME
        defaultTipoFerramentaShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTipoFerramentasByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipoFerramentaRepository.saveAndFlush(tipoFerramenta);

        // Get all the tipoFerramentaList where name is not null
        defaultTipoFerramentaShouldBeFound("name.specified=true");

        // Get all the tipoFerramentaList where name is null
        defaultTipoFerramentaShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllTipoFerramentasByNameContainsSomething() throws Exception {
        // Initialize the database
        tipoFerramentaRepository.saveAndFlush(tipoFerramenta);

        // Get all the tipoFerramentaList where name contains DEFAULT_NAME
        defaultTipoFerramentaShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the tipoFerramentaList where name contains UPDATED_NAME
        defaultTipoFerramentaShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTipoFerramentasByNameNotContainsSomething() throws Exception {
        // Initialize the database
        tipoFerramentaRepository.saveAndFlush(tipoFerramenta);

        // Get all the tipoFerramentaList where name does not contain DEFAULT_NAME
        defaultTipoFerramentaShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the tipoFerramentaList where name does not contain UPDATED_NAME
        defaultTipoFerramentaShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllTipoFerramentasByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        tipoFerramentaRepository.saveAndFlush(tipoFerramenta);

        // Get all the tipoFerramentaList where descricao equals to DEFAULT_DESCRICAO
        defaultTipoFerramentaShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the tipoFerramentaList where descricao equals to UPDATED_DESCRICAO
        defaultTipoFerramentaShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllTipoFerramentasByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tipoFerramentaRepository.saveAndFlush(tipoFerramenta);

        // Get all the tipoFerramentaList where descricao not equals to DEFAULT_DESCRICAO
        defaultTipoFerramentaShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the tipoFerramentaList where descricao not equals to UPDATED_DESCRICAO
        defaultTipoFerramentaShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllTipoFerramentasByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        tipoFerramentaRepository.saveAndFlush(tipoFerramenta);

        // Get all the tipoFerramentaList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultTipoFerramentaShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the tipoFerramentaList where descricao equals to UPDATED_DESCRICAO
        defaultTipoFerramentaShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllTipoFerramentasByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipoFerramentaRepository.saveAndFlush(tipoFerramenta);

        // Get all the tipoFerramentaList where descricao is not null
        defaultTipoFerramentaShouldBeFound("descricao.specified=true");

        // Get all the tipoFerramentaList where descricao is null
        defaultTipoFerramentaShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllTipoFerramentasByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        tipoFerramentaRepository.saveAndFlush(tipoFerramenta);

        // Get all the tipoFerramentaList where descricao contains DEFAULT_DESCRICAO
        defaultTipoFerramentaShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the tipoFerramentaList where descricao contains UPDATED_DESCRICAO
        defaultTipoFerramentaShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllTipoFerramentasByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        tipoFerramentaRepository.saveAndFlush(tipoFerramenta);

        // Get all the tipoFerramentaList where descricao does not contain DEFAULT_DESCRICAO
        defaultTipoFerramentaShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the tipoFerramentaList where descricao does not contain UPDATED_DESCRICAO
        defaultTipoFerramentaShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllTipoFerramentasByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        tipoFerramentaRepository.saveAndFlush(tipoFerramenta);

        // Get all the tipoFerramentaList where created equals to DEFAULT_CREATED
        defaultTipoFerramentaShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the tipoFerramentaList where created equals to UPDATED_CREATED
        defaultTipoFerramentaShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllTipoFerramentasByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tipoFerramentaRepository.saveAndFlush(tipoFerramenta);

        // Get all the tipoFerramentaList where created not equals to DEFAULT_CREATED
        defaultTipoFerramentaShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the tipoFerramentaList where created not equals to UPDATED_CREATED
        defaultTipoFerramentaShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllTipoFerramentasByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        tipoFerramentaRepository.saveAndFlush(tipoFerramenta);

        // Get all the tipoFerramentaList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultTipoFerramentaShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the tipoFerramentaList where created equals to UPDATED_CREATED
        defaultTipoFerramentaShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllTipoFerramentasByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipoFerramentaRepository.saveAndFlush(tipoFerramenta);

        // Get all the tipoFerramentaList where created is not null
        defaultTipoFerramentaShouldBeFound("created.specified=true");

        // Get all the tipoFerramentaList where created is null
        defaultTipoFerramentaShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllTipoFerramentasByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        tipoFerramentaRepository.saveAndFlush(tipoFerramenta);

        // Get all the tipoFerramentaList where updated equals to DEFAULT_UPDATED
        defaultTipoFerramentaShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the tipoFerramentaList where updated equals to UPDATED_UPDATED
        defaultTipoFerramentaShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllTipoFerramentasByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tipoFerramentaRepository.saveAndFlush(tipoFerramenta);

        // Get all the tipoFerramentaList where updated not equals to DEFAULT_UPDATED
        defaultTipoFerramentaShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the tipoFerramentaList where updated not equals to UPDATED_UPDATED
        defaultTipoFerramentaShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllTipoFerramentasByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        tipoFerramentaRepository.saveAndFlush(tipoFerramenta);

        // Get all the tipoFerramentaList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultTipoFerramentaShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the tipoFerramentaList where updated equals to UPDATED_UPDATED
        defaultTipoFerramentaShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllTipoFerramentasByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipoFerramentaRepository.saveAndFlush(tipoFerramenta);

        // Get all the tipoFerramentaList where updated is not null
        defaultTipoFerramentaShouldBeFound("updated.specified=true");

        // Get all the tipoFerramentaList where updated is null
        defaultTipoFerramentaShouldNotBeFound("updated.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTipoFerramentaShouldBeFound(String filter) throws Exception {
        restTipoFerramentaMockMvc.perform(get("/api/tipo-ferramentas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoFerramenta.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restTipoFerramentaMockMvc.perform(get("/api/tipo-ferramentas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTipoFerramentaShouldNotBeFound(String filter) throws Exception {
        restTipoFerramentaMockMvc.perform(get("/api/tipo-ferramentas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTipoFerramentaMockMvc.perform(get("/api/tipo-ferramentas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTipoFerramenta() throws Exception {
        // Get the tipoFerramenta
        restTipoFerramentaMockMvc.perform(get("/api/tipo-ferramentas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoFerramenta() throws Exception {
        // Initialize the database
        tipoFerramentaRepository.saveAndFlush(tipoFerramenta);

        int databaseSizeBeforeUpdate = tipoFerramentaRepository.findAll().size();

        // Update the tipoFerramenta
        TipoFerramenta updatedTipoFerramenta = tipoFerramentaRepository.findById(tipoFerramenta.getId()).get();
        // Disconnect from session so that the updates on updatedTipoFerramenta are not directly saved in db
        em.detach(updatedTipoFerramenta);
        updatedTipoFerramenta
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        TipoFerramentaDTO tipoFerramentaDTO = tipoFerramentaMapper.toDto(updatedTipoFerramenta);

        restTipoFerramentaMockMvc.perform(put("/api/tipo-ferramentas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoFerramentaDTO)))
            .andExpect(status().isOk());

        // Validate the TipoFerramenta in the database
        List<TipoFerramenta> tipoFerramentaList = tipoFerramentaRepository.findAll();
        assertThat(tipoFerramentaList).hasSize(databaseSizeBeforeUpdate);
        TipoFerramenta testTipoFerramenta = tipoFerramentaList.get(tipoFerramentaList.size() - 1);
        assertThat(testTipoFerramenta.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTipoFerramenta.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testTipoFerramenta.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testTipoFerramenta.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoFerramenta() throws Exception {
        int databaseSizeBeforeUpdate = tipoFerramentaRepository.findAll().size();

        // Create the TipoFerramenta
        TipoFerramentaDTO tipoFerramentaDTO = tipoFerramentaMapper.toDto(tipoFerramenta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoFerramentaMockMvc.perform(put("/api/tipo-ferramentas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoFerramentaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoFerramenta in the database
        List<TipoFerramenta> tipoFerramentaList = tipoFerramentaRepository.findAll();
        assertThat(tipoFerramentaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoFerramenta() throws Exception {
        // Initialize the database
        tipoFerramentaRepository.saveAndFlush(tipoFerramenta);

        int databaseSizeBeforeDelete = tipoFerramentaRepository.findAll().size();

        // Delete the tipoFerramenta
        restTipoFerramentaMockMvc.perform(delete("/api/tipo-ferramentas/{id}", tipoFerramenta.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoFerramenta> tipoFerramentaList = tipoFerramentaRepository.findAll();
        assertThat(tipoFerramentaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
