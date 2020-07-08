package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.Plano;
import org.tempestade.nucleo.repository.PlanoRepository;
import org.tempestade.nucleo.service.PlanoService;
import org.tempestade.nucleo.service.dto.PlanoDTO;
import org.tempestade.nucleo.service.mapper.PlanoMapper;

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

    private static final Integer DEFAULT_TRACKING_ATIVO = 1;
    private static final Integer UPDATED_TRACKING_ATIVO = 2;

    private static final Integer DEFAULT_PLR_ATIVO = 1;
    private static final Integer UPDATED_PLR_ATIVO = 2;

    private static final Integer DEFAULT_CODIGO_WIDGET_PREVISAO = 1;
    private static final Integer UPDATED_CODIGO_WIDGET_PREVISAO = 2;

    private static final String DEFAULT_KML_ALVO = "AAAAAAAAAA";
    private static final String UPDATED_KML_ALVO = "BBBBBBBBBB";

    private static final Integer DEFAULT_ZOOM_MIN = 1;
    private static final Integer UPDATED_ZOOM_MIN = 2;

    private static final LocalDate DEFAULT_DT_INICIO_CONTRATO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DT_INICIO_CONTRATO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATA_FIM_CONTRATO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_FIM_CONTRATO = LocalDate.now(ZoneId.systemDefault());

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
