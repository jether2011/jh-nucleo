package org.tempestade.nucleo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Meteograma.
 */
@Entity
@Table(name = "meteograma")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Meteograma implements Serializable {

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
    @Column(name = "arquivo", length = 255)
    private String arquivo;

    @Size(max = 255)
    @Column(name = "folder", length = 255)
    private String folder;

    @Size(max = 255)
    @Column(name = "tipoarquivo", length = 255)
    private String tipoarquivo;

    @NotNull
    @Column(name = "created", nullable = false)
    private Instant created;

    @Column(name = "updated")
    private Instant updated;

    @ManyToOne
    @JsonIgnoreProperties(value = "meteogramas", allowSetters = true)
    private Plano plano;

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

    public Meteograma name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescricao() {
        return descricao;
    }

    public Meteograma descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getArquivo() {
        return arquivo;
    }

    public Meteograma arquivo(String arquivo) {
        this.arquivo = arquivo;
        return this;
    }

    public void setArquivo(String arquivo) {
        this.arquivo = arquivo;
    }

    public String getFolder() {
        return folder;
    }

    public Meteograma folder(String folder) {
        this.folder = folder;
        return this;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getTipoarquivo() {
        return tipoarquivo;
    }

    public Meteograma tipoarquivo(String tipoarquivo) {
        this.tipoarquivo = tipoarquivo;
        return this;
    }

    public void setTipoarquivo(String tipoarquivo) {
        this.tipoarquivo = tipoarquivo;
    }

    public Instant getCreated() {
        return created;
    }

    public Meteograma created(Instant created) {
        this.created = created;
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getUpdated() {
        return updated;
    }

    public Meteograma updated(Instant updated) {
        this.updated = updated;
        return this;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    public Plano getPlano() {
        return plano;
    }

    public Meteograma plano(Plano plano) {
        this.plano = plano;
        return this;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Meteograma)) {
            return false;
        }
        return id != null && id.equals(((Meteograma) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Meteograma{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", arquivo='" + getArquivo() + "'" +
            ", folder='" + getFolder() + "'" +
            ", tipoarquivo='" + getTipoarquivo() + "'" +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            "}";
    }
}
