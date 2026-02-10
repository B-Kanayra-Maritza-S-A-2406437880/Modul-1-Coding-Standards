package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CreateProductFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    @DisplayName("Verify Product List page is accessible and has Create button")
    void testNavigationAndButtonDisplay(ChromeDriver driver) {
        driver.get(baseUrl + "/product/list");
        assertEquals("Product List", driver.getTitle());
        WebElement createButton = driver.findElement(By.linkText("Create Product"));
        assertTrue(createButton.isDisplayed());
    }

    @Test
    @DisplayName("Verify Create Product form elements and submission")
    void testCreateProductProcess(ChromeDriver driver) {
        driver.get(baseUrl + "/product/create");
        assertEquals("Create New Product", driver.getTitle());

        String productName = "Sampo Cap Bambang";
        String productQuantity = "10";

        // Isi form
        driver.findElement(By.id("nameInput")).sendKeys(productName);
        driver.findElement(By.id("quantityInput")).sendKeys(productQuantity);
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Verifikasi redirect dan data muncul di tabel
        assertEquals(baseUrl + "/product/list", driver.getCurrentUrl());
        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains(productName));
        assertTrue(pageSource.contains(productQuantity));
    }

    @Test
    @DisplayName("Verify multiple products are correctly ordered in the list")
    void testCreateMultipleProducts(ChromeDriver driver) {
        // Buat produk
        createProductHelper(driver, "Sampo Cap Tikus", "1");
        createProductHelper(driver, "Sampo Cap Cip Cup", "3");

        driver.get(baseUrl + "/product/list");

        // Ambil elemen baris pertama dan kedua
        WebElement row1 = driver.findElement(By.xpath("//tbody/tr[1]"));
        WebElement row2 = driver.findElement(By.xpath("//tbody/tr[2]"));

        // Verifikasi konten di baris 1
        assertTrue(row1.getText().contains("Sampo Cap Tikus"), "Baris 1 harus mengandung nama produk pertama");
        assertTrue(row1.getText().contains("1"), "Baris 1 harus mengandung quantity produk pertama");

        // Verifikasi konten di baris 2
        assertTrue(row2.getText().contains("Sampo Cap Cip Cup"), "Baris 2 harus mengandung nama produk kedua");
        assertTrue(row2.getText().contains("3"), "Baris 2 harus mengandung quantity produk kedua");
    }

    // Helper method
    private void createProductHelper(ChromeDriver driver, String name, String quantity) {
        driver.get(baseUrl + "/product/create");
        driver.findElement(By.id("nameInput")).sendKeys(name);
        driver.findElement(By.id("quantityInput")).sendKeys(quantity);
        driver.findElement(By.cssSelector("button[type='submit']")).click();
    }
}