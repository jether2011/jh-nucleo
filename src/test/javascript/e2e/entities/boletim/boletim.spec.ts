import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { BoletimComponentsPage, BoletimDeleteDialog, BoletimUpdatePage } from './boletim.page-object';

const expect = chai.expect;

describe('Boletim e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let boletimComponentsPage: BoletimComponentsPage;
  let boletimUpdatePage: BoletimUpdatePage;
  let boletimDeleteDialog: BoletimDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Boletims', async () => {
    await navBarPage.goToEntity('boletim');
    boletimComponentsPage = new BoletimComponentsPage();
    await browser.wait(ec.visibilityOf(boletimComponentsPage.title), 5000);
    expect(await boletimComponentsPage.getTitle()).to.eq('nucleoApp.boletim.home.title');
    await browser.wait(ec.or(ec.visibilityOf(boletimComponentsPage.entities), ec.visibilityOf(boletimComponentsPage.noResult)), 1000);
  });

  it('should load create Boletim page', async () => {
    await boletimComponentsPage.clickOnCreateButton();
    boletimUpdatePage = new BoletimUpdatePage();
    expect(await boletimUpdatePage.getPageTitle()).to.eq('nucleoApp.boletim.home.createOrEditLabel');
    await boletimUpdatePage.cancel();
  });

  it('should create and save Boletims', async () => {
    const nbButtonsBeforeCreate = await boletimComponentsPage.countDeleteButtons();

    await boletimComponentsPage.clickOnCreateButton();

    await promise.all([
      boletimUpdatePage.setNomeInput('nome'),
      boletimUpdatePage.setDescricaoInput('descricao'),
      boletimUpdatePage.setTextoInput('texto'),
      boletimUpdatePage.setTextoSmsInput('textoSms'),
      boletimUpdatePage.setImagemInput('imagem'),
      boletimUpdatePage.setAssuntoInput('assunto'),
      boletimUpdatePage.setTextoParte2Input('textoParte2'),
      boletimUpdatePage.setTextoParte3Input('textoParte3'),
      boletimUpdatePage.setSubAssuntoInput('subAssunto'),
      boletimUpdatePage.setNaoExibirPagEmpresaInput('5'),
      boletimUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      boletimUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      boletimUpdatePage.planoRecursoSelectLastOption(),
    ]);

    expect(await boletimUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await boletimUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    expect(await boletimUpdatePage.getTextoInput()).to.eq('texto', 'Expected Texto value to be equals to texto');
    expect(await boletimUpdatePage.getTextoSmsInput()).to.eq('textoSms', 'Expected TextoSms value to be equals to textoSms');
    expect(await boletimUpdatePage.getImagemInput()).to.eq('imagem', 'Expected Imagem value to be equals to imagem');
    expect(await boletimUpdatePage.getAssuntoInput()).to.eq('assunto', 'Expected Assunto value to be equals to assunto');
    expect(await boletimUpdatePage.getTextoParte2Input()).to.eq('textoParte2', 'Expected TextoParte2 value to be equals to textoParte2');
    expect(await boletimUpdatePage.getTextoParte3Input()).to.eq('textoParte3', 'Expected TextoParte3 value to be equals to textoParte3');
    expect(await boletimUpdatePage.getSubAssuntoInput()).to.eq('subAssunto', 'Expected SubAssunto value to be equals to subAssunto');
    expect(await boletimUpdatePage.getNaoExibirPagEmpresaInput()).to.eq('5', 'Expected naoExibirPagEmpresa value to be equals to 5');
    const selectedCritico = boletimUpdatePage.getCriticoInput();
    if (await selectedCritico.isSelected()) {
      await boletimUpdatePage.getCriticoInput().click();
      expect(await boletimUpdatePage.getCriticoInput().isSelected(), 'Expected critico not to be selected').to.be.false;
    } else {
      await boletimUpdatePage.getCriticoInput().click();
      expect(await boletimUpdatePage.getCriticoInput().isSelected(), 'Expected critico to be selected').to.be.true;
    }
    const selectedAprovado = boletimUpdatePage.getAprovadoInput();
    if (await selectedAprovado.isSelected()) {
      await boletimUpdatePage.getAprovadoInput().click();
      expect(await boletimUpdatePage.getAprovadoInput().isSelected(), 'Expected aprovado not to be selected').to.be.false;
    } else {
      await boletimUpdatePage.getAprovadoInput().click();
      expect(await boletimUpdatePage.getAprovadoInput().isSelected(), 'Expected aprovado to be selected').to.be.true;
    }
    const selectedEnviarSms = boletimUpdatePage.getEnviarSmsInput();
    if (await selectedEnviarSms.isSelected()) {
      await boletimUpdatePage.getEnviarSmsInput().click();
      expect(await boletimUpdatePage.getEnviarSmsInput().isSelected(), 'Expected enviarSms not to be selected').to.be.false;
    } else {
      await boletimUpdatePage.getEnviarSmsInput().click();
      expect(await boletimUpdatePage.getEnviarSmsInput().isSelected(), 'Expected enviarSms to be selected').to.be.true;
    }
    const selectedEnviarEmail = boletimUpdatePage.getEnviarEmailInput();
    if (await selectedEnviarEmail.isSelected()) {
      await boletimUpdatePage.getEnviarEmailInput().click();
      expect(await boletimUpdatePage.getEnviarEmailInput().isSelected(), 'Expected enviarEmail not to be selected').to.be.false;
    } else {
      await boletimUpdatePage.getEnviarEmailInput().click();
      expect(await boletimUpdatePage.getEnviarEmailInput().isSelected(), 'Expected enviarEmail to be selected').to.be.true;
    }
    expect(await boletimUpdatePage.getCreatedInput()).to.contain('2001-01-01T02:30', 'Expected created value to be equals to 2000-12-31');
    expect(await boletimUpdatePage.getUpdatedInput()).to.contain('2001-01-01T02:30', 'Expected updated value to be equals to 2000-12-31');

    await boletimUpdatePage.save();
    expect(await boletimUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await boletimComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Boletim', async () => {
    const nbButtonsBeforeDelete = await boletimComponentsPage.countDeleteButtons();
    await boletimComponentsPage.clickOnLastDeleteButton();

    boletimDeleteDialog = new BoletimDeleteDialog();
    expect(await boletimDeleteDialog.getDialogTitle()).to.eq('nucleoApp.boletim.delete.question');
    await boletimDeleteDialog.clickOnConfirmButton();

    expect(await boletimComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
