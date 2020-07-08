package org.tempestade.nucleo.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link org.tempestade.nucleo.domain.TempestadeNivel} entity.
 */
public class TempestadeNivelDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    @Size(max = 255)
    private String descricao;

    @Size(max = 255)
    private String taxaDeRaios;

    @Size(max = 255)
    private String ventosVelocidade;

    @Size(max = 255)
    private String granizo;

    @Size(max = 255)
    private String potencialDeDanos;

    @NotNull
    private Instant created;

    private Instant updated;


    private Long intensidadeChuvaId;
    
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

    public String getTaxaDeRaios() {
        return taxaDeRaios;
    }

    public void setTaxaDeRaios(String taxaDeRaios) {
        this.taxaDeRaios = taxaDeRaios;
    }

    public String getVentosVelocidade() {
        return ventosVelocidade;
    }

    public void setVentosVelocidade(String ventosVelocidade) {
        this.ventosVelocidade = ventosVelocidade;
    }

    public String getGranizo() {
        return granizo;
    }

    public void setGranizo(String granizo) {
        this.granizo = granizo;
    }

    public String getPotencialDeDanos() {
        return potencialDeDanos;
    }

    public void setPotencialDeDanos(String potencialDeDanos) {
        this.potencialDeDanos = potencialDeDanos;
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

    public Long getIntensidadeChuvaId() {
        return intensidadeChuvaId;
    }

    public void setIntensidadeChuvaId(Long intensidadeChuvaId) {
        this.intensidadeChuvaId = intensidadeChuvaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TempestadeNivelDTO)) {
            return false;
        }

        return id != null && id.equals(((TempestadeNivelDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TempestadeNivelDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", taxaDeRaios='" + getTaxaDeRaios() + "'" +
            ", ventosVelocidade='" + getVentosVelocidade() + "'" +
            ", granizo='" + getGranizo() + "'" +
            ", potencialDeDanos='" + getPotencialDeDanos() + "'" +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            ", intensidadeChuvaId=" + getIntensidadeChuvaId() +
            "}";
    }
}
