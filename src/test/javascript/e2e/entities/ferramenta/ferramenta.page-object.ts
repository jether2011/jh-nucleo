import { element, by, ElementFinder } from 'protractor';

export class FerramentaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-ferramenta div table .btn-danger'));
  title = element.all(by.css('jhi-ferramenta div h2#page-heading span')).first();
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

export class FerramentaUpdatePage {
  pageTitle = element(by.id('jhi-ferramenta-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nomeInput = element(by.id('field_nome'));
  descricaoInput = element(by.id('field_descricao'));
  createdInput = element(by.id('field_created'));
  updatedInput = element(by.id('field_updated'));

  tipoFerramentaSelect = element(by.id('field_tipoFerramenta'));

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

  async tipoFerramentaSelectLastOption(): Promise<void> {
    await this.tipoFerramentaSelect.all(by.tagName('option')).last().click();
  }

  async tipoFerramentaSelectOption(option: string): Promise<void> {
    await this.tipoFerramentaSelect.sendKeys(option);
  }

  getTipoFerramentaSelect(): ElementFinder {
    return this.tipoFerramentaSelect;
  }

  async getTipoFerramentaSelectedOption(): Promise<string> {
    return await this.tipoFerramentaSelect.element(by.css('option:checked')).getText();
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

export class FerramentaDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-ferramenta-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-ferramenta'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
