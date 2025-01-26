package components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import pages.IPage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RegFormComponent extends AbstractComponent implements IPage {

    public RegFormComponent(WebDriver driver){
          super(driver);
    }

    @Override
    public void openPage(String pageUrl) {
        driver.get(BASE_URL + pageUrl);
    }

    private final By textinputUsernameId = By.id("username");
    private final By textinputEmailId = By.id("email");
    private final By textinputPasswordId = By.id("password");
    private final By textinputConfirmPasswordId = By.id("confirm_password");
    private final By textinputBirthdateId = By.id("birthdate");
    private final By selectLanguageLevelId = By.id("language_level");
    private final By optionsOfSelectLanguageLevelSelector = By.cssSelector("#language_level option");
    private final By inputSubmitBtnSelector = By.cssSelector("form input[type='submit']");
    private final By divOutputId = By.id("output");

    public void writeIntoInputUsername(String someText) {
        driver.findElement(textinputUsernameId).sendKeys(someText);
    }
    public void writeIntoInputEmail(String someText) {
        driver.findElement(textinputEmailId).sendKeys(someText);
    }
    public void writeIntoInputPassword(String someText) {
        driver.findElement(textinputPasswordId).sendKeys(someText);
    }
    public void writeIntoInputConfirmPassword(String someText) {
        driver.findElement(textinputConfirmPasswordId).sendKeys(someText);
    }
    public String getValueOfInputPassword(){
        return driver.findElement(textinputPasswordId).getAttribute("value");
    }
    public String getValueOfInputConfirmPassword(){
        return driver.findElement(textinputPasswordId).getAttribute("value");
    }
    public void writeIntoInputBirthday(String someText) {
        driver.findElement(textinputBirthdateId).click();
        new Actions(driver)
                .sendKeys(someText)
                .perform();
    }
    public void selectLanguageLevel(String selectedValue) {
        Select select = new Select(driver.findElement(selectLanguageLevelId));
        select.selectByValue(selectedValue);
    }
    public void selectLanguageLevel(int selectedId) {
        Select select = new Select(driver.findElement(selectLanguageLevelId));
        select.selectByIndex(selectedId);
    }
    public ArrayList<String> getOptionsOfLanguageLevel() {
        List<WebElement> optionsWE = driver.findElements(optionsOfSelectLanguageLevelSelector);
        ArrayList<String> options = new ArrayList<>();
        optionsWE.forEach(option -> {
            if (!option.getAttribute("value").isEmpty())
                options.add(option.getAttribute("value"));
        });
        return options;
    }

    public boolean checkIfPasswordsInputsAreEqual() {
        return getValueOfInputConfirmPassword().equals(getValueOfInputPassword());
    }
    public void clickForSubmitForm() {
        driver.findElement(inputSubmitBtnSelector).click();
    }
    public String getTextFromOutputDiv() {
        return driver.findElement(divOutputId).getText();
    }
    public String ifValuesMatchesInDivOutput(String name, String email, String birthday, String languageLevel) {
//        if (getTextFromOutputDiv().matches("(.*)" + "Имя пользователя: " + name + "(.*)")
//                        && getTextFromOutputDiv().matches("(.*)" + "Электронная почта: " + email + "(.*)")
//                        && getTextFromOutputDiv().matches("(.*)" + "Дата рождения: " + birthday + "(.*)")
//                        && getTextFromOutputDiv().matches("(.*)" + "Уровень языка: " + languageLevel + "(.*)"
//        )
//        return getTextFromOutputDiv().matches(
//                "Имя пользователя: " + name + "\n" +
//                        "Электронная почта: " + email + "\n" +
//                        "Дата рождения: " + birthday + "\n" +
//                        "Уровень языка: " + languageLevel);
        String mismatches = "";

        if (!getTextFromOutputDiv().contains("Имя пользователя: " + name)) mismatches += "name ";
        if (!getTextFromOutputDiv().contains("Электронная почта: " + email)) mismatches += "email ";
        if (!getTextFromOutputDiv().contains("Дата рождения: " + birthday)) mismatches += "birthday ";
        if (!getTextFromOutputDiv().contains("Уровень языка: " + languageLevel)) mismatches += "languageLevel ";

        return mismatches;

    }


}
