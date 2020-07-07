import { element, by, ElementFinder } from 'protractor';

export class BoletimPrevisaoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-boletim-previsao div table .btn-danger'));
  title = element.all(by.css('jhi-boletim-previsao div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class BoletimPrevisaoUpdatePage {
  pageTitle = element(by.id('jhi-boletim-previsao-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nomeInput = element(by.id('field_nome'));
  descricaoInput = element(by.id('field_descricao'));
  localInput = element(by.id('field_local'));
  imgCondicaoTempoInput = element(by.id('field_imgCondicaoTempo'));
  condicaoTempoInput = element(by.id('field_condicaoTempo'));
  observacaoInput = element(by.id('field_observacao'));
  grupoOrdemInput = element(by.id('field_grupoOrdem'));
  ondasInput = element(by.id('field_ondas'));
  temperaturaDeInput = element(by.id('field_temperaturaDe'));
  temperaturaAteInput = element(by.id('field_temperaturaAte'));
  ventovelocidademediakmhInput = element(by.id('field_ventovelocidademediakmh'));
  ventosObservacaoInput = element(by.id('field_ventosObservacao'));
  ventoRajadaInput = element(by.id('field_ventoRajada'));
  tempestadeObservacaoInput = element(by.id('field_tempestadeObservacao'));
  chuvaObservacaoInput = element(by.id('field_chuvaObservacao'));
  createdInput = element(by.id('field_created'));
  updatedInput = element(by.id('field_updated'));

  boletimSelect = element(by.id('field_boletim'));
  boletimPrevObsSelect = element(by.id('field_boletimPrevObs'));
  intensidadeChuvaSelect = element(by.id('field_intensidadeChuva'));
  umidadeArSelect = element(by.id('field_umidadeAr'));
  alvoSelect = element(by.id('field_alvo'));
  pontosCardeaisSelect = element(by.id('field_pontosCardeais'));
  ventoVmFaixaSelect = element(by.id('field_ventoVmFaixa'));
  tempestadeProbabilidadeSelect = element(by.id('field_tempestadeProbabilidade'));
  tempestadeNivelSelect = element(by.id('field_tempestadeNivel'));
  acumuladoChuvaFaixaSelect = element(by.id('field_acumuladoChuvaFaixa'));
  condicaoTempoSelect = element(by.id('field_condicaoTempo'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNomeInput(nome: string): Promise<void> {
    await this.nomeInput.sendKeys(nome);
  }

  async getNomeInput(): Promise<string> {
    return await this.nomeInput.getAttribute('value');
  }

  async setDescricaoInput(descricao: string): Promise<void> {
    await this.descricaoInput.sendKeys(descricao);
  }

  async getDescricaoInput(): Promise<string> {
    return await this.descricaoInput.getAttribute('value');
  }

  async setLocalInput(local: string): Promise<void> {
    await this.localInput.sendKeys(local);
  }

  async getLocalInput(): Promise<string> {
    return await this.localInput.getAttribute('value');
  }

  async setImgCondicaoTempoInput(imgCondicaoTempo: string): Promise<void> {
    await this.imgCondicaoTempoInput.sendKeys(imgCondicaoTempo);
  }

  async getImgCondicaoTempoInput(): Promise<string> {
    return await this.imgCondicaoTempoInput.getAttribute('value');
  }

  async setCondicaoTempoInput(condicaoTempo: string): Promise<void> {
    await this.condicaoTempoInput.sendKeys(condicaoTempo);
  }

  async getCondicaoTempoInput(): Promise<string> {
    return await this.condicaoTempoInput.getAttribute('value');
  }

  async setObservacaoInput(observacao: string): Promise<void> {
    await this.observacaoInput.sendKeys(observacao);
  }

  async getObservacaoInput(): Promise<string> {
    return await this.observacaoInput.getAttribute('value');
  }

  async setGrupoOrdemInput(grupoOrdem: string): Promise<void> {
    await this.grupoOrdemInput.sendKeys(grupoOrdem);
  }

  async getGrupoOrdemInput(): Promise<string> {
    return await this.grupoOrdemInput.getAttribute('value');
  }

  async setOndasInput(ondas: string): Promise<void> {
    await this.ondasInput.sendKeys(ondas);
  }

  async getOndasInput(): Promise<string> {
    return await this.ondasInput.getAttribute('value');
  }

  async setTemperaturaDeInput(temperaturaDe: string): Promise<void> {
    await this.temperaturaDeInput.sendKeys(temperaturaDe);
  }

  async getTemperaturaDeInput(): Promise<string> {
    return await this.temperaturaDeInput.getAttribute('value');
  }

  async setTemperaturaAteInput(temperaturaAte: string): Promise<void> {
    await this.temperaturaAteInput.sendKeys(temperaturaAte);
  }

  async getTemperaturaAteInput(): Promise<string> {
    return await this.temperaturaAteInput.getAttribute('value');
  }

  async setVentovelocidademediakmhInput(ventovelocidademediakmh: string): Promise<void> {
    await this.ventovelocidademediakmhInput.sendKeys(ventovelocidademediakmh);
  }

  async getVentovelocidademediakmhInput(): Promise<string> {
    return await this.ventovelocidademediakmhInput.getAttribute('value');
  }

  async setVentosObservacaoInput(ventosObservacao: string): Promise<void> {
    await this.ventosObservacaoInput.sendKeys(ventosObservacao);
  }

  async getVentosObservacaoInput(): Promise<string> {
    return await this.ventosObservacaoInput.getAttribute('value');
  }

  getVentoRajadaInput(): ElementFinder {
    return this.ventoRajadaInput;
  }

  async setTempestadeObservacaoInput(tempestadeObservacao: string): Promise<void> {
    await this.tempestadeObservacaoInput.sendKeys(tempestadeObservacao);
  }

  async getTempestadeObservacaoInput(): Promise<string> {
    return await this.tempestadeObservacaoInput.getAttribute('value');
  }

  async setChuvaObservacaoInput(chuvaObservacao: string): Promise<void> {
    await this.chuvaObservacaoInput.sendKeys(chuvaObservacao);
  }

  async getChuvaObservacaoInput(): Promise<string> {
    return await this.chuvaObservacaoInput.getAttribute('value');
  }

  async setCreatedInput(created: string): Promise<void> {
    await this.createdInput.sendKeys(created);
  }

  async getCreatedInput(): Promise<string> {
    return await this.createdInput.getAttribute('value');
  }

  async setUpdatedInput(updated: string): Promise<void> {
    await this.updatedInput.sendKeys(updated);
  }

  async getUpdatedInput(): Promise<string> {
    return await this.updatedInput.getAttribute('value');
  }

  async boletimSelectLastOption(): Promise<void> {
    await this.boletimSelect.all(by.tagName('option')).last().click();
  }

  async boletimSelectOption(option: string): Promise<void> {
    await this.boletimSelect.sendKeys(option);
  }

  getBoletimSelect(): ElementFinder {
    return this.boletimSelect;
  }

  async getBoletimSelectedOption(): Promise<string> {
    return await this.boletimSelect.element(by.css('option:checked')).getText();
  }

  async boletimPrevObsSelectLastOption(): Promise<void> {
    await this.boletimPrevObsSelect.all(by.tagName('option')).last().click();
  }

  async boletimPrevObsSelectOption(option: string): Promise<void> {
    await this.boletimPrevObsSelect.sendKeys(option);
  }

  getBoletimPrevObsSelect(): ElementFinder {
    return this.boletimPrevObsSelect;
  }

  async getBoletimPrevObsSelectedOption(): Promise<string> {
    return await this.boletimPrevObsSelect.element(by.css('option:checked')).getText();
  }

  async intensidadeChuvaSelectLastOption(): Promise<void> {
    await this.intensidadeChuvaSelect.all(by.tagName('option')).last().click();
  }

  async intensidadeChuvaSelectOption(option: string): Promise<void> {
    await this.intensidadeChuvaSelect.sendKeys(option);
  }

  getIntensidadeChuvaSelect(): ElementFinder {
    return this.intensidadeChuvaSelect;
  }

  async getIntensidadeChuvaSelectedOption(): Promise<string> {
    return await this.intensidadeChuvaSelect.element(by.css('option:checked')).getText();
  }

  async umidadeArSelectLastOption(): Promise<void> {
    await this.umidadeArSelect.all(by.tagName('option')).last().click();
  }

  async umidadeArSelectOption(option: string): Promise<void> {
    await this.umidadeArSelect.sendKeys(option);
  }

  getUmidadeArSelect(): ElementFinder {
    return this.umidadeArSelect;
  }

  async getUmidadeArSelectedOption(): Promise<string> {
    return await this.umidadeArSelect.element(by.css('option:checked')).getText();
  }

  async alvoSelectLastOption(): Promise<void> {
    await this.alvoSelect.all(by.tagName('option')).last().click();
  }

  async alvoSelectOption(option: string): Promise<void> {
    await this.alvoSelect.sendKeys(option);
  }

  getAlvoSelect(): ElementFinder {
    return this.alvoSelect;
  }

  async getAlvoSelectedOption(): Promise<string> {
    return await this.alvoSelect.element(by.css('option:checked')).getText();
  }

  async pontosCardeaisSelectLastOption(): Promise<void> {
    await this.pontosCardeaisSelect.all(by.tagName('option')).last().click();
  }

  async pontosCardeaisSelectOption(option: string): Promise<void> {
    await this.pontosCardeaisSelect.sendKeys(option);
  }

  getPontosCardeaisSelect(): ElementFinder {
    return this.pontosCardeaisSelect;
  }

  async getPontosCardeaisSelectedOption(): Promise<string> {
    return await this.pontosCardeaisSelect.element(by.css('option:checked')).getText();
  }

  async ventoVmFaixaSelectLastOption(): Promise<void> {
    await this.ventoVmFaixaSelect.all(by.tagName('option')).last().click();
  }

  async ventoVmFaixaSelectOption(option: string): Promise<void> {
    await this.ventoVmFaixaSelect.sendKeys(option);
  }

  getVentoVmFaixaSelect(): ElementFinder {
    return this.ventoVmFaixaSelect;
  }

  async getVentoVmFaixaSelectedOption(): Promise<string> {
    return await this.ventoVmFaixaSelect.element(by.css('option:checked')).getText();
  }

  async tempestadeProbabilidadeSelectLastOption(): Promise<void> {
    await this.tempestadeProbabilidadeSelect.all(by.tagName('option')).last().click();
  }

  async tempestadeProbabilidadeSelectOption(option: string): Promise<void> {
    await this.tempestadeProbabilidadeSelect.sendKeys(option);
  }

  getTempestadeProbabilidadeSelect(): ElementFinder {
    return this.tempestadeProbabilidadeSelect;
  }

  async getTempestadeProbabilidadeSelectedOption(): Promise<string> {
    return await this.tempestadeProbabilidadeSelect.element(by.css('option:checked')).getText();
  }

  async tempestadeNivelSelectLastOption(): Promise<void> {
    await this.tempestadeNivelSelect.all(by.tagName('option')).last().click();
  }

  async tempestadeNivelSelectOption(option: string): Promise<void> {
    await this.tempestadeNivelSelect.sendKeys(option);
  }

  getTempestadeNivelSelect(): ElementFinder {
    return this.tempestadeNivelSelect;
  }

  async getTempestadeNivelSelectedOption(): Promise<string> {
    return await this.tempestadeNivelSelect.element(by.css('option:checked')).getText();
  }

  async acumuladoChuvaFaixaSelectLastOption(): Promise<void> {
    await this.acumuladoChuvaFaixaSelect.all(by.tagName('option')).last().click();
  }

  async acumuladoChuvaFaixaSelectOption(option: string): Promise<void> {
    await this.acumuladoChuvaFaixaSelect.sendKeys(option);
  }

  getAcumuladoChuvaFaixaSelect(): ElementFinder {
    return this.acumuladoChuvaFaixaSelect;
  }

  async getAcumuladoChuvaFaixaSelectedOption(): Promise<string> {
    return await this.acumuladoChuvaFaixaSelect.element(by.css('option:checked')).getText();
  }

  async condicaoTempoSelectLastOption(): Promise<void> {
    await this.condicaoTempoSelect.all(by.tagName('option')).last().click();
  }

  async condicaoTempoSelectOption(option: string): Promise<void> {
    await this.condicaoTempoSelect.sendKeys(option);
  }

  getCondicaoTempoSelect(): ElementFinder {
    return this.condicaoTempoSelect;
  }

  async getCondicaoTempoSelectedOption(): Promise<string> {
    return await this.condicaoTempoSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class BoletimPrevisaoDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-boletimPrevisao-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-boletimPrevisao'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
