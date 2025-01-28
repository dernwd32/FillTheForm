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
    MD5 md5 = new MD5();

    RegFormComponent regFormComponent = null;

    @BeforeEach
    void beforeEach() {
        //передаем аргументы запуска и получаем готовый вебдрайвер по заданным параметрам
        driver = webDriverFactory.webDriverFactory(".");

        regFormComponent = new RegFormComponent(driver);
        assertWithLog = new AssertWithLog(driver, logger);

    }

    @Test
    @DisplayName("Заполнение, отправка формы регистрации и проверка результатов")
    void FillTheFormAndCheckResults()  {
/*
//Так можно проверить компонент по этому сценарию на разных страницах

        boolean page = FillTheFormAndCheckResults_body("form.html");
        boolean page2 = FillTheFormAndCheckResults_body("form2.html");
        boolean page3 = FillTheFormAndCheckResults_body("form3.html");

        assertAll(
                () ->  assertTrue(page),
                () ->  assertTrue(page2),
                () ->  assertTrue(page3)
        );
 */

        String pageUrl = "form.html";
        String missmatches = FillTheFormAndCheckResults_body(pageUrl);
        String message = "FillTheFormAndCheckResults on " + pageUrl;
        boolean formResultsMatched = missmatches.isEmpty();
        if (!formResultsMatched) message +=  " (fails: " + missmatches + ")";

        assertWithLog.assertWithLog(formResultsMatched, message);
    }


    String FillTheFormAndCheckResults_body(String pageUrl) {
        regFormComponent.openPage(pageUrl);
        // ...
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
 //       regFormComponent.clickForSubmitForm();

        //надо ли проверять правильность работы js проверок каждого поля?

        //отправляем форму, перехватывая алерт о несовпадении пароля
        // если алерт появился - тест зафейлен из-за несовпадения пароля и его подтверждение
        if (regFormComponent.clickForSubmitForm() != null) return regFormComponent.clickForSubmitForm();

        //если пароль из окружения не совпадает с требуемым для "авторизации", фейлим тест
        if (!regFormComponent.checkIfPasswordsInputsAreEqual()) return "Неправильный пароль из консоли!";;

        //если с паролями все норм, проверяем остальные поля на соответствие полученных значений введённым
        return regFormComponent.ifValuesMatchesInDivOutput(fullname, email, birthday, randomLanguageLevelValue );
    }

    @AfterEach
    void tearDown() {
        if (driver != null) driver.close();
    }
}
