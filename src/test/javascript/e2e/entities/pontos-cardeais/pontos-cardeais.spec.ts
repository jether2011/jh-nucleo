import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PontosCardeaisComponentsPage, PontosCardeaisDeleteDialog, PontosCardeaisUpdatePage } from './pontos-cardeais.page-object';

const expect = chai.expect;

describe('PontosCardeais e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let pontosCardeaisComponentsPage: PontosCardeaisComponentsPage;
  let pontosCardeaisUpdatePage: PontosCardeaisUpdatePage;
  let pontosCardeaisDeleteDialog: PontosCardeaisDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PontosCardeais', async () => {
    await navBarPage.goToEntity('pontos-cardeais');
    pontosCardeaisComponentsPage = new PontosCardeaisComponentsPage();
    await browser.wait(ec.visibilityOf(pontosCardeaisComponentsPage.title), 5000);
    expect(await pontosCardeaisComponentsPage.getTitle()).to.eq('nucleoApp.pontosCardeais.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(pontosCardeaisComponentsPage.entities), ec.visibilityOf(pontosCardeaisComponentsPage.noResult)),
      1000
    );
  });

  it('should load create PontosCardeais page', async () => {
    await pontosCardeaisComponentsPage.clickOnCreateButton();
    pontosCardeaisUpdatePage = new PontosCardeaisUpdatePage();
    expect(await pontosCardeaisUpdatePage.getPageTitle()).to.eq('nucleoApp.pontosCardeais.home.createOrEditLabel');
    await pontosCardeaisUpdatePage.cancel();
  });

  it('should create and save PontosCardeais', async () => {
    const nbButtonsBeforeCreate = await pontosCardeaisComponentsPage.countDeleteButtons();

    await pontosCardeaisComponentsPage.clickOnCreateButton();

    await promise.all([
      pontosCardeaisUpdatePage.setNameInput('name'),
      pontosCardeaisUpdatePage.setDescricaoInput('descricao'),
      pontosCardeaisUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      pontosCardeaisUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
    ]);

    expect(await pontosCardeaisUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await pontosCardeaisUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    expect(await pontosCardeaisUpdatePage.getCreatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected created value to be equals to 2000-12-31'
    );
    expect(await pontosCardeaisUpdatePage.getUpdatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected updated value to be equals to 2000-12-31'
    );

    await pontosCardeaisUpdatePage.save();
    expect(await pontosCardeaisUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await pontosCardeaisComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last PontosCardeais', async () => {
    const nbButtonsBeforeDelete = await pontosCardeaisComponentsPage.countDeleteButtons();
    await pontosCardeaisComponentsPage.clickOnLastDeleteButton();

    pontosCardeaisDeleteDialog = new PontosCardeaisDeleteDialog();
    expect(await pontosCardeaisDeleteDialog.getDialogTitle()).to.eq('nucleoApp.pontosCardeais.delete.question');
    await pontosCardeaisDeleteDialog.clickOnConfirmButton();

    expect(await pontosCardeaisComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
