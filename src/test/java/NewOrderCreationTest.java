import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.yandex.prakticum.model.MainPageSamokat;
import ru.yandex.prakticum.model.OrderPageSamokat;

import static ru.yandex.prakticum.model.MainPageSamokat.HEADER_ORDER_BUTTON;
import static ru.yandex.prakticum.model.MainPageSamokat.HOME_ORDER_BUTTON;
import static ru.yandex.prakticum.model.OrderPageSamokat.BLACK_COLOR;
import static ru.yandex.prakticum.model.OrderPageSamokat.GREY_COLOR;

@RunWith(Parameterized.class)
public class NewOrderCreationTest {
    private final By orderButton;
    private final String name;
    private final String surname;
    private final String address;
    private final String metroStation;
    private final String phoneNumber;
    private final String date;
    private final String rentalPeriod;
    private final By color;
    private final String comment;
    private WebDriver driver;

    public NewOrderCreationTest(By orderButton, String name, String surname, String address,
                                String metroStation, String phoneNumber, String date, String rentalPeriod, By color, String comment) {
        this.orderButton = orderButton;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metroStation = metroStation;
        this.phoneNumber = phoneNumber;
        this.date = date;
        this.rentalPeriod = rentalPeriod;
        this.color = color;
        this.comment = comment;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][]{
                {HEADER_ORDER_BUTTON,
                        "Оксана", "Иванова", "ул. 5-я Магистральная, д. 5", "Хорошёво", "+78005353000",
                        "25.05.2023", "сутки", BLACK_COLOR, "Привет,мир!"},
                {HOME_ORDER_BUTTON,
                        "Никита", "Петров", "ул. Буженинова, д. 22", "Преображенская площадь", "89131111100",
                        "28.05.2023", "семеро суток", GREY_COLOR, "Хорошего дня!"},
        };
    }

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
    }

    @Test
    public void createNewOrderTest() {
        MainPageSamokat mainPage = new MainPageSamokat(driver);
        mainPage.open()
                .scrollToOrderButton(orderButton)
                .clickOrderButton(orderButton);
        OrderPageSamokat orderPage = new OrderPageSamokat(driver);
        orderPage.setDataFirstPageOrder(name, surname, address, metroStation, phoneNumber)
                .setDataSecondPageOrder(date, rentalPeriod, color, comment)
                .clickYesButton();
        boolean isDisplayed = orderPage.isOrderWindowDisplayed();
        Assert.assertTrue(isDisplayed);
        orderPage.getTextFromPopupOrderWindow();
        System.out.println("Тест пройден");
    }

    @After
    public void teardown() {
        driver.quit();
    }
}