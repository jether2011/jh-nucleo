import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PrevisaoComponentsPage, PrevisaoDeleteDialog, PrevisaoUpdatePage } from './previsao.page-object';

const expect = chai.expect;

describe('Previsao e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let previsaoComponentsPage: PrevisaoComponentsPage;
  let previsaoUpdatePage: PrevisaoUpdatePage;
  let previsaoDeleteDialog: PrevisaoDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Previsaos', async () => {
    await navBarPage.goToEntity('previsao');
    previsaoComponentsPage = new PrevisaoComponentsPage();
    await browser.wait(ec.visibilityOf(previsaoComponentsPage.title), 5000);
    expect(await previsaoComponentsPage.getTitle()).to.eq('nucleoApp.previsao.home.title');
    await browser.wait(ec.or(ec.visibilityOf(previsaoComponentsPage.entities), ec.visibilityOf(previsaoComponentsPage.noResult)), 1000);
  });

  it('should load create Previsao page', async () => {
    await previsaoComponentsPage.clickOnCreateButton();
    previsaoUpdatePage = new PrevisaoUpdatePage();
    expect(await previsaoUpdatePage.getPageTitle()).to.eq('nucleoApp.previsao.home.createOrEditLabel');
    await previsaoUpdatePage.cancel();
  });

  it('should create and save Previsaos', async () => {
    const nbButtonsBeforeCreate = await previsaoComponentsPage.countDeleteButtons();

    await previsaoComponentsPage.clickOnCreateButton();

    await promise.all([
      previsaoUpdatePage.setNameInput('name'),
      previsaoUpdatePage.setDescricaoInput('descricao'),
      previsaoUpdatePage.setTextoNorteInput('textoNorte'),
      previsaoUpdatePage.setTextoNorteImagemInput('textoNorteImagem'),
      previsaoUpdatePage.setTextoNordesteInput('textoNordeste'),
      previsaoUpdatePage.setTextoNordesteImagemInput('textoNordesteImagem'),
      previsaoUpdatePage.setTextoSulInput('textoSul'),
      previsaoUpdatePage.setTextoSulImagemInput('textoSulImagem'),
      previsaoUpdatePage.setTextoSudesteInput('textoSudeste'),
      previsaoUpdatePage.setTextoSudesteImagemInput('textoSudesteImagem'),
      previsaoUpdatePage.setTextoCentroOesteInput('textoCentroOeste'),
      previsaoUpdatePage.setTextoCentroOesteImagemInput('textoCentroOesteImagem'),
      previsaoUpdatePage.setTextoBrasilInput('textoBrasil'),
      previsaoUpdatePage.setTextoBrasilImagemInput('textoBrasilImagem'),
      previsaoUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      previsaoUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
    ]);

    expect(await previsaoUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await previsaoUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    expect(await previsaoUpdatePage.getTextoNorteInput()).to.eq('textoNorte', 'Expected TextoNorte value to be equals to textoNorte');
    expect(await previsaoUpdatePage.getTextoNorteImagemInput()).to.eq(
      'textoNorteImagem',
      'Expected TextoNorteImagem value to be equals to textoNorteImagem'
    );
    expect(await previsaoUpdatePage.getTextoNordesteInput()).to.eq(
      'textoNordeste',
      'Expected TextoNordeste value to be equals to textoNordeste'
    );
    expect(await previsaoUpdatePage.getTextoNordesteImagemInput()).to.eq(
      'textoNordesteImagem',
      'Expected TextoNordesteImagem value to be equals to textoNordesteImagem'
    );
    expect(await previsaoUpdatePage.getTextoSulInput()).to.eq('textoSul', 'Expected TextoSul value to be equals to textoSul');
    expect(await previsaoUpdatePage.getTextoSulImagemInput()).to.eq(
      'textoSulImagem',
      'Expected TextoSulImagem value to be equals to textoSulImagem'
    );
    expect(await previsaoUpdatePage.getTextoSudesteInput()).to.eq(
      'textoSudeste',
      'Expected TextoSudeste value to be equals to textoSudeste'
    );
    expect(await previsaoUpdatePage.getTextoSudesteImagemInput()).to.eq(
      'textoSudesteImagem',
      'Expected TextoSudesteImagem value to be equals to textoSudesteImagem'
    );
    expect(await previsaoUpdatePage.getTextoCentroOesteInput()).to.eq(
      'textoCentroOeste',
      'Expected TextoCentroOeste value to be equals to textoCentroOeste'
    );
    expect(await previsaoUpdatePage.getTextoCentroOesteImagemInput()).to.eq(
      'textoCentroOesteImagem',
      'Expected TextoCentroOesteImagem value to be equals to textoCentroOesteImagem'
    );
    const selectedEnviado = previsaoUpdatePage.getEnviadoInput();
    if (await selectedEnviado.isSelected()) {
      await previsaoUpdatePage.getEnviadoInput().click();
      expect(await previsaoUpdatePage.getEnviadoInput().isSelected(), 'Expected enviado not to be selected').to.be.false;
    } else {
      await previsaoUpdatePage.getEnviadoInput().click();
      expect(await previsaoUpdatePage.getEnviadoInput().isSelected(), 'Expected enviado to be selected').to.be.true;
    }
    expect(await previsaoUpdatePage.getTextoBrasilInput()).to.eq('textoBrasil', 'Expected TextoBrasil value to be equals to textoBrasil');
    expect(await previsaoUpdatePage.getTextoBrasilImagemInput()).to.eq(
      'textoBrasilImagem',
      'Expected TextoBrasilImagem value to be equals to textoBrasilImagem'
    );
    expect(await previsaoUpdatePage.getCreatedInput()).to.contain('2001-01-01T02:30', 'Expected created value to be equals to 2000-12-31');
    expect(await previsaoUpdatePage.getUpdatedInput()).to.contain('2001-01-01T02:30', 'Expected updated value to be equals to 2000-12-31');

    await previsaoUpdatePage.save();
    expect(await previsaoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await previsaoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Previsao', async () => {
    const nbButtonsBeforeDelete = await previsaoComponentsPage.countDeleteButtons();
    await previsaoComponentsPage.clickOnLastDeleteButton();

    previsaoDeleteDialog = new PrevisaoDeleteDialog();
    expect(await previsaoDeleteDialog.getDialogTitle()).to.eq('nucleoApp.previsao.delete.question');
    await previsaoDeleteDialog.clickOnConfirmButton();

    expect(await previsaoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
