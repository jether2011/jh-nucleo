import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TipoEnvioComponentsPage, TipoEnvioDeleteDialog, TipoEnvioUpdatePage } from './tipo-envio.page-object';

const expect = chai.expect;

describe('TipoEnvio e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let tipoEnvioComponentsPage: TipoEnvioComponentsPage;
  let tipoEnvioUpdatePage: TipoEnvioUpdatePage;
  let tipoEnvioDeleteDialog: TipoEnvioDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load TipoEnvios', async () => {
    await navBarPage.goToEntity('tipo-envio');
    tipoEnvioComponentsPage = new TipoEnvioComponentsPage();
    await browser.wait(ec.visibilityOf(tipoEnvioComponentsPage.title), 5000);
    expect(await tipoEnvioComponentsPage.getTitle()).to.eq('nucleoApp.tipoEnvio.home.title');
    await browser.wait(ec.or(ec.visibilityOf(tipoEnvioComponentsPage.entities), ec.visibilityOf(tipoEnvioComponentsPage.noResult)), 1000);
  });

  it('should load create TipoEnvio page', async () => {
    await tipoEnvioComponentsPage.clickOnCreateButton();
    tipoEnvioUpdatePage = new TipoEnvioUpdatePage();
    expect(await tipoEnvioUpdatePage.getPageTitle()).to.eq('nucleoApp.tipoEnvio.home.createOrEditLabel');
    await tipoEnvioUpdatePage.cancel();
  });

  it('should create and save TipoEnvios', async () => {
    const nbButtonsBeforeCreate = await tipoEnvioComponentsPage.countDeleteButtons();

    await tipoEnvioComponentsPage.clickOnCreateButton();

    await promise.all([
      tipoEnvioUpdatePage.setNameInput('name'),
      tipoEnvioUpdatePage.setDescricaoInput('descricao'),
      tipoEnvioUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      tipoEnvioUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
    ]);

    expect(await tipoEnvioUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await tipoEnvioUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    expect(await tipoEnvioUpdatePage.getCreatedInput()).to.contain('2001-01-01T02:30', 'Expected created value to be equals to 2000-12-31');
    expect(await tipoEnvioUpdatePage.getUpdatedInput()).to.contain('2001-01-01T02:30', 'Expected updated value to be equals to 2000-12-31');

    await tipoEnvioUpdatePage.save();
    expect(await tipoEnvioUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await tipoEnvioComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last TipoEnvio', async () => {
    const nbButtonsBeforeDelete = await tipoEnvioComponentsPage.countDeleteButtons();
    await tipoEnvioComponentsPage.clickOnLastDeleteButton();

    tipoEnvioDeleteDialog = new TipoEnvioDeleteDialog();
    expect(await tipoEnvioDeleteDialog.getDialogTitle()).to.eq('nucleoApp.tipoEnvio.delete.question');
    await tipoEnvioDeleteDialog.clickOnConfirmButton();

    expect(await tipoEnvioComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
