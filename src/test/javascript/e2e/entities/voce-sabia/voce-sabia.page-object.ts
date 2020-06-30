import { element, by, ElementFinder } from 'protractor';

export class VoceSabiaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-voce-sabia div table .btn-danger'));
  title = element.all(by.css('jhi-voce-sabia div h2#page-heading span')).first();
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

export class VoceSabiaUpdatePage {
  pageTitle = element(by.id('jhi-voce-sabia-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  descricaoInput = element(by.id('field_descricao'));
  tituloInput = element(by.id('field_titulo'));
  textoInput = element(by.id('field_texto'));
  imagemInput = element(by.id('field_imagem'));
  createdInput = element(by.id('field_created'));
  updatedInput = element(by.id('field_updated'));

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

  async setTituloInput(titulo: string): Promise<void> {
    await this.tituloInput.sendKeys(titulo);
  }

  async getTituloInput(): Promise<string> {
    return await this.tituloInput.getAttribute('value');
  }

  async setTextoInput(texto: string): Promise<void> {
    await this.textoInput.sendKeys(texto);
  }

  async getTextoInput(): Promise<string> {
    return await this.textoInput.getAttribute('value');
  }

  async setImagemInput(imagem: string): Promise<void> {
    await this.imagemInput.sendKeys(imagem);
  }

  async getImagemInput(): Promise<string> {
    return await this.imagemInput.getAttribute('value');
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

export class VoceSabiaDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-voceSabia-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-voceSabia'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
