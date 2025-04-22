package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class InventoryPage {
	WebDriver driver;
	
	public InventoryPage(WebDriver driver) {
		this.driver = driver;
	}
	
	private By inventoryContainer = By.id("inventory_container");
	
	public boolean isInventoryPageDisplayed() {
		return driver.findElement(inventoryContainer).isDisplayed();
	}
}