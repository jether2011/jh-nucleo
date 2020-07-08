import { Moment } from 'moment';

export interface IUsuarioPerfil {
  id?: number;
  name?: string;
  descricao?: string;
  created?: Moment;
  updated?: Moment;
  usuarioId?: number;
  perfilId?: number;
}

export class UsuarioPerfil implements IUsuarioPerfil {
  constructor(
    public id?: number,
    public name?: string,
    public descricao?: string,
    public created?: Moment,
    public updated?: Moment,
    public usuarioId?: number,
    public perfilId?: number
  ) {}
}
