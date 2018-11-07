import { element, by, ElementFinder } from 'protractor';

export default class DimnesionUpdatePage {
  pageTitle: ElementFinder = element(by.id('shopApp.dimnesion.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  lengthInput: ElementFinder = element(by.css('input#dimnesion-length'));
  widthInput: ElementFinder = element(by.css('input#dimnesion-width'));
  heightInput: ElementFinder = element(by.css('input#dimnesion-height'));
  weightInput: ElementFinder = element(by.css('input#dimnesion-weight'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setLengthInput(length) {
    await this.lengthInput.sendKeys(length);
  }

  async getLengthInput() {
    return this.lengthInput.getAttribute('value');
  }

  async setWidthInput(width) {
    await this.widthInput.sendKeys(width);
  }

  async getWidthInput() {
    return this.widthInput.getAttribute('value');
  }

  async setHeightInput(height) {
    await this.heightInput.sendKeys(height);
  }

  async getHeightInput() {
    return this.heightInput.getAttribute('value');
  }

  async setWeightInput(weight) {
    await this.weightInput.sendKeys(weight);
  }

  async getWeightInput() {
    return this.weightInput.getAttribute('value');
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
