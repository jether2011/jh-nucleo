import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  MeteogramaComponentsPage,
  /* MeteogramaDeleteDialog, */
  MeteogramaUpdatePage,
} from './meteograma.page-object';

const expect = chai.expect;

describe('Meteograma e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let meteogramaComponentsPage: MeteogramaComponentsPage;
  let meteogramaUpdatePage: MeteogramaUpdatePage;
  /* let meteogramaDeleteDialog: MeteogramaDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Meteogramas', async () => {
    await navBarPage.goToEntity('meteograma');
    meteogramaComponentsPage = new MeteogramaComponentsPage();
    await browser.wait(ec.visibilityOf(meteogramaComponentsPage.title), 5000);
    expect(await meteogramaComponentsPage.getTitle()).to.eq('nucleoApp.meteograma.home.title');
    await browser.wait(ec.or(ec.visibilityOf(meteogramaComponentsPage.entities), ec.visibilityOf(meteogramaComponentsPage.noResult)), 1000);
  });

  it('should load create Meteograma page', async () => {
    await meteogramaComponentsPage.clickOnCreateButton();
    meteogramaUpdatePage = new MeteogramaUpdatePage();
    expect(await meteogramaUpdatePage.getPageTitle()).to.eq('nucleoApp.meteograma.home.createOrEditLabel');
    await meteogramaUpdatePage.cancel();
  });

  /* it('should create and save Meteogramas', async () => {
        const nbButtonsBeforeCreate = await meteogramaComponentsPage.countDeleteButtons();

        await meteogramaComponentsPage.clickOnCreateButton();

        await promise.all([
            meteogramaUpdatePage.setNameInput('name'),
            meteogramaUpdatePage.setDescricaoInput('descricao'),
            meteogramaUpdatePage.setArquivoInput('arquivo'),
            meteogramaUpdatePage.setFolderInput('folder'),
            meteogramaUpdatePage.setTipoarquivoInput('tipoarquivo'),
            meteogramaUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            meteogramaUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            meteogramaUpdatePage.planoSelectLastOption(),
        ]);

        expect(await meteogramaUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
        expect(await meteogramaUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
        expect(await meteogramaUpdatePage.getArquivoInput()).to.eq('arquivo', 'Expected Arquivo value to be equals to arquivo');
        expect(await meteogramaUpdatePage.getFolderInput()).to.eq('folder', 'Expected Folder value to be equals to folder');
        expect(await meteogramaUpdatePage.getTipoarquivoInput()).to.eq('tipoarquivo', 'Expected Tipoarquivo value to be equals to tipoarquivo');
        expect(await meteogramaUpdatePage.getCreatedInput()).to.contain('2001-01-01T02:30', 'Expected created value to be equals to 2000-12-31');
        expect(await meteogramaUpdatePage.getUpdatedInput()).to.contain('2001-01-01T02:30', 'Expected updated value to be equals to 2000-12-31');

        await meteogramaUpdatePage.save();
        expect(await meteogramaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await meteogramaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last Meteograma', async () => {
        const nbButtonsBeforeDelete = await meteogramaComponentsPage.countDeleteButtons();
        await meteogramaComponentsPage.clickOnLastDeleteButton();

        meteogramaDeleteDialog = new MeteogramaDeleteDialog();
        expect(await meteogramaDeleteDialog.getDialogTitle())
            .to.eq('nucleoApp.meteograma.delete.question');
        await meteogramaDeleteDialog.clickOnConfirmButton();

        expect(await meteogramaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
