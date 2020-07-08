package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.ContatoTipoEnvio;
import org.tempestade.nucleo.repository.ContatoTipoEnvioRepository;
import org.tempestade.nucleo.service.ContatoTipoEnvioService;
import org.tempestade.nucleo.service.dto.ContatoTipoEnvioDTO;
import org.tempestade.nucleo.service.mapper.ContatoTipoEnvioMapper;

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
 * Integration tests for the {@link ContatoTipoEnvioResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ContatoTipoEnvioResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ContatoTipoEnvioRepository contatoTipoEnvioRepository;

    @Autowired
    private ContatoTipoEnvioMapper contatoTipoEnvioMapper;

    @Autowired
    private ContatoTipoEnvioService contatoTipoEnvioService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContatoTipoEnvioMockMvc;

    private ContatoTipoEnvio contatoTipoEnvio;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContatoTipoEnvio createEntity(EntityManager em) {
        ContatoTipoEnvio contatoTipoEnvio = new ContatoTipoEnvio()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return contatoTipoEnvio;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContatoTipoEnvio createUpdatedEntity(EntityManager em) {
        ContatoTipoEnvio contatoTipoEnvio = new ContatoTipoEnvio()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return contatoTipoEnvio;
    }

    @BeforeEach
    public void initTest() {
        contatoTipoEnvio = createEntity(em);
    }

    @Test
    @Transactional
    public void createContatoTipoEnvio() throws Exception {
        int databaseSizeBeforeCreate = contatoTipoEnvioRepository.findAll().size();
        // Create the ContatoTipoEnvio
        ContatoTipoEnvioDTO contatoTipoEnvioDTO = contatoTipoEnvioMapper.toDto(contatoTipoEnvio);
        restContatoTipoEnvioMockMvc.perform(post("/api/contato-tipo-envios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contatoTipoEnvioDTO)))
            .andExpect(status().isCreated());

        // Validate the ContatoTipoEnvio in the database
        List<ContatoTipoEnvio> contatoTipoEnvioList = contatoTipoEnvioRepository.findAll();
        assertThat(contatoTipoEnvioList).hasSize(databaseSizeBeforeCreate + 1);
        ContatoTipoEnvio testContatoTipoEnvio = contatoTipoEnvioList.get(contatoTipoEnvioList.size() - 1);
        assertThat(testContatoTipoEnvio.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testContatoTipoEnvio.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testContatoTipoEnvio.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testContatoTipoEnvio.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createContatoTipoEnvioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contatoTipoEnvioRepository.findAll().size();

        // Create the ContatoTipoEnvio with an existing ID
        contatoTipoEnvio.setId(1L);
        ContatoTipoEnvioDTO contatoTipoEnvioDTO = contatoTipoEnvioMapper.toDto(contatoTipoEnvio);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContatoTipoEnvioMockMvc.perform(post("/api/contato-tipo-envios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contatoTipoEnvioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContatoTipoEnvio in the database
        List<ContatoTipoEnvio> contatoTipoEnvioList = contatoTipoEnvioRepository.findAll();
        assertThat(contatoTipoEnvioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = contatoTipoEnvioRepository.findAll().size();
        // set the field null
        contatoTipoEnvio.setNome(null);

        // Create the ContatoTipoEnvio, which fails.
        ContatoTipoEnvioDTO contatoTipoEnvioDTO = contatoTipoEnvioMapper.toDto(contatoTipoEnvio);


        restContatoTipoEnvioMockMvc.perform(post("/api/contato-tipo-envios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contatoTipoEnvioDTO)))
            .andExpect(status().isBadRequest());

        List<ContatoTipoEnvio> contatoTipoEnvioList = contatoTipoEnvioRepository.findAll();
        assertThat(contatoTipoEnvioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = contatoTipoEnvioRepository.findAll().size();
        // set the field null
        contatoTipoEnvio.setCreated(null);

        // Create the ContatoTipoEnvio, which fails.
        ContatoTipoEnvioDTO contatoTipoEnvioDTO = contatoTipoEnvioMapper.toDto(contatoTipoEnvio);


        restContatoTipoEnvioMockMvc.perform(post("/api/contato-tipo-envios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contatoTipoEnvioDTO)))
            .andExpect(status().isBadRequest());

        List<ContatoTipoEnvio> contatoTipoEnvioList = contatoTipoEnvioRepository.findAll();
        assertThat(contatoTipoEnvioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContatoTipoEnvios() throws Exception {
        // Initialize the database
        contatoTipoEnvioRepository.saveAndFlush(contatoTipoEnvio);

        // Get all the contatoTipoEnvioList
        restContatoTipoEnvioMockMvc.perform(get("/api/contato-tipo-envios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contatoTipoEnvio.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getContatoTipoEnvio() throws Exception {
        // Initialize the database
        contatoTipoEnvioRepository.saveAndFlush(contatoTipoEnvio);

        // Get the contatoTipoEnvio
        restContatoTipoEnvioMockMvc.perform(get("/api/contato-tipo-envios/{id}", contatoTipoEnvio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contatoTipoEnvio.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingContatoTipoEnvio() throws Exception {
        // Get the contatoTipoEnvio
        restContatoTipoEnvioMockMvc.perform(get("/api/contato-tipo-envios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContatoTipoEnvio() throws Exception {
        // Initialize the database
        contatoTipoEnvioRepository.saveAndFlush(contatoTipoEnvio);

        int databaseSizeBeforeUpdate = contatoTipoEnvioRepository.findAll().size();

        // Update the contatoTipoEnvio
        ContatoTipoEnvio updatedContatoTipoEnvio = contatoTipoEnvioRepository.findById(contatoTipoEnvio.getId()).get();
        // Disconnect from session so that the updates on updatedContatoTipoEnvio are not directly saved in db
        em.detach(updatedContatoTipoEnvio);
        updatedContatoTipoEnvio
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        ContatoTipoEnvioDTO contatoTipoEnvioDTO = contatoTipoEnvioMapper.toDto(updatedContatoTipoEnvio);

        restContatoTipoEnvioMockMvc.perform(put("/api/contato-tipo-envios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contatoTipoEnvioDTO)))
            .andExpect(status().isOk());

        // Validate the ContatoTipoEnvio in the database
        List<ContatoTipoEnvio> contatoTipoEnvioList = contatoTipoEnvioRepository.findAll();
        assertThat(contatoTipoEnvioList).hasSize(databaseSizeBeforeUpdate);
        ContatoTipoEnvio testContatoTipoEnvio = contatoTipoEnvioList.get(contatoTipoEnvioList.size() - 1);
        assertThat(testContatoTipoEnvio.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testContatoTipoEnvio.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testContatoTipoEnvio.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testContatoTipoEnvio.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingContatoTipoEnvio() throws Exception {
        int databaseSizeBeforeUpdate = contatoTipoEnvioRepository.findAll().size();

        // Create the ContatoTipoEnvio
        ContatoTipoEnvioDTO contatoTipoEnvioDTO = contatoTipoEnvioMapper.toDto(contatoTipoEnvio);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContatoTipoEnvioMockMvc.perform(put("/api/contato-tipo-envios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contatoTipoEnvioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContatoTipoEnvio in the database
        List<ContatoTipoEnvio> contatoTipoEnvioList = contatoTipoEnvioRepository.findAll();
        assertThat(contatoTipoEnvioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContatoTipoEnvio() throws Exception {
        // Initialize the database
        contatoTipoEnvioRepository.saveAndFlush(contatoTipoEnvio);

        int databaseSizeBeforeDelete = contatoTipoEnvioRepository.findAll().size();

        // Delete the contatoTipoEnvio
        restContatoTipoEnvioMockMvc.perform(delete("/api/contato-tipo-envios/{id}", contatoTipoEnvio.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContatoTipoEnvio> contatoTipoEnvioList = contatoTipoEnvioRepository.findAll();
        assertThat(contatoTipoEnvioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
