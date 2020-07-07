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
 * Criteria class for the {@link org.tempestade.nucleo.domain.Meteograma} entity. This class is used
 * in {@link org.tempestade.nucleo.web.rest.MeteogramaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /meteogramas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MeteogramaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter descricao;

    private StringFilter arquivo;

    private StringFilter folder;

    private StringFilter tipoarquivo;

    private InstantFilter created;

    private InstantFilter updated;

    private LongFilter planoId;

    public MeteogramaCriteria() {
    }

    public MeteogramaCriteria(MeteogramaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.arquivo = other.arquivo == null ? null : other.arquivo.copy();
        this.folder = other.folder == null ? null : other.folder.copy();
        this.tipoarquivo = other.tipoarquivo == null ? null : other.tipoarquivo.copy();
        this.created = other.created == null ? null : other.created.copy();
        this.updated = other.updated == null ? null : other.updated.copy();
        this.planoId = other.planoId == null ? null : other.planoId.copy();
    }

    @Override
    public MeteogramaCriteria copy() {
        return new MeteogramaCriteria(this);
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

    public StringFilter getArquivo() {
        return arquivo;
    }

    public void setArquivo(StringFilter arquivo) {
        this.arquivo = arquivo;
    }

    public StringFilter getFolder() {
        return folder;
    }

    public void setFolder(StringFilter folder) {
        this.folder = folder;
    }

    public StringFilter getTipoarquivo() {
        return tipoarquivo;
    }

    public void setTipoarquivo(StringFilter tipoarquivo) {
        this.tipoarquivo = tipoarquivo;
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
        final MeteogramaCriteria that = (MeteogramaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(arquivo, that.arquivo) &&
            Objects.equals(folder, that.folder) &&
            Objects.equals(tipoarquivo, that.tipoarquivo) &&
            Objects.equals(created, that.created) &&
            Objects.equals(updated, that.updated) &&
            Objects.equals(planoId, that.planoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        descricao,
        arquivo,
        folder,
        tipoarquivo,
        created,
        updated,
        planoId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MeteogramaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (descricao != null ? "descricao=" + descricao + ", " : "") +
                (arquivo != null ? "arquivo=" + arquivo + ", " : "") +
                (folder != null ? "folder=" + folder + ", " : "") +
                (tipoarquivo != null ? "tipoarquivo=" + tipoarquivo + ", " : "") +
                (created != null ? "created=" + created + ", " : "") +
                (updated != null ? "updated=" + updated + ", " : "") +
                (planoId != null ? "planoId=" + planoId + ", " : "") +
            "}";
    }

}
