import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'acumulado-chuva-faixa',
        loadChildren: () => import('./acumulado-chuva-faixa/acumulado-chuva-faixa.module').then(m => m.NucleoAcumuladoChuvaFaixaModule),
      },
      {
        path: 'alerta',
        loadChildren: () => import('./alerta/alerta.module').then(m => m.NucleoAlertaModule),
      },
      {
        path: 'alerta-ferramenta',
        loadChildren: () => import('./alerta-ferramenta/alerta-ferramenta.module').then(m => m.NucleoAlertaFerramentaModule),
      },
      {
        path: 'alerta-risco',
        loadChildren: () => import('./alerta-risco/alerta-risco.module').then(m => m.NucleoAlertaRiscoModule),
      },
      {
        path: 'alerta-tipo',
        loadChildren: () => import('./alerta-tipo/alerta-tipo.module').then(m => m.NucleoAlertaTipoModule),
      },
      {
        path: 'alvo',
        loadChildren: () => import('./alvo/alvo.module').then(m => m.NucleoAlvoModule),
      },
      {
        path: 'alvo-bloqueio',
        loadChildren: () => import('./alvo-bloqueio/alvo-bloqueio.module').then(m => m.NucleoAlvoBloqueioModule),
      },
      {
        path: 'aviso',
        loadChildren: () => import('./aviso/aviso.module').then(m => m.NucleoAvisoModule),
      },
      {
        path: 'aviso-meteorologico',
        loadChildren: () => import('./aviso-meteorologico/aviso-meteorologico.module').then(m => m.NucleoAvisoMeteorologicoModule),
      },
      {
        path: 'aviso-tipo',
        loadChildren: () => import('./aviso-tipo/aviso-tipo.module').then(m => m.NucleoAvisoTipoModule),
      },
      {
        path: 'boletim',
        loadChildren: () => import('./boletim/boletim.module').then(m => m.NucleoBoletimModule),
      },
      {
        path: 'boletim-previsao',
        loadChildren: () => import('./boletim-previsao/boletim-previsao.module').then(m => m.NucleoBoletimPrevisaoModule),
      },
      {
        path: 'boletim-prev-obs',
        loadChildren: () => import('./boletim-prev-obs/boletim-prev-obs.module').then(m => m.NucleoBoletimPrevObsModule),
      },
      {
        path: 'boletim-prev-variavel-met',
        loadChildren: () =>
          import('./boletim-prev-variavel-met/boletim-prev-variavel-met.module').then(m => m.NucleoBoletimPrevVariavelMetModule),
      },
      {
        path: 'condicao-tempo',
        loadChildren: () => import('./condicao-tempo/condicao-tempo.module').then(m => m.NucleoCondicaoTempoModule),
      },
      {
        path: 'consolidacao',
        loadChildren: () => import('./consolidacao/consolidacao.module').then(m => m.NucleoConsolidacaoModule),
      },
      {
        path: 'contato',
        loadChildren: () => import('./contato/contato.module').then(m => m.NucleoContatoModule),
      },
      {
        path: 'contato-alvo',
        loadChildren: () => import('./contato-alvo/contato-alvo.module').then(m => m.NucleoContatoAlvoModule),
      },
      {
        path: 'contato-plano-recurso',
        loadChildren: () => import('./contato-plano-recurso/contato-plano-recurso.module').then(m => m.NucleoContatoPlanoRecursoModule),
      },
      {
        path: 'contato-tipo-envio',
        loadChildren: () => import('./contato-tipo-envio/contato-tipo-envio.module').then(m => m.NucleoContatoTipoEnvioModule),
      },
      {
        path: 'descarga',
        loadChildren: () => import('./descarga/descarga.module').then(m => m.NucleoDescargaModule),
      },
      {
        path: 'descarga-tipo',
        loadChildren: () => import('./descarga-tipo/descarga-tipo.module').then(m => m.NucleoDescargaTipoModule),
      },
      {
        path: 'descarga-unidade',
        loadChildren: () => import('./descarga-unidade/descarga-unidade.module').then(m => m.NucleoDescargaUnidadeModule),
      },
      {
        path: 'dia-semana',
        loadChildren: () => import('./dia-semana/dia-semana.module').then(m => m.NucleoDiaSemanaModule),
      },
      {
        path: 'empresa',
        loadChildren: () => import('./empresa/empresa.module').then(m => m.NucleoEmpresaModule),
      },
      {
        path: 'ferramenta',
        loadChildren: () => import('./ferramenta/ferramenta.module').then(m => m.NucleoFerramentaModule),
      },
      {
        path: 'informativo',
        loadChildren: () => import('./informativo/informativo.module').then(m => m.NucleoInformativoModule),
      },
      {
        path: 'integrador',
        loadChildren: () => import('./integrador/integrador.module').then(m => m.NucleoIntegradorModule),
      },
      {
        path: 'intensidade-chuva',
        loadChildren: () => import('./intensidade-chuva/intensidade-chuva.module').then(m => m.NucleoIntensidadeChuvaModule),
      },
      {
        path: 'jornada-trabalho',
        loadChildren: () => import('./jornada-trabalho/jornada-trabalho.module').then(m => m.NucleoJornadaTrabalhoModule),
      },
      {
        path: 'layer',
        loadChildren: () => import('./layer/layer.module').then(m => m.NucleoLayerModule),
      },
      {
        path: 'log',
        loadChildren: () => import('./log/log.module').then(m => m.NucleoLogModule),
      },
      {
        path: 'meteograma',
        loadChildren: () => import('./meteograma/meteograma.module').then(m => m.NucleoMeteogramaModule),
      },
      {
        path: 'noticia',
        loadChildren: () => import('./noticia/noticia.module').then(m => m.NucleoNoticiaModule),
      },
      {
        path: 'notificacao-enviada',
        loadChildren: () => import('./notificacao-enviada/notificacao-enviada.module').then(m => m.NucleoNotificacaoEnviadaModule),
      },
      {
        path: 'perfil',
        loadChildren: () => import('./perfil/perfil.module').then(m => m.NucleoPerfilModule),
      },
      {
        path: 'plano',
        loadChildren: () => import('./plano/plano.module').then(m => m.NucleoPlanoModule),
      },
      {
        path: 'plano-layer',
        loadChildren: () => import('./plano-layer/plano-layer.module').then(m => m.NucleoPlanoLayerModule),
      },
      {
        path: 'plano-recurso',
        loadChildren: () => import('./plano-recurso/plano-recurso.module').then(m => m.NucleoPlanoRecursoModule),
      },
      {
        path: 'plano-recurso-tipo-envio',
        loadChildren: () =>
          import('./plano-recurso-tipo-envio/plano-recurso-tipo-envio.module').then(m => m.NucleoPlanoRecursoTipoEnvioModule),
      },
      {
        path: 'plano-rede',
        loadChildren: () => import('./plano-rede/plano-rede.module').then(m => m.NucleoPlanoRedeModule),
      },
      {
        path: 'plano-status',
        loadChildren: () => import('./plano-status/plano-status.module').then(m => m.NucleoPlanoStatusModule),
      },
      {
        path: 'plano-usuario',
        loadChildren: () => import('./plano-usuario/plano-usuario.module').then(m => m.NucleoPlanoUsuarioModule),
      },
      {
        path: 'pontos-cardeais',
        loadChildren: () => import('./pontos-cardeais/pontos-cardeais.module').then(m => m.NucleoPontosCardeaisModule),
      },
      {
        path: 'previsao',
        loadChildren: () => import('./previsao/previsao.module').then(m => m.NucleoPrevisaoModule),
      },
      {
        path: 'recurso',
        loadChildren: () => import('./recurso/recurso.module').then(m => m.NucleoRecursoModule),
      },
      {
        path: 'recurso-template',
        loadChildren: () => import('./recurso-template/recurso-template.module').then(m => m.NucleoRecursoTemplateModule),
      },
      {
        path: 'recurso-tipo',
        loadChildren: () => import('./recurso-tipo/recurso-tipo.module').then(m => m.NucleoRecursoTipoModule),
      },
      {
        path: 'rede',
        loadChildren: () => import('./rede/rede.module').then(m => m.NucleoRedeModule),
      },
      {
        path: 'tempestade-nivel',
        loadChildren: () => import('./tempestade-nivel/tempestade-nivel.module').then(m => m.NucleoTempestadeNivelModule),
      },
      {
        path: 'tempestade-probabilidade',
        loadChildren: () =>
          import('./tempestade-probabilidade/tempestade-probabilidade.module').then(m => m.NucleoTempestadeProbabilidadeModule),
      },
      {
        path: 'tipo-envio',
        loadChildren: () => import('./tipo-envio/tipo-envio.module').then(m => m.NucleoTipoEnvioModule),
      },
      {
        path: 'tipo-envio-integrador',
        loadChildren: () => import('./tipo-envio-integrador/tipo-envio-integrador.module').then(m => m.NucleoTipoEnvioIntegradorModule),
      },
      {
        path: 'tipo-ferramenta',
        loadChildren: () => import('./tipo-ferramenta/tipo-ferramenta.module').then(m => m.NucleoTipoFerramentaModule),
      },
      {
        path: 'umidade-ar',
        loadChildren: () => import('./umidade-ar/umidade-ar.module').then(m => m.NucleoUmidadeArModule),
      },
      {
        path: 'usuario',
        loadChildren: () => import('./usuario/usuario.module').then(m => m.NucleoUsuarioModule),
      },
      {
        path: 'usuario-perfil',
        loadChildren: () => import('./usuario-perfil/usuario-perfil.module').then(m => m.NucleoUsuarioPerfilModule),
      },
      {
        path: 'variavel-meteorologica',
        loadChildren: () => import('./variavel-meteorologica/variavel-meteorologica.module').then(m => m.NucleoVariavelMeteorologicaModule),
      },
      {
        path: 'vento-vm-faixa',
        loadChildren: () => import('./vento-vm-faixa/vento-vm-faixa.module').then(m => m.NucleoVentoVmFaixaModule),
      },
      {
        path: 'voce-sabia',
        loadChildren: () => import('./voce-sabia/voce-sabia.module').then(m => m.NucleoVoceSabiaModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class NucleoEntityModule {}
