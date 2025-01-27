package components;

import com.github.javafaker.Faker;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
    private final By inputSubmitBtnSelector = By.cssSelector("input[type='submit']");
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
    //можно вынести в абстрактный класс?
    public ArrayList<String> getOptionsOfLanguageLevel() {
        List<WebElement> optionsWE = driver.findElements(optionsOfSelectLanguageLevelSelector);
        ArrayList<String> options = new ArrayList<>();
        optionsWE.forEach(option -> {
            if (!option.getAttribute("value").isEmpty())
                options.add(option.getAttribute("value"));
        });
        return options;
    }
    //можно вынести в абстрактный класс?
    public String generateRandomLanguageLevel() {
        ArrayList<String> languageLevels = getOptionsOfLanguageLevel();
        int levelIndex = faker.number().numberBetween(1, languageLevels.size()) - 1;
        return languageLevels.get(levelIndex);
    }
    public void selectLanguageLevel(String value) {
        Select select = new Select(driver.findElement(selectLanguageLevelId));
        select.selectByValue(value);
    }
    public boolean checkIfPasswordsInputsAreEqual() {
        return getValueOfInputConfirmPassword().equals(getValueOfInputPassword());
    }
    public String clickForSubmitForm() {
        // подтверждаем форму
        driver.findElement(inputSubmitBtnSelector).click();
        return checkPasswordAlert();
    }
    public String checkPasswordAlert(){
        // если вываливается алерт - возвращаем ошибку пароля,
        // в противном случае возвращаем нулл (который далее игнорится просто)
        try {
            Alert alert = driver.switchTo().alert();
            String msg = alert.getText();
            alert.accept();
            return msg;
        } catch (Exception e) {
            return null;
        }
    }
    public String getTextFromOutputDiv() {
        return driver.findElement(divOutputId).getText();
    }
    public String ifValuesMatchesInDivOutput(String name, String email, String birthday, String languageLevel) {
        String mismatches = "";
        if (!getTextFromOutputDiv().contains("Имя пользователя: " + name)) mismatches += "name ";
        if (!getTextFromOutputDiv().contains("Электронная почта: " + email)) mismatches += "email ";
        if (!getTextFromOutputDiv().contains("Дата рождения: " + birthday)) mismatches += "birthday ";
        if (!getTextFromOutputDiv().contains("Уровень языка: " + languageLevel)) mismatches += "languageLevel ";
        return mismatches;
    }



}
