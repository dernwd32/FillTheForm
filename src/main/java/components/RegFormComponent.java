package components;

import annotations.ComponentBlueprint;
import org.openqa.selenium.*;
import pages.IPage;
import webelements.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@ComponentBlueprint(rootLocator = "form#registrationForm")
public class RegFormComponent extends AbstractComponent implements IPage {

    String BASE_URL = System.getProperty("base.url");

    public RegFormComponent(WebDriver driver){
          super(driver);
    }

    @Override
    public void openPage(String pageUrl) {
        driver.get(BASE_URL + pageUrl);
    }

    private final SelectboxElement selectboxElement = new SelectboxElement(driver);
    private final TextInputElement textInputElement = new TextInputElement(driver);
    private final ButtonElement buttonElement = new ButtonElement(driver);
    private String rootLocator = (String) getMetaValues("rootLocator");
    private final By languageDropdownOptionsSelector = By.cssSelector(rootLocator + " #language_level option");
    private final By inputSubmitBtnSelector = By.cssSelector(rootLocator + " input[type='submit']");


    public By getLocatorId(LocatorsEnum id) {
        //не будет работать, если айди в доме не полностью в нижнем регистре
        return By.id(id.name().toLowerCase());
    }
    public By getLocatorId(String id) {
        return By.id(id);
    }

    public void writeIntoThisTextInput(By locator, String value) {
        textInputElement.writeIntoTextInput(locator, value);
    }
    public void writeIntoInputBirthday(LocalDate birthday) {
        textInputElement.writeIntoSpecialTextInput(getLocatorId(LocatorsEnum.BIRTHDATE), convertDateToString(birthday, "ddMMyyyy"));
    }
    public ArrayList<String> getOptionsOfLanguageLevel() {
        return selectboxElement.getOptionsOfSelectbox(languageDropdownOptionsSelector);
    }
    public String generateRandomLanguageLevel() {
        return selectboxElement.getRandomOptionFromList(getOptionsOfLanguageLevel());
    }
    public void selectLanguageLevel(String value) {
        selectboxElement.setValueOfSelectbox(getLocatorId(LocatorsEnum.LANGUAGE_LEVEL), value);
    }
    public boolean checkIfPasswordIsEqualToConfirmation() {
        return textInputElement.getValueOfTextInput(getLocatorId(LocatorsEnum.PASSWORD))
                .equals(textInputElement.getValueOfTextInput(getLocatorId(LocatorsEnum.CONFIRM_PASSWORD)));
    }
    public void submitForm(){
        buttonElement.clickButton(inputSubmitBtnSelector);
    }
    public String convertDateToString (LocalDate localDate, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return localDate.format(formatter);
    }
    public boolean ifDivOutputContainsThisText(String searchPattern) {
        return standartWaiter.waitForTextMatches(getLocatorId(LocatorsEnum.OUTPUT), searchPattern);
    }



}
