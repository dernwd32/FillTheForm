package webelements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class SelectboxElement extends AbstractElement {

    public SelectboxElement(WebDriver driver, By locator) {
        super(driver, locator);
    }

    public List<String> getOptionsOfSelectbox() {
        List<WebElement> optionsAll = driver.findElement(locator).findElements(By.xpath(".//option"));
        ArrayList<String> options = new ArrayList<>();
        optionsAll.forEach(option -> {
            if (!option.getAttribute("value").isEmpty())
                options.add(option.getAttribute("value"));
        });
        return options;
    }
    public String getRandomOptionFromList(List<String> options) {
        int levelIndex = faker.number().numberBetween(1, options.size()) - 1;
        return options.get(levelIndex);
    }

    public void setValueOfSelectbox(String value){
        Select select = new Select(driver.findElement(locator));
        select.selectByValue(value);
    }
}
