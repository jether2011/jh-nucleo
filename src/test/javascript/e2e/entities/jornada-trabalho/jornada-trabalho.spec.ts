import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  JornadaTrabalhoComponentsPage,
  /* JornadaTrabalhoDeleteDialog, */
  JornadaTrabalhoUpdatePage,
} from './jornada-trabalho.page-object';

const expect = chai.expect;

describe('JornadaTrabalho e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let jornadaTrabalhoComponentsPage: JornadaTrabalhoComponentsPage;
  let jornadaTrabalhoUpdatePage: JornadaTrabalhoUpdatePage;
  /* let jornadaTrabalhoDeleteDialog: JornadaTrabalhoDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load JornadaTrabalhos', async () => {
    await navBarPage.goToEntity('jornada-trabalho');
    jornadaTrabalhoComponentsPage = new JornadaTrabalhoComponentsPage();
    await browser.wait(ec.visibilityOf(jornadaTrabalhoComponentsPage.title), 5000);
    expect(await jornadaTrabalhoComponentsPage.getTitle()).to.eq('nucleoApp.jornadaTrabalho.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(jornadaTrabalhoComponentsPage.entities), ec.visibilityOf(jornadaTrabalhoComponentsPage.noResult)),
      1000
    );
  });

  it('should load create JornadaTrabalho page', async () => {
    await jornadaTrabalhoComponentsPage.clickOnCreateButton();
    jornadaTrabalhoUpdatePage = new JornadaTrabalhoUpdatePage();
    expect(await jornadaTrabalhoUpdatePage.getPageTitle()).to.eq('nucleoApp.jornadaTrabalho.home.createOrEditLabel');
    await jornadaTrabalhoUpdatePage.cancel();
  });

  /* it('should create and save JornadaTrabalhos', async () => {
        const nbButtonsBeforeCreate = await jornadaTrabalhoComponentsPage.countDeleteButtons();

        await jornadaTrabalhoComponentsPage.clickOnCreateButton();

        await promise.all([
            jornadaTrabalhoUpdatePage.setNomeInput('nome'),
            jornadaTrabalhoUpdatePage.setDescricaoInput('descricao'),
            jornadaTrabalhoUpdatePage.setHorainicioInput('02:43:08'),
            jornadaTrabalhoUpdatePage.setDuracaoInput('09:54:11'),
            jornadaTrabalhoUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            jornadaTrabalhoUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            jornadaTrabalhoUpdatePage.planoSelectLastOption(),
            jornadaTrabalhoUpdatePage.diaSemanaSelectLastOption(),
        ]);

        expect(await jornadaTrabalhoUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
        expect(await jornadaTrabalhoUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
        expect(await jornadaTrabalhoUpdatePage.getHorainicioInput()).to.eq('02:43:08', 'Expected Horainicio value to be equals to 02:43:08');
        expect(await jornadaTrabalhoUpdatePage.getDuracaoInput()).to.eq('09:54:11', 'Expected Duracao value to be equals to 09:54:11');
        expect(await jornadaTrabalhoUpdatePage.getCreatedInput()).to.contain('2001-01-01T02:30', 'Expected created value to be equals to 2000-12-31');
        expect(await jornadaTrabalhoUpdatePage.getUpdatedInput()).to.contain('2001-01-01T02:30', 'Expected updated value to be equals to 2000-12-31');

        await jornadaTrabalhoUpdatePage.save();
        expect(await jornadaTrabalhoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await jornadaTrabalhoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last JornadaTrabalho', async () => {
        const nbButtonsBeforeDelete = await jornadaTrabalhoComponentsPage.countDeleteButtons();
        await jornadaTrabalhoComponentsPage.clickOnLastDeleteButton();

        jornadaTrabalhoDeleteDialog = new JornadaTrabalhoDeleteDialog();
        expect(await jornadaTrabalhoDeleteDialog.getDialogTitle())
            .to.eq('nucleoApp.jornadaTrabalho.delete.question');
        await jornadaTrabalhoDeleteDialog.clickOnConfirmButton();

        expect(await jornadaTrabalhoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
