import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  TipoEnvioIntegradorComponentsPage,
  /* TipoEnvioIntegradorDeleteDialog, */
  TipoEnvioIntegradorUpdatePage,
} from './tipo-envio-integrador.page-object';

const expect = chai.expect;

describe('TipoEnvioIntegrador e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let tipoEnvioIntegradorComponentsPage: TipoEnvioIntegradorComponentsPage;
  let tipoEnvioIntegradorUpdatePage: TipoEnvioIntegradorUpdatePage;
  /* let tipoEnvioIntegradorDeleteDialog: TipoEnvioIntegradorDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load TipoEnvioIntegradors', async () => {
    await navBarPage.goToEntity('tipo-envio-integrador');
    tipoEnvioIntegradorComponentsPage = new TipoEnvioIntegradorComponentsPage();
    await browser.wait(ec.visibilityOf(tipoEnvioIntegradorComponentsPage.title), 5000);
    expect(await tipoEnvioIntegradorComponentsPage.getTitle()).to.eq('nucleoApp.tipoEnvioIntegrador.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(tipoEnvioIntegradorComponentsPage.entities), ec.visibilityOf(tipoEnvioIntegradorComponentsPage.noResult)),
      1000
    );
  });

  it('should load create TipoEnvioIntegrador page', async () => {
    await tipoEnvioIntegradorComponentsPage.clickOnCreateButton();
    tipoEnvioIntegradorUpdatePage = new TipoEnvioIntegradorUpdatePage();
    expect(await tipoEnvioIntegradorUpdatePage.getPageTitle()).to.eq('nucleoApp.tipoEnvioIntegrador.home.createOrEditLabel');
    await tipoEnvioIntegradorUpdatePage.cancel();
  });

  /* it('should create and save TipoEnvioIntegradors', async () => {
        const nbButtonsBeforeCreate = await tipoEnvioIntegradorComponentsPage.countDeleteButtons();

        await tipoEnvioIntegradorComponentsPage.clickOnCreateButton();

        await promise.all([
            tipoEnvioIntegradorUpdatePage.setNameInput('name'),
            tipoEnvioIntegradorUpdatePage.setDescricaoInput('descricao'),
            tipoEnvioIntegradorUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            tipoEnvioIntegradorUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            tipoEnvioIntegradorUpdatePage.tipoEnvioSelectLastOption(),
            tipoEnvioIntegradorUpdatePage.integradorSelectLastOption(),
        ]);

        expect(await tipoEnvioIntegradorUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
        expect(await tipoEnvioIntegradorUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
        const selectedAtivo = tipoEnvioIntegradorUpdatePage.getAtivoInput();
        if (await selectedAtivo.isSelected()) {
            await tipoEnvioIntegradorUpdatePage.getAtivoInput().click();
            expect(await tipoEnvioIntegradorUpdatePage.getAtivoInput().isSelected(), 'Expected ativo not to be selected').to.be.false;
        } else {
            await tipoEnvioIntegradorUpdatePage.getAtivoInput().click();
            expect(await tipoEnvioIntegradorUpdatePage.getAtivoInput().isSelected(), 'Expected ativo to be selected').to.be.true;
        }
        expect(await tipoEnvioIntegradorUpdatePage.getCreatedInput()).to.contain('2001-01-01T02:30', 'Expected created value to be equals to 2000-12-31');
        expect(await tipoEnvioIntegradorUpdatePage.getUpdatedInput()).to.contain('2001-01-01T02:30', 'Expected updated value to be equals to 2000-12-31');

        await tipoEnvioIntegradorUpdatePage.save();
        expect(await tipoEnvioIntegradorUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await tipoEnvioIntegradorComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last TipoEnvioIntegrador', async () => {
        const nbButtonsBeforeDelete = await tipoEnvioIntegradorComponentsPage.countDeleteButtons();
        await tipoEnvioIntegradorComponentsPage.clickOnLastDeleteButton();

        tipoEnvioIntegradorDeleteDialog = new TipoEnvioIntegradorDeleteDialog();
        expect(await tipoEnvioIntegradorDeleteDialog.getDialogTitle())
            .to.eq('nucleoApp.tipoEnvioIntegrador.delete.question');
        await tipoEnvioIntegradorDeleteDialog.clickOnConfirmButton();

        expect(await tipoEnvioIntegradorComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
