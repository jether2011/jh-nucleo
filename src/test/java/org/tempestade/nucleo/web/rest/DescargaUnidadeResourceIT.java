package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.DescargaUnidade;
import org.tempestade.nucleo.repository.DescargaUnidadeRepository;
import org.tempestade.nucleo.service.DescargaUnidadeService;
import org.tempestade.nucleo.service.dto.DescargaUnidadeDTO;
import org.tempestade.nucleo.service.mapper.DescargaUnidadeMapper;
import org.tempestade.nucleo.service.dto.DescargaUnidadeCriteria;
import org.tempestade.nucleo.service.DescargaUnidadeQueryService;

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
 * Integration tests for the {@link DescargaUnidadeResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DescargaUnidadeResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Integer DEFAULT_UNIDADE = 1;
    private static final Integer UPDATED_UNIDADE = 2;
    private static final Integer SMALLER_UNIDADE = 1 - 1;

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private DescargaUnidadeRepository descargaUnidadeRepository;

    @Autowired
    private DescargaUnidadeMapper descargaUnidadeMapper;

    @Autowired
    private DescargaUnidadeService descargaUnidadeService;

    @Autowired
    private DescargaUnidadeQueryService descargaUnidadeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDescargaUnidadeMockMvc;

    private DescargaUnidade descargaUnidade;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DescargaUnidade createEntity(EntityManager em) {
        DescargaUnidade descargaUnidade = new DescargaUnidade()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .unidade(DEFAULT_UNIDADE)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return descargaUnidade;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DescargaUnidade createUpdatedEntity(EntityManager em) {
        DescargaUnidade descargaUnidade = new DescargaUnidade()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .unidade(UPDATED_UNIDADE)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return descargaUnidade;
    }

    @BeforeEach
    public void initTest() {
        descargaUnidade = createEntity(em);
    }

    @Test
    @Transactional
    public void createDescargaUnidade() throws Exception {
        int databaseSizeBeforeCreate = descargaUnidadeRepository.findAll().size();
        // Create the DescargaUnidade
        DescargaUnidadeDTO descargaUnidadeDTO = descargaUnidadeMapper.toDto(descargaUnidade);
        restDescargaUnidadeMockMvc.perform(post("/api/descarga-unidades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(descargaUnidadeDTO)))
            .andExpect(status().isCreated());

        // Validate the DescargaUnidade in the database
        List<DescargaUnidade> descargaUnidadeList = descargaUnidadeRepository.findAll();
        assertThat(descargaUnidadeList).hasSize(databaseSizeBeforeCreate + 1);
        DescargaUnidade testDescargaUnidade = descargaUnidadeList.get(descargaUnidadeList.size() - 1);
        assertThat(testDescargaUnidade.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testDescargaUnidade.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testDescargaUnidade.getUnidade()).isEqualTo(DEFAULT_UNIDADE);
        assertThat(testDescargaUnidade.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testDescargaUnidade.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createDescargaUnidadeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = descargaUnidadeRepository.findAll().size();

        // Create the DescargaUnidade with an existing ID
        descargaUnidade.setId(1L);
        DescargaUnidadeDTO descargaUnidadeDTO = descargaUnidadeMapper.toDto(descargaUnidade);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDescargaUnidadeMockMvc.perform(post("/api/descarga-unidades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(descargaUnidadeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DescargaUnidade in the database
        List<DescargaUnidade> descargaUnidadeList = descargaUnidadeRepository.findAll();
        assertThat(descargaUnidadeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = descargaUnidadeRepository.findAll().size();
        // set the field null
        descargaUnidade.setNome(null);

        // Create the DescargaUnidade, which fails.
        DescargaUnidadeDTO descargaUnidadeDTO = descargaUnidadeMapper.toDto(descargaUnidade);


        restDescargaUnidadeMockMvc.perform(post("/api/descarga-unidades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(descargaUnidadeDTO)))
            .andExpect(status().isBadRequest());

        List<DescargaUnidade> descargaUnidadeList = descargaUnidadeRepository.findAll();
        assertThat(descargaUnidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = descargaUnidadeRepository.findAll().size();
        // set the field null
        descargaUnidade.setUnidade(null);

        // Create the DescargaUnidade, which fails.
        DescargaUnidadeDTO descargaUnidadeDTO = descargaUnidadeMapper.toDto(descargaUnidade);


        restDescargaUnidadeMockMvc.perform(post("/api/descarga-unidades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(descargaUnidadeDTO)))
            .andExpect(status().isBadRequest());

        List<DescargaUnidade> descargaUnidadeList = descargaUnidadeRepository.findAll();
        assertThat(descargaUnidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = descargaUnidadeRepository.findAll().size();
        // set the field null
        descargaUnidade.setCreated(null);

        // Create the DescargaUnidade, which fails.
        DescargaUnidadeDTO descargaUnidadeDTO = descargaUnidadeMapper.toDto(descargaUnidade);


        restDescargaUnidadeMockMvc.perform(post("/api/descarga-unidades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(descargaUnidadeDTO)))
            .andExpect(status().isBadRequest());

        List<DescargaUnidade> descargaUnidadeList = descargaUnidadeRepository.findAll();
        assertThat(descargaUnidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDescargaUnidades() throws Exception {
        // Initialize the database
        descargaUnidadeRepository.saveAndFlush(descargaUnidade);

        // Get all the descargaUnidadeList
        restDescargaUnidadeMockMvc.perform(get("/api/descarga-unidades?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(descargaUnidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].unidade").value(hasItem(DEFAULT_UNIDADE)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getDescargaUnidade() throws Exception {
        // Initialize the database
        descargaUnidadeRepository.saveAndFlush(descargaUnidade);

        // Get the descargaUnidade
        restDescargaUnidadeMockMvc.perform(get("/api/descarga-unidades/{id}", descargaUnidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(descargaUnidade.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.unidade").value(DEFAULT_UNIDADE))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }


    @Test
    @Transactional
    public void getDescargaUnidadesByIdFiltering() throws Exception {
        // Initialize the database
        descargaUnidadeRepository.saveAndFlush(descargaUnidade);

        Long id = descargaUnidade.getId();

        defaultDescargaUnidadeShouldBeFound("id.equals=" + id);
        defaultDescargaUnidadeShouldNotBeFound("id.notEquals=" + id);

        defaultDescargaUnidadeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDescargaUnidadeShouldNotBeFound("id.greaterThan=" + id);

        defaultDescargaUnidadeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDescargaUnidadeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDescargaUnidadesByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        descargaUnidadeRepository.saveAndFlush(descargaUnidade);

        // Get all the descargaUnidadeList where nome equals to DEFAULT_NOME
        defaultDescargaUnidadeShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the descargaUnidadeList where nome equals to UPDATED_NOME
        defaultDescargaUnidadeShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllDescargaUnidadesByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        descargaUnidadeRepository.saveAndFlush(descargaUnidade);

        // Get all the descargaUnidadeList where nome not equals to DEFAULT_NOME
        defaultDescargaUnidadeShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the descargaUnidadeList where nome not equals to UPDATED_NOME
        defaultDescargaUnidadeShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllDescargaUnidadesByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        descargaUnidadeRepository.saveAndFlush(descargaUnidade);

        // Get all the descargaUnidadeList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultDescargaUnidadeShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the descargaUnidadeList where nome equals to UPDATED_NOME
        defaultDescargaUnidadeShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllDescargaUnidadesByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        descargaUnidadeRepository.saveAndFlush(descargaUnidade);

        // Get all the descargaUnidadeList where nome is not null
        defaultDescargaUnidadeShouldBeFound("nome.specified=true");

        // Get all the descargaUnidadeList where nome is null
        defaultDescargaUnidadeShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllDescargaUnidadesByNomeContainsSomething() throws Exception {
        // Initialize the database
        descargaUnidadeRepository.saveAndFlush(descargaUnidade);

        // Get all the descargaUnidadeList where nome contains DEFAULT_NOME
        defaultDescargaUnidadeShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the descargaUnidadeList where nome contains UPDATED_NOME
        defaultDescargaUnidadeShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllDescargaUnidadesByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        descargaUnidadeRepository.saveAndFlush(descargaUnidade);

        // Get all the descargaUnidadeList where nome does not contain DEFAULT_NOME
        defaultDescargaUnidadeShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the descargaUnidadeList where nome does not contain UPDATED_NOME
        defaultDescargaUnidadeShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllDescargaUnidadesByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        descargaUnidadeRepository.saveAndFlush(descargaUnidade);

        // Get all the descargaUnidadeList where descricao equals to DEFAULT_DESCRICAO
        defaultDescargaUnidadeShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the descargaUnidadeList where descricao equals to UPDATED_DESCRICAO
        defaultDescargaUnidadeShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllDescargaUnidadesByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        descargaUnidadeRepository.saveAndFlush(descargaUnidade);

        // Get all the descargaUnidadeList where descricao not equals to DEFAULT_DESCRICAO
        defaultDescargaUnidadeShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the descargaUnidadeList where descricao not equals to UPDATED_DESCRICAO
        defaultDescargaUnidadeShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllDescargaUnidadesByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        descargaUnidadeRepository.saveAndFlush(descargaUnidade);

        // Get all the descargaUnidadeList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultDescargaUnidadeShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the descargaUnidadeList where descricao equals to UPDATED_DESCRICAO
        defaultDescargaUnidadeShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllDescargaUnidadesByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        descargaUnidadeRepository.saveAndFlush(descargaUnidade);

        // Get all the descargaUnidadeList where descricao is not null
        defaultDescargaUnidadeShouldBeFound("descricao.specified=true");

        // Get all the descargaUnidadeList where descricao is null
        defaultDescargaUnidadeShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllDescargaUnidadesByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        descargaUnidadeRepository.saveAndFlush(descargaUnidade);

        // Get all the descargaUnidadeList where descricao contains DEFAULT_DESCRICAO
        defaultDescargaUnidadeShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the descargaUnidadeList where descricao contains UPDATED_DESCRICAO
        defaultDescargaUnidadeShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllDescargaUnidadesByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        descargaUnidadeRepository.saveAndFlush(descargaUnidade);

        // Get all the descargaUnidadeList where descricao does not contain DEFAULT_DESCRICAO
        defaultDescargaUnidadeShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the descargaUnidadeList where descricao does not contain UPDATED_DESCRICAO
        defaultDescargaUnidadeShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllDescargaUnidadesByUnidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        descargaUnidadeRepository.saveAndFlush(descargaUnidade);

        // Get all the descargaUnidadeList where unidade equals to DEFAULT_UNIDADE
        defaultDescargaUnidadeShouldBeFound("unidade.equals=" + DEFAULT_UNIDADE);

        // Get all the descargaUnidadeList where unidade equals to UPDATED_UNIDADE
        defaultDescargaUnidadeShouldNotBeFound("unidade.equals=" + UPDATED_UNIDADE);
    }

    @Test
    @Transactional
    public void getAllDescargaUnidadesByUnidadeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        descargaUnidadeRepository.saveAndFlush(descargaUnidade);

        // Get all the descargaUnidadeList where unidade not equals to DEFAULT_UNIDADE
        defaultDescargaUnidadeShouldNotBeFound("unidade.notEquals=" + DEFAULT_UNIDADE);

        // Get all the descargaUnidadeList where unidade not equals to UPDATED_UNIDADE
        defaultDescargaUnidadeShouldBeFound("unidade.notEquals=" + UPDATED_UNIDADE);
    }

    @Test
    @Transactional
    public void getAllDescargaUnidadesByUnidadeIsInShouldWork() throws Exception {
        // Initialize the database
        descargaUnidadeRepository.saveAndFlush(descargaUnidade);

        // Get all the descargaUnidadeList where unidade in DEFAULT_UNIDADE or UPDATED_UNIDADE
        defaultDescargaUnidadeShouldBeFound("unidade.in=" + DEFAULT_UNIDADE + "," + UPDATED_UNIDADE);

        // Get all the descargaUnidadeList where unidade equals to UPDATED_UNIDADE
        defaultDescargaUnidadeShouldNotBeFound("unidade.in=" + UPDATED_UNIDADE);
    }

    @Test
    @Transactional
    public void getAllDescargaUnidadesByUnidadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        descargaUnidadeRepository.saveAndFlush(descargaUnidade);

        // Get all the descargaUnidadeList where unidade is not null
        defaultDescargaUnidadeShouldBeFound("unidade.specified=true");

        // Get all the descargaUnidadeList where unidade is null
        defaultDescargaUnidadeShouldNotBeFound("unidade.specified=false");
    }

    @Test
    @Transactional
    public void getAllDescargaUnidadesByUnidadeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        descargaUnidadeRepository.saveAndFlush(descargaUnidade);

        // Get all the descargaUnidadeList where unidade is greater than or equal to DEFAULT_UNIDADE
        defaultDescargaUnidadeShouldBeFound("unidade.greaterThanOrEqual=" + DEFAULT_UNIDADE);

        // Get all the descargaUnidadeList where unidade is greater than or equal to UPDATED_UNIDADE
        defaultDescargaUnidadeShouldNotBeFound("unidade.greaterThanOrEqual=" + UPDATED_UNIDADE);
    }

    @Test
    @Transactional
    public void getAllDescargaUnidadesByUnidadeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        descargaUnidadeRepository.saveAndFlush(descargaUnidade);

        // Get all the descargaUnidadeList where unidade is less than or equal to DEFAULT_UNIDADE
        defaultDescargaUnidadeShouldBeFound("unidade.lessThanOrEqual=" + DEFAULT_UNIDADE);

        // Get all the descargaUnidadeList where unidade is less than or equal to SMALLER_UNIDADE
        defaultDescargaUnidadeShouldNotBeFound("unidade.lessThanOrEqual=" + SMALLER_UNIDADE);
    }

    @Test
    @Transactional
    public void getAllDescargaUnidadesByUnidadeIsLessThanSomething() throws Exception {
        // Initialize the database
        descargaUnidadeRepository.saveAndFlush(descargaUnidade);

        // Get all the descargaUnidadeList where unidade is less than DEFAULT_UNIDADE
        defaultDescargaUnidadeShouldNotBeFound("unidade.lessThan=" + DEFAULT_UNIDADE);

        // Get all the descargaUnidadeList where unidade is less than UPDATED_UNIDADE
        defaultDescargaUnidadeShouldBeFound("unidade.lessThan=" + UPDATED_UNIDADE);
    }

    @Test
    @Transactional
    public void getAllDescargaUnidadesByUnidadeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        descargaUnidadeRepository.saveAndFlush(descargaUnidade);

        // Get all the descargaUnidadeList where unidade is greater than DEFAULT_UNIDADE
        defaultDescargaUnidadeShouldNotBeFound("unidade.greaterThan=" + DEFAULT_UNIDADE);

        // Get all the descargaUnidadeList where unidade is greater than SMALLER_UNIDADE
        defaultDescargaUnidadeShouldBeFound("unidade.greaterThan=" + SMALLER_UNIDADE);
    }


    @Test
    @Transactional
    public void getAllDescargaUnidadesByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        descargaUnidadeRepository.saveAndFlush(descargaUnidade);

        // Get all the descargaUnidadeList where created equals to DEFAULT_CREATED
        defaultDescargaUnidadeShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the descargaUnidadeList where created equals to UPDATED_CREATED
        defaultDescargaUnidadeShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllDescargaUnidadesByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        descargaUnidadeRepository.saveAndFlush(descargaUnidade);

        // Get all the descargaUnidadeList where created not equals to DEFAULT_CREATED
        defaultDescargaUnidadeShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the descargaUnidadeList where created not equals to UPDATED_CREATED
        defaultDescargaUnidadeShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllDescargaUnidadesByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        descargaUnidadeRepository.saveAndFlush(descargaUnidade);

        // Get all the descargaUnidadeList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultDescargaUnidadeShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the descargaUnidadeList where created equals to UPDATED_CREATED
        defaultDescargaUnidadeShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllDescargaUnidadesByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        descargaUnidadeRepository.saveAndFlush(descargaUnidade);

        // Get all the descargaUnidadeList where created is not null
        defaultDescargaUnidadeShouldBeFound("created.specified=true");

        // Get all the descargaUnidadeList where created is null
        defaultDescargaUnidadeShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllDescargaUnidadesByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        descargaUnidadeRepository.saveAndFlush(descargaUnidade);

        // Get all the descargaUnidadeList where updated equals to DEFAULT_UPDATED
        defaultDescargaUnidadeShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the descargaUnidadeList where updated equals to UPDATED_UPDATED
        defaultDescargaUnidadeShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllDescargaUnidadesByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        descargaUnidadeRepository.saveAndFlush(descargaUnidade);

        // Get all the descargaUnidadeList where updated not equals to DEFAULT_UPDATED
        defaultDescargaUnidadeShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the descargaUnidadeList where updated not equals to UPDATED_UPDATED
        defaultDescargaUnidadeShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllDescargaUnidadesByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        descargaUnidadeRepository.saveAndFlush(descargaUnidade);

        // Get all the descargaUnidadeList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultDescargaUnidadeShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the descargaUnidadeList where updated equals to UPDATED_UPDATED
        defaultDescargaUnidadeShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllDescargaUnidadesByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        descargaUnidadeRepository.saveAndFlush(descargaUnidade);

        // Get all the descargaUnidadeList where updated is not null
        defaultDescargaUnidadeShouldBeFound("updated.specified=true");

        // Get all the descargaUnidadeList where updated is null
        defaultDescargaUnidadeShouldNotBeFound("updated.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDescargaUnidadeShouldBeFound(String filter) throws Exception {
        restDescargaUnidadeMockMvc.perform(get("/api/descarga-unidades?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(descargaUnidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].unidade").value(hasItem(DEFAULT_UNIDADE)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restDescargaUnidadeMockMvc.perform(get("/api/descarga-unidades/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDescargaUnidadeShouldNotBeFound(String filter) throws Exception {
        restDescargaUnidadeMockMvc.perform(get("/api/descarga-unidades?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDescargaUnidadeMockMvc.perform(get("/api/descarga-unidades/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingDescargaUnidade() throws Exception {
        // Get the descargaUnidade
        restDescargaUnidadeMockMvc.perform(get("/api/descarga-unidades/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDescargaUnidade() throws Exception {
        // Initialize the database
        descargaUnidadeRepository.saveAndFlush(descargaUnidade);

        int databaseSizeBeforeUpdate = descargaUnidadeRepository.findAll().size();

        // Update the descargaUnidade
        DescargaUnidade updatedDescargaUnidade = descargaUnidadeRepository.findById(descargaUnidade.getId()).get();
        // Disconnect from session so that the updates on updatedDescargaUnidade are not directly saved in db
        em.detach(updatedDescargaUnidade);
        updatedDescargaUnidade
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .unidade(UPDATED_UNIDADE)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        DescargaUnidadeDTO descargaUnidadeDTO = descargaUnidadeMapper.toDto(updatedDescargaUnidade);

        restDescargaUnidadeMockMvc.perform(put("/api/descarga-unidades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(descargaUnidadeDTO)))
            .andExpect(status().isOk());

        // Validate the DescargaUnidade in the database
        List<DescargaUnidade> descargaUnidadeList = descargaUnidadeRepository.findAll();
        assertThat(descargaUnidadeList).hasSize(databaseSizeBeforeUpdate);
        DescargaUnidade testDescargaUnidade = descargaUnidadeList.get(descargaUnidadeList.size() - 1);
        assertThat(testDescargaUnidade.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testDescargaUnidade.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testDescargaUnidade.getUnidade()).isEqualTo(UPDATED_UNIDADE);
        assertThat(testDescargaUnidade.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testDescargaUnidade.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingDescargaUnidade() throws Exception {
        int databaseSizeBeforeUpdate = descargaUnidadeRepository.findAll().size();

        // Create the DescargaUnidade
        DescargaUnidadeDTO descargaUnidadeDTO = descargaUnidadeMapper.toDto(descargaUnidade);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDescargaUnidadeMockMvc.perform(put("/api/descarga-unidades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(descargaUnidadeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DescargaUnidade in the database
        List<DescargaUnidade> descargaUnidadeList = descargaUnidadeRepository.findAll();
        assertThat(descargaUnidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDescargaUnidade() throws Exception {
        // Initialize the database
        descargaUnidadeRepository.saveAndFlush(descargaUnidade);

        int databaseSizeBeforeDelete = descargaUnidadeRepository.findAll().size();

        // Delete the descargaUnidade
        restDescargaUnidadeMockMvc.perform(delete("/api/descarga-unidades/{id}", descargaUnidade.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DescargaUnidade> descargaUnidadeList = descargaUnidadeRepository.findAll();
        assertThat(descargaUnidadeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
