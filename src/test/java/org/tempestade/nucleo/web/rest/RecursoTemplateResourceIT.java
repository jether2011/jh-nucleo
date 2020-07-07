package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.RecursoTemplate;
import org.tempestade.nucleo.domain.Recurso;
import org.tempestade.nucleo.domain.TipoEnvio;
import org.tempestade.nucleo.domain.AlertaTipo;
import org.tempestade.nucleo.repository.RecursoTemplateRepository;
import org.tempestade.nucleo.service.RecursoTemplateService;
import org.tempestade.nucleo.service.dto.RecursoTemplateDTO;
import org.tempestade.nucleo.service.mapper.RecursoTemplateMapper;
import org.tempestade.nucleo.service.dto.RecursoTemplateCriteria;
import org.tempestade.nucleo.service.RecursoTemplateQueryService;

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
 * Integration tests for the {@link RecursoTemplateResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class RecursoTemplateResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_TEMPLATE = "AAAAAAAAAA";
    private static final String UPDATED_TEMPLATE = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private RecursoTemplateRepository recursoTemplateRepository;

    @Autowired
    private RecursoTemplateMapper recursoTemplateMapper;

    @Autowired
    private RecursoTemplateService recursoTemplateService;

    @Autowired
    private RecursoTemplateQueryService recursoTemplateQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRecursoTemplateMockMvc;

    private RecursoTemplate recursoTemplate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RecursoTemplate createEntity(EntityManager em) {
        RecursoTemplate recursoTemplate = new RecursoTemplate()
            .name(DEFAULT_NAME)
            .descricao(DEFAULT_DESCRICAO)
            .template(DEFAULT_TEMPLATE)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return recursoTemplate;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RecursoTemplate createUpdatedEntity(EntityManager em) {
        RecursoTemplate recursoTemplate = new RecursoTemplate()
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .template(UPDATED_TEMPLATE)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return recursoTemplate;
    }

    @BeforeEach
    public void initTest() {
        recursoTemplate = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecursoTemplate() throws Exception {
        int databaseSizeBeforeCreate = recursoTemplateRepository.findAll().size();
        // Create the RecursoTemplate
        RecursoTemplateDTO recursoTemplateDTO = recursoTemplateMapper.toDto(recursoTemplate);
        restRecursoTemplateMockMvc.perform(post("/api/recurso-templates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recursoTemplateDTO)))
            .andExpect(status().isCreated());

        // Validate the RecursoTemplate in the database
        List<RecursoTemplate> recursoTemplateList = recursoTemplateRepository.findAll();
        assertThat(recursoTemplateList).hasSize(databaseSizeBeforeCreate + 1);
        RecursoTemplate testRecursoTemplate = recursoTemplateList.get(recursoTemplateList.size() - 1);
        assertThat(testRecursoTemplate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRecursoTemplate.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testRecursoTemplate.getTemplate()).isEqualTo(DEFAULT_TEMPLATE);
        assertThat(testRecursoTemplate.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testRecursoTemplate.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createRecursoTemplateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recursoTemplateRepository.findAll().size();

        // Create the RecursoTemplate with an existing ID
        recursoTemplate.setId(1L);
        RecursoTemplateDTO recursoTemplateDTO = recursoTemplateMapper.toDto(recursoTemplate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecursoTemplateMockMvc.perform(post("/api/recurso-templates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recursoTemplateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RecursoTemplate in the database
        List<RecursoTemplate> recursoTemplateList = recursoTemplateRepository.findAll();
        assertThat(recursoTemplateList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = recursoTemplateRepository.findAll().size();
        // set the field null
        recursoTemplate.setName(null);

        // Create the RecursoTemplate, which fails.
        RecursoTemplateDTO recursoTemplateDTO = recursoTemplateMapper.toDto(recursoTemplate);


        restRecursoTemplateMockMvc.perform(post("/api/recurso-templates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recursoTemplateDTO)))
            .andExpect(status().isBadRequest());

        List<RecursoTemplate> recursoTemplateList = recursoTemplateRepository.findAll();
        assertThat(recursoTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = recursoTemplateRepository.findAll().size();
        // set the field null
        recursoTemplate.setDescricao(null);

        // Create the RecursoTemplate, which fails.
        RecursoTemplateDTO recursoTemplateDTO = recursoTemplateMapper.toDto(recursoTemplate);


        restRecursoTemplateMockMvc.perform(post("/api/recurso-templates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recursoTemplateDTO)))
            .andExpect(status().isBadRequest());

        List<RecursoTemplate> recursoTemplateList = recursoTemplateRepository.findAll();
        assertThat(recursoTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = recursoTemplateRepository.findAll().size();
        // set the field null
        recursoTemplate.setCreated(null);

        // Create the RecursoTemplate, which fails.
        RecursoTemplateDTO recursoTemplateDTO = recursoTemplateMapper.toDto(recursoTemplate);


        restRecursoTemplateMockMvc.perform(post("/api/recurso-templates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recursoTemplateDTO)))
            .andExpect(status().isBadRequest());

        List<RecursoTemplate> recursoTemplateList = recursoTemplateRepository.findAll();
        assertThat(recursoTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRecursoTemplates() throws Exception {
        // Initialize the database
        recursoTemplateRepository.saveAndFlush(recursoTemplate);

        // Get all the recursoTemplateList
        restRecursoTemplateMockMvc.perform(get("/api/recurso-templates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recursoTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].template").value(hasItem(DEFAULT_TEMPLATE)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getRecursoTemplate() throws Exception {
        // Initialize the database
        recursoTemplateRepository.saveAndFlush(recursoTemplate);

        // Get the recursoTemplate
        restRecursoTemplateMockMvc.perform(get("/api/recurso-templates/{id}", recursoTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(recursoTemplate.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.template").value(DEFAULT_TEMPLATE))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }


    @Test
    @Transactional
    public void getRecursoTemplatesByIdFiltering() throws Exception {
        // Initialize the database
        recursoTemplateRepository.saveAndFlush(recursoTemplate);

        Long id = recursoTemplate.getId();

        defaultRecursoTemplateShouldBeFound("id.equals=" + id);
        defaultRecursoTemplateShouldNotBeFound("id.notEquals=" + id);

        defaultRecursoTemplateShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRecursoTemplateShouldNotBeFound("id.greaterThan=" + id);

        defaultRecursoTemplateShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRecursoTemplateShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllRecursoTemplatesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        recursoTemplateRepository.saveAndFlush(recursoTemplate);

        // Get all the recursoTemplateList where name equals to DEFAULT_NAME
        defaultRecursoTemplateShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the recursoTemplateList where name equals to UPDATED_NAME
        defaultRecursoTemplateShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRecursoTemplatesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        recursoTemplateRepository.saveAndFlush(recursoTemplate);

        // Get all the recursoTemplateList where name not equals to DEFAULT_NAME
        defaultRecursoTemplateShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the recursoTemplateList where name not equals to UPDATED_NAME
        defaultRecursoTemplateShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRecursoTemplatesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        recursoTemplateRepository.saveAndFlush(recursoTemplate);

        // Get all the recursoTemplateList where name in DEFAULT_NAME or UPDATED_NAME
        defaultRecursoTemplateShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the recursoTemplateList where name equals to UPDATED_NAME
        defaultRecursoTemplateShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRecursoTemplatesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        recursoTemplateRepository.saveAndFlush(recursoTemplate);

        // Get all the recursoTemplateList where name is not null
        defaultRecursoTemplateShouldBeFound("name.specified=true");

        // Get all the recursoTemplateList where name is null
        defaultRecursoTemplateShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllRecursoTemplatesByNameContainsSomething() throws Exception {
        // Initialize the database
        recursoTemplateRepository.saveAndFlush(recursoTemplate);

        // Get all the recursoTemplateList where name contains DEFAULT_NAME
        defaultRecursoTemplateShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the recursoTemplateList where name contains UPDATED_NAME
        defaultRecursoTemplateShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRecursoTemplatesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        recursoTemplateRepository.saveAndFlush(recursoTemplate);

        // Get all the recursoTemplateList where name does not contain DEFAULT_NAME
        defaultRecursoTemplateShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the recursoTemplateList where name does not contain UPDATED_NAME
        defaultRecursoTemplateShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllRecursoTemplatesByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        recursoTemplateRepository.saveAndFlush(recursoTemplate);

        // Get all the recursoTemplateList where descricao equals to DEFAULT_DESCRICAO
        defaultRecursoTemplateShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the recursoTemplateList where descricao equals to UPDATED_DESCRICAO
        defaultRecursoTemplateShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllRecursoTemplatesByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        recursoTemplateRepository.saveAndFlush(recursoTemplate);

        // Get all the recursoTemplateList where descricao not equals to DEFAULT_DESCRICAO
        defaultRecursoTemplateShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the recursoTemplateList where descricao not equals to UPDATED_DESCRICAO
        defaultRecursoTemplateShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllRecursoTemplatesByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        recursoTemplateRepository.saveAndFlush(recursoTemplate);

        // Get all the recursoTemplateList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultRecursoTemplateShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the recursoTemplateList where descricao equals to UPDATED_DESCRICAO
        defaultRecursoTemplateShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllRecursoTemplatesByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        recursoTemplateRepository.saveAndFlush(recursoTemplate);

        // Get all the recursoTemplateList where descricao is not null
        defaultRecursoTemplateShouldBeFound("descricao.specified=true");

        // Get all the recursoTemplateList where descricao is null
        defaultRecursoTemplateShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllRecursoTemplatesByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        recursoTemplateRepository.saveAndFlush(recursoTemplate);

        // Get all the recursoTemplateList where descricao contains DEFAULT_DESCRICAO
        defaultRecursoTemplateShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the recursoTemplateList where descricao contains UPDATED_DESCRICAO
        defaultRecursoTemplateShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllRecursoTemplatesByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        recursoTemplateRepository.saveAndFlush(recursoTemplate);

        // Get all the recursoTemplateList where descricao does not contain DEFAULT_DESCRICAO
        defaultRecursoTemplateShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the recursoTemplateList where descricao does not contain UPDATED_DESCRICAO
        defaultRecursoTemplateShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllRecursoTemplatesByTemplateIsEqualToSomething() throws Exception {
        // Initialize the database
        recursoTemplateRepository.saveAndFlush(recursoTemplate);

        // Get all the recursoTemplateList where template equals to DEFAULT_TEMPLATE
        defaultRecursoTemplateShouldBeFound("template.equals=" + DEFAULT_TEMPLATE);

        // Get all the recursoTemplateList where template equals to UPDATED_TEMPLATE
        defaultRecursoTemplateShouldNotBeFound("template.equals=" + UPDATED_TEMPLATE);
    }

    @Test
    @Transactional
    public void getAllRecursoTemplatesByTemplateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        recursoTemplateRepository.saveAndFlush(recursoTemplate);

        // Get all the recursoTemplateList where template not equals to DEFAULT_TEMPLATE
        defaultRecursoTemplateShouldNotBeFound("template.notEquals=" + DEFAULT_TEMPLATE);

        // Get all the recursoTemplateList where template not equals to UPDATED_TEMPLATE
        defaultRecursoTemplateShouldBeFound("template.notEquals=" + UPDATED_TEMPLATE);
    }

    @Test
    @Transactional
    public void getAllRecursoTemplatesByTemplateIsInShouldWork() throws Exception {
        // Initialize the database
        recursoTemplateRepository.saveAndFlush(recursoTemplate);

        // Get all the recursoTemplateList where template in DEFAULT_TEMPLATE or UPDATED_TEMPLATE
        defaultRecursoTemplateShouldBeFound("template.in=" + DEFAULT_TEMPLATE + "," + UPDATED_TEMPLATE);

        // Get all the recursoTemplateList where template equals to UPDATED_TEMPLATE
        defaultRecursoTemplateShouldNotBeFound("template.in=" + UPDATED_TEMPLATE);
    }

    @Test
    @Transactional
    public void getAllRecursoTemplatesByTemplateIsNullOrNotNull() throws Exception {
        // Initialize the database
        recursoTemplateRepository.saveAndFlush(recursoTemplate);

        // Get all the recursoTemplateList where template is not null
        defaultRecursoTemplateShouldBeFound("template.specified=true");

        // Get all the recursoTemplateList where template is null
        defaultRecursoTemplateShouldNotBeFound("template.specified=false");
    }
                @Test
    @Transactional
    public void getAllRecursoTemplatesByTemplateContainsSomething() throws Exception {
        // Initialize the database
        recursoTemplateRepository.saveAndFlush(recursoTemplate);

        // Get all the recursoTemplateList where template contains DEFAULT_TEMPLATE
        defaultRecursoTemplateShouldBeFound("template.contains=" + DEFAULT_TEMPLATE);

        // Get all the recursoTemplateList where template contains UPDATED_TEMPLATE
        defaultRecursoTemplateShouldNotBeFound("template.contains=" + UPDATED_TEMPLATE);
    }

    @Test
    @Transactional
    public void getAllRecursoTemplatesByTemplateNotContainsSomething() throws Exception {
        // Initialize the database
        recursoTemplateRepository.saveAndFlush(recursoTemplate);

        // Get all the recursoTemplateList where template does not contain DEFAULT_TEMPLATE
        defaultRecursoTemplateShouldNotBeFound("template.doesNotContain=" + DEFAULT_TEMPLATE);

        // Get all the recursoTemplateList where template does not contain UPDATED_TEMPLATE
        defaultRecursoTemplateShouldBeFound("template.doesNotContain=" + UPDATED_TEMPLATE);
    }


    @Test
    @Transactional
    public void getAllRecursoTemplatesByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        recursoTemplateRepository.saveAndFlush(recursoTemplate);

        // Get all the recursoTemplateList where created equals to DEFAULT_CREATED
        defaultRecursoTemplateShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the recursoTemplateList where created equals to UPDATED_CREATED
        defaultRecursoTemplateShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllRecursoTemplatesByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        recursoTemplateRepository.saveAndFlush(recursoTemplate);

        // Get all the recursoTemplateList where created not equals to DEFAULT_CREATED
        defaultRecursoTemplateShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the recursoTemplateList where created not equals to UPDATED_CREATED
        defaultRecursoTemplateShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllRecursoTemplatesByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        recursoTemplateRepository.saveAndFlush(recursoTemplate);

        // Get all the recursoTemplateList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultRecursoTemplateShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the recursoTemplateList where created equals to UPDATED_CREATED
        defaultRecursoTemplateShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllRecursoTemplatesByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        recursoTemplateRepository.saveAndFlush(recursoTemplate);

        // Get all the recursoTemplateList where created is not null
        defaultRecursoTemplateShouldBeFound("created.specified=true");

        // Get all the recursoTemplateList where created is null
        defaultRecursoTemplateShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllRecursoTemplatesByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        recursoTemplateRepository.saveAndFlush(recursoTemplate);

        // Get all the recursoTemplateList where updated equals to DEFAULT_UPDATED
        defaultRecursoTemplateShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the recursoTemplateList where updated equals to UPDATED_UPDATED
        defaultRecursoTemplateShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllRecursoTemplatesByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        recursoTemplateRepository.saveAndFlush(recursoTemplate);

        // Get all the recursoTemplateList where updated not equals to DEFAULT_UPDATED
        defaultRecursoTemplateShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the recursoTemplateList where updated not equals to UPDATED_UPDATED
        defaultRecursoTemplateShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllRecursoTemplatesByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        recursoTemplateRepository.saveAndFlush(recursoTemplate);

        // Get all the recursoTemplateList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultRecursoTemplateShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the recursoTemplateList where updated equals to UPDATED_UPDATED
        defaultRecursoTemplateShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllRecursoTemplatesByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        recursoTemplateRepository.saveAndFlush(recursoTemplate);

        // Get all the recursoTemplateList where updated is not null
        defaultRecursoTemplateShouldBeFound("updated.specified=true");

        // Get all the recursoTemplateList where updated is null
        defaultRecursoTemplateShouldNotBeFound("updated.specified=false");
    }

    @Test
    @Transactional
    public void getAllRecursoTemplatesByRecursoIsEqualToSomething() throws Exception {
        // Initialize the database
        recursoTemplateRepository.saveAndFlush(recursoTemplate);
        Recurso recurso = RecursoResourceIT.createEntity(em);
        em.persist(recurso);
        em.flush();
        recursoTemplate.setRecurso(recurso);
        recursoTemplateRepository.saveAndFlush(recursoTemplate);
        Long recursoId = recurso.getId();

        // Get all the recursoTemplateList where recurso equals to recursoId
        defaultRecursoTemplateShouldBeFound("recursoId.equals=" + recursoId);

        // Get all the recursoTemplateList where recurso equals to recursoId + 1
        defaultRecursoTemplateShouldNotBeFound("recursoId.equals=" + (recursoId + 1));
    }


    @Test
    @Transactional
    public void getAllRecursoTemplatesByTipoEnvioIsEqualToSomething() throws Exception {
        // Initialize the database
        recursoTemplateRepository.saveAndFlush(recursoTemplate);
        TipoEnvio tipoEnvio = TipoEnvioResourceIT.createEntity(em);
        em.persist(tipoEnvio);
        em.flush();
        recursoTemplate.setTipoEnvio(tipoEnvio);
        recursoTemplateRepository.saveAndFlush(recursoTemplate);
        Long tipoEnvioId = tipoEnvio.getId();

        // Get all the recursoTemplateList where tipoEnvio equals to tipoEnvioId
        defaultRecursoTemplateShouldBeFound("tipoEnvioId.equals=" + tipoEnvioId);

        // Get all the recursoTemplateList where tipoEnvio equals to tipoEnvioId + 1
        defaultRecursoTemplateShouldNotBeFound("tipoEnvioId.equals=" + (tipoEnvioId + 1));
    }


    @Test
    @Transactional
    public void getAllRecursoTemplatesByAlertaTipoIsEqualToSomething() throws Exception {
        // Initialize the database
        recursoTemplateRepository.saveAndFlush(recursoTemplate);
        AlertaTipo alertaTipo = AlertaTipoResourceIT.createEntity(em);
        em.persist(alertaTipo);
        em.flush();
        recursoTemplate.setAlertaTipo(alertaTipo);
        recursoTemplateRepository.saveAndFlush(recursoTemplate);
        Long alertaTipoId = alertaTipo.getId();

        // Get all the recursoTemplateList where alertaTipo equals to alertaTipoId
        defaultRecursoTemplateShouldBeFound("alertaTipoId.equals=" + alertaTipoId);

        // Get all the recursoTemplateList where alertaTipo equals to alertaTipoId + 1
        defaultRecursoTemplateShouldNotBeFound("alertaTipoId.equals=" + (alertaTipoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRecursoTemplateShouldBeFound(String filter) throws Exception {
        restRecursoTemplateMockMvc.perform(get("/api/recurso-templates?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recursoTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].template").value(hasItem(DEFAULT_TEMPLATE)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restRecursoTemplateMockMvc.perform(get("/api/recurso-templates/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRecursoTemplateShouldNotBeFound(String filter) throws Exception {
        restRecursoTemplateMockMvc.perform(get("/api/recurso-templates?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRecursoTemplateMockMvc.perform(get("/api/recurso-templates/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingRecursoTemplate() throws Exception {
        // Get the recursoTemplate
        restRecursoTemplateMockMvc.perform(get("/api/recurso-templates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecursoTemplate() throws Exception {
        // Initialize the database
        recursoTemplateRepository.saveAndFlush(recursoTemplate);

        int databaseSizeBeforeUpdate = recursoTemplateRepository.findAll().size();

        // Update the recursoTemplate
        RecursoTemplate updatedRecursoTemplate = recursoTemplateRepository.findById(recursoTemplate.getId()).get();
        // Disconnect from session so that the updates on updatedRecursoTemplate are not directly saved in db
        em.detach(updatedRecursoTemplate);
        updatedRecursoTemplate
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .template(UPDATED_TEMPLATE)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        RecursoTemplateDTO recursoTemplateDTO = recursoTemplateMapper.toDto(updatedRecursoTemplate);

        restRecursoTemplateMockMvc.perform(put("/api/recurso-templates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recursoTemplateDTO)))
            .andExpect(status().isOk());

        // Validate the RecursoTemplate in the database
        List<RecursoTemplate> recursoTemplateList = recursoTemplateRepository.findAll();
        assertThat(recursoTemplateList).hasSize(databaseSizeBeforeUpdate);
        RecursoTemplate testRecursoTemplate = recursoTemplateList.get(recursoTemplateList.size() - 1);
        assertThat(testRecursoTemplate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRecursoTemplate.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testRecursoTemplate.getTemplate()).isEqualTo(UPDATED_TEMPLATE);
        assertThat(testRecursoTemplate.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testRecursoTemplate.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingRecursoTemplate() throws Exception {
        int databaseSizeBeforeUpdate = recursoTemplateRepository.findAll().size();

        // Create the RecursoTemplate
        RecursoTemplateDTO recursoTemplateDTO = recursoTemplateMapper.toDto(recursoTemplate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecursoTemplateMockMvc.perform(put("/api/recurso-templates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recursoTemplateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RecursoTemplate in the database
        List<RecursoTemplate> recursoTemplateList = recursoTemplateRepository.findAll();
        assertThat(recursoTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRecursoTemplate() throws Exception {
        // Initialize the database
        recursoTemplateRepository.saveAndFlush(recursoTemplate);

        int databaseSizeBeforeDelete = recursoTemplateRepository.findAll().size();

        // Delete the recursoTemplate
        restRecursoTemplateMockMvc.perform(delete("/api/recurso-templates/{id}", recursoTemplate.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RecursoTemplate> recursoTemplateList = recursoTemplateRepository.findAll();
        assertThat(recursoTemplateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
