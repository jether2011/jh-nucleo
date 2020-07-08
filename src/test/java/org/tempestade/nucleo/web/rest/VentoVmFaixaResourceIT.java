package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.VentoVmFaixa;
import org.tempestade.nucleo.repository.VentoVmFaixaRepository;
import org.tempestade.nucleo.service.VentoVmFaixaService;
import org.tempestade.nucleo.service.dto.VentoVmFaixaDTO;
import org.tempestade.nucleo.service.mapper.VentoVmFaixaMapper;

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
 * Integration tests for the {@link VentoVmFaixaResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class VentoVmFaixaResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Integer DEFAULT_DE = 1;
    private static final Integer UPDATED_DE = 2;

    private static final Integer DEFAULT_ATE = 1;
    private static final Integer UPDATED_ATE = 2;

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private VentoVmFaixaRepository ventoVmFaixaRepository;

    @Autowired
    private VentoVmFaixaMapper ventoVmFaixaMapper;

    @Autowired
    private VentoVmFaixaService ventoVmFaixaService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVentoVmFaixaMockMvc;

    private VentoVmFaixa ventoVmFaixa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VentoVmFaixa createEntity(EntityManager em) {
        VentoVmFaixa ventoVmFaixa = new VentoVmFaixa()
            .name(DEFAULT_NAME)
            .descricao(DEFAULT_DESCRICAO)
            .de(DEFAULT_DE)
            .ate(DEFAULT_ATE)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return ventoVmFaixa;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VentoVmFaixa createUpdatedEntity(EntityManager em) {
        VentoVmFaixa ventoVmFaixa = new VentoVmFaixa()
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .de(UPDATED_DE)
            .ate(UPDATED_ATE)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return ventoVmFaixa;
    }

    @BeforeEach
    public void initTest() {
        ventoVmFaixa = createEntity(em);
    }

    @Test
    @Transactional
    public void createVentoVmFaixa() throws Exception {
        int databaseSizeBeforeCreate = ventoVmFaixaRepository.findAll().size();
        // Create the VentoVmFaixa
        VentoVmFaixaDTO ventoVmFaixaDTO = ventoVmFaixaMapper.toDto(ventoVmFaixa);
        restVentoVmFaixaMockMvc.perform(post("/api/vento-vm-faixas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ventoVmFaixaDTO)))
            .andExpect(status().isCreated());

        // Validate the VentoVmFaixa in the database
        List<VentoVmFaixa> ventoVmFaixaList = ventoVmFaixaRepository.findAll();
        assertThat(ventoVmFaixaList).hasSize(databaseSizeBeforeCreate + 1);
        VentoVmFaixa testVentoVmFaixa = ventoVmFaixaList.get(ventoVmFaixaList.size() - 1);
        assertThat(testVentoVmFaixa.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVentoVmFaixa.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testVentoVmFaixa.getDe()).isEqualTo(DEFAULT_DE);
        assertThat(testVentoVmFaixa.getAte()).isEqualTo(DEFAULT_ATE);
        assertThat(testVentoVmFaixa.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testVentoVmFaixa.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createVentoVmFaixaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ventoVmFaixaRepository.findAll().size();

        // Create the VentoVmFaixa with an existing ID
        ventoVmFaixa.setId(1L);
        VentoVmFaixaDTO ventoVmFaixaDTO = ventoVmFaixaMapper.toDto(ventoVmFaixa);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVentoVmFaixaMockMvc.perform(post("/api/vento-vm-faixas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ventoVmFaixaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VentoVmFaixa in the database
        List<VentoVmFaixa> ventoVmFaixaList = ventoVmFaixaRepository.findAll();
        assertThat(ventoVmFaixaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = ventoVmFaixaRepository.findAll().size();
        // set the field null
        ventoVmFaixa.setName(null);

        // Create the VentoVmFaixa, which fails.
        VentoVmFaixaDTO ventoVmFaixaDTO = ventoVmFaixaMapper.toDto(ventoVmFaixa);


        restVentoVmFaixaMockMvc.perform(post("/api/vento-vm-faixas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ventoVmFaixaDTO)))
            .andExpect(status().isBadRequest());

        List<VentoVmFaixa> ventoVmFaixaList = ventoVmFaixaRepository.findAll();
        assertThat(ventoVmFaixaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = ventoVmFaixaRepository.findAll().size();
        // set the field null
        ventoVmFaixa.setDescricao(null);

        // Create the VentoVmFaixa, which fails.
        VentoVmFaixaDTO ventoVmFaixaDTO = ventoVmFaixaMapper.toDto(ventoVmFaixa);


        restVentoVmFaixaMockMvc.perform(post("/api/vento-vm-faixas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ventoVmFaixaDTO)))
            .andExpect(status().isBadRequest());

        List<VentoVmFaixa> ventoVmFaixaList = ventoVmFaixaRepository.findAll();
        assertThat(ventoVmFaixaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = ventoVmFaixaRepository.findAll().size();
        // set the field null
        ventoVmFaixa.setCreated(null);

        // Create the VentoVmFaixa, which fails.
        VentoVmFaixaDTO ventoVmFaixaDTO = ventoVmFaixaMapper.toDto(ventoVmFaixa);


        restVentoVmFaixaMockMvc.perform(post("/api/vento-vm-faixas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ventoVmFaixaDTO)))
            .andExpect(status().isBadRequest());

        List<VentoVmFaixa> ventoVmFaixaList = ventoVmFaixaRepository.findAll();
        assertThat(ventoVmFaixaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVentoVmFaixas() throws Exception {
        // Initialize the database
        ventoVmFaixaRepository.saveAndFlush(ventoVmFaixa);

        // Get all the ventoVmFaixaList
        restVentoVmFaixaMockMvc.perform(get("/api/vento-vm-faixas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ventoVmFaixa.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].de").value(hasItem(DEFAULT_DE)))
            .andExpect(jsonPath("$.[*].ate").value(hasItem(DEFAULT_ATE)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getVentoVmFaixa() throws Exception {
        // Initialize the database
        ventoVmFaixaRepository.saveAndFlush(ventoVmFaixa);

        // Get the ventoVmFaixa
        restVentoVmFaixaMockMvc.perform(get("/api/vento-vm-faixas/{id}", ventoVmFaixa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ventoVmFaixa.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.de").value(DEFAULT_DE))
            .andExpect(jsonPath("$.ate").value(DEFAULT_ATE))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingVentoVmFaixa() throws Exception {
        // Get the ventoVmFaixa
        restVentoVmFaixaMockMvc.perform(get("/api/vento-vm-faixas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVentoVmFaixa() throws Exception {
        // Initialize the database
        ventoVmFaixaRepository.saveAndFlush(ventoVmFaixa);

        int databaseSizeBeforeUpdate = ventoVmFaixaRepository.findAll().size();

        // Update the ventoVmFaixa
        VentoVmFaixa updatedVentoVmFaixa = ventoVmFaixaRepository.findById(ventoVmFaixa.getId()).get();
        // Disconnect from session so that the updates on updatedVentoVmFaixa are not directly saved in db
        em.detach(updatedVentoVmFaixa);
        updatedVentoVmFaixa
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .de(UPDATED_DE)
            .ate(UPDATED_ATE)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        VentoVmFaixaDTO ventoVmFaixaDTO = ventoVmFaixaMapper.toDto(updatedVentoVmFaixa);

        restVentoVmFaixaMockMvc.perform(put("/api/vento-vm-faixas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ventoVmFaixaDTO)))
            .andExpect(status().isOk());

        // Validate the VentoVmFaixa in the database
        List<VentoVmFaixa> ventoVmFaixaList = ventoVmFaixaRepository.findAll();
        assertThat(ventoVmFaixaList).hasSize(databaseSizeBeforeUpdate);
        VentoVmFaixa testVentoVmFaixa = ventoVmFaixaList.get(ventoVmFaixaList.size() - 1);
        assertThat(testVentoVmFaixa.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVentoVmFaixa.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testVentoVmFaixa.getDe()).isEqualTo(UPDATED_DE);
        assertThat(testVentoVmFaixa.getAte()).isEqualTo(UPDATED_ATE);
        assertThat(testVentoVmFaixa.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testVentoVmFaixa.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingVentoVmFaixa() throws Exception {
        int databaseSizeBeforeUpdate = ventoVmFaixaRepository.findAll().size();

        // Create the VentoVmFaixa
        VentoVmFaixaDTO ventoVmFaixaDTO = ventoVmFaixaMapper.toDto(ventoVmFaixa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVentoVmFaixaMockMvc.perform(put("/api/vento-vm-faixas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ventoVmFaixaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VentoVmFaixa in the database
        List<VentoVmFaixa> ventoVmFaixaList = ventoVmFaixaRepository.findAll();
        assertThat(ventoVmFaixaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVentoVmFaixa() throws Exception {
        // Initialize the database
        ventoVmFaixaRepository.saveAndFlush(ventoVmFaixa);

        int databaseSizeBeforeDelete = ventoVmFaixaRepository.findAll().size();

        // Delete the ventoVmFaixa
        restVentoVmFaixaMockMvc.perform(delete("/api/vento-vm-faixas/{id}", ventoVmFaixa.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VentoVmFaixa> ventoVmFaixaList = ventoVmFaixaRepository.findAll();
        assertThat(ventoVmFaixaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
