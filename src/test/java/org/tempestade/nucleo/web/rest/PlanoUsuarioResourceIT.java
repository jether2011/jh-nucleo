package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.PlanoUsuario;
import org.tempestade.nucleo.repository.PlanoUsuarioRepository;
import org.tempestade.nucleo.service.PlanoUsuarioService;
import org.tempestade.nucleo.service.dto.PlanoUsuarioDTO;
import org.tempestade.nucleo.service.mapper.PlanoUsuarioMapper;

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
 * Integration tests for the {@link PlanoUsuarioResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PlanoUsuarioResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PlanoUsuarioRepository planoUsuarioRepository;

    @Autowired
    private PlanoUsuarioMapper planoUsuarioMapper;

    @Autowired
    private PlanoUsuarioService planoUsuarioService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlanoUsuarioMockMvc;

    private PlanoUsuario planoUsuario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanoUsuario createEntity(EntityManager em) {
        PlanoUsuario planoUsuario = new PlanoUsuario()
            .name(DEFAULT_NAME)
            .descricao(DEFAULT_DESCRICAO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return planoUsuario;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanoUsuario createUpdatedEntity(EntityManager em) {
        PlanoUsuario planoUsuario = new PlanoUsuario()
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return planoUsuario;
    }

    @BeforeEach
    public void initTest() {
        planoUsuario = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlanoUsuario() throws Exception {
        int databaseSizeBeforeCreate = planoUsuarioRepository.findAll().size();
        // Create the PlanoUsuario
        PlanoUsuarioDTO planoUsuarioDTO = planoUsuarioMapper.toDto(planoUsuario);
        restPlanoUsuarioMockMvc.perform(post("/api/plano-usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoUsuarioDTO)))
            .andExpect(status().isCreated());

        // Validate the PlanoUsuario in the database
        List<PlanoUsuario> planoUsuarioList = planoUsuarioRepository.findAll();
        assertThat(planoUsuarioList).hasSize(databaseSizeBeforeCreate + 1);
        PlanoUsuario testPlanoUsuario = planoUsuarioList.get(planoUsuarioList.size() - 1);
        assertThat(testPlanoUsuario.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPlanoUsuario.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testPlanoUsuario.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testPlanoUsuario.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createPlanoUsuarioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = planoUsuarioRepository.findAll().size();

        // Create the PlanoUsuario with an existing ID
        planoUsuario.setId(1L);
        PlanoUsuarioDTO planoUsuarioDTO = planoUsuarioMapper.toDto(planoUsuario);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanoUsuarioMockMvc.perform(post("/api/plano-usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoUsuarioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PlanoUsuario in the database
        List<PlanoUsuario> planoUsuarioList = planoUsuarioRepository.findAll();
        assertThat(planoUsuarioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoUsuarioRepository.findAll().size();
        // set the field null
        planoUsuario.setName(null);

        // Create the PlanoUsuario, which fails.
        PlanoUsuarioDTO planoUsuarioDTO = planoUsuarioMapper.toDto(planoUsuario);


        restPlanoUsuarioMockMvc.perform(post("/api/plano-usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoUsuarioDTO)))
            .andExpect(status().isBadRequest());

        List<PlanoUsuario> planoUsuarioList = planoUsuarioRepository.findAll();
        assertThat(planoUsuarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoUsuarioRepository.findAll().size();
        // set the field null
        planoUsuario.setDescricao(null);

        // Create the PlanoUsuario, which fails.
        PlanoUsuarioDTO planoUsuarioDTO = planoUsuarioMapper.toDto(planoUsuario);


        restPlanoUsuarioMockMvc.perform(post("/api/plano-usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoUsuarioDTO)))
            .andExpect(status().isBadRequest());

        List<PlanoUsuario> planoUsuarioList = planoUsuarioRepository.findAll();
        assertThat(planoUsuarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoUsuarioRepository.findAll().size();
        // set the field null
        planoUsuario.setCreated(null);

        // Create the PlanoUsuario, which fails.
        PlanoUsuarioDTO planoUsuarioDTO = planoUsuarioMapper.toDto(planoUsuario);


        restPlanoUsuarioMockMvc.perform(post("/api/plano-usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoUsuarioDTO)))
            .andExpect(status().isBadRequest());

        List<PlanoUsuario> planoUsuarioList = planoUsuarioRepository.findAll();
        assertThat(planoUsuarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPlanoUsuarios() throws Exception {
        // Initialize the database
        planoUsuarioRepository.saveAndFlush(planoUsuario);

        // Get all the planoUsuarioList
        restPlanoUsuarioMockMvc.perform(get("/api/plano-usuarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planoUsuario.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getPlanoUsuario() throws Exception {
        // Initialize the database
        planoUsuarioRepository.saveAndFlush(planoUsuario);

        // Get the planoUsuario
        restPlanoUsuarioMockMvc.perform(get("/api/plano-usuarios/{id}", planoUsuario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(planoUsuario.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPlanoUsuario() throws Exception {
        // Get the planoUsuario
        restPlanoUsuarioMockMvc.perform(get("/api/plano-usuarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlanoUsuario() throws Exception {
        // Initialize the database
        planoUsuarioRepository.saveAndFlush(planoUsuario);

        int databaseSizeBeforeUpdate = planoUsuarioRepository.findAll().size();

        // Update the planoUsuario
        PlanoUsuario updatedPlanoUsuario = planoUsuarioRepository.findById(planoUsuario.getId()).get();
        // Disconnect from session so that the updates on updatedPlanoUsuario are not directly saved in db
        em.detach(updatedPlanoUsuario);
        updatedPlanoUsuario
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        PlanoUsuarioDTO planoUsuarioDTO = planoUsuarioMapper.toDto(updatedPlanoUsuario);

        restPlanoUsuarioMockMvc.perform(put("/api/plano-usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoUsuarioDTO)))
            .andExpect(status().isOk());

        // Validate the PlanoUsuario in the database
        List<PlanoUsuario> planoUsuarioList = planoUsuarioRepository.findAll();
        assertThat(planoUsuarioList).hasSize(databaseSizeBeforeUpdate);
        PlanoUsuario testPlanoUsuario = planoUsuarioList.get(planoUsuarioList.size() - 1);
        assertThat(testPlanoUsuario.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPlanoUsuario.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testPlanoUsuario.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testPlanoUsuario.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingPlanoUsuario() throws Exception {
        int databaseSizeBeforeUpdate = planoUsuarioRepository.findAll().size();

        // Create the PlanoUsuario
        PlanoUsuarioDTO planoUsuarioDTO = planoUsuarioMapper.toDto(planoUsuario);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanoUsuarioMockMvc.perform(put("/api/plano-usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoUsuarioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PlanoUsuario in the database
        List<PlanoUsuario> planoUsuarioList = planoUsuarioRepository.findAll();
        assertThat(planoUsuarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePlanoUsuario() throws Exception {
        // Initialize the database
        planoUsuarioRepository.saveAndFlush(planoUsuario);

        int databaseSizeBeforeDelete = planoUsuarioRepository.findAll().size();

        // Delete the planoUsuario
        restPlanoUsuarioMockMvc.perform(delete("/api/plano-usuarios/{id}", planoUsuario.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlanoUsuario> planoUsuarioList = planoUsuarioRepository.findAll();
        assertThat(planoUsuarioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
