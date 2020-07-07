import { Moment } from 'moment';

export interface IContatoTipoEnvio {
  id?: number;
  nome?: string;
  descricao?: string;
  created?: Moment;
  updated?: Moment;
  contatoId?: number;
  tipoEnvioId?: number;
}

export class ContatoTipoEnvio implements IContatoTipoEnvio {
  constructor(
    public id?: number,
    public nome?: string,
    public descricao?: string,
    public created?: Moment,
    public updated?: Moment,
    public contatoId?: number,
    public tipoEnvioId?: number
  ) {}
}
