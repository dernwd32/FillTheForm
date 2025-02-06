package waiters;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.regex.Pattern;

public class StandartWaiter implements WaiterInt {

  private WebDriver driver = null;

  public StandartWaiter(WebDriver driver) {
    this.driver = driver;
  }


  public boolean waitForCondition(ExpectedCondition condition) {
    return waitForCondition( condition, DEFAULT_WAITER_TIMEOUT);
  }

  @Override
  public boolean waitForCondition(ExpectedCondition condition, long timeout) {
    WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofMillis(timeout));
    try {
      webDriverWait.until(condition);
      return true;
    } catch (Exception ex) {
      return false;
    }
  }

  public boolean waitForTextMatches(By locator, String pattern) {
    return waitForCondition(ExpectedConditions.textMatches(locator, Pattern.compile(pattern)), 2500);
  }
  public boolean waitToBeClickable(By locator) {
    return waitForCondition(ExpectedConditions.elementToBeClickable(locator));
  }


}