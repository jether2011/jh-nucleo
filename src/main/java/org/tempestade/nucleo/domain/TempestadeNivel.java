package org.tempestade.nucleo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A TempestadeNivel.
 */
@Entity
@Table(name = "tempestade_nivel")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TempestadeNivel implements Serializable {

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
    @Column(name = "taxa_de_raios", length = 255)
    private String taxaDeRaios;

    @Size(max = 255)
    @Column(name = "ventos_velocidade", length = 255)
    private String ventosVelocidade;

    @Size(max = 255)
    @Column(name = "granizo", length = 255)
    private String granizo;

    @Size(max = 255)
    @Column(name = "potencial_de_danos", length = 255)
    private String potencialDeDanos;

    @NotNull
    @Column(name = "created", nullable = false)
    private Instant created;

    @Column(name = "updated")
    private Instant updated;

    @ManyToOne
    @JsonIgnoreProperties(value = "tempestadeNivels", allowSetters = true)
    private IntensidadeChuva intensidadeChuva;

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

    public TempestadeNivel name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescricao() {
        return descricao;
    }

    public TempestadeNivel descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTaxaDeRaios() {
        return taxaDeRaios;
    }

    public TempestadeNivel taxaDeRaios(String taxaDeRaios) {
        this.taxaDeRaios = taxaDeRaios;
        return this;
    }

    public void setTaxaDeRaios(String taxaDeRaios) {
        this.taxaDeRaios = taxaDeRaios;
    }

    public String getVentosVelocidade() {
        return ventosVelocidade;
    }

    public TempestadeNivel ventosVelocidade(String ventosVelocidade) {
        this.ventosVelocidade = ventosVelocidade;
        return this;
    }

    public void setVentosVelocidade(String ventosVelocidade) {
        this.ventosVelocidade = ventosVelocidade;
    }

    public String getGranizo() {
        return granizo;
    }

    public TempestadeNivel granizo(String granizo) {
        this.granizo = granizo;
        return this;
    }

    public void setGranizo(String granizo) {
        this.granizo = granizo;
    }

    public String getPotencialDeDanos() {
        return potencialDeDanos;
    }

    public TempestadeNivel potencialDeDanos(String potencialDeDanos) {
        this.potencialDeDanos = potencialDeDanos;
        return this;
    }

    public void setPotencialDeDanos(String potencialDeDanos) {
        this.potencialDeDanos = potencialDeDanos;
    }

    public Instant getCreated() {
        return created;
    }

    public TempestadeNivel created(Instant created) {
        this.created = created;
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getUpdated() {
        return updated;
    }

    public TempestadeNivel updated(Instant updated) {
        this.updated = updated;
        return this;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    public IntensidadeChuva getIntensidadeChuva() {
        return intensidadeChuva;
    }

    public TempestadeNivel intensidadeChuva(IntensidadeChuva intensidadeChuva) {
        this.intensidadeChuva = intensidadeChuva;
        return this;
    }

    public void setIntensidadeChuva(IntensidadeChuva intensidadeChuva) {
        this.intensidadeChuva = intensidadeChuva;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TempestadeNivel)) {
            return false;
        }
        return id != null && id.equals(((TempestadeNivel) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TempestadeNivel{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", taxaDeRaios='" + getTaxaDeRaios() + "'" +
            ", ventosVelocidade='" + getVentosVelocidade() + "'" +
            ", granizo='" + getGranizo() + "'" +
            ", potencialDeDanos='" + getPotencialDeDanos() + "'" +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            "}";
    }
}
