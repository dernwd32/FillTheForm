package webelements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ButtonElement extends AbstractElement {
    public ButtonElement(WebDriver driver) {
        super(driver);
    }

    public void clickButton(By locator) {
        driver.findElement(locator).click();
    }
}
