package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.BoletimPrevVariavelMet;
import org.tempestade.nucleo.repository.BoletimPrevVariavelMetRepository;
import org.tempestade.nucleo.service.BoletimPrevVariavelMetService;
import org.tempestade.nucleo.service.dto.BoletimPrevVariavelMetDTO;
import org.tempestade.nucleo.service.mapper.BoletimPrevVariavelMetMapper;

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
 * Integration tests for the {@link BoletimPrevVariavelMetResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BoletimPrevVariavelMetResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private BoletimPrevVariavelMetRepository boletimPrevVariavelMetRepository;

    @Autowired
    private BoletimPrevVariavelMetMapper boletimPrevVariavelMetMapper;

    @Autowired
    private BoletimPrevVariavelMetService boletimPrevVariavelMetService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBoletimPrevVariavelMetMockMvc;

    private BoletimPrevVariavelMet boletimPrevVariavelMet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BoletimPrevVariavelMet createEntity(EntityManager em) {
        BoletimPrevVariavelMet boletimPrevVariavelMet = new BoletimPrevVariavelMet()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return boletimPrevVariavelMet;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BoletimPrevVariavelMet createUpdatedEntity(EntityManager em) {
        BoletimPrevVariavelMet boletimPrevVariavelMet = new BoletimPrevVariavelMet()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return boletimPrevVariavelMet;
    }

    @BeforeEach
    public void initTest() {
        boletimPrevVariavelMet = createEntity(em);
    }

    @Test
    @Transactional
    public void createBoletimPrevVariavelMet() throws Exception {
        int databaseSizeBeforeCreate = boletimPrevVariavelMetRepository.findAll().size();
        // Create the BoletimPrevVariavelMet
        BoletimPrevVariavelMetDTO boletimPrevVariavelMetDTO = boletimPrevVariavelMetMapper.toDto(boletimPrevVariavelMet);
        restBoletimPrevVariavelMetMockMvc.perform(post("/api/boletim-prev-variavel-mets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boletimPrevVariavelMetDTO)))
            .andExpect(status().isCreated());

        // Validate the BoletimPrevVariavelMet in the database
        List<BoletimPrevVariavelMet> boletimPrevVariavelMetList = boletimPrevVariavelMetRepository.findAll();
        assertThat(boletimPrevVariavelMetList).hasSize(databaseSizeBeforeCreate + 1);
        BoletimPrevVariavelMet testBoletimPrevVariavelMet = boletimPrevVariavelMetList.get(boletimPrevVariavelMetList.size() - 1);
        assertThat(testBoletimPrevVariavelMet.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testBoletimPrevVariavelMet.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testBoletimPrevVariavelMet.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testBoletimPrevVariavelMet.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createBoletimPrevVariavelMetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = boletimPrevVariavelMetRepository.findAll().size();

        // Create the BoletimPrevVariavelMet with an existing ID
        boletimPrevVariavelMet.setId(1L);
        BoletimPrevVariavelMetDTO boletimPrevVariavelMetDTO = boletimPrevVariavelMetMapper.toDto(boletimPrevVariavelMet);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBoletimPrevVariavelMetMockMvc.perform(post("/api/boletim-prev-variavel-mets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boletimPrevVariavelMetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BoletimPrevVariavelMet in the database
        List<BoletimPrevVariavelMet> boletimPrevVariavelMetList = boletimPrevVariavelMetRepository.findAll();
        assertThat(boletimPrevVariavelMetList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = boletimPrevVariavelMetRepository.findAll().size();
        // set the field null
        boletimPrevVariavelMet.setNome(null);

        // Create the BoletimPrevVariavelMet, which fails.
        BoletimPrevVariavelMetDTO boletimPrevVariavelMetDTO = boletimPrevVariavelMetMapper.toDto(boletimPrevVariavelMet);


        restBoletimPrevVariavelMetMockMvc.perform(post("/api/boletim-prev-variavel-mets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boletimPrevVariavelMetDTO)))
            .andExpect(status().isBadRequest());

        List<BoletimPrevVariavelMet> boletimPrevVariavelMetList = boletimPrevVariavelMetRepository.findAll();
        assertThat(boletimPrevVariavelMetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = boletimPrevVariavelMetRepository.findAll().size();
        // set the field null
        boletimPrevVariavelMet.setCreated(null);

        // Create the BoletimPrevVariavelMet, which fails.
        BoletimPrevVariavelMetDTO boletimPrevVariavelMetDTO = boletimPrevVariavelMetMapper.toDto(boletimPrevVariavelMet);


        restBoletimPrevVariavelMetMockMvc.perform(post("/api/boletim-prev-variavel-mets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boletimPrevVariavelMetDTO)))
            .andExpect(status().isBadRequest());

        List<BoletimPrevVariavelMet> boletimPrevVariavelMetList = boletimPrevVariavelMetRepository.findAll();
        assertThat(boletimPrevVariavelMetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevVariavelMets() throws Exception {
        // Initialize the database
        boletimPrevVariavelMetRepository.saveAndFlush(boletimPrevVariavelMet);

        // Get all the boletimPrevVariavelMetList
        restBoletimPrevVariavelMetMockMvc.perform(get("/api/boletim-prev-variavel-mets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(boletimPrevVariavelMet.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getBoletimPrevVariavelMet() throws Exception {
        // Initialize the database
        boletimPrevVariavelMetRepository.saveAndFlush(boletimPrevVariavelMet);

        // Get the boletimPrevVariavelMet
        restBoletimPrevVariavelMetMockMvc.perform(get("/api/boletim-prev-variavel-mets/{id}", boletimPrevVariavelMet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(boletimPrevVariavelMet.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingBoletimPrevVariavelMet() throws Exception {
        // Get the boletimPrevVariavelMet
        restBoletimPrevVariavelMetMockMvc.perform(get("/api/boletim-prev-variavel-mets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBoletimPrevVariavelMet() throws Exception {
        // Initialize the database
        boletimPrevVariavelMetRepository.saveAndFlush(boletimPrevVariavelMet);

        int databaseSizeBeforeUpdate = boletimPrevVariavelMetRepository.findAll().size();

        // Update the boletimPrevVariavelMet
        BoletimPrevVariavelMet updatedBoletimPrevVariavelMet = boletimPrevVariavelMetRepository.findById(boletimPrevVariavelMet.getId()).get();
        // Disconnect from session so that the updates on updatedBoletimPrevVariavelMet are not directly saved in db
        em.detach(updatedBoletimPrevVariavelMet);
        updatedBoletimPrevVariavelMet
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        BoletimPrevVariavelMetDTO boletimPrevVariavelMetDTO = boletimPrevVariavelMetMapper.toDto(updatedBoletimPrevVariavelMet);

        restBoletimPrevVariavelMetMockMvc.perform(put("/api/boletim-prev-variavel-mets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boletimPrevVariavelMetDTO)))
            .andExpect(status().isOk());

        // Validate the BoletimPrevVariavelMet in the database
        List<BoletimPrevVariavelMet> boletimPrevVariavelMetList = boletimPrevVariavelMetRepository.findAll();
        assertThat(boletimPrevVariavelMetList).hasSize(databaseSizeBeforeUpdate);
        BoletimPrevVariavelMet testBoletimPrevVariavelMet = boletimPrevVariavelMetList.get(boletimPrevVariavelMetList.size() - 1);
        assertThat(testBoletimPrevVariavelMet.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testBoletimPrevVariavelMet.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testBoletimPrevVariavelMet.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testBoletimPrevVariavelMet.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingBoletimPrevVariavelMet() throws Exception {
        int databaseSizeBeforeUpdate = boletimPrevVariavelMetRepository.findAll().size();

        // Create the BoletimPrevVariavelMet
        BoletimPrevVariavelMetDTO boletimPrevVariavelMetDTO = boletimPrevVariavelMetMapper.toDto(boletimPrevVariavelMet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBoletimPrevVariavelMetMockMvc.perform(put("/api/boletim-prev-variavel-mets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boletimPrevVariavelMetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BoletimPrevVariavelMet in the database
        List<BoletimPrevVariavelMet> boletimPrevVariavelMetList = boletimPrevVariavelMetRepository.findAll();
        assertThat(boletimPrevVariavelMetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBoletimPrevVariavelMet() throws Exception {
        // Initialize the database
        boletimPrevVariavelMetRepository.saveAndFlush(boletimPrevVariavelMet);

        int databaseSizeBeforeDelete = boletimPrevVariavelMetRepository.findAll().size();

        // Delete the boletimPrevVariavelMet
        restBoletimPrevVariavelMetMockMvc.perform(delete("/api/boletim-prev-variavel-mets/{id}", boletimPrevVariavelMet.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BoletimPrevVariavelMet> boletimPrevVariavelMetList = boletimPrevVariavelMetRepository.findAll();
        assertThat(boletimPrevVariavelMetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
