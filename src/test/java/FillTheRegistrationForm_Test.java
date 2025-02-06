import asserts.AssertWithLog;
import components.LocatorsEnum;
import components.RegFormComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.WebDriver;
import userdata.NewUserData;
import webdriver.WebDriverFactory;

import static org.junit.jupiter.api.Assertions.assertAll;


public class FillTheRegistrationForm_Test {

    private static final Logger logger = LogManager.getLogger(FillTheRegistrationForm_Test.class);
    private WebDriver driver;
    private final WebDriverFactory webDriverFactory = new WebDriverFactory();
    private AssertWithLog assertWithLog = null;
    private RegFormComponent regFormComponent = null;

    @BeforeEach
    void beforeEach() {
        String webDriverName = System.getProperty("browser", "firefox").toLowerCase();
        driver = webDriverFactory.create(webDriverName);
        regFormComponent = new RegFormComponent(driver);
        assertWithLog = new AssertWithLog(driver, logger);
    }

    @DisplayName("Заполнение, отправка формы регистрации и проверка результатов")
    @ParameterizedTest
    @CsvSource(value = {"form.html"}, ignoreLeadingAndTrailingWhitespace = true)
/*          "form1.html, 2324fewervef",
*            - с такой записью, можно передавать несколько переменных в тест.
*            Т.о. одна из них - урл, где проверять, другие - по необходимости относящиеся к самой проверке
*            при желании можно даже файл настроек в .csv сюда передавать, если переменных много, чтоб не захламлять класс
*/
    void fillTheFormAndCheckResults(String pageUrl)  {
        regFormComponent.openPage(pageUrl);
        NewUserData newUserData = new NewUserData(driver, "en");

        regFormComponent.getUsernameElement().writeIntoTextInput(newUserData.getUsername());
        regFormComponent.getEmailElement().writeIntoTextInput(newUserData.getEmail());
        regFormComponent.getPasswordElement().writeIntoTextInput(newUserData.getPassword());
        regFormComponent.getConfirmElement().writeIntoTextInput(newUserData.getPassword());
        regFormComponent.writeIntoInputBirthday(newUserData.getBirthday());
        regFormComponent.selectLanguageLevel(newUserData.getLanguageLevel());
        regFormComponent.submitForm();

        assertAll(
                () -> assertWithLog.assertWithLog(regFormComponent.checkIfPasswordIsEqualToConfirmation(),
                pageUrl + " совпадение подтверждения пароля"),
                () -> assertWithLog.assertWithLog(
                regFormComponent.ifDivOutputContainsThisText(newUserData.getUsername()),
                pageUrl + " имя на выводе"),
                () -> assertWithLog.assertWithLog(
                regFormComponent.ifDivOutputContainsThisText(newUserData.getEmail()),
                pageUrl + " почта на выводе"),
                () -> assertWithLog.assertWithLog(
                regFormComponent.ifDivOutputContainsThisText(regFormComponent.convertDateToString(newUserData.getBirthday(), "yyyy-MM-dd")),
                pageUrl + " дата рождения на выводе"),
                () -> assertWithLog.assertWithLog(
                regFormComponent.ifDivOutputContainsThisText(newUserData.getLanguageLevel()),
                pageUrl + " уровень языка на выводе")
        );
    }


    @AfterEach
    void tearDown() {
        if (driver != null) driver.quit();
    }
}
