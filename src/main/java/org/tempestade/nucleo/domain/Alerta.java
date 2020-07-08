package org.tempestade.nucleo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Alerta.
 */
@Entity
@Table(name = "alerta")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Alerta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "nome", length = 255, nullable = false, unique = true)
    private String nome;

    @Size(max = 255)
    @Column(name = "contato", length = 255)
    private String contato;

    @Size(min = 8, max = 8)
    @Pattern(regexp = "^(([0-1]\\d)|(2[0-3])):([0-5]\\d):([0-5]\\d)$")
    @Column(name = "duracao", length = 8)
    private String duracao;

    @Column(name = "automatico")
    private Boolean automatico;

    @Column(name = "critico")
    private Boolean critico;

    @Size(max = 255)
    @Column(name = "observacao", length = 255)
    private String observacao;

    @Column(name = "alerta_pai_id")
    private Integer alertaPaiId;

    @NotNull
    @Column(name = "created", nullable = false)
    private Instant created;

    @Column(name = "updated")
    private Instant updated;

    @ManyToOne
    @JsonIgnoreProperties(value = "alertas", allowSetters = true)
    private PlanoRecurso planoRecurso;

    @ManyToOne
    @JsonIgnoreProperties(value = "alertas", allowSetters = true)
    private Alvo alvo;

    @ManyToOne
    @JsonIgnoreProperties(value = "alertas", allowSetters = true)
    private Usuario usuario;

    @ManyToOne
    @JsonIgnoreProperties(value = "alertas", allowSetters = true)
    private AlertaRisco alertaRisco;

    @ManyToOne
    @JsonIgnoreProperties(value = "alertas", allowSetters = true)
    private TempestadeNivel tempestadeNivel;

    @ManyToOne
    @JsonIgnoreProperties(value = "alertas", allowSetters = true)
    private AlertaTipo alertaTipo;

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

    public Alerta nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getContato() {
        return contato;
    }

    public Alerta contato(String contato) {
        this.contato = contato;
        return this;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getDuracao() {
        return duracao;
    }

    public Alerta duracao(String duracao) {
        this.duracao = duracao;
        return this;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public Boolean isAutomatico() {
        return automatico;
    }

    public Alerta automatico(Boolean automatico) {
        this.automatico = automatico;
        return this;
    }

    public void setAutomatico(Boolean automatico) {
        this.automatico = automatico;
    }

    public Boolean isCritico() {
        return critico;
    }

    public Alerta critico(Boolean critico) {
        this.critico = critico;
        return this;
    }

    public void setCritico(Boolean critico) {
        this.critico = critico;
    }

    public String getObservacao() {
        return observacao;
    }

    public Alerta observacao(String observacao) {
        this.observacao = observacao;
        return this;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Integer getAlertaPaiId() {
        return alertaPaiId;
    }

    public Alerta alertaPaiId(Integer alertaPaiId) {
        this.alertaPaiId = alertaPaiId;
        return this;
    }

    public void setAlertaPaiId(Integer alertaPaiId) {
        this.alertaPaiId = alertaPaiId;
    }

    public Instant getCreated() {
        return created;
    }

    public Alerta created(Instant created) {
        this.created = created;
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getUpdated() {
        return updated;
    }

    public Alerta updated(Instant updated) {
        this.updated = updated;
        return this;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    public PlanoRecurso getPlanoRecurso() {
        return planoRecurso;
    }

    public Alerta planoRecurso(PlanoRecurso planoRecurso) {
        this.planoRecurso = planoRecurso;
        return this;
    }

    public void setPlanoRecurso(PlanoRecurso planoRecurso) {
        this.planoRecurso = planoRecurso;
    }

    public Alvo getAlvo() {
        return alvo;
    }

    public Alerta alvo(Alvo alvo) {
        this.alvo = alvo;
        return this;
    }

    public void setAlvo(Alvo alvo) {
        this.alvo = alvo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Alerta usuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public AlertaRisco getAlertaRisco() {
        return alertaRisco;
    }

    public Alerta alertaRisco(AlertaRisco alertaRisco) {
        this.alertaRisco = alertaRisco;
        return this;
    }

    public void setAlertaRisco(AlertaRisco alertaRisco) {
        this.alertaRisco = alertaRisco;
    }

    public TempestadeNivel getTempestadeNivel() {
        return tempestadeNivel;
    }

    public Alerta tempestadeNivel(TempestadeNivel tempestadeNivel) {
        this.tempestadeNivel = tempestadeNivel;
        return this;
    }

    public void setTempestadeNivel(TempestadeNivel tempestadeNivel) {
        this.tempestadeNivel = tempestadeNivel;
    }

    public AlertaTipo getAlertaTipo() {
        return alertaTipo;
    }

    public Alerta alertaTipo(AlertaTipo alertaTipo) {
        this.alertaTipo = alertaTipo;
        return this;
    }

    public void setAlertaTipo(AlertaTipo alertaTipo) {
        this.alertaTipo = alertaTipo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Alerta)) {
            return false;
        }
        return id != null && id.equals(((Alerta) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Alerta{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", contato='" + getContato() + "'" +
            ", duracao='" + getDuracao() + "'" +
            ", automatico='" + isAutomatico() + "'" +
            ", critico='" + isCritico() + "'" +
            ", observacao='" + getObservacao() + "'" +
            ", alertaPaiId=" + getAlertaPaiId() +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            "}";
    }
}
