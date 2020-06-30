import { Moment } from 'moment';

export interface IAlerta {
  id?: number;
  nome?: string;
  contato?: string;
  duracao?: string;
  automatico?: boolean;
  critico?: boolean;
  observacao?: string;
  alertaPaiId?: number;
  created?: Moment;
  updated?: Moment;
  planoRecursoId?: number;
  alvoId?: number;
  operadorUsuarioId?: number;
  alertaRiscoId?: number;
  tempestadeNivelId?: number;
  alertaTipoId?: number;
}

export class Alerta implements IAlerta {
  constructor(
    public id?: number,
    public nome?: string,
    public contato?: string,
    public duracao?: string,
    public automatico?: boolean,
    public critico?: boolean,
    public observacao?: string,
    public alertaPaiId?: number,
    public created?: Moment,
    public updated?: Moment,
    public planoRecursoId?: number,
    public alvoId?: number,
    public operadorUsuarioId?: number,
    public alertaRiscoId?: number,
    public tempestadeNivelId?: number,
    public alertaTipoId?: number
  ) {
    this.automatico = this.automatico || false;
    this.critico = this.critico || false;
  }
}
