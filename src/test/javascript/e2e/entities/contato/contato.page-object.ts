import { element, by, ElementFinder } from 'protractor';

export class ContatoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-contato div table .btn-danger'));
  title = element.all(by.css('jhi-contato div h2#page-heading span')).first();
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

export class ContatoUpdatePage {
  pageTitle = element(by.id('jhi-contato-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nomeInput = element(by.id('field_nome'));
  descricaoInput = element(by.id('field_descricao'));
  emailInput = element(by.id('field_email'));
  celularInput = element(by.id('field_celular'));
  ativoInput = element(by.id('field_ativo'));
  contatoAlertaTelefonicoInput = element(by.id('field_contatoAlertaTelefonico'));
  prioridadeInput = element(by.id('field_prioridade'));
  horaLigacaoInicialInput = element(by.id('field_horaLigacaoInicial'));
  horaLigacaoFinalInput = element(by.id('field_horaLigacaoFinal'));
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

  async setEmailInput(email: string): Promise<void> {
    await this.emailInput.sendKeys(email);
  }

  async getEmailInput(): Promise<string> {
    return await this.emailInput.getAttribute('value');
  }

  async setCelularInput(celular: string): Promise<void> {
    await this.celularInput.sendKeys(celular);
  }

  async getCelularInput(): Promise<string> {
    return await this.celularInput.getAttribute('value');
  }

  getAtivoInput(): ElementFinder {
    return this.ativoInput;
  }

  getContatoAlertaTelefonicoInput(): ElementFinder {
    return this.contatoAlertaTelefonicoInput;
  }

  async setPrioridadeInput(prioridade: string): Promise<void> {
    await this.prioridadeInput.sendKeys(prioridade);
  }

  async getPrioridadeInput(): Promise<string> {
    return await this.prioridadeInput.getAttribute('value');
  }

  async setHoraLigacaoInicialInput(horaLigacaoInicial: string): Promise<void> {
    await this.horaLigacaoInicialInput.sendKeys(horaLigacaoInicial);
  }

  async getHoraLigacaoInicialInput(): Promise<string> {
    return await this.horaLigacaoInicialInput.getAttribute('value');
  }

  async setHoraLigacaoFinalInput(horaLigacaoFinal: string): Promise<void> {
    await this.horaLigacaoFinalInput.sendKeys(horaLigacaoFinal);
  }

  async getHoraLigacaoFinalInput(): Promise<string> {
    return await this.horaLigacaoFinalInput.getAttribute('value');
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

export class ContatoDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-contato-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-contato'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
