import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PlanoLayerComponentsPage, PlanoLayerDeleteDialog, PlanoLayerUpdatePage } from './plano-layer.page-object';

const expect = chai.expect;

describe('PlanoLayer e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let planoLayerComponentsPage: PlanoLayerComponentsPage;
  let planoLayerUpdatePage: PlanoLayerUpdatePage;
  let planoLayerDeleteDialog: PlanoLayerDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PlanoLayers', async () => {
    await navBarPage.goToEntity('plano-layer');
    planoLayerComponentsPage = new PlanoLayerComponentsPage();
    await browser.wait(ec.visibilityOf(planoLayerComponentsPage.title), 5000);
    expect(await planoLayerComponentsPage.getTitle()).to.eq('nucleoApp.planoLayer.home.title');
    await browser.wait(ec.or(ec.visibilityOf(planoLayerComponentsPage.entities), ec.visibilityOf(planoLayerComponentsPage.noResult)), 1000);
  });

  it('should load create PlanoLayer page', async () => {
    await planoLayerComponentsPage.clickOnCreateButton();
    planoLayerUpdatePage = new PlanoLayerUpdatePage();
    expect(await planoLayerUpdatePage.getPageTitle()).to.eq('nucleoApp.planoLayer.home.createOrEditLabel');
    await planoLayerUpdatePage.cancel();
  });

  it('should create and save PlanoLayers', async () => {
    const nbButtonsBeforeCreate = await planoLayerComponentsPage.countDeleteButtons();

    await planoLayerComponentsPage.clickOnCreateButton();

    await promise.all([
      planoLayerUpdatePage.setNameInput('name'),
      planoLayerUpdatePage.setDescricaoInput('descricao'),
      planoLayerUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      planoLayerUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      planoLayerUpdatePage.planoSelectLastOption(),
      planoLayerUpdatePage.layerSelectLastOption(),
      planoLayerUpdatePage.alvoSelectLastOption(),
    ]);

    expect(await planoLayerUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await planoLayerUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    expect(await planoLayerUpdatePage.getCreatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected created value to be equals to 2000-12-31'
    );
    expect(await planoLayerUpdatePage.getUpdatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected updated value to be equals to 2000-12-31'
    );

    await planoLayerUpdatePage.save();
    expect(await planoLayerUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await planoLayerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last PlanoLayer', async () => {
    const nbButtonsBeforeDelete = await planoLayerComponentsPage.countDeleteButtons();
    await planoLayerComponentsPage.clickOnLastDeleteButton();

    planoLayerDeleteDialog = new PlanoLayerDeleteDialog();
    expect(await planoLayerDeleteDialog.getDialogTitle()).to.eq('nucleoApp.planoLayer.delete.question');
    await planoLayerDeleteDialog.clickOnConfirmButton();

    expect(await planoLayerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
