package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.TempestadeProbabilidade;
import org.tempestade.nucleo.repository.TempestadeProbabilidadeRepository;
import org.tempestade.nucleo.service.TempestadeProbabilidadeService;
import org.tempestade.nucleo.service.dto.TempestadeProbabilidadeDTO;
import org.tempestade.nucleo.service.mapper.TempestadeProbabilidadeMapper;

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
 * Integration tests for the {@link TempestadeProbabilidadeResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TempestadeProbabilidadeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_FAIXA = "AAAAAAAAAA";
    private static final String UPDATED_FAIXA = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private TempestadeProbabilidadeRepository tempestadeProbabilidadeRepository;

    @Autowired
    private TempestadeProbabilidadeMapper tempestadeProbabilidadeMapper;

    @Autowired
    private TempestadeProbabilidadeService tempestadeProbabilidadeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTempestadeProbabilidadeMockMvc;

    private TempestadeProbabilidade tempestadeProbabilidade;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TempestadeProbabilidade createEntity(EntityManager em) {
        TempestadeProbabilidade tempestadeProbabilidade = new TempestadeProbabilidade()
            .name(DEFAULT_NAME)
            .descricao(DEFAULT_DESCRICAO)
            .faixa(DEFAULT_FAIXA)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return tempestadeProbabilidade;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TempestadeProbabilidade createUpdatedEntity(EntityManager em) {
        TempestadeProbabilidade tempestadeProbabilidade = new TempestadeProbabilidade()
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .faixa(UPDATED_FAIXA)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return tempestadeProbabilidade;
    }

    @BeforeEach
    public void initTest() {
        tempestadeProbabilidade = createEntity(em);
    }

    @Test
    @Transactional
    public void createTempestadeProbabilidade() throws Exception {
        int databaseSizeBeforeCreate = tempestadeProbabilidadeRepository.findAll().size();
        // Create the TempestadeProbabilidade
        TempestadeProbabilidadeDTO tempestadeProbabilidadeDTO = tempestadeProbabilidadeMapper.toDto(tempestadeProbabilidade);
        restTempestadeProbabilidadeMockMvc.perform(post("/api/tempestade-probabilidades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tempestadeProbabilidadeDTO)))
            .andExpect(status().isCreated());

        // Validate the TempestadeProbabilidade in the database
        List<TempestadeProbabilidade> tempestadeProbabilidadeList = tempestadeProbabilidadeRepository.findAll();
        assertThat(tempestadeProbabilidadeList).hasSize(databaseSizeBeforeCreate + 1);
        TempestadeProbabilidade testTempestadeProbabilidade = tempestadeProbabilidadeList.get(tempestadeProbabilidadeList.size() - 1);
        assertThat(testTempestadeProbabilidade.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTempestadeProbabilidade.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testTempestadeProbabilidade.getFaixa()).isEqualTo(DEFAULT_FAIXA);
        assertThat(testTempestadeProbabilidade.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testTempestadeProbabilidade.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createTempestadeProbabilidadeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tempestadeProbabilidadeRepository.findAll().size();

        // Create the TempestadeProbabilidade with an existing ID
        tempestadeProbabilidade.setId(1L);
        TempestadeProbabilidadeDTO tempestadeProbabilidadeDTO = tempestadeProbabilidadeMapper.toDto(tempestadeProbabilidade);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTempestadeProbabilidadeMockMvc.perform(post("/api/tempestade-probabilidades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tempestadeProbabilidadeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TempestadeProbabilidade in the database
        List<TempestadeProbabilidade> tempestadeProbabilidadeList = tempestadeProbabilidadeRepository.findAll();
        assertThat(tempestadeProbabilidadeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tempestadeProbabilidadeRepository.findAll().size();
        // set the field null
        tempestadeProbabilidade.setName(null);

        // Create the TempestadeProbabilidade, which fails.
        TempestadeProbabilidadeDTO tempestadeProbabilidadeDTO = tempestadeProbabilidadeMapper.toDto(tempestadeProbabilidade);


        restTempestadeProbabilidadeMockMvc.perform(post("/api/tempestade-probabilidades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tempestadeProbabilidadeDTO)))
            .andExpect(status().isBadRequest());

        List<TempestadeProbabilidade> tempestadeProbabilidadeList = tempestadeProbabilidadeRepository.findAll();
        assertThat(tempestadeProbabilidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tempestadeProbabilidadeRepository.findAll().size();
        // set the field null
        tempestadeProbabilidade.setDescricao(null);

        // Create the TempestadeProbabilidade, which fails.
        TempestadeProbabilidadeDTO tempestadeProbabilidadeDTO = tempestadeProbabilidadeMapper.toDto(tempestadeProbabilidade);


        restTempestadeProbabilidadeMockMvc.perform(post("/api/tempestade-probabilidades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tempestadeProbabilidadeDTO)))
            .andExpect(status().isBadRequest());

        List<TempestadeProbabilidade> tempestadeProbabilidadeList = tempestadeProbabilidadeRepository.findAll();
        assertThat(tempestadeProbabilidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = tempestadeProbabilidadeRepository.findAll().size();
        // set the field null
        tempestadeProbabilidade.setCreated(null);

        // Create the TempestadeProbabilidade, which fails.
        TempestadeProbabilidadeDTO tempestadeProbabilidadeDTO = tempestadeProbabilidadeMapper.toDto(tempestadeProbabilidade);


        restTempestadeProbabilidadeMockMvc.perform(post("/api/tempestade-probabilidades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tempestadeProbabilidadeDTO)))
            .andExpect(status().isBadRequest());

        List<TempestadeProbabilidade> tempestadeProbabilidadeList = tempestadeProbabilidadeRepository.findAll();
        assertThat(tempestadeProbabilidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTempestadeProbabilidades() throws Exception {
        // Initialize the database
        tempestadeProbabilidadeRepository.saveAndFlush(tempestadeProbabilidade);

        // Get all the tempestadeProbabilidadeList
        restTempestadeProbabilidadeMockMvc.perform(get("/api/tempestade-probabilidades?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tempestadeProbabilidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].faixa").value(hasItem(DEFAULT_FAIXA)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getTempestadeProbabilidade() throws Exception {
        // Initialize the database
        tempestadeProbabilidadeRepository.saveAndFlush(tempestadeProbabilidade);

        // Get the tempestadeProbabilidade
        restTempestadeProbabilidadeMockMvc.perform(get("/api/tempestade-probabilidades/{id}", tempestadeProbabilidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tempestadeProbabilidade.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.faixa").value(DEFAULT_FAIXA))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingTempestadeProbabilidade() throws Exception {
        // Get the tempestadeProbabilidade
        restTempestadeProbabilidadeMockMvc.perform(get("/api/tempestade-probabilidades/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTempestadeProbabilidade() throws Exception {
        // Initialize the database
        tempestadeProbabilidadeRepository.saveAndFlush(tempestadeProbabilidade);

        int databaseSizeBeforeUpdate = tempestadeProbabilidadeRepository.findAll().size();

        // Update the tempestadeProbabilidade
        TempestadeProbabilidade updatedTempestadeProbabilidade = tempestadeProbabilidadeRepository.findById(tempestadeProbabilidade.getId()).get();
        // Disconnect from session so that the updates on updatedTempestadeProbabilidade are not directly saved in db
        em.detach(updatedTempestadeProbabilidade);
        updatedTempestadeProbabilidade
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .faixa(UPDATED_FAIXA)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        TempestadeProbabilidadeDTO tempestadeProbabilidadeDTO = tempestadeProbabilidadeMapper.toDto(updatedTempestadeProbabilidade);

        restTempestadeProbabilidadeMockMvc.perform(put("/api/tempestade-probabilidades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tempestadeProbabilidadeDTO)))
            .andExpect(status().isOk());

        // Validate the TempestadeProbabilidade in the database
        List<TempestadeProbabilidade> tempestadeProbabilidadeList = tempestadeProbabilidadeRepository.findAll();
        assertThat(tempestadeProbabilidadeList).hasSize(databaseSizeBeforeUpdate);
        TempestadeProbabilidade testTempestadeProbabilidade = tempestadeProbabilidadeList.get(tempestadeProbabilidadeList.size() - 1);
        assertThat(testTempestadeProbabilidade.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTempestadeProbabilidade.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testTempestadeProbabilidade.getFaixa()).isEqualTo(UPDATED_FAIXA);
        assertThat(testTempestadeProbabilidade.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testTempestadeProbabilidade.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingTempestadeProbabilidade() throws Exception {
        int databaseSizeBeforeUpdate = tempestadeProbabilidadeRepository.findAll().size();

        // Create the TempestadeProbabilidade
        TempestadeProbabilidadeDTO tempestadeProbabilidadeDTO = tempestadeProbabilidadeMapper.toDto(tempestadeProbabilidade);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTempestadeProbabilidadeMockMvc.perform(put("/api/tempestade-probabilidades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tempestadeProbabilidadeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TempestadeProbabilidade in the database
        List<TempestadeProbabilidade> tempestadeProbabilidadeList = tempestadeProbabilidadeRepository.findAll();
        assertThat(tempestadeProbabilidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTempestadeProbabilidade() throws Exception {
        // Initialize the database
        tempestadeProbabilidadeRepository.saveAndFlush(tempestadeProbabilidade);

        int databaseSizeBeforeDelete = tempestadeProbabilidadeRepository.findAll().size();

        // Delete the tempestadeProbabilidade
        restTempestadeProbabilidadeMockMvc.perform(delete("/api/tempestade-probabilidades/{id}", tempestadeProbabilidade.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TempestadeProbabilidade> tempestadeProbabilidadeList = tempestadeProbabilidadeRepository.findAll();
        assertThat(tempestadeProbabilidadeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
