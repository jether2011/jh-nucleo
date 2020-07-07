import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DiaSemanaComponentsPage, DiaSemanaDeleteDialog, DiaSemanaUpdatePage } from './dia-semana.page-object';

const expect = chai.expect;

describe('DiaSemana e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let diaSemanaComponentsPage: DiaSemanaComponentsPage;
  let diaSemanaUpdatePage: DiaSemanaUpdatePage;
  let diaSemanaDeleteDialog: DiaSemanaDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load DiaSemanas', async () => {
    await navBarPage.goToEntity('dia-semana');
    diaSemanaComponentsPage = new DiaSemanaComponentsPage();
    await browser.wait(ec.visibilityOf(diaSemanaComponentsPage.title), 5000);
    expect(await diaSemanaComponentsPage.getTitle()).to.eq('nucleoApp.diaSemana.home.title');
    await browser.wait(ec.or(ec.visibilityOf(diaSemanaComponentsPage.entities), ec.visibilityOf(diaSemanaComponentsPage.noResult)), 1000);
  });

  it('should load create DiaSemana page', async () => {
    await diaSemanaComponentsPage.clickOnCreateButton();
    diaSemanaUpdatePage = new DiaSemanaUpdatePage();
    expect(await diaSemanaUpdatePage.getPageTitle()).to.eq('nucleoApp.diaSemana.home.createOrEditLabel');
    await diaSemanaUpdatePage.cancel();
  });

  it('should create and save DiaSemanas', async () => {
    const nbButtonsBeforeCreate = await diaSemanaComponentsPage.countDeleteButtons();

    await diaSemanaComponentsPage.clickOnCreateButton();

    await promise.all([
      diaSemanaUpdatePage.setNomeInput('nome'),
      diaSemanaUpdatePage.setDescricaoInput('descricao'),
      diaSemanaUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      diaSemanaUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
    ]);

    expect(await diaSemanaUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await diaSemanaUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    expect(await diaSemanaUpdatePage.getCreatedInput()).to.contain('2001-01-01T02:30', 'Expected created value to be equals to 2000-12-31');
    expect(await diaSemanaUpdatePage.getUpdatedInput()).to.contain('2001-01-01T02:30', 'Expected updated value to be equals to 2000-12-31');

    await diaSemanaUpdatePage.save();
    expect(await diaSemanaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await diaSemanaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last DiaSemana', async () => {
    const nbButtonsBeforeDelete = await diaSemanaComponentsPage.countDeleteButtons();
    await diaSemanaComponentsPage.clickOnLastDeleteButton();

    diaSemanaDeleteDialog = new DiaSemanaDeleteDialog();
    expect(await diaSemanaDeleteDialog.getDialogTitle()).to.eq('nucleoApp.diaSemana.delete.question');
    await diaSemanaDeleteDialog.clickOnConfirmButton();

    expect(await diaSemanaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
