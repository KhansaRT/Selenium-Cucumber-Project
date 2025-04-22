package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
	WebDriver driver;
	
	//locators 
	private By usernameField = By.id("user-name");
	private By passwordField = By.id("password");
	private By loginButton = By.id("login-button");
	private By errorMessage = By.xpath("//h3[@data-test='error']"); 
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void enterUsername (String username) {
		//waitForElementVisible(usernameField, 5);
		driver.findElement(usernameField).sendKeys(username);
	}
	
	public void enterPassword (String password) {
		//waitForElementVisible(passwordField, 5);
		driver.findElement(passwordField).sendKeys(password);
	}
	
	public void clickLogin() {
		//waitForElementVisible(loginButton, 5);
		driver.findElement(loginButton).click();
	}
	
	public String getErrorMessage() {
		return driver.findElement(errorMessage).getText();
	}
}