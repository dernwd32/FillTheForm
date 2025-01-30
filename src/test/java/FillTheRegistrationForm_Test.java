import asserts.AssertWithLog;
import com.github.javafaker.Faker;
import components.RegFormComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.WebDriver;
import webdriver.WebDriverFactory;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


public class FillTheRegistrationForm_Test {
    //подключаем логгер
    private static final Logger logger = LogManager.getLogger(FillTheRegistrationForm_Test.class);
    WebDriver driver;
    WebDriverFactory webDriverFactory = new WebDriverFactory();
    AssertWithLog assertWithLog = null;
    Faker faker = new Faker(new Locale("en"));

    RegFormComponent regFormComponent = null;

    @BeforeEach
    void beforeEach() {
        driver = webDriverFactory.webDriverFactory("maximize");
        regFormComponent = new RegFormComponent(driver);
        assertWithLog = new AssertWithLog(driver, logger);
    }

    //@Test
    @DisplayName("Заполнение, отправка формы регистрации и проверка результатов")
    @ParameterizedTest
    @CsvSource(value = {
            "form.html"
/*          "form1.html, 2324fewervef",
*            - с такой записью, можно передавать несколько переменных в тест.
*            Т.о. одна из них - урл, где проверять, другие - по необходимости относящиеся к самой проверке
*            при желании можно даже файл настроек в .csv сюда передавать, если переменных много, чтоб не захламлять класс
*/
    }, ignoreLeadingAndTrailingWhitespace = true)
    void FillTheFormAndCheckResults(String pageUrl)  {
        regFormComponent.openPage(pageUrl);

        String username = faker.name().fullName();
        String email = faker.internet().emailAddress();
        String passwordFromEnv = System.getProperty("password", "qweasdzxc"); //это значение намерено неверное
        Date birthday = faker.date().birthday();
        regFormComponent.writeIntoThisTextInput("username", username);
        regFormComponent.writeIntoThisTextInput("email", email);
        regFormComponent.writeIntoThisTextInput("password", passwordFromEnv);
        regFormComponent.writeIntoThisTextInput("confirm_password", passwordFromEnv);
        regFormComponent.writeIntoInputBirthday(birthday);
        String randomLanguageLevelValue = regFormComponent.generateRandomLanguageLevel();
        regFormComponent.selectLanguageLevel(randomLanguageLevelValue);

        //отправка формы, перехват алерта о несовпадении пароля с подтверждением
        boolean passwordMatchConfirmation = regFormComponent.clickForSubmitFormAndAnswerIfThereWasNotAlert();
        //проверка пароля из окружения на совпадение с требуемым для "авторизации"
        boolean passwordFromEnvIsCorrect = regFormComponent.checkIfPasswordFromEnvIsCorrect(passwordFromEnv);
        if (!passwordFromEnvIsCorrect || !passwordMatchConfirmation)
            assertAll(
                    () -> assertWithLog.assertWithLog(passwordMatchConfirmation, pageUrl + " подтверждение пароля"),
                    () -> assertWithLog.assertWithLog(passwordFromEnvIsCorrect, pageUrl + " пароль из консоли")
            );

        //если с паролями все норм, форма отправлена, проверяем остальные поля на соответствие полученных значений введённым
        assertAll(
                () ->  assertWithLog.assertWithLog(regFormComponent.ifDivOutputContainsThisText("output", username),
                        pageUrl + " имя на выводе"),
                () ->  assertWithLog.assertWithLog(regFormComponent.ifDivOutputContainsThisText("output", email),
                        pageUrl + " почта на выводе"),
                () ->  assertWithLog.assertWithLog(regFormComponent.ifDivOutputContainsThisText("output", new SimpleDateFormat("yyyy-MM-dd").format(birthday)),
                        pageUrl + " дата рождения на выводе"),
                () ->  assertWithLog.assertWithLog(regFormComponent.ifDivOutputContainsThisText("output", randomLanguageLevelValue),
                        pageUrl + " уровень языка на выводе")
        );

    }


    @AfterEach
    void tearDown() {
        if (driver != null) driver.close();
    }
}
