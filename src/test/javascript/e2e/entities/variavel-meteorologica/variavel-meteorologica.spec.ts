import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  VariavelMeteorologicaComponentsPage,
  VariavelMeteorologicaDeleteDialog,
  VariavelMeteorologicaUpdatePage,
} from './variavel-meteorologica.page-object';

const expect = chai.expect;

describe('VariavelMeteorologica e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let variavelMeteorologicaComponentsPage: VariavelMeteorologicaComponentsPage;
  let variavelMeteorologicaUpdatePage: VariavelMeteorologicaUpdatePage;
  let variavelMeteorologicaDeleteDialog: VariavelMeteorologicaDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load VariavelMeteorologicas', async () => {
    await navBarPage.goToEntity('variavel-meteorologica');
    variavelMeteorologicaComponentsPage = new VariavelMeteorologicaComponentsPage();
    await browser.wait(ec.visibilityOf(variavelMeteorologicaComponentsPage.title), 5000);
    expect(await variavelMeteorologicaComponentsPage.getTitle()).to.eq('nucleoApp.variavelMeteorologica.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(variavelMeteorologicaComponentsPage.entities), ec.visibilityOf(variavelMeteorologicaComponentsPage.noResult)),
      1000
    );
  });

  it('should load create VariavelMeteorologica page', async () => {
    await variavelMeteorologicaComponentsPage.clickOnCreateButton();
    variavelMeteorologicaUpdatePage = new VariavelMeteorologicaUpdatePage();
    expect(await variavelMeteorologicaUpdatePage.getPageTitle()).to.eq('nucleoApp.variavelMeteorologica.home.createOrEditLabel');
    await variavelMeteorologicaUpdatePage.cancel();
  });

  it('should create and save VariavelMeteorologicas', async () => {
    const nbButtonsBeforeCreate = await variavelMeteorologicaComponentsPage.countDeleteButtons();

    await variavelMeteorologicaComponentsPage.clickOnCreateButton();

    await promise.all([
      variavelMeteorologicaUpdatePage.setNameInput('name'),
      variavelMeteorologicaUpdatePage.setDescricaoInput('descricao'),
      variavelMeteorologicaUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      variavelMeteorologicaUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
    ]);

    expect(await variavelMeteorologicaUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await variavelMeteorologicaUpdatePage.getDescricaoInput()).to.eq(
      'descricao',
      'Expected Descricao value to be equals to descricao'
    );
    expect(await variavelMeteorologicaUpdatePage.getCreatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected created value to be equals to 2000-12-31'
    );
    expect(await variavelMeteorologicaUpdatePage.getUpdatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected updated value to be equals to 2000-12-31'
    );

    await variavelMeteorologicaUpdatePage.save();
    expect(await variavelMeteorologicaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await variavelMeteorologicaComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last VariavelMeteorologica', async () => {
    const nbButtonsBeforeDelete = await variavelMeteorologicaComponentsPage.countDeleteButtons();
    await variavelMeteorologicaComponentsPage.clickOnLastDeleteButton();

    variavelMeteorologicaDeleteDialog = new VariavelMeteorologicaDeleteDialog();
    expect(await variavelMeteorologicaDeleteDialog.getDialogTitle()).to.eq('nucleoApp.variavelMeteorologica.delete.question');
    await variavelMeteorologicaDeleteDialog.clickOnConfirmButton();

    expect(await variavelMeteorologicaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
