import { Moment } from 'moment';

export interface IBoletimPrevisao {
  id?: number;
  nome?: string;
  descricao?: string;
  local?: string;
  imgCondicaoTempo?: number;
  condicaoTempo?: string;
  observacao?: string;
  grupoOrdem?: number;
  ondas?: string;
  temperaturaDe?: number;
  temperaturaAte?: number;
  ventovelocidademediakmh?: number;
  ventosObservacao?: string;
  ventoRajada?: boolean;
  tempestadeObservacao?: string;
  chuvaObservacao?: string;
  created?: Moment;
  updated?: Moment;
  boletimId?: number;
  boletimPrevObsId?: number;
  intensidadeChuvaId?: number;
  umidadeArId?: number;
  alvoId?: number;
  ventosOrigemPontosCardeaisId?: number;
  ventoRajadaVentoVmFaixaId?: number;
  tempestadeProbabilidadeId?: number;
  tempestadeNivelId?: number;
  acumuladoChuvaFaixaId?: number;
  condicaoTempoId?: number;
}

export class BoletimPrevisao implements IBoletimPrevisao {
  constructor(
    public id?: number,
    public nome?: string,
    public descricao?: string,
    public local?: string,
    public imgCondicaoTempo?: number,
    public condicaoTempo?: string,
    public observacao?: string,
    public grupoOrdem?: number,
    public ondas?: string,
    public temperaturaDe?: number,
    public temperaturaAte?: number,
    public ventovelocidademediakmh?: number,
    public ventosObservacao?: string,
    public ventoRajada?: boolean,
    public tempestadeObservacao?: string,
    public chuvaObservacao?: string,
    public created?: Moment,
    public updated?: Moment,
    public boletimId?: number,
    public boletimPrevObsId?: number,
    public intensidadeChuvaId?: number,
    public umidadeArId?: number,
    public alvoId?: number,
    public ventosOrigemPontosCardeaisId?: number,
    public ventoRajadaVentoVmFaixaId?: number,
    public tempestadeProbabilidadeId?: number,
    public tempestadeNivelId?: number,
    public acumuladoChuvaFaixaId?: number,
    public condicaoTempoId?: number
  ) {
    this.ventoRajada = this.ventoRajada || false;
  }
}
