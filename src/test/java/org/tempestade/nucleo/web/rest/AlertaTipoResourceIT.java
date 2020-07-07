package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.AlertaTipo;
import org.tempestade.nucleo.repository.AlertaTipoRepository;
import org.tempestade.nucleo.service.AlertaTipoService;
import org.tempestade.nucleo.service.dto.AlertaTipoDTO;
import org.tempestade.nucleo.service.mapper.AlertaTipoMapper;
import org.tempestade.nucleo.service.dto.AlertaTipoCriteria;
import org.tempestade.nucleo.service.AlertaTipoQueryService;

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
 * Integration tests for the {@link AlertaTipoResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AlertaTipoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private AlertaTipoRepository alertaTipoRepository;

    @Autowired
    private AlertaTipoMapper alertaTipoMapper;

    @Autowired
    private AlertaTipoService alertaTipoService;

    @Autowired
    private AlertaTipoQueryService alertaTipoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAlertaTipoMockMvc;

    private AlertaTipo alertaTipo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlertaTipo createEntity(EntityManager em) {
        AlertaTipo alertaTipo = new AlertaTipo()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return alertaTipo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlertaTipo createUpdatedEntity(EntityManager em) {
        AlertaTipo alertaTipo = new AlertaTipo()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return alertaTipo;
    }

    @BeforeEach
    public void initTest() {
        alertaTipo = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlertaTipo() throws Exception {
        int databaseSizeBeforeCreate = alertaTipoRepository.findAll().size();
        // Create the AlertaTipo
        AlertaTipoDTO alertaTipoDTO = alertaTipoMapper.toDto(alertaTipo);
        restAlertaTipoMockMvc.perform(post("/api/alerta-tipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertaTipoDTO)))
            .andExpect(status().isCreated());

        // Validate the AlertaTipo in the database
        List<AlertaTipo> alertaTipoList = alertaTipoRepository.findAll();
        assertThat(alertaTipoList).hasSize(databaseSizeBeforeCreate + 1);
        AlertaTipo testAlertaTipo = alertaTipoList.get(alertaTipoList.size() - 1);
        assertThat(testAlertaTipo.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testAlertaTipo.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testAlertaTipo.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testAlertaTipo.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createAlertaTipoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alertaTipoRepository.findAll().size();

        // Create the AlertaTipo with an existing ID
        alertaTipo.setId(1L);
        AlertaTipoDTO alertaTipoDTO = alertaTipoMapper.toDto(alertaTipo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlertaTipoMockMvc.perform(post("/api/alerta-tipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertaTipoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AlertaTipo in the database
        List<AlertaTipo> alertaTipoList = alertaTipoRepository.findAll();
        assertThat(alertaTipoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = alertaTipoRepository.findAll().size();
        // set the field null
        alertaTipo.setNome(null);

        // Create the AlertaTipo, which fails.
        AlertaTipoDTO alertaTipoDTO = alertaTipoMapper.toDto(alertaTipo);


        restAlertaTipoMockMvc.perform(post("/api/alerta-tipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertaTipoDTO)))
            .andExpect(status().isBadRequest());

        List<AlertaTipo> alertaTipoList = alertaTipoRepository.findAll();
        assertThat(alertaTipoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = alertaTipoRepository.findAll().size();
        // set the field null
        alertaTipo.setCreated(null);

        // Create the AlertaTipo, which fails.
        AlertaTipoDTO alertaTipoDTO = alertaTipoMapper.toDto(alertaTipo);


        restAlertaTipoMockMvc.perform(post("/api/alerta-tipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertaTipoDTO)))
            .andExpect(status().isBadRequest());

        List<AlertaTipo> alertaTipoList = alertaTipoRepository.findAll();
        assertThat(alertaTipoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAlertaTipos() throws Exception {
        // Initialize the database
        alertaTipoRepository.saveAndFlush(alertaTipo);

        // Get all the alertaTipoList
        restAlertaTipoMockMvc.perform(get("/api/alerta-tipos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alertaTipo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getAlertaTipo() throws Exception {
        // Initialize the database
        alertaTipoRepository.saveAndFlush(alertaTipo);

        // Get the alertaTipo
        restAlertaTipoMockMvc.perform(get("/api/alerta-tipos/{id}", alertaTipo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(alertaTipo.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }


    @Test
    @Transactional
    public void getAlertaTiposByIdFiltering() throws Exception {
        // Initialize the database
        alertaTipoRepository.saveAndFlush(alertaTipo);

        Long id = alertaTipo.getId();

        defaultAlertaTipoShouldBeFound("id.equals=" + id);
        defaultAlertaTipoShouldNotBeFound("id.notEquals=" + id);

        defaultAlertaTipoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAlertaTipoShouldNotBeFound("id.greaterThan=" + id);

        defaultAlertaTipoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAlertaTipoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAlertaTiposByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        alertaTipoRepository.saveAndFlush(alertaTipo);

        // Get all the alertaTipoList where nome equals to DEFAULT_NOME
        defaultAlertaTipoShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the alertaTipoList where nome equals to UPDATED_NOME
        defaultAlertaTipoShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAlertaTiposByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alertaTipoRepository.saveAndFlush(alertaTipo);

        // Get all the alertaTipoList where nome not equals to DEFAULT_NOME
        defaultAlertaTipoShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the alertaTipoList where nome not equals to UPDATED_NOME
        defaultAlertaTipoShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAlertaTiposByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        alertaTipoRepository.saveAndFlush(alertaTipo);

        // Get all the alertaTipoList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultAlertaTipoShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the alertaTipoList where nome equals to UPDATED_NOME
        defaultAlertaTipoShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAlertaTiposByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        alertaTipoRepository.saveAndFlush(alertaTipo);

        // Get all the alertaTipoList where nome is not null
        defaultAlertaTipoShouldBeFound("nome.specified=true");

        // Get all the alertaTipoList where nome is null
        defaultAlertaTipoShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlertaTiposByNomeContainsSomething() throws Exception {
        // Initialize the database
        alertaTipoRepository.saveAndFlush(alertaTipo);

        // Get all the alertaTipoList where nome contains DEFAULT_NOME
        defaultAlertaTipoShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the alertaTipoList where nome contains UPDATED_NOME
        defaultAlertaTipoShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAlertaTiposByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        alertaTipoRepository.saveAndFlush(alertaTipo);

        // Get all the alertaTipoList where nome does not contain DEFAULT_NOME
        defaultAlertaTipoShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the alertaTipoList where nome does not contain UPDATED_NOME
        defaultAlertaTipoShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllAlertaTiposByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        alertaTipoRepository.saveAndFlush(alertaTipo);

        // Get all the alertaTipoList where descricao equals to DEFAULT_DESCRICAO
        defaultAlertaTipoShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the alertaTipoList where descricao equals to UPDATED_DESCRICAO
        defaultAlertaTipoShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllAlertaTiposByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alertaTipoRepository.saveAndFlush(alertaTipo);

        // Get all the alertaTipoList where descricao not equals to DEFAULT_DESCRICAO
        defaultAlertaTipoShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the alertaTipoList where descricao not equals to UPDATED_DESCRICAO
        defaultAlertaTipoShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllAlertaTiposByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        alertaTipoRepository.saveAndFlush(alertaTipo);

        // Get all the alertaTipoList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultAlertaTipoShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the alertaTipoList where descricao equals to UPDATED_DESCRICAO
        defaultAlertaTipoShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllAlertaTiposByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        alertaTipoRepository.saveAndFlush(alertaTipo);

        // Get all the alertaTipoList where descricao is not null
        defaultAlertaTipoShouldBeFound("descricao.specified=true");

        // Get all the alertaTipoList where descricao is null
        defaultAlertaTipoShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlertaTiposByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        alertaTipoRepository.saveAndFlush(alertaTipo);

        // Get all the alertaTipoList where descricao contains DEFAULT_DESCRICAO
        defaultAlertaTipoShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the alertaTipoList where descricao contains UPDATED_DESCRICAO
        defaultAlertaTipoShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllAlertaTiposByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        alertaTipoRepository.saveAndFlush(alertaTipo);

        // Get all the alertaTipoList where descricao does not contain DEFAULT_DESCRICAO
        defaultAlertaTipoShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the alertaTipoList where descricao does not contain UPDATED_DESCRICAO
        defaultAlertaTipoShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllAlertaTiposByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        alertaTipoRepository.saveAndFlush(alertaTipo);

        // Get all the alertaTipoList where created equals to DEFAULT_CREATED
        defaultAlertaTipoShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the alertaTipoList where created equals to UPDATED_CREATED
        defaultAlertaTipoShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllAlertaTiposByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alertaTipoRepository.saveAndFlush(alertaTipo);

        // Get all the alertaTipoList where created not equals to DEFAULT_CREATED
        defaultAlertaTipoShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the alertaTipoList where created not equals to UPDATED_CREATED
        defaultAlertaTipoShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllAlertaTiposByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        alertaTipoRepository.saveAndFlush(alertaTipo);

        // Get all the alertaTipoList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultAlertaTipoShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the alertaTipoList where created equals to UPDATED_CREATED
        defaultAlertaTipoShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllAlertaTiposByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        alertaTipoRepository.saveAndFlush(alertaTipo);

        // Get all the alertaTipoList where created is not null
        defaultAlertaTipoShouldBeFound("created.specified=true");

        // Get all the alertaTipoList where created is null
        defaultAlertaTipoShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllAlertaTiposByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        alertaTipoRepository.saveAndFlush(alertaTipo);

        // Get all the alertaTipoList where updated equals to DEFAULT_UPDATED
        defaultAlertaTipoShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the alertaTipoList where updated equals to UPDATED_UPDATED
        defaultAlertaTipoShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllAlertaTiposByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alertaTipoRepository.saveAndFlush(alertaTipo);

        // Get all the alertaTipoList where updated not equals to DEFAULT_UPDATED
        defaultAlertaTipoShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the alertaTipoList where updated not equals to UPDATED_UPDATED
        defaultAlertaTipoShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllAlertaTiposByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        alertaTipoRepository.saveAndFlush(alertaTipo);

        // Get all the alertaTipoList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultAlertaTipoShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the alertaTipoList where updated equals to UPDATED_UPDATED
        defaultAlertaTipoShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllAlertaTiposByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        alertaTipoRepository.saveAndFlush(alertaTipo);

        // Get all the alertaTipoList where updated is not null
        defaultAlertaTipoShouldBeFound("updated.specified=true");

        // Get all the alertaTipoList where updated is null
        defaultAlertaTipoShouldNotBeFound("updated.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAlertaTipoShouldBeFound(String filter) throws Exception {
        restAlertaTipoMockMvc.perform(get("/api/alerta-tipos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alertaTipo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restAlertaTipoMockMvc.perform(get("/api/alerta-tipos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAlertaTipoShouldNotBeFound(String filter) throws Exception {
        restAlertaTipoMockMvc.perform(get("/api/alerta-tipos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAlertaTipoMockMvc.perform(get("/api/alerta-tipos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingAlertaTipo() throws Exception {
        // Get the alertaTipo
        restAlertaTipoMockMvc.perform(get("/api/alerta-tipos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlertaTipo() throws Exception {
        // Initialize the database
        alertaTipoRepository.saveAndFlush(alertaTipo);

        int databaseSizeBeforeUpdate = alertaTipoRepository.findAll().size();

        // Update the alertaTipo
        AlertaTipo updatedAlertaTipo = alertaTipoRepository.findById(alertaTipo.getId()).get();
        // Disconnect from session so that the updates on updatedAlertaTipo are not directly saved in db
        em.detach(updatedAlertaTipo);
        updatedAlertaTipo
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        AlertaTipoDTO alertaTipoDTO = alertaTipoMapper.toDto(updatedAlertaTipo);

        restAlertaTipoMockMvc.perform(put("/api/alerta-tipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertaTipoDTO)))
            .andExpect(status().isOk());

        // Validate the AlertaTipo in the database
        List<AlertaTipo> alertaTipoList = alertaTipoRepository.findAll();
        assertThat(alertaTipoList).hasSize(databaseSizeBeforeUpdate);
        AlertaTipo testAlertaTipo = alertaTipoList.get(alertaTipoList.size() - 1);
        assertThat(testAlertaTipo.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testAlertaTipo.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testAlertaTipo.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testAlertaTipo.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingAlertaTipo() throws Exception {
        int databaseSizeBeforeUpdate = alertaTipoRepository.findAll().size();

        // Create the AlertaTipo
        AlertaTipoDTO alertaTipoDTO = alertaTipoMapper.toDto(alertaTipo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlertaTipoMockMvc.perform(put("/api/alerta-tipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertaTipoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AlertaTipo in the database
        List<AlertaTipo> alertaTipoList = alertaTipoRepository.findAll();
        assertThat(alertaTipoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAlertaTipo() throws Exception {
        // Initialize the database
        alertaTipoRepository.saveAndFlush(alertaTipo);

        int databaseSizeBeforeDelete = alertaTipoRepository.findAll().size();

        // Delete the alertaTipo
        restAlertaTipoMockMvc.perform(delete("/api/alerta-tipos/{id}", alertaTipo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AlertaTipo> alertaTipoList = alertaTipoRepository.findAll();
        assertThat(alertaTipoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
