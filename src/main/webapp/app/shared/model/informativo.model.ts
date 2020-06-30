import { Moment } from 'moment';

export interface IInformativo {
  id?: number;
  nome?: string;
  descricao?: string;
  texto?: string;
  qtdEmail?: number;
  imagem?: string;
  arquivoEml?: string;
  assunto?: string;
  subAssunto?: string;
  created?: Moment;
  updated?: Moment;
  planoRecursoId?: number;
}

export class Informativo implements IInformativo {
  constructor(
    public id?: number,
    public nome?: string,
    public descricao?: string,
    public texto?: string,
    public qtdEmail?: number,
    public imagem?: string,
    public arquivoEml?: string,
    public assunto?: string,
    public subAssunto?: string,
    public created?: Moment,
    public updated?: Moment,
    public planoRecursoId?: number
  ) {}
}
