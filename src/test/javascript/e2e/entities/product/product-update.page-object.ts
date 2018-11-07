import { element, by, ElementFinder } from 'protractor';

export default class ProductUpdatePage {
  pageTitle: ElementFinder = element(by.id('shopApp.product.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  brandInput: ElementFinder = element(by.css('input#product-brand'));
  nameInput: ElementFinder = element(by.css('input#product-name'));
  releaseDateInput: ElementFinder = element(by.css('input#product-releaseDate'));
  commentInput: ElementFinder = element(by.css('input#product-comment'));
  manufactureOriginInput: ElementFinder = element(by.css('input#product-manufactureOrigin'));
  meterialsInput: ElementFinder = element(by.css('input#product-meterials'));
  externalUrlInput: ElementFinder = element(by.css('input#product-externalUrl'));
  originalPriceInput: ElementFinder = element(by.css('input#product-originalPrice'));
  actualPriceInput: ElementFinder = element(by.css('input#product-actualPrice'));
  garantieInput: ElementFinder = element(by.css('input#product-garantie'));
  photoInput: ElementFinder = element(by.css('input#product-photo'));
  stateInput: ElementFinder = element(by.css('input#product-state'));
  creationDateInput: ElementFinder = element(by.css('input#product-creationDate'));
  modificationDateInput: ElementFinder = element(by.css('input#product-modificationDate'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setBrandInput(brand) {
    await this.brandInput.sendKeys(brand);
  }

  async getBrandInput() {
    return this.brandInput.getAttribute('value');
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return this.nameInput.getAttribute('value');
  }

  async setReleaseDateInput(releaseDate) {
    await this.releaseDateInput.sendKeys(releaseDate);
  }

  async getReleaseDateInput() {
    return this.releaseDateInput.getAttribute('value');
  }

  async setCommentInput(comment) {
    await this.commentInput.sendKeys(comment);
  }

  async getCommentInput() {
    return this.commentInput.getAttribute('value');
  }

  async setManufactureOriginInput(manufactureOrigin) {
    await this.manufactureOriginInput.sendKeys(manufactureOrigin);
  }

  async getManufactureOriginInput() {
    return this.manufactureOriginInput.getAttribute('value');
  }

  async setMeterialsInput(meterials) {
    await this.meterialsInput.sendKeys(meterials);
  }

  async getMeterialsInput() {
    return this.meterialsInput.getAttribute('value');
  }

  async setExternalUrlInput(externalUrl) {
    await this.externalUrlInput.sendKeys(externalUrl);
  }

  async getExternalUrlInput() {
    return this.externalUrlInput.getAttribute('value');
  }

  async setOriginalPriceInput(originalPrice) {
    await this.originalPriceInput.sendKeys(originalPrice);
  }

  async getOriginalPriceInput() {
    return this.originalPriceInput.getAttribute('value');
  }

  async setActualPriceInput(actualPrice) {
    await this.actualPriceInput.sendKeys(actualPrice);
  }

  async getActualPriceInput() {
    return this.actualPriceInput.getAttribute('value');
  }

  getGarantieInput() {
    return this.garantieInput;
  }
  async setPhotoInput(photo) {
    await this.photoInput.sendKeys(photo);
  }

  async getPhotoInput() {
    return this.photoInput.getAttribute('value');
  }

  async setStateInput(state) {
    await this.stateInput.sendKeys(state);
  }

  async getStateInput() {
    return this.stateInput.getAttribute('value');
  }

  async setCreationDateInput(creationDate) {
    await this.creationDateInput.sendKeys(creationDate);
  }

  async getCreationDateInput() {
    return this.creationDateInput.getAttribute('value');
  }

  async setModificationDateInput(modificationDate) {
    await this.modificationDateInput.sendKeys(modificationDate);
  }

  async getModificationDateInput() {
    return this.modificationDateInput.getAttribute('value');
  }

  async save() {
    await this.saveButton.click();
  }

  async cancel() {
    await this.cancelButton.click();
  }

  getSaveButton() {
    return this.saveButton;
  }
}
