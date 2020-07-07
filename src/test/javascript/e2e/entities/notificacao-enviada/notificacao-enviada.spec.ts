import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  NotificacaoEnviadaComponentsPage,
  NotificacaoEnviadaDeleteDialog,
  NotificacaoEnviadaUpdatePage,
} from './notificacao-enviada.page-object';

const expect = chai.expect;

describe('NotificacaoEnviada e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let notificacaoEnviadaComponentsPage: NotificacaoEnviadaComponentsPage;
  let notificacaoEnviadaUpdatePage: NotificacaoEnviadaUpdatePage;
  let notificacaoEnviadaDeleteDialog: NotificacaoEnviadaDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load NotificacaoEnviadas', async () => {
    await navBarPage.goToEntity('notificacao-enviada');
    notificacaoEnviadaComponentsPage = new NotificacaoEnviadaComponentsPage();
    await browser.wait(ec.visibilityOf(notificacaoEnviadaComponentsPage.title), 5000);
    expect(await notificacaoEnviadaComponentsPage.getTitle()).to.eq('nucleoApp.notificacaoEnviada.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(notificacaoEnviadaComponentsPage.entities), ec.visibilityOf(notificacaoEnviadaComponentsPage.noResult)),
      1000
    );
  });

  it('should load create NotificacaoEnviada page', async () => {
    await notificacaoEnviadaComponentsPage.clickOnCreateButton();
    notificacaoEnviadaUpdatePage = new NotificacaoEnviadaUpdatePage();
    expect(await notificacaoEnviadaUpdatePage.getPageTitle()).to.eq('nucleoApp.notificacaoEnviada.home.createOrEditLabel');
    await notificacaoEnviadaUpdatePage.cancel();
  });

  it('should create and save NotificacaoEnviadas', async () => {
    const nbButtonsBeforeCreate = await notificacaoEnviadaComponentsPage.countDeleteButtons();

    await notificacaoEnviadaComponentsPage.clickOnCreateButton();

    await promise.all([
      notificacaoEnviadaUpdatePage.setNameInput('name'),
      notificacaoEnviadaUpdatePage.setDescricaoInput('descricao'),
      notificacaoEnviadaUpdatePage.setDestinatariosInput('destinatarios'),
      notificacaoEnviadaUpdatePage.setTipoInput('tipo'),
      notificacaoEnviadaUpdatePage.setStatusInput('status'),
      notificacaoEnviadaUpdatePage.setAssuntoInput('assunto'),
      notificacaoEnviadaUpdatePage.setEnviadoInput('5'),
      notificacaoEnviadaUpdatePage.setContadorInput('5'),
      notificacaoEnviadaUpdatePage.setAmazonMessageIdInput('amazonMessageId'),
      notificacaoEnviadaUpdatePage.setAmazonDateLogInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      notificacaoEnviadaUpdatePage.setPriceInUsdInput('5'),
      notificacaoEnviadaUpdatePage.setAmazonRespostaInput('amazonResposta'),
      notificacaoEnviadaUpdatePage.setReferenceIdInput('5'),
      notificacaoEnviadaUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      notificacaoEnviadaUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      notificacaoEnviadaUpdatePage.planoRecursoSelectLastOption(),
    ]);

    expect(await notificacaoEnviadaUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await notificacaoEnviadaUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    expect(await notificacaoEnviadaUpdatePage.getDestinatariosInput()).to.eq(
      'destinatarios',
      'Expected Destinatarios value to be equals to destinatarios'
    );
    expect(await notificacaoEnviadaUpdatePage.getTipoInput()).to.eq('tipo', 'Expected Tipo value to be equals to tipo');
    expect(await notificacaoEnviadaUpdatePage.getStatusInput()).to.eq('status', 'Expected Status value to be equals to status');
    expect(await notificacaoEnviadaUpdatePage.getAssuntoInput()).to.eq('assunto', 'Expected Assunto value to be equals to assunto');
    expect(await notificacaoEnviadaUpdatePage.getEnviadoInput()).to.eq('5', 'Expected enviado value to be equals to 5');
    expect(await notificacaoEnviadaUpdatePage.getContadorInput()).to.eq('5', 'Expected contador value to be equals to 5');
    expect(await notificacaoEnviadaUpdatePage.getAmazonMessageIdInput()).to.eq(
      'amazonMessageId',
      'Expected AmazonMessageId value to be equals to amazonMessageId'
    );
    expect(await notificacaoEnviadaUpdatePage.getAmazonDateLogInput()).to.contain(
      '2001-01-01T02:30',
      'Expected amazonDateLog value to be equals to 2000-12-31'
    );
    expect(await notificacaoEnviadaUpdatePage.getPriceInUsdInput()).to.eq('5', 'Expected priceInUsd value to be equals to 5');
    expect(await notificacaoEnviadaUpdatePage.getAmazonRespostaInput()).to.eq(
      'amazonResposta',
      'Expected AmazonResposta value to be equals to amazonResposta'
    );
    expect(await notificacaoEnviadaUpdatePage.getReferenceIdInput()).to.eq('5', 'Expected referenceId value to be equals to 5');
    expect(await notificacaoEnviadaUpdatePage.getCreatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected created value to be equals to 2000-12-31'
    );
    expect(await notificacaoEnviadaUpdatePage.getUpdatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected updated value to be equals to 2000-12-31'
    );

    await notificacaoEnviadaUpdatePage.save();
    expect(await notificacaoEnviadaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await notificacaoEnviadaComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last NotificacaoEnviada', async () => {
    const nbButtonsBeforeDelete = await notificacaoEnviadaComponentsPage.countDeleteButtons();
    await notificacaoEnviadaComponentsPage.clickOnLastDeleteButton();

    notificacaoEnviadaDeleteDialog = new NotificacaoEnviadaDeleteDialog();
    expect(await notificacaoEnviadaDeleteDialog.getDialogTitle()).to.eq('nucleoApp.notificacaoEnviada.delete.question');
    await notificacaoEnviadaDeleteDialog.clickOnConfirmButton();

    expect(await notificacaoEnviadaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
