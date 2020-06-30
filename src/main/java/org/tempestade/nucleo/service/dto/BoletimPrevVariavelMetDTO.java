package org.tempestade.nucleo.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link org.tempestade.nucleo.domain.BoletimPrevVariavelMet} entity.
 */
public class BoletimPrevVariavelMetDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 255)
    private String nome;

    private String descricao;

    @NotNull
    private Instant created;

    private Instant updated;


    private Long boletimPrevisaoId;

    private Long variavelMeteorologicaId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

    public Long getBoletimPrevisaoId() {
        return boletimPrevisaoId;
    }

    public void setBoletimPrevisaoId(Long boletimPrevisaoId) {
        this.boletimPrevisaoId = boletimPrevisaoId;
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
        if (!(o instanceof BoletimPrevVariavelMetDTO)) {
            return false;
        }

        return id != null && id.equals(((BoletimPrevVariavelMetDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BoletimPrevVariavelMetDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            ", boletimPrevisaoId=" + getBoletimPrevisaoId() +
            ", variavelMeteorologicaId=" + getVariavelMeteorologicaId() +
            "}";
    }
}
