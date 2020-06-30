import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AlertaRiscoComponentsPage, AlertaRiscoDeleteDialog, AlertaRiscoUpdatePage } from './alerta-risco.page-object';

const expect = chai.expect;

describe('AlertaRisco e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let alertaRiscoComponentsPage: AlertaRiscoComponentsPage;
  let alertaRiscoUpdatePage: AlertaRiscoUpdatePage;
  let alertaRiscoDeleteDialog: AlertaRiscoDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load AlertaRiscos', async () => {
    await navBarPage.goToEntity('alerta-risco');
    alertaRiscoComponentsPage = new AlertaRiscoComponentsPage();
    await browser.wait(ec.visibilityOf(alertaRiscoComponentsPage.title), 5000);
    expect(await alertaRiscoComponentsPage.getTitle()).to.eq('nucleoApp.alertaRisco.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(alertaRiscoComponentsPage.entities), ec.visibilityOf(alertaRiscoComponentsPage.noResult)),
      1000
    );
  });

  it('should load create AlertaRisco page', async () => {
    await alertaRiscoComponentsPage.clickOnCreateButton();
    alertaRiscoUpdatePage = new AlertaRiscoUpdatePage();
    expect(await alertaRiscoUpdatePage.getPageTitle()).to.eq('nucleoApp.alertaRisco.home.createOrEditLabel');
    await alertaRiscoUpdatePage.cancel();
  });

  it('should create and save AlertaRiscos', async () => {
    const nbButtonsBeforeCreate = await alertaRiscoComponentsPage.countDeleteButtons();

    await alertaRiscoComponentsPage.clickOnCreateButton();

    await promise.all([
      alertaRiscoUpdatePage.setNomeInput('nome'),
      alertaRiscoUpdatePage.setDescricaoInput('descricao'),
      alertaRiscoUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      alertaRiscoUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
    ]);

    expect(await alertaRiscoUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await alertaRiscoUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    expect(await alertaRiscoUpdatePage.getCreatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected created value to be equals to 2000-12-31'
    );
    expect(await alertaRiscoUpdatePage.getUpdatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected updated value to be equals to 2000-12-31'
    );

    await alertaRiscoUpdatePage.save();
    expect(await alertaRiscoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await alertaRiscoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last AlertaRisco', async () => {
    const nbButtonsBeforeDelete = await alertaRiscoComponentsPage.countDeleteButtons();
    await alertaRiscoComponentsPage.clickOnLastDeleteButton();

    alertaRiscoDeleteDialog = new AlertaRiscoDeleteDialog();
    expect(await alertaRiscoDeleteDialog.getDialogTitle()).to.eq('nucleoApp.alertaRisco.delete.question');
    await alertaRiscoDeleteDialog.clickOnConfirmButton();

    expect(await alertaRiscoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
