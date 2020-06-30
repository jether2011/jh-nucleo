package org.tempestade.nucleo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A AlertaFerramenta.
 */
@Entity
@Table(name = "alerta_ferramenta")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AlertaFerramenta implements Serializable {

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
    private Instant updated;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "alertaFerramentas", allowSetters = true)
    private Alerta alerta;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "alertaFerramentas", allowSetters = true)
    private Ferramenta ferramenta;

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

    public AlertaFerramenta nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public AlertaFerramenta descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Instant getCreated() {
        return created;
    }

    public AlertaFerramenta created(Instant created) {
        this.created = created;
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getUpdated() {
        return updated;
    }

    public AlertaFerramenta updated(Instant updated) {
        this.updated = updated;
        return this;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    public Alerta getAlerta() {
        return alerta;
    }

    public AlertaFerramenta alerta(Alerta alerta) {
        this.alerta = alerta;
        return this;
    }

    public void setAlerta(Alerta alerta) {
        this.alerta = alerta;
    }

    public Ferramenta getFerramenta() {
        return ferramenta;
    }

    public AlertaFerramenta ferramenta(Ferramenta ferramenta) {
        this.ferramenta = ferramenta;
        return this;
    }

    public void setFerramenta(Ferramenta ferramenta) {
        this.ferramenta = ferramenta;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AlertaFerramenta)) {
            return false;
        }
        return id != null && id.equals(((AlertaFerramenta) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AlertaFerramenta{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            "}";
    }
}
