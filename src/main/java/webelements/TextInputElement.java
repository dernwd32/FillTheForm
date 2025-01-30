package webelements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

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
}
