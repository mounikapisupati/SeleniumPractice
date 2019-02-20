	package library;
	
	import java.io.File;
	import java.io.FileInputStream;
	import java.io.IOException;
    import java.util.Random;
    import java.util.concurrent.TimeUnit;
	
	import org.apache.poi.hssf.usermodel.HSSFWorkbook;
	import org.apache.poi.ss.usermodel.Row;
	import org.apache.poi.ss.usermodel.Sheet;
	import org.apache.poi.ss.usermodel.Workbook;
	import org.apache.poi.xssf.usermodel.XSSFWorkbook;
	import org.openqa.selenium.By;
	import org.openqa.selenium.NoSuchElementException;
	import org.openqa.selenium.StaleElementReferenceException;
	import org.openqa.selenium.TimeoutException;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.support.ui.ExpectedCondition;
	import org.openqa.selenium.support.ui.FluentWait;
	import org.testng.Reporter;

	import objectRepo.LogInObj;
	import reporting.TestLogger;
	
	public class GeneralFunc extends InitialSetUp implements LogInObj {
		
		public WebElement mDriver;
		public By mBy;
		String locator = null;
		public GeneralFunc() throws Exception {
	
		}
	  
		/* 
		 * Description : Method will read the existing excel return a string according to the parameters provided
		 * Parameters  :
		 * fileName - Name of the file to be accessed Eg : TestXl.xls or TestXl.xlsx 
		 * sheetName - Name of the sheet to be accessed in the file
		 * columnName - Name of the column for which user needs value
		 * rowNum - Row number of the data set for the test case
		 */
		
		public String getExcelValue(String fileName, String sheetName, String columnName, int rowNum) throws IOException {
	
			int colNum = 0;
			Workbook mWorkbook = null;
	
		// Create a file object and provide the file path,  dataFilePath is a static variable read from config file	
			File file = new File("src/test/resources/" + fileName);
			FileInputStream inputStream = new FileInputStream(file);
	
			String fileExtensionName = fileName.substring(fileName.indexOf("."));
	
		// Check the extension of the excel and create the workbook object accordingly	
			if (fileExtensionName.equals(".xlsx")) {
				mWorkbook = new XSSFWorkbook(inputStream);
			} else if (fileExtensionName.equals(".xls")) {
				mWorkbook = new HSSFWorkbook(inputStream);
			}
	
			Sheet mSheet = mWorkbook.getSheet(sheetName);
			Row headerRow = mSheet.getRow(0);
	
		// Getting the the column number from the column name, which is passed as a parameter	
			for (int j = 0; j < headerRow.getLastCellNum(); j++) {
				String colValue = headerRow.getCell(j).getStringCellValue();
				if (colValue.equalsIgnoreCase(columnName)) {
					colNum = j;
				}
			}
			String rowValue = String.valueOf(mSheet.getRow(rowNum).getCell(colNum));
	   // Getting and returning the value from excel form the row number and column number 
//			return mSheet.getRow(rowNum).getCell(colNum).getStringCellValue();
			return rowValue;
		}
		
		
		// This method is used for setting driver object based on locators
	
		public String selectLocator(String objlocator) {
	
			if (objlocator.substring(0,2).trim().equalsIgnoreCase("id")) {
				locator = "id";
				return locator;
			} else if (objlocator.substring(0,5).trim().equalsIgnoreCase("class")) {
				locator = "class";
				return locator;
			} else if (objlocator.substring(0,4).trim().equalsIgnoreCase("name")) {
					locator = "name";
					return locator;
			} else if (objlocator.substring(0,7).trim().equalsIgnoreCase("tagName")) {
				locator = "tagName";
				return locator;
			} else if (objlocator.substring(0,3).trim().equalsIgnoreCase("css")) {
				locator = "css";
				return locator;
			} else if (objlocator.substring(0,8).trim().equalsIgnoreCase("linkText")) {
				locator = "linkText";
				return locator;
			} else if (objlocator.substring(0,5).trim().equalsIgnoreCase("xpath")) {
				locator = "xpath";
				return locator;
			}
			return null;
		}
		
		public By buildLocator(String objlocator) {
			String loc = selectLocator(objlocator);
			switch (loc) {
			case "id": {
				String objloc = objlocator.substring(2).trim();
				mBy = By.id(objloc);
				return mBy;
			}
			case "class": {
				String objloc = objlocator.substring(5).trim();
				mBy = By.className(objloc);
				return mBy;
			}
			case "name": {
				String objloc = objlocator.substring(4).trim();
				mBy = By.name(objloc);
				return mBy;
			}
			case "tagName": {
				String objloc = objlocator.substring(7).trim();
				mBy = By.tagName(objloc);
				return mBy;
			}
			case "css": {
				String objloc = objlocator.substring(3).trim();
				mBy = By.cssSelector(objloc);
				return mBy;
			}
			case "linkText": {
				String objloc = objlocator.substring(8).trim();
				mBy = By.linkText(objloc);
				return mBy;
			}
			case "xpath": {
				String objloc = objlocator.substring(5).trim();
				mBy = By.xpath(objloc);
				return mBy;
			}
			}
			return null;
		}
	
		public WebElement buildDriverElement(String objlocator) {
			String loc = selectLocator(objlocator);
			switch (loc) {
			case "id": {
				String objloc = objlocator.substring(2).trim();
				mDriver = driver.findElement(By.id(objloc));
				return mDriver;
			}
			case "class": {
				String objloc = objlocator.substring(5).trim();
				mDriver = driver.findElement(By.className(objloc));
				return mDriver;
			}
			case "name": {
				String objloc = objlocator.substring(4).trim();
				mDriver = driver.findElement(By.name(objloc));
				return mDriver;
			}
			case "tagName": {
				String objloc = objlocator.substring(7).trim();
				mDriver = driver.findElement(By.tagName(objloc));
				return mDriver;
			}
			case "css": {
				String objloc = objlocator.substring(3).trim();
				mDriver = driver.findElement(By.cssSelector(objloc));
				return mDriver;
			}
			case "linkText": {
				String objloc = objlocator.substring(8).trim();
				mDriver = driver.findElement(By.linkText(objloc));
				return mDriver;
			}
			case "xpath": {
				String objloc = objlocator.substring(5).trim();
				mDriver = driver.findElement(By.xpath(objloc));
				return mDriver;
			}
			}
			return null;
		}
		
		// This method replaces the string in the Object locator (Eg : Object locator = xpath)
		public String modifyObjLocator(String objLocator, String replaceString) {
			if (objLocator != null && replaceString != null) {
				String Str = new String(objLocator);
				String mStr = Str.replace("ReplaceString", replaceString);
				return mStr;
			} else {
				Reporter.log("<br> Fail : objLocator/replaceString is null <br>");
				return null;
			}
		}
	
		// This method replaces the string in the Object locator (Eg : Object locator = xpath)
		public String modifyObjLocator(String objLocator, String replaceString1, String replaceString2) {
			if (objLocator != null && replaceString1 != null && replaceString2 != null) {
				String Str = new String(objLocator);
				String mStr = Str.replace("ReplaceString1", replaceString1);
				String mStr1 = mStr.replace("ReplaceString2", replaceString2);
				return mStr1;
			} else {
				Reporter.log("<br> Fail : objLocator/replaceString is null <br>");
				return null;
			}
		}
		
		// This method replaces the string in the Object locator (Eg : Object locator = xpath)
		public String modifyObjLocator(String objLocator, String replaceString1, String replaceString2, String replaceString3) {
			if (objLocator != null && replaceString1 != null && replaceString2 != null) {
				String Str = new String(objLocator);
				String mStr = Str.replace("ReplaceString1", replaceString1);
				String mStr1 = mStr.replace("ReplaceString2", replaceString2);
				String mStr2 = mStr1.replace("ReplaceString3", replaceString3);
				return mStr2;
			} else {
				Reporter.log("<br> Fail : objLocator/replaceString is null <br>");
				return null;
			}
		}
		
		public boolean isElementExists(WebElement we, String message) {
			try {
				we.isDisplayed();
				TestLogger.log("Pass","(Validation)-" + message);
				return true;
			} catch (NoSuchElementException e) {
				return false;
			}
		}	
		
		public void verifyText(WebElement we, String expectedText) {
			try {				
				String actualText = we.getText().trim().toLowerCase();				
				if (actualText.contains(expectedText.trim().toLowerCase())){		
					TestLogger.log("Pass", "(Validation)-" + "Text : " + expectedText + " exists");
				} else {
					TestLogger.log("Fail", "(Validation)-" + "Actual Text : " + actualText + " - Expected Text : " + expectedText);
				}
			} catch (NoSuchElementException e) {
				TestLogger.log("Fail", "Element doesnot exist");
			}
		}
		
		public void verifyText(String actualText, String expectedText) {
			try {				
				String actual = actualText.trim();				
				if (actual.equalsIgnoreCase(expectedText.trim())){		
					TestLogger.log("Pass", "(Validation)-" + "Text : " + expectedText + " exists");
				} else {
					TestLogger.log("Fail", "(Validation)-" + "Actual Text : " + actual + " - Expected Text : " + expectedText);
				}
			} catch (NoSuchElementException e) {
				TestLogger.log("Fail", "Element doesnot exist");
			}
		}
			
		public void verifyAttributeText(WebElement we,String attribute, String expectedText) {
			try {	
				String actualText = we.getAttribute(attribute).trim();	
				if (actualText.contains(expectedText.trim())) {
					TestLogger.log("Pass", "(Validation)-" + attribute + " : " + expectedText + " exists");
				} else {
					TestLogger.log("Fail", "(Validation)-" + attribute + " : " + expectedText + " doesn't exists");
				}
			} catch (NoSuchElementException e) {
				TestLogger.log("Fail", "Element doesnot exist");
			}
	
		}
		
	public boolean isAttribtuePresent(WebElement we, String attribute) {
		boolean result = false;
		try {
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			String value = we.getAttribute(attribute);
			if (value != null) {
				driver.manage().timeouts().implicitlyWait(implicitTimeout, TimeUnit.SECONDS);
				result = true;
			}
		} catch (Exception e) {}
		driver.manage().timeouts().implicitlyWait(implicitTimeout, TimeUnit.SECONDS);
		return result;
	}
		
	public String randomString(String reqString, int length) {
		String genString = null;

		switch (reqString.toLowerCase()) {
		case "alpha": {
			genString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
			break;
		}
		case "numeric": {
			genString = "123456789";
			break;
		}
		}

		StringBuilder randString = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int character = (int) (Math.random() * genString.length());
			randString.append(genString.charAt(character));
		}
		return randString.toString();
	}
	
	public void pageSync() throws InterruptedException {
		Thread.sleep(1000);
		if (driver.findElements(this.buildLocator(comGlobalSpinner)).size() != 0) {
		}
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		while (driver.findElements(this.buildLocator(comGlobalSpinner)).size() >= 1) {}
		driver.manage().timeouts().implicitlyWait(implicitTimeout, TimeUnit.SECONDS);
	}
		
	// ************************************** Un used functions ***************************************** //
		
		// Function is not working it is throwing exception
		public void waitUntilElementDisappear(final WebElement element){
	        try{
	              new FluentWait<WebDriver>(driver)
	              .withTimeout(explicitTimeout,TimeUnit.SECONDS)
	              .pollingEvery(2,TimeUnit.SECONDS)
	              .ignoring(NoSuchElementException.class)
	              .ignoring(StaleElementReferenceException.class)
	              .until(new ExpectedCondition<Boolean>(){
	                public Boolean apply(WebDriver driver){
	                    return (!element.isDisplayed());
	                }
	              }
	            );
	        }catch(TimeoutException | NoSuchElementException | StaleElementReferenceException e){
	        	
	        	TestLogger.log("Fail", "Element is still visible");
	        }
	        
	    }
		
		/*import java.io.BufferedReader;
		import java.io.FileOutputStream;
		import java.io.InputStreamReader;
		import java.io.PrintWriter;
		import com.itextpdf.awt.geom.Rectangle;
		import com.itextpdf.text.pdf.PdfReader;
		import com.itextpdf.text.pdf.parser.FilteredTextRenderListener;
		import com.itextpdf.text.pdf.parser.LocationTextExtractionStrategy;
		import com.itextpdf.text.pdf.parser.PdfTextExtractor;
		import com.itextpdf.text.pdf.parser.RegionTextRenderFilter;
		import com.itextpdf.text.pdf.parser.RenderFilter;
		import com.itextpdf.text.pdf.parser.TextExtractionStrategy;*/
		
	/*	public void readPDFData(String pdfName, String outputFileName) {
			try {
				PdfReader reader = new PdfReader(pdfName);
				PrintWriter out = new PrintWriter(new FileOutputStream(outputFileName));
				Rectangle rect = new Rectangle(0, 0, 1000, 1000);
				RenderFilter filter = new RegionTextRenderFilter(rect);
				TextExtractionStrategy strategy;
	
				for (int i = 1; i <= reader.getNumberOfPages(); i++) {
					strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
					out.println(PdfTextExtractor.getTextFromPage(reader, i, strategy));
				}
				out.flush();
				out.close();
	
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
		public void verifyFileData(String data[], String outputFileName) {
			try {
	
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(outputFileName)));
				StringBuilder stringData = new StringBuilder();
	
				String line = null;
	
				while ((line = br.readLine()) != null) {
					stringData.append(line);
				}
	
				for (int i = 0; i < data.length; i++) {
					String txt = data[i];
					if (stringData.indexOf(txt) > 0) {
						TestLogger.log("Pass", txt + " contains in downloaded PDF");
					} else {
						TestLogger.log("Fail", txt + " does not contains in downloaded PDF");
					}
				}
	
				br.close();
	
			} catch (Exception e) {
				e.printStackTrace();
			}
		}*/
		
	}