package org.tempestade.nucleo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Recurso.
 */
@Entity
@Table(name = "recurso")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Recurso implements Serializable {

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

    @Column(name = "ativo")
    private Boolean ativo;

    @NotNull
    @Column(name = "created", nullable = false)
    private Instant created;

    @Column(name = "updated")
    private Instant updated;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "recursos", allowSetters = true)
    private RecursoTipo recursoTipo;

    @ManyToOne
    @JsonIgnoreProperties(value = "recursos", allowSetters = true)
    private VariavelMeteorologica variavelMeteorologica;

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

    public Recurso name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescricao() {
        return descricao;
    }

    public Recurso descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public Recurso ativo(Boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Instant getCreated() {
        return created;
    }

    public Recurso created(Instant created) {
        this.created = created;
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getUpdated() {
        return updated;
    }

    public Recurso updated(Instant updated) {
        this.updated = updated;
        return this;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    public RecursoTipo getRecursoTipo() {
        return recursoTipo;
    }

    public Recurso recursoTipo(RecursoTipo recursoTipo) {
        this.recursoTipo = recursoTipo;
        return this;
    }

    public void setRecursoTipo(RecursoTipo recursoTipo) {
        this.recursoTipo = recursoTipo;
    }

    public VariavelMeteorologica getVariavelMeteorologica() {
        return variavelMeteorologica;
    }

    public Recurso variavelMeteorologica(VariavelMeteorologica variavelMeteorologica) {
        this.variavelMeteorologica = variavelMeteorologica;
        return this;
    }

    public void setVariavelMeteorologica(VariavelMeteorologica variavelMeteorologica) {
        this.variavelMeteorologica = variavelMeteorologica;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Recurso)) {
            return false;
        }
        return id != null && id.equals(((Recurso) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Recurso{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", ativo='" + isAtivo() + "'" +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            "}";
    }
}
