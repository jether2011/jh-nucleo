import { element, by, ElementFinder } from 'protractor';

export class PrevisaoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-previsao div table .btn-danger'));
  title = element.all(by.css('jhi-previsao div h2#page-heading span')).first();
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

export class PrevisaoUpdatePage {
  pageTitle = element(by.id('jhi-previsao-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  descricaoInput = element(by.id('field_descricao'));
  textoNorteInput = element(by.id('field_textoNorte'));
  textoNorteImagemInput = element(by.id('field_textoNorteImagem'));
  textoNordesteInput = element(by.id('field_textoNordeste'));
  textoNordesteImagemInput = element(by.id('field_textoNordesteImagem'));
  textoSulInput = element(by.id('field_textoSul'));
  textoSulImagemInput = element(by.id('field_textoSulImagem'));
  textoSudesteInput = element(by.id('field_textoSudeste'));
  textoSudesteImagemInput = element(by.id('field_textoSudesteImagem'));
  textoCentroOesteInput = element(by.id('field_textoCentroOeste'));
  textoCentroOesteImagemInput = element(by.id('field_textoCentroOesteImagem'));
  enviadoInput = element(by.id('field_enviado'));
  textoBrasilInput = element(by.id('field_textoBrasil'));
  textoBrasilImagemInput = element(by.id('field_textoBrasilImagem'));
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

  async setTextoNorteInput(textoNorte: string): Promise<void> {
    await this.textoNorteInput.sendKeys(textoNorte);
  }

  async getTextoNorteInput(): Promise<string> {
    return await this.textoNorteInput.getAttribute('value');
  }

  async setTextoNorteImagemInput(textoNorteImagem: string): Promise<void> {
    await this.textoNorteImagemInput.sendKeys(textoNorteImagem);
  }

  async getTextoNorteImagemInput(): Promise<string> {
    return await this.textoNorteImagemInput.getAttribute('value');
  }

  async setTextoNordesteInput(textoNordeste: string): Promise<void> {
    await this.textoNordesteInput.sendKeys(textoNordeste);
  }

  async getTextoNordesteInput(): Promise<string> {
    return await this.textoNordesteInput.getAttribute('value');
  }

  async setTextoNordesteImagemInput(textoNordesteImagem: string): Promise<void> {
    await this.textoNordesteImagemInput.sendKeys(textoNordesteImagem);
  }

  async getTextoNordesteImagemInput(): Promise<string> {
    return await this.textoNordesteImagemInput.getAttribute('value');
  }

  async setTextoSulInput(textoSul: string): Promise<void> {
    await this.textoSulInput.sendKeys(textoSul);
  }

  async getTextoSulInput(): Promise<string> {
    return await this.textoSulInput.getAttribute('value');
  }

  async setTextoSulImagemInput(textoSulImagem: string): Promise<void> {
    await this.textoSulImagemInput.sendKeys(textoSulImagem);
  }

  async getTextoSulImagemInput(): Promise<string> {
    return await this.textoSulImagemInput.getAttribute('value');
  }

  async setTextoSudesteInput(textoSudeste: string): Promise<void> {
    await this.textoSudesteInput.sendKeys(textoSudeste);
  }

  async getTextoSudesteInput(): Promise<string> {
    return await this.textoSudesteInput.getAttribute('value');
  }

  async setTextoSudesteImagemInput(textoSudesteImagem: string): Promise<void> {
    await this.textoSudesteImagemInput.sendKeys(textoSudesteImagem);
  }

  async getTextoSudesteImagemInput(): Promise<string> {
    return await this.textoSudesteImagemInput.getAttribute('value');
  }

  async setTextoCentroOesteInput(textoCentroOeste: string): Promise<void> {
    await this.textoCentroOesteInput.sendKeys(textoCentroOeste);
  }

  async getTextoCentroOesteInput(): Promise<string> {
    return await this.textoCentroOesteInput.getAttribute('value');
  }

  async setTextoCentroOesteImagemInput(textoCentroOesteImagem: string): Promise<void> {
    await this.textoCentroOesteImagemInput.sendKeys(textoCentroOesteImagem);
  }

  async getTextoCentroOesteImagemInput(): Promise<string> {
    return await this.textoCentroOesteImagemInput.getAttribute('value');
  }

  getEnviadoInput(): ElementFinder {
    return this.enviadoInput;
  }

  async setTextoBrasilInput(textoBrasil: string): Promise<void> {
    await this.textoBrasilInput.sendKeys(textoBrasil);
  }

  async getTextoBrasilInput(): Promise<string> {
    return await this.textoBrasilInput.getAttribute('value');
  }

  async setTextoBrasilImagemInput(textoBrasilImagem: string): Promise<void> {
    await this.textoBrasilImagemInput.sendKeys(textoBrasilImagem);
  }

  async getTextoBrasilImagemInput(): Promise<string> {
    return await this.textoBrasilImagemInput.getAttribute('value');
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

export class PrevisaoDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-previsao-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-previsao'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
