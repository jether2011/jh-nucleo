import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { LayerComponentsPage, LayerDeleteDialog, LayerUpdatePage } from './layer.page-object';

const expect = chai.expect;

describe('Layer e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let layerComponentsPage: LayerComponentsPage;
  let layerUpdatePage: LayerUpdatePage;
  let layerDeleteDialog: LayerDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Layers', async () => {
    await navBarPage.goToEntity('layer');
    layerComponentsPage = new LayerComponentsPage();
    await browser.wait(ec.visibilityOf(layerComponentsPage.title), 5000);
    expect(await layerComponentsPage.getTitle()).to.eq('nucleoApp.layer.home.title');
    await browser.wait(ec.or(ec.visibilityOf(layerComponentsPage.entities), ec.visibilityOf(layerComponentsPage.noResult)), 1000);
  });

  it('should load create Layer page', async () => {
    await layerComponentsPage.clickOnCreateButton();
    layerUpdatePage = new LayerUpdatePage();
    expect(await layerUpdatePage.getPageTitle()).to.eq('nucleoApp.layer.home.createOrEditLabel');
    await layerUpdatePage.cancel();
  });

  it('should create and save Layers', async () => {
    const nbButtonsBeforeCreate = await layerComponentsPage.countDeleteButtons();

    await layerComponentsPage.clickOnCreateButton();

    await promise.all([
      layerUpdatePage.setNameInput('name'),
      layerUpdatePage.setDescriptionInput('description'),
      layerUpdatePage.setMapHostInput('mapHost'),
      layerUpdatePage.layerTypeSelectLastOption(),
      layerUpdatePage.setTitleInput('title'),
      layerUpdatePage.setAttributionInput('attribution'),
      layerUpdatePage.setWorkspaceInput('workspace'),
      layerUpdatePage.setOpacityInput('5'),
      layerUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      layerUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
    ]);

    expect(await layerUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await layerUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');
    expect(await layerUpdatePage.getMapHostInput()).to.eq('mapHost', 'Expected MapHost value to be equals to mapHost');
    expect(await layerUpdatePage.getTitleInput()).to.eq('title', 'Expected Title value to be equals to title');
    expect(await layerUpdatePage.getAttributionInput()).to.eq('attribution', 'Expected Attribution value to be equals to attribution');
    expect(await layerUpdatePage.getWorkspaceInput()).to.eq('workspace', 'Expected Workspace value to be equals to workspace');
    expect(await layerUpdatePage.getOpacityInput()).to.eq('5', 'Expected opacity value to be equals to 5');
    const selectedBaselayer = layerUpdatePage.getBaselayerInput();
    if (await selectedBaselayer.isSelected()) {
      await layerUpdatePage.getBaselayerInput().click();
      expect(await layerUpdatePage.getBaselayerInput().isSelected(), 'Expected baselayer not to be selected').to.be.false;
    } else {
      await layerUpdatePage.getBaselayerInput().click();
      expect(await layerUpdatePage.getBaselayerInput().isSelected(), 'Expected baselayer to be selected').to.be.true;
    }
    const selectedTiled = layerUpdatePage.getTiledInput();
    if (await selectedTiled.isSelected()) {
      await layerUpdatePage.getTiledInput().click();
      expect(await layerUpdatePage.getTiledInput().isSelected(), 'Expected tiled not to be selected').to.be.false;
    } else {
      await layerUpdatePage.getTiledInput().click();
      expect(await layerUpdatePage.getTiledInput().isSelected(), 'Expected tiled to be selected').to.be.true;
    }
    const selectedGwcActived = layerUpdatePage.getGwcActivedInput();
    if (await selectedGwcActived.isSelected()) {
      await layerUpdatePage.getGwcActivedInput().click();
      expect(await layerUpdatePage.getGwcActivedInput().isSelected(), 'Expected gwcActived not to be selected').to.be.false;
    } else {
      await layerUpdatePage.getGwcActivedInput().click();
      expect(await layerUpdatePage.getGwcActivedInput().isSelected(), 'Expected gwcActived to be selected').to.be.true;
    }
    const selectedActive = layerUpdatePage.getActiveInput();
    if (await selectedActive.isSelected()) {
      await layerUpdatePage.getActiveInput().click();
      expect(await layerUpdatePage.getActiveInput().isSelected(), 'Expected active not to be selected').to.be.false;
    } else {
      await layerUpdatePage.getActiveInput().click();
      expect(await layerUpdatePage.getActiveInput().isSelected(), 'Expected active to be selected').to.be.true;
    }
    const selectedEnabled = layerUpdatePage.getEnabledInput();
    if (await selectedEnabled.isSelected()) {
      await layerUpdatePage.getEnabledInput().click();
      expect(await layerUpdatePage.getEnabledInput().isSelected(), 'Expected enabled not to be selected').to.be.false;
    } else {
      await layerUpdatePage.getEnabledInput().click();
      expect(await layerUpdatePage.getEnabledInput().isSelected(), 'Expected enabled to be selected').to.be.true;
    }
    expect(await layerUpdatePage.getCreatedInput()).to.contain('2001-01-01T02:30', 'Expected created value to be equals to 2000-12-31');
    expect(await layerUpdatePage.getUpdatedInput()).to.contain('2001-01-01T02:30', 'Expected updated value to be equals to 2000-12-31');

    await layerUpdatePage.save();
    expect(await layerUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await layerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Layer', async () => {
    const nbButtonsBeforeDelete = await layerComponentsPage.countDeleteButtons();
    await layerComponentsPage.clickOnLastDeleteButton();

    layerDeleteDialog = new LayerDeleteDialog();
    expect(await layerDeleteDialog.getDialogTitle()).to.eq('nucleoApp.layer.delete.question');
    await layerDeleteDialog.clickOnConfirmButton();

    expect(await layerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
