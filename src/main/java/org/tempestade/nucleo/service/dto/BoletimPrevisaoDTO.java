package org.tempestade.nucleo.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link org.tempestade.nucleo.domain.BoletimPrevisao} entity.
 */
public class BoletimPrevisaoDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 255)
    private String nome;

    private String descricao;

    @Size(max = 255)
    private String local;

    private Integer imgCondicaoTempo;

    @Size(max = 255)
    private String condicaoTempo;

    @Size(max = 255)
    private String observacao;

    private Integer grupoOrdem;

    @Size(max = 255)
    private String ondas;

    private Integer temperaturaDe;

    private Integer temperaturaAte;

    private Integer ventovelocidademediakmh;

    @Size(max = 255)
    private String ventosObservacao;

    private Boolean ventoRajada;

    @Size(max = 255)
    private String tempestadeObservacao;

    @Size(max = 255)
    private String chuvaObservacao;

    @NotNull
    private Instant created;

    private Instant updated;


    private Long boletimId;

    private Long boletimPrevObsId;

    private Long intensidadeChuvaId;

    private Long umidadeArId;

    private Long alvoId;

    private Long ventosOrigemPontosCardeaisId;

    private Long ventoRajadaVentoVmFaixaId;

    private Long tempestadeProbabilidadeId;

    private Long tempestadeNivelId;

    private Long acumuladoChuvaFaixaId;

    private Long condicaoTempoId;
    
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public Integer getImgCondicaoTempo() {
        return imgCondicaoTempo;
    }

    public void setImgCondicaoTempo(Integer imgCondicaoTempo) {
        this.imgCondicaoTempo = imgCondicaoTempo;
    }

    public String getCondicaoTempo() {
        return condicaoTempo;
    }

    public void setCondicaoTempo(String condicaoTempo) {
        this.condicaoTempo = condicaoTempo;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Integer getGrupoOrdem() {
        return grupoOrdem;
    }

    public void setGrupoOrdem(Integer grupoOrdem) {
        this.grupoOrdem = grupoOrdem;
    }

    public String getOndas() {
        return ondas;
    }

    public void setOndas(String ondas) {
        this.ondas = ondas;
    }

    public Integer getTemperaturaDe() {
        return temperaturaDe;
    }

    public void setTemperaturaDe(Integer temperaturaDe) {
        this.temperaturaDe = temperaturaDe;
    }

    public Integer getTemperaturaAte() {
        return temperaturaAte;
    }

    public void setTemperaturaAte(Integer temperaturaAte) {
        this.temperaturaAte = temperaturaAte;
    }

    public Integer getVentovelocidademediakmh() {
        return ventovelocidademediakmh;
    }

    public void setVentovelocidademediakmh(Integer ventovelocidademediakmh) {
        this.ventovelocidademediakmh = ventovelocidademediakmh;
    }

    public String getVentosObservacao() {
        return ventosObservacao;
    }

    public void setVentosObservacao(String ventosObservacao) {
        this.ventosObservacao = ventosObservacao;
    }

    public Boolean isVentoRajada() {
        return ventoRajada;
    }

    public void setVentoRajada(Boolean ventoRajada) {
        this.ventoRajada = ventoRajada;
    }

    public String getTempestadeObservacao() {
        return tempestadeObservacao;
    }

    public void setTempestadeObservacao(String tempestadeObservacao) {
        this.tempestadeObservacao = tempestadeObservacao;
    }

    public String getChuvaObservacao() {
        return chuvaObservacao;
    }

    public void setChuvaObservacao(String chuvaObservacao) {
        this.chuvaObservacao = chuvaObservacao;
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

    public Long getBoletimId() {
        return boletimId;
    }

    public void setBoletimId(Long boletimId) {
        this.boletimId = boletimId;
    }

    public Long getBoletimPrevObsId() {
        return boletimPrevObsId;
    }

    public void setBoletimPrevObsId(Long boletimPrevObsId) {
        this.boletimPrevObsId = boletimPrevObsId;
    }

    public Long getIntensidadeChuvaId() {
        return intensidadeChuvaId;
    }

    public void setIntensidadeChuvaId(Long intensidadeChuvaId) {
        this.intensidadeChuvaId = intensidadeChuvaId;
    }

    public Long getUmidadeArId() {
        return umidadeArId;
    }

    public void setUmidadeArId(Long umidadeArId) {
        this.umidadeArId = umidadeArId;
    }

    public Long getAlvoId() {
        return alvoId;
    }

    public void setAlvoId(Long alvoId) {
        this.alvoId = alvoId;
    }

    public Long getVentosOrigemPontosCardeaisId() {
        return ventosOrigemPontosCardeaisId;
    }

    public void setVentosOrigemPontosCardeaisId(Long pontosCardeaisId) {
        this.ventosOrigemPontosCardeaisId = pontosCardeaisId;
    }

    public Long getVentoRajadaVentoVmFaixaId() {
        return ventoRajadaVentoVmFaixaId;
    }

    public void setVentoRajadaVentoVmFaixaId(Long ventoVmFaixaId) {
        this.ventoRajadaVentoVmFaixaId = ventoVmFaixaId;
    }

    public Long getTempestadeProbabilidadeId() {
        return tempestadeProbabilidadeId;
    }

    public void setTempestadeProbabilidadeId(Long tempestadeProbabilidadeId) {
        this.tempestadeProbabilidadeId = tempestadeProbabilidadeId;
    }

    public Long getTempestadeNivelId() {
        return tempestadeNivelId;
    }

    public void setTempestadeNivelId(Long tempestadeNivelId) {
        this.tempestadeNivelId = tempestadeNivelId;
    }

    public Long getAcumuladoChuvaFaixaId() {
        return acumuladoChuvaFaixaId;
    }

    public void setAcumuladoChuvaFaixaId(Long acumuladoChuvaFaixaId) {
        this.acumuladoChuvaFaixaId = acumuladoChuvaFaixaId;
    }

    public Long getCondicaoTempoId() {
        return condicaoTempoId;
    }

    public void setCondicaoTempoId(Long condicaoTempoId) {
        this.condicaoTempoId = condicaoTempoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BoletimPrevisaoDTO)) {
            return false;
        }

        return id != null && id.equals(((BoletimPrevisaoDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BoletimPrevisaoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", local='" + getLocal() + "'" +
            ", imgCondicaoTempo=" + getImgCondicaoTempo() +
            ", condicaoTempo='" + getCondicaoTempo() + "'" +
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
            ", boletimId=" + getBoletimId() +
            ", boletimPrevObsId=" + getBoletimPrevObsId() +
            ", intensidadeChuvaId=" + getIntensidadeChuvaId() +
            ", umidadeArId=" + getUmidadeArId() +
            ", alvoId=" + getAlvoId() +
            ", ventosOrigemPontosCardeaisId=" + getVentosOrigemPontosCardeaisId() +
            ", ventoRajadaVentoVmFaixaId=" + getVentoRajadaVentoVmFaixaId() +
            ", tempestadeProbabilidadeId=" + getTempestadeProbabilidadeId() +
            ", tempestadeNivelId=" + getTempestadeNivelId() +
            ", acumuladoChuvaFaixaId=" + getAcumuladoChuvaFaixaId() +
            ", condicaoTempoId=" + getCondicaoTempoId() +
            "}";
    }
}
