package seleniumproject.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import seleniumproject.AbstractComponents.AbstractComponent;

public class CheckoutPage extends AbstractComponent{

	WebDriver driver;
	
	
	public CheckoutPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@class='user__address']/div/input")
	WebElement country;
	
	@FindBy(xpath = "//button[contains(@class,'ta-item')]")
	WebElement item;
	
	@FindBy(css = ".action__submit")
	WebElement submitButton;
	
	By results= By.cssSelector(".ta-results");
	
	public void selectCountry(String countryName) {
		
		Actions actions = new Actions(driver);
		actions.sendKeys(country, countryName).build().perform();
		waitForElementToAppear(results);
		item.click();
	}
	
	public ConfirmationPage placeOrder() {
		submitButton.click();
		return new ConfirmationPage(driver);
		 
	}
}
