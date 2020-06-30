import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { VentoVmFaixaComponentsPage, VentoVmFaixaDeleteDialog, VentoVmFaixaUpdatePage } from './vento-vm-faixa.page-object';

const expect = chai.expect;

describe('VentoVmFaixa e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let ventoVmFaixaComponentsPage: VentoVmFaixaComponentsPage;
  let ventoVmFaixaUpdatePage: VentoVmFaixaUpdatePage;
  let ventoVmFaixaDeleteDialog: VentoVmFaixaDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load VentoVmFaixas', async () => {
    await navBarPage.goToEntity('vento-vm-faixa');
    ventoVmFaixaComponentsPage = new VentoVmFaixaComponentsPage();
    await browser.wait(ec.visibilityOf(ventoVmFaixaComponentsPage.title), 5000);
    expect(await ventoVmFaixaComponentsPage.getTitle()).to.eq('nucleoApp.ventoVmFaixa.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(ventoVmFaixaComponentsPage.entities), ec.visibilityOf(ventoVmFaixaComponentsPage.noResult)),
      1000
    );
  });

  it('should load create VentoVmFaixa page', async () => {
    await ventoVmFaixaComponentsPage.clickOnCreateButton();
    ventoVmFaixaUpdatePage = new VentoVmFaixaUpdatePage();
    expect(await ventoVmFaixaUpdatePage.getPageTitle()).to.eq('nucleoApp.ventoVmFaixa.home.createOrEditLabel');
    await ventoVmFaixaUpdatePage.cancel();
  });

  it('should create and save VentoVmFaixas', async () => {
    const nbButtonsBeforeCreate = await ventoVmFaixaComponentsPage.countDeleteButtons();

    await ventoVmFaixaComponentsPage.clickOnCreateButton();

    await promise.all([
      ventoVmFaixaUpdatePage.setNameInput('name'),
      ventoVmFaixaUpdatePage.setDescricaoInput('descricao'),
      ventoVmFaixaUpdatePage.setDeInput('5'),
      ventoVmFaixaUpdatePage.setAteInput('5'),
      ventoVmFaixaUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      ventoVmFaixaUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
    ]);

    expect(await ventoVmFaixaUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await ventoVmFaixaUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    expect(await ventoVmFaixaUpdatePage.getDeInput()).to.eq('5', 'Expected de value to be equals to 5');
    expect(await ventoVmFaixaUpdatePage.getAteInput()).to.eq('5', 'Expected ate value to be equals to 5');
    expect(await ventoVmFaixaUpdatePage.getCreatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected created value to be equals to 2000-12-31'
    );
    expect(await ventoVmFaixaUpdatePage.getUpdatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected updated value to be equals to 2000-12-31'
    );

    await ventoVmFaixaUpdatePage.save();
    expect(await ventoVmFaixaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await ventoVmFaixaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last VentoVmFaixa', async () => {
    const nbButtonsBeforeDelete = await ventoVmFaixaComponentsPage.countDeleteButtons();
    await ventoVmFaixaComponentsPage.clickOnLastDeleteButton();

    ventoVmFaixaDeleteDialog = new VentoVmFaixaDeleteDialog();
    expect(await ventoVmFaixaDeleteDialog.getDialogTitle()).to.eq('nucleoApp.ventoVmFaixa.delete.question');
    await ventoVmFaixaDeleteDialog.clickOnConfirmButton();

    expect(await ventoVmFaixaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
