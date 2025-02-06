package webelements;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import waiters.StandartWaiter;

public abstract class AbstractElement {

    protected WebDriver driver = null;
    protected StandartWaiter standartWaiter = null;
    protected Faker faker = new Faker();
    protected By locator = null;
    //protected WebElement element = null;

    protected AbstractElement(WebDriver driver, By locator){
        this.driver = driver;
        this.locator = locator;
        standartWaiter = new StandartWaiter(driver);
       // element = driver.findElement(locator);
    }

}
