import { element, by, ElementFinder } from 'protractor';

export class InformativoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-informativo div table .btn-danger'));
  title = element.all(by.css('jhi-informativo div h2#page-heading span')).first();
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

export class InformativoUpdatePage {
  pageTitle = element(by.id('jhi-informativo-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nomeInput = element(by.id('field_nome'));
  descricaoInput = element(by.id('field_descricao'));
  textoInput = element(by.id('field_texto'));
  qtdEmailInput = element(by.id('field_qtdEmail'));
  imagemInput = element(by.id('field_imagem'));
  arquivoEmlInput = element(by.id('field_arquivoEml'));
  assuntoInput = element(by.id('field_assunto'));
  subAssuntoInput = element(by.id('field_subAssunto'));
  createdInput = element(by.id('field_created'));
  updatedInput = element(by.id('field_updated'));

  planoRecursoSelect = element(by.id('field_planoRecurso'));

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

  async setTextoInput(texto: string): Promise<void> {
    await this.textoInput.sendKeys(texto);
  }

  async getTextoInput(): Promise<string> {
    return await this.textoInput.getAttribute('value');
  }

  async setQtdEmailInput(qtdEmail: string): Promise<void> {
    await this.qtdEmailInput.sendKeys(qtdEmail);
  }

  async getQtdEmailInput(): Promise<string> {
    return await this.qtdEmailInput.getAttribute('value');
  }

  async setImagemInput(imagem: string): Promise<void> {
    await this.imagemInput.sendKeys(imagem);
  }

  async getImagemInput(): Promise<string> {
    return await this.imagemInput.getAttribute('value');
  }

  async setArquivoEmlInput(arquivoEml: string): Promise<void> {
    await this.arquivoEmlInput.sendKeys(arquivoEml);
  }

  async getArquivoEmlInput(): Promise<string> {
    return await this.arquivoEmlInput.getAttribute('value');
  }

  async setAssuntoInput(assunto: string): Promise<void> {
    await this.assuntoInput.sendKeys(assunto);
  }

  async getAssuntoInput(): Promise<string> {
    return await this.assuntoInput.getAttribute('value');
  }

  async setSubAssuntoInput(subAssunto: string): Promise<void> {
    await this.subAssuntoInput.sendKeys(subAssunto);
  }

  async getSubAssuntoInput(): Promise<string> {
    return await this.subAssuntoInput.getAttribute('value');
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

  async planoRecursoSelectLastOption(): Promise<void> {
    await this.planoRecursoSelect.all(by.tagName('option')).last().click();
  }

  async planoRecursoSelectOption(option: string): Promise<void> {
    await this.planoRecursoSelect.sendKeys(option);
  }

  getPlanoRecursoSelect(): ElementFinder {
    return this.planoRecursoSelect;
  }

  async getPlanoRecursoSelectedOption(): Promise<string> {
    return await this.planoRecursoSelect.element(by.css('option:checked')).getText();
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

export class InformativoDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-informativo-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-informativo'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
