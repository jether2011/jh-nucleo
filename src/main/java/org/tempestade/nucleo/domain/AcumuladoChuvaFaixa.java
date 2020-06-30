package org.tempestade.nucleo.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A AcumuladoChuvaFaixa.
 */
@Entity
@Table(name = "acumulado_chuva_faixa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AcumuladoChuvaFaixa implements Serializable {

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
    @Column(name = "de_mm", nullable = false)
    private Integer deMm;

    @NotNull
    @Column(name = "ate_mm", nullable = false)
    private Integer ateMm;

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

    public AcumuladoChuvaFaixa nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public AcumuladoChuvaFaixa descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getDeMm() {
        return deMm;
    }

    public AcumuladoChuvaFaixa deMm(Integer deMm) {
        this.deMm = deMm;
        return this;
    }

    public void setDeMm(Integer deMm) {
        this.deMm = deMm;
    }

    public Integer getAteMm() {
        return ateMm;
    }

    public AcumuladoChuvaFaixa ateMm(Integer ateMm) {
        this.ateMm = ateMm;
        return this;
    }

    public void setAteMm(Integer ateMm) {
        this.ateMm = ateMm;
    }

    public Instant getCreated() {
        return created;
    }

    public AcumuladoChuvaFaixa created(Instant created) {
        this.created = created;
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getUpdated() {
        return updated;
    }

    public AcumuladoChuvaFaixa updated(Instant updated) {
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
        if (!(o instanceof AcumuladoChuvaFaixa)) {
            return false;
        }
        return id != null && id.equals(((AcumuladoChuvaFaixa) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AcumuladoChuvaFaixa{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", deMm=" + getDeMm() +
            ", ateMm=" + getAteMm() +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            "}";
    }
}
