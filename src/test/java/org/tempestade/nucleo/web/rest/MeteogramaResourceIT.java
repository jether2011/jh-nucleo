package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.Meteograma;
import org.tempestade.nucleo.domain.Plano;
import org.tempestade.nucleo.repository.MeteogramaRepository;
import org.tempestade.nucleo.service.MeteogramaService;
import org.tempestade.nucleo.service.dto.MeteogramaDTO;
import org.tempestade.nucleo.service.mapper.MeteogramaMapper;
import org.tempestade.nucleo.service.dto.MeteogramaCriteria;
import org.tempestade.nucleo.service.MeteogramaQueryService;

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
    private MeteogramaQueryService meteogramaQueryService;

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
    public void getMeteogramasByIdFiltering() throws Exception {
        // Initialize the database
        meteogramaRepository.saveAndFlush(meteograma);

        Long id = meteograma.getId();

        defaultMeteogramaShouldBeFound("id.equals=" + id);
        defaultMeteogramaShouldNotBeFound("id.notEquals=" + id);

        defaultMeteogramaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMeteogramaShouldNotBeFound("id.greaterThan=" + id);

        defaultMeteogramaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMeteogramaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMeteogramasByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        meteogramaRepository.saveAndFlush(meteograma);

        // Get all the meteogramaList where name equals to DEFAULT_NAME
        defaultMeteogramaShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the meteogramaList where name equals to UPDATED_NAME
        defaultMeteogramaShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMeteogramasByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        meteogramaRepository.saveAndFlush(meteograma);

        // Get all the meteogramaList where name not equals to DEFAULT_NAME
        defaultMeteogramaShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the meteogramaList where name not equals to UPDATED_NAME
        defaultMeteogramaShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMeteogramasByNameIsInShouldWork() throws Exception {
        // Initialize the database
        meteogramaRepository.saveAndFlush(meteograma);

        // Get all the meteogramaList where name in DEFAULT_NAME or UPDATED_NAME
        defaultMeteogramaShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the meteogramaList where name equals to UPDATED_NAME
        defaultMeteogramaShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMeteogramasByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        meteogramaRepository.saveAndFlush(meteograma);

        // Get all the meteogramaList where name is not null
        defaultMeteogramaShouldBeFound("name.specified=true");

        // Get all the meteogramaList where name is null
        defaultMeteogramaShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllMeteogramasByNameContainsSomething() throws Exception {
        // Initialize the database
        meteogramaRepository.saveAndFlush(meteograma);

        // Get all the meteogramaList where name contains DEFAULT_NAME
        defaultMeteogramaShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the meteogramaList where name contains UPDATED_NAME
        defaultMeteogramaShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMeteogramasByNameNotContainsSomething() throws Exception {
        // Initialize the database
        meteogramaRepository.saveAndFlush(meteograma);

        // Get all the meteogramaList where name does not contain DEFAULT_NAME
        defaultMeteogramaShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the meteogramaList where name does not contain UPDATED_NAME
        defaultMeteogramaShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllMeteogramasByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        meteogramaRepository.saveAndFlush(meteograma);

        // Get all the meteogramaList where descricao equals to DEFAULT_DESCRICAO
        defaultMeteogramaShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the meteogramaList where descricao equals to UPDATED_DESCRICAO
        defaultMeteogramaShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllMeteogramasByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        meteogramaRepository.saveAndFlush(meteograma);

        // Get all the meteogramaList where descricao not equals to DEFAULT_DESCRICAO
        defaultMeteogramaShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the meteogramaList where descricao not equals to UPDATED_DESCRICAO
        defaultMeteogramaShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllMeteogramasByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        meteogramaRepository.saveAndFlush(meteograma);

        // Get all the meteogramaList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultMeteogramaShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the meteogramaList where descricao equals to UPDATED_DESCRICAO
        defaultMeteogramaShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllMeteogramasByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        meteogramaRepository.saveAndFlush(meteograma);

        // Get all the meteogramaList where descricao is not null
        defaultMeteogramaShouldBeFound("descricao.specified=true");

        // Get all the meteogramaList where descricao is null
        defaultMeteogramaShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllMeteogramasByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        meteogramaRepository.saveAndFlush(meteograma);

        // Get all the meteogramaList where descricao contains DEFAULT_DESCRICAO
        defaultMeteogramaShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the meteogramaList where descricao contains UPDATED_DESCRICAO
        defaultMeteogramaShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllMeteogramasByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        meteogramaRepository.saveAndFlush(meteograma);

        // Get all the meteogramaList where descricao does not contain DEFAULT_DESCRICAO
        defaultMeteogramaShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the meteogramaList where descricao does not contain UPDATED_DESCRICAO
        defaultMeteogramaShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllMeteogramasByArquivoIsEqualToSomething() throws Exception {
        // Initialize the database
        meteogramaRepository.saveAndFlush(meteograma);

        // Get all the meteogramaList where arquivo equals to DEFAULT_ARQUIVO
        defaultMeteogramaShouldBeFound("arquivo.equals=" + DEFAULT_ARQUIVO);

        // Get all the meteogramaList where arquivo equals to UPDATED_ARQUIVO
        defaultMeteogramaShouldNotBeFound("arquivo.equals=" + UPDATED_ARQUIVO);
    }

    @Test
    @Transactional
    public void getAllMeteogramasByArquivoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        meteogramaRepository.saveAndFlush(meteograma);

        // Get all the meteogramaList where arquivo not equals to DEFAULT_ARQUIVO
        defaultMeteogramaShouldNotBeFound("arquivo.notEquals=" + DEFAULT_ARQUIVO);

        // Get all the meteogramaList where arquivo not equals to UPDATED_ARQUIVO
        defaultMeteogramaShouldBeFound("arquivo.notEquals=" + UPDATED_ARQUIVO);
    }

    @Test
    @Transactional
    public void getAllMeteogramasByArquivoIsInShouldWork() throws Exception {
        // Initialize the database
        meteogramaRepository.saveAndFlush(meteograma);

        // Get all the meteogramaList where arquivo in DEFAULT_ARQUIVO or UPDATED_ARQUIVO
        defaultMeteogramaShouldBeFound("arquivo.in=" + DEFAULT_ARQUIVO + "," + UPDATED_ARQUIVO);

        // Get all the meteogramaList where arquivo equals to UPDATED_ARQUIVO
        defaultMeteogramaShouldNotBeFound("arquivo.in=" + UPDATED_ARQUIVO);
    }

    @Test
    @Transactional
    public void getAllMeteogramasByArquivoIsNullOrNotNull() throws Exception {
        // Initialize the database
        meteogramaRepository.saveAndFlush(meteograma);

        // Get all the meteogramaList where arquivo is not null
        defaultMeteogramaShouldBeFound("arquivo.specified=true");

        // Get all the meteogramaList where arquivo is null
        defaultMeteogramaShouldNotBeFound("arquivo.specified=false");
    }
                @Test
    @Transactional
    public void getAllMeteogramasByArquivoContainsSomething() throws Exception {
        // Initialize the database
        meteogramaRepository.saveAndFlush(meteograma);

        // Get all the meteogramaList where arquivo contains DEFAULT_ARQUIVO
        defaultMeteogramaShouldBeFound("arquivo.contains=" + DEFAULT_ARQUIVO);

        // Get all the meteogramaList where arquivo contains UPDATED_ARQUIVO
        defaultMeteogramaShouldNotBeFound("arquivo.contains=" + UPDATED_ARQUIVO);
    }

    @Test
    @Transactional
    public void getAllMeteogramasByArquivoNotContainsSomething() throws Exception {
        // Initialize the database
        meteogramaRepository.saveAndFlush(meteograma);

        // Get all the meteogramaList where arquivo does not contain DEFAULT_ARQUIVO
        defaultMeteogramaShouldNotBeFound("arquivo.doesNotContain=" + DEFAULT_ARQUIVO);

        // Get all the meteogramaList where arquivo does not contain UPDATED_ARQUIVO
        defaultMeteogramaShouldBeFound("arquivo.doesNotContain=" + UPDATED_ARQUIVO);
    }


    @Test
    @Transactional
    public void getAllMeteogramasByFolderIsEqualToSomething() throws Exception {
        // Initialize the database
        meteogramaRepository.saveAndFlush(meteograma);

        // Get all the meteogramaList where folder equals to DEFAULT_FOLDER
        defaultMeteogramaShouldBeFound("folder.equals=" + DEFAULT_FOLDER);

        // Get all the meteogramaList where folder equals to UPDATED_FOLDER
        defaultMeteogramaShouldNotBeFound("folder.equals=" + UPDATED_FOLDER);
    }

    @Test
    @Transactional
    public void getAllMeteogramasByFolderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        meteogramaRepository.saveAndFlush(meteograma);

        // Get all the meteogramaList where folder not equals to DEFAULT_FOLDER
        defaultMeteogramaShouldNotBeFound("folder.notEquals=" + DEFAULT_FOLDER);

        // Get all the meteogramaList where folder not equals to UPDATED_FOLDER
        defaultMeteogramaShouldBeFound("folder.notEquals=" + UPDATED_FOLDER);
    }

    @Test
    @Transactional
    public void getAllMeteogramasByFolderIsInShouldWork() throws Exception {
        // Initialize the database
        meteogramaRepository.saveAndFlush(meteograma);

        // Get all the meteogramaList where folder in DEFAULT_FOLDER or UPDATED_FOLDER
        defaultMeteogramaShouldBeFound("folder.in=" + DEFAULT_FOLDER + "," + UPDATED_FOLDER);

        // Get all the meteogramaList where folder equals to UPDATED_FOLDER
        defaultMeteogramaShouldNotBeFound("folder.in=" + UPDATED_FOLDER);
    }

    @Test
    @Transactional
    public void getAllMeteogramasByFolderIsNullOrNotNull() throws Exception {
        // Initialize the database
        meteogramaRepository.saveAndFlush(meteograma);

        // Get all the meteogramaList where folder is not null
        defaultMeteogramaShouldBeFound("folder.specified=true");

        // Get all the meteogramaList where folder is null
        defaultMeteogramaShouldNotBeFound("folder.specified=false");
    }
                @Test
    @Transactional
    public void getAllMeteogramasByFolderContainsSomething() throws Exception {
        // Initialize the database
        meteogramaRepository.saveAndFlush(meteograma);

        // Get all the meteogramaList where folder contains DEFAULT_FOLDER
        defaultMeteogramaShouldBeFound("folder.contains=" + DEFAULT_FOLDER);

        // Get all the meteogramaList where folder contains UPDATED_FOLDER
        defaultMeteogramaShouldNotBeFound("folder.contains=" + UPDATED_FOLDER);
    }

    @Test
    @Transactional
    public void getAllMeteogramasByFolderNotContainsSomething() throws Exception {
        // Initialize the database
        meteogramaRepository.saveAndFlush(meteograma);

        // Get all the meteogramaList where folder does not contain DEFAULT_FOLDER
        defaultMeteogramaShouldNotBeFound("folder.doesNotContain=" + DEFAULT_FOLDER);

        // Get all the meteogramaList where folder does not contain UPDATED_FOLDER
        defaultMeteogramaShouldBeFound("folder.doesNotContain=" + UPDATED_FOLDER);
    }


    @Test
    @Transactional
    public void getAllMeteogramasByTipoarquivoIsEqualToSomething() throws Exception {
        // Initialize the database
        meteogramaRepository.saveAndFlush(meteograma);

        // Get all the meteogramaList where tipoarquivo equals to DEFAULT_TIPOARQUIVO
        defaultMeteogramaShouldBeFound("tipoarquivo.equals=" + DEFAULT_TIPOARQUIVO);

        // Get all the meteogramaList where tipoarquivo equals to UPDATED_TIPOARQUIVO
        defaultMeteogramaShouldNotBeFound("tipoarquivo.equals=" + UPDATED_TIPOARQUIVO);
    }

    @Test
    @Transactional
    public void getAllMeteogramasByTipoarquivoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        meteogramaRepository.saveAndFlush(meteograma);

        // Get all the meteogramaList where tipoarquivo not equals to DEFAULT_TIPOARQUIVO
        defaultMeteogramaShouldNotBeFound("tipoarquivo.notEquals=" + DEFAULT_TIPOARQUIVO);

        // Get all the meteogramaList where tipoarquivo not equals to UPDATED_TIPOARQUIVO
        defaultMeteogramaShouldBeFound("tipoarquivo.notEquals=" + UPDATED_TIPOARQUIVO);
    }

    @Test
    @Transactional
    public void getAllMeteogramasByTipoarquivoIsInShouldWork() throws Exception {
        // Initialize the database
        meteogramaRepository.saveAndFlush(meteograma);

        // Get all the meteogramaList where tipoarquivo in DEFAULT_TIPOARQUIVO or UPDATED_TIPOARQUIVO
        defaultMeteogramaShouldBeFound("tipoarquivo.in=" + DEFAULT_TIPOARQUIVO + "," + UPDATED_TIPOARQUIVO);

        // Get all the meteogramaList where tipoarquivo equals to UPDATED_TIPOARQUIVO
        defaultMeteogramaShouldNotBeFound("tipoarquivo.in=" + UPDATED_TIPOARQUIVO);
    }

    @Test
    @Transactional
    public void getAllMeteogramasByTipoarquivoIsNullOrNotNull() throws Exception {
        // Initialize the database
        meteogramaRepository.saveAndFlush(meteograma);

        // Get all the meteogramaList where tipoarquivo is not null
        defaultMeteogramaShouldBeFound("tipoarquivo.specified=true");

        // Get all the meteogramaList where tipoarquivo is null
        defaultMeteogramaShouldNotBeFound("tipoarquivo.specified=false");
    }
                @Test
    @Transactional
    public void getAllMeteogramasByTipoarquivoContainsSomething() throws Exception {
        // Initialize the database
        meteogramaRepository.saveAndFlush(meteograma);

        // Get all the meteogramaList where tipoarquivo contains DEFAULT_TIPOARQUIVO
        defaultMeteogramaShouldBeFound("tipoarquivo.contains=" + DEFAULT_TIPOARQUIVO);

        // Get all the meteogramaList where tipoarquivo contains UPDATED_TIPOARQUIVO
        defaultMeteogramaShouldNotBeFound("tipoarquivo.contains=" + UPDATED_TIPOARQUIVO);
    }

    @Test
    @Transactional
    public void getAllMeteogramasByTipoarquivoNotContainsSomething() throws Exception {
        // Initialize the database
        meteogramaRepository.saveAndFlush(meteograma);

        // Get all the meteogramaList where tipoarquivo does not contain DEFAULT_TIPOARQUIVO
        defaultMeteogramaShouldNotBeFound("tipoarquivo.doesNotContain=" + DEFAULT_TIPOARQUIVO);

        // Get all the meteogramaList where tipoarquivo does not contain UPDATED_TIPOARQUIVO
        defaultMeteogramaShouldBeFound("tipoarquivo.doesNotContain=" + UPDATED_TIPOARQUIVO);
    }


    @Test
    @Transactional
    public void getAllMeteogramasByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        meteogramaRepository.saveAndFlush(meteograma);

        // Get all the meteogramaList where created equals to DEFAULT_CREATED
        defaultMeteogramaShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the meteogramaList where created equals to UPDATED_CREATED
        defaultMeteogramaShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllMeteogramasByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        meteogramaRepository.saveAndFlush(meteograma);

        // Get all the meteogramaList where created not equals to DEFAULT_CREATED
        defaultMeteogramaShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the meteogramaList where created not equals to UPDATED_CREATED
        defaultMeteogramaShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllMeteogramasByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        meteogramaRepository.saveAndFlush(meteograma);

        // Get all the meteogramaList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultMeteogramaShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the meteogramaList where created equals to UPDATED_CREATED
        defaultMeteogramaShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllMeteogramasByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        meteogramaRepository.saveAndFlush(meteograma);

        // Get all the meteogramaList where created is not null
        defaultMeteogramaShouldBeFound("created.specified=true");

        // Get all the meteogramaList where created is null
        defaultMeteogramaShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllMeteogramasByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        meteogramaRepository.saveAndFlush(meteograma);

        // Get all the meteogramaList where updated equals to DEFAULT_UPDATED
        defaultMeteogramaShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the meteogramaList where updated equals to UPDATED_UPDATED
        defaultMeteogramaShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllMeteogramasByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        meteogramaRepository.saveAndFlush(meteograma);

        // Get all the meteogramaList where updated not equals to DEFAULT_UPDATED
        defaultMeteogramaShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the meteogramaList where updated not equals to UPDATED_UPDATED
        defaultMeteogramaShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllMeteogramasByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        meteogramaRepository.saveAndFlush(meteograma);

        // Get all the meteogramaList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultMeteogramaShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the meteogramaList where updated equals to UPDATED_UPDATED
        defaultMeteogramaShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllMeteogramasByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        meteogramaRepository.saveAndFlush(meteograma);

        // Get all the meteogramaList where updated is not null
        defaultMeteogramaShouldBeFound("updated.specified=true");

        // Get all the meteogramaList where updated is null
        defaultMeteogramaShouldNotBeFound("updated.specified=false");
    }

    @Test
    @Transactional
    public void getAllMeteogramasByPlanoIsEqualToSomething() throws Exception {
        // Initialize the database
        meteogramaRepository.saveAndFlush(meteograma);
        Plano plano = PlanoResourceIT.createEntity(em);
        em.persist(plano);
        em.flush();
        meteograma.setPlano(plano);
        meteogramaRepository.saveAndFlush(meteograma);
        Long planoId = plano.getId();

        // Get all the meteogramaList where plano equals to planoId
        defaultMeteogramaShouldBeFound("planoId.equals=" + planoId);

        // Get all the meteogramaList where plano equals to planoId + 1
        defaultMeteogramaShouldNotBeFound("planoId.equals=" + (planoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMeteogramaShouldBeFound(String filter) throws Exception {
        restMeteogramaMockMvc.perform(get("/api/meteogramas?sort=id,desc&" + filter))
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

        // Check, that the count call also returns 1
        restMeteogramaMockMvc.perform(get("/api/meteogramas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMeteogramaShouldNotBeFound(String filter) throws Exception {
        restMeteogramaMockMvc.perform(get("/api/meteogramas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMeteogramaMockMvc.perform(get("/api/meteogramas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
