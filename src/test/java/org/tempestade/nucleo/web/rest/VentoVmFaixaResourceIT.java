package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.VentoVmFaixa;
import org.tempestade.nucleo.repository.VentoVmFaixaRepository;
import org.tempestade.nucleo.service.VentoVmFaixaService;
import org.tempestade.nucleo.service.dto.VentoVmFaixaDTO;
import org.tempestade.nucleo.service.mapper.VentoVmFaixaMapper;
import org.tempestade.nucleo.service.dto.VentoVmFaixaCriteria;
import org.tempestade.nucleo.service.VentoVmFaixaQueryService;

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
    private static final Integer SMALLER_DE = 1 - 1;

    private static final Integer DEFAULT_ATE = 1;
    private static final Integer UPDATED_ATE = 2;
    private static final Integer SMALLER_ATE = 1 - 1;

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
    private VentoVmFaixaQueryService ventoVmFaixaQueryService;

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
    public void getVentoVmFaixasByIdFiltering() throws Exception {
        // Initialize the database
        ventoVmFaixaRepository.saveAndFlush(ventoVmFaixa);

        Long id = ventoVmFaixa.getId();

        defaultVentoVmFaixaShouldBeFound("id.equals=" + id);
        defaultVentoVmFaixaShouldNotBeFound("id.notEquals=" + id);

        defaultVentoVmFaixaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVentoVmFaixaShouldNotBeFound("id.greaterThan=" + id);

        defaultVentoVmFaixaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVentoVmFaixaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllVentoVmFaixasByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        ventoVmFaixaRepository.saveAndFlush(ventoVmFaixa);

        // Get all the ventoVmFaixaList where name equals to DEFAULT_NAME
        defaultVentoVmFaixaShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the ventoVmFaixaList where name equals to UPDATED_NAME
        defaultVentoVmFaixaShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllVentoVmFaixasByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ventoVmFaixaRepository.saveAndFlush(ventoVmFaixa);

        // Get all the ventoVmFaixaList where name not equals to DEFAULT_NAME
        defaultVentoVmFaixaShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the ventoVmFaixaList where name not equals to UPDATED_NAME
        defaultVentoVmFaixaShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllVentoVmFaixasByNameIsInShouldWork() throws Exception {
        // Initialize the database
        ventoVmFaixaRepository.saveAndFlush(ventoVmFaixa);

        // Get all the ventoVmFaixaList where name in DEFAULT_NAME or UPDATED_NAME
        defaultVentoVmFaixaShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the ventoVmFaixaList where name equals to UPDATED_NAME
        defaultVentoVmFaixaShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllVentoVmFaixasByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        ventoVmFaixaRepository.saveAndFlush(ventoVmFaixa);

        // Get all the ventoVmFaixaList where name is not null
        defaultVentoVmFaixaShouldBeFound("name.specified=true");

        // Get all the ventoVmFaixaList where name is null
        defaultVentoVmFaixaShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllVentoVmFaixasByNameContainsSomething() throws Exception {
        // Initialize the database
        ventoVmFaixaRepository.saveAndFlush(ventoVmFaixa);

        // Get all the ventoVmFaixaList where name contains DEFAULT_NAME
        defaultVentoVmFaixaShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the ventoVmFaixaList where name contains UPDATED_NAME
        defaultVentoVmFaixaShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllVentoVmFaixasByNameNotContainsSomething() throws Exception {
        // Initialize the database
        ventoVmFaixaRepository.saveAndFlush(ventoVmFaixa);

        // Get all the ventoVmFaixaList where name does not contain DEFAULT_NAME
        defaultVentoVmFaixaShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the ventoVmFaixaList where name does not contain UPDATED_NAME
        defaultVentoVmFaixaShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllVentoVmFaixasByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        ventoVmFaixaRepository.saveAndFlush(ventoVmFaixa);

        // Get all the ventoVmFaixaList where descricao equals to DEFAULT_DESCRICAO
        defaultVentoVmFaixaShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the ventoVmFaixaList where descricao equals to UPDATED_DESCRICAO
        defaultVentoVmFaixaShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllVentoVmFaixasByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ventoVmFaixaRepository.saveAndFlush(ventoVmFaixa);

        // Get all the ventoVmFaixaList where descricao not equals to DEFAULT_DESCRICAO
        defaultVentoVmFaixaShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the ventoVmFaixaList where descricao not equals to UPDATED_DESCRICAO
        defaultVentoVmFaixaShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllVentoVmFaixasByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        ventoVmFaixaRepository.saveAndFlush(ventoVmFaixa);

        // Get all the ventoVmFaixaList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultVentoVmFaixaShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the ventoVmFaixaList where descricao equals to UPDATED_DESCRICAO
        defaultVentoVmFaixaShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllVentoVmFaixasByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        ventoVmFaixaRepository.saveAndFlush(ventoVmFaixa);

        // Get all the ventoVmFaixaList where descricao is not null
        defaultVentoVmFaixaShouldBeFound("descricao.specified=true");

        // Get all the ventoVmFaixaList where descricao is null
        defaultVentoVmFaixaShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllVentoVmFaixasByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        ventoVmFaixaRepository.saveAndFlush(ventoVmFaixa);

        // Get all the ventoVmFaixaList where descricao contains DEFAULT_DESCRICAO
        defaultVentoVmFaixaShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the ventoVmFaixaList where descricao contains UPDATED_DESCRICAO
        defaultVentoVmFaixaShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllVentoVmFaixasByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        ventoVmFaixaRepository.saveAndFlush(ventoVmFaixa);

        // Get all the ventoVmFaixaList where descricao does not contain DEFAULT_DESCRICAO
        defaultVentoVmFaixaShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the ventoVmFaixaList where descricao does not contain UPDATED_DESCRICAO
        defaultVentoVmFaixaShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllVentoVmFaixasByDeIsEqualToSomething() throws Exception {
        // Initialize the database
        ventoVmFaixaRepository.saveAndFlush(ventoVmFaixa);

        // Get all the ventoVmFaixaList where de equals to DEFAULT_DE
        defaultVentoVmFaixaShouldBeFound("de.equals=" + DEFAULT_DE);

        // Get all the ventoVmFaixaList where de equals to UPDATED_DE
        defaultVentoVmFaixaShouldNotBeFound("de.equals=" + UPDATED_DE);
    }

    @Test
    @Transactional
    public void getAllVentoVmFaixasByDeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ventoVmFaixaRepository.saveAndFlush(ventoVmFaixa);

        // Get all the ventoVmFaixaList where de not equals to DEFAULT_DE
        defaultVentoVmFaixaShouldNotBeFound("de.notEquals=" + DEFAULT_DE);

        // Get all the ventoVmFaixaList where de not equals to UPDATED_DE
        defaultVentoVmFaixaShouldBeFound("de.notEquals=" + UPDATED_DE);
    }

    @Test
    @Transactional
    public void getAllVentoVmFaixasByDeIsInShouldWork() throws Exception {
        // Initialize the database
        ventoVmFaixaRepository.saveAndFlush(ventoVmFaixa);

        // Get all the ventoVmFaixaList where de in DEFAULT_DE or UPDATED_DE
        defaultVentoVmFaixaShouldBeFound("de.in=" + DEFAULT_DE + "," + UPDATED_DE);

        // Get all the ventoVmFaixaList where de equals to UPDATED_DE
        defaultVentoVmFaixaShouldNotBeFound("de.in=" + UPDATED_DE);
    }

    @Test
    @Transactional
    public void getAllVentoVmFaixasByDeIsNullOrNotNull() throws Exception {
        // Initialize the database
        ventoVmFaixaRepository.saveAndFlush(ventoVmFaixa);

        // Get all the ventoVmFaixaList where de is not null
        defaultVentoVmFaixaShouldBeFound("de.specified=true");

        // Get all the ventoVmFaixaList where de is null
        defaultVentoVmFaixaShouldNotBeFound("de.specified=false");
    }

    @Test
    @Transactional
    public void getAllVentoVmFaixasByDeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ventoVmFaixaRepository.saveAndFlush(ventoVmFaixa);

        // Get all the ventoVmFaixaList where de is greater than or equal to DEFAULT_DE
        defaultVentoVmFaixaShouldBeFound("de.greaterThanOrEqual=" + DEFAULT_DE);

        // Get all the ventoVmFaixaList where de is greater than or equal to UPDATED_DE
        defaultVentoVmFaixaShouldNotBeFound("de.greaterThanOrEqual=" + UPDATED_DE);
    }

    @Test
    @Transactional
    public void getAllVentoVmFaixasByDeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ventoVmFaixaRepository.saveAndFlush(ventoVmFaixa);

        // Get all the ventoVmFaixaList where de is less than or equal to DEFAULT_DE
        defaultVentoVmFaixaShouldBeFound("de.lessThanOrEqual=" + DEFAULT_DE);

        // Get all the ventoVmFaixaList where de is less than or equal to SMALLER_DE
        defaultVentoVmFaixaShouldNotBeFound("de.lessThanOrEqual=" + SMALLER_DE);
    }

    @Test
    @Transactional
    public void getAllVentoVmFaixasByDeIsLessThanSomething() throws Exception {
        // Initialize the database
        ventoVmFaixaRepository.saveAndFlush(ventoVmFaixa);

        // Get all the ventoVmFaixaList where de is less than DEFAULT_DE
        defaultVentoVmFaixaShouldNotBeFound("de.lessThan=" + DEFAULT_DE);

        // Get all the ventoVmFaixaList where de is less than UPDATED_DE
        defaultVentoVmFaixaShouldBeFound("de.lessThan=" + UPDATED_DE);
    }

    @Test
    @Transactional
    public void getAllVentoVmFaixasByDeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ventoVmFaixaRepository.saveAndFlush(ventoVmFaixa);

        // Get all the ventoVmFaixaList where de is greater than DEFAULT_DE
        defaultVentoVmFaixaShouldNotBeFound("de.greaterThan=" + DEFAULT_DE);

        // Get all the ventoVmFaixaList where de is greater than SMALLER_DE
        defaultVentoVmFaixaShouldBeFound("de.greaterThan=" + SMALLER_DE);
    }


    @Test
    @Transactional
    public void getAllVentoVmFaixasByAteIsEqualToSomething() throws Exception {
        // Initialize the database
        ventoVmFaixaRepository.saveAndFlush(ventoVmFaixa);

        // Get all the ventoVmFaixaList where ate equals to DEFAULT_ATE
        defaultVentoVmFaixaShouldBeFound("ate.equals=" + DEFAULT_ATE);

        // Get all the ventoVmFaixaList where ate equals to UPDATED_ATE
        defaultVentoVmFaixaShouldNotBeFound("ate.equals=" + UPDATED_ATE);
    }

    @Test
    @Transactional
    public void getAllVentoVmFaixasByAteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ventoVmFaixaRepository.saveAndFlush(ventoVmFaixa);

        // Get all the ventoVmFaixaList where ate not equals to DEFAULT_ATE
        defaultVentoVmFaixaShouldNotBeFound("ate.notEquals=" + DEFAULT_ATE);

        // Get all the ventoVmFaixaList where ate not equals to UPDATED_ATE
        defaultVentoVmFaixaShouldBeFound("ate.notEquals=" + UPDATED_ATE);
    }

    @Test
    @Transactional
    public void getAllVentoVmFaixasByAteIsInShouldWork() throws Exception {
        // Initialize the database
        ventoVmFaixaRepository.saveAndFlush(ventoVmFaixa);

        // Get all the ventoVmFaixaList where ate in DEFAULT_ATE or UPDATED_ATE
        defaultVentoVmFaixaShouldBeFound("ate.in=" + DEFAULT_ATE + "," + UPDATED_ATE);

        // Get all the ventoVmFaixaList where ate equals to UPDATED_ATE
        defaultVentoVmFaixaShouldNotBeFound("ate.in=" + UPDATED_ATE);
    }

    @Test
    @Transactional
    public void getAllVentoVmFaixasByAteIsNullOrNotNull() throws Exception {
        // Initialize the database
        ventoVmFaixaRepository.saveAndFlush(ventoVmFaixa);

        // Get all the ventoVmFaixaList where ate is not null
        defaultVentoVmFaixaShouldBeFound("ate.specified=true");

        // Get all the ventoVmFaixaList where ate is null
        defaultVentoVmFaixaShouldNotBeFound("ate.specified=false");
    }

    @Test
    @Transactional
    public void getAllVentoVmFaixasByAteIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ventoVmFaixaRepository.saveAndFlush(ventoVmFaixa);

        // Get all the ventoVmFaixaList where ate is greater than or equal to DEFAULT_ATE
        defaultVentoVmFaixaShouldBeFound("ate.greaterThanOrEqual=" + DEFAULT_ATE);

        // Get all the ventoVmFaixaList where ate is greater than or equal to UPDATED_ATE
        defaultVentoVmFaixaShouldNotBeFound("ate.greaterThanOrEqual=" + UPDATED_ATE);
    }

    @Test
    @Transactional
    public void getAllVentoVmFaixasByAteIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ventoVmFaixaRepository.saveAndFlush(ventoVmFaixa);

        // Get all the ventoVmFaixaList where ate is less than or equal to DEFAULT_ATE
        defaultVentoVmFaixaShouldBeFound("ate.lessThanOrEqual=" + DEFAULT_ATE);

        // Get all the ventoVmFaixaList where ate is less than or equal to SMALLER_ATE
        defaultVentoVmFaixaShouldNotBeFound("ate.lessThanOrEqual=" + SMALLER_ATE);
    }

    @Test
    @Transactional
    public void getAllVentoVmFaixasByAteIsLessThanSomething() throws Exception {
        // Initialize the database
        ventoVmFaixaRepository.saveAndFlush(ventoVmFaixa);

        // Get all the ventoVmFaixaList where ate is less than DEFAULT_ATE
        defaultVentoVmFaixaShouldNotBeFound("ate.lessThan=" + DEFAULT_ATE);

        // Get all the ventoVmFaixaList where ate is less than UPDATED_ATE
        defaultVentoVmFaixaShouldBeFound("ate.lessThan=" + UPDATED_ATE);
    }

    @Test
    @Transactional
    public void getAllVentoVmFaixasByAteIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ventoVmFaixaRepository.saveAndFlush(ventoVmFaixa);

        // Get all the ventoVmFaixaList where ate is greater than DEFAULT_ATE
        defaultVentoVmFaixaShouldNotBeFound("ate.greaterThan=" + DEFAULT_ATE);

        // Get all the ventoVmFaixaList where ate is greater than SMALLER_ATE
        defaultVentoVmFaixaShouldBeFound("ate.greaterThan=" + SMALLER_ATE);
    }


    @Test
    @Transactional
    public void getAllVentoVmFaixasByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        ventoVmFaixaRepository.saveAndFlush(ventoVmFaixa);

        // Get all the ventoVmFaixaList where created equals to DEFAULT_CREATED
        defaultVentoVmFaixaShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the ventoVmFaixaList where created equals to UPDATED_CREATED
        defaultVentoVmFaixaShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllVentoVmFaixasByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ventoVmFaixaRepository.saveAndFlush(ventoVmFaixa);

        // Get all the ventoVmFaixaList where created not equals to DEFAULT_CREATED
        defaultVentoVmFaixaShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the ventoVmFaixaList where created not equals to UPDATED_CREATED
        defaultVentoVmFaixaShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllVentoVmFaixasByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        ventoVmFaixaRepository.saveAndFlush(ventoVmFaixa);

        // Get all the ventoVmFaixaList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultVentoVmFaixaShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the ventoVmFaixaList where created equals to UPDATED_CREATED
        defaultVentoVmFaixaShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllVentoVmFaixasByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        ventoVmFaixaRepository.saveAndFlush(ventoVmFaixa);

        // Get all the ventoVmFaixaList where created is not null
        defaultVentoVmFaixaShouldBeFound("created.specified=true");

        // Get all the ventoVmFaixaList where created is null
        defaultVentoVmFaixaShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllVentoVmFaixasByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        ventoVmFaixaRepository.saveAndFlush(ventoVmFaixa);

        // Get all the ventoVmFaixaList where updated equals to DEFAULT_UPDATED
        defaultVentoVmFaixaShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the ventoVmFaixaList where updated equals to UPDATED_UPDATED
        defaultVentoVmFaixaShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllVentoVmFaixasByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ventoVmFaixaRepository.saveAndFlush(ventoVmFaixa);

        // Get all the ventoVmFaixaList where updated not equals to DEFAULT_UPDATED
        defaultVentoVmFaixaShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the ventoVmFaixaList where updated not equals to UPDATED_UPDATED
        defaultVentoVmFaixaShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllVentoVmFaixasByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        ventoVmFaixaRepository.saveAndFlush(ventoVmFaixa);

        // Get all the ventoVmFaixaList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultVentoVmFaixaShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the ventoVmFaixaList where updated equals to UPDATED_UPDATED
        defaultVentoVmFaixaShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllVentoVmFaixasByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        ventoVmFaixaRepository.saveAndFlush(ventoVmFaixa);

        // Get all the ventoVmFaixaList where updated is not null
        defaultVentoVmFaixaShouldBeFound("updated.specified=true");

        // Get all the ventoVmFaixaList where updated is null
        defaultVentoVmFaixaShouldNotBeFound("updated.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVentoVmFaixaShouldBeFound(String filter) throws Exception {
        restVentoVmFaixaMockMvc.perform(get("/api/vento-vm-faixas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ventoVmFaixa.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].de").value(hasItem(DEFAULT_DE)))
            .andExpect(jsonPath("$.[*].ate").value(hasItem(DEFAULT_ATE)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restVentoVmFaixaMockMvc.perform(get("/api/vento-vm-faixas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVentoVmFaixaShouldNotBeFound(String filter) throws Exception {
        restVentoVmFaixaMockMvc.perform(get("/api/vento-vm-faixas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVentoVmFaixaMockMvc.perform(get("/api/vento-vm-faixas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
