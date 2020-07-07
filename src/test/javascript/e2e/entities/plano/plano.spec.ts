import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PlanoComponentsPage, PlanoDeleteDialog, PlanoUpdatePage } from './plano.page-object';

const expect = chai.expect;

describe('Plano e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let planoComponentsPage: PlanoComponentsPage;
  let planoUpdatePage: PlanoUpdatePage;
  let planoDeleteDialog: PlanoDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Planos', async () => {
    await navBarPage.goToEntity('plano');
    planoComponentsPage = new PlanoComponentsPage();
    await browser.wait(ec.visibilityOf(planoComponentsPage.title), 5000);
    expect(await planoComponentsPage.getTitle()).to.eq('nucleoApp.plano.home.title');
    await browser.wait(ec.or(ec.visibilityOf(planoComponentsPage.entities), ec.visibilityOf(planoComponentsPage.noResult)), 1000);
  });

  it('should load create Plano page', async () => {
    await planoComponentsPage.clickOnCreateButton();
    planoUpdatePage = new PlanoUpdatePage();
    expect(await planoUpdatePage.getPageTitle()).to.eq('nucleoApp.plano.home.createOrEditLabel');
    await planoUpdatePage.cancel();
  });

  it('should create and save Planos', async () => {
    const nbButtonsBeforeCreate = await planoComponentsPage.countDeleteButtons();

    await planoComponentsPage.clickOnCreateButton();

    await promise.all([
      planoUpdatePage.setNameInput('name'),
      planoUpdatePage.setDescricaoInput('descricao'),
      planoUpdatePage.setHorarioPrevistoInput('5'),
      planoUpdatePage.setTrackingAtivoInput('5'),
      planoUpdatePage.setPlrAtivoInput('5'),
      planoUpdatePage.setCodigoWidgetPrevisaoInput('5'),
      planoUpdatePage.setKmlAlvoInput('kmlAlvo'),
      planoUpdatePage.setZoomMinInput('5'),
      planoUpdatePage.setDtInicioContratoInput('2000-12-31'),
      planoUpdatePage.setDataFimContratoInput('2000-12-31'),
      planoUpdatePage.setHorarioMonitInicioInput('21:50:46'),
      planoUpdatePage.setHorarioMonitFinalInput('23:41:35'),
      planoUpdatePage.setBlocosInput('blocos'),
      planoUpdatePage.setExtentInput('extent'),
      planoUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      planoUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      planoUpdatePage.empresaSelectLastOption(),
      planoUpdatePage.planoStatusSelectLastOption(),
    ]);

    expect(await planoUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await planoUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    expect(await planoUpdatePage.getHorarioPrevistoInput()).to.eq('5', 'Expected horarioPrevisto value to be equals to 5');
    expect(await planoUpdatePage.getTrackingAtivoInput()).to.eq('5', 'Expected trackingAtivo value to be equals to 5');
    expect(await planoUpdatePage.getPlrAtivoInput()).to.eq('5', 'Expected plrAtivo value to be equals to 5');
    expect(await planoUpdatePage.getCodigoWidgetPrevisaoInput()).to.eq('5', 'Expected codigoWidgetPrevisao value to be equals to 5');
    expect(await planoUpdatePage.getKmlAlvoInput()).to.eq('kmlAlvo', 'Expected KmlAlvo value to be equals to kmlAlvo');
    expect(await planoUpdatePage.getZoomMinInput()).to.eq('5', 'Expected zoomMin value to be equals to 5');
    expect(await planoUpdatePage.getDtInicioContratoInput()).to.eq(
      '2000-12-31',
      'Expected dtInicioContrato value to be equals to 2000-12-31'
    );
    expect(await planoUpdatePage.getDataFimContratoInput()).to.eq(
      '2000-12-31',
      'Expected dataFimContrato value to be equals to 2000-12-31'
    );
    expect(await planoUpdatePage.getHorarioMonitInicioInput()).to.eq(
      '21:50:46',
      'Expected HorarioMonitInicio value to be equals to 21:50:46'
    );
    expect(await planoUpdatePage.getHorarioMonitFinalInput()).to.eq(
      '23:41:35',
      'Expected HorarioMonitFinal value to be equals to 23:41:35'
    );
    expect(await planoUpdatePage.getBlocosInput()).to.eq('blocos', 'Expected Blocos value to be equals to blocos');
    expect(await planoUpdatePage.getExtentInput()).to.eq('extent', 'Expected Extent value to be equals to extent');
    expect(await planoUpdatePage.getCreatedInput()).to.contain('2001-01-01T02:30', 'Expected created value to be equals to 2000-12-31');
    expect(await planoUpdatePage.getUpdatedInput()).to.contain('2001-01-01T02:30', 'Expected updated value to be equals to 2000-12-31');

    await planoUpdatePage.save();
    expect(await planoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await planoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Plano', async () => {
    const nbButtonsBeforeDelete = await planoComponentsPage.countDeleteButtons();
    await planoComponentsPage.clickOnLastDeleteButton();

    planoDeleteDialog = new PlanoDeleteDialog();
    expect(await planoDeleteDialog.getDialogTitle()).to.eq('nucleoApp.plano.delete.question');
    await planoDeleteDialog.clickOnConfirmButton();

    expect(await planoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
