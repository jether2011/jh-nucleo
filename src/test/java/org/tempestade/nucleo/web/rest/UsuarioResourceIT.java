package org.tempestade.nucleo.web.rest;

import org.tempestade.nucleo.NucleoApp;
import org.tempestade.nucleo.domain.Usuario;
import org.tempestade.nucleo.repository.UsuarioRepository;
import org.tempestade.nucleo.service.UsuarioService;
import org.tempestade.nucleo.service.dto.UsuarioDTO;
import org.tempestade.nucleo.service.mapper.UsuarioMapper;

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
 * Integration tests for the {@link UsuarioResource} REST controller.
 */
@SpringBootTest(classes = NucleoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UsuarioResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_SENHA = "AAAAAAAAAA";
    private static final String UPDATED_SENHA = "BBBBBBBBBB";

    private static final String DEFAULT_CNPJ = "AAAAAAAAAA";
    private static final String UPDATED_CNPJ = "BBBBBBBBBB";

    private static final String DEFAULT_CPF = "AAAAAAAAAA";
    private static final String UPDATED_CPF = "BBBBBBBBBB";

    private static final String DEFAULT_CEP = "AAAAAAAAA";
    private static final String UPDATED_CEP = "BBBBBBBBB";

    private static final String DEFAULT_ENDERECO = "AAAAAAAAAA";
    private static final String UPDATED_ENDERECO = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERO = 1;
    private static final Integer UPDATED_NUMERO = 2;

    private static final String DEFAULT_BAIRRO = "AAAAAAAAAA";
    private static final String UPDATED_BAIRRO = "BBBBBBBBBB";

    private static final String DEFAULT_CIDADE = "AAAAAAAAAA";
    private static final String UPDATED_CIDADE = "BBBBBBBBBB";

    private static final String DEFAULT_ESTADO = "AA";
    private static final String UPDATED_ESTADO = "BB";

    private static final String DEFAULT_TELEFONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE = "BBBBBBBBBB";

    private static final String DEFAULT_FAX = "AAAAAAAAAA";
    private static final String UPDATED_FAX = "BBBBBBBBBB";

    private static final String DEFAULT_CELULAR = "AAAAAAAAAA";
    private static final String UPDATED_CELULAR = "BBBBBBBBBB";

    private static final String DEFAULT_DETALHE = "AAAAAAAAAA";
    private static final String UPDATED_DETALHE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_BLOQUEADO = false;
    private static final Boolean UPDATED_BLOQUEADO = true;

    private static final String DEFAULT_COMPLEMENTO = "AAAAAAAAAA";
    private static final String UPDATED_COMPLEMENTO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_NAO_PODE_EXCLUIR = false;
    private static final Boolean UPDATED_NAO_PODE_EXCLUIR = true;

    private static final Instant DEFAULT_ULTIMO_ACESSO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ULTIMO_ACESSO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_SENHA_FIREBASE = "AAAAAAAAAA";
    private static final String UPDATED_SENHA_FIREBASE = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUsuarioMockMvc;

    private Usuario usuario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Usuario createEntity(EntityManager em) {
        Usuario usuario = new Usuario()
            .name(DEFAULT_NAME)
            .descricao(DEFAULT_DESCRICAO)
            .email(DEFAULT_EMAIL)
            .senha(DEFAULT_SENHA)
            .cnpj(DEFAULT_CNPJ)
            .cpf(DEFAULT_CPF)
            .cep(DEFAULT_CEP)
            .endereco(DEFAULT_ENDERECO)
            .numero(DEFAULT_NUMERO)
            .bairro(DEFAULT_BAIRRO)
            .cidade(DEFAULT_CIDADE)
            .estado(DEFAULT_ESTADO)
            .telefone(DEFAULT_TELEFONE)
            .fax(DEFAULT_FAX)
            .celular(DEFAULT_CELULAR)
            .detalhe(DEFAULT_DETALHE)
            .bloqueado(DEFAULT_BLOQUEADO)
            .complemento(DEFAULT_COMPLEMENTO)
            .naoPodeExcluir(DEFAULT_NAO_PODE_EXCLUIR)
            .ultimoAcesso(DEFAULT_ULTIMO_ACESSO)
            .senhaFirebase(DEFAULT_SENHA_FIREBASE)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return usuario;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Usuario createUpdatedEntity(EntityManager em) {
        Usuario usuario = new Usuario()
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .email(UPDATED_EMAIL)
            .senha(UPDATED_SENHA)
            .cnpj(UPDATED_CNPJ)
            .cpf(UPDATED_CPF)
            .cep(UPDATED_CEP)
            .endereco(UPDATED_ENDERECO)
            .numero(UPDATED_NUMERO)
            .bairro(UPDATED_BAIRRO)
            .cidade(UPDATED_CIDADE)
            .estado(UPDATED_ESTADO)
            .telefone(UPDATED_TELEFONE)
            .fax(UPDATED_FAX)
            .celular(UPDATED_CELULAR)
            .detalhe(UPDATED_DETALHE)
            .bloqueado(UPDATED_BLOQUEADO)
            .complemento(UPDATED_COMPLEMENTO)
            .naoPodeExcluir(UPDATED_NAO_PODE_EXCLUIR)
            .ultimoAcesso(UPDATED_ULTIMO_ACESSO)
            .senhaFirebase(UPDATED_SENHA_FIREBASE)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return usuario;
    }

    @BeforeEach
    public void initTest() {
        usuario = createEntity(em);
    }

    @Test
    @Transactional
    public void createUsuario() throws Exception {
        int databaseSizeBeforeCreate = usuarioRepository.findAll().size();
        // Create the Usuario
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);
        restUsuarioMockMvc.perform(post("/api/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuarioDTO)))
            .andExpect(status().isCreated());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeCreate + 1);
        Usuario testUsuario = usuarioList.get(usuarioList.size() - 1);
        assertThat(testUsuario.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUsuario.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testUsuario.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testUsuario.getSenha()).isEqualTo(DEFAULT_SENHA);
        assertThat(testUsuario.getCnpj()).isEqualTo(DEFAULT_CNPJ);
        assertThat(testUsuario.getCpf()).isEqualTo(DEFAULT_CPF);
        assertThat(testUsuario.getCep()).isEqualTo(DEFAULT_CEP);
        assertThat(testUsuario.getEndereco()).isEqualTo(DEFAULT_ENDERECO);
        assertThat(testUsuario.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testUsuario.getBairro()).isEqualTo(DEFAULT_BAIRRO);
        assertThat(testUsuario.getCidade()).isEqualTo(DEFAULT_CIDADE);
        assertThat(testUsuario.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testUsuario.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
        assertThat(testUsuario.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testUsuario.getCelular()).isEqualTo(DEFAULT_CELULAR);
        assertThat(testUsuario.getDetalhe()).isEqualTo(DEFAULT_DETALHE);
        assertThat(testUsuario.isBloqueado()).isEqualTo(DEFAULT_BLOQUEADO);
        assertThat(testUsuario.getComplemento()).isEqualTo(DEFAULT_COMPLEMENTO);
        assertThat(testUsuario.isNaoPodeExcluir()).isEqualTo(DEFAULT_NAO_PODE_EXCLUIR);
        assertThat(testUsuario.getUltimoAcesso()).isEqualTo(DEFAULT_ULTIMO_ACESSO);
        assertThat(testUsuario.getSenhaFirebase()).isEqualTo(DEFAULT_SENHA_FIREBASE);
        assertThat(testUsuario.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testUsuario.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createUsuarioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = usuarioRepository.findAll().size();

        // Create the Usuario with an existing ID
        usuario.setId(1L);
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsuarioMockMvc.perform(post("/api/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuarioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuarioRepository.findAll().size();
        // set the field null
        usuario.setName(null);

        // Create the Usuario, which fails.
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);


        restUsuarioMockMvc.perform(post("/api/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuarioDTO)))
            .andExpect(status().isBadRequest());

        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuarioRepository.findAll().size();
        // set the field null
        usuario.setDescricao(null);

        // Create the Usuario, which fails.
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);


        restUsuarioMockMvc.perform(post("/api/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuarioDTO)))
            .andExpect(status().isBadRequest());

        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuarioRepository.findAll().size();
        // set the field null
        usuario.setEmail(null);

        // Create the Usuario, which fails.
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);


        restUsuarioMockMvc.perform(post("/api/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuarioDTO)))
            .andExpect(status().isBadRequest());

        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSenhaIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuarioRepository.findAll().size();
        // set the field null
        usuario.setSenha(null);

        // Create the Usuario, which fails.
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);


        restUsuarioMockMvc.perform(post("/api/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuarioDTO)))
            .andExpect(status().isBadRequest());

        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUltimoAcessoIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuarioRepository.findAll().size();
        // set the field null
        usuario.setUltimoAcesso(null);

        // Create the Usuario, which fails.
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);


        restUsuarioMockMvc.perform(post("/api/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuarioDTO)))
            .andExpect(status().isBadRequest());

        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuarioRepository.findAll().size();
        // set the field null
        usuario.setCreated(null);

        // Create the Usuario, which fails.
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);


        restUsuarioMockMvc.perform(post("/api/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuarioDTO)))
            .andExpect(status().isBadRequest());

        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUsuarios() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList
        restUsuarioMockMvc.perform(get("/api/usuarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usuario.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].senha").value(hasItem(DEFAULT_SENHA)))
            .andExpect(jsonPath("$.[*].cnpj").value(hasItem(DEFAULT_CNPJ)))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF)))
            .andExpect(jsonPath("$.[*].cep").value(hasItem(DEFAULT_CEP)))
            .andExpect(jsonPath("$.[*].endereco").value(hasItem(DEFAULT_ENDERECO)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].bairro").value(hasItem(DEFAULT_BAIRRO)))
            .andExpect(jsonPath("$.[*].cidade").value(hasItem(DEFAULT_CIDADE)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)))
            .andExpect(jsonPath("$.[*].celular").value(hasItem(DEFAULT_CELULAR)))
            .andExpect(jsonPath("$.[*].detalhe").value(hasItem(DEFAULT_DETALHE)))
            .andExpect(jsonPath("$.[*].bloqueado").value(hasItem(DEFAULT_BLOQUEADO.booleanValue())))
            .andExpect(jsonPath("$.[*].complemento").value(hasItem(DEFAULT_COMPLEMENTO)))
            .andExpect(jsonPath("$.[*].naoPodeExcluir").value(hasItem(DEFAULT_NAO_PODE_EXCLUIR.booleanValue())))
            .andExpect(jsonPath("$.[*].ultimoAcesso").value(hasItem(DEFAULT_ULTIMO_ACESSO.toString())))
            .andExpect(jsonPath("$.[*].senhaFirebase").value(hasItem(DEFAULT_SENHA_FIREBASE)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getUsuario() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get the usuario
        restUsuarioMockMvc.perform(get("/api/usuarios/{id}", usuario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(usuario.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.senha").value(DEFAULT_SENHA))
            .andExpect(jsonPath("$.cnpj").value(DEFAULT_CNPJ))
            .andExpect(jsonPath("$.cpf").value(DEFAULT_CPF))
            .andExpect(jsonPath("$.cep").value(DEFAULT_CEP))
            .andExpect(jsonPath("$.endereco").value(DEFAULT_ENDERECO))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.bairro").value(DEFAULT_BAIRRO))
            .andExpect(jsonPath("$.cidade").value(DEFAULT_CIDADE))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX))
            .andExpect(jsonPath("$.celular").value(DEFAULT_CELULAR))
            .andExpect(jsonPath("$.detalhe").value(DEFAULT_DETALHE))
            .andExpect(jsonPath("$.bloqueado").value(DEFAULT_BLOQUEADO.booleanValue()))
            .andExpect(jsonPath("$.complemento").value(DEFAULT_COMPLEMENTO))
            .andExpect(jsonPath("$.naoPodeExcluir").value(DEFAULT_NAO_PODE_EXCLUIR.booleanValue()))
            .andExpect(jsonPath("$.ultimoAcesso").value(DEFAULT_ULTIMO_ACESSO.toString()))
            .andExpect(jsonPath("$.senhaFirebase").value(DEFAULT_SENHA_FIREBASE))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingUsuario() throws Exception {
        // Get the usuario
        restUsuarioMockMvc.perform(get("/api/usuarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUsuario() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        int databaseSizeBeforeUpdate = usuarioRepository.findAll().size();

        // Update the usuario
        Usuario updatedUsuario = usuarioRepository.findById(usuario.getId()).get();
        // Disconnect from session so that the updates on updatedUsuario are not directly saved in db
        em.detach(updatedUsuario);
        updatedUsuario
            .name(UPDATED_NAME)
            .descricao(UPDATED_DESCRICAO)
            .email(UPDATED_EMAIL)
            .senha(UPDATED_SENHA)
            .cnpj(UPDATED_CNPJ)
            .cpf(UPDATED_CPF)
            .cep(UPDATED_CEP)
            .endereco(UPDATED_ENDERECO)
            .numero(UPDATED_NUMERO)
            .bairro(UPDATED_BAIRRO)
            .cidade(UPDATED_CIDADE)
            .estado(UPDATED_ESTADO)
            .telefone(UPDATED_TELEFONE)
            .fax(UPDATED_FAX)
            .celular(UPDATED_CELULAR)
            .detalhe(UPDATED_DETALHE)
            .bloqueado(UPDATED_BLOQUEADO)
            .complemento(UPDATED_COMPLEMENTO)
            .naoPodeExcluir(UPDATED_NAO_PODE_EXCLUIR)
            .ultimoAcesso(UPDATED_ULTIMO_ACESSO)
            .senhaFirebase(UPDATED_SENHA_FIREBASE)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(updatedUsuario);

        restUsuarioMockMvc.perform(put("/api/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuarioDTO)))
            .andExpect(status().isOk());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate);
        Usuario testUsuario = usuarioList.get(usuarioList.size() - 1);
        assertThat(testUsuario.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUsuario.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testUsuario.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testUsuario.getSenha()).isEqualTo(UPDATED_SENHA);
        assertThat(testUsuario.getCnpj()).isEqualTo(UPDATED_CNPJ);
        assertThat(testUsuario.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testUsuario.getCep()).isEqualTo(UPDATED_CEP);
        assertThat(testUsuario.getEndereco()).isEqualTo(UPDATED_ENDERECO);
        assertThat(testUsuario.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testUsuario.getBairro()).isEqualTo(UPDATED_BAIRRO);
        assertThat(testUsuario.getCidade()).isEqualTo(UPDATED_CIDADE);
        assertThat(testUsuario.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testUsuario.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testUsuario.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testUsuario.getCelular()).isEqualTo(UPDATED_CELULAR);
        assertThat(testUsuario.getDetalhe()).isEqualTo(UPDATED_DETALHE);
        assertThat(testUsuario.isBloqueado()).isEqualTo(UPDATED_BLOQUEADO);
        assertThat(testUsuario.getComplemento()).isEqualTo(UPDATED_COMPLEMENTO);
        assertThat(testUsuario.isNaoPodeExcluir()).isEqualTo(UPDATED_NAO_PODE_EXCLUIR);
        assertThat(testUsuario.getUltimoAcesso()).isEqualTo(UPDATED_ULTIMO_ACESSO);
        assertThat(testUsuario.getSenhaFirebase()).isEqualTo(UPDATED_SENHA_FIREBASE);
        assertThat(testUsuario.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testUsuario.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingUsuario() throws Exception {
        int databaseSizeBeforeUpdate = usuarioRepository.findAll().size();

        // Create the Usuario
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsuarioMockMvc.perform(put("/api/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuarioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUsuario() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        int databaseSizeBeforeDelete = usuarioRepository.findAll().size();

        // Delete the usuario
        restUsuarioMockMvc.perform(delete("/api/usuarios/{id}", usuario.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
