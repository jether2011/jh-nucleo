import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { UmidadeArComponentsPage, UmidadeArDeleteDialog, UmidadeArUpdatePage } from './umidade-ar.page-object';

const expect = chai.expect;

describe('UmidadeAr e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let umidadeArComponentsPage: UmidadeArComponentsPage;
  let umidadeArUpdatePage: UmidadeArUpdatePage;
  let umidadeArDeleteDialog: UmidadeArDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load UmidadeArs', async () => {
    await navBarPage.goToEntity('umidade-ar');
    umidadeArComponentsPage = new UmidadeArComponentsPage();
    await browser.wait(ec.visibilityOf(umidadeArComponentsPage.title), 5000);
    expect(await umidadeArComponentsPage.getTitle()).to.eq('nucleoApp.umidadeAr.home.title');
    await browser.wait(ec.or(ec.visibilityOf(umidadeArComponentsPage.entities), ec.visibilityOf(umidadeArComponentsPage.noResult)), 1000);
  });

  it('should load create UmidadeAr page', async () => {
    await umidadeArComponentsPage.clickOnCreateButton();
    umidadeArUpdatePage = new UmidadeArUpdatePage();
    expect(await umidadeArUpdatePage.getPageTitle()).to.eq('nucleoApp.umidadeAr.home.createOrEditLabel');
    await umidadeArUpdatePage.cancel();
  });

  it('should create and save UmidadeArs', async () => {
    const nbButtonsBeforeCreate = await umidadeArComponentsPage.countDeleteButtons();

    await umidadeArComponentsPage.clickOnCreateButton();

    await promise.all([
      umidadeArUpdatePage.setNameInput('name'),
      umidadeArUpdatePage.setDescricaoInput('descricao'),
      umidadeArUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      umidadeArUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
    ]);

    expect(await umidadeArUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await umidadeArUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    expect(await umidadeArUpdatePage.getCreatedInput()).to.contain('2001-01-01T02:30', 'Expected created value to be equals to 2000-12-31');
    expect(await umidadeArUpdatePage.getUpdatedInput()).to.contain('2001-01-01T02:30', 'Expected updated value to be equals to 2000-12-31');

    await umidadeArUpdatePage.save();
    expect(await umidadeArUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await umidadeArComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last UmidadeAr', async () => {
    const nbButtonsBeforeDelete = await umidadeArComponentsPage.countDeleteButtons();
    await umidadeArComponentsPage.clickOnLastDeleteButton();

    umidadeArDeleteDialog = new UmidadeArDeleteDialog();
    expect(await umidadeArDeleteDialog.getDialogTitle()).to.eq('nucleoApp.umidadeAr.delete.question');
    await umidadeArDeleteDialog.clickOnConfirmButton();

    expect(await umidadeArComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
