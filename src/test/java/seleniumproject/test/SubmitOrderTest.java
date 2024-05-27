package seleniumproject.test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.compress.harmony.pack200.NewAttribute;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import seleniumproject.AbstractComponents.OrderPage;
import seleniumproject.TestComponents.BaseTest;
import seleniumproject.TestComponents.Retry;
import seleniumproject.pageObjects.CartPage;
import seleniumproject.pageObjects.CheckoutPage;
import seleniumproject.pageObjects.ConfirmationPage;
import seleniumproject.pageObjects.LandingPage;
import seleniumproject.pageObjects.ProductCatalogue;


public class SubmitOrderTest extends BaseTest {
	@Test(dataProvider = "getData", groups={"Purchase"}, retryAnalyzer = Retry.class)
	public void SubmitOrder(HashMap<String, String> input) throws InterruptedException, IOException {
		//use the method login to perform actions
		//create object of Product Catalogue
		ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"), input.get("password"));
		
		//get list of products
		productCatalogue.getProductList();
			
		//add the product Zara Coat 3
	 	productCatalogue.addProductToCart(input.get("product"));
	 	 	
	 	//Click on cart icon
	 	//create object of CartPage
	 	CartPage cartPage = landingPage.clickCart();
	 	
	 	//validate product in page
	 	boolean match = cartPage.validateProduct(input.get("product"));
	 	Assert.assertTrue(match);
	 		 	
	 	//click on Checkout	 		 	
	 	//create object of Checkout Page
	 	CheckoutPage checkoutPage = cartPage.checkout();

	 	checkoutPage.selectCountry("colombia");	 	
	 	
	 	ConfirmationPage confirmationPage = checkoutPage.placeOrder();
	 	
	 	String confirmationMessage = confirmationPage.validateConfirmationMessage();
	 	Assert.assertTrue(confirmationMessage.equalsIgnoreCase(confirmationMessage),("Thankyou for the order."));
	 	
	}
	
	//now validate that ZARA COAT 3 is displaying to Order Page
	
	@Test(dependsOnMethods = {"SubmitOrder"}, dataProvider = "getData") 
	public void orderHistoryTest(String productName) {
		ProductCatalogue productCatalogue = landingPage.loginApplication("camilojimenez@mail.com", "Abc123456#");
		OrderPage orderPage = productCatalogue.clickOrder();
		Assert.assertTrue(orderPage.verifyOrderDisplayed("Algo")); 
	}
	
	@DataProvider
	public Object[][] getData() throws IOException {
		
		/*create object to send data from here
		return new Object[][] {{"camilojimenez@mail.com", "Abc123456#", "ZARA COAT 3"},{"camiloj@mail.com","Abc1234567#","ADIDAS ORIGINAL"}};
		*/
		
		List<HashMap<String, String>> data= getJsonDataToMap(System.getProperty("user.dir")+"//src//test//java//seleniumproject//data//PurchaseData.json");
		return new Object[][] {{data.get(0)},{data.get(1)}};
	}
	
	

}
