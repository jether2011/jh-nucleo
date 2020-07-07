package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.AlertaRisco;
import org.tempestade.nucleo.repository.AlertaRiscoRepository;
import org.tempestade.nucleo.service.AlertaRiscoService;
import org.tempestade.nucleo.service.dto.AlertaRiscoDTO;
import org.tempestade.nucleo.service.mapper.AlertaRiscoMapper;
import org.tempestade.nucleo.service.dto.AlertaRiscoCriteria;
import org.tempestade.nucleo.service.AlertaRiscoQueryService;

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
 * Integration tests for the {@link AlertaRiscoResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AlertaRiscoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private AlertaRiscoRepository alertaRiscoRepository;

    @Autowired
    private AlertaRiscoMapper alertaRiscoMapper;

    @Autowired
    private AlertaRiscoService alertaRiscoService;

    @Autowired
    private AlertaRiscoQueryService alertaRiscoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAlertaRiscoMockMvc;

    private AlertaRisco alertaRisco;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlertaRisco createEntity(EntityManager em) {
        AlertaRisco alertaRisco = new AlertaRisco()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return alertaRisco;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlertaRisco createUpdatedEntity(EntityManager em) {
        AlertaRisco alertaRisco = new AlertaRisco()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return alertaRisco;
    }

    @BeforeEach
    public void initTest() {
        alertaRisco = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlertaRisco() throws Exception {
        int databaseSizeBeforeCreate = alertaRiscoRepository.findAll().size();
        // Create the AlertaRisco
        AlertaRiscoDTO alertaRiscoDTO = alertaRiscoMapper.toDto(alertaRisco);
        restAlertaRiscoMockMvc.perform(post("/api/alerta-riscos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertaRiscoDTO)))
            .andExpect(status().isCreated());

        // Validate the AlertaRisco in the database
        List<AlertaRisco> alertaRiscoList = alertaRiscoRepository.findAll();
        assertThat(alertaRiscoList).hasSize(databaseSizeBeforeCreate + 1);
        AlertaRisco testAlertaRisco = alertaRiscoList.get(alertaRiscoList.size() - 1);
        assertThat(testAlertaRisco.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testAlertaRisco.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testAlertaRisco.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testAlertaRisco.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createAlertaRiscoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alertaRiscoRepository.findAll().size();

        // Create the AlertaRisco with an existing ID
        alertaRisco.setId(1L);
        AlertaRiscoDTO alertaRiscoDTO = alertaRiscoMapper.toDto(alertaRisco);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlertaRiscoMockMvc.perform(post("/api/alerta-riscos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertaRiscoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AlertaRisco in the database
        List<AlertaRisco> alertaRiscoList = alertaRiscoRepository.findAll();
        assertThat(alertaRiscoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = alertaRiscoRepository.findAll().size();
        // set the field null
        alertaRisco.setNome(null);

        // Create the AlertaRisco, which fails.
        AlertaRiscoDTO alertaRiscoDTO = alertaRiscoMapper.toDto(alertaRisco);


        restAlertaRiscoMockMvc.perform(post("/api/alerta-riscos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertaRiscoDTO)))
            .andExpect(status().isBadRequest());

        List<AlertaRisco> alertaRiscoList = alertaRiscoRepository.findAll();
        assertThat(alertaRiscoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = alertaRiscoRepository.findAll().size();
        // set the field null
        alertaRisco.setCreated(null);

        // Create the AlertaRisco, which fails.
        AlertaRiscoDTO alertaRiscoDTO = alertaRiscoMapper.toDto(alertaRisco);


        restAlertaRiscoMockMvc.perform(post("/api/alerta-riscos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertaRiscoDTO)))
            .andExpect(status().isBadRequest());

        List<AlertaRisco> alertaRiscoList = alertaRiscoRepository.findAll();
        assertThat(alertaRiscoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAlertaRiscos() throws Exception {
        // Initialize the database
        alertaRiscoRepository.saveAndFlush(alertaRisco);

        // Get all the alertaRiscoList
        restAlertaRiscoMockMvc.perform(get("/api/alerta-riscos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alertaRisco.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getAlertaRisco() throws Exception {
        // Initialize the database
        alertaRiscoRepository.saveAndFlush(alertaRisco);

        // Get the alertaRisco
        restAlertaRiscoMockMvc.perform(get("/api/alerta-riscos/{id}", alertaRisco.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(alertaRisco.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }


    @Test
    @Transactional
    public void getAlertaRiscosByIdFiltering() throws Exception {
        // Initialize the database
        alertaRiscoRepository.saveAndFlush(alertaRisco);

        Long id = alertaRisco.getId();

        defaultAlertaRiscoShouldBeFound("id.equals=" + id);
        defaultAlertaRiscoShouldNotBeFound("id.notEquals=" + id);

        defaultAlertaRiscoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAlertaRiscoShouldNotBeFound("id.greaterThan=" + id);

        defaultAlertaRiscoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAlertaRiscoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAlertaRiscosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        alertaRiscoRepository.saveAndFlush(alertaRisco);

        // Get all the alertaRiscoList where nome equals to DEFAULT_NOME
        defaultAlertaRiscoShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the alertaRiscoList where nome equals to UPDATED_NOME
        defaultAlertaRiscoShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAlertaRiscosByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alertaRiscoRepository.saveAndFlush(alertaRisco);

        // Get all the alertaRiscoList where nome not equals to DEFAULT_NOME
        defaultAlertaRiscoShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the alertaRiscoList where nome not equals to UPDATED_NOME
        defaultAlertaRiscoShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAlertaRiscosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        alertaRiscoRepository.saveAndFlush(alertaRisco);

        // Get all the alertaRiscoList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultAlertaRiscoShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the alertaRiscoList where nome equals to UPDATED_NOME
        defaultAlertaRiscoShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAlertaRiscosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        alertaRiscoRepository.saveAndFlush(alertaRisco);

        // Get all the alertaRiscoList where nome is not null
        defaultAlertaRiscoShouldBeFound("nome.specified=true");

        // Get all the alertaRiscoList where nome is null
        defaultAlertaRiscoShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlertaRiscosByNomeContainsSomething() throws Exception {
        // Initialize the database
        alertaRiscoRepository.saveAndFlush(alertaRisco);

        // Get all the alertaRiscoList where nome contains DEFAULT_NOME
        defaultAlertaRiscoShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the alertaRiscoList where nome contains UPDATED_NOME
        defaultAlertaRiscoShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAlertaRiscosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        alertaRiscoRepository.saveAndFlush(alertaRisco);

        // Get all the alertaRiscoList where nome does not contain DEFAULT_NOME
        defaultAlertaRiscoShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the alertaRiscoList where nome does not contain UPDATED_NOME
        defaultAlertaRiscoShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllAlertaRiscosByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        alertaRiscoRepository.saveAndFlush(alertaRisco);

        // Get all the alertaRiscoList where descricao equals to DEFAULT_DESCRICAO
        defaultAlertaRiscoShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the alertaRiscoList where descricao equals to UPDATED_DESCRICAO
        defaultAlertaRiscoShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllAlertaRiscosByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alertaRiscoRepository.saveAndFlush(alertaRisco);

        // Get all the alertaRiscoList where descricao not equals to DEFAULT_DESCRICAO
        defaultAlertaRiscoShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the alertaRiscoList where descricao not equals to UPDATED_DESCRICAO
        defaultAlertaRiscoShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllAlertaRiscosByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        alertaRiscoRepository.saveAndFlush(alertaRisco);

        // Get all the alertaRiscoList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultAlertaRiscoShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the alertaRiscoList where descricao equals to UPDATED_DESCRICAO
        defaultAlertaRiscoShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllAlertaRiscosByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        alertaRiscoRepository.saveAndFlush(alertaRisco);

        // Get all the alertaRiscoList where descricao is not null
        defaultAlertaRiscoShouldBeFound("descricao.specified=true");

        // Get all the alertaRiscoList where descricao is null
        defaultAlertaRiscoShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlertaRiscosByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        alertaRiscoRepository.saveAndFlush(alertaRisco);

        // Get all the alertaRiscoList where descricao contains DEFAULT_DESCRICAO
        defaultAlertaRiscoShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the alertaRiscoList where descricao contains UPDATED_DESCRICAO
        defaultAlertaRiscoShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllAlertaRiscosByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        alertaRiscoRepository.saveAndFlush(alertaRisco);

        // Get all the alertaRiscoList where descricao does not contain DEFAULT_DESCRICAO
        defaultAlertaRiscoShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the alertaRiscoList where descricao does not contain UPDATED_DESCRICAO
        defaultAlertaRiscoShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllAlertaRiscosByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        alertaRiscoRepository.saveAndFlush(alertaRisco);

        // Get all the alertaRiscoList where created equals to DEFAULT_CREATED
        defaultAlertaRiscoShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the alertaRiscoList where created equals to UPDATED_CREATED
        defaultAlertaRiscoShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllAlertaRiscosByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alertaRiscoRepository.saveAndFlush(alertaRisco);

        // Get all the alertaRiscoList where created not equals to DEFAULT_CREATED
        defaultAlertaRiscoShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the alertaRiscoList where created not equals to UPDATED_CREATED
        defaultAlertaRiscoShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllAlertaRiscosByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        alertaRiscoRepository.saveAndFlush(alertaRisco);

        // Get all the alertaRiscoList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultAlertaRiscoShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the alertaRiscoList where created equals to UPDATED_CREATED
        defaultAlertaRiscoShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllAlertaRiscosByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        alertaRiscoRepository.saveAndFlush(alertaRisco);

        // Get all the alertaRiscoList where created is not null
        defaultAlertaRiscoShouldBeFound("created.specified=true");

        // Get all the alertaRiscoList where created is null
        defaultAlertaRiscoShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllAlertaRiscosByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        alertaRiscoRepository.saveAndFlush(alertaRisco);

        // Get all the alertaRiscoList where updated equals to DEFAULT_UPDATED
        defaultAlertaRiscoShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the alertaRiscoList where updated equals to UPDATED_UPDATED
        defaultAlertaRiscoShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllAlertaRiscosByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alertaRiscoRepository.saveAndFlush(alertaRisco);

        // Get all the alertaRiscoList where updated not equals to DEFAULT_UPDATED
        defaultAlertaRiscoShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the alertaRiscoList where updated not equals to UPDATED_UPDATED
        defaultAlertaRiscoShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllAlertaRiscosByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        alertaRiscoRepository.saveAndFlush(alertaRisco);

        // Get all the alertaRiscoList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultAlertaRiscoShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the alertaRiscoList where updated equals to UPDATED_UPDATED
        defaultAlertaRiscoShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllAlertaRiscosByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        alertaRiscoRepository.saveAndFlush(alertaRisco);

        // Get all the alertaRiscoList where updated is not null
        defaultAlertaRiscoShouldBeFound("updated.specified=true");

        // Get all the alertaRiscoList where updated is null
        defaultAlertaRiscoShouldNotBeFound("updated.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAlertaRiscoShouldBeFound(String filter) throws Exception {
        restAlertaRiscoMockMvc.perform(get("/api/alerta-riscos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alertaRisco.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restAlertaRiscoMockMvc.perform(get("/api/alerta-riscos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAlertaRiscoShouldNotBeFound(String filter) throws Exception {
        restAlertaRiscoMockMvc.perform(get("/api/alerta-riscos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAlertaRiscoMockMvc.perform(get("/api/alerta-riscos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingAlertaRisco() throws Exception {
        // Get the alertaRisco
        restAlertaRiscoMockMvc.perform(get("/api/alerta-riscos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlertaRisco() throws Exception {
        // Initialize the database
        alertaRiscoRepository.saveAndFlush(alertaRisco);

        int databaseSizeBeforeUpdate = alertaRiscoRepository.findAll().size();

        // Update the alertaRisco
        AlertaRisco updatedAlertaRisco = alertaRiscoRepository.findById(alertaRisco.getId()).get();
        // Disconnect from session so that the updates on updatedAlertaRisco are not directly saved in db
        em.detach(updatedAlertaRisco);
        updatedAlertaRisco
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        AlertaRiscoDTO alertaRiscoDTO = alertaRiscoMapper.toDto(updatedAlertaRisco);

        restAlertaRiscoMockMvc.perform(put("/api/alerta-riscos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertaRiscoDTO)))
            .andExpect(status().isOk());

        // Validate the AlertaRisco in the database
        List<AlertaRisco> alertaRiscoList = alertaRiscoRepository.findAll();
        assertThat(alertaRiscoList).hasSize(databaseSizeBeforeUpdate);
        AlertaRisco testAlertaRisco = alertaRiscoList.get(alertaRiscoList.size() - 1);
        assertThat(testAlertaRisco.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testAlertaRisco.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testAlertaRisco.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testAlertaRisco.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingAlertaRisco() throws Exception {
        int databaseSizeBeforeUpdate = alertaRiscoRepository.findAll().size();

        // Create the AlertaRisco
        AlertaRiscoDTO alertaRiscoDTO = alertaRiscoMapper.toDto(alertaRisco);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlertaRiscoMockMvc.perform(put("/api/alerta-riscos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertaRiscoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AlertaRisco in the database
        List<AlertaRisco> alertaRiscoList = alertaRiscoRepository.findAll();
        assertThat(alertaRiscoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAlertaRisco() throws Exception {
        // Initialize the database
        alertaRiscoRepository.saveAndFlush(alertaRisco);

        int databaseSizeBeforeDelete = alertaRiscoRepository.findAll().size();

        // Delete the alertaRisco
        restAlertaRiscoMockMvc.perform(delete("/api/alerta-riscos/{id}", alertaRisco.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AlertaRisco> alertaRiscoList = alertaRiscoRepository.findAll();
        assertThat(alertaRiscoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
