import { Moment } from 'moment';

export interface IPlanoRecursoTipoEnvio {
  id?: number;
  name?: string;
  descricao?: string;
  qtd?: number;
  created?: Moment;
  updated?: Moment;
  planoRecursoId?: number;
  tipoEnvioId?: number;
}

export class PlanoRecursoTipoEnvio implements IPlanoRecursoTipoEnvio {
  constructor(
    public id?: number,
    public name?: string,
    public descricao?: string,
    public qtd?: number,
    public created?: Moment,
    public updated?: Moment,
    public planoRecursoId?: number,
    public tipoEnvioId?: number
  ) {}
}
