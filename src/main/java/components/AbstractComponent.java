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

    public ArrayList<String> getOptionsOfSelectbox(By optionsOfSelectbox) {
        List<WebElement> optionsAll = driver.findElements(optionsOfSelectbox);
        ArrayList<String> options = new ArrayList<>();
        optionsAll.forEach(option -> {
            if (!option.getAttribute("value").isEmpty())
                options.add(option.getAttribute("value"));
        });
        return options;
    }
    public String generateRandomOptionFromList(ArrayList<String> options) {
        int levelIndex = faker.number().numberBetween(1, options.size()) - 1;
        return options.get(levelIndex);
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
