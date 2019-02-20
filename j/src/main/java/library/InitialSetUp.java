package library;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.*;

import reporting.ExtentManager;
import reporting.ExtentTestManager;
import reporting.TestLogger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class InitialSetUp {

	public static WebDriver driver;
	public static String dataFilePath;
	public static String documentPath;
	public static int explicitTimeout;
	public static int implicitTimeout;
	public ApacheRestart Restart;

	public String browser;
	public String url;
	public String cmd;
	public String chromeDriver;
	public String safariDriverpath;

	public static String brwsStackUsername;
	public static String brwsStackKey;
	public static String brwsStackURL;

	public WebElement webElement;
	public Properties config;
	public static ExtentReports extent;
	public static ExtentTest test;
	public static int executionTimes = 0;
	public static int testCaseCount = 0;

	public InitialSetUp() throws Exception {
		config = new Properties();
		Restart = new ApacheRestart();
		String propFilename = "config.properties";
		InputStream inputstream = getClass().getClassLoader().getResourceAsStream(propFilename);
		config.load(inputstream);

		browser = config.getProperty("BROWSER");
		url = config.getProperty("URL");
		chromeDriver = config.getProperty("CHROME_DRIVER_LOC");
		safariDriverpath = config.getProperty("SAFARI_DRIVER_LOC");

//		brwsStackUsername = config.getProperty("USERNAME");
//		brwsStackKey = config.getProperty("AUTOMATE_KEY");
//		System.out.println("brwsStackUsername : " +brwsStackUsername);
//		System.out.println("brwsStackKey :" +brwsStackKey);
//		brwsStackURL = "https://" + brwsStackUsername + ":" + brwsStackKey + "@hub-cloud.browserstack.com/wd/hub";
//		System.out.println("brwsStackURL ::: " +brwsStackURL);
		dataFilePath = config.getProperty("DATA_LOCATION");
		documentPath = config.getProperty("DOC_LOCATION");
		explicitTimeout = Integer.parseInt(config.getProperty("EXPLICIT_TIMEOUT"));
		implicitTimeout = Integer.parseInt(config.getProperty("IMPLICIT_TIMEOUT"));

		/*
		 * if (executionTimes == 0) { System.out.println("Waiting for build to deploy");
		 * TimeUnit.MINUTES.sleep(5); executionTimes = executionTimes + 1;
		 * Restart.serverRestart(); //This is used to restart apache server }
		 */
	}

	// Method to initiate browser and set browser capabilities
	@BeforeSuite(alwaysRun = true)
	public void instantiateWebDriver() throws Exception {
		try {
			if (browser.equalsIgnoreCase("CHROME")) {

				ChromeOptions options = new ChromeOptions();
				options.addArguments("--start-maximized");
				HashMap<String, Object> prefs = new HashMap<String, Object>();
				prefs.put("credentials_enable_service", false);
				prefs.put("profile.password_manager_enabled", false);
				options.setExperimentalOption("prefs", prefs);
				/*
				 * DesiredCapabilities caps = new DesiredCapabilities();
				 * caps.setCapability(ChromeOptions.CAPABILITY, options);
				 * caps.setCapability("browser", "Chrome"); caps.setCapability("os", "WINDOWS");
				 * caps.setCapability("os_version", "10"); caps.setCapability("resolution",
				 * "1366x768"); caps.setCapability("browserstack.debug", "true"); java.net.URL
				 * myURL = new java.net.URL(brwsStackURL); driver = new RemoteWebDriver(myURL,
				 * caps);
				 */
				System.setProperty("webdriver.chrome.driver", chromeDriver);
				driver = new ChromeDriver(options);
				driver.manage().window().maximize();
			} else if (browser.equalsIgnoreCase("SAFARI")) {

				System.setProperty("webdriver.safari.driver", safariDriverpath);
				driver = new SafariDriver();
				driver.manage().window().maximize();
				
			}else if (browser.equalsIgnoreCase("macSafariSauceLabs")) {
				String USERNAME = "sasidharreddy";
				String ACCESS_KEY = "fcc5e25a-2cf8-4ba3-a3a1-d1aa2cf7fa99";
				String URL = "https://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:443/wd/hub";

				DesiredCapabilities caps = DesiredCapabilities.safari();
				caps.setCapability("platform", "macOS 10.12");
				caps.setCapability("version", "11.0");
				driver = new RemoteWebDriver(new URL(URL), caps);

			}else if (browser.equalsIgnoreCase("WindowsChromeSauceLabs")) {
				String USERNAME = "sasidharreddy";
				String ACCESS_KEY = "fcc5e25a-2cf8-4ba3-a3a1-d1aa2cf7fa99";
				String URL = "https://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:443/wd/hub";

				DesiredCapabilities caps = DesiredCapabilities.chrome();
			    caps.setCapability("platform", "Windows 10");
			    caps.setCapability("version", "71.0");
				driver = new RemoteWebDriver(new URL(URL), caps);
			}else if (browser.equalsIgnoreCase("macChromeSauceLabs")) {
				String USERNAME = "sasidharreddy";
				String ACCESS_KEY = "fcc5e25a-2cf8-4ba3-a3a1-d1aa2cf7fa99";
				String URL = "https://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:443/wd/hub";

				DesiredCapabilities caps = DesiredCapabilities.chrome();
				caps.setCapability("platform", "macOS 10.13");
				caps.setCapability("version", "71.0");
				driver = new RemoteWebDriver(new URL(URL), caps);

			} else if (browser.equalsIgnoreCase("saucelabsSimulator")) {
				// it will run in simulator in saucelabs
				String USERNAME = "sasidharreddy";
				String ACCESS_KEY = "fcc5e25a-2cf8-4ba3-a3a1-d1aa2cf7fa99";
				String URL = "https://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:443/wd/hub";
				DesiredCapabilities caps = DesiredCapabilities.iphone();
				caps.setCapability("appiumVersion", "1.9.1");
				caps.setCapability("deviceName", "iPad (6th generation) Simulator");
				caps.setCapability("deviceOrientation", "portrait");
				caps.setCapability("platformVersion", "12.0");
				caps.setCapability("platformName", "iOS");
				caps.setCapability("browserName", "Safari");
				driver = new RemoteWebDriver(new URL(URL), caps);

			}
			driver.manage().timeouts().implicitlyWait(implicitTimeout, TimeUnit.SECONDS);
			driver.get(url);
			Thread.sleep(5000);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Method to refresh browser after every class
//	@AfterClass(alwaysRun = true)
//	public void refreshBrowser() {
//		try {
//			GeneralFunc generalFun = new GeneralFunc();
//
//			if (!driver.toString().contains("null")) {
//				driver.navigate().to(url);
//				Thread.sleep(1000);
//				driver.navigate().refresh();
//				Thread.sleep(1000);
////				generalFun.pageSync();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/*
	 * Below methods are used for reporting purpose and supporting methods are
	 * present in reporting package
	 */

	@BeforeSuite(alwaysRun = true)
	public void extentSetup(ITestContext context) {
		ExtentManager.setOutputDirectory(context);
		extent = ExtentManager.getInstance();
	}

	@BeforeMethod(alwaysRun = true)
	public void startExtent(Method method) {
		Test test = method.getAnnotation(Test.class);
		if (test == null) {
			return;
		}
		if (test.description() == null) {
			ExtentTestManager.startTest(method.getName());
		} else {
			ExtentTestManager.startTest(method.getName(), test.description());
		}
	}

	protected String getStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		return sw.toString();
	}

	@AfterMethod(alwaysRun = true)
	public void afterEachTestMethod(ITestResult result) {
		ExtentTestManager.getTest().getTest().setStartedTime(getTime(result.getStartMillis()));
		ExtentTestManager.getTest().getTest().setEndedTime(getTime(result.getEndMillis()));
		testCaseCount = testCaseCount + 1;

		for (String group : result.getMethod().getGroups()) {
			ExtentTestManager.getTest().assignCategory(group);
		}

		if (result.getStatus() == 1) {
			ExtentTestManager.getTest().log(LogStatus.PASS, "Test Passed");
			System.out.println(Integer.toString(testCaseCount) + " - (Passed) - " + result.getName() + " : "
					+ result.getMethod().getDescription());

		} else if (result.getStatus() == 2) {
			ExtentTestManager.getTest().log(LogStatus.FAIL, getStackTrace(result.getThrowable()));
			System.out.println(Integer.toString(testCaseCount) + " - (Failed) - " + result.getName() + " : "
					+ result.getMethod().getDescription());

		} else if (result.getStatus() == 3) {
			ExtentTestManager.getTest().log(LogStatus.SKIP, "Test Skipped");
			System.out.println(Integer.toString(testCaseCount) + " - (Skipped) - " + result.getName() + " : "
					+ result.getMethod().getDescription());
		}

		ExtentTestManager.endTest();
		extent.flush();
	}

	@AfterSuite(alwaysRun = true)
	public void generateReport() throws InterruptedException, MessagingException {
		extent.close();
		driver.manage().deleteAllCookies();
		driver.quit();
		this.sendEmail();
	}

	private Date getTime(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}

	public void sendEmail() throws MessagingException {

		String username = config.getProperty("E_USERNAME");
		String password = config.getProperty("E_PASSWORD");
		String to = config.getProperty("E_TO");
		String cc = config.getProperty("E_CC");
		String from = username;
		String eSubject = config.getProperty("E_SUBJECT");
		String ebody = config.getProperty("E_BODY");

		Properties props = System.getProperties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.required", "true");

		// Get the Session object.
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			// Create a default MimeMessage object.
			Message message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));

			// Set Subject: header field
			message.setSubject(eSubject);

			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();
			Multipart messageMultiPart = new MimeMultipart();
			// Now set the actual message
			message.setContent(ebody, "text/html; charset=utf-8");

			// Set the email attachment file
			System.out.println("Attachment path : " + ExtentManager.reportFilename);
			FileDataSource fileDataSource = new FileDataSource(ExtentManager.reportFilename) {
				@Override
				public String getContentType() {
					return "application/octet-stream";
				}
			};

			messageBodyPart.setDataHandler(new DataHandler(fileDataSource));
			messageBodyPart.setFileName(ExtentManager.reportFilename);
			messageMultiPart.addBodyPart(messageBodyPart);
			message.setContent(messageMultiPart);

			// Send message
			Transport.send(message);

		} catch (MessagingException e) {
			throw e;
		} catch (Exception ex) {
			throw ex;
		}
	}
}