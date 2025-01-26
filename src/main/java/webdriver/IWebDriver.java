package webdriver;

import org.openqa.selenium.WebDriver;

public interface IWebDriver {

    //def - default подставляется, если не указано значение в окружении
    String BROWSER_DRIVER = System.getProperty("browser", "firefox");
    long DEFAULT_IMPLICITLY_DURATION = Integer.parseInt(System.getProperty("waiter.timeout", "5000"));

    WebDriver webDriverFactory(String mode);

}
