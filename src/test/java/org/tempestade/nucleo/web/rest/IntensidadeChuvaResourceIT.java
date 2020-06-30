package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.IntensidadeChuva;
import org.tempestade.nucleo.repository.IntensidadeChuvaRepository;
import org.tempestade.nucleo.service.IntensidadeChuvaService;
import org.tempestade.nucleo.service.dto.IntensidadeChuvaDTO;
import org.tempestade.nucleo.service.mapper.IntensidadeChuvaMapper;
import org.tempestade.nucleo.service.dto.IntensidadeChuvaCriteria;
import org.tempestade.nucleo.service.IntensidadeChuvaQueryService;

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
 * Integration tests for the {@link IntensidadeChuvaResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class IntensidadeChuvaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_FAIXA = "AAAAAAAAAA";
    private static final String UPDATED_FAIXA = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private IntensidadeChuvaRepository intensidadeChuvaRepository;

    @Autowired
    private IntensidadeChuvaMapper intensidadeChuvaMapper;

    @Autowired
    private IntensidadeChuvaService intensidadeChuvaService;

    @Autowired
    private IntensidadeChuvaQueryService intensidadeChuvaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIntensidadeChuvaMockMvc;

    private IntensidadeChuva intensidadeChuva;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IntensidadeChuva createEntity(EntityManager em) {
        IntensidadeChuva intensidadeChuva = new IntensidadeChuva()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .faixa(DEFAULT_FAIXA)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return intensidadeChuva;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IntensidadeChuva createUpdatedEntity(EntityManager em) {
        IntensidadeChuva intensidadeChuva = new IntensidadeChuva()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .faixa(UPDATED_FAIXA)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return intensidadeChuva;
    }

    @BeforeEach
    public void initTest() {
        intensidadeChuva = createEntity(em);
    }

    @Test
    @Transactional
    public void createIntensidadeChuva() throws Exception {
        int databaseSizeBeforeCreate = intensidadeChuvaRepository.findAll().size();
        // Create the IntensidadeChuva
        IntensidadeChuvaDTO intensidadeChuvaDTO = intensidadeChuvaMapper.toDto(intensidadeChuva);
        restIntensidadeChuvaMockMvc.perform(post("/api/intensidade-chuvas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(intensidadeChuvaDTO)))
            .andExpect(status().isCreated());

        // Validate the IntensidadeChuva in the database
        List<IntensidadeChuva> intensidadeChuvaList = intensidadeChuvaRepository.findAll();
        assertThat(intensidadeChuvaList).hasSize(databaseSizeBeforeCreate + 1);
        IntensidadeChuva testIntensidadeChuva = intensidadeChuvaList.get(intensidadeChuvaList.size() - 1);
        assertThat(testIntensidadeChuva.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testIntensidadeChuva.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testIntensidadeChuva.getFaixa()).isEqualTo(DEFAULT_FAIXA);
        assertThat(testIntensidadeChuva.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testIntensidadeChuva.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createIntensidadeChuvaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = intensidadeChuvaRepository.findAll().size();

        // Create the IntensidadeChuva with an existing ID
        intensidadeChuva.setId(1L);
        IntensidadeChuvaDTO intensidadeChuvaDTO = intensidadeChuvaMapper.toDto(intensidadeChuva);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIntensidadeChuvaMockMvc.perform(post("/api/intensidade-chuvas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(intensidadeChuvaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IntensidadeChuva in the database
        List<IntensidadeChuva> intensidadeChuvaList = intensidadeChuvaRepository.findAll();
        assertThat(intensidadeChuvaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = intensidadeChuvaRepository.findAll().size();
        // set the field null
        intensidadeChuva.setNome(null);

        // Create the IntensidadeChuva, which fails.
        IntensidadeChuvaDTO intensidadeChuvaDTO = intensidadeChuvaMapper.toDto(intensidadeChuva);


        restIntensidadeChuvaMockMvc.perform(post("/api/intensidade-chuvas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(intensidadeChuvaDTO)))
            .andExpect(status().isBadRequest());

        List<IntensidadeChuva> intensidadeChuvaList = intensidadeChuvaRepository.findAll();
        assertThat(intensidadeChuvaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = intensidadeChuvaRepository.findAll().size();
        // set the field null
        intensidadeChuva.setDescricao(null);

        // Create the IntensidadeChuva, which fails.
        IntensidadeChuvaDTO intensidadeChuvaDTO = intensidadeChuvaMapper.toDto(intensidadeChuva);


        restIntensidadeChuvaMockMvc.perform(post("/api/intensidade-chuvas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(intensidadeChuvaDTO)))
            .andExpect(status().isBadRequest());

        List<IntensidadeChuva> intensidadeChuvaList = intensidadeChuvaRepository.findAll();
        assertThat(intensidadeChuvaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFaixaIsRequired() throws Exception {
        int databaseSizeBeforeTest = intensidadeChuvaRepository.findAll().size();
        // set the field null
        intensidadeChuva.setFaixa(null);

        // Create the IntensidadeChuva, which fails.
        IntensidadeChuvaDTO intensidadeChuvaDTO = intensidadeChuvaMapper.toDto(intensidadeChuva);


        restIntensidadeChuvaMockMvc.perform(post("/api/intensidade-chuvas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(intensidadeChuvaDTO)))
            .andExpect(status().isBadRequest());

        List<IntensidadeChuva> intensidadeChuvaList = intensidadeChuvaRepository.findAll();
        assertThat(intensidadeChuvaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = intensidadeChuvaRepository.findAll().size();
        // set the field null
        intensidadeChuva.setCreated(null);

        // Create the IntensidadeChuva, which fails.
        IntensidadeChuvaDTO intensidadeChuvaDTO = intensidadeChuvaMapper.toDto(intensidadeChuva);


        restIntensidadeChuvaMockMvc.perform(post("/api/intensidade-chuvas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(intensidadeChuvaDTO)))
            .andExpect(status().isBadRequest());

        List<IntensidadeChuva> intensidadeChuvaList = intensidadeChuvaRepository.findAll();
        assertThat(intensidadeChuvaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIntensidadeChuvas() throws Exception {
        // Initialize the database
        intensidadeChuvaRepository.saveAndFlush(intensidadeChuva);

        // Get all the intensidadeChuvaList
        restIntensidadeChuvaMockMvc.perform(get("/api/intensidade-chuvas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(intensidadeChuva.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].faixa").value(hasItem(DEFAULT_FAIXA)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getIntensidadeChuva() throws Exception {
        // Initialize the database
        intensidadeChuvaRepository.saveAndFlush(intensidadeChuva);

        // Get the intensidadeChuva
        restIntensidadeChuvaMockMvc.perform(get("/api/intensidade-chuvas/{id}", intensidadeChuva.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(intensidadeChuva.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.faixa").value(DEFAULT_FAIXA))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }


    @Test
    @Transactional
    public void getIntensidadeChuvasByIdFiltering() throws Exception {
        // Initialize the database
        intensidadeChuvaRepository.saveAndFlush(intensidadeChuva);

        Long id = intensidadeChuva.getId();

        defaultIntensidadeChuvaShouldBeFound("id.equals=" + id);
        defaultIntensidadeChuvaShouldNotBeFound("id.notEquals=" + id);

        defaultIntensidadeChuvaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultIntensidadeChuvaShouldNotBeFound("id.greaterThan=" + id);

        defaultIntensidadeChuvaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultIntensidadeChuvaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllIntensidadeChuvasByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        intensidadeChuvaRepository.saveAndFlush(intensidadeChuva);

        // Get all the intensidadeChuvaList where nome equals to DEFAULT_NOME
        defaultIntensidadeChuvaShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the intensidadeChuvaList where nome equals to UPDATED_NOME
        defaultIntensidadeChuvaShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllIntensidadeChuvasByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        intensidadeChuvaRepository.saveAndFlush(intensidadeChuva);

        // Get all the intensidadeChuvaList where nome not equals to DEFAULT_NOME
        defaultIntensidadeChuvaShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the intensidadeChuvaList where nome not equals to UPDATED_NOME
        defaultIntensidadeChuvaShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllIntensidadeChuvasByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        intensidadeChuvaRepository.saveAndFlush(intensidadeChuva);

        // Get all the intensidadeChuvaList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultIntensidadeChuvaShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the intensidadeChuvaList where nome equals to UPDATED_NOME
        defaultIntensidadeChuvaShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllIntensidadeChuvasByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        intensidadeChuvaRepository.saveAndFlush(intensidadeChuva);

        // Get all the intensidadeChuvaList where nome is not null
        defaultIntensidadeChuvaShouldBeFound("nome.specified=true");

        // Get all the intensidadeChuvaList where nome is null
        defaultIntensidadeChuvaShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllIntensidadeChuvasByNomeContainsSomething() throws Exception {
        // Initialize the database
        intensidadeChuvaRepository.saveAndFlush(intensidadeChuva);

        // Get all the intensidadeChuvaList where nome contains DEFAULT_NOME
        defaultIntensidadeChuvaShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the intensidadeChuvaList where nome contains UPDATED_NOME
        defaultIntensidadeChuvaShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllIntensidadeChuvasByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        intensidadeChuvaRepository.saveAndFlush(intensidadeChuva);

        // Get all the intensidadeChuvaList where nome does not contain DEFAULT_NOME
        defaultIntensidadeChuvaShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the intensidadeChuvaList where nome does not contain UPDATED_NOME
        defaultIntensidadeChuvaShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllIntensidadeChuvasByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        intensidadeChuvaRepository.saveAndFlush(intensidadeChuva);

        // Get all the intensidadeChuvaList where descricao equals to DEFAULT_DESCRICAO
        defaultIntensidadeChuvaShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the intensidadeChuvaList where descricao equals to UPDATED_DESCRICAO
        defaultIntensidadeChuvaShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllIntensidadeChuvasByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        intensidadeChuvaRepository.saveAndFlush(intensidadeChuva);

        // Get all the intensidadeChuvaList where descricao not equals to DEFAULT_DESCRICAO
        defaultIntensidadeChuvaShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the intensidadeChuvaList where descricao not equals to UPDATED_DESCRICAO
        defaultIntensidadeChuvaShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllIntensidadeChuvasByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        intensidadeChuvaRepository.saveAndFlush(intensidadeChuva);

        // Get all the intensidadeChuvaList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultIntensidadeChuvaShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the intensidadeChuvaList where descricao equals to UPDATED_DESCRICAO
        defaultIntensidadeChuvaShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllIntensidadeChuvasByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        intensidadeChuvaRepository.saveAndFlush(intensidadeChuva);

        // Get all the intensidadeChuvaList where descricao is not null
        defaultIntensidadeChuvaShouldBeFound("descricao.specified=true");

        // Get all the intensidadeChuvaList where descricao is null
        defaultIntensidadeChuvaShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllIntensidadeChuvasByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        intensidadeChuvaRepository.saveAndFlush(intensidadeChuva);

        // Get all the intensidadeChuvaList where descricao contains DEFAULT_DESCRICAO
        defaultIntensidadeChuvaShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the intensidadeChuvaList where descricao contains UPDATED_DESCRICAO
        defaultIntensidadeChuvaShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllIntensidadeChuvasByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        intensidadeChuvaRepository.saveAndFlush(intensidadeChuva);

        // Get all the intensidadeChuvaList where descricao does not contain DEFAULT_DESCRICAO
        defaultIntensidadeChuvaShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the intensidadeChuvaList where descricao does not contain UPDATED_DESCRICAO
        defaultIntensidadeChuvaShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllIntensidadeChuvasByFaixaIsEqualToSomething() throws Exception {
        // Initialize the database
        intensidadeChuvaRepository.saveAndFlush(intensidadeChuva);

        // Get all the intensidadeChuvaList where faixa equals to DEFAULT_FAIXA
        defaultIntensidadeChuvaShouldBeFound("faixa.equals=" + DEFAULT_FAIXA);

        // Get all the intensidadeChuvaList where faixa equals to UPDATED_FAIXA
        defaultIntensidadeChuvaShouldNotBeFound("faixa.equals=" + UPDATED_FAIXA);
    }

    @Test
    @Transactional
    public void getAllIntensidadeChuvasByFaixaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        intensidadeChuvaRepository.saveAndFlush(intensidadeChuva);

        // Get all the intensidadeChuvaList where faixa not equals to DEFAULT_FAIXA
        defaultIntensidadeChuvaShouldNotBeFound("faixa.notEquals=" + DEFAULT_FAIXA);

        // Get all the intensidadeChuvaList where faixa not equals to UPDATED_FAIXA
        defaultIntensidadeChuvaShouldBeFound("faixa.notEquals=" + UPDATED_FAIXA);
    }

    @Test
    @Transactional
    public void getAllIntensidadeChuvasByFaixaIsInShouldWork() throws Exception {
        // Initialize the database
        intensidadeChuvaRepository.saveAndFlush(intensidadeChuva);

        // Get all the intensidadeChuvaList where faixa in DEFAULT_FAIXA or UPDATED_FAIXA
        defaultIntensidadeChuvaShouldBeFound("faixa.in=" + DEFAULT_FAIXA + "," + UPDATED_FAIXA);

        // Get all the intensidadeChuvaList where faixa equals to UPDATED_FAIXA
        defaultIntensidadeChuvaShouldNotBeFound("faixa.in=" + UPDATED_FAIXA);
    }

    @Test
    @Transactional
    public void getAllIntensidadeChuvasByFaixaIsNullOrNotNull() throws Exception {
        // Initialize the database
        intensidadeChuvaRepository.saveAndFlush(intensidadeChuva);

        // Get all the intensidadeChuvaList where faixa is not null
        defaultIntensidadeChuvaShouldBeFound("faixa.specified=true");

        // Get all the intensidadeChuvaList where faixa is null
        defaultIntensidadeChuvaShouldNotBeFound("faixa.specified=false");
    }
                @Test
    @Transactional
    public void getAllIntensidadeChuvasByFaixaContainsSomething() throws Exception {
        // Initialize the database
        intensidadeChuvaRepository.saveAndFlush(intensidadeChuva);

        // Get all the intensidadeChuvaList where faixa contains DEFAULT_FAIXA
        defaultIntensidadeChuvaShouldBeFound("faixa.contains=" + DEFAULT_FAIXA);

        // Get all the intensidadeChuvaList where faixa contains UPDATED_FAIXA
        defaultIntensidadeChuvaShouldNotBeFound("faixa.contains=" + UPDATED_FAIXA);
    }

    @Test
    @Transactional
    public void getAllIntensidadeChuvasByFaixaNotContainsSomething() throws Exception {
        // Initialize the database
        intensidadeChuvaRepository.saveAndFlush(intensidadeChuva);

        // Get all the intensidadeChuvaList where faixa does not contain DEFAULT_FAIXA
        defaultIntensidadeChuvaShouldNotBeFound("faixa.doesNotContain=" + DEFAULT_FAIXA);

        // Get all the intensidadeChuvaList where faixa does not contain UPDATED_FAIXA
        defaultIntensidadeChuvaShouldBeFound("faixa.doesNotContain=" + UPDATED_FAIXA);
    }


    @Test
    @Transactional
    public void getAllIntensidadeChuvasByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        intensidadeChuvaRepository.saveAndFlush(intensidadeChuva);

        // Get all the intensidadeChuvaList where created equals to DEFAULT_CREATED
        defaultIntensidadeChuvaShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the intensidadeChuvaList where created equals to UPDATED_CREATED
        defaultIntensidadeChuvaShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllIntensidadeChuvasByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        intensidadeChuvaRepository.saveAndFlush(intensidadeChuva);

        // Get all the intensidadeChuvaList where created not equals to DEFAULT_CREATED
        defaultIntensidadeChuvaShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the intensidadeChuvaList where created not equals to UPDATED_CREATED
        defaultIntensidadeChuvaShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllIntensidadeChuvasByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        intensidadeChuvaRepository.saveAndFlush(intensidadeChuva);

        // Get all the intensidadeChuvaList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultIntensidadeChuvaShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the intensidadeChuvaList where created equals to UPDATED_CREATED
        defaultIntensidadeChuvaShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllIntensidadeChuvasByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        intensidadeChuvaRepository.saveAndFlush(intensidadeChuva);

        // Get all the intensidadeChuvaList where created is not null
        defaultIntensidadeChuvaShouldBeFound("created.specified=true");

        // Get all the intensidadeChuvaList where created is null
        defaultIntensidadeChuvaShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllIntensidadeChuvasByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        intensidadeChuvaRepository.saveAndFlush(intensidadeChuva);

        // Get all the intensidadeChuvaList where updated equals to DEFAULT_UPDATED
        defaultIntensidadeChuvaShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the intensidadeChuvaList where updated equals to UPDATED_UPDATED
        defaultIntensidadeChuvaShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllIntensidadeChuvasByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        intensidadeChuvaRepository.saveAndFlush(intensidadeChuva);

        // Get all the intensidadeChuvaList where updated not equals to DEFAULT_UPDATED
        defaultIntensidadeChuvaShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the intensidadeChuvaList where updated not equals to UPDATED_UPDATED
        defaultIntensidadeChuvaShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllIntensidadeChuvasByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        intensidadeChuvaRepository.saveAndFlush(intensidadeChuva);

        // Get all the intensidadeChuvaList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultIntensidadeChuvaShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the intensidadeChuvaList where updated equals to UPDATED_UPDATED
        defaultIntensidadeChuvaShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllIntensidadeChuvasByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        intensidadeChuvaRepository.saveAndFlush(intensidadeChuva);

        // Get all the intensidadeChuvaList where updated is not null
        defaultIntensidadeChuvaShouldBeFound("updated.specified=true");

        // Get all the intensidadeChuvaList where updated is null
        defaultIntensidadeChuvaShouldNotBeFound("updated.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultIntensidadeChuvaShouldBeFound(String filter) throws Exception {
        restIntensidadeChuvaMockMvc.perform(get("/api/intensidade-chuvas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(intensidadeChuva.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].faixa").value(hasItem(DEFAULT_FAIXA)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restIntensidadeChuvaMockMvc.perform(get("/api/intensidade-chuvas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultIntensidadeChuvaShouldNotBeFound(String filter) throws Exception {
        restIntensidadeChuvaMockMvc.perform(get("/api/intensidade-chuvas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restIntensidadeChuvaMockMvc.perform(get("/api/intensidade-chuvas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingIntensidadeChuva() throws Exception {
        // Get the intensidadeChuva
        restIntensidadeChuvaMockMvc.perform(get("/api/intensidade-chuvas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIntensidadeChuva() throws Exception {
        // Initialize the database
        intensidadeChuvaRepository.saveAndFlush(intensidadeChuva);

        int databaseSizeBeforeUpdate = intensidadeChuvaRepository.findAll().size();

        // Update the intensidadeChuva
        IntensidadeChuva updatedIntensidadeChuva = intensidadeChuvaRepository.findById(intensidadeChuva.getId()).get();
        // Disconnect from session so that the updates on updatedIntensidadeChuva are not directly saved in db
        em.detach(updatedIntensidadeChuva);
        updatedIntensidadeChuva
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .faixa(UPDATED_FAIXA)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        IntensidadeChuvaDTO intensidadeChuvaDTO = intensidadeChuvaMapper.toDto(updatedIntensidadeChuva);

        restIntensidadeChuvaMockMvc.perform(put("/api/intensidade-chuvas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(intensidadeChuvaDTO)))
            .andExpect(status().isOk());

        // Validate the IntensidadeChuva in the database
        List<IntensidadeChuva> intensidadeChuvaList = intensidadeChuvaRepository.findAll();
        assertThat(intensidadeChuvaList).hasSize(databaseSizeBeforeUpdate);
        IntensidadeChuva testIntensidadeChuva = intensidadeChuvaList.get(intensidadeChuvaList.size() - 1);
        assertThat(testIntensidadeChuva.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testIntensidadeChuva.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testIntensidadeChuva.getFaixa()).isEqualTo(UPDATED_FAIXA);
        assertThat(testIntensidadeChuva.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testIntensidadeChuva.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingIntensidadeChuva() throws Exception {
        int databaseSizeBeforeUpdate = intensidadeChuvaRepository.findAll().size();

        // Create the IntensidadeChuva
        IntensidadeChuvaDTO intensidadeChuvaDTO = intensidadeChuvaMapper.toDto(intensidadeChuva);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIntensidadeChuvaMockMvc.perform(put("/api/intensidade-chuvas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(intensidadeChuvaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IntensidadeChuva in the database
        List<IntensidadeChuva> intensidadeChuvaList = intensidadeChuvaRepository.findAll();
        assertThat(intensidadeChuvaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIntensidadeChuva() throws Exception {
        // Initialize the database
        intensidadeChuvaRepository.saveAndFlush(intensidadeChuva);

        int databaseSizeBeforeDelete = intensidadeChuvaRepository.findAll().size();

        // Delete the intensidadeChuva
        restIntensidadeChuvaMockMvc.perform(delete("/api/intensidade-chuvas/{id}", intensidadeChuva.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IntensidadeChuva> intensidadeChuvaList = intensidadeChuvaRepository.findAll();
        assertThat(intensidadeChuvaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
