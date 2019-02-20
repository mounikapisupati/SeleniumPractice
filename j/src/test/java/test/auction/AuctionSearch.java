package test.auction;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.SeleniumException;

import library.LogInFunc;
import objectRepo.AuctionOR;
import objectRepo.CommonOR;
import reporting.TestLogger;

public class AuctionSearch extends LogInFunc implements AuctionOR, CommonOR {

	// Variable or Object declaration
//	String username;
//	String password;
	LogInFunc logObj = new LogInFunc();
	WebDriverWait driverWait;

	// Methods declaration
	public AuctionSearch() throws Exception {
		super();
	}

	private void sync() throws InterruptedException {
		if (driver.findElements(logObj.buildLocator(LoadingList)).size() != 0) {
		}

		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		while (driver.findElements(logObj.buildLocator(LoadingList)).size() >= 1) {
			Thread.sleep(1000);
		}
		driver.manage().timeouts().implicitlyWait(implicitTimeout, TimeUnit.SECONDS);
	}

	// TC Description : Verify "Search" option in "Auction" tab
	@Test(groups = "FunctionalTest", testName = "Se_Au_01", enabled = true, description = "(Auction Tab, Search) - Verify user has Search option in Auction tab")
	public void Se_Au_01() throws InterruptedException {
		try {

			String fileName = "Auction.xlsx";
			String sheetName = "Se_Bu_01";
			int rowNum = 1;

			logObj.isElementExists(logObj.buildDriverElement(SearchLabel), "Search label exists");
			logObj.isElementExists(logObj.buildDriverElement(SearchField), "Search field exists");

			logObj.verifyAttributeText(logObj.buildDriverElement(SearchField), "placeholder",
					logObj.getExcelValue(fileName, sheetName, "CompareText", rowNum).trim());

			logObj.buildDriverElement(SearchField).clear();
			logObj.buildDriverElement(SearchField)
					.sendKeys(logObj.getExcelValue(fileName, sheetName, "EnteredText", rowNum).trim());
			Thread.sleep(1000);
			TestLogger.log("Info", "Search field is a free text");
			logObj.buildDriverElement(SearchField).clear();

			WebDriverWait wait = new WebDriverWait(driver, 15);
			wait.until(ExpectedConditions.elementToBeClickable(logObj.buildDriverElement(StateField)));

		} catch (SeleniumException | IOException e) {
			e.printStackTrace();
		}
	}

