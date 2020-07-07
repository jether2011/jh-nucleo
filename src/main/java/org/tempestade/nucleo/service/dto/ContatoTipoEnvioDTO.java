package org.tempestade.nucleo.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link org.tempestade.nucleo.domain.ContatoTipoEnvio} entity.
 */
public class ContatoTipoEnvioDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 255)
    private String nome;

    private String descricao;

    @NotNull
    private Instant created;

    private Instant updated;


    private Long contatoId;

    private Long tipoEnvioId;
    
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

    public Long getContatoId() {
        return contatoId;
    }

    public void setContatoId(Long contatoId) {
        this.contatoId = contatoId;
    }

    public Long getTipoEnvioId() {
        return tipoEnvioId;
    }

    public void setTipoEnvioId(Long tipoEnvioId) {
        this.tipoEnvioId = tipoEnvioId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContatoTipoEnvioDTO)) {
            return false;
        }

        return id != null && id.equals(((ContatoTipoEnvioDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContatoTipoEnvioDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            ", contatoId=" + getContatoId() +
            ", tipoEnvioId=" + getTipoEnvioId() +
            "}";
    }
}
