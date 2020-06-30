import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  ContatoTipoEnvioComponentsPage,
  /* ContatoTipoEnvioDeleteDialog, */
  ContatoTipoEnvioUpdatePage,
} from './contato-tipo-envio.page-object';

const expect = chai.expect;

describe('ContatoTipoEnvio e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let contatoTipoEnvioComponentsPage: ContatoTipoEnvioComponentsPage;
  let contatoTipoEnvioUpdatePage: ContatoTipoEnvioUpdatePage;
  /* let contatoTipoEnvioDeleteDialog: ContatoTipoEnvioDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ContatoTipoEnvios', async () => {
    await navBarPage.goToEntity('contato-tipo-envio');
    contatoTipoEnvioComponentsPage = new ContatoTipoEnvioComponentsPage();
    await browser.wait(ec.visibilityOf(contatoTipoEnvioComponentsPage.title), 5000);
    expect(await contatoTipoEnvioComponentsPage.getTitle()).to.eq('nucleoApp.contatoTipoEnvio.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(contatoTipoEnvioComponentsPage.entities), ec.visibilityOf(contatoTipoEnvioComponentsPage.noResult)),
      1000
    );
  });

  it('should load create ContatoTipoEnvio page', async () => {
    await contatoTipoEnvioComponentsPage.clickOnCreateButton();
    contatoTipoEnvioUpdatePage = new ContatoTipoEnvioUpdatePage();
    expect(await contatoTipoEnvioUpdatePage.getPageTitle()).to.eq('nucleoApp.contatoTipoEnvio.home.createOrEditLabel');
    await contatoTipoEnvioUpdatePage.cancel();
  });

  /* it('should create and save ContatoTipoEnvios', async () => {
        const nbButtonsBeforeCreate = await contatoTipoEnvioComponentsPage.countDeleteButtons();

        await contatoTipoEnvioComponentsPage.clickOnCreateButton();

        await promise.all([
            contatoTipoEnvioUpdatePage.setNomeInput('nome'),
            contatoTipoEnvioUpdatePage.setDescricaoInput('descricao'),
            contatoTipoEnvioUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            contatoTipoEnvioUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            contatoTipoEnvioUpdatePage.contatoSelectLastOption(),
            contatoTipoEnvioUpdatePage.tipoEnvioSelectLastOption(),
        ]);

        expect(await contatoTipoEnvioUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
        expect(await contatoTipoEnvioUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
        expect(await contatoTipoEnvioUpdatePage.getCreatedInput()).to.contain('2001-01-01T02:30', 'Expected created value to be equals to 2000-12-31');
        expect(await contatoTipoEnvioUpdatePage.getUpdatedInput()).to.contain('2001-01-01T02:30', 'Expected updated value to be equals to 2000-12-31');

        await contatoTipoEnvioUpdatePage.save();
        expect(await contatoTipoEnvioUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await contatoTipoEnvioComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last ContatoTipoEnvio', async () => {
        const nbButtonsBeforeDelete = await contatoTipoEnvioComponentsPage.countDeleteButtons();
        await contatoTipoEnvioComponentsPage.clickOnLastDeleteButton();

        contatoTipoEnvioDeleteDialog = new ContatoTipoEnvioDeleteDialog();
        expect(await contatoTipoEnvioDeleteDialog.getDialogTitle())
            .to.eq('nucleoApp.contatoTipoEnvio.delete.question');
        await contatoTipoEnvioDeleteDialog.clickOnConfirmButton();

        expect(await contatoTipoEnvioComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
