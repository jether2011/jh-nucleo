import { element, by, ElementFinder } from 'protractor';

export class AlvoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-alvo div table .btn-danger'));
  title = element.all(by.css('jhi-alvo div h2#page-heading span')).first();
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

export class AlvoUpdatePage {
  pageTitle = element(by.id('jhi-alvo-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nomeInput = element(by.id('field_nome'));
  nomeReduzidoInput = element(by.id('field_nomeReduzido'));
  descricaoInput = element(by.id('field_descricao'));
  primeiroPontoInput = element(by.id('field_primeiroPonto'));
  ultimoPontoInput = element(by.id('field_ultimoPonto'));
  horarioLiberacaoInput = element(by.id('field_horarioLiberacao'));
  horarioInput = element(by.id('field_horario'));
  duracaoInput = element(by.id('field_duracao'));
  duracaoAtualInput = element(by.id('field_duracaoAtual'));
  dataDesativadoInput = element(by.id('field_dataDesativado'));
  coordenadasAlertaPontosInput = element(by.id('field_coordenadasAlertaPontos'));
  coordenadasLiberacaoPontosInput = element(by.id('field_coordenadasLiberacaoPontos'));
  telegramTokenBotInput = element(by.id('field_telegramTokenBot'));
  telegramChatIdInput = element(by.id('field_telegramChatId'));
  horarioBloqueioNotificacaoInput = element(by.id('field_horarioBloqueioNotificacao'));
  coordenadasOriginalPontosInput = element(by.id('field_coordenadasOriginalPontos'));
  ativoInput = element(by.id('field_ativo'));
  createdInput = element(by.id('field_created'));
  updatedInput = element(by.id('field_updated'));

  planoSelect = element(by.id('field_plano'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNomeInput(nome: string): Promise<void> {
    await this.nomeInput.sendKeys(nome);
  }

  async getNomeInput(): Promise<string> {
    return await this.nomeInput.getAttribute('value');
  }

  async setNomeReduzidoInput(nomeReduzido: string): Promise<void> {
    await this.nomeReduzidoInput.sendKeys(nomeReduzido);
  }

  async getNomeReduzidoInput(): Promise<string> {
    return await this.nomeReduzidoInput.getAttribute('value');
  }

  async setDescricaoInput(descricao: string): Promise<void> {
    await this.descricaoInput.sendKeys(descricao);
  }

  async getDescricaoInput(): Promise<string> {
    return await this.descricaoInput.getAttribute('value');
  }

  async setPrimeiroPontoInput(primeiroPonto: string): Promise<void> {
    await this.primeiroPontoInput.sendKeys(primeiroPonto);
  }

  async getPrimeiroPontoInput(): Promise<string> {
    return await this.primeiroPontoInput.getAttribute('value');
  }

  async setUltimoPontoInput(ultimoPonto: string): Promise<void> {
    await this.ultimoPontoInput.sendKeys(ultimoPonto);
  }

  async getUltimoPontoInput(): Promise<string> {
    return await this.ultimoPontoInput.getAttribute('value');
  }

  async setHorarioLiberacaoInput(horarioLiberacao: string): Promise<void> {
    await this.horarioLiberacaoInput.sendKeys(horarioLiberacao);
  }

  async getHorarioLiberacaoInput(): Promise<string> {
    return await this.horarioLiberacaoInput.getAttribute('value');
  }

  async setHorarioInput(horario: string): Promise<void> {
    await this.horarioInput.sendKeys(horario);
  }

  async getHorarioInput(): Promise<string> {
    return await this.horarioInput.getAttribute('value');
  }

  async setDuracaoInput(duracao: string): Promise<void> {
    await this.duracaoInput.sendKeys(duracao);
  }

  async getDuracaoInput(): Promise<string> {
    return await this.duracaoInput.getAttribute('value');
  }

  async setDuracaoAtualInput(duracaoAtual: string): Promise<void> {
    await this.duracaoAtualInput.sendKeys(duracaoAtual);
  }

  async getDuracaoAtualInput(): Promise<string> {
    return await this.duracaoAtualInput.getAttribute('value');
  }

  async setDataDesativadoInput(dataDesativado: string): Promise<void> {
    await this.dataDesativadoInput.sendKeys(dataDesativado);
  }

  async getDataDesativadoInput(): Promise<string> {
    return await this.dataDesativadoInput.getAttribute('value');
  }

  async setCoordenadasAlertaPontosInput(coordenadasAlertaPontos: string): Promise<void> {
    await this.coordenadasAlertaPontosInput.sendKeys(coordenadasAlertaPontos);
  }

  async getCoordenadasAlertaPontosInput(): Promise<string> {
    return await this.coordenadasAlertaPontosInput.getAttribute('value');
  }

  async setCoordenadasLiberacaoPontosInput(coordenadasLiberacaoPontos: string): Promise<void> {
    await this.coordenadasLiberacaoPontosInput.sendKeys(coordenadasLiberacaoPontos);
  }

  async getCoordenadasLiberacaoPontosInput(): Promise<string> {
    return await this.coordenadasLiberacaoPontosInput.getAttribute('value');
  }

  async setTelegramTokenBotInput(telegramTokenBot: string): Promise<void> {
    await this.telegramTokenBotInput.sendKeys(telegramTokenBot);
  }

  async getTelegramTokenBotInput(): Promise<string> {
    return await this.telegramTokenBotInput.getAttribute('value');
  }

  async setTelegramChatIdInput(telegramChatId: string): Promise<void> {
    await this.telegramChatIdInput.sendKeys(telegramChatId);
  }

  async getTelegramChatIdInput(): Promise<string> {
    return await this.telegramChatIdInput.getAttribute('value');
  }

  async setHorarioBloqueioNotificacaoInput(horarioBloqueioNotificacao: string): Promise<void> {
    await this.horarioBloqueioNotificacaoInput.sendKeys(horarioBloqueioNotificacao);
  }

  async getHorarioBloqueioNotificacaoInput(): Promise<string> {
    return await this.horarioBloqueioNotificacaoInput.getAttribute('value');
  }

  async setCoordenadasOriginalPontosInput(coordenadasOriginalPontos: string): Promise<void> {
    await this.coordenadasOriginalPontosInput.sendKeys(coordenadasOriginalPontos);
  }

  async getCoordenadasOriginalPontosInput(): Promise<string> {
    return await this.coordenadasOriginalPontosInput.getAttribute('value');
  }

  getAtivoInput(): ElementFinder {
    return this.ativoInput;
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

  async planoSelectLastOption(): Promise<void> {
    await this.planoSelect.all(by.tagName('option')).last().click();
  }

  async planoSelectOption(option: string): Promise<void> {
    await this.planoSelect.sendKeys(option);
  }

  getPlanoSelect(): ElementFinder {
    return this.planoSelect;
  }

  async getPlanoSelectedOption(): Promise<string> {
    return await this.planoSelect.element(by.css('option:checked')).getText();
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

export class AlvoDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-alvo-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-alvo'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
