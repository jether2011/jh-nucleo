import { Moment } from 'moment';

export interface IUsuario {
  id?: number;
  name?: string;
  descricao?: string;
  email?: string;
  senha?: string;
  cnpj?: string;
  cpf?: string;
  cep?: string;
  endereco?: string;
  numero?: number;
  bairro?: string;
  cidade?: string;
  estado?: string;
  telefone?: string;
  fax?: string;
  celular?: string;
  detalhe?: string;
  bloqueado?: boolean;
  complemento?: string;
  naoPodeExcluir?: boolean;
  ultimoAcesso?: Moment;
  senhaFirebase?: string;
  created?: Moment;
  updated?: Moment;
}

export class Usuario implements IUsuario {
  constructor(
    public id?: number,
    public name?: string,
    public descricao?: string,
    public email?: string,
    public senha?: string,
    public cnpj?: string,
    public cpf?: string,
    public cep?: string,
    public endereco?: string,
    public numero?: number,
    public bairro?: string,
    public cidade?: string,
    public estado?: string,
    public telefone?: string,
    public fax?: string,
    public celular?: string,
    public detalhe?: string,
    public bloqueado?: boolean,
    public complemento?: string,
    public naoPodeExcluir?: boolean,
    public ultimoAcesso?: Moment,
    public senhaFirebase?: string,
    public created?: Moment,
    public updated?: Moment
  ) {
    this.bloqueado = this.bloqueado || false;
    this.naoPodeExcluir = this.naoPodeExcluir || false;
  }
}
