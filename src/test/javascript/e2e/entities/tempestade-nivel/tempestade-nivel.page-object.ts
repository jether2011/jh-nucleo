import { element, by, ElementFinder } from 'protractor';

export class TempestadeNivelComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-tempestade-nivel div table .btn-danger'));
  title = element.all(by.css('jhi-tempestade-nivel div h2#page-heading span')).first();
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

export class TempestadeNivelUpdatePage {
  pageTitle = element(by.id('jhi-tempestade-nivel-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  descricaoInput = element(by.id('field_descricao'));
  taxaDeRaiosInput = element(by.id('field_taxaDeRaios'));
  ventosVelocidadeInput = element(by.id('field_ventosVelocidade'));
  granizoInput = element(by.id('field_granizo'));
  potencialDeDanosInput = element(by.id('field_potencialDeDanos'));
  createdInput = element(by.id('field_created'));
  updatedInput = element(by.id('field_updated'));

  intensidadeChuvaSelect = element(by.id('field_intensidadeChuva'));

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

  async setTaxaDeRaiosInput(taxaDeRaios: string): Promise<void> {
    await this.taxaDeRaiosInput.sendKeys(taxaDeRaios);
  }

  async getTaxaDeRaiosInput(): Promise<string> {
    return await this.taxaDeRaiosInput.getAttribute('value');
  }

  async setVentosVelocidadeInput(ventosVelocidade: string): Promise<void> {
    await this.ventosVelocidadeInput.sendKeys(ventosVelocidade);
  }

  async getVentosVelocidadeInput(): Promise<string> {
    return await this.ventosVelocidadeInput.getAttribute('value');
  }

  async setGranizoInput(granizo: string): Promise<void> {
    await this.granizoInput.sendKeys(granizo);
  }

  async getGranizoInput(): Promise<string> {
    return await this.granizoInput.getAttribute('value');
  }

  async setPotencialDeDanosInput(potencialDeDanos: string): Promise<void> {
    await this.potencialDeDanosInput.sendKeys(potencialDeDanos);
  }

  async getPotencialDeDanosInput(): Promise<string> {
    return await this.potencialDeDanosInput.getAttribute('value');
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

  async intensidadeChuvaSelectLastOption(): Promise<void> {
    await this.intensidadeChuvaSelect.all(by.tagName('option')).last().click();
  }

  async intensidadeChuvaSelectOption(option: string): Promise<void> {
    await this.intensidadeChuvaSelect.sendKeys(option);
  }

  getIntensidadeChuvaSelect(): ElementFinder {
    return this.intensidadeChuvaSelect;
  }

  async getIntensidadeChuvaSelectedOption(): Promise<string> {
    return await this.intensidadeChuvaSelect.element(by.css('option:checked')).getText();
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

export class TempestadeNivelDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-tempestadeNivel-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-tempestadeNivel'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
