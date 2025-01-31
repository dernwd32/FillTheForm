package components;

import org.openqa.selenium.*;
import pages.IPage;
import webelements.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RegFormComponent extends AbstractComponent implements IPage {

    public RegFormComponent(WebDriver driver){
          super(driver);
    }

    @Override
    public void openPage(String pageUrl) {
        driver.get(BASE_URL + pageUrl);
    }

    private final SelectboxElement selectboxElement = new SelectboxElement(driver);
    private final TextInputElement textInputElement = new TextInputElement(driver);
    private final DivElement divElement = new DivElement(driver);
    private final SpecialTextInputElement specialTextInputElement = new SpecialTextInputElement(driver);
    private final ButtonElement buttonElement = new ButtonElement(driver);

    private final By textinputUsernameId = By.id("username");
    private final By textinputEmailId = By.id("email");
    private final By textinputPasswordId = By.id("password");
    private final By textinputConfirmPasswordId = By.id("confirm_password");
    private final By textinputBirthdateId = By.id("birthdate");
    private final By languageSelectboxId = By.id("language_level");
    private final By languageDropdownOptionsSelector = By.cssSelector("#language_level option");
    private final By inputSubmitBtnSelector = By.cssSelector("input[type='submit']");
    private final By divOutputId = By.id("output");

    public By getTextinputUsernameId() {
        return textinputUsernameId;
    }
    public By getTextinputEmailId() {return textinputEmailId;}
    public By getTextinputPasswordId() {
        return textinputPasswordId;
    }
    public By getTextinputConfirmPasswordId() {
        return textinputConfirmPasswordId;
    }


    public void writeIntoThisTextInput(By locator, String value) {
        textInputElement.writeIntoTextInput(locator, value);
    }
    public void writeIntoInputBirthday(Date birthday) {
        specialTextInputElement.writeIntoSpecialTextInput(textinputBirthdateId, convertDateToString(birthday, "ddMMyyyy"));
    }
    public ArrayList<String> getOptionsOfLanguageLevel() {
        return selectboxElement.getOptionsOfSelectbox(languageDropdownOptionsSelector);
    }
    public String generateRandomLanguageLevel() {
        return selectboxElement.getRandomOptionFromList(getOptionsOfLanguageLevel());
    }
    public void selectLanguageLevel(String value) {
        selectboxElement.setValueOfSelectbox(languageSelectboxId, value);
    }
    public boolean checkIfPasswordIsEqualToConfirmation() {
        return textInputElement.getValueOfTextInput(textinputPasswordId)
                .equals(textInputElement.getValueOfTextInput(textinputConfirmPasswordId));
    }
    public void submitForm(){
        buttonElement.clickButton(inputSubmitBtnSelector);
    }
    public String convertDateToString (Date date, String pattern) {
       return new SimpleDateFormat(pattern).format(date);
    }
    public boolean ifDivOutputContainsThisText(String searchPattern) {
        return divElement.ifDivContainsThisText(divOutputId, searchPattern);
    }



}
