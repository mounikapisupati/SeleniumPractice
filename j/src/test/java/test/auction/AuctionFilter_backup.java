package test.auction;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.SeleniumException;

import library.LogInFunc;
import objectRepo.AuctionOR;
import objectRepo.CommonOR;
import reporting.TestLogger;

public class AuctionFilter_backup extends LogInFunc implements AuctionOR, CommonOR {

	// Variable or Object declaration
	String username;
	String password;
	LogInFunc logObj = new LogInFunc();
	WebDriverWait driverWait;

	// Methods declaration
	public AuctionFilter_backup() throws Exception {
		super();
	}

	private void sync() throws InterruptedException {
		if (driver.findElements(logObj.buildLocator(LoadingList)).size() != 0) {
		}

		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		while (driver.findElements(logObj.buildLocator(LoadingList)).size() >= 1) {
			Thread.sleep(3000);
		}
		driver.manage().timeouts().implicitlyWait(implicitTimeout, TimeUnit.SECONDS);
	}

	// TC Description : Verify user has "Filter" option in "Auction" tab
	@Test(groups = "FunctionalTest", testName = "Fi_Bu_01", enabled = true, description = "(Auction Tab, Filter) - Validate user has filter option")
	public void Fi_Bu_01() throws InterruptedException {
		try {
System.out.println("hello medam");
Thread.sleep(7000);
//			if (driver.getCurrentUrl().contains("Master")) {
				TestLogger.log("Info", "User landed on the Auction page.");
				System.out.println("hello sir");
				Thread.sleep(7000);
				logObj.isElementExists(logObj.buildDriverElement(StateField), "State filter exists");
				logObj.isElementExists(logObj.buildDriverElement(CountyField), "County filter exists");
				TestLogger.log("Info", "County filter exists.");
				logObj.isElementExists(logObj.buildDriverElement(TownshipField), "Township filter exists");
				logObj.isElementExists(logObj.buildDriverElement(TaxYearField), "Tax year filter exists");
				logObj.isElementExists(logObj.buildDriverElement(PropertyClassField),
						"Property class filter exists");
				logObj.isElementExists(logObj.buildDriverElement(UnderwritingStatusField),
						"UnderwritingStatus filter exists");
				logObj.isElementExists(logObj.buildDriverElement(BuyerField), "Buyer filter exists");
				logObj.isElementExists(logObj.buildDriverElement(bidRateList),
						"bidRateList field exists");
				logObj.isElementExists(logObj.buildDriverElement(priorMin), "PriorMin field exists");
				logObj.isElementExists(logObj.buildDriverElement(priorMax), "PriorMax field exists");
				logObj.isElementExists(logObj.buildDriverElement(saleMin),
						"SaleAmountMin field exists");
				logObj.isElementExists(logObj.buildDriverElement(saleMax), "SaleAmountMax field exists");
				logObj.isElementExists(logObj.buildDriverElement(SearchButton), "Search button exists");
				logObj.isElementExists(logObj.buildDriverElement(ClearButton), "Clear button exists");
				TestLogger.log("Info", "Clear button exists.");
				logObj.isElementExists(logObj.buildDriverElement(gradeMenu),
						"Grade field exists");
				TestLogger.log("Info", "Grade field exists.");
				
//			}
		} catch (SeleniumException e) {
			e.printStackTrace();
		}
	}

	/*// TC Description : Verify all the records are displayed, when user doesn't
	// set any filter value and click on "Search"
	@Test(groups = "FunctionalTest", testName = "Fi_Bu_02", enabled = true, description = "(Auction Tab, Filter) - Validate search when there is filter value selected")
	public void Fi_Bu_02() throws InterruptedException {
		try {

			
			if (driver.getCurrentUrl().contains("Master?type=auction")) {
				TestLogger.log("Info", "User landed on the Auction page.");
			}
			Thread.sleep(5000);
//			logObj.buildDriverElement(FilterButton).click();
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(unCheckFilterPaid));
			logObj.buildDriverElement(unCheckFilterPaid).click();
			logObj.buildDriverElement(SearchButton).click();
			this.sync();
			Thread.sleep(5000);
			// add validation here
			System.out.println(logObj.buildDriverElement(CurrentListPage).getText());
//			logObj.verifyText(logObj.buildDriverElement(buyCurrentListPage), "1");
			TestLogger.log("Info",
					"Current listing page is : " + logObj.buildDriverElement(CurrentListPage).getText().trim());

			if (driver.findElements(logObj.buildLocator(LiveList)).size() == 100) {
				TestLogger.log("Pass", "100 records are loaded in listing page");
				Thread.sleep(3000);
//				driverWait.until(
//						ExpectedConditions.elementToBeClickable(logObj.buildDriverElement(pf2)));
				String recordedIdPin = logObj.buildDriverElement(pf2).getText().trim();

				if (recordedIdPin.contains(
						logObj.buildDriverElement(logObj.modifyObjLocator(IdPinInList, "1")).getText().trim())) {
					TestLogger.log("Pass", "First record is selected in the list");
				} else {
					TestLogger.log("Fail", "First record is not selected in the list");
				}
			} else {
				TestLogger.log("Fail", "Records does not loaded in listing page");
			}

		} catch (SeleniumException e) {
			e.printStackTrace();
		}
	}*/

