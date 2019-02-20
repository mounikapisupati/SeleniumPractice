package test.auction;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import com.thoughtworks.selenium.SeleniumException;

import library.InitialSetUp;
import library.LogInFunc;
import objectRepo.BuyOR;
import objectRepo.CommonOR;
import reporting.TestLogger;

 public class BuySearch_ols extends LogInFunc implements BuyOR, CommonOR {

		// Variable or Object declaration
		String username;
		String password;
		LogInFunc logObj = new LogInFunc();
		WebDriverWait driverWait;
		private int loginTimes = 0;
		
		// Methods declaration
		public BuySearch_ols() throws Exception {
			super();
			String loginFN = "LoginData.xlsx";
			String loginSN = "Login";
			int rowNum = 1;
			
			username = logObj.getExcelValue(loginFN, loginSN, "Username", rowNum);
			password = logObj.getExcelValue(loginFN, loginSN, "Password", rowNum);
		}

		private void login() throws InterruptedException {
			if (driver.toString().contains("null")) {
				try {			
					InitialSetUp brwObj = new InitialSetUp();
					brwObj.instantiateWebDriver();
					loginTimes = 0;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		if (loginTimes == 0) {
			driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
			if (driver.findElements(logObj.buildLocator(logUserName)).size() != 0) {
				logObj.LoginTest(driver, username, password);
				Thread.sleep(5000);
			}
			loginTimes = loginTimes + 1;
		}
			driver.manage().timeouts().implicitlyWait(implicitTimeout, TimeUnit.SECONDS);
		}
		
		private void sync() throws InterruptedException {
			if (driver.findElements(logObj.buildLocator(buyLoadingList)).size() != 0) {}
			
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			while(driver.findElements(logObj.buildLocator(buyLoadingList)).size() >= 1){ Thread.sleep(1000);}
			driver.manage().timeouts().implicitlyWait(implicitTimeout, TimeUnit.SECONDS);
		}
		
		// TC Description : Verify user has "Search" option in "Buy" tab
		@Test(groups = "FunctionalTest", testName = "Se_Bu_01", enabled = true, description = "(Buy Tab, Search) - Validate search option is present")
		public void Se_Bu_01() throws InterruptedException {
			try {
				
				String fileName = "Buy.xlsx";
				String sheetName = "Se_Bu_01";
				int rowNum = 1;
				
				this.login();
				//added navigation link code
				if(!logObj.buildDriverElement(page_header).getText().contains("Newline Portal - Underwrite")){
					logObj.buildDriverElement(pages_grid).click();
					logObj.buildDriverElement(comBuyTab).click();
					
				}
				driverWait = new WebDriverWait(driver,explicitTimeout);
				driverWait.until(ExpectedConditions.elementToBeClickable(logObj.buildLocator(buySearchLabel)));
				
				logObj.isElementExists(logObj.buildDriverElement(buySearchLabel), "Search label exists");
				logObj.isElementExists(logObj.buildDriverElement(buySearchField), "Search field exists");
								
				logObj.verifyAttributeText(logObj.buildDriverElement(buySearchField),"placeholder", 
						logObj.getExcelValue(fileName, sheetName, "CompareText", rowNum).trim());
				
				logObj.buildDriverElement(buySearchField).clear();
				logObj.buildDriverElement(buySearchField).sendKeys(logObj.getExcelValue(fileName, sheetName, "EnteredText", rowNum).trim());
				Thread.sleep(1000);				
				TestLogger.log("Info", "Search field is a free text");
				logObj.buildDriverElement(buySearchField).clear();
				
			} catch (SeleniumException | IOException e) {
				e.printStackTrace();
			}
		}
		
		// TC Description : Verify all the records are displayed, when user doesn't enter search value and click on "Search"
		@Test(groups = "FunctionalTest", testName = "Se_Bu_02", enabled = true, description = "(Buy Tab, Search) - Validate search when no search value is present")
		public void Se_Bu_02() throws InterruptedException {
			try {
				
				this.login();
				Thread.sleep(1000);
				//added navigation link code
				if(!logObj.buildDriverElement(page_header).getText().contains("Newline Portal - Underwrite")){
					logObj.buildDriverElement(pages_grid).click();
					logObj.buildDriverElement(comBuyTab).click();
					
				}
				driverWait = new WebDriverWait(driver,explicitTimeout);
				driverWait.until(ExpectedConditions.elementToBeClickable(logObj.buildLocator(buyClearButton)));
				logObj.buildDriverElement(buyClearButton).click();
				Thread.sleep(1000);
				
				logObj.isElementExists(logObj.buildDriverElement(buySearchButton), "Search button exists");
				logObj.buildDriverElement(buySearchButton).click();
				
				this.sync();
				Thread.sleep(15000);
				
				if (driver.findElements(logObj.buildLocator(buyLiveList)).size() == 100) {
					TestLogger.log("Pass", "100 records are loaded in listing page");
				} else {
					TestLogger.log("Fail", "100 records should be loaded in listing page");
				}
				
				logObj.verifyText(logObj.buildDriverElement(buyCurrentListPage), "1");
				TestLogger.log("Info", "Current listing page is : " + logObj.buildDriverElement(buyCurrentListPage).getText().trim());
				TestLogger.log("Info", "Total listing page is : " + logObj.buildDriverElement(buyTotalListPage).getText().trim());
				
				String recordedIdPin = logObj.buildDriverElement(buyPropertyInfoBlock1).getText().trim();
				
				if (recordedIdPin.contains(logObj.buildDriverElement(logObj.modifyObjLocator(buyIdPinInList,"1")).getText().trim())) {
					TestLogger.log("Pass", "First record is selected in the list");
				} else {
					TestLogger.log("Fail", "First record is not selected in the list");
				}	
								
				logObj.buildDriverElement(buySearchField).clear();
				
			} catch (SeleniumException e) {
				e.printStackTrace();
			}
		}
		
		// TC Description : Verify searched records when user search for the full "Address"
		@Test(groups = "FunctionalTest", testName = "Se_Bu_03", enabled = true, description = "(Buy Tab, Search) - Validate search when full Address is searched")
		public void Se_Bu_03() throws InterruptedException {
			try {
				
				String fileName = "Buy.xlsx";
				String sheetName = "Se_Bu_03";
				int rowNum = 1;
				
				this.login();
				//added navigation link code
				if(!logObj.buildDriverElement(page_header).getText().contains("Newline Portal - Underwrite")){
					logObj.buildDriverElement(pages_grid).click();
					logObj.buildDriverElement(comBuyTab).click();
					
				}
				driverWait = new WebDriverWait(driver,explicitTimeout);
				driverWait.until(ExpectedConditions.elementToBeClickable(logObj.buildLocator(buySearchButton)));
				logObj.isElementExists(logObj.buildDriverElement(buySearchField), "Search field exists");
												
				logObj.buildDriverElement(buySearchField).clear();
				Thread.sleep(1000);
				logObj.buildDriverElement(buySearchField).sendKeys(logObj.getExcelValue(fileName, sheetName, "EnteredText", rowNum).trim());
				Thread.sleep(1000);
				logObj.buildDriverElement(buySearchButton).click();
				this.sync();
				
				if (driver.findElements(logObj.buildLocator(buyLiveList)).size() == 1) {
					TestLogger.log("Pass", "1 record is returned, Since full address is given and is unique");
				} else {
					TestLogger.log("Fail", "There are more than 1 record, Enter complete address");
				}
				
				logObj.verifyText(logObj.buildDriverElement(buyCurrentListPage), "1");
				TestLogger.log("Info", "Current listing page is : " + logObj.buildDriverElement(buyCurrentListPage).getText().trim());
				TestLogger.log("Info", "Total listing page is : " + logObj.buildDriverElement(buyTotalListPage).getText().trim());
						
				logObj.verifyText(logObj.buildDriverElement(buyPropertyInfoBlock2), 
						logObj.getExcelValue(fileName, sheetName, "EnteredText", rowNum));
				
				logObj.verifyText(logObj.buildDriverElement(logObj.modifyObjLocator(buyAddressFieldInList,"1")), 
						logObj.getExcelValue(fileName, sheetName, "EnteredText", rowNum));
				
				logObj.buildDriverElement(buySearchField).clear();
				
			} catch (SeleniumException | IOException e) {
				e.printStackTrace();
			}
		}
		
		// TC Description : Verify searched records when user search the entire "Property ID/ ID"
		@Test(groups = "FunctionalTest", testName = "Se_Bu_04", enabled = true, description = "(Buy Tab, Search) - Validate search when property id is searched")
		public void Se_Bu_04() throws InterruptedException {
			try {
				
				String fileName = "Buy.xlsx";
				String sheetName = "Se_Bu_04";
				int rowNum = 1;
				
				this.login();
				//added navigation link code
				if(!logObj.buildDriverElement(page_header).getText().contains("Newline Portal - Underwrite")){
					logObj.buildDriverElement(pages_grid).click();
					logObj.buildDriverElement(comBuyTab).click();
					
				}
				driverWait = new WebDriverWait(driver,explicitTimeout);
				driverWait.until(ExpectedConditions.elementToBeClickable(logObj.buildLocator(buySearchButton)));
				logObj.isElementExists(logObj.buildDriverElement(buySearchField), "Search field exists");
												
				logObj.buildDriverElement(buySearchField).clear();
				Thread.sleep(1000);
				logObj.buildDriverElement(buySearchField).sendKeys(logObj.getExcelValue(fileName, sheetName, "EnteredText", rowNum).trim());
				Thread.sleep(1000);
				logObj.buildDriverElement(buySearchButton).click();
				
				this.sync();
				
				if (driver.findElements(logObj.buildLocator(buyLiveList)).size() == 1) {
					TestLogger.log("Pass", "1 record is returned, Since ID is unique");
				} else {
					TestLogger.log("Fail", "There are more than 1 record, Enter proper Id");
				}
				
				logObj.verifyText(logObj.buildDriverElement(buyCurrentListPage), "1");
				TestLogger.log("Info", "Current listing page is : " + logObj.buildDriverElement(buyCurrentListPage).getText().trim());
				TestLogger.log("Info", "Total listing page is : " + logObj.buildDriverElement(buyTotalListPage).getText().trim());
						
				logObj.verifyText(logObj.buildDriverElement(buyPropertyInfoBlock1), 
						logObj.getExcelValue(fileName, sheetName, "EnteredText", rowNum));
				
			} catch (SeleniumException | IOException e) {
				e.printStackTrace();
			}
		}

		// TC Description : Verify searched records when user search other than "Property ID/ ID" or "Address"
		@Test(groups = "FunctionalTest", testName = "Se_Bu_05", enabled = true, description = "(Buy Tab, Search) - Validate search when user search other than property ID or address")
		public void Se_Bu_05() throws InterruptedException {
			try {
				
				String fileName = "Buy.xlsx";
				String sheetName = "Se_Bu_05";
				int rowNum = 1;
				
				this.login();
				//added navigation link code
				if(!logObj.buildDriverElement(page_header).getText().contains("Newline Portal - Underwrite")){
					logObj.buildDriverElement(pages_grid).click();
					logObj.buildDriverElement(comBuyTab).click();
					
				}
				driverWait = new WebDriverWait(driver,explicitTimeout);
				driverWait.until(ExpectedConditions.elementToBeClickable(logObj.buildLocator(buySearchButton)));
				logObj.isElementExists(logObj.buildDriverElement(buySearchField), "Search field exists");
												
				logObj.buildDriverElement(buySearchField).clear();
				Thread.sleep(1000);
				logObj.buildDriverElement(buySearchField).sendKeys(logObj.getExcelValue(fileName, sheetName, "EnteredText", rowNum).trim());
				Thread.sleep(1000);
				logObj.buildDriverElement(buySearchButton).click();
				
				this.sync();
				
				logObj.isElementExists(logObj.buildDriverElement(comNoResultPic), "No Result found");
				TestLogger.log("Info", "No Result Pic is displayed");
				
				driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
				if (driver.findElements(logObj.buildLocator(buyLiveList)).size() == 0) {
					TestLogger.log("Pass", "0 Records are searched");
				} else {
					TestLogger.log("Fail",driver.findElements(logObj.buildLocator(buyLiveList)).size() + " Records are searched");
				}
				driver.manage().timeouts().implicitlyWait(implicitTimeout, TimeUnit.SECONDS);
						
				logObj.verifyText(logObj.buildDriverElement(buyNoDataFoundInList),"No data found"); 						
				
			} catch (SeleniumException | IOException e) {
				e.printStackTrace();
			}
		}
		
		// TC Description : Verify when value is searched and then filters are set 
		@Test(groups = "FunctionalTest", testName = "Se_Bu_12", enabled = true, description = "(Buy Tab, Search) - Search the value and then set filters")
		public void Se_Bu_12() throws InterruptedException {
			try {
				
				String fileName = "Buy.xlsx";
				String sheetName = "Se_Bu_12";
				int rowNum = 1;
				
				this.login();
				//added navigation link code
				if(!logObj.buildDriverElement(page_header).getText().contains("Newline Portal - Underwrite")){
					logObj.buildDriverElement(pages_grid).click();
					logObj.buildDriverElement(comBuyTab).click();
					
				}
				driverWait = new WebDriverWait(driver,explicitTimeout);
				driverWait.until(ExpectedConditions.elementToBeClickable(logObj.buildLocator(buyClearButton)));
				logObj.buildDriverElement(buyClearButton).click();
				Thread.sleep(1000);
				logObj.isElementExists(logObj.buildDriverElement(buySearchField), "Search field exists");
				
				logObj.buildDriverElement(buySearchField).clear();
				Thread.sleep(1000);
				logObj.buildDriverElement(buySearchField).sendKeys(logObj.getExcelValue(fileName, sheetName, "EnteredText", rowNum).trim());
				Thread.sleep(1000);
				logObj.buildDriverElement(buySearchButton).click();
				
				this.sync();
				
				if (driver.findElements(logObj.buildLocator(buyLiveList)).size() >= 1) {
					TestLogger.log("Pass", "Search record is returned");
				} else {
					TestLogger.log("Fail", "No records are searched");
				}
				logObj.verifyText(logObj.buildDriverElement(buyPropertyInfoBlock2), 
						logObj.getExcelValue(fileName, sheetName, "EnteredText", rowNum));
				
				logObj.isElementExists(logObj.buildDriverElement(buyFilterButton),"Filter button exists");
				logObj.buildDriverElement(buyFilterButton).click();
				Thread.sleep(1000);
				
				Select dropdown = new Select(logObj.buildDriverElement(buyCountyField));
				dropdown.selectByVisibleText(logObj.getExcelValue(fileName, sheetName, "FilterText", rowNum).trim());
				
				logObj.buildDriverElement(buySearchButton).click();
				
				this.sync();
				
				if (driver.findElements(logObj.buildLocator(buyLiveList)).size() >= 1) {
					TestLogger.log("Pass", "Search record is returned");
				} else {
					TestLogger.log("Fail", "No records are searched");
				}
				
				logObj.verifyText(logObj.buildDriverElement(buyPropertyInfoBlock2), 
						logObj.getExcelValue(fileName, sheetName, "FilterText", rowNum).trim());
				
				logObj.verifyText(logObj.buildDriverElement(buyPropertyInfoBlock2), 
						logObj.getExcelValue(fileName, sheetName, "EnteredText", rowNum));
								
				TestLogger.log("Info", "Records contains both filter and search data");
				
			} catch (SeleniumException | IOException e) {
				e.printStackTrace();
			} finally {
				logObj.buildDriverElement(buyClearButton).click();
			}
		}
}