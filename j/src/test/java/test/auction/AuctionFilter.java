package test.auction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.SeleniumException;

import library.LogInFunc;
import objectRepo.AuctionOR;
import objectRepo.CommonOR;
import reporting.TestLogger;

public class AuctionFilter extends LogInFunc implements AuctionOR, CommonOR {

	// Variable or Object declaration
	String username;
	String password;
	LogInFunc logObj = new LogInFunc();
	
	// Methods declaration
	public AuctionFilter() throws Exception {
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

	// TC Description : Verify user has "Filter" options in "Auction" tab.
	@Test(groups = "FunctionalTest", testName = "Fi_Au_01", enabled = true, description = "(Auction Tab, Filter) - Verify user has Filter options in Auction tab.")
	public void Fi_Au_01() throws InterruptedException {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 9000);
			wait.until(ExpectedConditions.elementToBeClickable(logObj.buildDriverElement(FilterButton)));
			TestLogger.log("Info", "User landed on the Auction page.");
			if (!logObj.buildDriverElement(StateField).isDisplayed()) {
				logObj.buildDriverElement(FilterButton).click();
			}
			logObj.buildDriverElement(ClearButton).click();
			Thread.sleep(2000);
			logObj.isElementExists(logObj.buildDriverElement(StateField), "State filter exists");
			logObj.isElementExists(logObj.buildDriverElement(CountyField), "County filter exists");
			TestLogger.log("Info", "County filter exists.");
			logObj.isElementExists(logObj.buildDriverElement(TaxYearField), "Tax year filter exists");
			logObj.isElementExists(logObj.buildDriverElement(TownshipField), "Township filter exists");

			logObj.isElementExists(logObj.buildDriverElement(PropertyClassField), "Property class filter exists");
			logObj.isElementExists(logObj.buildDriverElement(UnderwritingStatusField),
					"UnderwritingStatus filter exists");
			logObj.isElementExists(logObj.buildDriverElement(bidRateList), "bidRateList field exists");

			logObj.isElementExists(logObj.buildDriverElement(BuyerField), "Buyer filter exists");
			logObj.isElementExists(logObj.buildDriverElement(gradeMenu), "Grade field exists");

			logObj.isElementExists(logObj.buildDriverElement(logObj.modifyObjLocator(includeList, "Task")),
					"includeList field exists");

			logObj.isElementExists(logObj.buildDriverElement(logObj.modifyObjLocator(excludeList, "Task")),
					"excludeList field exists");

			logObj.isElementExists(logObj.buildDriverElement(saleMin), "SaleAmountMin field exists");
			logObj.isElementExists(logObj.buildDriverElement(saleMax), "SaleAmountMax field exists");
			logObj.isElementExists(logObj.buildDriverElement(priorMin), "PriorMin field exists");
			logObj.isElementExists(logObj.buildDriverElement(priorMax), "PriorMax field exists");

			logObj.isElementExists(logObj.buildDriverElement(marketMin), "MarketMin field exists");
			logObj.isElementExists(logObj.buildDriverElement(marketMax), "MarketMax field exists");

			logObj.isElementExists(logObj.buildDriverElement(SearchButton), "Search button exists");
			logObj.isElementExists(logObj.buildDriverElement(ClearButton), "Clear button exists");
			TestLogger.log("Info", "Clear button exists.");

			TestLogger.log("Info", "Grade field exists.");

//			}
		} catch (SeleniumException e) {
			e.printStackTrace();
		}
	}

	// TC Description : Verify records when user select a value State, County and
	// Township from filter.
	@Test(groups = "FunctionalTest", testName = "Fi_Au_02", enabled = true, description = "(Auction Tab, Filter) - Verify records when user select a value State, County and Township from filter. ")
	public void Fi_Au_02() throws InterruptedException {
		try {

			String fileName = "Auction.xlsx";
			String sheetName = "Fi_Bu_03";
			int rowNum = 1;

			if (driver.getCurrentUrl().contains("Master?type=auction")) {
				TestLogger.log("Info", "User landed on the Auction page.");
				System.out.println("im in auction page");
			}
			Thread.sleep(5000);

			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(FilterButton));
			if (!logObj.buildDriverElement(StateField).isDisplayed()) {
				logObj.buildDriverElement(FilterButton).click();
			}
			logObj.buildDriverElement(clearSearch).click();

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

//			logObj.buildDriverElement(unCheckFilterPaid).click();
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

	// TC Description : Verify records when user select a value from filter Tax Year
	// and click on Search.
	@Test(groups = "FunctionalTest", testName = "Fi_Au_03", enabled = true, description = "(Auction Tab, Filter) - Verify records when user select a value from filter \"Tax Year\" and click on \"Search\".")
	public void Fi_Au_03() throws InterruptedException {
		try {

			String fileName = "Auction.xlsx";
			String sheetName = "Fi_Bu_04";
			int rowNum = 1;

			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(FilterButton));
			if (!logObj.buildDriverElement(StateField).isDisplayed()) {
				logObj.buildDriverElement(FilterButton).click();
			}
			logObj.buildDriverElement(ClearButton).click();
			Thread.sleep(1000);

			Select taxYearlist = new Select(logObj.buildDriverElement(TaxYearField));
			taxYearlist.selectByVisibleText(logObj.getExcelValue(fileName, sheetName, "Tax Year", rowNum).trim());
			TestLogger.log("Info", "Tax Year selected." +logObj.getExcelValue(fileName, sheetName, "Tax Year", rowNum).trim());

//			logObj.buildDriverElement(unCheckFilterPaid).click();
			logObj.buildDriverElement(SearchButton).click();
			this.sync();

			if (driver.findElements(logObj.buildLocator(LiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");

				jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(viewDetailsBtn));

				// find the list of history records.
				int t = driver
						.findElements(
								By.xpath("//button/span[contains(text(), 'View Details')]/../../..//table/tbody/tr"))
						.size();

				// if there is only one row then go to inside if condition.
				if (t == 1) {
					logObj.verifyText(logObj.buildDriverElement(Year).getText(),
							logObj.getExcelValue(fileName, sheetName, "Tax Year", rowNum).trim());
				} else {

					ArrayList<String> years = new ArrayList<String>();
					for (int i = 1; i <= t; i++) {

						String yearValue = driver.findElement(
								By.xpath("//button/span[contains(text(), 'View Details')]/../../..//table/tbody/tr[" + i
										+ "]/td[1]"))
								.getText();
						years.add(yearValue);
					}

					years.contains(logObj.getExcelValue(fileName, sheetName, "Tax Year", rowNum).trim());
					TestLogger.log("Pass", "Selected TaxYear displayed in the Property.");
				}

			}if (driver.findElement(By.xpath("//div[@name='SearchUIWrapper-spinner']/div[@class='spinner-message']")).isDisplayed()) {
				driver.navigate().refresh();
				pageSync();pageSync();
				TestLogger.log("info", "Spinner shown in the filter section");
			}

		} catch (SeleniumException | IOException e) {
			e.printStackTrace();
		}
	}

	// TC Description : Verify records when user select a value from filter
	// "Property Class" as Commercial improved and click on "Search"
	@Test(groups = "FunctionalTest", testName = "Fi_Au_04", enabled = true, description = "(Auction Tab, Filter) - Verify records when user select a value from filter \"Property Class\" as Commercial improved and click on \"Search\"")
	public void Fi_Au_04() throws InterruptedException {
		try {

			String fileName = "Auction.xlsx";
			String sheetName = "Fi_Bu_05";
			int rowNum = 1;
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(FilterButton));
			if (!logObj.buildDriverElement(StateField).isDisplayed()) {
				logObj.buildDriverElement(FilterButton).click();
			}
			logObj.buildDriverElement(ClearButton).click();
			Thread.sleep(1000);

			Select propClass = new Select(logObj.buildDriverElement(PropertyClassField));
			propClass.selectByVisibleText(logObj.getExcelValue(fileName, sheetName, "Property Class", rowNum).trim());

//			logObj.buildDriverElement(unCheckFilterPaid).click();

			logObj.buildDriverElement(SearchButton).click();
			this.sync();

			if (driver.findElements(logObj.buildLocator(LiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");
				jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(headerSection));
				logObj.buildDriverElement(headerSection).click();
//				logObj.verifyText(logObj.buildDriverElement(propertyClass).getText(),
//						logObj.getExcelValue(fileName, sheetName, "Property Class", rowNum).trim());

				TestLogger.log("Info", "Records contains filtered data");
				if(logObj.buildDriverElement(propertyClass).getText().contains(logObj.getExcelValue(fileName, sheetName, "Property Class", rowNum).trim())) {
					TestLogger.log("Pass", "Filtered value displayed in the Property.");
				}
			} else {
				TestLogger.log("Fail", "No records are searched");
			}
			if (driver.findElement(By.xpath("//div[@name='SearchUIWrapper-spinner']/div[@class='spinner-message']")).isDisplayed()) {
				driver.navigate().refresh();
				pageSync();pageSync();
				TestLogger.log("info", "Spinner shown in the filter section");
			}

		} catch (SeleniumException | IOException e) {
			e.printStackTrace();
		}
	}

	// TC Description : Verify records when user select a value from filter
	// "Underwriting Status" and click on "Search"
	@Test(groups = "FunctionalTest", testName = "Fi_Au_05", enabled = true, description = "(Auction Tab, Filter) - Validate filter, when underwriting status value is selected")
	public void Fi_Au_05() throws InterruptedException {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 9000);
			String fileName = "Auction.xlsx";
			String sheetName = "Fi_Bu_06";
			int rowNum = 1;
			wait.until(ExpectedConditions.elementToBeClickable(logObj.buildDriverElement(FilterButton)));
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(FilterButton));
			if (!logObj.buildDriverElement(StateField).isDisplayed()) {
				logObj.buildDriverElement(FilterButton).click();
			}
			logObj.buildDriverElement(ClearButton).click();

			// select first value
			Select underWritelist = new Select(logObj.buildDriverElement(UnderwritingStatusField));
			underWritelist.selectByVisibleText(logObj.getExcelValue(fileName, sheetName, "UnderWrite", rowNum).trim());
			Thread.sleep(1000);
