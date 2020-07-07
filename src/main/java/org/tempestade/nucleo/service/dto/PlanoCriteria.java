package org.tempestade.nucleo.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link org.tempestade.nucleo.domain.Plano} entity. This class is used
 * in {@link org.tempestade.nucleo.web.rest.PlanoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /planos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PlanoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter descricao;

    private IntegerFilter horarioPrevisto;

    private IntegerFilter trackingAtivo;

    private IntegerFilter plrAtivo;

    private IntegerFilter codigoWidgetPrevisao;

    private StringFilter kmlAlvo;

    private IntegerFilter zoomMin;

    private LocalDateFilter dtInicioContrato;

    private LocalDateFilter dataFimContrato;

    private StringFilter horarioMonitInicio;

    private StringFilter horarioMonitFinal;

    private StringFilter blocos;

    private StringFilter extent;

    private InstantFilter created;

    private InstantFilter updated;

    private LongFilter empresaId;

    private LongFilter planoStatusId;

    public PlanoCriteria() {
    }

    public PlanoCriteria(PlanoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.horarioPrevisto = other.horarioPrevisto == null ? null : other.horarioPrevisto.copy();
        this.trackingAtivo = other.trackingAtivo == null ? null : other.trackingAtivo.copy();
        this.plrAtivo = other.plrAtivo == null ? null : other.plrAtivo.copy();
        this.codigoWidgetPrevisao = other.codigoWidgetPrevisao == null ? null : other.codigoWidgetPrevisao.copy();
        this.kmlAlvo = other.kmlAlvo == null ? null : other.kmlAlvo.copy();
        this.zoomMin = other.zoomMin == null ? null : other.zoomMin.copy();
        this.dtInicioContrato = other.dtInicioContrato == null ? null : other.dtInicioContrato.copy();
        this.dataFimContrato = other.dataFimContrato == null ? null : other.dataFimContrato.copy();
        this.horarioMonitInicio = other.horarioMonitInicio == null ? null : other.horarioMonitInicio.copy();
        this.horarioMonitFinal = other.horarioMonitFinal == null ? null : other.horarioMonitFinal.copy();
        this.blocos = other.blocos == null ? null : other.blocos.copy();
        this.extent = other.extent == null ? null : other.extent.copy();
        this.created = other.created == null ? null : other.created.copy();
        this.updated = other.updated == null ? null : other.updated.copy();
        this.empresaId = other.empresaId == null ? null : other.empresaId.copy();
        this.planoStatusId = other.planoStatusId == null ? null : other.planoStatusId.copy();
    }

    @Override
    public PlanoCriteria copy() {
        return new PlanoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getDescricao() {
        return descricao;
    }

    public void setDescricao(StringFilter descricao) {
        this.descricao = descricao;
    }

    public IntegerFilter getHorarioPrevisto() {
        return horarioPrevisto;
    }

    public void setHorarioPrevisto(IntegerFilter horarioPrevisto) {
        this.horarioPrevisto = horarioPrevisto;
    }

    public IntegerFilter getTrackingAtivo() {
        return trackingAtivo;
    }

    public void setTrackingAtivo(IntegerFilter trackingAtivo) {
        this.trackingAtivo = trackingAtivo;
    }

    public IntegerFilter getPlrAtivo() {
        return plrAtivo;
    }

    public void setPlrAtivo(IntegerFilter plrAtivo) {
        this.plrAtivo = plrAtivo;
    }

    public IntegerFilter getCodigoWidgetPrevisao() {
        return codigoWidgetPrevisao;
    }

    public void setCodigoWidgetPrevisao(IntegerFilter codigoWidgetPrevisao) {
        this.codigoWidgetPrevisao = codigoWidgetPrevisao;
    }

    public StringFilter getKmlAlvo() {
        return kmlAlvo;
    }

    public void setKmlAlvo(StringFilter kmlAlvo) {
        this.kmlAlvo = kmlAlvo;
    }

    public IntegerFilter getZoomMin() {
        return zoomMin;
    }

    public void setZoomMin(IntegerFilter zoomMin) {
        this.zoomMin = zoomMin;
    }

    public LocalDateFilter getDtInicioContrato() {
        return dtInicioContrato;
    }

    public void setDtInicioContrato(LocalDateFilter dtInicioContrato) {
        this.dtInicioContrato = dtInicioContrato;
    }

    public LocalDateFilter getDataFimContrato() {
        return dataFimContrato;
    }

    public void setDataFimContrato(LocalDateFilter dataFimContrato) {
        this.dataFimContrato = dataFimContrato;
    }

    public StringFilter getHorarioMonitInicio() {
        return horarioMonitInicio;
    }

    public void setHorarioMonitInicio(StringFilter horarioMonitInicio) {
        this.horarioMonitInicio = horarioMonitInicio;
    }

    public StringFilter getHorarioMonitFinal() {
        return horarioMonitFinal;
    }

    public void setHorarioMonitFinal(StringFilter horarioMonitFinal) {
        this.horarioMonitFinal = horarioMonitFinal;
    }

    public StringFilter getBlocos() {
        return blocos;
    }

    public void setBlocos(StringFilter blocos) {
        this.blocos = blocos;
    }

    public StringFilter getExtent() {
        return extent;
    }

    public void setExtent(StringFilter extent) {
        this.extent = extent;
    }

    public InstantFilter getCreated() {
        return created;
    }

    public void setCreated(InstantFilter created) {
        this.created = created;
    }

    public InstantFilter getUpdated() {
        return updated;
    }

    public void setUpdated(InstantFilter updated) {
        this.updated = updated;
    }

    public LongFilter getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(LongFilter empresaId) {
        this.empresaId = empresaId;
    }

    public LongFilter getPlanoStatusId() {
        return planoStatusId;
    }

    public void setPlanoStatusId(LongFilter planoStatusId) {
        this.planoStatusId = planoStatusId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PlanoCriteria that = (PlanoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(horarioPrevisto, that.horarioPrevisto) &&
            Objects.equals(trackingAtivo, that.trackingAtivo) &&
            Objects.equals(plrAtivo, that.plrAtivo) &&
            Objects.equals(codigoWidgetPrevisao, that.codigoWidgetPrevisao) &&
            Objects.equals(kmlAlvo, that.kmlAlvo) &&
            Objects.equals(zoomMin, that.zoomMin) &&
            Objects.equals(dtInicioContrato, that.dtInicioContrato) &&
            Objects.equals(dataFimContrato, that.dataFimContrato) &&
            Objects.equals(horarioMonitInicio, that.horarioMonitInicio) &&
            Objects.equals(horarioMonitFinal, that.horarioMonitFinal) &&
            Objects.equals(blocos, that.blocos) &&
            Objects.equals(extent, that.extent) &&
            Objects.equals(created, that.created) &&
            Objects.equals(updated, that.updated) &&
            Objects.equals(empresaId, that.empresaId) &&
            Objects.equals(planoStatusId, that.planoStatusId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        descricao,
        horarioPrevisto,
        trackingAtivo,
        plrAtivo,
        codigoWidgetPrevisao,
        kmlAlvo,
        zoomMin,
        dtInicioContrato,
        dataFimContrato,
        horarioMonitInicio,
        horarioMonitFinal,
        blocos,
        extent,
        created,
        updated,
        empresaId,
        planoStatusId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlanoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (descricao != null ? "descricao=" + descricao + ", " : "") +
                (horarioPrevisto != null ? "horarioPrevisto=" + horarioPrevisto + ", " : "") +
                (trackingAtivo != null ? "trackingAtivo=" + trackingAtivo + ", " : "") +
                (plrAtivo != null ? "plrAtivo=" + plrAtivo + ", " : "") +
                (codigoWidgetPrevisao != null ? "codigoWidgetPrevisao=" + codigoWidgetPrevisao + ", " : "") +
                (kmlAlvo != null ? "kmlAlvo=" + kmlAlvo + ", " : "") +
                (zoomMin != null ? "zoomMin=" + zoomMin + ", " : "") +
                (dtInicioContrato != null ? "dtInicioContrato=" + dtInicioContrato + ", " : "") +
                (dataFimContrato != null ? "dataFimContrato=" + dataFimContrato + ", " : "") +
                (horarioMonitInicio != null ? "horarioMonitInicio=" + horarioMonitInicio + ", " : "") +
                (horarioMonitFinal != null ? "horarioMonitFinal=" + horarioMonitFinal + ", " : "") +
                (blocos != null ? "blocos=" + blocos + ", " : "") +
                (extent != null ? "extent=" + extent + ", " : "") +
                (created != null ? "created=" + created + ", " : "") +
                (updated != null ? "updated=" + updated + ", " : "") +
                (empresaId != null ? "empresaId=" + empresaId + ", " : "") +
                (planoStatusId != null ? "planoStatusId=" + planoStatusId + ", " : "") +
            "}";
    }

}
