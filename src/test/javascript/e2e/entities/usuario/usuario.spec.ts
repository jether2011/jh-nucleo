import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { UsuarioComponentsPage, UsuarioDeleteDialog, UsuarioUpdatePage } from './usuario.page-object';

const expect = chai.expect;

describe('Usuario e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let usuarioComponentsPage: UsuarioComponentsPage;
  let usuarioUpdatePage: UsuarioUpdatePage;
  let usuarioDeleteDialog: UsuarioDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Usuarios', async () => {
    await navBarPage.goToEntity('usuario');
    usuarioComponentsPage = new UsuarioComponentsPage();
    await browser.wait(ec.visibilityOf(usuarioComponentsPage.title), 5000);
    expect(await usuarioComponentsPage.getTitle()).to.eq('nucleoApp.usuario.home.title');
    await browser.wait(ec.or(ec.visibilityOf(usuarioComponentsPage.entities), ec.visibilityOf(usuarioComponentsPage.noResult)), 1000);
  });

  it('should load create Usuario page', async () => {
    await usuarioComponentsPage.clickOnCreateButton();
    usuarioUpdatePage = new UsuarioUpdatePage();
    expect(await usuarioUpdatePage.getPageTitle()).to.eq('nucleoApp.usuario.home.createOrEditLabel');
    await usuarioUpdatePage.cancel();
  });

  it('should create and save Usuarios', async () => {
    const nbButtonsBeforeCreate = await usuarioComponentsPage.countDeleteButtons();

    await usuarioComponentsPage.clickOnCreateButton();

    await promise.all([
      usuarioUpdatePage.setNameInput('name'),
      usuarioUpdatePage.setDescricaoInput('descricao'),
      usuarioUpdatePage.setEmailInput('email'),
      usuarioUpdatePage.setSenhaInput('senha'),
      usuarioUpdatePage.setCnpjInput('cnpj'),
      usuarioUpdatePage.setCpfInput('cpf'),
      usuarioUpdatePage.setCepInput('cep'),
      usuarioUpdatePage.setEnderecoInput('endereco'),
      usuarioUpdatePage.setNumeroInput('5'),
      usuarioUpdatePage.setBairroInput('bairro'),
      usuarioUpdatePage.setCidadeInput('cidade'),
      usuarioUpdatePage.setEstadoInput('estado'),
      usuarioUpdatePage.setTelefoneInput('telefone'),
      usuarioUpdatePage.setFaxInput('fax'),
      usuarioUpdatePage.setCelularInput('celular'),
      usuarioUpdatePage.setDetalheInput('detalhe'),
      usuarioUpdatePage.setComplementoInput('complemento'),
      usuarioUpdatePage.setUltimoAcessoInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      usuarioUpdatePage.setSenhaFirebaseInput('senhaFirebase'),
      usuarioUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      usuarioUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
    ]);

    expect(await usuarioUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await usuarioUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    expect(await usuarioUpdatePage.getEmailInput()).to.eq('email', 'Expected Email value to be equals to email');
    expect(await usuarioUpdatePage.getSenhaInput()).to.eq('senha', 'Expected Senha value to be equals to senha');
    expect(await usuarioUpdatePage.getCnpjInput()).to.eq('cnpj', 'Expected Cnpj value to be equals to cnpj');
    expect(await usuarioUpdatePage.getCpfInput()).to.eq('cpf', 'Expected Cpf value to be equals to cpf');
    expect(await usuarioUpdatePage.getCepInput()).to.eq('cep', 'Expected Cep value to be equals to cep');
    expect(await usuarioUpdatePage.getEnderecoInput()).to.eq('endereco', 'Expected Endereco value to be equals to endereco');
    expect(await usuarioUpdatePage.getNumeroInput()).to.eq('5', 'Expected numero value to be equals to 5');
    expect(await usuarioUpdatePage.getBairroInput()).to.eq('bairro', 'Expected Bairro value to be equals to bairro');
    expect(await usuarioUpdatePage.getCidadeInput()).to.eq('cidade', 'Expected Cidade value to be equals to cidade');
    expect(await usuarioUpdatePage.getEstadoInput()).to.eq('estado', 'Expected Estado value to be equals to estado');
    expect(await usuarioUpdatePage.getTelefoneInput()).to.eq('telefone', 'Expected Telefone value to be equals to telefone');
    expect(await usuarioUpdatePage.getFaxInput()).to.eq('fax', 'Expected Fax value to be equals to fax');
    expect(await usuarioUpdatePage.getCelularInput()).to.eq('celular', 'Expected Celular value to be equals to celular');
    expect(await usuarioUpdatePage.getDetalheInput()).to.eq('detalhe', 'Expected Detalhe value to be equals to detalhe');
    const selectedBloqueado = usuarioUpdatePage.getBloqueadoInput();
    if (await selectedBloqueado.isSelected()) {
      await usuarioUpdatePage.getBloqueadoInput().click();
      expect(await usuarioUpdatePage.getBloqueadoInput().isSelected(), 'Expected bloqueado not to be selected').to.be.false;
    } else {
      await usuarioUpdatePage.getBloqueadoInput().click();
      expect(await usuarioUpdatePage.getBloqueadoInput().isSelected(), 'Expected bloqueado to be selected').to.be.true;
    }
    expect(await usuarioUpdatePage.getComplementoInput()).to.eq('complemento', 'Expected Complemento value to be equals to complemento');
    const selectedNaoPodeExcluir = usuarioUpdatePage.getNaoPodeExcluirInput();
    if (await selectedNaoPodeExcluir.isSelected()) {
      await usuarioUpdatePage.getNaoPodeExcluirInput().click();
      expect(await usuarioUpdatePage.getNaoPodeExcluirInput().isSelected(), 'Expected naoPodeExcluir not to be selected').to.be.false;
    } else {
      await usuarioUpdatePage.getNaoPodeExcluirInput().click();
      expect(await usuarioUpdatePage.getNaoPodeExcluirInput().isSelected(), 'Expected naoPodeExcluir to be selected').to.be.true;
    }
    expect(await usuarioUpdatePage.getUltimoAcessoInput()).to.contain(
      '2001-01-01T02:30',
      'Expected ultimoAcesso value to be equals to 2000-12-31'
    );
    expect(await usuarioUpdatePage.getSenhaFirebaseInput()).to.eq(
      'senhaFirebase',
      'Expected SenhaFirebase value to be equals to senhaFirebase'
    );
    expect(await usuarioUpdatePage.getCreatedInput()).to.contain('2001-01-01T02:30', 'Expected created value to be equals to 2000-12-31');
    expect(await usuarioUpdatePage.getUpdatedInput()).to.contain('2001-01-01T02:30', 'Expected updated value to be equals to 2000-12-31');

    await usuarioUpdatePage.save();
    expect(await usuarioUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await usuarioComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Usuario', async () => {
    const nbButtonsBeforeDelete = await usuarioComponentsPage.countDeleteButtons();
    await usuarioComponentsPage.clickOnLastDeleteButton();

    usuarioDeleteDialog = new UsuarioDeleteDialog();
    expect(await usuarioDeleteDialog.getDialogTitle()).to.eq('nucleoApp.usuario.delete.question');
    await usuarioDeleteDialog.clickOnConfirmButton();

    expect(await usuarioComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
