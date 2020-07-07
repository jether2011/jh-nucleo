import { element, by, ElementFinder } from 'protractor';

export class PlanoUsuarioComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-plano-usuario div table .btn-danger'));
  title = element.all(by.css('jhi-plano-usuario div h2#page-heading span')).first();
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

export class PlanoUsuarioUpdatePage {
  pageTitle = element(by.id('jhi-plano-usuario-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  descricaoInput = element(by.id('field_descricao'));
  createdInput = element(by.id('field_created'));
  updatedInput = element(by.id('field_updated'));

  planoSelect = element(by.id('field_plano'));
  usuarioSelect = element(by.id('field_usuario'));

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

  async planoSelectLastOption(): Promise<void> {
    await this.planoSelect.all(by.tagName('option')).last().click();
  }

  async planoSelectOption(option: string): Promise<void> {
    await this.planoSelect.sendKeys(option);
  }

  getPlanoSelect(): ElementFinder {
    return this.planoSelect;
  }

  async getPlanoSelectedOption(): Promise<string> {
    return await this.planoSelect.element(by.css('option:checked')).getText();
  }

  async usuarioSelectLastOption(): Promise<void> {
    await this.usuarioSelect.all(by.tagName('option')).last().click();
  }

  async usuarioSelectOption(option: string): Promise<void> {
    await this.usuarioSelect.sendKeys(option);
  }

  getUsuarioSelect(): ElementFinder {
    return this.usuarioSelect;
  }

  async getUsuarioSelectedOption(): Promise<string> {
    return await this.usuarioSelect.element(by.css('option:checked')).getText();
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

export class PlanoUsuarioDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-planoUsuario-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-planoUsuario'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
