package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.PlanoUsuario;
import org.tempestade.nucleo.domain.Plano;
import org.tempestade.nucleo.domain.Usuario;
import org.tempestade.nucleo.repository.PlanoUsuarioRepository;
import org.tempestade.nucleo.service.PlanoUsuarioService;
import org.tempestade.nucleo.service.dto.PlanoUsuarioDTO;
import org.tempestade.nucleo.service.mapper.PlanoUsuarioMapper;
import org.tempestade.nucleo.service.dto.PlanoUsuarioCriteria;
import org.tempestade.nucleo.service.PlanoUsuarioQueryService;

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
    private PlanoUsuarioQueryService planoUsuarioQueryService;

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
    public void getPlanoUsuariosByIdFiltering() throws Exception {
        // Initialize the database
        planoUsuarioRepository.saveAndFlush(planoUsuario);

        Long id = planoUsuario.getId();

        defaultPlanoUsuarioShouldBeFound("id.equals=" + id);
        defaultPlanoUsuarioShouldNotBeFound("id.notEquals=" + id);

        defaultPlanoUsuarioShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPlanoUsuarioShouldNotBeFound("id.greaterThan=" + id);

        defaultPlanoUsuarioShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPlanoUsuarioShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPlanoUsuariosByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        planoUsuarioRepository.saveAndFlush(planoUsuario);

        // Get all the planoUsuarioList where name equals to DEFAULT_NAME
        defaultPlanoUsuarioShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the planoUsuarioList where name equals to UPDATED_NAME
        defaultPlanoUsuarioShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPlanoUsuariosByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        planoUsuarioRepository.saveAndFlush(planoUsuario);

        // Get all the planoUsuarioList where name not equals to DEFAULT_NAME
        defaultPlanoUsuarioShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the planoUsuarioList where name not equals to UPDATED_NAME
        defaultPlanoUsuarioShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPlanoUsuariosByNameIsInShouldWork() throws Exception {
        // Initialize the database
        planoUsuarioRepository.saveAndFlush(planoUsuario);

        // Get all the planoUsuarioList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPlanoUsuarioShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the planoUsuarioList where name equals to UPDATED_NAME
        defaultPlanoUsuarioShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPlanoUsuariosByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoUsuarioRepository.saveAndFlush(planoUsuario);

        // Get all the planoUsuarioList where name is not null
        defaultPlanoUsuarioShouldBeFound("name.specified=true");

        // Get all the planoUsuarioList where name is null
        defaultPlanoUsuarioShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllPlanoUsuariosByNameContainsSomething() throws Exception {
        // Initialize the database
        planoUsuarioRepository.saveAndFlush(planoUsuario);

        // Get all the planoUsuarioList where name contains DEFAULT_NAME
        defaultPlanoUsuarioShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the planoUsuarioList where name contains UPDATED_NAME
        defaultPlanoUsuarioShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPlanoUsuariosByNameNotContainsSomething() throws Exception {
        // Initialize the database
        planoUsuarioRepository.saveAndFlush(planoUsuario);

        // Get all the planoUsuarioList where name does not contain DEFAULT_NAME
        defaultPlanoUsuarioShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the planoUsuarioList where name does not contain UPDATED_NAME
        defaultPlanoUsuarioShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllPlanoUsuariosByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        planoUsuarioRepository.saveAndFlush(planoUsuario);

        // Get all the planoUsuarioList where descricao equals to DEFAULT_DESCRICAO
        defaultPlanoUsuarioShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the planoUsuarioList where descricao equals to UPDATED_DESCRICAO
        defaultPlanoUsuarioShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllPlanoUsuariosByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        planoUsuarioRepository.saveAndFlush(planoUsuario);

        // Get all the planoUsuarioList where descricao not equals to DEFAULT_DESCRICAO
        defaultPlanoUsuarioShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the planoUsuarioList where descricao not equals to UPDATED_DESCRICAO
        defaultPlanoUsuarioShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllPlanoUsuariosByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        planoUsuarioRepository.saveAndFlush(planoUsuario);

        // Get all the planoUsuarioList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultPlanoUsuarioShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the planoUsuarioList where descricao equals to UPDATED_DESCRICAO
        defaultPlanoUsuarioShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllPlanoUsuariosByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoUsuarioRepository.saveAndFlush(planoUsuario);

        // Get all the planoUsuarioList where descricao is not null
        defaultPlanoUsuarioShouldBeFound("descricao.specified=true");

        // Get all the planoUsuarioList where descricao is null
        defaultPlanoUsuarioShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllPlanoUsuariosByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        planoUsuarioRepository.saveAndFlush(planoUsuario);

        // Get all the planoUsuarioList where descricao contains DEFAULT_DESCRICAO
        defaultPlanoUsuarioShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the planoUsuarioList where descricao contains UPDATED_DESCRICAO
        defaultPlanoUsuarioShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllPlanoUsuariosByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        planoUsuarioRepository.saveAndFlush(planoUsuario);

        // Get all the planoUsuarioList where descricao does not contain DEFAULT_DESCRICAO
        defaultPlanoUsuarioShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the planoUsuarioList where descricao does not contain UPDATED_DESCRICAO
        defaultPlanoUsuarioShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllPlanoUsuariosByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        planoUsuarioRepository.saveAndFlush(planoUsuario);

        // Get all the planoUsuarioList where created equals to DEFAULT_CREATED
        defaultPlanoUsuarioShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the planoUsuarioList where created equals to UPDATED_CREATED
        defaultPlanoUsuarioShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllPlanoUsuariosByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        planoUsuarioRepository.saveAndFlush(planoUsuario);

        // Get all the planoUsuarioList where created not equals to DEFAULT_CREATED
        defaultPlanoUsuarioShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the planoUsuarioList where created not equals to UPDATED_CREATED
        defaultPlanoUsuarioShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllPlanoUsuariosByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        planoUsuarioRepository.saveAndFlush(planoUsuario);

        // Get all the planoUsuarioList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultPlanoUsuarioShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the planoUsuarioList where created equals to UPDATED_CREATED
        defaultPlanoUsuarioShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllPlanoUsuariosByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoUsuarioRepository.saveAndFlush(planoUsuario);

        // Get all the planoUsuarioList where created is not null
        defaultPlanoUsuarioShouldBeFound("created.specified=true");

        // Get all the planoUsuarioList where created is null
        defaultPlanoUsuarioShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlanoUsuariosByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        planoUsuarioRepository.saveAndFlush(planoUsuario);

        // Get all the planoUsuarioList where updated equals to DEFAULT_UPDATED
        defaultPlanoUsuarioShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the planoUsuarioList where updated equals to UPDATED_UPDATED
        defaultPlanoUsuarioShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllPlanoUsuariosByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        planoUsuarioRepository.saveAndFlush(planoUsuario);

        // Get all the planoUsuarioList where updated not equals to DEFAULT_UPDATED
        defaultPlanoUsuarioShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the planoUsuarioList where updated not equals to UPDATED_UPDATED
        defaultPlanoUsuarioShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllPlanoUsuariosByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        planoUsuarioRepository.saveAndFlush(planoUsuario);

        // Get all the planoUsuarioList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultPlanoUsuarioShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the planoUsuarioList where updated equals to UPDATED_UPDATED
        defaultPlanoUsuarioShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllPlanoUsuariosByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        planoUsuarioRepository.saveAndFlush(planoUsuario);

        // Get all the planoUsuarioList where updated is not null
        defaultPlanoUsuarioShouldBeFound("updated.specified=true");

        // Get all the planoUsuarioList where updated is null
        defaultPlanoUsuarioShouldNotBeFound("updated.specified=false");
    }

    @Test
    @Transactional
    public void getAllPlanoUsuariosByPlanoIsEqualToSomething() throws Exception {
        // Initialize the database
        planoUsuarioRepository.saveAndFlush(planoUsuario);
        Plano plano = PlanoResourceIT.createEntity(em);
        em.persist(plano);
        em.flush();
        planoUsuario.setPlano(plano);
        planoUsuarioRepository.saveAndFlush(planoUsuario);
        Long planoId = plano.getId();

        // Get all the planoUsuarioList where plano equals to planoId
        defaultPlanoUsuarioShouldBeFound("planoId.equals=" + planoId);

        // Get all the planoUsuarioList where plano equals to planoId + 1
        defaultPlanoUsuarioShouldNotBeFound("planoId.equals=" + (planoId + 1));
    }


    @Test
    @Transactional
    public void getAllPlanoUsuariosByUsuarioIsEqualToSomething() throws Exception {
        // Initialize the database
        planoUsuarioRepository.saveAndFlush(planoUsuario);
        Usuario usuario = UsuarioResourceIT.createEntity(em);
        em.persist(usuario);
        em.flush();
        planoUsuario.setUsuario(usuario);
        planoUsuarioRepository.saveAndFlush(planoUsuario);
        Long usuarioId = usuario.getId();

        // Get all the planoUsuarioList where usuario equals to usuarioId
        defaultPlanoUsuarioShouldBeFound("usuarioId.equals=" + usuarioId);

        // Get all the planoUsuarioList where usuario equals to usuarioId + 1
        defaultPlanoUsuarioShouldNotBeFound("usuarioId.equals=" + (usuarioId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPlanoUsuarioShouldBeFound(String filter) throws Exception {
        restPlanoUsuarioMockMvc.perform(get("/api/plano-usuarios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planoUsuario.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restPlanoUsuarioMockMvc.perform(get("/api/plano-usuarios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPlanoUsuarioShouldNotBeFound(String filter) throws Exception {
        restPlanoUsuarioMockMvc.perform(get("/api/plano-usuarios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPlanoUsuarioMockMvc.perform(get("/api/plano-usuarios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
