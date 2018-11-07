import { element, by, ElementFinder } from 'protractor';

export default class ToyUpdatePage {
  pageTitle: ElementFinder = element(by.id('shopApp.toy.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  recommendedAgeInput: ElementFinder = element(by.css('input#toy-recommendedAge'));
  genderInput: ElementFinder = element(by.css('input#toy-gender'));
  purchaseDateInput: ElementFinder = element(by.css('input#toy-purchaseDate'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setRecommendedAgeInput(recommendedAge) {
    await this.recommendedAgeInput.sendKeys(recommendedAge);
  }

  async getRecommendedAgeInput() {
    return this.recommendedAgeInput.getAttribute('value');
  }

  async setGenderInput(gender) {
    await this.genderInput.sendKeys(gender);
  }

  async getGenderInput() {
    return this.genderInput.getAttribute('value');
  }

  async setPurchaseDateInput(purchaseDate) {
    await this.purchaseDateInput.sendKeys(purchaseDate);
  }

  async getPurchaseDateInput() {
    return this.purchaseDateInput.getAttribute('value');
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
