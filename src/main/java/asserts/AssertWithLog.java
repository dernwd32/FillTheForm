package asserts;

import org.apache.logging.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class AssertWithLog {
    private WebDriver driver = null;
    private org.apache.logging.log4j.Logger logger = null;

    //конструктор с передачей текущего драйвера и логгера
    public AssertWithLog(WebDriver driver, org.apache.logging.log4j.Logger logger) {
        this.driver = driver;
        this.logger = logger;
    }

    //дефолтный конструктор
    public AssertWithLog(){};

    // Метод принимающий только условие, автоматически вычисляющий всё остальное.
    // Работает с конструктором AssertWithLog(WebDriver driver, ILog ILog)
    public void assertWithLog(boolean condition) {

        // если не передано значение message, получаем StackTrace для того, чтоб узнать имя тестового метода,
        // вызывающего assertWithLog
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        assertWithLog(
                condition,
                //по дефолту отправляем в message название тестового метода, из которого вызван текущий assertWithLog
                stackTrace[2].getMethodName()
        );
    }

    // Метод принимающий условие и сообщение, автоматически вычисляющий всё остальное
    // Работает с конструктором AssertWithLog(WebDriver driver, ILog ILog)
    public void assertWithLog(boolean condition, String message) {

        assertWithLog(
                condition,
                message,
                logger,
                ((RemoteWebDriver) driver).getCapabilities().getBrowserName()
        );

    }

    // основной перегруженный метод
    // работает с дефолтным конструктором
    public void assertWithLog(boolean condition, String message, Logger logger, String currentBrowser) {
        SoftAssertions softly = new SoftAssertions();
        message = String.format("%-125s",
                        String.format("%-11s", "[" + currentBrowser + "]")
                        + "-> "
                        + message
                  );

        String messagePass = message + " > PASS";
        String messageFail = message + " > FAIL";

        if (condition) logger.info(messagePass);
        else logger.error(messageFail);

        //assertTrue(condition);
        softly.assertThat(condition).isTrue();
    }
}
