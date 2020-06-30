import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { IntegradorComponentsPage, IntegradorDeleteDialog, IntegradorUpdatePage } from './integrador.page-object';

const expect = chai.expect;

describe('Integrador e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let integradorComponentsPage: IntegradorComponentsPage;
  let integradorUpdatePage: IntegradorUpdatePage;
  let integradorDeleteDialog: IntegradorDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Integradors', async () => {
    await navBarPage.goToEntity('integrador');
    integradorComponentsPage = new IntegradorComponentsPage();
    await browser.wait(ec.visibilityOf(integradorComponentsPage.title), 5000);
    expect(await integradorComponentsPage.getTitle()).to.eq('nucleoApp.integrador.home.title');
    await browser.wait(ec.or(ec.visibilityOf(integradorComponentsPage.entities), ec.visibilityOf(integradorComponentsPage.noResult)), 1000);
  });

  it('should load create Integrador page', async () => {
    await integradorComponentsPage.clickOnCreateButton();
    integradorUpdatePage = new IntegradorUpdatePage();
    expect(await integradorUpdatePage.getPageTitle()).to.eq('nucleoApp.integrador.home.createOrEditLabel');
    await integradorUpdatePage.cancel();
  });

  it('should create and save Integradors', async () => {
    const nbButtonsBeforeCreate = await integradorComponentsPage.countDeleteButtons();

    await integradorComponentsPage.clickOnCreateButton();

    await promise.all([
      integradorUpdatePage.setNomeInput('nome'),
      integradorUpdatePage.setDescricaoInput('descricao'),
      integradorUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      integradorUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
    ]);

    expect(await integradorUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await integradorUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    expect(await integradorUpdatePage.getCreatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected created value to be equals to 2000-12-31'
    );
    expect(await integradorUpdatePage.getUpdatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected updated value to be equals to 2000-12-31'
    );

    await integradorUpdatePage.save();
    expect(await integradorUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await integradorComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Integrador', async () => {
    const nbButtonsBeforeDelete = await integradorComponentsPage.countDeleteButtons();
    await integradorComponentsPage.clickOnLastDeleteButton();

    integradorDeleteDialog = new IntegradorDeleteDialog();
    expect(await integradorDeleteDialog.getDialogTitle()).to.eq('nucleoApp.integrador.delete.question');
    await integradorDeleteDialog.clickOnConfirmButton();

    expect(await integradorComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
