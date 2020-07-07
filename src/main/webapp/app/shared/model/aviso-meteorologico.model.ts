import { Moment } from 'moment';

export interface IAvisoMeteorologico {
  id?: number;
  nome?: string;
  assunto?: string;
  inicio?: Moment;
  fim?: Moment;
  texto?: string;
  imagem?: string;
  imagemAssinatura?: string;
  created?: Moment;
  updated?: Moment;
  planoRecursoId?: number;
}

export class AvisoMeteorologico implements IAvisoMeteorologico {
  constructor(
    public id?: number,
    public nome?: string,
    public assunto?: string,
    public inicio?: Moment,
    public fim?: Moment,
    public texto?: string,
    public imagem?: string,
    public imagemAssinatura?: string,
    public created?: Moment,
    public updated?: Moment,
    public planoRecursoId?: number
  ) {}
}
