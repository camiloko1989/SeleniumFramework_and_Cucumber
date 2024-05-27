package seleniumproject.TestComponents;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import seleniumproject.resources.ExtentReportNG;

public class Listeners extends BaseTest implements ITestListener{
	
	ExtentTest test;
	ExtentReports extent = ExtentReportNG.getReport();
	ThreadLocal<ExtentTest> extentTest= new ThreadLocal<ExtentTest>(); //helps when there is concurrency
	
	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		test = extent.createTest(result.getMethod().getMethodName());
		extentTest.set(test); //creates a unique thread id for each test
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		extentTest.get().log(Status.PASS, "Test Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		/* TODO Auto-generated method stub
		test.fail(result.getThrowable());
		//add screenshot
		String filePath = null;
		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		try {
			filePath = getScreenshots(result.getMethod().getMethodName(), driver);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		test.addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());*/
		extentTest.get().fail(result.getThrowable());
        WebDriver driver = getDriverInstance(result); //unique thread id -> test
        try {
            String filePath = getScreenshots(result.getMethod().getMethodName(), driver);
            test.addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		extent.flush();
	}
	
	private WebDriver getDriverInstance(ITestResult result) {
        try {
            return (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
