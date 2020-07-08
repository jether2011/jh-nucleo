package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.PlanoRede;
import org.tempestade.nucleo.repository.PlanoRedeRepository;
import org.tempestade.nucleo.service.PlanoRedeService;
import org.tempestade.nucleo.service.dto.PlanoRedeDTO;
import org.tempestade.nucleo.service.mapper.PlanoRedeMapper;

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
 * Integration tests for the {@link PlanoRedeResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PlanoRedeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PlanoRedeRepository planoRedeRepository;

    @Autowired
    private PlanoRedeMapper planoRedeMapper;

    @Autowired
    private PlanoRedeService planoRedeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlanoRedeMockMvc;

    private PlanoRede planoRede;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanoRede createEntity(EntityManager em) {
        PlanoRede planoRede = new PlanoRede()
            .name(DEFAULT_NAME)
            .descricao(DEFAULT_DESCRICAO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return planoRede;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanoRede createUpdatedEntity(EntityManager em) {
        PlanoRede planoRede = new PlanoRede()
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return planoRede;
    }

    @BeforeEach
    public void initTest() {
        planoRede = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlanoRede() throws Exception {
        int databaseSizeBeforeCreate = planoRedeRepository.findAll().size();
        // Create the PlanoRede
        PlanoRedeDTO planoRedeDTO = planoRedeMapper.toDto(planoRede);
        restPlanoRedeMockMvc.perform(post("/api/plano-redes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoRedeDTO)))
            .andExpect(status().isCreated());

        // Validate the PlanoRede in the database
        List<PlanoRede> planoRedeList = planoRedeRepository.findAll();
        assertThat(planoRedeList).hasSize(databaseSizeBeforeCreate + 1);
        PlanoRede testPlanoRede = planoRedeList.get(planoRedeList.size() - 1);
        assertThat(testPlanoRede.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPlanoRede.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testPlanoRede.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testPlanoRede.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createPlanoRedeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = planoRedeRepository.findAll().size();

        // Create the PlanoRede with an existing ID
        planoRede.setId(1L);
        PlanoRedeDTO planoRedeDTO = planoRedeMapper.toDto(planoRede);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanoRedeMockMvc.perform(post("/api/plano-redes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoRedeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PlanoRede in the database
        List<PlanoRede> planoRedeList = planoRedeRepository.findAll();
        assertThat(planoRedeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoRedeRepository.findAll().size();
        // set the field null
        planoRede.setName(null);

        // Create the PlanoRede, which fails.
        PlanoRedeDTO planoRedeDTO = planoRedeMapper.toDto(planoRede);


        restPlanoRedeMockMvc.perform(post("/api/plano-redes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoRedeDTO)))
            .andExpect(status().isBadRequest());

        List<PlanoRede> planoRedeList = planoRedeRepository.findAll();
        assertThat(planoRedeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoRedeRepository.findAll().size();
        // set the field null
        planoRede.setDescricao(null);

        // Create the PlanoRede, which fails.
        PlanoRedeDTO planoRedeDTO = planoRedeMapper.toDto(planoRede);


        restPlanoRedeMockMvc.perform(post("/api/plano-redes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoRedeDTO)))
            .andExpect(status().isBadRequest());

        List<PlanoRede> planoRedeList = planoRedeRepository.findAll();
        assertThat(planoRedeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoRedeRepository.findAll().size();
        // set the field null
        planoRede.setCreated(null);

        // Create the PlanoRede, which fails.
        PlanoRedeDTO planoRedeDTO = planoRedeMapper.toDto(planoRede);


        restPlanoRedeMockMvc.perform(post("/api/plano-redes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoRedeDTO)))
            .andExpect(status().isBadRequest());

        List<PlanoRede> planoRedeList = planoRedeRepository.findAll();
        assertThat(planoRedeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPlanoRedes() throws Exception {
        // Initialize the database
        planoRedeRepository.saveAndFlush(planoRede);

        // Get all the planoRedeList
        restPlanoRedeMockMvc.perform(get("/api/plano-redes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planoRede.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getPlanoRede() throws Exception {
        // Initialize the database
        planoRedeRepository.saveAndFlush(planoRede);

        // Get the planoRede
        restPlanoRedeMockMvc.perform(get("/api/plano-redes/{id}", planoRede.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(planoRede.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPlanoRede() throws Exception {
        // Get the planoRede
        restPlanoRedeMockMvc.perform(get("/api/plano-redes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlanoRede() throws Exception {
        // Initialize the database
        planoRedeRepository.saveAndFlush(planoRede);

        int databaseSizeBeforeUpdate = planoRedeRepository.findAll().size();

        // Update the planoRede
        PlanoRede updatedPlanoRede = planoRedeRepository.findById(planoRede.getId()).get();
        // Disconnect from session so that the updates on updatedPlanoRede are not directly saved in db
        em.detach(updatedPlanoRede);
        updatedPlanoRede
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        PlanoRedeDTO planoRedeDTO = planoRedeMapper.toDto(updatedPlanoRede);

        restPlanoRedeMockMvc.perform(put("/api/plano-redes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoRedeDTO)))
            .andExpect(status().isOk());

        // Validate the PlanoRede in the database
        List<PlanoRede> planoRedeList = planoRedeRepository.findAll();
        assertThat(planoRedeList).hasSize(databaseSizeBeforeUpdate);
        PlanoRede testPlanoRede = planoRedeList.get(planoRedeList.size() - 1);
        assertThat(testPlanoRede.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPlanoRede.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testPlanoRede.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testPlanoRede.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingPlanoRede() throws Exception {
        int databaseSizeBeforeUpdate = planoRedeRepository.findAll().size();

        // Create the PlanoRede
        PlanoRedeDTO planoRedeDTO = planoRedeMapper.toDto(planoRede);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanoRedeMockMvc.perform(put("/api/plano-redes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoRedeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PlanoRede in the database
        List<PlanoRede> planoRedeList = planoRedeRepository.findAll();
        assertThat(planoRedeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePlanoRede() throws Exception {
        // Initialize the database
        planoRedeRepository.saveAndFlush(planoRede);

        int databaseSizeBeforeDelete = planoRedeRepository.findAll().size();

        // Delete the planoRede
        restPlanoRedeMockMvc.perform(delete("/api/plano-redes/{id}", planoRede.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlanoRede> planoRedeList = planoRedeRepository.findAll();
        assertThat(planoRedeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
