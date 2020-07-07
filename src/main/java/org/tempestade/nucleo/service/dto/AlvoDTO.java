package org.tempestade.nucleo.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link org.tempestade.nucleo.domain.Alvo} entity.
 */
public class AlvoDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 255)
    private String nome;

    @Size(max = 255)
    private String nomeReduzido;

    private String descricao;

    @Size(max = 255)
    private String primeiroPonto;

    @Size(max = 255)
    private String ultimoPonto;

    @NotNull
    private Instant horarioLiberacao;

    @NotNull
    private Instant horario;

    @Size(min = 8, max = 8)
    @Pattern(regexp = "^(([0-1]\\d)|(2[0-3])):([0-5]\\d):([0-5]\\d)$")
    private String duracao;

    @Size(min = 8, max = 8)
    @Pattern(regexp = "^(([0-1]\\d)|(2[0-3])):([0-5]\\d):([0-5]\\d)$")
    private String duracaoAtual;

    @NotNull
    private Instant dataDesativado;

    @Size(max = 255)
    private String coordenadasAlertaPontos;

    @Size(max = 255)
    private String coordenadasLiberacaoPontos;

    @Size(max = 255)
    private String telegramTokenBot;

    @Size(max = 255)
    private String telegramChatId;

    @NotNull
    private Instant horarioBloqueioNotificacao;

    @Size(max = 255)
    private String coordenadasOriginalPontos;

    private Boolean ativo;

    @NotNull
    private Instant created;

    private Instant updated;


    private Long planoId;
    
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

    public String getNomeReduzido() {
        return nomeReduzido;
    }

    public void setNomeReduzido(String nomeReduzido) {
        this.nomeReduzido = nomeReduzido;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPrimeiroPonto() {
        return primeiroPonto;
    }

    public void setPrimeiroPonto(String primeiroPonto) {
        this.primeiroPonto = primeiroPonto;
    }

    public String getUltimoPonto() {
        return ultimoPonto;
    }

    public void setUltimoPonto(String ultimoPonto) {
        this.ultimoPonto = ultimoPonto;
    }

    public Instant getHorarioLiberacao() {
        return horarioLiberacao;
    }

    public void setHorarioLiberacao(Instant horarioLiberacao) {
        this.horarioLiberacao = horarioLiberacao;
    }

    public Instant getHorario() {
        return horario;
    }

    public void setHorario(Instant horario) {
        this.horario = horario;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public String getDuracaoAtual() {
        return duracaoAtual;
    }

    public void setDuracaoAtual(String duracaoAtual) {
        this.duracaoAtual = duracaoAtual;
    }

    public Instant getDataDesativado() {
        return dataDesativado;
    }

    public void setDataDesativado(Instant dataDesativado) {
        this.dataDesativado = dataDesativado;
    }

    public String getCoordenadasAlertaPontos() {
        return coordenadasAlertaPontos;
    }

    public void setCoordenadasAlertaPontos(String coordenadasAlertaPontos) {
        this.coordenadasAlertaPontos = coordenadasAlertaPontos;
    }

    public String getCoordenadasLiberacaoPontos() {
        return coordenadasLiberacaoPontos;
    }

    public void setCoordenadasLiberacaoPontos(String coordenadasLiberacaoPontos) {
        this.coordenadasLiberacaoPontos = coordenadasLiberacaoPontos;
    }

    public String getTelegramTokenBot() {
        return telegramTokenBot;
    }

    public void setTelegramTokenBot(String telegramTokenBot) {
        this.telegramTokenBot = telegramTokenBot;
    }

    public String getTelegramChatId() {
        return telegramChatId;
    }

    public void setTelegramChatId(String telegramChatId) {
        this.telegramChatId = telegramChatId;
    }

    public Instant getHorarioBloqueioNotificacao() {
        return horarioBloqueioNotificacao;
    }

    public void setHorarioBloqueioNotificacao(Instant horarioBloqueioNotificacao) {
        this.horarioBloqueioNotificacao = horarioBloqueioNotificacao;
    }

    public String getCoordenadasOriginalPontos() {
        return coordenadasOriginalPontos;
    }

    public void setCoordenadasOriginalPontos(String coordenadasOriginalPontos) {
        this.coordenadasOriginalPontos = coordenadasOriginalPontos;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
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

    public Long getPlanoId() {
        return planoId;
    }

    public void setPlanoId(Long planoId) {
        this.planoId = planoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AlvoDTO)) {
            return false;
        }

        return id != null && id.equals(((AlvoDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AlvoDTO{" +
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
            ", planoId=" + getPlanoId() +
            "}";
    }
}
