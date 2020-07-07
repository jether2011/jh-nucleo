package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.AlvoBloqueio;
import org.tempestade.nucleo.domain.Alvo;
import org.tempestade.nucleo.repository.AlvoBloqueioRepository;
import org.tempestade.nucleo.service.AlvoBloqueioService;
import org.tempestade.nucleo.service.dto.AlvoBloqueioDTO;
import org.tempestade.nucleo.service.mapper.AlvoBloqueioMapper;
import org.tempestade.nucleo.service.dto.AlvoBloqueioCriteria;
import org.tempestade.nucleo.service.AlvoBloqueioQueryService;

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
 * Integration tests for the {@link AlvoBloqueioResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AlvoBloqueioResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private AlvoBloqueioRepository alvoBloqueioRepository;

    @Autowired
    private AlvoBloqueioMapper alvoBloqueioMapper;

    @Autowired
    private AlvoBloqueioService alvoBloqueioService;

    @Autowired
    private AlvoBloqueioQueryService alvoBloqueioQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAlvoBloqueioMockMvc;

    private AlvoBloqueio alvoBloqueio;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlvoBloqueio createEntity(EntityManager em) {
        AlvoBloqueio alvoBloqueio = new AlvoBloqueio()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return alvoBloqueio;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlvoBloqueio createUpdatedEntity(EntityManager em) {
        AlvoBloqueio alvoBloqueio = new AlvoBloqueio()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return alvoBloqueio;
    }

    @BeforeEach
    public void initTest() {
        alvoBloqueio = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlvoBloqueio() throws Exception {
        int databaseSizeBeforeCreate = alvoBloqueioRepository.findAll().size();
        // Create the AlvoBloqueio
        AlvoBloqueioDTO alvoBloqueioDTO = alvoBloqueioMapper.toDto(alvoBloqueio);
        restAlvoBloqueioMockMvc.perform(post("/api/alvo-bloqueios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alvoBloqueioDTO)))
            .andExpect(status().isCreated());

        // Validate the AlvoBloqueio in the database
        List<AlvoBloqueio> alvoBloqueioList = alvoBloqueioRepository.findAll();
        assertThat(alvoBloqueioList).hasSize(databaseSizeBeforeCreate + 1);
        AlvoBloqueio testAlvoBloqueio = alvoBloqueioList.get(alvoBloqueioList.size() - 1);
        assertThat(testAlvoBloqueio.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testAlvoBloqueio.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testAlvoBloqueio.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testAlvoBloqueio.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createAlvoBloqueioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alvoBloqueioRepository.findAll().size();

        // Create the AlvoBloqueio with an existing ID
        alvoBloqueio.setId(1L);
        AlvoBloqueioDTO alvoBloqueioDTO = alvoBloqueioMapper.toDto(alvoBloqueio);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlvoBloqueioMockMvc.perform(post("/api/alvo-bloqueios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alvoBloqueioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AlvoBloqueio in the database
        List<AlvoBloqueio> alvoBloqueioList = alvoBloqueioRepository.findAll();
        assertThat(alvoBloqueioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = alvoBloqueioRepository.findAll().size();
        // set the field null
        alvoBloqueio.setNome(null);

        // Create the AlvoBloqueio, which fails.
        AlvoBloqueioDTO alvoBloqueioDTO = alvoBloqueioMapper.toDto(alvoBloqueio);


        restAlvoBloqueioMockMvc.perform(post("/api/alvo-bloqueios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alvoBloqueioDTO)))
            .andExpect(status().isBadRequest());

        List<AlvoBloqueio> alvoBloqueioList = alvoBloqueioRepository.findAll();
        assertThat(alvoBloqueioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = alvoBloqueioRepository.findAll().size();
        // set the field null
        alvoBloqueio.setCreated(null);

        // Create the AlvoBloqueio, which fails.
        AlvoBloqueioDTO alvoBloqueioDTO = alvoBloqueioMapper.toDto(alvoBloqueio);


        restAlvoBloqueioMockMvc.perform(post("/api/alvo-bloqueios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alvoBloqueioDTO)))
            .andExpect(status().isBadRequest());

        List<AlvoBloqueio> alvoBloqueioList = alvoBloqueioRepository.findAll();
        assertThat(alvoBloqueioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAlvoBloqueios() throws Exception {
        // Initialize the database
        alvoBloqueioRepository.saveAndFlush(alvoBloqueio);

        // Get all the alvoBloqueioList
        restAlvoBloqueioMockMvc.perform(get("/api/alvo-bloqueios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alvoBloqueio.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getAlvoBloqueio() throws Exception {
        // Initialize the database
        alvoBloqueioRepository.saveAndFlush(alvoBloqueio);

        // Get the alvoBloqueio
        restAlvoBloqueioMockMvc.perform(get("/api/alvo-bloqueios/{id}", alvoBloqueio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(alvoBloqueio.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }


    @Test
    @Transactional
    public void getAlvoBloqueiosByIdFiltering() throws Exception {
        // Initialize the database
        alvoBloqueioRepository.saveAndFlush(alvoBloqueio);

        Long id = alvoBloqueio.getId();

        defaultAlvoBloqueioShouldBeFound("id.equals=" + id);
        defaultAlvoBloqueioShouldNotBeFound("id.notEquals=" + id);

        defaultAlvoBloqueioShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAlvoBloqueioShouldNotBeFound("id.greaterThan=" + id);

        defaultAlvoBloqueioShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAlvoBloqueioShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAlvoBloqueiosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        alvoBloqueioRepository.saveAndFlush(alvoBloqueio);

        // Get all the alvoBloqueioList where nome equals to DEFAULT_NOME
        defaultAlvoBloqueioShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the alvoBloqueioList where nome equals to UPDATED_NOME
        defaultAlvoBloqueioShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAlvoBloqueiosByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alvoBloqueioRepository.saveAndFlush(alvoBloqueio);

        // Get all the alvoBloqueioList where nome not equals to DEFAULT_NOME
        defaultAlvoBloqueioShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the alvoBloqueioList where nome not equals to UPDATED_NOME
        defaultAlvoBloqueioShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAlvoBloqueiosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        alvoBloqueioRepository.saveAndFlush(alvoBloqueio);

        // Get all the alvoBloqueioList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultAlvoBloqueioShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the alvoBloqueioList where nome equals to UPDATED_NOME
        defaultAlvoBloqueioShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAlvoBloqueiosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        alvoBloqueioRepository.saveAndFlush(alvoBloqueio);

        // Get all the alvoBloqueioList where nome is not null
        defaultAlvoBloqueioShouldBeFound("nome.specified=true");

        // Get all the alvoBloqueioList where nome is null
        defaultAlvoBloqueioShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlvoBloqueiosByNomeContainsSomething() throws Exception {
        // Initialize the database
        alvoBloqueioRepository.saveAndFlush(alvoBloqueio);

        // Get all the alvoBloqueioList where nome contains DEFAULT_NOME
        defaultAlvoBloqueioShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the alvoBloqueioList where nome contains UPDATED_NOME
        defaultAlvoBloqueioShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAlvoBloqueiosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        alvoBloqueioRepository.saveAndFlush(alvoBloqueio);

        // Get all the alvoBloqueioList where nome does not contain DEFAULT_NOME
        defaultAlvoBloqueioShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the alvoBloqueioList where nome does not contain UPDATED_NOME
        defaultAlvoBloqueioShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllAlvoBloqueiosByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        alvoBloqueioRepository.saveAndFlush(alvoBloqueio);

        // Get all the alvoBloqueioList where descricao equals to DEFAULT_DESCRICAO
        defaultAlvoBloqueioShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the alvoBloqueioList where descricao equals to UPDATED_DESCRICAO
        defaultAlvoBloqueioShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllAlvoBloqueiosByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alvoBloqueioRepository.saveAndFlush(alvoBloqueio);

        // Get all the alvoBloqueioList where descricao not equals to DEFAULT_DESCRICAO
        defaultAlvoBloqueioShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the alvoBloqueioList where descricao not equals to UPDATED_DESCRICAO
        defaultAlvoBloqueioShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllAlvoBloqueiosByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        alvoBloqueioRepository.saveAndFlush(alvoBloqueio);

        // Get all the alvoBloqueioList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultAlvoBloqueioShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the alvoBloqueioList where descricao equals to UPDATED_DESCRICAO
        defaultAlvoBloqueioShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllAlvoBloqueiosByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        alvoBloqueioRepository.saveAndFlush(alvoBloqueio);

        // Get all the alvoBloqueioList where descricao is not null
        defaultAlvoBloqueioShouldBeFound("descricao.specified=true");

        // Get all the alvoBloqueioList where descricao is null
        defaultAlvoBloqueioShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlvoBloqueiosByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        alvoBloqueioRepository.saveAndFlush(alvoBloqueio);

        // Get all the alvoBloqueioList where descricao contains DEFAULT_DESCRICAO
        defaultAlvoBloqueioShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the alvoBloqueioList where descricao contains UPDATED_DESCRICAO
        defaultAlvoBloqueioShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllAlvoBloqueiosByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        alvoBloqueioRepository.saveAndFlush(alvoBloqueio);

        // Get all the alvoBloqueioList where descricao does not contain DEFAULT_DESCRICAO
        defaultAlvoBloqueioShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the alvoBloqueioList where descricao does not contain UPDATED_DESCRICAO
        defaultAlvoBloqueioShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllAlvoBloqueiosByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        alvoBloqueioRepository.saveAndFlush(alvoBloqueio);

        // Get all the alvoBloqueioList where created equals to DEFAULT_CREATED
        defaultAlvoBloqueioShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the alvoBloqueioList where created equals to UPDATED_CREATED
        defaultAlvoBloqueioShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllAlvoBloqueiosByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alvoBloqueioRepository.saveAndFlush(alvoBloqueio);

        // Get all the alvoBloqueioList where created not equals to DEFAULT_CREATED
        defaultAlvoBloqueioShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the alvoBloqueioList where created not equals to UPDATED_CREATED
        defaultAlvoBloqueioShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllAlvoBloqueiosByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        alvoBloqueioRepository.saveAndFlush(alvoBloqueio);

        // Get all the alvoBloqueioList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultAlvoBloqueioShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the alvoBloqueioList where created equals to UPDATED_CREATED
        defaultAlvoBloqueioShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllAlvoBloqueiosByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        alvoBloqueioRepository.saveAndFlush(alvoBloqueio);

        // Get all the alvoBloqueioList where created is not null
        defaultAlvoBloqueioShouldBeFound("created.specified=true");

        // Get all the alvoBloqueioList where created is null
        defaultAlvoBloqueioShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllAlvoBloqueiosByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        alvoBloqueioRepository.saveAndFlush(alvoBloqueio);

        // Get all the alvoBloqueioList where updated equals to DEFAULT_UPDATED
        defaultAlvoBloqueioShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the alvoBloqueioList where updated equals to UPDATED_UPDATED
        defaultAlvoBloqueioShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllAlvoBloqueiosByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alvoBloqueioRepository.saveAndFlush(alvoBloqueio);

        // Get all the alvoBloqueioList where updated not equals to DEFAULT_UPDATED
        defaultAlvoBloqueioShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the alvoBloqueioList where updated not equals to UPDATED_UPDATED
        defaultAlvoBloqueioShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllAlvoBloqueiosByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        alvoBloqueioRepository.saveAndFlush(alvoBloqueio);

        // Get all the alvoBloqueioList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultAlvoBloqueioShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the alvoBloqueioList where updated equals to UPDATED_UPDATED
        defaultAlvoBloqueioShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllAlvoBloqueiosByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        alvoBloqueioRepository.saveAndFlush(alvoBloqueio);

        // Get all the alvoBloqueioList where updated is not null
        defaultAlvoBloqueioShouldBeFound("updated.specified=true");

        // Get all the alvoBloqueioList where updated is null
        defaultAlvoBloqueioShouldNotBeFound("updated.specified=false");
    }

    @Test
    @Transactional
    public void getAllAlvoBloqueiosByAlvoIsEqualToSomething() throws Exception {
        // Initialize the database
        alvoBloqueioRepository.saveAndFlush(alvoBloqueio);
        Alvo alvo = AlvoResourceIT.createEntity(em);
        em.persist(alvo);
        em.flush();
        alvoBloqueio.setAlvo(alvo);
        alvoBloqueioRepository.saveAndFlush(alvoBloqueio);
        Long alvoId = alvo.getId();

        // Get all the alvoBloqueioList where alvo equals to alvoId
        defaultAlvoBloqueioShouldBeFound("alvoId.equals=" + alvoId);

        // Get all the alvoBloqueioList where alvo equals to alvoId + 1
        defaultAlvoBloqueioShouldNotBeFound("alvoId.equals=" + (alvoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAlvoBloqueioShouldBeFound(String filter) throws Exception {
        restAlvoBloqueioMockMvc.perform(get("/api/alvo-bloqueios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alvoBloqueio.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restAlvoBloqueioMockMvc.perform(get("/api/alvo-bloqueios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAlvoBloqueioShouldNotBeFound(String filter) throws Exception {
        restAlvoBloqueioMockMvc.perform(get("/api/alvo-bloqueios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAlvoBloqueioMockMvc.perform(get("/api/alvo-bloqueios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingAlvoBloqueio() throws Exception {
        // Get the alvoBloqueio
        restAlvoBloqueioMockMvc.perform(get("/api/alvo-bloqueios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlvoBloqueio() throws Exception {
        // Initialize the database
        alvoBloqueioRepository.saveAndFlush(alvoBloqueio);

        int databaseSizeBeforeUpdate = alvoBloqueioRepository.findAll().size();

        // Update the alvoBloqueio
        AlvoBloqueio updatedAlvoBloqueio = alvoBloqueioRepository.findById(alvoBloqueio.getId()).get();
        // Disconnect from session so that the updates on updatedAlvoBloqueio are not directly saved in db
        em.detach(updatedAlvoBloqueio);
        updatedAlvoBloqueio
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        AlvoBloqueioDTO alvoBloqueioDTO = alvoBloqueioMapper.toDto(updatedAlvoBloqueio);

        restAlvoBloqueioMockMvc.perform(put("/api/alvo-bloqueios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alvoBloqueioDTO)))
            .andExpect(status().isOk());

        // Validate the AlvoBloqueio in the database
        List<AlvoBloqueio> alvoBloqueioList = alvoBloqueioRepository.findAll();
        assertThat(alvoBloqueioList).hasSize(databaseSizeBeforeUpdate);
        AlvoBloqueio testAlvoBloqueio = alvoBloqueioList.get(alvoBloqueioList.size() - 1);
        assertThat(testAlvoBloqueio.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testAlvoBloqueio.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testAlvoBloqueio.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testAlvoBloqueio.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingAlvoBloqueio() throws Exception {
        int databaseSizeBeforeUpdate = alvoBloqueioRepository.findAll().size();

        // Create the AlvoBloqueio
        AlvoBloqueioDTO alvoBloqueioDTO = alvoBloqueioMapper.toDto(alvoBloqueio);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlvoBloqueioMockMvc.perform(put("/api/alvo-bloqueios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alvoBloqueioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AlvoBloqueio in the database
        List<AlvoBloqueio> alvoBloqueioList = alvoBloqueioRepository.findAll();
        assertThat(alvoBloqueioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAlvoBloqueio() throws Exception {
        // Initialize the database
        alvoBloqueioRepository.saveAndFlush(alvoBloqueio);

        int databaseSizeBeforeDelete = alvoBloqueioRepository.findAll().size();

        // Delete the alvoBloqueio
        restAlvoBloqueioMockMvc.perform(delete("/api/alvo-bloqueios/{id}", alvoBloqueio.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AlvoBloqueio> alvoBloqueioList = alvoBloqueioRepository.findAll();
        assertThat(alvoBloqueioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
