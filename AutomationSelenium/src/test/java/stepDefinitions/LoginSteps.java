package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
//import java.time.Duration;
import org.junit.Assert;
//import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
import pages.InventoryPage;
import pages.LoginPage;
import utils.Hooks;

public class LoginSteps {
    private WebDriver driver;
    LoginPage loginPage;
    InventoryPage inventoryPage;

    public LoginSteps() {
    	this.driver = Hooks.driver; 
    } 

    @Given("user is on login page")
    public void user_is_on_login_page() {
        driver.get("https://www.saucedemo.com/");
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
    }

    @When("user enters {string} and {string}")
    public void user_enters_username_and_password(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }

    @And("user clicks on login button")
    public void user_clicks_on_login_button() {
    	loginPage.clickLogin();
    }

    @Then("user is navigated to the inventory page")
    public void user_is_navigated_to_the_inventory_page() {
        Assert.assertTrue(inventoryPage.isInventoryPageDisplayed());
    } 

    @Then("user got error message {string}")
    public void user_got_error_message(String errorMessage) {
        Assert.assertEquals(errorMessage, loginPage.getErrorMessage());
    }
}