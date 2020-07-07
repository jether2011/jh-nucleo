package org.tempestade.nucleo.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Contato.
 */
@Entity
@Table(name = "contato")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Contato implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "nome", length = 255, nullable = false, unique = true)
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @NotNull
    @Size(max = 255)
    @Column(name = "email", length = 255, nullable = false)
    private String email;

    @Size(max = 20)
    @Column(name = "celular", length = 20)
    private String celular;

    @Column(name = "ativo")
    private Boolean ativo;

    @Column(name = "contato_alerta_telefonico")
    private Boolean contatoAlertaTelefonico;

    @Column(name = "prioridade")
    private Integer prioridade;

    @Size(min = 8, max = 8)
    @Pattern(regexp = "^(([0-1]\\d)|(2[0-3])):([0-5]\\d):([0-5]\\d)$")
    @Column(name = "hora_ligacao_inicial", length = 8)
    private String horaLigacaoInicial;

    @Size(min = 8, max = 8)
    @Pattern(regexp = "^(([0-1]\\d)|(2[0-3])):([0-5]\\d):([0-5]\\d)$")
    @Column(name = "hora_ligacao_final", length = 8)
    private String horaLigacaoFinal;

    @NotNull
    @Column(name = "created", nullable = false)
    private Instant created;

    @Column(name = "updated")
    private Instant updated;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Contato nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Contato descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEmail() {
        return email;
    }

    public Contato email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public Contato celular(String celular) {
        this.celular = celular;
        return this;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public Contato ativo(Boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Boolean isContatoAlertaTelefonico() {
        return contatoAlertaTelefonico;
    }

    public Contato contatoAlertaTelefonico(Boolean contatoAlertaTelefonico) {
        this.contatoAlertaTelefonico = contatoAlertaTelefonico;
        return this;
    }

    public void setContatoAlertaTelefonico(Boolean contatoAlertaTelefonico) {
        this.contatoAlertaTelefonico = contatoAlertaTelefonico;
    }

    public Integer getPrioridade() {
        return prioridade;
    }

    public Contato prioridade(Integer prioridade) {
        this.prioridade = prioridade;
        return this;
    }

    public void setPrioridade(Integer prioridade) {
        this.prioridade = prioridade;
    }

    public String getHoraLigacaoInicial() {
        return horaLigacaoInicial;
    }

    public Contato horaLigacaoInicial(String horaLigacaoInicial) {
        this.horaLigacaoInicial = horaLigacaoInicial;
        return this;
    }

    public void setHoraLigacaoInicial(String horaLigacaoInicial) {
        this.horaLigacaoInicial = horaLigacaoInicial;
    }

    public String getHoraLigacaoFinal() {
        return horaLigacaoFinal;
    }

    public Contato horaLigacaoFinal(String horaLigacaoFinal) {
        this.horaLigacaoFinal = horaLigacaoFinal;
        return this;
    }

    public void setHoraLigacaoFinal(String horaLigacaoFinal) {
        this.horaLigacaoFinal = horaLigacaoFinal;
    }

    public Instant getCreated() {
        return created;
    }

    public Contato created(Instant created) {
        this.created = created;
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getUpdated() {
        return updated;
    }

    public Contato updated(Instant updated) {
        this.updated = updated;
        return this;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contato)) {
            return false;
        }
        return id != null && id.equals(((Contato) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Contato{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", email='" + getEmail() + "'" +
            ", celular='" + getCelular() + "'" +
            ", ativo='" + isAtivo() + "'" +
            ", contatoAlertaTelefonico='" + isContatoAlertaTelefonico() + "'" +
            ", prioridade=" + getPrioridade() +
            ", horaLigacaoInicial='" + getHoraLigacaoInicial() + "'" +
            ", horaLigacaoFinal='" + getHoraLigacaoFinal() + "'" +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            "}";
    }
}
