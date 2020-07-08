import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { RecursoTipoComponentsPage, RecursoTipoDeleteDialog, RecursoTipoUpdatePage } from './recurso-tipo.page-object';

const expect = chai.expect;

describe('RecursoTipo e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let recursoTipoComponentsPage: RecursoTipoComponentsPage;
  let recursoTipoUpdatePage: RecursoTipoUpdatePage;
  let recursoTipoDeleteDialog: RecursoTipoDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load RecursoTipos', async () => {
    await navBarPage.goToEntity('recurso-tipo');
    recursoTipoComponentsPage = new RecursoTipoComponentsPage();
    await browser.wait(ec.visibilityOf(recursoTipoComponentsPage.title), 5000);
    expect(await recursoTipoComponentsPage.getTitle()).to.eq('nucleoApp.recursoTipo.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(recursoTipoComponentsPage.entities), ec.visibilityOf(recursoTipoComponentsPage.noResult)),
      1000
    );
  });

  it('should load create RecursoTipo page', async () => {
    await recursoTipoComponentsPage.clickOnCreateButton();
    recursoTipoUpdatePage = new RecursoTipoUpdatePage();
    expect(await recursoTipoUpdatePage.getPageTitle()).to.eq('nucleoApp.recursoTipo.home.createOrEditLabel');
    await recursoTipoUpdatePage.cancel();
  });

  it('should create and save RecursoTipos', async () => {
    const nbButtonsBeforeCreate = await recursoTipoComponentsPage.countDeleteButtons();

    await recursoTipoComponentsPage.clickOnCreateButton();

    await promise.all([
      recursoTipoUpdatePage.setNameInput('name'),
      recursoTipoUpdatePage.setDescricaoInput('descricao'),
      recursoTipoUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      recursoTipoUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
    ]);

    expect(await recursoTipoUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await recursoTipoUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    expect(await recursoTipoUpdatePage.getCreatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected created value to be equals to 2000-12-31'
    );
    expect(await recursoTipoUpdatePage.getUpdatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected updated value to be equals to 2000-12-31'
    );

    await recursoTipoUpdatePage.save();
    expect(await recursoTipoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await recursoTipoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last RecursoTipo', async () => {
    const nbButtonsBeforeDelete = await recursoTipoComponentsPage.countDeleteButtons();
    await recursoTipoComponentsPage.clickOnLastDeleteButton();

    recursoTipoDeleteDialog = new RecursoTipoDeleteDialog();
    expect(await recursoTipoDeleteDialog.getDialogTitle()).to.eq('nucleoApp.recursoTipo.delete.question');
    await recursoTipoDeleteDialog.clickOnConfirmButton();

    expect(await recursoTipoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
