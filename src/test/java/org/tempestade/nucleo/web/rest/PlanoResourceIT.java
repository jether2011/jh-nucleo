package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.Plano;
import org.tempestade.nucleo.domain.Empresa;
import org.tempestade.nucleo.domain.PlanoStatus;
import org.tempestade.nucleo.repository.PlanoRepository;
import org.tempestade.nucleo.service.PlanoService;
import org.tempestade.nucleo.service.dto.PlanoDTO;
import org.tempestade.nucleo.service.mapper.PlanoMapper;
import org.tempestade.nucleo.service.dto.PlanoCriteria;
import org.tempestade.nucleo.service.PlanoQueryService;

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
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PlanoResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PlanoResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Integer DEFAULT_HORARIO_PREVISTO = 1;
    private static final Integer UPDATED_HORARIO_PREVISTO = 2;
    private static final Integer SMALLER_HORARIO_PREVISTO = 1 - 1;

    private static final Integer DEFAULT_TRACKING_ATIVO = 1;
    private static final Integer UPDATED_TRACKING_ATIVO = 2;
    private static final Integer SMALLER_TRACKING_ATIVO = 1 - 1;

    private static final Integer DEFAULT_PLR_ATIVO = 1;
    private static final Integer UPDATED_PLR_ATIVO = 2;
    private static final Integer SMALLER_PLR_ATIVO = 1 - 1;

    private static final Integer DEFAULT_CODIGO_WIDGET_PREVISAO = 1;
    private static final Integer UPDATED_CODIGO_WIDGET_PREVISAO = 2;
    private static final Integer SMALLER_CODIGO_WIDGET_PREVISAO = 1 - 1;

    private static final String DEFAULT_KML_ALVO = "AAAAAAAAAA";
    private static final String UPDATED_KML_ALVO = "BBBBBBBBBB";

    private static final Integer DEFAULT_ZOOM_MIN = 1;
    private static final Integer UPDATED_ZOOM_MIN = 2;
    private static final Integer SMALLER_ZOOM_MIN = 1 - 1;

    private static final LocalDate DEFAULT_DT_INICIO_CONTRATO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DT_INICIO_CONTRATO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DT_INICIO_CONTRATO = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_DATA_FIM_CONTRATO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_FIM_CONTRATO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATA_FIM_CONTRATO = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_HORARIO_MONIT_INICIO = "23:50:36";
    private static final String UPDATED_HORARIO_MONIT_INICIO = "22:50:07";

    private static final String DEFAULT_HORARIO_MONIT_FINAL = "19:50:09";
    private static final String UPDATED_HORARIO_MONIT_FINAL = "21:30:54";

    private static final String DEFAULT_BLOCOS = "AAAAAAAAAA";
    private static final String UPDATED_BLOCOS = "BBBBBBBBBB";

    private static final String DEFAULT_EXTENT = "AAAAAAAAAA";
    private static final String UPDATED_EXTENT = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PlanoRepository planoRepository;

    @Autowired
    private PlanoMapper planoMapper;

    @Autowired
    private PlanoService planoService;

    @Autowired
    private PlanoQueryService planoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlanoMockMvc;

    private Plano plano;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Plano createEntity(EntityManager em) {
        Plano plano = new Plano()
            .name(DEFAULT_NAME)
            .descricao(DEFAULT_DESCRICAO)
            .horarioPrevisto(DEFAULT_HORARIO_PREVISTO)
            .trackingAtivo(DEFAULT_TRACKING_ATIVO)
            .plrAtivo(DEFAULT_PLR_ATIVO)
            .codigoWidgetPrevisao(DEFAULT_CODIGO_WIDGET_PREVISAO)
            .kmlAlvo(DEFAULT_KML_ALVO)
            .zoomMin(DEFAULT_ZOOM_MIN)
            .dtInicioContrato(DEFAULT_DT_INICIO_CONTRATO)
            .dataFimContrato(DEFAULT_DATA_FIM_CONTRATO)
            .horarioMonitInicio(DEFAULT_HORARIO_MONIT_INICIO)
            .horarioMonitFinal(DEFAULT_HORARIO_MONIT_FINAL)
            .blocos(DEFAULT_BLOCOS)
            .extent(DEFAULT_EXTENT)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return plano;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Plano createUpdatedEntity(EntityManager em) {
        Plano plano = new Plano()
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .horarioPrevisto(UPDATED_HORARIO_PREVISTO)
            .trackingAtivo(UPDATED_TRACKING_ATIVO)
            .plrAtivo(UPDATED_PLR_ATIVO)
            .codigoWidgetPrevisao(UPDATED_CODIGO_WIDGET_PREVISAO)
            .kmlAlvo(UPDATED_KML_ALVO)
            .zoomMin(UPDATED_ZOOM_MIN)
            .dtInicioContrato(UPDATED_DT_INICIO_CONTRATO)
            .dataFimContrato(UPDATED_DATA_FIM_CONTRATO)
            .horarioMonitInicio(UPDATED_HORARIO_MONIT_INICIO)
            .horarioMonitFinal(UPDATED_HORARIO_MONIT_FINAL)
            .blocos(UPDATED_BLOCOS)
            .extent(UPDATED_EXTENT)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return plano;
    }

    @BeforeEach
    public void initTest() {
        plano = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlano() throws Exception {
        int databaseSizeBeforeCreate = planoRepository.findAll().size();
        // Create the Plano
        PlanoDTO planoDTO = planoMapper.toDto(plano);
        restPlanoMockMvc.perform(post("/api/planos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoDTO)))
            .andExpect(status().isCreated());

        // Validate the Plano in the database
        List<Plano> planoList = planoRepository.findAll();
        assertThat(planoList).hasSize(databaseSizeBeforeCreate + 1);
        Plano testPlano = planoList.get(planoList.size() - 1);
        assertThat(testPlano.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPlano.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testPlano.getHorarioPrevisto()).isEqualTo(DEFAULT_HORARIO_PREVISTO);
        assertThat(testPlano.getTrackingAtivo()).isEqualTo(DEFAULT_TRACKING_ATIVO);
        assertThat(testPlano.getPlrAtivo()).isEqualTo(DEFAULT_PLR_ATIVO);
        assertThat(testPlano.getCodigoWidgetPrevisao()).isEqualTo(DEFAULT_CODIGO_WIDGET_PREVISAO);
        assertThat(testPlano.getKmlAlvo()).isEqualTo(DEFAULT_KML_ALVO);
        assertThat(testPlano.getZoomMin()).isEqualTo(DEFAULT_ZOOM_MIN);
        assertThat(testPlano.getDtInicioContrato()).isEqualTo(DEFAULT_DT_INICIO_CONTRATO);
        assertThat(testPlano.getDataFimContrato()).isEqualTo(DEFAULT_DATA_FIM_CONTRATO);
        assertThat(testPlano.getHorarioMonitInicio()).isEqualTo(DEFAULT_HORARIO_MONIT_INICIO);
        assertThat(testPlano.getHorarioMonitFinal()).isEqualTo(DEFAULT_HORARIO_MONIT_FINAL);
        assertThat(testPlano.getBlocos()).isEqualTo(DEFAULT_BLOCOS);
        assertThat(testPlano.getExtent()).isEqualTo(DEFAULT_EXTENT);
        assertThat(testPlano.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testPlano.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createPlanoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = planoRepository.findAll().size();

        // Create the Plano with an existing ID
        plano.setId(1L);
        PlanoDTO planoDTO = planoMapper.toDto(plano);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanoMockMvc.perform(post("/api/planos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Plano in the database
        List<Plano> planoList = planoRepository.findAll();
        assertThat(planoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoRepository.findAll().size();
        // set the field null
        plano.setName(null);

        // Create the Plano, which fails.
        PlanoDTO planoDTO = planoMapper.toDto(plano);


        restPlanoMockMvc.perform(post("/api/planos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoDTO)))
            .andExpect(status().isBadRequest());

        List<Plano> planoList = planoRepository.findAll();
        assertThat(planoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoRepository.findAll().size();
        // set the field null
        plano.setDescricao(null);

        // Create the Plano, which fails.
        PlanoDTO planoDTO = planoMapper.toDto(plano);


        restPlanoMockMvc.perform(post("/api/planos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoDTO)))
            .andExpect(status().isBadRequest());

        List<Plano> planoList = planoRepository.findAll();
        assertThat(planoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoRepository.findAll().size();
        // set the field null
        plano.setCreated(null);

        // Create the Plano, which fails.
        PlanoDTO planoDTO = planoMapper.toDto(plano);


        restPlanoMockMvc.perform(post("/api/planos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoDTO)))
            .andExpect(status().isBadRequest());

        List<Plano> planoList = planoRepository.findAll();
        assertThat(planoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPlanos() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList
        restPlanoMockMvc.perform(get("/api/planos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plano.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].horarioPrevisto").value(hasItem(DEFAULT_HORARIO_PREVISTO)))
            .andExpect(jsonPath("$.[*].trackingAtivo").value(hasItem(DEFAULT_TRACKING_ATIVO)))
            .andExpect(jsonPath("$.[*].plrAtivo").value(hasItem(DEFAULT_PLR_ATIVO)))
            .andExpect(jsonPath("$.[*].codigoWidgetPrevisao").value(hasItem(DEFAULT_CODIGO_WIDGET_PREVISAO)))
            .andExpect(jsonPath("$.[*].kmlAlvo").value(hasItem(DEFAULT_KML_ALVO)))
            .andExpect(jsonPath("$.[*].zoomMin").value(hasItem(DEFAULT_ZOOM_MIN)))
            .andExpect(jsonPath("$.[*].dtInicioContrato").value(hasItem(DEFAULT_DT_INICIO_CONTRATO.toString())))
            .andExpect(jsonPath("$.[*].dataFimContrato").value(hasItem(DEFAULT_DATA_FIM_CONTRATO.toString())))
            .andExpect(jsonPath("$.[*].horarioMonitInicio").value(hasItem(DEFAULT_HORARIO_MONIT_INICIO)))
            .andExpect(jsonPath("$.[*].horarioMonitFinal").value(hasItem(DEFAULT_HORARIO_MONIT_FINAL)))
            .andExpect(jsonPath("$.[*].blocos").value(hasItem(DEFAULT_BLOCOS)))
            .andExpect(jsonPath("$.[*].extent").value(hasItem(DEFAULT_EXTENT)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getPlano() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get the plano
        restPlanoMockMvc.perform(get("/api/planos/{id}", plano.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(plano.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.horarioPrevisto").value(DEFAULT_HORARIO_PREVISTO))
            .andExpect(jsonPath("$.trackingAtivo").value(DEFAULT_TRACKING_ATIVO))
            .andExpect(jsonPath("$.plrAtivo").value(DEFAULT_PLR_ATIVO))
            .andExpect(jsonPath("$.codigoWidgetPrevisao").value(DEFAULT_CODIGO_WIDGET_PREVISAO))
            .andExpect(jsonPath("$.kmlAlvo").value(DEFAULT_KML_ALVO))
            .andExpect(jsonPath("$.zoomMin").value(DEFAULT_ZOOM_MIN))
            .andExpect(jsonPath("$.dtInicioContrato").value(DEFAULT_DT_INICIO_CONTRATO.toString()))
            .andExpect(jsonPath("$.dataFimContrato").value(DEFAULT_DATA_FIM_CONTRATO.toString()))
            .andExpect(jsonPath("$.horarioMonitInicio").value(DEFAULT_HORARIO_MONIT_INICIO))
            .andExpect(jsonPath("$.horarioMonitFinal").value(DEFAULT_HORARIO_MONIT_FINAL))
            .andExpect(jsonPath("$.blocos").value(DEFAULT_BLOCOS))
            .andExpect(jsonPath("$.extent").value(DEFAULT_EXTENT))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }


    @Test
    @Transactional
    public void getPlanosByIdFiltering() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        Long id = plano.getId();

        defaultPlanoShouldBeFound("id.equals=" + id);
        defaultPlanoShouldNotBeFound("id.notEquals=" + id);

        defaultPlanoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPlanoShouldNotBeFound("id.greaterThan=" + id);

        defaultPlanoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPlanoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPlanosByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where name equals to DEFAULT_NAME
        defaultPlanoShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the planoList where name equals to UPDATED_NAME
        defaultPlanoShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPlanosByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where name not equals to DEFAULT_NAME
        defaultPlanoShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the planoList where name not equals to UPDATED_NAME
        defaultPlanoShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPlanosByNameIsInShouldWork() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPlanoShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the planoList where name equals to UPDATED_NAME
        defaultPlanoShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPlanosByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where name is not null
        defaultPlanoShouldBeFound("name.specified=true");

        // Get all the planoList where name is null
        defaultPlanoShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllPlanosByNameContainsSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where name contains DEFAULT_NAME
        defaultPlanoShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the planoList where name contains UPDATED_NAME
        defaultPlanoShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPlanosByNameNotContainsSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where name does not contain DEFAULT_NAME
        defaultPlanoShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the planoList where name does not contain UPDATED_NAME
        defaultPlanoShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllPlanosByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where descricao equals to DEFAULT_DESCRICAO
        defaultPlanoShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the planoList where descricao equals to UPDATED_DESCRICAO
        defaultPlanoShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllPlanosByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where descricao not equals to DEFAULT_DESCRICAO
        defaultPlanoShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the planoList where descricao not equals to UPDATED_DESCRICAO
        defaultPlanoShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllPlanosByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultPlanoShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the planoList where descricao equals to UPDATED_DESCRICAO
        defaultPlanoShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllPlanosByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where descricao is not null
        defaultPlanoShouldBeFound("descricao.specified=true");

        // Get all the planoList where descricao is null
        defaultPlanoShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllPlanosByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where descricao contains DEFAULT_DESCRICAO
        defaultPlanoShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the planoList where descricao contains UPDATED_DESCRICAO
        defaultPlanoShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllPlanosByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where descricao does not contain DEFAULT_DESCRICAO
        defaultPlanoShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the planoList where descricao does not contain UPDATED_DESCRICAO
        defaultPlanoShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllPlanosByHorarioPrevistoIsEqualToSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where horarioPrevisto equals to DEFAULT_HORARIO_PREVISTO
        defaultPlanoShouldBeFound("horarioPrevisto.equals=" + DEFAULT_HORARIO_PREVISTO);

        // Get all the planoList where horarioPrevisto equals to UPDATED_HORARIO_PREVISTO
        defaultPlanoShouldNotBeFound("horarioPrevisto.equals=" + UPDATED_HORARIO_PREVISTO);
    }

    @Test
    @Transactional
    public void getAllPlanosByHorarioPrevistoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where horarioPrevisto not equals to DEFAULT_HORARIO_PREVISTO
        defaultPlanoShouldNotBeFound("horarioPrevisto.notEquals=" + DEFAULT_HORARIO_PREVISTO);

        // Get all the planoList where horarioPrevisto not equals to UPDATED_HORARIO_PREVISTO
        defaultPlanoShouldBeFound("horarioPrevisto.notEquals=" + UPDATED_HORARIO_PREVISTO);
    }

    @Test
    @Transactional
    public void getAllPlanosByHorarioPrevistoIsInShouldWork() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where horarioPrevisto in DEFAULT_HORARIO_PREVISTO or UPDATED_HORARIO_PREVISTO
        defaultPlanoShouldBeFound("horarioPrevisto.in=" + DEFAULT_HORARIO_PREVISTO + "," + UPDATED_HORARIO_PREVISTO);

        // Get all the planoList where horarioPrevisto equals to UPDATED_HORARIO_PREVISTO
        defaultPlanoShouldNotBeFound("horarioPrevisto.in=" + UPDATED_HORARIO_PREVISTO);
    }

    @Test
    @Transactional
    public void getAllPlanosByHorarioPrevistoIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where horarioPrevisto is not null
        defaultPlanoShouldBeFound("horarioPrevisto.specified=true");

        // Get all the planoList where horarioPrevisto is null
        defaultPlanoShouldNotBeFound("horarioPrevisto.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlanosByHorarioPrevistoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where horarioPrevisto is greater than or equal to DEFAULT_HORARIO_PREVISTO
        defaultPlanoShouldBeFound("horarioPrevisto.greaterThanOrEqual=" + DEFAULT_HORARIO_PREVISTO);

        // Get all the planoList where horarioPrevisto is greater than or equal to UPDATED_HORARIO_PREVISTO
        defaultPlanoShouldNotBeFound("horarioPrevisto.greaterThanOrEqual=" + UPDATED_HORARIO_PREVISTO);
    }

    @Test
    @Transactional
    public void getAllPlanosByHorarioPrevistoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where horarioPrevisto is less than or equal to DEFAULT_HORARIO_PREVISTO
        defaultPlanoShouldBeFound("horarioPrevisto.lessThanOrEqual=" + DEFAULT_HORARIO_PREVISTO);

        // Get all the planoList where horarioPrevisto is less than or equal to SMALLER_HORARIO_PREVISTO
        defaultPlanoShouldNotBeFound("horarioPrevisto.lessThanOrEqual=" + SMALLER_HORARIO_PREVISTO);
    }

    @Test
    @Transactional
    public void getAllPlanosByHorarioPrevistoIsLessThanSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where horarioPrevisto is less than DEFAULT_HORARIO_PREVISTO
        defaultPlanoShouldNotBeFound("horarioPrevisto.lessThan=" + DEFAULT_HORARIO_PREVISTO);

        // Get all the planoList where horarioPrevisto is less than UPDATED_HORARIO_PREVISTO
        defaultPlanoShouldBeFound("horarioPrevisto.lessThan=" + UPDATED_HORARIO_PREVISTO);
    }

    @Test
    @Transactional
    public void getAllPlanosByHorarioPrevistoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where horarioPrevisto is greater than DEFAULT_HORARIO_PREVISTO
        defaultPlanoShouldNotBeFound("horarioPrevisto.greaterThan=" + DEFAULT_HORARIO_PREVISTO);

        // Get all the planoList where horarioPrevisto is greater than SMALLER_HORARIO_PREVISTO
        defaultPlanoShouldBeFound("horarioPrevisto.greaterThan=" + SMALLER_HORARIO_PREVISTO);
    }


    @Test
    @Transactional
    public void getAllPlanosByTrackingAtivoIsEqualToSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where trackingAtivo equals to DEFAULT_TRACKING_ATIVO
        defaultPlanoShouldBeFound("trackingAtivo.equals=" + DEFAULT_TRACKING_ATIVO);

        // Get all the planoList where trackingAtivo equals to UPDATED_TRACKING_ATIVO
        defaultPlanoShouldNotBeFound("trackingAtivo.equals=" + UPDATED_TRACKING_ATIVO);
    }

    @Test
    @Transactional
    public void getAllPlanosByTrackingAtivoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where trackingAtivo not equals to DEFAULT_TRACKING_ATIVO
        defaultPlanoShouldNotBeFound("trackingAtivo.notEquals=" + DEFAULT_TRACKING_ATIVO);

        // Get all the planoList where trackingAtivo not equals to UPDATED_TRACKING_ATIVO
        defaultPlanoShouldBeFound("trackingAtivo.notEquals=" + UPDATED_TRACKING_ATIVO);
    }

    @Test
    @Transactional
    public void getAllPlanosByTrackingAtivoIsInShouldWork() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where trackingAtivo in DEFAULT_TRACKING_ATIVO or UPDATED_TRACKING_ATIVO
        defaultPlanoShouldBeFound("trackingAtivo.in=" + DEFAULT_TRACKING_ATIVO + "," + UPDATED_TRACKING_ATIVO);

        // Get all the planoList where trackingAtivo equals to UPDATED_TRACKING_ATIVO
        defaultPlanoShouldNotBeFound("trackingAtivo.in=" + UPDATED_TRACKING_ATIVO);
    }

    @Test
    @Transactional
    public void getAllPlanosByTrackingAtivoIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where trackingAtivo is not null
        defaultPlanoShouldBeFound("trackingAtivo.specified=true");

        // Get all the planoList where trackingAtivo is null
        defaultPlanoShouldNotBeFound("trackingAtivo.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlanosByTrackingAtivoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where trackingAtivo is greater than or equal to DEFAULT_TRACKING_ATIVO
        defaultPlanoShouldBeFound("trackingAtivo.greaterThanOrEqual=" + DEFAULT_TRACKING_ATIVO);

        // Get all the planoList where trackingAtivo is greater than or equal to UPDATED_TRACKING_ATIVO
        defaultPlanoShouldNotBeFound("trackingAtivo.greaterThanOrEqual=" + UPDATED_TRACKING_ATIVO);
    }

    @Test
    @Transactional
    public void getAllPlanosByTrackingAtivoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where trackingAtivo is less than or equal to DEFAULT_TRACKING_ATIVO
        defaultPlanoShouldBeFound("trackingAtivo.lessThanOrEqual=" + DEFAULT_TRACKING_ATIVO);

        // Get all the planoList where trackingAtivo is less than or equal to SMALLER_TRACKING_ATIVO
        defaultPlanoShouldNotBeFound("trackingAtivo.lessThanOrEqual=" + SMALLER_TRACKING_ATIVO);
    }

    @Test
    @Transactional
    public void getAllPlanosByTrackingAtivoIsLessThanSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where trackingAtivo is less than DEFAULT_TRACKING_ATIVO
        defaultPlanoShouldNotBeFound("trackingAtivo.lessThan=" + DEFAULT_TRACKING_ATIVO);

        // Get all the planoList where trackingAtivo is less than UPDATED_TRACKING_ATIVO
        defaultPlanoShouldBeFound("trackingAtivo.lessThan=" + UPDATED_TRACKING_ATIVO);
    }

    @Test
    @Transactional
    public void getAllPlanosByTrackingAtivoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where trackingAtivo is greater than DEFAULT_TRACKING_ATIVO
        defaultPlanoShouldNotBeFound("trackingAtivo.greaterThan=" + DEFAULT_TRACKING_ATIVO);

        // Get all the planoList where trackingAtivo is greater than SMALLER_TRACKING_ATIVO
        defaultPlanoShouldBeFound("trackingAtivo.greaterThan=" + SMALLER_TRACKING_ATIVO);
    }


    @Test
    @Transactional
    public void getAllPlanosByPlrAtivoIsEqualToSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where plrAtivo equals to DEFAULT_PLR_ATIVO
        defaultPlanoShouldBeFound("plrAtivo.equals=" + DEFAULT_PLR_ATIVO);

        // Get all the planoList where plrAtivo equals to UPDATED_PLR_ATIVO
        defaultPlanoShouldNotBeFound("plrAtivo.equals=" + UPDATED_PLR_ATIVO);
    }

    @Test
    @Transactional
    public void getAllPlanosByPlrAtivoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where plrAtivo not equals to DEFAULT_PLR_ATIVO
        defaultPlanoShouldNotBeFound("plrAtivo.notEquals=" + DEFAULT_PLR_ATIVO);

        // Get all the planoList where plrAtivo not equals to UPDATED_PLR_ATIVO
        defaultPlanoShouldBeFound("plrAtivo.notEquals=" + UPDATED_PLR_ATIVO);
    }

    @Test
    @Transactional
    public void getAllPlanosByPlrAtivoIsInShouldWork() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where plrAtivo in DEFAULT_PLR_ATIVO or UPDATED_PLR_ATIVO
        defaultPlanoShouldBeFound("plrAtivo.in=" + DEFAULT_PLR_ATIVO + "," + UPDATED_PLR_ATIVO);

        // Get all the planoList where plrAtivo equals to UPDATED_PLR_ATIVO
        defaultPlanoShouldNotBeFound("plrAtivo.in=" + UPDATED_PLR_ATIVO);
    }

    @Test
    @Transactional
    public void getAllPlanosByPlrAtivoIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where plrAtivo is not null
        defaultPlanoShouldBeFound("plrAtivo.specified=true");

        // Get all the planoList where plrAtivo is null
        defaultPlanoShouldNotBeFound("plrAtivo.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlanosByPlrAtivoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where plrAtivo is greater than or equal to DEFAULT_PLR_ATIVO
        defaultPlanoShouldBeFound("plrAtivo.greaterThanOrEqual=" + DEFAULT_PLR_ATIVO);

        // Get all the planoList where plrAtivo is greater than or equal to UPDATED_PLR_ATIVO
        defaultPlanoShouldNotBeFound("plrAtivo.greaterThanOrEqual=" + UPDATED_PLR_ATIVO);
    }

    @Test
    @Transactional
    public void getAllPlanosByPlrAtivoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where plrAtivo is less than or equal to DEFAULT_PLR_ATIVO
        defaultPlanoShouldBeFound("plrAtivo.lessThanOrEqual=" + DEFAULT_PLR_ATIVO);

        // Get all the planoList where plrAtivo is less than or equal to SMALLER_PLR_ATIVO
        defaultPlanoShouldNotBeFound("plrAtivo.lessThanOrEqual=" + SMALLER_PLR_ATIVO);
    }

    @Test
    @Transactional
    public void getAllPlanosByPlrAtivoIsLessThanSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where plrAtivo is less than DEFAULT_PLR_ATIVO
        defaultPlanoShouldNotBeFound("plrAtivo.lessThan=" + DEFAULT_PLR_ATIVO);

        // Get all the planoList where plrAtivo is less than UPDATED_PLR_ATIVO
        defaultPlanoShouldBeFound("plrAtivo.lessThan=" + UPDATED_PLR_ATIVO);
    }

    @Test
    @Transactional
    public void getAllPlanosByPlrAtivoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where plrAtivo is greater than DEFAULT_PLR_ATIVO
        defaultPlanoShouldNotBeFound("plrAtivo.greaterThan=" + DEFAULT_PLR_ATIVO);

        // Get all the planoList where plrAtivo is greater than SMALLER_PLR_ATIVO
        defaultPlanoShouldBeFound("plrAtivo.greaterThan=" + SMALLER_PLR_ATIVO);
    }


    @Test
    @Transactional
    public void getAllPlanosByCodigoWidgetPrevisaoIsEqualToSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where codigoWidgetPrevisao equals to DEFAULT_CODIGO_WIDGET_PREVISAO
        defaultPlanoShouldBeFound("codigoWidgetPrevisao.equals=" + DEFAULT_CODIGO_WIDGET_PREVISAO);

        // Get all the planoList where codigoWidgetPrevisao equals to UPDATED_CODIGO_WIDGET_PREVISAO
        defaultPlanoShouldNotBeFound("codigoWidgetPrevisao.equals=" + UPDATED_CODIGO_WIDGET_PREVISAO);
    }

    @Test
    @Transactional
    public void getAllPlanosByCodigoWidgetPrevisaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where codigoWidgetPrevisao not equals to DEFAULT_CODIGO_WIDGET_PREVISAO
        defaultPlanoShouldNotBeFound("codigoWidgetPrevisao.notEquals=" + DEFAULT_CODIGO_WIDGET_PREVISAO);

        // Get all the planoList where codigoWidgetPrevisao not equals to UPDATED_CODIGO_WIDGET_PREVISAO
        defaultPlanoShouldBeFound("codigoWidgetPrevisao.notEquals=" + UPDATED_CODIGO_WIDGET_PREVISAO);
    }

    @Test
    @Transactional
    public void getAllPlanosByCodigoWidgetPrevisaoIsInShouldWork() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where codigoWidgetPrevisao in DEFAULT_CODIGO_WIDGET_PREVISAO or UPDATED_CODIGO_WIDGET_PREVISAO
        defaultPlanoShouldBeFound("codigoWidgetPrevisao.in=" + DEFAULT_CODIGO_WIDGET_PREVISAO + "," + UPDATED_CODIGO_WIDGET_PREVISAO);

        // Get all the planoList where codigoWidgetPrevisao equals to UPDATED_CODIGO_WIDGET_PREVISAO
        defaultPlanoShouldNotBeFound("codigoWidgetPrevisao.in=" + UPDATED_CODIGO_WIDGET_PREVISAO);
    }

    @Test
    @Transactional
    public void getAllPlanosByCodigoWidgetPrevisaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where codigoWidgetPrevisao is not null
        defaultPlanoShouldBeFound("codigoWidgetPrevisao.specified=true");

        // Get all the planoList where codigoWidgetPrevisao is null
        defaultPlanoShouldNotBeFound("codigoWidgetPrevisao.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlanosByCodigoWidgetPrevisaoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where codigoWidgetPrevisao is greater than or equal to DEFAULT_CODIGO_WIDGET_PREVISAO
        defaultPlanoShouldBeFound("codigoWidgetPrevisao.greaterThanOrEqual=" + DEFAULT_CODIGO_WIDGET_PREVISAO);

        // Get all the planoList where codigoWidgetPrevisao is greater than or equal to UPDATED_CODIGO_WIDGET_PREVISAO
        defaultPlanoShouldNotBeFound("codigoWidgetPrevisao.greaterThanOrEqual=" + UPDATED_CODIGO_WIDGET_PREVISAO);
    }

    @Test
    @Transactional
    public void getAllPlanosByCodigoWidgetPrevisaoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where codigoWidgetPrevisao is less than or equal to DEFAULT_CODIGO_WIDGET_PREVISAO
        defaultPlanoShouldBeFound("codigoWidgetPrevisao.lessThanOrEqual=" + DEFAULT_CODIGO_WIDGET_PREVISAO);

        // Get all the planoList where codigoWidgetPrevisao is less than or equal to SMALLER_CODIGO_WIDGET_PREVISAO
        defaultPlanoShouldNotBeFound("codigoWidgetPrevisao.lessThanOrEqual=" + SMALLER_CODIGO_WIDGET_PREVISAO);
    }

    @Test
    @Transactional
    public void getAllPlanosByCodigoWidgetPrevisaoIsLessThanSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where codigoWidgetPrevisao is less than DEFAULT_CODIGO_WIDGET_PREVISAO
        defaultPlanoShouldNotBeFound("codigoWidgetPrevisao.lessThan=" + DEFAULT_CODIGO_WIDGET_PREVISAO);

        // Get all the planoList where codigoWidgetPrevisao is less than UPDATED_CODIGO_WIDGET_PREVISAO
        defaultPlanoShouldBeFound("codigoWidgetPrevisao.lessThan=" + UPDATED_CODIGO_WIDGET_PREVISAO);
    }

    @Test
    @Transactional
    public void getAllPlanosByCodigoWidgetPrevisaoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where codigoWidgetPrevisao is greater than DEFAULT_CODIGO_WIDGET_PREVISAO
        defaultPlanoShouldNotBeFound("codigoWidgetPrevisao.greaterThan=" + DEFAULT_CODIGO_WIDGET_PREVISAO);

        // Get all the planoList where codigoWidgetPrevisao is greater than SMALLER_CODIGO_WIDGET_PREVISAO
        defaultPlanoShouldBeFound("codigoWidgetPrevisao.greaterThan=" + SMALLER_CODIGO_WIDGET_PREVISAO);
    }


    @Test
    @Transactional
    public void getAllPlanosByKmlAlvoIsEqualToSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where kmlAlvo equals to DEFAULT_KML_ALVO
        defaultPlanoShouldBeFound("kmlAlvo.equals=" + DEFAULT_KML_ALVO);

        // Get all the planoList where kmlAlvo equals to UPDATED_KML_ALVO
        defaultPlanoShouldNotBeFound("kmlAlvo.equals=" + UPDATED_KML_ALVO);
    }

    @Test
    @Transactional
    public void getAllPlanosByKmlAlvoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where kmlAlvo not equals to DEFAULT_KML_ALVO
        defaultPlanoShouldNotBeFound("kmlAlvo.notEquals=" + DEFAULT_KML_ALVO);

        // Get all the planoList where kmlAlvo not equals to UPDATED_KML_ALVO
        defaultPlanoShouldBeFound("kmlAlvo.notEquals=" + UPDATED_KML_ALVO);
    }

    @Test
    @Transactional
    public void getAllPlanosByKmlAlvoIsInShouldWork() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where kmlAlvo in DEFAULT_KML_ALVO or UPDATED_KML_ALVO
        defaultPlanoShouldBeFound("kmlAlvo.in=" + DEFAULT_KML_ALVO + "," + UPDATED_KML_ALVO);

        // Get all the planoList where kmlAlvo equals to UPDATED_KML_ALVO
        defaultPlanoShouldNotBeFound("kmlAlvo.in=" + UPDATED_KML_ALVO);
    }

    @Test
    @Transactional
    public void getAllPlanosByKmlAlvoIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where kmlAlvo is not null
        defaultPlanoShouldBeFound("kmlAlvo.specified=true");

        // Get all the planoList where kmlAlvo is null
        defaultPlanoShouldNotBeFound("kmlAlvo.specified=false");
    }
                @Test
    @Transactional
    public void getAllPlanosByKmlAlvoContainsSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where kmlAlvo contains DEFAULT_KML_ALVO
        defaultPlanoShouldBeFound("kmlAlvo.contains=" + DEFAULT_KML_ALVO);

        // Get all the planoList where kmlAlvo contains UPDATED_KML_ALVO
        defaultPlanoShouldNotBeFound("kmlAlvo.contains=" + UPDATED_KML_ALVO);
    }

    @Test
    @Transactional
    public void getAllPlanosByKmlAlvoNotContainsSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where kmlAlvo does not contain DEFAULT_KML_ALVO
        defaultPlanoShouldNotBeFound("kmlAlvo.doesNotContain=" + DEFAULT_KML_ALVO);

        // Get all the planoList where kmlAlvo does not contain UPDATED_KML_ALVO
        defaultPlanoShouldBeFound("kmlAlvo.doesNotContain=" + UPDATED_KML_ALVO);
    }


    @Test
    @Transactional
    public void getAllPlanosByZoomMinIsEqualToSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where zoomMin equals to DEFAULT_ZOOM_MIN
        defaultPlanoShouldBeFound("zoomMin.equals=" + DEFAULT_ZOOM_MIN);

        // Get all the planoList where zoomMin equals to UPDATED_ZOOM_MIN
        defaultPlanoShouldNotBeFound("zoomMin.equals=" + UPDATED_ZOOM_MIN);
    }

    @Test
    @Transactional
    public void getAllPlanosByZoomMinIsNotEqualToSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where zoomMin not equals to DEFAULT_ZOOM_MIN
        defaultPlanoShouldNotBeFound("zoomMin.notEquals=" + DEFAULT_ZOOM_MIN);

        // Get all the planoList where zoomMin not equals to UPDATED_ZOOM_MIN
        defaultPlanoShouldBeFound("zoomMin.notEquals=" + UPDATED_ZOOM_MIN);
    }

    @Test
    @Transactional
    public void getAllPlanosByZoomMinIsInShouldWork() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where zoomMin in DEFAULT_ZOOM_MIN or UPDATED_ZOOM_MIN
        defaultPlanoShouldBeFound("zoomMin.in=" + DEFAULT_ZOOM_MIN + "," + UPDATED_ZOOM_MIN);

        // Get all the planoList where zoomMin equals to UPDATED_ZOOM_MIN
        defaultPlanoShouldNotBeFound("zoomMin.in=" + UPDATED_ZOOM_MIN);
    }

    @Test
    @Transactional
    public void getAllPlanosByZoomMinIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where zoomMin is not null
        defaultPlanoShouldBeFound("zoomMin.specified=true");

        // Get all the planoList where zoomMin is null
        defaultPlanoShouldNotBeFound("zoomMin.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlanosByZoomMinIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where zoomMin is greater than or equal to DEFAULT_ZOOM_MIN
        defaultPlanoShouldBeFound("zoomMin.greaterThanOrEqual=" + DEFAULT_ZOOM_MIN);

        // Get all the planoList where zoomMin is greater than or equal to UPDATED_ZOOM_MIN
        defaultPlanoShouldNotBeFound("zoomMin.greaterThanOrEqual=" + UPDATED_ZOOM_MIN);
    }

    @Test
    @Transactional
    public void getAllPlanosByZoomMinIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where zoomMin is less than or equal to DEFAULT_ZOOM_MIN
        defaultPlanoShouldBeFound("zoomMin.lessThanOrEqual=" + DEFAULT_ZOOM_MIN);

        // Get all the planoList where zoomMin is less than or equal to SMALLER_ZOOM_MIN
        defaultPlanoShouldNotBeFound("zoomMin.lessThanOrEqual=" + SMALLER_ZOOM_MIN);
    }

    @Test
    @Transactional
    public void getAllPlanosByZoomMinIsLessThanSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where zoomMin is less than DEFAULT_ZOOM_MIN
        defaultPlanoShouldNotBeFound("zoomMin.lessThan=" + DEFAULT_ZOOM_MIN);

        // Get all the planoList where zoomMin is less than UPDATED_ZOOM_MIN
        defaultPlanoShouldBeFound("zoomMin.lessThan=" + UPDATED_ZOOM_MIN);
    }

    @Test
    @Transactional
    public void getAllPlanosByZoomMinIsGreaterThanSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where zoomMin is greater than DEFAULT_ZOOM_MIN
        defaultPlanoShouldNotBeFound("zoomMin.greaterThan=" + DEFAULT_ZOOM_MIN);

        // Get all the planoList where zoomMin is greater than SMALLER_ZOOM_MIN
        defaultPlanoShouldBeFound("zoomMin.greaterThan=" + SMALLER_ZOOM_MIN);
    }


    @Test
    @Transactional
    public void getAllPlanosByDtInicioContratoIsEqualToSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where dtInicioContrato equals to DEFAULT_DT_INICIO_CONTRATO
        defaultPlanoShouldBeFound("dtInicioContrato.equals=" + DEFAULT_DT_INICIO_CONTRATO);

        // Get all the planoList where dtInicioContrato equals to UPDATED_DT_INICIO_CONTRATO
        defaultPlanoShouldNotBeFound("dtInicioContrato.equals=" + UPDATED_DT_INICIO_CONTRATO);
    }

    @Test
    @Transactional
    public void getAllPlanosByDtInicioContratoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where dtInicioContrato not equals to DEFAULT_DT_INICIO_CONTRATO
        defaultPlanoShouldNotBeFound("dtInicioContrato.notEquals=" + DEFAULT_DT_INICIO_CONTRATO);

        // Get all the planoList where dtInicioContrato not equals to UPDATED_DT_INICIO_CONTRATO
        defaultPlanoShouldBeFound("dtInicioContrato.notEquals=" + UPDATED_DT_INICIO_CONTRATO);
    }

    @Test
    @Transactional
    public void getAllPlanosByDtInicioContratoIsInShouldWork() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where dtInicioContrato in DEFAULT_DT_INICIO_CONTRATO or UPDATED_DT_INICIO_CONTRATO
        defaultPlanoShouldBeFound("dtInicioContrato.in=" + DEFAULT_DT_INICIO_CONTRATO + "," + UPDATED_DT_INICIO_CONTRATO);

        // Get all the planoList where dtInicioContrato equals to UPDATED_DT_INICIO_CONTRATO
        defaultPlanoShouldNotBeFound("dtInicioContrato.in=" + UPDATED_DT_INICIO_CONTRATO);
    }

    @Test
    @Transactional
    public void getAllPlanosByDtInicioContratoIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where dtInicioContrato is not null
        defaultPlanoShouldBeFound("dtInicioContrato.specified=true");

        // Get all the planoList where dtInicioContrato is null
        defaultPlanoShouldNotBeFound("dtInicioContrato.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlanosByDtInicioContratoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where dtInicioContrato is greater than or equal to DEFAULT_DT_INICIO_CONTRATO
        defaultPlanoShouldBeFound("dtInicioContrato.greaterThanOrEqual=" + DEFAULT_DT_INICIO_CONTRATO);

        // Get all the planoList where dtInicioContrato is greater than or equal to UPDATED_DT_INICIO_CONTRATO
        defaultPlanoShouldNotBeFound("dtInicioContrato.greaterThanOrEqual=" + UPDATED_DT_INICIO_CONTRATO);
    }

    @Test
    @Transactional
    public void getAllPlanosByDtInicioContratoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where dtInicioContrato is less than or equal to DEFAULT_DT_INICIO_CONTRATO
        defaultPlanoShouldBeFound("dtInicioContrato.lessThanOrEqual=" + DEFAULT_DT_INICIO_CONTRATO);

        // Get all the planoList where dtInicioContrato is less than or equal to SMALLER_DT_INICIO_CONTRATO
        defaultPlanoShouldNotBeFound("dtInicioContrato.lessThanOrEqual=" + SMALLER_DT_INICIO_CONTRATO);
    }

    @Test
    @Transactional
    public void getAllPlanosByDtInicioContratoIsLessThanSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where dtInicioContrato is less than DEFAULT_DT_INICIO_CONTRATO
        defaultPlanoShouldNotBeFound("dtInicioContrato.lessThan=" + DEFAULT_DT_INICIO_CONTRATO);

        // Get all the planoList where dtInicioContrato is less than UPDATED_DT_INICIO_CONTRATO
        defaultPlanoShouldBeFound("dtInicioContrato.lessThan=" + UPDATED_DT_INICIO_CONTRATO);
    }

    @Test
    @Transactional
    public void getAllPlanosByDtInicioContratoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where dtInicioContrato is greater than DEFAULT_DT_INICIO_CONTRATO
        defaultPlanoShouldNotBeFound("dtInicioContrato.greaterThan=" + DEFAULT_DT_INICIO_CONTRATO);

        // Get all the planoList where dtInicioContrato is greater than SMALLER_DT_INICIO_CONTRATO
        defaultPlanoShouldBeFound("dtInicioContrato.greaterThan=" + SMALLER_DT_INICIO_CONTRATO);
    }


    @Test
    @Transactional
    public void getAllPlanosByDataFimContratoIsEqualToSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where dataFimContrato equals to DEFAULT_DATA_FIM_CONTRATO
        defaultPlanoShouldBeFound("dataFimContrato.equals=" + DEFAULT_DATA_FIM_CONTRATO);

        // Get all the planoList where dataFimContrato equals to UPDATED_DATA_FIM_CONTRATO
        defaultPlanoShouldNotBeFound("dataFimContrato.equals=" + UPDATED_DATA_FIM_CONTRATO);
    }

    @Test
    @Transactional
    public void getAllPlanosByDataFimContratoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where dataFimContrato not equals to DEFAULT_DATA_FIM_CONTRATO
        defaultPlanoShouldNotBeFound("dataFimContrato.notEquals=" + DEFAULT_DATA_FIM_CONTRATO);

        // Get all the planoList where dataFimContrato not equals to UPDATED_DATA_FIM_CONTRATO
        defaultPlanoShouldBeFound("dataFimContrato.notEquals=" + UPDATED_DATA_FIM_CONTRATO);
    }

    @Test
    @Transactional
    public void getAllPlanosByDataFimContratoIsInShouldWork() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where dataFimContrato in DEFAULT_DATA_FIM_CONTRATO or UPDATED_DATA_FIM_CONTRATO
        defaultPlanoShouldBeFound("dataFimContrato.in=" + DEFAULT_DATA_FIM_CONTRATO + "," + UPDATED_DATA_FIM_CONTRATO);

        // Get all the planoList where dataFimContrato equals to UPDATED_DATA_FIM_CONTRATO
        defaultPlanoShouldNotBeFound("dataFimContrato.in=" + UPDATED_DATA_FIM_CONTRATO);
    }

    @Test
    @Transactional
    public void getAllPlanosByDataFimContratoIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where dataFimContrato is not null
        defaultPlanoShouldBeFound("dataFimContrato.specified=true");

        // Get all the planoList where dataFimContrato is null
        defaultPlanoShouldNotBeFound("dataFimContrato.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlanosByDataFimContratoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where dataFimContrato is greater than or equal to DEFAULT_DATA_FIM_CONTRATO
        defaultPlanoShouldBeFound("dataFimContrato.greaterThanOrEqual=" + DEFAULT_DATA_FIM_CONTRATO);

        // Get all the planoList where dataFimContrato is greater than or equal to UPDATED_DATA_FIM_CONTRATO
        defaultPlanoShouldNotBeFound("dataFimContrato.greaterThanOrEqual=" + UPDATED_DATA_FIM_CONTRATO);
    }

    @Test
    @Transactional
    public void getAllPlanosByDataFimContratoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where dataFimContrato is less than or equal to DEFAULT_DATA_FIM_CONTRATO
        defaultPlanoShouldBeFound("dataFimContrato.lessThanOrEqual=" + DEFAULT_DATA_FIM_CONTRATO);

        // Get all the planoList where dataFimContrato is less than or equal to SMALLER_DATA_FIM_CONTRATO
        defaultPlanoShouldNotBeFound("dataFimContrato.lessThanOrEqual=" + SMALLER_DATA_FIM_CONTRATO);
    }

    @Test
    @Transactional
    public void getAllPlanosByDataFimContratoIsLessThanSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where dataFimContrato is less than DEFAULT_DATA_FIM_CONTRATO
        defaultPlanoShouldNotBeFound("dataFimContrato.lessThan=" + DEFAULT_DATA_FIM_CONTRATO);

        // Get all the planoList where dataFimContrato is less than UPDATED_DATA_FIM_CONTRATO
        defaultPlanoShouldBeFound("dataFimContrato.lessThan=" + UPDATED_DATA_FIM_CONTRATO);
    }

    @Test
    @Transactional
    public void getAllPlanosByDataFimContratoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where dataFimContrato is greater than DEFAULT_DATA_FIM_CONTRATO
        defaultPlanoShouldNotBeFound("dataFimContrato.greaterThan=" + DEFAULT_DATA_FIM_CONTRATO);

        // Get all the planoList where dataFimContrato is greater than SMALLER_DATA_FIM_CONTRATO
        defaultPlanoShouldBeFound("dataFimContrato.greaterThan=" + SMALLER_DATA_FIM_CONTRATO);
    }


    @Test
    @Transactional
    public void getAllPlanosByHorarioMonitInicioIsEqualToSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where horarioMonitInicio equals to DEFAULT_HORARIO_MONIT_INICIO
        defaultPlanoShouldBeFound("horarioMonitInicio.equals=" + DEFAULT_HORARIO_MONIT_INICIO);

        // Get all the planoList where horarioMonitInicio equals to UPDATED_HORARIO_MONIT_INICIO
        defaultPlanoShouldNotBeFound("horarioMonitInicio.equals=" + UPDATED_HORARIO_MONIT_INICIO);
    }

    @Test
    @Transactional
    public void getAllPlanosByHorarioMonitInicioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where horarioMonitInicio not equals to DEFAULT_HORARIO_MONIT_INICIO
        defaultPlanoShouldNotBeFound("horarioMonitInicio.notEquals=" + DEFAULT_HORARIO_MONIT_INICIO);

        // Get all the planoList where horarioMonitInicio not equals to UPDATED_HORARIO_MONIT_INICIO
        defaultPlanoShouldBeFound("horarioMonitInicio.notEquals=" + UPDATED_HORARIO_MONIT_INICIO);
    }

    @Test
    @Transactional
    public void getAllPlanosByHorarioMonitInicioIsInShouldWork() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where horarioMonitInicio in DEFAULT_HORARIO_MONIT_INICIO or UPDATED_HORARIO_MONIT_INICIO
        defaultPlanoShouldBeFound("horarioMonitInicio.in=" + DEFAULT_HORARIO_MONIT_INICIO + "," + UPDATED_HORARIO_MONIT_INICIO);

        // Get all the planoList where horarioMonitInicio equals to UPDATED_HORARIO_MONIT_INICIO
        defaultPlanoShouldNotBeFound("horarioMonitInicio.in=" + UPDATED_HORARIO_MONIT_INICIO);
    }

    @Test
    @Transactional
    public void getAllPlanosByHorarioMonitInicioIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where horarioMonitInicio is not null
        defaultPlanoShouldBeFound("horarioMonitInicio.specified=true");

        // Get all the planoList where horarioMonitInicio is null
        defaultPlanoShouldNotBeFound("horarioMonitInicio.specified=false");
    }
                @Test
    @Transactional
    public void getAllPlanosByHorarioMonitInicioContainsSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where horarioMonitInicio contains DEFAULT_HORARIO_MONIT_INICIO
        defaultPlanoShouldBeFound("horarioMonitInicio.contains=" + DEFAULT_HORARIO_MONIT_INICIO);

        // Get all the planoList where horarioMonitInicio contains UPDATED_HORARIO_MONIT_INICIO
        defaultPlanoShouldNotBeFound("horarioMonitInicio.contains=" + UPDATED_HORARIO_MONIT_INICIO);
    }

    @Test
    @Transactional
    public void getAllPlanosByHorarioMonitInicioNotContainsSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where horarioMonitInicio does not contain DEFAULT_HORARIO_MONIT_INICIO
        defaultPlanoShouldNotBeFound("horarioMonitInicio.doesNotContain=" + DEFAULT_HORARIO_MONIT_INICIO);

        // Get all the planoList where horarioMonitInicio does not contain UPDATED_HORARIO_MONIT_INICIO
        defaultPlanoShouldBeFound("horarioMonitInicio.doesNotContain=" + UPDATED_HORARIO_MONIT_INICIO);
    }


    @Test
    @Transactional
    public void getAllPlanosByHorarioMonitFinalIsEqualToSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where horarioMonitFinal equals to DEFAULT_HORARIO_MONIT_FINAL
        defaultPlanoShouldBeFound("horarioMonitFinal.equals=" + DEFAULT_HORARIO_MONIT_FINAL);

        // Get all the planoList where horarioMonitFinal equals to UPDATED_HORARIO_MONIT_FINAL
        defaultPlanoShouldNotBeFound("horarioMonitFinal.equals=" + UPDATED_HORARIO_MONIT_FINAL);
    }

    @Test
    @Transactional
    public void getAllPlanosByHorarioMonitFinalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where horarioMonitFinal not equals to DEFAULT_HORARIO_MONIT_FINAL
        defaultPlanoShouldNotBeFound("horarioMonitFinal.notEquals=" + DEFAULT_HORARIO_MONIT_FINAL);

        // Get all the planoList where horarioMonitFinal not equals to UPDATED_HORARIO_MONIT_FINAL
        defaultPlanoShouldBeFound("horarioMonitFinal.notEquals=" + UPDATED_HORARIO_MONIT_FINAL);
    }

    @Test
    @Transactional
    public void getAllPlanosByHorarioMonitFinalIsInShouldWork() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where horarioMonitFinal in DEFAULT_HORARIO_MONIT_FINAL or UPDATED_HORARIO_MONIT_FINAL
        defaultPlanoShouldBeFound("horarioMonitFinal.in=" + DEFAULT_HORARIO_MONIT_FINAL + "," + UPDATED_HORARIO_MONIT_FINAL);

        // Get all the planoList where horarioMonitFinal equals to UPDATED_HORARIO_MONIT_FINAL
        defaultPlanoShouldNotBeFound("horarioMonitFinal.in=" + UPDATED_HORARIO_MONIT_FINAL);
    }

    @Test
    @Transactional
    public void getAllPlanosByHorarioMonitFinalIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where horarioMonitFinal is not null
        defaultPlanoShouldBeFound("horarioMonitFinal.specified=true");

        // Get all the planoList where horarioMonitFinal is null
        defaultPlanoShouldNotBeFound("horarioMonitFinal.specified=false");
    }
                @Test
    @Transactional
    public void getAllPlanosByHorarioMonitFinalContainsSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where horarioMonitFinal contains DEFAULT_HORARIO_MONIT_FINAL
        defaultPlanoShouldBeFound("horarioMonitFinal.contains=" + DEFAULT_HORARIO_MONIT_FINAL);

        // Get all the planoList where horarioMonitFinal contains UPDATED_HORARIO_MONIT_FINAL
        defaultPlanoShouldNotBeFound("horarioMonitFinal.contains=" + UPDATED_HORARIO_MONIT_FINAL);
    }

    @Test
    @Transactional
    public void getAllPlanosByHorarioMonitFinalNotContainsSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where horarioMonitFinal does not contain DEFAULT_HORARIO_MONIT_FINAL
        defaultPlanoShouldNotBeFound("horarioMonitFinal.doesNotContain=" + DEFAULT_HORARIO_MONIT_FINAL);

        // Get all the planoList where horarioMonitFinal does not contain UPDATED_HORARIO_MONIT_FINAL
        defaultPlanoShouldBeFound("horarioMonitFinal.doesNotContain=" + UPDATED_HORARIO_MONIT_FINAL);
    }


    @Test
    @Transactional
    public void getAllPlanosByBlocosIsEqualToSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where blocos equals to DEFAULT_BLOCOS
        defaultPlanoShouldBeFound("blocos.equals=" + DEFAULT_BLOCOS);

        // Get all the planoList where blocos equals to UPDATED_BLOCOS
        defaultPlanoShouldNotBeFound("blocos.equals=" + UPDATED_BLOCOS);
    }

    @Test
    @Transactional
    public void getAllPlanosByBlocosIsNotEqualToSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where blocos not equals to DEFAULT_BLOCOS
        defaultPlanoShouldNotBeFound("blocos.notEquals=" + DEFAULT_BLOCOS);

        // Get all the planoList where blocos not equals to UPDATED_BLOCOS
        defaultPlanoShouldBeFound("blocos.notEquals=" + UPDATED_BLOCOS);
    }

    @Test
    @Transactional
    public void getAllPlanosByBlocosIsInShouldWork() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where blocos in DEFAULT_BLOCOS or UPDATED_BLOCOS
        defaultPlanoShouldBeFound("blocos.in=" + DEFAULT_BLOCOS + "," + UPDATED_BLOCOS);

        // Get all the planoList where blocos equals to UPDATED_BLOCOS
        defaultPlanoShouldNotBeFound("blocos.in=" + UPDATED_BLOCOS);
    }

    @Test
    @Transactional
    public void getAllPlanosByBlocosIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where blocos is not null
        defaultPlanoShouldBeFound("blocos.specified=true");

        // Get all the planoList where blocos is null
        defaultPlanoShouldNotBeFound("blocos.specified=false");
    }
                @Test
    @Transactional
    public void getAllPlanosByBlocosContainsSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where blocos contains DEFAULT_BLOCOS
        defaultPlanoShouldBeFound("blocos.contains=" + DEFAULT_BLOCOS);

        // Get all the planoList where blocos contains UPDATED_BLOCOS
        defaultPlanoShouldNotBeFound("blocos.contains=" + UPDATED_BLOCOS);
    }

    @Test
    @Transactional
    public void getAllPlanosByBlocosNotContainsSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where blocos does not contain DEFAULT_BLOCOS
        defaultPlanoShouldNotBeFound("blocos.doesNotContain=" + DEFAULT_BLOCOS);

        // Get all the planoList where blocos does not contain UPDATED_BLOCOS
        defaultPlanoShouldBeFound("blocos.doesNotContain=" + UPDATED_BLOCOS);
    }


    @Test
    @Transactional
    public void getAllPlanosByExtentIsEqualToSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where extent equals to DEFAULT_EXTENT
        defaultPlanoShouldBeFound("extent.equals=" + DEFAULT_EXTENT);

        // Get all the planoList where extent equals to UPDATED_EXTENT
        defaultPlanoShouldNotBeFound("extent.equals=" + UPDATED_EXTENT);
    }

    @Test
    @Transactional
    public void getAllPlanosByExtentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where extent not equals to DEFAULT_EXTENT
        defaultPlanoShouldNotBeFound("extent.notEquals=" + DEFAULT_EXTENT);

        // Get all the planoList where extent not equals to UPDATED_EXTENT
        defaultPlanoShouldBeFound("extent.notEquals=" + UPDATED_EXTENT);
    }

    @Test
    @Transactional
    public void getAllPlanosByExtentIsInShouldWork() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where extent in DEFAULT_EXTENT or UPDATED_EXTENT
        defaultPlanoShouldBeFound("extent.in=" + DEFAULT_EXTENT + "," + UPDATED_EXTENT);

        // Get all the planoList where extent equals to UPDATED_EXTENT
        defaultPlanoShouldNotBeFound("extent.in=" + UPDATED_EXTENT);
    }

    @Test
    @Transactional
    public void getAllPlanosByExtentIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where extent is not null
        defaultPlanoShouldBeFound("extent.specified=true");

        // Get all the planoList where extent is null
        defaultPlanoShouldNotBeFound("extent.specified=false");
    }
                @Test
    @Transactional
    public void getAllPlanosByExtentContainsSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where extent contains DEFAULT_EXTENT
        defaultPlanoShouldBeFound("extent.contains=" + DEFAULT_EXTENT);

        // Get all the planoList where extent contains UPDATED_EXTENT
        defaultPlanoShouldNotBeFound("extent.contains=" + UPDATED_EXTENT);
    }

    @Test
    @Transactional
    public void getAllPlanosByExtentNotContainsSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where extent does not contain DEFAULT_EXTENT
        defaultPlanoShouldNotBeFound("extent.doesNotContain=" + DEFAULT_EXTENT);

        // Get all the planoList where extent does not contain UPDATED_EXTENT
        defaultPlanoShouldBeFound("extent.doesNotContain=" + UPDATED_EXTENT);
    }


    @Test
    @Transactional
    public void getAllPlanosByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where created equals to DEFAULT_CREATED
        defaultPlanoShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the planoList where created equals to UPDATED_CREATED
        defaultPlanoShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllPlanosByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where created not equals to DEFAULT_CREATED
        defaultPlanoShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the planoList where created not equals to UPDATED_CREATED
        defaultPlanoShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllPlanosByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultPlanoShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the planoList where created equals to UPDATED_CREATED
        defaultPlanoShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllPlanosByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where created is not null
        defaultPlanoShouldBeFound("created.specified=true");

        // Get all the planoList where created is null
        defaultPlanoShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlanosByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where updated equals to DEFAULT_UPDATED
        defaultPlanoShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the planoList where updated equals to UPDATED_UPDATED
        defaultPlanoShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllPlanosByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where updated not equals to DEFAULT_UPDATED
        defaultPlanoShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the planoList where updated not equals to UPDATED_UPDATED
        defaultPlanoShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllPlanosByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultPlanoShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the planoList where updated equals to UPDATED_UPDATED
        defaultPlanoShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllPlanosByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList where updated is not null
        defaultPlanoShouldBeFound("updated.specified=true");

        // Get all the planoList where updated is null
        defaultPlanoShouldNotBeFound("updated.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlanosByEmpresaIsEqualToSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);
        Empresa empresa = EmpresaResourceIT.createEntity(em);
        em.persist(empresa);
        em.flush();
        plano.setEmpresa(empresa);
        planoRepository.saveAndFlush(plano);
        Long empresaId = empresa.getId();

        // Get all the planoList where empresa equals to empresaId
        defaultPlanoShouldBeFound("empresaId.equals=" + empresaId);

        // Get all the planoList where empresa equals to empresaId + 1
        defaultPlanoShouldNotBeFound("empresaId.equals=" + (empresaId + 1));
    }


    @Test
    @Transactional
    public void getAllPlanosByPlanoStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);
        PlanoStatus planoStatus = PlanoStatusResourceIT.createEntity(em);
        em.persist(planoStatus);
        em.flush();
        plano.setPlanoStatus(planoStatus);
        planoRepository.saveAndFlush(plano);
        Long planoStatusId = planoStatus.getId();

        // Get all the planoList where planoStatus equals to planoStatusId
        defaultPlanoShouldBeFound("planoStatusId.equals=" + planoStatusId);

        // Get all the planoList where planoStatus equals to planoStatusId + 1
        defaultPlanoShouldNotBeFound("planoStatusId.equals=" + (planoStatusId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPlanoShouldBeFound(String filter) throws Exception {
        restPlanoMockMvc.perform(get("/api/planos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plano.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].horarioPrevisto").value(hasItem(DEFAULT_HORARIO_PREVISTO)))
            .andExpect(jsonPath("$.[*].trackingAtivo").value(hasItem(DEFAULT_TRACKING_ATIVO)))
            .andExpect(jsonPath("$.[*].plrAtivo").value(hasItem(DEFAULT_PLR_ATIVO)))
            .andExpect(jsonPath("$.[*].codigoWidgetPrevisao").value(hasItem(DEFAULT_CODIGO_WIDGET_PREVISAO)))
            .andExpect(jsonPath("$.[*].kmlAlvo").value(hasItem(DEFAULT_KML_ALVO)))
            .andExpect(jsonPath("$.[*].zoomMin").value(hasItem(DEFAULT_ZOOM_MIN)))
            .andExpect(jsonPath("$.[*].dtInicioContrato").value(hasItem(DEFAULT_DT_INICIO_CONTRATO.toString())))
            .andExpect(jsonPath("$.[*].dataFimContrato").value(hasItem(DEFAULT_DATA_FIM_CONTRATO.toString())))
            .andExpect(jsonPath("$.[*].horarioMonitInicio").value(hasItem(DEFAULT_HORARIO_MONIT_INICIO)))
            .andExpect(jsonPath("$.[*].horarioMonitFinal").value(hasItem(DEFAULT_HORARIO_MONIT_FINAL)))
            .andExpect(jsonPath("$.[*].blocos").value(hasItem(DEFAULT_BLOCOS)))
            .andExpect(jsonPath("$.[*].extent").value(hasItem(DEFAULT_EXTENT)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restPlanoMockMvc.perform(get("/api/planos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPlanoShouldNotBeFound(String filter) throws Exception {
        restPlanoMockMvc.perform(get("/api/planos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPlanoMockMvc.perform(get("/api/planos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPlano() throws Exception {
        // Get the plano
        restPlanoMockMvc.perform(get("/api/planos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlano() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        int databaseSizeBeforeUpdate = planoRepository.findAll().size();

        // Update the plano
        Plano updatedPlano = planoRepository.findById(plano.getId()).get();
        // Disconnect from session so that the updates on updatedPlano are not directly saved in db
        em.detach(updatedPlano);
        updatedPlano
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .horarioPrevisto(UPDATED_HORARIO_PREVISTO)
            .trackingAtivo(UPDATED_TRACKING_ATIVO)
            .plrAtivo(UPDATED_PLR_ATIVO)
            .codigoWidgetPrevisao(UPDATED_CODIGO_WIDGET_PREVISAO)
            .kmlAlvo(UPDATED_KML_ALVO)
            .zoomMin(UPDATED_ZOOM_MIN)
            .dtInicioContrato(UPDATED_DT_INICIO_CONTRATO)
            .dataFimContrato(UPDATED_DATA_FIM_CONTRATO)
            .horarioMonitInicio(UPDATED_HORARIO_MONIT_INICIO)
            .horarioMonitFinal(UPDATED_HORARIO_MONIT_FINAL)
            .blocos(UPDATED_BLOCOS)
            .extent(UPDATED_EXTENT)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        PlanoDTO planoDTO = planoMapper.toDto(updatedPlano);

        restPlanoMockMvc.perform(put("/api/planos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoDTO)))
            .andExpect(status().isOk());

        // Validate the Plano in the database
        List<Plano> planoList = planoRepository.findAll();
        assertThat(planoList).hasSize(databaseSizeBeforeUpdate);
        Plano testPlano = planoList.get(planoList.size() - 1);
        assertThat(testPlano.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPlano.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testPlano.getHorarioPrevisto()).isEqualTo(UPDATED_HORARIO_PREVISTO);
        assertThat(testPlano.getTrackingAtivo()).isEqualTo(UPDATED_TRACKING_ATIVO);
        assertThat(testPlano.getPlrAtivo()).isEqualTo(UPDATED_PLR_ATIVO);
        assertThat(testPlano.getCodigoWidgetPrevisao()).isEqualTo(UPDATED_CODIGO_WIDGET_PREVISAO);
        assertThat(testPlano.getKmlAlvo()).isEqualTo(UPDATED_KML_ALVO);
        assertThat(testPlano.getZoomMin()).isEqualTo(UPDATED_ZOOM_MIN);
        assertThat(testPlano.getDtInicioContrato()).isEqualTo(UPDATED_DT_INICIO_CONTRATO);
        assertThat(testPlano.getDataFimContrato()).isEqualTo(UPDATED_DATA_FIM_CONTRATO);
        assertThat(testPlano.getHorarioMonitInicio()).isEqualTo(UPDATED_HORARIO_MONIT_INICIO);
        assertThat(testPlano.getHorarioMonitFinal()).isEqualTo(UPDATED_HORARIO_MONIT_FINAL);
        assertThat(testPlano.getBlocos()).isEqualTo(UPDATED_BLOCOS);
        assertThat(testPlano.getExtent()).isEqualTo(UPDATED_EXTENT);
        assertThat(testPlano.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testPlano.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingPlano() throws Exception {
        int databaseSizeBeforeUpdate = planoRepository.findAll().size();

        // Create the Plano
        PlanoDTO planoDTO = planoMapper.toDto(plano);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanoMockMvc.perform(put("/api/planos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Plano in the database
        List<Plano> planoList = planoRepository.findAll();
        assertThat(planoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePlano() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        int databaseSizeBeforeDelete = planoRepository.findAll().size();

        // Delete the plano
        restPlanoMockMvc.perform(delete("/api/planos/{id}", plano.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Plano> planoList = planoRepository.findAll();
        assertThat(planoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
