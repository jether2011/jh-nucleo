import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  PlanoRedeComponentsPage,
  /* PlanoRedeDeleteDialog, */
  PlanoRedeUpdatePage,
} from './plano-rede.page-object';

const expect = chai.expect;

describe('PlanoRede e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let planoRedeComponentsPage: PlanoRedeComponentsPage;
  let planoRedeUpdatePage: PlanoRedeUpdatePage;
  /* let planoRedeDeleteDialog: PlanoRedeDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PlanoRedes', async () => {
    await navBarPage.goToEntity('plano-rede');
    planoRedeComponentsPage = new PlanoRedeComponentsPage();
    await browser.wait(ec.visibilityOf(planoRedeComponentsPage.title), 5000);
    expect(await planoRedeComponentsPage.getTitle()).to.eq('nucleoApp.planoRede.home.title');
    await browser.wait(ec.or(ec.visibilityOf(planoRedeComponentsPage.entities), ec.visibilityOf(planoRedeComponentsPage.noResult)), 1000);
  });

  it('should load create PlanoRede page', async () => {
    await planoRedeComponentsPage.clickOnCreateButton();
    planoRedeUpdatePage = new PlanoRedeUpdatePage();
    expect(await planoRedeUpdatePage.getPageTitle()).to.eq('nucleoApp.planoRede.home.createOrEditLabel');
    await planoRedeUpdatePage.cancel();
  });

  /* it('should create and save PlanoRedes', async () => {
        const nbButtonsBeforeCreate = await planoRedeComponentsPage.countDeleteButtons();

        await planoRedeComponentsPage.clickOnCreateButton();

        await promise.all([
            planoRedeUpdatePage.setNameInput('name'),
            planoRedeUpdatePage.setDescricaoInput('descricao'),
            planoRedeUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            planoRedeUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            planoRedeUpdatePage.planoSelectLastOption(),
            planoRedeUpdatePage.redeSelectLastOption(),
        ]);

        expect(await planoRedeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
        expect(await planoRedeUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
        expect(await planoRedeUpdatePage.getCreatedInput()).to.contain('2001-01-01T02:30', 'Expected created value to be equals to 2000-12-31');
        expect(await planoRedeUpdatePage.getUpdatedInput()).to.contain('2001-01-01T02:30', 'Expected updated value to be equals to 2000-12-31');

        await planoRedeUpdatePage.save();
        expect(await planoRedeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await planoRedeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last PlanoRede', async () => {
        const nbButtonsBeforeDelete = await planoRedeComponentsPage.countDeleteButtons();
        await planoRedeComponentsPage.clickOnLastDeleteButton();

        planoRedeDeleteDialog = new PlanoRedeDeleteDialog();
        expect(await planoRedeDeleteDialog.getDialogTitle())
            .to.eq('nucleoApp.planoRede.delete.question');
        await planoRedeDeleteDialog.clickOnConfirmButton();

        expect(await planoRedeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