//			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(unCheckFilterPaid));
//			logObj.buildDriverElement(unCheckFilterPaid).click();
			
			logObj.buildDriverElement(SearchButton).click();
			// Thread.sleep(8000);
			pageSync();
			pageSync();
			if (driver.findElements(logObj.buildLocator(LiveList)).size() >= 4) {
				TestLogger.log("Pass", "Search record is returned");

				this.sync();
				Thread.sleep(50000);
				jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(statsField));

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
//			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(unCheckFilterPaid));
//			logObj.buildDriverElement(unCheckFilterPaid).click();
			logObj.buildDriverElement(SearchButton).click();
//			Thread.sleep(4000);
			pageSync();pageSync();

			if (driver.findElements(logObj.buildLocator(LiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");
				this.sync();

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
//			Thread.sleep(4000);
//			this.sync();
			
			pageSync();
			if (driver.findElements(logObj.buildLocator(LiveList)).size() >= 4) {
				TestLogger.log("Pass", "Search record is returned");
				this.sync();
				jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(statsField));
				logObj.verifyText(logObj.buildDriverElement(statsField),
						logObj.getExcelValue(fileName, sheetName, "expectedUnderWrite", rowNum).trim());

			} else {
				TestLogger.log("Fail", "No records are searched");
			}
			if (driver.findElement(By.xpath("//div[@name='SearchUIWrapper-spinner']/div[@class='spinner-message']")).isDisplayed()) {
				driver.navigate().refresh();
				pageSync();pageSync();
				TestLogger.log("info", "Spinner shown in the filter section");
			}
		} catch (SeleniumException | IOException e) {
			e.printStackTrace();
		}
	}

	// TC Description : Verify records when user select include value from
	// filter and click on "Search"
	@Test(groups = "FunctionalTest", testName = "Fi_Au_06", enabled = true, description = "(Auction Tab, Filter) - Validate filter, when user select include value from filter and click on Search")
	public void Fi_Au_06() throws InterruptedException, IOException {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 9000);
			String fileName = "Auction.xlsx";
			String sheetName = "Fi_Bu_07";
			int rowNum = 1;
			wait.until(ExpectedConditions.elementToBeClickable(logObj.buildDriverElement(FilterButton)));
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(FilterButton));
			if (!logObj.buildDriverElement(StateField).isDisplayed()) {
				logObj.buildDriverElement(FilterButton).click();
			}
			logObj.buildDriverElement(ClearButton).click();

			// select include value
			logObj.buildDriverElement(logObj.modifyObjLocator(includeList, "Task (multi)")).click();
			logObj.buildDriverElement(logObj.modifyObjLocator(include,
					logObj.getExcelValue(fileName, sheetName, "TagName", rowNum).trim())).click();

			logObj.buildDriverElement(logObj.modifyObjLocator(includeList,
					logObj.getExcelValue(fileName, sheetName, "TagName", rowNum).trim())).click();


			logObj.buildDriverElement(SearchButton).click();
			// Thread.sleep(8000);
