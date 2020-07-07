import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CondicaoTempoComponentsPage, CondicaoTempoDeleteDialog, CondicaoTempoUpdatePage } from './condicao-tempo.page-object';

const expect = chai.expect;

describe('CondicaoTempo e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let condicaoTempoComponentsPage: CondicaoTempoComponentsPage;
  let condicaoTempoUpdatePage: CondicaoTempoUpdatePage;
  let condicaoTempoDeleteDialog: CondicaoTempoDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load CondicaoTempos', async () => {
    await navBarPage.goToEntity('condicao-tempo');
    condicaoTempoComponentsPage = new CondicaoTempoComponentsPage();
    await browser.wait(ec.visibilityOf(condicaoTempoComponentsPage.title), 5000);
    expect(await condicaoTempoComponentsPage.getTitle()).to.eq('nucleoApp.condicaoTempo.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(condicaoTempoComponentsPage.entities), ec.visibilityOf(condicaoTempoComponentsPage.noResult)),
      1000
    );
  });

  it('should load create CondicaoTempo page', async () => {
    await condicaoTempoComponentsPage.clickOnCreateButton();
    condicaoTempoUpdatePage = new CondicaoTempoUpdatePage();
    expect(await condicaoTempoUpdatePage.getPageTitle()).to.eq('nucleoApp.condicaoTempo.home.createOrEditLabel');
    await condicaoTempoUpdatePage.cancel();
  });

  it('should create and save CondicaoTempos', async () => {
    const nbButtonsBeforeCreate = await condicaoTempoComponentsPage.countDeleteButtons();

    await condicaoTempoComponentsPage.clickOnCreateButton();

    await promise.all([
      condicaoTempoUpdatePage.setNomeInput('nome'),
      condicaoTempoUpdatePage.setDescricaoInput('descricao'),
      condicaoTempoUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      condicaoTempoUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
    ]);

    expect(await condicaoTempoUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await condicaoTempoUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    expect(await condicaoTempoUpdatePage.getCreatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected created value to be equals to 2000-12-31'
    );
    expect(await condicaoTempoUpdatePage.getUpdatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected updated value to be equals to 2000-12-31'
    );

    await condicaoTempoUpdatePage.save();
    expect(await condicaoTempoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await condicaoTempoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last CondicaoTempo', async () => {
    const nbButtonsBeforeDelete = await condicaoTempoComponentsPage.countDeleteButtons();
    await condicaoTempoComponentsPage.clickOnLastDeleteButton();

    condicaoTempoDeleteDialog = new CondicaoTempoDeleteDialog();
    expect(await condicaoTempoDeleteDialog.getDialogTitle()).to.eq('nucleoApp.condicaoTempo.delete.question');
    await condicaoTempoDeleteDialog.clickOnConfirmButton();

    expect(await condicaoTempoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