	/*
	 * Now we can not select County option and Township without State. We have
	 * another test case for State selection. we can skip this test case
	 * temporarly.
	 */
	// TC Description : Verify records when user select a value "State, County
	// and Township from filter. (clubbed 3 test cases into single case. )
	@Test(groups = "FunctionalTest", testName = "Fi_Bu_03", enabled = true, description = "(Auction Tab, Filter) - Validate filter, when county value is selected")
	public void Fi_Bu_03() throws InterruptedException {
		try {

			String fileName = "Auction.xlsx";
			String sheetName = "Fi_Bu_03";
			int rowNum = 1;

			if (driver.getCurrentUrl().contains("Master?type=auction")) {
				TestLogger.log("Info", "User landed on the Auction page.");
				System.out.println("im in auction page");
			}
			Thread.sleep(5000);
			System.out.println("Going to 2nd case");
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(FilterButton));
//			logObj.buildDriverElement(FilterButton).click();
//			logObj.buildDriverElement(clearSearch).click();
//			Thread.sleep(1000);
			// Select State filter
			logObj.buildDriverElement(StateField).click();
            Thread.sleep(3000);
			Select state = new Select(logObj.buildDriverElement(StateField));
			state.selectByVisibleText(logObj.getExcelValue(fileName, sheetName, "State", rowNum).trim());
			TestLogger.log("Pass", "State selected.");
			// Select County filter
			Select county = new Select(logObj.buildDriverElement(CountyField));
			county.selectByVisibleText(logObj.getExcelValue(fileName, sheetName, "County", rowNum).trim());
			TestLogger.log("Pass", "County selected.");
			// Select Township filter
			Select township = new Select(logObj.buildDriverElement(TownshipField));
			township.selectByVisibleText(logObj.getExcelValue(fileName, sheetName, "Township", rowNum).trim());
			TestLogger.log("Pass", "Township selected.");

			logObj.buildDriverElement(unCheckFilterPaid).click();
			TestLogger.log("Pass", "Uncheck Filter remove checkbox.");
			logObj.buildDriverElement(SearchButton).click();
			this.sync();
			Thread.sleep(6000);
			if (driver.findElements(logObj.buildLocator(LiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");
				this.sync();
				logObj.buildDriverElement(layoutgrid).click();
				logObj.verifyText(logObj.buildDriverElement(pf2),
						logObj.getExcelValue(fileName, sheetName, "State", rowNum).trim());
				logObj.verifyText(logObj.buildDriverElement(pf2),
						logObj.getExcelValue(fileName, sheetName, "County", rowNum).trim());
				logObj.verifyText(logObj.buildDriverElement(pf2),
						logObj.getExcelValue(fileName, sheetName, "Township", rowNum).trim());

				TestLogger.log("Info", "Records contains filtered data");
			}
		} catch (SeleniumException | IOException e) {
			e.printStackTrace();
		}
	}
/*
	@Test(groups = "FunctionalTest", testName = "Fi_Bu_03_1", enabled = false, description = "(Auction Tab, Filter) - Validate filter, when State value is selected")
	public void Fi_Bu_03_1() throws InterruptedException {
		try {

			String fileName = "Auction.xlsx";
			String sheetName = "Fi_Bu_03";
			int rowNum = 1;

//			if (driver.getCurrentUrl().contains("Master?type=auction")) {
//				TestLogger.log("Info", "User landed on the Auction page.");
//			}
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(FilterButton));
			logObj.buildDriverElement(FilterButton).click();
			logObj.buildDriverElement(ClearButton).click();
			Thread.sleep(1000);
			// Select State filter
			logObj.buildDriverElement(StateField).click();

			Select state = new Select(logObj.buildDriverElement(StateField));
			state.selectByVisibleText(logObj.getExcelValue(fileName, sheetName, "State", rowNum).trim());
			TestLogger.log("Pass", "State selected.");

			logObj.buildDriverElement(unCheckFilterPaid).click();
			TestLogger.log("Pass", "Uncheck Filter remove checkbox.");
			logObj.buildDriverElement(SearchButton).click();
			this.sync();
			Thread.sleep(6000);
			if (driver.findElements(logObj.buildLocator(LiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");
				this.sync();
				logObj.buildDriverElement(layoutgrid).click();
				logObj.verifyText(logObj.buildDriverElement(pf2),
						logObj.getExcelValue(fileName, sheetName, "State", rowNum).trim());

				TestLogger.log("Info", "Records contains filtered data");
			}
		} catch (SeleniumException | IOException e) {
			e.printStackTrace();
		}
	}*/

	/*@Test(groups = "FunctionalTest", testName = "Fi_Bu_03_2", enabled = false, description = "(Auction Tab, Filter) - Validate filter, when State and County value is selected")
	public void Fi_Bu_03_2() throws InterruptedException {
		try {

			String fileName = "Auction.xlsx";
			String sheetName = "Fi_Bu_03";
			int rowNum = 1;

//			if (driver.getCurrentUrl().contains("Master?type=auction")) {
//				TestLogger.log("Info", "User landed on the Auction page.");
//			}
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(FilterButton));
			logObj.buildDriverElement(FilterButton).click();
			logObj.buildDriverElement(ClearButton).click();
			Thread.sleep(1000);
			// Select State filter
			logObj.buildDriverElement(StateField).click();

			Select state = new Select(logObj.buildDriverElement(StateField));
			state.selectByVisibleText(logObj.getExcelValue(fileName, sheetName, "State", rowNum).trim());
			TestLogger.log("Pass", "State selected.");
			// Select County filter
			Select county = new Select(logObj.buildDriverElement(CountyField));
			county.selectByVisibleText(logObj.getExcelValue(fileName, sheetName, "County", rowNum).trim());
			TestLogger.log("Pass", "County selected.");

			logObj.buildDriverElement(unCheckFilterPaid).click();
			TestLogger.log("Pass", "Uncheck Filter remove checkbox.");
			logObj.buildDriverElement(SearchButton).click();
			this.sync();
			Thread.sleep(6000);
			if (driver.findElements(logObj.buildLocator(LiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");
				this.sync();
				logObj.buildDriverElement(layoutgrid).click();
				logObj.verifyText(logObj.buildDriverElement(pf2),
						logObj.getExcelValue(fileName, sheetName, "State", rowNum).trim());
				logObj.verifyText(logObj.buildDriverElement(pf2),
						logObj.getExcelValue(fileName, sheetName, "County", rowNum).trim());

				TestLogger.log("Info", "Records contains filtered data");
			}
		} catch (SeleniumException | IOException e) {
			e.printStackTrace();
		}
	}*/

	/*@Test(groups = "FunctionalTest", testName = "Fi_Bu_03_3", enabled = false, description = "(Auction Tab, Filter) - Validate filter, when State, County and Township value is selected")
	public void Fi_Bu_03_3() throws InterruptedException {
		try {

			String fileName = "Auction.xlsx";
			String sheetName = "Fi_Bu_03";
			int rowNum = 1;

//			if (driver.getCurrentUrl().contains("Master?type=auction")) {
//				TestLogger.log("Info", "User landed on the Auction page.");
//			}
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(FilterButton));
			logObj.buildDriverElement(FilterButton).click();
			logObj.buildDriverElement(ClearButton).click();
			Thread.sleep(1000);
			// Select State filter
			logObj.buildDriverElement(StateField).click();

			Select state = new Select(logObj.buildDriverElement(StateField));
			state.selectByVisibleText(logObj.getExcelValue(fileName, sheetName, "State", rowNum).trim());
			TestLogger.log("Pass", "State selected.");
			// Select County filter
			Select county = new Select(logObj.buildDriverElement(CountyField));
			county.selectByVisibleText(logObj.getExcelValue(fileName, sheetName, "County", rowNum).trim());
			TestLogger.log("Pass", "County selected.");
			// Select Township filter
			Select township = new Select(logObj.buildDriverElement(TownshipField));
			township.selectByVisibleText(logObj.getExcelValue(fileName, sheetName, "Township", rowNum).trim());
			TestLogger.log("Pass", "Township selected.");

			logObj.buildDriverElement(unCheckFilterPaid).click();
			TestLogger.log("Pass", "Uncheck Filter remove checkbox.");
			logObj.buildDriverElement(SearchButton).click();
			this.sync();
			Thread.sleep(6000);
			if (driver.findElements(logObj.buildLocator(LiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");
				this.sync();
				logObj.buildDriverElement(layoutgrid).click();
				logObj.verifyText(logObj.buildDriverElement(pf2),
						logObj.getExcelValue(fileName, sheetName, "State", rowNum).trim());
				logObj.verifyText(logObj.buildDriverElement(pf2),
						logObj.getExcelValue(fileName, sheetName, "County", rowNum).trim());
				logObj.verifyText(logObj.buildDriverElement(pf2),
						logObj.getExcelValue(fileName, sheetName, "Township", rowNum).trim());

				TestLogger.log("Info", "Records contains filtered data");
			}
		} catch (SeleniumException | IOException e) {
			e.printStackTrace();
		}
	}*/

	// TC Description : Verify records when user select a value from filter "Tax
	// Year" and click on "Search"
	@Test(groups = "FunctionalTest", testName = "Fi_Bu_04", enabled = true, description = "(Auction Tab, Filter) - Validate filter, when tax year value is selected")
	public void Fi_Bu_04() throws InterruptedException {
		try {

			String fileName = "Auction.xlsx";
			String sheetName = "Fi_Bu_04";
			int rowNum = 1;
//			if (driver.getCurrentUrl().contains("Master?type=auction")) {
//				TestLogger.log("Info", "User landed on the Auction page.");
//			}
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(FilterButton));
//			 logObj.buildDriverElement(FilterButton).click();
			 logObj.buildDriverElement(ClearButton).click();
			Thread.sleep(1000);

			System.out.println("Going to select tax year");

			Select taxYearlist = new Select(logObj.buildDriverElement(TaxYearField));
			taxYearlist.selectByVisibleText(logObj.getExcelValue(fileName, sheetName, "Tax Year", rowNum).trim());
			TestLogger.log("Info", "Tax Year selected.");
			System.out.println(driver.findElement(By.xpath("//select[@name='TaxYear']")).getText() + "++++++++++++");

			logObj.buildDriverElement(unCheckFilterPaid).click();
			logObj.buildDriverElement(SearchButton).click();
			this.sync();

			if (driver.findElements(logObj.buildLocator(LiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");

				jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(viewDetailsBtn));

				// find the list of history records.
				String s = driver.findElement(By.xpath("//nav//ul[2]/li/a")).getText();

				String[] parts = s.split(" ");
				String lastWord = parts[parts.length - 1];

				int t = Integer.parseInt(lastWord);
				// if there is only one row then go to inside if condition.
				if (t == 1) {
					logObj.verifyText(logObj.buildDriverElement(Year).getText(),
							logObj.getExcelValue(fileName, sheetName, "Tax Year", rowNum).trim());
				} else {
					logObj.verifyText(logObj.buildDriverElement(Year).getText(),
							logObj.getExcelValue(fileName, sheetName, "Tax Year", rowNum).trim());
				}

			}
		} catch (SeleniumException | IOException e) {
			e.printStackTrace();
		}
	}

	// TC Description : Verify records when user select a value from filter
	// "Property Class" as Commercial improved and click on "Search"
	@Test(groups = "FunctionalTest", testName = "Fi_Bu_05", enabled = true, description = "(Auction Tab, Filter) - Validate filter, when property class value is selected")
	public void Fi_Bu_05() throws InterruptedException {
		try {

			String fileName = "Auction.xlsx";
			String sheetName = "Fi_Bu_05";
			int rowNum = 1;

//			if (driver.getCurrentUrl().contains("Master?type=auction")) {
//				TestLogger.log("Info", "User landed on the Auction page.");
//			}
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(FilterButton));
//			logObj.buildDriverElement(FilterButton).click();
			logObj.buildDriverElement(ClearButton).click();
			Thread.sleep(1000);

			Select propClass = new Select(logObj.buildDriverElement(PropertyClassField));
			propClass.selectByVisibleText(logObj.getExcelValue(fileName, sheetName, "Property Class", rowNum).trim());

			logObj.buildDriverElement(unCheckFilterPaid).click();

			logObj.buildDriverElement(SearchButton).click();
			this.sync();

			if (driver.findElements(logObj.buildLocator(LiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");
				jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(headerSection));
				logObj.buildDriverElement(headerSection).click();
				logObj.verifyText(logObj.buildDriverElement(propertyClass),
						logObj.getExcelValue(fileName, sheetName, "Property Class", rowNum).trim());
				System.out.println("i am done");

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
	@Test(groups = "FunctionalTest", testName = "Fi_Bu_06", enabled = true, description = "(Auction Tab, Filter) - Validate filter, when underwriting status value is selected")
	public void Fi_Bu_06() throws InterruptedException {
		try {

			String fileName = "Auction.xlsx";
			String sheetName = "Fi_Bu_06";
			int rowNum = 1;

//			if (driver.getCurrentUrl().contains("Master?type=auction")) {
//				TestLogger.log("Info", "User landed on the Auction page.");
//			}
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(FilterButton));
//			logObj.buildDriverElement(FilterButton).click();
			logObj.buildDriverElement(ClearButton).click();

			// select first value
			Select underWritelist = new Select(logObj.buildDriverElement(UnderwritingStatusField));
			underWritelist.selectByVisibleText(logObj.getExcelValue(fileName, sheetName, "UnderWrite", rowNum).trim());
			Thread.sleep(1000);
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(unCheckFilterPaid));
			logObj.buildDriverElement(unCheckFilterPaid).click();

			logObj.buildDriverElement(SearchButton).click();
			// Thread.sleep(8000);
			this.sync();

			if (driver.findElements(logObj.buildLocator(LiveList)).size() >= 4) {
				TestLogger.log("Pass", "Search record is returned");

				this.sync();
				Thread.sleep(50000);
				jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(statsField));
				System.out.println(logObj.buildDriverElement(statsField).getText() + "**********");
				logObj.verifyText(logObj.buildDriverElement(statsField),
						logObj.getExcelValue(fileName, sheetName, "expectedUnderWrite", rowNum).trim());
			} else {
				TestLogger.log("Fail", "No records are searched");
			}
			// Select second value
			TestLogger.log("Info", "Second iteration with different value");
			rowNum = 2;
			logObj.buildDriverElement(FilterButton).click();
			logObj.buildDriverElement(ClearButton).click();
			Thread.sleep(1000);

			underWritelist.selectByVisibleText(logObj.getExcelValue(fileName, sheetName, "UnderWrite", rowNum).trim());
			Thread.sleep(1000);
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(unCheckFilterPaid));
			logObj.buildDriverElement(unCheckFilterPaid).click();
			logObj.buildDriverElement(SearchButton).click();
			Thread.sleep(4000);
			this.sync();

			if (driver.findElements(logObj.buildLocator(LiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");
				this.sync();
				System.out.println(logObj.buildDriverElement(statsField).getText() + "--------------");
				jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(statsField));
				logObj.verifyText(logObj.buildDriverElement(statsField),
						logObj.getExcelValue(fileName, sheetName, "expectedUnderWrite", rowNum).trim());
			} else {
				TestLogger.log("Fail", "No records are searched");
			}
			// Select third value
			TestLogger.log("Info", "Third iteration with different value");
			rowNum = 3;

			logObj.buildDriverElement(FilterButton).click();
			logObj.buildDriverElement(ClearButton).click();
			Thread.sleep(1000);

			underWritelist.selectByVisibleText(logObj.getExcelValue(fileName, sheetName, "UnderWrite", rowNum).trim());
			Thread.sleep(1000);
			logObj.buildDriverElement(SearchButton).click();
			Thread.sleep(4000);
			this.sync();
			if (driver.findElements(logObj.buildLocator(LiveList)).size() >= 4) {
				TestLogger.log("Pass", "Search record is returned");
				this.sync();
				jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(statsField));
				logObj.verifyText(logObj.buildDriverElement(statsField),
						logObj.getExcelValue(fileName, sheetName, "expectedUnderWrite", rowNum).trim());

			} else {
				TestLogger.log("Fail", "No records are searched");
			}

		} catch (SeleniumException | IOException e) {
			e.printStackTrace();
		}
	}

