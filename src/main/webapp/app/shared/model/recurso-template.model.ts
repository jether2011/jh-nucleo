import { Moment } from 'moment';

export interface IRecursoTemplate {
  id?: number;
  name?: string;
  descricao?: string;
  template?: string;
  created?: Moment;
  updated?: Moment;
  recursoId?: number;
  tipoEnvioId?: number;
  alertaTipoId?: number;
}

export class RecursoTemplate implements IRecursoTemplate {
  constructor(
    public id?: number,
    public name?: string,
    public descricao?: string,
    public template?: string,
    public created?: Moment,
    public updated?: Moment,
    public recursoId?: number,
    public tipoEnvioId?: number,
    public alertaTipoId?: number
  ) {}
}
