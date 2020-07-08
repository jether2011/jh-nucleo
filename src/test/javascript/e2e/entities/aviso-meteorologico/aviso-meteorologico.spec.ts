import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  AvisoMeteorologicoComponentsPage,
  AvisoMeteorologicoDeleteDialog,
  AvisoMeteorologicoUpdatePage,
} from './aviso-meteorologico.page-object';

const expect = chai.expect;

describe('AvisoMeteorologico e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let avisoMeteorologicoComponentsPage: AvisoMeteorologicoComponentsPage;
  let avisoMeteorologicoUpdatePage: AvisoMeteorologicoUpdatePage;
  let avisoMeteorologicoDeleteDialog: AvisoMeteorologicoDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load AvisoMeteorologicos', async () => {
    await navBarPage.goToEntity('aviso-meteorologico');
    avisoMeteorologicoComponentsPage = new AvisoMeteorologicoComponentsPage();
    await browser.wait(ec.visibilityOf(avisoMeteorologicoComponentsPage.title), 5000);
    expect(await avisoMeteorologicoComponentsPage.getTitle()).to.eq('nucleoApp.avisoMeteorologico.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(avisoMeteorologicoComponentsPage.entities), ec.visibilityOf(avisoMeteorologicoComponentsPage.noResult)),
      1000
    );
  });

  it('should load create AvisoMeteorologico page', async () => {
    await avisoMeteorologicoComponentsPage.clickOnCreateButton();
    avisoMeteorologicoUpdatePage = new AvisoMeteorologicoUpdatePage();
    expect(await avisoMeteorologicoUpdatePage.getPageTitle()).to.eq('nucleoApp.avisoMeteorologico.home.createOrEditLabel');
    await avisoMeteorologicoUpdatePage.cancel();
  });

  it('should create and save AvisoMeteorologicos', async () => {
    const nbButtonsBeforeCreate = await avisoMeteorologicoComponentsPage.countDeleteButtons();

    await avisoMeteorologicoComponentsPage.clickOnCreateButton();

    await promise.all([
      avisoMeteorologicoUpdatePage.setNomeInput('nome'),
      avisoMeteorologicoUpdatePage.setAssuntoInput('assunto'),
      avisoMeteorologicoUpdatePage.setInicioInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      avisoMeteorologicoUpdatePage.setFimInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      avisoMeteorologicoUpdatePage.setTextoInput('texto'),
      avisoMeteorologicoUpdatePage.setImagemInput('imagem'),
      avisoMeteorologicoUpdatePage.setImagemAssinaturaInput('imagemAssinatura'),
      avisoMeteorologicoUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      avisoMeteorologicoUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      avisoMeteorologicoUpdatePage.planoRecursoSelectLastOption(),
    ]);

    expect(await avisoMeteorologicoUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await avisoMeteorologicoUpdatePage.getAssuntoInput()).to.eq('assunto', 'Expected Assunto value to be equals to assunto');
    expect(await avisoMeteorologicoUpdatePage.getInicioInput()).to.contain(
      '2001-01-01T02:30',
      'Expected inicio value to be equals to 2000-12-31'
    );
    expect(await avisoMeteorologicoUpdatePage.getFimInput()).to.contain(
      '2001-01-01T02:30',
      'Expected fim value to be equals to 2000-12-31'
    );
    expect(await avisoMeteorologicoUpdatePage.getTextoInput()).to.eq('texto', 'Expected Texto value to be equals to texto');
    expect(await avisoMeteorologicoUpdatePage.getImagemInput()).to.eq('imagem', 'Expected Imagem value to be equals to imagem');
    expect(await avisoMeteorologicoUpdatePage.getImagemAssinaturaInput()).to.eq(
      'imagemAssinatura',
      'Expected ImagemAssinatura value to be equals to imagemAssinatura'
    );
    expect(await avisoMeteorologicoUpdatePage.getCreatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected created value to be equals to 2000-12-31'
    );
    expect(await avisoMeteorologicoUpdatePage.getUpdatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected updated value to be equals to 2000-12-31'
    );

    await avisoMeteorologicoUpdatePage.save();
    expect(await avisoMeteorologicoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await avisoMeteorologicoComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last AvisoMeteorologico', async () => {
    const nbButtonsBeforeDelete = await avisoMeteorologicoComponentsPage.countDeleteButtons();
    await avisoMeteorologicoComponentsPage.clickOnLastDeleteButton();

    avisoMeteorologicoDeleteDialog = new AvisoMeteorologicoDeleteDialog();
    expect(await avisoMeteorologicoDeleteDialog.getDialogTitle()).to.eq('nucleoApp.avisoMeteorologico.delete.question');
    await avisoMeteorologicoDeleteDialog.clickOnConfirmButton();

    expect(await avisoMeteorologicoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
