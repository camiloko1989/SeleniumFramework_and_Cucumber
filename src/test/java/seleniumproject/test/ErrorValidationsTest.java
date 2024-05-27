package seleniumproject.test;

import java.io.IOException;
import org.testng.Assert;
import org.testng.annotations.Test;

import seleniumproject.TestComponents.BaseTest;
import seleniumproject.pageObjects.CartPage;
import seleniumproject.pageObjects.CheckoutPage;
import seleniumproject.pageObjects.ConfirmationPage;
import seleniumproject.pageObjects.LandingPage;
import seleniumproject.pageObjects.ProductCatalogue;


public class ErrorValidationsTest extends BaseTest {

	@Test
	public void loginErrorValidation() throws InterruptedException, IOException {
		String selectedProduct ="ZARA COAT 3";		
				
		//use the method login to perform actions
		//create object of Product Catalogue
		landingPage.loginApplication("camilojimenez@mail.com", "Failed123456#");
		Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage()); 
	 
	}
	
	@Test
	public void productErrorValidation() {
		String selectedProduct ="ZARA COAT 3";		
		
		ProductCatalogue productCatalogue = landingPage.loginApplication("camilojimenez@mail.com", "Abc123456#");
		productCatalogue.getProductList();

	 	productCatalogue.addProductToCart(selectedProduct);

	 	CartPage cartPage = landingPage.clickCart();

	 	boolean match = cartPage.validateProduct("Zara 33");
	 	Assert.assertFalse(match);
	}

}
