package seleniumproject.stepDefinitions;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.Then.Thens;
import io.cucumber.java.en.When;
import seleniumproject.TestComponents.BaseTest;
import seleniumproject.pageObjects.CartPage;
import seleniumproject.pageObjects.CheckoutPage;
import seleniumproject.pageObjects.ConfirmationPage;
import seleniumproject.pageObjects.LandingPage;
import seleniumproject.pageObjects.ProductCatalogue;

public class StepDefinitionImplementation extends BaseTest{

	public LandingPage landingPage;
	public ProductCatalogue productCatalogue;
	public ConfirmationPage confirmationPage;
	
	@Given("I landed on Ecommerce page")
	public void I_landed_on_Ecommerce_page() throws IOException {
		landingPage = launchApplication();
	}
	
	@Given("^I am logged in with valid username (.+) and password (.+)$")  //(.*) is a regular ex that matches anything
	public void I_am_logged_in_with_valid_username_password (String userName, String password) {
		productCatalogue = landingPage.loginApplication(userName, password);
	}
	
	@When("^I add the product (.+)$")
	public void I_add_product(String productName) {		
		List<WebElement> products= productCatalogue.getProductList();
		productCatalogue.addProductToCart(productName);
	}
	
	@When("^I checkout (.+) and submit order$")
	public void I_checkout_product_and_submit_order(String productName) {		
		CartPage cartPage = landingPage.clickCart();
	 	boolean match = cartPage.validateProduct(productName);
	 	Assert.assertTrue(match);
	 	CheckoutPage checkoutPage = cartPage.checkout();
	 	checkoutPage.selectCountry("colombia");	 	
	 	confirmationPage = checkoutPage.placeOrder();
	}
	
	@Then("verify the {string} message displayed in confirmation page")
	public void verify_message_message_displayed_confirmation_page(String message) {
		String confirmationMessage = confirmationPage.validateConfirmationMessage();
	 	Assert.assertTrue(confirmationMessage.equalsIgnoreCase(confirmationMessage),(message));
	 	driver.close();
	}
	
	@Then("{string} is displayed")
	public void error_message_is_displayed(String message) {
		Assert.assertEquals(message, landingPage.getErrorMessage());
	 	driver.close();
	}

	
	
	
	
}
