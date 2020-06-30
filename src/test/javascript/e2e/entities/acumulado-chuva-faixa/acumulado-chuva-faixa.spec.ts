import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  AcumuladoChuvaFaixaComponentsPage,
  AcumuladoChuvaFaixaDeleteDialog,
  AcumuladoChuvaFaixaUpdatePage,
} from './acumulado-chuva-faixa.page-object';

const expect = chai.expect;

describe('AcumuladoChuvaFaixa e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let acumuladoChuvaFaixaComponentsPage: AcumuladoChuvaFaixaComponentsPage;
  let acumuladoChuvaFaixaUpdatePage: AcumuladoChuvaFaixaUpdatePage;
  let acumuladoChuvaFaixaDeleteDialog: AcumuladoChuvaFaixaDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load AcumuladoChuvaFaixas', async () => {
    await navBarPage.goToEntity('acumulado-chuva-faixa');
    acumuladoChuvaFaixaComponentsPage = new AcumuladoChuvaFaixaComponentsPage();
    await browser.wait(ec.visibilityOf(acumuladoChuvaFaixaComponentsPage.title), 5000);
    expect(await acumuladoChuvaFaixaComponentsPage.getTitle()).to.eq('nucleoApp.acumuladoChuvaFaixa.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(acumuladoChuvaFaixaComponentsPage.entities), ec.visibilityOf(acumuladoChuvaFaixaComponentsPage.noResult)),
      1000
    );
  });

  it('should load create AcumuladoChuvaFaixa page', async () => {
    await acumuladoChuvaFaixaComponentsPage.clickOnCreateButton();
    acumuladoChuvaFaixaUpdatePage = new AcumuladoChuvaFaixaUpdatePage();
    expect(await acumuladoChuvaFaixaUpdatePage.getPageTitle()).to.eq('nucleoApp.acumuladoChuvaFaixa.home.createOrEditLabel');
    await acumuladoChuvaFaixaUpdatePage.cancel();
  });

  it('should create and save AcumuladoChuvaFaixas', async () => {
    const nbButtonsBeforeCreate = await acumuladoChuvaFaixaComponentsPage.countDeleteButtons();

    await acumuladoChuvaFaixaComponentsPage.clickOnCreateButton();

    await promise.all([
      acumuladoChuvaFaixaUpdatePage.setNomeInput('nome'),
      acumuladoChuvaFaixaUpdatePage.setDescricaoInput('descricao'),
      acumuladoChuvaFaixaUpdatePage.setDeMmInput('5'),
      acumuladoChuvaFaixaUpdatePage.setAteMmInput('5'),
      acumuladoChuvaFaixaUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      acumuladoChuvaFaixaUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
    ]);

    expect(await acumuladoChuvaFaixaUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await acumuladoChuvaFaixaUpdatePage.getDescricaoInput()).to.eq(
      'descricao',
      'Expected Descricao value to be equals to descricao'
    );
    expect(await acumuladoChuvaFaixaUpdatePage.getDeMmInput()).to.eq('5', 'Expected deMm value to be equals to 5');
    expect(await acumuladoChuvaFaixaUpdatePage.getAteMmInput()).to.eq('5', 'Expected ateMm value to be equals to 5');
    expect(await acumuladoChuvaFaixaUpdatePage.getCreatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected created value to be equals to 2000-12-31'
    );
    expect(await acumuladoChuvaFaixaUpdatePage.getUpdatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected updated value to be equals to 2000-12-31'
    );

    await acumuladoChuvaFaixaUpdatePage.save();
    expect(await acumuladoChuvaFaixaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await acumuladoChuvaFaixaComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last AcumuladoChuvaFaixa', async () => {
    const nbButtonsBeforeDelete = await acumuladoChuvaFaixaComponentsPage.countDeleteButtons();
    await acumuladoChuvaFaixaComponentsPage.clickOnLastDeleteButton();

    acumuladoChuvaFaixaDeleteDialog = new AcumuladoChuvaFaixaDeleteDialog();
    expect(await acumuladoChuvaFaixaDeleteDialog.getDialogTitle()).to.eq('nucleoApp.acumuladoChuvaFaixa.delete.question');
    await acumuladoChuvaFaixaDeleteDialog.clickOnConfirmButton();

    expect(await acumuladoChuvaFaixaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
