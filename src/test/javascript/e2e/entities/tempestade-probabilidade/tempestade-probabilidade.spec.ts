import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  TempestadeProbabilidadeComponentsPage,
  TempestadeProbabilidadeDeleteDialog,
  TempestadeProbabilidadeUpdatePage,
} from './tempestade-probabilidade.page-object';

const expect = chai.expect;

describe('TempestadeProbabilidade e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let tempestadeProbabilidadeComponentsPage: TempestadeProbabilidadeComponentsPage;
  let tempestadeProbabilidadeUpdatePage: TempestadeProbabilidadeUpdatePage;
  let tempestadeProbabilidadeDeleteDialog: TempestadeProbabilidadeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load TempestadeProbabilidades', async () => {
    await navBarPage.goToEntity('tempestade-probabilidade');
    tempestadeProbabilidadeComponentsPage = new TempestadeProbabilidadeComponentsPage();
    await browser.wait(ec.visibilityOf(tempestadeProbabilidadeComponentsPage.title), 5000);
    expect(await tempestadeProbabilidadeComponentsPage.getTitle()).to.eq('nucleoApp.tempestadeProbabilidade.home.title');
    await browser.wait(
      ec.or(
        ec.visibilityOf(tempestadeProbabilidadeComponentsPage.entities),
        ec.visibilityOf(tempestadeProbabilidadeComponentsPage.noResult)
      ),
      1000
    );
  });

  it('should load create TempestadeProbabilidade page', async () => {
    await tempestadeProbabilidadeComponentsPage.clickOnCreateButton();
    tempestadeProbabilidadeUpdatePage = new TempestadeProbabilidadeUpdatePage();
    expect(await tempestadeProbabilidadeUpdatePage.getPageTitle()).to.eq('nucleoApp.tempestadeProbabilidade.home.createOrEditLabel');
    await tempestadeProbabilidadeUpdatePage.cancel();
  });

  it('should create and save TempestadeProbabilidades', async () => {
    const nbButtonsBeforeCreate = await tempestadeProbabilidadeComponentsPage.countDeleteButtons();

    await tempestadeProbabilidadeComponentsPage.clickOnCreateButton();

    await promise.all([
      tempestadeProbabilidadeUpdatePage.setNameInput('name'),
      tempestadeProbabilidadeUpdatePage.setDescricaoInput('descricao'),
      tempestadeProbabilidadeUpdatePage.setFaixaInput('faixa'),
      tempestadeProbabilidadeUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      tempestadeProbabilidadeUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
    ]);

    expect(await tempestadeProbabilidadeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await tempestadeProbabilidadeUpdatePage.getDescricaoInput()).to.eq(
      'descricao',
      'Expected Descricao value to be equals to descricao'
    );
    expect(await tempestadeProbabilidadeUpdatePage.getFaixaInput()).to.eq('faixa', 'Expected Faixa value to be equals to faixa');
    expect(await tempestadeProbabilidadeUpdatePage.getCreatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected created value to be equals to 2000-12-31'
    );
    expect(await tempestadeProbabilidadeUpdatePage.getUpdatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected updated value to be equals to 2000-12-31'
    );

    await tempestadeProbabilidadeUpdatePage.save();
    expect(await tempestadeProbabilidadeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await tempestadeProbabilidadeComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last TempestadeProbabilidade', async () => {
    const nbButtonsBeforeDelete = await tempestadeProbabilidadeComponentsPage.countDeleteButtons();
    await tempestadeProbabilidadeComponentsPage.clickOnLastDeleteButton();

    tempestadeProbabilidadeDeleteDialog = new TempestadeProbabilidadeDeleteDialog();
    expect(await tempestadeProbabilidadeDeleteDialog.getDialogTitle()).to.eq('nucleoApp.tempestadeProbabilidade.delete.question');
    await tempestadeProbabilidadeDeleteDialog.clickOnConfirmButton();

    expect(await tempestadeProbabilidadeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
