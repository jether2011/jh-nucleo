package org.tempestade.nucleo.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link org.tempestade.nucleo.domain.RecursoTemplate} entity.
 */
public class RecursoTemplateDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    @Size(max = 255)
    private String descricao;

    @Size(max = 255)
    private String template;

    @NotNull
    private Instant created;

    private Instant updated;


    private Long recursoId;

    private Long tipoEnvioId;

    private Long alertaTipoId;
    
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

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
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

    public Long getRecursoId() {
        return recursoId;
    }

    public void setRecursoId(Long recursoId) {
        this.recursoId = recursoId;
    }

    public Long getTipoEnvioId() {
        return tipoEnvioId;
    }

    public void setTipoEnvioId(Long tipoEnvioId) {
        this.tipoEnvioId = tipoEnvioId;
    }

    public Long getAlertaTipoId() {
        return alertaTipoId;
    }

    public void setAlertaTipoId(Long alertaTipoId) {
        this.alertaTipoId = alertaTipoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RecursoTemplateDTO)) {
            return false;
        }

        return id != null && id.equals(((RecursoTemplateDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RecursoTemplateDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", template='" + getTemplate() + "'" +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            ", recursoId=" + getRecursoId() +
            ", tipoEnvioId=" + getTipoEnvioId() +
            ", alertaTipoId=" + getAlertaTipoId() +
            "}";
    }
}