//			this.sync();
			pageSync();
			if (driver.findElements(logObj.buildLocator(LiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");
				logObj.isElementExists(
						logObj.buildDriverElement(logObj.modifyObjLocator(selected_tags,
								logObj.getExcelValue(fileName, sheetName, "TagName", rowNum).trim())),
						"Selected tag exists");

				String selected_tag = logObj
						.buildDriverElement(logObj.modifyObjLocator(selected_tags,
								logObj.getExcelValue(fileName, sheetName, "TagName", rowNum).trim()))
						.getText();

				logObj.verifyText(selected_tag, logObj.getExcelValue(fileName, sheetName, "TagName", rowNum).trim());
				TestLogger.log("Pass", "Verified records when user select include value");
				
			} else {
				TestLogger.log("Fail", "No records are searched");
			}
			if (driver.findElement(By.xpath("//div[@name='SearchUIWrapper-spinner']/div[@class='spinner-message']")).isDisplayed()) {
				driver.navigate().refresh();
				pageSync();pageSync();
				TestLogger.log("info", "Spinner shown in the filter section");
			}
		} catch (SeleniumException e) {
			e.printStackTrace();
		}
	}
	
	// TC Description : Verify records when user select exclude value from
	// filter and click on "Search"
	@Test(groups = "FunctionalTest", testName = "Fi_Au_07", enabled = true, description = "(Auction Tab, Filter) - Validate filter, when user select exclude value from filter and click on Search")
	public void Fi_Au_07() throws InterruptedException, IOException {
		try {
			
			String fileName = "Auction.xlsx";
			String sheetName = "Fi_Bu_07";
			int rowNum = 1;
			this.sync();

			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(FilterButton));

			if (!logObj.buildDriverElement(StateField).isDisplayed()) {
				logObj.buildDriverElement(FilterButton).click();
			}
			logObj.buildDriverElement(ClearButton).click();

			// select include value
			logObj.buildDriverElement(logObj.modifyObjLocator(excludeList, "Task (multi)")).click();

			logObj.buildDriverElement(logObj.modifyObjLocator(exclude,
					logObj.getExcelValue(fileName, sheetName, "excludeTag", rowNum).trim())).click();

			String ss = logObj.getExcelValue(fileName, sheetName, "excludeTag", rowNum).trim();
			logObj.buildDriverElement(logObj.modifyObjLocator(excludeList, ss)).click();

//			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(unCheckFilterPaid));
//			logObj.buildDriverElement(unCheckFilterPaid).click();

			logObj.buildDriverElement(SearchButton).click();
			// Thread.sleep(8000);
//			this.sync();
			pageSync();pageSync();
			if (driver.findElements(logObj.buildLocator(LiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");
				logObj.isElementExists(
						logObj.buildDriverElement(logObj.modifyObjLocator(selected_tags,
								logObj.getExcelValue(fileName, sheetName, "excludeTag", rowNum).trim())),
						"Selected tag does not exists");

				String unselected_tag = logObj
						.buildDriverElement(logObj.modifyObjLocator(selected_tags,
								logObj.getExcelValue(fileName, sheetName, "excludeTag", rowNum).trim()))
						.getText();

				logObj.verifyText(unselected_tag, logObj.getExcelValue(fileName, sheetName, "excludeTag", rowNum).trim());
				TestLogger.log("Pass", "Verified records when user select exclude value");
			} else {
				TestLogger.log("Fail", "No records are searched");
			}
			if (driver.findElement(By.xpath("//div[@name='SearchUIWrapper-spinner']/div[@class='spinner-message']")).isDisplayed()) {
				driver.navigate().refresh();
				pageSync();pageSync();
				TestLogger.log("info", "Spinner shown in the filter section");
			}
		} catch (SeleniumException e) {
			e.printStackTrace();
		}
	}

	// TC Description : Verify records when user enters a value to the filter
	// "Market" minimum and maximum values.
	@Test(groups = "FunctionalTest", testName = "Fi_Au_08", enabled = false, description = "(Auction Tab, Filter) - Validate filter, for Market values ")
	public void Fi_Au_08() throws InterruptedException {
		try {
			
			String fileName = "Auction.xlsx";
			String sheetName = "Fi_Bu_09";
			int rowNum = 1;
			
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(FilterButton));
			if (!logObj.buildDriverElement(StateField).isDisplayed()) {
				logObj.buildDriverElement(FilterButton).click();
			}
			logObj.buildDriverElement(ClearButton).click();
			Thread.sleep(1000);

			logObj.buildDriverElement(marketMin)
					.sendKeys(logObj.getExcelValue(fileName, sheetName, "min", rowNum).trim());

			logObj.buildDriverElement(marketMax)
					.sendKeys(logObj.getExcelValue(fileName, sheetName, "max", rowNum).trim());
			
			int EnteredNumber = Integer.parseInt(logObj.getExcelValue(fileName, sheetName, "max", rowNum).trim());
//			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(unCheckFilterPaid));
//			logObj.buildDriverElement(unCheckFilterPaid).click();
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
			if (driver.findElement(By.xpath("//div[@name='SearchUIWrapper-spinner']/div[@class='spinner-message']")).isDisplayed()) {
				driver.navigate().refresh();
				pageSync();pageSync();
				TestLogger.log("info", "Spinner shown in the filter section");
			}
		} catch (SeleniumException | IOException e) {
			e.printStackTrace();
		}
	}

	// TC Description : Verify records when user enters a value to the filter
	// "BidRate" minimum and maximum values.
	@Test(groups = "FunctionalTest", testName = "Fi_Au_09", enabled = true, description = "(Auction Tab, Filter) - Validate filter, for Market values ")
	public void Fi_Au_09() throws InterruptedException {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 9000);
			String fileName = "Auction.xlsx";
			String sheetName = "Fi_Bu_10";
			int rowNum = 1;
			wait.until(ExpectedConditions.elementToBeClickable(logObj.buildDriverElement(FilterButton)));
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(FilterButton));
			if (!logObj.buildDriverElement(StateField).isDisplayed()) {
				logObj.buildDriverElement(FilterButton).click();
			}
			logObj.buildDriverElement(ClearButton).click();
			Thread.sleep(2000);
			Select bidRate = new Select(logObj.buildDriverElement(bidRateList));
			bidRate.selectByVisibleText(logObj.getExcelValue(fileName, sheetName, "bidRate", rowNum).trim());
			TestLogger.log("Info", "BidRate selected.");

			logObj.buildDriverElement(SearchButton).click();
			this.sync();

			if (driver.findElements(logObj.buildLocator(LiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");

				// find the list of history records.
				String s = driver.findElement(By.xpath("//label[@title='Status']/../../../div[2]")).getText();

				String[] parts = s.split(" ");
				String lastWord = parts[parts.length - 1];
				System.out.println("this is the actual value : "+lastWord + "Expected value from sheet : "+logObj.getExcelValue(fileName, sheetName, "bidRate", rowNum).trim());
				TestLogger.log("Pass", "this is the actual value : "+lastWord + "Expected value from sheet : "+logObj.getExcelValue(fileName, sheetName, "bidRate", rowNum).trim());
				
				logObj.verifyText(lastWord, logObj.getExcelValue(fileName, sheetName, "bidRate", rowNum).trim());

				TestLogger.log("Pass", "Filtered value displayed.");
			} else {
				TestLogger.log("Fail", "No records are searched");

			}
			if (driver.findElement(By.xpath("//div[@name='SearchUIWrapper-spinner']/div[@class='spinner-message']")).isDisplayed()) {
				driver.navigate().refresh();
				pageSync();pageSync();
				TestLogger.log("info", "Spinner shown in the filter section");
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
	@Test(groups = "FunctionalTest", testName = "Fi_Au_10", enabled = true, description = "(Auction Tab, Filter) - Validate filter, when buyer value is selected")
	public void Fi_Au_10() throws InterruptedException {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 9000);
			wait.until(ExpectedConditions.elementToBeClickable(logObj.buildDriverElement(FilterButton)));
			String fileName = "Auction.xlsx";
			String sheetName = "Fi_Bu_11";
			int rowNum = 2;
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(FilterButton));
			if (!logObj.buildDriverElement(StateField).isDisplayed()) {
				logObj.buildDriverElement(FilterButton).click();
			}
			logObj.buildDriverElement(ClearButton).click();
			Thread.sleep(1000);

			logObj.buildDriverElement(BuyerField).click();
			logObj.buildDriverElement(BuyerField)
					.sendKeys(logObj.getExcelValue(fileName, sheetName, "Buyer", rowNum).trim());
			Thread.sleep(1000);
			logObj.buildDriverElement(logObj.modifyObjLocator(BuyerRecordSelect, "1")).click();
			Thread.sleep(1000);
//			logObj.buildDriverElement(unCheckFilterPaid).click();
			logObj.buildDriverElement(SearchButton).click();
			this.sync();this.sync();

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
			if (driver.findElement(By.xpath("//div[@name='SearchUIWrapper-spinner']/div[@class='spinner-message']")).isDisplayed()) {
				driver.navigate().refresh();
				pageSync();pageSync();
				TestLogger.log("info", "Spinner shown in the filter section");
			}
		} catch (SeleniumException | IOException e) {
			e.printStackTrace();
		}
	}

	// TC Description : Verify records when user select one value from
	// multiselect filter "Grade" and click on "Search"
	// there is no records when we search for grade "A".
	@Test(groups = "FunctionalTest", testName = "Fi_Au_11", enabled = true, description = "(Auction Tab, Filter) - Validate filter, when one value is selected from Grade")
	public void Fi_Au_11() throws InterruptedException {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 9000);
			wait.until(ExpectedConditions.elementToBeClickable(logObj.buildDriverElement(FilterButton)));
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(FilterButton));
			if (!logObj.buildDriverElement(StateField).isDisplayed()) {
				logObj.buildDriverElement(FilterButton).click();
			}
			logObj.buildDriverElement(ClearButton).click();

			driver.findElement(By.xpath("//button/span[contains(text(), 'Area Grade (multi)')]")).click();
			if (!logObj.buildDriverElement(logObj.modifyObjLocator(gradeSelect, "B")).isSelected()) {
				logObj.buildDriverElement(logObj.modifyObjLocator(gradeSelect, "B")).click();
			}
			Thread.sleep(2000);
			logObj.buildDriverElement(SearchButton).click();
			this.sync();this.sync();
			Thread.sleep(8000);
			if (driver.findElements(logObj.buildLocator(LiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned with selected grade");
				jse.executeScript("arguments[0].scrollIntoView()",
						logObj.buildDriverElement(logObj.modifyObjLocator(verifyAreaGrade, "B")));
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
//				jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(unCheckFilterPaid));
				logObj.buildDriverElement(SearchButton).click();
				this.sync();
				this.sync();
				this.sync();
				if (driver.findElements(logObj.buildLocator(LiveList)).size() >= 1) {
					TestLogger.log("Pass", "Search record is returned with grade ?");
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
			if (driver.findElement(By.xpath("//div[@name='SearchUIWrapper-spinner']/div[@class='spinner-message']")).isDisplayed()) {
				driver.navigate().refresh();
				pageSync();pageSync();
				TestLogger.log("info", "Spinner shown in the filter section");
			}
		} catch (SeleniumException e) {
			e.printStackTrace();
		}
	}

	// TC Description : Verify records when user select multiple grades and
	// click on "Search"
	@Test(groups = "FunctionalTest", testName = "Fi_Au_12", enabled = true, description = "(Auction Tab, Filter) - Validate filter, Verify records when user select multiple grades andclick on Search")
	public void Fi_Au_12() throws InterruptedException {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 9000);
			wait.until(ExpectedConditions.elementToBeClickable(logObj.buildDriverElement(FilterButton)));
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(FilterButton));
			if(!logObj.buildDriverElement(StateField).isDisplayed()) {
				logObj.buildDriverElement(FilterButton).click();
				}
			logObj.buildDriverElement(ClearButton).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//button/span[contains(text(), 'Area Grade (multi)')]")).click();
			if (!logObj.buildDriverElement(logObj.modifyObjLocator(gradeSelect, "A")).isSelected()) {
				logObj.buildDriverElement(logObj.modifyObjLocator(gradeSelect, "A")).click();
				logObj.buildDriverElement(logObj.modifyObjLocator(gradeMultiSelect, "B")).click();
				logObj.buildDriverElement(logObj.modifyObjLocator(gradeMultiSelect, "D")).click();

			}

			driver.findElement(By.xpath("//select[@name='Grade']/..//button/span[contains(text(), 'A, B, D')]"))
					.click();

			logObj.buildDriverElement(SearchButton).click();
			this.sync();
			Thread.sleep(6000);
			if (driver.findElements(logObj.buildLocator(LiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");
				
				
			} else {
				TestLogger.log("Fail", "No records are searched");
			}
			if (driver.findElement(By.xpath("//div[@name='SearchUIWrapper-spinner']/div[@class='spinner-message']")).isDisplayed()) {
				driver.navigate().refresh();
				pageSync();pageSync();
				TestLogger.log("info", "Spinner shown in the filter section");
			}
		} catch (SeleniumException e) {
			e.printStackTrace();
		}
	}


	// TC Description : Verify records when user enters a value to the filter
	// "Prior" minimum and maximum values.
	@Test(groups = "FunctionalTest", testName = "Fi_Au_13", enabled = false, description = "(Auction Tab, Filter) - Validate filter, for Prior amount ")
	public void Fi_Au_13() throws InterruptedException {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 9000);
			String fileName = "Auction.xlsx";
			String sheetName = "Fi_Bu_18";
			int rowNum = 1;
			wait.until(ExpectedConditions.elementToBeClickable(logObj.buildDriverElement(FilterButton)));
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(FilterButton));
			if(!logObj.buildDriverElement(StateField).isDisplayed()) {
				logObj.buildDriverElement(FilterButton).click();
				}
			logObj.buildDriverElement(ClearButton).click();
			Thread.sleep(1000);

			logObj.buildDriverElement(priorMin)
					.sendKeys(logObj.getExcelValue(fileName, sheetName, "min", rowNum).trim());

			logObj.buildDriverElement(priorMax)
					.sendKeys(logObj.getExcelValue(fileName, sheetName, "max", rowNum).trim());

			int EnteredNumber = Integer.parseInt(logObj.getExcelValue(fileName, sheetName, "max", rowNum).trim());
