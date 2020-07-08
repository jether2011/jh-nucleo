package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.Rede;
import org.tempestade.nucleo.repository.RedeRepository;
import org.tempestade.nucleo.service.RedeService;
import org.tempestade.nucleo.service.dto.RedeDTO;
import org.tempestade.nucleo.service.mapper.RedeMapper;

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
 * Integration tests for the {@link RedeResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class RedeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private RedeRepository redeRepository;

    @Autowired
    private RedeMapper redeMapper;

    @Autowired
    private RedeService redeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRedeMockMvc;

    private Rede rede;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rede createEntity(EntityManager em) {
        Rede rede = new Rede()
            .name(DEFAULT_NAME)
            .descricao(DEFAULT_DESCRICAO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return rede;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rede createUpdatedEntity(EntityManager em) {
        Rede rede = new Rede()
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return rede;
    }

    @BeforeEach
    public void initTest() {
        rede = createEntity(em);
    }

    @Test
    @Transactional
    public void createRede() throws Exception {
        int databaseSizeBeforeCreate = redeRepository.findAll().size();
        // Create the Rede
        RedeDTO redeDTO = redeMapper.toDto(rede);
        restRedeMockMvc.perform(post("/api/redes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(redeDTO)))
            .andExpect(status().isCreated());

        // Validate the Rede in the database
        List<Rede> redeList = redeRepository.findAll();
        assertThat(redeList).hasSize(databaseSizeBeforeCreate + 1);
        Rede testRede = redeList.get(redeList.size() - 1);
        assertThat(testRede.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRede.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testRede.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testRede.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createRedeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = redeRepository.findAll().size();

        // Create the Rede with an existing ID
        rede.setId(1L);
        RedeDTO redeDTO = redeMapper.toDto(rede);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRedeMockMvc.perform(post("/api/redes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(redeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Rede in the database
        List<Rede> redeList = redeRepository.findAll();
        assertThat(redeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = redeRepository.findAll().size();
        // set the field null
        rede.setName(null);

        // Create the Rede, which fails.
        RedeDTO redeDTO = redeMapper.toDto(rede);


        restRedeMockMvc.perform(post("/api/redes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(redeDTO)))
            .andExpect(status().isBadRequest());

        List<Rede> redeList = redeRepository.findAll();
        assertThat(redeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = redeRepository.findAll().size();
        // set the field null
        rede.setDescricao(null);

        // Create the Rede, which fails.
        RedeDTO redeDTO = redeMapper.toDto(rede);


        restRedeMockMvc.perform(post("/api/redes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(redeDTO)))
            .andExpect(status().isBadRequest());

        List<Rede> redeList = redeRepository.findAll();
        assertThat(redeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = redeRepository.findAll().size();
        // set the field null
        rede.setCreated(null);

        // Create the Rede, which fails.
        RedeDTO redeDTO = redeMapper.toDto(rede);


        restRedeMockMvc.perform(post("/api/redes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(redeDTO)))
            .andExpect(status().isBadRequest());

        List<Rede> redeList = redeRepository.findAll();
        assertThat(redeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRedes() throws Exception {
        // Initialize the database
        redeRepository.saveAndFlush(rede);

        // Get all the redeList
        restRedeMockMvc.perform(get("/api/redes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rede.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getRede() throws Exception {
        // Initialize the database
        redeRepository.saveAndFlush(rede);

        // Get the rede
        restRedeMockMvc.perform(get("/api/redes/{id}", rede.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rede.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingRede() throws Exception {
        // Get the rede
        restRedeMockMvc.perform(get("/api/redes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRede() throws Exception {
        // Initialize the database
        redeRepository.saveAndFlush(rede);

        int databaseSizeBeforeUpdate = redeRepository.findAll().size();

        // Update the rede
        Rede updatedRede = redeRepository.findById(rede.getId()).get();
        // Disconnect from session so that the updates on updatedRede are not directly saved in db
        em.detach(updatedRede);
        updatedRede
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        RedeDTO redeDTO = redeMapper.toDto(updatedRede);

        restRedeMockMvc.perform(put("/api/redes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(redeDTO)))
            .andExpect(status().isOk());

        // Validate the Rede in the database
        List<Rede> redeList = redeRepository.findAll();
        assertThat(redeList).hasSize(databaseSizeBeforeUpdate);
        Rede testRede = redeList.get(redeList.size() - 1);
        assertThat(testRede.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRede.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testRede.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testRede.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingRede() throws Exception {
        int databaseSizeBeforeUpdate = redeRepository.findAll().size();

        // Create the Rede
        RedeDTO redeDTO = redeMapper.toDto(rede);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRedeMockMvc.perform(put("/api/redes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(redeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Rede in the database
        List<Rede> redeList = redeRepository.findAll();
        assertThat(redeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRede() throws Exception {
        // Initialize the database
        redeRepository.saveAndFlush(rede);

        int databaseSizeBeforeDelete = redeRepository.findAll().size();

        // Delete the rede
        restRedeMockMvc.perform(delete("/api/redes/{id}", rede.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Rede> redeList = redeRepository.findAll();
        assertThat(redeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
