package org.tempestade.nucleo.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link org.tempestade.nucleo.domain.Descarga} entity.
 */
public class DescargaDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 255)
    private String nome;

    private String descricao;

    private Integer qtd;

    @NotNull
    private Instant dataPrimeiraDescarga;

    @Size(min = 8, max = 8)
    @Pattern(regexp = "^(([0-1]\\d)|(2[0-3])):([0-5]\\d):([0-5]\\d)$")
    private String tempoAntecipacao;

    @NotNull
    private Instant created;

    private Instant updated;


    private Long redeId;

    private Long descargaTipoId;

    private Long descargaUnidadeId;

    private Long alertaId;
    
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

    public Integer getQtd() {
        return qtd;
    }

    public void setQtd(Integer qtd) {
        this.qtd = qtd;
    }

    public Instant getDataPrimeiraDescarga() {
        return dataPrimeiraDescarga;
    }

    public void setDataPrimeiraDescarga(Instant dataPrimeiraDescarga) {
        this.dataPrimeiraDescarga = dataPrimeiraDescarga;
    }

    public String getTempoAntecipacao() {
        return tempoAntecipacao;
    }

    public void setTempoAntecipacao(String tempoAntecipacao) {
        this.tempoAntecipacao = tempoAntecipacao;
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

    public Long getRedeId() {
        return redeId;
    }

    public void setRedeId(Long redeId) {
        this.redeId = redeId;
    }

    public Long getDescargaTipoId() {
        return descargaTipoId;
    }

    public void setDescargaTipoId(Long descargaTipoId) {
        this.descargaTipoId = descargaTipoId;
    }

    public Long getDescargaUnidadeId() {
        return descargaUnidadeId;
    }

    public void setDescargaUnidadeId(Long descargaUnidadeId) {
        this.descargaUnidadeId = descargaUnidadeId;
    }

    public Long getAlertaId() {
        return alertaId;
    }

    public void setAlertaId(Long alertaId) {
        this.alertaId = alertaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DescargaDTO)) {
            return false;
        }

        return id != null && id.equals(((DescargaDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DescargaDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", qtd=" + getQtd() +
            ", dataPrimeiraDescarga='" + getDataPrimeiraDescarga() + "'" +
            ", tempoAntecipacao='" + getTempoAntecipacao() + "'" +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            ", redeId=" + getRedeId() +
            ", descargaTipoId=" + getDescargaTipoId() +
            ", descargaUnidadeId=" + getDescargaUnidadeId() +
            ", alertaId=" + getAlertaId() +
            "}";
    }
}
