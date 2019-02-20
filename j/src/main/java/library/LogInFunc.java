package library;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import objectRepo.*;

public class LogInFunc extends GeneralFunc implements LogInObj {
	
	GeneralFunc generalFun = new GeneralFunc();

	public LogInFunc() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	public void LoginTest(WebDriver driver, String userName, String password) {
		try {
			
			if (driver.toString().contains("null")) {
				try {
					
					InitialSetUp brwObj = new InitialSetUp();
					brwObj.instantiateWebDriver();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			driver.manage().timeouts().implicitlyWait(implicitTimeout, TimeUnit.SECONDS);
			generalFun.buildDriverElement(logUserName).clear();
			Thread.sleep(1000);
			generalFun.buildDriverElement(logUserName).sendKeys(userName);
			generalFun.buildDriverElement(logNext).click();
			
			Thread.sleep(4000);
			generalFun.buildDriverElement(logPassword).click();
			generalFun.buildDriverElement(logPassword).clear();
			generalFun.buildDriverElement(logPassword).sendKeys(password);
			Thread.sleep(1000);
			generalFun.buildDriverElement(logSignIn).click();
			
			if (driver.findElements(generalFun.buildLocator(logRecoveryEmailOption)).size() != 0) {
				generalFun.buildDriverElement(logRecoveryEmailOption).click();
			}
			
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			if (driver.findElements(generalFun.buildLocator(logRecoveryEmailId)).size() != 0) {
				generalFun.buildDriverElement(logRecoveryEmailId).click();
				generalFun.buildDriverElement(logRecoveryEmailId).sendKeys(generalFun.getExcelValue("LoginData.xlsx", "Login", "RecoveryEmailId",1));
				Thread.sleep(1000);
				generalFun.buildDriverElement(logDone).click();
			}
			
			driver.manage().timeouts().implicitlyWait(implicitTimeout, TimeUnit.SECONDS);
			generalFun.pageSync();
			generalFun.isElementExists(generalFun.buildDriverElement(grid_page), "(Function) : User login successfully");
									
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void LogoutTest(WebDriver driver) {
		driver.manage().timeouts().implicitlyWait(implicitTimeout, TimeUnit.SECONDS);
		generalFun.buildDriverElement(logLogoutDropDown).click();
		generalFun.buildDriverElement(logLogoutButton).click();
		generalFun.isElementExists(generalFun.buildDriverElement(logUserName), "(Function) : User logout successfully");
	}
	
}