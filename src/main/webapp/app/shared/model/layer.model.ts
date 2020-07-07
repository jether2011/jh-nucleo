import { Moment } from 'moment';
import { LayerType } from 'app/shared/model/enumerations/layer-type.model';

export interface ILayer {
  id?: number;
  name?: string;
  description?: string;
  mapHost?: string;
  layerType?: LayerType;
  title?: string;
  attribution?: string;
  workspace?: string;
  opacity?: number;
  baselayer?: boolean;
  tiled?: boolean;
  gwcActived?: boolean;
  active?: boolean;
  enabled?: boolean;
  created?: Moment;
  updated?: Moment;
}

export class Layer implements ILayer {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public mapHost?: string,
    public layerType?: LayerType,
    public title?: string,
    public attribution?: string,
    public workspace?: string,
    public opacity?: number,
    public baselayer?: boolean,
    public tiled?: boolean,
    public gwcActived?: boolean,
    public active?: boolean,
    public enabled?: boolean,
    public created?: Moment,
    public updated?: Moment
  ) {
    this.baselayer = this.baselayer || false;
    this.tiled = this.tiled || false;
    this.gwcActived = this.gwcActived || false;
    this.active = this.active || false;
    this.enabled = this.enabled || false;
  }
}
