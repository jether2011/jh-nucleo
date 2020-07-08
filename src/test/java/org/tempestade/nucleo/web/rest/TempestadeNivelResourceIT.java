package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.TempestadeNivel;
import org.tempestade.nucleo.repository.TempestadeNivelRepository;
import org.tempestade.nucleo.service.TempestadeNivelService;
import org.tempestade.nucleo.service.dto.TempestadeNivelDTO;
import org.tempestade.nucleo.service.mapper.TempestadeNivelMapper;

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
 * Integration tests for the {@link TempestadeNivelResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TempestadeNivelResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_TAXA_DE_RAIOS = "AAAAAAAAAA";
    private static final String UPDATED_TAXA_DE_RAIOS = "BBBBBBBBBB";

    private static final String DEFAULT_VENTOS_VELOCIDADE = "AAAAAAAAAA";
    private static final String UPDATED_VENTOS_VELOCIDADE = "BBBBBBBBBB";

    private static final String DEFAULT_GRANIZO = "AAAAAAAAAA";
    private static final String UPDATED_GRANIZO = "BBBBBBBBBB";

    private static final String DEFAULT_POTENCIAL_DE_DANOS = "AAAAAAAAAA";
    private static final String UPDATED_POTENCIAL_DE_DANOS = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private TempestadeNivelRepository tempestadeNivelRepository;

    @Autowired
    private TempestadeNivelMapper tempestadeNivelMapper;

    @Autowired
    private TempestadeNivelService tempestadeNivelService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTempestadeNivelMockMvc;

    private TempestadeNivel tempestadeNivel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TempestadeNivel createEntity(EntityManager em) {
        TempestadeNivel tempestadeNivel = new TempestadeNivel()
            .name(DEFAULT_NAME)
            .descricao(DEFAULT_DESCRICAO)
            .taxaDeRaios(DEFAULT_TAXA_DE_RAIOS)
            .ventosVelocidade(DEFAULT_VENTOS_VELOCIDADE)
            .granizo(DEFAULT_GRANIZO)
            .potencialDeDanos(DEFAULT_POTENCIAL_DE_DANOS)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return tempestadeNivel;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TempestadeNivel createUpdatedEntity(EntityManager em) {
        TempestadeNivel tempestadeNivel = new TempestadeNivel()
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .taxaDeRaios(UPDATED_TAXA_DE_RAIOS)
            .ventosVelocidade(UPDATED_VENTOS_VELOCIDADE)
            .granizo(UPDATED_GRANIZO)
            .potencialDeDanos(UPDATED_POTENCIAL_DE_DANOS)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return tempestadeNivel;
    }

    @BeforeEach
    public void initTest() {
        tempestadeNivel = createEntity(em);
    }

    @Test
    @Transactional
    public void createTempestadeNivel() throws Exception {
        int databaseSizeBeforeCreate = tempestadeNivelRepository.findAll().size();
        // Create the TempestadeNivel
        TempestadeNivelDTO tempestadeNivelDTO = tempestadeNivelMapper.toDto(tempestadeNivel);
        restTempestadeNivelMockMvc.perform(post("/api/tempestade-nivels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tempestadeNivelDTO)))
            .andExpect(status().isCreated());

        // Validate the TempestadeNivel in the database
        List<TempestadeNivel> tempestadeNivelList = tempestadeNivelRepository.findAll();
        assertThat(tempestadeNivelList).hasSize(databaseSizeBeforeCreate + 1);
        TempestadeNivel testTempestadeNivel = tempestadeNivelList.get(tempestadeNivelList.size() - 1);
        assertThat(testTempestadeNivel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTempestadeNivel.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testTempestadeNivel.getTaxaDeRaios()).isEqualTo(DEFAULT_TAXA_DE_RAIOS);
        assertThat(testTempestadeNivel.getVentosVelocidade()).isEqualTo(DEFAULT_VENTOS_VELOCIDADE);
        assertThat(testTempestadeNivel.getGranizo()).isEqualTo(DEFAULT_GRANIZO);
        assertThat(testTempestadeNivel.getPotencialDeDanos()).isEqualTo(DEFAULT_POTENCIAL_DE_DANOS);
        assertThat(testTempestadeNivel.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testTempestadeNivel.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createTempestadeNivelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tempestadeNivelRepository.findAll().size();

        // Create the TempestadeNivel with an existing ID
        tempestadeNivel.setId(1L);
        TempestadeNivelDTO tempestadeNivelDTO = tempestadeNivelMapper.toDto(tempestadeNivel);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTempestadeNivelMockMvc.perform(post("/api/tempestade-nivels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tempestadeNivelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TempestadeNivel in the database
        List<TempestadeNivel> tempestadeNivelList = tempestadeNivelRepository.findAll();
        assertThat(tempestadeNivelList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tempestadeNivelRepository.findAll().size();
        // set the field null
        tempestadeNivel.setName(null);

        // Create the TempestadeNivel, which fails.
        TempestadeNivelDTO tempestadeNivelDTO = tempestadeNivelMapper.toDto(tempestadeNivel);


        restTempestadeNivelMockMvc.perform(post("/api/tempestade-nivels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tempestadeNivelDTO)))
            .andExpect(status().isBadRequest());

        List<TempestadeNivel> tempestadeNivelList = tempestadeNivelRepository.findAll();
        assertThat(tempestadeNivelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tempestadeNivelRepository.findAll().size();
        // set the field null
        tempestadeNivel.setDescricao(null);

        // Create the TempestadeNivel, which fails.
        TempestadeNivelDTO tempestadeNivelDTO = tempestadeNivelMapper.toDto(tempestadeNivel);


        restTempestadeNivelMockMvc.perform(post("/api/tempestade-nivels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tempestadeNivelDTO)))
            .andExpect(status().isBadRequest());

        List<TempestadeNivel> tempestadeNivelList = tempestadeNivelRepository.findAll();
        assertThat(tempestadeNivelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = tempestadeNivelRepository.findAll().size();
        // set the field null
        tempestadeNivel.setCreated(null);

        // Create the TempestadeNivel, which fails.
        TempestadeNivelDTO tempestadeNivelDTO = tempestadeNivelMapper.toDto(tempestadeNivel);


        restTempestadeNivelMockMvc.perform(post("/api/tempestade-nivels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tempestadeNivelDTO)))
            .andExpect(status().isBadRequest());

        List<TempestadeNivel> tempestadeNivelList = tempestadeNivelRepository.findAll();
        assertThat(tempestadeNivelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTempestadeNivels() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        // Get all the tempestadeNivelList
        restTempestadeNivelMockMvc.perform(get("/api/tempestade-nivels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tempestadeNivel.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].taxaDeRaios").value(hasItem(DEFAULT_TAXA_DE_RAIOS)))
            .andExpect(jsonPath("$.[*].ventosVelocidade").value(hasItem(DEFAULT_VENTOS_VELOCIDADE)))
            .andExpect(jsonPath("$.[*].granizo").value(hasItem(DEFAULT_GRANIZO)))
            .andExpect(jsonPath("$.[*].potencialDeDanos").value(hasItem(DEFAULT_POTENCIAL_DE_DANOS)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getTempestadeNivel() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        // Get the tempestadeNivel
        restTempestadeNivelMockMvc.perform(get("/api/tempestade-nivels/{id}", tempestadeNivel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tempestadeNivel.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.taxaDeRaios").value(DEFAULT_TAXA_DE_RAIOS))
            .andExpect(jsonPath("$.ventosVelocidade").value(DEFAULT_VENTOS_VELOCIDADE))
            .andExpect(jsonPath("$.granizo").value(DEFAULT_GRANIZO))
            .andExpect(jsonPath("$.potencialDeDanos").value(DEFAULT_POTENCIAL_DE_DANOS))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingTempestadeNivel() throws Exception {
        // Get the tempestadeNivel
        restTempestadeNivelMockMvc.perform(get("/api/tempestade-nivels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTempestadeNivel() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        int databaseSizeBeforeUpdate = tempestadeNivelRepository.findAll().size();

        // Update the tempestadeNivel
        TempestadeNivel updatedTempestadeNivel = tempestadeNivelRepository.findById(tempestadeNivel.getId()).get();
        // Disconnect from session so that the updates on updatedTempestadeNivel are not directly saved in db
        em.detach(updatedTempestadeNivel);
        updatedTempestadeNivel
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .taxaDeRaios(UPDATED_TAXA_DE_RAIOS)
            .ventosVelocidade(UPDATED_VENTOS_VELOCIDADE)
            .granizo(UPDATED_GRANIZO)
            .potencialDeDanos(UPDATED_POTENCIAL_DE_DANOS)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        TempestadeNivelDTO tempestadeNivelDTO = tempestadeNivelMapper.toDto(updatedTempestadeNivel);

        restTempestadeNivelMockMvc.perform(put("/api/tempestade-nivels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tempestadeNivelDTO)))
            .andExpect(status().isOk());

        // Validate the TempestadeNivel in the database
        List<TempestadeNivel> tempestadeNivelList = tempestadeNivelRepository.findAll();
        assertThat(tempestadeNivelList).hasSize(databaseSizeBeforeUpdate);
        TempestadeNivel testTempestadeNivel = tempestadeNivelList.get(tempestadeNivelList.size() - 1);
        assertThat(testTempestadeNivel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTempestadeNivel.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testTempestadeNivel.getTaxaDeRaios()).isEqualTo(UPDATED_TAXA_DE_RAIOS);
        assertThat(testTempestadeNivel.getVentosVelocidade()).isEqualTo(UPDATED_VENTOS_VELOCIDADE);
        assertThat(testTempestadeNivel.getGranizo()).isEqualTo(UPDATED_GRANIZO);
        assertThat(testTempestadeNivel.getPotencialDeDanos()).isEqualTo(UPDATED_POTENCIAL_DE_DANOS);
        assertThat(testTempestadeNivel.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testTempestadeNivel.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingTempestadeNivel() throws Exception {
        int databaseSizeBeforeUpdate = tempestadeNivelRepository.findAll().size();

        // Create the TempestadeNivel
        TempestadeNivelDTO tempestadeNivelDTO = tempestadeNivelMapper.toDto(tempestadeNivel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTempestadeNivelMockMvc.perform(put("/api/tempestade-nivels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tempestadeNivelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TempestadeNivel in the database
        List<TempestadeNivel> tempestadeNivelList = tempestadeNivelRepository.findAll();
        assertThat(tempestadeNivelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTempestadeNivel() throws Exception {
        // Initialize the database
        tempestadeNivelRepository.saveAndFlush(tempestadeNivel);

        int databaseSizeBeforeDelete = tempestadeNivelRepository.findAll().size();

        // Delete the tempestadeNivel
        restTempestadeNivelMockMvc.perform(delete("/api/tempestade-nivels/{id}", tempestadeNivel.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TempestadeNivel> tempestadeNivelList = tempestadeNivelRepository.findAll();
        assertThat(tempestadeNivelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
