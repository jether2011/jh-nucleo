import { Moment } from 'moment';

export interface IVoceSabia {
  id?: number;
  name?: string;
  descricao?: string;
  titulo?: string;
  texto?: string;
  imagem?: string;
  created?: Moment;
  updated?: Moment;
}

export class VoceSabia implements IVoceSabia {
  constructor(
    public id?: number,
    public name?: string,
    public descricao?: string,
    public titulo?: string,
    public texto?: string,
    public imagem?: string,
    public created?: Moment,
    public updated?: Moment
  ) {}
}
