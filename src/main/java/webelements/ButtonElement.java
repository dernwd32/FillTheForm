package webelements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ButtonElement extends AbstractElement {

    public ButtonElement(WebDriver driver, By locator) {
        super(driver, locator);
    }

    public void click() {
        if (standartWaiter.waitToBeClickable(locator))
            driver.findElement(locator).click();
    }
}
