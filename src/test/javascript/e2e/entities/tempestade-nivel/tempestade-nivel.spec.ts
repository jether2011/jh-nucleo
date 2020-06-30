import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TempestadeNivelComponentsPage, TempestadeNivelDeleteDialog, TempestadeNivelUpdatePage } from './tempestade-nivel.page-object';

const expect = chai.expect;

describe('TempestadeNivel e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let tempestadeNivelComponentsPage: TempestadeNivelComponentsPage;
  let tempestadeNivelUpdatePage: TempestadeNivelUpdatePage;
  let tempestadeNivelDeleteDialog: TempestadeNivelDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load TempestadeNivels', async () => {
    await navBarPage.goToEntity('tempestade-nivel');
    tempestadeNivelComponentsPage = new TempestadeNivelComponentsPage();
    await browser.wait(ec.visibilityOf(tempestadeNivelComponentsPage.title), 5000);
    expect(await tempestadeNivelComponentsPage.getTitle()).to.eq('nucleoApp.tempestadeNivel.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(tempestadeNivelComponentsPage.entities), ec.visibilityOf(tempestadeNivelComponentsPage.noResult)),
      1000
    );
  });

  it('should load create TempestadeNivel page', async () => {
    await tempestadeNivelComponentsPage.clickOnCreateButton();
    tempestadeNivelUpdatePage = new TempestadeNivelUpdatePage();
    expect(await tempestadeNivelUpdatePage.getPageTitle()).to.eq('nucleoApp.tempestadeNivel.home.createOrEditLabel');
    await tempestadeNivelUpdatePage.cancel();
  });

  it('should create and save TempestadeNivels', async () => {
    const nbButtonsBeforeCreate = await tempestadeNivelComponentsPage.countDeleteButtons();

    await tempestadeNivelComponentsPage.clickOnCreateButton();

    await promise.all([
      tempestadeNivelUpdatePage.setNameInput('name'),
      tempestadeNivelUpdatePage.setDescricaoInput('descricao'),
      tempestadeNivelUpdatePage.setTaxaDeRaiosInput('taxaDeRaios'),
      tempestadeNivelUpdatePage.setVentosVelocidadeInput('ventosVelocidade'),
      tempestadeNivelUpdatePage.setGranizoInput('granizo'),
      tempestadeNivelUpdatePage.setPotencialDeDanosInput('potencialDeDanos'),
      tempestadeNivelUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      tempestadeNivelUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      tempestadeNivelUpdatePage.intensidadeChuvaSelectLastOption(),
    ]);

    expect(await tempestadeNivelUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await tempestadeNivelUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    expect(await tempestadeNivelUpdatePage.getTaxaDeRaiosInput()).to.eq(
      'taxaDeRaios',
      'Expected TaxaDeRaios value to be equals to taxaDeRaios'
    );
    expect(await tempestadeNivelUpdatePage.getVentosVelocidadeInput()).to.eq(
      'ventosVelocidade',
      'Expected VentosVelocidade value to be equals to ventosVelocidade'
    );
    expect(await tempestadeNivelUpdatePage.getGranizoInput()).to.eq('granizo', 'Expected Granizo value to be equals to granizo');
    expect(await tempestadeNivelUpdatePage.getPotencialDeDanosInput()).to.eq(
      'potencialDeDanos',
      'Expected PotencialDeDanos value to be equals to potencialDeDanos'
    );
    expect(await tempestadeNivelUpdatePage.getCreatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected created value to be equals to 2000-12-31'
    );
    expect(await tempestadeNivelUpdatePage.getUpdatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected updated value to be equals to 2000-12-31'
    );

    await tempestadeNivelUpdatePage.save();
    expect(await tempestadeNivelUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await tempestadeNivelComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last TempestadeNivel', async () => {
    const nbButtonsBeforeDelete = await tempestadeNivelComponentsPage.countDeleteButtons();
    await tempestadeNivelComponentsPage.clickOnLastDeleteButton();

    tempestadeNivelDeleteDialog = new TempestadeNivelDeleteDialog();
    expect(await tempestadeNivelDeleteDialog.getDialogTitle()).to.eq('nucleoApp.tempestadeNivel.delete.question');
    await tempestadeNivelDeleteDialog.clickOnConfirmButton();

    expect(await tempestadeNivelComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
