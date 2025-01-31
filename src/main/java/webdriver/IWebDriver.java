package webdriver;

import org.openqa.selenium.WebDriver;

public interface IWebDriver {

    //def - default подставляется, если не указано значение в окружении
    long DEFAULT_IMPLICITLY_DURATION = Integer.parseInt(System.getProperty("waiter.timeout", "5000"));

    WebDriver create(String webDriverName);
    WebDriver create(String browser, String mode);

}
