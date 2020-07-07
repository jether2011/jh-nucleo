import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PlanoUsuarioComponentsPage, PlanoUsuarioDeleteDialog, PlanoUsuarioUpdatePage } from './plano-usuario.page-object';

const expect = chai.expect;

describe('PlanoUsuario e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let planoUsuarioComponentsPage: PlanoUsuarioComponentsPage;
  let planoUsuarioUpdatePage: PlanoUsuarioUpdatePage;
  let planoUsuarioDeleteDialog: PlanoUsuarioDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PlanoUsuarios', async () => {
    await navBarPage.goToEntity('plano-usuario');
    planoUsuarioComponentsPage = new PlanoUsuarioComponentsPage();
    await browser.wait(ec.visibilityOf(planoUsuarioComponentsPage.title), 5000);
    expect(await planoUsuarioComponentsPage.getTitle()).to.eq('nucleoApp.planoUsuario.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(planoUsuarioComponentsPage.entities), ec.visibilityOf(planoUsuarioComponentsPage.noResult)),
      1000
    );
  });

  it('should load create PlanoUsuario page', async () => {
    await planoUsuarioComponentsPage.clickOnCreateButton();
    planoUsuarioUpdatePage = new PlanoUsuarioUpdatePage();
    expect(await planoUsuarioUpdatePage.getPageTitle()).to.eq('nucleoApp.planoUsuario.home.createOrEditLabel');
    await planoUsuarioUpdatePage.cancel();
  });

  it('should create and save PlanoUsuarios', async () => {
    const nbButtonsBeforeCreate = await planoUsuarioComponentsPage.countDeleteButtons();

    await planoUsuarioComponentsPage.clickOnCreateButton();

    await promise.all([
      planoUsuarioUpdatePage.setNameInput('name'),
      planoUsuarioUpdatePage.setDescricaoInput('descricao'),
      planoUsuarioUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      planoUsuarioUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      planoUsuarioUpdatePage.planoSelectLastOption(),
      planoUsuarioUpdatePage.usuarioSelectLastOption(),
    ]);

    expect(await planoUsuarioUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await planoUsuarioUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    expect(await planoUsuarioUpdatePage.getCreatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected created value to be equals to 2000-12-31'
    );
    expect(await planoUsuarioUpdatePage.getUpdatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected updated value to be equals to 2000-12-31'
    );

    await planoUsuarioUpdatePage.save();
    expect(await planoUsuarioUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await planoUsuarioComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last PlanoUsuario', async () => {
    const nbButtonsBeforeDelete = await planoUsuarioComponentsPage.countDeleteButtons();
    await planoUsuarioComponentsPage.clickOnLastDeleteButton();

    planoUsuarioDeleteDialog = new PlanoUsuarioDeleteDialog();
    expect(await planoUsuarioDeleteDialog.getDialogTitle()).to.eq('nucleoApp.planoUsuario.delete.question');
    await planoUsuarioDeleteDialog.clickOnConfirmButton();

    expect(await planoUsuarioComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
