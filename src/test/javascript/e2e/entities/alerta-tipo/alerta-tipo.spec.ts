import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AlertaTipoComponentsPage, AlertaTipoDeleteDialog, AlertaTipoUpdatePage } from './alerta-tipo.page-object';

const expect = chai.expect;

describe('AlertaTipo e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let alertaTipoComponentsPage: AlertaTipoComponentsPage;
  let alertaTipoUpdatePage: AlertaTipoUpdatePage;
  let alertaTipoDeleteDialog: AlertaTipoDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load AlertaTipos', async () => {
    await navBarPage.goToEntity('alerta-tipo');
    alertaTipoComponentsPage = new AlertaTipoComponentsPage();
    await browser.wait(ec.visibilityOf(alertaTipoComponentsPage.title), 5000);
    expect(await alertaTipoComponentsPage.getTitle()).to.eq('nucleoApp.alertaTipo.home.title');
    await browser.wait(ec.or(ec.visibilityOf(alertaTipoComponentsPage.entities), ec.visibilityOf(alertaTipoComponentsPage.noResult)), 1000);
  });

  it('should load create AlertaTipo page', async () => {
    await alertaTipoComponentsPage.clickOnCreateButton();
    alertaTipoUpdatePage = new AlertaTipoUpdatePage();
    expect(await alertaTipoUpdatePage.getPageTitle()).to.eq('nucleoApp.alertaTipo.home.createOrEditLabel');
    await alertaTipoUpdatePage.cancel();
  });

  it('should create and save AlertaTipos', async () => {
    const nbButtonsBeforeCreate = await alertaTipoComponentsPage.countDeleteButtons();

    await alertaTipoComponentsPage.clickOnCreateButton();

    await promise.all([
      alertaTipoUpdatePage.setNomeInput('nome'),
      alertaTipoUpdatePage.setDescricaoInput('descricao'),
      alertaTipoUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      alertaTipoUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
    ]);

    expect(await alertaTipoUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await alertaTipoUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    expect(await alertaTipoUpdatePage.getCreatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected created value to be equals to 2000-12-31'
    );
    expect(await alertaTipoUpdatePage.getUpdatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected updated value to be equals to 2000-12-31'
    );

    await alertaTipoUpdatePage.save();
    expect(await alertaTipoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await alertaTipoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last AlertaTipo', async () => {
    const nbButtonsBeforeDelete = await alertaTipoComponentsPage.countDeleteButtons();
    await alertaTipoComponentsPage.clickOnLastDeleteButton();

    alertaTipoDeleteDialog = new AlertaTipoDeleteDialog();
    expect(await alertaTipoDeleteDialog.getDialogTitle()).to.eq('nucleoApp.alertaTipo.delete.question');
    await alertaTipoDeleteDialog.clickOnConfirmButton();

    expect(await alertaTipoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
