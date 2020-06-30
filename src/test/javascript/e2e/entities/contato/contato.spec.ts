import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ContatoComponentsPage, ContatoDeleteDialog, ContatoUpdatePage } from './contato.page-object';

const expect = chai.expect;

describe('Contato e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let contatoComponentsPage: ContatoComponentsPage;
  let contatoUpdatePage: ContatoUpdatePage;
  let contatoDeleteDialog: ContatoDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Contatoes', async () => {
    await navBarPage.goToEntity('contato');
    contatoComponentsPage = new ContatoComponentsPage();
    await browser.wait(ec.visibilityOf(contatoComponentsPage.title), 5000);
    expect(await contatoComponentsPage.getTitle()).to.eq('nucleoApp.contato.home.title');
    await browser.wait(ec.or(ec.visibilityOf(contatoComponentsPage.entities), ec.visibilityOf(contatoComponentsPage.noResult)), 1000);
  });

  it('should load create Contato page', async () => {
    await contatoComponentsPage.clickOnCreateButton();
    contatoUpdatePage = new ContatoUpdatePage();
    expect(await contatoUpdatePage.getPageTitle()).to.eq('nucleoApp.contato.home.createOrEditLabel');
    await contatoUpdatePage.cancel();
  });

  it('should create and save Contatoes', async () => {
    const nbButtonsBeforeCreate = await contatoComponentsPage.countDeleteButtons();

    await contatoComponentsPage.clickOnCreateButton();

    await promise.all([
      contatoUpdatePage.setNomeInput('nome'),
      contatoUpdatePage.setDescricaoInput('descricao'),
      contatoUpdatePage.setEmailInput('email'),
      contatoUpdatePage.setCelularInput('celular'),
      contatoUpdatePage.setPrioridadeInput('5'),
      contatoUpdatePage.setHoraLigacaoInicialInput('21:44:27'),
      contatoUpdatePage.setHoraLigacaoFinalInput('21:49:28'),
      contatoUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      contatoUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
    ]);

    expect(await contatoUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await contatoUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    expect(await contatoUpdatePage.getEmailInput()).to.eq('email', 'Expected Email value to be equals to email');
    expect(await contatoUpdatePage.getCelularInput()).to.eq('celular', 'Expected Celular value to be equals to celular');
    const selectedAtivo = contatoUpdatePage.getAtivoInput();
    if (await selectedAtivo.isSelected()) {
      await contatoUpdatePage.getAtivoInput().click();
      expect(await contatoUpdatePage.getAtivoInput().isSelected(), 'Expected ativo not to be selected').to.be.false;
    } else {
      await contatoUpdatePage.getAtivoInput().click();
      expect(await contatoUpdatePage.getAtivoInput().isSelected(), 'Expected ativo to be selected').to.be.true;
    }
    const selectedContatoAlertaTelefonico = contatoUpdatePage.getContatoAlertaTelefonicoInput();
    if (await selectedContatoAlertaTelefonico.isSelected()) {
      await contatoUpdatePage.getContatoAlertaTelefonicoInput().click();
      expect(await contatoUpdatePage.getContatoAlertaTelefonicoInput().isSelected(), 'Expected contatoAlertaTelefonico not to be selected')
        .to.be.false;
    } else {
      await contatoUpdatePage.getContatoAlertaTelefonicoInput().click();
      expect(await contatoUpdatePage.getContatoAlertaTelefonicoInput().isSelected(), 'Expected contatoAlertaTelefonico to be selected').to
        .be.true;
    }
    expect(await contatoUpdatePage.getPrioridadeInput()).to.eq('5', 'Expected prioridade value to be equals to 5');
    expect(await contatoUpdatePage.getHoraLigacaoInicialInput()).to.eq(
      '21:44:27',
      'Expected HoraLigacaoInicial value to be equals to 21:44:27'
    );
    expect(await contatoUpdatePage.getHoraLigacaoFinalInput()).to.eq(
      '21:49:28',
      'Expected HoraLigacaoFinal value to be equals to 21:49:28'
    );
    expect(await contatoUpdatePage.getCreatedInput()).to.contain('2001-01-01T02:30', 'Expected created value to be equals to 2000-12-31');
    expect(await contatoUpdatePage.getUpdatedInput()).to.contain('2001-01-01T02:30', 'Expected updated value to be equals to 2000-12-31');

    await contatoUpdatePage.save();
    expect(await contatoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await contatoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Contato', async () => {
    const nbButtonsBeforeDelete = await contatoComponentsPage.countDeleteButtons();
    await contatoComponentsPage.clickOnLastDeleteButton();

    contatoDeleteDialog = new ContatoDeleteDialog();
    expect(await contatoDeleteDialog.getDialogTitle()).to.eq('nucleoApp.contato.delete.question');
    await contatoDeleteDialog.clickOnConfirmButton();

    expect(await contatoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
