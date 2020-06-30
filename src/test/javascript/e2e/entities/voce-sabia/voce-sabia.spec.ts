import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { VoceSabiaComponentsPage, VoceSabiaDeleteDialog, VoceSabiaUpdatePage } from './voce-sabia.page-object';

const expect = chai.expect;

describe('VoceSabia e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let voceSabiaComponentsPage: VoceSabiaComponentsPage;
  let voceSabiaUpdatePage: VoceSabiaUpdatePage;
  let voceSabiaDeleteDialog: VoceSabiaDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load VoceSabias', async () => {
    await navBarPage.goToEntity('voce-sabia');
    voceSabiaComponentsPage = new VoceSabiaComponentsPage();
    await browser.wait(ec.visibilityOf(voceSabiaComponentsPage.title), 5000);
    expect(await voceSabiaComponentsPage.getTitle()).to.eq('nucleoApp.voceSabia.home.title');
    await browser.wait(ec.or(ec.visibilityOf(voceSabiaComponentsPage.entities), ec.visibilityOf(voceSabiaComponentsPage.noResult)), 1000);
  });

  it('should load create VoceSabia page', async () => {
    await voceSabiaComponentsPage.clickOnCreateButton();
    voceSabiaUpdatePage = new VoceSabiaUpdatePage();
    expect(await voceSabiaUpdatePage.getPageTitle()).to.eq('nucleoApp.voceSabia.home.createOrEditLabel');
    await voceSabiaUpdatePage.cancel();
  });

  it('should create and save VoceSabias', async () => {
    const nbButtonsBeforeCreate = await voceSabiaComponentsPage.countDeleteButtons();

    await voceSabiaComponentsPage.clickOnCreateButton();

    await promise.all([
      voceSabiaUpdatePage.setNameInput('name'),
      voceSabiaUpdatePage.setDescricaoInput('descricao'),
      voceSabiaUpdatePage.setTituloInput('titulo'),
      voceSabiaUpdatePage.setTextoInput('texto'),
      voceSabiaUpdatePage.setImagemInput('imagem'),
      voceSabiaUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      voceSabiaUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
    ]);

    expect(await voceSabiaUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await voceSabiaUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    expect(await voceSabiaUpdatePage.getTituloInput()).to.eq('titulo', 'Expected Titulo value to be equals to titulo');
    expect(await voceSabiaUpdatePage.getTextoInput()).to.eq('texto', 'Expected Texto value to be equals to texto');
    expect(await voceSabiaUpdatePage.getImagemInput()).to.eq('imagem', 'Expected Imagem value to be equals to imagem');
    expect(await voceSabiaUpdatePage.getCreatedInput()).to.contain('2001-01-01T02:30', 'Expected created value to be equals to 2000-12-31');
    expect(await voceSabiaUpdatePage.getUpdatedInput()).to.contain('2001-01-01T02:30', 'Expected updated value to be equals to 2000-12-31');

    await voceSabiaUpdatePage.save();
    expect(await voceSabiaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await voceSabiaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last VoceSabia', async () => {
    const nbButtonsBeforeDelete = await voceSabiaComponentsPage.countDeleteButtons();
    await voceSabiaComponentsPage.clickOnLastDeleteButton();

    voceSabiaDeleteDialog = new VoceSabiaDeleteDialog();
    expect(await voceSabiaDeleteDialog.getDialogTitle()).to.eq('nucleoApp.voceSabia.delete.question');
    await voceSabiaDeleteDialog.clickOnConfirmButton();

    expect(await voceSabiaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
