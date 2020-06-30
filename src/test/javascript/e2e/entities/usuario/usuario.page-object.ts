import { element, by, ElementFinder } from 'protractor';

export class UsuarioComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-usuario div table .btn-danger'));
  title = element.all(by.css('jhi-usuario div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class UsuarioUpdatePage {
  pageTitle = element(by.id('jhi-usuario-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  descricaoInput = element(by.id('field_descricao'));
  emailInput = element(by.id('field_email'));
  senhaInput = element(by.id('field_senha'));
  cnpjInput = element(by.id('field_cnpj'));
  cpfInput = element(by.id('field_cpf'));
  cepInput = element(by.id('field_cep'));
  enderecoInput = element(by.id('field_endereco'));
  numeroInput = element(by.id('field_numero'));
  bairroInput = element(by.id('field_bairro'));
  cidadeInput = element(by.id('field_cidade'));
  estadoInput = element(by.id('field_estado'));
  telefoneInput = element(by.id('field_telefone'));
  faxInput = element(by.id('field_fax'));
  celularInput = element(by.id('field_celular'));
  detalheInput = element(by.id('field_detalhe'));
  bloqueadoInput = element(by.id('field_bloqueado'));
  complementoInput = element(by.id('field_complemento'));
  naoPodeExcluirInput = element(by.id('field_naoPodeExcluir'));
  ultimoAcessoInput = element(by.id('field_ultimoAcesso'));
  senhaFirebaseInput = element(by.id('field_senhaFirebase'));
  createdInput = element(by.id('field_created'));
  updatedInput = element(by.id('field_updated'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
  }

  async setDescricaoInput(descricao: string): Promise<void> {
    await this.descricaoInput.sendKeys(descricao);
  }

  async getDescricaoInput(): Promise<string> {
    return await this.descricaoInput.getAttribute('value');
  }

  async setEmailInput(email: string): Promise<void> {
    await this.emailInput.sendKeys(email);
  }

  async getEmailInput(): Promise<string> {
    return await this.emailInput.getAttribute('value');
  }

  async setSenhaInput(senha: string): Promise<void> {
    await this.senhaInput.sendKeys(senha);
  }

  async getSenhaInput(): Promise<string> {
    return await this.senhaInput.getAttribute('value');
  }

  async setCnpjInput(cnpj: string): Promise<void> {
    await this.cnpjInput.sendKeys(cnpj);
  }

  async getCnpjInput(): Promise<string> {
    return await this.cnpjInput.getAttribute('value');
  }

  async setCpfInput(cpf: string): Promise<void> {
    await this.cpfInput.sendKeys(cpf);
  }

  async getCpfInput(): Promise<string> {
    return await this.cpfInput.getAttribute('value');
  }

  async setCepInput(cep: string): Promise<void> {
    await this.cepInput.sendKeys(cep);
  }

  async getCepInput(): Promise<string> {
    return await this.cepInput.getAttribute('value');
  }

  async setEnderecoInput(endereco: string): Promise<void> {
    await this.enderecoInput.sendKeys(endereco);
  }

  async getEnderecoInput(): Promise<string> {
    return await this.enderecoInput.getAttribute('value');
  }

  async setNumeroInput(numero: string): Promise<void> {
    await this.numeroInput.sendKeys(numero);
  }

  async getNumeroInput(): Promise<string> {
    return await this.numeroInput.getAttribute('value');
  }

  async setBairroInput(bairro: string): Promise<void> {
    await this.bairroInput.sendKeys(bairro);
  }

  async getBairroInput(): Promise<string> {
    return await this.bairroInput.getAttribute('value');
  }

  async setCidadeInput(cidade: string): Promise<void> {
    await this.cidadeInput.sendKeys(cidade);
  }

  async getCidadeInput(): Promise<string> {
    return await this.cidadeInput.getAttribute('value');
  }

  async setEstadoInput(estado: string): Promise<void> {
    await this.estadoInput.sendKeys(estado);
  }

  async getEstadoInput(): Promise<string> {
    return await this.estadoInput.getAttribute('value');
  }

  async setTelefoneInput(telefone: string): Promise<void> {
    await this.telefoneInput.sendKeys(telefone);
  }

  async getTelefoneInput(): Promise<string> {
    return await this.telefoneInput.getAttribute('value');
  }

  async setFaxInput(fax: string): Promise<void> {
    await this.faxInput.sendKeys(fax);
  }

  async getFaxInput(): Promise<string> {
    return await this.faxInput.getAttribute('value');
  }

  async setCelularInput(celular: string): Promise<void> {
    await this.celularInput.sendKeys(celular);
  }

  async getCelularInput(): Promise<string> {
    return await this.celularInput.getAttribute('value');
  }

  async setDetalheInput(detalhe: string): Promise<void> {
    await this.detalheInput.sendKeys(detalhe);
  }

  async getDetalheInput(): Promise<string> {
    return await this.detalheInput.getAttribute('value');
  }

  getBloqueadoInput(): ElementFinder {
    return this.bloqueadoInput;
  }

  async setComplementoInput(complemento: string): Promise<void> {
    await this.complementoInput.sendKeys(complemento);
  }

  async getComplementoInput(): Promise<string> {
    return await this.complementoInput.getAttribute('value');
  }

  getNaoPodeExcluirInput(): ElementFinder {
    return this.naoPodeExcluirInput;
  }

  async setUltimoAcessoInput(ultimoAcesso: string): Promise<void> {
    await this.ultimoAcessoInput.sendKeys(ultimoAcesso);
  }

  async getUltimoAcessoInput(): Promise<string> {
    return await this.ultimoAcessoInput.getAttribute('value');
  }

  async setSenhaFirebaseInput(senhaFirebase: string): Promise<void> {
    await this.senhaFirebaseInput.sendKeys(senhaFirebase);
  }

  async getSenhaFirebaseInput(): Promise<string> {
    return await this.senhaFirebaseInput.getAttribute('value');
  }

  async setCreatedInput(created: string): Promise<void> {
    await this.createdInput.sendKeys(created);
  }

  async getCreatedInput(): Promise<string> {
    return await this.createdInput.getAttribute('value');
  }

  async setUpdatedInput(updated: string): Promise<void> {
    await this.updatedInput.sendKeys(updated);
  }

  async getUpdatedInput(): Promise<string> {
    return await this.updatedInput.getAttribute('value');
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class UsuarioDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-usuario-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-usuario'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
