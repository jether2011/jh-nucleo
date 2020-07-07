import { Moment } from 'moment';

export interface ITipoEnvioIntegrador {
  id?: number;
  name?: string;
  descricao?: string;
  ativo?: boolean;
  created?: Moment;
  updated?: Moment;
  tipoEnvioId?: number;
  integradorId?: number;
}

export class TipoEnvioIntegrador implements ITipoEnvioIntegrador {
  constructor(
    public id?: number,
    public name?: string,
    public descricao?: string,
    public ativo?: boolean,
    public created?: Moment,
    public updated?: Moment,
    public tipoEnvioId?: number,
    public integradorId?: number
  ) {
    this.ativo = this.ativo || false;
  }
}