	// TC Description : Verify records when user select include value from
	// filter and click on "Search"
	@Test(groups = "FunctionalTest", testName = "Fi_Bu_07", enabled = true, description = "(Auction Tab, Filter) - Validate filter, when user select include value from filter and click on Search")
	public void Fi_Bu_07() throws InterruptedException, IOException {
		try {

			String fileName = "Auction.xlsx";
			String sheetName = "Fi_Bu_07";
			int rowNum = 1;

//			if (driver.getCurrentUrl().contains("Master?type=auction")) {
//				TestLogger.log("Info", "User landed on the Auction page.");
//			}
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(FilterButton));

//			 logObj.buildDriverElement(FilterButton).click();
			 logObj.buildDriverElement(ClearButton).click();

			// select include value
			logObj.buildDriverElement(logObj.modifyObjLocator(includeList, "Task (multi)")).click();
			logObj.buildDriverElement(logObj.modifyObjLocator(include,
					logObj.getExcelValue(fileName, sheetName, "TagName", rowNum).trim())).click();

			logObj.buildDriverElement(logObj.modifyObjLocator(includeList,
					logObj.getExcelValue(fileName, sheetName, "TagName", rowNum).trim())).click();

			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(unCheckFilterPaid));
			logObj.buildDriverElement(unCheckFilterPaid).click();

