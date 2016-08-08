package ${packageName};

import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import cl.entel.test.util.*;

public class TestWeb${className} {

	private static final String HOST = "selenium.host";
	private static final String PORT = "selenium.port";

	private static final int TIMEOUT_SECONDS = 10;

	private WebDriver driver;
	private String url;
	private String baseUrl = "${baseURL}";
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	private org.apache.logging.log4j.Logger log;
	private MyProperties my;

	public TestWeb${className}() {
		this.log = LogManager.getLogger(TestWeb${className}.class);
		this.my = new MyProperties(log);
	}

	@Before
	public void setUp() throws Exception {
		url = "http://" + my.property(HOST, "localhost") + ":" + my.property(PORT, "4444") + "/wd/hub";
	}

	@Test
	public void ${methodName}() throws Exception {
		log.info("Conectando con servidor Selenium: " + url);
		testH004For(new RemoteWebDriver(new URL(url), DesiredCapabilities.internetExplorer()));
		testH004For(new RemoteWebDriver(new URL(url), DesiredCapabilities.chrome()));
		testH004For(new RemoteWebDriver(new URL(url), DesiredCapabilities.firefox()));
	}

	public void testH004For(WebDriver driver) throws Exception {
		this.driver = driver;
		driver.manage().timeouts().implicitlyWait(TIMEOUT_SECONDS, TimeUnit.SECONDS);



