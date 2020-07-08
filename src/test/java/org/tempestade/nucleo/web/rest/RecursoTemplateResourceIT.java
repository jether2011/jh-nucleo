package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.RecursoTemplate;
import org.tempestade.nucleo.repository.RecursoTemplateRepository;
import org.tempestade.nucleo.service.RecursoTemplateService;
import org.tempestade.nucleo.service.dto.RecursoTemplateDTO;
import org.tempestade.nucleo.service.mapper.RecursoTemplateMapper;

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
