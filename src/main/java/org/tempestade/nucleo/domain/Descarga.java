package org.tempestade.nucleo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Descarga.
 */
@Entity
@Table(name = "descarga")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Descarga implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "nome", length = 255, nullable = false, unique = true)
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "qtd")
    private Integer qtd;

    @NotNull
    @Column(name = "data_primeira_descarga", nullable = false)
    private Instant dataPrimeiraDescarga;

    @Size(min = 8, max = 8)
    @Pattern(regexp = "^(([0-1]\\d)|(2[0-3])):([0-5]\\d):([0-5]\\d)$")
    @Column(name = "tempo_antecipacao", length = 8)
    private String tempoAntecipacao;

    @NotNull
    @Column(name = "created", nullable = false)
    private Instant created;

    @Column(name = "updated")
    private Instant updated;

    @ManyToOne
    @JsonIgnoreProperties(value = "descargas", allowSetters = true)
    private Rede rede;

    @ManyToOne
    @JsonIgnoreProperties(value = "descargas", allowSetters = true)
    private DescargaTipo descargaTipo;

    @ManyToOne
    @JsonIgnoreProperties(value = "descargas", allowSetters = true)
    private DescargaUnidade descargaUnidade;

    @ManyToOne
    @JsonIgnoreProperties(value = "descargas", allowSetters = true)
    private Alerta alerta;

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

    public Descarga nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Descarga descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getQtd() {
        return qtd;
    }

    public Descarga qtd(Integer qtd) {
        this.qtd = qtd;
        return this;
    }

    public void setQtd(Integer qtd) {
        this.qtd = qtd;
    }

    public Instant getDataPrimeiraDescarga() {
        return dataPrimeiraDescarga;
    }

    public Descarga dataPrimeiraDescarga(Instant dataPrimeiraDescarga) {
        this.dataPrimeiraDescarga = dataPrimeiraDescarga;
        return this;
    }

    public void setDataPrimeiraDescarga(Instant dataPrimeiraDescarga) {
        this.dataPrimeiraDescarga = dataPrimeiraDescarga;
    }

    public String getTempoAntecipacao() {
        return tempoAntecipacao;
    }

    public Descarga tempoAntecipacao(String tempoAntecipacao) {
        this.tempoAntecipacao = tempoAntecipacao;
        return this;
    }

    public void setTempoAntecipacao(String tempoAntecipacao) {
        this.tempoAntecipacao = tempoAntecipacao;
    }

    public Instant getCreated() {
        return created;
    }

    public Descarga created(Instant created) {
        this.created = created;
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getUpdated() {
        return updated;
    }

    public Descarga updated(Instant updated) {
        this.updated = updated;
        return this;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    public Rede getRede() {
        return rede;
    }

    public Descarga rede(Rede rede) {
        this.rede = rede;
        return this;
    }

    public void setRede(Rede rede) {
        this.rede = rede;
    }

    public DescargaTipo getDescargaTipo() {
        return descargaTipo;
    }

    public Descarga descargaTipo(DescargaTipo descargaTipo) {
        this.descargaTipo = descargaTipo;
        return this;
    }

    public void setDescargaTipo(DescargaTipo descargaTipo) {
        this.descargaTipo = descargaTipo;
    }

    public DescargaUnidade getDescargaUnidade() {
        return descargaUnidade;
    }

    public Descarga descargaUnidade(DescargaUnidade descargaUnidade) {
        this.descargaUnidade = descargaUnidade;
        return this;
    }

    public void setDescargaUnidade(DescargaUnidade descargaUnidade) {
        this.descargaUnidade = descargaUnidade;
    }

    public Alerta getAlerta() {
        return alerta;
    }

    public Descarga alerta(Alerta alerta) {
        this.alerta = alerta;
        return this;
    }

    public void setAlerta(Alerta alerta) {
        this.alerta = alerta;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Descarga)) {
            return false;
        }
        return id != null && id.equals(((Descarga) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Descarga{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", qtd=" + getQtd() +
            ", dataPrimeiraDescarga='" + getDataPrimeiraDescarga() + "'" +
            ", tempoAntecipacao='" + getTempoAntecipacao() + "'" +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            "}";
    }
}
