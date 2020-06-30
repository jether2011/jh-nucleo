import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  AlertaFerramentaComponentsPage,
  /* AlertaFerramentaDeleteDialog, */
  AlertaFerramentaUpdatePage,
} from './alerta-ferramenta.page-object';

const expect = chai.expect;

describe('AlertaFerramenta e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let alertaFerramentaComponentsPage: AlertaFerramentaComponentsPage;
  let alertaFerramentaUpdatePage: AlertaFerramentaUpdatePage;
  /* let alertaFerramentaDeleteDialog: AlertaFerramentaDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load AlertaFerramentas', async () => {
    await navBarPage.goToEntity('alerta-ferramenta');
    alertaFerramentaComponentsPage = new AlertaFerramentaComponentsPage();
    await browser.wait(ec.visibilityOf(alertaFerramentaComponentsPage.title), 5000);
    expect(await alertaFerramentaComponentsPage.getTitle()).to.eq('nucleoApp.alertaFerramenta.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(alertaFerramentaComponentsPage.entities), ec.visibilityOf(alertaFerramentaComponentsPage.noResult)),
      1000
    );
  });

  it('should load create AlertaFerramenta page', async () => {
    await alertaFerramentaComponentsPage.clickOnCreateButton();
    alertaFerramentaUpdatePage = new AlertaFerramentaUpdatePage();
    expect(await alertaFerramentaUpdatePage.getPageTitle()).to.eq('nucleoApp.alertaFerramenta.home.createOrEditLabel');
    await alertaFerramentaUpdatePage.cancel();
  });

  /* it('should create and save AlertaFerramentas', async () => {
        const nbButtonsBeforeCreate = await alertaFerramentaComponentsPage.countDeleteButtons();

        await alertaFerramentaComponentsPage.clickOnCreateButton();

        await promise.all([
            alertaFerramentaUpdatePage.setNomeInput('nome'),
            alertaFerramentaUpdatePage.setDescricaoInput('descricao'),
            alertaFerramentaUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            alertaFerramentaUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            alertaFerramentaUpdatePage.alertaSelectLastOption(),
            alertaFerramentaUpdatePage.ferramentaSelectLastOption(),
        ]);

        expect(await alertaFerramentaUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
        expect(await alertaFerramentaUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
        expect(await alertaFerramentaUpdatePage.getCreatedInput()).to.contain('2001-01-01T02:30', 'Expected created value to be equals to 2000-12-31');
        expect(await alertaFerramentaUpdatePage.getUpdatedInput()).to.contain('2001-01-01T02:30', 'Expected updated value to be equals to 2000-12-31');

        await alertaFerramentaUpdatePage.save();
        expect(await alertaFerramentaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await alertaFerramentaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last AlertaFerramenta', async () => {
        const nbButtonsBeforeDelete = await alertaFerramentaComponentsPage.countDeleteButtons();
        await alertaFerramentaComponentsPage.clickOnLastDeleteButton();

        alertaFerramentaDeleteDialog = new AlertaFerramentaDeleteDialog();
        expect(await alertaFerramentaDeleteDialog.getDialogTitle())
            .to.eq('nucleoApp.alertaFerramenta.delete.question');
        await alertaFerramentaDeleteDialog.clickOnConfirmButton();

        expect(await alertaFerramentaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
