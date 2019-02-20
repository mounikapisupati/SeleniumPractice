package com.test.sampleJava;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.SeleniumException;

import library.LogInFunc;
import objectRepo.BasePageOR;
import objectRepo.CommonOR;
import reporting.TestLogger;

public class BasePage extends LogInFunc implements BasePageOR, CommonOR {

	// Variable or Object declaration

	LogInFunc logObj = new LogInFunc();
	WebDriverWait driverWait;

	// Methods declaration
	public BasePage() throws Exception {
		super();
	}

	

	// TC Description : Verify "Filter" option in "Cert" tab
	@Test(groups = "FunctionalTest", testName = "BasePage01", enabled = true, description = "(Cert Tab, Filter) - Validate has filter option")
	public void BasePage01() throws InterruptedException {
		try {
			driverWait = new WebDriverWait(driver, explicitTimeout);
			Thread.sleep(30000);
			if (!driver.getCurrentUrl().contains("Master?type=cert")) {
				logObj.buildDriverElement(certTab).click();
				driverWait.until(ExpectedConditions.elementToBeClickable(logObj.buildLocator(FilterButton)));
				TestLogger.log("Info", "User landed on the Cert page.");
			}
			pageSync();
			logObj.isElementExists(logObj.buildDriverElement(FilterButton), "Filter button exists");

			if (!logObj.buildDriverElement(StateField).isDisplayed()) {
				logObj.buildDriverElement(FilterButton).click();
			}
			Thread.sleep(1000);
			logObj.isElementExists(logObj.buildDriverElement(ClearButton1), "top Clear button exists");
			logObj.isElementExists(logObj.buildDriverElement(SearchButton1), "top Search button exists");
			logObj.isElementExists(logObj.buildDriverElement(StateField), "State filter exists");
			logObj.isElementExists(logObj.buildDriverElement(CountyField), "County filter exists");
			logObj.isElementExists(logObj.buildDriverElement(TaxYearField), "Tax year filter exists");
			logObj.isElementExists(logObj.buildDriverElement(TownshipField), "TownshipField filter exists");
			logObj.isElementExists(logObj.buildDriverElement(CertStatus), "StatusMulti filter exists");
			logObj.isElementExists(logObj.buildDriverElement(PropertyClass), "PropertyClass filter exists");
			logObj.isElementExists(logObj.buildDriverElement(MaturityDate), "MaturityDate filter exists");
			logObj.isElementExists(logObj.buildDriverElement(IncludeTask), "IncludeTask filter exists");
			logObj.isElementExists(logObj.buildDriverElement(ExcludeTask), "ExcludeTask filter exists");
			logObj.isElementExists(logObj.buildDriverElement(SearchButton2), "bottom Search button exists");
			logObj.isElementExists(logObj.buildDriverElement(ClearButton2), "bottom Clear button exists");

		} catch (SeleniumException e) {
			e.printStackTrace();
		}
	}

