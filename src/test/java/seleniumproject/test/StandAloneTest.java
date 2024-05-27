package seleniumproject.test;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class StandAloneTest {

	public static void main(String[] args) throws InterruptedException {
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://rahulshettyacademy.com/client/");
		
		String selectedProduct ="ZARA COAT 3";
		
		//login with credentials
		driver.findElement(By.id("userEmail")).sendKeys("camilojimenez@mail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Abc123456#");
		driver.findElement(By.id("login")).click();
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
		
		//Create list with all products
		List<WebElement> products= driver.findElements(By.cssSelector(".mb-3"));
		
		//find the product Zara Coat 3
	 	WebElement prod = products.stream().filter(product->product.findElement(By.cssSelector("b")).getText().equals(selectedProduct)).findFirst().orElse(null);
	 	
	 	//add it to cart
	 	prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
	 	
	 	//wait until the toast message appears 	
	 	wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
	 	
	 	//wait until the spinner is not visible
	 	wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
	 	
	 	//Click on cart icon
	 	driver.findElement(By.cssSelector("[routerlink*='cart']")).click();
	 	
	 	//find the items added in the cart
	 	List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
	 	
	 	//validate if the product was added to the cart
	 	boolean match = cartProducts.stream().anyMatch(cartProduct->cartProduct.getText().equalsIgnoreCase(selectedProduct));
	 	
	 	Assert.assertTrue(match);
	 	
	 	//click checkout button
	 	driver.findElement(By.xpath("//li[@class='totalRow']/button")).click();
	 	
	 	/*/find select country
	 	driver.findElement(By.xpath("//div[@class='user__address']/div/input")).sendKeys("Col");
	 	Thread.sleep(2000);
	 	driver.findElement(By.xpath("//div[@class='user__address']/div/input")).sendKeys(Keys.DOWN);
	 	
	 	List<WebElement> countries= driver.findElements(By.cssSelector(".ta-results.list-group.ng-star-inserted button span"));
	 	int i=0;
	 	while(!countries.get(i).getText().equals("Colombia")) {
	 		System.out.println(countries.get(i).getText());
	 		driver.findElement(By.cssSelector(".ta-results.list-group.ng-star-inserted button span")).sendKeys(Keys.DOWN);
			i++;
	 	}
	 	*/
	 	
	 	Actions actions = new Actions(driver);
	 	actions.sendKeys(driver.findElement(By.xpath("//div[@class='user__address']/div/input")), "Colombia").build().perform();
	 	
	 	wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
	 	driver.findElement(By.xpath("//button[contains(@class,'ta-item')]")).click();
	 	
	 	//click on Place Order
	 	driver.findElement(By.cssSelector(".action__submit")).click();
	 	
	 	//validate thank you message
	 	String confirmMessage= driver.findElement(By.className("hero-primary")).getText();
	 	Assert.assertTrue(confirmMessage.equalsIgnoreCase("Thankyou for the order."));
	 	driver.quit();
	}

}
