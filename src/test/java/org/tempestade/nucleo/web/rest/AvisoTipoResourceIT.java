package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.AvisoTipo;
import org.tempestade.nucleo.repository.AvisoTipoRepository;
import org.tempestade.nucleo.service.AvisoTipoService;
import org.tempestade.nucleo.service.dto.AvisoTipoDTO;
import org.tempestade.nucleo.service.mapper.AvisoTipoMapper;
import org.tempestade.nucleo.service.dto.AvisoTipoCriteria;
import org.tempestade.nucleo.service.AvisoTipoQueryService;

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
 * Integration tests for the {@link AvisoTipoResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AvisoTipoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private AvisoTipoRepository avisoTipoRepository;

    @Autowired
    private AvisoTipoMapper avisoTipoMapper;

    @Autowired
    private AvisoTipoService avisoTipoService;

    @Autowired
    private AvisoTipoQueryService avisoTipoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAvisoTipoMockMvc;

    private AvisoTipo avisoTipo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AvisoTipo createEntity(EntityManager em) {
        AvisoTipo avisoTipo = new AvisoTipo()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return avisoTipo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AvisoTipo createUpdatedEntity(EntityManager em) {
        AvisoTipo avisoTipo = new AvisoTipo()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return avisoTipo;
    }

    @BeforeEach
    public void initTest() {
        avisoTipo = createEntity(em);
    }

    @Test
    @Transactional
    public void createAvisoTipo() throws Exception {
        int databaseSizeBeforeCreate = avisoTipoRepository.findAll().size();
        // Create the AvisoTipo
        AvisoTipoDTO avisoTipoDTO = avisoTipoMapper.toDto(avisoTipo);
        restAvisoTipoMockMvc.perform(post("/api/aviso-tipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avisoTipoDTO)))
            .andExpect(status().isCreated());

        // Validate the AvisoTipo in the database
        List<AvisoTipo> avisoTipoList = avisoTipoRepository.findAll();
        assertThat(avisoTipoList).hasSize(databaseSizeBeforeCreate + 1);
        AvisoTipo testAvisoTipo = avisoTipoList.get(avisoTipoList.size() - 1);
        assertThat(testAvisoTipo.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testAvisoTipo.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testAvisoTipo.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testAvisoTipo.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createAvisoTipoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = avisoTipoRepository.findAll().size();

        // Create the AvisoTipo with an existing ID
        avisoTipo.setId(1L);
        AvisoTipoDTO avisoTipoDTO = avisoTipoMapper.toDto(avisoTipo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAvisoTipoMockMvc.perform(post("/api/aviso-tipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avisoTipoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AvisoTipo in the database
        List<AvisoTipo> avisoTipoList = avisoTipoRepository.findAll();
        assertThat(avisoTipoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = avisoTipoRepository.findAll().size();
        // set the field null
        avisoTipo.setNome(null);

        // Create the AvisoTipo, which fails.
        AvisoTipoDTO avisoTipoDTO = avisoTipoMapper.toDto(avisoTipo);


        restAvisoTipoMockMvc.perform(post("/api/aviso-tipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avisoTipoDTO)))
            .andExpect(status().isBadRequest());

        List<AvisoTipo> avisoTipoList = avisoTipoRepository.findAll();
        assertThat(avisoTipoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = avisoTipoRepository.findAll().size();
        // set the field null
        avisoTipo.setDescricao(null);

        // Create the AvisoTipo, which fails.
        AvisoTipoDTO avisoTipoDTO = avisoTipoMapper.toDto(avisoTipo);


        restAvisoTipoMockMvc.perform(post("/api/aviso-tipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avisoTipoDTO)))
            .andExpect(status().isBadRequest());

        List<AvisoTipo> avisoTipoList = avisoTipoRepository.findAll();
        assertThat(avisoTipoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = avisoTipoRepository.findAll().size();
        // set the field null
        avisoTipo.setCreated(null);

        // Create the AvisoTipo, which fails.
        AvisoTipoDTO avisoTipoDTO = avisoTipoMapper.toDto(avisoTipo);


        restAvisoTipoMockMvc.perform(post("/api/aviso-tipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avisoTipoDTO)))
            .andExpect(status().isBadRequest());

        List<AvisoTipo> avisoTipoList = avisoTipoRepository.findAll();
        assertThat(avisoTipoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAvisoTipos() throws Exception {
        // Initialize the database
        avisoTipoRepository.saveAndFlush(avisoTipo);

        // Get all the avisoTipoList
        restAvisoTipoMockMvc.perform(get("/api/aviso-tipos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(avisoTipo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getAvisoTipo() throws Exception {
        // Initialize the database
        avisoTipoRepository.saveAndFlush(avisoTipo);

        // Get the avisoTipo
        restAvisoTipoMockMvc.perform(get("/api/aviso-tipos/{id}", avisoTipo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(avisoTipo.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }


    @Test
    @Transactional
    public void getAvisoTiposByIdFiltering() throws Exception {
        // Initialize the database
        avisoTipoRepository.saveAndFlush(avisoTipo);

        Long id = avisoTipo.getId();

        defaultAvisoTipoShouldBeFound("id.equals=" + id);
        defaultAvisoTipoShouldNotBeFound("id.notEquals=" + id);

        defaultAvisoTipoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAvisoTipoShouldNotBeFound("id.greaterThan=" + id);

        defaultAvisoTipoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAvisoTipoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAvisoTiposByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        avisoTipoRepository.saveAndFlush(avisoTipo);

        // Get all the avisoTipoList where nome equals to DEFAULT_NOME
        defaultAvisoTipoShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the avisoTipoList where nome equals to UPDATED_NOME
        defaultAvisoTipoShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAvisoTiposByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        avisoTipoRepository.saveAndFlush(avisoTipo);

        // Get all the avisoTipoList where nome not equals to DEFAULT_NOME
        defaultAvisoTipoShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the avisoTipoList where nome not equals to UPDATED_NOME
        defaultAvisoTipoShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAvisoTiposByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        avisoTipoRepository.saveAndFlush(avisoTipo);

        // Get all the avisoTipoList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultAvisoTipoShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the avisoTipoList where nome equals to UPDATED_NOME
        defaultAvisoTipoShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAvisoTiposByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        avisoTipoRepository.saveAndFlush(avisoTipo);

        // Get all the avisoTipoList where nome is not null
        defaultAvisoTipoShouldBeFound("nome.specified=true");

        // Get all the avisoTipoList where nome is null
        defaultAvisoTipoShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllAvisoTiposByNomeContainsSomething() throws Exception {
        // Initialize the database
        avisoTipoRepository.saveAndFlush(avisoTipo);

        // Get all the avisoTipoList where nome contains DEFAULT_NOME
        defaultAvisoTipoShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the avisoTipoList where nome contains UPDATED_NOME
        defaultAvisoTipoShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAvisoTiposByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        avisoTipoRepository.saveAndFlush(avisoTipo);

        // Get all the avisoTipoList where nome does not contain DEFAULT_NOME
        defaultAvisoTipoShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the avisoTipoList where nome does not contain UPDATED_NOME
        defaultAvisoTipoShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllAvisoTiposByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        avisoTipoRepository.saveAndFlush(avisoTipo);

        // Get all the avisoTipoList where descricao equals to DEFAULT_DESCRICAO
        defaultAvisoTipoShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the avisoTipoList where descricao equals to UPDATED_DESCRICAO
        defaultAvisoTipoShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllAvisoTiposByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        avisoTipoRepository.saveAndFlush(avisoTipo);

        // Get all the avisoTipoList where descricao not equals to DEFAULT_DESCRICAO
        defaultAvisoTipoShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the avisoTipoList where descricao not equals to UPDATED_DESCRICAO
        defaultAvisoTipoShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllAvisoTiposByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        avisoTipoRepository.saveAndFlush(avisoTipo);

        // Get all the avisoTipoList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultAvisoTipoShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the avisoTipoList where descricao equals to UPDATED_DESCRICAO
        defaultAvisoTipoShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllAvisoTiposByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        avisoTipoRepository.saveAndFlush(avisoTipo);

        // Get all the avisoTipoList where descricao is not null
        defaultAvisoTipoShouldBeFound("descricao.specified=true");

        // Get all the avisoTipoList where descricao is null
        defaultAvisoTipoShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllAvisoTiposByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        avisoTipoRepository.saveAndFlush(avisoTipo);

        // Get all the avisoTipoList where descricao contains DEFAULT_DESCRICAO
        defaultAvisoTipoShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the avisoTipoList where descricao contains UPDATED_DESCRICAO
        defaultAvisoTipoShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllAvisoTiposByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        avisoTipoRepository.saveAndFlush(avisoTipo);

        // Get all the avisoTipoList where descricao does not contain DEFAULT_DESCRICAO
        defaultAvisoTipoShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the avisoTipoList where descricao does not contain UPDATED_DESCRICAO
        defaultAvisoTipoShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllAvisoTiposByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        avisoTipoRepository.saveAndFlush(avisoTipo);

        // Get all the avisoTipoList where created equals to DEFAULT_CREATED
        defaultAvisoTipoShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the avisoTipoList where created equals to UPDATED_CREATED
        defaultAvisoTipoShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllAvisoTiposByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        avisoTipoRepository.saveAndFlush(avisoTipo);

        // Get all the avisoTipoList where created not equals to DEFAULT_CREATED
        defaultAvisoTipoShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the avisoTipoList where created not equals to UPDATED_CREATED
        defaultAvisoTipoShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllAvisoTiposByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        avisoTipoRepository.saveAndFlush(avisoTipo);

        // Get all the avisoTipoList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultAvisoTipoShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the avisoTipoList where created equals to UPDATED_CREATED
        defaultAvisoTipoShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllAvisoTiposByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        avisoTipoRepository.saveAndFlush(avisoTipo);

        // Get all the avisoTipoList where created is not null
        defaultAvisoTipoShouldBeFound("created.specified=true");

        // Get all the avisoTipoList where created is null
        defaultAvisoTipoShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllAvisoTiposByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        avisoTipoRepository.saveAndFlush(avisoTipo);

        // Get all the avisoTipoList where updated equals to DEFAULT_UPDATED
        defaultAvisoTipoShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the avisoTipoList where updated equals to UPDATED_UPDATED
        defaultAvisoTipoShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllAvisoTiposByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        avisoTipoRepository.saveAndFlush(avisoTipo);

        // Get all the avisoTipoList where updated not equals to DEFAULT_UPDATED
        defaultAvisoTipoShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the avisoTipoList where updated not equals to UPDATED_UPDATED
        defaultAvisoTipoShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllAvisoTiposByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        avisoTipoRepository.saveAndFlush(avisoTipo);

        // Get all the avisoTipoList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultAvisoTipoShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the avisoTipoList where updated equals to UPDATED_UPDATED
        defaultAvisoTipoShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllAvisoTiposByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        avisoTipoRepository.saveAndFlush(avisoTipo);

        // Get all the avisoTipoList where updated is not null
        defaultAvisoTipoShouldBeFound("updated.specified=true");

        // Get all the avisoTipoList where updated is null
        defaultAvisoTipoShouldNotBeFound("updated.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAvisoTipoShouldBeFound(String filter) throws Exception {
        restAvisoTipoMockMvc.perform(get("/api/aviso-tipos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(avisoTipo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restAvisoTipoMockMvc.perform(get("/api/aviso-tipos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAvisoTipoShouldNotBeFound(String filter) throws Exception {
        restAvisoTipoMockMvc.perform(get("/api/aviso-tipos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAvisoTipoMockMvc.perform(get("/api/aviso-tipos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingAvisoTipo() throws Exception {
        // Get the avisoTipo
        restAvisoTipoMockMvc.perform(get("/api/aviso-tipos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAvisoTipo() throws Exception {
        // Initialize the database
        avisoTipoRepository.saveAndFlush(avisoTipo);

        int databaseSizeBeforeUpdate = avisoTipoRepository.findAll().size();

        // Update the avisoTipo
        AvisoTipo updatedAvisoTipo = avisoTipoRepository.findById(avisoTipo.getId()).get();
        // Disconnect from session so that the updates on updatedAvisoTipo are not directly saved in db
        em.detach(updatedAvisoTipo);
        updatedAvisoTipo
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        AvisoTipoDTO avisoTipoDTO = avisoTipoMapper.toDto(updatedAvisoTipo);

        restAvisoTipoMockMvc.perform(put("/api/aviso-tipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avisoTipoDTO)))
            .andExpect(status().isOk());

        // Validate the AvisoTipo in the database
        List<AvisoTipo> avisoTipoList = avisoTipoRepository.findAll();
        assertThat(avisoTipoList).hasSize(databaseSizeBeforeUpdate);
        AvisoTipo testAvisoTipo = avisoTipoList.get(avisoTipoList.size() - 1);
        assertThat(testAvisoTipo.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testAvisoTipo.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testAvisoTipo.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testAvisoTipo.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingAvisoTipo() throws Exception {
        int databaseSizeBeforeUpdate = avisoTipoRepository.findAll().size();

        // Create the AvisoTipo
        AvisoTipoDTO avisoTipoDTO = avisoTipoMapper.toDto(avisoTipo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAvisoTipoMockMvc.perform(put("/api/aviso-tipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avisoTipoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AvisoTipo in the database
        List<AvisoTipo> avisoTipoList = avisoTipoRepository.findAll();
        assertThat(avisoTipoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAvisoTipo() throws Exception {
        // Initialize the database
        avisoTipoRepository.saveAndFlush(avisoTipo);

        int databaseSizeBeforeDelete = avisoTipoRepository.findAll().size();

        // Delete the avisoTipo
        restAvisoTipoMockMvc.perform(delete("/api/aviso-tipos/{id}", avisoTipo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AvisoTipo> avisoTipoList = avisoTipoRepository.findAll();
        assertThat(avisoTipoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