	// TC Description : Verify all the records are displayed, when user doesn't
	// set
	// any filter value and click on "Search"
	@Test(groups = "FunctionalTest", testName = "Fi_Cert_02", enabled = true, description = "(Cert Tab, Filter) - Validate search when there is filter value selected")
	public void Fi_Cert_02() throws InterruptedException {
		try {

			driverWait = new WebDriverWait(driver, explicitTimeout);
			Thread.sleep(30000);
			if (!driver.getCurrentUrl().contains("Master?type=cert")) {
				logObj.buildDriverElement(certTab).click();
				driverWait.until(ExpectedConditions.elementToBeClickable(logObj.buildLocator(FilterButton)));
				TestLogger.log("Info", "User landed on the Cert page.");
			}

			logObj.isElementExists(logObj.buildDriverElement(FilterButton), "Filter button exists");
			driverWait.until(ExpectedConditions.elementToBeClickable(logObj.buildLocator(ClearButton1)));
			logObj.buildDriverElement(ClearButton1).click();
			Thread.sleep(1000);

			logObj.isElementExists(logObj.buildDriverElement(SearchButton1), "Search button exists");
			logObj.buildDriverElement(SearchButton1).click();

			this.pageSync();

			if (driver.findElements(logObj.buildLocator(LiveList)).size() == 20) {
				TestLogger.log("Pass", "20 records are loaded in listing page");
			} else {
				TestLogger.log("Fail", "20 records should be loaded in listing page");
			}

			String recordedIdPin = logObj.buildDriverElement(PropertyInfoBlock1).getText().trim();

			if (recordedIdPin.contains(
					logObj.buildDriverElement(logObj.modifyObjLocator(srvIdPinInList, "1")).getText().trim())) {
				TestLogger.log("Pass", "First record is selected in the list");
			} else {
				TestLogger.log("Fail", "First record is not selected in the list");
			}

		} catch (SeleniumException e) {
			e.printStackTrace();
		}
	}