			logObj.buildDriverElement(SearchButton).click();
			// Thread.sleep(8000);
			this.sync();

			if (driver.findElements(logObj.buildLocator(LiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");
				logObj.isElementExists(
						logObj.buildDriverElement(logObj.modifyObjLocator(selected_tags,
								logObj.getExcelValue(fileName, sheetName, "TagName", rowNum).trim())),
						"Selected tag exists");
				String selected_tag = logObj
						.buildDriverElement(logObj.modifyObjLocator(selected_tags,
								logObj.getExcelValue(fileName, sheetName, "TagName", rowNum).trim()))
						.getAttribute("class");
				logObj.verifyText(selected_tag, "btn-tags btn app-button ng-scope ng-isolate-scope btn-primary");

			} else {
				TestLogger.log("Fail", "No records are searched");
			}

		} catch (SeleniumException e) {
			e.printStackTrace();
		}
	}

	// TC Description : Verify records when user select exclude value from
	// filter and click on "Search"
	@Test(groups = "FunctionalTest", testName = "Fi_Bu_08", enabled = true, description = "(Auction Tab, Filter) - Validate filter, when user select exclude value from filter and click on Search")
	public void Fi_Bu_08() throws InterruptedException, IOException {
		try {

			String fileName = "Auction.xlsx";
			String sheetName = "Fi_Bu_07";
			int rowNum = 1;
			this.sync();
//			if (driver.getCurrentUrl().contains("Master?type=auction")) {
//				TestLogger.log("Info", "User landed on the Auction page.");
//			}
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(FilterButton));

			 logObj.buildDriverElement(FilterButton).click();
			 logObj.buildDriverElement(ClearButton).click();

			// select include value
			logObj.buildDriverElement(logObj.modifyObjLocator(excludeList, "Task (multi)")).click();

			logObj.buildDriverElement(logObj.modifyObjLocator(exclude,
					logObj.getExcelValue(fileName, sheetName, "excludeTag", rowNum).trim())).click();

			String ss = logObj.getExcelValue(fileName, sheetName, "excludeTag", rowNum).trim();
			logObj.buildDriverElement(logObj.modifyObjLocator(excludeList, ss)).click();

			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(unCheckFilterPaid));
			logObj.buildDriverElement(unCheckFilterPaid).click();

