import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AlertaComponentsPage, AlertaDeleteDialog, AlertaUpdatePage } from './alerta.page-object';

const expect = chai.expect;

describe('Alerta e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let alertaComponentsPage: AlertaComponentsPage;
  let alertaUpdatePage: AlertaUpdatePage;
  let alertaDeleteDialog: AlertaDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Alertas', async () => {
    await navBarPage.goToEntity('alerta');
    alertaComponentsPage = new AlertaComponentsPage();
    await browser.wait(ec.visibilityOf(alertaComponentsPage.title), 5000);
    expect(await alertaComponentsPage.getTitle()).to.eq('nucleoApp.alerta.home.title');
    await browser.wait(ec.or(ec.visibilityOf(alertaComponentsPage.entities), ec.visibilityOf(alertaComponentsPage.noResult)), 1000);
  });

  it('should load create Alerta page', async () => {
    await alertaComponentsPage.clickOnCreateButton();
    alertaUpdatePage = new AlertaUpdatePage();
    expect(await alertaUpdatePage.getPageTitle()).to.eq('nucleoApp.alerta.home.createOrEditLabel');
    await alertaUpdatePage.cancel();
  });

  it('should create and save Alertas', async () => {
    const nbButtonsBeforeCreate = await alertaComponentsPage.countDeleteButtons();

    await alertaComponentsPage.clickOnCreateButton();

    await promise.all([
      alertaUpdatePage.setNomeInput('nome'),
      alertaUpdatePage.setContatoInput('contato'),
      alertaUpdatePage.setDuracaoInput('05:09:19'),
      alertaUpdatePage.setObservacaoInput('observacao'),
      alertaUpdatePage.setAlertaPaiIdInput('5'),
      alertaUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      alertaUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      alertaUpdatePage.planoRecursoSelectLastOption(),
      alertaUpdatePage.alvoSelectLastOption(),
      alertaUpdatePage.operadorUsuarioSelectLastOption(),
      alertaUpdatePage.alertaRiscoSelectLastOption(),
      alertaUpdatePage.tempestadeNivelSelectLastOption(),
      alertaUpdatePage.alertaTipoSelectLastOption(),
    ]);

    expect(await alertaUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await alertaUpdatePage.getContatoInput()).to.eq('contato', 'Expected Contato value to be equals to contato');
    expect(await alertaUpdatePage.getDuracaoInput()).to.eq('05:09:19', 'Expected Duracao value to be equals to 05:09:19');
    const selectedAutomatico = alertaUpdatePage.getAutomaticoInput();
    if (await selectedAutomatico.isSelected()) {
      await alertaUpdatePage.getAutomaticoInput().click();
      expect(await alertaUpdatePage.getAutomaticoInput().isSelected(), 'Expected automatico not to be selected').to.be.false;
    } else {
      await alertaUpdatePage.getAutomaticoInput().click();
      expect(await alertaUpdatePage.getAutomaticoInput().isSelected(), 'Expected automatico to be selected').to.be.true;
    }
    const selectedCritico = alertaUpdatePage.getCriticoInput();
    if (await selectedCritico.isSelected()) {
      await alertaUpdatePage.getCriticoInput().click();
      expect(await alertaUpdatePage.getCriticoInput().isSelected(), 'Expected critico not to be selected').to.be.false;
    } else {
      await alertaUpdatePage.getCriticoInput().click();
      expect(await alertaUpdatePage.getCriticoInput().isSelected(), 'Expected critico to be selected').to.be.true;
    }
    expect(await alertaUpdatePage.getObservacaoInput()).to.eq('observacao', 'Expected Observacao value to be equals to observacao');
    expect(await alertaUpdatePage.getAlertaPaiIdInput()).to.eq('5', 'Expected alertaPaiId value to be equals to 5');
    expect(await alertaUpdatePage.getCreatedInput()).to.contain('2001-01-01T02:30', 'Expected created value to be equals to 2000-12-31');
    expect(await alertaUpdatePage.getUpdatedInput()).to.contain('2001-01-01T02:30', 'Expected updated value to be equals to 2000-12-31');

    await alertaUpdatePage.save();
    expect(await alertaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await alertaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Alerta', async () => {
    const nbButtonsBeforeDelete = await alertaComponentsPage.countDeleteButtons();
    await alertaComponentsPage.clickOnLastDeleteButton();

    alertaDeleteDialog = new AlertaDeleteDialog();
    expect(await alertaDeleteDialog.getDialogTitle()).to.eq('nucleoApp.alerta.delete.question');
    await alertaDeleteDialog.clickOnConfirmButton();

    expect(await alertaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
