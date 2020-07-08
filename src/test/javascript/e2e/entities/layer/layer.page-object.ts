import { element, by, ElementFinder } from 'protractor';

export class LayerComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-layer div table .btn-danger'));
  title = element.all(by.css('jhi-layer div h2#page-heading span')).first();
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

export class LayerUpdatePage {
  pageTitle = element(by.id('jhi-layer-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  descriptionInput = element(by.id('field_description'));
  mapHostInput = element(by.id('field_mapHost'));
  layerTypeSelect = element(by.id('field_layerType'));
  titleInput = element(by.id('field_title'));
  attributionInput = element(by.id('field_attribution'));
  workspaceInput = element(by.id('field_workspace'));
  opacityInput = element(by.id('field_opacity'));
  baselayerInput = element(by.id('field_baselayer'));
  tiledInput = element(by.id('field_tiled'));
  gwcActivedInput = element(by.id('field_gwcActived'));
  activeInput = element(by.id('field_active'));
  enabledInput = element(by.id('field_enabled'));
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

  async setDescriptionInput(description: string): Promise<void> {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput(): Promise<string> {
    return await this.descriptionInput.getAttribute('value');
  }

  async setMapHostInput(mapHost: string): Promise<void> {
    await this.mapHostInput.sendKeys(mapHost);
  }

  async getMapHostInput(): Promise<string> {
    return await this.mapHostInput.getAttribute('value');
  }

  async setLayerTypeSelect(layerType: string): Promise<void> {
    await this.layerTypeSelect.sendKeys(layerType);
  }

  async getLayerTypeSelect(): Promise<string> {
    return await this.layerTypeSelect.element(by.css('option:checked')).getText();
  }

  async layerTypeSelectLastOption(): Promise<void> {
    await this.layerTypeSelect.all(by.tagName('option')).last().click();
  }

  async setTitleInput(title: string): Promise<void> {
    await this.titleInput.sendKeys(title);
  }

  async getTitleInput(): Promise<string> {
    return await this.titleInput.getAttribute('value');
  }

  async setAttributionInput(attribution: string): Promise<void> {
    await this.attributionInput.sendKeys(attribution);
  }

  async getAttributionInput(): Promise<string> {
    return await this.attributionInput.getAttribute('value');
  }

  async setWorkspaceInput(workspace: string): Promise<void> {
    await this.workspaceInput.sendKeys(workspace);
  }

  async getWorkspaceInput(): Promise<string> {
    return await this.workspaceInput.getAttribute('value');
  }

  async setOpacityInput(opacity: string): Promise<void> {
    await this.opacityInput.sendKeys(opacity);
  }

  async getOpacityInput(): Promise<string> {
    return await this.opacityInput.getAttribute('value');
  }

  getBaselayerInput(): ElementFinder {
    return this.baselayerInput;
  }

  getTiledInput(): ElementFinder {
    return this.tiledInput;
  }

  getGwcActivedInput(): ElementFinder {
    return this.gwcActivedInput;
  }

  getActiveInput(): ElementFinder {
    return this.activeInput;
  }

  getEnabledInput(): ElementFinder {
    return this.enabledInput;
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

export class LayerDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-layer-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-layer'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
