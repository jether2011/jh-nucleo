package org.tempestade.nucleo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A RecursoTemplate.
 */
@Entity
@Table(name = "recurso_template")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RecursoTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "name", length = 255, nullable = false, unique = true)
    private String name;

    @NotNull
    @Size(max = 255)
    @Column(name = "descricao", length = 255, nullable = false)
    private String descricao;

    @Size(max = 255)
    @Column(name = "template", length = 255)
    private String template;

    @NotNull
    @Column(name = "created", nullable = false)
    private Instant created;

    @Column(name = "updated")
    private Instant updated;

    @ManyToOne
    @JsonIgnoreProperties(value = "recursoTemplates", allowSetters = true)
    private Recurso recurso;

    @ManyToOne
    @JsonIgnoreProperties(value = "recursoTemplates", allowSetters = true)
    private TipoEnvio tipoEnvio;

    @ManyToOne
    @JsonIgnoreProperties(value = "recursoTemplates", allowSetters = true)
    private AlertaTipo alertaTipo;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public RecursoTemplate name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescricao() {
        return descricao;
    }

    public RecursoTemplate descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTemplate() {
        return template;
    }

    public RecursoTemplate template(String template) {
        this.template = template;
        return this;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Instant getCreated() {
        return created;
    }

    public RecursoTemplate created(Instant created) {
        this.created = created;
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getUpdated() {
        return updated;
    }

    public RecursoTemplate updated(Instant updated) {
        this.updated = updated;
        return this;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    public Recurso getRecurso() {
        return recurso;
    }

    public RecursoTemplate recurso(Recurso recurso) {
        this.recurso = recurso;
        return this;
    }

    public void setRecurso(Recurso recurso) {
        this.recurso = recurso;
    }

    public TipoEnvio getTipoEnvio() {
        return tipoEnvio;
    }

    public RecursoTemplate tipoEnvio(TipoEnvio tipoEnvio) {
        this.tipoEnvio = tipoEnvio;
        return this;
    }

    public void setTipoEnvio(TipoEnvio tipoEnvio) {
        this.tipoEnvio = tipoEnvio;
    }

    public AlertaTipo getAlertaTipo() {
        return alertaTipo;
    }

    public RecursoTemplate alertaTipo(AlertaTipo alertaTipo) {
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
        if (!(o instanceof RecursoTemplate)) {
            return false;
        }
        return id != null && id.equals(((RecursoTemplate) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RecursoTemplate{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", template='" + getTemplate() + "'" +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            "}";
    }
}
