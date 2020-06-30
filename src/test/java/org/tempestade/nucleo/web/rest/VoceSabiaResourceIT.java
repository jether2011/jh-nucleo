package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.VoceSabia;
import org.tempestade.nucleo.repository.VoceSabiaRepository;
import org.tempestade.nucleo.service.VoceSabiaService;
import org.tempestade.nucleo.service.dto.VoceSabiaDTO;
import org.tempestade.nucleo.service.mapper.VoceSabiaMapper;
import org.tempestade.nucleo.service.dto.VoceSabiaCriteria;
import org.tempestade.nucleo.service.VoceSabiaQueryService;

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
 * Integration tests for the {@link VoceSabiaResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class VoceSabiaResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_TEXTO = "AAAAAAAAAA";
    private static final String UPDATED_TEXTO = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGEM = "AAAAAAAAAA";
    private static final String UPDATED_IMAGEM = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private VoceSabiaRepository voceSabiaRepository;

    @Autowired
    private VoceSabiaMapper voceSabiaMapper;

    @Autowired
    private VoceSabiaService voceSabiaService;

    @Autowired
    private VoceSabiaQueryService voceSabiaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVoceSabiaMockMvc;

    private VoceSabia voceSabia;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VoceSabia createEntity(EntityManager em) {
        VoceSabia voceSabia = new VoceSabia()
            .name(DEFAULT_NAME)
            .descricao(DEFAULT_DESCRICAO)
            .titulo(DEFAULT_TITULO)
            .texto(DEFAULT_TEXTO)
            .imagem(DEFAULT_IMAGEM)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return voceSabia;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VoceSabia createUpdatedEntity(EntityManager em) {
        VoceSabia voceSabia = new VoceSabia()
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .titulo(UPDATED_TITULO)
            .texto(UPDATED_TEXTO)
            .imagem(UPDATED_IMAGEM)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return voceSabia;
    }

    @BeforeEach
    public void initTest() {
        voceSabia = createEntity(em);
    }

    @Test
    @Transactional
    public void createVoceSabia() throws Exception {
        int databaseSizeBeforeCreate = voceSabiaRepository.findAll().size();
        // Create the VoceSabia
        VoceSabiaDTO voceSabiaDTO = voceSabiaMapper.toDto(voceSabia);
        restVoceSabiaMockMvc.perform(post("/api/voce-sabias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(voceSabiaDTO)))
            .andExpect(status().isCreated());

        // Validate the VoceSabia in the database
        List<VoceSabia> voceSabiaList = voceSabiaRepository.findAll();
        assertThat(voceSabiaList).hasSize(databaseSizeBeforeCreate + 1);
        VoceSabia testVoceSabia = voceSabiaList.get(voceSabiaList.size() - 1);
        assertThat(testVoceSabia.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVoceSabia.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testVoceSabia.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testVoceSabia.getTexto()).isEqualTo(DEFAULT_TEXTO);
        assertThat(testVoceSabia.getImagem()).isEqualTo(DEFAULT_IMAGEM);
        assertThat(testVoceSabia.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testVoceSabia.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createVoceSabiaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = voceSabiaRepository.findAll().size();

        // Create the VoceSabia with an existing ID
        voceSabia.setId(1L);
        VoceSabiaDTO voceSabiaDTO = voceSabiaMapper.toDto(voceSabia);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVoceSabiaMockMvc.perform(post("/api/voce-sabias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(voceSabiaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VoceSabia in the database
        List<VoceSabia> voceSabiaList = voceSabiaRepository.findAll();
        assertThat(voceSabiaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = voceSabiaRepository.findAll().size();
        // set the field null
        voceSabia.setName(null);

        // Create the VoceSabia, which fails.
        VoceSabiaDTO voceSabiaDTO = voceSabiaMapper.toDto(voceSabia);


        restVoceSabiaMockMvc.perform(post("/api/voce-sabias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(voceSabiaDTO)))
            .andExpect(status().isBadRequest());

        List<VoceSabia> voceSabiaList = voceSabiaRepository.findAll();
        assertThat(voceSabiaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = voceSabiaRepository.findAll().size();
        // set the field null
        voceSabia.setDescricao(null);

        // Create the VoceSabia, which fails.
        VoceSabiaDTO voceSabiaDTO = voceSabiaMapper.toDto(voceSabia);


        restVoceSabiaMockMvc.perform(post("/api/voce-sabias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(voceSabiaDTO)))
            .andExpect(status().isBadRequest());

        List<VoceSabia> voceSabiaList = voceSabiaRepository.findAll();
        assertThat(voceSabiaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = voceSabiaRepository.findAll().size();
        // set the field null
        voceSabia.setCreated(null);

        // Create the VoceSabia, which fails.
        VoceSabiaDTO voceSabiaDTO = voceSabiaMapper.toDto(voceSabia);


        restVoceSabiaMockMvc.perform(post("/api/voce-sabias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(voceSabiaDTO)))
            .andExpect(status().isBadRequest());

        List<VoceSabia> voceSabiaList = voceSabiaRepository.findAll();
        assertThat(voceSabiaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVoceSabias() throws Exception {
        // Initialize the database
        voceSabiaRepository.saveAndFlush(voceSabia);

        // Get all the voceSabiaList
        restVoceSabiaMockMvc.perform(get("/api/voce-sabias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voceSabia.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].texto").value(hasItem(DEFAULT_TEXTO)))
            .andExpect(jsonPath("$.[*].imagem").value(hasItem(DEFAULT_IMAGEM)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getVoceSabia() throws Exception {
        // Initialize the database
        voceSabiaRepository.saveAndFlush(voceSabia);

        // Get the voceSabia
        restVoceSabiaMockMvc.perform(get("/api/voce-sabias/{id}", voceSabia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(voceSabia.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.texto").value(DEFAULT_TEXTO))
            .andExpect(jsonPath("$.imagem").value(DEFAULT_IMAGEM))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }


    @Test
    @Transactional
    public void getVoceSabiasByIdFiltering() throws Exception {
        // Initialize the database
        voceSabiaRepository.saveAndFlush(voceSabia);

        Long id = voceSabia.getId();

        defaultVoceSabiaShouldBeFound("id.equals=" + id);
        defaultVoceSabiaShouldNotBeFound("id.notEquals=" + id);

        defaultVoceSabiaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVoceSabiaShouldNotBeFound("id.greaterThan=" + id);

        defaultVoceSabiaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVoceSabiaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllVoceSabiasByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        voceSabiaRepository.saveAndFlush(voceSabia);

        // Get all the voceSabiaList where name equals to DEFAULT_NAME
        defaultVoceSabiaShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the voceSabiaList where name equals to UPDATED_NAME
        defaultVoceSabiaShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllVoceSabiasByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        voceSabiaRepository.saveAndFlush(voceSabia);

        // Get all the voceSabiaList where name not equals to DEFAULT_NAME
        defaultVoceSabiaShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the voceSabiaList where name not equals to UPDATED_NAME
        defaultVoceSabiaShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllVoceSabiasByNameIsInShouldWork() throws Exception {
        // Initialize the database
        voceSabiaRepository.saveAndFlush(voceSabia);

        // Get all the voceSabiaList where name in DEFAULT_NAME or UPDATED_NAME
        defaultVoceSabiaShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the voceSabiaList where name equals to UPDATED_NAME
        defaultVoceSabiaShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllVoceSabiasByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        voceSabiaRepository.saveAndFlush(voceSabia);

        // Get all the voceSabiaList where name is not null
        defaultVoceSabiaShouldBeFound("name.specified=true");

        // Get all the voceSabiaList where name is null
        defaultVoceSabiaShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllVoceSabiasByNameContainsSomething() throws Exception {
        // Initialize the database
        voceSabiaRepository.saveAndFlush(voceSabia);

        // Get all the voceSabiaList where name contains DEFAULT_NAME
        defaultVoceSabiaShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the voceSabiaList where name contains UPDATED_NAME
        defaultVoceSabiaShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllVoceSabiasByNameNotContainsSomething() throws Exception {
        // Initialize the database
        voceSabiaRepository.saveAndFlush(voceSabia);

        // Get all the voceSabiaList where name does not contain DEFAULT_NAME
        defaultVoceSabiaShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the voceSabiaList where name does not contain UPDATED_NAME
        defaultVoceSabiaShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllVoceSabiasByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        voceSabiaRepository.saveAndFlush(voceSabia);

        // Get all the voceSabiaList where descricao equals to DEFAULT_DESCRICAO
        defaultVoceSabiaShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the voceSabiaList where descricao equals to UPDATED_DESCRICAO
        defaultVoceSabiaShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllVoceSabiasByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        voceSabiaRepository.saveAndFlush(voceSabia);

        // Get all the voceSabiaList where descricao not equals to DEFAULT_DESCRICAO
        defaultVoceSabiaShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the voceSabiaList where descricao not equals to UPDATED_DESCRICAO
        defaultVoceSabiaShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllVoceSabiasByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        voceSabiaRepository.saveAndFlush(voceSabia);

        // Get all the voceSabiaList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultVoceSabiaShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the voceSabiaList where descricao equals to UPDATED_DESCRICAO
        defaultVoceSabiaShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllVoceSabiasByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        voceSabiaRepository.saveAndFlush(voceSabia);

        // Get all the voceSabiaList where descricao is not null
        defaultVoceSabiaShouldBeFound("descricao.specified=true");

        // Get all the voceSabiaList where descricao is null
        defaultVoceSabiaShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllVoceSabiasByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        voceSabiaRepository.saveAndFlush(voceSabia);

        // Get all the voceSabiaList where descricao contains DEFAULT_DESCRICAO
        defaultVoceSabiaShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the voceSabiaList where descricao contains UPDATED_DESCRICAO
        defaultVoceSabiaShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllVoceSabiasByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        voceSabiaRepository.saveAndFlush(voceSabia);

        // Get all the voceSabiaList where descricao does not contain DEFAULT_DESCRICAO
        defaultVoceSabiaShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the voceSabiaList where descricao does not contain UPDATED_DESCRICAO
        defaultVoceSabiaShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllVoceSabiasByTituloIsEqualToSomething() throws Exception {
        // Initialize the database
        voceSabiaRepository.saveAndFlush(voceSabia);

        // Get all the voceSabiaList where titulo equals to DEFAULT_TITULO
        defaultVoceSabiaShouldBeFound("titulo.equals=" + DEFAULT_TITULO);

        // Get all the voceSabiaList where titulo equals to UPDATED_TITULO
        defaultVoceSabiaShouldNotBeFound("titulo.equals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllVoceSabiasByTituloIsNotEqualToSomething() throws Exception {
        // Initialize the database
        voceSabiaRepository.saveAndFlush(voceSabia);

        // Get all the voceSabiaList where titulo not equals to DEFAULT_TITULO
        defaultVoceSabiaShouldNotBeFound("titulo.notEquals=" + DEFAULT_TITULO);

        // Get all the voceSabiaList where titulo not equals to UPDATED_TITULO
        defaultVoceSabiaShouldBeFound("titulo.notEquals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllVoceSabiasByTituloIsInShouldWork() throws Exception {
        // Initialize the database
        voceSabiaRepository.saveAndFlush(voceSabia);

        // Get all the voceSabiaList where titulo in DEFAULT_TITULO or UPDATED_TITULO
        defaultVoceSabiaShouldBeFound("titulo.in=" + DEFAULT_TITULO + "," + UPDATED_TITULO);

        // Get all the voceSabiaList where titulo equals to UPDATED_TITULO
        defaultVoceSabiaShouldNotBeFound("titulo.in=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllVoceSabiasByTituloIsNullOrNotNull() throws Exception {
        // Initialize the database
        voceSabiaRepository.saveAndFlush(voceSabia);

        // Get all the voceSabiaList where titulo is not null
        defaultVoceSabiaShouldBeFound("titulo.specified=true");

        // Get all the voceSabiaList where titulo is null
        defaultVoceSabiaShouldNotBeFound("titulo.specified=false");
    }
                @Test
    @Transactional
    public void getAllVoceSabiasByTituloContainsSomething() throws Exception {
        // Initialize the database
        voceSabiaRepository.saveAndFlush(voceSabia);

        // Get all the voceSabiaList where titulo contains DEFAULT_TITULO
        defaultVoceSabiaShouldBeFound("titulo.contains=" + DEFAULT_TITULO);

        // Get all the voceSabiaList where titulo contains UPDATED_TITULO
        defaultVoceSabiaShouldNotBeFound("titulo.contains=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllVoceSabiasByTituloNotContainsSomething() throws Exception {
        // Initialize the database
        voceSabiaRepository.saveAndFlush(voceSabia);

        // Get all the voceSabiaList where titulo does not contain DEFAULT_TITULO
        defaultVoceSabiaShouldNotBeFound("titulo.doesNotContain=" + DEFAULT_TITULO);

        // Get all the voceSabiaList where titulo does not contain UPDATED_TITULO
        defaultVoceSabiaShouldBeFound("titulo.doesNotContain=" + UPDATED_TITULO);
    }


    @Test
    @Transactional
    public void getAllVoceSabiasByTextoIsEqualToSomething() throws Exception {
        // Initialize the database
        voceSabiaRepository.saveAndFlush(voceSabia);

        // Get all the voceSabiaList where texto equals to DEFAULT_TEXTO
        defaultVoceSabiaShouldBeFound("texto.equals=" + DEFAULT_TEXTO);

        // Get all the voceSabiaList where texto equals to UPDATED_TEXTO
        defaultVoceSabiaShouldNotBeFound("texto.equals=" + UPDATED_TEXTO);
    }

    @Test
    @Transactional
    public void getAllVoceSabiasByTextoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        voceSabiaRepository.saveAndFlush(voceSabia);

        // Get all the voceSabiaList where texto not equals to DEFAULT_TEXTO
        defaultVoceSabiaShouldNotBeFound("texto.notEquals=" + DEFAULT_TEXTO);

        // Get all the voceSabiaList where texto not equals to UPDATED_TEXTO
        defaultVoceSabiaShouldBeFound("texto.notEquals=" + UPDATED_TEXTO);
    }

    @Test
    @Transactional
    public void getAllVoceSabiasByTextoIsInShouldWork() throws Exception {
        // Initialize the database
        voceSabiaRepository.saveAndFlush(voceSabia);

        // Get all the voceSabiaList where texto in DEFAULT_TEXTO or UPDATED_TEXTO
        defaultVoceSabiaShouldBeFound("texto.in=" + DEFAULT_TEXTO + "," + UPDATED_TEXTO);

        // Get all the voceSabiaList where texto equals to UPDATED_TEXTO
        defaultVoceSabiaShouldNotBeFound("texto.in=" + UPDATED_TEXTO);
    }

    @Test
    @Transactional
    public void getAllVoceSabiasByTextoIsNullOrNotNull() throws Exception {
        // Initialize the database
        voceSabiaRepository.saveAndFlush(voceSabia);

        // Get all the voceSabiaList where texto is not null
        defaultVoceSabiaShouldBeFound("texto.specified=true");

        // Get all the voceSabiaList where texto is null
        defaultVoceSabiaShouldNotBeFound("texto.specified=false");
    }
                @Test
    @Transactional
    public void getAllVoceSabiasByTextoContainsSomething() throws Exception {
        // Initialize the database
        voceSabiaRepository.saveAndFlush(voceSabia);

        // Get all the voceSabiaList where texto contains DEFAULT_TEXTO
        defaultVoceSabiaShouldBeFound("texto.contains=" + DEFAULT_TEXTO);

        // Get all the voceSabiaList where texto contains UPDATED_TEXTO
        defaultVoceSabiaShouldNotBeFound("texto.contains=" + UPDATED_TEXTO);
    }

    @Test
    @Transactional
    public void getAllVoceSabiasByTextoNotContainsSomething() throws Exception {
        // Initialize the database
        voceSabiaRepository.saveAndFlush(voceSabia);

        // Get all the voceSabiaList where texto does not contain DEFAULT_TEXTO
        defaultVoceSabiaShouldNotBeFound("texto.doesNotContain=" + DEFAULT_TEXTO);

        // Get all the voceSabiaList where texto does not contain UPDATED_TEXTO
        defaultVoceSabiaShouldBeFound("texto.doesNotContain=" + UPDATED_TEXTO);
    }


    @Test
    @Transactional
    public void getAllVoceSabiasByImagemIsEqualToSomething() throws Exception {
        // Initialize the database
        voceSabiaRepository.saveAndFlush(voceSabia);

        // Get all the voceSabiaList where imagem equals to DEFAULT_IMAGEM
        defaultVoceSabiaShouldBeFound("imagem.equals=" + DEFAULT_IMAGEM);

        // Get all the voceSabiaList where imagem equals to UPDATED_IMAGEM
        defaultVoceSabiaShouldNotBeFound("imagem.equals=" + UPDATED_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllVoceSabiasByImagemIsNotEqualToSomething() throws Exception {
        // Initialize the database
        voceSabiaRepository.saveAndFlush(voceSabia);

        // Get all the voceSabiaList where imagem not equals to DEFAULT_IMAGEM
        defaultVoceSabiaShouldNotBeFound("imagem.notEquals=" + DEFAULT_IMAGEM);

        // Get all the voceSabiaList where imagem not equals to UPDATED_IMAGEM
        defaultVoceSabiaShouldBeFound("imagem.notEquals=" + UPDATED_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllVoceSabiasByImagemIsInShouldWork() throws Exception {
        // Initialize the database
        voceSabiaRepository.saveAndFlush(voceSabia);

        // Get all the voceSabiaList where imagem in DEFAULT_IMAGEM or UPDATED_IMAGEM
        defaultVoceSabiaShouldBeFound("imagem.in=" + DEFAULT_IMAGEM + "," + UPDATED_IMAGEM);

        // Get all the voceSabiaList where imagem equals to UPDATED_IMAGEM
        defaultVoceSabiaShouldNotBeFound("imagem.in=" + UPDATED_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllVoceSabiasByImagemIsNullOrNotNull() throws Exception {
        // Initialize the database
        voceSabiaRepository.saveAndFlush(voceSabia);

        // Get all the voceSabiaList where imagem is not null
        defaultVoceSabiaShouldBeFound("imagem.specified=true");

        // Get all the voceSabiaList where imagem is null
        defaultVoceSabiaShouldNotBeFound("imagem.specified=false");
    }
                @Test
    @Transactional
    public void getAllVoceSabiasByImagemContainsSomething() throws Exception {
        // Initialize the database
        voceSabiaRepository.saveAndFlush(voceSabia);

        // Get all the voceSabiaList where imagem contains DEFAULT_IMAGEM
        defaultVoceSabiaShouldBeFound("imagem.contains=" + DEFAULT_IMAGEM);

        // Get all the voceSabiaList where imagem contains UPDATED_IMAGEM
        defaultVoceSabiaShouldNotBeFound("imagem.contains=" + UPDATED_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllVoceSabiasByImagemNotContainsSomething() throws Exception {
        // Initialize the database
        voceSabiaRepository.saveAndFlush(voceSabia);

        // Get all the voceSabiaList where imagem does not contain DEFAULT_IMAGEM
        defaultVoceSabiaShouldNotBeFound("imagem.doesNotContain=" + DEFAULT_IMAGEM);

        // Get all the voceSabiaList where imagem does not contain UPDATED_IMAGEM
        defaultVoceSabiaShouldBeFound("imagem.doesNotContain=" + UPDATED_IMAGEM);
    }


    @Test
    @Transactional
    public void getAllVoceSabiasByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        voceSabiaRepository.saveAndFlush(voceSabia);

        // Get all the voceSabiaList where created equals to DEFAULT_CREATED
        defaultVoceSabiaShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the voceSabiaList where created equals to UPDATED_CREATED
        defaultVoceSabiaShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllVoceSabiasByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        voceSabiaRepository.saveAndFlush(voceSabia);

        // Get all the voceSabiaList where created not equals to DEFAULT_CREATED
        defaultVoceSabiaShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the voceSabiaList where created not equals to UPDATED_CREATED
        defaultVoceSabiaShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllVoceSabiasByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        voceSabiaRepository.saveAndFlush(voceSabia);

        // Get all the voceSabiaList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultVoceSabiaShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the voceSabiaList where created equals to UPDATED_CREATED
        defaultVoceSabiaShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllVoceSabiasByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        voceSabiaRepository.saveAndFlush(voceSabia);

        // Get all the voceSabiaList where created is not null
        defaultVoceSabiaShouldBeFound("created.specified=true");

        // Get all the voceSabiaList where created is null
        defaultVoceSabiaShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllVoceSabiasByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        voceSabiaRepository.saveAndFlush(voceSabia);

        // Get all the voceSabiaList where updated equals to DEFAULT_UPDATED
        defaultVoceSabiaShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the voceSabiaList where updated equals to UPDATED_UPDATED
        defaultVoceSabiaShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllVoceSabiasByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        voceSabiaRepository.saveAndFlush(voceSabia);

        // Get all the voceSabiaList where updated not equals to DEFAULT_UPDATED
        defaultVoceSabiaShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the voceSabiaList where updated not equals to UPDATED_UPDATED
        defaultVoceSabiaShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllVoceSabiasByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        voceSabiaRepository.saveAndFlush(voceSabia);

        // Get all the voceSabiaList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultVoceSabiaShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the voceSabiaList where updated equals to UPDATED_UPDATED
        defaultVoceSabiaShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllVoceSabiasByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        voceSabiaRepository.saveAndFlush(voceSabia);

        // Get all the voceSabiaList where updated is not null
        defaultVoceSabiaShouldBeFound("updated.specified=true");

        // Get all the voceSabiaList where updated is null
        defaultVoceSabiaShouldNotBeFound("updated.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVoceSabiaShouldBeFound(String filter) throws Exception {
        restVoceSabiaMockMvc.perform(get("/api/voce-sabias?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voceSabia.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].texto").value(hasItem(DEFAULT_TEXTO)))
            .andExpect(jsonPath("$.[*].imagem").value(hasItem(DEFAULT_IMAGEM)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restVoceSabiaMockMvc.perform(get("/api/voce-sabias/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVoceSabiaShouldNotBeFound(String filter) throws Exception {
        restVoceSabiaMockMvc.perform(get("/api/voce-sabias?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVoceSabiaMockMvc.perform(get("/api/voce-sabias/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingVoceSabia() throws Exception {
        // Get the voceSabia
        restVoceSabiaMockMvc.perform(get("/api/voce-sabias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVoceSabia() throws Exception {
        // Initialize the database
        voceSabiaRepository.saveAndFlush(voceSabia);

        int databaseSizeBeforeUpdate = voceSabiaRepository.findAll().size();

        // Update the voceSabia
        VoceSabia updatedVoceSabia = voceSabiaRepository.findById(voceSabia.getId()).get();
        // Disconnect from session so that the updates on updatedVoceSabia are not directly saved in db
        em.detach(updatedVoceSabia);
        updatedVoceSabia
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .titulo(UPDATED_TITULO)
            .texto(UPDATED_TEXTO)
            .imagem(UPDATED_IMAGEM)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        VoceSabiaDTO voceSabiaDTO = voceSabiaMapper.toDto(updatedVoceSabia);

        restVoceSabiaMockMvc.perform(put("/api/voce-sabias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(voceSabiaDTO)))
            .andExpect(status().isOk());

        // Validate the VoceSabia in the database
        List<VoceSabia> voceSabiaList = voceSabiaRepository.findAll();
        assertThat(voceSabiaList).hasSize(databaseSizeBeforeUpdate);
        VoceSabia testVoceSabia = voceSabiaList.get(voceSabiaList.size() - 1);
        assertThat(testVoceSabia.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVoceSabia.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testVoceSabia.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testVoceSabia.getTexto()).isEqualTo(UPDATED_TEXTO);
        assertThat(testVoceSabia.getImagem()).isEqualTo(UPDATED_IMAGEM);
        assertThat(testVoceSabia.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testVoceSabia.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingVoceSabia() throws Exception {
        int databaseSizeBeforeUpdate = voceSabiaRepository.findAll().size();

        // Create the VoceSabia
        VoceSabiaDTO voceSabiaDTO = voceSabiaMapper.toDto(voceSabia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoceSabiaMockMvc.perform(put("/api/voce-sabias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(voceSabiaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VoceSabia in the database
        List<VoceSabia> voceSabiaList = voceSabiaRepository.findAll();
        assertThat(voceSabiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVoceSabia() throws Exception {
        // Initialize the database
        voceSabiaRepository.saveAndFlush(voceSabia);

        int databaseSizeBeforeDelete = voceSabiaRepository.findAll().size();

        // Delete the voceSabia
        restVoceSabiaMockMvc.perform(delete("/api/voce-sabias/{id}", voceSabia.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VoceSabia> voceSabiaList = voceSabiaRepository.findAll();
        assertThat(voceSabiaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
