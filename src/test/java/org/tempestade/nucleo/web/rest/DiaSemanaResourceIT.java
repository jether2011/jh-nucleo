package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.DiaSemana;
import org.tempestade.nucleo.repository.DiaSemanaRepository;
import org.tempestade.nucleo.service.DiaSemanaService;
import org.tempestade.nucleo.service.dto.DiaSemanaDTO;
import org.tempestade.nucleo.service.mapper.DiaSemanaMapper;

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
 * Integration tests for the {@link DiaSemanaResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DiaSemanaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private DiaSemanaRepository diaSemanaRepository;

    @Autowired
    private DiaSemanaMapper diaSemanaMapper;

    @Autowired
    private DiaSemanaService diaSemanaService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDiaSemanaMockMvc;

    private DiaSemana diaSemana;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DiaSemana createEntity(EntityManager em) {
        DiaSemana diaSemana = new DiaSemana()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return diaSemana;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DiaSemana createUpdatedEntity(EntityManager em) {
        DiaSemana diaSemana = new DiaSemana()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return diaSemana;
    }

    @BeforeEach
    public void initTest() {
        diaSemana = createEntity(em);
    }

    @Test
    @Transactional
    public void createDiaSemana() throws Exception {
        int databaseSizeBeforeCreate = diaSemanaRepository.findAll().size();
        // Create the DiaSemana
        DiaSemanaDTO diaSemanaDTO = diaSemanaMapper.toDto(diaSemana);
        restDiaSemanaMockMvc.perform(post("/api/dia-semanas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(diaSemanaDTO)))
            .andExpect(status().isCreated());

        // Validate the DiaSemana in the database
        List<DiaSemana> diaSemanaList = diaSemanaRepository.findAll();
        assertThat(diaSemanaList).hasSize(databaseSizeBeforeCreate + 1);
        DiaSemana testDiaSemana = diaSemanaList.get(diaSemanaList.size() - 1);
        assertThat(testDiaSemana.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testDiaSemana.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testDiaSemana.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testDiaSemana.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createDiaSemanaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = diaSemanaRepository.findAll().size();

        // Create the DiaSemana with an existing ID
        diaSemana.setId(1L);
        DiaSemanaDTO diaSemanaDTO = diaSemanaMapper.toDto(diaSemana);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiaSemanaMockMvc.perform(post("/api/dia-semanas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(diaSemanaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DiaSemana in the database
        List<DiaSemana> diaSemanaList = diaSemanaRepository.findAll();
        assertThat(diaSemanaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = diaSemanaRepository.findAll().size();
        // set the field null
        diaSemana.setNome(null);

        // Create the DiaSemana, which fails.
        DiaSemanaDTO diaSemanaDTO = diaSemanaMapper.toDto(diaSemana);


        restDiaSemanaMockMvc.perform(post("/api/dia-semanas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(diaSemanaDTO)))
            .andExpect(status().isBadRequest());

        List<DiaSemana> diaSemanaList = diaSemanaRepository.findAll();
        assertThat(diaSemanaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = diaSemanaRepository.findAll().size();
        // set the field null
        diaSemana.setCreated(null);

        // Create the DiaSemana, which fails.
        DiaSemanaDTO diaSemanaDTO = diaSemanaMapper.toDto(diaSemana);


        restDiaSemanaMockMvc.perform(post("/api/dia-semanas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(diaSemanaDTO)))
            .andExpect(status().isBadRequest());

        List<DiaSemana> diaSemanaList = diaSemanaRepository.findAll();
        assertThat(diaSemanaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDiaSemanas() throws Exception {
        // Initialize the database
        diaSemanaRepository.saveAndFlush(diaSemana);

        // Get all the diaSemanaList
        restDiaSemanaMockMvc.perform(get("/api/dia-semanas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(diaSemana.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getDiaSemana() throws Exception {
        // Initialize the database
        diaSemanaRepository.saveAndFlush(diaSemana);

        // Get the diaSemana
        restDiaSemanaMockMvc.perform(get("/api/dia-semanas/{id}", diaSemana.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(diaSemana.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingDiaSemana() throws Exception {
        // Get the diaSemana
        restDiaSemanaMockMvc.perform(get("/api/dia-semanas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDiaSemana() throws Exception {
        // Initialize the database
        diaSemanaRepository.saveAndFlush(diaSemana);

        int databaseSizeBeforeUpdate = diaSemanaRepository.findAll().size();

        // Update the diaSemana
        DiaSemana updatedDiaSemana = diaSemanaRepository.findById(diaSemana.getId()).get();
        // Disconnect from session so that the updates on updatedDiaSemana are not directly saved in db
        em.detach(updatedDiaSemana);
        updatedDiaSemana
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        DiaSemanaDTO diaSemanaDTO = diaSemanaMapper.toDto(updatedDiaSemana);

        restDiaSemanaMockMvc.perform(put("/api/dia-semanas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(diaSemanaDTO)))
            .andExpect(status().isOk());

        // Validate the DiaSemana in the database
        List<DiaSemana> diaSemanaList = diaSemanaRepository.findAll();
        assertThat(diaSemanaList).hasSize(databaseSizeBeforeUpdate);
        DiaSemana testDiaSemana = diaSemanaList.get(diaSemanaList.size() - 1);
        assertThat(testDiaSemana.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testDiaSemana.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testDiaSemana.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testDiaSemana.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingDiaSemana() throws Exception {
        int databaseSizeBeforeUpdate = diaSemanaRepository.findAll().size();

        // Create the DiaSemana
        DiaSemanaDTO diaSemanaDTO = diaSemanaMapper.toDto(diaSemana);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiaSemanaMockMvc.perform(put("/api/dia-semanas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(diaSemanaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DiaSemana in the database
        List<DiaSemana> diaSemanaList = diaSemanaRepository.findAll();
        assertThat(diaSemanaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDiaSemana() throws Exception {
        // Initialize the database
        diaSemanaRepository.saveAndFlush(diaSemana);

        int databaseSizeBeforeDelete = diaSemanaRepository.findAll().size();

        // Delete the diaSemana
        restDiaSemanaMockMvc.perform(delete("/api/dia-semanas/{id}", diaSemana.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DiaSemana> diaSemanaList = diaSemanaRepository.findAll();
        assertThat(diaSemanaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
