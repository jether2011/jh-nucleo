import { element, by, ElementFinder } from 'protractor';

export class ContatoTipoEnvioComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-contato-tipo-envio div table .btn-danger'));
  title = element.all(by.css('jhi-contato-tipo-envio div h2#page-heading span')).first();
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

export class ContatoTipoEnvioUpdatePage {
  pageTitle = element(by.id('jhi-contato-tipo-envio-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nomeInput = element(by.id('field_nome'));
  descricaoInput = element(by.id('field_descricao'));
  createdInput = element(by.id('field_created'));
  updatedInput = element(by.id('field_updated'));

  contatoSelect = element(by.id('field_contato'));
  tipoEnvioSelect = element(by.id('field_tipoEnvio'));

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

  async contatoSelectLastOption(): Promise<void> {
    await this.contatoSelect.all(by.tagName('option')).last().click();
  }

  async contatoSelectOption(option: string): Promise<void> {
    await this.contatoSelect.sendKeys(option);
  }

  getContatoSelect(): ElementFinder {
    return this.contatoSelect;
  }

  async getContatoSelectedOption(): Promise<string> {
    return await this.contatoSelect.element(by.css('option:checked')).getText();
  }

  async tipoEnvioSelectLastOption(): Promise<void> {
    await this.tipoEnvioSelect.all(by.tagName('option')).last().click();
  }

  async tipoEnvioSelectOption(option: string): Promise<void> {
    await this.tipoEnvioSelect.sendKeys(option);
  }

  getTipoEnvioSelect(): ElementFinder {
    return this.tipoEnvioSelect;
  }

  async getTipoEnvioSelectedOption(): Promise<string> {
    return await this.tipoEnvioSelect.element(by.css('option:checked')).getText();
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

export class ContatoTipoEnvioDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-contatoTipoEnvio-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-contatoTipoEnvio'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
