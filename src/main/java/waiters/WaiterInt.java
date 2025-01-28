package waiters;

import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * Интерфейс стандартного набора ожиданий
 * @author Pavel Balahonov <p.balahonov@corp.mail.ru>
 */
public interface WaiterInt {
  boolean waitForCondition(ExpectedCondition condition, long timeout);
  long DEFAULT_WAITER_TIMEOUT = Integer.parseInt(System.getProperty("waiter.timeout", "1000"));
}
