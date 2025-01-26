package components;

import com.github.javafaker.Faker;
import org.openqa.selenium.WebDriver;
import waiters.StandartWaiter;

public abstract class AbstractComponent {
    protected WebDriver driver = null;
    protected StandartWaiter standartWaiter = null;
    protected Faker faker = new Faker();


    public AbstractComponent(WebDriver driver){
        this.driver = driver;
        standartWaiter = new StandartWaiter(driver);
    }
}
