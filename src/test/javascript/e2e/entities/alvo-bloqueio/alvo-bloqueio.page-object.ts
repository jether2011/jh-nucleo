import { element, by, ElementFinder } from 'protractor';

export class AlvoBloqueioComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-alvo-bloqueio div table .btn-danger'));
  title = element.all(by.css('jhi-alvo-bloqueio div h2#page-heading span')).first();
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

export class AlvoBloqueioUpdatePage {
  pageTitle = element(by.id('jhi-alvo-bloqueio-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nomeInput = element(by.id('field_nome'));
  descricaoInput = element(by.id('field_descricao'));
  createdInput = element(by.id('field_created'));
  updatedInput = element(by.id('field_updated'));

  alvoSelect = element(by.id('field_alvo'));

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

export class AlvoBloqueioDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-alvoBloqueio-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-alvoBloqueio'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
