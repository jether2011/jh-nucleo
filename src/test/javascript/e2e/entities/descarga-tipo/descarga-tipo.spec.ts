import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DescargaTipoComponentsPage, DescargaTipoDeleteDialog, DescargaTipoUpdatePage } from './descarga-tipo.page-object';

const expect = chai.expect;

describe('DescargaTipo e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let descargaTipoComponentsPage: DescargaTipoComponentsPage;
  let descargaTipoUpdatePage: DescargaTipoUpdatePage;
  let descargaTipoDeleteDialog: DescargaTipoDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load DescargaTipos', async () => {
    await navBarPage.goToEntity('descarga-tipo');
    descargaTipoComponentsPage = new DescargaTipoComponentsPage();
    await browser.wait(ec.visibilityOf(descargaTipoComponentsPage.title), 5000);
    expect(await descargaTipoComponentsPage.getTitle()).to.eq('nucleoApp.descargaTipo.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(descargaTipoComponentsPage.entities), ec.visibilityOf(descargaTipoComponentsPage.noResult)),
      1000
    );
  });

  it('should load create DescargaTipo page', async () => {
    await descargaTipoComponentsPage.clickOnCreateButton();
    descargaTipoUpdatePage = new DescargaTipoUpdatePage();
    expect(await descargaTipoUpdatePage.getPageTitle()).to.eq('nucleoApp.descargaTipo.home.createOrEditLabel');
    await descargaTipoUpdatePage.cancel();
  });

  it('should create and save DescargaTipos', async () => {
    const nbButtonsBeforeCreate = await descargaTipoComponentsPage.countDeleteButtons();

    await descargaTipoComponentsPage.clickOnCreateButton();

    await promise.all([
      descargaTipoUpdatePage.setNomeInput('nome'),
      descargaTipoUpdatePage.setDescricaoInput('descricao'),
      descargaTipoUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      descargaTipoUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
    ]);

    expect(await descargaTipoUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await descargaTipoUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    expect(await descargaTipoUpdatePage.getCreatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected created value to be equals to 2000-12-31'
    );
    expect(await descargaTipoUpdatePage.getUpdatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected updated value to be equals to 2000-12-31'
    );

    await descargaTipoUpdatePage.save();
    expect(await descargaTipoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await descargaTipoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last DescargaTipo', async () => {
    const nbButtonsBeforeDelete = await descargaTipoComponentsPage.countDeleteButtons();
    await descargaTipoComponentsPage.clickOnLastDeleteButton();

    descargaTipoDeleteDialog = new DescargaTipoDeleteDialog();
    expect(await descargaTipoDeleteDialog.getDialogTitle()).to.eq('nucleoApp.descargaTipo.delete.question');
    await descargaTipoDeleteDialog.clickOnConfirmButton();

    expect(await descargaTipoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
