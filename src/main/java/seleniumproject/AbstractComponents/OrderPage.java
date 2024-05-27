package seleniumproject.AbstractComponents;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import seleniumproject.pageObjects.CartPage;

public class OrderPage extends CartPage {

	WebDriver driver;
	
	public OrderPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css = ".totalRow button")
	WebElement checkout;
	
	@FindBy(css = "tr td:nth-child(3)")
	private List<WebElement> productNames;
	
	public boolean verifyOrderDisplayed(String productName) {
		Boolean match= productNames.stream().anyMatch(product
				-> product.getText().equalsIgnoreCase(productName));
		return match;
	}

}
