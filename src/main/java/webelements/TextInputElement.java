package webelements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class TextInputElement extends AbstractElement{
    public TextInputElement(WebDriver driver) {
        super(driver);
    }

    public void writeIntoTextInput(By locator, String value) {
        driver.findElement(locator).sendKeys(value);
    }
    public String getValueOfTextInput(By locator) {
        return driver.findElement(locator).getAttribute("value");
    }
    public void writeIntoSpecialTextInput(By locator, String value) {
        driver.findElement(locator).click();
        new Actions(driver)
                .sendKeys(value)
                .perform();
    }
}
