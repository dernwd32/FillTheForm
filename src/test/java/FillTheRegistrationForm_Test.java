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
        //String missmatches = FillTheFormAndCheckResults_body(pageUrl);


        String fullname = faker.name().fullName();
        String email = faker.internet().emailAddress();
        String randomPassword = faker.internet().password(10,15);
            //System.out.println(randomPassword);
        Date birthday = faker.date().birthday();
            //System.out.println(birthday);
        regFormComponent.writeIntoInputUsername(fullname);
        regFormComponent.writeIntoInputEmail(email);
        regFormComponent.writeIntoInputPassword(randomPassword);
        regFormComponent.writeIntoInputConfirmPassword(randomPassword);
        regFormComponent.writeIntoInputBirthday(birthday);
        String randomLanguageLevelValue = regFormComponent.generateRandomLanguageLevel();
        regFormComponent.selectLanguageLevel(randomLanguageLevelValue);
        //regFormComponent.clickForSubmitForm();

        //отправляем форму, перехватывая алерт о несовпадении пароля
        // если алерт появился - тест зафейлен из-за несовпадения пароля и его подтверждение
        boolean passwordDoesntMatchConfirmation = regFormComponent.clickForSubmitForm();
        //если пароль из окружения не совпадает с требуемым для "авторизации", фейлим тест
        boolean passwordFromEnvIsIncorrect = regFormComponent.checkIfPasswordFromEnvIsCorrect();


        if (!passwordFromEnvIsIncorrect || !passwordDoesntMatchConfirmation) {
            assertWithLog.assertWithLog(passwordDoesntMatchConfirmation, "подтверждение пароля");
            assertWithLog.assertWithLog(passwordFromEnvIsIncorrect, "пароль из консоли");
        }

        //если с паролями все норм, проверяем остальные поля на соответствие полученных значений введённым

        assertAll(
                () ->  assertWithLog.assertWithLog(regFormComponent.ifNameMatchesInDivOutput(fullname), "имя на выводе"),
                () ->  assertWithLog.assertWithLog(regFormComponent.ifEmailMatchesInDivOutput(email), "почта на выводе"),
                () ->  assertWithLog.assertWithLog(regFormComponent.ifBirthdayMatchesInDivOutput(birthday), "дата рождения на выводе"),
                () ->  assertWithLog.assertWithLog(regFormComponent.ifLanguageLevelMatchesInDivOutput(randomLanguageLevelValue), "уровень языка на выводе")
        );

    }


    @AfterEach
    void tearDown() {
        if (driver != null) driver.close();
    }
}
