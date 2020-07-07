import { Moment } from 'moment';

export interface IPlanoRecurso {
  id?: number;
  name?: string;
  descricao?: string;
  ativo?: boolean;
  created?: Moment;
  updated?: Moment;
  planoId?: number;
  recursoId?: number;
}

export class PlanoRecurso implements IPlanoRecurso {
  constructor(
    public id?: number,
    public name?: string,
    public descricao?: string,
    public ativo?: boolean,
    public created?: Moment,
    public updated?: Moment,
    public planoId?: number,
    public recursoId?: number
  ) {
    this.ativo = this.ativo || false;
  }
}
