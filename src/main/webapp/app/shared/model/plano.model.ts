import { Moment } from 'moment';

export interface IPlano {
  id?: number;
  name?: string;
  descricao?: string;
  horarioPrevisto?: number;
  trackingAtivo?: number;
  plrAtivo?: number;
  codigoWidgetPrevisao?: number;
  kmlAlvo?: string;
  zoomMin?: number;
  dtInicioContrato?: Moment;
  dataFimContrato?: Moment;
  horarioMonitInicio?: string;
  horarioMonitFinal?: string;
  blocos?: string;
  extent?: string;
  created?: Moment;
  updated?: Moment;
  empresaId?: number;
  planoStatusId?: number;
}

export class Plano implements IPlano {
  constructor(
    public id?: number,
    public name?: string,
    public descricao?: string,
    public horarioPrevisto?: number,
    public trackingAtivo?: number,
    public plrAtivo?: number,
    public codigoWidgetPrevisao?: number,
    public kmlAlvo?: string,
    public zoomMin?: number,
    public dtInicioContrato?: Moment,
    public dataFimContrato?: Moment,
    public horarioMonitInicio?: string,
    public horarioMonitFinal?: string,
    public blocos?: string,
    public extent?: string,
    public created?: Moment,
    public updated?: Moment,
    public empresaId?: number,
    public planoStatusId?: number
  ) {}
}
