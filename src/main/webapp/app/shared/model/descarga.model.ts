import { Moment } from 'moment';

export interface IDescarga {
  id?: number;
  nome?: string;
  descricao?: string;
  qtd?: number;
  dataPrimeiraDescarga?: Moment;
  tempoAntecipacao?: string;
  created?: Moment;
  updated?: Moment;
  redeId?: number;
  descargaTipoId?: number;
  descargaUnidadeId?: number;
  alertaId?: number;
}

export class Descarga implements IDescarga {
  constructor(
    public id?: number,
    public nome?: string,
    public descricao?: string,
    public qtd?: number,
    public dataPrimeiraDescarga?: Moment,
    public tempoAntecipacao?: string,
    public created?: Moment,
    public updated?: Moment,
    public redeId?: number,
    public descargaTipoId?: number,
    public descargaUnidadeId?: number,
    public alertaId?: number
  ) {}
}
