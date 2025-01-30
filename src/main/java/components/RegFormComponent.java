package components;

import org.apache.logging.log4j.core.util.NameUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import pages.IPage;
import webelements.DivElement;
import webelements.SelectboxElement;
import webelements.TextInputElement;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RegFormComponent extends AbstractComponent implements IPage {

    public RegFormComponent(WebDriver driver){
          super(driver);
    }

    SelectboxElement selectboxElement = new SelectboxElement(driver);
    TextInputElement textInputElement = new TextInputElement(driver);
    DivElement divElement = new DivElement(driver);

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

public void setUserData() {

}
    public void writeIntoThisTextInput(String locatorNameID, String value) {
        textInputElement.writeIntoTextInput(By.id(locatorNameID), value);
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
        return selectboxElement.getOptionsOfSelectbox(optionsOfSelectLanguageLevelSelector);
    }
    public String generateRandomLanguageLevel() {
        return selectboxElement.getRandomOptionFromList(getOptionsOfLanguageLevel());
    }
    public void selectLanguageLevel(String value) {
        Select select = new Select(driver.findElement(selectLanguageLevelId));
        select.selectByValue(value);
    }
    public boolean checkIfPasswordFromEnvIsCorrect(String passwordFromEnv) {
        String passwordMD5 = "76419c58730d9f35de7ac538c2fd6737"; //md5 правильного пароля
        return passwordMD5.equals(NameUtil.md5(passwordFromEnv));
    }
    public boolean checkIfPasswordIsEqualToConfirmation() {
        return getValueOfInputPassword().equals(getValueOfInputConfirmPassword());
    }
    public boolean clickForSubmitFormAndAnswerIfThereWasNotAlert() {
        driver.findElement(inputSubmitBtnSelector).click();
        return !ifThereWasAlertCloseAndAnswer();
    }

    public boolean ifDivOutputContainsThisText(String searchPattern) {
        return divElement.ifDivContainsThisText(divOutputId, searchPattern);

    }



}
