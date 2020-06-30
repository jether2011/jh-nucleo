import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AvisoComponentsPage, AvisoDeleteDialog, AvisoUpdatePage } from './aviso.page-object';

const expect = chai.expect;

describe('Aviso e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let avisoComponentsPage: AvisoComponentsPage;
  let avisoUpdatePage: AvisoUpdatePage;
  let avisoDeleteDialog: AvisoDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Avisos', async () => {
    await navBarPage.goToEntity('aviso');
    avisoComponentsPage = new AvisoComponentsPage();
    await browser.wait(ec.visibilityOf(avisoComponentsPage.title), 5000);
    expect(await avisoComponentsPage.getTitle()).to.eq('nucleoApp.aviso.home.title');
    await browser.wait(ec.or(ec.visibilityOf(avisoComponentsPage.entities), ec.visibilityOf(avisoComponentsPage.noResult)), 1000);
  });

  it('should load create Aviso page', async () => {
    await avisoComponentsPage.clickOnCreateButton();
    avisoUpdatePage = new AvisoUpdatePage();
    expect(await avisoUpdatePage.getPageTitle()).to.eq('nucleoApp.aviso.home.createOrEditLabel');
    await avisoUpdatePage.cancel();
  });

  it('should create and save Avisos', async () => {
    const nbButtonsBeforeCreate = await avisoComponentsPage.countDeleteButtons();

    await avisoComponentsPage.clickOnCreateButton();

    await promise.all([
      avisoUpdatePage.setNomeInput('nome'),
      avisoUpdatePage.setDescricaoInput('descricao'),
      avisoUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      avisoUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      avisoUpdatePage.avisoTipoSelectLastOption(),
    ]);

    expect(await avisoUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await avisoUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    const selectedEnviado = avisoUpdatePage.getEnviadoInput();
    if (await selectedEnviado.isSelected()) {
      await avisoUpdatePage.getEnviadoInput().click();
      expect(await avisoUpdatePage.getEnviadoInput().isSelected(), 'Expected enviado not to be selected').to.be.false;
    } else {
      await avisoUpdatePage.getEnviadoInput().click();
      expect(await avisoUpdatePage.getEnviadoInput().isSelected(), 'Expected enviado to be selected').to.be.true;
    }
    expect(await avisoUpdatePage.getCreatedInput()).to.contain('2001-01-01T02:30', 'Expected created value to be equals to 2000-12-31');
    expect(await avisoUpdatePage.getUpdatedInput()).to.contain('2001-01-01T02:30', 'Expected updated value to be equals to 2000-12-31');

    await avisoUpdatePage.save();
    expect(await avisoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await avisoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Aviso', async () => {
    const nbButtonsBeforeDelete = await avisoComponentsPage.countDeleteButtons();
    await avisoComponentsPage.clickOnLastDeleteButton();

    avisoDeleteDialog = new AvisoDeleteDialog();
    expect(await avisoDeleteDialog.getDialogTitle()).to.eq('nucleoApp.aviso.delete.question');
    await avisoDeleteDialog.clickOnConfirmButton();

    expect(await avisoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
