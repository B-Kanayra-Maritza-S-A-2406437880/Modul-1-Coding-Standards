package id.ac.ui.cs.advprog.eshop.functional;

import id.ac.ui.cs.advprog.eshop.EshopApplication;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = EshopApplication.class)
class OrderFunctionalTest {
    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;
    private ChromeDriver driver;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void pageTitle_isCorrect() {
        driver.get(baseUrl + "/order/create");
        String pageTitle = driver.getTitle();
        assertEquals("Create Order", pageTitle);
    }

    @Test
    void createOrderForm_isDisplayed() {
        driver.get(baseUrl + "/order/create");
        WebElement form = driver.findElement(By.tagName("form"));
        assertTrue(form.isDisplayed());
    }

    @Test
    void orderHistoryForm_isDisplayed() {
        driver.get(baseUrl + "/order/history");
        WebElement form = driver.findElement(By.tagName("form"));
        assertTrue(form.isDisplayed());
    }

    @Test
    void orderHistoryForm_hasAuthorInput() {
        driver.get(baseUrl + "/order/history");
        WebElement input = driver.findElement(By.id("author"));
        assertTrue(input.isDisplayed());
    }
}