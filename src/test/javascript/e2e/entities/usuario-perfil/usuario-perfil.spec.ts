import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  UsuarioPerfilComponentsPage,
  /* UsuarioPerfilDeleteDialog, */
  UsuarioPerfilUpdatePage,
} from './usuario-perfil.page-object';

const expect = chai.expect;

describe('UsuarioPerfil e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let usuarioPerfilComponentsPage: UsuarioPerfilComponentsPage;
  let usuarioPerfilUpdatePage: UsuarioPerfilUpdatePage;
  /* let usuarioPerfilDeleteDialog: UsuarioPerfilDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load UsuarioPerfils', async () => {
    await navBarPage.goToEntity('usuario-perfil');
    usuarioPerfilComponentsPage = new UsuarioPerfilComponentsPage();
    await browser.wait(ec.visibilityOf(usuarioPerfilComponentsPage.title), 5000);
    expect(await usuarioPerfilComponentsPage.getTitle()).to.eq('nucleoApp.usuarioPerfil.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(usuarioPerfilComponentsPage.entities), ec.visibilityOf(usuarioPerfilComponentsPage.noResult)),
      1000
    );
  });

  it('should load create UsuarioPerfil page', async () => {
    await usuarioPerfilComponentsPage.clickOnCreateButton();
    usuarioPerfilUpdatePage = new UsuarioPerfilUpdatePage();
    expect(await usuarioPerfilUpdatePage.getPageTitle()).to.eq('nucleoApp.usuarioPerfil.home.createOrEditLabel');
    await usuarioPerfilUpdatePage.cancel();
  });

  /* it('should create and save UsuarioPerfils', async () => {
        const nbButtonsBeforeCreate = await usuarioPerfilComponentsPage.countDeleteButtons();

        await usuarioPerfilComponentsPage.clickOnCreateButton();

        await promise.all([
            usuarioPerfilUpdatePage.setNameInput('name'),
            usuarioPerfilUpdatePage.setDescricaoInput('descricao'),
            usuarioPerfilUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            usuarioPerfilUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            usuarioPerfilUpdatePage.usuarioSelectLastOption(),
            usuarioPerfilUpdatePage.perfilSelectLastOption(),
        ]);

        expect(await usuarioPerfilUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
        expect(await usuarioPerfilUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
        expect(await usuarioPerfilUpdatePage.getCreatedInput()).to.contain('2001-01-01T02:30', 'Expected created value to be equals to 2000-12-31');
        expect(await usuarioPerfilUpdatePage.getUpdatedInput()).to.contain('2001-01-01T02:30', 'Expected updated value to be equals to 2000-12-31');

        await usuarioPerfilUpdatePage.save();
        expect(await usuarioPerfilUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await usuarioPerfilComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last UsuarioPerfil', async () => {
        const nbButtonsBeforeDelete = await usuarioPerfilComponentsPage.countDeleteButtons();
        await usuarioPerfilComponentsPage.clickOnLastDeleteButton();

        usuarioPerfilDeleteDialog = new UsuarioPerfilDeleteDialog();
        expect(await usuarioPerfilDeleteDialog.getDialogTitle())
            .to.eq('nucleoApp.usuarioPerfil.delete.question');
        await usuarioPerfilDeleteDialog.clickOnConfirmButton();

        expect(await usuarioPerfilComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
