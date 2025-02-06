package webelements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class TextInputElement extends AbstractElement{

    public TextInputElement(WebDriver driver, By locator) {
        super(driver, locator);
    }
    public void writeIntoTextInput(String value) {
        driver.findElement(locator).sendKeys(value);
    }
    public String getValueOfTextInput() {
        return driver.findElement(locator).getAttribute("value");
    }
    public void writeIntoSpecialTextInput(String value) {
        driver.findElement(locator).click();
        new Actions(driver)
                .sendKeys(value)
                .perform();
    }
}
