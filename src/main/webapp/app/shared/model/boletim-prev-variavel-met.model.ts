import { Moment } from 'moment';

export interface IBoletimPrevVariavelMet {
  id?: number;
  nome?: string;
  descricao?: string;
  created?: Moment;
  updated?: Moment;
  boletimPrevisaoId?: number;
  variavelMeteorologicaId?: number;
}

export class BoletimPrevVariavelMet implements IBoletimPrevVariavelMet {
  constructor(
    public id?: number,
    public nome?: string,
    public descricao?: string,
    public created?: Moment,
    public updated?: Moment,
    public boletimPrevisaoId?: number,
    public variavelMeteorologicaId?: number
  ) {}
}
