package org.tempestade.nucleo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A BoletimPrevVariavelMet.
 */
@Entity
@Table(name = "boletim_prev_variavel_met")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BoletimPrevVariavelMet implements Serializable {

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

    @ManyToOne
    @JsonIgnoreProperties(value = "boletimPrevVariavelMets", allowSetters = true)
    private BoletimPrevisao boletimPrevisao;

    @ManyToOne
    @JsonIgnoreProperties(value = "boletimPrevVariavelMets", allowSetters = true)
    private VariavelMeteorologica variavelMeteorologica;

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

    public BoletimPrevVariavelMet nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public BoletimPrevVariavelMet descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Instant getCreated() {
        return created;
    }

    public BoletimPrevVariavelMet created(Instant created) {
        this.created = created;
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getUpdated() {
        return updated;
    }

    public BoletimPrevVariavelMet updated(Instant updated) {
        this.updated = updated;
        return this;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    public BoletimPrevisao getBoletimPrevisao() {
        return boletimPrevisao;
    }

    public BoletimPrevVariavelMet boletimPrevisao(BoletimPrevisao boletimPrevisao) {
        this.boletimPrevisao = boletimPrevisao;
        return this;
    }

    public void setBoletimPrevisao(BoletimPrevisao boletimPrevisao) {
        this.boletimPrevisao = boletimPrevisao;
    }

    public VariavelMeteorologica getVariavelMeteorologica() {
        return variavelMeteorologica;
    }

    public BoletimPrevVariavelMet variavelMeteorologica(VariavelMeteorologica variavelMeteorologica) {
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
        if (!(o instanceof BoletimPrevVariavelMet)) {
            return false;
        }
        return id != null && id.equals(((BoletimPrevVariavelMet) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BoletimPrevVariavelMet{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            "}";
    }
}
