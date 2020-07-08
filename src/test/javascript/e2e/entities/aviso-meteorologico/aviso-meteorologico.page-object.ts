import { element, by, ElementFinder } from 'protractor';

export class AvisoMeteorologicoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-aviso-meteorologico div table .btn-danger'));
  title = element.all(by.css('jhi-aviso-meteorologico div h2#page-heading span')).first();
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

export class AvisoMeteorologicoUpdatePage {
  pageTitle = element(by.id('jhi-aviso-meteorologico-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nomeInput = element(by.id('field_nome'));
  assuntoInput = element(by.id('field_assunto'));
  inicioInput = element(by.id('field_inicio'));
  fimInput = element(by.id('field_fim'));
  textoInput = element(by.id('field_texto'));
  imagemInput = element(by.id('field_imagem'));
  imagemAssinaturaInput = element(by.id('field_imagemAssinatura'));
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

  async setAssuntoInput(assunto: string): Promise<void> {
    await this.assuntoInput.sendKeys(assunto);
  }

  async getAssuntoInput(): Promise<string> {
    return await this.assuntoInput.getAttribute('value');
  }

  async setInicioInput(inicio: string): Promise<void> {
    await this.inicioInput.sendKeys(inicio);
  }

  async getInicioInput(): Promise<string> {
    return await this.inicioInput.getAttribute('value');
  }

  async setFimInput(fim: string): Promise<void> {
    await this.fimInput.sendKeys(fim);
  }

  async getFimInput(): Promise<string> {
    return await this.fimInput.getAttribute('value');
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

  async setImagemAssinaturaInput(imagemAssinatura: string): Promise<void> {
    await this.imagemAssinaturaInput.sendKeys(imagemAssinatura);
  }

  async getImagemAssinaturaInput(): Promise<string> {
    return await this.imagemAssinaturaInput.getAttribute('value');
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

export class AvisoMeteorologicoDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-avisoMeteorologico-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-avisoMeteorologico'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
