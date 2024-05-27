package seleniumproject.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import seleniumproject.pageObjects.LandingPage;

public class BaseTest {
	
	public WebDriver driver;
	public LandingPage landingPage;

	public WebDriver initializeDriver() throws IOException {
		
		Properties properties = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+ 
				"//src//main//java//seleniumproject//resources//GlobalData.properties");
		properties.load(fis);
		String browserType = System.getProperty("broswer")!= null ? System.getProperty("browser") : properties.getProperty("browser");
				
	    switch (browserType.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            default:
                throw new IllegalArgumentException("Invalid browser type: " + browserType);
        }
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
        return driver;
        
		/*WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.manage().window().maximize();
		return driver;*/
		
		
		
	}
	
	@BeforeMethod
	public LandingPage launchApplication() throws IOException {
		driver = initializeDriver();
		landingPage = new LandingPage(driver);
		landingPage.goTo();
		return landingPage;
	}
	
	//@AfterMethod
	public void finishExecution() {
		if (driver != null) {
            driver.close(); // Quit instead of close for proper cleanup
        }
	}
	
	public List<HashMap<String, String>> getJsonDataToMap(String filePäth) throws IOException {
		
		//read json to string
		String jsonContent = FileUtils.readFileToString(new File(filePäth),StandardCharsets.UTF_8);
		
		//String to Hashmaps using Jackson Databind
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data= mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {});
		return data;
	}
	
	public String getScreenshots(String testCaseName, WebDriver driver) throws IOException {
		try {
			TakesScreenshot takesScreenshot = (TakesScreenshot)driver;
			File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
			File destinationFile = new File(System.getProperty("user.dir")+"\\reports\\"+testCaseName+".png");
			FileUtils.copyFile(sourceFile, destinationFile);
			return System.getProperty("user.dir")+"\\reports\\"+testCaseName+".png";
		} catch (Exception e) {
			e.printStackTrace();
            return null;
		}
	}
	
	
}
