package org.tempestade.nucleo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A BoletimPrevisao.
 */
@Entity
@Table(name = "boletim_previsao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BoletimPrevisao implements Serializable {

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

    @Size(max = 255)
    @Column(name = "local", length = 255)
    private String local;

    @Column(name = "img_condicao_tempo")
    private Integer imgCondicaoTempo;

    @Size(max = 255)
    @Column(name = "observacao", length = 255)
    private String observacao;

    @Column(name = "grupo_ordem")
    private Integer grupoOrdem;

    @Size(max = 255)
    @Column(name = "ondas", length = 255)
    private String ondas;

    @Column(name = "temperatura_de")
    private Integer temperaturaDe;

    @Column(name = "temperatura_ate")
    private Integer temperaturaAte;

    @Column(name = "ventovelocidademediakmh")
    private Integer ventovelocidademediakmh;

    @Size(max = 255)
    @Column(name = "ventos_observacao", length = 255)
    private String ventosObservacao;

    @Column(name = "vento_rajada")
    private Boolean ventoRajada;

    @Size(max = 255)
    @Column(name = "tempestade_observacao", length = 255)
    private String tempestadeObservacao;

    @Size(max = 255)
    @Column(name = "chuva_observacao", length = 255)
    private String chuvaObservacao;

    @NotNull
    @Column(name = "created", nullable = false)
    private Instant created;

    @Column(name = "updated")
    private Instant updated;

    @ManyToOne
    @JsonIgnoreProperties(value = "boletimPrevisaos", allowSetters = true)
    private Boletim boletim;

    @ManyToOne
    @JsonIgnoreProperties(value = "boletimPrevisaos", allowSetters = true)
    private BoletimPrevObs boletimPrevObs;

    @ManyToOne
    @JsonIgnoreProperties(value = "boletimPrevisaos", allowSetters = true)
    private IntensidadeChuva intensidadeChuva;

    @ManyToOne
    @JsonIgnoreProperties(value = "boletimPrevisaos", allowSetters = true)
    private UmidadeAr umidadeAr;

    @ManyToOne
    @JsonIgnoreProperties(value = "boletimPrevisaos", allowSetters = true)
    private Alvo alvo;

    @ManyToOne
    @JsonIgnoreProperties(value = "boletimPrevisaos", allowSetters = true)
    private PontosCardeais pontosCardeais;

    @ManyToOne
    @JsonIgnoreProperties(value = "boletimPrevisaos", allowSetters = true)
    private VentoVmFaixa ventoVmFaixa;

    @ManyToOne
    @JsonIgnoreProperties(value = "boletimPrevisaos", allowSetters = true)
    private TempestadeProbabilidade tempestadeProbabilidade;

    @ManyToOne
    @JsonIgnoreProperties(value = "boletimPrevisaos", allowSetters = true)
    private TempestadeNivel tempestadeNivel;

    @ManyToOne
    @JsonIgnoreProperties(value = "boletimPrevisaos", allowSetters = true)
    private AcumuladoChuvaFaixa acumuladoChuvaFaixa;

    @ManyToOne
    @JsonIgnoreProperties(value = "boletimPrevisaos", allowSetters = true)
    private CondicaoTempo condicaoTempo;

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

    public BoletimPrevisao nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public BoletimPrevisao descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLocal() {
        return local;
    }

    public BoletimPrevisao local(String local) {
        this.local = local;
        return this;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public Integer getImgCondicaoTempo() {
        return imgCondicaoTempo;
    }

    public BoletimPrevisao imgCondicaoTempo(Integer imgCondicaoTempo) {
        this.imgCondicaoTempo = imgCondicaoTempo;
        return this;
    }

    public void setImgCondicaoTempo(Integer imgCondicaoTempo) {
        this.imgCondicaoTempo = imgCondicaoTempo;
    }

    public String getObservacao() {
        return observacao;
    }

    public BoletimPrevisao observacao(String observacao) {
        this.observacao = observacao;
        return this;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Integer getGrupoOrdem() {
        return grupoOrdem;
    }

    public BoletimPrevisao grupoOrdem(Integer grupoOrdem) {
        this.grupoOrdem = grupoOrdem;
        return this;
    }

    public void setGrupoOrdem(Integer grupoOrdem) {
        this.grupoOrdem = grupoOrdem;
    }

    public String getOndas() {
        return ondas;
    }

    public BoletimPrevisao ondas(String ondas) {
        this.ondas = ondas;
        return this;
    }

    public void setOndas(String ondas) {
        this.ondas = ondas;
    }

    public Integer getTemperaturaDe() {
        return temperaturaDe;
    }

    public BoletimPrevisao temperaturaDe(Integer temperaturaDe) {
        this.temperaturaDe = temperaturaDe;
        return this;
    }

    public void setTemperaturaDe(Integer temperaturaDe) {
        this.temperaturaDe = temperaturaDe;
    }

    public Integer getTemperaturaAte() {
        return temperaturaAte;
    }

    public BoletimPrevisao temperaturaAte(Integer temperaturaAte) {
        this.temperaturaAte = temperaturaAte;
        return this;
    }

    public void setTemperaturaAte(Integer temperaturaAte) {
        this.temperaturaAte = temperaturaAte;
    }

    public Integer getVentovelocidademediakmh() {
        return ventovelocidademediakmh;
    }

    public BoletimPrevisao ventovelocidademediakmh(Integer ventovelocidademediakmh) {
        this.ventovelocidademediakmh = ventovelocidademediakmh;
        return this;
    }

    public void setVentovelocidademediakmh(Integer ventovelocidademediakmh) {
        this.ventovelocidademediakmh = ventovelocidademediakmh;
    }

    public String getVentosObservacao() {
        return ventosObservacao;
    }

    public BoletimPrevisao ventosObservacao(String ventosObservacao) {
        this.ventosObservacao = ventosObservacao;
        return this;
    }

    public void setVentosObservacao(String ventosObservacao) {
        this.ventosObservacao = ventosObservacao;
    }

    public Boolean isVentoRajada() {
        return ventoRajada;
    }

    public BoletimPrevisao ventoRajada(Boolean ventoRajada) {
        this.ventoRajada = ventoRajada;
        return this;
    }

    public void setVentoRajada(Boolean ventoRajada) {
        this.ventoRajada = ventoRajada;
    }

    public String getTempestadeObservacao() {
        return tempestadeObservacao;
    }

    public BoletimPrevisao tempestadeObservacao(String tempestadeObservacao) {
        this.tempestadeObservacao = tempestadeObservacao;
        return this;
    }

    public void setTempestadeObservacao(String tempestadeObservacao) {
        this.tempestadeObservacao = tempestadeObservacao;
    }

    public String getChuvaObservacao() {
        return chuvaObservacao;
    }

    public BoletimPrevisao chuvaObservacao(String chuvaObservacao) {
        this.chuvaObservacao = chuvaObservacao;
        return this;
    }

    public void setChuvaObservacao(String chuvaObservacao) {
        this.chuvaObservacao = chuvaObservacao;
    }

    public Instant getCreated() {
        return created;
    }

    public BoletimPrevisao created(Instant created) {
        this.created = created;
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getUpdated() {
        return updated;
    }

    public BoletimPrevisao updated(Instant updated) {
        this.updated = updated;
        return this;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    public Boletim getBoletim() {
        return boletim;
    }

    public BoletimPrevisao boletim(Boletim boletim) {
        this.boletim = boletim;
        return this;
    }

    public void setBoletim(Boletim boletim) {
        this.boletim = boletim;
    }

    public BoletimPrevObs getBoletimPrevObs() {
        return boletimPrevObs;
    }

    public BoletimPrevisao boletimPrevObs(BoletimPrevObs boletimPrevObs) {
        this.boletimPrevObs = boletimPrevObs;
        return this;
    }

    public void setBoletimPrevObs(BoletimPrevObs boletimPrevObs) {
        this.boletimPrevObs = boletimPrevObs;
    }

    public IntensidadeChuva getIntensidadeChuva() {
        return intensidadeChuva;
    }

    public BoletimPrevisao intensidadeChuva(IntensidadeChuva intensidadeChuva) {
        this.intensidadeChuva = intensidadeChuva;
        return this;
    }

    public void setIntensidadeChuva(IntensidadeChuva intensidadeChuva) {
        this.intensidadeChuva = intensidadeChuva;
    }

    public UmidadeAr getUmidadeAr() {
        return umidadeAr;
    }

    public BoletimPrevisao umidadeAr(UmidadeAr umidadeAr) {
        this.umidadeAr = umidadeAr;
        return this;
    }

    public void setUmidadeAr(UmidadeAr umidadeAr) {
        this.umidadeAr = umidadeAr;
    }

    public Alvo getAlvo() {
        return alvo;
    }

    public BoletimPrevisao alvo(Alvo alvo) {
        this.alvo = alvo;
        return this;
    }

    public void setAlvo(Alvo alvo) {
        this.alvo = alvo;
    }

    public PontosCardeais getPontosCardeais() {
        return pontosCardeais;
    }

    public BoletimPrevisao pontosCardeais(PontosCardeais pontosCardeais) {
        this.pontosCardeais = pontosCardeais;
        return this;
    }

    public void setPontosCardeais(PontosCardeais pontosCardeais) {
        this.pontosCardeais = pontosCardeais;
    }

    public VentoVmFaixa getVentoVmFaixa() {
        return ventoVmFaixa;
    }

    public BoletimPrevisao ventoVmFaixa(VentoVmFaixa ventoVmFaixa) {
        this.ventoVmFaixa = ventoVmFaixa;
        return this;
    }

    public void setVentoVmFaixa(VentoVmFaixa ventoVmFaixa) {
        this.ventoVmFaixa = ventoVmFaixa;
    }

    public TempestadeProbabilidade getTempestadeProbabilidade() {
        return tempestadeProbabilidade;
    }

    public BoletimPrevisao tempestadeProbabilidade(TempestadeProbabilidade tempestadeProbabilidade) {
        this.tempestadeProbabilidade = tempestadeProbabilidade;
        return this;
    }

    public void setTempestadeProbabilidade(TempestadeProbabilidade tempestadeProbabilidade) {
        this.tempestadeProbabilidade = tempestadeProbabilidade;
    }

    public TempestadeNivel getTempestadeNivel() {
        return tempestadeNivel;
    }

    public BoletimPrevisao tempestadeNivel(TempestadeNivel tempestadeNivel) {
        this.tempestadeNivel = tempestadeNivel;
        return this;
    }

    public void setTempestadeNivel(TempestadeNivel tempestadeNivel) {
        this.tempestadeNivel = tempestadeNivel;
    }

    public AcumuladoChuvaFaixa getAcumuladoChuvaFaixa() {
        return acumuladoChuvaFaixa;
    }

    public BoletimPrevisao acumuladoChuvaFaixa(AcumuladoChuvaFaixa acumuladoChuvaFaixa) {
        this.acumuladoChuvaFaixa = acumuladoChuvaFaixa;
        return this;
    }

    public void setAcumuladoChuvaFaixa(AcumuladoChuvaFaixa acumuladoChuvaFaixa) {
        this.acumuladoChuvaFaixa = acumuladoChuvaFaixa;
    }

    public CondicaoTempo getCondicaoTempo() {
        return condicaoTempo;
    }

    public BoletimPrevisao condicaoTempo(CondicaoTempo condicaoTempo) {
        this.condicaoTempo = condicaoTempo;
        return this;
    }

    public void setCondicaoTempo(CondicaoTempo condicaoTempo) {
        this.condicaoTempo = condicaoTempo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BoletimPrevisao)) {
            return false;
        }
        return id != null && id.equals(((BoletimPrevisao) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BoletimPrevisao{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", local='" + getLocal() + "'" +
            ", imgCondicaoTempo=" + getImgCondicaoTempo() +
            ", observacao='" + getObservacao() + "'" +
            ", grupoOrdem=" + getGrupoOrdem() +
            ", ondas='" + getOndas() + "'" +
            ", temperaturaDe=" + getTemperaturaDe() +
            ", temperaturaAte=" + getTemperaturaAte() +
            ", ventovelocidademediakmh=" + getVentovelocidademediakmh() +
            ", ventosObservacao='" + getVentosObservacao() + "'" +
            ", ventoRajada='" + isVentoRajada() + "'" +
            ", tempestadeObservacao='" + getTempestadeObservacao() + "'" +
            ", chuvaObservacao='" + getChuvaObservacao() + "'" +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            "}";
    }
}
