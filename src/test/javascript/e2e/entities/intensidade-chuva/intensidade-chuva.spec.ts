import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { IntensidadeChuvaComponentsPage, IntensidadeChuvaDeleteDialog, IntensidadeChuvaUpdatePage } from './intensidade-chuva.page-object';

const expect = chai.expect;

describe('IntensidadeChuva e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let intensidadeChuvaComponentsPage: IntensidadeChuvaComponentsPage;
  let intensidadeChuvaUpdatePage: IntensidadeChuvaUpdatePage;
  let intensidadeChuvaDeleteDialog: IntensidadeChuvaDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load IntensidadeChuvas', async () => {
    await navBarPage.goToEntity('intensidade-chuva');
    intensidadeChuvaComponentsPage = new IntensidadeChuvaComponentsPage();
    await browser.wait(ec.visibilityOf(intensidadeChuvaComponentsPage.title), 5000);
    expect(await intensidadeChuvaComponentsPage.getTitle()).to.eq('nucleoApp.intensidadeChuva.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(intensidadeChuvaComponentsPage.entities), ec.visibilityOf(intensidadeChuvaComponentsPage.noResult)),
      1000
    );
  });

  it('should load create IntensidadeChuva page', async () => {
    await intensidadeChuvaComponentsPage.clickOnCreateButton();
    intensidadeChuvaUpdatePage = new IntensidadeChuvaUpdatePage();
    expect(await intensidadeChuvaUpdatePage.getPageTitle()).to.eq('nucleoApp.intensidadeChuva.home.createOrEditLabel');
    await intensidadeChuvaUpdatePage.cancel();
  });

  it('should create and save IntensidadeChuvas', async () => {
    const nbButtonsBeforeCreate = await intensidadeChuvaComponentsPage.countDeleteButtons();

    await intensidadeChuvaComponentsPage.clickOnCreateButton();

    await promise.all([
      intensidadeChuvaUpdatePage.setNomeInput('nome'),
      intensidadeChuvaUpdatePage.setDescricaoInput('descricao'),
      intensidadeChuvaUpdatePage.setFaixaInput('faixa'),
      intensidadeChuvaUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      intensidadeChuvaUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
    ]);

    expect(await intensidadeChuvaUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await intensidadeChuvaUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    expect(await intensidadeChuvaUpdatePage.getFaixaInput()).to.eq('faixa', 'Expected Faixa value to be equals to faixa');
    expect(await intensidadeChuvaUpdatePage.getCreatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected created value to be equals to 2000-12-31'
    );
    expect(await intensidadeChuvaUpdatePage.getUpdatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected updated value to be equals to 2000-12-31'
    );

    await intensidadeChuvaUpdatePage.save();
    expect(await intensidadeChuvaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await intensidadeChuvaComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last IntensidadeChuva', async () => {
    const nbButtonsBeforeDelete = await intensidadeChuvaComponentsPage.countDeleteButtons();
    await intensidadeChuvaComponentsPage.clickOnLastDeleteButton();

    intensidadeChuvaDeleteDialog = new IntensidadeChuvaDeleteDialog();
    expect(await intensidadeChuvaDeleteDialog.getDialogTitle()).to.eq('nucleoApp.intensidadeChuva.delete.question');
    await intensidadeChuvaDeleteDialog.clickOnConfirmButton();

    expect(await intensidadeChuvaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
