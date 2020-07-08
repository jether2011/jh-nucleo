package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.BoletimPrevisao;
import org.tempestade.nucleo.repository.BoletimPrevisaoRepository;
import org.tempestade.nucleo.service.BoletimPrevisaoService;
import org.tempestade.nucleo.service.dto.BoletimPrevisaoDTO;
import org.tempestade.nucleo.service.mapper.BoletimPrevisaoMapper;

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
 * Integration tests for the {@link BoletimPrevisaoResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BoletimPrevisaoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_LOCAL = "AAAAAAAAAA";
    private static final String UPDATED_LOCAL = "BBBBBBBBBB";

    private static final Integer DEFAULT_IMG_CONDICAO_TEMPO = 1;
    private static final Integer UPDATED_IMG_CONDICAO_TEMPO = 2;

    private static final String DEFAULT_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO = "BBBBBBBBBB";

    private static final Integer DEFAULT_GRUPO_ORDEM = 1;
    private static final Integer UPDATED_GRUPO_ORDEM = 2;

    private static final String DEFAULT_ONDAS = "AAAAAAAAAA";
    private static final String UPDATED_ONDAS = "BBBBBBBBBB";

    private static final Integer DEFAULT_TEMPERATURA_DE = 1;
    private static final Integer UPDATED_TEMPERATURA_DE = 2;

    private static final Integer DEFAULT_TEMPERATURA_ATE = 1;
    private static final Integer UPDATED_TEMPERATURA_ATE = 2;

    private static final Integer DEFAULT_VENTOVELOCIDADEMEDIAKMH = 1;
    private static final Integer UPDATED_VENTOVELOCIDADEMEDIAKMH = 2;

    private static final String DEFAULT_VENTOS_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_VENTOS_OBSERVACAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_VENTO_RAJADA = false;
    private static final Boolean UPDATED_VENTO_RAJADA = true;

    private static final String DEFAULT_TEMPESTADE_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_TEMPESTADE_OBSERVACAO = "BBBBBBBBBB";

    private static final String DEFAULT_CHUVA_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_CHUVA_OBSERVACAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private BoletimPrevisaoRepository boletimPrevisaoRepository;

    @Autowired
    private BoletimPrevisaoMapper boletimPrevisaoMapper;

    @Autowired
    private BoletimPrevisaoService boletimPrevisaoService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBoletimPrevisaoMockMvc;

    private BoletimPrevisao boletimPrevisao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BoletimPrevisao createEntity(EntityManager em) {
        BoletimPrevisao boletimPrevisao = new BoletimPrevisao()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .local(DEFAULT_LOCAL)
            .imgCondicaoTempo(DEFAULT_IMG_CONDICAO_TEMPO)
            .observacao(DEFAULT_OBSERVACAO)
            .grupoOrdem(DEFAULT_GRUPO_ORDEM)
            .ondas(DEFAULT_ONDAS)
            .temperaturaDe(DEFAULT_TEMPERATURA_DE)
            .temperaturaAte(DEFAULT_TEMPERATURA_ATE)
            .ventovelocidademediakmh(DEFAULT_VENTOVELOCIDADEMEDIAKMH)
            .ventosObservacao(DEFAULT_VENTOS_OBSERVACAO)
            .ventoRajada(DEFAULT_VENTO_RAJADA)
            .tempestadeObservacao(DEFAULT_TEMPESTADE_OBSERVACAO)
            .chuvaObservacao(DEFAULT_CHUVA_OBSERVACAO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return boletimPrevisao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BoletimPrevisao createUpdatedEntity(EntityManager em) {
        BoletimPrevisao boletimPrevisao = new BoletimPrevisao()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .local(UPDATED_LOCAL)
            .imgCondicaoTempo(UPDATED_IMG_CONDICAO_TEMPO)
            .observacao(UPDATED_OBSERVACAO)
            .grupoOrdem(UPDATED_GRUPO_ORDEM)
            .ondas(UPDATED_ONDAS)
            .temperaturaDe(UPDATED_TEMPERATURA_DE)
            .temperaturaAte(UPDATED_TEMPERATURA_ATE)
            .ventovelocidademediakmh(UPDATED_VENTOVELOCIDADEMEDIAKMH)
            .ventosObservacao(UPDATED_VENTOS_OBSERVACAO)
            .ventoRajada(UPDATED_VENTO_RAJADA)
            .tempestadeObservacao(UPDATED_TEMPESTADE_OBSERVACAO)
            .chuvaObservacao(UPDATED_CHUVA_OBSERVACAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return boletimPrevisao;
    }

    @BeforeEach
    public void initTest() {
        boletimPrevisao = createEntity(em);
    }

    @Test
    @Transactional
    public void createBoletimPrevisao() throws Exception {
        int databaseSizeBeforeCreate = boletimPrevisaoRepository.findAll().size();
        // Create the BoletimPrevisao
        BoletimPrevisaoDTO boletimPrevisaoDTO = boletimPrevisaoMapper.toDto(boletimPrevisao);
        restBoletimPrevisaoMockMvc.perform(post("/api/boletim-previsaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boletimPrevisaoDTO)))
            .andExpect(status().isCreated());

        // Validate the BoletimPrevisao in the database
        List<BoletimPrevisao> boletimPrevisaoList = boletimPrevisaoRepository.findAll();
        assertThat(boletimPrevisaoList).hasSize(databaseSizeBeforeCreate + 1);
        BoletimPrevisao testBoletimPrevisao = boletimPrevisaoList.get(boletimPrevisaoList.size() - 1);
        assertThat(testBoletimPrevisao.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testBoletimPrevisao.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testBoletimPrevisao.getLocal()).isEqualTo(DEFAULT_LOCAL);
        assertThat(testBoletimPrevisao.getImgCondicaoTempo()).isEqualTo(DEFAULT_IMG_CONDICAO_TEMPO);
        assertThat(testBoletimPrevisao.getObservacao()).isEqualTo(DEFAULT_OBSERVACAO);
        assertThat(testBoletimPrevisao.getGrupoOrdem()).isEqualTo(DEFAULT_GRUPO_ORDEM);
        assertThat(testBoletimPrevisao.getOndas()).isEqualTo(DEFAULT_ONDAS);
        assertThat(testBoletimPrevisao.getTemperaturaDe()).isEqualTo(DEFAULT_TEMPERATURA_DE);
        assertThat(testBoletimPrevisao.getTemperaturaAte()).isEqualTo(DEFAULT_TEMPERATURA_ATE);
        assertThat(testBoletimPrevisao.getVentovelocidademediakmh()).isEqualTo(DEFAULT_VENTOVELOCIDADEMEDIAKMH);
        assertThat(testBoletimPrevisao.getVentosObservacao()).isEqualTo(DEFAULT_VENTOS_OBSERVACAO);
        assertThat(testBoletimPrevisao.isVentoRajada()).isEqualTo(DEFAULT_VENTO_RAJADA);
        assertThat(testBoletimPrevisao.getTempestadeObservacao()).isEqualTo(DEFAULT_TEMPESTADE_OBSERVACAO);
        assertThat(testBoletimPrevisao.getChuvaObservacao()).isEqualTo(DEFAULT_CHUVA_OBSERVACAO);
        assertThat(testBoletimPrevisao.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testBoletimPrevisao.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createBoletimPrevisaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = boletimPrevisaoRepository.findAll().size();

        // Create the BoletimPrevisao with an existing ID
        boletimPrevisao.setId(1L);
        BoletimPrevisaoDTO boletimPrevisaoDTO = boletimPrevisaoMapper.toDto(boletimPrevisao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBoletimPrevisaoMockMvc.perform(post("/api/boletim-previsaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boletimPrevisaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BoletimPrevisao in the database
        List<BoletimPrevisao> boletimPrevisaoList = boletimPrevisaoRepository.findAll();
        assertThat(boletimPrevisaoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = boletimPrevisaoRepository.findAll().size();
        // set the field null
        boletimPrevisao.setNome(null);

        // Create the BoletimPrevisao, which fails.
        BoletimPrevisaoDTO boletimPrevisaoDTO = boletimPrevisaoMapper.toDto(boletimPrevisao);


        restBoletimPrevisaoMockMvc.perform(post("/api/boletim-previsaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boletimPrevisaoDTO)))
            .andExpect(status().isBadRequest());

        List<BoletimPrevisao> boletimPrevisaoList = boletimPrevisaoRepository.findAll();
        assertThat(boletimPrevisaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = boletimPrevisaoRepository.findAll().size();
        // set the field null
        boletimPrevisao.setCreated(null);

        // Create the BoletimPrevisao, which fails.
        BoletimPrevisaoDTO boletimPrevisaoDTO = boletimPrevisaoMapper.toDto(boletimPrevisao);


        restBoletimPrevisaoMockMvc.perform(post("/api/boletim-previsaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boletimPrevisaoDTO)))
            .andExpect(status().isBadRequest());

        List<BoletimPrevisao> boletimPrevisaoList = boletimPrevisaoRepository.findAll();
        assertThat(boletimPrevisaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBoletimPrevisaos() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get all the boletimPrevisaoList
        restBoletimPrevisaoMockMvc.perform(get("/api/boletim-previsaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(boletimPrevisao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].local").value(hasItem(DEFAULT_LOCAL)))
            .andExpect(jsonPath("$.[*].imgCondicaoTempo").value(hasItem(DEFAULT_IMG_CONDICAO_TEMPO)))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO)))
            .andExpect(jsonPath("$.[*].grupoOrdem").value(hasItem(DEFAULT_GRUPO_ORDEM)))
            .andExpect(jsonPath("$.[*].ondas").value(hasItem(DEFAULT_ONDAS)))
            .andExpect(jsonPath("$.[*].temperaturaDe").value(hasItem(DEFAULT_TEMPERATURA_DE)))
            .andExpect(jsonPath("$.[*].temperaturaAte").value(hasItem(DEFAULT_TEMPERATURA_ATE)))
            .andExpect(jsonPath("$.[*].ventovelocidademediakmh").value(hasItem(DEFAULT_VENTOVELOCIDADEMEDIAKMH)))
            .andExpect(jsonPath("$.[*].ventosObservacao").value(hasItem(DEFAULT_VENTOS_OBSERVACAO)))
            .andExpect(jsonPath("$.[*].ventoRajada").value(hasItem(DEFAULT_VENTO_RAJADA.booleanValue())))
            .andExpect(jsonPath("$.[*].tempestadeObservacao").value(hasItem(DEFAULT_TEMPESTADE_OBSERVACAO)))
            .andExpect(jsonPath("$.[*].chuvaObservacao").value(hasItem(DEFAULT_CHUVA_OBSERVACAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getBoletimPrevisao() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        // Get the boletimPrevisao
        restBoletimPrevisaoMockMvc.perform(get("/api/boletim-previsaos/{id}", boletimPrevisao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(boletimPrevisao.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.local").value(DEFAULT_LOCAL))
            .andExpect(jsonPath("$.imgCondicaoTempo").value(DEFAULT_IMG_CONDICAO_TEMPO))
            .andExpect(jsonPath("$.observacao").value(DEFAULT_OBSERVACAO))
            .andExpect(jsonPath("$.grupoOrdem").value(DEFAULT_GRUPO_ORDEM))
            .andExpect(jsonPath("$.ondas").value(DEFAULT_ONDAS))
            .andExpect(jsonPath("$.temperaturaDe").value(DEFAULT_TEMPERATURA_DE))
            .andExpect(jsonPath("$.temperaturaAte").value(DEFAULT_TEMPERATURA_ATE))
            .andExpect(jsonPath("$.ventovelocidademediakmh").value(DEFAULT_VENTOVELOCIDADEMEDIAKMH))
            .andExpect(jsonPath("$.ventosObservacao").value(DEFAULT_VENTOS_OBSERVACAO))
            .andExpect(jsonPath("$.ventoRajada").value(DEFAULT_VENTO_RAJADA.booleanValue()))
            .andExpect(jsonPath("$.tempestadeObservacao").value(DEFAULT_TEMPESTADE_OBSERVACAO))
            .andExpect(jsonPath("$.chuvaObservacao").value(DEFAULT_CHUVA_OBSERVACAO))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingBoletimPrevisao() throws Exception {
        // Get the boletimPrevisao
        restBoletimPrevisaoMockMvc.perform(get("/api/boletim-previsaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBoletimPrevisao() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        int databaseSizeBeforeUpdate = boletimPrevisaoRepository.findAll().size();

        // Update the boletimPrevisao
        BoletimPrevisao updatedBoletimPrevisao = boletimPrevisaoRepository.findById(boletimPrevisao.getId()).get();
        // Disconnect from session so that the updates on updatedBoletimPrevisao are not directly saved in db
        em.detach(updatedBoletimPrevisao);
        updatedBoletimPrevisao
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .local(UPDATED_LOCAL)
            .imgCondicaoTempo(UPDATED_IMG_CONDICAO_TEMPO)
            .observacao(UPDATED_OBSERVACAO)
            .grupoOrdem(UPDATED_GRUPO_ORDEM)
            .ondas(UPDATED_ONDAS)
            .temperaturaDe(UPDATED_TEMPERATURA_DE)
            .temperaturaAte(UPDATED_TEMPERATURA_ATE)
            .ventovelocidademediakmh(UPDATED_VENTOVELOCIDADEMEDIAKMH)
            .ventosObservacao(UPDATED_VENTOS_OBSERVACAO)
            .ventoRajada(UPDATED_VENTO_RAJADA)
            .tempestadeObservacao(UPDATED_TEMPESTADE_OBSERVACAO)
            .chuvaObservacao(UPDATED_CHUVA_OBSERVACAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        BoletimPrevisaoDTO boletimPrevisaoDTO = boletimPrevisaoMapper.toDto(updatedBoletimPrevisao);

        restBoletimPrevisaoMockMvc.perform(put("/api/boletim-previsaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boletimPrevisaoDTO)))
            .andExpect(status().isOk());

        // Validate the BoletimPrevisao in the database
        List<BoletimPrevisao> boletimPrevisaoList = boletimPrevisaoRepository.findAll();
        assertThat(boletimPrevisaoList).hasSize(databaseSizeBeforeUpdate);
        BoletimPrevisao testBoletimPrevisao = boletimPrevisaoList.get(boletimPrevisaoList.size() - 1);
        assertThat(testBoletimPrevisao.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testBoletimPrevisao.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testBoletimPrevisao.getLocal()).isEqualTo(UPDATED_LOCAL);
        assertThat(testBoletimPrevisao.getImgCondicaoTempo()).isEqualTo(UPDATED_IMG_CONDICAO_TEMPO);
        assertThat(testBoletimPrevisao.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
        assertThat(testBoletimPrevisao.getGrupoOrdem()).isEqualTo(UPDATED_GRUPO_ORDEM);
        assertThat(testBoletimPrevisao.getOndas()).isEqualTo(UPDATED_ONDAS);
        assertThat(testBoletimPrevisao.getTemperaturaDe()).isEqualTo(UPDATED_TEMPERATURA_DE);
        assertThat(testBoletimPrevisao.getTemperaturaAte()).isEqualTo(UPDATED_TEMPERATURA_ATE);
        assertThat(testBoletimPrevisao.getVentovelocidademediakmh()).isEqualTo(UPDATED_VENTOVELOCIDADEMEDIAKMH);
        assertThat(testBoletimPrevisao.getVentosObservacao()).isEqualTo(UPDATED_VENTOS_OBSERVACAO);
        assertThat(testBoletimPrevisao.isVentoRajada()).isEqualTo(UPDATED_VENTO_RAJADA);
        assertThat(testBoletimPrevisao.getTempestadeObservacao()).isEqualTo(UPDATED_TEMPESTADE_OBSERVACAO);
        assertThat(testBoletimPrevisao.getChuvaObservacao()).isEqualTo(UPDATED_CHUVA_OBSERVACAO);
        assertThat(testBoletimPrevisao.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testBoletimPrevisao.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingBoletimPrevisao() throws Exception {
        int databaseSizeBeforeUpdate = boletimPrevisaoRepository.findAll().size();

        // Create the BoletimPrevisao
        BoletimPrevisaoDTO boletimPrevisaoDTO = boletimPrevisaoMapper.toDto(boletimPrevisao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBoletimPrevisaoMockMvc.perform(put("/api/boletim-previsaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boletimPrevisaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BoletimPrevisao in the database
        List<BoletimPrevisao> boletimPrevisaoList = boletimPrevisaoRepository.findAll();
        assertThat(boletimPrevisaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBoletimPrevisao() throws Exception {
        // Initialize the database
        boletimPrevisaoRepository.saveAndFlush(boletimPrevisao);

        int databaseSizeBeforeDelete = boletimPrevisaoRepository.findAll().size();

        // Delete the boletimPrevisao
        restBoletimPrevisaoMockMvc.perform(delete("/api/boletim-previsaos/{id}", boletimPrevisao.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BoletimPrevisao> boletimPrevisaoList = boletimPrevisaoRepository.findAll();
        assertThat(boletimPrevisaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
