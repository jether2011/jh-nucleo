import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { NoticiaComponentsPage, NoticiaDeleteDialog, NoticiaUpdatePage } from './noticia.page-object';

const expect = chai.expect;

describe('Noticia e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let noticiaComponentsPage: NoticiaComponentsPage;
  let noticiaUpdatePage: NoticiaUpdatePage;
  let noticiaDeleteDialog: NoticiaDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Noticias', async () => {
    await navBarPage.goToEntity('noticia');
    noticiaComponentsPage = new NoticiaComponentsPage();
    await browser.wait(ec.visibilityOf(noticiaComponentsPage.title), 5000);
    expect(await noticiaComponentsPage.getTitle()).to.eq('nucleoApp.noticia.home.title');
    await browser.wait(ec.or(ec.visibilityOf(noticiaComponentsPage.entities), ec.visibilityOf(noticiaComponentsPage.noResult)), 1000);
  });

  it('should load create Noticia page', async () => {
    await noticiaComponentsPage.clickOnCreateButton();
    noticiaUpdatePage = new NoticiaUpdatePage();
    expect(await noticiaUpdatePage.getPageTitle()).to.eq('nucleoApp.noticia.home.createOrEditLabel');
    await noticiaUpdatePage.cancel();
  });

  it('should create and save Noticias', async () => {
    const nbButtonsBeforeCreate = await noticiaComponentsPage.countDeleteButtons();

    await noticiaComponentsPage.clickOnCreateButton();

    await promise.all([
      noticiaUpdatePage.setNameInput('name'),
      noticiaUpdatePage.setTextoInput('texto'),
      noticiaUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      noticiaUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
    ]);

    expect(await noticiaUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await noticiaUpdatePage.getTextoInput()).to.eq('texto', 'Expected Texto value to be equals to texto');
    const selectedEnviado = noticiaUpdatePage.getEnviadoInput();
    if (await selectedEnviado.isSelected()) {
      await noticiaUpdatePage.getEnviadoInput().click();
      expect(await noticiaUpdatePage.getEnviadoInput().isSelected(), 'Expected enviado not to be selected').to.be.false;
    } else {
      await noticiaUpdatePage.getEnviadoInput().click();
      expect(await noticiaUpdatePage.getEnviadoInput().isSelected(), 'Expected enviado to be selected').to.be.true;
    }
    expect(await noticiaUpdatePage.getCreatedInput()).to.contain('2001-01-01T02:30', 'Expected created value to be equals to 2000-12-31');
    expect(await noticiaUpdatePage.getUpdatedInput()).to.contain('2001-01-01T02:30', 'Expected updated value to be equals to 2000-12-31');

    await noticiaUpdatePage.save();
    expect(await noticiaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await noticiaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Noticia', async () => {
    const nbButtonsBeforeDelete = await noticiaComponentsPage.countDeleteButtons();
    await noticiaComponentsPage.clickOnLastDeleteButton();

    noticiaDeleteDialog = new NoticiaDeleteDialog();
    expect(await noticiaDeleteDialog.getDialogTitle()).to.eq('nucleoApp.noticia.delete.question');
    await noticiaDeleteDialog.clickOnConfirmButton();

    expect(await noticiaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
