package test.auction;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import com.thoughtworks.selenium.SeleniumException;
import library.InitialSetUp;
import library.LogInFunc;
import objectRepo.BuyOR;
import objectRepo.CommonOR;
import reporting.TestLogger;

public class BuyFilter_old extends LogInFunc implements BuyOR, CommonOR {

	// Variable or Object declaration
	String username;
	String password;
	LogInFunc logObj = new LogInFunc();
	WebDriverWait driverWait;
	private int loginTimes = 0;

	// Methods declaration
	public BuyFilter_old() throws Exception {
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
			}
			loginTimes = loginTimes + 1;
		}
		driver.manage().timeouts().implicitlyWait(implicitTimeout, TimeUnit.SECONDS);
	}

	private void sync() throws InterruptedException {
		if (driver.findElements(logObj.buildLocator(buyLoadingList)).size() != 0) {
		}

		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		while (driver.findElements(logObj.buildLocator(buyLoadingList)).size() >= 1) {
			Thread.sleep(1000);
		}
		driver.manage().timeouts().implicitlyWait(implicitTimeout, TimeUnit.SECONDS);
	}

	// TC Description : Verify user has "Filter" option in "Buy" tab
	@Test(groups = "FunctionalTest", testName = "Fi_Bu_01", enabled = true, description = "(Buy Tab, Filter) - Validate user has filter option")
	public void Fi_Bu_01() throws InterruptedException {
		try {

			this.login();
			if (!logObj.buildDriverElement(page_header).getText().contains("Newline Portal - Underwrite")) {
				logObj.buildDriverElement(pages_grid).click();
				logObj.buildDriverElement(comBuyTab).click();
			}
			driverWait = new WebDriverWait(driver, explicitTimeout);
			driverWait.until(ExpectedConditions.elementToBeClickable(logObj.buildLocator(buyFilterButton)));
			logObj.isElementExists(logObj.buildDriverElement(buyFilterButton), "Filter button exists");

			logObj.buildDriverElement(buyFilterButton).click();
			Thread.sleep(1000);
			logObj.isElementExists(logObj.buildDriverElement(buyStateField), "State filter exists");
			logObj.isElementExists(logObj.buildDriverElement(buyCountyField), "County filter exists");
			logObj.isElementExists(logObj.buildDriverElement(buyTownshipField), "Township filter exists");
			logObj.isElementExists(logObj.buildDriverElement(buyTaxYearField), "Tax year filter exists");
			logObj.isElementExists(logObj.buildDriverElement(buyPropertyClassField), "Property class filter exists");
			logObj.isElementExists(logObj.buildDriverElement(buyUnderwritingStatusField),
					"UnderwritingStatus filter exists");
			logObj.isElementExists(logObj.buildDriverElement(buyBuyerField), "Buyer filter exists");
			logObj.isElementExists(logObj.buildDriverElement(buyPriorTaxAmtCondtionalField),
					"PriorTaxAmt field exists");
			logObj.isElementExists(logObj.buildDriverElement(buyPriorTaxAmtField), "PriorTaxAmt field exists");
			logObj.isElementExists(logObj.buildDriverElement(buySaleAmountCondtionalField), "SaleAmount field exists");
			logObj.isElementExists(logObj.buildDriverElement(buySaleAmountField), "SaleAmount field exists");
			logObj.isElementExists(logObj.buildDriverElement(buyTagCondtionalField), "Tag field exists");
			logObj.isElementExists(logObj.buildDriverElement(buyTagField), "Tag field exists");
			logObj.isElementExists(logObj.buildDriverElement(buySearchButton), "Search button exists");
			logObj.isElementExists(logObj.buildDriverElement(buyClearButton), "Clear button exists");

			logObj.isElementExists(
					logObj.buildDriverElement(logObj.modifyObjLocator(buyCheckboxSelect, "AuctionDay", "1")),
					"Auction Day field exists");
			logObj.isElementExists(logObj.buildDriverElement(logObj.modifyObjLocator(buyCheckboxSelect, "Grade", "a")),
					"Grade field exists");

		} catch (SeleniumException e) {
			e.printStackTrace();
		}
	}

	// TC Description : Verify all the records are displayed, when user doesn't
	// set any filter value and click on "Search"
	@Test(groups = "FunctionalTest", testName = "Fi_Bu_02", enabled = true, description = "(Buy Tab, Filter) - Validate search when there is filter value selected")
	public void Fi_Bu_02() throws InterruptedException {
		try {

			this.login();
			if (!logObj.buildDriverElement(page_header).getText().contains("Newline Portal - Underwrite")) {
				logObj.buildDriverElement(pages_grid).click();
				logObj.buildDriverElement(comBuyTab).click();

			}
			driverWait = new WebDriverWait(driver, explicitTimeout);
			driverWait.until(ExpectedConditions.elementToBeClickable(logObj.buildLocator(buyClearButton)));
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(buyClearButton));
			logObj.buildDriverElement(buyClearButton).click();
			logObj.buildDriverElement(buyFilterButton).click();
			logObj.isElementExists(logObj.buildDriverElement(buySearchButton), "Search button exists");
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(unCheckFilterPaid));
			logObj.buildDriverElement(unCheckFilterPaid).click();
			logObj.buildDriverElement(buySearchButton).click();
			this.sync();
			logObj.verifyText(logObj.buildDriverElement(buyCurrentListPage), "1");
			TestLogger.log("Info",
					"Current listing page is : " + logObj.buildDriverElement(buyCurrentListPage).getText().trim());
			TestLogger.log("Info",
					"Total listing page is : " + logObj.buildDriverElement(buyTotalListPage).getText().trim());

			if (driver.findElements(logObj.buildLocator(buyLiveList)).size() == 100) {
				TestLogger.log("Pass", "100 records are loaded in listing page");
				jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(headerSection));
				driverWait.until(ExpectedConditions.elementToBeClickable(logObj.buildDriverElement(buyPropertyInfoBlock1)));
				String recordedIdPin = logObj.buildDriverElement(buyPropertyInfoBlock1).getText().trim();

				if (recordedIdPin.contains(
						logObj.buildDriverElement(logObj.modifyObjLocator(buyIdPinInList, "1")).getText().trim())) {
					TestLogger.log("Pass", "First record is selected in the list");
				} else {
					TestLogger.log("Fail", "First record is not selected in the list");
				}
			} else {
				TestLogger.log("Fail", "100 records should be loaded in listing page");
			}

		} catch (SeleniumException e) {
			e.printStackTrace();
		}
	}

	// TC Description : Verify records when user select a value from filter
	// "County" and click on "Search"
	@Test(groups = "FunctionalTest", testName = "Fi_Bu_03", enabled = true, description = "(Buy Tab, Filter) - Validate filter, when county value is selected")
	public void Fi_Bu_03() throws InterruptedException {
		try {

			String fileName = "Buy.xlsx";
			String sheetName = "Fi_Bu_03";
			int rowNum = 1;

			this.login();
			if (!logObj.buildDriverElement(page_header).getText().contains("Newline Portal - Underwrite")) {
				logObj.buildDriverElement(pages_grid).click();
				logObj.buildDriverElement(comBuyTab).click();

			}
			driverWait = new WebDriverWait(driver, explicitTimeout);
			driverWait.until(ExpectedConditions.elementToBeClickable(logObj.buildLocator(buyClearButton)));
			logObj.isElementExists(logObj.buildDriverElement(buyFilterButton), "Filter button exists");
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(buyClearButton));
			logObj.buildDriverElement(buyClearButton).click();
			Thread.sleep(1000);
			logObj.buildDriverElement(buyFilterButton).click();
			Thread.sleep(1000);

			Select dropdown = new Select(logObj.buildDriverElement(buyCountyField));
			dropdown.selectByVisibleText(logObj.getExcelValue(fileName, sheetName, "FilterText", rowNum).trim());
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(unCheckFilterPaid));
			logObj.buildDriverElement(unCheckFilterPaid).click();
			logObj.buildDriverElement(buySearchButton).click();
			this.sync();
			Thread.sleep(1000);

			if (driver.findElements(logObj.buildLocator(buyLiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");

				logObj.verifyText(logObj.buildDriverElement(buyPropertyInfoBlock2),
						logObj.getExcelValue(fileName, sheetName, "FilterText", rowNum).trim());

				TestLogger.log("Info", "Records contains filtered data");
			} else {
				TestLogger.log("Fail", "No records are searched");
			}
		} catch (SeleniumException | IOException e) {
			e.printStackTrace();
		}
	}

	// TC Description : Verify records when user select a value from filter
	// "Township" and click on "Search"
	@Test(groups = "FunctionalTest", testName = "Fi_Bu_04", enabled = true, description = "(Buy Tab, Filter) - Validate filter, when township value is selected")
	public void Fi_Bu_04() throws InterruptedException {
		try {

			String fileName = "Buy.xlsx";
			String sheetName = "Fi_Bu_04";
			int rowNum = 1;

			this.login();
			if (!logObj.buildDriverElement(page_header).getText().contains("Newline Portal - Underwrite")) {
				logObj.buildDriverElement(pages_grid).click();
				logObj.buildDriverElement(comBuyTab).click();

			}
			driverWait = new WebDriverWait(driver, explicitTimeout);
			driverWait.until(ExpectedConditions.elementToBeClickable(logObj.buildLocator(buyClearButton)));
			logObj.isElementExists(logObj.buildDriverElement(buyFilterButton), "Filter button exists");
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(buyClearButton));
			logObj.buildDriverElement(buyClearButton).click();
			Thread.sleep(1000);
			logObj.buildDriverElement(buyFilterButton).click();
			Thread.sleep(1000);

			Select dropdown = new Select(logObj.buildDriverElement(buyTownshipField));
			dropdown.selectByVisibleText(logObj.getExcelValue(fileName, sheetName, "FilterText", rowNum).trim());
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(unCheckFilterPaid));
			logObj.buildDriverElement(unCheckFilterPaid).click();
			logObj.buildDriverElement(buySearchButton).click();
			this.sync();

			if (driver.findElements(logObj.buildLocator(buyLiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");

				logObj.verifyText(logObj.buildDriverElement(buyPropertyInfoBlock2),
						logObj.getExcelValue(fileName, sheetName, "FilterText", rowNum).trim());

				TestLogger.log("Info", "Records contains filtered data");
			} else {
				TestLogger.log("Fail", "No records are searched");
			}
		} catch (SeleniumException | IOException e) {
			e.printStackTrace();
		}
	}

	// TC Description : Verify records when user select a value from filter "Tax
	// Year" and click on "Search"
	@Test(groups = "FunctionalTest", testName = "Fi_Bu_05", enabled = true, description = "(Buy Tab, Filter) - Validate filter, when tax year value is selected")
	public void Fi_Bu_05() throws InterruptedException {
		try {

			String fileName = "Buy.xlsx";
			String sheetName = "Fi_Bu_05";
			int rowNum = 1;

			this.login();
			if (!logObj.buildDriverElement(page_header).getText().contains("Newline Portal - Underwrite")) {
				logObj.buildDriverElement(pages_grid).click();
				logObj.buildDriverElement(comBuyTab).click();

			}
			driverWait = new WebDriverWait(driver, explicitTimeout);
			driverWait.until(ExpectedConditions.elementToBeClickable(logObj.buildLocator(buyClearButton)));
			logObj.isElementExists(logObj.buildDriverElement(buyFilterButton), "Filter button exists");
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(buyClearButton));
			logObj.buildDriverElement(buyClearButton).click();
			Thread.sleep(1000);
			logObj.buildDriverElement(buyFilterButton).click();
			Thread.sleep(1000);

			Select dropdown = new Select(logObj.buildDriverElement(buyTaxYearField));
			dropdown.selectByVisibleText(logObj.getExcelValue(fileName, sheetName, "FilterText", rowNum).trim());
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(unCheckFilterPaid));
			logObj.buildDriverElement(unCheckFilterPaid).click();
			logObj.buildDriverElement(buySearchButton).click();
			this.sync();

			if (driver.findElements(logObj.buildLocator(buyLiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");

				jse.executeScript("arguments[0].scrollIntoView()",
						logObj.buildDriverElement(buyTaxSaleHistoryViewDetailsLabel));
				List<WebElement> elem = driver.findElements(logObj.buildLocator(buyTaxSaleHistoryRowHeader));
				int colNo = 0;
				for (int i = 1; i < elem.size(); i++) {
					String cmp = logObj
							.buildDriverElement(
									logObj.modifyObjLocator(buyTaxSaleHistoryHeaderValue, String.valueOf(i)))
							.getText().trim();
					if (cmp.equalsIgnoreCase("Year")) {
						colNo = i;
					}
				}

				List<WebElement> elem1 = driver.findElements(logObj.buildLocator(buyTaxSaleHistoryRowBody));
				for (int j = 1; j <= elem1.size(); j++) {
					String cmp = logObj.buildDriverElement(logObj.modifyObjLocator(buyTaxSaleHistoryBodyCellValue,
							String.valueOf(j), String.valueOf(colNo))).getText().trim();

					if (cmp.equalsIgnoreCase(logObj.getExcelValue(fileName, sheetName, "FilterText", rowNum).trim())) {
						logObj.isElementExists(
								logObj.buildDriverElement(logObj.modifyObjLocator(buyTaxSaleHistoryBodyCellValue,
										String.valueOf(j), String.valueOf(colNo))),
								"Filtered year exists");
						colNo = 200;
						break;
					}
				}

				if (colNo == 200) {
					TestLogger.log("Info", "Records contains filtered data");
				} else {
					logObj.isElementExists(logObj.buildDriverElement(
							logObj.modifyObjLocator(buyTaxSaleHistoryBodyCellValue, "100", String.valueOf(colNo))),
							"Filtered year exists");
				}
			} else {
				TestLogger.log("Fail", "No records are searched");
			}
		} catch (SeleniumException | IOException e) {
			e.printStackTrace();
		}
	}

	// TC Description : Verify records when user select a value from filter
	// "Property Class" and click on "Search"
	@Test(groups = "FunctionalTest", testName = "Fi_Bu_06", enabled = true, description = "(Buy Tab, Filter) - Validate filter, when property class value is selected")
	public void Fi_Bu_06() throws InterruptedException {
		try {

			String fileName = "Buy.xlsx";
			String sheetName = "Fi_Bu_06";
			int rowNum = 1;

			this.login();
			if (!logObj.buildDriverElement(page_header).getText().contains("Newline Portal - Underwrite")) {
				logObj.buildDriverElement(pages_grid).click();
				logObj.buildDriverElement(comBuyTab).click();

			}
			driverWait = new WebDriverWait(driver, explicitTimeout);
			driverWait.until(ExpectedConditions.elementToBeClickable(logObj.buildLocator(buyClearButton)));
			logObj.isElementExists(logObj.buildDriverElement(buyFilterButton), "Filter button exists");
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(buyClearButton));
			logObj.buildDriverElement(buyClearButton).click();
			Thread.sleep(1000);
			logObj.buildDriverElement(buyFilterButton).click();
			Thread.sleep(1000);

			Select dropdown = new Select(logObj.buildDriverElement(buyPropertyClassField));
			dropdown.selectByVisibleText(logObj.getExcelValue(fileName, sheetName, "FilterText", rowNum).trim());
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(unCheckFilterPaid));
			logObj.buildDriverElement(unCheckFilterPaid).click();

			logObj.buildDriverElement(buySearchButton).click();
			this.sync();

			if (driver.findElements(logObj.buildLocator(buyLiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");
				jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(headerSection));
				logObj.verifyText(logObj.buildDriverElement(buyPropertyInfoBlock4),
						logObj.getExcelValue(fileName, sheetName, "FilterText", rowNum).trim());

				TestLogger.log("Info", "Records contains filtered data");
			} else {
				TestLogger.log("Fail", "No records are searched");
			}

		} catch (SeleniumException | IOException e) {
			e.printStackTrace();
		}
	}

	// TC Description : Verify records when user select a value from filter
	// "Underwriting Status" and click on "Search"
	@Test(groups = "FunctionalTest", testName = "Fi_Bu_07", enabled = true, description = "(Buy Tab, Filter) - Validate filter, when underwriting status value is selected")
	public void Fi_Bu_07() throws InterruptedException {
		try {

			String fileName = "Buy.xlsx";
			String sheetName = "Fi_Bu_07";
			int rowNum = 1;

			this.login();
			if (!logObj.buildDriverElement(page_header).getText().contains("Newline Portal - Underwrite")) {
				logObj.buildDriverElement(pages_grid).click();
				logObj.buildDriverElement(comBuyTab).click();

			}
			driverWait = new WebDriverWait(driver, explicitTimeout);
			driverWait.until(ExpectedConditions.elementToBeClickable(logObj.buildLocator(buyClearButton)));
			logObj.isElementExists(logObj.buildDriverElement(buyFilterButton), "Filter button exists");
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(buyClearButton));
			logObj.buildDriverElement(buyClearButton).click();
			Thread.sleep(1000);
			logObj.buildDriverElement(buyFilterButton).click();
			Thread.sleep(1000);

			Select dropdown = new Select(logObj.buildDriverElement(buyUnderwritingStatusField));
			dropdown.selectByVisibleText(logObj.getExcelValue(fileName, sheetName, "FilterText", rowNum).trim());
			Thread.sleep(1000);
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(unCheckFilterPaid));
			logObj.buildDriverElement(unCheckFilterPaid).click();

			logObj.buildDriverElement(buySearchButton).click();
