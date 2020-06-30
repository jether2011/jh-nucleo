import { Moment } from 'moment';

export interface IPlanoUsuario {
  id?: number;
  name?: string;
  descricao?: string;
  created?: Moment;
  updated?: Moment;
  idId?: number;
  usuarioId?: number;
}

export class PlanoUsuario implements IPlanoUsuario {
  constructor(
    public id?: number,
    public name?: string,
    public descricao?: string,
    public created?: Moment,
    public updated?: Moment,
    public idId?: number,
    public usuarioId?: number
  ) {}
}
