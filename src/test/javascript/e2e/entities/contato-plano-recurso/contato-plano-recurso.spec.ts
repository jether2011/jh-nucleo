import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  ContatoPlanoRecursoComponentsPage,
  /* ContatoPlanoRecursoDeleteDialog, */
  ContatoPlanoRecursoUpdatePage,
} from './contato-plano-recurso.page-object';

const expect = chai.expect;

describe('ContatoPlanoRecurso e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let contatoPlanoRecursoComponentsPage: ContatoPlanoRecursoComponentsPage;
  let contatoPlanoRecursoUpdatePage: ContatoPlanoRecursoUpdatePage;
  /* let contatoPlanoRecursoDeleteDialog: ContatoPlanoRecursoDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ContatoPlanoRecursos', async () => {
    await navBarPage.goToEntity('contato-plano-recurso');
    contatoPlanoRecursoComponentsPage = new ContatoPlanoRecursoComponentsPage();
    await browser.wait(ec.visibilityOf(contatoPlanoRecursoComponentsPage.title), 5000);
    expect(await contatoPlanoRecursoComponentsPage.getTitle()).to.eq('nucleoApp.contatoPlanoRecurso.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(contatoPlanoRecursoComponentsPage.entities), ec.visibilityOf(contatoPlanoRecursoComponentsPage.noResult)),
      1000
    );
  });

  it('should load create ContatoPlanoRecurso page', async () => {
    await contatoPlanoRecursoComponentsPage.clickOnCreateButton();
    contatoPlanoRecursoUpdatePage = new ContatoPlanoRecursoUpdatePage();
    expect(await contatoPlanoRecursoUpdatePage.getPageTitle()).to.eq('nucleoApp.contatoPlanoRecurso.home.createOrEditLabel');
    await contatoPlanoRecursoUpdatePage.cancel();
  });

  /* it('should create and save ContatoPlanoRecursos', async () => {
        const nbButtonsBeforeCreate = await contatoPlanoRecursoComponentsPage.countDeleteButtons();

        await contatoPlanoRecursoComponentsPage.clickOnCreateButton();

        await promise.all([
            contatoPlanoRecursoUpdatePage.setNomeInput('nome'),
            contatoPlanoRecursoUpdatePage.setDescricaoInput('descricao'),
            contatoPlanoRecursoUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            contatoPlanoRecursoUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            contatoPlanoRecursoUpdatePage.contatoSelectLastOption(),
            contatoPlanoRecursoUpdatePage.planoRecursoSelectLastOption(),
        ]);

        expect(await contatoPlanoRecursoUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
        expect(await contatoPlanoRecursoUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
        expect(await contatoPlanoRecursoUpdatePage.getCreatedInput()).to.contain('2001-01-01T02:30', 'Expected created value to be equals to 2000-12-31');
        expect(await contatoPlanoRecursoUpdatePage.getUpdatedInput()).to.contain('2001-01-01T02:30', 'Expected updated value to be equals to 2000-12-31');

        await contatoPlanoRecursoUpdatePage.save();
        expect(await contatoPlanoRecursoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await contatoPlanoRecursoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last ContatoPlanoRecurso', async () => {
        const nbButtonsBeforeDelete = await contatoPlanoRecursoComponentsPage.countDeleteButtons();
        await contatoPlanoRecursoComponentsPage.clickOnLastDeleteButton();

        contatoPlanoRecursoDeleteDialog = new ContatoPlanoRecursoDeleteDialog();
        expect(await contatoPlanoRecursoDeleteDialog.getDialogTitle())
            .to.eq('nucleoApp.contatoPlanoRecurso.delete.question');
        await contatoPlanoRecursoDeleteDialog.clickOnConfirmButton();

        expect(await contatoPlanoRecursoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
