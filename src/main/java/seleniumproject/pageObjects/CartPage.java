package seleniumproject.pageObjects;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import seleniumproject.AbstractComponents.AbstractComponent;

public class CartPage extends AbstractComponent {

	WebDriver driver;
	
	public CartPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css=".cartSection h3")
	List<WebElement> addedProducts;
	
	@FindBy(xpath = "//li[@class='totalRow']/button")
	WebElement checkoutButton;
	
	public boolean validateProduct(String product) {
		return addedProducts.stream().anyMatch(cartProduct->cartProduct.getText().equalsIgnoreCase(product));
	}
	
	public CheckoutPage checkout() {
		checkoutButton.click();
		CheckoutPage checkoutPage = new CheckoutPage(driver);
		return checkoutPage;
	}
}
