package userdata;

import com.github.javafaker.Faker;
import components.RegFormComponent;
import org.openqa.selenium.WebDriver;

import java.util.Date;
import java.util.Locale;

public class NewUserData {
    private String username;
    private String email;
    private String password;
    private Date birthday;
    private String languageLevel;

    public NewUserData(WebDriver driver, String locale) {
        Faker faker = new Faker(new Locale(locale));
        RegFormComponent regFormComponent = new RegFormComponent(driver);
        this.username = faker.name().fullName();
        this.email = faker.internet().emailAddress();
        this.password = System.getProperty("password", "qweasdzxc");
        this.birthday = faker.date().birthday();
        this.languageLevel = regFormComponent.generateRandomLanguageLevel();
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getLanguageLevel() {
        return languageLevel;
    }


}
