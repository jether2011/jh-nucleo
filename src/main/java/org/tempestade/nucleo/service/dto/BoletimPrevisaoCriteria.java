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
 * Criteria class for the {@link org.tempestade.nucleo.domain.BoletimPrevisao} entity. This class is used
 * in {@link org.tempestade.nucleo.web.rest.BoletimPrevisaoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /boletim-previsaos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BoletimPrevisaoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private StringFilter descricao;

    private StringFilter local;

    private IntegerFilter imgCondicaoTempo;

    private StringFilter condicaoTempo;

    private StringFilter observacao;

    private IntegerFilter grupoOrdem;

    private StringFilter ondas;

    private IntegerFilter temperaturaDe;

    private IntegerFilter temperaturaAte;

    private IntegerFilter ventovelocidademediakmh;

    private StringFilter ventosObservacao;

    private BooleanFilter ventoRajada;

    private StringFilter tempestadeObservacao;

    private StringFilter chuvaObservacao;

    private InstantFilter created;

    private InstantFilter updated;

    private LongFilter boletimId;

    private LongFilter boletimPrevObsId;

    private LongFilter intensidadeChuvaId;

    private LongFilter umidadeArId;

    private LongFilter alvoId;

    private LongFilter pontosCardeaisId;

    private LongFilter ventoVmFaixaId;

    private LongFilter tempestadeProbabilidadeId;

    private LongFilter tempestadeNivelId;

    private LongFilter acumuladoChuvaFaixaId;

    private LongFilter condicaoTempoId;

    public BoletimPrevisaoCriteria() {
    }

    public BoletimPrevisaoCriteria(BoletimPrevisaoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.local = other.local == null ? null : other.local.copy();
        this.imgCondicaoTempo = other.imgCondicaoTempo == null ? null : other.imgCondicaoTempo.copy();
        this.condicaoTempo = other.condicaoTempo == null ? null : other.condicaoTempo.copy();
        this.observacao = other.observacao == null ? null : other.observacao.copy();
        this.grupoOrdem = other.grupoOrdem == null ? null : other.grupoOrdem.copy();
        this.ondas = other.ondas == null ? null : other.ondas.copy();
        this.temperaturaDe = other.temperaturaDe == null ? null : other.temperaturaDe.copy();
        this.temperaturaAte = other.temperaturaAte == null ? null : other.temperaturaAte.copy();
        this.ventovelocidademediakmh = other.ventovelocidademediakmh == null ? null : other.ventovelocidademediakmh.copy();
        this.ventosObservacao = other.ventosObservacao == null ? null : other.ventosObservacao.copy();
        this.ventoRajada = other.ventoRajada == null ? null : other.ventoRajada.copy();
        this.tempestadeObservacao = other.tempestadeObservacao == null ? null : other.tempestadeObservacao.copy();
        this.chuvaObservacao = other.chuvaObservacao == null ? null : other.chuvaObservacao.copy();
        this.created = other.created == null ? null : other.created.copy();
        this.updated = other.updated == null ? null : other.updated.copy();
        this.boletimId = other.boletimId == null ? null : other.boletimId.copy();
        this.boletimPrevObsId = other.boletimPrevObsId == null ? null : other.boletimPrevObsId.copy();
        this.intensidadeChuvaId = other.intensidadeChuvaId == null ? null : other.intensidadeChuvaId.copy();
        this.umidadeArId = other.umidadeArId == null ? null : other.umidadeArId.copy();
        this.alvoId = other.alvoId == null ? null : other.alvoId.copy();
        this.pontosCardeaisId = other.pontosCardeaisId == null ? null : other.pontosCardeaisId.copy();
        this.ventoVmFaixaId = other.ventoVmFaixaId == null ? null : other.ventoVmFaixaId.copy();
        this.tempestadeProbabilidadeId = other.tempestadeProbabilidadeId == null ? null : other.tempestadeProbabilidadeId.copy();
        this.tempestadeNivelId = other.tempestadeNivelId == null ? null : other.tempestadeNivelId.copy();
        this.acumuladoChuvaFaixaId = other.acumuladoChuvaFaixaId == null ? null : other.acumuladoChuvaFaixaId.copy();
        this.condicaoTempoId = other.condicaoTempoId == null ? null : other.condicaoTempoId.copy();
    }

    @Override
    public BoletimPrevisaoCriteria copy() {
        return new BoletimPrevisaoCriteria(this);
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

    public StringFilter getLocal() {
        return local;
    }

    public void setLocal(StringFilter local) {
        this.local = local;
    }

    public IntegerFilter getImgCondicaoTempo() {
        return imgCondicaoTempo;
    }

    public void setImgCondicaoTempo(IntegerFilter imgCondicaoTempo) {
        this.imgCondicaoTempo = imgCondicaoTempo;
    }

    public StringFilter getCondicaoTempo() {
        return condicaoTempo;
    }

    public void setCondicaoTempo(StringFilter condicaoTempo) {
        this.condicaoTempo = condicaoTempo;
    }

    public StringFilter getObservacao() {
        return observacao;
    }

    public void setObservacao(StringFilter observacao) {
        this.observacao = observacao;
    }

    public IntegerFilter getGrupoOrdem() {
        return grupoOrdem;
    }

    public void setGrupoOrdem(IntegerFilter grupoOrdem) {
        this.grupoOrdem = grupoOrdem;
    }

    public StringFilter getOndas() {
        return ondas;
    }

    public void setOndas(StringFilter ondas) {
        this.ondas = ondas;
    }

    public IntegerFilter getTemperaturaDe() {
        return temperaturaDe;
    }

    public void setTemperaturaDe(IntegerFilter temperaturaDe) {
        this.temperaturaDe = temperaturaDe;
    }

    public IntegerFilter getTemperaturaAte() {
        return temperaturaAte;
    }

    public void setTemperaturaAte(IntegerFilter temperaturaAte) {
        this.temperaturaAte = temperaturaAte;
    }

    public IntegerFilter getVentovelocidademediakmh() {
        return ventovelocidademediakmh;
    }

    public void setVentovelocidademediakmh(IntegerFilter ventovelocidademediakmh) {
        this.ventovelocidademediakmh = ventovelocidademediakmh;
    }

    public StringFilter getVentosObservacao() {
        return ventosObservacao;
    }

    public void setVentosObservacao(StringFilter ventosObservacao) {
        this.ventosObservacao = ventosObservacao;
    }

    public BooleanFilter getVentoRajada() {
        return ventoRajada;
    }

    public void setVentoRajada(BooleanFilter ventoRajada) {
        this.ventoRajada = ventoRajada;
    }

    public StringFilter getTempestadeObservacao() {
        return tempestadeObservacao;
    }

    public void setTempestadeObservacao(StringFilter tempestadeObservacao) {
        this.tempestadeObservacao = tempestadeObservacao;
    }

    public StringFilter getChuvaObservacao() {
        return chuvaObservacao;
    }

    public void setChuvaObservacao(StringFilter chuvaObservacao) {
        this.chuvaObservacao = chuvaObservacao;
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

    public LongFilter getBoletimId() {
        return boletimId;
    }

    public void setBoletimId(LongFilter boletimId) {
        this.boletimId = boletimId;
    }

    public LongFilter getBoletimPrevObsId() {
        return boletimPrevObsId;
    }

    public void setBoletimPrevObsId(LongFilter boletimPrevObsId) {
        this.boletimPrevObsId = boletimPrevObsId;
    }

    public LongFilter getIntensidadeChuvaId() {
        return intensidadeChuvaId;
    }

    public void setIntensidadeChuvaId(LongFilter intensidadeChuvaId) {
        this.intensidadeChuvaId = intensidadeChuvaId;
    }

    public LongFilter getUmidadeArId() {
        return umidadeArId;
    }

    public void setUmidadeArId(LongFilter umidadeArId) {
        this.umidadeArId = umidadeArId;
    }

    public LongFilter getAlvoId() {
        return alvoId;
    }

    public void setAlvoId(LongFilter alvoId) {
        this.alvoId = alvoId;
    }

    public LongFilter getPontosCardeaisId() {
        return pontosCardeaisId;
    }

    public void setPontosCardeaisId(LongFilter pontosCardeaisId) {
        this.pontosCardeaisId = pontosCardeaisId;
    }

    public LongFilter getVentoVmFaixaId() {
        return ventoVmFaixaId;
    }

    public void setVentoVmFaixaId(LongFilter ventoVmFaixaId) {
        this.ventoVmFaixaId = ventoVmFaixaId;
    }

    public LongFilter getTempestadeProbabilidadeId() {
        return tempestadeProbabilidadeId;
    }

    public void setTempestadeProbabilidadeId(LongFilter tempestadeProbabilidadeId) {
        this.tempestadeProbabilidadeId = tempestadeProbabilidadeId;
    }

    public LongFilter getTempestadeNivelId() {
        return tempestadeNivelId;
    }

    public void setTempestadeNivelId(LongFilter tempestadeNivelId) {
        this.tempestadeNivelId = tempestadeNivelId;
    }

    public LongFilter getAcumuladoChuvaFaixaId() {
        return acumuladoChuvaFaixaId;
    }

    public void setAcumuladoChuvaFaixaId(LongFilter acumuladoChuvaFaixaId) {
        this.acumuladoChuvaFaixaId = acumuladoChuvaFaixaId;
    }

    public LongFilter getCondicaoTempoId() {
        return condicaoTempoId;
    }

    public void setCondicaoTempoId(LongFilter condicaoTempoId) {
        this.condicaoTempoId = condicaoTempoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BoletimPrevisaoCriteria that = (BoletimPrevisaoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(local, that.local) &&
            Objects.equals(imgCondicaoTempo, that.imgCondicaoTempo) &&
            Objects.equals(condicaoTempo, that.condicaoTempo) &&
            Objects.equals(observacao, that.observacao) &&
            Objects.equals(grupoOrdem, that.grupoOrdem) &&
            Objects.equals(ondas, that.ondas) &&
            Objects.equals(temperaturaDe, that.temperaturaDe) &&
            Objects.equals(temperaturaAte, that.temperaturaAte) &&
            Objects.equals(ventovelocidademediakmh, that.ventovelocidademediakmh) &&
            Objects.equals(ventosObservacao, that.ventosObservacao) &&
            Objects.equals(ventoRajada, that.ventoRajada) &&
            Objects.equals(tempestadeObservacao, that.tempestadeObservacao) &&
            Objects.equals(chuvaObservacao, that.chuvaObservacao) &&
            Objects.equals(created, that.created) &&
            Objects.equals(updated, that.updated) &&
            Objects.equals(boletimId, that.boletimId) &&
            Objects.equals(boletimPrevObsId, that.boletimPrevObsId) &&
            Objects.equals(intensidadeChuvaId, that.intensidadeChuvaId) &&
            Objects.equals(umidadeArId, that.umidadeArId) &&
            Objects.equals(alvoId, that.alvoId) &&
            Objects.equals(pontosCardeaisId, that.pontosCardeaisId) &&
            Objects.equals(ventoVmFaixaId, that.ventoVmFaixaId) &&
            Objects.equals(tempestadeProbabilidadeId, that.tempestadeProbabilidadeId) &&
            Objects.equals(tempestadeNivelId, that.tempestadeNivelId) &&
            Objects.equals(acumuladoChuvaFaixaId, that.acumuladoChuvaFaixaId) &&
            Objects.equals(condicaoTempoId, that.condicaoTempoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nome,
        descricao,
        local,
        imgCondicaoTempo,
        condicaoTempo,
        observacao,
        grupoOrdem,
        ondas,
        temperaturaDe,
        temperaturaAte,
        ventovelocidademediakmh,
        ventosObservacao,
        ventoRajada,
        tempestadeObservacao,
        chuvaObservacao,
        created,
        updated,
        boletimId,
        boletimPrevObsId,
        intensidadeChuvaId,
        umidadeArId,
        alvoId,
        pontosCardeaisId,
        ventoVmFaixaId,
        tempestadeProbabilidadeId,
        tempestadeNivelId,
        acumuladoChuvaFaixaId,
        condicaoTempoId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BoletimPrevisaoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (descricao != null ? "descricao=" + descricao + ", " : "") +
                (local != null ? "local=" + local + ", " : "") +
                (imgCondicaoTempo != null ? "imgCondicaoTempo=" + imgCondicaoTempo + ", " : "") +
                (condicaoTempo != null ? "condicaoTempo=" + condicaoTempo + ", " : "") +
                (observacao != null ? "observacao=" + observacao + ", " : "") +
                (grupoOrdem != null ? "grupoOrdem=" + grupoOrdem + ", " : "") +
                (ondas != null ? "ondas=" + ondas + ", " : "") +
                (temperaturaDe != null ? "temperaturaDe=" + temperaturaDe + ", " : "") +
                (temperaturaAte != null ? "temperaturaAte=" + temperaturaAte + ", " : "") +
                (ventovelocidademediakmh != null ? "ventovelocidademediakmh=" + ventovelocidademediakmh + ", " : "") +
                (ventosObservacao != null ? "ventosObservacao=" + ventosObservacao + ", " : "") +
                (ventoRajada != null ? "ventoRajada=" + ventoRajada + ", " : "") +
                (tempestadeObservacao != null ? "tempestadeObservacao=" + tempestadeObservacao + ", " : "") +
                (chuvaObservacao != null ? "chuvaObservacao=" + chuvaObservacao + ", " : "") +
                (created != null ? "created=" + created + ", " : "") +
                (updated != null ? "updated=" + updated + ", " : "") +
                (boletimId != null ? "boletimId=" + boletimId + ", " : "") +
                (boletimPrevObsId != null ? "boletimPrevObsId=" + boletimPrevObsId + ", " : "") +
                (intensidadeChuvaId != null ? "intensidadeChuvaId=" + intensidadeChuvaId + ", " : "") +
                (umidadeArId != null ? "umidadeArId=" + umidadeArId + ", " : "") +
                (alvoId != null ? "alvoId=" + alvoId + ", " : "") +
                (pontosCardeaisId != null ? "pontosCardeaisId=" + pontosCardeaisId + ", " : "") +
                (ventoVmFaixaId != null ? "ventoVmFaixaId=" + ventoVmFaixaId + ", " : "") +
                (tempestadeProbabilidadeId != null ? "tempestadeProbabilidadeId=" + tempestadeProbabilidadeId + ", " : "") +
                (tempestadeNivelId != null ? "tempestadeNivelId=" + tempestadeNivelId + ", " : "") +
                (acumuladoChuvaFaixaId != null ? "acumuladoChuvaFaixaId=" + acumuladoChuvaFaixaId + ", " : "") +
                (condicaoTempoId != null ? "condicaoTempoId=" + condicaoTempoId + ", " : "") +
            "}";
    }

}
