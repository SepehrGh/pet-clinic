import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.apache.commons.io.FileUtils;
import java.io.File;

public class UIFirstScenarioTest {
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	JavascriptExecutor js;
	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "/home/sepehr/IdeaProjects/pet-clinic-ca7/src/test/resources/webDriver/chromedriver");
		driver = new ChromeDriver();
		baseUrl = "https://www.google.com/";
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		js = (JavascriptExecutor) driver;
	}

	@Test
	public void testUntitledTestCase() throws Exception {
		driver.get("http://localhost:8080/");
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[2]/a/span[2]")).click();
		driver.findElement(By.linkText("Add Owner")).click();
		driver.findElement(By.id("firstName")).click();
		driver.findElement(By.id("firstName")).clear();
		driver.findElement(By.id("firstName")).sendKeys("Masoud");
		driver.findElement(By.id("lastName")).click();
		driver.findElement(By.id("lastName")).clear();
		driver.findElement(By.id("lastName")).sendKeys("Sadeghloo");
		driver.findElement(By.id("address")).click();
		driver.findElement(By.id("address")).clear();
		driver.findElement(By.id("address")).sendKeys("Arghavan 13.");
		driver.findElement(By.id("city")).click();
		driver.findElement(By.id("city")).clear();
		driver.findElement(By.id("city")).sendKeys("Torghuzabad");
		driver.findElement(By.id("telephone")).click();
		driver.findElement(By.id("telephone")).clear();
		driver.findElement(By.id("telephone")).sendKeys("9092301202");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Add\n      New Pet')]")).click();
		driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Name'])[1]/following::div[3]")).click();
		driver.findElement(By.id("name")).click();
		driver.findElement(By.id("name")).clear();
		driver.findElement(By.id("name")).sendKeys("Bulldog 639");
		driver.findElement(By.id("birthDate")).click();
		driver.findElement(By.id("birthDate")).clear();
		driver.findElement(By.id("birthDate")).sendKeys("2015-12-12");
		new Select(driver.findElement(By.id("type"))).selectByVisibleText("dog");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Add\n                  Visit')]")).click();
		driver.findElement(By.id("description")).click();
		driver.findElement(By.id("description")).clear();
		driver.findElement(By.id("description")).sendKeys("Monthly Checkup");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
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