	// TC Description : Verify records when user select a value from filter
	// "State,
	// County, Township"
	// and click on "Search"
	@Test(groups = "FunctionalTest", testName = "Fi_Cert_03", enabled = true, description = "(Cert Tab, Filter) - Validate filter, when county value is selected")
	public void Fi_Cert_03() throws InterruptedException {
		try {

			String fileName = "Service.xlsx";
			String sheetName = "Fi_Se_03";
			int rowNum = 1;
			driverWait = new WebDriverWait(driver, explicitTimeout);
			Thread.sleep(30000);
			if (!driver.getCurrentUrl().contains("Master?type=cert")) {
				logObj.buildDriverElement(certTab).click();
				driverWait.until(ExpectedConditions.elementToBeClickable(logObj.buildLocator(FilterButton)));
				TestLogger.log("Info", "User landed on the Cert page.");
			}
			logObj.isElementExists(logObj.buildDriverElement(FilterButton), "Filter button exists");

			if (!logObj.buildDriverElement(StateField).isDisplayed()) {
				logObj.buildDriverElement(FilterButton).click();
			}

			logObj.buildDriverElement(ClearButton1).click();
			Thread.sleep(1000);

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
			logObj.buildDriverElement(SearchButton1).click();
			this.pageSync();
			Thread.sleep(6000);
			if (driver.findElements(logObj.buildLocator(LiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");
				this.pageSync();
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

	// TC Description : Verify records when user select a value from filter
	// "TaxYear" and click on "Search"
	@Test(groups = "FunctionalTest", testName = "Fi_Cert_04", enabled = true, description = "(Cert Tab, Filter) - Validate filter, when tax year value is selected")
	public void Fi_Cert_04() throws InterruptedException {
		try {

			String fileName = "Service.xlsx";
			String sheetName = "Fi_Se_04";
			int rowNum = 1;

			// added navigation link code
			driverWait = new WebDriverWait(driver, explicitTimeout);
			Thread.sleep(30000);
			if (!driver.getCurrentUrl().contains("Master?type=cert")) {
				logObj.buildDriverElement(certTab).click();
				TestLogger.log("Info", "User landed on the Cert page.");
			}

			logObj.isElementExists(logObj.buildDriverElement(FilterButton), "Filter button exists");

			if (!logObj.buildDriverElement(StateField).isDisplayed()) {
				logObj.buildDriverElement(FilterButton).click();
			}
			logObj.buildDriverElement(ClearButton1).click();
			Thread.sleep(1000);

			Select taxYearlist = new Select(logObj.buildDriverElement(TaxYearField));
			taxYearlist.selectByVisibleText(logObj.getExcelValue(fileName, sheetName, "TaxYear", rowNum).trim());
			TestLogger.log("Info",
					"Tax Year selected." + logObj.getExcelValue(fileName, sheetName, "TaxYear", rowNum).trim());

			logObj.buildDriverElement(SearchButton1).click();

			this.pageSync();

			if (driver.findElements(logObj.buildLocator(LiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");
			} else {
				TestLogger.log("Fail", "No records are searched");
			}
			logObj.verifyText(logObj.buildDriverElement(YEARINST).getText(),
					logObj.getExcelValue(fileName, sheetName, "TaxYear", rowNum).trim());

			TestLogger.log("Info", "Records contains filtered data");

		} catch (SeleniumException | IOException e) {
			e.printStackTrace();
		}
	}

	// TC Description : Verify records when user select one value from
	// multiselect
	// filter "Status" and click on "Search"
	@Test(groups = "FunctionalTest", testName = "Fi_Cert_05", enabled = true, description = "(Cert Tab, Filter) - Validate filter, when one value is selected from status filter")
	public void Fi_Cert_05() throws InterruptedException, IOException {
		try {
			String fileName = "Service.xlsx";
			String sheetName = "Fi_Se_03";
			int rowNum1 = 1;
			int rowNum2 = 2;
			String status1 = logObj.getExcelValue(fileName, sheetName, "Status", rowNum1).trim();
			String status2 = logObj.getExcelValue(fileName, sheetName, "Status", rowNum2).trim();

			System.out
					.println("status selected." + logObj.getExcelValue(fileName, sheetName, "Status", rowNum1).trim());
			System.out
					.println("status selected." + logObj.getExcelValue(fileName, sheetName, "Status", rowNum2).trim());
			// added navigation link code
			driverWait = new WebDriverWait(driver, explicitTimeout);
			Thread.sleep(30000);
			if (!driver.getCurrentUrl().contains("Master?type=cert")) {
				logObj.buildDriverElement(certTab).click();
				TestLogger.log("Info", "User landed on the Cert page.");
			}
			Thread.sleep(30000);
			logObj.isElementExists(logObj.buildDriverElement(FilterButton), "Filter button exists");

			if (!logObj.buildDriverElement(StateField).isDisplayed()) {
				logObj.buildDriverElement(FilterButton).click();
			}
			logObj.buildDriverElement(ClearButton1).click();
			Thread.sleep(5000);

			logObj.buildDriverElement(CertStatus).click();

			// Select redeem
			if (!logObj.buildDriverElement(logObj.modifyObjLocator(IsSelectStatus, status1)).isSelected()) {
				logObj.buildDriverElement(logObj.modifyObjLocator(SelectStatus, status1)).click();
			}
			logObj.buildDriverElement(statusBtn).click();
			TestLogger.log("Info", "status selected." + status1);

			logObj.buildDriverElement(SearchButton1).click();

			this.pageSync();

			if (driver.findElements(logObj.buildLocator(LiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");
			} else {
				TestLogger.log("Fail", "No records are searched");
			}

			logObj.verifyText(logObj.buildDriverElement(logObj.modifyObjLocator(StatusValue, status1)), status1);

			// Select second value
			TestLogger.log("Info", "Second iteration with different value");
			logObj.buildDriverElement(ClearButton1).click();
			Thread.sleep(1000);
			logObj.buildDriverElement(FilterButton).click();
			Thread.sleep(1000);
			logObj.buildDriverElement(CertStatus).click();
			// Select redeem-pending
			if (!logObj.buildDriverElement(logObj.modifyObjLocator(IsSelectStatus, status2)).isSelected()) {
				logObj.buildDriverElement(logObj.modifyObjLocator(SelectStatus, status2)).click();
			}
			logObj.buildDriverElement(statusBtn).click();
			logObj.buildDriverElement(SearchButton1).click();

			this.pageSync();

			if (driver.findElements(logObj.buildLocator(LiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");
			} else {
				TestLogger.log("Fail", "No records are searched");
			}

			logObj.verifyText(logObj.buildDriverElement(logObj.modifyObjLocator(StatusValue, status2)), status2);

		} catch (SeleniumException e) {
			e.printStackTrace();
		}
	}

	// TC Description : Verify records when user select multiple value from
	// multiselect filter "Status" and click on "Search"
	@Test(groups = "FunctionalTest", testName = "Fi_Cert_06", enabled = true, description = "(Cert Tab, Filter) - Validate filter, when multiple value is selected from status filter")
	public void Fi_Cert_06() throws InterruptedException, IOException {
		try {

			String fileName = "Service.xlsx";
			String sheetName = "Fi_Se_03";
			int rowNum1 = 1;
			int rowNum2 = 2;
			String status1 = logObj.getExcelValue(fileName, sheetName, "Status", rowNum1).trim();
			String status2 = logObj.getExcelValue(fileName, sheetName, "Status", rowNum2).trim();
			// added navigation link code
			driverWait = new WebDriverWait(driver, explicitTimeout);

			if (!driver.getCurrentUrl().contains("Master?type=cert")) {
				logObj.buildDriverElement(certTab).click();
				TestLogger.log("Info", "User landed on the Cert page.");
			}
			Thread.sleep(30000);
			logObj.isElementExists(logObj.buildDriverElement(FilterButton), "Filter button exists");

			if (!logObj.buildDriverElement(StateField).isDisplayed()) {
				logObj.buildDriverElement(FilterButton).click();
			}
			logObj.buildDriverElement(ClearButton1).click();
			Thread.sleep(1000);

			logObj.buildDriverElement(CertStatus).click();
			// Select redeem
			if (!logObj.buildDriverElement(logObj.modifyObjLocator(IsSelectStatus, status1)).isSelected()) {
				logObj.buildDriverElement(logObj.modifyObjLocator(SelectStatus, status1)).click();
			}

			TestLogger.log("Info", "status selected." + status1);

			// Select redeem-pending
			if (!logObj.buildDriverElement(logObj.modifyObjLocator(IsSelectStatus, status2)).isSelected()) {
				logObj.buildDriverElement(logObj.modifyObjLocator(SelectStatus, status2)).click();
			}
			TestLogger.log("Info", "status selected." + status2);
			logObj.buildDriverElement(statusBtn).click();
			logObj.buildDriverElement(SearchButton1).click();

			this.pageSync();

			if (driver.findElements(logObj.buildLocator(LiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");
			} else {
				TestLogger.log("Fail", "No records are searched");
			}
			logObj.verifyText(logObj.buildDriverElement(logObj.modifyObjLocator(StatusValue, status1)), status1);

		} catch (SeleniumException e) {
			e.printStackTrace();
		}
	}

	// TC Description : Verify records when user selects all the value in
	// multiselect filter "Status" and click on "Search"
	@Test(groups = "FunctionalTest", testName = "Fi_Cert_07", enabled = true, description = "(Cert Tab, Filter) - Validate filter, when all value is selected from status filter")
	public void Fi_Cert_07() throws InterruptedException, IOException {
		try {

			// added navigation link code

			String fileName = "Service.xlsx";
			String sheetName = "Fi_Se_03";
			int rowNum = 3; // xlsheet status is "Select all"
			// String selectAll = logObj.getExcelValue(fileName, sheetName,
			// "Status", rowNum).trim();
			String selectAll = "Select all";
			// added navigation link code
			driverWait = new WebDriverWait(driver, explicitTimeout);
			Thread.sleep(30000);
			if (!driver.getCurrentUrl().contains("Master?type=cert")) {
				logObj.buildDriverElement(certTab).click();
				TestLogger.log("Info", "User landed on the Cert page.");
			}

			logObj.isElementExists(logObj.buildDriverElement(FilterButton), "Filter button exists");

			if (!logObj.buildDriverElement(StateField).isDisplayed()) {
				logObj.buildDriverElement(FilterButton).click();
			}
			logObj.buildDriverElement(ClearButton1).click();
			Thread.sleep(1000);

			logObj.buildDriverElement(CertStatus).click();
			Thread.sleep(1000);
			logObj.buildDriverElement(logObj.modifyObjLocator(SelectallStatus, selectAll)).click();

			logObj.buildDriverElement(statusBtn).click();

			TestLogger.log("Info", "status selected." + selectAll);

			logObj.buildDriverElement(SearchButton1).click();

			this.sync();
			this.sync();
			this.sync();

			if (driver.findElements(logObj.buildLocator(LiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");
			} else {
				TestLogger.log("Fail", "No records are searched");
			}

		} catch (SeleniumException e) {
			e.printStackTrace();
		}
	}

	// TC Description : Verify records when user select a value from filter
	// "Expiration Date" , also selects "<=" and click on "Search"
	@Test(groups = "FunctionalTest", testName = "Fi_Cert_08", enabled = true, description = "(Cert Tab, Filter) - Validate filter, when expiration date and <= is selected")
	public void Fi_Cert_08() throws InterruptedException, ParseException {
		try {
			String fileName = "Service.xlsx";
			String sheetName = "Fi_Se_08";
			int rowNum = 1;
			// added navigation link code
			driverWait = new WebDriverWait(driver, explicitTimeout);
			Thread.sleep(30000);
			if (!driver.getCurrentUrl().contains("Master?type=cert")) {
				logObj.buildDriverElement(certTab).click();
				TestLogger.log("Info", "User landed on the Cert page.");
			}
			Thread.sleep(30000);
			logObj.isElementExists(logObj.buildDriverElement(FilterButton), "Filter button exists");
			if (!logObj.buildDriverElement(StateField).isDisplayed()) {
				logObj.buildDriverElement(FilterButton).click();
			}
			logObj.buildDriverElement(ClearButton1).click();
			// we have to write code from here.....
			// select the equation list
			Select operator = new Select(logObj.buildDriverElement(ExpirationDateCondtionalField));
			operator.selectByVisibleText("<=");
			logObj.buildDriverElement(ExpirationDateField).sendKeys(logObj.getExcelValue(fileName, sheetName, "FilterText", rowNum).trim());
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");
			Date FilterDate = formatter.parse(logObj.getExcelValue(fileName, sheetName, "FilterText", rowNum).trim());
			logObj.buildDriverElement(SearchButton1).click();
			this.sync();
			if (driver.findElements(logObj.buildLocator(LiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");
			} else {
				TestLogger.log("Fail", "No records are searched");
			}
			String actualMaturityate = logObj.buildDriverElement(ExpiresDateValue).getText();
			Date FilterDate2 = formatter.parse(actualMaturityate);
			if (FilterDate.compareTo(FilterDate2) >= 0) {
				TestLogger.log("Pass", FilterDate.compareTo(FilterDate2) + " : Record contain filtered value");
			} else {
				TestLogger.log("Fail", FilterDate.compareTo(FilterDate2) + " : Record doesn't contain filtered value");
			}
		} catch (SeleniumException | IOException e) {

			e.printStackTrace();

		}

	}

	// TC Description : Verify records when user select a value from filter

	// "Expiration Date" , also selects ">=" and click on "Search"

	@Test(groups = "FunctionalTest", testName = "Fi_Cert_09", enabled = true, description = "(Cert Tab, Filter) - Validate filter, when expiration date and >= is selected")
	public void Fi_Cert_09() throws InterruptedException {
		try {
			String fileName = "Service.xlsx";
			String sheetName = "Fi_Se_09";
			int rowNum = 1;
			// added navigation link code
			driverWait = new WebDriverWait(driver, explicitTimeout);
			Thread.sleep(30000);
			if (!driver.getCurrentUrl().contains("Master?type=cert")) {
				logObj.buildDriverElement(certTab).click();
				TestLogger.log("Info", "User landed on the Cert page.");
			}
			logObj.isElementExists(logObj.buildDriverElement(FilterButton), "Filter button exists");
			if (!logObj.buildDriverElement(StateField).isDisplayed()) {
				logObj.buildDriverElement(FilterButton).click();
			}
			logObj.buildDriverElement(ClearButton1).click();
			Select dropdown = new Select(logObj.buildDriverElement(ExpirationDateCondtionalField));
			dropdown.selectByVisibleText(">=");
			logObj.buildDriverElement(ExpirationDateField).sendKeys(logObj.getExcelValue(fileName, sheetName, "FilterText", rowNum).trim());
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");
			Date FilterDate = formatter.parse(logObj.getExcelValue(fileName, sheetName, "FilterText", rowNum).trim());
			logObj.buildDriverElement(SearchButton1).click();
			this.sync();
			if (driver.findElements(logObj.buildLocator(LiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");
			} else {
				TestLogger.log("Fail", "No records are searched");
			}

			String ActualText = logObj.buildDriverElement(ExpiresDateValue).getText();
			Date RecordDate = formatter.parse(ActualText);
			if (FilterDate.compareTo(RecordDate) <= 0) {
				TestLogger.log("Pass", "Record contain filtered value");
			} else {
				TestLogger.log("Fail", "Record doesn't contain filtered value");
			}
		} catch (SeleniumException | IOException | ParseException e) {

			e.printStackTrace();

		}

	}

	// TC Description : Verify records when user select a value from filter

	// "Expiration Date" , also selects "=" and click on "Search"

	@Test(groups = "FunctionalTest", testName = "Fi_Cert_10", enabled = true, description = "( Cert Tab, Filter) - Validate filter, when expiration date and = is selected")

	public void Fi_Cert_10() throws InterruptedException {

		try {
			String fileName = "Service.xlsx";
			String sheetName = "Fi_Se_10";
			int rowNum = 1;
			// added navigation link code
			driverWait = new WebDriverWait(driver, explicitTimeout);
			Thread.sleep(30000);
			if (!driver.getCurrentUrl().contains("Master?type=cert")) {
				logObj.buildDriverElement(certTab).click();
				TestLogger.log("Info", "User landed on the Cert page.");
			}
			logObj.isElementExists(logObj.buildDriverElement(FilterButton), "Filter button exists");
			if (!logObj.buildDriverElement(StateField).isDisplayed()) {
				logObj.buildDriverElement(FilterButton).click();
			}
			logObj.buildDriverElement(ClearButton1).click();
			Select dropdown = new Select(logObj.buildDriverElement(ExpirationDateCondtionalField));
			dropdown.selectByVisibleText("=");
			logObj.buildDriverElement(ExpirationDateField).sendKeys(logObj.getExcelValue(fileName, sheetName, "FilterText", rowNum).trim());
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");
			Date FilterDate = formatter.parse(logObj.getExcelValue(fileName, sheetName, "FilterText", rowNum).trim());
			logObj.buildDriverElement(SearchButton1).click();

			this.sync();
			if (driver.findElements(logObj.buildLocator(LiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");
			} else {
				TestLogger.log("Fail", "No records are searched");
			}

			String ActualText = logObj.buildDriverElement(ExpiresDateValue).getText();
			Date RecordDate = formatter.parse(ActualText);
			if (FilterDate.compareTo(RecordDate) == 0) {
				TestLogger.log("Pass", "Record contain filtered value");
			} else {
				TestLogger.log("Fail", "Record doesn't contain filtered value");
			}
		} catch (SeleniumException | IOException | ParseException e) {
			e.printStackTrace();
		}

	}

	// TC Description : Verify records when user select Include Task and
	// "Search"
	@Test(groups = "FunctionalTest", testName = "Fi_Cert_11", enabled = true, description = "(Cert Tab, Filter) - Verify records when user select Include Task and \"Search\"")
	public void Fi_Cert_11() throws InterruptedException {
		try {

			String fileName = "Service.xlsx";
			String sheetName = "Fi_Se_11";
			int rowNum = 1;
			String includeTag = logObj.getExcelValue(fileName, sheetName, "TagName", rowNum).trim();

			// added navigation link code
			driverWait = new WebDriverWait(driver, explicitTimeout);
			Thread.sleep(30000);
			if (!driver.getCurrentUrl().contains("Master?type=cert")) {
				logObj.buildDriverElement(certTab).click();
				TestLogger.log("Info", "User landed on the Cert page.");
			}

			logObj.isElementExists(logObj.buildDriverElement(FilterButton), "Filter button exists");

			if (!logObj.buildDriverElement(StateField).isDisplayed()) {
				logObj.buildDriverElement(FilterButton).click();
			}
			logObj.buildDriverElement(ClearButton1).click();

			logObj.buildDriverElement(IncludeTask).click();

			// Select include task/tag
			if (!logObj.buildDriverElement(logObj.modifyObjLocator(IsSelectInclude, includeTag)).isSelected()) {
				logObj.buildDriverElement(logObj.modifyObjLocator(SelectInclude, includeTag)).click();
			}
			logObj.buildDriverElement(IncludeBtn).click();
			TestLogger.log("Info", "status selected." + includeTag);

			logObj.buildDriverElement(SearchButton1).click();

			this.sync();

			if (driver.findElements(logObj.buildLocator(LiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");
				// write code to find the selected tag in the Tasks table.
				// While writing tasks test cases we have to complete this.
				Thread.sleep(1000);
			} else {
				TestLogger.log("Fail", "No records are searched");
			}

		} catch (SeleniumException | IOException e) {
			e.printStackTrace();
		}
	}

	// TC Description : Verify records when user select Exclude Task and
	// "Search"
	@Test(groups = "FunctionalTest", testName = "Fi_Cert_12", enabled = true, description = "(Cert Tab, Filter) - Verify records when user select Exclude Task and \"Search\"")
	public void Fi_Cert_12() throws InterruptedException {
		try {

			String fileName = "Service.xlsx";
			String sheetName = "Fi_Se_12";
			int rowNum = 1;
			String excludeTag = logObj.getExcelValue(fileName, sheetName, "TagName", rowNum).trim();
			// added navigation link code
			driverWait = new WebDriverWait(driver, explicitTimeout);
			Thread.sleep(30000);
			if (!driver.getCurrentUrl().contains("Master?type=cert")) {
				logObj.buildDriverElement(certTab).click();
				TestLogger.log("Info", "User landed on the Cert page.");
			}

			logObj.isElementExists(logObj.buildDriverElement(FilterButton), "Filter button exists");

			if (!logObj.buildDriverElement(StateField).isDisplayed()) {
				logObj.buildDriverElement(FilterButton).click();
			}
			logObj.buildDriverElement(ClearButton1).click();
			logObj.buildDriverElement(ExcludeTask).click();
			// select exclude task/tag
			if (!logObj.buildDriverElement(logObj.modifyObjLocator(IsSelectExclude, excludeTag)).isSelected()) {
				logObj.buildDriverElement(logObj.modifyObjLocator(SelectExclude, excludeTag)).click();
			}
			logObj.buildDriverElement(ExcludeBtn).click();
			TestLogger.log("Info", "status selected." + excludeTag);

			logObj.buildDriverElement(SearchButton1).click();
			this.sync();
			this.sync();
			this.sync();
			if (driver.findElements(logObj.buildLocator(LiveList)).size() >= 1) {
				TestLogger.log("Pass", "Search record is returned");
				// write code to find the selected tag in the Tasks table.
				// While writing tasks test cases we have to complete this.
				Thread.sleep(1000);
			} else {
				TestLogger.log("Fail", "No records are searched");
			}

		} catch (SeleniumException | IOException e) {
			e.printStackTrace();
		}
	}

}