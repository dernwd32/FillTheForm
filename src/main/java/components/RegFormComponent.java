package components;

import org.apache.logging.log4j.core.util.NameUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import pages.IPage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    public void writeIntoInputBirthday(Date birthday) {
        driver.findElement(textinputBirthdateId).click();
        new Actions(driver)
                .sendKeys(new SimpleDateFormat("ddMMyyyy").format(birthday))
                .perform();
    }
    public ArrayList<String> getOptionsOfLanguageLevel() {
        return getOptionsOfSelectbox(optionsOfSelectLanguageLevelSelector);
    }
    public String generateRandomLanguageLevel() {
       return generateRandomOptionFromList(getOptionsOfLanguageLevel());
    }
    public void selectLanguageLevel(String value) {
        Select select = new Select(driver.findElement(selectLanguageLevelId));
        select.selectByValue(value);
    }
    public boolean checkIfPasswordFromEnvIsCorrect(String passwordFromEnv) {
        String passwordMD5 = "76419c58730d9f35de7ac538c2fd6737"; //md5 правильного пароля
        return passwordMD5.equals(NameUtil.md5(passwordFromEnv));
    }
    public boolean clickForSubmitForm() {
        // подтверждаем форму
        driver.findElement(inputSubmitBtnSelector).click();
        return checkPasswordAlert();
    }
    public boolean checkPasswordAlert(){
        boolean passOk = true;
        if (standartWaiter.waitForAlertToBePresent()) {
            Alert alert = driver.switchTo().alert();
            alert.accept();
            passOk = false;
        }
        return passOk;
    }
    public String getTextFromOutputDiv() {
        return driver.findElement(divOutputId).getText();
    }


    public boolean ifNameMatchesInDivOutput(String name){
        return getTextFromOutputDiv().contains("Имя пользователя: " + name);
    }
    public boolean ifEmailMatchesInDivOutput(String email){
        return getTextFromOutputDiv().contains("Электронная почта: " + email);
    }
    public boolean ifBirthdayMatchesInDivOutput(Date birthday){
        return getTextFromOutputDiv().contains("Дата рождения: "
                + new SimpleDateFormat("yyyy-MM-dd").format(birthday));
    }
    public boolean ifLanguageLevelMatchesInDivOutput(String languageLevel){
        return getTextFromOutputDiv().contains("Уровень языка: " + languageLevel);
    }


}
