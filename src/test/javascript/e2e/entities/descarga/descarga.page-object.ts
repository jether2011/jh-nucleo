import { element, by, ElementFinder } from 'protractor';

export class DescargaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-descarga div table .btn-danger'));
  title = element.all(by.css('jhi-descarga div h2#page-heading span')).first();
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

export class DescargaUpdatePage {
  pageTitle = element(by.id('jhi-descarga-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nomeInput = element(by.id('field_nome'));
  descricaoInput = element(by.id('field_descricao'));
  qtdInput = element(by.id('field_qtd'));
  dataPrimeiraDescargaInput = element(by.id('field_dataPrimeiraDescarga'));
  tempoAntecipacaoInput = element(by.id('field_tempoAntecipacao'));
  createdInput = element(by.id('field_created'));
  updatedInput = element(by.id('field_updated'));

  redeSelect = element(by.id('field_rede'));
  descargaTipoSelect = element(by.id('field_descargaTipo'));
  descargaUnidadeSelect = element(by.id('field_descargaUnidade'));
  alertaSelect = element(by.id('field_alerta'));

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

  async setQtdInput(qtd: string): Promise<void> {
    await this.qtdInput.sendKeys(qtd);
  }

  async getQtdInput(): Promise<string> {
    return await this.qtdInput.getAttribute('value');
  }

  async setDataPrimeiraDescargaInput(dataPrimeiraDescarga: string): Promise<void> {
    await this.dataPrimeiraDescargaInput.sendKeys(dataPrimeiraDescarga);
  }

  async getDataPrimeiraDescargaInput(): Promise<string> {
    return await this.dataPrimeiraDescargaInput.getAttribute('value');
  }

  async setTempoAntecipacaoInput(tempoAntecipacao: string): Promise<void> {
    await this.tempoAntecipacaoInput.sendKeys(tempoAntecipacao);
  }

  async getTempoAntecipacaoInput(): Promise<string> {
    return await this.tempoAntecipacaoInput.getAttribute('value');
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

  async redeSelectLastOption(): Promise<void> {
    await this.redeSelect.all(by.tagName('option')).last().click();
  }

  async redeSelectOption(option: string): Promise<void> {
    await this.redeSelect.sendKeys(option);
  }

  getRedeSelect(): ElementFinder {
    return this.redeSelect;
  }

  async getRedeSelectedOption(): Promise<string> {
    return await this.redeSelect.element(by.css('option:checked')).getText();
  }

  async descargaTipoSelectLastOption(): Promise<void> {
    await this.descargaTipoSelect.all(by.tagName('option')).last().click();
  }

  async descargaTipoSelectOption(option: string): Promise<void> {
    await this.descargaTipoSelect.sendKeys(option);
  }

  getDescargaTipoSelect(): ElementFinder {
    return this.descargaTipoSelect;
  }

  async getDescargaTipoSelectedOption(): Promise<string> {
    return await this.descargaTipoSelect.element(by.css('option:checked')).getText();
  }

  async descargaUnidadeSelectLastOption(): Promise<void> {
    await this.descargaUnidadeSelect.all(by.tagName('option')).last().click();
  }

  async descargaUnidadeSelectOption(option: string): Promise<void> {
    await this.descargaUnidadeSelect.sendKeys(option);
  }

  getDescargaUnidadeSelect(): ElementFinder {
    return this.descargaUnidadeSelect;
  }

  async getDescargaUnidadeSelectedOption(): Promise<string> {
    return await this.descargaUnidadeSelect.element(by.css('option:checked')).getText();
  }

  async alertaSelectLastOption(): Promise<void> {
    await this.alertaSelect.all(by.tagName('option')).last().click();
  }

  async alertaSelectOption(option: string): Promise<void> {
    await this.alertaSelect.sendKeys(option);
  }

  getAlertaSelect(): ElementFinder {
    return this.alertaSelect;
  }

  async getAlertaSelectedOption(): Promise<string> {
    return await this.alertaSelect.element(by.css('option:checked')).getText();
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

export class DescargaDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-descarga-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-descarga'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
