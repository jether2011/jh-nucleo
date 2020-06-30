import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ConsolidacaoComponentsPage, ConsolidacaoDeleteDialog, ConsolidacaoUpdatePage } from './consolidacao.page-object';

const expect = chai.expect;

describe('Consolidacao e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let consolidacaoComponentsPage: ConsolidacaoComponentsPage;
  let consolidacaoUpdatePage: ConsolidacaoUpdatePage;
  let consolidacaoDeleteDialog: ConsolidacaoDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Consolidacaos', async () => {
    await navBarPage.goToEntity('consolidacao');
    consolidacaoComponentsPage = new ConsolidacaoComponentsPage();
    await browser.wait(ec.visibilityOf(consolidacaoComponentsPage.title), 5000);
    expect(await consolidacaoComponentsPage.getTitle()).to.eq('nucleoApp.consolidacao.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(consolidacaoComponentsPage.entities), ec.visibilityOf(consolidacaoComponentsPage.noResult)),
      1000
    );
  });

  it('should load create Consolidacao page', async () => {
    await consolidacaoComponentsPage.clickOnCreateButton();
    consolidacaoUpdatePage = new ConsolidacaoUpdatePage();
    expect(await consolidacaoUpdatePage.getPageTitle()).to.eq('nucleoApp.consolidacao.home.createOrEditLabel');
    await consolidacaoUpdatePage.cancel();
  });

  it('should create and save Consolidacaos', async () => {
    const nbButtonsBeforeCreate = await consolidacaoComponentsPage.countDeleteButtons();

    await consolidacaoComponentsPage.clickOnCreateButton();

    await promise.all([
      consolidacaoUpdatePage.setNomeInput('nome'),
      consolidacaoUpdatePage.setDescricaoInput('descricao'),
      consolidacaoUpdatePage.setDataInput('2000-12-31'),
      consolidacaoUpdatePage.setTextoInput('texto'),
      consolidacaoUpdatePage.setQtdEmailInput('5'),
      consolidacaoUpdatePage.setImagemInput('imagem'),
      consolidacaoUpdatePage.setArquivoEmlInput('arquivoEml'),
      consolidacaoUpdatePage.setAssuntoInput('assunto'),
      consolidacaoUpdatePage.setSubAssuntoInput('subAssunto'),
      consolidacaoUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      consolidacaoUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      consolidacaoUpdatePage.planoRecursoSelectLastOption(),
    ]);

    expect(await consolidacaoUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await consolidacaoUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    expect(await consolidacaoUpdatePage.getDataInput()).to.eq('2000-12-31', 'Expected data value to be equals to 2000-12-31');
    expect(await consolidacaoUpdatePage.getTextoInput()).to.eq('texto', 'Expected Texto value to be equals to texto');
    expect(await consolidacaoUpdatePage.getQtdEmailInput()).to.eq('5', 'Expected qtdEmail value to be equals to 5');
    expect(await consolidacaoUpdatePage.getImagemInput()).to.eq('imagem', 'Expected Imagem value to be equals to imagem');
    expect(await consolidacaoUpdatePage.getArquivoEmlInput()).to.eq('arquivoEml', 'Expected ArquivoEml value to be equals to arquivoEml');
    expect(await consolidacaoUpdatePage.getAssuntoInput()).to.eq('assunto', 'Expected Assunto value to be equals to assunto');
    expect(await consolidacaoUpdatePage.getSubAssuntoInput()).to.eq('subAssunto', 'Expected SubAssunto value to be equals to subAssunto');
    expect(await consolidacaoUpdatePage.getCreatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected created value to be equals to 2000-12-31'
    );
    expect(await consolidacaoUpdatePage.getUpdatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected updated value to be equals to 2000-12-31'
    );

    await consolidacaoUpdatePage.save();
    expect(await consolidacaoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await consolidacaoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Consolidacao', async () => {
    const nbButtonsBeforeDelete = await consolidacaoComponentsPage.countDeleteButtons();
    await consolidacaoComponentsPage.clickOnLastDeleteButton();

    consolidacaoDeleteDialog = new ConsolidacaoDeleteDialog();
    expect(await consolidacaoDeleteDialog.getDialogTitle()).to.eq('nucleoApp.consolidacao.delete.question');
    await consolidacaoDeleteDialog.clickOnConfirmButton();

    expect(await consolidacaoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
