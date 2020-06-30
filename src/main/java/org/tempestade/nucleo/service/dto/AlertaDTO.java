package org.tempestade.nucleo.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link org.tempestade.nucleo.domain.Alerta} entity.
 */
public class AlertaDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 255)
    private String nome;

    @Size(max = 255)
    private String contato;

    @Size(min = 8, max = 8)
    @Pattern(regexp = "^(([0-1]\\d)|(2[0-3])):([0-5]\\d):([0-5]\\d)$")
    private String duracao;

    private Boolean automatico;

    private Boolean critico;

    @Size(max = 255)
    private String observacao;

    private Integer alertaPaiId;

    @NotNull
    private Instant created;

    private Instant updated;


    private Long planoRecursoId;

    private Long alvoId;

    private Long operadorUsuarioId;

    private Long alertaRiscoId;

    private Long tempestadeNivelId;

    private Long alertaTipoId;
    
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

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public Boolean isAutomatico() {
        return automatico;
    }

    public void setAutomatico(Boolean automatico) {
        this.automatico = automatico;
    }

    public Boolean isCritico() {
        return critico;
    }

    public void setCritico(Boolean critico) {
        this.critico = critico;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Integer getAlertaPaiId() {
        return alertaPaiId;
    }

    public void setAlertaPaiId(Integer alertaPaiId) {
        this.alertaPaiId = alertaPaiId;
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

    public Long getPlanoRecursoId() {
        return planoRecursoId;
    }

    public void setPlanoRecursoId(Long planoRecursoId) {
        this.planoRecursoId = planoRecursoId;
    }

    public Long getAlvoId() {
        return alvoId;
    }

    public void setAlvoId(Long alvoId) {
        this.alvoId = alvoId;
    }

    public Long getOperadorUsuarioId() {
        return operadorUsuarioId;
    }

    public void setOperadorUsuarioId(Long usuarioId) {
        this.operadorUsuarioId = usuarioId;
    }

    public Long getAlertaRiscoId() {
        return alertaRiscoId;
    }

    public void setAlertaRiscoId(Long alertaRiscoId) {
        this.alertaRiscoId = alertaRiscoId;
    }

    public Long getTempestadeNivelId() {
        return tempestadeNivelId;
    }

    public void setTempestadeNivelId(Long tempestadeNivelId) {
        this.tempestadeNivelId = tempestadeNivelId;
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
        if (!(o instanceof AlertaDTO)) {
            return false;
        }

        return id != null && id.equals(((AlertaDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AlertaDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", contato='" + getContato() + "'" +
            ", duracao='" + getDuracao() + "'" +
            ", automatico='" + isAutomatico() + "'" +
            ", critico='" + isCritico() + "'" +
            ", observacao='" + getObservacao() + "'" +
            ", alertaPaiId=" + getAlertaPaiId() +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            ", planoRecursoId=" + getPlanoRecursoId() +
            ", alvoId=" + getAlvoId() +
            ", operadorUsuarioId=" + getOperadorUsuarioId() +
            ", alertaRiscoId=" + getAlertaRiscoId() +
            ", tempestadeNivelId=" + getTempestadeNivelId() +
            ", alertaTipoId=" + getAlertaTipoId() +
            "}";
    }
}
