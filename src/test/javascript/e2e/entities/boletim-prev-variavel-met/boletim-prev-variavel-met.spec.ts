import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  BoletimPrevVariavelMetComponentsPage,
  BoletimPrevVariavelMetDeleteDialog,
  BoletimPrevVariavelMetUpdatePage,
} from './boletim-prev-variavel-met.page-object';

const expect = chai.expect;

describe('BoletimPrevVariavelMet e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let boletimPrevVariavelMetComponentsPage: BoletimPrevVariavelMetComponentsPage;
  let boletimPrevVariavelMetUpdatePage: BoletimPrevVariavelMetUpdatePage;
  let boletimPrevVariavelMetDeleteDialog: BoletimPrevVariavelMetDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load BoletimPrevVariavelMets', async () => {
    await navBarPage.goToEntity('boletim-prev-variavel-met');
    boletimPrevVariavelMetComponentsPage = new BoletimPrevVariavelMetComponentsPage();
    await browser.wait(ec.visibilityOf(boletimPrevVariavelMetComponentsPage.title), 5000);
    expect(await boletimPrevVariavelMetComponentsPage.getTitle()).to.eq('nucleoApp.boletimPrevVariavelMet.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(boletimPrevVariavelMetComponentsPage.entities), ec.visibilityOf(boletimPrevVariavelMetComponentsPage.noResult)),
      1000
    );
  });

  it('should load create BoletimPrevVariavelMet page', async () => {
    await boletimPrevVariavelMetComponentsPage.clickOnCreateButton();
    boletimPrevVariavelMetUpdatePage = new BoletimPrevVariavelMetUpdatePage();
    expect(await boletimPrevVariavelMetUpdatePage.getPageTitle()).to.eq('nucleoApp.boletimPrevVariavelMet.home.createOrEditLabel');
    await boletimPrevVariavelMetUpdatePage.cancel();
  });

  it('should create and save BoletimPrevVariavelMets', async () => {
    const nbButtonsBeforeCreate = await boletimPrevVariavelMetComponentsPage.countDeleteButtons();

    await boletimPrevVariavelMetComponentsPage.clickOnCreateButton();

    await promise.all([
      boletimPrevVariavelMetUpdatePage.setNomeInput('nome'),
      boletimPrevVariavelMetUpdatePage.setDescricaoInput('descricao'),
      boletimPrevVariavelMetUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      boletimPrevVariavelMetUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      boletimPrevVariavelMetUpdatePage.boletimPrevisaoSelectLastOption(),
      boletimPrevVariavelMetUpdatePage.variavelMeteorologicaSelectLastOption(),
    ]);

    expect(await boletimPrevVariavelMetUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await boletimPrevVariavelMetUpdatePage.getDescricaoInput()).to.eq(
      'descricao',
      'Expected Descricao value to be equals to descricao'
    );
    expect(await boletimPrevVariavelMetUpdatePage.getCreatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected created value to be equals to 2000-12-31'
    );
    expect(await boletimPrevVariavelMetUpdatePage.getUpdatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected updated value to be equals to 2000-12-31'
    );

    await boletimPrevVariavelMetUpdatePage.save();
    expect(await boletimPrevVariavelMetUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await boletimPrevVariavelMetComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last BoletimPrevVariavelMet', async () => {
    const nbButtonsBeforeDelete = await boletimPrevVariavelMetComponentsPage.countDeleteButtons();
    await boletimPrevVariavelMetComponentsPage.clickOnLastDeleteButton();

    boletimPrevVariavelMetDeleteDialog = new BoletimPrevVariavelMetDeleteDialog();
    expect(await boletimPrevVariavelMetDeleteDialog.getDialogTitle()).to.eq('nucleoApp.boletimPrevVariavelMet.delete.question');
    await boletimPrevVariavelMetDeleteDialog.clickOnConfirmButton();

    expect(await boletimPrevVariavelMetComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
