import { Moment } from 'moment';

export interface IAlertaFerramenta {
  id?: number;
  nome?: string;
  descricao?: string;
  created?: Moment;
  updated?: Moment;
  alertaId?: number;
  ferramentaId?: number;
}

export class AlertaFerramenta implements IAlertaFerramenta {
  constructor(
    public id?: number,
    public nome?: string,
    public descricao?: string,
    public created?: Moment,
    public updated?: Moment,
    public alertaId?: number,
    public ferramentaId?: number
  ) {}
}
