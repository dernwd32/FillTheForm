package webelements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class SpecialTextInputElement extends AbstractElement{
    public SpecialTextInputElement(WebDriver driver) {
        super(driver);
    }

    public void writeIntoSpecialTextInput(By locator, String value) {
        driver.findElement(locator).click();
        new Actions(driver)
                .sendKeys(value)
                .perform();
    }
}
