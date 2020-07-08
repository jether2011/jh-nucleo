package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.AcumuladoChuvaFaixa;
import org.tempestade.nucleo.repository.AcumuladoChuvaFaixaRepository;
import org.tempestade.nucleo.service.AcumuladoChuvaFaixaService;
import org.tempestade.nucleo.service.dto.AcumuladoChuvaFaixaDTO;
import org.tempestade.nucleo.service.mapper.AcumuladoChuvaFaixaMapper;

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
 * Integration tests for the {@link AcumuladoChuvaFaixaResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AcumuladoChuvaFaixaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Integer DEFAULT_DE_MM = 1;
    private static final Integer UPDATED_DE_MM = 2;

    private static final Integer DEFAULT_ATE_MM = 1;
    private static final Integer UPDATED_ATE_MM = 2;

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private AcumuladoChuvaFaixaRepository acumuladoChuvaFaixaRepository;

    @Autowired
    private AcumuladoChuvaFaixaMapper acumuladoChuvaFaixaMapper;

    @Autowired
    private AcumuladoChuvaFaixaService acumuladoChuvaFaixaService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAcumuladoChuvaFaixaMockMvc;

    private AcumuladoChuvaFaixa acumuladoChuvaFaixa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AcumuladoChuvaFaixa createEntity(EntityManager em) {
        AcumuladoChuvaFaixa acumuladoChuvaFaixa = new AcumuladoChuvaFaixa()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .deMm(DEFAULT_DE_MM)
            .ateMm(DEFAULT_ATE_MM)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return acumuladoChuvaFaixa;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AcumuladoChuvaFaixa createUpdatedEntity(EntityManager em) {
        AcumuladoChuvaFaixa acumuladoChuvaFaixa = new AcumuladoChuvaFaixa()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .deMm(UPDATED_DE_MM)
            .ateMm(UPDATED_ATE_MM)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return acumuladoChuvaFaixa;
    }

    @BeforeEach
    public void initTest() {
        acumuladoChuvaFaixa = createEntity(em);
    }

    @Test
    @Transactional
    public void createAcumuladoChuvaFaixa() throws Exception {
        int databaseSizeBeforeCreate = acumuladoChuvaFaixaRepository.findAll().size();
        // Create the AcumuladoChuvaFaixa
        AcumuladoChuvaFaixaDTO acumuladoChuvaFaixaDTO = acumuladoChuvaFaixaMapper.toDto(acumuladoChuvaFaixa);
        restAcumuladoChuvaFaixaMockMvc.perform(post("/api/acumulado-chuva-faixas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(acumuladoChuvaFaixaDTO)))
            .andExpect(status().isCreated());

        // Validate the AcumuladoChuvaFaixa in the database
        List<AcumuladoChuvaFaixa> acumuladoChuvaFaixaList = acumuladoChuvaFaixaRepository.findAll();
        assertThat(acumuladoChuvaFaixaList).hasSize(databaseSizeBeforeCreate + 1);
        AcumuladoChuvaFaixa testAcumuladoChuvaFaixa = acumuladoChuvaFaixaList.get(acumuladoChuvaFaixaList.size() - 1);
        assertThat(testAcumuladoChuvaFaixa.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testAcumuladoChuvaFaixa.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testAcumuladoChuvaFaixa.getDeMm()).isEqualTo(DEFAULT_DE_MM);
        assertThat(testAcumuladoChuvaFaixa.getAteMm()).isEqualTo(DEFAULT_ATE_MM);
        assertThat(testAcumuladoChuvaFaixa.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testAcumuladoChuvaFaixa.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createAcumuladoChuvaFaixaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = acumuladoChuvaFaixaRepository.findAll().size();

        // Create the AcumuladoChuvaFaixa with an existing ID
        acumuladoChuvaFaixa.setId(1L);
        AcumuladoChuvaFaixaDTO acumuladoChuvaFaixaDTO = acumuladoChuvaFaixaMapper.toDto(acumuladoChuvaFaixa);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAcumuladoChuvaFaixaMockMvc.perform(post("/api/acumulado-chuva-faixas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(acumuladoChuvaFaixaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AcumuladoChuvaFaixa in the database
        List<AcumuladoChuvaFaixa> acumuladoChuvaFaixaList = acumuladoChuvaFaixaRepository.findAll();
        assertThat(acumuladoChuvaFaixaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = acumuladoChuvaFaixaRepository.findAll().size();
        // set the field null
        acumuladoChuvaFaixa.setNome(null);

        // Create the AcumuladoChuvaFaixa, which fails.
        AcumuladoChuvaFaixaDTO acumuladoChuvaFaixaDTO = acumuladoChuvaFaixaMapper.toDto(acumuladoChuvaFaixa);


        restAcumuladoChuvaFaixaMockMvc.perform(post("/api/acumulado-chuva-faixas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(acumuladoChuvaFaixaDTO)))
            .andExpect(status().isBadRequest());

        List<AcumuladoChuvaFaixa> acumuladoChuvaFaixaList = acumuladoChuvaFaixaRepository.findAll();
        assertThat(acumuladoChuvaFaixaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDeMmIsRequired() throws Exception {
        int databaseSizeBeforeTest = acumuladoChuvaFaixaRepository.findAll().size();
        // set the field null
        acumuladoChuvaFaixa.setDeMm(null);

        // Create the AcumuladoChuvaFaixa, which fails.
        AcumuladoChuvaFaixaDTO acumuladoChuvaFaixaDTO = acumuladoChuvaFaixaMapper.toDto(acumuladoChuvaFaixa);


        restAcumuladoChuvaFaixaMockMvc.perform(post("/api/acumulado-chuva-faixas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(acumuladoChuvaFaixaDTO)))
            .andExpect(status().isBadRequest());

        List<AcumuladoChuvaFaixa> acumuladoChuvaFaixaList = acumuladoChuvaFaixaRepository.findAll();
        assertThat(acumuladoChuvaFaixaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAteMmIsRequired() throws Exception {
        int databaseSizeBeforeTest = acumuladoChuvaFaixaRepository.findAll().size();
        // set the field null
        acumuladoChuvaFaixa.setAteMm(null);

        // Create the AcumuladoChuvaFaixa, which fails.
        AcumuladoChuvaFaixaDTO acumuladoChuvaFaixaDTO = acumuladoChuvaFaixaMapper.toDto(acumuladoChuvaFaixa);


        restAcumuladoChuvaFaixaMockMvc.perform(post("/api/acumulado-chuva-faixas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(acumuladoChuvaFaixaDTO)))
            .andExpect(status().isBadRequest());

        List<AcumuladoChuvaFaixa> acumuladoChuvaFaixaList = acumuladoChuvaFaixaRepository.findAll();
        assertThat(acumuladoChuvaFaixaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = acumuladoChuvaFaixaRepository.findAll().size();
        // set the field null
        acumuladoChuvaFaixa.setCreated(null);

        // Create the AcumuladoChuvaFaixa, which fails.
        AcumuladoChuvaFaixaDTO acumuladoChuvaFaixaDTO = acumuladoChuvaFaixaMapper.toDto(acumuladoChuvaFaixa);


        restAcumuladoChuvaFaixaMockMvc.perform(post("/api/acumulado-chuva-faixas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(acumuladoChuvaFaixaDTO)))
            .andExpect(status().isBadRequest());

        List<AcumuladoChuvaFaixa> acumuladoChuvaFaixaList = acumuladoChuvaFaixaRepository.findAll();
        assertThat(acumuladoChuvaFaixaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAcumuladoChuvaFaixas() throws Exception {
        // Initialize the database
        acumuladoChuvaFaixaRepository.saveAndFlush(acumuladoChuvaFaixa);

        // Get all the acumuladoChuvaFaixaList
        restAcumuladoChuvaFaixaMockMvc.perform(get("/api/acumulado-chuva-faixas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(acumuladoChuvaFaixa.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].deMm").value(hasItem(DEFAULT_DE_MM)))
            .andExpect(jsonPath("$.[*].ateMm").value(hasItem(DEFAULT_ATE_MM)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getAcumuladoChuvaFaixa() throws Exception {
        // Initialize the database
        acumuladoChuvaFaixaRepository.saveAndFlush(acumuladoChuvaFaixa);

        // Get the acumuladoChuvaFaixa
        restAcumuladoChuvaFaixaMockMvc.perform(get("/api/acumulado-chuva-faixas/{id}", acumuladoChuvaFaixa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(acumuladoChuvaFaixa.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.deMm").value(DEFAULT_DE_MM))
            .andExpect(jsonPath("$.ateMm").value(DEFAULT_ATE_MM))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingAcumuladoChuvaFaixa() throws Exception {
        // Get the acumuladoChuvaFaixa
        restAcumuladoChuvaFaixaMockMvc.perform(get("/api/acumulado-chuva-faixas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAcumuladoChuvaFaixa() throws Exception {
        // Initialize the database
        acumuladoChuvaFaixaRepository.saveAndFlush(acumuladoChuvaFaixa);

        int databaseSizeBeforeUpdate = acumuladoChuvaFaixaRepository.findAll().size();

        // Update the acumuladoChuvaFaixa
        AcumuladoChuvaFaixa updatedAcumuladoChuvaFaixa = acumuladoChuvaFaixaRepository.findById(acumuladoChuvaFaixa.getId()).get();
        // Disconnect from session so that the updates on updatedAcumuladoChuvaFaixa are not directly saved in db
        em.detach(updatedAcumuladoChuvaFaixa);
        updatedAcumuladoChuvaFaixa
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .deMm(UPDATED_DE_MM)
            .ateMm(UPDATED_ATE_MM)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        AcumuladoChuvaFaixaDTO acumuladoChuvaFaixaDTO = acumuladoChuvaFaixaMapper.toDto(updatedAcumuladoChuvaFaixa);

        restAcumuladoChuvaFaixaMockMvc.perform(put("/api/acumulado-chuva-faixas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(acumuladoChuvaFaixaDTO)))
            .andExpect(status().isOk());

        // Validate the AcumuladoChuvaFaixa in the database
        List<AcumuladoChuvaFaixa> acumuladoChuvaFaixaList = acumuladoChuvaFaixaRepository.findAll();
        assertThat(acumuladoChuvaFaixaList).hasSize(databaseSizeBeforeUpdate);
        AcumuladoChuvaFaixa testAcumuladoChuvaFaixa = acumuladoChuvaFaixaList.get(acumuladoChuvaFaixaList.size() - 1);
        assertThat(testAcumuladoChuvaFaixa.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testAcumuladoChuvaFaixa.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testAcumuladoChuvaFaixa.getDeMm()).isEqualTo(UPDATED_DE_MM);
        assertThat(testAcumuladoChuvaFaixa.getAteMm()).isEqualTo(UPDATED_ATE_MM);
        assertThat(testAcumuladoChuvaFaixa.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testAcumuladoChuvaFaixa.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingAcumuladoChuvaFaixa() throws Exception {
        int databaseSizeBeforeUpdate = acumuladoChuvaFaixaRepository.findAll().size();

        // Create the AcumuladoChuvaFaixa
        AcumuladoChuvaFaixaDTO acumuladoChuvaFaixaDTO = acumuladoChuvaFaixaMapper.toDto(acumuladoChuvaFaixa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAcumuladoChuvaFaixaMockMvc.perform(put("/api/acumulado-chuva-faixas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(acumuladoChuvaFaixaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AcumuladoChuvaFaixa in the database
        List<AcumuladoChuvaFaixa> acumuladoChuvaFaixaList = acumuladoChuvaFaixaRepository.findAll();
        assertThat(acumuladoChuvaFaixaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAcumuladoChuvaFaixa() throws Exception {
        // Initialize the database
        acumuladoChuvaFaixaRepository.saveAndFlush(acumuladoChuvaFaixa);

        int databaseSizeBeforeDelete = acumuladoChuvaFaixaRepository.findAll().size();

        // Delete the acumuladoChuvaFaixa
        restAcumuladoChuvaFaixaMockMvc.perform(delete("/api/acumulado-chuva-faixas/{id}", acumuladoChuvaFaixa.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AcumuladoChuvaFaixa> acumuladoChuvaFaixaList = acumuladoChuvaFaixaRepository.findAll();
        assertThat(acumuladoChuvaFaixaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
