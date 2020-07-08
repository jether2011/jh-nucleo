package org.tempestade.nucleo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

/**
 * A Plano.
 */
@Entity
@Table(name = "plano")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Plano implements Serializable {

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

    @Column(name = "horario_previsto")
    private Integer horarioPrevisto;

    @Column(name = "tracking_ativo")
    private Integer trackingAtivo;

    @Column(name = "plr_ativo")
    private Integer plrAtivo;

    @Column(name = "codigo_widget_previsao")
    private Integer codigoWidgetPrevisao;

    @Size(max = 255)
    @Column(name = "kml_alvo", length = 255)
    private String kmlAlvo;

    @Column(name = "zoom_min")
    private Integer zoomMin;

    @Column(name = "dt_inicio_contrato")
    private LocalDate dtInicioContrato;

    @Column(name = "data_fim_contrato")
    private LocalDate dataFimContrato;

    @Size(min = 8, max = 8)
    @Pattern(regexp = "^(([0-1]\\d)|(2[0-3])):([0-5]\\d):([0-5]\\d)$")
    @Column(name = "horario_monit_inicio", length = 8)
    private String horarioMonitInicio;

    @Size(min = 8, max = 8)
    @Pattern(regexp = "^(([0-1]\\d)|(2[0-3])):([0-5]\\d):([0-5]\\d)$")
    @Column(name = "horario_monit_final", length = 8)
    private String horarioMonitFinal;

    @Size(max = 255)
    @Column(name = "blocos", length = 255)
    private String blocos;

    @Size(max = 255)
    @Column(name = "extent", length = 255)
    private String extent;

    @NotNull
    @Column(name = "created", nullable = false)
    private Instant created;

    @Column(name = "updated")
    private Instant updated;

    @ManyToOne
    @JsonIgnoreProperties(value = "planos", allowSetters = true)
    private Empresa empresa;

    @ManyToOne
    @JsonIgnoreProperties(value = "planos", allowSetters = true)
    private PlanoStatus planoStatus;

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

    public Plano name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescricao() {
        return descricao;
    }

    public Plano descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getHorarioPrevisto() {
        return horarioPrevisto;
    }

    public Plano horarioPrevisto(Integer horarioPrevisto) {
        this.horarioPrevisto = horarioPrevisto;
        return this;
    }

    public void setHorarioPrevisto(Integer horarioPrevisto) {
        this.horarioPrevisto = horarioPrevisto;
    }

    public Integer getTrackingAtivo() {
        return trackingAtivo;
    }

    public Plano trackingAtivo(Integer trackingAtivo) {
        this.trackingAtivo = trackingAtivo;
        return this;
    }

    public void setTrackingAtivo(Integer trackingAtivo) {
        this.trackingAtivo = trackingAtivo;
    }

    public Integer getPlrAtivo() {
        return plrAtivo;
    }

    public Plano plrAtivo(Integer plrAtivo) {
        this.plrAtivo = plrAtivo;
        return this;
    }

    public void setPlrAtivo(Integer plrAtivo) {
        this.plrAtivo = plrAtivo;
    }

    public Integer getCodigoWidgetPrevisao() {
        return codigoWidgetPrevisao;
    }

    public Plano codigoWidgetPrevisao(Integer codigoWidgetPrevisao) {
        this.codigoWidgetPrevisao = codigoWidgetPrevisao;
        return this;
    }

    public void setCodigoWidgetPrevisao(Integer codigoWidgetPrevisao) {
        this.codigoWidgetPrevisao = codigoWidgetPrevisao;
    }

    public String getKmlAlvo() {
        return kmlAlvo;
    }

    public Plano kmlAlvo(String kmlAlvo) {
        this.kmlAlvo = kmlAlvo;
        return this;
    }

    public void setKmlAlvo(String kmlAlvo) {
        this.kmlAlvo = kmlAlvo;
    }

    public Integer getZoomMin() {
        return zoomMin;
    }

    public Plano zoomMin(Integer zoomMin) {
        this.zoomMin = zoomMin;
        return this;
    }

    public void setZoomMin(Integer zoomMin) {
        this.zoomMin = zoomMin;
    }

    public LocalDate getDtInicioContrato() {
        return dtInicioContrato;
    }

    public Plano dtInicioContrato(LocalDate dtInicioContrato) {
        this.dtInicioContrato = dtInicioContrato;
        return this;
    }

    public void setDtInicioContrato(LocalDate dtInicioContrato) {
        this.dtInicioContrato = dtInicioContrato;
    }

    public LocalDate getDataFimContrato() {
        return dataFimContrato;
    }

    public Plano dataFimContrato(LocalDate dataFimContrato) {
        this.dataFimContrato = dataFimContrato;
        return this;
    }

    public void setDataFimContrato(LocalDate dataFimContrato) {
        this.dataFimContrato = dataFimContrato;
    }

    public String getHorarioMonitInicio() {
        return horarioMonitInicio;
    }

    public Plano horarioMonitInicio(String horarioMonitInicio) {
        this.horarioMonitInicio = horarioMonitInicio;
        return this;
    }

    public void setHorarioMonitInicio(String horarioMonitInicio) {
        this.horarioMonitInicio = horarioMonitInicio;
    }

    public String getHorarioMonitFinal() {
        return horarioMonitFinal;
    }

    public Plano horarioMonitFinal(String horarioMonitFinal) {
        this.horarioMonitFinal = horarioMonitFinal;
        return this;
    }

    public void setHorarioMonitFinal(String horarioMonitFinal) {
        this.horarioMonitFinal = horarioMonitFinal;
    }

    public String getBlocos() {
        return blocos;
    }

    public Plano blocos(String blocos) {
        this.blocos = blocos;
        return this;
    }

    public void setBlocos(String blocos) {
        this.blocos = blocos;
    }

    public String getExtent() {
        return extent;
    }

    public Plano extent(String extent) {
        this.extent = extent;
        return this;
    }

    public void setExtent(String extent) {
        this.extent = extent;
    }

    public Instant getCreated() {
        return created;
    }

    public Plano created(Instant created) {
        this.created = created;
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getUpdated() {
        return updated;
    }

    public Plano updated(Instant updated) {
        this.updated = updated;
        return this;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public Plano empresa(Empresa empresa) {
        this.empresa = empresa;
        return this;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public PlanoStatus getPlanoStatus() {
        return planoStatus;
    }

    public Plano planoStatus(PlanoStatus planoStatus) {
        this.planoStatus = planoStatus;
        return this;
    }

    public void setPlanoStatus(PlanoStatus planoStatus) {
        this.planoStatus = planoStatus;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Plano)) {
            return false;
        }
        return id != null && id.equals(((Plano) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Plano{" +
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
            "}";
    }
}
