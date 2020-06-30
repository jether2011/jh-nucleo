import { element, by, ElementFinder } from 'protractor';

export class AvisoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-aviso div table .btn-danger'));
  title = element.all(by.css('jhi-aviso div h2#page-heading span')).first();
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

export class AvisoUpdatePage {
  pageTitle = element(by.id('jhi-aviso-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nomeInput = element(by.id('field_nome'));
  descricaoInput = element(by.id('field_descricao'));
  enviadoInput = element(by.id('field_enviado'));
  createdInput = element(by.id('field_created'));
  updatedInput = element(by.id('field_updated'));

  avisoTipoSelect = element(by.id('field_avisoTipo'));

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

  getEnviadoInput(): ElementFinder {
    return this.enviadoInput;
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

  async avisoTipoSelectLastOption(): Promise<void> {
    await this.avisoTipoSelect.all(by.tagName('option')).last().click();
  }

  async avisoTipoSelectOption(option: string): Promise<void> {
    await this.avisoTipoSelect.sendKeys(option);
  }

  getAvisoTipoSelect(): ElementFinder {
    return this.avisoTipoSelect;
  }

  async getAvisoTipoSelectedOption(): Promise<string> {
    return await this.avisoTipoSelect.element(by.css('option:checked')).getText();
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

export class AvisoDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-aviso-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-aviso'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
