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

/**
 * Criteria class for the {@link org.tempestade.nucleo.domain.Descarga} entity. This class is used
 * in {@link org.tempestade.nucleo.web.rest.DescargaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /descargas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DescargaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private StringFilter descricao;

    private IntegerFilter qtd;

    private InstantFilter dataPrimeiraDescarga;

    private StringFilter tempoAntecipacao;

    private InstantFilter created;

    private InstantFilter updated;

    private LongFilter redeId;

    private LongFilter descargaTipoId;

    private LongFilter descargaUnidadeId;

    private LongFilter alertaId;

    public DescargaCriteria() {
    }

    public DescargaCriteria(DescargaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.qtd = other.qtd == null ? null : other.qtd.copy();
        this.dataPrimeiraDescarga = other.dataPrimeiraDescarga == null ? null : other.dataPrimeiraDescarga.copy();
        this.tempoAntecipacao = other.tempoAntecipacao == null ? null : other.tempoAntecipacao.copy();
        this.created = other.created == null ? null : other.created.copy();
        this.updated = other.updated == null ? null : other.updated.copy();
        this.redeId = other.redeId == null ? null : other.redeId.copy();
        this.descargaTipoId = other.descargaTipoId == null ? null : other.descargaTipoId.copy();
        this.descargaUnidadeId = other.descargaUnidadeId == null ? null : other.descargaUnidadeId.copy();
        this.alertaId = other.alertaId == null ? null : other.alertaId.copy();
    }

    @Override
    public DescargaCriteria copy() {
        return new DescargaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNome() {
        return nome;
    }

    public void setNome(StringFilter nome) {
        this.nome = nome;
    }

    public StringFilter getDescricao() {
        return descricao;
    }

    public void setDescricao(StringFilter descricao) {
        this.descricao = descricao;
    }

    public IntegerFilter getQtd() {
        return qtd;
    }

    public void setQtd(IntegerFilter qtd) {
        this.qtd = qtd;
    }

    public InstantFilter getDataPrimeiraDescarga() {
        return dataPrimeiraDescarga;
    }

    public void setDataPrimeiraDescarga(InstantFilter dataPrimeiraDescarga) {
        this.dataPrimeiraDescarga = dataPrimeiraDescarga;
    }

    public StringFilter getTempoAntecipacao() {
        return tempoAntecipacao;
    }

    public void setTempoAntecipacao(StringFilter tempoAntecipacao) {
        this.tempoAntecipacao = tempoAntecipacao;
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

    public LongFilter getRedeId() {
        return redeId;
    }

    public void setRedeId(LongFilter redeId) {
        this.redeId = redeId;
    }

    public LongFilter getDescargaTipoId() {
        return descargaTipoId;
    }

    public void setDescargaTipoId(LongFilter descargaTipoId) {
        this.descargaTipoId = descargaTipoId;
    }

    public LongFilter getDescargaUnidadeId() {
        return descargaUnidadeId;
    }

    public void setDescargaUnidadeId(LongFilter descargaUnidadeId) {
        this.descargaUnidadeId = descargaUnidadeId;
    }

    public LongFilter getAlertaId() {
        return alertaId;
    }

    public void setAlertaId(LongFilter alertaId) {
        this.alertaId = alertaId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DescargaCriteria that = (DescargaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(qtd, that.qtd) &&
            Objects.equals(dataPrimeiraDescarga, that.dataPrimeiraDescarga) &&
            Objects.equals(tempoAntecipacao, that.tempoAntecipacao) &&
            Objects.equals(created, that.created) &&
            Objects.equals(updated, that.updated) &&
            Objects.equals(redeId, that.redeId) &&
            Objects.equals(descargaTipoId, that.descargaTipoId) &&
            Objects.equals(descargaUnidadeId, that.descargaUnidadeId) &&
            Objects.equals(alertaId, that.alertaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nome,
        descricao,
        qtd,
        dataPrimeiraDescarga,
        tempoAntecipacao,
        created,
        updated,
        redeId,
        descargaTipoId,
        descargaUnidadeId,
        alertaId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DescargaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (descricao != null ? "descricao=" + descricao + ", " : "") +
                (qtd != null ? "qtd=" + qtd + ", " : "") +
                (dataPrimeiraDescarga != null ? "dataPrimeiraDescarga=" + dataPrimeiraDescarga + ", " : "") +
                (tempoAntecipacao != null ? "tempoAntecipacao=" + tempoAntecipacao + ", " : "") +
                (created != null ? "created=" + created + ", " : "") +
                (updated != null ? "updated=" + updated + ", " : "") +
                (redeId != null ? "redeId=" + redeId + ", " : "") +
                (descargaTipoId != null ? "descargaTipoId=" + descargaTipoId + ", " : "") +
                (descargaUnidadeId != null ? "descargaUnidadeId=" + descargaUnidadeId + ", " : "") +
                (alertaId != null ? "alertaId=" + alertaId + ", " : "") +
            "}";
    }

}
