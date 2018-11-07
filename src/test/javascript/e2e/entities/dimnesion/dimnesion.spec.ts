/* tslint:disable no-unused-expression */
import { browser } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import DimnesionComponentsPage from './dimnesion.page-object';
import { DimnesionDeleteDialog } from './dimnesion.page-object';
import DimnesionUpdatePage from './dimnesion-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('Dimnesion e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let dimnesionUpdatePage: DimnesionUpdatePage;
  let dimnesionComponentsPage: DimnesionComponentsPage;
  let dimnesionDeleteDialog: DimnesionDeleteDialog;

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

  it('should load Dimnesions', async () => {
    await navBarPage.getEntityPage('dimnesion');
    dimnesionComponentsPage = new DimnesionComponentsPage();
    expect(await dimnesionComponentsPage.getTitle().getText()).to.match(/Dimnesions/);
  });

  it('should load create Dimnesion page', async () => {
    await dimnesionComponentsPage.clickOnCreateButton();
    dimnesionUpdatePage = new DimnesionUpdatePage();
    expect(await dimnesionUpdatePage.getPageTitle().getAttribute('id')).to.match(/shopApp.dimnesion.home.createOrEditLabel/);
  });

  it('should create and save Dimnesions', async () => {
    const nbButtonsBeforeCreate = await dimnesionComponentsPage.countDeleteButtons();

    await dimnesionUpdatePage.setLengthInput('5');
    expect(await dimnesionUpdatePage.getLengthInput()).to.eq('5');
    await dimnesionUpdatePage.setWidthInput('5');
    expect(await dimnesionUpdatePage.getWidthInput()).to.eq('5');
    await dimnesionUpdatePage.setHeightInput('5');
    expect(await dimnesionUpdatePage.getHeightInput()).to.eq('5');
    await dimnesionUpdatePage.setWeightInput('5');
    expect(await dimnesionUpdatePage.getWeightInput()).to.eq('5');
    await waitUntilDisplayed(dimnesionUpdatePage.getSaveButton());
    await dimnesionUpdatePage.save();
    await waitUntilHidden(dimnesionUpdatePage.getSaveButton());
    expect(await dimnesionUpdatePage.getSaveButton().isPresent()).to.be.false;

    await dimnesionComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await dimnesionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last Dimnesion', async () => {
    await dimnesionComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await dimnesionComponentsPage.countDeleteButtons();
    await dimnesionComponentsPage.clickOnLastDeleteButton();

    dimnesionDeleteDialog = new DimnesionDeleteDialog();
    expect(await dimnesionDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/shopApp.dimnesion.delete.question/);
    await dimnesionDeleteDialog.clickOnConfirmButton();

    await dimnesionComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await dimnesionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
