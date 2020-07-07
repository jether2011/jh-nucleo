package org.tempestade.nucleo.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import org.tempestade.nucleo.domain.enumeration.LayerType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link org.tempestade.nucleo.domain.Layer} entity. This class is used
 * in {@link org.tempestade.nucleo.web.rest.LayerResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /layers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LayerCriteria implements Serializable, Criteria {
    /**
     * Class for filtering LayerType
     */
    public static class LayerTypeFilter extends Filter<LayerType> {

        public LayerTypeFilter() {
        }

        public LayerTypeFilter(LayerTypeFilter filter) {
            super(filter);
        }

        @Override
        public LayerTypeFilter copy() {
            return new LayerTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private StringFilter mapHost;

    private LayerTypeFilter layerType;

    private StringFilter title;

    private StringFilter attribution;

    private StringFilter workspace;

    private IntegerFilter opacity;

    private BooleanFilter baselayer;

    private BooleanFilter tiled;

    private BooleanFilter gwcActived;

    private BooleanFilter active;

    private BooleanFilter enabled;

    private InstantFilter created;

    private InstantFilter updated;

    public LayerCriteria() {
    }

    public LayerCriteria(LayerCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.mapHost = other.mapHost == null ? null : other.mapHost.copy();
        this.layerType = other.layerType == null ? null : other.layerType.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.attribution = other.attribution == null ? null : other.attribution.copy();
        this.workspace = other.workspace == null ? null : other.workspace.copy();
        this.opacity = other.opacity == null ? null : other.opacity.copy();
        this.baselayer = other.baselayer == null ? null : other.baselayer.copy();
        this.tiled = other.tiled == null ? null : other.tiled.copy();
        this.gwcActived = other.gwcActived == null ? null : other.gwcActived.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.enabled = other.enabled == null ? null : other.enabled.copy();
        this.created = other.created == null ? null : other.created.copy();
        this.updated = other.updated == null ? null : other.updated.copy();
    }

    @Override
    public LayerCriteria copy() {
        return new LayerCriteria(this);
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

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getMapHost() {
        return mapHost;
    }

    public void setMapHost(StringFilter mapHost) {
        this.mapHost = mapHost;
    }

    public LayerTypeFilter getLayerType() {
        return layerType;
    }

    public void setLayerType(LayerTypeFilter layerType) {
        this.layerType = layerType;
    }

    public StringFilter getTitle() {
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public StringFilter getAttribution() {
        return attribution;
    }

    public void setAttribution(StringFilter attribution) {
        this.attribution = attribution;
    }

    public StringFilter getWorkspace() {
        return workspace;
    }

    public void setWorkspace(StringFilter workspace) {
        this.workspace = workspace;
    }

    public IntegerFilter getOpacity() {
        return opacity;
    }

    public void setOpacity(IntegerFilter opacity) {
        this.opacity = opacity;
    }

    public BooleanFilter getBaselayer() {
        return baselayer;
    }

    public void setBaselayer(BooleanFilter baselayer) {
        this.baselayer = baselayer;
    }

    public BooleanFilter getTiled() {
        return tiled;
    }

    public void setTiled(BooleanFilter tiled) {
        this.tiled = tiled;
    }

    public BooleanFilter getGwcActived() {
        return gwcActived;
    }

    public void setGwcActived(BooleanFilter gwcActived) {
        this.gwcActived = gwcActived;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }

    public BooleanFilter getEnabled() {
        return enabled;
    }

    public void setEnabled(BooleanFilter enabled) {
        this.enabled = enabled;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LayerCriteria that = (LayerCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(mapHost, that.mapHost) &&
            Objects.equals(layerType, that.layerType) &&
            Objects.equals(title, that.title) &&
            Objects.equals(attribution, that.attribution) &&
            Objects.equals(workspace, that.workspace) &&
            Objects.equals(opacity, that.opacity) &&
            Objects.equals(baselayer, that.baselayer) &&
            Objects.equals(tiled, that.tiled) &&
            Objects.equals(gwcActived, that.gwcActived) &&
            Objects.equals(active, that.active) &&
            Objects.equals(enabled, that.enabled) &&
            Objects.equals(created, that.created) &&
            Objects.equals(updated, that.updated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        description,
        mapHost,
        layerType,
        title,
        attribution,
        workspace,
        opacity,
        baselayer,
        tiled,
        gwcActived,
        active,
        enabled,
        created,
        updated
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LayerCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (mapHost != null ? "mapHost=" + mapHost + ", " : "") +
                (layerType != null ? "layerType=" + layerType + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (attribution != null ? "attribution=" + attribution + ", " : "") +
                (workspace != null ? "workspace=" + workspace + ", " : "") +
                (opacity != null ? "opacity=" + opacity + ", " : "") +
                (baselayer != null ? "baselayer=" + baselayer + ", " : "") +
                (tiled != null ? "tiled=" + tiled + ", " : "") +
                (gwcActived != null ? "gwcActived=" + gwcActived + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (enabled != null ? "enabled=" + enabled + ", " : "") +
                (created != null ? "created=" + created + ", " : "") +
                (updated != null ? "updated=" + updated + ", " : "") +
            "}";
    }

}
