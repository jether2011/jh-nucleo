import { element, by, ElementFinder } from 'protractor';

export class PlanoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-plano div table .btn-danger'));
  title = element.all(by.css('jhi-plano div h2#page-heading span')).first();
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

export class PlanoUpdatePage {
  pageTitle = element(by.id('jhi-plano-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  descricaoInput = element(by.id('field_descricao'));
  horarioPrevistoInput = element(by.id('field_horarioPrevisto'));
  trackingAtivoInput = element(by.id('field_trackingAtivo'));
  plrAtivoInput = element(by.id('field_plrAtivo'));
  codigoWidgetPrevisaoInput = element(by.id('field_codigoWidgetPrevisao'));
  kmlAlvoInput = element(by.id('field_kmlAlvo'));
  zoomMinInput = element(by.id('field_zoomMin'));
  dtInicioContratoInput = element(by.id('field_dtInicioContrato'));
  dataFimContratoInput = element(by.id('field_dataFimContrato'));
  horarioMonitInicioInput = element(by.id('field_horarioMonitInicio'));
  horarioMonitFinalInput = element(by.id('field_horarioMonitFinal'));
  blocosInput = element(by.id('field_blocos'));
  extentInput = element(by.id('field_extent'));
  createdInput = element(by.id('field_created'));
  updatedInput = element(by.id('field_updated'));

  empresaSelect = element(by.id('field_empresa'));
  planoStatusSelect = element(by.id('field_planoStatus'));

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

  async setHorarioPrevistoInput(horarioPrevisto: string): Promise<void> {
    await this.horarioPrevistoInput.sendKeys(horarioPrevisto);
  }

  async getHorarioPrevistoInput(): Promise<string> {
    return await this.horarioPrevistoInput.getAttribute('value');
  }

  async setTrackingAtivoInput(trackingAtivo: string): Promise<void> {
    await this.trackingAtivoInput.sendKeys(trackingAtivo);
  }

  async getTrackingAtivoInput(): Promise<string> {
    return await this.trackingAtivoInput.getAttribute('value');
  }

  async setPlrAtivoInput(plrAtivo: string): Promise<void> {
    await this.plrAtivoInput.sendKeys(plrAtivo);
  }

  async getPlrAtivoInput(): Promise<string> {
    return await this.plrAtivoInput.getAttribute('value');
  }

  async setCodigoWidgetPrevisaoInput(codigoWidgetPrevisao: string): Promise<void> {
    await this.codigoWidgetPrevisaoInput.sendKeys(codigoWidgetPrevisao);
  }

  async getCodigoWidgetPrevisaoInput(): Promise<string> {
    return await this.codigoWidgetPrevisaoInput.getAttribute('value');
  }

  async setKmlAlvoInput(kmlAlvo: string): Promise<void> {
    await this.kmlAlvoInput.sendKeys(kmlAlvo);
  }

  async getKmlAlvoInput(): Promise<string> {
    return await this.kmlAlvoInput.getAttribute('value');
  }

  async setZoomMinInput(zoomMin: string): Promise<void> {
    await this.zoomMinInput.sendKeys(zoomMin);
  }

  async getZoomMinInput(): Promise<string> {
    return await this.zoomMinInput.getAttribute('value');
  }

  async setDtInicioContratoInput(dtInicioContrato: string): Promise<void> {
    await this.dtInicioContratoInput.sendKeys(dtInicioContrato);
  }

  async getDtInicioContratoInput(): Promise<string> {
    return await this.dtInicioContratoInput.getAttribute('value');
  }

  async setDataFimContratoInput(dataFimContrato: string): Promise<void> {
    await this.dataFimContratoInput.sendKeys(dataFimContrato);
  }

  async getDataFimContratoInput(): Promise<string> {
    return await this.dataFimContratoInput.getAttribute('value');
  }

  async setHorarioMonitInicioInput(horarioMonitInicio: string): Promise<void> {
    await this.horarioMonitInicioInput.sendKeys(horarioMonitInicio);
  }

  async getHorarioMonitInicioInput(): Promise<string> {
    return await this.horarioMonitInicioInput.getAttribute('value');
  }

  async setHorarioMonitFinalInput(horarioMonitFinal: string): Promise<void> {
    await this.horarioMonitFinalInput.sendKeys(horarioMonitFinal);
  }

  async getHorarioMonitFinalInput(): Promise<string> {
    return await this.horarioMonitFinalInput.getAttribute('value');
  }

  async setBlocosInput(blocos: string): Promise<void> {
    await this.blocosInput.sendKeys(blocos);
  }

  async getBlocosInput(): Promise<string> {
    return await this.blocosInput.getAttribute('value');
  }

  async setExtentInput(extent: string): Promise<void> {
    await this.extentInput.sendKeys(extent);
  }

  async getExtentInput(): Promise<string> {
    return await this.extentInput.getAttribute('value');
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

  async empresaSelectLastOption(): Promise<void> {
    await this.empresaSelect.all(by.tagName('option')).last().click();
  }

  async empresaSelectOption(option: string): Promise<void> {
    await this.empresaSelect.sendKeys(option);
  }

  getEmpresaSelect(): ElementFinder {
    return this.empresaSelect;
  }

  async getEmpresaSelectedOption(): Promise<string> {
    return await this.empresaSelect.element(by.css('option:checked')).getText();
  }

  async planoStatusSelectLastOption(): Promise<void> {
    await this.planoStatusSelect.all(by.tagName('option')).last().click();
  }

  async planoStatusSelectOption(option: string): Promise<void> {
    await this.planoStatusSelect.sendKeys(option);
  }

  getPlanoStatusSelect(): ElementFinder {
    return this.planoStatusSelect;
  }

  async getPlanoStatusSelectedOption(): Promise<string> {
    return await this.planoStatusSelect.element(by.css('option:checked')).getText();
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

export class PlanoDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-plano-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-plano'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
