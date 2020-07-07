package org.tempestade.nucleo.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link org.tempestade.nucleo.domain.VentoVmFaixa} entity.
 */
public class VentoVmFaixaDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    @Size(max = 255)
    private String descricao;

    private Integer de;

    private Integer ate;

    @NotNull
    private Instant created;

    private Instant updated;

    
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

    public Integer getDe() {
        return de;
    }

    public void setDe(Integer de) {
        this.de = de;
    }

    public Integer getAte() {
        return ate;
    }

    public void setAte(Integer ate) {
        this.ate = ate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VentoVmFaixaDTO)) {
            return false;
        }

        return id != null && id.equals(((VentoVmFaixaDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VentoVmFaixaDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", de=" + getDe() +
            ", ate=" + getAte() +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            "}";
    }
}
