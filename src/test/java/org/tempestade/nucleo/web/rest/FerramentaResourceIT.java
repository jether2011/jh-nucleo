package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.Ferramenta;
import org.tempestade.nucleo.domain.TipoFerramenta;
import org.tempestade.nucleo.repository.FerramentaRepository;
import org.tempestade.nucleo.service.FerramentaService;
import org.tempestade.nucleo.service.dto.FerramentaDTO;
import org.tempestade.nucleo.service.mapper.FerramentaMapper;
import org.tempestade.nucleo.service.dto.FerramentaCriteria;
import org.tempestade.nucleo.service.FerramentaQueryService;

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
 * Integration tests for the {@link FerramentaResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FerramentaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_UPDATED = 1;
    private static final Integer UPDATED_UPDATED = 2;
    private static final Integer SMALLER_UPDATED = 1 - 1;

    @Autowired
    private FerramentaRepository ferramentaRepository;

    @Autowired
    private FerramentaMapper ferramentaMapper;

    @Autowired
    private FerramentaService ferramentaService;

    @Autowired
    private FerramentaQueryService ferramentaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFerramentaMockMvc;

    private Ferramenta ferramenta;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ferramenta createEntity(EntityManager em) {
        Ferramenta ferramenta = new Ferramenta()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return ferramenta;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ferramenta createUpdatedEntity(EntityManager em) {
        Ferramenta ferramenta = new Ferramenta()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return ferramenta;
    }

    @BeforeEach
    public void initTest() {
        ferramenta = createEntity(em);
    }

    @Test
    @Transactional
    public void createFerramenta() throws Exception {
        int databaseSizeBeforeCreate = ferramentaRepository.findAll().size();
        // Create the Ferramenta
        FerramentaDTO ferramentaDTO = ferramentaMapper.toDto(ferramenta);
        restFerramentaMockMvc.perform(post("/api/ferramentas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ferramentaDTO)))
            .andExpect(status().isCreated());

        // Validate the Ferramenta in the database
        List<Ferramenta> ferramentaList = ferramentaRepository.findAll();
        assertThat(ferramentaList).hasSize(databaseSizeBeforeCreate + 1);
        Ferramenta testFerramenta = ferramentaList.get(ferramentaList.size() - 1);
        assertThat(testFerramenta.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testFerramenta.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testFerramenta.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testFerramenta.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createFerramentaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ferramentaRepository.findAll().size();

        // Create the Ferramenta with an existing ID
        ferramenta.setId(1L);
        FerramentaDTO ferramentaDTO = ferramentaMapper.toDto(ferramenta);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFerramentaMockMvc.perform(post("/api/ferramentas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ferramentaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ferramenta in the database
        List<Ferramenta> ferramentaList = ferramentaRepository.findAll();
        assertThat(ferramentaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = ferramentaRepository.findAll().size();
        // set the field null
        ferramenta.setNome(null);

        // Create the Ferramenta, which fails.
        FerramentaDTO ferramentaDTO = ferramentaMapper.toDto(ferramenta);


        restFerramentaMockMvc.perform(post("/api/ferramentas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ferramentaDTO)))
            .andExpect(status().isBadRequest());

        List<Ferramenta> ferramentaList = ferramentaRepository.findAll();
        assertThat(ferramentaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = ferramentaRepository.findAll().size();
        // set the field null
        ferramenta.setCreated(null);

        // Create the Ferramenta, which fails.
        FerramentaDTO ferramentaDTO = ferramentaMapper.toDto(ferramenta);


        restFerramentaMockMvc.perform(post("/api/ferramentas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ferramentaDTO)))
            .andExpect(status().isBadRequest());

        List<Ferramenta> ferramentaList = ferramentaRepository.findAll();
        assertThat(ferramentaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFerramentas() throws Exception {
        // Initialize the database
        ferramentaRepository.saveAndFlush(ferramenta);

        // Get all the ferramentaList
        restFerramentaMockMvc.perform(get("/api/ferramentas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ferramenta.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED)));
    }
    
    @Test
    @Transactional
    public void getFerramenta() throws Exception {
        // Initialize the database
        ferramentaRepository.saveAndFlush(ferramenta);

        // Get the ferramenta
        restFerramentaMockMvc.perform(get("/api/ferramentas/{id}", ferramenta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ferramenta.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED));
    }


    @Test
    @Transactional
    public void getFerramentasByIdFiltering() throws Exception {
        // Initialize the database
        ferramentaRepository.saveAndFlush(ferramenta);

        Long id = ferramenta.getId();

        defaultFerramentaShouldBeFound("id.equals=" + id);
        defaultFerramentaShouldNotBeFound("id.notEquals=" + id);

        defaultFerramentaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFerramentaShouldNotBeFound("id.greaterThan=" + id);

        defaultFerramentaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFerramentaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllFerramentasByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        ferramentaRepository.saveAndFlush(ferramenta);

        // Get all the ferramentaList where nome equals to DEFAULT_NOME
        defaultFerramentaShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the ferramentaList where nome equals to UPDATED_NOME
        defaultFerramentaShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllFerramentasByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ferramentaRepository.saveAndFlush(ferramenta);

        // Get all the ferramentaList where nome not equals to DEFAULT_NOME
        defaultFerramentaShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the ferramentaList where nome not equals to UPDATED_NOME
        defaultFerramentaShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllFerramentasByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        ferramentaRepository.saveAndFlush(ferramenta);

        // Get all the ferramentaList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultFerramentaShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the ferramentaList where nome equals to UPDATED_NOME
        defaultFerramentaShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllFerramentasByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        ferramentaRepository.saveAndFlush(ferramenta);

        // Get all the ferramentaList where nome is not null
        defaultFerramentaShouldBeFound("nome.specified=true");

        // Get all the ferramentaList where nome is null
        defaultFerramentaShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllFerramentasByNomeContainsSomething() throws Exception {
        // Initialize the database
        ferramentaRepository.saveAndFlush(ferramenta);

        // Get all the ferramentaList where nome contains DEFAULT_NOME
        defaultFerramentaShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the ferramentaList where nome contains UPDATED_NOME
        defaultFerramentaShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllFerramentasByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        ferramentaRepository.saveAndFlush(ferramenta);

        // Get all the ferramentaList where nome does not contain DEFAULT_NOME
        defaultFerramentaShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the ferramentaList where nome does not contain UPDATED_NOME
        defaultFerramentaShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllFerramentasByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        ferramentaRepository.saveAndFlush(ferramenta);

        // Get all the ferramentaList where descricao equals to DEFAULT_DESCRICAO
        defaultFerramentaShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the ferramentaList where descricao equals to UPDATED_DESCRICAO
        defaultFerramentaShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllFerramentasByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ferramentaRepository.saveAndFlush(ferramenta);

        // Get all the ferramentaList where descricao not equals to DEFAULT_DESCRICAO
        defaultFerramentaShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the ferramentaList where descricao not equals to UPDATED_DESCRICAO
        defaultFerramentaShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllFerramentasByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        ferramentaRepository.saveAndFlush(ferramenta);

        // Get all the ferramentaList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultFerramentaShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the ferramentaList where descricao equals to UPDATED_DESCRICAO
        defaultFerramentaShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllFerramentasByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        ferramentaRepository.saveAndFlush(ferramenta);

        // Get all the ferramentaList where descricao is not null
        defaultFerramentaShouldBeFound("descricao.specified=true");

        // Get all the ferramentaList where descricao is null
        defaultFerramentaShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllFerramentasByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        ferramentaRepository.saveAndFlush(ferramenta);

        // Get all the ferramentaList where descricao contains DEFAULT_DESCRICAO
        defaultFerramentaShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the ferramentaList where descricao contains UPDATED_DESCRICAO
        defaultFerramentaShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllFerramentasByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        ferramentaRepository.saveAndFlush(ferramenta);

        // Get all the ferramentaList where descricao does not contain DEFAULT_DESCRICAO
        defaultFerramentaShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the ferramentaList where descricao does not contain UPDATED_DESCRICAO
        defaultFerramentaShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllFerramentasByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        ferramentaRepository.saveAndFlush(ferramenta);

        // Get all the ferramentaList where created equals to DEFAULT_CREATED
        defaultFerramentaShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the ferramentaList where created equals to UPDATED_CREATED
        defaultFerramentaShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllFerramentasByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ferramentaRepository.saveAndFlush(ferramenta);

        // Get all the ferramentaList where created not equals to DEFAULT_CREATED
        defaultFerramentaShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the ferramentaList where created not equals to UPDATED_CREATED
        defaultFerramentaShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllFerramentasByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        ferramentaRepository.saveAndFlush(ferramenta);

        // Get all the ferramentaList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultFerramentaShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the ferramentaList where created equals to UPDATED_CREATED
        defaultFerramentaShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllFerramentasByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        ferramentaRepository.saveAndFlush(ferramenta);

        // Get all the ferramentaList where created is not null
        defaultFerramentaShouldBeFound("created.specified=true");

        // Get all the ferramentaList where created is null
        defaultFerramentaShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllFerramentasByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        ferramentaRepository.saveAndFlush(ferramenta);

        // Get all the ferramentaList where updated equals to DEFAULT_UPDATED
        defaultFerramentaShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the ferramentaList where updated equals to UPDATED_UPDATED
        defaultFerramentaShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllFerramentasByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ferramentaRepository.saveAndFlush(ferramenta);

        // Get all the ferramentaList where updated not equals to DEFAULT_UPDATED
        defaultFerramentaShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the ferramentaList where updated not equals to UPDATED_UPDATED
        defaultFerramentaShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllFerramentasByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        ferramentaRepository.saveAndFlush(ferramenta);

        // Get all the ferramentaList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultFerramentaShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the ferramentaList where updated equals to UPDATED_UPDATED
        defaultFerramentaShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllFerramentasByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        ferramentaRepository.saveAndFlush(ferramenta);

        // Get all the ferramentaList where updated is not null
        defaultFerramentaShouldBeFound("updated.specified=true");

        // Get all the ferramentaList where updated is null
        defaultFerramentaShouldNotBeFound("updated.specified=false");
    }

    @Test
    @Transactional
    public void getAllFerramentasByUpdatedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ferramentaRepository.saveAndFlush(ferramenta);

        // Get all the ferramentaList where updated is greater than or equal to DEFAULT_UPDATED
        defaultFerramentaShouldBeFound("updated.greaterThanOrEqual=" + DEFAULT_UPDATED);

        // Get all the ferramentaList where updated is greater than or equal to UPDATED_UPDATED
        defaultFerramentaShouldNotBeFound("updated.greaterThanOrEqual=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllFerramentasByUpdatedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ferramentaRepository.saveAndFlush(ferramenta);

        // Get all the ferramentaList where updated is less than or equal to DEFAULT_UPDATED
        defaultFerramentaShouldBeFound("updated.lessThanOrEqual=" + DEFAULT_UPDATED);

        // Get all the ferramentaList where updated is less than or equal to SMALLER_UPDATED
        defaultFerramentaShouldNotBeFound("updated.lessThanOrEqual=" + SMALLER_UPDATED);
    }

    @Test
    @Transactional
    public void getAllFerramentasByUpdatedIsLessThanSomething() throws Exception {
        // Initialize the database
        ferramentaRepository.saveAndFlush(ferramenta);

        // Get all the ferramentaList where updated is less than DEFAULT_UPDATED
        defaultFerramentaShouldNotBeFound("updated.lessThan=" + DEFAULT_UPDATED);

        // Get all the ferramentaList where updated is less than UPDATED_UPDATED
        defaultFerramentaShouldBeFound("updated.lessThan=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllFerramentasByUpdatedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ferramentaRepository.saveAndFlush(ferramenta);

        // Get all the ferramentaList where updated is greater than DEFAULT_UPDATED
        defaultFerramentaShouldNotBeFound("updated.greaterThan=" + DEFAULT_UPDATED);

        // Get all the ferramentaList where updated is greater than SMALLER_UPDATED
        defaultFerramentaShouldBeFound("updated.greaterThan=" + SMALLER_UPDATED);
    }


    @Test
    @Transactional
    public void getAllFerramentasByTipoFerramentaIsEqualToSomething() throws Exception {
        // Initialize the database
        ferramentaRepository.saveAndFlush(ferramenta);
        TipoFerramenta tipoFerramenta = TipoFerramentaResourceIT.createEntity(em);
        em.persist(tipoFerramenta);
        em.flush();
        ferramenta.setTipoFerramenta(tipoFerramenta);
        ferramentaRepository.saveAndFlush(ferramenta);
        Long tipoFerramentaId = tipoFerramenta.getId();

        // Get all the ferramentaList where tipoFerramenta equals to tipoFerramentaId
        defaultFerramentaShouldBeFound("tipoFerramentaId.equals=" + tipoFerramentaId);

        // Get all the ferramentaList where tipoFerramenta equals to tipoFerramentaId + 1
        defaultFerramentaShouldNotBeFound("tipoFerramentaId.equals=" + (tipoFerramentaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFerramentaShouldBeFound(String filter) throws Exception {
        restFerramentaMockMvc.perform(get("/api/ferramentas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ferramenta.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED)));

        // Check, that the count call also returns 1
        restFerramentaMockMvc.perform(get("/api/ferramentas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFerramentaShouldNotBeFound(String filter) throws Exception {
        restFerramentaMockMvc.perform(get("/api/ferramentas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFerramentaMockMvc.perform(get("/api/ferramentas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingFerramenta() throws Exception {
        // Get the ferramenta
        restFerramentaMockMvc.perform(get("/api/ferramentas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFerramenta() throws Exception {
        // Initialize the database
        ferramentaRepository.saveAndFlush(ferramenta);

        int databaseSizeBeforeUpdate = ferramentaRepository.findAll().size();

        // Update the ferramenta
        Ferramenta updatedFerramenta = ferramentaRepository.findById(ferramenta.getId()).get();
        // Disconnect from session so that the updates on updatedFerramenta are not directly saved in db
        em.detach(updatedFerramenta);
        updatedFerramenta
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        FerramentaDTO ferramentaDTO = ferramentaMapper.toDto(updatedFerramenta);

        restFerramentaMockMvc.perform(put("/api/ferramentas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ferramentaDTO)))
            .andExpect(status().isOk());

        // Validate the Ferramenta in the database
        List<Ferramenta> ferramentaList = ferramentaRepository.findAll();
        assertThat(ferramentaList).hasSize(databaseSizeBeforeUpdate);
        Ferramenta testFerramenta = ferramentaList.get(ferramentaList.size() - 1);
        assertThat(testFerramenta.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testFerramenta.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testFerramenta.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testFerramenta.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingFerramenta() throws Exception {
        int databaseSizeBeforeUpdate = ferramentaRepository.findAll().size();

        // Create the Ferramenta
        FerramentaDTO ferramentaDTO = ferramentaMapper.toDto(ferramenta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFerramentaMockMvc.perform(put("/api/ferramentas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ferramentaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ferramenta in the database
        List<Ferramenta> ferramentaList = ferramentaRepository.findAll();
        assertThat(ferramentaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFerramenta() throws Exception {
        // Initialize the database
        ferramentaRepository.saveAndFlush(ferramenta);

        int databaseSizeBeforeDelete = ferramentaRepository.findAll().size();

        // Delete the ferramenta
        restFerramentaMockMvc.perform(delete("/api/ferramentas/{id}", ferramenta.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ferramenta> ferramentaList = ferramentaRepository.findAll();
        assertThat(ferramentaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
