package components;

import org.openqa.selenium.WebDriver;
import waiters.StandartWaiter;

public abstract class AbstractComponent {
    protected WebDriver driver = null;
    protected StandartWaiter standartWaiter = null;

    public AbstractComponent(WebDriver driver){
        this.driver = driver;
        standartWaiter = new StandartWaiter(driver);
    }
}
