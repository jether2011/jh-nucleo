package org.tempestade.nucleo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A JornadaTrabalho.
 */
@Entity
@Table(name = "jornada_trabalho")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class JornadaTrabalho implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "nome", length = 255, nullable = false, unique = true)
    private String nome;

    @NotNull
    @Size(max = 255)
    @Column(name = "descricao", length = 255, nullable = false)
    private String descricao;

    @Size(min = 8, max = 8)
    @Pattern(regexp = "^(([0-1]\\d)|(2[0-3])):([0-5]\\d):([0-5]\\d)$")
    @Column(name = "horainicio", length = 8)
    private String horainicio;

    @Size(min = 8, max = 8)
    @Pattern(regexp = "^(([0-1]\\d)|(2[0-3])):([0-5]\\d):([0-5]\\d)$")
    @Column(name = "duracao", length = 8)
    private String duracao;

    @NotNull
    @Column(name = "created", nullable = false)
    private Instant created;

    @Column(name = "updated")
    private Instant updated;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "jornadaTrabalhos", allowSetters = true)
    private Plano plano;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "jornadaTrabalhos", allowSetters = true)
    private DiaSemana diaSemana;

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

    public JornadaTrabalho nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public JornadaTrabalho descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getHorainicio() {
        return horainicio;
    }

    public JornadaTrabalho horainicio(String horainicio) {
        this.horainicio = horainicio;
        return this;
    }

    public void setHorainicio(String horainicio) {
        this.horainicio = horainicio;
    }

    public String getDuracao() {
        return duracao;
    }

    public JornadaTrabalho duracao(String duracao) {
        this.duracao = duracao;
        return this;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public Instant getCreated() {
        return created;
    }

    public JornadaTrabalho created(Instant created) {
        this.created = created;
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getUpdated() {
        return updated;
    }

    public JornadaTrabalho updated(Instant updated) {
        this.updated = updated;
        return this;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    public Plano getPlano() {
        return plano;
    }

    public JornadaTrabalho plano(Plano plano) {
        this.plano = plano;
        return this;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    public DiaSemana getDiaSemana() {
        return diaSemana;
    }

    public JornadaTrabalho diaSemana(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
        return this;
    }

    public void setDiaSemana(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JornadaTrabalho)) {
            return false;
        }
        return id != null && id.equals(((JornadaTrabalho) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JornadaTrabalho{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", horainicio='" + getHorainicio() + "'" +
            ", duracao='" + getDuracao() + "'" +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            "}";
    }
}
