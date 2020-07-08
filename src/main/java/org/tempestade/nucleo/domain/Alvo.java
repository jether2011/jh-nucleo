package org.tempestade.nucleo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Alvo.
 */
@Entity
@Table(name = "alvo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Alvo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "nome", length = 255, nullable = false, unique = true)
    private String nome;

    @Size(max = 255)
    @Column(name = "nome_reduzido", length = 255)
    private String nomeReduzido;

    @Column(name = "descricao")
    private String descricao;

    @Size(max = 255)
    @Column(name = "primeiro_ponto", length = 255)
    private String primeiroPonto;

    @Size(max = 255)
    @Column(name = "ultimo_ponto", length = 255)
    private String ultimoPonto;

    @NotNull
    @Column(name = "horario_liberacao", nullable = false)
    private Instant horarioLiberacao;

    @NotNull
    @Column(name = "horario", nullable = false)
    private Instant horario;

    @Size(min = 8, max = 8)
    @Pattern(regexp = "^(([0-1]\\d)|(2[0-3])):([0-5]\\d):([0-5]\\d)$")
    @Column(name = "duracao", length = 8)
    private String duracao;

    @Size(min = 8, max = 8)
    @Pattern(regexp = "^(([0-1]\\d)|(2[0-3])):([0-5]\\d):([0-5]\\d)$")
    @Column(name = "duracao_atual", length = 8)
    private String duracaoAtual;

    @NotNull
    @Column(name = "data_desativado", nullable = false)
    private Instant dataDesativado;

    @Size(max = 255)
    @Column(name = "coordenadas_alerta_pontos", length = 255)
    private String coordenadasAlertaPontos;

    @Size(max = 255)
    @Column(name = "coordenadas_liberacao_pontos", length = 255)
    private String coordenadasLiberacaoPontos;

    @Size(max = 255)
    @Column(name = "telegram_token_bot", length = 255)
    private String telegramTokenBot;

    @Size(max = 255)
    @Column(name = "telegram_chat_id", length = 255)
    private String telegramChatId;

    @NotNull
    @Column(name = "horario_bloqueio_notificacao", nullable = false)
    private Instant horarioBloqueioNotificacao;

    @Size(max = 255)
    @Column(name = "coordenadas_original_pontos", length = 255)
    private String coordenadasOriginalPontos;

    @Column(name = "ativo")
    private Boolean ativo;

    @NotNull
    @Column(name = "created", nullable = false)
    private Instant created;

    @Column(name = "updated")
    private Instant updated;

    @ManyToOne
    @JsonIgnoreProperties(value = "alvos", allowSetters = true)
    private Plano plano;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Alvo nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeReduzido() {
        return nomeReduzido;
    }

    public Alvo nomeReduzido(String nomeReduzido) {
        this.nomeReduzido = nomeReduzido;
        return this;
    }

    public void setNomeReduzido(String nomeReduzido) {
        this.nomeReduzido = nomeReduzido;
    }

    public String getDescricao() {
        return descricao;
    }

    public Alvo descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPrimeiroPonto() {
        return primeiroPonto;
    }

    public Alvo primeiroPonto(String primeiroPonto) {
        this.primeiroPonto = primeiroPonto;
        return this;
    }

    public void setPrimeiroPonto(String primeiroPonto) {
        this.primeiroPonto = primeiroPonto;
    }

    public String getUltimoPonto() {
        return ultimoPonto;
    }

    public Alvo ultimoPonto(String ultimoPonto) {
        this.ultimoPonto = ultimoPonto;
        return this;
    }

    public void setUltimoPonto(String ultimoPonto) {
        this.ultimoPonto = ultimoPonto;
    }

    public Instant getHorarioLiberacao() {
        return horarioLiberacao;
    }

    public Alvo horarioLiberacao(Instant horarioLiberacao) {
        this.horarioLiberacao = horarioLiberacao;
        return this;
    }

    public void setHorarioLiberacao(Instant horarioLiberacao) {
        this.horarioLiberacao = horarioLiberacao;
    }

    public Instant getHorario() {
        return horario;
    }

    public Alvo horario(Instant horario) {
        this.horario = horario;
        return this;
    }

    public void setHorario(Instant horario) {
        this.horario = horario;
    }

    public String getDuracao() {
        return duracao;
    }

    public Alvo duracao(String duracao) {
        this.duracao = duracao;
        return this;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public String getDuracaoAtual() {
        return duracaoAtual;
    }

    public Alvo duracaoAtual(String duracaoAtual) {
        this.duracaoAtual = duracaoAtual;
        return this;
    }

    public void setDuracaoAtual(String duracaoAtual) {
        this.duracaoAtual = duracaoAtual;
    }

    public Instant getDataDesativado() {
        return dataDesativado;
    }

    public Alvo dataDesativado(Instant dataDesativado) {
        this.dataDesativado = dataDesativado;
        return this;
    }

    public void setDataDesativado(Instant dataDesativado) {
        this.dataDesativado = dataDesativado;
    }

    public String getCoordenadasAlertaPontos() {
        return coordenadasAlertaPontos;
    }

    public Alvo coordenadasAlertaPontos(String coordenadasAlertaPontos) {
        this.coordenadasAlertaPontos = coordenadasAlertaPontos;
        return this;
    }

    public void setCoordenadasAlertaPontos(String coordenadasAlertaPontos) {
        this.coordenadasAlertaPontos = coordenadasAlertaPontos;
    }

    public String getCoordenadasLiberacaoPontos() {
        return coordenadasLiberacaoPontos;
    }

    public Alvo coordenadasLiberacaoPontos(String coordenadasLiberacaoPontos) {
        this.coordenadasLiberacaoPontos = coordenadasLiberacaoPontos;
        return this;
    }

    public void setCoordenadasLiberacaoPontos(String coordenadasLiberacaoPontos) {
        this.coordenadasLiberacaoPontos = coordenadasLiberacaoPontos;
    }

    public String getTelegramTokenBot() {
        return telegramTokenBot;
    }

    public Alvo telegramTokenBot(String telegramTokenBot) {
        this.telegramTokenBot = telegramTokenBot;
        return this;
    }

    public void setTelegramTokenBot(String telegramTokenBot) {
        this.telegramTokenBot = telegramTokenBot;
    }

    public String getTelegramChatId() {
        return telegramChatId;
    }

    public Alvo telegramChatId(String telegramChatId) {
        this.telegramChatId = telegramChatId;
        return this;
    }

    public void setTelegramChatId(String telegramChatId) {
        this.telegramChatId = telegramChatId;
    }

    public Instant getHorarioBloqueioNotificacao() {
        return horarioBloqueioNotificacao;
    }

    public Alvo horarioBloqueioNotificacao(Instant horarioBloqueioNotificacao) {
        this.horarioBloqueioNotificacao = horarioBloqueioNotificacao;
        return this;
    }

    public void setHorarioBloqueioNotificacao(Instant horarioBloqueioNotificacao) {
        this.horarioBloqueioNotificacao = horarioBloqueioNotificacao;
    }

    public String getCoordenadasOriginalPontos() {
        return coordenadasOriginalPontos;
    }

    public Alvo coordenadasOriginalPontos(String coordenadasOriginalPontos) {
        this.coordenadasOriginalPontos = coordenadasOriginalPontos;
        return this;
    }

    public void setCoordenadasOriginalPontos(String coordenadasOriginalPontos) {
        this.coordenadasOriginalPontos = coordenadasOriginalPontos;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public Alvo ativo(Boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Instant getCreated() {
        return created;
    }

    public Alvo created(Instant created) {
        this.created = created;
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getUpdated() {
        return updated;
    }

    public Alvo updated(Instant updated) {
        this.updated = updated;
        return this;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    public Plano getPlano() {
        return plano;
    }

    public Alvo plano(Plano plano) {
        this.plano = plano;
        return this;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Alvo)) {
            return false;
        }
        return id != null && id.equals(((Alvo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Alvo{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", nomeReduzido='" + getNomeReduzido() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", primeiroPonto='" + getPrimeiroPonto() + "'" +
            ", ultimoPonto='" + getUltimoPonto() + "'" +
            ", horarioLiberacao='" + getHorarioLiberacao() + "'" +
            ", horario='" + getHorario() + "'" +
            ", duracao='" + getDuracao() + "'" +
            ", duracaoAtual='" + getDuracaoAtual() + "'" +
            ", dataDesativado='" + getDataDesativado() + "'" +
            ", coordenadasAlertaPontos='" + getCoordenadasAlertaPontos() + "'" +
            ", coordenadasLiberacaoPontos='" + getCoordenadasLiberacaoPontos() + "'" +
            ", telegramTokenBot='" + getTelegramTokenBot() + "'" +
            ", telegramChatId='" + getTelegramChatId() + "'" +
            ", horarioBloqueioNotificacao='" + getHorarioBloqueioNotificacao() + "'" +
            ", coordenadasOriginalPontos='" + getCoordenadasOriginalPontos() + "'" +
            ", ativo='" + isAtivo() + "'" +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            "}";
    }
}