//			Thread.sleep(8000);
			this.sync();

			if (driver.findElements(logObj.buildLocator(buyLiveList)).size() >= 4) {
				TestLogger.log("Pass", "Search record is returned");

				this.sync();
				logObj.verifyText(
						logObj.buildDriverElement(logObj.modifyObjLocator(buyStatsSectionStatusField, "approved")),
						logObj.getExcelValue(fileName, sheetName, "SearchedText", rowNum).trim());
			} else {
				TestLogger.log("Fail", "No records are searched");
			}
			// Select second value
			TestLogger.log("Info", "Second iteration with different value");
			rowNum = 2;
			logObj.buildDriverElement(buyClearButton).click();
			Thread.sleep(1000);
			logObj.buildDriverElement(buyFilterButton).click();
			Thread.sleep(1000);

			dropdown.selectByVisibleText(logObj.getExcelValue(fileName, sheetName, "FilterText", rowNum).trim());
			Thread.sleep(1000);
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(unCheckFilterPaid));
			logObj.buildDriverElement(unCheckFilterPaid).click();
			logObj.buildDriverElement(buySearchButton).click();
			Thread.sleep(4000);
			this.sync();

			if (driver.findElements(logObj.buildLocator(buyLiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");

				// logObj.buildDriverElement(buyLiveList + "[4]").click();
				this.sync();
				Thread.sleep(5000);
				logObj.verifyText(
						logObj.buildDriverElement(logObj.modifyObjLocator(buyStatsSectionStatusField, "pending")),
						logObj.getExcelValue(fileName, sheetName, "SearchedText", rowNum).trim());
			} else {
				TestLogger.log("Fail", "No records are searched");
			}
			// Select third value
			TestLogger.log("Info", "Third iteration with different value");
			rowNum = 3;
			logObj.buildDriverElement(buyClearButton).click();
			Thread.sleep(1000);
			logObj.buildDriverElement(buyFilterButton).click();
			Thread.sleep(1000);

			dropdown.selectByVisibleText(logObj.getExcelValue(fileName, sheetName, "FilterText", rowNum).trim());
			Thread.sleep(1000);
			logObj.buildDriverElement(buySearchButton).click();
			Thread.sleep(4000);
			this.sync();
			if (driver.findElements(logObj.buildLocator(buyLiveList)).size() >= 4) {
				TestLogger.log("Pass", "Search record is returned");
				// logObj.buildDriverElement(buyLiveList + "[4]").click();
				this.sync();
				// Thread.sleep(5000);
				logObj.verifyText(
						logObj.buildDriverElement(logObj.modifyObjLocator(buyStatsSectionStatusField, "declined")),
						logObj.getExcelValue(fileName, sheetName, "SearchedText", rowNum).trim());
			} else {
				TestLogger.log("Fail", "No records are searched");
			}

		} catch (SeleniumException | IOException e) {
			e.printStackTrace();
		}
	}

	// TC Description : Verify records when user select one value from
	// multiselect filter "Auction Day" and click on "Search"
	@Test(groups = "FunctionalTest", testName = "Fi_Bu_08", enabled = true, description = "(Buy Tab, Filter) - Validate filter, when one value is selected in auction day")
	public void Fi_Bu_08() throws InterruptedException {
		try {

			this.login();
			if (!logObj.buildDriverElement(page_header).getText().contains("Newline Portal - Underwrite")) {
				logObj.buildDriverElement(pages_grid).click();
				logObj.buildDriverElement(comBuyTab).click();
			}
			Thread.sleep(4000);
			driverWait = new WebDriverWait(driver, explicitTimeout);
			driverWait.until(ExpectedConditions.elementToBeClickable(logObj.buildLocator(buyClearButton)));
			logObj.isElementExists(logObj.buildDriverElement(buyFilterButton), "Filter button exists");
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(buyClearButton));
			logObj.buildDriverElement(buyClearButton).click();
			Thread.sleep(1000);
			logObj.buildDriverElement(buyFilterButton).click();
			Thread.sleep(1000);

			if (!logObj.buildDriverElement(logObj.modifyObjLocator(buyCheckboxSelect, "AuctionDay", "1"))
					.isSelected()) {
				logObj.buildDriverElement(logObj.modifyObjLocator(buyCheckboxSelect, "AuctionDay", "1")).click();
			}
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(unCheckFilterPaid));
			logObj.buildDriverElement(unCheckFilterPaid).click();
			logObj.buildDriverElement(buySearchButton).click();
			this.sync();

			if (driver.findElements(logObj.buildLocator(buyLiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");
			} else {
				TestLogger.log("Fail", "No records are searched");
			}

		} catch (SeleniumException e) {
			e.printStackTrace();
		}
	}

	// TC Description : Verify records when user select multiple value from
	// multiselect filter "Auction Day" and click on "Search"
	@Test(groups = "FunctionalTest", testName = "Fi_Bu_09", enabled = true, description = "(Buy Tab, Filter) - Validate filter, when multiple value is selected from auction day")
	public void Fi_Bu_09() throws InterruptedException {
		try {

			this.login();
			if (!logObj.buildDriverElement(page_header).getText().contains("Newline Portal - Underwrite")) {
				logObj.buildDriverElement(pages_grid).click();
				logObj.buildDriverElement(comBuyTab).click();

			}
			driverWait = new WebDriverWait(driver, explicitTimeout);
			driverWait.until(ExpectedConditions.elementToBeClickable(logObj.buildLocator(buyClearButton)));
			logObj.isElementExists(logObj.buildDriverElement(buyFilterButton), "Filter button exists");
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(buyClearButton));
			logObj.buildDriverElement(buyClearButton).click();
			Thread.sleep(1000);
			logObj.buildDriverElement(buyFilterButton).click();
			Thread.sleep(1000);

			if (!logObj.buildDriverElement(logObj.modifyObjLocator(buyCheckboxSelect, "AuctionDay", "1"))
					.isSelected()) {
				logObj.buildDriverElement(logObj.modifyObjLocator(buyCheckboxSelect, "AuctionDay", "1")).click();
			}

			if (!logObj.buildDriverElement(logObj.modifyObjLocator(buyCheckboxSelect, "AuctionDay", "null"))
					.isSelected()) {
				logObj.buildDriverElement(logObj.modifyObjLocator(buyCheckboxSelect, "AuctionDay", "null")).click();
			}

			if (!logObj.buildDriverElement(logObj.modifyObjLocator(buyCheckboxSelect, "AuctionDay", "2"))
					.isSelected()) {
				logObj.buildDriverElement(logObj.modifyObjLocator(buyCheckboxSelect, "AuctionDay", "2")).click();
			}
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(unCheckFilterPaid));
			logObj.buildDriverElement(unCheckFilterPaid).click();
			logObj.buildDriverElement(buySearchButton).click();
			this.sync();

			if (driver.findElements(logObj.buildLocator(buyLiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");
			} else {
				TestLogger.log("Fail", "No records are searched");
			}

		} catch (SeleniumException e) {
			e.printStackTrace();
		}
	}

	// TC Description : Verify records when user selects all the value in
	// multiselect filter "Auction Day" and click on "Search"
	@Test(groups = "FunctionalTest", testName = "Fi_Bu_10", enabled = true, description = "(Buy Tab, Filter) - Validate filter, when all the value is selected from auction day")
	public void Fi_Bu_10() throws InterruptedException {
		try {

			this.login();
			if (!logObj.buildDriverElement(page_header).getText().contains("Newline Portal - Underwrite")) {
				logObj.buildDriverElement(pages_grid).click();
				logObj.buildDriverElement(comBuyTab).click();

			}
			driverWait = new WebDriverWait(driver, explicitTimeout);
			driverWait.until(ExpectedConditions.elementToBeClickable(logObj.buildLocator(buyClearButton)));
			logObj.isElementExists(logObj.buildDriverElement(buyFilterButton), "Filter button exists");
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(buyClearButton));
			logObj.buildDriverElement(buyClearButton).click();
			Thread.sleep(1000);
			logObj.buildDriverElement(buyFilterButton).click();
			Thread.sleep(1000);

			List<WebElement> myElements = driver
					.findElements(logObj.buildLocator(logObj.modifyObjLocator(buyCheckboxes, "AuctionDay")));
			for (WebElement e : myElements) {
				if (!logObj.buildDriverElement(
						logObj.modifyObjLocator(buyCheckboxSelect, "AuctionDay", e.getText().toLowerCase().trim()))
						.isSelected()) {
					logObj.buildDriverElement(
							logObj.modifyObjLocator(buyCheckboxSelect, "AuctionDay", e.getText().toLowerCase().trim()))
							.click();
				}
			}
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(unCheckFilterPaid));
			logObj.buildDriverElement(unCheckFilterPaid).click();
			logObj.buildDriverElement(buySearchButton).click();
			this.sync();

			if (driver.findElements(logObj.buildLocator(buyLiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");
			} else {
				TestLogger.log("Fail", "No records are searched");
			}

		} catch (SeleniumException e) {
			e.printStackTrace();
		}
	}

	// TC Description : Verify records when user select a value from filter
	// "Buyer" and click on "Search"
	@Test(groups = "FunctionalTest", testName = "Fi_Bu_11", enabled = true, description = "(Buy Tab, Filter) - Validate filter, when buyer value is selected")
	public void Fi_Bu_11() throws InterruptedException {
		try {

			String fileName = "Buy.xlsx";
			String sheetName = "Fi_Bu_11";
			int rowNum = 1;

			this.login();
			if (!logObj.buildDriverElement(page_header).getText().contains("Newline Portal - Underwrite")) {
				logObj.buildDriverElement(pages_grid).click();
				logObj.buildDriverElement(comBuyTab).click();

			}
			driverWait = new WebDriverWait(driver, explicitTimeout);
			driverWait.until(ExpectedConditions.elementToBeClickable(logObj.buildLocator(buyClearButton)));
			logObj.isElementExists(logObj.buildDriverElement(buyFilterButton), "Filter button exists");
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(buyClearButton));
			logObj.buildDriverElement(buyClearButton).click();
			Thread.sleep(1000);
			logObj.buildDriverElement(buyFilterButton).click();
			Thread.sleep(1000);

			logObj.buildDriverElement(buyBuyerField).click();
			logObj.buildDriverElement(buyBuyerField)
					.sendKeys(logObj.getExcelValue(fileName, sheetName, "FilterText", rowNum).trim());
			Thread.sleep(1000);
			logObj.buildDriverElement(logObj.modifyObjLocator(buyBuyerRecordSelect, "1")).click();
			Thread.sleep(1000);
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(unCheckFilterPaid));
			logObj.buildDriverElement(unCheckFilterPaid).click();
			logObj.buildDriverElement(buySearchButton).click();
			this.sync();

			if (driver.findElements(logObj.buildLocator(buyLiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");
				jse.executeScript("arguments[0].scrollIntoView()",
						logObj.buildDriverElement(buyTaxSaleHistoryViewDetailsLabel));
				logObj.buildDriverElement(buyTaxSaleHistoryViewDetailsLabel).click();
				Thread.sleep(2000);
				logObj.isElementExists(logObj.buildDriverElement(buyTaxSaleHistoryDetailsDialogTitle),
						"Tax history details dialog is open");

				logObj.isElementExists(
						logObj.buildDriverElement(logObj.modifyObjLocator(buyTaxSaleHistoryDetailsTables, "Auctions",
								logObj.getExcelValue(fileName, sheetName, "FilterText", rowNum).trim())),
						"Records contains filtered data");
				logObj.buildDriverElement(buyTaxSaleHistoryDetailsDialogClose).click();
				Thread.sleep(1000);
			} else {
				TestLogger.log("Fail", "No records are searched");
			}
		} catch (SeleniumException | IOException e) {
			e.printStackTrace();
		}
	}

	// TC Description : Verify records when user select one value from
	// multiselect filter "Grade" and click on "Search"
	@Test(groups = "FunctionalTest", testName = "Fi_Bu_12", enabled = true, description = "(Buy Tab, Filter) - Validate filter, when one value is selected from Grade")
	public void Fi_Bu_12() throws InterruptedException {
		try {
			driverWait = new WebDriverWait(driver, explicitTimeout);
			driverWait.until(ExpectedConditions.elementToBeClickable(logObj.buildLocator(buyClearButton)));
			logObj.isElementExists(logObj.buildDriverElement(buyClearButton), "Filter button exists");
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(buyClearButton));
			logObj.buildDriverElement(buyClearButton).click();
			Thread.sleep(1000);
			logObj.buildDriverElement(buyFilterButton).click();
			Thread.sleep(1000);

			if (!logObj.buildDriverElement(logObj.modifyObjLocator(buyCheckboxSelect, "Grade", "a")).isSelected()) {
				logObj.buildDriverElement(logObj.modifyObjLocator(buyCheckboxSelect, "Grade", "a")).click();
			}
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(unCheckFilterPaid));
			logObj.buildDriverElement(unCheckFilterPaid).click();
			logObj.buildDriverElement(buySearchButton).click();
			this.sync();

			if (driver.findElements(logObj.buildLocator(buyLiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned with selected grade");

				String cmpText = logObj.buildDriverElement(logObj.modifyObjLocator(buyStatsSectionAreaGradeField, "A"))
						.getText();
				if (cmpText.startsWith("A")) {
					logObj.isElementExists(
							logObj.buildDriverElement(logObj.modifyObjLocator(buyStatsSectionAreaGradeField, "A")),
							"Searched value exists");
				} else {
					logObj.isElementExists(
							logObj.buildDriverElement(logObj.modifyObjLocator(buyStatsSectionAreaGradeField, "Note")),
							"Searched value doesn't exists");
				}

				// Select second value
				TestLogger.log("Info", "Second iteration with different value");
				jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(buyClearButton));
				logObj.buildDriverElement(buyClearButton).click();
				Thread.sleep(1000);
				driverWait.until(ExpectedConditions.elementToBeClickable(logObj.buildLocator(buyFilterButton)));
				logObj.buildDriverElement(buyFilterButton).click();
				Thread.sleep(1000);

				if (!logObj.buildDriverElement(logObj.modifyObjLocator(buyCheckboxSelect, "Grade", "?")).isSelected()) {
					logObj.buildDriverElement(logObj.modifyObjLocator(buyCheckboxSelect, "Grade", "?")).click();
				}
				jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(unCheckFilterPaid));
				logObj.buildDriverElement(unCheckFilterPaid).click();
				logObj.buildDriverElement(buySearchButton).click();
				this.sync();

				if (driver.findElements(logObj.buildLocator(buyLiveList)).size() >= 1) {
					TestLogger.log("Pass", "Search record is returned");

					cmpText = logObj.buildDriverElement(logObj.modifyObjLocator(buyStatsSectionAreaGradeField, "?"))
							.getText();
					if (cmpText.startsWith("?")) {
						logObj.isElementExists(
								logObj.buildDriverElement(logObj.modifyObjLocator(buyStatsSectionAreaGradeField, "?")),
								"Searched value exists");
					} else {
						logObj.isElementExists(
								logObj.buildDriverElement(
										logObj.modifyObjLocator(buyStatsSectionAreaGradeField, "Note")),
								"Searched value doesn't exists");
					}
				} else {
					TestLogger.log("Fail", "No records are searched");
				}
			} else {
				TestLogger.log("Fail", "No records are searched");
			}
		} catch (SeleniumException e) {
			e.printStackTrace();
		}
	}

	// TC Description : Verify records when user select multiple value from
	// multiselect filter "Grade" and click on "Search"
	@Test(groups = "FunctionalTest", testName = "Fi_Bu_13", enabled = true, description = "(Buy Tab, Filter) - Validate filter, when multiple values is selected from Grade")
	public void Fi_Bu_13() throws InterruptedException {
		try {

			this.login();
			if (!logObj.buildDriverElement(page_header).getText().contains("Newline Portal - Underwrite")) {
				logObj.buildDriverElement(pages_grid).click();
				logObj.buildDriverElement(comBuyTab).click();

			}
			driverWait = new WebDriverWait(driver, explicitTimeout);
			driverWait.until(ExpectedConditions.elementToBeClickable(logObj.buildLocator(buyClearButton)));
			logObj.isElementExists(logObj.buildDriverElement(buyFilterButton), "Filter button exists");
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(buyClearButton));
			logObj.buildDriverElement(buyClearButton).click();
			Thread.sleep(1000);
			logObj.buildDriverElement(buyFilterButton).click();
			Thread.sleep(1000);

			if (!logObj.buildDriverElement(logObj.modifyObjLocator(buyCheckboxSelect, "Grade", "a")).isSelected()) {
				logObj.buildDriverElement(logObj.modifyObjLocator(buyCheckboxSelect, "Grade", "a")).click();
			}

			if (!logObj.buildDriverElement(logObj.modifyObjLocator(buyCheckboxSelect, "Grade", "b")).isSelected()) {
				logObj.buildDriverElement(logObj.modifyObjLocator(buyCheckboxSelect, "Grade", "b")).click();
			}

			if (!logObj.buildDriverElement(logObj.modifyObjLocator(buyCheckboxSelect, "Grade", "d")).isSelected()) {
				logObj.buildDriverElement(logObj.modifyObjLocator(buyCheckboxSelect, "Grade", "d")).click();
			}
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(unCheckFilterPaid));
			logObj.buildDriverElement(unCheckFilterPaid).click();
			logObj.buildDriverElement(buySearchButton).click();
			this.sync();

			if (driver.findElements(logObj.buildLocator(buyLiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");
			} else {
				TestLogger.log("Fail", "No records are searched");
			}

		} catch (SeleniumException e) {
			e.printStackTrace();
		}
	}

	// TC Description : Verify records when user selects all the value in
	// multiselect filter "Grade" and click on "Search"
	@Test(groups = "FunctionalTest", testName = "Fi_Bu_14", enabled = true, description = "(Buy Tab, Filter) - Validate filter, when all values is selected from Grade")
	public void Fi_Bu_14() throws InterruptedException {
		try {

			this.login();
			if (!logObj.buildDriverElement(page_header).getText().contains("Newline Portal - Underwrite")) {
				logObj.buildDriverElement(pages_grid).click();
				logObj.buildDriverElement(comBuyTab).click();

			}
			driverWait = new WebDriverWait(driver, explicitTimeout);
			driverWait.until(ExpectedConditions.elementToBeClickable(logObj.buildLocator(buyClearButton)));
			logObj.isElementExists(logObj.buildDriverElement(buyFilterButton), "Filter button exists");
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(buyClearButton));
			logObj.buildDriverElement(buyClearButton).click();
			Thread.sleep(1000);
			logObj.buildDriverElement(buyFilterButton).click();
			Thread.sleep(1000);

			List<WebElement> myElements = driver
					.findElements(logObj.buildLocator(logObj.modifyObjLocator(buyCheckboxes, "Grade")));
			for (WebElement e : myElements) {
				if (!logObj
						.buildDriverElement(
								logObj.modifyObjLocator(buyCheckboxSelect, "Grade", e.getText().toLowerCase().trim()))
						.isSelected()) {
					logObj.buildDriverElement(
							logObj.modifyObjLocator(buyCheckboxSelect, "Grade", e.getText().toLowerCase().trim()))
							.click();
				}
			}
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(unCheckFilterPaid));
			logObj.buildDriverElement(unCheckFilterPaid).click();
			logObj.buildDriverElement(buySearchButton).click();
			this.sync();

			if (driver.findElements(logObj.buildLocator(buyLiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");
				int cnt = 0;
				for (WebElement e : myElements) {
					if (logObj.buildDriverElement(buyStatsSectionAreaGradeValue).getText().trim()
							.contains(e.getText().trim())) {
						cnt = cnt + 1;
					}
				}
				if (cnt > 0) {
					TestLogger.log("Pass", "Record contain one of the filtered tag");
				} else {
					TestLogger.log("Fail", "Record doesn't contain any of the filtered tag");
				}
			} else {
				TestLogger.log("Fail", "No records are searched");
			}
		} catch (SeleniumException e) {
			e.printStackTrace();
		}
	}

	// TC Description : Verify records when user select "=" operator and select
	// a "Tag" and "Search"
	@Test(groups = "FunctionalTest", testName = "Fi_Bu_15", enabled = true, description = "(Buy Tab, Filter) - Validate filter, when '=' and a tag is selected")
	public void Fi_Bu_15() throws InterruptedException {
		try {

			String fileName = "Buy.xlsx";
			String sheetName = "Fi_Bu_15";
			int rowNum = 1;

			this.login();
			if (!logObj.buildDriverElement(page_header).getText().contains("Newline Portal - Underwrite")) {
				logObj.buildDriverElement(pages_grid).click();
				logObj.buildDriverElement(comBuyTab).click();

			}
			driverWait = new WebDriverWait(driver, explicitTimeout);
			driverWait.until(ExpectedConditions.elementToBeClickable(logObj.buildLocator(buyClearButton)));
			logObj.isElementExists(logObj.buildDriverElement(buyFilterButton), "Filter button exists");
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(buyClearButton));
			logObj.buildDriverElement(buyClearButton).click();
			Thread.sleep(1000);
			logObj.buildDriverElement(buyFilterButton).click();
			Thread.sleep(1000);

			Select dropdown1 = new Select(logObj.buildDriverElement(buyTagCondtionalField));
			dropdown1.selectByVisibleText("=");

			Select dropdown2 = new Select(logObj.buildDriverElement(buyTagField));
			dropdown2.selectByVisibleText(logObj.getExcelValue(fileName, sheetName, "TagName", rowNum).trim());
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(unCheckFilterPaid));
			logObj.buildDriverElement(unCheckFilterPaid).click();
			logObj.buildDriverElement(buySearchButton).click();
			this.sync();
			if (driver.findElements(logObj.buildLocator(buyLiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");
				jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(tagSection));
				logObj.isElementExists(
						logObj.buildDriverElement(logObj.modifyObjLocator(buySelectedTag,
								logObj.getExcelValue(fileName, sheetName, "TagId", rowNum).trim())),
						"Tag condition is filtered properly");
				Thread.sleep(1000);
			} else {
				TestLogger.log("Fail", "No records are searched");
			}

		} catch (SeleniumException | IOException e) {
			e.printStackTrace();
		}
	}

	// TC Description : Verify records when user select "<>" operator and select
	// a "Tag" and "Search"
	@Test(groups = "FunctionalTest", testName = "Fi_Bu_16", enabled = true, description = "(Buy Tab, Filter) - Validate filter, when '<>' and a tag is selected")
	public void Fi_Bu_16() throws InterruptedException {
		try {
			String fileName = "Buy.xlsx";
			String sheetName = "Fi_Bu_16";
			int rowNum = 1;

			this.login();
			if (!logObj.buildDriverElement(page_header).getText().contains("Newline Portal - Underwrite")) {
				logObj.buildDriverElement(pages_grid).click();
				logObj.buildDriverElement(comBuyTab).click();

			}

			driverWait = new WebDriverWait(driver, explicitTimeout);
			driverWait.until(ExpectedConditions.elementToBeClickable(logObj.buildLocator(buyClearButton)));
			logObj.isElementExists(logObj.buildDriverElement(buyFilterButton), "Filter button exists");
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(buyClearButton));
			logObj.buildDriverElement(buyClearButton).click();
			Thread.sleep(1000);
			logObj.buildDriverElement(buyFilterButton).click();
			Thread.sleep(1000);

			WebElement ee = logObj.buildDriverElement(buyTagCondtionalField);
			jse.executeScript("arguments[0].scrollIntoView()", ee);
			Thread.sleep(1000);
			// logObj.buildDriverElement(buyTagCondtionalField).click();
			Select dropdown3 = new Select(logObj.buildDriverElement(buyTagCondtionalField));
			// dropdown3.selectByVisibleText("<>");
			dropdown3.selectByValue("string:<>");
			Select dropdown2 = new Select(logObj.buildDriverElement(buyTagField));
			dropdown2.selectByVisibleText(logObj.getExcelValue(fileName, sheetName, "TagName", rowNum).trim());
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(unCheckFilterPaid));
			logObj.buildDriverElement(unCheckFilterPaid).click();
			logObj.buildDriverElement(buySearchButton).click();
			this.sync();
			if (driver.findElements(logObj.buildLocator(buyLiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");
				jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(tagSection));
				logObj.isElementExists(
						logObj.buildDriverElement(logObj.modifyObjLocator(buyUnSelectedTag,
								logObj.getExcelValue(fileName, sheetName, "TagId", rowNum).trim())),
						"Tag condition is filtered properly");
			} else {
				TestLogger.log("Fail", "No records are searched");
			}
		} catch (SeleniumException | IOException e) {
			e.printStackTrace();
		}
	}

	// TC Description : Verify records when user enters a value to the filter
	// "Priority Tax Amount" , also selects "<=" and click on "Search"
	@Test(groups = "FunctionalTest", testName = "Fi_Bu_18", enabled = true, description = "(Buy Tab, Filter) - Validate filter, for priority tax amount and  <= is selected")
	public void Fi_Bu_18() throws InterruptedException {
		try {

			String fileName = "Buy.xlsx";
			String sheetName = "Fi_Bu_18";
			int rowNum = 1;

			this.login();
			if (!logObj.buildDriverElement(page_header).getText().contains("Newline Portal - Underwrite")) {
				logObj.buildDriverElement(pages_grid).click();
				logObj.buildDriverElement(comBuyTab).click();

			}
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(buyClearButton));
			driverWait = new WebDriverWait(driver, explicitTimeout);
			driverWait.until(ExpectedConditions.elementToBeClickable(logObj.buildLocator(buyFilterButton)));
			logObj.isElementExists(logObj.buildDriverElement(buyFilterButton), "Filter button exists");
			logObj.buildDriverElement(buyClearButton).click();
			Thread.sleep(1000);
			logObj.buildDriverElement(buyFilterButton).click();
			Thread.sleep(1000);

			Select dropdown = new Select(logObj.buildDriverElement(buyPriorTaxAmtCondtionalField));
			dropdown.selectByVisibleText("<=");

			logObj.buildDriverElement(buyPriorTaxAmtField)
					.sendKeys(logObj.getExcelValue(fileName, sheetName, "FilterText", rowNum).trim());
			int EnteredNumber = Integer
					.parseInt(logObj.getExcelValue(fileName, sheetName, "FilterText", rowNum).trim());
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(unCheckFilterPaid));
			logObj.buildDriverElement(unCheckFilterPaid).click();
			logObj.buildDriverElement(buySearchButton).click();
			this.sync();

			if (driver.findElements(logObj.buildLocator(buyLiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");

				String ActualText = logObj.buildDriverElement(buyPriorAmt).getText();
				float ActualNumber = Float.parseFloat(ActualText.substring(1).trim().replace(",", ""));

				if (ActualNumber <= EnteredNumber) {
					TestLogger.log("Pass", "Record contain filtered value");
				} else {
					TestLogger.log("Fail", "Record doesn't contain filtered value");
				}
			} else {
				TestLogger.log("Fail", "No records are searched");
			}

		} catch (SeleniumException | IOException e) {
			e.printStackTrace();
		}
	}

	// TC Description : Verify records when user enters a value to the filter
	// "Priority Tax Amount" , also selects ">=" and click on "Search"
	@Test(groups = "FunctionalTest", testName = "Fi_Bu_19", enabled = true, description = "(Buy Tab, Filter) - Validate filter, for priority tax amount and  >= is selected")
	public void Fi_Bu_19() throws InterruptedException {
		try {

			String fileName = "Buy.xlsx";
			String sheetName = "Fi_Bu_19";
			int rowNum = 1;

			this.login();
			if (!logObj.buildDriverElement(page_header).getText().contains("Newline Portal - Underwrite")) {
				logObj.buildDriverElement(pages_grid).click();
				logObj.buildDriverElement(comBuyTab).click();

			}
			driverWait = new WebDriverWait(driver, explicitTimeout);
			driverWait.until(ExpectedConditions.elementToBeClickable(logObj.buildLocator(buyClearButton)));
			logObj.isElementExists(logObj.buildDriverElement(buyFilterButton), "Filter button exists");
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(buyClearButton));
			logObj.buildDriverElement(buyClearButton).click();
			Thread.sleep(1000);
			logObj.buildDriverElement(buyFilterButton).click();
			Thread.sleep(1000);

			Select dropdown = new Select(logObj.buildDriverElement(buyPriorTaxAmtCondtionalField));
			dropdown.selectByVisibleText(">=");

			logObj.buildDriverElement(buyPriorTaxAmtField)
					.sendKeys(logObj.getExcelValue(fileName, sheetName, "FilterText", rowNum).trim());
			int EnteredNumber = Integer
					.parseInt(logObj.getExcelValue(fileName, sheetName, "FilterText", rowNum).trim());
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(unCheckFilterPaid));
			logObj.buildDriverElement(unCheckFilterPaid).click();
			logObj.buildDriverElement(buySearchButton).click();
			this.sync();

			if (driver.findElements(logObj.buildLocator(buyLiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");

				Thread.sleep(2000);

				String ActualText = logObj.buildDriverElement(buyPriorAmt).getText();
				float ActualNumber = Float.parseFloat(ActualText.substring(1).trim().replace(",", ""));

				if (ActualNumber >= EnteredNumber) {
					TestLogger.log("Pass", "Record contain filtered value");
				} else {
					TestLogger.log("Fail", "Record doesn't contain filtered value");
					TestLogger.log("Fail", "Actual number : " + ActualNumber + "- Entered Number : " + EnteredNumber);
				}
			} else {
				TestLogger.log("Fail", "No records are searched");
			}
		} catch (SeleniumException | IOException e) {
			e.printStackTrace();
		}
	}

	// TC Description : Verify the value searched through the filter and value
	// in the records fetched will be exact match
	@Test(groups = "FunctionalTest", testName = "Fi_Bu_41", enabled = true, description = "(Buy Tab, Filter) - Validate filter value is a exact match")
	public void Fi_Bu_41() throws InterruptedException {
		try {

			String fileName = "Buy.xlsx";
			String sheetName = "Fi_Bu_41";
			int rowNum = 1;

			this.login();
			Thread.sleep(4000);
			if (!logObj.buildDriverElement(page_header).getText().contains("Newline Portal - Underwrite")) {
				logObj.buildDriverElement(pages_grid).click();
				logObj.buildDriverElement(comBuyTab).click();

			}
			driverWait = new WebDriverWait(driver, explicitTimeout);
			driverWait.until(ExpectedConditions.elementToBeClickable(logObj.buildLocator(buyClearButton)));
			logObj.isElementExists(logObj.buildDriverElement(buyFilterButton), "Filter button exists");
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(buyClearButton));
			logObj.buildDriverElement(buyClearButton).click();
			Thread.sleep(1000);
			if (!driver.findElement(logObj.buildLocator(buyTownshipField)).isDisplayed()) {
				logObj.buildDriverElement(buyFilterButton).click();
				Thread.sleep(1000);
			}

			Select dropdown = new Select(logObj.buildDriverElement(buyTownshipField));
			dropdown.selectByVisibleText(logObj.getExcelValue(fileName, sheetName, "Township", rowNum).trim());
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(unCheckFilterPaid));
			logObj.buildDriverElement(unCheckFilterPaid).click();
			logObj.buildDriverElement(buySearchButton).click();
			this.sync();

			if (driver.findElements(logObj.buildLocator(buyLiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");

				logObj.verifyText(logObj.buildDriverElement(buyPropertyInfoBlock2),
						logObj.getExcelValue(fileName, sheetName, "Township", rowNum).trim());
			} else {
				TestLogger.log("Fail", "No records are searched");
			}
		} catch (SeleniumException | IOException e) {
			e.printStackTrace();
		}
	}

	// TC Description : Verify records when user select a value from filter
	// "State" and click on "Search"
	@Test(groups = "FunctionalTest", testName = "Fi_Bu_45", enabled = true, description = "(Buy Tab, Filter) - Validate filter, when state is selected")
	public void Fi_Bu_45() throws InterruptedException {
		try {

			String fileName = "Buy.xlsx";
			String sheetName = "Fi_Bu_45";
			int rowNum = 1;

			this.login();
			Thread.sleep(4000);
			if (!logObj.buildDriverElement(page_header).getText().contains("Newline Portal - Underwrite")) {
				logObj.buildDriverElement(pages_grid).click();
				logObj.buildDriverElement(comBuyTab).click();

			}
			driverWait = new WebDriverWait(driver, explicitTimeout);
			driverWait.until(ExpectedConditions.elementToBeClickable(logObj.buildLocator(buyClearButton)));
			logObj.isElementExists(logObj.buildDriverElement(buyFilterButton), "Filter button exists");
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(buyClearButton));
			logObj.buildDriverElement(buyClearButton).click();
			Thread.sleep(1000);
			if (!driver.findElement(logObj.buildLocator(buyStateField)).isDisplayed()) {
				logObj.buildDriverElement(buyFilterButton).click();
				Thread.sleep(1000);
			}

			Select dropdown = new Select(logObj.buildDriverElement(buyStateField));
			dropdown.selectByVisibleText(logObj.getExcelValue(fileName, sheetName, "State", rowNum).trim());
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(unCheckFilterPaid));
			logObj.buildDriverElement(unCheckFilterPaid).click();
			logObj.buildDriverElement(buySearchButton).click();
			this.sync();

			if (driver.findElements(logObj.buildLocator(buyLiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");

				logObj.verifyText(logObj.buildDriverElement(buyPropertyInfoBlock2),
						logObj.getExcelValue(fileName, sheetName, "State", rowNum).trim());

				TestLogger.log("Info", "Records contains filtered data");
			} else {
				TestLogger.log("Fail", "No records are searched");
			}
		} catch (SeleniumException | IOException e) {
			e.printStackTrace();
		}
	}

	// TC Description : Verify records when user enters a value to the filter
	// "Open Sales" , also selects "<=" and click on "Search"
	@Test(groups = "FunctionalTest", testName = "Fi_Bu_48", enabled = true, description = "(Buy Tab, Filter) - Validate filter, for open sales amount and  <= is selected")
	public void Fi_Bu_48() throws InterruptedException {
		try {

			String fileName = "Buy.xlsx";
			String sheetName = "Fi_Bu_48";
			int rowNum = 1;

			this.login();
			if (!logObj.buildDriverElement(page_header).getText().contains("Newline Portal - Underwrite")) {
				logObj.buildDriverElement(pages_grid).click();
				logObj.buildDriverElement(comBuyTab).click();

			}
			driverWait = new WebDriverWait(driver, explicitTimeout);
			driverWait.until(ExpectedConditions.elementToBeClickable(logObj.buildLocator(buyClearButton)));
			logObj.isElementExists(logObj.buildDriverElement(buyFilterButton), "Filter button exists");
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(buyClearButton));
			logObj.buildDriverElement(buyClearButton).click();
			Thread.sleep(1000);
			logObj.buildDriverElement(buyFilterButton).click();
			Thread.sleep(1000);

			Select dropdown = new Select(logObj.buildDriverElement(buySaleAmountCondtionalField));
			dropdown.selectByVisibleText("<=");

			logObj.buildDriverElement(buySaleAmountField)
					.sendKeys(logObj.getExcelValue(fileName, sheetName, "SaleAmount", rowNum).trim());
			int EnteredNumber = Integer
					.parseInt(logObj.getExcelValue(fileName, sheetName, "SaleAmount", rowNum).trim());
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(unCheckFilterPaid));
			logObj.buildDriverElement(unCheckFilterPaid).click();
			logObj.buildDriverElement(buySearchButton).click();
			this.sync();

			if (driver.findElements(logObj.buildLocator(buyLiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");

				String ActualText = logObj.buildDriverElement(buyOpenSaleAmt).getText();
				float ActualNumber = Float.parseFloat(ActualText.substring(1).trim().replace(",", ""));

				if (ActualNumber <= EnteredNumber) {
					TestLogger.log("Pass", "Record contain filtered value");
				} else {
					TestLogger.log("Fail", "Record doesn't contain filtered value. :: ActualNumber" + ActualNumber
							+ " :: EnteredNumber" + EnteredNumber);
				}
			} else {
				TestLogger.log("Fail", "No records are searched");
			}
		} catch (SeleniumException | IOException e) {
			e.printStackTrace();
		}
	}

	// TC Description : Verify records when user enters a value to the filter
	// "Open Sales Amount" , also selects ">=" and click on "Search"
	@Test(groups = "FunctionalTest", testName = "Fi_Bu_49", enabled = true, description = "(Buy Tab, Filter) - Validate filter, for open sales amount and  >= is selected")
	public void Fi_Bu_49() throws InterruptedException {
		try {

			String fileName = "Buy.xlsx";
			String sheetName = "Fi_Bu_49";
			int rowNum = 1;

			this.login();
			if (!logObj.buildDriverElement(page_header).getText().contains("Newline Portal - Underwrite")) {
				logObj.buildDriverElement(pages_grid).click();
				logObj.buildDriverElement(comBuyTab).click();

			}
			driverWait = new WebDriverWait(driver, explicitTimeout);
			driverWait.until(ExpectedConditions.elementToBeClickable(logObj.buildLocator(buyClearButton)));
			logObj.isElementExists(logObj.buildDriverElement(buyFilterButton), "Filter button exists");
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(buyClearButton));
			logObj.buildDriverElement(buyClearButton).click();
			Thread.sleep(1000);
			logObj.buildDriverElement(buyFilterButton).click();
			Thread.sleep(1000);

			Select dropdown = new Select(logObj.buildDriverElement(buySaleAmountCondtionalField));
			dropdown.selectByVisibleText(">=");

			logObj.buildDriverElement(buySaleAmountField).click();
			logObj.buildDriverElement(buySaleAmountField).clear();
			logObj.buildDriverElement(buySaleAmountField)
					.sendKeys(logObj.getExcelValue(fileName, sheetName, "SaleAmount", rowNum).trim());
			int EnteredNumber = Integer
					.parseInt(logObj.getExcelValue(fileName, sheetName, "SaleAmount", rowNum).trim());
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(unCheckFilterPaid));
			logObj.buildDriverElement(unCheckFilterPaid).click();
			logObj.buildDriverElement(buySearchButton).click();
			this.sync();

			if (driver.findElements(logObj.buildLocator(buyLiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");

				Thread.sleep(2000);

				String ActualText = logObj.buildDriverElement(buyOpenSaleAmt).getText();
				float ActualNumber = Float.parseFloat(ActualText.substring(1).trim().replace(",", ""));

				if (ActualNumber >= EnteredNumber) {
					TestLogger.log("Pass", "Record contain filtered value");
				} else {
					TestLogger.log("Fail", "Record doesn't contain filtered value");
				}
			} else {
				TestLogger.log("Fail", "No records are searched");
			}

		} catch (SeleniumException | IOException e) {
			e.printStackTrace();
		}
	}
}