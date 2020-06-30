import { Moment } from 'moment';

export interface IAviso {
  id?: number;
  nome?: string;
  descricao?: string;
  enviado?: boolean;
  created?: Moment;
  updated?: Moment;
  avisoTipoId?: number;
}

export class Aviso implements IAviso {
  constructor(
    public id?: number,
    public nome?: string,
    public descricao?: string,
    public enviado?: boolean,
    public created?: Moment,
    public updated?: Moment,
    public avisoTipoId?: number
  ) {
    this.enviado = this.enviado || false;
  }
}
