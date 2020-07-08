import { Moment } from 'moment';

export interface IBoletim {
  id?: number;
  nome?: string;
  descricao?: string;
  texto?: string;
  textoSms?: string;
  imagem?: string;
  assunto?: string;
  textoParte2?: string;
  textoParte3?: string;
  subAssunto?: string;
  naoExibirPagEmpresa?: number;
  critico?: boolean;
  aprovado?: boolean;
  enviarSms?: boolean;
  enviarEmail?: boolean;
  created?: Moment;
  updated?: Moment;
  planoRecursoId?: number;
}

export class Boletim implements IBoletim {
  constructor(
    public id?: number,
    public nome?: string,
    public descricao?: string,
    public texto?: string,
    public textoSms?: string,
    public imagem?: string,
    public assunto?: string,
    public textoParte2?: string,
    public textoParte3?: string,
    public subAssunto?: string,
    public naoExibirPagEmpresa?: number,
    public critico?: boolean,
    public aprovado?: boolean,
    public enviarSms?: boolean,
    public enviarEmail?: boolean,
    public created?: Moment,
    public updated?: Moment,
    public planoRecursoId?: number
  ) {
    this.critico = this.critico || false;
    this.aprovado = this.aprovado || false;
    this.enviarSms = this.enviarSms || false;
    this.enviarEmail = this.enviarEmail || false;
  }
}
