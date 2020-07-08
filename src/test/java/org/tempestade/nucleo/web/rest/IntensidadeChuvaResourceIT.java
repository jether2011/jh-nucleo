package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.IntensidadeChuva;
import org.tempestade.nucleo.repository.IntensidadeChuvaRepository;
import org.tempestade.nucleo.service.IntensidadeChuvaService;
import org.tempestade.nucleo.service.dto.IntensidadeChuvaDTO;
import org.tempestade.nucleo.service.mapper.IntensidadeChuvaMapper;

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
 * Integration tests for the {@link IntensidadeChuvaResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class IntensidadeChuvaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_FAIXA = "AAAAAAAAAA";
    private static final String UPDATED_FAIXA = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private IntensidadeChuvaRepository intensidadeChuvaRepository;

    @Autowired
    private IntensidadeChuvaMapper intensidadeChuvaMapper;

    @Autowired
    private IntensidadeChuvaService intensidadeChuvaService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIntensidadeChuvaMockMvc;

    private IntensidadeChuva intensidadeChuva;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IntensidadeChuva createEntity(EntityManager em) {
        IntensidadeChuva intensidadeChuva = new IntensidadeChuva()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .faixa(DEFAULT_FAIXA)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return intensidadeChuva;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IntensidadeChuva createUpdatedEntity(EntityManager em) {
        IntensidadeChuva intensidadeChuva = new IntensidadeChuva()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .faixa(UPDATED_FAIXA)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return intensidadeChuva;
    }

    @BeforeEach
    public void initTest() {
        intensidadeChuva = createEntity(em);
    }

    @Test
    @Transactional
    public void createIntensidadeChuva() throws Exception {
        int databaseSizeBeforeCreate = intensidadeChuvaRepository.findAll().size();
        // Create the IntensidadeChuva
        IntensidadeChuvaDTO intensidadeChuvaDTO = intensidadeChuvaMapper.toDto(intensidadeChuva);
        restIntensidadeChuvaMockMvc.perform(post("/api/intensidade-chuvas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(intensidadeChuvaDTO)))
            .andExpect(status().isCreated());

        // Validate the IntensidadeChuva in the database
        List<IntensidadeChuva> intensidadeChuvaList = intensidadeChuvaRepository.findAll();
        assertThat(intensidadeChuvaList).hasSize(databaseSizeBeforeCreate + 1);
        IntensidadeChuva testIntensidadeChuva = intensidadeChuvaList.get(intensidadeChuvaList.size() - 1);
        assertThat(testIntensidadeChuva.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testIntensidadeChuva.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testIntensidadeChuva.getFaixa()).isEqualTo(DEFAULT_FAIXA);
        assertThat(testIntensidadeChuva.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testIntensidadeChuva.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createIntensidadeChuvaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = intensidadeChuvaRepository.findAll().size();

        // Create the IntensidadeChuva with an existing ID
        intensidadeChuva.setId(1L);
        IntensidadeChuvaDTO intensidadeChuvaDTO = intensidadeChuvaMapper.toDto(intensidadeChuva);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIntensidadeChuvaMockMvc.perform(post("/api/intensidade-chuvas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(intensidadeChuvaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IntensidadeChuva in the database
        List<IntensidadeChuva> intensidadeChuvaList = intensidadeChuvaRepository.findAll();
        assertThat(intensidadeChuvaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = intensidadeChuvaRepository.findAll().size();
        // set the field null
        intensidadeChuva.setNome(null);

        // Create the IntensidadeChuva, which fails.
        IntensidadeChuvaDTO intensidadeChuvaDTO = intensidadeChuvaMapper.toDto(intensidadeChuva);


        restIntensidadeChuvaMockMvc.perform(post("/api/intensidade-chuvas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(intensidadeChuvaDTO)))
            .andExpect(status().isBadRequest());

        List<IntensidadeChuva> intensidadeChuvaList = intensidadeChuvaRepository.findAll();
        assertThat(intensidadeChuvaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = intensidadeChuvaRepository.findAll().size();
        // set the field null
        intensidadeChuva.setDescricao(null);

        // Create the IntensidadeChuva, which fails.
        IntensidadeChuvaDTO intensidadeChuvaDTO = intensidadeChuvaMapper.toDto(intensidadeChuva);


        restIntensidadeChuvaMockMvc.perform(post("/api/intensidade-chuvas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(intensidadeChuvaDTO)))
            .andExpect(status().isBadRequest());

        List<IntensidadeChuva> intensidadeChuvaList = intensidadeChuvaRepository.findAll();
        assertThat(intensidadeChuvaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFaixaIsRequired() throws Exception {
        int databaseSizeBeforeTest = intensidadeChuvaRepository.findAll().size();
        // set the field null
        intensidadeChuva.setFaixa(null);

        // Create the IntensidadeChuva, which fails.
        IntensidadeChuvaDTO intensidadeChuvaDTO = intensidadeChuvaMapper.toDto(intensidadeChuva);


        restIntensidadeChuvaMockMvc.perform(post("/api/intensidade-chuvas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(intensidadeChuvaDTO)))
            .andExpect(status().isBadRequest());

        List<IntensidadeChuva> intensidadeChuvaList = intensidadeChuvaRepository.findAll();
        assertThat(intensidadeChuvaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = intensidadeChuvaRepository.findAll().size();
        // set the field null
        intensidadeChuva.setCreated(null);

        // Create the IntensidadeChuva, which fails.
        IntensidadeChuvaDTO intensidadeChuvaDTO = intensidadeChuvaMapper.toDto(intensidadeChuva);


        restIntensidadeChuvaMockMvc.perform(post("/api/intensidade-chuvas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(intensidadeChuvaDTO)))
            .andExpect(status().isBadRequest());

        List<IntensidadeChuva> intensidadeChuvaList = intensidadeChuvaRepository.findAll();
        assertThat(intensidadeChuvaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIntensidadeChuvas() throws Exception {
        // Initialize the database
        intensidadeChuvaRepository.saveAndFlush(intensidadeChuva);

        // Get all the intensidadeChuvaList
        restIntensidadeChuvaMockMvc.perform(get("/api/intensidade-chuvas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(intensidadeChuva.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].faixa").value(hasItem(DEFAULT_FAIXA)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getIntensidadeChuva() throws Exception {
        // Initialize the database
        intensidadeChuvaRepository.saveAndFlush(intensidadeChuva);

        // Get the intensidadeChuva
        restIntensidadeChuvaMockMvc.perform(get("/api/intensidade-chuvas/{id}", intensidadeChuva.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(intensidadeChuva.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.faixa").value(DEFAULT_FAIXA))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingIntensidadeChuva() throws Exception {
        // Get the intensidadeChuva
        restIntensidadeChuvaMockMvc.perform(get("/api/intensidade-chuvas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIntensidadeChuva() throws Exception {
        // Initialize the database
        intensidadeChuvaRepository.saveAndFlush(intensidadeChuva);

        int databaseSizeBeforeUpdate = intensidadeChuvaRepository.findAll().size();

        // Update the intensidadeChuva
        IntensidadeChuva updatedIntensidadeChuva = intensidadeChuvaRepository.findById(intensidadeChuva.getId()).get();
        // Disconnect from session so that the updates on updatedIntensidadeChuva are not directly saved in db
        em.detach(updatedIntensidadeChuva);
        updatedIntensidadeChuva
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .faixa(UPDATED_FAIXA)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        IntensidadeChuvaDTO intensidadeChuvaDTO = intensidadeChuvaMapper.toDto(updatedIntensidadeChuva);

        restIntensidadeChuvaMockMvc.perform(put("/api/intensidade-chuvas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(intensidadeChuvaDTO)))
            .andExpect(status().isOk());

        // Validate the IntensidadeChuva in the database
        List<IntensidadeChuva> intensidadeChuvaList = intensidadeChuvaRepository.findAll();
        assertThat(intensidadeChuvaList).hasSize(databaseSizeBeforeUpdate);
        IntensidadeChuva testIntensidadeChuva = intensidadeChuvaList.get(intensidadeChuvaList.size() - 1);
        assertThat(testIntensidadeChuva.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testIntensidadeChuva.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testIntensidadeChuva.getFaixa()).isEqualTo(UPDATED_FAIXA);
        assertThat(testIntensidadeChuva.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testIntensidadeChuva.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingIntensidadeChuva() throws Exception {
        int databaseSizeBeforeUpdate = intensidadeChuvaRepository.findAll().size();

        // Create the IntensidadeChuva
        IntensidadeChuvaDTO intensidadeChuvaDTO = intensidadeChuvaMapper.toDto(intensidadeChuva);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIntensidadeChuvaMockMvc.perform(put("/api/intensidade-chuvas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(intensidadeChuvaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IntensidadeChuva in the database
        List<IntensidadeChuva> intensidadeChuvaList = intensidadeChuvaRepository.findAll();
        assertThat(intensidadeChuvaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIntensidadeChuva() throws Exception {
        // Initialize the database
        intensidadeChuvaRepository.saveAndFlush(intensidadeChuva);

        int databaseSizeBeforeDelete = intensidadeChuvaRepository.findAll().size();

        // Delete the intensidadeChuva
        restIntensidadeChuvaMockMvc.perform(delete("/api/intensidade-chuvas/{id}", intensidadeChuva.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IntensidadeChuva> intensidadeChuvaList = intensidadeChuvaRepository.findAll();
        assertThat(intensidadeChuvaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
