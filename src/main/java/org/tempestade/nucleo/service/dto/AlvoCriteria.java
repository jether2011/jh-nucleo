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
 * Criteria class for the {@link org.tempestade.nucleo.domain.Alvo} entity. This class is used
 * in {@link org.tempestade.nucleo.web.rest.AlvoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /alvos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AlvoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private StringFilter nomeReduzido;

    private StringFilter descricao;

    private StringFilter primeiroPonto;

    private StringFilter ultimoPonto;

    private InstantFilter horarioLiberacao;

    private InstantFilter horario;

    private StringFilter duracao;

    private StringFilter duracaoAtual;

    private InstantFilter dataDesativado;

    private StringFilter coordenadasAlertaPontos;

    private StringFilter coordenadasLiberacaoPontos;

    private StringFilter telegramTokenBot;

    private StringFilter telegramChatId;

    private InstantFilter horarioBloqueioNotificacao;

    private StringFilter coordenadasOriginalPontos;

    private BooleanFilter ativo;

    private InstantFilter created;

    private InstantFilter updated;

    private LongFilter planoId;

    public AlvoCriteria() {
    }

    public AlvoCriteria(AlvoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.nomeReduzido = other.nomeReduzido == null ? null : other.nomeReduzido.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.primeiroPonto = other.primeiroPonto == null ? null : other.primeiroPonto.copy();
        this.ultimoPonto = other.ultimoPonto == null ? null : other.ultimoPonto.copy();
        this.horarioLiberacao = other.horarioLiberacao == null ? null : other.horarioLiberacao.copy();
        this.horario = other.horario == null ? null : other.horario.copy();
        this.duracao = other.duracao == null ? null : other.duracao.copy();
        this.duracaoAtual = other.duracaoAtual == null ? null : other.duracaoAtual.copy();
        this.dataDesativado = other.dataDesativado == null ? null : other.dataDesativado.copy();
        this.coordenadasAlertaPontos = other.coordenadasAlertaPontos == null ? null : other.coordenadasAlertaPontos.copy();
        this.coordenadasLiberacaoPontos = other.coordenadasLiberacaoPontos == null ? null : other.coordenadasLiberacaoPontos.copy();
        this.telegramTokenBot = other.telegramTokenBot == null ? null : other.telegramTokenBot.copy();
        this.telegramChatId = other.telegramChatId == null ? null : other.telegramChatId.copy();
        this.horarioBloqueioNotificacao = other.horarioBloqueioNotificacao == null ? null : other.horarioBloqueioNotificacao.copy();
        this.coordenadasOriginalPontos = other.coordenadasOriginalPontos == null ? null : other.coordenadasOriginalPontos.copy();
        this.ativo = other.ativo == null ? null : other.ativo.copy();
        this.created = other.created == null ? null : other.created.copy();
        this.updated = other.updated == null ? null : other.updated.copy();
        this.planoId = other.planoId == null ? null : other.planoId.copy();
    }

    @Override
    public AlvoCriteria copy() {
        return new AlvoCriteria(this);
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

    public StringFilter getNomeReduzido() {
        return nomeReduzido;
    }

    public void setNomeReduzido(StringFilter nomeReduzido) {
        this.nomeReduzido = nomeReduzido;
    }

    public StringFilter getDescricao() {
        return descricao;
    }

    public void setDescricao(StringFilter descricao) {
        this.descricao = descricao;
    }

    public StringFilter getPrimeiroPonto() {
        return primeiroPonto;
    }

    public void setPrimeiroPonto(StringFilter primeiroPonto) {
        this.primeiroPonto = primeiroPonto;
    }

    public StringFilter getUltimoPonto() {
        return ultimoPonto;
    }

    public void setUltimoPonto(StringFilter ultimoPonto) {
        this.ultimoPonto = ultimoPonto;
    }

    public InstantFilter getHorarioLiberacao() {
        return horarioLiberacao;
    }

    public void setHorarioLiberacao(InstantFilter horarioLiberacao) {
        this.horarioLiberacao = horarioLiberacao;
    }

    public InstantFilter getHorario() {
        return horario;
    }

    public void setHorario(InstantFilter horario) {
        this.horario = horario;
    }

    public StringFilter getDuracao() {
        return duracao;
    }

    public void setDuracao(StringFilter duracao) {
        this.duracao = duracao;
    }

    public StringFilter getDuracaoAtual() {
        return duracaoAtual;
    }

    public void setDuracaoAtual(StringFilter duracaoAtual) {
        this.duracaoAtual = duracaoAtual;
    }

    public InstantFilter getDataDesativado() {
        return dataDesativado;
    }

    public void setDataDesativado(InstantFilter dataDesativado) {
        this.dataDesativado = dataDesativado;
    }

    public StringFilter getCoordenadasAlertaPontos() {
        return coordenadasAlertaPontos;
    }

    public void setCoordenadasAlertaPontos(StringFilter coordenadasAlertaPontos) {
        this.coordenadasAlertaPontos = coordenadasAlertaPontos;
    }

    public StringFilter getCoordenadasLiberacaoPontos() {
        return coordenadasLiberacaoPontos;
    }

    public void setCoordenadasLiberacaoPontos(StringFilter coordenadasLiberacaoPontos) {
        this.coordenadasLiberacaoPontos = coordenadasLiberacaoPontos;
    }

    public StringFilter getTelegramTokenBot() {
        return telegramTokenBot;
    }

    public void setTelegramTokenBot(StringFilter telegramTokenBot) {
        this.telegramTokenBot = telegramTokenBot;
    }

    public StringFilter getTelegramChatId() {
        return telegramChatId;
    }

    public void setTelegramChatId(StringFilter telegramChatId) {
        this.telegramChatId = telegramChatId;
    }

    public InstantFilter getHorarioBloqueioNotificacao() {
        return horarioBloqueioNotificacao;
    }

    public void setHorarioBloqueioNotificacao(InstantFilter horarioBloqueioNotificacao) {
        this.horarioBloqueioNotificacao = horarioBloqueioNotificacao;
    }

    public StringFilter getCoordenadasOriginalPontos() {
        return coordenadasOriginalPontos;
    }

    public void setCoordenadasOriginalPontos(StringFilter coordenadasOriginalPontos) {
        this.coordenadasOriginalPontos = coordenadasOriginalPontos;
    }

    public BooleanFilter getAtivo() {
        return ativo;
    }

    public void setAtivo(BooleanFilter ativo) {
        this.ativo = ativo;
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

    public LongFilter getPlanoId() {
        return planoId;
    }

    public void setPlanoId(LongFilter planoId) {
        this.planoId = planoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AlvoCriteria that = (AlvoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(nomeReduzido, that.nomeReduzido) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(primeiroPonto, that.primeiroPonto) &&
            Objects.equals(ultimoPonto, that.ultimoPonto) &&
            Objects.equals(horarioLiberacao, that.horarioLiberacao) &&
            Objects.equals(horario, that.horario) &&
            Objects.equals(duracao, that.duracao) &&
            Objects.equals(duracaoAtual, that.duracaoAtual) &&
            Objects.equals(dataDesativado, that.dataDesativado) &&
            Objects.equals(coordenadasAlertaPontos, that.coordenadasAlertaPontos) &&
            Objects.equals(coordenadasLiberacaoPontos, that.coordenadasLiberacaoPontos) &&
            Objects.equals(telegramTokenBot, that.telegramTokenBot) &&
            Objects.equals(telegramChatId, that.telegramChatId) &&
            Objects.equals(horarioBloqueioNotificacao, that.horarioBloqueioNotificacao) &&
            Objects.equals(coordenadasOriginalPontos, that.coordenadasOriginalPontos) &&
            Objects.equals(ativo, that.ativo) &&
            Objects.equals(created, that.created) &&
            Objects.equals(updated, that.updated) &&
            Objects.equals(planoId, that.planoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nome,
        nomeReduzido,
        descricao,
        primeiroPonto,
        ultimoPonto,
        horarioLiberacao,
        horario,
        duracao,
        duracaoAtual,
        dataDesativado,
        coordenadasAlertaPontos,
        coordenadasLiberacaoPontos,
        telegramTokenBot,
        telegramChatId,
        horarioBloqueioNotificacao,
        coordenadasOriginalPontos,
        ativo,
        created,
        updated,
        planoId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AlvoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (nomeReduzido != null ? "nomeReduzido=" + nomeReduzido + ", " : "") +
                (descricao != null ? "descricao=" + descricao + ", " : "") +
                (primeiroPonto != null ? "primeiroPonto=" + primeiroPonto + ", " : "") +
                (ultimoPonto != null ? "ultimoPonto=" + ultimoPonto + ", " : "") +
                (horarioLiberacao != null ? "horarioLiberacao=" + horarioLiberacao + ", " : "") +
                (horario != null ? "horario=" + horario + ", " : "") +
                (duracao != null ? "duracao=" + duracao + ", " : "") +
                (duracaoAtual != null ? "duracaoAtual=" + duracaoAtual + ", " : "") +
                (dataDesativado != null ? "dataDesativado=" + dataDesativado + ", " : "") +
                (coordenadasAlertaPontos != null ? "coordenadasAlertaPontos=" + coordenadasAlertaPontos + ", " : "") +
                (coordenadasLiberacaoPontos != null ? "coordenadasLiberacaoPontos=" + coordenadasLiberacaoPontos + ", " : "") +
                (telegramTokenBot != null ? "telegramTokenBot=" + telegramTokenBot + ", " : "") +
                (telegramChatId != null ? "telegramChatId=" + telegramChatId + ", " : "") +
                (horarioBloqueioNotificacao != null ? "horarioBloqueioNotificacao=" + horarioBloqueioNotificacao + ", " : "") +
                (coordenadasOriginalPontos != null ? "coordenadasOriginalPontos=" + coordenadasOriginalPontos + ", " : "") +
                (ativo != null ? "ativo=" + ativo + ", " : "") +
                (created != null ? "created=" + created + ", " : "") +
                (updated != null ? "updated=" + updated + ", " : "") +
                (planoId != null ? "planoId=" + planoId + ", " : "") +
            "}";
    }

}
