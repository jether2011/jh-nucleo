import { Moment } from 'moment';

export interface IRecurso {
  id?: number;
  name?: string;
  descricao?: string;
  ativo?: boolean;
  created?: Moment;
  updated?: Moment;
  recursoTipoId?: number;
  variavelMeteorologicaId?: number;
}

export class Recurso implements IRecurso {
  constructor(
    public id?: number,
    public name?: string,
    public descricao?: string,
    public ativo?: boolean,
    public created?: Moment,
    public updated?: Moment,
    public recursoTipoId?: number,
    public variavelMeteorologicaId?: number
  ) {
    this.ativo = this.ativo || false;
  }
}
