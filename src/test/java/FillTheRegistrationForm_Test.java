import asserts.AssertWithLog;
import com.github.javafaker.Faker;
import components.RegFormComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.util.NameUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import tools.MD5;
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

    @Test
    @DisplayName("Заполнение, отправка формы регистрации и проверка результатов")
    void FillTheFormAndCheckResults()  {

        String pageUrl = "form.html";
        regFormComponent.openPage(pageUrl);

        String fullname = faker.name().fullName();
        String email = faker.internet().emailAddress();
        String passwordFromEnv = System.getProperty("password", "qweasdzxc"); //это значение намерено неверное
        Date birthday = faker.date().birthday();
        regFormComponent.writeIntoInputUsername(fullname);
        regFormComponent.writeIntoInputEmail(email);
        regFormComponent.writeIntoInputPassword(passwordFromEnv);
        regFormComponent.writeIntoInputConfirmPassword(passwordFromEnv);
        regFormComponent.writeIntoInputBirthday(birthday);
        String randomLanguageLevelValue = regFormComponent.generateRandomLanguageLevel();
        regFormComponent.selectLanguageLevel(randomLanguageLevelValue);

        //отправка формы, перехват алерта о несовпадении пароля с подтверждением
        boolean passwordDoesntMatchConfirmation = regFormComponent.clickForSubmitForm();
        //проверка пароля из окружения на совпадение с требуемым для "авторизации"
        boolean passwordFromEnvIsIncorrect = regFormComponent.checkIfPasswordFromEnvIsCorrect(passwordFromEnv);
        if (!passwordFromEnvIsIncorrect || !passwordDoesntMatchConfirmation)
            assertAll(
                    () -> assertWithLog.assertWithLog(passwordDoesntMatchConfirmation, pageUrl + " подтверждение пароля"),
                    () -> assertWithLog.assertWithLog(passwordFromEnvIsIncorrect, pageUrl + " пароль из консоли")
            );

        //если с паролями все норм, форма отправлена, проверяем остальные поля на соответствие полученных значений введённым
        assertAll(
                () ->  assertWithLog.assertWithLog(regFormComponent.ifNameMatchesInDivOutput(fullname), pageUrl + " имя на выводе"),
                () ->  assertWithLog.assertWithLog(regFormComponent.ifEmailMatchesInDivOutput(email), pageUrl + " почта на выводе"),
                () ->  assertWithLog.assertWithLog(regFormComponent.ifBirthdayMatchesInDivOutput(birthday), pageUrl + " дата рождения на выводе"),
                () ->  assertWithLog.assertWithLog(regFormComponent.ifLanguageLevelMatchesInDivOutput(randomLanguageLevelValue), pageUrl + " уровень языка на выводе")
        );

    }


    @AfterEach
    void tearDown() {
        if (driver != null) driver.close();
    }
}
