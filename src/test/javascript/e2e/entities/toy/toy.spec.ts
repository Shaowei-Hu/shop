/* tslint:disable no-unused-expression */
import { browser, protractor } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import ToyComponentsPage from './toy.page-object';
import { ToyDeleteDialog } from './toy.page-object';
import ToyUpdatePage from './toy-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('Toy e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let toyUpdatePage: ToyUpdatePage;
  let toyComponentsPage: ToyComponentsPage;
  let toyDeleteDialog: ToyDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.waitUntilDisplayed();

    await signInPage.username.sendKeys('admin');
    await signInPage.password.sendKeys('admin');
    await signInPage.loginButton.click();
    await signInPage.waitUntilHidden();

    await waitUntilDisplayed(navBarPage.entityMenu);
  });

  it('should load Toys', async () => {
    await navBarPage.getEntityPage('toy');
    toyComponentsPage = new ToyComponentsPage();
    expect(await toyComponentsPage.getTitle().getText()).to.match(/Toys/);
  });

  it('should load create Toy page', async () => {
    await toyComponentsPage.clickOnCreateButton();
    toyUpdatePage = new ToyUpdatePage();
    expect(await toyUpdatePage.getPageTitle().getAttribute('id')).to.match(/shopApp.toy.home.createOrEditLabel/);
  });

  it('should create and save Toys', async () => {
    const nbButtonsBeforeCreate = await toyComponentsPage.countDeleteButtons();

    await toyUpdatePage.setRecommendedAgeInput('recommendedAge');
    expect(await toyUpdatePage.getRecommendedAgeInput()).to.match(/recommendedAge/);
    await toyUpdatePage.setGenderInput('gender');
    expect(await toyUpdatePage.getGenderInput()).to.match(/gender/);
    await toyUpdatePage.setPurchaseDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await toyUpdatePage.getPurchaseDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(toyUpdatePage.getSaveButton());
    await toyUpdatePage.save();
    await waitUntilHidden(toyUpdatePage.getSaveButton());
    expect(await toyUpdatePage.getSaveButton().isPresent()).to.be.false;

    await toyComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await toyComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last Toy', async () => {
    await toyComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await toyComponentsPage.countDeleteButtons();
    await toyComponentsPage.clickOnLastDeleteButton();

    toyDeleteDialog = new ToyDeleteDialog();
    expect(await toyDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/shopApp.toy.delete.question/);
    await toyDeleteDialog.clickOnConfirmButton();

    await toyComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await toyComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
