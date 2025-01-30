package webelements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DivElement extends AbstractElement{
    public DivElement(WebDriver driver) {
        super(driver);
    }

    public String getTextOfDiv(By locator) {
        return driver.findElement(locator).getText();
    }
    public boolean ifDivContainsThisText(By locator, String searchPattern) {
        return getTextOfDiv(locator).contains(searchPattern);
    }

}
