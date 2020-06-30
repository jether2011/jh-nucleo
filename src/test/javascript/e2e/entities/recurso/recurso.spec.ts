import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  RecursoComponentsPage,
  /* RecursoDeleteDialog, */
  RecursoUpdatePage,
} from './recurso.page-object';

const expect = chai.expect;

describe('Recurso e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let recursoComponentsPage: RecursoComponentsPage;
  let recursoUpdatePage: RecursoUpdatePage;
  /* let recursoDeleteDialog: RecursoDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Recursos', async () => {
    await navBarPage.goToEntity('recurso');
    recursoComponentsPage = new RecursoComponentsPage();
    await browser.wait(ec.visibilityOf(recursoComponentsPage.title), 5000);
    expect(await recursoComponentsPage.getTitle()).to.eq('nucleoApp.recurso.home.title');
    await browser.wait(ec.or(ec.visibilityOf(recursoComponentsPage.entities), ec.visibilityOf(recursoComponentsPage.noResult)), 1000);
  });

  it('should load create Recurso page', async () => {
    await recursoComponentsPage.clickOnCreateButton();
    recursoUpdatePage = new RecursoUpdatePage();
    expect(await recursoUpdatePage.getPageTitle()).to.eq('nucleoApp.recurso.home.createOrEditLabel');
    await recursoUpdatePage.cancel();
  });

  /* it('should create and save Recursos', async () => {
        const nbButtonsBeforeCreate = await recursoComponentsPage.countDeleteButtons();

        await recursoComponentsPage.clickOnCreateButton();

        await promise.all([
            recursoUpdatePage.setNameInput('name'),
            recursoUpdatePage.setDescricaoInput('descricao'),
            recursoUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            recursoUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            recursoUpdatePage.recursoTipoSelectLastOption(),
            recursoUpdatePage.variavelMeteorologicaSelectLastOption(),
        ]);

        expect(await recursoUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
        expect(await recursoUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
        const selectedAtivo = recursoUpdatePage.getAtivoInput();
        if (await selectedAtivo.isSelected()) {
            await recursoUpdatePage.getAtivoInput().click();
            expect(await recursoUpdatePage.getAtivoInput().isSelected(), 'Expected ativo not to be selected').to.be.false;
        } else {
            await recursoUpdatePage.getAtivoInput().click();
            expect(await recursoUpdatePage.getAtivoInput().isSelected(), 'Expected ativo to be selected').to.be.true;
        }
        expect(await recursoUpdatePage.getCreatedInput()).to.contain('2001-01-01T02:30', 'Expected created value to be equals to 2000-12-31');
        expect(await recursoUpdatePage.getUpdatedInput()).to.contain('2001-01-01T02:30', 'Expected updated value to be equals to 2000-12-31');

        await recursoUpdatePage.save();
        expect(await recursoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await recursoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last Recurso', async () => {
        const nbButtonsBeforeDelete = await recursoComponentsPage.countDeleteButtons();
        await recursoComponentsPage.clickOnLastDeleteButton();

        recursoDeleteDialog = new RecursoDeleteDialog();
        expect(await recursoDeleteDialog.getDialogTitle())
            .to.eq('nucleoApp.recurso.delete.question');
        await recursoDeleteDialog.clickOnConfirmButton();

        expect(await recursoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
