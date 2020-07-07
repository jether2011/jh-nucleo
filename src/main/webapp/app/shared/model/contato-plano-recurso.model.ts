import { Moment } from 'moment';

export interface IContatoPlanoRecurso {
  id?: number;
  nome?: string;
  descricao?: string;
  created?: Moment;
  updated?: Moment;
  contatoId?: number;
  planoRecursoId?: number;
}

export class ContatoPlanoRecurso implements IContatoPlanoRecurso {
  constructor(
    public id?: number,
    public nome?: string,
    public descricao?: string,
    public created?: Moment,
    public updated?: Moment,
    public contatoId?: number,
    public planoRecursoId?: number
  ) {}
}
