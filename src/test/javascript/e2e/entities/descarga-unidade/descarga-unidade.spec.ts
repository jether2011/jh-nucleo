import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DescargaUnidadeComponentsPage, DescargaUnidadeDeleteDialog, DescargaUnidadeUpdatePage } from './descarga-unidade.page-object';

const expect = chai.expect;

describe('DescargaUnidade e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let descargaUnidadeComponentsPage: DescargaUnidadeComponentsPage;
  let descargaUnidadeUpdatePage: DescargaUnidadeUpdatePage;
  let descargaUnidadeDeleteDialog: DescargaUnidadeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load DescargaUnidades', async () => {
    await navBarPage.goToEntity('descarga-unidade');
    descargaUnidadeComponentsPage = new DescargaUnidadeComponentsPage();
    await browser.wait(ec.visibilityOf(descargaUnidadeComponentsPage.title), 5000);
    expect(await descargaUnidadeComponentsPage.getTitle()).to.eq('nucleoApp.descargaUnidade.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(descargaUnidadeComponentsPage.entities), ec.visibilityOf(descargaUnidadeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create DescargaUnidade page', async () => {
    await descargaUnidadeComponentsPage.clickOnCreateButton();
    descargaUnidadeUpdatePage = new DescargaUnidadeUpdatePage();
    expect(await descargaUnidadeUpdatePage.getPageTitle()).to.eq('nucleoApp.descargaUnidade.home.createOrEditLabel');
    await descargaUnidadeUpdatePage.cancel();
  });

  it('should create and save DescargaUnidades', async () => {
    const nbButtonsBeforeCreate = await descargaUnidadeComponentsPage.countDeleteButtons();

    await descargaUnidadeComponentsPage.clickOnCreateButton();

    await promise.all([
      descargaUnidadeUpdatePage.setNomeInput('nome'),
      descargaUnidadeUpdatePage.setDescricaoInput('descricao'),
      descargaUnidadeUpdatePage.setUnidadeInput('5'),
      descargaUnidadeUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      descargaUnidadeUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
    ]);

    expect(await descargaUnidadeUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await descargaUnidadeUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    expect(await descargaUnidadeUpdatePage.getUnidadeInput()).to.eq('5', 'Expected unidade value to be equals to 5');
    expect(await descargaUnidadeUpdatePage.getCreatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected created value to be equals to 2000-12-31'
    );
    expect(await descargaUnidadeUpdatePage.getUpdatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected updated value to be equals to 2000-12-31'
    );

    await descargaUnidadeUpdatePage.save();
    expect(await descargaUnidadeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await descargaUnidadeComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last DescargaUnidade', async () => {
    const nbButtonsBeforeDelete = await descargaUnidadeComponentsPage.countDeleteButtons();
    await descargaUnidadeComponentsPage.clickOnLastDeleteButton();

    descargaUnidadeDeleteDialog = new DescargaUnidadeDeleteDialog();
    expect(await descargaUnidadeDeleteDialog.getDialogTitle()).to.eq('nucleoApp.descargaUnidade.delete.question');
    await descargaUnidadeDeleteDialog.clickOnConfirmButton();

    expect(await descargaUnidadeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
