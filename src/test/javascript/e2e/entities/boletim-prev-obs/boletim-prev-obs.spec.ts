import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { BoletimPrevObsComponentsPage, BoletimPrevObsDeleteDialog, BoletimPrevObsUpdatePage } from './boletim-prev-obs.page-object';

const expect = chai.expect;

describe('BoletimPrevObs e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let boletimPrevObsComponentsPage: BoletimPrevObsComponentsPage;
  let boletimPrevObsUpdatePage: BoletimPrevObsUpdatePage;
  let boletimPrevObsDeleteDialog: BoletimPrevObsDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load BoletimPrevObs', async () => {
    await navBarPage.goToEntity('boletim-prev-obs');
    boletimPrevObsComponentsPage = new BoletimPrevObsComponentsPage();
    await browser.wait(ec.visibilityOf(boletimPrevObsComponentsPage.title), 5000);
    expect(await boletimPrevObsComponentsPage.getTitle()).to.eq('nucleoApp.boletimPrevObs.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(boletimPrevObsComponentsPage.entities), ec.visibilityOf(boletimPrevObsComponentsPage.noResult)),
      1000
    );
  });

  it('should load create BoletimPrevObs page', async () => {
    await boletimPrevObsComponentsPage.clickOnCreateButton();
    boletimPrevObsUpdatePage = new BoletimPrevObsUpdatePage();
    expect(await boletimPrevObsUpdatePage.getPageTitle()).to.eq('nucleoApp.boletimPrevObs.home.createOrEditLabel');
    await boletimPrevObsUpdatePage.cancel();
  });

  it('should create and save BoletimPrevObs', async () => {
    const nbButtonsBeforeCreate = await boletimPrevObsComponentsPage.countDeleteButtons();

    await boletimPrevObsComponentsPage.clickOnCreateButton();

    await promise.all([
      boletimPrevObsUpdatePage.setNomeInput('nome'),
      boletimPrevObsUpdatePage.setDescricaoInput('descricao'),
      boletimPrevObsUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      boletimPrevObsUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
    ]);

    expect(await boletimPrevObsUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await boletimPrevObsUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    expect(await boletimPrevObsUpdatePage.getCreatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected created value to be equals to 2000-12-31'
    );
    expect(await boletimPrevObsUpdatePage.getUpdatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected updated value to be equals to 2000-12-31'
    );

    await boletimPrevObsUpdatePage.save();
    expect(await boletimPrevObsUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await boletimPrevObsComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last BoletimPrevObs', async () => {
    const nbButtonsBeforeDelete = await boletimPrevObsComponentsPage.countDeleteButtons();
    await boletimPrevObsComponentsPage.clickOnLastDeleteButton();

    boletimPrevObsDeleteDialog = new BoletimPrevObsDeleteDialog();
    expect(await boletimPrevObsDeleteDialog.getDialogTitle()).to.eq('nucleoApp.boletimPrevObs.delete.question');
    await boletimPrevObsDeleteDialog.clickOnConfirmButton();

    expect(await boletimPrevObsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
