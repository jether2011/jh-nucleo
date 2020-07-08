package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.VariavelMeteorologica;
import org.tempestade.nucleo.repository.VariavelMeteorologicaRepository;
import org.tempestade.nucleo.service.VariavelMeteorologicaService;
import org.tempestade.nucleo.service.dto.VariavelMeteorologicaDTO;
import org.tempestade.nucleo.service.mapper.VariavelMeteorologicaMapper;

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
 * Integration tests for the {@link VariavelMeteorologicaResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class VariavelMeteorologicaResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private VariavelMeteorologicaRepository variavelMeteorologicaRepository;

    @Autowired
    private VariavelMeteorologicaMapper variavelMeteorologicaMapper;

    @Autowired
    private VariavelMeteorologicaService variavelMeteorologicaService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVariavelMeteorologicaMockMvc;

    private VariavelMeteorologica variavelMeteorologica;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VariavelMeteorologica createEntity(EntityManager em) {
        VariavelMeteorologica variavelMeteorologica = new VariavelMeteorologica()
            .name(DEFAULT_NAME)
            .descricao(DEFAULT_DESCRICAO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return variavelMeteorologica;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VariavelMeteorologica createUpdatedEntity(EntityManager em) {
        VariavelMeteorologica variavelMeteorologica = new VariavelMeteorologica()
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return variavelMeteorologica;
    }

    @BeforeEach
    public void initTest() {
        variavelMeteorologica = createEntity(em);
    }

    @Test
    @Transactional
    public void createVariavelMeteorologica() throws Exception {
        int databaseSizeBeforeCreate = variavelMeteorologicaRepository.findAll().size();
        // Create the VariavelMeteorologica
        VariavelMeteorologicaDTO variavelMeteorologicaDTO = variavelMeteorologicaMapper.toDto(variavelMeteorologica);
        restVariavelMeteorologicaMockMvc.perform(post("/api/variavel-meteorologicas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(variavelMeteorologicaDTO)))
            .andExpect(status().isCreated());

        // Validate the VariavelMeteorologica in the database
        List<VariavelMeteorologica> variavelMeteorologicaList = variavelMeteorologicaRepository.findAll();
        assertThat(variavelMeteorologicaList).hasSize(databaseSizeBeforeCreate + 1);
        VariavelMeteorologica testVariavelMeteorologica = variavelMeteorologicaList.get(variavelMeteorologicaList.size() - 1);
        assertThat(testVariavelMeteorologica.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVariavelMeteorologica.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testVariavelMeteorologica.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testVariavelMeteorologica.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createVariavelMeteorologicaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = variavelMeteorologicaRepository.findAll().size();

        // Create the VariavelMeteorologica with an existing ID
        variavelMeteorologica.setId(1L);
        VariavelMeteorologicaDTO variavelMeteorologicaDTO = variavelMeteorologicaMapper.toDto(variavelMeteorologica);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVariavelMeteorologicaMockMvc.perform(post("/api/variavel-meteorologicas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(variavelMeteorologicaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VariavelMeteorologica in the database
        List<VariavelMeteorologica> variavelMeteorologicaList = variavelMeteorologicaRepository.findAll();
        assertThat(variavelMeteorologicaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = variavelMeteorologicaRepository.findAll().size();
        // set the field null
        variavelMeteorologica.setName(null);

        // Create the VariavelMeteorologica, which fails.
        VariavelMeteorologicaDTO variavelMeteorologicaDTO = variavelMeteorologicaMapper.toDto(variavelMeteorologica);


        restVariavelMeteorologicaMockMvc.perform(post("/api/variavel-meteorologicas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(variavelMeteorologicaDTO)))
            .andExpect(status().isBadRequest());

        List<VariavelMeteorologica> variavelMeteorologicaList = variavelMeteorologicaRepository.findAll();
        assertThat(variavelMeteorologicaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = variavelMeteorologicaRepository.findAll().size();
        // set the field null
        variavelMeteorologica.setDescricao(null);

        // Create the VariavelMeteorologica, which fails.
        VariavelMeteorologicaDTO variavelMeteorologicaDTO = variavelMeteorologicaMapper.toDto(variavelMeteorologica);


        restVariavelMeteorologicaMockMvc.perform(post("/api/variavel-meteorologicas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(variavelMeteorologicaDTO)))
            .andExpect(status().isBadRequest());

        List<VariavelMeteorologica> variavelMeteorologicaList = variavelMeteorologicaRepository.findAll();
        assertThat(variavelMeteorologicaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = variavelMeteorologicaRepository.findAll().size();
        // set the field null
        variavelMeteorologica.setCreated(null);

        // Create the VariavelMeteorologica, which fails.
        VariavelMeteorologicaDTO variavelMeteorologicaDTO = variavelMeteorologicaMapper.toDto(variavelMeteorologica);


        restVariavelMeteorologicaMockMvc.perform(post("/api/variavel-meteorologicas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(variavelMeteorologicaDTO)))
            .andExpect(status().isBadRequest());

        List<VariavelMeteorologica> variavelMeteorologicaList = variavelMeteorologicaRepository.findAll();
        assertThat(variavelMeteorologicaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVariavelMeteorologicas() throws Exception {
        // Initialize the database
        variavelMeteorologicaRepository.saveAndFlush(variavelMeteorologica);

        // Get all the variavelMeteorologicaList
        restVariavelMeteorologicaMockMvc.perform(get("/api/variavel-meteorologicas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(variavelMeteorologica.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getVariavelMeteorologica() throws Exception {
        // Initialize the database
        variavelMeteorologicaRepository.saveAndFlush(variavelMeteorologica);

        // Get the variavelMeteorologica
        restVariavelMeteorologicaMockMvc.perform(get("/api/variavel-meteorologicas/{id}", variavelMeteorologica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(variavelMeteorologica.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingVariavelMeteorologica() throws Exception {
        // Get the variavelMeteorologica
        restVariavelMeteorologicaMockMvc.perform(get("/api/variavel-meteorologicas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVariavelMeteorologica() throws Exception {
        // Initialize the database
        variavelMeteorologicaRepository.saveAndFlush(variavelMeteorologica);

        int databaseSizeBeforeUpdate = variavelMeteorologicaRepository.findAll().size();

        // Update the variavelMeteorologica
        VariavelMeteorologica updatedVariavelMeteorologica = variavelMeteorologicaRepository.findById(variavelMeteorologica.getId()).get();
        // Disconnect from session so that the updates on updatedVariavelMeteorologica are not directly saved in db
        em.detach(updatedVariavelMeteorologica);
        updatedVariavelMeteorologica
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        VariavelMeteorologicaDTO variavelMeteorologicaDTO = variavelMeteorologicaMapper.toDto(updatedVariavelMeteorologica);

        restVariavelMeteorologicaMockMvc.perform(put("/api/variavel-meteorologicas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(variavelMeteorologicaDTO)))
            .andExpect(status().isOk());

        // Validate the VariavelMeteorologica in the database
        List<VariavelMeteorologica> variavelMeteorologicaList = variavelMeteorologicaRepository.findAll();
        assertThat(variavelMeteorologicaList).hasSize(databaseSizeBeforeUpdate);
        VariavelMeteorologica testVariavelMeteorologica = variavelMeteorologicaList.get(variavelMeteorologicaList.size() - 1);
        assertThat(testVariavelMeteorologica.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVariavelMeteorologica.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testVariavelMeteorologica.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testVariavelMeteorologica.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingVariavelMeteorologica() throws Exception {
        int databaseSizeBeforeUpdate = variavelMeteorologicaRepository.findAll().size();

        // Create the VariavelMeteorologica
        VariavelMeteorologicaDTO variavelMeteorologicaDTO = variavelMeteorologicaMapper.toDto(variavelMeteorologica);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVariavelMeteorologicaMockMvc.perform(put("/api/variavel-meteorologicas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(variavelMeteorologicaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VariavelMeteorologica in the database
        List<VariavelMeteorologica> variavelMeteorologicaList = variavelMeteorologicaRepository.findAll();
        assertThat(variavelMeteorologicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVariavelMeteorologica() throws Exception {
        // Initialize the database
        variavelMeteorologicaRepository.saveAndFlush(variavelMeteorologica);

        int databaseSizeBeforeDelete = variavelMeteorologicaRepository.findAll().size();

        // Delete the variavelMeteorologica
        restVariavelMeteorologicaMockMvc.perform(delete("/api/variavel-meteorologicas/{id}", variavelMeteorologica.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VariavelMeteorologica> variavelMeteorologicaList = variavelMeteorologicaRepository.findAll();
        assertThat(variavelMeteorologicaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