			logObj.buildDriverElement(SearchButton).click();
			// Thread.sleep(8000);
			this.sync();

			if (driver.findElements(logObj.buildLocator(LiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");
				logObj.isElementExists(
						logObj.buildDriverElement(logObj.modifyObjLocator(selected_tags,
								logObj.getExcelValue(fileName, sheetName, "excludeTag", rowNum).trim())),
						"Selected tag does not exists");
				String unselected_tag = logObj
						.buildDriverElement(logObj.modifyObjLocator(selected_tags,
								logObj.getExcelValue(fileName, sheetName, "excludeTag", rowNum).trim()))
						.getAttribute("class");

				logObj.verifyText(unselected_tag, "btn-tags btn app-button ng-scope ng-isolate-scope btn-default");

			} else {
				TestLogger.log("Fail", "No records are searched");
			}

		} catch (SeleniumException e) {
			e.printStackTrace();
		}
	}

	// TC Description : Verify records when user enters a value to the filter
	// "Market" minimum and maximum values.
	@Test(groups = "FunctionalTest", testName = "Fi_Bu_09", enabled = false, description = "(Auction Tab, Filter) - Validate filter, for Market values ")
	public void Fi_Bu_09() throws InterruptedException {
		try {

			String fileName = "Auction.xlsx";
			String sheetName = "Fi_Bu_09";
			int rowNum = 1;

//			if (driver.getCurrentUrl().contains("Master?type=auction")) {
//				TestLogger.log("Info", "User landed on the Auction page.");
//			}
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(FilterButton));
			 logObj.buildDriverElement(FilterButton).click();
			 logObj.buildDriverElement(ClearButton).click();
			Thread.sleep(1000);

			logObj.buildDriverElement(marketMin)
					.sendKeys(logObj.getExcelValue(fileName, sheetName, "min", rowNum).trim());

			logObj.buildDriverElement(marketMax)
					.sendKeys(logObj.getExcelValue(fileName, sheetName, "max", rowNum).trim());

			int EnteredNumber = Integer.parseInt(logObj.getExcelValue(fileName, sheetName, "max", rowNum).trim());
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(unCheckFilterPaid));
			logObj.buildDriverElement(unCheckFilterPaid).click();
			logObj.buildDriverElement(SearchButton).click();
			this.sync();

