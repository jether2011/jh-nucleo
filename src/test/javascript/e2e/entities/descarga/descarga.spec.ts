import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DescargaComponentsPage, DescargaDeleteDialog, DescargaUpdatePage } from './descarga.page-object';

const expect = chai.expect;

describe('Descarga e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let descargaComponentsPage: DescargaComponentsPage;
  let descargaUpdatePage: DescargaUpdatePage;
  let descargaDeleteDialog: DescargaDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Descargas', async () => {
    await navBarPage.goToEntity('descarga');
    descargaComponentsPage = new DescargaComponentsPage();
    await browser.wait(ec.visibilityOf(descargaComponentsPage.title), 5000);
    expect(await descargaComponentsPage.getTitle()).to.eq('nucleoApp.descarga.home.title');
    await browser.wait(ec.or(ec.visibilityOf(descargaComponentsPage.entities), ec.visibilityOf(descargaComponentsPage.noResult)), 1000);
  });

  it('should load create Descarga page', async () => {
    await descargaComponentsPage.clickOnCreateButton();
    descargaUpdatePage = new DescargaUpdatePage();
    expect(await descargaUpdatePage.getPageTitle()).to.eq('nucleoApp.descarga.home.createOrEditLabel');
    await descargaUpdatePage.cancel();
  });

  it('should create and save Descargas', async () => {
    const nbButtonsBeforeCreate = await descargaComponentsPage.countDeleteButtons();

    await descargaComponentsPage.clickOnCreateButton();

    await promise.all([
      descargaUpdatePage.setNomeInput('nome'),
      descargaUpdatePage.setDescricaoInput('descricao'),
      descargaUpdatePage.setQtdInput('5'),
      descargaUpdatePage.setDataPrimeiraDescargaInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      descargaUpdatePage.setTempoAntecipacaoInput('21:42:30'),
      descargaUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      descargaUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      descargaUpdatePage.redeSelectLastOption(),
      descargaUpdatePage.descargaTipoSelectLastOption(),
      descargaUpdatePage.descargaUnidadeSelectLastOption(),
      descargaUpdatePage.alertaSelectLastOption(),
    ]);

    expect(await descargaUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await descargaUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    expect(await descargaUpdatePage.getQtdInput()).to.eq('5', 'Expected qtd value to be equals to 5');
    expect(await descargaUpdatePage.getDataPrimeiraDescargaInput()).to.contain(
      '2001-01-01T02:30',
      'Expected dataPrimeiraDescarga value to be equals to 2000-12-31'
    );
    expect(await descargaUpdatePage.getTempoAntecipacaoInput()).to.eq(
      '21:42:30',
      'Expected TempoAntecipacao value to be equals to 21:42:30'
    );
    expect(await descargaUpdatePage.getCreatedInput()).to.contain('2001-01-01T02:30', 'Expected created value to be equals to 2000-12-31');
    expect(await descargaUpdatePage.getUpdatedInput()).to.contain('2001-01-01T02:30', 'Expected updated value to be equals to 2000-12-31');

    await descargaUpdatePage.save();
    expect(await descargaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await descargaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Descarga', async () => {
    const nbButtonsBeforeDelete = await descargaComponentsPage.countDeleteButtons();
    await descargaComponentsPage.clickOnLastDeleteButton();

    descargaDeleteDialog = new DescargaDeleteDialog();
    expect(await descargaDeleteDialog.getDialogTitle()).to.eq('nucleoApp.descarga.delete.question');
    await descargaDeleteDialog.clickOnConfirmButton();

    expect(await descargaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
