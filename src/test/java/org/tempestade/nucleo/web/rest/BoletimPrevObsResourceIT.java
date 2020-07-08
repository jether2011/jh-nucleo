package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.BoletimPrevObs;
import org.tempestade.nucleo.repository.BoletimPrevObsRepository;
import org.tempestade.nucleo.service.BoletimPrevObsService;
import org.tempestade.nucleo.service.dto.BoletimPrevObsDTO;
import org.tempestade.nucleo.service.mapper.BoletimPrevObsMapper;

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
 * Integration tests for the {@link BoletimPrevObsResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BoletimPrevObsResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private BoletimPrevObsRepository boletimPrevObsRepository;

    @Autowired
    private BoletimPrevObsMapper boletimPrevObsMapper;

    @Autowired
    private BoletimPrevObsService boletimPrevObsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBoletimPrevObsMockMvc;

    private BoletimPrevObs boletimPrevObs;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BoletimPrevObs createEntity(EntityManager em) {
        BoletimPrevObs boletimPrevObs = new BoletimPrevObs()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return boletimPrevObs;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BoletimPrevObs createUpdatedEntity(EntityManager em) {
        BoletimPrevObs boletimPrevObs = new BoletimPrevObs()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return boletimPrevObs;
    }

    @BeforeEach
    public void initTest() {
        boletimPrevObs = createEntity(em);
    }

    @Test
    @Transactional
    public void createBoletimPrevObs() throws Exception {
        int databaseSizeBeforeCreate = boletimPrevObsRepository.findAll().size();
        // Create the BoletimPrevObs
        BoletimPrevObsDTO boletimPrevObsDTO = boletimPrevObsMapper.toDto(boletimPrevObs);
        restBoletimPrevObsMockMvc.perform(post("/api/boletim-prev-obs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boletimPrevObsDTO)))
            .andExpect(status().isCreated());

        // Validate the BoletimPrevObs in the database
        List<BoletimPrevObs> boletimPrevObsList = boletimPrevObsRepository.findAll();
        assertThat(boletimPrevObsList).hasSize(databaseSizeBeforeCreate + 1);
        BoletimPrevObs testBoletimPrevObs = boletimPrevObsList.get(boletimPrevObsList.size() - 1);
        assertThat(testBoletimPrevObs.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testBoletimPrevObs.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testBoletimPrevObs.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testBoletimPrevObs.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createBoletimPrevObsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = boletimPrevObsRepository.findAll().size();

        // Create the BoletimPrevObs with an existing ID
        boletimPrevObs.setId(1L);
        BoletimPrevObsDTO boletimPrevObsDTO = boletimPrevObsMapper.toDto(boletimPrevObs);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBoletimPrevObsMockMvc.perform(post("/api/boletim-prev-obs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boletimPrevObsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BoletimPrevObs in the database
        List<BoletimPrevObs> boletimPrevObsList = boletimPrevObsRepository.findAll();
        assertThat(boletimPrevObsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = boletimPrevObsRepository.findAll().size();
        // set the field null
        boletimPrevObs.setNome(null);

        // Create the BoletimPrevObs, which fails.
        BoletimPrevObsDTO boletimPrevObsDTO = boletimPrevObsMapper.toDto(boletimPrevObs);


        restBoletimPrevObsMockMvc.perform(post("/api/boletim-prev-obs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boletimPrevObsDTO)))
            .andExpect(status().isBadRequest());

        List<BoletimPrevObs> boletimPrevObsList = boletimPrevObsRepository.findAll();
        assertThat(boletimPrevObsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = boletimPrevObsRepository.findAll().size();
        // set the field null
        boletimPrevObs.setCreated(null);

        // Create the BoletimPrevObs, which fails.
        BoletimPrevObsDTO boletimPrevObsDTO = boletimPrevObsMapper.toDto(boletimPrevObs);


        restBoletimPrevObsMockMvc.perform(post("/api/boletim-prev-obs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boletimPrevObsDTO)))
            .andExpect(status().isBadRequest());

        List<BoletimPrevObs> boletimPrevObsList = boletimPrevObsRepository.findAll();
        assertThat(boletimPrevObsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevObs() throws Exception {
        // Initialize the database
        boletimPrevObsRepository.saveAndFlush(boletimPrevObs);

        // Get all the boletimPrevObsList
        restBoletimPrevObsMockMvc.perform(get("/api/boletim-prev-obs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(boletimPrevObs.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getBoletimPrevObs() throws Exception {
        // Initialize the database
        boletimPrevObsRepository.saveAndFlush(boletimPrevObs);

        // Get the boletimPrevObs
        restBoletimPrevObsMockMvc.perform(get("/api/boletim-prev-obs/{id}", boletimPrevObs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(boletimPrevObs.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingBoletimPrevObs() throws Exception {
        // Get the boletimPrevObs
        restBoletimPrevObsMockMvc.perform(get("/api/boletim-prev-obs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBoletimPrevObs() throws Exception {
        // Initialize the database
        boletimPrevObsRepository.saveAndFlush(boletimPrevObs);

        int databaseSizeBeforeUpdate = boletimPrevObsRepository.findAll().size();

        // Update the boletimPrevObs
        BoletimPrevObs updatedBoletimPrevObs = boletimPrevObsRepository.findById(boletimPrevObs.getId()).get();
        // Disconnect from session so that the updates on updatedBoletimPrevObs are not directly saved in db
        em.detach(updatedBoletimPrevObs);
        updatedBoletimPrevObs
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        BoletimPrevObsDTO boletimPrevObsDTO = boletimPrevObsMapper.toDto(updatedBoletimPrevObs);

        restBoletimPrevObsMockMvc.perform(put("/api/boletim-prev-obs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boletimPrevObsDTO)))
            .andExpect(status().isOk());

        // Validate the BoletimPrevObs in the database
        List<BoletimPrevObs> boletimPrevObsList = boletimPrevObsRepository.findAll();
        assertThat(boletimPrevObsList).hasSize(databaseSizeBeforeUpdate);
        BoletimPrevObs testBoletimPrevObs = boletimPrevObsList.get(boletimPrevObsList.size() - 1);
        assertThat(testBoletimPrevObs.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testBoletimPrevObs.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testBoletimPrevObs.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testBoletimPrevObs.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingBoletimPrevObs() throws Exception {
        int databaseSizeBeforeUpdate = boletimPrevObsRepository.findAll().size();

        // Create the BoletimPrevObs
        BoletimPrevObsDTO boletimPrevObsDTO = boletimPrevObsMapper.toDto(boletimPrevObs);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBoletimPrevObsMockMvc.perform(put("/api/boletim-prev-obs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boletimPrevObsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BoletimPrevObs in the database
        List<BoletimPrevObs> boletimPrevObsList = boletimPrevObsRepository.findAll();
        assertThat(boletimPrevObsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBoletimPrevObs() throws Exception {
        // Initialize the database
        boletimPrevObsRepository.saveAndFlush(boletimPrevObs);

        int databaseSizeBeforeDelete = boletimPrevObsRepository.findAll().size();

        // Delete the boletimPrevObs
        restBoletimPrevObsMockMvc.perform(delete("/api/boletim-prev-obs/{id}", boletimPrevObs.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BoletimPrevObs> boletimPrevObsList = boletimPrevObsRepository.findAll();
        assertThat(boletimPrevObsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
