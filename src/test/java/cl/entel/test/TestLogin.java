package cl.entel.test;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.net.*;
import java.io.File;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import com.saucelabs.saucerest.*;
import cl.entel.test.util.*;

public class TestLogin {

	private static final String USER = "saucelabs.user";
	private static final String KEY = "saucelabs.key";
	private static final String HOST = "selenium.host";
	private static final String PORT = "selenium.port";

	private static final int TIMEOUT_SECONDS = 30;

	private String url;
	private WebDriver driver;
	private SauceREST notifier;
	private String baseUrl = "http://entel.cl";
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	private Logger log;
	private MyProperties my;

	public TestLogin() {
		this.log = LogManager.getLogger(TestLogin.class);
		this.my = new MyProperties(log);
	}

	@Before
	public void setUp() throws Exception {
		url = "http://" + my.property(USER) + ":" + my.property(KEY);
		url += "@" + my.property(HOST) + ":" + my.property(PORT) + "/wd/hub";
		notifier = new SauceREST(my.property(USER), my.property(KEY));
	}

	@Test
	public void testLogin() throws Exception {
		log.info("Conectando con servidor Selenium: " + url);

		DesiredCapabilities ie = DesiredCapabilities.internetExplorer();
		DesiredCapabilities chrome = DesiredCapabilities.chrome();
		DesiredCapabilities firefox = DesiredCapabilities.firefox();
		DesiredCapabilities safari = DesiredCapabilities.safari();
		
		testH004For(browser("Windows 7", ie, "9.0", "H004"));
		testH004For(browser("Windows 7", chrome, "47.0", "H004"));
		testH004For(browser("Windows 7", firefox, "39.0", "H004"));
		testH004For(browser("Linux", chrome, "47.0", "H004"));
		testH004For(browser("OS X 10.8", safari, "6.0", "H004"));
	}

	@After
	public void tearDown() throws Exception {
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	public void testH004For(WebDriver driver) throws Exception {
		String job = (((RemoteWebDriver) driver).getSessionId()).toString();
		this.driver = driver;
		driver.manage().timeouts().implicitlyWait(TIMEOUT_SECONDS, TimeUnit.SECONDS);

		// // Login
		// open | / | 
		driver.get(baseUrl + "/");
		// click | //a[contains(text(),'Ingresar a Mi entel')] | 
		driver.findElement(By.xpath("(//a[contains(text(),'Ingresar a Mi entel')])[2]")).click();
		// type | id=MovilVisible | 965827579
		driver.findElement(By.id("MovilVisible")).sendKeys("965827579");
		// type | id=Rut | 5002451
		driver.findElement(By.id("Rut")).clear();
		driver.findElement(By.id("Rut")).sendKeys("5002451");
		// type | id=PIN | 4297
		driver.findElement(By.id("PIN")).clear();
		driver.findElement(By.id("PIN")).sendKeys("4297");
		// click | id=LB_btnLoginEntel | 
		driver.findElement(By.id("LB_btnLoginEntel")).click();
		// // Verificar que estamos en página principal
		// verifyText | //div[@id='cabecera_mientel']/table/tbody/tr/td[2] | Mi Entel
		try {
			assertEquals(
 				"Mi Entel",
				driver.findElement(
 					By.xpath("//div[@id='cabecera_mientel']/table/tbody/tr/td[2]")).getText());
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}

		notifier.jobPassed(job);
		driver.quit();
	}

	/**
	 * Construye instancia de browser remoto.
	 */
	private WebDriver browser(String os, DesiredCapabilities dc, String version, String name)
			throws MalformedURLException { 
		dc.setCapability("platform", os);
		dc.setCapability("version", version);
		dc.setCapability("name", name + " [" + os + " " + dc.getBrowserName() + " " + version + "]");
		return new RemoteWebDriver(new URL(url), dc);
	}
	
	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch(NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch(NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if(acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}

}
