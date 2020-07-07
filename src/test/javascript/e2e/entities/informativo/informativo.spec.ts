import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { InformativoComponentsPage, InformativoDeleteDialog, InformativoUpdatePage } from './informativo.page-object';

const expect = chai.expect;

describe('Informativo e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let informativoComponentsPage: InformativoComponentsPage;
  let informativoUpdatePage: InformativoUpdatePage;
  let informativoDeleteDialog: InformativoDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Informativos', async () => {
    await navBarPage.goToEntity('informativo');
    informativoComponentsPage = new InformativoComponentsPage();
    await browser.wait(ec.visibilityOf(informativoComponentsPage.title), 5000);
    expect(await informativoComponentsPage.getTitle()).to.eq('nucleoApp.informativo.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(informativoComponentsPage.entities), ec.visibilityOf(informativoComponentsPage.noResult)),
      1000
    );
  });

  it('should load create Informativo page', async () => {
    await informativoComponentsPage.clickOnCreateButton();
    informativoUpdatePage = new InformativoUpdatePage();
    expect(await informativoUpdatePage.getPageTitle()).to.eq('nucleoApp.informativo.home.createOrEditLabel');
    await informativoUpdatePage.cancel();
  });

  it('should create and save Informativos', async () => {
    const nbButtonsBeforeCreate = await informativoComponentsPage.countDeleteButtons();

    await informativoComponentsPage.clickOnCreateButton();

    await promise.all([
      informativoUpdatePage.setNomeInput('nome'),
      informativoUpdatePage.setDescricaoInput('descricao'),
      informativoUpdatePage.setTextoInput('texto'),
      informativoUpdatePage.setQtdEmailInput('5'),
      informativoUpdatePage.setImagemInput('imagem'),
      informativoUpdatePage.setArquivoEmlInput('arquivoEml'),
      informativoUpdatePage.setAssuntoInput('assunto'),
      informativoUpdatePage.setSubAssuntoInput('subAssunto'),
      informativoUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      informativoUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      informativoUpdatePage.planoRecursoSelectLastOption(),
    ]);

    expect(await informativoUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await informativoUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    expect(await informativoUpdatePage.getTextoInput()).to.eq('texto', 'Expected Texto value to be equals to texto');
    expect(await informativoUpdatePage.getQtdEmailInput()).to.eq('5', 'Expected qtdEmail value to be equals to 5');
    expect(await informativoUpdatePage.getImagemInput()).to.eq('imagem', 'Expected Imagem value to be equals to imagem');
    expect(await informativoUpdatePage.getArquivoEmlInput()).to.eq('arquivoEml', 'Expected ArquivoEml value to be equals to arquivoEml');
    expect(await informativoUpdatePage.getAssuntoInput()).to.eq('assunto', 'Expected Assunto value to be equals to assunto');
    expect(await informativoUpdatePage.getSubAssuntoInput()).to.eq('subAssunto', 'Expected SubAssunto value to be equals to subAssunto');
    expect(await informativoUpdatePage.getCreatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected created value to be equals to 2000-12-31'
    );
    expect(await informativoUpdatePage.getUpdatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected updated value to be equals to 2000-12-31'
    );

    await informativoUpdatePage.save();
    expect(await informativoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await informativoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Informativo', async () => {
    const nbButtonsBeforeDelete = await informativoComponentsPage.countDeleteButtons();
    await informativoComponentsPage.clickOnLastDeleteButton();

    informativoDeleteDialog = new InformativoDeleteDialog();
    expect(await informativoDeleteDialog.getDialogTitle()).to.eq('nucleoApp.informativo.delete.question');
    await informativoDeleteDialog.clickOnConfirmButton();

    expect(await informativoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
