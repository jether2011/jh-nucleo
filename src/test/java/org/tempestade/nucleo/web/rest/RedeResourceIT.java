package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.Rede;
import org.tempestade.nucleo.repository.RedeRepository;
import org.tempestade.nucleo.service.RedeService;
import org.tempestade.nucleo.service.dto.RedeDTO;
import org.tempestade.nucleo.service.mapper.RedeMapper;
import org.tempestade.nucleo.service.dto.RedeCriteria;
import org.tempestade.nucleo.service.RedeQueryService;

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
 * Integration tests for the {@link RedeResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class RedeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private RedeRepository redeRepository;

    @Autowired
    private RedeMapper redeMapper;

    @Autowired
    private RedeService redeService;

    @Autowired
    private RedeQueryService redeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRedeMockMvc;

    private Rede rede;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rede createEntity(EntityManager em) {
        Rede rede = new Rede()
            .name(DEFAULT_NAME)
            .descricao(DEFAULT_DESCRICAO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return rede;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rede createUpdatedEntity(EntityManager em) {
        Rede rede = new Rede()
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return rede;
    }

    @BeforeEach
    public void initTest() {
        rede = createEntity(em);
    }

    @Test
    @Transactional
    public void createRede() throws Exception {
        int databaseSizeBeforeCreate = redeRepository.findAll().size();
        // Create the Rede
        RedeDTO redeDTO = redeMapper.toDto(rede);
        restRedeMockMvc.perform(post("/api/redes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(redeDTO)))
            .andExpect(status().isCreated());

        // Validate the Rede in the database
        List<Rede> redeList = redeRepository.findAll();
        assertThat(redeList).hasSize(databaseSizeBeforeCreate + 1);
        Rede testRede = redeList.get(redeList.size() - 1);
        assertThat(testRede.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRede.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testRede.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testRede.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createRedeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = redeRepository.findAll().size();

        // Create the Rede with an existing ID
        rede.setId(1L);
        RedeDTO redeDTO = redeMapper.toDto(rede);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRedeMockMvc.perform(post("/api/redes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(redeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Rede in the database
        List<Rede> redeList = redeRepository.findAll();
        assertThat(redeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = redeRepository.findAll().size();
        // set the field null
        rede.setName(null);

        // Create the Rede, which fails.
        RedeDTO redeDTO = redeMapper.toDto(rede);


        restRedeMockMvc.perform(post("/api/redes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(redeDTO)))
            .andExpect(status().isBadRequest());

        List<Rede> redeList = redeRepository.findAll();
        assertThat(redeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = redeRepository.findAll().size();
        // set the field null
        rede.setDescricao(null);

        // Create the Rede, which fails.
        RedeDTO redeDTO = redeMapper.toDto(rede);


        restRedeMockMvc.perform(post("/api/redes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(redeDTO)))
            .andExpect(status().isBadRequest());

        List<Rede> redeList = redeRepository.findAll();
        assertThat(redeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = redeRepository.findAll().size();
        // set the field null
        rede.setCreated(null);

        // Create the Rede, which fails.
        RedeDTO redeDTO = redeMapper.toDto(rede);


        restRedeMockMvc.perform(post("/api/redes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(redeDTO)))
            .andExpect(status().isBadRequest());

        List<Rede> redeList = redeRepository.findAll();
        assertThat(redeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRedes() throws Exception {
        // Initialize the database
        redeRepository.saveAndFlush(rede);

        // Get all the redeList
        restRedeMockMvc.perform(get("/api/redes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rede.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getRede() throws Exception {
        // Initialize the database
        redeRepository.saveAndFlush(rede);

        // Get the rede
        restRedeMockMvc.perform(get("/api/redes/{id}", rede.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rede.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }


    @Test
    @Transactional
    public void getRedesByIdFiltering() throws Exception {
        // Initialize the database
        redeRepository.saveAndFlush(rede);

        Long id = rede.getId();

        defaultRedeShouldBeFound("id.equals=" + id);
        defaultRedeShouldNotBeFound("id.notEquals=" + id);

        defaultRedeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRedeShouldNotBeFound("id.greaterThan=" + id);

        defaultRedeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRedeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllRedesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        redeRepository.saveAndFlush(rede);

        // Get all the redeList where name equals to DEFAULT_NAME
        defaultRedeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the redeList where name equals to UPDATED_NAME
        defaultRedeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRedesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        redeRepository.saveAndFlush(rede);

        // Get all the redeList where name not equals to DEFAULT_NAME
        defaultRedeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the redeList where name not equals to UPDATED_NAME
        defaultRedeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRedesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        redeRepository.saveAndFlush(rede);

        // Get all the redeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultRedeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the redeList where name equals to UPDATED_NAME
        defaultRedeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRedesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        redeRepository.saveAndFlush(rede);

        // Get all the redeList where name is not null
        defaultRedeShouldBeFound("name.specified=true");

        // Get all the redeList where name is null
        defaultRedeShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllRedesByNameContainsSomething() throws Exception {
        // Initialize the database
        redeRepository.saveAndFlush(rede);

        // Get all the redeList where name contains DEFAULT_NAME
        defaultRedeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the redeList where name contains UPDATED_NAME
        defaultRedeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRedesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        redeRepository.saveAndFlush(rede);

        // Get all the redeList where name does not contain DEFAULT_NAME
        defaultRedeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the redeList where name does not contain UPDATED_NAME
        defaultRedeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllRedesByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        redeRepository.saveAndFlush(rede);

        // Get all the redeList where descricao equals to DEFAULT_DESCRICAO
        defaultRedeShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the redeList where descricao equals to UPDATED_DESCRICAO
        defaultRedeShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllRedesByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        redeRepository.saveAndFlush(rede);

        // Get all the redeList where descricao not equals to DEFAULT_DESCRICAO
        defaultRedeShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the redeList where descricao not equals to UPDATED_DESCRICAO
        defaultRedeShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllRedesByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        redeRepository.saveAndFlush(rede);

        // Get all the redeList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultRedeShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the redeList where descricao equals to UPDATED_DESCRICAO
        defaultRedeShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllRedesByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        redeRepository.saveAndFlush(rede);

        // Get all the redeList where descricao is not null
        defaultRedeShouldBeFound("descricao.specified=true");

        // Get all the redeList where descricao is null
        defaultRedeShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllRedesByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        redeRepository.saveAndFlush(rede);

        // Get all the redeList where descricao contains DEFAULT_DESCRICAO
        defaultRedeShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the redeList where descricao contains UPDATED_DESCRICAO
        defaultRedeShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllRedesByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        redeRepository.saveAndFlush(rede);

        // Get all the redeList where descricao does not contain DEFAULT_DESCRICAO
        defaultRedeShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the redeList where descricao does not contain UPDATED_DESCRICAO
        defaultRedeShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllRedesByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        redeRepository.saveAndFlush(rede);

        // Get all the redeList where created equals to DEFAULT_CREATED
        defaultRedeShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the redeList where created equals to UPDATED_CREATED
        defaultRedeShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllRedesByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        redeRepository.saveAndFlush(rede);

        // Get all the redeList where created not equals to DEFAULT_CREATED
        defaultRedeShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the redeList where created not equals to UPDATED_CREATED
        defaultRedeShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllRedesByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        redeRepository.saveAndFlush(rede);

        // Get all the redeList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultRedeShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the redeList where created equals to UPDATED_CREATED
        defaultRedeShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllRedesByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        redeRepository.saveAndFlush(rede);

        // Get all the redeList where created is not null
        defaultRedeShouldBeFound("created.specified=true");

        // Get all the redeList where created is null
        defaultRedeShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllRedesByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        redeRepository.saveAndFlush(rede);

        // Get all the redeList where updated equals to DEFAULT_UPDATED
        defaultRedeShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the redeList where updated equals to UPDATED_UPDATED
        defaultRedeShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllRedesByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        redeRepository.saveAndFlush(rede);

        // Get all the redeList where updated not equals to DEFAULT_UPDATED
        defaultRedeShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the redeList where updated not equals to UPDATED_UPDATED
        defaultRedeShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllRedesByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        redeRepository.saveAndFlush(rede);

        // Get all the redeList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultRedeShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the redeList where updated equals to UPDATED_UPDATED
        defaultRedeShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllRedesByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        redeRepository.saveAndFlush(rede);

        // Get all the redeList where updated is not null
        defaultRedeShouldBeFound("updated.specified=true");

        // Get all the redeList where updated is null
        defaultRedeShouldNotBeFound("updated.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRedeShouldBeFound(String filter) throws Exception {
        restRedeMockMvc.perform(get("/api/redes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rede.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restRedeMockMvc.perform(get("/api/redes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRedeShouldNotBeFound(String filter) throws Exception {
        restRedeMockMvc.perform(get("/api/redes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRedeMockMvc.perform(get("/api/redes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingRede() throws Exception {
        // Get the rede
        restRedeMockMvc.perform(get("/api/redes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRede() throws Exception {
        // Initialize the database
        redeRepository.saveAndFlush(rede);

        int databaseSizeBeforeUpdate = redeRepository.findAll().size();

        // Update the rede
        Rede updatedRede = redeRepository.findById(rede.getId()).get();
        // Disconnect from session so that the updates on updatedRede are not directly saved in db
        em.detach(updatedRede);
        updatedRede
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        RedeDTO redeDTO = redeMapper.toDto(updatedRede);

        restRedeMockMvc.perform(put("/api/redes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(redeDTO)))
            .andExpect(status().isOk());

        // Validate the Rede in the database
        List<Rede> redeList = redeRepository.findAll();
        assertThat(redeList).hasSize(databaseSizeBeforeUpdate);
        Rede testRede = redeList.get(redeList.size() - 1);
        assertThat(testRede.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRede.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testRede.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testRede.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingRede() throws Exception {
        int databaseSizeBeforeUpdate = redeRepository.findAll().size();

        // Create the Rede
        RedeDTO redeDTO = redeMapper.toDto(rede);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRedeMockMvc.perform(put("/api/redes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(redeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Rede in the database
        List<Rede> redeList = redeRepository.findAll();
        assertThat(redeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRede() throws Exception {
        // Initialize the database
        redeRepository.saveAndFlush(rede);

        int databaseSizeBeforeDelete = redeRepository.findAll().size();

        // Delete the rede
        restRedeMockMvc.perform(delete("/api/redes/{id}", rede.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Rede> redeList = redeRepository.findAll();
        assertThat(redeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
