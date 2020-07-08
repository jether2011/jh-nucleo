import { element, by, ElementFinder } from 'protractor';

export class AcumuladoChuvaFaixaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-acumulado-chuva-faixa div table .btn-danger'));
  title = element.all(by.css('jhi-acumulado-chuva-faixa div h2#page-heading span')).first();
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

export class AcumuladoChuvaFaixaUpdatePage {
  pageTitle = element(by.id('jhi-acumulado-chuva-faixa-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nomeInput = element(by.id('field_nome'));
  descricaoInput = element(by.id('field_descricao'));
  deMmInput = element(by.id('field_deMm'));
  ateMmInput = element(by.id('field_ateMm'));
  createdInput = element(by.id('field_created'));
  updatedInput = element(by.id('field_updated'));

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

  async setDeMmInput(deMm: string): Promise<void> {
    await this.deMmInput.sendKeys(deMm);
  }

  async getDeMmInput(): Promise<string> {
    return await this.deMmInput.getAttribute('value');
  }

  async setAteMmInput(ateMm: string): Promise<void> {
    await this.ateMmInput.sendKeys(ateMm);
  }

  async getAteMmInput(): Promise<string> {
    return await this.ateMmInput.getAttribute('value');
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

export class AcumuladoChuvaFaixaDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-acumuladoChuvaFaixa-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-acumuladoChuvaFaixa'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
