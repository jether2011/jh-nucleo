package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.DescargaUnidade;
import org.tempestade.nucleo.repository.DescargaUnidadeRepository;
import org.tempestade.nucleo.service.DescargaUnidadeService;
import org.tempestade.nucleo.service.dto.DescargaUnidadeDTO;
import org.tempestade.nucleo.service.mapper.DescargaUnidadeMapper;

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
 * Integration tests for the {@link DescargaUnidadeResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DescargaUnidadeResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Integer DEFAULT_UNIDADE = 1;
    private static final Integer UPDATED_UNIDADE = 2;

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private DescargaUnidadeRepository descargaUnidadeRepository;

    @Autowired
    private DescargaUnidadeMapper descargaUnidadeMapper;

    @Autowired
    private DescargaUnidadeService descargaUnidadeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDescargaUnidadeMockMvc;

    private DescargaUnidade descargaUnidade;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DescargaUnidade createEntity(EntityManager em) {
        DescargaUnidade descargaUnidade = new DescargaUnidade()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .unidade(DEFAULT_UNIDADE)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return descargaUnidade;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DescargaUnidade createUpdatedEntity(EntityManager em) {
        DescargaUnidade descargaUnidade = new DescargaUnidade()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .unidade(UPDATED_UNIDADE)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return descargaUnidade;
    }

    @BeforeEach
    public void initTest() {
        descargaUnidade = createEntity(em);
    }

    @Test
    @Transactional
    public void createDescargaUnidade() throws Exception {
        int databaseSizeBeforeCreate = descargaUnidadeRepository.findAll().size();
        // Create the DescargaUnidade
        DescargaUnidadeDTO descargaUnidadeDTO = descargaUnidadeMapper.toDto(descargaUnidade);
        restDescargaUnidadeMockMvc.perform(post("/api/descarga-unidades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(descargaUnidadeDTO)))
            .andExpect(status().isCreated());

        // Validate the DescargaUnidade in the database
        List<DescargaUnidade> descargaUnidadeList = descargaUnidadeRepository.findAll();
        assertThat(descargaUnidadeList).hasSize(databaseSizeBeforeCreate + 1);
        DescargaUnidade testDescargaUnidade = descargaUnidadeList.get(descargaUnidadeList.size() - 1);
        assertThat(testDescargaUnidade.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testDescargaUnidade.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testDescargaUnidade.getUnidade()).isEqualTo(DEFAULT_UNIDADE);
        assertThat(testDescargaUnidade.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testDescargaUnidade.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createDescargaUnidadeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = descargaUnidadeRepository.findAll().size();

        // Create the DescargaUnidade with an existing ID
        descargaUnidade.setId(1L);
        DescargaUnidadeDTO descargaUnidadeDTO = descargaUnidadeMapper.toDto(descargaUnidade);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDescargaUnidadeMockMvc.perform(post("/api/descarga-unidades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(descargaUnidadeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DescargaUnidade in the database
        List<DescargaUnidade> descargaUnidadeList = descargaUnidadeRepository.findAll();
        assertThat(descargaUnidadeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = descargaUnidadeRepository.findAll().size();
        // set the field null
        descargaUnidade.setNome(null);

        // Create the DescargaUnidade, which fails.
        DescargaUnidadeDTO descargaUnidadeDTO = descargaUnidadeMapper.toDto(descargaUnidade);


        restDescargaUnidadeMockMvc.perform(post("/api/descarga-unidades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(descargaUnidadeDTO)))
            .andExpect(status().isBadRequest());

        List<DescargaUnidade> descargaUnidadeList = descargaUnidadeRepository.findAll();
        assertThat(descargaUnidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = descargaUnidadeRepository.findAll().size();
        // set the field null
        descargaUnidade.setUnidade(null);

        // Create the DescargaUnidade, which fails.
        DescargaUnidadeDTO descargaUnidadeDTO = descargaUnidadeMapper.toDto(descargaUnidade);


        restDescargaUnidadeMockMvc.perform(post("/api/descarga-unidades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(descargaUnidadeDTO)))
            .andExpect(status().isBadRequest());

        List<DescargaUnidade> descargaUnidadeList = descargaUnidadeRepository.findAll();
        assertThat(descargaUnidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = descargaUnidadeRepository.findAll().size();
        // set the field null
        descargaUnidade.setCreated(null);

        // Create the DescargaUnidade, which fails.
        DescargaUnidadeDTO descargaUnidadeDTO = descargaUnidadeMapper.toDto(descargaUnidade);


        restDescargaUnidadeMockMvc.perform(post("/api/descarga-unidades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(descargaUnidadeDTO)))
            .andExpect(status().isBadRequest());

        List<DescargaUnidade> descargaUnidadeList = descargaUnidadeRepository.findAll();
        assertThat(descargaUnidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDescargaUnidades() throws Exception {
        // Initialize the database
        descargaUnidadeRepository.saveAndFlush(descargaUnidade);

        // Get all the descargaUnidadeList
        restDescargaUnidadeMockMvc.perform(get("/api/descarga-unidades?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(descargaUnidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].unidade").value(hasItem(DEFAULT_UNIDADE)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getDescargaUnidade() throws Exception {
        // Initialize the database
        descargaUnidadeRepository.saveAndFlush(descargaUnidade);

        // Get the descargaUnidade
        restDescargaUnidadeMockMvc.perform(get("/api/descarga-unidades/{id}", descargaUnidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(descargaUnidade.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.unidade").value(DEFAULT_UNIDADE))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingDescargaUnidade() throws Exception {
        // Get the descargaUnidade
        restDescargaUnidadeMockMvc.perform(get("/api/descarga-unidades/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDescargaUnidade() throws Exception {
        // Initialize the database
        descargaUnidadeRepository.saveAndFlush(descargaUnidade);

        int databaseSizeBeforeUpdate = descargaUnidadeRepository.findAll().size();

        // Update the descargaUnidade
        DescargaUnidade updatedDescargaUnidade = descargaUnidadeRepository.findById(descargaUnidade.getId()).get();
        // Disconnect from session so that the updates on updatedDescargaUnidade are not directly saved in db
        em.detach(updatedDescargaUnidade);
        updatedDescargaUnidade
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .unidade(UPDATED_UNIDADE)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        DescargaUnidadeDTO descargaUnidadeDTO = descargaUnidadeMapper.toDto(updatedDescargaUnidade);

        restDescargaUnidadeMockMvc.perform(put("/api/descarga-unidades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(descargaUnidadeDTO)))
            .andExpect(status().isOk());

        // Validate the DescargaUnidade in the database
        List<DescargaUnidade> descargaUnidadeList = descargaUnidadeRepository.findAll();
        assertThat(descargaUnidadeList).hasSize(databaseSizeBeforeUpdate);
        DescargaUnidade testDescargaUnidade = descargaUnidadeList.get(descargaUnidadeList.size() - 1);
        assertThat(testDescargaUnidade.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testDescargaUnidade.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testDescargaUnidade.getUnidade()).isEqualTo(UPDATED_UNIDADE);
        assertThat(testDescargaUnidade.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testDescargaUnidade.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingDescargaUnidade() throws Exception {
        int databaseSizeBeforeUpdate = descargaUnidadeRepository.findAll().size();

        // Create the DescargaUnidade
        DescargaUnidadeDTO descargaUnidadeDTO = descargaUnidadeMapper.toDto(descargaUnidade);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDescargaUnidadeMockMvc.perform(put("/api/descarga-unidades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(descargaUnidadeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DescargaUnidade in the database
        List<DescargaUnidade> descargaUnidadeList = descargaUnidadeRepository.findAll();
        assertThat(descargaUnidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDescargaUnidade() throws Exception {
        // Initialize the database
        descargaUnidadeRepository.saveAndFlush(descargaUnidade);

        int databaseSizeBeforeDelete = descargaUnidadeRepository.findAll().size();

        // Delete the descargaUnidade
        restDescargaUnidadeMockMvc.perform(delete("/api/descarga-unidades/{id}", descargaUnidade.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DescargaUnidade> descargaUnidadeList = descargaUnidadeRepository.findAll();
        assertThat(descargaUnidadeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
