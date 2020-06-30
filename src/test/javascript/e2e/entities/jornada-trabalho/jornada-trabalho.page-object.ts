import { element, by, ElementFinder } from 'protractor';

export class JornadaTrabalhoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-jornada-trabalho div table .btn-danger'));
  title = element.all(by.css('jhi-jornada-trabalho div h2#page-heading span')).first();
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

export class JornadaTrabalhoUpdatePage {
  pageTitle = element(by.id('jhi-jornada-trabalho-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nomeInput = element(by.id('field_nome'));
  descricaoInput = element(by.id('field_descricao'));
  horainicioInput = element(by.id('field_horainicio'));
  duracaoInput = element(by.id('field_duracao'));
  createdInput = element(by.id('field_created'));
  updatedInput = element(by.id('field_updated'));

  planoSelect = element(by.id('field_plano'));
  diaSemanaSelect = element(by.id('field_diaSemana'));

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

  async setHorainicioInput(horainicio: string): Promise<void> {
    await this.horainicioInput.sendKeys(horainicio);
  }

  async getHorainicioInput(): Promise<string> {
    return await this.horainicioInput.getAttribute('value');
  }

  async setDuracaoInput(duracao: string): Promise<void> {
    await this.duracaoInput.sendKeys(duracao);
  }

  async getDuracaoInput(): Promise<string> {
    return await this.duracaoInput.getAttribute('value');
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

  async diaSemanaSelectLastOption(): Promise<void> {
    await this.diaSemanaSelect.all(by.tagName('option')).last().click();
  }

  async diaSemanaSelectOption(option: string): Promise<void> {
    await this.diaSemanaSelect.sendKeys(option);
  }

  getDiaSemanaSelect(): ElementFinder {
    return this.diaSemanaSelect;
  }

  async getDiaSemanaSelectedOption(): Promise<string> {
    return await this.diaSemanaSelect.element(by.css('option:checked')).getText();
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

export class JornadaTrabalhoDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-jornadaTrabalho-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-jornadaTrabalho'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
