package webelements;

import com.github.javafaker.Faker;
import org.openqa.selenium.WebDriver;
import waiters.StandartWaiter;

public abstract class AbstractElement {

    protected WebDriver driver = null;
    protected StandartWaiter standartWaiter = null;
    protected Faker faker = new Faker();

    protected AbstractElement(WebDriver driver){
        this.driver = driver;
        standartWaiter = new StandartWaiter(driver);
    }
}
