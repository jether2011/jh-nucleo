import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ContatoAlvoComponentsPage, ContatoAlvoDeleteDialog, ContatoAlvoUpdatePage } from './contato-alvo.page-object';

const expect = chai.expect;

describe('ContatoAlvo e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let contatoAlvoComponentsPage: ContatoAlvoComponentsPage;
  let contatoAlvoUpdatePage: ContatoAlvoUpdatePage;
  let contatoAlvoDeleteDialog: ContatoAlvoDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ContatoAlvos', async () => {
    await navBarPage.goToEntity('contato-alvo');
    contatoAlvoComponentsPage = new ContatoAlvoComponentsPage();
    await browser.wait(ec.visibilityOf(contatoAlvoComponentsPage.title), 5000);
    expect(await contatoAlvoComponentsPage.getTitle()).to.eq('nucleoApp.contatoAlvo.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(contatoAlvoComponentsPage.entities), ec.visibilityOf(contatoAlvoComponentsPage.noResult)),
      1000
    );
  });

  it('should load create ContatoAlvo page', async () => {
    await contatoAlvoComponentsPage.clickOnCreateButton();
    contatoAlvoUpdatePage = new ContatoAlvoUpdatePage();
    expect(await contatoAlvoUpdatePage.getPageTitle()).to.eq('nucleoApp.contatoAlvo.home.createOrEditLabel');
    await contatoAlvoUpdatePage.cancel();
  });

  it('should create and save ContatoAlvos', async () => {
    const nbButtonsBeforeCreate = await contatoAlvoComponentsPage.countDeleteButtons();

    await contatoAlvoComponentsPage.clickOnCreateButton();

    await promise.all([
      contatoAlvoUpdatePage.setNomeInput('nome'),
      contatoAlvoUpdatePage.setDescricaoInput('descricao'),
      contatoAlvoUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      contatoAlvoUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      contatoAlvoUpdatePage.contatoSelectLastOption(),
      contatoAlvoUpdatePage.alvoSelectLastOption(),
    ]);

    expect(await contatoAlvoUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await contatoAlvoUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    expect(await contatoAlvoUpdatePage.getCreatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected created value to be equals to 2000-12-31'
    );
    expect(await contatoAlvoUpdatePage.getUpdatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected updated value to be equals to 2000-12-31'
    );

    await contatoAlvoUpdatePage.save();
    expect(await contatoAlvoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await contatoAlvoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last ContatoAlvo', async () => {
    const nbButtonsBeforeDelete = await contatoAlvoComponentsPage.countDeleteButtons();
    await contatoAlvoComponentsPage.clickOnLastDeleteButton();

    contatoAlvoDeleteDialog = new ContatoAlvoDeleteDialog();
    expect(await contatoAlvoDeleteDialog.getDialogTitle()).to.eq('nucleoApp.contatoAlvo.delete.question');
    await contatoAlvoDeleteDialog.clickOnConfirmButton();

    expect(await contatoAlvoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
