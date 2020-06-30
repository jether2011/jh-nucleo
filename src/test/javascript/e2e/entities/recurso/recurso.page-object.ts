import { element, by, ElementFinder } from 'protractor';

export class RecursoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-recurso div table .btn-danger'));
  title = element.all(by.css('jhi-recurso div h2#page-heading span')).first();
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

export class RecursoUpdatePage {
  pageTitle = element(by.id('jhi-recurso-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  descricaoInput = element(by.id('field_descricao'));
  ativoInput = element(by.id('field_ativo'));
  createdInput = element(by.id('field_created'));
  updatedInput = element(by.id('field_updated'));

  recursoTipoSelect = element(by.id('field_recursoTipo'));
  variavelMeteorologicaSelect = element(by.id('field_variavelMeteorologica'));

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

  getAtivoInput(): ElementFinder {
    return this.ativoInput;
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

  async recursoTipoSelectLastOption(): Promise<void> {
    await this.recursoTipoSelect.all(by.tagName('option')).last().click();
  }

  async recursoTipoSelectOption(option: string): Promise<void> {
    await this.recursoTipoSelect.sendKeys(option);
  }

  getRecursoTipoSelect(): ElementFinder {
    return this.recursoTipoSelect;
  }

  async getRecursoTipoSelectedOption(): Promise<string> {
    return await this.recursoTipoSelect.element(by.css('option:checked')).getText();
  }

  async variavelMeteorologicaSelectLastOption(): Promise<void> {
    await this.variavelMeteorologicaSelect.all(by.tagName('option')).last().click();
  }

  async variavelMeteorologicaSelectOption(option: string): Promise<void> {
    await this.variavelMeteorologicaSelect.sendKeys(option);
  }

  getVariavelMeteorologicaSelect(): ElementFinder {
    return this.variavelMeteorologicaSelect;
  }

  async getVariavelMeteorologicaSelectedOption(): Promise<string> {
    return await this.variavelMeteorologicaSelect.element(by.css('option:checked')).getText();
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

export class RecursoDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-recurso-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-recurso'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
