package utils;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {
	public static WebDriver driver;
	@Before
	public void setUp() {
		if(driver == null) { // pastikan driver hanya dilihat sekali
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
			//String driverPath = System.getProperty("user.dir") + "D:\\BOOTCAMP ARUTALA\\Selenium\\AutomationSelenium\\src\\test\\resources\\drivers\\chromedriver.exe";
			System.setProperty("webdriver.chrome.driver", "D:\\BOOTCAMP ARUTALA\\Selenium\\AutomationSelenium\\src\\test\\resources\\drivers\\chromedriver.exe");
			
		}
	}
	
	@After
	public void tearDown() {
		if (driver != null) {
			driver.quit();
			driver = null; // reset driver setelah test selesai
		}
	}
}