	// TC Description : Search with default search criteria and Verify search result
	@Test(groups = "FunctionalTest", testName = "Se_Au_02", enabled = false, description = "(Auction Tab, Search) - Search with default search criteria and Verify search result")
	public void Se_Au_02() throws InterruptedException {
		try {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(unCheckFilterPaid));
			WebDriverWait wait = new WebDriverWait(driver, 15);
			wait.until(ExpectedConditions.visibilityOf(logObj.buildDriverElement(unCheckFilterPaid)));
			Thread.sleep(7000);
			logObj.buildDriverElement(unCheckFilterPaid).click();
			logObj.buildDriverElement(SearchButton).click();
			TestLogger.log("Pass", "Clicked Search button");

			if (driver.findElements(this.buildLocator(comGlobalSpinner)).size() != 0) {
				TestLogger.log("Fail", "Spinner showing");

			} else {
				TestLogger.log("Pass", "Spinner is not showning");
			}

			JavascriptExecutor js = (JavascriptExecutor) driver;
			if (js.executeScript("return document.readyState").toString().equals("complete")) {
				System.out.println("Page Is loaded.");
				return;
			}
			System.out.println("im in this place ..");
			if (driver.findElements(logObj.buildLocator(LiveList)).size() == 100) {
				TestLogger.log("Pass", "100 records are loaded in listing page");
				System.out.println("100 records are loaded in listing page");
				TestLogger.log("Info",
						"Current listing page is : " + logObj.buildDriverElement(CurrentListPage).getText().trim());
				System.out.println(
						"Current listing page is : " + logObj.buildDriverElement(CurrentListPage).getText().trim());
				logObj.verifyText(logObj.buildDriverElement(CurrentListPage), "Page 1");

				String recordedIdPin = logObj.buildDriverElement(PropertyInfoBlock1).getText().trim();

				if (recordedIdPin.contains(
						logObj.buildDriverElement(logObj.modifyObjLocator(IdPinInList, "1")).getText().trim())) {
					TestLogger.log("Pass", "First record is selected in the list");
				} else {
					TestLogger.log("Fail", "First record is not selected in the list");
				}
			} else {
				TestLogger.log("Fail", "No records found or lessthan 100 records found.");
			}

		} catch (SeleniumException e) {
			e.printStackTrace();
		}
	}

	// TC Description : Search with Property Address and Verify search results.
	@Test(groups = "FunctionalTest", testName = "Se_Au_03", enabled = true, description = "(Auction Tab, Search) - Search with Property Address and Verify search results.")
	public void Se_Au_03() throws InterruptedException {
		try {

			String fileName = "Auction.xlsx";
			String sheetName = "Se_Bu_03";
			int rowNum = 1;
			if(!logObj.buildDriverElement(StateField).isDisplayed()) {
				logObj.buildDriverElement(FilterButton).click();
				}
			logObj.buildDriverElement(ClearButton).click();
			Thread.sleep(1000);
			
			logObj.buildDriverElement(SearchField)
					.sendKeys(logObj.getExcelValue(fileName, sheetName, "EnteredText", rowNum).trim());
			Thread.sleep(1000);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(unCheckFilterPaid));
			logObj.buildDriverElement(unCheckFilterPaid).click();
			logObj.buildDriverElement(SearchButton).click();
			this.sync();

			if (driver.findElements(logObj.buildLocator(LiveList)).size() == 1) {
				TestLogger.log("Pass", "1 record is returned, Since full address is given and is unique");

				logObj.verifyText(logObj.buildDriverElement(CurrentListPage), "1");
				TestLogger.log("Info",
						"Current listing page is : " + logObj.buildDriverElement(CurrentListPage).getText().trim());

				logObj.verifyText(logObj.buildDriverElement(pf2),
						logObj.getExcelValue(fileName, sheetName, "EnteredText", rowNum));

				logObj.verifyText(logObj.buildDriverElement(logObj.modifyObjLocator(AddressFieldInList, "1")),
						logObj.getExcelValue(fileName, sheetName, "EnteredText", rowNum));
			} else {
				TestLogger.log("Fail", "There are more than 1 record, Enter complete address");
			}
		} catch (SeleniumException | IOException e) {
			e.printStackTrace();
		}
	}

	// TC Description : Search with Property ID and Verify search results.
	@Test(groups = "FunctionalTest", testName = "Se_Au_04", enabled = true, description = "(Auction Tab, Search) - Search with Property ID and Verify search results.")
	public void Se_Au_04() throws InterruptedException {
		try {

			String fileName = "Auction.xlsx";
			String sheetName = "Se_Bu_04";
			int rowNum = 1;
			if(!logObj.buildDriverElement(StateField).isDisplayed()) {
			logObj.buildDriverElement(FilterButton).click();
			}
			logObj.buildDriverElement(ClearButton).click();
			
			Thread.sleep(1000);
			String prop_id = logObj.getExcelValue(fileName, sheetName, "EnteredText", rowNum).trim();
			
			
			
			logObj.buildDriverElement(SearchField)
					.sendKeys("1460466");
			
			
			TestLogger.log("Info", "entered Property id as  "+prop_id);
			Thread.sleep(1000);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(unCheckFilterPaid));
			logObj.buildDriverElement(unCheckFilterPaid).click();
			logObj.buildDriverElement(SearchButton).click();

			this.sync();

			if (driver.findElements(logObj.buildLocator(LiveList)).size() == 1) {
				TestLogger.log("Pass", "1 record is returned, Since ID is unique");

				logObj.verifyText(logObj.buildDriverElement(CurrentListPage), "1");
				TestLogger.log("Info",
						"Current listing page is : " + logObj.buildDriverElement(CurrentListPage).getText().trim());
				logObj.buildDriverElement(CurrentListPage).click();
				TestLogger.log("Info", "Total listing page is : "
						+ driver.findElements(By.xpath("//select[@name='pageselect']/option")).size());

				logObj.verifyText(logObj.buildDriverElement(PropertyID),
						"1460466");
			} else if (driver.findElements(logObj.buildLocator(LiveList)).size() > 1) {
				TestLogger.log("Fail", "Duplicate Properties displayed when the user search with property id.");
			} else {
				TestLogger.log("Fail", "No records displayed.");
			}
		} catch (SeleniumException | IOException e) {
			e.printStackTrace();
		}
	}

	// TC Description : Search with invalid text(not property address) and verify
	// the search result
	@Test(groups = "FunctionalTest", testName = "Se_Au_05", enabled = true, description = "(Auction Tab, Search) - Search with invalid text(not property address) and verify the search result")
	public void Se_Au_05() throws InterruptedException {
		try {

			String fileName = "Auction.xlsx";
			String sheetName = "Se_Bu_05";
			int rowNum = 1;

			if(!logObj.buildDriverElement(StateField).isDisplayed()) {
				logObj.buildDriverElement(FilterButton).click();
				}
			logObj.buildDriverElement(ClearButton).click();
			Thread.sleep(1000);

			logObj.buildDriverElement(SearchField)
					.sendKeys(logObj.getExcelValue(fileName, sheetName, "EnteredText", rowNum).trim());
			Thread.sleep(1000);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(unCheckFilterPaid));
			logObj.buildDriverElement(unCheckFilterPaid).click();
			logObj.buildDriverElement(SearchButton).click();

			this.sync();

			logObj.isElementExists(logObj.buildDriverElement(comNoResultPic), "No Result found");
			TestLogger.log("Info", "No Result Pic is displayed");

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			if (driver.findElements(logObj.buildLocator(LiveList)).size() == 0) {
				TestLogger.log("Pass", "0 Records are searched");
			} else {
				TestLogger.log("Fail",
						driver.findElements(logObj.buildLocator(LiveList)).size() + " Records are searched");
			}
			driver.manage().timeouts().implicitlyWait(implicitTimeout, TimeUnit.SECONDS);

			logObj.verifyText(logObj.buildDriverElement(NoDataFoundInList), "No data found");

		} catch (SeleniumException | IOException e) {
			e.printStackTrace();
		}
	}

	// TC Description : Verify when value is searched and then filters are set
	@Test(groups = "FunctionalTest", testName = "Se_Au_06", enabled = true, description = "(Auction Tab, Search) - Search the value and then set filters")
	public void Se_Au_06() throws InterruptedException {
		try {

			String fileName = "Auction.xlsx";
			String sheetName = "Se_Bu_12";// rename as 06
			int rowNum = 1;

			if(!logObj.buildDriverElement(StateField).isDisplayed()) {
				logObj.buildDriverElement(FilterButton).click();
				}
			logObj.buildDriverElement(ClearButton).click();
			Thread.sleep(1000);
			logObj.buildDriverElement(SearchField)
					.sendKeys(logObj.getExcelValue(fileName, sheetName, "EnteredText", rowNum).trim());
			Thread.sleep(1000);
			logObj.buildDriverElement(SearchButton).click();

			this.sync();

			if (driver.findElements(logObj.buildLocator(LiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");
				logObj.verifyText(logObj.buildDriverElement(pf2),
						logObj.getExcelValue(fileName, sheetName, "EnteredText", rowNum));

			} else {
				TestLogger.log("Fail", "No records are searched");
			}

			TestLogger.log("Info", "Records contains both filter and search data");

		} catch (SeleniumException | IOException e) {
			e.printStackTrace();
		}
	}
}