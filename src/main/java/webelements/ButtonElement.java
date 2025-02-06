package webelements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ButtonElement extends AbstractElement {

    WebElement element;

    public ButtonElement(WebDriver driver, By locator) {
        super(driver, locator);
        element = driver.findElement(locator);
    }

    public void click() {
        //driver.findElement(locator).click();
        element.click();
    }
}
