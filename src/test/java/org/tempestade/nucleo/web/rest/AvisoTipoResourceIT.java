package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.AvisoTipo;
import org.tempestade.nucleo.repository.AvisoTipoRepository;
import org.tempestade.nucleo.service.AvisoTipoService;
import org.tempestade.nucleo.service.dto.AvisoTipoDTO;
import org.tempestade.nucleo.service.mapper.AvisoTipoMapper;

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
 * Integration tests for the {@link AvisoTipoResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AvisoTipoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private AvisoTipoRepository avisoTipoRepository;

    @Autowired
    private AvisoTipoMapper avisoTipoMapper;

    @Autowired
    private AvisoTipoService avisoTipoService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAvisoTipoMockMvc;

    private AvisoTipo avisoTipo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AvisoTipo createEntity(EntityManager em) {
        AvisoTipo avisoTipo = new AvisoTipo()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return avisoTipo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AvisoTipo createUpdatedEntity(EntityManager em) {
        AvisoTipo avisoTipo = new AvisoTipo()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return avisoTipo;
    }

    @BeforeEach
    public void initTest() {
        avisoTipo = createEntity(em);
    }

    @Test
    @Transactional
    public void createAvisoTipo() throws Exception {
        int databaseSizeBeforeCreate = avisoTipoRepository.findAll().size();
        // Create the AvisoTipo
        AvisoTipoDTO avisoTipoDTO = avisoTipoMapper.toDto(avisoTipo);
        restAvisoTipoMockMvc.perform(post("/api/aviso-tipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avisoTipoDTO)))
            .andExpect(status().isCreated());

        // Validate the AvisoTipo in the database
        List<AvisoTipo> avisoTipoList = avisoTipoRepository.findAll();
        assertThat(avisoTipoList).hasSize(databaseSizeBeforeCreate + 1);
        AvisoTipo testAvisoTipo = avisoTipoList.get(avisoTipoList.size() - 1);
        assertThat(testAvisoTipo.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testAvisoTipo.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testAvisoTipo.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testAvisoTipo.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createAvisoTipoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = avisoTipoRepository.findAll().size();

        // Create the AvisoTipo with an existing ID
        avisoTipo.setId(1L);
        AvisoTipoDTO avisoTipoDTO = avisoTipoMapper.toDto(avisoTipo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAvisoTipoMockMvc.perform(post("/api/aviso-tipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avisoTipoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AvisoTipo in the database
        List<AvisoTipo> avisoTipoList = avisoTipoRepository.findAll();
        assertThat(avisoTipoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = avisoTipoRepository.findAll().size();
        // set the field null
        avisoTipo.setNome(null);

        // Create the AvisoTipo, which fails.
        AvisoTipoDTO avisoTipoDTO = avisoTipoMapper.toDto(avisoTipo);


        restAvisoTipoMockMvc.perform(post("/api/aviso-tipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avisoTipoDTO)))
            .andExpect(status().isBadRequest());

        List<AvisoTipo> avisoTipoList = avisoTipoRepository.findAll();
        assertThat(avisoTipoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = avisoTipoRepository.findAll().size();
        // set the field null
        avisoTipo.setDescricao(null);

        // Create the AvisoTipo, which fails.
        AvisoTipoDTO avisoTipoDTO = avisoTipoMapper.toDto(avisoTipo);


        restAvisoTipoMockMvc.perform(post("/api/aviso-tipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avisoTipoDTO)))
            .andExpect(status().isBadRequest());

        List<AvisoTipo> avisoTipoList = avisoTipoRepository.findAll();
        assertThat(avisoTipoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = avisoTipoRepository.findAll().size();
        // set the field null
        avisoTipo.setCreated(null);

        // Create the AvisoTipo, which fails.
        AvisoTipoDTO avisoTipoDTO = avisoTipoMapper.toDto(avisoTipo);


        restAvisoTipoMockMvc.perform(post("/api/aviso-tipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avisoTipoDTO)))
            .andExpect(status().isBadRequest());

        List<AvisoTipo> avisoTipoList = avisoTipoRepository.findAll();
        assertThat(avisoTipoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAvisoTipos() throws Exception {
        // Initialize the database
        avisoTipoRepository.saveAndFlush(avisoTipo);

        // Get all the avisoTipoList
        restAvisoTipoMockMvc.perform(get("/api/aviso-tipos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(avisoTipo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getAvisoTipo() throws Exception {
        // Initialize the database
        avisoTipoRepository.saveAndFlush(avisoTipo);

        // Get the avisoTipo
        restAvisoTipoMockMvc.perform(get("/api/aviso-tipos/{id}", avisoTipo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(avisoTipo.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingAvisoTipo() throws Exception {
        // Get the avisoTipo
        restAvisoTipoMockMvc.perform(get("/api/aviso-tipos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAvisoTipo() throws Exception {
        // Initialize the database
        avisoTipoRepository.saveAndFlush(avisoTipo);

        int databaseSizeBeforeUpdate = avisoTipoRepository.findAll().size();

        // Update the avisoTipo
        AvisoTipo updatedAvisoTipo = avisoTipoRepository.findById(avisoTipo.getId()).get();
        // Disconnect from session so that the updates on updatedAvisoTipo are not directly saved in db
        em.detach(updatedAvisoTipo);
        updatedAvisoTipo
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        AvisoTipoDTO avisoTipoDTO = avisoTipoMapper.toDto(updatedAvisoTipo);

        restAvisoTipoMockMvc.perform(put("/api/aviso-tipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avisoTipoDTO)))
            .andExpect(status().isOk());

        // Validate the AvisoTipo in the database
        List<AvisoTipo> avisoTipoList = avisoTipoRepository.findAll();
        assertThat(avisoTipoList).hasSize(databaseSizeBeforeUpdate);
        AvisoTipo testAvisoTipo = avisoTipoList.get(avisoTipoList.size() - 1);
        assertThat(testAvisoTipo.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testAvisoTipo.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testAvisoTipo.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testAvisoTipo.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingAvisoTipo() throws Exception {
        int databaseSizeBeforeUpdate = avisoTipoRepository.findAll().size();

        // Create the AvisoTipo
        AvisoTipoDTO avisoTipoDTO = avisoTipoMapper.toDto(avisoTipo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAvisoTipoMockMvc.perform(put("/api/aviso-tipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avisoTipoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AvisoTipo in the database
        List<AvisoTipo> avisoTipoList = avisoTipoRepository.findAll();
        assertThat(avisoTipoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAvisoTipo() throws Exception {
        // Initialize the database
        avisoTipoRepository.saveAndFlush(avisoTipo);

        int databaseSizeBeforeDelete = avisoTipoRepository.findAll().size();

        // Delete the avisoTipo
        restAvisoTipoMockMvc.perform(delete("/api/aviso-tipos/{id}", avisoTipo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AvisoTipo> avisoTipoList = avisoTipoRepository.findAll();
        assertThat(avisoTipoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
