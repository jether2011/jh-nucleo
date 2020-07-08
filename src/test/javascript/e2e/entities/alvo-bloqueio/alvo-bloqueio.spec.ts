import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AlvoBloqueioComponentsPage, AlvoBloqueioDeleteDialog, AlvoBloqueioUpdatePage } from './alvo-bloqueio.page-object';

const expect = chai.expect;

describe('AlvoBloqueio e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let alvoBloqueioComponentsPage: AlvoBloqueioComponentsPage;
  let alvoBloqueioUpdatePage: AlvoBloqueioUpdatePage;
  let alvoBloqueioDeleteDialog: AlvoBloqueioDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load AlvoBloqueios', async () => {
    await navBarPage.goToEntity('alvo-bloqueio');
    alvoBloqueioComponentsPage = new AlvoBloqueioComponentsPage();
    await browser.wait(ec.visibilityOf(alvoBloqueioComponentsPage.title), 5000);
    expect(await alvoBloqueioComponentsPage.getTitle()).to.eq('nucleoApp.alvoBloqueio.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(alvoBloqueioComponentsPage.entities), ec.visibilityOf(alvoBloqueioComponentsPage.noResult)),
      1000
    );
  });

  it('should load create AlvoBloqueio page', async () => {
    await alvoBloqueioComponentsPage.clickOnCreateButton();
    alvoBloqueioUpdatePage = new AlvoBloqueioUpdatePage();
    expect(await alvoBloqueioUpdatePage.getPageTitle()).to.eq('nucleoApp.alvoBloqueio.home.createOrEditLabel');
    await alvoBloqueioUpdatePage.cancel();
  });

  it('should create and save AlvoBloqueios', async () => {
    const nbButtonsBeforeCreate = await alvoBloqueioComponentsPage.countDeleteButtons();

    await alvoBloqueioComponentsPage.clickOnCreateButton();

    await promise.all([
      alvoBloqueioUpdatePage.setNomeInput('nome'),
      alvoBloqueioUpdatePage.setDescricaoInput('descricao'),
      alvoBloqueioUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      alvoBloqueioUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      alvoBloqueioUpdatePage.alvoSelectLastOption(),
    ]);

    expect(await alvoBloqueioUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await alvoBloqueioUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    expect(await alvoBloqueioUpdatePage.getCreatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected created value to be equals to 2000-12-31'
    );
    expect(await alvoBloqueioUpdatePage.getUpdatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected updated value to be equals to 2000-12-31'
    );

    await alvoBloqueioUpdatePage.save();
    expect(await alvoBloqueioUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await alvoBloqueioComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last AlvoBloqueio', async () => {
    const nbButtonsBeforeDelete = await alvoBloqueioComponentsPage.countDeleteButtons();
    await alvoBloqueioComponentsPage.clickOnLastDeleteButton();

    alvoBloqueioDeleteDialog = new AlvoBloqueioDeleteDialog();
    expect(await alvoBloqueioDeleteDialog.getDialogTitle()).to.eq('nucleoApp.alvoBloqueio.delete.question');
    await alvoBloqueioDeleteDialog.clickOnConfirmButton();

    expect(await alvoBloqueioComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
