import { element, by, ElementFinder } from 'protractor';

export class BoletimComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-boletim div table .btn-danger'));
  title = element.all(by.css('jhi-boletim div h2#page-heading span')).first();
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

export class BoletimUpdatePage {
  pageTitle = element(by.id('jhi-boletim-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nomeInput = element(by.id('field_nome'));
  descricaoInput = element(by.id('field_descricao'));
  textoInput = element(by.id('field_texto'));
  textoSmsInput = element(by.id('field_textoSms'));
  imagemInput = element(by.id('field_imagem'));
  assuntoInput = element(by.id('field_assunto'));
  textoParte2Input = element(by.id('field_textoParte2'));
  textoParte3Input = element(by.id('field_textoParte3'));
  subAssuntoInput = element(by.id('field_subAssunto'));
  naoExibirPagEmpresaInput = element(by.id('field_naoExibirPagEmpresa'));
  criticoInput = element(by.id('field_critico'));
  aprovadoInput = element(by.id('field_aprovado'));
  enviarSmsInput = element(by.id('field_enviarSms'));
  enviarEmailInput = element(by.id('field_enviarEmail'));
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

  async setTextoSmsInput(textoSms: string): Promise<void> {
    await this.textoSmsInput.sendKeys(textoSms);
  }

  async getTextoSmsInput(): Promise<string> {
    return await this.textoSmsInput.getAttribute('value');
  }

  async setImagemInput(imagem: string): Promise<void> {
    await this.imagemInput.sendKeys(imagem);
  }

  async getImagemInput(): Promise<string> {
    return await this.imagemInput.getAttribute('value');
  }

  async setAssuntoInput(assunto: string): Promise<void> {
    await this.assuntoInput.sendKeys(assunto);
  }

  async getAssuntoInput(): Promise<string> {
    return await this.assuntoInput.getAttribute('value');
  }

  async setTextoParte2Input(textoParte2: string): Promise<void> {
    await this.textoParte2Input.sendKeys(textoParte2);
  }

  async getTextoParte2Input(): Promise<string> {
    return await this.textoParte2Input.getAttribute('value');
  }

  async setTextoParte3Input(textoParte3: string): Promise<void> {
    await this.textoParte3Input.sendKeys(textoParte3);
  }

  async getTextoParte3Input(): Promise<string> {
    return await this.textoParte3Input.getAttribute('value');
  }

  async setSubAssuntoInput(subAssunto: string): Promise<void> {
    await this.subAssuntoInput.sendKeys(subAssunto);
  }

  async getSubAssuntoInput(): Promise<string> {
    return await this.subAssuntoInput.getAttribute('value');
  }

  async setNaoExibirPagEmpresaInput(naoExibirPagEmpresa: string): Promise<void> {
    await this.naoExibirPagEmpresaInput.sendKeys(naoExibirPagEmpresa);
  }

  async getNaoExibirPagEmpresaInput(): Promise<string> {
    return await this.naoExibirPagEmpresaInput.getAttribute('value');
  }

  getCriticoInput(): ElementFinder {
    return this.criticoInput;
  }

  getAprovadoInput(): ElementFinder {
    return this.aprovadoInput;
  }

  getEnviarSmsInput(): ElementFinder {
    return this.enviarSmsInput;
  }

  getEnviarEmailInput(): ElementFinder {
    return this.enviarEmailInput;
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

export class BoletimDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-boletim-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-boletim'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
