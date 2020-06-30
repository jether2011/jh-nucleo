package org.tempestade.nucleo.service.dto;

import java.time.Instant;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link org.tempestade.nucleo.domain.Plano} entity.
 */
public class PlanoDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    @Size(max = 255)
    private String descricao;

    private Integer horarioPrevisto;

    private Integer trackingAtivo;

    private Integer plrAtivo;

    private Integer codigoWidgetPrevisao;

    @Size(max = 255)
    private String kmlAlvo;

    private Integer zoomMin;

    private LocalDate dtInicioContrato;

    private LocalDate dataFimContrato;

    @Size(min = 8, max = 8)
    @Pattern(regexp = "^(([0-1]\\d)|(2[0-3])):([0-5]\\d):([0-5]\\d)$")
    private String horarioMonitInicio;

    @Size(min = 8, max = 8)
    @Pattern(regexp = "^(([0-1]\\d)|(2[0-3])):([0-5]\\d):([0-5]\\d)$")
    private String horarioMonitFinal;

    @Size(max = 255)
    private String blocos;

    @Size(max = 255)
    private String extent;

    @NotNull
    private Instant created;

    private Instant updated;


    private Long empresaId;

    private Long planoStatusId;
    
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

    public Integer getHorarioPrevisto() {
        return horarioPrevisto;
    }

    public void setHorarioPrevisto(Integer horarioPrevisto) {
        this.horarioPrevisto = horarioPrevisto;
    }

    public Integer getTrackingAtivo() {
        return trackingAtivo;
    }

    public void setTrackingAtivo(Integer trackingAtivo) {
        this.trackingAtivo = trackingAtivo;
    }

    public Integer getPlrAtivo() {
        return plrAtivo;
    }

    public void setPlrAtivo(Integer plrAtivo) {
        this.plrAtivo = plrAtivo;
    }

    public Integer getCodigoWidgetPrevisao() {
        return codigoWidgetPrevisao;
    }

    public void setCodigoWidgetPrevisao(Integer codigoWidgetPrevisao) {
        this.codigoWidgetPrevisao = codigoWidgetPrevisao;
    }

    public String getKmlAlvo() {
        return kmlAlvo;
    }

    public void setKmlAlvo(String kmlAlvo) {
        this.kmlAlvo = kmlAlvo;
    }

    public Integer getZoomMin() {
        return zoomMin;
    }

    public void setZoomMin(Integer zoomMin) {
        this.zoomMin = zoomMin;
    }

    public LocalDate getDtInicioContrato() {
        return dtInicioContrato;
    }

    public void setDtInicioContrato(LocalDate dtInicioContrato) {
        this.dtInicioContrato = dtInicioContrato;
    }

    public LocalDate getDataFimContrato() {
        return dataFimContrato;
    }

    public void setDataFimContrato(LocalDate dataFimContrato) {
        this.dataFimContrato = dataFimContrato;
    }

    public String getHorarioMonitInicio() {
        return horarioMonitInicio;
    }

    public void setHorarioMonitInicio(String horarioMonitInicio) {
        this.horarioMonitInicio = horarioMonitInicio;
    }

    public String getHorarioMonitFinal() {
        return horarioMonitFinal;
    }

    public void setHorarioMonitFinal(String horarioMonitFinal) {
        this.horarioMonitFinal = horarioMonitFinal;
    }

    public String getBlocos() {
        return blocos;
    }

    public void setBlocos(String blocos) {
        this.blocos = blocos;
    }

    public String getExtent() {
        return extent;
    }

    public void setExtent(String extent) {
        this.extent = extent;
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

    public Long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }

    public Long getPlanoStatusId() {
        return planoStatusId;
    }

    public void setPlanoStatusId(Long planoStatusId) {
        this.planoStatusId = planoStatusId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlanoDTO)) {
            return false;
        }

        return id != null && id.equals(((PlanoDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlanoDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", horarioPrevisto=" + getHorarioPrevisto() +
            ", trackingAtivo=" + getTrackingAtivo() +
            ", plrAtivo=" + getPlrAtivo() +
            ", codigoWidgetPrevisao=" + getCodigoWidgetPrevisao() +
            ", kmlAlvo='" + getKmlAlvo() + "'" +
            ", zoomMin=" + getZoomMin() +
            ", dtInicioContrato='" + getDtInicioContrato() + "'" +
            ", dataFimContrato='" + getDataFimContrato() + "'" +
            ", horarioMonitInicio='" + getHorarioMonitInicio() + "'" +
            ", horarioMonitFinal='" + getHorarioMonitFinal() + "'" +
            ", blocos='" + getBlocos() + "'" +
            ", extent='" + getExtent() + "'" +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            ", empresaId=" + getEmpresaId() +
            ", planoStatusId=" + getPlanoStatusId() +
            "}";
    }
}
