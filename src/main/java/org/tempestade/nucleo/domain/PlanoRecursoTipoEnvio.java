package org.tempestade.nucleo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A PlanoRecursoTipoEnvio.
 */
@Entity
@Table(name = "plano_recurso_tipo_envio")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PlanoRecursoTipoEnvio implements Serializable {

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

    @Column(name = "qtd")
    private Integer qtd;

    @NotNull
    @Column(name = "created", nullable = false)
    private Instant created;

    @Column(name = "updated")
    private Instant updated;

    @ManyToOne
    @JsonIgnoreProperties(value = "planoRecursoTipoEnvios", allowSetters = true)
    private PlanoRecurso planoRecurso;

    @ManyToOne
    @JsonIgnoreProperties(value = "planoRecursoTipoEnvios", allowSetters = true)
    private TipoEnvio tipoEnvio;

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

    public PlanoRecursoTipoEnvio name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescricao() {
        return descricao;
    }

    public PlanoRecursoTipoEnvio descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getQtd() {
        return qtd;
    }

    public PlanoRecursoTipoEnvio qtd(Integer qtd) {
        this.qtd = qtd;
        return this;
    }

    public void setQtd(Integer qtd) {
        this.qtd = qtd;
    }

    public Instant getCreated() {
        return created;
    }

    public PlanoRecursoTipoEnvio created(Instant created) {
        this.created = created;
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getUpdated() {
        return updated;
    }

    public PlanoRecursoTipoEnvio updated(Instant updated) {
        this.updated = updated;
        return this;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    public PlanoRecurso getPlanoRecurso() {
        return planoRecurso;
    }

    public PlanoRecursoTipoEnvio planoRecurso(PlanoRecurso planoRecurso) {
        this.planoRecurso = planoRecurso;
        return this;
    }

    public void setPlanoRecurso(PlanoRecurso planoRecurso) {
        this.planoRecurso = planoRecurso;
    }

    public TipoEnvio getTipoEnvio() {
        return tipoEnvio;
    }

    public PlanoRecursoTipoEnvio tipoEnvio(TipoEnvio tipoEnvio) {
        this.tipoEnvio = tipoEnvio;
        return this;
    }

    public void setTipoEnvio(TipoEnvio tipoEnvio) {
        this.tipoEnvio = tipoEnvio;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlanoRecursoTipoEnvio)) {
            return false;
        }
        return id != null && id.equals(((PlanoRecursoTipoEnvio) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlanoRecursoTipoEnvio{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", qtd=" + getQtd() +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            "}";
    }
}