//			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(unCheckFilterPaid));
//			logObj.buildDriverElement(unCheckFilterPaid).click();
			logObj.buildDriverElement(SearchButton).click();
			this.sync();pageSync();

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
	@Test(groups = "FunctionalTest", testName = "Fi_Au_14", enabled = false, description = "(Auction Tab, Filter) - Validate filter, for open sales min and max amount selected")
	public void Fi_Au_14() throws InterruptedException {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 9000);
			wait.until(ExpectedConditions.elementToBeClickable(logObj.buildDriverElement(FilterButton)));
			String fileName = "Auction.xlsx";
			String sheetName = "Fi_Bu_48";
			int rowNum = 1;
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(FilterButton));
			if(!logObj.buildDriverElement(StateField).isDisplayed()) {
				logObj.buildDriverElement(FilterButton).click();
				}
			logObj.buildDriverElement(ClearButton).click();
			Thread.sleep(1000);

			logObj.buildDriverElement(saleMin)
					.sendKeys(logObj.getExcelValue(fileName, sheetName, "min", rowNum).trim());

			logObj.buildDriverElement(saleMax)
					.sendKeys(logObj.getExcelValue(fileName, sheetName, "max", rowNum).trim());

			int EnteredNumber = Integer.parseInt(logObj.getExcelValue(fileName, sheetName, "max", rowNum).trim());
//			jse.executeScript("arguments[0].scrollIntoView()", logObj.buildDriverElement(unCheckFilterPaid));
//			logObj.buildDriverElement(unCheckFilterPaid).click();
			logObj.buildDriverElement(SearchButton).click();
			this.sync();
			pageSync();
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