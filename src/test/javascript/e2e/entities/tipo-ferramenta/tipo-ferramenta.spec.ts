import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TipoFerramentaComponentsPage, TipoFerramentaDeleteDialog, TipoFerramentaUpdatePage } from './tipo-ferramenta.page-object';

const expect = chai.expect;

describe('TipoFerramenta e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let tipoFerramentaComponentsPage: TipoFerramentaComponentsPage;
  let tipoFerramentaUpdatePage: TipoFerramentaUpdatePage;
  let tipoFerramentaDeleteDialog: TipoFerramentaDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load TipoFerramentas', async () => {
    await navBarPage.goToEntity('tipo-ferramenta');
    tipoFerramentaComponentsPage = new TipoFerramentaComponentsPage();
    await browser.wait(ec.visibilityOf(tipoFerramentaComponentsPage.title), 5000);
    expect(await tipoFerramentaComponentsPage.getTitle()).to.eq('nucleoApp.tipoFerramenta.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(tipoFerramentaComponentsPage.entities), ec.visibilityOf(tipoFerramentaComponentsPage.noResult)),
      1000
    );
  });

  it('should load create TipoFerramenta page', async () => {
    await tipoFerramentaComponentsPage.clickOnCreateButton();
    tipoFerramentaUpdatePage = new TipoFerramentaUpdatePage();
    expect(await tipoFerramentaUpdatePage.getPageTitle()).to.eq('nucleoApp.tipoFerramenta.home.createOrEditLabel');
    await tipoFerramentaUpdatePage.cancel();
  });

  it('should create and save TipoFerramentas', async () => {
    const nbButtonsBeforeCreate = await tipoFerramentaComponentsPage.countDeleteButtons();

    await tipoFerramentaComponentsPage.clickOnCreateButton();

    await promise.all([
      tipoFerramentaUpdatePage.setNameInput('name'),
      tipoFerramentaUpdatePage.setDescricaoInput('descricao'),
      tipoFerramentaUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      tipoFerramentaUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
    ]);

    expect(await tipoFerramentaUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await tipoFerramentaUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    expect(await tipoFerramentaUpdatePage.getCreatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected created value to be equals to 2000-12-31'
    );
    expect(await tipoFerramentaUpdatePage.getUpdatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected updated value to be equals to 2000-12-31'
    );

    await tipoFerramentaUpdatePage.save();
    expect(await tipoFerramentaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await tipoFerramentaComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last TipoFerramenta', async () => {
    const nbButtonsBeforeDelete = await tipoFerramentaComponentsPage.countDeleteButtons();
    await tipoFerramentaComponentsPage.clickOnLastDeleteButton();

    tipoFerramentaDeleteDialog = new TipoFerramentaDeleteDialog();
    expect(await tipoFerramentaDeleteDialog.getDialogTitle()).to.eq('nucleoApp.tipoFerramenta.delete.question');
    await tipoFerramentaDeleteDialog.clickOnConfirmButton();

    expect(await tipoFerramentaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
