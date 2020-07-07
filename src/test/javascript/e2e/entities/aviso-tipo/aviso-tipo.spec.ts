import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AvisoTipoComponentsPage, AvisoTipoDeleteDialog, AvisoTipoUpdatePage } from './aviso-tipo.page-object';

const expect = chai.expect;

describe('AvisoTipo e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let avisoTipoComponentsPage: AvisoTipoComponentsPage;
  let avisoTipoUpdatePage: AvisoTipoUpdatePage;
  let avisoTipoDeleteDialog: AvisoTipoDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load AvisoTipos', async () => {
    await navBarPage.goToEntity('aviso-tipo');
    avisoTipoComponentsPage = new AvisoTipoComponentsPage();
    await browser.wait(ec.visibilityOf(avisoTipoComponentsPage.title), 5000);
    expect(await avisoTipoComponentsPage.getTitle()).to.eq('nucleoApp.avisoTipo.home.title');
    await browser.wait(ec.or(ec.visibilityOf(avisoTipoComponentsPage.entities), ec.visibilityOf(avisoTipoComponentsPage.noResult)), 1000);
  });

  it('should load create AvisoTipo page', async () => {
    await avisoTipoComponentsPage.clickOnCreateButton();
    avisoTipoUpdatePage = new AvisoTipoUpdatePage();
    expect(await avisoTipoUpdatePage.getPageTitle()).to.eq('nucleoApp.avisoTipo.home.createOrEditLabel');
    await avisoTipoUpdatePage.cancel();
  });

  it('should create and save AvisoTipos', async () => {
    const nbButtonsBeforeCreate = await avisoTipoComponentsPage.countDeleteButtons();

    await avisoTipoComponentsPage.clickOnCreateButton();

    await promise.all([
      avisoTipoUpdatePage.setNomeInput('nome'),
      avisoTipoUpdatePage.setDescricaoInput('descricao'),
      avisoTipoUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      avisoTipoUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
    ]);

    expect(await avisoTipoUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await avisoTipoUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    expect(await avisoTipoUpdatePage.getCreatedInput()).to.contain('2001-01-01T02:30', 'Expected created value to be equals to 2000-12-31');
    expect(await avisoTipoUpdatePage.getUpdatedInput()).to.contain('2001-01-01T02:30', 'Expected updated value to be equals to 2000-12-31');

    await avisoTipoUpdatePage.save();
    expect(await avisoTipoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await avisoTipoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last AvisoTipo', async () => {
    const nbButtonsBeforeDelete = await avisoTipoComponentsPage.countDeleteButtons();
    await avisoTipoComponentsPage.clickOnLastDeleteButton();

    avisoTipoDeleteDialog = new AvisoTipoDeleteDialog();
    expect(await avisoTipoDeleteDialog.getDialogTitle()).to.eq('nucleoApp.avisoTipo.delete.question');
    await avisoTipoDeleteDialog.clickOnConfirmButton();

    expect(await avisoTipoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
