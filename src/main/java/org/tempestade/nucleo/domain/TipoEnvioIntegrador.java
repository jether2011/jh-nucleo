package org.tempestade.nucleo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A TipoEnvioIntegrador.
 */
@Entity
@Table(name = "tipo_envio_integrador")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TipoEnvioIntegrador implements Serializable {

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
    @JsonIgnoreProperties(value = "tipoEnvioIntegradors", allowSetters = true)
    private TipoEnvio tipoEnvio;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "tipoEnvioIntegradors", allowSetters = true)
    private Integrador integrador;

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

    public TipoEnvioIntegrador name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescricao() {
        return descricao;
    }

    public TipoEnvioIntegrador descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public TipoEnvioIntegrador ativo(Boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Instant getCreated() {
        return created;
    }

    public TipoEnvioIntegrador created(Instant created) {
        this.created = created;
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getUpdated() {
        return updated;
    }

    public TipoEnvioIntegrador updated(Instant updated) {
        this.updated = updated;
        return this;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    public TipoEnvio getTipoEnvio() {
        return tipoEnvio;
    }

    public TipoEnvioIntegrador tipoEnvio(TipoEnvio tipoEnvio) {
        this.tipoEnvio = tipoEnvio;
        return this;
    }

    public void setTipoEnvio(TipoEnvio tipoEnvio) {
        this.tipoEnvio = tipoEnvio;
    }

    public Integrador getIntegrador() {
        return integrador;
    }

    public TipoEnvioIntegrador integrador(Integrador integrador) {
        this.integrador = integrador;
        return this;
    }

    public void setIntegrador(Integrador integrador) {
        this.integrador = integrador;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoEnvioIntegrador)) {
            return false;
        }
        return id != null && id.equals(((TipoEnvioIntegrador) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TipoEnvioIntegrador{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", ativo='" + isAtivo() + "'" +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            "}";
    }
}
