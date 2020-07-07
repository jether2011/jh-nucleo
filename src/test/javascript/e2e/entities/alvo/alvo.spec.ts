import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AlvoComponentsPage, AlvoDeleteDialog, AlvoUpdatePage } from './alvo.page-object';

const expect = chai.expect;

describe('Alvo e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let alvoComponentsPage: AlvoComponentsPage;
  let alvoUpdatePage: AlvoUpdatePage;
  let alvoDeleteDialog: AlvoDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Alvos', async () => {
    await navBarPage.goToEntity('alvo');
    alvoComponentsPage = new AlvoComponentsPage();
    await browser.wait(ec.visibilityOf(alvoComponentsPage.title), 5000);
    expect(await alvoComponentsPage.getTitle()).to.eq('nucleoApp.alvo.home.title');
    await browser.wait(ec.or(ec.visibilityOf(alvoComponentsPage.entities), ec.visibilityOf(alvoComponentsPage.noResult)), 1000);
  });

  it('should load create Alvo page', async () => {
    await alvoComponentsPage.clickOnCreateButton();
    alvoUpdatePage = new AlvoUpdatePage();
    expect(await alvoUpdatePage.getPageTitle()).to.eq('nucleoApp.alvo.home.createOrEditLabel');
    await alvoUpdatePage.cancel();
  });

  it('should create and save Alvos', async () => {
    const nbButtonsBeforeCreate = await alvoComponentsPage.countDeleteButtons();

    await alvoComponentsPage.clickOnCreateButton();

    await promise.all([
      alvoUpdatePage.setNomeInput('nome'),
      alvoUpdatePage.setNomeReduzidoInput('nomeReduzido'),
      alvoUpdatePage.setDescricaoInput('descricao'),
      alvoUpdatePage.setPrimeiroPontoInput('primeiroPonto'),
      alvoUpdatePage.setUltimoPontoInput('ultimoPonto'),
      alvoUpdatePage.setHorarioLiberacaoInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      alvoUpdatePage.setHorarioInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      alvoUpdatePage.setDuracaoInput('18:26:54'),
      alvoUpdatePage.setDuracaoAtualInput('07:02:04'),
      alvoUpdatePage.setDataDesativadoInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      alvoUpdatePage.setCoordenadasAlertaPontosInput('coordenadasAlertaPontos'),
      alvoUpdatePage.setCoordenadasLiberacaoPontosInput('coordenadasLiberacaoPontos'),
      alvoUpdatePage.setTelegramTokenBotInput('telegramTokenBot'),
      alvoUpdatePage.setTelegramChatIdInput('telegramChatId'),
      alvoUpdatePage.setHorarioBloqueioNotificacaoInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      alvoUpdatePage.setCoordenadasOriginalPontosInput('coordenadasOriginalPontos'),
      alvoUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      alvoUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      alvoUpdatePage.planoSelectLastOption(),
    ]);

    expect(await alvoUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await alvoUpdatePage.getNomeReduzidoInput()).to.eq('nomeReduzido', 'Expected NomeReduzido value to be equals to nomeReduzido');
    expect(await alvoUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    expect(await alvoUpdatePage.getPrimeiroPontoInput()).to.eq(
      'primeiroPonto',
      'Expected PrimeiroPonto value to be equals to primeiroPonto'
    );
    expect(await alvoUpdatePage.getUltimoPontoInput()).to.eq('ultimoPonto', 'Expected UltimoPonto value to be equals to ultimoPonto');
    expect(await alvoUpdatePage.getHorarioLiberacaoInput()).to.contain(
      '2001-01-01T02:30',
      'Expected horarioLiberacao value to be equals to 2000-12-31'
    );
    expect(await alvoUpdatePage.getHorarioInput()).to.contain('2001-01-01T02:30', 'Expected horario value to be equals to 2000-12-31');
    expect(await alvoUpdatePage.getDuracaoInput()).to.eq('18:26:54', 'Expected Duracao value to be equals to 18:26:54');
    expect(await alvoUpdatePage.getDuracaoAtualInput()).to.eq('07:02:04', 'Expected DuracaoAtual value to be equals to 07:02:04');
    expect(await alvoUpdatePage.getDataDesativadoInput()).to.contain(
      '2001-01-01T02:30',
      'Expected dataDesativado value to be equals to 2000-12-31'
    );
    expect(await alvoUpdatePage.getCoordenadasAlertaPontosInput()).to.eq(
      'coordenadasAlertaPontos',
      'Expected CoordenadasAlertaPontos value to be equals to coordenadasAlertaPontos'
    );
    expect(await alvoUpdatePage.getCoordenadasLiberacaoPontosInput()).to.eq(
      'coordenadasLiberacaoPontos',
      'Expected CoordenadasLiberacaoPontos value to be equals to coordenadasLiberacaoPontos'
    );
    expect(await alvoUpdatePage.getTelegramTokenBotInput()).to.eq(
      'telegramTokenBot',
      'Expected TelegramTokenBot value to be equals to telegramTokenBot'
    );
    expect(await alvoUpdatePage.getTelegramChatIdInput()).to.eq(
      'telegramChatId',
      'Expected TelegramChatId value to be equals to telegramChatId'
    );
    expect(await alvoUpdatePage.getHorarioBloqueioNotificacaoInput()).to.contain(
      '2001-01-01T02:30',
      'Expected horarioBloqueioNotificacao value to be equals to 2000-12-31'
    );
    expect(await alvoUpdatePage.getCoordenadasOriginalPontosInput()).to.eq(
      'coordenadasOriginalPontos',
      'Expected CoordenadasOriginalPontos value to be equals to coordenadasOriginalPontos'
    );
    const selectedAtivo = alvoUpdatePage.getAtivoInput();
    if (await selectedAtivo.isSelected()) {
      await alvoUpdatePage.getAtivoInput().click();
      expect(await alvoUpdatePage.getAtivoInput().isSelected(), 'Expected ativo not to be selected').to.be.false;
    } else {
      await alvoUpdatePage.getAtivoInput().click();
      expect(await alvoUpdatePage.getAtivoInput().isSelected(), 'Expected ativo to be selected').to.be.true;
    }
    expect(await alvoUpdatePage.getCreatedInput()).to.contain('2001-01-01T02:30', 'Expected created value to be equals to 2000-12-31');
    expect(await alvoUpdatePage.getUpdatedInput()).to.contain('2001-01-01T02:30', 'Expected updated value to be equals to 2000-12-31');

    await alvoUpdatePage.save();
    expect(await alvoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await alvoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Alvo', async () => {
    const nbButtonsBeforeDelete = await alvoComponentsPage.countDeleteButtons();
    await alvoComponentsPage.clickOnLastDeleteButton();

    alvoDeleteDialog = new AlvoDeleteDialog();
    expect(await alvoDeleteDialog.getDialogTitle()).to.eq('nucleoApp.alvo.delete.question');
    await alvoDeleteDialog.clickOnConfirmButton();

    expect(await alvoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
