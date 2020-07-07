import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { BoletimPrevisaoComponentsPage, BoletimPrevisaoDeleteDialog, BoletimPrevisaoUpdatePage } from './boletim-previsao.page-object';

const expect = chai.expect;

describe('BoletimPrevisao e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let boletimPrevisaoComponentsPage: BoletimPrevisaoComponentsPage;
  let boletimPrevisaoUpdatePage: BoletimPrevisaoUpdatePage;
  let boletimPrevisaoDeleteDialog: BoletimPrevisaoDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load BoletimPrevisaos', async () => {
    await navBarPage.goToEntity('boletim-previsao');
    boletimPrevisaoComponentsPage = new BoletimPrevisaoComponentsPage();
    await browser.wait(ec.visibilityOf(boletimPrevisaoComponentsPage.title), 5000);
    expect(await boletimPrevisaoComponentsPage.getTitle()).to.eq('nucleoApp.boletimPrevisao.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(boletimPrevisaoComponentsPage.entities), ec.visibilityOf(boletimPrevisaoComponentsPage.noResult)),
      1000
    );
  });

  it('should load create BoletimPrevisao page', async () => {
    await boletimPrevisaoComponentsPage.clickOnCreateButton();
    boletimPrevisaoUpdatePage = new BoletimPrevisaoUpdatePage();
    expect(await boletimPrevisaoUpdatePage.getPageTitle()).to.eq('nucleoApp.boletimPrevisao.home.createOrEditLabel');
    await boletimPrevisaoUpdatePage.cancel();
  });

  it('should create and save BoletimPrevisaos', async () => {
    const nbButtonsBeforeCreate = await boletimPrevisaoComponentsPage.countDeleteButtons();

    await boletimPrevisaoComponentsPage.clickOnCreateButton();

    await promise.all([
      boletimPrevisaoUpdatePage.setNomeInput('nome'),
      boletimPrevisaoUpdatePage.setDescricaoInput('descricao'),
      boletimPrevisaoUpdatePage.setLocalInput('local'),
      boletimPrevisaoUpdatePage.setImgCondicaoTempoInput('5'),
      boletimPrevisaoUpdatePage.setCondicaoTempoInput('condicaoTempo'),
      boletimPrevisaoUpdatePage.setObservacaoInput('observacao'),
      boletimPrevisaoUpdatePage.setGrupoOrdemInput('5'),
      boletimPrevisaoUpdatePage.setOndasInput('ondas'),
      boletimPrevisaoUpdatePage.setTemperaturaDeInput('5'),
      boletimPrevisaoUpdatePage.setTemperaturaAteInput('5'),
      boletimPrevisaoUpdatePage.setVentovelocidademediakmhInput('5'),
      boletimPrevisaoUpdatePage.setVentosObservacaoInput('ventosObservacao'),
      boletimPrevisaoUpdatePage.setTempestadeObservacaoInput('tempestadeObservacao'),
      boletimPrevisaoUpdatePage.setChuvaObservacaoInput('chuvaObservacao'),
      boletimPrevisaoUpdatePage.setCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      boletimPrevisaoUpdatePage.setUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      boletimPrevisaoUpdatePage.boletimSelectLastOption(),
      boletimPrevisaoUpdatePage.boletimPrevObsSelectLastOption(),
      boletimPrevisaoUpdatePage.intensidadeChuvaSelectLastOption(),
      boletimPrevisaoUpdatePage.umidadeArSelectLastOption(),
      boletimPrevisaoUpdatePage.alvoSelectLastOption(),
      boletimPrevisaoUpdatePage.pontosCardeaisSelectLastOption(),
      boletimPrevisaoUpdatePage.ventoVmFaixaSelectLastOption(),
      boletimPrevisaoUpdatePage.tempestadeProbabilidadeSelectLastOption(),
      boletimPrevisaoUpdatePage.tempestadeNivelSelectLastOption(),
      boletimPrevisaoUpdatePage.acumuladoChuvaFaixaSelectLastOption(),
      boletimPrevisaoUpdatePage.condicaoTempoSelectLastOption(),
    ]);

    expect(await boletimPrevisaoUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await boletimPrevisaoUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    expect(await boletimPrevisaoUpdatePage.getLocalInput()).to.eq('local', 'Expected Local value to be equals to local');
    expect(await boletimPrevisaoUpdatePage.getImgCondicaoTempoInput()).to.eq('5', 'Expected imgCondicaoTempo value to be equals to 5');
    expect(await boletimPrevisaoUpdatePage.getCondicaoTempoInput()).to.eq(
      'condicaoTempo',
      'Expected CondicaoTempo value to be equals to condicaoTempo'
    );
    expect(await boletimPrevisaoUpdatePage.getObservacaoInput()).to.eq(
      'observacao',
      'Expected Observacao value to be equals to observacao'
    );
    expect(await boletimPrevisaoUpdatePage.getGrupoOrdemInput()).to.eq('5', 'Expected grupoOrdem value to be equals to 5');
    expect(await boletimPrevisaoUpdatePage.getOndasInput()).to.eq('ondas', 'Expected Ondas value to be equals to ondas');
    expect(await boletimPrevisaoUpdatePage.getTemperaturaDeInput()).to.eq('5', 'Expected temperaturaDe value to be equals to 5');
    expect(await boletimPrevisaoUpdatePage.getTemperaturaAteInput()).to.eq('5', 'Expected temperaturaAte value to be equals to 5');
    expect(await boletimPrevisaoUpdatePage.getVentovelocidademediakmhInput()).to.eq(
      '5',
      'Expected ventovelocidademediakmh value to be equals to 5'
    );
    expect(await boletimPrevisaoUpdatePage.getVentosObservacaoInput()).to.eq(
      'ventosObservacao',
      'Expected VentosObservacao value to be equals to ventosObservacao'
    );
    const selectedVentoRajada = boletimPrevisaoUpdatePage.getVentoRajadaInput();
    if (await selectedVentoRajada.isSelected()) {
      await boletimPrevisaoUpdatePage.getVentoRajadaInput().click();
      expect(await boletimPrevisaoUpdatePage.getVentoRajadaInput().isSelected(), 'Expected ventoRajada not to be selected').to.be.false;
    } else {
      await boletimPrevisaoUpdatePage.getVentoRajadaInput().click();
      expect(await boletimPrevisaoUpdatePage.getVentoRajadaInput().isSelected(), 'Expected ventoRajada to be selected').to.be.true;
    }
    expect(await boletimPrevisaoUpdatePage.getTempestadeObservacaoInput()).to.eq(
      'tempestadeObservacao',
      'Expected TempestadeObservacao value to be equals to tempestadeObservacao'
    );
    expect(await boletimPrevisaoUpdatePage.getChuvaObservacaoInput()).to.eq(
      'chuvaObservacao',
      'Expected ChuvaObservacao value to be equals to chuvaObservacao'
    );
    expect(await boletimPrevisaoUpdatePage.getCreatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected created value to be equals to 2000-12-31'
    );
    expect(await boletimPrevisaoUpdatePage.getUpdatedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected updated value to be equals to 2000-12-31'
    );

    await boletimPrevisaoUpdatePage.save();
    expect(await boletimPrevisaoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await boletimPrevisaoComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last BoletimPrevisao', async () => {
    const nbButtonsBeforeDelete = await boletimPrevisaoComponentsPage.countDeleteButtons();
    await boletimPrevisaoComponentsPage.clickOnLastDeleteButton();

    boletimPrevisaoDeleteDialog = new BoletimPrevisaoDeleteDialog();
    expect(await boletimPrevisaoDeleteDialog.getDialogTitle()).to.eq('nucleoApp.boletimPrevisao.delete.question');
    await boletimPrevisaoDeleteDialog.clickOnConfirmButton();

    expect(await boletimPrevisaoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
