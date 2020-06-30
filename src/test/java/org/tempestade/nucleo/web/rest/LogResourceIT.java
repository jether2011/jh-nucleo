package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.Log;
import org.tempestade.nucleo.repository.LogRepository;
import org.tempestade.nucleo.service.LogService;
import org.tempestade.nucleo.service.dto.LogDTO;
import org.tempestade.nucleo.service.mapper.LogMapper;
import org.tempestade.nucleo.service.dto.LogCriteria;
import org.tempestade.nucleo.service.LogQueryService;

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
 * Integration tests for the {@link LogResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class LogResourceIT {

    private static final String DEFAULT_MESSAGEM = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGEM = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private LogMapper logMapper;

    @Autowired
    private LogService logService;

    @Autowired
    private LogQueryService logQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLogMockMvc;

    private Log log;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Log createEntity(EntityManager em) {
        Log log = new Log()
            .messagem(DEFAULT_MESSAGEM)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return log;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Log createUpdatedEntity(EntityManager em) {
        Log log = new Log()
            .messagem(UPDATED_MESSAGEM)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return log;
    }

    @BeforeEach
    public void initTest() {
        log = createEntity(em);
    }

    @Test
    @Transactional
    public void createLog() throws Exception {
        int databaseSizeBeforeCreate = logRepository.findAll().size();
        // Create the Log
        LogDTO logDTO = logMapper.toDto(log);
        restLogMockMvc.perform(post("/api/logs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logDTO)))
            .andExpect(status().isCreated());

        // Validate the Log in the database
        List<Log> logList = logRepository.findAll();
        assertThat(logList).hasSize(databaseSizeBeforeCreate + 1);
        Log testLog = logList.get(logList.size() - 1);
        assertThat(testLog.getMessagem()).isEqualTo(DEFAULT_MESSAGEM);
        assertThat(testLog.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testLog.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = logRepository.findAll().size();

        // Create the Log with an existing ID
        log.setId(1L);
        LogDTO logDTO = logMapper.toDto(log);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLogMockMvc.perform(post("/api/logs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Log in the database
        List<Log> logList = logRepository.findAll();
        assertThat(logList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = logRepository.findAll().size();
        // set the field null
        log.setCreated(null);

        // Create the Log, which fails.
        LogDTO logDTO = logMapper.toDto(log);


        restLogMockMvc.perform(post("/api/logs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logDTO)))
            .andExpect(status().isBadRequest());

        List<Log> logList = logRepository.findAll();
        assertThat(logList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLogs() throws Exception {
        // Initialize the database
        logRepository.saveAndFlush(log);

        // Get all the logList
        restLogMockMvc.perform(get("/api/logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(log.getId().intValue())))
            .andExpect(jsonPath("$.[*].messagem").value(hasItem(DEFAULT_MESSAGEM)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getLog() throws Exception {
        // Initialize the database
        logRepository.saveAndFlush(log);

        // Get the log
        restLogMockMvc.perform(get("/api/logs/{id}", log.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(log.getId().intValue()))
            .andExpect(jsonPath("$.messagem").value(DEFAULT_MESSAGEM))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }


    @Test
    @Transactional
    public void getLogsByIdFiltering() throws Exception {
        // Initialize the database
        logRepository.saveAndFlush(log);

        Long id = log.getId();

        defaultLogShouldBeFound("id.equals=" + id);
        defaultLogShouldNotBeFound("id.notEquals=" + id);

        defaultLogShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLogShouldNotBeFound("id.greaterThan=" + id);

        defaultLogShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLogShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllLogsByMessagemIsEqualToSomething() throws Exception {
        // Initialize the database
        logRepository.saveAndFlush(log);

        // Get all the logList where messagem equals to DEFAULT_MESSAGEM
        defaultLogShouldBeFound("messagem.equals=" + DEFAULT_MESSAGEM);

        // Get all the logList where messagem equals to UPDATED_MESSAGEM
        defaultLogShouldNotBeFound("messagem.equals=" + UPDATED_MESSAGEM);
    }

    @Test
    @Transactional
    public void getAllLogsByMessagemIsNotEqualToSomething() throws Exception {
        // Initialize the database
        logRepository.saveAndFlush(log);

        // Get all the logList where messagem not equals to DEFAULT_MESSAGEM
        defaultLogShouldNotBeFound("messagem.notEquals=" + DEFAULT_MESSAGEM);

        // Get all the logList where messagem not equals to UPDATED_MESSAGEM
        defaultLogShouldBeFound("messagem.notEquals=" + UPDATED_MESSAGEM);
    }

    @Test
    @Transactional
    public void getAllLogsByMessagemIsInShouldWork() throws Exception {
        // Initialize the database
        logRepository.saveAndFlush(log);

        // Get all the logList where messagem in DEFAULT_MESSAGEM or UPDATED_MESSAGEM
        defaultLogShouldBeFound("messagem.in=" + DEFAULT_MESSAGEM + "," + UPDATED_MESSAGEM);

        // Get all the logList where messagem equals to UPDATED_MESSAGEM
        defaultLogShouldNotBeFound("messagem.in=" + UPDATED_MESSAGEM);
    }

    @Test
    @Transactional
    public void getAllLogsByMessagemIsNullOrNotNull() throws Exception {
        // Initialize the database
        logRepository.saveAndFlush(log);

        // Get all the logList where messagem is not null
        defaultLogShouldBeFound("messagem.specified=true");

        // Get all the logList where messagem is null
        defaultLogShouldNotBeFound("messagem.specified=false");
    }
                @Test
    @Transactional
    public void getAllLogsByMessagemContainsSomething() throws Exception {
        // Initialize the database
        logRepository.saveAndFlush(log);

        // Get all the logList where messagem contains DEFAULT_MESSAGEM
        defaultLogShouldBeFound("messagem.contains=" + DEFAULT_MESSAGEM);

        // Get all the logList where messagem contains UPDATED_MESSAGEM
        defaultLogShouldNotBeFound("messagem.contains=" + UPDATED_MESSAGEM);
    }

    @Test
    @Transactional
    public void getAllLogsByMessagemNotContainsSomething() throws Exception {
        // Initialize the database
        logRepository.saveAndFlush(log);

        // Get all the logList where messagem does not contain DEFAULT_MESSAGEM
        defaultLogShouldNotBeFound("messagem.doesNotContain=" + DEFAULT_MESSAGEM);

        // Get all the logList where messagem does not contain UPDATED_MESSAGEM
        defaultLogShouldBeFound("messagem.doesNotContain=" + UPDATED_MESSAGEM);
    }


    @Test
    @Transactional
    public void getAllLogsByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        logRepository.saveAndFlush(log);

        // Get all the logList where created equals to DEFAULT_CREATED
        defaultLogShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the logList where created equals to UPDATED_CREATED
        defaultLogShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllLogsByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        logRepository.saveAndFlush(log);

        // Get all the logList where created not equals to DEFAULT_CREATED
        defaultLogShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the logList where created not equals to UPDATED_CREATED
        defaultLogShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllLogsByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        logRepository.saveAndFlush(log);

        // Get all the logList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultLogShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the logList where created equals to UPDATED_CREATED
        defaultLogShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllLogsByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        logRepository.saveAndFlush(log);

        // Get all the logList where created is not null
        defaultLogShouldBeFound("created.specified=true");

        // Get all the logList where created is null
        defaultLogShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllLogsByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        logRepository.saveAndFlush(log);

        // Get all the logList where updated equals to DEFAULT_UPDATED
        defaultLogShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the logList where updated equals to UPDATED_UPDATED
        defaultLogShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllLogsByUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        logRepository.saveAndFlush(log);

        // Get all the logList where updated not equals to DEFAULT_UPDATED
        defaultLogShouldNotBeFound("updated.notEquals=" + DEFAULT_UPDATED);

        // Get all the logList where updated not equals to UPDATED_UPDATED
        defaultLogShouldBeFound("updated.notEquals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllLogsByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        logRepository.saveAndFlush(log);

        // Get all the logList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultLogShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the logList where updated equals to UPDATED_UPDATED
        defaultLogShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllLogsByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        logRepository.saveAndFlush(log);

        // Get all the logList where updated is not null
        defaultLogShouldBeFound("updated.specified=true");

        // Get all the logList where updated is null
        defaultLogShouldNotBeFound("updated.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLogShouldBeFound(String filter) throws Exception {
        restLogMockMvc.perform(get("/api/logs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(log.getId().intValue())))
            .andExpect(jsonPath("$.[*].messagem").value(hasItem(DEFAULT_MESSAGEM)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));

        // Check, that the count call also returns 1
        restLogMockMvc.perform(get("/api/logs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLogShouldNotBeFound(String filter) throws Exception {
        restLogMockMvc.perform(get("/api/logs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLogMockMvc.perform(get("/api/logs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingLog() throws Exception {
        // Get the log
        restLogMockMvc.perform(get("/api/logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLog() throws Exception {
        // Initialize the database
        logRepository.saveAndFlush(log);

        int databaseSizeBeforeUpdate = logRepository.findAll().size();

        // Update the log
        Log updatedLog = logRepository.findById(log.getId()).get();
        // Disconnect from session so that the updates on updatedLog are not directly saved in db
        em.detach(updatedLog);
        updatedLog
            .messagem(UPDATED_MESSAGEM)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        LogDTO logDTO = logMapper.toDto(updatedLog);

        restLogMockMvc.perform(put("/api/logs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logDTO)))
            .andExpect(status().isOk());

        // Validate the Log in the database
        List<Log> logList = logRepository.findAll();
        assertThat(logList).hasSize(databaseSizeBeforeUpdate);
        Log testLog = logList.get(logList.size() - 1);
        assertThat(testLog.getMessagem()).isEqualTo(UPDATED_MESSAGEM);
        assertThat(testLog.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testLog.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingLog() throws Exception {
        int databaseSizeBeforeUpdate = logRepository.findAll().size();

        // Create the Log
        LogDTO logDTO = logMapper.toDto(log);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLogMockMvc.perform(put("/api/logs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Log in the database
        List<Log> logList = logRepository.findAll();
        assertThat(logList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLog() throws Exception {
        // Initialize the database
        logRepository.saveAndFlush(log);

        int databaseSizeBeforeDelete = logRepository.findAll().size();

        // Delete the log
        restLogMockMvc.perform(delete("/api/logs/{id}", log.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Log> logList = logRepository.findAll();
        assertThat(logList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
