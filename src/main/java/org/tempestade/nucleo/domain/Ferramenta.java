package org.tempestade.nucleo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Ferramenta.
 */
@Entity
@Table(name = "ferramenta")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Ferramenta implements Serializable {

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
    @Column(name = "created", nullable = false)
    private Instant created;

    @Column(name = "updated")
    private Integer updated;

    @ManyToOne
    @JsonIgnoreProperties(value = "ferramentas", allowSetters = true)
    private TipoFerramenta tipoFerramenta;

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

    public Ferramenta nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Ferramenta descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Instant getCreated() {
        return created;
    }

    public Ferramenta created(Instant created) {
        this.created = created;
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Integer getUpdated() {
        return updated;
    }

    public Ferramenta updated(Integer updated) {
        this.updated = updated;
        return this;
    }

    public void setUpdated(Integer updated) {
        this.updated = updated;
    }

    public TipoFerramenta getTipoFerramenta() {
        return tipoFerramenta;
    }

    public Ferramenta tipoFerramenta(TipoFerramenta tipoFerramenta) {
        this.tipoFerramenta = tipoFerramenta;
        return this;
    }

    public void setTipoFerramenta(TipoFerramenta tipoFerramenta) {
        this.tipoFerramenta = tipoFerramenta;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ferramenta)) {
            return false;
        }
        return id != null && id.equals(((Ferramenta) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ferramenta{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", created='" + getCreated() + "'" +
            ", updated=" + getUpdated() +
            "}";
    }
}
