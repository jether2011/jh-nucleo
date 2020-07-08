package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.Previsao;
import org.tempestade.nucleo.repository.PrevisaoRepository;
import org.tempestade.nucleo.service.PrevisaoService;
import org.tempestade.nucleo.service.dto.PrevisaoDTO;
import org.tempestade.nucleo.service.mapper.PrevisaoMapper;

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
 * Integration tests for the {@link PrevisaoResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PrevisaoResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_TEXTO_NORTE = "AAAAAAAAAA";
    private static final String UPDATED_TEXTO_NORTE = "BBBBBBBBBB";

    private static final String DEFAULT_TEXTO_NORTE_IMAGEM = "AAAAAAAAAA";
    private static final String UPDATED_TEXTO_NORTE_IMAGEM = "BBBBBBBBBB";

    private static final String DEFAULT_TEXTO_NORDESTE = "AAAAAAAAAA";
    private static final String UPDATED_TEXTO_NORDESTE = "BBBBBBBBBB";

    private static final String DEFAULT_TEXTO_NORDESTE_IMAGEM = "AAAAAAAAAA";
    private static final String UPDATED_TEXTO_NORDESTE_IMAGEM = "BBBBBBBBBB";

    private static final String DEFAULT_TEXTO_SUL = "AAAAAAAAAA";
    private static final String UPDATED_TEXTO_SUL = "BBBBBBBBBB";

    private static final String DEFAULT_TEXTO_SUL_IMAGEM = "AAAAAAAAAA";
    private static final String UPDATED_TEXTO_SUL_IMAGEM = "BBBBBBBBBB";

    private static final String DEFAULT_TEXTO_SUDESTE = "AAAAAAAAAA";
    private static final String UPDATED_TEXTO_SUDESTE = "BBBBBBBBBB";

    private static final String DEFAULT_TEXTO_SUDESTE_IMAGEM = "AAAAAAAAAA";
    private static final String UPDATED_TEXTO_SUDESTE_IMAGEM = "BBBBBBBBBB";

    private static final String DEFAULT_TEXTO_CENTRO_OESTE = "AAAAAAAAAA";
    private static final String UPDATED_TEXTO_CENTRO_OESTE = "BBBBBBBBBB";

    private static final String DEFAULT_TEXTO_CENTRO_OESTE_IMAGEM = "AAAAAAAAAA";
    private static final String UPDATED_TEXTO_CENTRO_OESTE_IMAGEM = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENVIADO = false;
    private static final Boolean UPDATED_ENVIADO = true;

    private static final String DEFAULT_TEXTO_BRASIL = "AAAAAAAAAA";
    private static final String UPDATED_TEXTO_BRASIL = "BBBBBBBBBB";

    private static final String DEFAULT_TEXTO_BRASIL_IMAGEM = "AAAAAAAAAA";
    private static final String UPDATED_TEXTO_BRASIL_IMAGEM = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PrevisaoRepository previsaoRepository;

    @Autowired
    private PrevisaoMapper previsaoMapper;

    @Autowired
    private PrevisaoService previsaoService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPrevisaoMockMvc;

    private Previsao previsao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Previsao createEntity(EntityManager em) {
        Previsao previsao = new Previsao()
            .name(DEFAULT_NAME)
            .descricao(DEFAULT_DESCRICAO)
            .textoNorte(DEFAULT_TEXTO_NORTE)
            .textoNorteImagem(DEFAULT_TEXTO_NORTE_IMAGEM)
            .textoNordeste(DEFAULT_TEXTO_NORDESTE)
            .textoNordesteImagem(DEFAULT_TEXTO_NORDESTE_IMAGEM)
            .textoSul(DEFAULT_TEXTO_SUL)
            .textoSulImagem(DEFAULT_TEXTO_SUL_IMAGEM)
            .textoSudeste(DEFAULT_TEXTO_SUDESTE)
            .textoSudesteImagem(DEFAULT_TEXTO_SUDESTE_IMAGEM)
            .textoCentroOeste(DEFAULT_TEXTO_CENTRO_OESTE)
            .textoCentroOesteImagem(DEFAULT_TEXTO_CENTRO_OESTE_IMAGEM)
            .enviado(DEFAULT_ENVIADO)
            .textoBrasil(DEFAULT_TEXTO_BRASIL)
            .textoBrasilImagem(DEFAULT_TEXTO_BRASIL_IMAGEM)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return previsao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Previsao createUpdatedEntity(EntityManager em) {
        Previsao previsao = new Previsao()
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .textoNorte(UPDATED_TEXTO_NORTE)
            .textoNorteImagem(UPDATED_TEXTO_NORTE_IMAGEM)
            .textoNordeste(UPDATED_TEXTO_NORDESTE)
            .textoNordesteImagem(UPDATED_TEXTO_NORDESTE_IMAGEM)
            .textoSul(UPDATED_TEXTO_SUL)
            .textoSulImagem(UPDATED_TEXTO_SUL_IMAGEM)
            .textoSudeste(UPDATED_TEXTO_SUDESTE)
            .textoSudesteImagem(UPDATED_TEXTO_SUDESTE_IMAGEM)
            .textoCentroOeste(UPDATED_TEXTO_CENTRO_OESTE)
            .textoCentroOesteImagem(UPDATED_TEXTO_CENTRO_OESTE_IMAGEM)
            .enviado(UPDATED_ENVIADO)
            .textoBrasil(UPDATED_TEXTO_BRASIL)
            .textoBrasilImagem(UPDATED_TEXTO_BRASIL_IMAGEM)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return previsao;
    }

    @BeforeEach
    public void initTest() {
        previsao = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrevisao() throws Exception {
        int databaseSizeBeforeCreate = previsaoRepository.findAll().size();
        // Create the Previsao
        PrevisaoDTO previsaoDTO = previsaoMapper.toDto(previsao);
        restPrevisaoMockMvc.perform(post("/api/previsaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(previsaoDTO)))
            .andExpect(status().isCreated());

        // Validate the Previsao in the database
        List<Previsao> previsaoList = previsaoRepository.findAll();
        assertThat(previsaoList).hasSize(databaseSizeBeforeCreate + 1);
        Previsao testPrevisao = previsaoList.get(previsaoList.size() - 1);
        assertThat(testPrevisao.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPrevisao.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testPrevisao.getTextoNorte()).isEqualTo(DEFAULT_TEXTO_NORTE);
        assertThat(testPrevisao.getTextoNorteImagem()).isEqualTo(DEFAULT_TEXTO_NORTE_IMAGEM);
        assertThat(testPrevisao.getTextoNordeste()).isEqualTo(DEFAULT_TEXTO_NORDESTE);
        assertThat(testPrevisao.getTextoNordesteImagem()).isEqualTo(DEFAULT_TEXTO_NORDESTE_IMAGEM);
        assertThat(testPrevisao.getTextoSul()).isEqualTo(DEFAULT_TEXTO_SUL);
        assertThat(testPrevisao.getTextoSulImagem()).isEqualTo(DEFAULT_TEXTO_SUL_IMAGEM);
        assertThat(testPrevisao.getTextoSudeste()).isEqualTo(DEFAULT_TEXTO_SUDESTE);
        assertThat(testPrevisao.getTextoSudesteImagem()).isEqualTo(DEFAULT_TEXTO_SUDESTE_IMAGEM);
        assertThat(testPrevisao.getTextoCentroOeste()).isEqualTo(DEFAULT_TEXTO_CENTRO_OESTE);
        assertThat(testPrevisao.getTextoCentroOesteImagem()).isEqualTo(DEFAULT_TEXTO_CENTRO_OESTE_IMAGEM);
        assertThat(testPrevisao.isEnviado()).isEqualTo(DEFAULT_ENVIADO);
        assertThat(testPrevisao.getTextoBrasil()).isEqualTo(DEFAULT_TEXTO_BRASIL);
        assertThat(testPrevisao.getTextoBrasilImagem()).isEqualTo(DEFAULT_TEXTO_BRASIL_IMAGEM);
        assertThat(testPrevisao.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testPrevisao.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createPrevisaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = previsaoRepository.findAll().size();

        // Create the Previsao with an existing ID
        previsao.setId(1L);
        PrevisaoDTO previsaoDTO = previsaoMapper.toDto(previsao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrevisaoMockMvc.perform(post("/api/previsaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(previsaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Previsao in the database
        List<Previsao> previsaoList = previsaoRepository.findAll();
        assertThat(previsaoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = previsaoRepository.findAll().size();
        // set the field null
        previsao.setName(null);

        // Create the Previsao, which fails.
        PrevisaoDTO previsaoDTO = previsaoMapper.toDto(previsao);


        restPrevisaoMockMvc.perform(post("/api/previsaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(previsaoDTO)))
            .andExpect(status().isBadRequest());

        List<Previsao> previsaoList = previsaoRepository.findAll();
        assertThat(previsaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = previsaoRepository.findAll().size();
        // set the field null
        previsao.setDescricao(null);

        // Create the Previsao, which fails.
        PrevisaoDTO previsaoDTO = previsaoMapper.toDto(previsao);


        restPrevisaoMockMvc.perform(post("/api/previsaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(previsaoDTO)))
            .andExpect(status().isBadRequest());

        List<Previsao> previsaoList = previsaoRepository.findAll();
        assertThat(previsaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = previsaoRepository.findAll().size();
        // set the field null
        previsao.setCreated(null);

        // Create the Previsao, which fails.
        PrevisaoDTO previsaoDTO = previsaoMapper.toDto(previsao);


        restPrevisaoMockMvc.perform(post("/api/previsaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(previsaoDTO)))
            .andExpect(status().isBadRequest());

        List<Previsao> previsaoList = previsaoRepository.findAll();
        assertThat(previsaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPrevisaos() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get all the previsaoList
        restPrevisaoMockMvc.perform(get("/api/previsaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(previsao.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].textoNorte").value(hasItem(DEFAULT_TEXTO_NORTE)))
            .andExpect(jsonPath("$.[*].textoNorteImagem").value(hasItem(DEFAULT_TEXTO_NORTE_IMAGEM)))
            .andExpect(jsonPath("$.[*].textoNordeste").value(hasItem(DEFAULT_TEXTO_NORDESTE)))
            .andExpect(jsonPath("$.[*].textoNordesteImagem").value(hasItem(DEFAULT_TEXTO_NORDESTE_IMAGEM)))
            .andExpect(jsonPath("$.[*].textoSul").value(hasItem(DEFAULT_TEXTO_SUL)))
            .andExpect(jsonPath("$.[*].textoSulImagem").value(hasItem(DEFAULT_TEXTO_SUL_IMAGEM)))
            .andExpect(jsonPath("$.[*].textoSudeste").value(hasItem(DEFAULT_TEXTO_SUDESTE)))
            .andExpect(jsonPath("$.[*].textoSudesteImagem").value(hasItem(DEFAULT_TEXTO_SUDESTE_IMAGEM)))
            .andExpect(jsonPath("$.[*].textoCentroOeste").value(hasItem(DEFAULT_TEXTO_CENTRO_OESTE)))
            .andExpect(jsonPath("$.[*].textoCentroOesteImagem").value(hasItem(DEFAULT_TEXTO_CENTRO_OESTE_IMAGEM)))
            .andExpect(jsonPath("$.[*].enviado").value(hasItem(DEFAULT_ENVIADO.booleanValue())))
            .andExpect(jsonPath("$.[*].textoBrasil").value(hasItem(DEFAULT_TEXTO_BRASIL)))
            .andExpect(jsonPath("$.[*].textoBrasilImagem").value(hasItem(DEFAULT_TEXTO_BRASIL_IMAGEM)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getPrevisao() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        // Get the previsao
        restPrevisaoMockMvc.perform(get("/api/previsaos/{id}", previsao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(previsao.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.textoNorte").value(DEFAULT_TEXTO_NORTE))
            .andExpect(jsonPath("$.textoNorteImagem").value(DEFAULT_TEXTO_NORTE_IMAGEM))
            .andExpect(jsonPath("$.textoNordeste").value(DEFAULT_TEXTO_NORDESTE))
            .andExpect(jsonPath("$.textoNordesteImagem").value(DEFAULT_TEXTO_NORDESTE_IMAGEM))
            .andExpect(jsonPath("$.textoSul").value(DEFAULT_TEXTO_SUL))
            .andExpect(jsonPath("$.textoSulImagem").value(DEFAULT_TEXTO_SUL_IMAGEM))
            .andExpect(jsonPath("$.textoSudeste").value(DEFAULT_TEXTO_SUDESTE))
            .andExpect(jsonPath("$.textoSudesteImagem").value(DEFAULT_TEXTO_SUDESTE_IMAGEM))
            .andExpect(jsonPath("$.textoCentroOeste").value(DEFAULT_TEXTO_CENTRO_OESTE))
            .andExpect(jsonPath("$.textoCentroOesteImagem").value(DEFAULT_TEXTO_CENTRO_OESTE_IMAGEM))
            .andExpect(jsonPath("$.enviado").value(DEFAULT_ENVIADO.booleanValue()))
            .andExpect(jsonPath("$.textoBrasil").value(DEFAULT_TEXTO_BRASIL))
            .andExpect(jsonPath("$.textoBrasilImagem").value(DEFAULT_TEXTO_BRASIL_IMAGEM))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPrevisao() throws Exception {
        // Get the previsao
        restPrevisaoMockMvc.perform(get("/api/previsaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrevisao() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        int databaseSizeBeforeUpdate = previsaoRepository.findAll().size();

        // Update the previsao
        Previsao updatedPrevisao = previsaoRepository.findById(previsao.getId()).get();
        // Disconnect from session so that the updates on updatedPrevisao are not directly saved in db
        em.detach(updatedPrevisao);
        updatedPrevisao
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .textoNorte(UPDATED_TEXTO_NORTE)
            .textoNorteImagem(UPDATED_TEXTO_NORTE_IMAGEM)
            .textoNordeste(UPDATED_TEXTO_NORDESTE)
            .textoNordesteImagem(UPDATED_TEXTO_NORDESTE_IMAGEM)
            .textoSul(UPDATED_TEXTO_SUL)
            .textoSulImagem(UPDATED_TEXTO_SUL_IMAGEM)
            .textoSudeste(UPDATED_TEXTO_SUDESTE)
            .textoSudesteImagem(UPDATED_TEXTO_SUDESTE_IMAGEM)
            .textoCentroOeste(UPDATED_TEXTO_CENTRO_OESTE)
            .textoCentroOesteImagem(UPDATED_TEXTO_CENTRO_OESTE_IMAGEM)
            .enviado(UPDATED_ENVIADO)
            .textoBrasil(UPDATED_TEXTO_BRASIL)
            .textoBrasilImagem(UPDATED_TEXTO_BRASIL_IMAGEM)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        PrevisaoDTO previsaoDTO = previsaoMapper.toDto(updatedPrevisao);

        restPrevisaoMockMvc.perform(put("/api/previsaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(previsaoDTO)))
            .andExpect(status().isOk());

        // Validate the Previsao in the database
        List<Previsao> previsaoList = previsaoRepository.findAll();
        assertThat(previsaoList).hasSize(databaseSizeBeforeUpdate);
        Previsao testPrevisao = previsaoList.get(previsaoList.size() - 1);
        assertThat(testPrevisao.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPrevisao.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testPrevisao.getTextoNorte()).isEqualTo(UPDATED_TEXTO_NORTE);
        assertThat(testPrevisao.getTextoNorteImagem()).isEqualTo(UPDATED_TEXTO_NORTE_IMAGEM);
        assertThat(testPrevisao.getTextoNordeste()).isEqualTo(UPDATED_TEXTO_NORDESTE);
        assertThat(testPrevisao.getTextoNordesteImagem()).isEqualTo(UPDATED_TEXTO_NORDESTE_IMAGEM);
        assertThat(testPrevisao.getTextoSul()).isEqualTo(UPDATED_TEXTO_SUL);
        assertThat(testPrevisao.getTextoSulImagem()).isEqualTo(UPDATED_TEXTO_SUL_IMAGEM);
        assertThat(testPrevisao.getTextoSudeste()).isEqualTo(UPDATED_TEXTO_SUDESTE);
        assertThat(testPrevisao.getTextoSudesteImagem()).isEqualTo(UPDATED_TEXTO_SUDESTE_IMAGEM);
        assertThat(testPrevisao.getTextoCentroOeste()).isEqualTo(UPDATED_TEXTO_CENTRO_OESTE);
        assertThat(testPrevisao.getTextoCentroOesteImagem()).isEqualTo(UPDATED_TEXTO_CENTRO_OESTE_IMAGEM);
        assertThat(testPrevisao.isEnviado()).isEqualTo(UPDATED_ENVIADO);
        assertThat(testPrevisao.getTextoBrasil()).isEqualTo(UPDATED_TEXTO_BRASIL);
        assertThat(testPrevisao.getTextoBrasilImagem()).isEqualTo(UPDATED_TEXTO_BRASIL_IMAGEM);
        assertThat(testPrevisao.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testPrevisao.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingPrevisao() throws Exception {
        int databaseSizeBeforeUpdate = previsaoRepository.findAll().size();

        // Create the Previsao
        PrevisaoDTO previsaoDTO = previsaoMapper.toDto(previsao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrevisaoMockMvc.perform(put("/api/previsaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(previsaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Previsao in the database
        List<Previsao> previsaoList = previsaoRepository.findAll();
        assertThat(previsaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePrevisao() throws Exception {
        // Initialize the database
        previsaoRepository.saveAndFlush(previsao);

        int databaseSizeBeforeDelete = previsaoRepository.findAll().size();

        // Delete the previsao
        restPrevisaoMockMvc.perform(delete("/api/previsaos/{id}", previsao.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Previsao> previsaoList = previsaoRepository.findAll();
        assertThat(previsaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
