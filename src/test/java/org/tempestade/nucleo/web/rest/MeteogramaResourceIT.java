package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.Meteograma;
import org.tempestade.nucleo.repository.MeteogramaRepository;
import org.tempestade.nucleo.service.MeteogramaService;
import org.tempestade.nucleo.service.dto.MeteogramaDTO;
import org.tempestade.nucleo.service.mapper.MeteogramaMapper;

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
 * Integration tests for the {@link MeteogramaResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MeteogramaResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_ARQUIVO = "AAAAAAAAAA";
    private static final String UPDATED_ARQUIVO = "BBBBBBBBBB";

    private static final String DEFAULT_FOLDER = "AAAAAAAAAA";
    private static final String UPDATED_FOLDER = "BBBBBBBBBB";

    private static final String DEFAULT_TIPOARQUIVO = "AAAAAAAAAA";
    private static final String UPDATED_TIPOARQUIVO = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private MeteogramaRepository meteogramaRepository;

    @Autowired
    private MeteogramaMapper meteogramaMapper;

    @Autowired
    private MeteogramaService meteogramaService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMeteogramaMockMvc;

    private Meteograma meteograma;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Meteograma createEntity(EntityManager em) {
        Meteograma meteograma = new Meteograma()
            .name(DEFAULT_NAME)
            .descricao(DEFAULT_DESCRICAO)
            .arquivo(DEFAULT_ARQUIVO)
            .folder(DEFAULT_FOLDER)
            .tipoarquivo(DEFAULT_TIPOARQUIVO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return meteograma;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Meteograma createUpdatedEntity(EntityManager em) {
        Meteograma meteograma = new Meteograma()
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .arquivo(UPDATED_ARQUIVO)
            .folder(UPDATED_FOLDER)
            .tipoarquivo(UPDATED_TIPOARQUIVO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return meteograma;
    }

    @BeforeEach
    public void initTest() {
        meteograma = createEntity(em);
    }

    @Test
    @Transactional
    public void createMeteograma() throws Exception {
        int databaseSizeBeforeCreate = meteogramaRepository.findAll().size();
        // Create the Meteograma
        MeteogramaDTO meteogramaDTO = meteogramaMapper.toDto(meteograma);
        restMeteogramaMockMvc.perform(post("/api/meteogramas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(meteogramaDTO)))
            .andExpect(status().isCreated());

        // Validate the Meteograma in the database
        List<Meteograma> meteogramaList = meteogramaRepository.findAll();
        assertThat(meteogramaList).hasSize(databaseSizeBeforeCreate + 1);
        Meteograma testMeteograma = meteogramaList.get(meteogramaList.size() - 1);
        assertThat(testMeteograma.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMeteograma.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testMeteograma.getArquivo()).isEqualTo(DEFAULT_ARQUIVO);
        assertThat(testMeteograma.getFolder()).isEqualTo(DEFAULT_FOLDER);
        assertThat(testMeteograma.getTipoarquivo()).isEqualTo(DEFAULT_TIPOARQUIVO);
        assertThat(testMeteograma.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testMeteograma.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createMeteogramaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = meteogramaRepository.findAll().size();

        // Create the Meteograma with an existing ID
        meteograma.setId(1L);
        MeteogramaDTO meteogramaDTO = meteogramaMapper.toDto(meteograma);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMeteogramaMockMvc.perform(post("/api/meteogramas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(meteogramaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Meteograma in the database
        List<Meteograma> meteogramaList = meteogramaRepository.findAll();
        assertThat(meteogramaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = meteogramaRepository.findAll().size();
        // set the field null
        meteograma.setName(null);

        // Create the Meteograma, which fails.
        MeteogramaDTO meteogramaDTO = meteogramaMapper.toDto(meteograma);


        restMeteogramaMockMvc.perform(post("/api/meteogramas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(meteogramaDTO)))
            .andExpect(status().isBadRequest());

        List<Meteograma> meteogramaList = meteogramaRepository.findAll();
        assertThat(meteogramaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = meteogramaRepository.findAll().size();
        // set the field null
        meteograma.setDescricao(null);

        // Create the Meteograma, which fails.
        MeteogramaDTO meteogramaDTO = meteogramaMapper.toDto(meteograma);


        restMeteogramaMockMvc.perform(post("/api/meteogramas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(meteogramaDTO)))
            .andExpect(status().isBadRequest());

        List<Meteograma> meteogramaList = meteogramaRepository.findAll();
        assertThat(meteogramaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = meteogramaRepository.findAll().size();
        // set the field null
        meteograma.setCreated(null);

        // Create the Meteograma, which fails.
        MeteogramaDTO meteogramaDTO = meteogramaMapper.toDto(meteograma);


        restMeteogramaMockMvc.perform(post("/api/meteogramas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(meteogramaDTO)))
            .andExpect(status().isBadRequest());

        List<Meteograma> meteogramaList = meteogramaRepository.findAll();
        assertThat(meteogramaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMeteogramas() throws Exception {
        // Initialize the database
        meteogramaRepository.saveAndFlush(meteograma);

        // Get all the meteogramaList
        restMeteogramaMockMvc.perform(get("/api/meteogramas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(meteograma.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].arquivo").value(hasItem(DEFAULT_ARQUIVO)))
            .andExpect(jsonPath("$.[*].folder").value(hasItem(DEFAULT_FOLDER)))
            .andExpect(jsonPath("$.[*].tipoarquivo").value(hasItem(DEFAULT_TIPOARQUIVO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getMeteograma() throws Exception {
        // Initialize the database
        meteogramaRepository.saveAndFlush(meteograma);

        // Get the meteograma
        restMeteogramaMockMvc.perform(get("/api/meteogramas/{id}", meteograma.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(meteograma.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.arquivo").value(DEFAULT_ARQUIVO))
            .andExpect(jsonPath("$.folder").value(DEFAULT_FOLDER))
            .andExpect(jsonPath("$.tipoarquivo").value(DEFAULT_TIPOARQUIVO))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingMeteograma() throws Exception {
        // Get the meteograma
        restMeteogramaMockMvc.perform(get("/api/meteogramas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMeteograma() throws Exception {
        // Initialize the database
        meteogramaRepository.saveAndFlush(meteograma);

        int databaseSizeBeforeUpdate = meteogramaRepository.findAll().size();

        // Update the meteograma
        Meteograma updatedMeteograma = meteogramaRepository.findById(meteograma.getId()).get();
        // Disconnect from session so that the updates on updatedMeteograma are not directly saved in db
        em.detach(updatedMeteograma);
        updatedMeteograma
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .arquivo(UPDATED_ARQUIVO)
            .folder(UPDATED_FOLDER)
            .tipoarquivo(UPDATED_TIPOARQUIVO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        MeteogramaDTO meteogramaDTO = meteogramaMapper.toDto(updatedMeteograma);

        restMeteogramaMockMvc.perform(put("/api/meteogramas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(meteogramaDTO)))
            .andExpect(status().isOk());

        // Validate the Meteograma in the database
        List<Meteograma> meteogramaList = meteogramaRepository.findAll();
        assertThat(meteogramaList).hasSize(databaseSizeBeforeUpdate);
        Meteograma testMeteograma = meteogramaList.get(meteogramaList.size() - 1);
        assertThat(testMeteograma.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMeteograma.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testMeteograma.getArquivo()).isEqualTo(UPDATED_ARQUIVO);
        assertThat(testMeteograma.getFolder()).isEqualTo(UPDATED_FOLDER);
        assertThat(testMeteograma.getTipoarquivo()).isEqualTo(UPDATED_TIPOARQUIVO);
        assertThat(testMeteograma.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testMeteograma.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingMeteograma() throws Exception {
        int databaseSizeBeforeUpdate = meteogramaRepository.findAll().size();

        // Create the Meteograma
        MeteogramaDTO meteogramaDTO = meteogramaMapper.toDto(meteograma);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMeteogramaMockMvc.perform(put("/api/meteogramas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(meteogramaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Meteograma in the database
        List<Meteograma> meteogramaList = meteogramaRepository.findAll();
        assertThat(meteogramaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMeteograma() throws Exception {
        // Initialize the database
        meteogramaRepository.saveAndFlush(meteograma);

        int databaseSizeBeforeDelete = meteogramaRepository.findAll().size();

        // Delete the meteograma
        restMeteogramaMockMvc.perform(delete("/api/meteogramas/{id}", meteograma.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Meteograma> meteogramaList = meteogramaRepository.findAll();
        assertThat(meteogramaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
