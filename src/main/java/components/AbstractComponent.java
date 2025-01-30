package components;

import com.github.javafaker.Faker;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import waiters.StandartWaiter;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractComponent {
    protected WebDriver driver = null;
    protected StandartWaiter standartWaiter = null;
    protected Faker faker = new Faker();


    public AbstractComponent(WebDriver driver){
        this.driver = driver;
        standartWaiter = new StandartWaiter(driver);
    }

    public boolean ifThereWasAlertCloseAndAnswer() {
        boolean thereWasAlert = false;
        if (standartWaiter.waitForAlertToBePresent()) {
            Alert alert = driver.switchTo().alert();
            alert.accept();
            thereWasAlert = true;
        }
        return thereWasAlert;
    }
}
