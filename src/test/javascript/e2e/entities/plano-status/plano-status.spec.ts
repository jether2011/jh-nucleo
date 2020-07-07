import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PlanoStatusComponentsPage, PlanoStatusDeleteDialog, PlanoStatusUpdatePage } from './plano-status.page-object';

const expect = chai.expect;

describe('PlanoStatus e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let planoStatusComponentsPage: PlanoStatusComponentsPage;
  let planoStatusUpdatePage: PlanoStatusUpdatePage;
  let planoStatusDeleteDialog: PlanoStatusDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PlanoStatuses', async () => {
    await navBarPage.goToEntity('plano-status');
    planoStatusComponentsPage = new PlanoStatusComponentsPage();
    await browser.wait(ec.visibilityOf(planoStatusComponentsPage.title), 5000);
    expect(await planoStatusComponentsPage.getTitle()).to.eq('nucleoApp.planoStatus.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(planoStatusComponentsPage.entities), ec.visibilityOf(planoStatusComponentsPage.noResult)),
      1000
    );
  });

  it('should load create PlanoStatus page', async () => {
    await planoStatusComponentsPage.clickOnCreateButton();
    planoStatusUpdatePage = new PlanoStatusUpdatePage();
    expect(await planoStatusUpdatePage.getPageTitle()).to.eq('nucleoApp.planoStatus.home.createOrEditLabel');
    await planoStatusUpdatePage.cancel();
  });

  it('should create and save PlanoStatuses', async () => {
    const nbButtonsBeforeCreate = await planoStatusComponentsPage.countDeleteButtons();

    await planoStatusComponentsPage.clickOnCreateButton();

    await promise.all([
      planoStatusUpdatePage.setNameInput('name'),
      planoStatusUpdatePage.setDescricaoInput('descricao'),
      planoStatusUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      planoStatusUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
    ]);

    expect(await planoStatusUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await planoStatusUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    expect(await planoStatusUpdatePage.getCreatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected created value to be equals to 2000-12-31'
    );
    expect(await planoStatusUpdatePage.getUpdatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected updated value to be equals to 2000-12-31'
    );

    await planoStatusUpdatePage.save();
    expect(await planoStatusUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await planoStatusComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last PlanoStatus', async () => {
    const nbButtonsBeforeDelete = await planoStatusComponentsPage.countDeleteButtons();
    await planoStatusComponentsPage.clickOnLastDeleteButton();

    planoStatusDeleteDialog = new PlanoStatusDeleteDialog();
    expect(await planoStatusDeleteDialog.getDialogTitle()).to.eq('nucleoApp.planoStatus.delete.question');
    await planoStatusDeleteDialog.clickOnConfirmButton();

    expect(await planoStatusComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
