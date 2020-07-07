package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.DiaSemana;
import org.tempestade.nucleo.repository.DiaSemanaRepository;
import org.tempestade.nucleo.service.DiaSemanaService;
import org.tempestade.nucleo.service.dto.DiaSemanaDTO;
import org.tempestade.nucleo.service.mapper.DiaSemanaMapper;
import org.tempestade.nucleo.service.dto.DiaSemanaCriteria;
import org.tempestade.nucleo.service.DiaSemanaQueryService;

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
 * Integration tests for the {@link DiaSemanaResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DiaSemanaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private DiaSemanaRepository diaSemanaRepository;

    @Autowired
    private DiaSemanaMapper diaSemanaMapper;

    @Autowired
    private DiaSemanaService diaSemanaService;

    @Autowired
    private DiaSemanaQueryService diaSemanaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDiaSemanaMockMvc;

    private DiaSemana diaSemana;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DiaSemana createEntity(EntityManager em) {
        DiaSemana diaSemana = new DiaSemana()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return diaSemana;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DiaSemana createUpdatedEntity(EntityManager em) {
        DiaSemana diaSemana = new DiaSemana()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return diaSemana;
    }

    @BeforeEach
    public void initTest() {
        diaSemana = createEntity(em);
    }

    @Test
    @Transactional
    public void createDiaSemana() throws Exception {
        int databaseSizeBeforeCreate = diaSemanaRepository.findAll().size();
        // Create the DiaSemana
        DiaSemanaDTO diaSemanaDTO = diaSemanaMapper.toDto(diaSemana);
        restDiaSemanaMockMvc.perform(post("/api/dia-semanas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(diaSemanaDTO)))
            .andExpect(status().isCreated());

        // Validate the DiaSemana in the database
        List<DiaSemana> diaSemanaList = diaSemanaRepository.findAll();
        assertThat(diaSemanaList).hasSize(databaseSizeBeforeCreate + 1);
        DiaSemana testDiaSemana = diaSemanaList.get(diaSemanaList.size() - 1);
        assertThat(testDiaSemana.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testDiaSemana.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testDiaSemana.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testDiaSemana.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createDiaSemanaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = diaSemanaRepository.findAll().size();

        // Create the DiaSemana with an existing ID
        diaSemana.setId(1L);
        DiaSemanaDTO diaSemanaDTO = diaSemanaMapper.toDto(diaSemana);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiaSemanaMockMvc.perform(post("/api/dia-semanas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(diaSemanaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DiaSemana in the database
        List<DiaSemana> diaSemanaList = diaSemanaRepository.findAll();
        assertThat(diaSemanaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = diaSemanaRepository.findAll().size();
        // set the field null
        diaSemana.setNome(null);

        // Create the DiaSemana, which fails.
        DiaSemanaDTO diaSemanaDTO = diaSemanaMapper.toDto(diaSemana);


        restDiaSemanaMockMvc.perform(post("/api/dia-semanas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(diaSemanaDTO)))
            .andExpect(status().isBadRequest());

        List<DiaSemana> diaSemanaList = diaSemanaRepository.findAll();
        assertThat(diaSemanaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = diaSemanaRepository.findAll().size();
        // set the field null
        diaSemana.setCreated(null);

        // Create the DiaSemana, which fails.
        DiaSemanaDTO diaSemanaDTO = diaSemanaMapper.toDto(diaSemana);


        restDiaSemanaMockMvc.perform(post("/api/dia-semanas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(diaSemanaDTO)))
            .andExpect(status().isBadRequest());

        List<DiaSemana> diaSemanaList = diaSemanaRepository.findAll();
        assertThat(diaSemanaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDiaSemanas() throws Exception {
        // Initialize the database
        diaSemanaRepository.saveAndFlush(diaSemana);

        // Get all the diaSemanaList
        restDiaSemanaMockMvc.perform(get("/api/dia-semanas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(diaSemana.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getDiaSemana() throws Exception {
        // Initialize the database
        diaSemanaRepository.saveAndFlush(diaSemana);

        // Get the diaSemana
        restDiaSemanaMockMvc.perform(get("/api/dia-semanas/{id}", diaSemana.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(diaSemana.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }


    @Test
    @Transactional
    public void getDiaSemanasByIdFiltering() throws Exception {
        // Initialize the database
        diaSemanaRepository.saveAndFlush(diaSemana);

        Long id = diaSemana.getId();

        defaultDiaSemanaShouldBeFound("id.equals=" + id);
        defaultDiaSemanaShouldNotBeFound("id.notEquals=" + id);

        defaultDiaSemanaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDiaSemanaShouldNotBeFound("id.greaterThan=" + id);

        defaultDiaSemanaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDiaSemanaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDiaSemanasByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        diaSemanaRepository.saveAndFlush(diaSemana);

        // Get all the diaSemanaList where nome equals to DEFAULT_NOME
        defaultDiaSemanaShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the diaSemanaList where nome equals to UPDATED_NOME
        defaultDiaSemanaShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllDiaSemanasByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        diaSemanaRepository.saveAndFlush(diaSemana);

        // Get all the diaSemanaList where nome not equals to DEFAULT_NOME
        defaultDiaSemanaShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the diaSemanaList where nome not equals to UPDATED_NOME
        defaultDiaSemanaShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllDiaSemanasByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        diaSemanaRepository.saveAndFlush(diaSemana);

        // Get all the diaSemanaList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultDiaSemanaShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the diaSemanaList where nome equals to UPDATED_NOME
        defaultDiaSemanaShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllDiaSemanasByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        diaSemanaRepository.saveAndFlush(diaSemana);

        // Get all the diaSemanaList where nome is not null
        defaultDiaSemanaShouldBeFound("nome.specified=true");

        // Get all the diaSemanaList where nome is null
        defaultDiaSemanaShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllDiaSemanasByNomeContainsSomething() throws Exception {
        // Initialize the database
        diaSemanaRepository.saveAndFlush(diaSemana);

        // Get all the diaSemanaList where nome contains DEFAULT_NOME
        defaultDiaSemanaShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the diaSemanaList where nome contains UPDATED_NOME
        defaultDiaSemanaShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllDiaSemanasByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        diaSemanaRepository.saveAndFlush(diaSemana);

        // Get all the diaSemanaList where nome does not contain DEFAULT_NOME
        defaultDiaSemanaShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the diaSemanaList where nome does not contain UPDATED_NOME
        defaultDiaSemanaShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllDiaSemanasByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        diaSemanaRepository.saveAndFlush(diaSemana);

        // Get all the diaSemanaList where descricao equals to DEFAULT_DESCRICAO
        defaultDiaSemanaShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the diaSemanaList where descricao equals to UPDATED_DESCRICAO
        defaultDiaSemanaShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllDiaSemanasByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        diaSemanaRepository.saveAndFlush(diaSemana);

        // Get all the diaSemanaList where descricao not equals to DEFAULT_DESCRICAO
        defaultDiaSemanaShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the diaSemanaList where descricao not equals to UPDATED_DESCRICAO
        defaultDiaSemanaShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllDiaSemanasByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        diaSemanaRepository.saveAndFlush(diaSemana);

        // Get all the diaSemanaList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultDiaSemanaShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the diaSemanaList where descricao equals to UPDATED_DESCRICAO
        defaultDiaSemanaShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllDiaSemanasByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        diaSemanaRepository.saveAndFlush(diaSemana);

        // Get all the diaSemanaList where descricao is not null
        defaultDiaSemanaShouldBeFound("descricao.specified=true");

        // Get all the diaSemanaList where descricao is null
        defaultDiaSemanaShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllDiaSemanasByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        diaSemanaRepository.saveAndFlush(diaSemana);

        // Get all the diaSemanaList where descricao contains DEFAULT_DESCRICAO
        defaultDiaSemanaShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the diaSemanaList where descricao contains UPDATED_DESCRICAO
        defaultDiaSemanaShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllDiaSemanasByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        diaSemanaRepository.saveAndFlush(diaSemana);

        // Get all the diaSemanaList where descricao does not contain DEFAULT_DESCRICAO
        defaultDiaSemanaShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the diaSemanaList where descricao does not contain UPDATED_DESCRICAO
        defaultDiaSemanaShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllDiaSemanasByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        diaSemanaRepository.saveAndFlush(diaSemana);

        // Get all the diaSemanaList where created equals to DEFAULT_CREATED
        defaultDiaSemanaShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the diaSemanaList where created equals to UPDATED_CREATED
        defaultDiaSemanaShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllDiaSemanasByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        diaSemanaRepository.saveAndFlush(diaSemana);

        // Get all the diaSemanaList where created not equals to DEFAULT_CREATED
        defaultDiaSemanaShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the diaSemanaList where created not equals to UPDATED_CREATED
        defaultDiaSemanaShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllDiaSemanasByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        diaSemanaRepository.saveAndFlush(diaSemana);

        // Get all the diaSemanaList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultDiaSemanaShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the diaSemanaList where created equals to UPDATED_CREATED
        defaultDiaSemanaShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllDiaSemanasByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        diaSemanaRepository.saveAndFlush(diaSemana);

        // Get all the diaSemanaList where created is not null
        defaultDiaSemanaShouldBeFound("created.specified=true");

        // Get all the diaSemanaList where created is null
        defaultDiaSemanaShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllDiaSemanasByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        diaSemanaRepository.saveAndFlush(diaSemana);

        // Get all the diaSemanaList where updated equals to DEFAULT_UPDATED
        defaultDiaSemanaShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the diaSemanaList where updated equals to UPDATED_UPDATED
        defaultDiaSemanaShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllDiaSemanasByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        diaSemanaRepository.saveAndFlush(diaSemana);

        // Get all the diaSemanaList where updated not equals to DEFAULT_UPDATED
        defaultDiaSemanaShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the diaSemanaList where updated not equals to UPDATED_UPDATED
        defaultDiaSemanaShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllDiaSemanasByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        diaSemanaRepository.saveAndFlush(diaSemana);

        // Get all the diaSemanaList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultDiaSemanaShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the diaSemanaList where updated equals to UPDATED_UPDATED
        defaultDiaSemanaShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllDiaSemanasByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        diaSemanaRepository.saveAndFlush(diaSemana);

        // Get all the diaSemanaList where updated is not null
        defaultDiaSemanaShouldBeFound("updated.specified=true");

        // Get all the diaSemanaList where updated is null
        defaultDiaSemanaShouldNotBeFound("updated.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDiaSemanaShouldBeFound(String filter) throws Exception {
        restDiaSemanaMockMvc.perform(get("/api/dia-semanas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(diaSemana.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restDiaSemanaMockMvc.perform(get("/api/dia-semanas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDiaSemanaShouldNotBeFound(String filter) throws Exception {
        restDiaSemanaMockMvc.perform(get("/api/dia-semanas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDiaSemanaMockMvc.perform(get("/api/dia-semanas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingDiaSemana() throws Exception {
        // Get the diaSemana
        restDiaSemanaMockMvc.perform(get("/api/dia-semanas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDiaSemana() throws Exception {
        // Initialize the database
        diaSemanaRepository.saveAndFlush(diaSemana);

        int databaseSizeBeforeUpdate = diaSemanaRepository.findAll().size();

        // Update the diaSemana
        DiaSemana updatedDiaSemana = diaSemanaRepository.findById(diaSemana.getId()).get();
        // Disconnect from session so that the updates on updatedDiaSemana are not directly saved in db
        em.detach(updatedDiaSemana);
        updatedDiaSemana
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        DiaSemanaDTO diaSemanaDTO = diaSemanaMapper.toDto(updatedDiaSemana);

        restDiaSemanaMockMvc.perform(put("/api/dia-semanas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(diaSemanaDTO)))
            .andExpect(status().isOk());

        // Validate the DiaSemana in the database
        List<DiaSemana> diaSemanaList = diaSemanaRepository.findAll();
        assertThat(diaSemanaList).hasSize(databaseSizeBeforeUpdate);
        DiaSemana testDiaSemana = diaSemanaList.get(diaSemanaList.size() - 1);
        assertThat(testDiaSemana.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testDiaSemana.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testDiaSemana.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testDiaSemana.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingDiaSemana() throws Exception {
        int databaseSizeBeforeUpdate = diaSemanaRepository.findAll().size();

        // Create the DiaSemana
        DiaSemanaDTO diaSemanaDTO = diaSemanaMapper.toDto(diaSemana);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiaSemanaMockMvc.perform(put("/api/dia-semanas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(diaSemanaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DiaSemana in the database
        List<DiaSemana> diaSemanaList = diaSemanaRepository.findAll();
        assertThat(diaSemanaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDiaSemana() throws Exception {
        // Initialize the database
        diaSemanaRepository.saveAndFlush(diaSemana);

        int databaseSizeBeforeDelete = diaSemanaRepository.findAll().size();

        // Delete the diaSemana
        restDiaSemanaMockMvc.perform(delete("/api/dia-semanas/{id}", diaSemana.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DiaSemana> diaSemanaList = diaSemanaRepository.findAll();
        assertThat(diaSemanaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
