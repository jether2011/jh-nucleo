package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.UmidadeAr;
import org.tempestade.nucleo.repository.UmidadeArRepository;
import org.tempestade.nucleo.service.UmidadeArService;
import org.tempestade.nucleo.service.dto.UmidadeArDTO;
import org.tempestade.nucleo.service.mapper.UmidadeArMapper;
import org.tempestade.nucleo.service.dto.UmidadeArCriteria;
import org.tempestade.nucleo.service.UmidadeArQueryService;

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
 * Integration tests for the {@link UmidadeArResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UmidadeArResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private UmidadeArRepository umidadeArRepository;

    @Autowired
    private UmidadeArMapper umidadeArMapper;

    @Autowired
    private UmidadeArService umidadeArService;

    @Autowired
    private UmidadeArQueryService umidadeArQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUmidadeArMockMvc;

    private UmidadeAr umidadeAr;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UmidadeAr createEntity(EntityManager em) {
        UmidadeAr umidadeAr = new UmidadeAr()
            .name(DEFAULT_NAME)
            .descricao(DEFAULT_DESCRICAO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return umidadeAr;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UmidadeAr createUpdatedEntity(EntityManager em) {
        UmidadeAr umidadeAr = new UmidadeAr()
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return umidadeAr;
    }

    @BeforeEach
    public void initTest() {
        umidadeAr = createEntity(em);
    }

    @Test
    @Transactional
    public void createUmidadeAr() throws Exception {
        int databaseSizeBeforeCreate = umidadeArRepository.findAll().size();
        // Create the UmidadeAr
        UmidadeArDTO umidadeArDTO = umidadeArMapper.toDto(umidadeAr);
        restUmidadeArMockMvc.perform(post("/api/umidade-ars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(umidadeArDTO)))
            .andExpect(status().isCreated());

        // Validate the UmidadeAr in the database
        List<UmidadeAr> umidadeArList = umidadeArRepository.findAll();
        assertThat(umidadeArList).hasSize(databaseSizeBeforeCreate + 1);
        UmidadeAr testUmidadeAr = umidadeArList.get(umidadeArList.size() - 1);
        assertThat(testUmidadeAr.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUmidadeAr.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testUmidadeAr.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testUmidadeAr.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createUmidadeArWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = umidadeArRepository.findAll().size();

        // Create the UmidadeAr with an existing ID
        umidadeAr.setId(1L);
        UmidadeArDTO umidadeArDTO = umidadeArMapper.toDto(umidadeAr);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUmidadeArMockMvc.perform(post("/api/umidade-ars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(umidadeArDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UmidadeAr in the database
        List<UmidadeAr> umidadeArList = umidadeArRepository.findAll();
        assertThat(umidadeArList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = umidadeArRepository.findAll().size();
        // set the field null
        umidadeAr.setName(null);

        // Create the UmidadeAr, which fails.
        UmidadeArDTO umidadeArDTO = umidadeArMapper.toDto(umidadeAr);


        restUmidadeArMockMvc.perform(post("/api/umidade-ars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(umidadeArDTO)))
            .andExpect(status().isBadRequest());

        List<UmidadeAr> umidadeArList = umidadeArRepository.findAll();
        assertThat(umidadeArList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = umidadeArRepository.findAll().size();
        // set the field null
        umidadeAr.setDescricao(null);

        // Create the UmidadeAr, which fails.
        UmidadeArDTO umidadeArDTO = umidadeArMapper.toDto(umidadeAr);


        restUmidadeArMockMvc.perform(post("/api/umidade-ars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(umidadeArDTO)))
            .andExpect(status().isBadRequest());

        List<UmidadeAr> umidadeArList = umidadeArRepository.findAll();
        assertThat(umidadeArList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = umidadeArRepository.findAll().size();
        // set the field null
        umidadeAr.setCreated(null);

        // Create the UmidadeAr, which fails.
        UmidadeArDTO umidadeArDTO = umidadeArMapper.toDto(umidadeAr);


        restUmidadeArMockMvc.perform(post("/api/umidade-ars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(umidadeArDTO)))
            .andExpect(status().isBadRequest());

        List<UmidadeAr> umidadeArList = umidadeArRepository.findAll();
        assertThat(umidadeArList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUmidadeArs() throws Exception {
        // Initialize the database
        umidadeArRepository.saveAndFlush(umidadeAr);

        // Get all the umidadeArList
        restUmidadeArMockMvc.perform(get("/api/umidade-ars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(umidadeAr.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getUmidadeAr() throws Exception {
        // Initialize the database
        umidadeArRepository.saveAndFlush(umidadeAr);

        // Get the umidadeAr
        restUmidadeArMockMvc.perform(get("/api/umidade-ars/{id}", umidadeAr.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(umidadeAr.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }


    @Test
    @Transactional
    public void getUmidadeArsByIdFiltering() throws Exception {
        // Initialize the database
        umidadeArRepository.saveAndFlush(umidadeAr);

        Long id = umidadeAr.getId();

        defaultUmidadeArShouldBeFound("id.equals=" + id);
        defaultUmidadeArShouldNotBeFound("id.notEquals=" + id);

        defaultUmidadeArShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUmidadeArShouldNotBeFound("id.greaterThan=" + id);

        defaultUmidadeArShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUmidadeArShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllUmidadeArsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        umidadeArRepository.saveAndFlush(umidadeAr);

        // Get all the umidadeArList where name equals to DEFAULT_NAME
        defaultUmidadeArShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the umidadeArList where name equals to UPDATED_NAME
        defaultUmidadeArShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUmidadeArsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        umidadeArRepository.saveAndFlush(umidadeAr);

        // Get all the umidadeArList where name not equals to DEFAULT_NAME
        defaultUmidadeArShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the umidadeArList where name not equals to UPDATED_NAME
        defaultUmidadeArShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUmidadeArsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        umidadeArRepository.saveAndFlush(umidadeAr);

        // Get all the umidadeArList where name in DEFAULT_NAME or UPDATED_NAME
        defaultUmidadeArShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the umidadeArList where name equals to UPDATED_NAME
        defaultUmidadeArShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUmidadeArsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        umidadeArRepository.saveAndFlush(umidadeAr);

        // Get all the umidadeArList where name is not null
        defaultUmidadeArShouldBeFound("name.specified=true");

        // Get all the umidadeArList where name is null
        defaultUmidadeArShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllUmidadeArsByNameContainsSomething() throws Exception {
        // Initialize the database
        umidadeArRepository.saveAndFlush(umidadeAr);

        // Get all the umidadeArList where name contains DEFAULT_NAME
        defaultUmidadeArShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the umidadeArList where name contains UPDATED_NAME
        defaultUmidadeArShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUmidadeArsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        umidadeArRepository.saveAndFlush(umidadeAr);

        // Get all the umidadeArList where name does not contain DEFAULT_NAME
        defaultUmidadeArShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the umidadeArList where name does not contain UPDATED_NAME
        defaultUmidadeArShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllUmidadeArsByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        umidadeArRepository.saveAndFlush(umidadeAr);

        // Get all the umidadeArList where descricao equals to DEFAULT_DESCRICAO
        defaultUmidadeArShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the umidadeArList where descricao equals to UPDATED_DESCRICAO
        defaultUmidadeArShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllUmidadeArsByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        umidadeArRepository.saveAndFlush(umidadeAr);

        // Get all the umidadeArList where descricao not equals to DEFAULT_DESCRICAO
        defaultUmidadeArShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the umidadeArList where descricao not equals to UPDATED_DESCRICAO
        defaultUmidadeArShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllUmidadeArsByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        umidadeArRepository.saveAndFlush(umidadeAr);

        // Get all the umidadeArList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultUmidadeArShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the umidadeArList where descricao equals to UPDATED_DESCRICAO
        defaultUmidadeArShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllUmidadeArsByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        umidadeArRepository.saveAndFlush(umidadeAr);

        // Get all the umidadeArList where descricao is not null
        defaultUmidadeArShouldBeFound("descricao.specified=true");

        // Get all the umidadeArList where descricao is null
        defaultUmidadeArShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllUmidadeArsByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        umidadeArRepository.saveAndFlush(umidadeAr);

        // Get all the umidadeArList where descricao contains DEFAULT_DESCRICAO
        defaultUmidadeArShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the umidadeArList where descricao contains UPDATED_DESCRICAO
        defaultUmidadeArShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllUmidadeArsByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        umidadeArRepository.saveAndFlush(umidadeAr);

        // Get all the umidadeArList where descricao does not contain DEFAULT_DESCRICAO
        defaultUmidadeArShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the umidadeArList where descricao does not contain UPDATED_DESCRICAO
        defaultUmidadeArShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllUmidadeArsByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        umidadeArRepository.saveAndFlush(umidadeAr);

        // Get all the umidadeArList where created equals to DEFAULT_CREATED
        defaultUmidadeArShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the umidadeArList where created equals to UPDATED_CREATED
        defaultUmidadeArShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllUmidadeArsByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        umidadeArRepository.saveAndFlush(umidadeAr);

        // Get all the umidadeArList where created not equals to DEFAULT_CREATED
        defaultUmidadeArShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the umidadeArList where created not equals to UPDATED_CREATED
        defaultUmidadeArShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllUmidadeArsByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        umidadeArRepository.saveAndFlush(umidadeAr);

        // Get all the umidadeArList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultUmidadeArShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the umidadeArList where created equals to UPDATED_CREATED
        defaultUmidadeArShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllUmidadeArsByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        umidadeArRepository.saveAndFlush(umidadeAr);

        // Get all the umidadeArList where created is not null
        defaultUmidadeArShouldBeFound("created.specified=true");

        // Get all the umidadeArList where created is null
        defaultUmidadeArShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllUmidadeArsByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        umidadeArRepository.saveAndFlush(umidadeAr);

        // Get all the umidadeArList where updated equals to DEFAULT_UPDATED
        defaultUmidadeArShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the umidadeArList where updated equals to UPDATED_UPDATED
        defaultUmidadeArShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllUmidadeArsByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        umidadeArRepository.saveAndFlush(umidadeAr);

        // Get all the umidadeArList where updated not equals to DEFAULT_UPDATED
        defaultUmidadeArShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the umidadeArList where updated not equals to UPDATED_UPDATED
        defaultUmidadeArShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllUmidadeArsByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        umidadeArRepository.saveAndFlush(umidadeAr);

        // Get all the umidadeArList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultUmidadeArShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the umidadeArList where updated equals to UPDATED_UPDATED
        defaultUmidadeArShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllUmidadeArsByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        umidadeArRepository.saveAndFlush(umidadeAr);

        // Get all the umidadeArList where updated is not null
        defaultUmidadeArShouldBeFound("updated.specified=true");

        // Get all the umidadeArList where updated is null
        defaultUmidadeArShouldNotBeFound("updated.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUmidadeArShouldBeFound(String filter) throws Exception {
        restUmidadeArMockMvc.perform(get("/api/umidade-ars?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(umidadeAr.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restUmidadeArMockMvc.perform(get("/api/umidade-ars/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUmidadeArShouldNotBeFound(String filter) throws Exception {
        restUmidadeArMockMvc.perform(get("/api/umidade-ars?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUmidadeArMockMvc.perform(get("/api/umidade-ars/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingUmidadeAr() throws Exception {
        // Get the umidadeAr
        restUmidadeArMockMvc.perform(get("/api/umidade-ars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUmidadeAr() throws Exception {
        // Initialize the database
        umidadeArRepository.saveAndFlush(umidadeAr);

        int databaseSizeBeforeUpdate = umidadeArRepository.findAll().size();

        // Update the umidadeAr
        UmidadeAr updatedUmidadeAr = umidadeArRepository.findById(umidadeAr.getId()).get();
        // Disconnect from session so that the updates on updatedUmidadeAr are not directly saved in db
        em.detach(updatedUmidadeAr);
        updatedUmidadeAr
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        UmidadeArDTO umidadeArDTO = umidadeArMapper.toDto(updatedUmidadeAr);

        restUmidadeArMockMvc.perform(put("/api/umidade-ars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(umidadeArDTO)))
            .andExpect(status().isOk());

        // Validate the UmidadeAr in the database
        List<UmidadeAr> umidadeArList = umidadeArRepository.findAll();
        assertThat(umidadeArList).hasSize(databaseSizeBeforeUpdate);
        UmidadeAr testUmidadeAr = umidadeArList.get(umidadeArList.size() - 1);
        assertThat(testUmidadeAr.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUmidadeAr.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testUmidadeAr.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testUmidadeAr.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingUmidadeAr() throws Exception {
        int databaseSizeBeforeUpdate = umidadeArRepository.findAll().size();

        // Create the UmidadeAr
        UmidadeArDTO umidadeArDTO = umidadeArMapper.toDto(umidadeAr);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUmidadeArMockMvc.perform(put("/api/umidade-ars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(umidadeArDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UmidadeAr in the database
        List<UmidadeAr> umidadeArList = umidadeArRepository.findAll();
        assertThat(umidadeArList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUmidadeAr() throws Exception {
        // Initialize the database
        umidadeArRepository.saveAndFlush(umidadeAr);

        int databaseSizeBeforeDelete = umidadeArRepository.findAll().size();

        // Delete the umidadeAr
        restUmidadeArMockMvc.perform(delete("/api/umidade-ars/{id}", umidadeAr.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UmidadeAr> umidadeArList = umidadeArRepository.findAll();
        assertThat(umidadeArList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
