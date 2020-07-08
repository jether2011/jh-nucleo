import { element, by, ElementFinder } from 'protractor';

export class PlanoRecursoTipoEnvioComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-plano-recurso-tipo-envio div table .btn-danger'));
  title = element.all(by.css('jhi-plano-recurso-tipo-envio div h2#page-heading span')).first();
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

export class PlanoRecursoTipoEnvioUpdatePage {
  pageTitle = element(by.id('jhi-plano-recurso-tipo-envio-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  descricaoInput = element(by.id('field_descricao'));
  qtdInput = element(by.id('field_qtd'));
  createdInput = element(by.id('field_created'));
  updatedInput = element(by.id('field_updated'));

  planoRecursoSelect = element(by.id('field_planoRecurso'));
  tipoEnvioSelect = element(by.id('field_tipoEnvio'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
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

export class PlanoRecursoTipoEnvioDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-planoRecursoTipoEnvio-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-planoRecursoTipoEnvio'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
