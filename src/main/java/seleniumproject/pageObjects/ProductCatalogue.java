package seleniumproject.pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import seleniumproject.AbstractComponents.AbstractComponent;

public class ProductCatalogue extends AbstractComponent{

	WebDriver driver;
	
	public ProductCatalogue(WebDriver driver) {
		super(driver);
		// Initialize the driver
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	//Page Factory
	
	//List<WebElement> products= driver.findElements(By.cssSelector(".mb-3"));
	
	@FindBy(css=".mb-3")
	List<WebElement> products;
	
	@FindBy(css = ".ng-animating")
	WebElement spinner;
	
	
	
	By byProd = By.cssSelector(".mb-3");
	By addToCart = By.cssSelector(".card-body button:last-of-type");
	By toast = By.cssSelector("#toast-container");
	

	public List<WebElement> getProductList() {
		waitForElementToAppear(byProd);
		return products;
	}
	
	public WebElement getProductByName(String productName) {
		WebElement prod = getProductList().stream().filter(product->
		product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
		return prod;
	}
	
	public void addProductToCart(String productName) {
		WebElement prod = getProductByName(productName);
		prod.findElement(addToCart).click();
		waitForElementToAppear(toast);
		waitForElementToDissappear(spinner);
	}
	
	
}
