package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.Integrador;
import org.tempestade.nucleo.repository.IntegradorRepository;
import org.tempestade.nucleo.service.IntegradorService;
import org.tempestade.nucleo.service.dto.IntegradorDTO;
import org.tempestade.nucleo.service.mapper.IntegradorMapper;
import org.tempestade.nucleo.service.dto.IntegradorCriteria;
import org.tempestade.nucleo.service.IntegradorQueryService;

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
 * Integration tests for the {@link IntegradorResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class IntegradorResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private IntegradorRepository integradorRepository;

    @Autowired
    private IntegradorMapper integradorMapper;

    @Autowired
    private IntegradorService integradorService;

    @Autowired
    private IntegradorQueryService integradorQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIntegradorMockMvc;

    private Integrador integrador;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Integrador createEntity(EntityManager em) {
        Integrador integrador = new Integrador()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return integrador;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Integrador createUpdatedEntity(EntityManager em) {
        Integrador integrador = new Integrador()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return integrador;
    }

    @BeforeEach
    public void initTest() {
        integrador = createEntity(em);
    }

    @Test
    @Transactional
    public void createIntegrador() throws Exception {
        int databaseSizeBeforeCreate = integradorRepository.findAll().size();
        // Create the Integrador
        IntegradorDTO integradorDTO = integradorMapper.toDto(integrador);
        restIntegradorMockMvc.perform(post("/api/integradors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(integradorDTO)))
            .andExpect(status().isCreated());

        // Validate the Integrador in the database
        List<Integrador> integradorList = integradorRepository.findAll();
        assertThat(integradorList).hasSize(databaseSizeBeforeCreate + 1);
        Integrador testIntegrador = integradorList.get(integradorList.size() - 1);
        assertThat(testIntegrador.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testIntegrador.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testIntegrador.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testIntegrador.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createIntegradorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = integradorRepository.findAll().size();

        // Create the Integrador with an existing ID
        integrador.setId(1L);
        IntegradorDTO integradorDTO = integradorMapper.toDto(integrador);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIntegradorMockMvc.perform(post("/api/integradors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(integradorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Integrador in the database
        List<Integrador> integradorList = integradorRepository.findAll();
        assertThat(integradorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = integradorRepository.findAll().size();
        // set the field null
        integrador.setNome(null);

        // Create the Integrador, which fails.
        IntegradorDTO integradorDTO = integradorMapper.toDto(integrador);


        restIntegradorMockMvc.perform(post("/api/integradors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(integradorDTO)))
            .andExpect(status().isBadRequest());

        List<Integrador> integradorList = integradorRepository.findAll();
        assertThat(integradorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = integradorRepository.findAll().size();
        // set the field null
        integrador.setCreated(null);

        // Create the Integrador, which fails.
        IntegradorDTO integradorDTO = integradorMapper.toDto(integrador);


        restIntegradorMockMvc.perform(post("/api/integradors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(integradorDTO)))
            .andExpect(status().isBadRequest());

        List<Integrador> integradorList = integradorRepository.findAll();
        assertThat(integradorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIntegradors() throws Exception {
        // Initialize the database
        integradorRepository.saveAndFlush(integrador);

        // Get all the integradorList
        restIntegradorMockMvc.perform(get("/api/integradors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(integrador.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getIntegrador() throws Exception {
        // Initialize the database
        integradorRepository.saveAndFlush(integrador);

        // Get the integrador
        restIntegradorMockMvc.perform(get("/api/integradors/{id}", integrador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(integrador.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }


    @Test
    @Transactional
    public void getIntegradorsByIdFiltering() throws Exception {
        // Initialize the database
        integradorRepository.saveAndFlush(integrador);

        Long id = integrador.getId();

        defaultIntegradorShouldBeFound("id.equals=" + id);
        defaultIntegradorShouldNotBeFound("id.notEquals=" + id);

        defaultIntegradorShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultIntegradorShouldNotBeFound("id.greaterThan=" + id);

        defaultIntegradorShouldBeFound("id.lessThanOrEqual=" + id);
        defaultIntegradorShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllIntegradorsByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        integradorRepository.saveAndFlush(integrador);

        // Get all the integradorList where nome equals to DEFAULT_NOME
        defaultIntegradorShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the integradorList where nome equals to UPDATED_NOME
        defaultIntegradorShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllIntegradorsByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        integradorRepository.saveAndFlush(integrador);

        // Get all the integradorList where nome not equals to DEFAULT_NOME
        defaultIntegradorShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the integradorList where nome not equals to UPDATED_NOME
        defaultIntegradorShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllIntegradorsByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        integradorRepository.saveAndFlush(integrador);

        // Get all the integradorList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultIntegradorShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the integradorList where nome equals to UPDATED_NOME
        defaultIntegradorShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllIntegradorsByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        integradorRepository.saveAndFlush(integrador);

        // Get all the integradorList where nome is not null
        defaultIntegradorShouldBeFound("nome.specified=true");

        // Get all the integradorList where nome is null
        defaultIntegradorShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllIntegradorsByNomeContainsSomething() throws Exception {
        // Initialize the database
        integradorRepository.saveAndFlush(integrador);

        // Get all the integradorList where nome contains DEFAULT_NOME
        defaultIntegradorShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the integradorList where nome contains UPDATED_NOME
        defaultIntegradorShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllIntegradorsByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        integradorRepository.saveAndFlush(integrador);

        // Get all the integradorList where nome does not contain DEFAULT_NOME
        defaultIntegradorShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the integradorList where nome does not contain UPDATED_NOME
        defaultIntegradorShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllIntegradorsByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        integradorRepository.saveAndFlush(integrador);

        // Get all the integradorList where descricao equals to DEFAULT_DESCRICAO
        defaultIntegradorShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the integradorList where descricao equals to UPDATED_DESCRICAO
        defaultIntegradorShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllIntegradorsByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        integradorRepository.saveAndFlush(integrador);

        // Get all the integradorList where descricao not equals to DEFAULT_DESCRICAO
        defaultIntegradorShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the integradorList where descricao not equals to UPDATED_DESCRICAO
        defaultIntegradorShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllIntegradorsByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        integradorRepository.saveAndFlush(integrador);

        // Get all the integradorList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultIntegradorShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the integradorList where descricao equals to UPDATED_DESCRICAO
        defaultIntegradorShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllIntegradorsByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        integradorRepository.saveAndFlush(integrador);

        // Get all the integradorList where descricao is not null
        defaultIntegradorShouldBeFound("descricao.specified=true");

        // Get all the integradorList where descricao is null
        defaultIntegradorShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllIntegradorsByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        integradorRepository.saveAndFlush(integrador);

        // Get all the integradorList where descricao contains DEFAULT_DESCRICAO
        defaultIntegradorShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the integradorList where descricao contains UPDATED_DESCRICAO
        defaultIntegradorShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllIntegradorsByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        integradorRepository.saveAndFlush(integrador);

        // Get all the integradorList where descricao does not contain DEFAULT_DESCRICAO
        defaultIntegradorShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the integradorList where descricao does not contain UPDATED_DESCRICAO
        defaultIntegradorShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllIntegradorsByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        integradorRepository.saveAndFlush(integrador);

        // Get all the integradorList where created equals to DEFAULT_CREATED
        defaultIntegradorShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the integradorList where created equals to UPDATED_CREATED
        defaultIntegradorShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllIntegradorsByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        integradorRepository.saveAndFlush(integrador);

        // Get all the integradorList where created not equals to DEFAULT_CREATED
        defaultIntegradorShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the integradorList where created not equals to UPDATED_CREATED
        defaultIntegradorShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllIntegradorsByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        integradorRepository.saveAndFlush(integrador);

        // Get all the integradorList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultIntegradorShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the integradorList where created equals to UPDATED_CREATED
        defaultIntegradorShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllIntegradorsByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        integradorRepository.saveAndFlush(integrador);

        // Get all the integradorList where created is not null
        defaultIntegradorShouldBeFound("created.specified=true");

        // Get all the integradorList where created is null
        defaultIntegradorShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllIntegradorsByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        integradorRepository.saveAndFlush(integrador);

        // Get all the integradorList where updated equals to DEFAULT_UPDATED
        defaultIntegradorShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the integradorList where updated equals to UPDATED_UPDATED
        defaultIntegradorShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllIntegradorsByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        integradorRepository.saveAndFlush(integrador);

        // Get all the integradorList where updated not equals to DEFAULT_UPDATED
        defaultIntegradorShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the integradorList where updated not equals to UPDATED_UPDATED
        defaultIntegradorShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllIntegradorsByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        integradorRepository.saveAndFlush(integrador);

        // Get all the integradorList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultIntegradorShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the integradorList where updated equals to UPDATED_UPDATED
        defaultIntegradorShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllIntegradorsByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        integradorRepository.saveAndFlush(integrador);

        // Get all the integradorList where updated is not null
        defaultIntegradorShouldBeFound("updated.specified=true");

        // Get all the integradorList where updated is null
        defaultIntegradorShouldNotBeFound("updated.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultIntegradorShouldBeFound(String filter) throws Exception {
        restIntegradorMockMvc.perform(get("/api/integradors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(integrador.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restIntegradorMockMvc.perform(get("/api/integradors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultIntegradorShouldNotBeFound(String filter) throws Exception {
        restIntegradorMockMvc.perform(get("/api/integradors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restIntegradorMockMvc.perform(get("/api/integradors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingIntegrador() throws Exception {
        // Get the integrador
        restIntegradorMockMvc.perform(get("/api/integradors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIntegrador() throws Exception {
        // Initialize the database
        integradorRepository.saveAndFlush(integrador);

        int databaseSizeBeforeUpdate = integradorRepository.findAll().size();

        // Update the integrador
        Integrador updatedIntegrador = integradorRepository.findById(integrador.getId()).get();
        // Disconnect from session so that the updates on updatedIntegrador are not directly saved in db
        em.detach(updatedIntegrador);
        updatedIntegrador
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        IntegradorDTO integradorDTO = integradorMapper.toDto(updatedIntegrador);

        restIntegradorMockMvc.perform(put("/api/integradors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(integradorDTO)))
            .andExpect(status().isOk());

        // Validate the Integrador in the database
        List<Integrador> integradorList = integradorRepository.findAll();
        assertThat(integradorList).hasSize(databaseSizeBeforeUpdate);
        Integrador testIntegrador = integradorList.get(integradorList.size() - 1);
        assertThat(testIntegrador.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testIntegrador.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testIntegrador.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testIntegrador.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingIntegrador() throws Exception {
        int databaseSizeBeforeUpdate = integradorRepository.findAll().size();

        // Create the Integrador
        IntegradorDTO integradorDTO = integradorMapper.toDto(integrador);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIntegradorMockMvc.perform(put("/api/integradors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(integradorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Integrador in the database
        List<Integrador> integradorList = integradorRepository.findAll();
        assertThat(integradorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIntegrador() throws Exception {
        // Initialize the database
        integradorRepository.saveAndFlush(integrador);

        int databaseSizeBeforeDelete = integradorRepository.findAll().size();

        // Delete the integrador
        restIntegradorMockMvc.perform(delete("/api/integradors/{id}", integrador.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Integrador> integradorList = integradorRepository.findAll();
        assertThat(integradorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
