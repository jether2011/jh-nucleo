import { Moment } from 'moment';

export interface IJornadaTrabalho {
  id?: number;
  nome?: string;
  descricao?: string;
  horainicio?: string;
  duracao?: string;
  created?: Moment;
  updated?: Moment;
  planoId?: number;
  diaSemanaId?: number;
}

export class JornadaTrabalho implements IJornadaTrabalho {
  constructor(
    public id?: number,
    public nome?: string,
    public descricao?: string,
    public horainicio?: string,
    public duracao?: string,
    public created?: Moment,
    public updated?: Moment,
    public planoId?: number,
    public diaSemanaId?: number
  ) {}
}
