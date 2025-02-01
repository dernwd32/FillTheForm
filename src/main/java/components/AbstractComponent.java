package components;

import annotations.ComponentBlueprint;
import com.github.javafaker.Faker;
import org.openqa.selenium.WebDriver;
import waiters.StandartWaiter;

public abstract class AbstractComponent {
    protected WebDriver driver = null;
    protected StandartWaiter standartWaiter = null;
    protected Faker faker = new Faker();

    public AbstractComponent(WebDriver driver){
        this.driver = driver;
        standartWaiter = new StandartWaiter(driver);
    }

    public Object getMetaValues(String metaName) {
        Class clazz = this.getClass();
        if(clazz.isAnnotationPresent(ComponentBlueprint.class)) {
            ComponentBlueprint componentBlueprint = (ComponentBlueprint) clazz.getDeclaredAnnotation(ComponentBlueprint.class);
            switch (metaName){
                case "rootLocator" -> { return componentBlueprint.rootLocator();}
                case "someThingElse" -> { return componentBlueprint.someThingElse();}
            }
        }
        return "";
    }
}
