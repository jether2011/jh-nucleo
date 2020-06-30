import { Moment } from 'moment';

export interface IEmpresa {
  id?: number;
  nome?: string;
  descricao?: string;
  email?: string;
  titulo?: string;
  nomeReduzido?: string;
  logo?: string;
  apelido?: string;
  observacao?: string;
  created?: Moment;
  updated?: Moment;
}

export class Empresa implements IEmpresa {
  constructor(
    public id?: number,
    public nome?: string,
    public descricao?: string,
    public email?: string,
    public titulo?: string,
    public nomeReduzido?: string,
    public logo?: string,
    public apelido?: string,
    public observacao?: string,
    public created?: Moment,
    public updated?: Moment
  ) {}
}
