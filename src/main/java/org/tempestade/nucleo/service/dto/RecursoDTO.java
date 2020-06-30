package org.tempestade.nucleo.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link org.tempestade.nucleo.domain.Recurso} entity.
 */
public class RecursoDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    @Size(max = 255)
    private String descricao;

    private Boolean ativo;

    @NotNull
    private Instant created;

    private Instant updated;


    private Long recursoTipoId;

    private Long variavelMeteorologicaId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getUpdated() {
        return updated;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    public Long getRecursoTipoId() {
        return recursoTipoId;
    }

    public void setRecursoTipoId(Long recursoTipoId) {
        this.recursoTipoId = recursoTipoId;
    }

    public Long getVariavelMeteorologicaId() {
        return variavelMeteorologicaId;
    }

    public void setVariavelMeteorologicaId(Long variavelMeteorologicaId) {
        this.variavelMeteorologicaId = variavelMeteorologicaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RecursoDTO)) {
            return false;
        }

        return id != null && id.equals(((RecursoDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RecursoDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", ativo='" + isAtivo() + "'" +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            ", recursoTipoId=" + getRecursoTipoId() +
            ", variavelMeteorologicaId=" + getVariavelMeteorologicaId() +
            "}";
    }
}
