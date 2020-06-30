import { element, by, ElementFinder } from 'protractor';

export class AlertaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-alerta div table .btn-danger'));
  title = element.all(by.css('jhi-alerta div h2#page-heading span')).first();
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

export class AlertaUpdatePage {
  pageTitle = element(by.id('jhi-alerta-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nomeInput = element(by.id('field_nome'));
  contatoInput = element(by.id('field_contato'));
  duracaoInput = element(by.id('field_duracao'));
  automaticoInput = element(by.id('field_automatico'));
  criticoInput = element(by.id('field_critico'));
  observacaoInput = element(by.id('field_observacao'));
  alertaPaiIdInput = element(by.id('field_alertaPaiId'));
  createdInput = element(by.id('field_created'));
  updatedInput = element(by.id('field_updated'));

  planoRecursoSelect = element(by.id('field_planoRecurso'));
  alvoSelect = element(by.id('field_alvo'));
  operadorUsuarioSelect = element(by.id('field_operadorUsuario'));
  alertaRiscoSelect = element(by.id('field_alertaRisco'));
  tempestadeNivelSelect = element(by.id('field_tempestadeNivel'));
  alertaTipoSelect = element(by.id('field_alertaTipo'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNomeInput(nome: string): Promise<void> {
    await this.nomeInput.sendKeys(nome);
  }

  async getNomeInput(): Promise<string> {
    return await this.nomeInput.getAttribute('value');
  }

  async setContatoInput(contato: string): Promise<void> {
    await this.contatoInput.sendKeys(contato);
  }

  async getContatoInput(): Promise<string> {
    return await this.contatoInput.getAttribute('value');
  }

  async setDuracaoInput(duracao: string): Promise<void> {
    await this.duracaoInput.sendKeys(duracao);
  }

  async getDuracaoInput(): Promise<string> {
    return await this.duracaoInput.getAttribute('value');
  }

  getAutomaticoInput(): ElementFinder {
    return this.automaticoInput;
  }

  getCriticoInput(): ElementFinder {
    return this.criticoInput;
  }

  async setObservacaoInput(observacao: string): Promise<void> {
    await this.observacaoInput.sendKeys(observacao);
  }

  async getObservacaoInput(): Promise<string> {
    return await this.observacaoInput.getAttribute('value');
  }

  async setAlertaPaiIdInput(alertaPaiId: string): Promise<void> {
    await this.alertaPaiIdInput.sendKeys(alertaPaiId);
  }

  async getAlertaPaiIdInput(): Promise<string> {
    return await this.alertaPaiIdInput.getAttribute('value');
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

  async planoRecursoSelectLastOption(): Promise<void> {
    await this.planoRecursoSelect.all(by.tagName('option')).last().click();
  }

  async planoRecursoSelectOption(option: string): Promise<void> {
    await this.planoRecursoSelect.sendKeys(option);
  }

  getPlanoRecursoSelect(): ElementFinder {
    return this.planoRecursoSelect;
  }

  async getPlanoRecursoSelectedOption(): Promise<string> {
    return await this.planoRecursoSelect.element(by.css('option:checked')).getText();
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

  async operadorUsuarioSelectLastOption(): Promise<void> {
    await this.operadorUsuarioSelect.all(by.tagName('option')).last().click();
  }

  async operadorUsuarioSelectOption(option: string): Promise<void> {
    await this.operadorUsuarioSelect.sendKeys(option);
  }

  getOperadorUsuarioSelect(): ElementFinder {
    return this.operadorUsuarioSelect;
  }

  async getOperadorUsuarioSelectedOption(): Promise<string> {
    return await this.operadorUsuarioSelect.element(by.css('option:checked')).getText();
  }

  async alertaRiscoSelectLastOption(): Promise<void> {
    await this.alertaRiscoSelect.all(by.tagName('option')).last().click();
  }

  async alertaRiscoSelectOption(option: string): Promise<void> {
    await this.alertaRiscoSelect.sendKeys(option);
  }

  getAlertaRiscoSelect(): ElementFinder {
    return this.alertaRiscoSelect;
  }

  async getAlertaRiscoSelectedOption(): Promise<string> {
    return await this.alertaRiscoSelect.element(by.css('option:checked')).getText();
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

  async alertaTipoSelectLastOption(): Promise<void> {
    await this.alertaTipoSelect.all(by.tagName('option')).last().click();
  }

  async alertaTipoSelectOption(option: string): Promise<void> {
    await this.alertaTipoSelect.sendKeys(option);
  }

  getAlertaTipoSelect(): ElementFinder {
    return this.alertaTipoSelect;
  }

  async getAlertaTipoSelectedOption(): Promise<string> {
    return await this.alertaTipoSelect.element(by.css('option:checked')).getText();
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

export class AlertaDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-alerta-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-alerta'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
