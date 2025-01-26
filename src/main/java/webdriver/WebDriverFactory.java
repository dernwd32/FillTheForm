package webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;


public class WebDriverFactory implements IWebDriver{


    @Override
    public WebDriver webDriverFactory(String mode)  {

        WebDriver driver;

        switch (BROWSER_DRIVER) {

            case "firefox" -> {
                FirefoxOptions options = new FirefoxOptions();
                //Если начинается с дефиса - передаём в options браузера, иначе - через driver.manage
                if (mode.charAt(0) == '-') options.addArguments(mode);
                driver = new FirefoxDriver(options);
            }
            case "chrome" -> {
                ChromeOptions options = new ChromeOptions();
                if (mode.charAt(0) == '-') options.addArguments(mode);
                driver = new ChromeDriver(options);
            }
            default -> {
               throw new RuntimeException(String.format("Browser <%s> is not supported by the factory", BROWSER_DRIVER));
            }

        }

        //режимы, передаваемые в driver.manage без "-" в начале
        switch (mode) {
            case "maximize" -> driver.manage().window().maximize();
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(DEFAULT_IMPLICITLY_DURATION));

        return driver;
    }
}
