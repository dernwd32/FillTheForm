package pages;

public interface IPage {
    String BASE_URL = System.getProperty("base.url");

    void openPage(String pageUrl);
}
