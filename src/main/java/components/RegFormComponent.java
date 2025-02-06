package components;

import annotations.ComponentBlueprint;
import org.openqa.selenium.*;
import pages.IPage;
import webelements.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@ComponentBlueprint(rootLocator = "form#registrationForm")
public class RegFormComponent extends AbstractComponent implements IPage {

    private final String baseUrl = System.getProperty("base.url");

    public RegFormComponent(WebDriver driver){
          super(driver);
    }

    @Override
    public void openPage(String pageUrl) {
        driver.get(baseUrl + pageUrl);
    }

   // private final TextInputElement textInputElement = new TextInputElement(driver);
    private final String rootLocator = (String) getMetaValues("rootLocator");
    private final By inputSubmitBtnSelector = By.cssSelector(rootLocator + " input[type='submit']");
    private final SelectboxElement selectboxElement = new SelectboxElement(driver, getLocatorId(LocatorsEnum.LANGUAGE_LEVEL));
    private final ButtonElement buttonElement = new ButtonElement(driver, inputSubmitBtnSelector);
    private final TextInputElement birthdayElement = new TextInputElement(driver, getLocatorId(LocatorsEnum.BIRTHDATE));

    private final TextInputElement usernameElement = new TextInputElement(driver, getLocatorId(LocatorsEnum.USERNAME.getValue()));
    private final TextInputElement emailElement = new TextInputElement(driver, getLocatorId(LocatorsEnum.EMAIL.getValue()));
    private final TextInputElement passwordElement = new TextInputElement(driver, getLocatorId(LocatorsEnum.PASSWORD.getValue()));
    private final TextInputElement confirmElement = new TextInputElement(driver, getLocatorId(LocatorsEnum.CONFIRM_PASSWORD.getValue()));
    public TextInputElement getUsernameElement() {        return usernameElement;    }
    public TextInputElement getEmailElement() {        return emailElement;    }
    public TextInputElement getPasswordElement() {        return passwordElement;    }
    public TextInputElement getConfirmElement() {        return confirmElement;    }

    public By getLocatorId(LocatorsEnum id) {
        //не будет работать, если айди в доме не полностью в нижнем регистре
        return By.id(id.name().toLowerCase());
    }
    public By getLocatorId(String id) {
        //надёжнее всегда через value enum'a передавать
        return By.id(id);
    }
    public void writeIntoInputBirthday(LocalDate birthday) {
        birthdayElement.writeIntoSpecialTextInput(convertDateToString(birthday, "ddMMyyyy"));
    }
    public List<String> getOptionsOfLanguageLevel() {
        return selectboxElement.getOptionsOfSelectbox();
    }
    public String generateRandomLanguageLevel() {
        return selectboxElement.getRandomOptionFromList(getOptionsOfLanguageLevel());
    }
    public void selectLanguageLevel(String value) {
        selectboxElement.setValueOfSelectbox(value);
    }
    public boolean checkIfPasswordIsEqualToConfirmation() {
        return Objects.equals(passwordElement.getValueOfTextInput(), confirmElement.getValueOfTextInput());
    }
    public void submitForm(){
        buttonElement.click();
    }
    public String convertDateToString (LocalDate localDate, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return localDate.format(formatter);
    }
    public boolean ifDivOutputContainsThisText(String searchPattern) {
        return standartWaiter.waitForTextMatches(getLocatorId(LocatorsEnum.OUTPUT), searchPattern);
    }



}
