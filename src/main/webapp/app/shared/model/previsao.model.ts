import { Moment } from 'moment';

export interface IPrevisao {
  id?: number;
  name?: string;
  descricao?: string;
  textoNorte?: string;
  textoNorteImagem?: string;
  textoNordeste?: string;
  textoNordesteImagem?: string;
  textoSul?: string;
  textoSulImagem?: string;
  textoSudeste?: string;
  textoSudesteImagem?: string;
  textoCentroOeste?: string;
  textoCentroOesteImagem?: string;
  enviado?: boolean;
  textoBrasil?: string;
  textoBrasilImagem?: string;
  created?: Moment;
  updated?: Moment;
}

export class Previsao implements IPrevisao {
  constructor(
    public id?: number,
    public name?: string,
    public descricao?: string,
    public textoNorte?: string,
    public textoNorteImagem?: string,
    public textoNordeste?: string,
    public textoNordesteImagem?: string,
    public textoSul?: string,
    public textoSulImagem?: string,
    public textoSudeste?: string,
    public textoSudesteImagem?: string,
    public textoCentroOeste?: string,
    public textoCentroOesteImagem?: string,
    public enviado?: boolean,
    public textoBrasil?: string,
    public textoBrasilImagem?: string,
    public created?: Moment,
    public updated?: Moment
  ) {
    this.enviado = this.enviado || false;
  }
}