			if (driver.findElements(logObj.buildLocator(LiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");

				String ActualText = logObj.buildDriverElement(MarketAmt).getText();
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
	// "BidRate" minimum and maximum values.
	@Test(groups = "FunctionalTest", testName = "Fi_Bu_10", enabled = true, description = "(Auction Tab, Filter) - Validate filter, for Market values ")
	public void Fi_Bu_10() throws InterruptedException {
		try {

			String fileName = "Auction.xlsx";
			String sheetName = "Fi_Bu_10";
			int rowNum = 1;

//			if (driver.getCurrentUrl().contains("Master?type=auction")) {
//				TestLogger.log("Info", "User landed on the Auction page.");
//			}
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(FilterButton));
			 logObj.buildDriverElement(FilterButton).click();
			 logObj.buildDriverElement(ClearButton).click();
			Thread.sleep(2000);
			
			Select bidRate = new Select(logObj.buildDriverElement(bidRateList));
			bidRate.selectByVisibleText(logObj.getExcelValue(fileName, sheetName, "bidRate", rowNum).trim());
			TestLogger.log("Info", "BidRate selected.");

			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(unCheckFilterPaid));
			logObj.buildDriverElement(unCheckFilterPaid).click();
			logObj.buildDriverElement(SearchButton).click();
			this.sync();

			if (driver.findElements(logObj.buildLocator(LiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");

				// find the list of history records.
				String s = driver.findElement(By.xpath("//label[@title='Status']/../../../div[2]")).getText();

				String[] parts = s.split(" ");
				String lastWord = parts[parts.length - 1];
				logObj.verifyText(lastWord, logObj.getExcelValue(fileName, sheetName, "Tax Year", rowNum).trim());
				
				TestLogger.log("Pass", "Filtered value displayed.");
			} else {
				TestLogger.log("Fail", "No records are searched");

			}
		} catch (SeleniumException | IOException e) {
			e.printStackTrace();
		}
	}

	// TC Description : Verify records when user select a value from filter
	// "Buyer" and click on "Search"
	/*
	 * Test passed but there is no test data when we searched with the current
	 * previous test data.
	 */
	@Test(groups = "FunctionalTest", testName = "Fi_Bu_11", enabled = false, description = "(Auction Tab, Filter) - Validate filter, when buyer value is selected")
	public void Fi_Bu_11() throws InterruptedException {
		try {

			String fileName = "Auction.xlsx";
			String sheetName = "Fi_Bu_11";
			// int rowNum = 1; //there is no test data with this so added
			// another one.
			int rowNum = 2;

//			if (driver.getCurrentUrl().contains("Master?type=auction")) {
//				TestLogger.log("Info", "User landed on the Auction page.");
//			}
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(FilterButton));
			logObj.buildDriverElement(FilterButton).click();
			logObj.buildDriverElement(ClearButton).click();
			Thread.sleep(1000);

			logObj.buildDriverElement(BuyerField).click();
			logObj.buildDriverElement(BuyerField)
					.sendKeys(logObj.getExcelValue(fileName, sheetName, "Buyer", rowNum).trim());
			Thread.sleep(1000);
			logObj.buildDriverElement(logObj.modifyObjLocator(BuyerRecordSelect, "1")).click();
			Thread.sleep(1000);
			logObj.buildDriverElement(unCheckFilterPaid).click();
			logObj.buildDriverElement(SearchButton).click();
			this.sync();

			if (driver.findElements(logObj.buildLocator(LiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");
				jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(viewDetailsBtn));
				logObj.buildDriverElement(viewDetailsBtn).click();
				Thread.sleep(2000);
				logObj.isElementExists(logObj.buildDriverElement(TaxSaleHistoryDetailsDialogTitle),
						"Tax history details dialog is open");

				logObj.isElementExists(
						logObj.buildDriverElement(logObj.modifyObjLocator(TaxSaleHistoryDetailsTables, "Auctions",
								logObj.getExcelValue(fileName, sheetName, "Buyer", rowNum).trim())),
						"Records contains filtered data");
				logObj.buildDriverElement(TaxSaleHistoryDetailsDialogClose).click();
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
	// there is no records when we search for grade "A".
	@Test(groups = "FunctionalTest", testName = "Fi_Bu_12", enabled = false, description = "(Auction Tab, Filter) - Validate filter, when one value is selected from Grade")
	public void Fi_Bu_12() throws InterruptedException {
		try {

			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(FilterButton));
//			logObj.buildDriverElement(FilterButton).click();
			logObj.buildDriverElement(ClearButton).click();

			driver.findElement(By.xpath("//button/span[contains(text(), 'Area Grade (multi)')]")).click();
			if (!logObj.buildDriverElement(logObj.modifyObjLocator(gradeSelect, "B")).isSelected()) {
				logObj.buildDriverElement(logObj.modifyObjLocator(gradeSelect, "B")).click();
			}
//			driver.findElement(By.xpath("//select[@name='Grade']/..//button/span[contains(text(), 'B')]")).click();
			Thread.sleep(2000);
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(unCheckFilterPaid));
			logObj.buildDriverElement(unCheckFilterPaid).click();
			logObj.buildDriverElement(SearchButton).click();
			this.sync();
			Thread.sleep(8000);
			if (driver.findElements(logObj.buildLocator(LiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned with selected grade");
				jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(logObj.modifyObjLocator(verifyAreaGrade, "B")));
				String cmpText = logObj.buildDriverElement(logObj.modifyObjLocator(verifyAreaGrade, "B")).getText();
				if (cmpText.startsWith("B")) {
					logObj.isElementExists(logObj.buildDriverElement(logObj.modifyObjLocator(verifyAreaGrade, "B")),
							"Searched value exists");
				} else {
					TestLogger.log("Fail", "Searched value doesn't exists in the Stats area grade.");
				}

				// Select second value
				TestLogger.log("Info", "Second iteration with different value");
				jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(FilterButton));
				logObj.buildDriverElement(FilterButton).click();
				logObj.buildDriverElement(ClearButton).click();
				Thread.sleep(3000);

				driver.findElement(By.xpath("//button/span[contains(text(), 'Area Grade (multi)')]")).click();

				if (!logObj.buildDriverElement(logObj.modifyObjLocator(gradeSelect, "?")).isSelected()) {
					logObj.buildDriverElement(logObj.modifyObjLocator(gradeSelect, "?")).click();
				}
				Thread.sleep(3000);
				driver.findElement(By.xpath("//select[@name='Grade']/..//button/span[contains(text(), '?')]")).click();
				jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(unCheckFilterPaid));
				logObj.buildDriverElement(unCheckFilterPaid).click();
				logObj.buildDriverElement(SearchButton).click();
				this.sync();
				Thread.sleep(10000);
				if (driver.findElements(logObj.buildLocator(LiveList)).size() >= 1) {
					TestLogger.log("Pass", "Search record is returned");

					cmpText = logObj.buildDriverElement(logObj.modifyObjLocator(verifyAreaGrade, "?")).getText();
					if (cmpText.startsWith("?")) {
						logObj.isElementExists(logObj.buildDriverElement(logObj.modifyObjLocator(verifyAreaGrade, "?")),
								"Searched value exists");
					} else {
						TestLogger.log("Fail", "Searched value doesn't exists in the Stats area grade.");
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

	// TC Description : Verify records when user select multiple grades and
	// click on "Search"
	@Test(groups = "FunctionalTest", testName = "Fi_Bu_13", enabled = false, description = "(Auction Tab, Filter) - Validate filter, Verify records when user select multiple grades andclick on Search")
	public void Fi_Bu_13() throws InterruptedException {
		try {

//			if (driver.getCurrentUrl().contains("Master?type=auction")) {
//				TestLogger.log("Info", "User landed on the Auction page.");
//			}

			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(FilterButton));
//			logObj.buildDriverElement(FilterButton).click();
			logObj.buildDriverElement(ClearButton).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//button/span[contains(text(), 'Area Grade (multi)')]")).click();
			if (!logObj.buildDriverElement(logObj.modifyObjLocator(gradeSelect, "A")).isSelected()) {
				logObj.buildDriverElement(logObj.modifyObjLocator(gradeSelect, "A")).click();
				
				// driver.findElement(By.xpath("//ul/li/label/input[@title='B']")).click();
				// driver.findElement(By.xpath("//ul/li/label/input[@title='D']")).click();
				logObj.buildDriverElement(logObj.modifyObjLocator(gradeMultiSelect, "B")).click();
				logObj.buildDriverElement(logObj.modifyObjLocator(gradeMultiSelect, "D")).click();
				
			}

			driver.findElement(By.xpath("//select[@name='Grade']/..//button/span[contains(text(), 'A, B, D')]"))
					.click();

			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(unCheckFilterPaid));
			logObj.buildDriverElement(unCheckFilterPaid).click();
			logObj.buildDriverElement(SearchButton).click();
			this.sync();
			Thread.sleep(6000);
			if (driver.findElements(logObj.buildLocator(LiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");
			} else {
				TestLogger.log("Fail", "No records are searched");
			}

		} catch (SeleniumException e) {
			e.printStackTrace();
		}
	}

	// TC Description : Verify records when user select all Grades and click on
	// "Search"
	/*
	 * We can remove this case. we covered same functionality in the previous
	 * test case.
	 */
	@Test(groups = "FunctionalTest", testName = "Fi_Bu_14", enabled = false, description = "(Auction Tab, Filter) - Validate filter, Verify records when user select all Grades and click on Search")
	public void Fi_Bu_14() throws InterruptedException {
		try {

//			if (driver.getCurrentUrl().contains("Master?type=auction")) {
//				TestLogger.log("Info", "User landed on the Auction page.");
//			}

			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(FilterButton));
//			logObj.buildDriverElement(FilterButton).click();
			logObj.buildDriverElement(ClearButton).click();
			Thread.sleep(1000);

			List<WebElement> myElements = driver
					.findElements(logObj.buildLocator(logObj.modifyObjLocator(gradeSelect, "Grade")));

			for (WebElement e : myElements) {
				System.out.println(e.getText());
				if (!logObj.buildDriverElement(logObj.modifyObjLocator(gradeSelect, e.getText().toLowerCase().trim()))
						.isSelected()) {
					logObj.buildDriverElement(logObj.modifyObjLocator(gradeSelect, e.getText().toLowerCase().trim()))
							.click();
				}
			}
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(unCheckFilterPaid));
			logObj.buildDriverElement(unCheckFilterPaid).click();
			logObj.buildDriverElement(SearchButton).click();
			this.sync();
			Thread.sleep(8000);
			if (driver.findElements(logObj.buildLocator(LiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");
				int cnt = 0;
				for (WebElement e : myElements) {
					if (logObj.buildDriverElement(StatsSectionAreaGradeValue).getText().trim()
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

	// TC Description : Verify records when user enters a value to the filter
	// "Prior" minimum and maximum values.
	@Test(groups = "FunctionalTest", testName = "Fi_Bu_15", enabled = false, description = "(Auction Tab, Filter) - Validate filter, for Prior amount ")
	public void Fi_Bu_15() throws InterruptedException {
		try {

			String fileName = "Auction.xlsx";
			String sheetName = "Fi_Bu_18";
			int rowNum = 1;

//			if (driver.getCurrentUrl().contains("Master?type=auction")) {
//				TestLogger.log("Info", "User landed on the Auction page.");
//			}
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(FilterButton));
//			logObj.buildDriverElement(FilterButton).click();
			logObj.buildDriverElement(ClearButton).click();
			Thread.sleep(1000);

			logObj.buildDriverElement(priorMin)
					.sendKeys(logObj.getExcelValue(fileName, sheetName, "min", rowNum).trim());

			logObj.buildDriverElement(priorMax)
					.sendKeys(logObj.getExcelValue(fileName, sheetName, "max", rowNum).trim());

			int EnteredNumber = Integer.parseInt(logObj.getExcelValue(fileName, sheetName, "max", rowNum).trim());
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(unCheckFilterPaid));
			logObj.buildDriverElement(unCheckFilterPaid).click();
			logObj.buildDriverElement(SearchButton).click();
			this.sync();

			if (driver.findElements(logObj.buildLocator(LiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");

				String ActualText = logObj.buildDriverElement(PriorAmt).getText();
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
	// "Open Sales" minimum and maximum values.
	@Test(groups = "FunctionalTest", testName = "Fi_Bu_16", enabled = false, description = "(Auction Tab, Filter) - Validate filter, for open sales min and max amount selected")
	public void Fi_Bu_16() throws InterruptedException {
		try {

			String fileName = "Auction.xlsx";
			String sheetName = "Fi_Bu_48";
			int rowNum = 1;

//			if (driver.getCurrentUrl().contains("Master?type=auction")) {
//				TestLogger.log("Info", "User landed on the Auction page.");
//			}

			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(FilterButton));
//			logObj.buildDriverElement(FilterButton).click();
			logObj.buildDriverElement(ClearButton).click();
			Thread.sleep(1000);

			logObj.buildDriverElement(saleMin)
					.sendKeys(logObj.getExcelValue(fileName, sheetName, "min", rowNum).trim());

			logObj.buildDriverElement(saleMax)
					.sendKeys(logObj.getExcelValue(fileName, sheetName, "max", rowNum).trim());

			int EnteredNumber = Integer.parseInt(logObj.getExcelValue(fileName, sheetName, "max", rowNum).trim());
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(unCheckFilterPaid));
			logObj.buildDriverElement(unCheckFilterPaid).click();
			logObj.buildDriverElement(SearchButton).click();
			this.sync();

			if (driver.findElements(logObj.buildLocator(LiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");

				String ActualText = logObj.buildDriverElement(OpenSaleAmt).getText();
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

}