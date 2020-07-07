import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { EmpresaComponentsPage, EmpresaDeleteDialog, EmpresaUpdatePage } from './empresa.page-object';

const expect = chai.expect;

describe('Empresa e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let empresaComponentsPage: EmpresaComponentsPage;
  let empresaUpdatePage: EmpresaUpdatePage;
  let empresaDeleteDialog: EmpresaDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Empresas', async () => {
    await navBarPage.goToEntity('empresa');
    empresaComponentsPage = new EmpresaComponentsPage();
    await browser.wait(ec.visibilityOf(empresaComponentsPage.title), 5000);
    expect(await empresaComponentsPage.getTitle()).to.eq('nucleoApp.empresa.home.title');
    await browser.wait(ec.or(ec.visibilityOf(empresaComponentsPage.entities), ec.visibilityOf(empresaComponentsPage.noResult)), 1000);
  });

  it('should load create Empresa page', async () => {
    await empresaComponentsPage.clickOnCreateButton();
    empresaUpdatePage = new EmpresaUpdatePage();
    expect(await empresaUpdatePage.getPageTitle()).to.eq('nucleoApp.empresa.home.createOrEditLabel');
    await empresaUpdatePage.cancel();
  });

  it('should create and save Empresas', async () => {
    const nbButtonsBeforeCreate = await empresaComponentsPage.countDeleteButtons();

    await empresaComponentsPage.clickOnCreateButton();

    await promise.all([
      empresaUpdatePage.setNomeInput('nome'),
      empresaUpdatePage.setDescricaoInput('descricao'),
      empresaUpdatePage.setEmailInput('email'),
      empresaUpdatePage.setTituloInput('titulo'),
      empresaUpdatePage.setNomeReduzidoInput('nomeReduzido'),
      empresaUpdatePage.setLogoInput('logo'),
      empresaUpdatePage.setApelidoInput('apelido'),
      empresaUpdatePage.setObservacaoInput('observacao'),
      empresaUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      empresaUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
    ]);

    expect(await empresaUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await empresaUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    expect(await empresaUpdatePage.getEmailInput()).to.eq('email', 'Expected Email value to be equals to email');
    expect(await empresaUpdatePage.getTituloInput()).to.eq('titulo', 'Expected Titulo value to be equals to titulo');
    expect(await empresaUpdatePage.getNomeReduzidoInput()).to.eq(
      'nomeReduzido',
      'Expected NomeReduzido value to be equals to nomeReduzido'
    );
    expect(await empresaUpdatePage.getLogoInput()).to.eq('logo', 'Expected Logo value to be equals to logo');
    expect(await empresaUpdatePage.getApelidoInput()).to.eq('apelido', 'Expected Apelido value to be equals to apelido');
    expect(await empresaUpdatePage.getObservacaoInput()).to.eq('observacao', 'Expected Observacao value to be equals to observacao');
    expect(await empresaUpdatePage.getCreatedInput()).to.contain('2001-01-01T02:30', 'Expected created value to be equals to 2000-12-31');
    expect(await empresaUpdatePage.getUpdatedInput()).to.contain('2001-01-01T02:30', 'Expected updated value to be equals to 2000-12-31');

    await empresaUpdatePage.save();
    expect(await empresaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await empresaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Empresa', async () => {
    const nbButtonsBeforeDelete = await empresaComponentsPage.countDeleteButtons();
    await empresaComponentsPage.clickOnLastDeleteButton();

    empresaDeleteDialog = new EmpresaDeleteDialog();
    expect(await empresaDeleteDialog.getDialogTitle()).to.eq('nucleoApp.empresa.delete.question');
    await empresaDeleteDialog.clickOnConfirmButton();

    expect(await empresaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
