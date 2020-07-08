import { element, by, ElementFinder } from 'protractor';

export class NotificacaoEnviadaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-notificacao-enviada div table .btn-danger'));
  title = element.all(by.css('jhi-notificacao-enviada div h2#page-heading span')).first();
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

export class NotificacaoEnviadaUpdatePage {
  pageTitle = element(by.id('jhi-notificacao-enviada-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  descricaoInput = element(by.id('field_descricao'));
  destinatariosInput = element(by.id('field_destinatarios'));
  tipoInput = element(by.id('field_tipo'));
  statusInput = element(by.id('field_status'));
  assuntoInput = element(by.id('field_assunto'));
  enviadoInput = element(by.id('field_enviado'));
  contadorInput = element(by.id('field_contador'));
  amazonMessageIdInput = element(by.id('field_amazonMessageId'));
  amazonDateLogInput = element(by.id('field_amazonDateLog'));
  priceInUsdInput = element(by.id('field_priceInUsd'));
  amazonRespostaInput = element(by.id('field_amazonResposta'));
  referenceIdInput = element(by.id('field_referenceId'));
  createdInput = element(by.id('field_created'));
  updatedInput = element(by.id('field_updated'));

  planoRecursoSelect = element(by.id('field_planoRecurso'));

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

  async setDestinatariosInput(destinatarios: string): Promise<void> {
    await this.destinatariosInput.sendKeys(destinatarios);
  }

  async getDestinatariosInput(): Promise<string> {
    return await this.destinatariosInput.getAttribute('value');
  }

  async setTipoInput(tipo: string): Promise<void> {
    await this.tipoInput.sendKeys(tipo);
  }

  async getTipoInput(): Promise<string> {
    return await this.tipoInput.getAttribute('value');
  }

  async setStatusInput(status: string): Promise<void> {
    await this.statusInput.sendKeys(status);
  }

  async getStatusInput(): Promise<string> {
    return await this.statusInput.getAttribute('value');
  }

  async setAssuntoInput(assunto: string): Promise<void> {
    await this.assuntoInput.sendKeys(assunto);
  }

  async getAssuntoInput(): Promise<string> {
    return await this.assuntoInput.getAttribute('value');
  }

  async setEnviadoInput(enviado: string): Promise<void> {
    await this.enviadoInput.sendKeys(enviado);
  }

  async getEnviadoInput(): Promise<string> {
    return await this.enviadoInput.getAttribute('value');
  }

  async setContadorInput(contador: string): Promise<void> {
    await this.contadorInput.sendKeys(contador);
  }

  async getContadorInput(): Promise<string> {
    return await this.contadorInput.getAttribute('value');
  }

  async setAmazonMessageIdInput(amazonMessageId: string): Promise<void> {
    await this.amazonMessageIdInput.sendKeys(amazonMessageId);
  }

  async getAmazonMessageIdInput(): Promise<string> {
    return await this.amazonMessageIdInput.getAttribute('value');
  }

  async setAmazonDateLogInput(amazonDateLog: string): Promise<void> {
    await this.amazonDateLogInput.sendKeys(amazonDateLog);
  }

  async getAmazonDateLogInput(): Promise<string> {
    return await this.amazonDateLogInput.getAttribute('value');
  }

  async setPriceInUsdInput(priceInUsd: string): Promise<void> {
    await this.priceInUsdInput.sendKeys(priceInUsd);
  }

  async getPriceInUsdInput(): Promise<string> {
    return await this.priceInUsdInput.getAttribute('value');
  }

  async setAmazonRespostaInput(amazonResposta: string): Promise<void> {
    await this.amazonRespostaInput.sendKeys(amazonResposta);
  }

  async getAmazonRespostaInput(): Promise<string> {
    return await this.amazonRespostaInput.getAttribute('value');
  }

  async setReferenceIdInput(referenceId: string): Promise<void> {
    await this.referenceIdInput.sendKeys(referenceId);
  }

  async getReferenceIdInput(): Promise<string> {
    return await this.referenceIdInput.getAttribute('value');
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

export class NotificacaoEnviadaDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-notificacaoEnviada-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-notificacaoEnviada'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
