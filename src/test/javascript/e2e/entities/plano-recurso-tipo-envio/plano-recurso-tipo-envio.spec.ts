import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  PlanoRecursoTipoEnvioComponentsPage,
  PlanoRecursoTipoEnvioDeleteDialog,
  PlanoRecursoTipoEnvioUpdatePage,
} from './plano-recurso-tipo-envio.page-object';

const expect = chai.expect;

describe('PlanoRecursoTipoEnvio e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let planoRecursoTipoEnvioComponentsPage: PlanoRecursoTipoEnvioComponentsPage;
  let planoRecursoTipoEnvioUpdatePage: PlanoRecursoTipoEnvioUpdatePage;
  let planoRecursoTipoEnvioDeleteDialog: PlanoRecursoTipoEnvioDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PlanoRecursoTipoEnvios', async () => {
    await navBarPage.goToEntity('plano-recurso-tipo-envio');
    planoRecursoTipoEnvioComponentsPage = new PlanoRecursoTipoEnvioComponentsPage();
    await browser.wait(ec.visibilityOf(planoRecursoTipoEnvioComponentsPage.title), 5000);
    expect(await planoRecursoTipoEnvioComponentsPage.getTitle()).to.eq('nucleoApp.planoRecursoTipoEnvio.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(planoRecursoTipoEnvioComponentsPage.entities), ec.visibilityOf(planoRecursoTipoEnvioComponentsPage.noResult)),
      1000
    );
  });

  it('should load create PlanoRecursoTipoEnvio page', async () => {
    await planoRecursoTipoEnvioComponentsPage.clickOnCreateButton();
    planoRecursoTipoEnvioUpdatePage = new PlanoRecursoTipoEnvioUpdatePage();
    expect(await planoRecursoTipoEnvioUpdatePage.getPageTitle()).to.eq('nucleoApp.planoRecursoTipoEnvio.home.createOrEditLabel');
    await planoRecursoTipoEnvioUpdatePage.cancel();
  });

  it('should create and save PlanoRecursoTipoEnvios', async () => {
    const nbButtonsBeforeCreate = await planoRecursoTipoEnvioComponentsPage.countDeleteButtons();

    await planoRecursoTipoEnvioComponentsPage.clickOnCreateButton();

    await promise.all([
      planoRecursoTipoEnvioUpdatePage.setNameInput('name'),
      planoRecursoTipoEnvioUpdatePage.setDescricaoInput('descricao'),
      planoRecursoTipoEnvioUpdatePage.setQtdInput('5'),
      planoRecursoTipoEnvioUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      planoRecursoTipoEnvioUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      planoRecursoTipoEnvioUpdatePage.planoRecursoSelectLastOption(),
      planoRecursoTipoEnvioUpdatePage.tipoEnvioSelectLastOption(),
    ]);

    expect(await planoRecursoTipoEnvioUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await planoRecursoTipoEnvioUpdatePage.getDescricaoInput()).to.eq(
      'descricao',
      'Expected Descricao value to be equals to descricao'
    );
    expect(await planoRecursoTipoEnvioUpdatePage.getQtdInput()).to.eq('5', 'Expected qtd value to be equals to 5');
    expect(await planoRecursoTipoEnvioUpdatePage.getCreatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected created value to be equals to 2000-12-31'
    );
    expect(await planoRecursoTipoEnvioUpdatePage.getUpdatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected updated value to be equals to 2000-12-31'
    );

    await planoRecursoTipoEnvioUpdatePage.save();
    expect(await planoRecursoTipoEnvioUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await planoRecursoTipoEnvioComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last PlanoRecursoTipoEnvio', async () => {
    const nbButtonsBeforeDelete = await planoRecursoTipoEnvioComponentsPage.countDeleteButtons();
    await planoRecursoTipoEnvioComponentsPage.clickOnLastDeleteButton();

    planoRecursoTipoEnvioDeleteDialog = new PlanoRecursoTipoEnvioDeleteDialog();
    expect(await planoRecursoTipoEnvioDeleteDialog.getDialogTitle()).to.eq('nucleoApp.planoRecursoTipoEnvio.delete.question');
    await planoRecursoTipoEnvioDeleteDialog.clickOnConfirmButton();

    expect(await planoRecursoTipoEnvioComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
