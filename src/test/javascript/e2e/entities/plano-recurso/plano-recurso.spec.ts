import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PlanoRecursoComponentsPage, PlanoRecursoDeleteDialog, PlanoRecursoUpdatePage } from './plano-recurso.page-object';

const expect = chai.expect;

describe('PlanoRecurso e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let planoRecursoComponentsPage: PlanoRecursoComponentsPage;
  let planoRecursoUpdatePage: PlanoRecursoUpdatePage;
  let planoRecursoDeleteDialog: PlanoRecursoDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PlanoRecursos', async () => {
    await navBarPage.goToEntity('plano-recurso');
    planoRecursoComponentsPage = new PlanoRecursoComponentsPage();
    await browser.wait(ec.visibilityOf(planoRecursoComponentsPage.title), 5000);
    expect(await planoRecursoComponentsPage.getTitle()).to.eq('nucleoApp.planoRecurso.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(planoRecursoComponentsPage.entities), ec.visibilityOf(planoRecursoComponentsPage.noResult)),
      1000
    );
  });

  it('should load create PlanoRecurso page', async () => {
    await planoRecursoComponentsPage.clickOnCreateButton();
    planoRecursoUpdatePage = new PlanoRecursoUpdatePage();
    expect(await planoRecursoUpdatePage.getPageTitle()).to.eq('nucleoApp.planoRecurso.home.createOrEditLabel');
    await planoRecursoUpdatePage.cancel();
  });

  it('should create and save PlanoRecursos', async () => {
    const nbButtonsBeforeCreate = await planoRecursoComponentsPage.countDeleteButtons();

    await planoRecursoComponentsPage.clickOnCreateButton();

    await promise.all([
      planoRecursoUpdatePage.setNameInput('name'),
      planoRecursoUpdatePage.setDescricaoInput('descricao'),
      planoRecursoUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      planoRecursoUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      planoRecursoUpdatePage.planoSelectLastOption(),
      planoRecursoUpdatePage.recursoSelectLastOption(),
    ]);

    expect(await planoRecursoUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await planoRecursoUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    const selectedAtivo = planoRecursoUpdatePage.getAtivoInput();
    if (await selectedAtivo.isSelected()) {
      await planoRecursoUpdatePage.getAtivoInput().click();
      expect(await planoRecursoUpdatePage.getAtivoInput().isSelected(), 'Expected ativo not to be selected').to.be.false;
    } else {
      await planoRecursoUpdatePage.getAtivoInput().click();
      expect(await planoRecursoUpdatePage.getAtivoInput().isSelected(), 'Expected ativo to be selected').to.be.true;
    }
    expect(await planoRecursoUpdatePage.getCreatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected created value to be equals to 2000-12-31'
    );
    expect(await planoRecursoUpdatePage.getUpdatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected updated value to be equals to 2000-12-31'
    );

    await planoRecursoUpdatePage.save();
    expect(await planoRecursoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await planoRecursoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last PlanoRecurso', async () => {
    const nbButtonsBeforeDelete = await planoRecursoComponentsPage.countDeleteButtons();
    await planoRecursoComponentsPage.clickOnLastDeleteButton();

    planoRecursoDeleteDialog = new PlanoRecursoDeleteDialog();
    expect(await planoRecursoDeleteDialog.getDialogTitle()).to.eq('nucleoApp.planoRecurso.delete.question');
    await planoRecursoDeleteDialog.clickOnConfirmButton();

    expect(await planoRecursoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
