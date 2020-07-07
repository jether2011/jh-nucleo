import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { RedeComponentsPage, RedeDeleteDialog, RedeUpdatePage } from './rede.page-object';

const expect = chai.expect;

describe('Rede e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let redeComponentsPage: RedeComponentsPage;
  let redeUpdatePage: RedeUpdatePage;
  let redeDeleteDialog: RedeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Redes', async () => {
    await navBarPage.goToEntity('rede');
    redeComponentsPage = new RedeComponentsPage();
    await browser.wait(ec.visibilityOf(redeComponentsPage.title), 5000);
    expect(await redeComponentsPage.getTitle()).to.eq('nucleoApp.rede.home.title');
    await browser.wait(ec.or(ec.visibilityOf(redeComponentsPage.entities), ec.visibilityOf(redeComponentsPage.noResult)), 1000);
  });

  it('should load create Rede page', async () => {
    await redeComponentsPage.clickOnCreateButton();
    redeUpdatePage = new RedeUpdatePage();
    expect(await redeUpdatePage.getPageTitle()).to.eq('nucleoApp.rede.home.createOrEditLabel');
    await redeUpdatePage.cancel();
  });

  it('should create and save Redes', async () => {
    const nbButtonsBeforeCreate = await redeComponentsPage.countDeleteButtons();

    await redeComponentsPage.clickOnCreateButton();

    await promise.all([
      redeUpdatePage.setNameInput('name'),
      redeUpdatePage.setDescricaoInput('descricao'),
      redeUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      redeUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
    ]);

    expect(await redeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await redeUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    expect(await redeUpdatePage.getCreatedInput()).to.contain('2001-01-01T02:30', 'Expected created value to be equals to 2000-12-31');
    expect(await redeUpdatePage.getUpdatedInput()).to.contain('2001-01-01T02:30', 'Expected updated value to be equals to 2000-12-31');

    await redeUpdatePage.save();
    expect(await redeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await redeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Rede', async () => {
    const nbButtonsBeforeDelete = await redeComponentsPage.countDeleteButtons();
    await redeComponentsPage.clickOnLastDeleteButton();

    redeDeleteDialog = new RedeDeleteDialog();
    expect(await redeDeleteDialog.getDialogTitle()).to.eq('nucleoApp.rede.delete.question');
    await redeDeleteDialog.clickOnConfirmButton();

    expect(await redeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
