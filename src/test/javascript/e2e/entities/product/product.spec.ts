/* tslint:disable no-unused-expression */
import { browser, protractor } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import ProductComponentsPage from './product.page-object';
import { ProductDeleteDialog } from './product.page-object';
import ProductUpdatePage from './product-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('Product e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let productUpdatePage: ProductUpdatePage;
  let productComponentsPage: ProductComponentsPage;
  let productDeleteDialog: ProductDeleteDialog;

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

  it('should load Products', async () => {
    await navBarPage.getEntityPage('product');
    productComponentsPage = new ProductComponentsPage();
    expect(await productComponentsPage.getTitle().getText()).to.match(/Products/);
  });

  it('should load create Product page', async () => {
    await productComponentsPage.clickOnCreateButton();
    productUpdatePage = new ProductUpdatePage();
    expect(await productUpdatePage.getPageTitle().getAttribute('id')).to.match(/shopApp.product.home.createOrEditLabel/);
  });

  it('should create and save Products', async () => {
    const nbButtonsBeforeCreate = await productComponentsPage.countDeleteButtons();

    await productUpdatePage.setBrandInput('brand');
    expect(await productUpdatePage.getBrandInput()).to.match(/brand/);
    await productUpdatePage.setNameInput('name');
    expect(await productUpdatePage.getNameInput()).to.match(/name/);
    await productUpdatePage.setReleaseDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await productUpdatePage.getReleaseDateInput()).to.contain('2001-01-01T02:30');
    await productUpdatePage.setCommentInput('comment');
    expect(await productUpdatePage.getCommentInput()).to.match(/comment/);
    await productUpdatePage.setManufactureOriginInput('manufactureOrigin');
    expect(await productUpdatePage.getManufactureOriginInput()).to.match(/manufactureOrigin/);
    await productUpdatePage.setMeterialsInput('meterials');
    expect(await productUpdatePage.getMeterialsInput()).to.match(/meterials/);
    await productUpdatePage.setExternalUrlInput('externalUrl');
    expect(await productUpdatePage.getExternalUrlInput()).to.match(/externalUrl/);
    await productUpdatePage.setOriginalPriceInput('5');
    expect(await productUpdatePage.getOriginalPriceInput()).to.eq('5');
    await productUpdatePage.setActualPriceInput('5');
    expect(await productUpdatePage.getActualPriceInput()).to.eq('5');
    const selectedGarantie = await productUpdatePage.getGarantieInput().isSelected();
    if (selectedGarantie) {
      await productUpdatePage.getGarantieInput().click();
      expect(await productUpdatePage.getGarantieInput().isSelected()).to.be.false;
    } else {
      await productUpdatePage.getGarantieInput().click();
      expect(await productUpdatePage.getGarantieInput().isSelected()).to.be.true;
    }
    await productUpdatePage.setPhotoInput('photo');
    expect(await productUpdatePage.getPhotoInput()).to.match(/photo/);
    await productUpdatePage.setStateInput('state');
    expect(await productUpdatePage.getStateInput()).to.match(/state/);
    await productUpdatePage.setCreationDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await productUpdatePage.getCreationDateInput()).to.contain('2001-01-01T02:30');
    await productUpdatePage.setModificationDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await productUpdatePage.getModificationDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(productUpdatePage.getSaveButton());
    await productUpdatePage.save();
    await waitUntilHidden(productUpdatePage.getSaveButton());
    expect(await productUpdatePage.getSaveButton().isPresent()).to.be.false;

    await productComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await productComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last Product', async () => {
    await productComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await productComponentsPage.countDeleteButtons();
    await productComponentsPage.clickOnLastDeleteButton();

    productDeleteDialog = new ProductDeleteDialog();
    expect(await productDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/shopApp.product.delete.question/);
    await productDeleteDialog.clickOnConfirmButton();

    await productComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await productComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
