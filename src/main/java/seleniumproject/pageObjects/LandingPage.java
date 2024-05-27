package seleniumproject.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import seleniumproject.AbstractComponents.AbstractComponent;

public class LandingPage extends AbstractComponent {

	WebDriver driver;
	
	public LandingPage(WebDriver driver) {
		super(driver);
		// Initialize the driver
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	
	//WebElement userEmail = driver.findElement(By.id("userEmail"));
	//WebElement userPass = driver.findElement(By.id("userPassword"));
	
	//Page Factory
	
	@FindBy(id="userEmail")
	WebElement userEmail;
	
	@FindBy(id="userPassword")
	WebElement userPassword;
	
	@FindBy(id="login")
	WebElement loginButton;
	
	@FindBy(css = "[class*='flyInOut']")
	WebElement errorMessage;
	
	public void goTo() {
		driver.get("https://rahulshettyacademy.com/client/");
	}
	
	public ProductCatalogue loginApplication(String email, String password) {
		userEmail.sendKeys(email);
		userPassword.sendKeys(password);
		loginButton.click();
		ProductCatalogue productCatalogue = new ProductCatalogue(driver);
		return productCatalogue;
	}
	
	public String getErrorMessage() {
		waitForWebElementToAppear(errorMessage);
		return errorMessage.getText();
	}
}
