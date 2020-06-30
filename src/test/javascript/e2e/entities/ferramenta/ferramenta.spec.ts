import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { FerramentaComponentsPage, FerramentaDeleteDialog, FerramentaUpdatePage } from './ferramenta.page-object';

const expect = chai.expect;

describe('Ferramenta e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let ferramentaComponentsPage: FerramentaComponentsPage;
  let ferramentaUpdatePage: FerramentaUpdatePage;
  let ferramentaDeleteDialog: FerramentaDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Ferramentas', async () => {
    await navBarPage.goToEntity('ferramenta');
    ferramentaComponentsPage = new FerramentaComponentsPage();
    await browser.wait(ec.visibilityOf(ferramentaComponentsPage.title), 5000);
    expect(await ferramentaComponentsPage.getTitle()).to.eq('nucleoApp.ferramenta.home.title');
    await browser.wait(ec.or(ec.visibilityOf(ferramentaComponentsPage.entities), ec.visibilityOf(ferramentaComponentsPage.noResult)), 1000);
  });

  it('should load create Ferramenta page', async () => {
    await ferramentaComponentsPage.clickOnCreateButton();
    ferramentaUpdatePage = new FerramentaUpdatePage();
    expect(await ferramentaUpdatePage.getPageTitle()).to.eq('nucleoApp.ferramenta.home.createOrEditLabel');
    await ferramentaUpdatePage.cancel();
  });

  it('should create and save Ferramentas', async () => {
    const nbButtonsBeforeCreate = await ferramentaComponentsPage.countDeleteButtons();

    await ferramentaComponentsPage.clickOnCreateButton();

    await promise.all([
      ferramentaUpdatePage.setNomeInput('nome'),
      ferramentaUpdatePage.setDescricaoInput('descricao'),
      ferramentaUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      ferramentaUpdatePage.setUpdatedInput('5'),
      ferramentaUpdatePage.tipoFerramentaSelectLastOption(),
    ]);

    expect(await ferramentaUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await ferramentaUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    expect(await ferramentaUpdatePage.getCreatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected created value to be equals to 2000-12-31'
    );
    expect(await ferramentaUpdatePage.getUpdatedInput()).to.eq('5', 'Expected updated value to be equals to 5');

    await ferramentaUpdatePage.save();
    expect(await ferramentaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await ferramentaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Ferramenta', async () => {
    const nbButtonsBeforeDelete = await ferramentaComponentsPage.countDeleteButtons();
    await ferramentaComponentsPage.clickOnLastDeleteButton();

    ferramentaDeleteDialog = new FerramentaDeleteDialog();
    expect(await ferramentaDeleteDialog.getDialogTitle()).to.eq('nucleoApp.ferramenta.delete.question');
    await ferramentaDeleteDialog.clickOnConfirmButton();

    expect(await ferramentaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
