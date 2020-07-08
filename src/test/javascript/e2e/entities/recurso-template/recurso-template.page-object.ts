import { element, by, ElementFinder } from 'protractor';

export class RecursoTemplateComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-recurso-template div table .btn-danger'));
  title = element.all(by.css('jhi-recurso-template div h2#page-heading span')).first();
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

export class RecursoTemplateUpdatePage {
  pageTitle = element(by.id('jhi-recurso-template-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  descricaoInput = element(by.id('field_descricao'));
  templateInput = element(by.id('field_template'));
  createdInput = element(by.id('field_created'));
  updatedInput = element(by.id('field_updated'));

  recursoSelect = element(by.id('field_recurso'));
  tipoEnvioSelect = element(by.id('field_tipoEnvio'));
  alertaTipoSelect = element(by.id('field_alertaTipo'));

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

  async setTemplateInput(template: string): Promise<void> {
    await this.templateInput.sendKeys(template);
  }

  async getTemplateInput(): Promise<string> {
    return await this.templateInput.getAttribute('value');
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

  async recursoSelectLastOption(): Promise<void> {
    await this.recursoSelect.all(by.tagName('option')).last().click();
  }

  async recursoSelectOption(option: string): Promise<void> {
    await this.recursoSelect.sendKeys(option);
  }

  getRecursoSelect(): ElementFinder {
    return this.recursoSelect;
  }

  async getRecursoSelectedOption(): Promise<string> {
    return await this.recursoSelect.element(by.css('option:checked')).getText();
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

export class RecursoTemplateDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-recursoTemplate-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-recursoTemplate'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
