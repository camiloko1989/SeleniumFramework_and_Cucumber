package seleniumproject.TestComponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer {

	int count=0; //
	int maxTry=1; //maximum of times the cases will be re-run
	
	@Override
	public boolean retry(ITestResult result) {

		if(count<maxTry) {
			return true;
		}
		
		return false;
	}

	
}
