import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PerfilComponentsPage, PerfilDeleteDialog, PerfilUpdatePage } from './perfil.page-object';

const expect = chai.expect;

describe('Perfil e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let perfilComponentsPage: PerfilComponentsPage;
  let perfilUpdatePage: PerfilUpdatePage;
  let perfilDeleteDialog: PerfilDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Perfils', async () => {
    await navBarPage.goToEntity('perfil');
    perfilComponentsPage = new PerfilComponentsPage();
    await browser.wait(ec.visibilityOf(perfilComponentsPage.title), 5000);
    expect(await perfilComponentsPage.getTitle()).to.eq('nucleoApp.perfil.home.title');
    await browser.wait(ec.or(ec.visibilityOf(perfilComponentsPage.entities), ec.visibilityOf(perfilComponentsPage.noResult)), 1000);
  });

  it('should load create Perfil page', async () => {
    await perfilComponentsPage.clickOnCreateButton();
    perfilUpdatePage = new PerfilUpdatePage();
    expect(await perfilUpdatePage.getPageTitle()).to.eq('nucleoApp.perfil.home.createOrEditLabel');
    await perfilUpdatePage.cancel();
  });

  it('should create and save Perfils', async () => {
    const nbButtonsBeforeCreate = await perfilComponentsPage.countDeleteButtons();

    await perfilComponentsPage.clickOnCreateButton();

    await promise.all([
      perfilUpdatePage.setNameInput('name'),
      perfilUpdatePage.setDescricaoInput('descricao'),
      perfilUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      perfilUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
    ]);

    expect(await perfilUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await perfilUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    expect(await perfilUpdatePage.getCreatedInput()).to.contain('2001-01-01T02:30', 'Expected created value to be equals to 2000-12-31');
    expect(await perfilUpdatePage.getUpdatedInput()).to.contain('2001-01-01T02:30', 'Expected updated value to be equals to 2000-12-31');

    await perfilUpdatePage.save();
    expect(await perfilUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await perfilComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Perfil', async () => {
    const nbButtonsBeforeDelete = await perfilComponentsPage.countDeleteButtons();
    await perfilComponentsPage.clickOnLastDeleteButton();

    perfilDeleteDialog = new PerfilDeleteDialog();
    expect(await perfilDeleteDialog.getDialogTitle()).to.eq('nucleoApp.perfil.delete.question');
    await perfilDeleteDialog.clickOnConfirmButton();

    expect(await perfilComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
