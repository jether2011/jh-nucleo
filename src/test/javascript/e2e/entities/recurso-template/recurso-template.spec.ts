import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { RecursoTemplateComponentsPage, RecursoTemplateDeleteDialog, RecursoTemplateUpdatePage } from './recurso-template.page-object';

const expect = chai.expect;

describe('RecursoTemplate e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let recursoTemplateComponentsPage: RecursoTemplateComponentsPage;
  let recursoTemplateUpdatePage: RecursoTemplateUpdatePage;
  let recursoTemplateDeleteDialog: RecursoTemplateDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load RecursoTemplates', async () => {
    await navBarPage.goToEntity('recurso-template');
    recursoTemplateComponentsPage = new RecursoTemplateComponentsPage();
    await browser.wait(ec.visibilityOf(recursoTemplateComponentsPage.title), 5000);
    expect(await recursoTemplateComponentsPage.getTitle()).to.eq('nucleoApp.recursoTemplate.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(recursoTemplateComponentsPage.entities), ec.visibilityOf(recursoTemplateComponentsPage.noResult)),
      1000
    );
  });

  it('should load create RecursoTemplate page', async () => {
    await recursoTemplateComponentsPage.clickOnCreateButton();
    recursoTemplateUpdatePage = new RecursoTemplateUpdatePage();
    expect(await recursoTemplateUpdatePage.getPageTitle()).to.eq('nucleoApp.recursoTemplate.home.createOrEditLabel');
    await recursoTemplateUpdatePage.cancel();
  });

  it('should create and save RecursoTemplates', async () => {
    const nbButtonsBeforeCreate = await recursoTemplateComponentsPage.countDeleteButtons();

    await recursoTemplateComponentsPage.clickOnCreateButton();

    await promise.all([
      recursoTemplateUpdatePage.setNameInput('name'),
      recursoTemplateUpdatePage.setDescricaoInput('descricao'),
      recursoTemplateUpdatePage.setTemplateInput('template'),
      recursoTemplateUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      recursoTemplateUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      recursoTemplateUpdatePage.recursoSelectLastOption(),
      recursoTemplateUpdatePage.tipoEnvioSelectLastOption(),
      recursoTemplateUpdatePage.alertaTipoSelectLastOption(),
    ]);

    expect(await recursoTemplateUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await recursoTemplateUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    expect(await recursoTemplateUpdatePage.getTemplateInput()).to.eq('template', 'Expected Template value to be equals to template');
    expect(await recursoTemplateUpdatePage.getCreatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected created value to be equals to 2000-12-31'
    );
    expect(await recursoTemplateUpdatePage.getUpdatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected updated value to be equals to 2000-12-31'
    );

    await recursoTemplateUpdatePage.save();
    expect(await recursoTemplateUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await recursoTemplateComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last RecursoTemplate', async () => {
    const nbButtonsBeforeDelete = await recursoTemplateComponentsPage.countDeleteButtons();
    await recursoTemplateComponentsPage.clickOnLastDeleteButton();

    recursoTemplateDeleteDialog = new RecursoTemplateDeleteDialog();
    expect(await recursoTemplateDeleteDialog.getDialogTitle()).to.eq('nucleoApp.recursoTemplate.delete.question');
    await recursoTemplateDeleteDialog.clickOnConfirmButton();

    expect(await recursoTemplateComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
